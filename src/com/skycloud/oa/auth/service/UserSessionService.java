package com.skycloud.oa.auth.service;

import org.apache.shiro.session.Session;

import com.skycloud.oa.utils.OaMsg;

/**
 * 用户session业务逻辑处理接口类
 * @ClassName: UserSessionService 
 * @Description: TODO
 * @author xie
 * @date 2015年9月28日 下午3:24:07
 */
public interface UserSessionService
{
	
	/**
	 * 查看所有在线用户
	 * @Description: TODO
	 * @author xie
	 * @date 2015年9月28日 下午3:26:00  
	 * @param msg
	 */
	void getActiveSessions(OaMsg msg);

	/**
	 * 注销用户session
	 * @Description: TODO
	 * @author xie
	 * @date 2015年9月28日 下午3:26:33  
	 * @param msg
	 */
	void distorySession(String sessionId,OaMsg msg);
	
	/**
	 * 根据用户名查询session
	 * @Description: TODO
	 * @author xie
	 * @date 2015年9月29日 下午1:12:27  
	 * @param name
	 * @return
	 */
	Session getSessionByname(String name);
	
	/**
	 * 踢出用户
	 * @Description: TODO
	 * @author xie
	 * @date 2015年9月29日 下午3:10:51  
	 * @param name
	 */
	void kickSession(String name);
}
