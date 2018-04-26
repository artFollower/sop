package com.skycloud.oa.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.service.ShipRefService;
import com.skycloud.oa.utils.OaMsg;

@Controller
@RequestMapping("/shipref")
public class ShipRefController {
@Autowired
private ShipRefService shipRefService;


    @RequestMapping("/list")
    @ResponseBody
	public OaMsg list(HttpServletRequest request ,HttpServletResponse response,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page,int shipId) throws OAException{
		OaMsg oaMsg=shipRefService.getShipRefList(shipId,new PageView(pagesize, page));
		return oaMsg;
	}
    
	
}
