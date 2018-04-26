package com.skycloud.oa.inbound.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.inbound.dto.NoticeLogDto;
import com.skycloud.oa.inbound.model.NoticeLog;
import com.skycloud.oa.inbound.service.ArrivalPlanService;
import com.skycloud.oa.inbound.service.NoticeLogService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 代理控制类
 * @author yanyufeng
 *
 */
@Controller
@RequestMapping("/noticelog")
public class NoticeLogController {
@Autowired
private NoticeLogService noticeLogService;
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute NoticeLogDto noDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)throws OAException{
		OaMsg oaMsg=null;
		oaMsg=noticeLogService.getNoticeLogList(noDto,new PageView(pagesize, page));
		return oaMsg;
	}
	
	@RequestMapping(value = "add")
	@ResponseBody
	public Object add(HttpServletRequest request, HttpServletResponse response,NoticeLog noticeLog)throws OAException{
		OaMsg oaMsg=null;
		oaMsg=noticeLogService.addNoticeLog(noticeLog);
		return oaMsg;
	}
	
	
}
