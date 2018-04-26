/**
 * 
 * @Project:sop
 * @Title:ReportController.java
 * @Package:com.skycloud.oa.report.controller
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月26日 上午10:30:46
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.controller;

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
import com.skycloud.oa.inbound.service.OperationLogService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.model.ThroughtMsg;
import com.skycloud.oa.report.service.ReportDataService;
import com.skycloud.oa.report.service.ReportService;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;

@Controller
@RequestMapping("/report")
public class ReportController {
	@Autowired
	private ReportService reportService;
	@Autowired
	private ReportDataService reDataService;
	@Autowired
	private OperationLogService operationLogService;
	
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(@RequestParam(defaultValue = "10", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page,
			@ModelAttribute ReportDto report) {
		return reportService.list(report, new PageView(pagesize, page));
	}
	@RequestMapping(value = "addorupdateThroughMsg")
	@ResponseBody
	public Object addorupdateThroughMsg(@ModelAttribute ThroughtMsg throughtMsg) throws OAException {
		return reportService.addorupdateThroughMsg(throughtMsg);
	}
	@RequestMapping(value = "monthberth")
	@ResponseBody
	public Object monthBerth(@ModelAttribute ReportDto report) throws OAException {
		return reDataService.getMonthBerth(report);
	}

	@RequestMapping(value = "yearpipe")
	@ResponseBody
	public Object yearPipe(@ModelAttribute ReportDto report) throws OAException {
		return reDataService.getYearPipe(report);
	}

	/**
	 * 分流台账列表
	 */
	@RequestMapping(value = "outboundbooklist")
	@ResponseBody
	public Object berthPlanList(@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page,
			@ModelAttribute ReportDto report) throws OAException {
		return reDataService.getOutBoundBook(report, new PageView(pagesize, page));
	}

	
	/**
	 * 调度日志列表
	 */
	@RequestMapping(value = "inboundbooklist")
	@ResponseBody
	public Object inboundbooklist(@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page,
			String startTime,String endTime) throws OAException {
		long mStartTime=Long.parseLong(startTime);
		long mEndTime=Long.parseLong(endTime);
			return operationLogService.getOperationLogList(mStartTime, mEndTime, new PageView(pagesize, page) );
		}
	
	


	/**
	 * 管道运输通过明细表导出到Excel
	 */
	@RequestMapping(value = "exportyearpipe")
	public void exportYearPipe(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute final ReportDto report, String name) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook = reportService.exportYearPipe(request, report);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}

	/**
	 * 泊位利用率Excel
	 */
	@RequestMapping(value = "exportmonthberth")
	public void exportMonthBerth(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute final ReportDto report, String name) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook = reportService.exportMonthBerth(request, report);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	@RequestMapping(value = "getBerthDetailList")
	@ResponseBody
	public Object getBerthDetailList(@RequestParam(defaultValue = "10", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page,
			@ModelAttribute ReportDto report) throws OAException {
		return reportService.getBerthDetailList(report, new PageView(pagesize, page));
	}

}