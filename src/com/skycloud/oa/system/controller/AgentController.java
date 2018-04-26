package com.skycloud.oa.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 代理控制类
 * @author yanyufeng
 *
 */
@Controller
@RequestMapping("/agent")
public class AgentController {



	@RequestMapping(value = "list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			String page){
	return new ModelAndView("sys_agent/messageCenter").addObject("page", page);
	}
	
	
}
