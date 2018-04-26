package com.skycloud.oa.auth.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.auth.model.Parties;
import com.skycloud.oa.auth.service.OrganizationService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 组织架构管理控制类
 * @ClassName: OrganizationController 
 * @Description: TODO
 * @author xie
 * @date 2015年1月16日 上午11:03:43
 */
@Controller
@RequestMapping("/auth/organization")
public class OrganizationController {
	
	@Autowired
    private OrganizationService organizationService;
	
	/**
	 * 查询
	 * @Description: TODO
	 * @param resource
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(
			@ModelAttribute Parties parties,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		organizationService.get(parties, pageView ,msg);
		return msg;
	}
	
	/**
	 * 添加
	 * @Description: TODO
	 * @param parties
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create")
	@RequiresPermissions(value="AORGADD")
	public Object create(
			@ModelAttribute Parties parties) {
		OaMsg msg = new OaMsg();
		organizationService.create(parties, msg);
		return msg;
	}
	
	/**
	 * 删除
	 * @Description: TODO
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/remove")
	@RequiresPermissions(value="AORGDELETE")
	public Object delete(String ids) {
		OaMsg msg = new OaMsg();
		organizationService.delete(ids, msg);
		return msg;
	}
	
	/**
	 * 更新
	 * @Description: TODO
	 * @param parties
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	@RequiresPermissions(value="AORGUPDATE")
	public Object update(
			@ModelAttribute Parties parties) {
		OaMsg msg = new OaMsg();
		organizationService.modify(parties, msg);
		return msg;
	}
	
	/**
	 * 更新员工信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月8日 下午7:22:10  
	 * @param parties
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateEmployee")
	@RequiresPermissions(value="AEMPLOYEEUPDATE")
	public Object updateEmployee(
			@ModelAttribute Parties parties) {
		OaMsg msg = new OaMsg();
		organizationService.modifyeMPLOYEE(parties, msg);
		return msg;
	}
	
	/**
	 * 获取组织架构
	 * @Description: TODO
	 * @param parties
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrganization")
	public Object getOrganization(
			@ModelAttribute Parties parties) {
		return organizationService.getOrganization(parties);
	}
	
	/**
	 * 获取关联的用户
	 * @Description: TODO
	 * @param parties
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRelationUser")
	public Object getRelationUser(
			@ModelAttribute Parties parties,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		organizationService.getRelationUser(parties, pageView ,msg);
		return msg;
	}
	
	/**
	 * 获取未关联的用户
	 * @Description: TODO
	 * @param parties
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUnRelationUser")
	public Object getUnRelationUser(
			@ModelAttribute Parties parties,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		organizationService.getUnRelationUser(parties, pageView ,msg);
		return msg;
	}
	
	/**
	 * 添加用户到部门
	 * @Description: TODO
	 * @param userIds
	 * @param departmentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/relationUser")
	@RequiresPermissions(value="AORGEMPLORE")
	public Object relationUser(String userIds,int departmentId) {
		OaMsg msg = new OaMsg();
		organizationService.relationUser(userIds, departmentId, msg);
		return msg;
	}

}
