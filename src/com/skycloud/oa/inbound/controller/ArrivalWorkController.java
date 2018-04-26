package com.skycloud.oa.inbound.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.ArrivalWork;
import com.skycloud.oa.inbound.service.ArrivalWorkService;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.utils.OaMsg;

/**
 * 到港计划控制类
 * @ClassName: ArrivalWorkController 
 * @Description: TODO
 * @author xie
 * @date 2014年12月4日 上午12:28:00
 */
@Controller
@RequestMapping("/arrivalWork")
public class ArrivalWorkController {
	
	@Autowired
	private ArrivalWorkService arrivalWorkService;
	
	/**
	 * 修改确认过的货批,加入到港联系单
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param arrivalWork
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response, String description,ArrivalWork arrivalWork,String cargoAgentId,String code,String inspectAgentIds,String inspectAgentNames,String goodsInspect) throws OAException {
		OaMsg msg = arrivalWorkService.addArrivalWork(description,arrivalWork,cargoAgentId,code,inspectAgentIds,inspectAgentNames,goodsInspect);
		return msg;
	}
	
	/**
	 * 计算合同批次号
	 * @param request
	 * @param response
	 * @param contractDto
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "getcode")
	@ResponseBody
	public Object getcode(HttpServletRequest request, HttpServletResponse response, ContractDto contractDto) throws OAException {
		OaMsg msg = arrivalWorkService.getTheCargoCode(contractDto);
		return msg;
	}
	/**
	 * 检查合同批次号
	 * @param request
	 * @param response
	 * @param contractDto
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "checkcode")
	@ResponseBody
	public Object checkcode(HttpServletRequest request, HttpServletResponse response, String code) throws OAException {
		OaMsg msg = arrivalWorkService.checkCargoCode(code);
		return msg;
	}
	
	
	@RequestMapping(value = "deleteCargo")
	@ResponseBody
	public Object deleteCargo(HttpServletRequest request, HttpServletResponse response, Integer cargoId) throws OAException {
		OaMsg msg = arrivalWorkService.deleteCargo(cargoId);
		return msg;
	}
	

}
