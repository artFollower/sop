package com.skycloud.oa.auth.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.auth.model.SecurityResources;
import com.skycloud.oa.auth.service.ResourceService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 资源管理控制类
 * @ClassName: ResourceController 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 下午2:22:16
 */
@Controller
@RequestMapping("/auth/resource")
public class ResourceController {
	
	@Autowired
    private ResourceService resourceService;
	
	/**
	 * 查询资源
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(
			@ModelAttribute SecurityResources resource,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		resourceService.get(resource, pageView, msg);
		return msg;
	}
	
	/**
	 * 添加资源
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create")
	@RequiresPermissions(value="resourceAdd")
	public Object create(
			@ModelAttribute SecurityResources resource) {
		OaMsg msg = new OaMsg();
		resourceService.create(resource, msg);
		return msg;
	}
	
	/**
	 * 删除资源
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/remove")
	@RequiresPermissions(value="resourceDelete")
	public Object delete(String ids) {
		OaMsg msg = new OaMsg();
		resourceService.delete(ids, msg);
		return msg;
	}
	
	/**
	 * 资源信息更新
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	@RequiresPermissions(value="resourceUpdate")
	public Object update(
			@ModelAttribute SecurityResources resource) {
		OaMsg msg = new OaMsg();
		resourceService.update(resource, msg);
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "/move")
	@RequiresPermissions(value="resourceUpdate")
	public Object move(
			@ModelAttribute SecurityResources resource) {
		OaMsg msg = new OaMsg();
		resourceService.move(resource, msg);
		return msg;
	}

}
