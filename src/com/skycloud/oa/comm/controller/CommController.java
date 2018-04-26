package com.skycloud.oa.comm.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.skycloud.oa.comm.weight.SerialBean;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.utils.OaMsg;

/**
 * 地磅数据获取控制类
 * @ClassName: CommController 
 * @Description: 
 * @author xie
 * @date 2015年3月12日 上午9:35:32
 */
@Controller
@RequestMapping("/comm")
public class CommController {
	
	private static Logger LOG = Logger.getLogger(CommController.class);

	@RequestMapping(value = "read")
	@ResponseBody
	public Object read() {
		OaMsg oaMsg = new OaMsg();
		oaMsg.setMsg(SerialBean.result);
		return oaMsg;
	}

	/**
	 * 获取地磅数据
	 * @Description: 
	 * @author
	 * @date 2015年3月12日 上午9:35:13  
	 * @return
	 */
	@RequestMapping(value = "get")
	@ResponseBody
	public Object get(String url) 
	{
		OaMsg oaMsg = new OaMsg();
		try {
			URL localURL = new URL("http://"+url+":8080/comm/read");
			URLConnection connection = localURL.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

			httpURLConnection.setDoOutput(true);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
			httpURLConnection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			httpURLConnection.setReadTimeout(10000);

			
			InputStream inputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader reader = null;
			StringBuffer resultBuffer = new StringBuffer();
			String tempLine = null;
			try {

				if (httpURLConnection.getResponseCode() != 200) {
					oaMsg.setCode(Constant.SYS_CODE_SYS_ERR);
					oaMsg.setMsg("连接称重服务器超时");
					LOG.error("获取称重计数据错误:"+httpURLConnection.getResponseCode());
				}else {

					inputStream = httpURLConnection.getInputStream();
					inputStreamReader = new InputStreamReader(inputStream);
					reader = new BufferedReader(inputStreamReader);
	
					while ((tempLine = reader.readLine()) != null) {
						resultBuffer.append(tempLine);
					}
					oaMsg = new Gson().fromJson(resultBuffer.toString(), OaMsg.class);
					LOG.debug("节点检测结果:"+resultBuffer.toString());
				}
			} finally {

				if (reader != null) {
					reader.close();
				}

				if (inputStreamReader != null) {
					inputStreamReader.close();
				}

				if (inputStream != null) {
					inputStream.close();
				}

			}
			
		} catch (Exception e) {
			oaMsg.setCode(Constant.SYS_CODE_SYS_ERR);
			oaMsg.setMsg("连接称重服务器超时");
			LOG.error("获取称重计数据错误:"+e.getMessage());
		}
		return oaMsg;
	}
	
}
