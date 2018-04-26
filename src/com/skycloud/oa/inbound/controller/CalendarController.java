package com.skycloud.oa.inbound.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.service.ArrivalPlanService;
import com.skycloud.oa.orm.PageView;

/**
 * 代理控制类
 * @author yanyufeng
 *
 */
@Controller
@RequestMapping("/calendar")
public class CalendarController {

	@RequestMapping(value = "get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response,
			String page){
	return new ModelAndView("inbound/operationlog/list_operation");
	}
	
	
}
