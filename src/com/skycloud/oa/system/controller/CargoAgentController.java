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
import org.springframework.web.servlet.ModelAndView;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.model.CargoAgent;
import com.skycloud.oa.system.service.ProductService;
import com.skycloud.oa.system.service.CargoAgentService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Controller
@RequestMapping("/cargoagent")
public class CargoAgentController {
@Autowired
private CargoAgentService cargoAgentService;
@Autowired
private ProductService productService;


    @RequestMapping("/list")
    @ResponseBody
	public Object list(@ModelAttribute CargoAgentDto ptDto,HttpServletRequest request ,HttpServletResponse response,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
    	PageView pageView=new PageView(pagesize, page);
		OaMsg oaMsg=cargoAgentService.getCargoAgentList(ptDto,pageView);
		return oaMsg;
//		return new ModelAndView("sys_cargoagent/list").addObject("oaMsg", oaMsg);
	}
    @RequestMapping("/get")
    @ResponseBody
	public ModelAndView get(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CargoAgentDto ptDto,String method) throws OAException{
		if(Common.empty(ptDto.getId())&&Common.isNull(ptDto.getId())){
			return new ModelAndView("sys_cargoagent/add");
		}else {
			OaMsg oaMsg=cargoAgentService.getCargoAgentList(ptDto,new PageView(0,0));
			if(oaMsg.getData().size()>0){
				if (!Common.empty(method) && method.equals(Constant.METHOD_DO)) {
				return new ModelAndView("sys_cargoagent/edit").addObject("cargoagent", oaMsg.getData().get(0));
			}else{
				return new ModelAndView("sys_cargoagent/get").addObject("cargoagent", oaMsg.getData().get(0));
			}
		}else{
			oaMsg.setMsg("所要查询的对象不存在");
			return new ModelAndView(Constant.PAGE_ERROR).addObject(oaMsg);
		}
		}
	}
    @RequestMapping("/save")
    @ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CargoAgent CargoAgent) throws OAException{
		return cargoAgentService.addCargoAgent(CargoAgent);
	}
    @RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CargoAgent CargoAgent) throws OAException{
    	CargoAgent.setEditTime(new Timestamp(System.currentTimeMillis()));
		return cargoAgentService.updateCargoAgent(CargoAgent);
	}
    @RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,String id) throws OAException{
		
		return cargoAgentService.deleteCargoAgent(id);
		
	}
	
    @RequestMapping(value = "select")
  	@ResponseBody
  	public Object select(HttpServletRequest request, HttpServletResponse response, @ModelAttribute CargoAgentDto cargoAgentDto) throws OAException {
  		OaMsg msg = cargoAgentService.getCargoAgentList(cargoAgentDto, new PageView(0,0));
  		return msg;
  	}
  	
}
