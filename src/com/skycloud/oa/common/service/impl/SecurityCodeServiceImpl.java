package com.skycloud.oa.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.common.dao.SecurityCodeDao;
import com.skycloud.oa.common.model.SecurityCode;
import com.skycloud.oa.common.service.SecurityCodeService;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.config.Global;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.message.handler.WeiXinHandler;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.VerifyCodeUtil;
import com.sun.jmx.snmp.Timestamp;

/**
 * 
 * @author Administrator
 *
 */
@Service
public class SecurityCodeServiceImpl implements SecurityCodeService {
	
	private static Logger LOG = Logger.getLogger(SecurityCodeServiceImpl.class);
	
	@Autowired
	private SecurityCodeDao securityCodeDao;

	@Override
	@Log(object=C.LOG_OBJECT.PCS_SECURITYCODE,type=C.LOG_TYPE.SEND)
	public void sendPermissionCode(String key,String userId,String content,OaMsg msg) {
		// TODO Auto-generated method stub
		if(Common.empty(key) || Common.empty(userId)) {
			msg.setCode(Constant.SYS_CODE_PARAM_NULL);
			msg.setMsg("必要的参数未选择");
			return;
		}
		Subject  subject = SecurityUtils.getSubject();
//		if(!subject.isPermitted(key)) {//判断用户是否有该权限
//			LOG.debug("");
//			msg.setCode(Constant.SYS_CODE_UNAUTHORIZE);
//			msg.setMsg("无权限");
//			return;
//		}
		try{
			List<Map<String,Object>> list = securityCodeDao.getPermissionOpenIds(key,userId);
			SecurityCode code = new SecurityCode();
			User user = (User) subject.getPrincipals().getPrimaryPrincipal();
			code.setObject(user.getId()+key);
			code.setType("2");
			code.setCreateTime(System.currentTimeMillis()/1000);
			long failTime = 30;
			try {
				failTime = Long.parseLong(Global.cloudConfig.get("valCodeFailTime").toString());
			}catch(Exception e) {
			}
			code.setFailTime(System.currentTimeMillis()/1000 + failTime*60);
			
			for(Map<String,Object> map : list) {
				if(map.containsKey("openid")) {
					code.setCode(VerifyCodeUtil.generateVerifyCode());
					code.setUserId(map.get("id").toString());
					securityCodeDao.create(code);

					map.put("title", user.getName()+"申请'"+map.get("name").toString()+"'的操作验证码。详情为：\r\n"+content);
					map.put("valCode", code.getCode());
					map.put("time", failTime);
					
					WeiXinHandler.getInstance().handlerMsg(map, "wxValcode", map.get("openid").toString());
				}
			}
		}catch(Exception e) {
			LOG.error(e);
		}
	}

	/**
	 * @Title sendMsg
	 * @Descrption:TODO
	 * @param:@param key
	 * @param:@param userId
	 * @param:@param content
	 * @param:@param msg
	 * @auhor jiahy
	 * @date 2016年8月31日上午8:43:53
	 * @throws
	 */
	@Override
	public void sendMsg(String key, String userId, String content, OaMsg msg) {
		try {
		//当前用户
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		//获取接收人Id,权限，openId
			List<Map<String,Object>> list = securityCodeDao.getPermissionOpenIds(key,userId);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
			for(Map<String, Object> map:list){
				map.put("code", sdf1.format(new Date()));
				map.put("title", "入库数量审核");
				map.put("time", sdf.format(new Date()));
				map.put("content",  user.getName()+"发送给您一条信息。详情为：\r\n"+content);
				WeiXinHandler.getInstance().handlerMsg(map, "wxMsg", map.get("openid").toString());
			}
		} catch (OAException e) {
			e.printStackTrace();
		}
		
	}
}
