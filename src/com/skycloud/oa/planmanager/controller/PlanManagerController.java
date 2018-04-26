/**
 * 
 */
package com.skycloud.oa.planmanager.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.planmanager.dto.PlanManagerDto;
import com.skycloud.oa.planmanager.service.PlanManagerService;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;

/**
 *
 * @author jiahy
 * @version 2015年8月14日 下午3:45:52
 */
@Controller
@RequestMapping("/planmanager")
public class PlanManagerController {
@Autowired
private PlanManagerService planManagerService;

/**
 * 获取靠泊方案
 *@author jiahy
 * @param pagesize
 * @param page
 * @param planManagerDto
 * @return
 * @throws OAException
 */
@RequestMapping(value="berthplanlist")
@ResponseBody
public Object berthPlanList(@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
		           @RequestParam(defaultValue = "0", required = false, value = "page") int page,
		           @ModelAttribute PlanManagerDto planManagerDto) throws OAException
{
	return planManagerService.getBerthPlanList(planManagerDto,new PageView(pagesize, page)) ;
}

/**
 * 获取接卸方案
 *@author jiahy
 * @param pagesize
 * @param page
 * @param planManagerDto
 * @return
 * @throws OAException
 */
@RequestMapping(value="unloadingplanlist")
@ResponseBody
public Object unloadingPlanList(@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
		           @RequestParam(defaultValue = "0", required = false, value = "page") int page,
		           @ModelAttribute PlanManagerDto planManagerDto) throws OAException
{
	return planManagerService.getUnloadingPlanList(planManagerDto,new PageView(pagesize, page)) ;
}

/**
 * 获取打循环（倒罐）方案
 *@author jiahy
 * @param pagesize
 * @param page
 * @param planManagerDto
 * @return
 * @throws OAException
 */
@RequestMapping(value="backflowlist")
@ResponseBody
public Object backFlowPlanList(@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
		           @RequestParam(defaultValue = "0", required = false, value = "page") int page,
		           @ModelAttribute PlanManagerDto planManagerDto) throws OAException
{
	return planManagerService.getBackFlowPlanList(planManagerDto,new PageView(pagesize, page)) ;
}
@RequestMapping(value="addtransportprogram")
@ResponseBody
public Object addBackFlowPlan(TransportProgram transportProgram) throws OAException{
	 return planManagerService.addBackFlowPlan(transportProgram);
}


/**导出出库信息表*/
@RequestMapping(value = "exportExcel")
public void exportInbound(HttpServletRequest request,HttpServletResponse response,@ModelAttribute final PlanManagerDto planManagerDto) throws OAException {
	ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

		@Override
		public HSSFWorkbook getWorkBook(HttpServletRequest request) {
			HSSFWorkbook workbook = null;
			try {
				workbook= planManagerService.exportExcel(request,planManagerDto);;
				
			} catch (OAException e) {
				e.printStackTrace();
			}
			return workbook;
		}
	});
}
@RequestMapping(value = "exportTransportProgram")
public void exportTransportProgram(HttpServletRequest request,HttpServletResponse response,final int transportId) throws OAException{
	ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

		@Override
		public HSSFWorkbook getWorkBook(HttpServletRequest request) {
			HSSFWorkbook workbook = null;
			try {
				workbook= planManagerService.exportTransportProgram(request,transportId);;
				
			} catch (OAException e) {
				e.printStackTrace();
			}
			return workbook;
		}
	});
}
@RequestMapping(value = "exportChangeTankProgram")
public void exportChangeTankProgram(HttpServletRequest request,HttpServletResponse response,final int transportId,final int type) throws OAException{
	ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

		@Override
		public HSSFWorkbook getWorkBook(HttpServletRequest request) {
			HSSFWorkbook workbook = null;
			try {
				workbook= planManagerService.exportChangeTankProgram(request,transportId,type);
				
			} catch (OAException e) {
				e.printStackTrace();
			}
			return workbook;
		}
	});
}
@RequestMapping(value = "exportBerthProgram")
public void exportBerthProgram(HttpServletRequest request,HttpServletResponse response,final int arrivalId) throws OAException{
	ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

		@Override
		public HSSFWorkbook getWorkBook(HttpServletRequest request) {
			HSSFWorkbook workbook = null;
			try {
				workbook= planManagerService.exportBerthProgram(request,arrivalId);
				
			} catch (OAException e) {
				e.printStackTrace();
			}
			return workbook;
		}
	});
}
}
