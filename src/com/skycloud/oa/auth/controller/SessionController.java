package com.skycloud.oa.auth.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.auth.service.UserSessionService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 用户session控制类
 * @ClassName: SessionController 
 * @Description: TODO
 * @author xie
 * @date 2015年9月28日 下午3:37:07
 */
@Controller
@RequestMapping("/auth/session")
public class SessionController {
	
	@Autowired
    private UserSessionService userSessionService;
	
	/**
	 * 查询所有在线用户
	 * @Description: TODO
	 * @author xie
	 * @date 2015年9月28日 下午3:38:37  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/list")
	@RequiresPermissions(value="sessionList")
	public Object list() {
		OaMsg msg = new OaMsg();
		userSessionService.getActiveSessions(msg);
		return msg;
	}
	
	
	
	/**
	 * 注销用户session
	 * @Description: TODO
	 * @author xie
	 * @date 2015年9月28日 下午3:40:02  
	 * @param sessionId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	@RequiresPermissions(value="sessionDelete")
	public Object delete(String sessionId) {
		OaMsg msg = new OaMsg();
		userSessionService.distorySession(sessionId, msg);
		return msg;
	}

}
