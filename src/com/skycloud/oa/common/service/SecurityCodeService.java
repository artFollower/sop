package com.skycloud.oa.common.service;

import com.skycloud.oa.utils.OaMsg;

/**
 * 验证码业务逻辑处理类
 * @author Administrator
 *
 */
public interface SecurityCodeService {

	
	/**
	 * 发送权限认证的验证码
	 * @param msg
	 */
	void sendPermissionCode(String key,String userId,String content,OaMsg msg);

	/**
	 * @Title sendMsg
	 * @Descrption:TODO
	 * @param:@param key
	 * @param:@param userId
	 * @param:@param content
	 * @param:@param msg
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年8月31日上午8:43:42
	 * @throws
	 */
	void sendMsg(String key, String userId, String content, OaMsg msg);
}
