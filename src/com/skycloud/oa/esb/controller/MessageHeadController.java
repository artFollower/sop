package com.skycloud.oa.esb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.skycloud.oa.esb.model.MessageHead;
import com.skycloud.oa.esb.service.MessageHeadService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 报文头管理控制类
 * @ClassName: MessageHeadController 
 * @Description: TODO
 * @author xie
 * @date 2015年1月26日 下午1:46:04
 */
@Controller
@RequestMapping("/esb/messageHead")
public class MessageHeadController {
	
	@Autowired
    private MessageHeadService messageHeadService;
	
	/**
	 * 查询报文头
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月26日 下午1:47:56  
	 * @param head
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(
			String head,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		MessageHead mh = new Gson().fromJson(head, MessageHead.class);
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		messageHeadService.get(mh, pageView, msg);
		return msg;
	}
	
	/**
	 * 报文头更新
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月26日 下午1:48:08  
	 * @param resource
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public Object update(
			@ModelAttribute MessageHead messgeHead) {
		OaMsg msg = new OaMsg();
		messageHeadService.update(messgeHead, msg);
		return msg;
	}

}
