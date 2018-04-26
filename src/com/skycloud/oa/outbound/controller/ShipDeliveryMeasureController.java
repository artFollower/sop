package com.skycloud.oa.outbound.controller;

import javax.persistence.AttributeOverride;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.ShipDeliverMeasureDto;
import com.skycloud.oa.outbound.model.ShipDeliveryMeasure;
import com.skycloud.oa.outbound.service.ShipDeliveryMeasureService;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;

/**
 * 
 * <p>台账管理---流量计台账</p>
 * @ClassName:ShipDeliveryMeasureController
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月28日 上午10:53:38
 *
 */
@Controller
@RequestMapping("/shipDeliveryMeasure")
public class ShipDeliveryMeasureController 
{
	/**
	 * shipDeliveryMeasureService
	 */
	@Autowired
	private ShipDeliveryMeasureService shipDeliveryMeasureService ;
	
	/**
	 * 查询查询船发流量计台账
	 * @Title:ShipDeliveryMeasureController
	 * @Description:
	 * @param request
	 * @param response
	 * @param shipDeliverMeasureDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @Date:2015年5月28日 上午10:54:49
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,
			           @ModelAttribute ShipDeliverMeasureDto shipDeliverMeasureDto,
			           @RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
			           @RequestParam(defaultValue = "0", required = false, value = "page") int page){
		return shipDeliveryMeasureService.list(shipDeliverMeasureDto, new PageView(pagesize, page));
	}
	
	/**
	 * 更新货添加船发流量计台账
	 * @Title:ShipDeliveryMeasureController
	 * @Description:
	 * @param shipDeliverMeasure
	 * @return
	 * @Date:2015年5月28日 上午10:55:05
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "addorupdate")
	@ResponseBody
	public Object addorupdate(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ShipDeliveryMeasure s) 
	{
		return shipDeliveryMeasureService.addorupdate(s) ;
	}
	
	/**
	 * 通过id获取船发流量计台账
	 * @Title:ShipDeliveryMeasureController
	 * @Description:
	 * @param id
	 * @return
	 * @Date:2015年5月28日 上午10:55:23
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "get")
	@ResponseBody
	public Object get(int id) 
	{
		return shipDeliveryMeasureService.get(id) ;
	}
	
	/**
	 * 删除船发流量计台账
	 * @Title:ShipDeliveryMeasureController
	 * @Description:
	 * @param id
	 * @return
	 * @Date:2015年5月28日 上午10:55:41
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(int id) 
	{
		return shipDeliveryMeasureService.delete(id) ;
	}
	@RequestMapping(value = "exportExcel")
	public void exportMonthStorage(HttpServletRequest request, HttpServletResponse response) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				workbook= shipDeliveryMeasureService.exportExcel(request);
				return workbook;
			}
		});
	}
	
}
