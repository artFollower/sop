/**
 * 
 * @Project:sop
 * @Title:ExportController.java
 * @Package:com.skycloud.oa.report.controller
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月4日 下午4:13:39
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

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.report.dto.ExportDto;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.service.ExportService;
import com.skycloud.oa.report.service.ReportService;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;
@Controller
@RequestMapping("/export")
public class ExportController {
	@Autowired
	private ExportService exportService;
	@Autowired
	private ReportService reportService;
	/**
	 * 月仓储入库明细列表导出Excel 
	 */
	@RequestMapping(value = "exportMonthStorage")
	public void exportMonthStorage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute final ReportDto report, String name, final String type, final String params) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook= exportService.exportMonthStorage(request, type, params, report);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	/**
	 * 货物进出港通过量统计导出到Excel 
	 */
	@RequestMapping(value = "exportInOut")
	public void exportInOut(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute final ReportDto report) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook= exportService.exportInOut(request, report);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	
	/**
	 * 储罐周转率导出到Excel 
	 */
	@RequestMapping(value = "exportTankTurnRate")
	public void exportTankTurnRate(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute final ExportDto export) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook= exportService.exportTankTurnRate(request, export);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	
	
	/**
	 * 管道运输通过明细表导出到Excel 
	 */
	@RequestMapping(value = "exportPipe")
	public void exportPipe(HttpServletRequest request, HttpServletResponse response, @ModelAttribute final ReportDto report,
			String name, final String type, final String params) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook=exportService.exportPipe(request, type, params, report);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	
	/**
	 * 吞吐量统计表导出到Excel
	 */
	@RequestMapping(value = "exportThroughput")
	public void exportThroughput(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute final ExportDto export, String name, final String type, final String params) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook=exportService.exportThroughtRate(request, type, params, export);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}

	/**
	 * 码头进出港统计表导出到Excel
	 */
	@RequestMapping(value = "exportDock")
	public void exportDock(HttpServletRequest request, HttpServletResponse response, @ModelAttribute final ExportDto export,
			String name, final String type, final String params) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook=exportService.exportDock(request, type, params, export);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}

	/**
	 * 外贸进出港统计表导出到Excel 
	 */
	@RequestMapping(value = "exportTrade")
	public void exportTrade(HttpServletRequest request, HttpServletResponse response, @ModelAttribute final ExportDto export,
			String name, final String type, final String params) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook=exportService.exportTrade(request, type, params, export);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	/**
	 * 通过单位统计表 
	 */
	@RequestMapping(value = "exportUnit")
	public void exportUnit(HttpServletRequest request, HttpServletResponse response, @ModelAttribute final ExportDto export,
			String name, final String type, final String params) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook=exportService.exportUnit(request, type, params, export);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	/**
	 * 装车站发货统计表导出到Excel
	 */
	@RequestMapping(value = "exportStationStatis")
	public void exportStationStatis(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute final ExportDto export, String name, final String type, final String params) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook=exportService.exportStation(request, type, params, export);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}

	

	/**
	 * 年度仓储汇总表导出到Excel
	 */
	@RequestMapping(value = "exportStorage")
	public void exportStorage(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute final ExportDto export, String name, final String type, final String params) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook=exportService.exportStorage(request, type, params, export);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
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
					workbook=reportService.exportYearPipe(request, report);
					
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
					workbook=reportService.exportMonthBerth(request, report);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}

	/**
	 * 分流台账明细表导出到Excel 
	 */
	@RequestMapping(value = "outboundbook")
	public void outBoundBook(HttpServletRequest request, HttpServletResponse response, @ModelAttribute final ReportDto report,
			String name, final String type) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook=exportService.outBoundBook(request, type, report);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}

	/**
	 * 调度日志明细表导出到Excel 
	 */
	@RequestMapping(value = "inboundbook")
	public void inboundbook(HttpServletRequest request, HttpServletResponse response, @ModelAttribute final ReportDto report,
			String name, final String type) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook= exportService.inBoundBook(request, type, report);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	/**
	 * 泵棚Excel
	 */
	@RequestMapping(value = "exportpsrotation")
	public void exportPumpShedRotation(HttpServletRequest request, HttpServletResponse response,@ModelAttribute final ReportDto report, String name) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook= exportService.exportPumpShedRotation(request,report);
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	

}
