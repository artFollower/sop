package com.skycloud.oa.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.TruckDto;
import com.skycloud.oa.system.model.Truck;
import com.skycloud.oa.system.service.TruckService;

/**
 * 车辆操作控制类
 * @author yanyufeng
 *
 */
@Controller
@RequestMapping("/truck")
public class TruckController {

	@Autowired
	private TruckService truckService;
	

	/**
	 * 查询车辆列表
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param truckDto
	 * @param pageSize
	 * @param currentPage
	 * @return
	 * @
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(
			@ModelAttribute TruckDto truckDto,
			@RequestParam(defaultValue = "20", required = false, value = "pageSize") int pageSize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		PageView pageView=new PageView(pageSize, page);
		return truckService.getTruckList(truckDto, pageView);
	}
	

	/**
	 * 查询一个车辆
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param truckDto
	 * @param method
	 * @return
	 * @
	 */
	@RequestMapping(value = "get")
	@ResponseBody
	public Object get( 
			@ModelAttribute TruckDto truckDto, 
			String method)  {
			PageView pageView=new PageView(0, 0);
			return  truckService.getTruckList(truckDto,pageView);
	}
	
	

	/**
	 * 保存车辆信息
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param truck
	 * @return
	 * @
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save( @ModelAttribute Truck truck)  {
		return truckService.addTruck(truck);
	}


	/**
	 * 删除车辆
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param truckIds
	 * @return
	 * @
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete( String truckIds)  {
		return truckService.deleteTruck(truckIds);
	}
	
	/**
	 * 更新车辆
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param truck
	 * @return
	 * @
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(@ModelAttribute Truck truck)  {
		return truckService.updateTruck(truck);
	}
	
	
}
