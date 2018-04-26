package com.skycloud.oa.auth.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.auth.model.Role;
import com.skycloud.oa.auth.service.RoleService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 角色管理控制类
 * @ClassName: RoleController 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 上午11:49:10
 */
@Controller
@RequestMapping("/auth/role")
public class RoleController {
	
	@Autowired
    private RoleService roleService;
	
	/**
	 * 查询角色
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
			@ModelAttribute Role role,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		roleService.get(role, pageView, msg);
		return msg;
	}
	
	/**
	 * 创建角色
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create")
	@RequiresPermissions(value="roleAdd")
	public Object create(
			@ModelAttribute Role role) {
		OaMsg msg = new OaMsg();
		roleService.create(role, msg);
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
	@RequiresPermissions(value="roleDelete")
	public Object delete(String ids) {
		OaMsg msg = new OaMsg();
		roleService.delete(ids, msg);
		return msg;
	}
	
	/**
	 * 授权
	 * @Description: TODO
	 * @author xie
	 * @param roleId
	 * @param permissionIds
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/grantPermission")
	@RequiresPermissions(value="APOWERGRANT")
	public Object grantPermission(String roleId,String permissionIds) {
		OaMsg msg = new OaMsg();
		roleService.grantPermission(roleId, permissionIds, msg);
		return msg;
	}
	
	/**
	 * 获取已分配的权限
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getGrantPermission")
	public Object getGrantPermission(Role role) {
		OaMsg msg = new OaMsg();
		roleService.getGrantPermission(role, msg);
		return msg.getData();
	}
	
	/**
	 * 获取未分配的角色
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnGrantPermission")
	public Object getUnGrantPermission(Role role) {
		OaMsg msg = new OaMsg();
		roleService.getUnGrantPermission(role, msg);
		return msg;
	}
	
	/**
	 * 回收权限
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/recoverPermission")
	@RequiresPermissions(value="APOWERREVOCER")
	public Object recoverPermission(String ids) {
		OaMsg msg = new OaMsg();
		roleService.recoverPermission(ids, msg);
		return msg;
	}
	
	/**
	 * 更新角色信息
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	@RequiresPermissions(value="roleUpdate")
	public Object update(
			@ModelAttribute Role role ) {
		OaMsg msg = new OaMsg();
		roleService.update(role, msg);
		return msg;
	}
	
	/**
	 * 获取用户
	 * @param role
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUser")
	public Object getUser(
			@ModelAttribute Role role,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		roleService.getUser(role, pageView, msg);
		return msg;
	}

}
