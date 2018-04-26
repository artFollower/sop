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

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.FeeBillDto;
import com.skycloud.oa.feebill.model.FeeBill;
import com.skycloud.oa.feebill.service.FeeBillService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;

/**
 *账单
 * @author jiahy
 * @version 2015年6月14日 下午6:38:25
 */
@Controller
@RequestMapping("/feebill")
public class FeeBillController {
@Autowired
private FeeBillService feeBillService;
	
	
	 
		@RequestMapping(value="/list")
		@ResponseBody
		public OaMsg list(HttpServletRequest request,HttpServletResponse response,FeeBillDto feeBillDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
				@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			OaMsg oaMsg=feeBillService.getFeeBillList(feeBillDto, new PageView(pagesize, page));
          return oaMsg;
		}
		
		@RequestMapping(value="/add")
		@ResponseBody
		public OaMsg add(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute FeeBill feeBill)throws OAException{
			OaMsg oaMsg=feeBillService.addFeeBill(feeBill);
			return oaMsg;
		}
		
		@RequestMapping(value="/update")
		@ResponseBody
		public OaMsg update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute FeeBill feeBill) throws OAException{
			OaMsg oaMsg=feeBillService.updateFeeBill(feeBill);
			return oaMsg;
		}
		@RequestMapping(value="/delete")
		@ResponseBody
		public OaMsg delete(HttpServletRequest request,HttpServletResponse response,FeeBillDto  feeBillDto) throws OAException{
			OaMsg oaMsg=feeBillService.deleteFeeBill(feeBillDto);
			return oaMsg;
		}
		//获取首期费编号
		@RequestMapping(value="/getcodenum")
		@ResponseBody
		public OaMsg getCodeNum(HttpServletRequest request,HttpServletResponse response,FeeBillDto  feeBillDto) throws OAException{
			OaMsg oaMsg=feeBillService.getCodeNum(feeBillDto);
			return oaMsg;
		}
		@RequestMapping(value="/gettotalfee")
		@ResponseBody
		public OaMsg getTotalFee(HttpServletRequest request,HttpServletResponse response,FeeBillDto feeBillDto) throws OAException{
			OaMsg oaMsg=feeBillService.getTotalFee(feeBillDto);
          return oaMsg;
		}
		@RequestMapping(value="/rollback")
		@ResponseBody
		public OaMsg rollback(HttpServletRequest request,HttpServletResponse response,FeeBillDto feeBillDto) throws OAException{
			OaMsg oaMsg=feeBillService.rollback(feeBillDto);
          return oaMsg;
		}
		@RequestMapping(value="/exportDetailFee")
		@ResponseBody
		public void exportDetailFee(HttpServletRequest request,HttpServletResponse response,final FeeBillDto feeBillDto) throws OAException{
			ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

				@Override
				public HSSFWorkbook getWorkBook(HttpServletRequest request) {
					HSSFWorkbook workbook = null;
					try {
						workbook= feeBillService.exportDetailFee(request,feeBillDto);
						
					} catch (OAException e) {
						e.printStackTrace();
					}
					return workbook;
				}
			});
			
		}
}
