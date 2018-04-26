package com.skycloud.oa.common.handler;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.skycloud.oa.common.dao.SecurityCodeDao;
import com.skycloud.oa.common.model.SecurityCode;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.SpringContextUtil;

public class SecurityCodeHandler
{
	private Logger LOG = Logger.getLogger(SecurityCodeHandler.class);

	private static SecurityCodeHandler securityCodeHandler;

	/**
	 * 单例实例化
	 * 
	 * @Description: TODO
	 * @author xie
	 * @date 2016年1月13日 下午7:39:57
	 * @return
	 */
	public static synchronized SecurityCodeHandler getInstance()
	{
		if (Common.empty(securityCodeHandler)) {
			securityCodeHandler = new SecurityCodeHandler();
		}
		return securityCodeHandler;
	}

	/**
	 * 判断验证码是否正确
	 * @Description: TODO
	 * @author xie
	 * @date 2016年1月20日 下午8:49:30  
	 * @param object
	 * @param code
	 * @param msg
	 * @return
	 */
	public boolean volidateCode(String object,String code,OaMsg msg) {
		SecurityCodeDao securityCodeDao = (SecurityCodeDao) SpringContextUtil.getBean("securityCodeDaoImpl");
		SecurityCode securityCode = new SecurityCode();
		securityCode.setObject(object);
		securityCode.setCode(code);
		List<Map<String, Object>> codeList = securityCodeDao.get(securityCode);
		if(!Common.empty(codeList) && codeList.size() > 0) {
			Gson gson = new Gson();
			securityCode = gson.fromJson(gson.toJson(codeList.get(0)), SecurityCode.class);
			if(!Common.empty(code) && !Common.empty(securityCode.getCode())) {
				if(System.currentTimeMillis()/1000 > securityCode.getFailTime()) {//验证码过期
					msg.setCode(Constant.SYS_CODE_TIME_OUT);
					msg.setMsg("验证码过期");
					LOG.debug("判断"+object+"的验证码"+code+"失败:验证码过期");
					return false;
				}else if(!code.equals(securityCode.getCode())){
					msg.setCode(Constant.SYS_CODE_PARAM_NULL);
					msg.setMsg("验证码错误");
					LOG.debug("判断"+object+"的验证码"+code+"失败:验证码错误");
					return false;
				}
			}else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("验证码不存在");
				LOG.debug("判断"+object+"的验证码"+code+"失败:验证码不存在");
				return false;
			}
		}else {//验证码不存在
			msg.setCode(Constant.SYS_CODE_NOT_EXIT);
			msg.setMsg("验证码不存在");
			LOG.debug("判断"+object+"的验证码"+code+"失败:验证码不存在");
			return false;
		}
		LOG.debug("判断"+object+"的验证码"+code+"通过");
		securityCodeDao.deleteUse(securityCode);
		msg.getMap().put("userId", securityCode.getUserId());
		return true;
	}
}