package com.skycloud.oa.message.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.message.dto.MessageDto;
import com.skycloud.oa.message.service.MessageService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;


@Controller
@RequestMapping("/messageCenter")
public class MessageController {
	
	@Autowired
    private MessageService messageService;
	

	/**
	 * 查询消息
	 * @author yanyufeng
	 * @param messageDto
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(
			@ModelAttribute MessageDto messageDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		messageDto.setGetUserId(user.getId());
		messageService.get(messageDto, pageView, msg);
		return msg;
	}
	
	

	/**
	 * 更新为已读
	 * @author yanyufeng
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateReadStatus")
	public Object updateReadStatus(String ids) {
		OaMsg msg = new OaMsg();
		if(!Common.empty(ids)){
			messageService.updateReadStatus(ids, msg);
		}
		else{
			msg.setCode(Constant.SYS_CODE_NULL_ERR);
			msg.setMsg("id为空");
		}
		return msg;
	}
	
	/**
	 * 查询消息数量
	 * @author yanyufeng
	 * @param messageDto
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCount")
	public Object getCount(@ModelAttribute MessageDto messageDto) {
		OaMsg msg = new OaMsg();
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		messageDto.setGetUserId(user.getId());
		messageService.getCount(messageDto, msg);
		return msg;
	}
	

	/**
	 * 发送系统消息
	 * @author yanyufeng
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sendSystemMessage")
	public Object sendSystemMessage(String content) {
		OaMsg msg = new OaMsg();
		messageService.sendSystemMessage(content, msg);
		return msg;
	}
	
	/**
	 * 设置全部消息为已读
	 * @author yanyufeng
	 * @param content
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/setAllRead")
	public Object setAllRead() {
		OaMsg msg = new OaMsg();
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		messageService.setAllRead(user.getId(),msg);
		return msg;
	}
}
