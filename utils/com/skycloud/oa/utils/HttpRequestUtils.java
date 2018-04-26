package com.skycloud.oa.utils;

import java.io.IOException;
import java.net.URLDecoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * http请求
 * @author Administrator
 *
 */
public class HttpRequestUtils {

	private static Logger logger = Logger.getLogger(HttpRequestUtils.class);    //日志记录
	public final static String IP="http://192.168.1.229/"; 
	public static JSONObject httpPost(String url,String param) {
		 JSONObject json = null;
		 //post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost method = new HttpPost(url);
        try {
            if (null != param) {
                //解决中文乱码问题
                StringEntity entity = new StringEntity(param, "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/x-www-form-urlencoded");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /**请求发送成功，并得到响应**/
            if (result.getStatusLine().getStatusCode() == 200) {
                String str = "";
                try {
                    /**读取服务器返回过来的json字符串数据**/
                    str = EntityUtils.toString(result.getEntity());
                    /**把json字符串转换成json对象**/
                    json = JSONObject.parseObject(str);
                } catch (Exception e) {
                    logger.error("post请求提交失败:" + url, e);
                }
            }
        } catch (IOException e) {
            logger.error("post请求提交失败:" + url, e);
        }
		
		return json;
	}
	
	public static void main(String[] args) {
		//查询
		/*JSONObject json = httpPost("http://127.0.0.1:8080/get","notifies=20160504001,20160504002");
		System.out.println(json.get("data").toString());*/
		//开票
		JSONArray array = new JSONArray();
		for(int i = 0;i<5;i++) {
			JSONObject obj = new JSONObject();
			obj.put("notify", "2016050600"+i);//通知单号
			obj.put("hpz", "D");//货品
			obj.put("ydb", "13");//调拨量(开票数量)
			obj.put("m_num", "13");//计划发送量
			obj.put("tankNo", "T01");////罐号
			obj.put("fInTime", "2016-05-04 11:11:12");//进库时间
			array.add(obj);
		}
		JSONObject obj = new JSONObject();
		obj.put("weights", array);
		JSONObject json = httpPost("http://192.168.1.229/create","weights="+obj.toJSONString());
		System.out.println(json);
	}
}
