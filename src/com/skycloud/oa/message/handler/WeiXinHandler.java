package com.skycloud.oa.message.handler;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jeewx.api.core.req.model.message.IndustryTemplateMessageSend;
import org.jeewx.api.core.req.model.message.TemplateMessage;
import org.jeewx.api.wxbase.wxtoken.JwTokenAPI;
import org.jeewx.api.wxsendmsg.JwTemplateMessageAPI;

import com.google.gson.Gson;
import com.skycloud.oa.config.Global;
import com.skycloud.oa.utils.Common;

/**
 * 微信消息组件
 * @ClassName: WeiXinHandler 
 * @Description: TODO
 * @author xie
 * @date 2016年4月26日 上午9:25:59
 */
public class WeiXinHandler
{
	private Logger LOG = Logger.getLogger(WeiXinHandler.class);

	private static WeiXinHandler weiXinHandler;

	/**
	 * 单例实例化
	 * 
	 * @Description: TODO
	 * @author xie
	 * @date 2016年1月9日 下午9:49:57
	 * @return
	 */
	public static synchronized WeiXinHandler getInstance()
	{
		if (Common.empty(weiXinHandler)) {
			weiXinHandler = new WeiXinHandler();
		}
		return weiXinHandler;
	}
	
	/**
	 * 发送微信消息
	 * @Description: TODO
	 * @author xie
	 * @date 2016年5月7日 下午9:19:49  
	 * @param map
	 * @param code
	 * @param userId
	 */
	public void handlerMsg(Map<String,Object> map,String code,String openids) {
		Object template = Global.msgTmp.get(code).get("template");
		if(!Common.empty(template) && !Common.empty(openids)) {
			IndustryTemplateMessageSend its = new Gson().fromJson(template.toString(), IndustryTemplateMessageSend.class);
			try {
				String token = JwTokenAPI.getAccessToken(Global.cloudConfig.get("WXAppID").toString(),Global.cloudConfig.get("WXAppSecret").toString());
				its.setAccess_token(token);
				
				for(String openid : openids.split(",")) {
					its.setTouser(openid);
					TemplateMessage data = its.getData();
					data.getFirst().setValue(match(map, data.getFirst().getValue()));
					
					if(!Common.empty(data.getKeyword1())) {
						data.getKeyword1().setValue(match(map, data.getKeyword1().getValue()));
					}
					
					if(!Common.empty(data.getKeyword2())) {
						data.getKeyword2().setValue(match(map, data.getKeyword2().getValue()));
					}
					
					if(!Common.empty(data.getKeyword3())) {
						data.getKeyword3().setValue(match(map, data.getKeyword3().getValue()));
					}
					
					if(!Common.empty(data.getKeyword4())) {
						data.getKeyword4().setValue(match(map, data.getKeyword4().getValue()));
					}
					
					data.getRemark().setValue(match(map, data.getRemark().getValue()));
					
					String msg = JwTemplateMessageAPI.sendTemplateMsg(its);//发送消息
					LOG.error("发送微信信息:"+msg);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error("发送微信信息错误:"+e.getMessage());
			}
		}
	}
	
	private String match(Map<String, Object> map, String template)
	{
		String regex = "\\{\\{.*?\\}\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(template);
		while (matcher.find()) {// 替换关键字
			String key = matcher.group();
			String _key = key.replaceAll("\\{", "").replaceAll("\\}", "");
			template = template.replaceAll(_key, map.containsKey(_key) ? map.get(_key).toString() : "***");
		}
		return template.replaceAll("\\{", "").replaceAll("\\}", "");
	}
	
	public static void main(String[] args) {
		try{
			JwTokenAPI.getAccessToken("wx48c2d3cee4888853","1fac7726e945cee0aeac2940036f239e");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
