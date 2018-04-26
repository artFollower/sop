package com.skycloud.oa.auth.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.auth.service.UserService;
import com.skycloud.oa.utils.OaMsg;
import com.sun.tracing.dtrace.ModuleAttributes;

/**
 * 登陆用户控制器。
 * @ClassName: LoginController 
 * @Description: TODO
 * @author xie
 * @date 2014年12月26日 下午1:30:43
 */
@Controller
@RequestMapping
public class LoginController {
	
//	private static Logger LOG = Logger.getLogger(LoginController.class);

	@Autowired
    private UserService userService;
	
	/**
	 * 如果登陆了 就不用再次到登陆页面，直接就可以进入。
	 * @Description: TODO
	 * @author xie
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		Subject  currentUser = SecurityUtils.getSubject();
//		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
        if(currentUser.isAuthenticated()){
            return "redirect:/";
        }
		return "login";
	}

	/**
	 * 用户登陆
	 * @Description: TODO
	 * @author xie
	 * @param request
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Object login(HttpServletRequest request, User user,String rememberMe) {
		OaMsg msg = new OaMsg();
		userService.login(user,rememberMe, msg);
		return msg;
	}
	
	/**
	 * 用户退出
	 * @Description: TODO
	 * @author xie
	 * @return
	 */
    @RequestMapping(value = "/logout")
    @ResponseBody
    public Object logout() {
    	OaMsg msg = new OaMsg();
        SecurityUtils.getSubject().logout();
        return msg;
    }
}
