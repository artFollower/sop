package com.skycloud.oa.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.ApproveContentDto;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.system.service.ApproveContentService;

/**
 * 工作流审批
 * @author jiahy
 *
 */
@Controller
@RequestMapping("/approvecontent")
public class ApproveContentController {
	@Autowired
	private ApproveContentService approveContentService;
	/**获取审批信息*/
	@RequestMapping(value="getapprovecontent")
	@ResponseBody
	public Object getapprovework(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ApproveContent approveContent) throws OAException{
//			   User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
//			approveContent.setUserId(user.getId());
		if(approveContent.getWorkId()!=null){
			return approveContentService.getApproveContent(approveContent);
		}else {
			return null;
		}
	}
	/**工作流审批*/
	@RequestMapping(value="sendcheck")
	@ResponseBody
	public Object sendcheck(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ApproveContentDto approveContentDto) throws OAException{
		return approveContentService.sendCheck(approveContentDto);
	}
	/**抄送发送消息*/
	@RequestMapping(value="sendcopy")
	@ResponseBody
	public Object sendcopy(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ApproveContentDto approveContentDto) throws OAException{
		return approveContentService.sendCopy(approveContentDto);
	}
	/** 检查是否有上一次记录*/
	@RequestMapping(value="checkcache")
	@ResponseBody
	public Object checkcache(HttpServletRequest request, HttpServletResponse response,int workType) throws OAException{
		User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		return approveContentService.checkcache(mUser.getId(),workType);
	}
	
}
