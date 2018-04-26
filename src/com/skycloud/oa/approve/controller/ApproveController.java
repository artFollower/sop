package com.skycloud.oa.approve.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.approve.model.ApproveCenter;
import com.skycloud.oa.approve.service.ApproveService;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 审批控制类
 * @ClassName: ApproveController 
 * @Description: TODO
 * @author yan
 * @date 2016年2月17日 下午6:50:16
 */
@Controller
@RequestMapping("/approve")
public class ApproveController {
	
	@Autowired
	private ApproveService approveService;
	

	/**
	 * 保存审批信息
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param approveCenter
	 * @return
	 * @throws OAException
	 * 2016-2-17 下午7:29:34
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ApproveCenter approveCenter) throws OAException {
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		approveCenter.setCreateUserId(user.getId());
		
		approveCenter.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
		
		return approveService.save(approveCenter);
	}
	
	
	/**
	 * 更新审批状态
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param id
	 * @param status
	 * @return
	 * @throws OAException
	 * 2016-2-17 下午7:50:11
	 */
	@RequestMapping(value = "updateStatus")
	@ResponseBody
	public Object updateStatus(HttpServletRequest request, HttpServletResponse response, int id,int status) throws OAException {
		return approveService.updateStatus(id,status);
	}
	
	
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ApproveCenter approveCenter,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException {
		OaMsg msg = approveService.getApproveListByCondition(approveCenter, new PageView(pagesize, page));
		return msg;
	}
	
}
