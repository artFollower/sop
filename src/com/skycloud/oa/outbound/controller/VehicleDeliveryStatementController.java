package com.skycloud.oa.outbound.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.VehicleDeliveryStatementDto;
import com.skycloud.oa.outbound.service.VehicleDeliveryStatementService;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;

/**
 * 
 * <p>台账管理---流量报表</p>
 * @ClassName:VehicleDeliveryStatementController
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午10:42:06
 *
 */
@Controller
@RequestMapping("/vehicleDeliveryStatement")
public class VehicleDeliveryStatementController 
{
	/**
	 * vehicleDeliveryStatementService
	 */
	@Autowired
	private VehicleDeliveryStatementService vehicleDeliveryStatementService ;
	
	/**
	 * 查询车位发货日报表信息
	 * @Title:VehicleDeliveryStatementController
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pagesize
	 * @param pagequeryWeighDailyStatement
	 * @return
	 * @Date:2015年5月27日 下午10:43:13
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "queryParkDailyStatement")
	@ResponseBody
	public Object queryParkDailyStatement(@ModelAttribute VehicleDeliveryStatementDto vdDto,
										  @RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
										  @RequestParam(defaultValue = "0", required = false, value = "page") int page)
	{
		return vehicleDeliveryStatementService.queryParkDailyStatement(vdDto, new PageView(pagesize, page)) ;
	}
	
	/**
	 * 查询车位流量日报表
	 * @Title:VehicleDeliveryStatementController
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @Date:2015年5月27日 下午10:43:50
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "queryWeighDailyStatement")
	@ResponseBody
	public Object queryWeighDailyStatement(@ModelAttribute VehicleDeliveryStatementDto vehicleDeliveryStatementDto,
										   @RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
										   @RequestParam(defaultValue = "0", required = false, value = "page") int page)
	{
		return vehicleDeliveryStatementService.queryWeighDailyStatement(vehicleDeliveryStatementDto, new PageView(pagesize, page)) ;
	}
	
	@RequestMapping(value = "getTotalNum")
	@ResponseBody
	public Object getTotalNum(@ModelAttribute VehicleDeliveryStatementDto vsDto) throws OAException
	{
		return vehicleDeliveryStatementService.getTotalNum(vsDto) ;
	}
	
	/**
	 * 查询车位发货月报表
	 * @Title:VehicleDeliveryStatementController
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @Date:2015年5月27日 下午10:44:24
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "queryVehicleMonthlyStatement")
	@ResponseBody
	public Object queryVehicleMonthlyStatement(@ModelAttribute VehicleDeliveryStatementDto vehicleDeliveryStatementDto,
										       @RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
										       @RequestParam(defaultValue = "0", required = false, value = "page") int page) 
	{
		return vehicleDeliveryStatementService.queryVehicleMonthlyStatement(vehicleDeliveryStatementDto, new PageView(pagesize, page));
	}
	
	/**
	 * 查询车位发货历史累计量报表
	 * @Title:VehicleDeliveryStatementController
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @Date:2015年5月27日 下午10:45:15
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "queryVehicleHistoryCumulantStatement")
	@ResponseBody
	public Object queryVehicleHistoryCumulantStatement(@ModelAttribute VehicleDeliveryStatementDto vehicleDeliveryStatementDto,
													   @RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
													   @RequestParam(defaultValue = "0", required = false, value = "page") int page)
	{
		return vehicleDeliveryStatementService.queryVehicleHistoryCumulantStatement(vehicleDeliveryStatementDto, new PageView(pagesize, page)) ;
	}
	
	/**
	 * 查询车位流量计月报总表
	 * @Title:VehicleDeliveryStatementController
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @Date:2015年5月27日 下午10:45:51
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "queryVehicleMonthlyTotalStatement")
	@ResponseBody
	public Object queryVehicleMonthlyTotalStatement(@ModelAttribute VehicleDeliveryStatementDto vehicleDeliveryStatementDto,
													@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
													@RequestParam(defaultValue = "0", required = false, value = "page") int page) 
	{
		return vehicleDeliveryStatementService.queryVehicleMonthlyTotalStatement(vehicleDeliveryStatementDto, new PageView(pagesize, page)) ;
	}
	
	/**
	 * 查询货品月发货量报表
	 * @Title:VehicleDeliveryStatementController
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pagesize
	 * @param page
	 * @return
	 * @Date:2015年5月27日 下午10:46:23
	 * @return:OaMsg 
	 * @throws
	 */
	@RequestMapping(value = "queryProductMonthlyStatement")
	@ResponseBody
	public OaMsg queryProductMonthlyStatement(@ModelAttribute VehicleDeliveryStatementDto vehicleDeliveryStatementDto,
											  @RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
											  @RequestParam(defaultValue = "0", required = false, value = "page") int page) 
	{
		return vehicleDeliveryStatementService.queryProductMonthlyStatement(vehicleDeliveryStatementDto, new PageView(pagesize, page));
	}
	
	
	/**
	 * 月仓储入库明细列表导出Excel
	 * @Title:ExportController
	 * @Description:
	 * @param request
	 * @param response
	 * @param report
	 * @param name
	 * @Date:2015年11月11日 上午11:08:03
	 * @return: void 
	 * @throws
	 */
	@RequestMapping(value = "exportExcel")
	public void exportMonthStorage(HttpServletRequest request, HttpServletResponse response,@ModelAttribute final VehicleDeliveryStatementDto vsDto,String name,final String type) 
	{
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
					workbook= vehicleDeliveryStatementService.exportExcel(request, type,vsDto);
				return workbook;
			}
		});
	}
	
	
}
