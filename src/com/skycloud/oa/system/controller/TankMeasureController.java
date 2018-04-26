package com.skycloud.oa.system.controller;

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
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.TankMeasureDto;
import com.skycloud.oa.system.model.TankMeasure;
import com.skycloud.oa.system.service.TankMeasureService;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;

/**
 *油品参数表
 * @author jiahy
 *
 */
@Controller
@RequestMapping("/tankmeasure")
public class TankMeasureController {
  @Autowired
	private TankMeasureService tankMeasureService;
	
	
    @RequestMapping("/list")
    @ResponseBody
	public Object list(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute TankMeasureDto tankMeasureDto,@ModelAttribute InboundOperationDto ioDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg oaMsg=tankMeasureService.getList(tankMeasureDto, new PageView(pagesize, page));
		return oaMsg;
	}
   
    @RequestMapping("/add")
    @ResponseBody
	public Object save(HttpServletRequest request,HttpServletResponse response,@ModelAttribute TankMeasure tankMeasure) throws OAException{
		return tankMeasureService.addTankMeasure(tankMeasure);
	}
    @RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute  TankMeasure tankMeasure) throws OAException{
		return tankMeasureService.updateTankMeasure(tankMeasure);
	}
    @RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,@ModelAttribute TankMeasureDto tankMeasureDto) throws OAException{
		return tankMeasureService.deleteTankMeasure(tankMeasureDto);
	}
	
    @RequestMapping("/getcodenum")
    @ResponseBody
	public Object getCodeNum(HttpServletRequest request,HttpServletResponse response) throws OAException{
		return tankMeasureService.getCodeNum();
	}
    
	@RequestMapping("/exportexcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) throws OAException {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack() {
			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook = tankMeasureService.exportExcel(request);

				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
}
