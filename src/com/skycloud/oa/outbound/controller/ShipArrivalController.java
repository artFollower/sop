package com.skycloud.oa.outbound.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.ShipArrivalDto;
import com.skycloud.oa.outbound.service.ShipArrivalService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 
 * <p>出库管理---出港计划</p>
 * @ClassName:ShipArrivalController
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午8:28:18
 *
 */
@Controller
@RequestMapping("/shipArrival")
public class ShipArrivalController 
{
	/**
	 * shipArrivalService
	 */
	@Autowired
	private ShipArrivalService shipArrivalService ;
	
	/**
	 * 查询出港计划
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param pagesize
	 * @param page
	 * @param shipArrivalDto
	 * @return
	 * @Date:2015年5月28日 上午11:08:01
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="list")
	@ResponseBody
	public Object list(@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
			           @RequestParam(defaultValue = "0", required = false, value = "page") int page,
			           @ModelAttribute ShipArrivalDto shipArrivalDto)
	{
		return shipArrivalService.list(shipArrivalDto,new PageView(pagesize, page)) ;
	}
	
	/**
	 * 通过id获取出港计划
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param id
	 * @return
	 * @Date:2015年5月28日 上午11:08:23
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="get")
	@ResponseBody
	public Object get(String id)
	{
		return shipArrivalService.get(id) ;
	}
	
	/**
	 * 获取货体列表
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param clientId
	 * @param productId
	 * @return
	 * @Date:2015年5月28日 上午11:08:40
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="getGoodsInfo")
	@ResponseBody
	public Object getGoodsInfo(int clientId,int productId) 
	{
		return shipArrivalService.getGoodsList(clientId,productId) ;
	}
	
	/**
	 * 添加到港计划信息如果存在就更新
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param shipArrivalDto
	 * @return
	 * @Date:2015年5月28日 上午11:09:04
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="add")
	@ResponseBody
	public Object add(String shipArrivalDto)
	{
		ShipArrivalDto s  = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(shipArrivalDto, ShipArrivalDto.class) ;
		return shipArrivalService.add(s) ;
	}
	
	/**
	 * 更新到港计划基本信息
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param shipArrivalDto
	 * @return
	 * @Date:2015年5月28日 上午11:09:22
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public Object update(String shipArrivalDto)
	{
		ShipArrivalDto s  = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(shipArrivalDto, ShipArrivalDto.class) ;
		return shipArrivalService.update(s) ;
	}
	
	/**
	 * 更新到港计划信息
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param arrivalPlan
	 * @return
	 * @Date:2015年5月28日 上午11:09:47
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="updatePlan")
	@ResponseBody
	public Object updatePlan(HttpServletRequest request,HttpServletResponse response,@ModelAttribute  ArrivalPlan arrivalPlan)
	{
		return shipArrivalService.updatePlan(arrivalPlan) ;
	}
	
	@RequestMapping(value="reback")
	@ResponseBody
	public Object reBack(HttpServletRequest request,HttpServletResponse response,Integer arrivalId) throws OAException
	{
		return shipArrivalService.reBackPlan(arrivalId) ;
	}
	/**
	 * 添加保存到港计划信息
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param arrivalPlan
	 * @return
	 * @Date:2015年5月28日 上午11:10:07
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="saveArrivalPlan")
	@ResponseBody
	public Object saveArrivalPlan(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ArrivalPlan arrivalPlan)
	{
		return shipArrivalService.saveArrivalPlan(arrivalPlan) ;
	}
	
	/**
	 * 通过id删除到港计划信息
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param arrivalPlanId
	 * @return
	 * @Date:2015年5月28日 上午11:10:38
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="deleteShipPlanById")
	@ResponseBody
	public Object deleteShipPlanById(String arrivalPlanId,Integer status)
	{
		return shipArrivalService.deleteShipPlanById(arrivalPlanId,status) ;
	}
	
	/**
	 * 更新审批状态
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param arrivalPlanId
	 * @return
	 * @Date:2015年5月28日 上午11:11:01
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="updateShipPlanStatus")
	@ResponseBody
	public Object updateShipPlanStatus(String arrivalPlanId)
	{
		return shipArrivalService.updateShipPlanStatus(arrivalPlanId) ;
	}
	
	/**
	 * 更新出港确认状态
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param arrivalId
	 * @return
	 * @Date:2015年5月28日 上午11:11:25
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="updateArrivalStatus")
	@ResponseBody
	public Object updateArrivalStatus(String arrivalId)
	{
		return shipArrivalService.updateArrivalStatus(arrivalId) ;
	}
	
	/**
	 * 删除到港计划信息
	 * @Title:ShipArrivalController
	 * @Description:
	 * @param ids
	 * @return
	 * @Date:2015年5月28日 上午11:11:39
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="delete")
	@ResponseBody
	public Object delete(String ids)
	{
		return shipArrivalService.delete(ids) ;
	}
	@RequestMapping(value="getgoodsmsg")
	@ResponseBody
	public OaMsg getGoodsMsgByClientId(@ModelAttribute ArrivalPlanDto arrivalPlanDto) throws OAException{
		
		return shipArrivalService.getGoodsMsg(arrivalPlanDto);
	}
	
	@RequestMapping(value="getDifPlanAndInvoiceMsg")
	@ResponseBody
	public OaMsg getDifPlanAndInvoiceMsg(@ModelAttribute ArrivalPlanDto arrivalPlanDto)throws OAException{
		return shipArrivalService.getDifPlanAndInvoiceMsg(arrivalPlanDto);
	}
	@RequestMapping(value="updateArrivalPlan")
	@ResponseBody
	public OaMsg updateArrivalPlan(@ModelAttribute ArrivalPlan arrivalPlan)throws OAException{
		return shipArrivalService.updateArrivalPlan(arrivalPlan);
	}
}
