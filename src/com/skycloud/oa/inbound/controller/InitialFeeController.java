package com.skycloud.oa.inbound.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.InitialFeeDto;
import com.skycloud.oa.inbound.model.InitialFee;
import com.skycloud.oa.inbound.service.InitialFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;

/**
 *首期费
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午3:13:30
 */
@Controller
@RequestMapping("/initialfee")
public class InitialFeeController {
	
	@Autowired
	private InitialFeeService initialFeeService;
	
     //获取系统用户信息
	@RequestMapping(value="/getsystemuser")
	@ResponseBody
	public OaMsg  getSystemUser(HttpServletRequest request,HttpServletResponse response){
		OaMsg oaMsg=new OaMsg();
		//到港信息表数据
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", user.getId());
		map.put("userName", user.getName());
		oaMsg.getData().add(map);
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		return oaMsg;
	}
	//获取首期费列表
	@RequestMapping(value="/list")
	@ResponseBody
	public OaMsg list(HttpServletRequest request,HttpServletResponse response,InitialFeeDto iFeeDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg oaMsg=initialFeeService.getInitialFeeList(iFeeDto, new PageView(pagesize, page));
		return oaMsg;
	}
	//获取首期费信息
		@RequestMapping(value="/getinitialfeemsg")
		@ResponseBody
		public OaMsg getInitialFeeMsg(HttpServletRequest request,HttpServletResponse response,InitialFeeDto iFeeDto) throws OAException{
			OaMsg oaMsg=initialFeeService.getInitialFeeMsg(iFeeDto);
			return oaMsg;
		}
	//更新首期费
	@RequestMapping(value="/update")
	@ResponseBody
	public OaMsg update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute InitialFee initialFee) throws OAException{
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		if(initialFee.getStatus()!=null&&(initialFee.getStatus()==0||initialFee.getStatus()==1)){
			initialFee.setCreateUserId(user.getId());
		}
		OaMsg oaMsg=initialFeeService.updateInitialFee(initialFee);
		return oaMsg;
	}
	//添加货批的首期费
	@RequestMapping(value="/add")
	@ResponseBody
	public OaMsg add(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute InitialFee initialFee)throws OAException{
		OaMsg oaMsg=initialFeeService.addInitialFee(initialFee);
		return oaMsg;
	}
	//添加货批的首期费
		@RequestMapping(value="/addorupdate")
		@ResponseBody
		public OaMsg addorupdate(HttpServletRequest request ,HttpServletResponse response,String iFeeDto)throws OAException{
			InitialFeeDto niFeeDto=new Gson().fromJson(iFeeDto, InitialFeeDto.class);
			OaMsg oaMsg=initialFeeService.addOrUpdateInitialFee(niFeeDto);
			return oaMsg;
		}
	//添加合同的首期费
	@RequestMapping(value="/addcontractfee")
	@ResponseBody
	public OaMsg addContractFee(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute InitialFee initialFee)throws OAException{
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		initialFee.setCreateUserId(user.getId());
		OaMsg oaMsg=initialFeeService.addContractInitialFee(initialFee);
		return oaMsg;
	}
	//删除首期费
	@RequestMapping(value="/delete")
	@ResponseBody
	public OaMsg delete(HttpServletRequest request,HttpServletResponse response,InitialFeeDto iFeeDto) throws OAException{
		OaMsg oaMsg=initialFeeService.deleteInitialFee(iFeeDto);
		return oaMsg;
	}
	//获取首期费编号
	@RequestMapping(value="/getcodenum")
	@ResponseBody
	public OaMsg getCodeNum(HttpServletRequest request,HttpServletResponse response,InitialFeeDto iFeeDto) throws OAException{
		OaMsg oaMsg=initialFeeService.getCodeNum(iFeeDto);
		return oaMsg;
	}
		/**
		 * 根据合同号，起止时间获取时间内的已经入库的货批
		 *@author jiahy
		 * @param request
		 * @param response
		 * @param iFeeDto
		 * @return
		 * @throws OAException
		 */
		@RequestMapping(value="/getcontractcargolist")
		@ResponseBody
		public OaMsg getContractCargoList(HttpServletRequest request,HttpServletResponse response,InitialFeeDto iFeeDto) throws OAException{
			OaMsg oaMsg=initialFeeService.getContractCargoList(iFeeDto);
			return oaMsg;
		}
	
		
		/**
		 * 校验货批是否生成了结算单
		 *@author jiahy
		 * @param request
		 * @param response
		 * @param iFeeDto
		 * @return
		 * @throws OAException
		 */
		@RequestMapping(value="/checkcargofee")
		@ResponseBody
		public OaMsg checkCargoFee(HttpServletRequest request,HttpServletResponse response,InitialFeeDto iFeeDto) throws OAException{
			OaMsg oaMsg=initialFeeService.checkCargoFee(iFeeDto);
			return oaMsg;
		}
		
		/**
		 * 更新货批生成结算单未生成账单的货主和开票抬头
		 *@author jiahy
		 * @param request
		 * @param response
		 * @param iFeeDto
		 * @return
		 * @throws OAException
		 */
		@RequestMapping(value="/updatecargofee")
		@ResponseBody
		public OaMsg updateCargoFee(HttpServletRequest request,HttpServletResponse response,InitialFeeDto iFeeDto) throws OAException{
			OaMsg oaMsg=initialFeeService.updateCargoFee(iFeeDto);
			return oaMsg;
		}
		//更新首期费
		@RequestMapping(value="/backstatus")
		@ResponseBody
		public OaMsg backStatus(HttpServletRequest request,HttpServletResponse response,@ModelAttribute InitialFee initialFee) throws OAException{
			OaMsg oaMsg=initialFeeService.backStatus(initialFee);
			return oaMsg;
		}
		@RequestMapping(value="/getoutboundtotallist")
		@ResponseBody
		public OaMsg getOutBoundTotalList(HttpServletRequest request,HttpServletResponse response,InitialFeeDto iFeeDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
				@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			OaMsg oaMsg=initialFeeService.getOutBoundTotalList(iFeeDto,new PageView(pagesize, page));
			return oaMsg;
		}
		
		/**
		 * 获取油品结算单
		 * @Title getOilsFeeMsg
		 * @Descrption:TODO
		 * @param:@param request
		 * @param:@param response
		 * @param:@param iFeeDto
		 * @param:@return
		 * @param:@throws OAException
		 * @return:OaMsg
		 * @auhor jiahy
		 * @date 2016年2月23日下午2:50:24
		 * @throws
		 */
		@RequestMapping(value="/getoilsfeemsg")
		@ResponseBody
		public OaMsg getOilsFeeMsg(HttpServletRequest request,HttpServletResponse response,InitialFeeDto iFeeDto) throws OAException{
			OaMsg oaMsg=initialFeeService.getOilsFeeMsg(iFeeDto);
			return oaMsg;
		}
		/**
		 * 获取出库油品结算
		 * @Title getOutFeeMsg
		 * @Descrption:TODO
		 * @param:@param request
		 * @param:@param response
		 * @param:@param iFeeDto
		 * @param:@return
		 * @param:@throws OAException
		 * @return:OaMsg
		 * @auhor jiahy
		 * @date 2016年2月23日下午2:50:43
		 * @throws
		 */
		@RequestMapping(value="/getoutfeemsg")
		@ResponseBody
		public OaMsg getOutFeeMsg(HttpServletRequest request,HttpServletResponse response,InitialFeeDto iFeeDto) throws OAException{
			OaMsg oaMsg=initialFeeService.getOutFeeMsg(iFeeDto);
			return oaMsg;
		}
		@RequestMapping(value = "/exportExcel")
		public void exportExcel(HttpServletRequest request, HttpServletResponse response,final InitialFeeDto iFeeDto) {
			ExcelOutputUtil.handleExcelOutput(request, response, new CallBack() {
				@Override
				public HSSFWorkbook getWorkBook(HttpServletRequest request) {
					HSSFWorkbook workbook =initialFeeService.exportExcel(request,iFeeDto);
					return workbook;
				}
			});
		}
		
		
}
