/**
 * 
 */
package com.skycloud.oa.feebill.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.DockFeeBillDto;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.dto.FeeBillDto;
import com.skycloud.oa.feebill.model.DockFeeBill;
import com.skycloud.oa.feebill.model.FeeBill;
import com.skycloud.oa.feebill.service.DockFeeBillService;
import com.skycloud.oa.feebill.service.FeeBillService;
import com.skycloud.oa.inbound.dto.InitialFeeDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;

/**
 *码头规费账单
 * @author jiahy
 * @version 2015年6月14日 下午6:38:25
 */
@Controller
@RequestMapping("/dockfeebill")
public class DockFeeBillController {
@Autowired
private DockFeeBillService dockfeeBillService;
	
		@RequestMapping(value="/list")
		@ResponseBody
		public OaMsg list(HttpServletRequest request,HttpServletResponse response,DockFeeBillDto dDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
				@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			OaMsg oaMsg=dockfeeBillService.getDockFeeBillList(dDto, new PageView(pagesize, page));
          return oaMsg;
		}
		@RequestMapping(value="/getDockFeeBillMsg")
		@ResponseBody
		public OaMsg getDockFeeBillMsg(HttpServletRequest request,HttpServletResponse response,DockFeeBillDto dDto) throws OAException{
			OaMsg oaMsg=dockfeeBillService.getDockFeeBillMsg(dDto);
          return oaMsg;
		}
		@RequestMapping(value="/addorupdate")
		@ResponseBody
		public OaMsg add(HttpServletRequest request ,HttpServletResponse response,String dfDto)throws OAException{
			DockFeeBillDto ndfDto=new Gson().fromJson(dfDto, DockFeeBillDto.class);
			OaMsg oaMsg=dockfeeBillService.addDockFeeBill(ndfDto);
			return oaMsg;
		}
		
		@RequestMapping(value="/update")
		@ResponseBody
		public OaMsg update(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute DockFeeBill dockFeeBill)throws OAException{
			OaMsg oaMsg=dockfeeBillService.updateDockFeeBill(dockFeeBill);
			return oaMsg;
		}
		
		@RequestMapping(value="/delete")
		@ResponseBody
		public OaMsg delete(HttpServletRequest request,HttpServletResponse response,DockFeeBillDto  dockFeeBillDto) throws OAException{
			OaMsg oaMsg=dockfeeBillService.deleteDockFeeBill(dockFeeBillDto);
			return oaMsg;
		}
		//获取首期费编号
		@RequestMapping(value="/getcodenum")
		@ResponseBody
		public OaMsg getCodeNum(HttpServletRequest request,HttpServletResponse response,DockFeeBillDto  dockFeeBillDto) throws OAException{
			OaMsg oaMsg=dockfeeBillService.getCodeNum(dockFeeBillDto);
			return oaMsg;
		}
		@RequestMapping(value="/gettotalfee")
		@ResponseBody
		public OaMsg getTotalFee(HttpServletRequest request,HttpServletResponse response,DockFeeBillDto  dockFeeBillDto) throws OAException{
			OaMsg oaMsg=dockfeeBillService.getTotalFee(dockFeeBillDto);
          return oaMsg;
		}
		@RequestMapping(value="/removeFeecharge")
		@ResponseBody
		public OaMsg removeFeecharge(HttpServletRequest request,HttpServletResponse response, String dfDto) throws OAException{
			DockFeeBillDto ndfDto=new Gson().fromJson(dfDto, DockFeeBillDto.class);
			OaMsg oaMsg=dockfeeBillService.removeFeecharge(ndfDto);
          return oaMsg;
		}
		@RequestMapping(value="/makefeebill")
		@ResponseBody
		public OaMsg makeFeeBill(HttpServletRequest request,HttpServletResponse response,DockFeeChargeDto  dfcDto) throws OAException{
			OaMsg oaMsg=dockfeeBillService.makeFeeBill(dfcDto);
          return oaMsg;
		}
		@RequestMapping(value="/reviewfeebill")
		@ResponseBody
		public OaMsg reviewFeeBill(HttpServletRequest request,HttpServletResponse response,DockFeeChargeDto  dfcDto) throws OAException{
			OaMsg oaMsg=dockfeeBillService.reviewFeeBill(dfcDto);
          return oaMsg;
		}
		@RequestMapping(value="/rollback")
		@ResponseBody
		public OaMsg rollBack(HttpServletRequest request,HttpServletResponse response,DockFeeBillDto  dockFeeBillDto) throws OAException{
			OaMsg oaMsg=dockfeeBillService.rollBack(dockFeeBillDto);
          return oaMsg;
		}
		@RequestMapping(value="/exportExcel")
		@ResponseBody
		public void exportExcel(HttpServletRequest request,HttpServletResponse response,final DockFeeBillDto  dockFeeBillDto,final int type ) throws OAException{
			ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

				@Override
				public HSSFWorkbook getWorkBook(HttpServletRequest request) {
					HSSFWorkbook workbook = null;
					try {
						
						if(type==2){
							
							workbook= dockfeeBillService.exportExcel(request,dockFeeBillDto);
						}else{
							workbook= dockfeeBillService.exportExcel1(request,dockFeeBillDto);
						}
						
					} catch (OAException e) {
						e.printStackTrace();
					}
					return workbook;
				}
			});
		}
		@RequestMapping(value="exportDetailFee")
		@ResponseBody
		public void exportDetailFee(HttpServletRequest request,HttpServletResponse response,final int id) throws OAException{
			ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

				@Override
				public HSSFWorkbook getWorkBook(HttpServletRequest request) {
					HSSFWorkbook workbook = null;
					try {
						workbook= dockfeeBillService.exportDetailFee(request,id);
						
					} catch (OAException e) {
						e.printStackTrace();
					}
					return workbook;
				}
			});
		}
		}
