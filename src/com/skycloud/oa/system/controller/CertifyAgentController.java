package com.skycloud.oa.system.controller;

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
import com.skycloud.oa.system.dto.CertifyAgentDto;
import com.skycloud.oa.system.model.CertifyAgent;
import com.skycloud.oa.system.service.CertifyAgentService;

/**
 * 开证控制类
 * @author yanyufeng
 *
 */
@Controller
@RequestMapping("/certifyAgent")
public class CertifyAgentController {

	@Autowired
	private CertifyAgentService certifyAgentService;
	


	@RequestMapping(value = "list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute  CertifyAgentDto certifyAgentDto,
			@RequestParam(defaultValue = "20", required = false, value = "pageSize") int pageSize,
			@RequestParam(defaultValue = "1", required = false, value = "currentPage") int currentPage) throws OAException{
		//OaMsg msg = certifyAgentService.getCertifyAgentList(certifyAgentDto, 0, 0);
		//return new ModelAndView("sys_agent/certifylist").addObject("oaMsg", msg);
		return null;
	}
	
	

	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CertifyAgent certifyAgent) throws OAException {
		return certifyAgentService.addCertifyAgent(certifyAgent);
	}



	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response, String certifyAgentIds) throws OAException {
		return certifyAgentService.deleteCertifyAgent(certifyAgentIds);
	}
	
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CertifyAgent certifyAgent) throws OAException {
		return certifyAgentService.updateCertifyAgent(certifyAgent);
	}
	
	@RequestMapping(value = "initAdd")
	public ModelAndView initAdd(HttpServletRequest request, HttpServletResponse response) throws OAException {
		return new ModelAndView("sys_agent/addCertifyCustomer");
	}
}
