package com.skycloud.oa.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.service.ContractTypeService;
import com.skycloud.oa.utils.OaMsg;


@Controller
@RequestMapping("/contractType")
public class ContractTypeController {

	@Autowired
	private ContractTypeService contractTypeService;
	
	@RequestMapping(value = "select")
	@ResponseBody
	public OaMsg select(HttpServletRequest request, HttpServletResponse response) throws OAException{
		OaMsg msg = contractTypeService.getContractTypeList();
		
		return msg;
	}
	

}
