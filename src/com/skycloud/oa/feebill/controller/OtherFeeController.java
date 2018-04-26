/**
 * 
 */
package com.skycloud.oa.feebill.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.OtherFeeCargoDto;
import com.skycloud.oa.feebill.dto.OtherFeeDto;
import com.skycloud.oa.feebill.service.OtherFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;

/**
 *其他费用（仓单注册费）
 * @author jiahy
 * @version 2015年6月14日 下午6:38:25
 */
@Controller
@RequestMapping("/otherfee")
public class OtherFeeController {
@Autowired
private OtherFeeService otherFeeService;

	 
		@RequestMapping(value="/list")
		@ResponseBody
		public OaMsg list(HttpServletRequest request,HttpServletResponse response,OtherFeeDto otherFeeDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
				@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			OaMsg oaMsg=otherFeeService.getOtherFeeList(otherFeeDto, new PageView(pagesize, page));
          return oaMsg;
		}
		
		@RequestMapping(value="/add")
		@ResponseBody
		public OaMsg add(HttpServletRequest request ,HttpServletResponse response, String otherfeedto)throws OAException{
			OtherFeeDto d = new Gson().fromJson(otherfeedto, OtherFeeDto.class) ;
			OaMsg oaMsg=otherFeeService.addOtherFee(d);
			return oaMsg;
		}
		
		@RequestMapping(value="/update")
		@ResponseBody
		public OaMsg update(HttpServletRequest request,HttpServletResponse response, String otherfeedto) throws OAException{
			OtherFeeDto d = new Gson().fromJson(otherfeedto, OtherFeeDto.class) ;
			OaMsg oaMsg=otherFeeService.updateOtherFee(d);
			return oaMsg;
		}
		@RequestMapping(value="/delete")
		@ResponseBody
		public OaMsg delete(HttpServletRequest request,HttpServletResponse response,OtherFeeDto  otherFeeDto) throws OAException{
			OaMsg oaMsg=otherFeeService.deleteOtherFee(otherFeeDto);
			return oaMsg;
		}
		//获取其他费编号
		@RequestMapping(value="/getcodenum")
		@ResponseBody
		public OaMsg getCodeNum(HttpServletRequest request,HttpServletResponse response,OtherFeeDto  otherFeeDto) throws OAException{
			OaMsg oaMsg=otherFeeService.getCodeNum(otherFeeDto);
			return oaMsg;
		}
		//获取其他费编号
		@RequestMapping(value="/getcargomsg")
		@ResponseBody
		public OaMsg getCargoMsg(HttpServletRequest request,HttpServletResponse response,OtherFeeDto  otherFeeDto) throws OAException{
			OaMsg oaMsg=otherFeeService.getCargoMsg(otherFeeDto);
			return oaMsg;
		}
		//获取其他费编号
		@RequestMapping(value="/getotherfeecargo")
		@ResponseBody
		public OaMsg getOtherFeeCargo(HttpServletRequest request,HttpServletResponse response,OtherFeeDto  otherFeeDto) throws OAException{
			OaMsg oaMsg=otherFeeService.getOtherFeeCargo(otherFeeDto);
			return oaMsg;
		}
		//获取其他费编号
		@RequestMapping(value="/deletefeecargo")
		@ResponseBody
		public OaMsg deleteFeeCargo(HttpServletRequest request,HttpServletResponse response,OtherFeeCargoDto  otherFeeDto) throws OAException{
			OaMsg oaMsg=otherFeeService.deleteFeeCargo(otherFeeDto);
			return oaMsg;
		}
		 @RequestMapping(value="exportExcelList")
		   public void exportExcelList(HttpServletRequest request,HttpServletResponse response,final OtherFeeDto eFeeDto){
				ExcelOutputUtil.handleExcelOutput(request, response, new CallBack() {
					@Override
					public HSSFWorkbook getWorkBook(HttpServletRequest request) {
						OaMsg msg = new OaMsg();
						HSSFWorkbook workbook = null;
						 workbook=otherFeeService.exportExcelList(request,eFeeDto);
						return workbook;
					}
				});
			
		   };
		
}
