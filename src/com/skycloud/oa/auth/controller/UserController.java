package com.skycloud.oa.auth.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.auth.dto.UserDto;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.auth.service.UserService;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 用户管理控制类
 * @ClassName: UserController 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 上午11:48:59
 */
@Controller
@RequestMapping("/auth/user")
public class UserController {
	
	@Autowired
    private UserService userService;
	
	/**
	 * 查询用户
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(
			@ModelAttribute UserDto user,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		userService.get(user, pageView, msg);
		return msg;
	}
	
	/**
	 * 创建用户
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create")
	@RequiresPermissions(value="userAdd")
	public Object create(
			@ModelAttribute User user) {
		OaMsg msg = new OaMsg();
		userService.create(user, msg);
		return msg;
	}
	
	/**
	 * 更新用户基本信息
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	@RequiresPermissions(value="userUpdate")
	public Object update(
			@ModelAttribute User user) {
		OaMsg msg = new OaMsg();
		userService.update(user, msg);
		return msg;
	}
	
	/**
	 * 修改个人账户密码
	 * @param user
	 * @param opswd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/changePaswd")
	public Object changePaswd(@ModelAttribute UserDto user,String opswd) {
		OaMsg msg = new OaMsg();
		if(Common.empty(opswd)) {
			msg.setCode(Constant.SYS_CODE_NULL_ERR);
			msg.setMsg("原密码不能为空");
			return msg;
		}
		userService.changePassword(user,opswd, msg);
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "/changeUserPaswd")
	public Object changeUserPaswd(@ModelAttribute UserDto user) {
		OaMsg msg = new OaMsg();
		userService.changePassword(user,null, msg);
		return msg;
	}
	
	/**
	 * 删除用户
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/remove")
	@RequiresPermissions(value="userDelete")
	public Object delete(String ids) {
		OaMsg msg = new OaMsg();
		userService.delete(ids, msg);
		return msg;
	}
	
	/**
	 * 分配角色
	 * @Description: TODO
	 * @author xie
	 * @param userId
	 * @param roleIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/grantRole")
	@RequiresPermissions(value="AROLERGRANT")
	public Object grantRole(String userId,String roleIds) {
		OaMsg msg = new OaMsg();
		userService.grantAuthority(userId, roleIds, msg);
		return msg;
	}
	
	/**
	 * 获取已分配的角色
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGrantRole")
	public Object getGrantRole(User user) {
		OaMsg msg = new OaMsg();
		userService.getGrantRole(user, msg);
		return msg;
	}
	
	/**
	 * 获取未分配的角色
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnGrantRole")
	public Object getUnGrantRole(User user) {
		OaMsg msg = new OaMsg();
		userService.getUnGrantRole(user, msg);
		return msg;
	}
	
	/**
	 * 回收角色
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recoverRole")
	@RequiresPermissions(value="AROLEREVOCER")
	public Object recoverRole(String ids) {
		OaMsg msg = new OaMsg();
		userService.recoverAuthority(ids, msg);
		return msg;
	}
	
	/**
	 * 获取用户权限
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月9日 下午3:41:19  
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPermission")
	public Object getPermission(User user) {
		OaMsg msg = new OaMsg();
		msg.getData().addAll(userService.getUserPermission(user));
		return msg;
	}
	
	@RequestMapping(value = "console")
	public String console(HttpServletRequest request, HttpServletResponse response) {
		return "console";
	}

	/**
	 * 模拟退出
	 * @Description: TODO
	 * @author xie
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "loginOut")
	public String loginOut(HttpServletRequest request, HttpServletResponse response) {
		return "index";
	}
	
	/**
	 * 上传头像
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月21日 下午7:23:22  
	 * @param request
	 * @param response
	 * @param photoFile
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "uploadPhoto")
	public Object uploadPhoto(HttpServletRequest request, HttpServletResponse response,User user) {
		OaMsg msg = new OaMsg();
		userService.uploadPhoto(user, msg);
		return msg;
	}
	/**
	 * 查询用户
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUserByPermission")
	public Object getUserByPermission(@ModelAttribute UserDto user) {
		return userService.getUserByPermission(user);
	}
}
