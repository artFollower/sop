package com.skycloud.oa.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ParkDto;
import com.skycloud.oa.system.model.Park;
import com.skycloud.oa.system.service.ParkService;
import com.skycloud.oa.utils.OaMsg;


/**
 * 车位基础模块
 * @author jiahy
 *
 */
@Controller
@RequestMapping("/park")
public class ParkController {

	@Autowired
	private ParkService parkService;
	

   //获取车位列表
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,@ModelAttribute ParkDto ParkDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg msg = parkService.getParkList(ParkDto,new PageView(pagesize, page));
		return msg;
	}
	//更新车位
	@RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Park park) throws OAException{
		OaMsg oaMsg=null;
		oaMsg= parkService.updatePark(park);
		return oaMsg;
	}
	//添加车位
	@RequestMapping("/add")
    @ResponseBody
	public Object add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Park park) throws OAException{
		OaMsg oaMsg=null;
    	oaMsg=parkService.addPark(park);
    	return oaMsg;
	}
	//删除车位
	@RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ParkDto parkDto) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=parkService.deletePark(parkDto);
		return oaMsg;
	}
}
