package com.skycloud.oa.auth.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.skycloud.oa.auth.service.UserSessionService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.DateTimeUtil;
import com.skycloud.oa.utils.OaMsg;

/**
 * 用户session管理
 * @ClassName: UserServiceImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年9月28日 下午3:28:23
 */
@Service
public class UserSessionServiceImpl implements UserSessionService {
	
	@Autowired
	SessionDAO sessionDao;

	@Override
	public void getActiveSessions(OaMsg msg)
	{
		// TODO Auto-generated method stub
		for(Session session : sessionDao.getActiveSessions()) {
			if(Common.empty(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY))) {
				continue;
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id", session.getId());
			map.put("host", session.getHost());
			map.put("principals", session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));
			map.put("lastAccessTime", DateTimeUtil.getLongDateTimeString(session.getLastAccessTime()));
			map.put("startTimestamp", DateTimeUtil.getLongDateTimeString(session.getStartTimestamp()));
			map.put("timeout", session.getTimeout());
			msg.getData().add(map);
		}
	}

	@Override
	public void distorySession(String sessionId,OaMsg msg)
	{
		// TODO Auto-generated method stub
		sessionDao.delete(sessionDao.readSession(sessionId));
	}

	@Override
	public Session getSessionByname(String name)
	{
		// TODO Auto-generated method stub
		try{
			for(Session session : sessionDao.getActiveSessions()) {
				JSONObject obj = (JSONObject) JSON.parse(new Gson().toJson(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)));
				if(Common.empty(obj)) {
					continue;
				}
				JSONObject _obj = obj.getJSONObject("realmPrincipals");
				for(String key : _obj.keySet()) {
					for(Object _u : _obj.getJSONArray(key)) {
						JSONObject user = (JSONObject) _u;
						if(name.equals(user.getString("account")) || name.equals(user.getString("email")) || name.equals(user.getString("phone"))) {
							return session;
						}
					}
				}
				
			}
		}catch(Exception e) {
			
		}
		return null;
	}

	@Override
	public void kickSession(String name)
	{
		// TODO Auto-generated method stub
		try{
			for(Session session : sessionDao.getActiveSessions()) {
				if(session.getId().toString().equals(SecurityUtils.getSubject().getSession().getId().toString())) {
					continue;
				}
				JSONObject obj = (JSONObject) JSON.parse(new Gson().toJson(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY)));
				if(Common.empty(obj)) {
					continue;
				}
				JSONObject _obj = obj.getJSONObject("realmPrincipals");
				for(String key : _obj.keySet()) {
					for(Object _u : _obj.getJSONArray(key)) {
						JSONObject user = (JSONObject) _u;
						if(name.equals(user.getString("account")) || name.equals(user.getString("email")) || name.equals(user.getString("phone"))) {
							session.setTimeout(0);
							sessionDao.delete(session);
						}
					}
				}
				
			}
		}catch(Exception e) {
			
		}
	}
	
}
