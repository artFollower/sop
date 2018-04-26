package com.skycloud.oa.system.controller;

import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ShipAgentDto;
import com.skycloud.oa.system.model.ShipAgent;
import com.skycloud.oa.system.service.ProductService;
import com.skycloud.oa.system.service.ShipAgentService;
import com.skycloud.oa.utils.OaMsg;

@Controller
@RequestMapping("/shipagent")
public class ShipAgentController {
@Autowired
private ShipAgentService shipAgentService;



    @RequestMapping("/list")
    @ResponseBody
	public Object list(@ModelAttribute ShipAgentDto ptDto,HttpServletRequest request ,HttpServletResponse response,@RequestParam(defaultValue = "20", required = false, value = "pageSize") int pageSize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page){
    	PageView pageView=new PageView(pageSize, page);
		return shipAgentService.getShipAgentList(ptDto,pageView);
	}
    @RequestMapping("/get")
    @ResponseBody
	public Object get(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ShipAgentDto ptDto,String method){
    		PageView pageView=new PageView(0, 0);
			return shipAgentService.getShipAgentList(ptDto,pageView);
	}
    @RequestMapping("/save")
    @ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ShipAgent ShipAgent){
		return shipAgentService.addShipAgent(ShipAgent);
	}
    @RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ShipAgent ShipAgent){
    	ShipAgent.setEditTime(new Timestamp(System.currentTimeMillis()));
		return shipAgentService.updateShipAgent(ShipAgent);
	}
    @RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,String id){
		return shipAgentService.deleteShipAgent(id);
	}
    @RequestMapping(value = "select")
	@ResponseBody
	public Object select(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ShipAgentDto shipAgentDto) {
    	PageView pageView=new PageView(0, 0);
		OaMsg msg = shipAgentService.getShipAgentList(shipAgentDto,pageView);
		return msg;
	}
	
}
