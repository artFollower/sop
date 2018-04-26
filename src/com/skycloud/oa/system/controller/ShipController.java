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

import com.google.gson.Gson;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ShipDto;
import com.skycloud.oa.system.dto.ShipInfoDtoList;
import com.skycloud.oa.system.service.ShipService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 车辆操作控制类
 * @author yanyufeng
 *
 */
@Controller
@RequestMapping("/ship")
public class ShipController {

	@Autowired
	private ShipService shipService;
	


	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute ShipDto shipDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		PageView pageView=new PageView(pagesize, page);
		OaMsg msg = shipService.getShipList(shipDto, pageView);
		return msg;
	}
	

	@RequestMapping(value = "get")
	public ModelAndView get(HttpServletRequest request, HttpServletResponse response, 
			@ModelAttribute ShipDto shipDto, 
			String method) throws OAException {
		if (Common.empty(shipDto.getId()) || shipDto.getId() == 0) {// 为传id
			return new ModelAndView("sys_ship/add");
		} else {
			OaMsg shipMsg = shipService.getShipList(shipDto, new PageView(0,0));
			if (shipMsg.getData().size() > 0) {
				if (!Common.empty(method) && method.equals(Constant.METHOD_DO)) {
					return new ModelAndView("sys_ship/edit").addObject("ship",shipMsg.getData().get(0));
				} 
				else {
					return new ModelAndView("sys_ship/get").addObject("ship", shipMsg.getData().get(0));
				}
			} else {
				shipMsg.setMsg("所要查询的对象不存在");
				return new ModelAndView(Constant.PAGE_ERROR).addObject(shipMsg);
			}

		}
	}
	
	

	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(String shipDtoList) throws OAException {
		ShipInfoDtoList  shipInfoDtoList =  new Gson().fromJson(shipDtoList, ShipInfoDtoList.class) ;
		return shipService.addShip(shipInfoDtoList);
	}



	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response, String shipIds) throws OAException {
		return shipService.deleteShip(shipIds);
	}
	@RequestMapping(value = "getShipRefList")
	@ResponseBody
	public Object getShipRefList(String shipId) throws OAException {
		return shipService.getShipRefList(shipId);
	}
	@RequestMapping(value = "deleteShipRefInfo")
	@ResponseBody
	public Object deleteShipRefInfo(String shipRefId) throws OAException {
		return shipService.deleteShipRefInfo(shipRefId);
	}
	
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(String shipDtoList) throws OAException {
		ShipInfoDtoList  shipInfoDtoList =  new Gson().fromJson(shipDtoList, ShipInfoDtoList.class) ;
		return shipService.updateShip(shipInfoDtoList);
	}
	
	@RequestMapping(value = "select")
	@ResponseBody
	public Object select(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ShipDto shipDto) throws OAException {
		OaMsg msg = shipService.getShipList(shipDto,new PageView(0,0));
		return msg;
	}
	
}
