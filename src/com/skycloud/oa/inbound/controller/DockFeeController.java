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

import com.google.gson.Gson;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.model.DockFeeCharge;
import com.skycloud.oa.inbound.dto.DockFeeDto;
import com.skycloud.oa.inbound.service.DockFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;

/**
 *码头规费
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午3:13:30
 */
@Controller
@RequestMapping("/dockfee")
public class DockFeeController {
	
	@Autowired
	private DockFeeService dockFeeService;
	
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
	//获取码头规费列表
	@RequestMapping(value="/list")
	@ResponseBody
	public OaMsg list(HttpServletRequest request,HttpServletResponse response,DockFeeDto dFeeDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg oaMsg=dockFeeService.getDockFeeList(dFeeDto, new PageView(pagesize, page));
		return oaMsg;
	}
	
	//获取码头规费列表
		@RequestMapping(value="/getdockfeemsg")
		@ResponseBody
		public OaMsg getDockFeeMsg(HttpServletRequest request,HttpServletResponse response,DockFeeDto dFeeDto) throws OAException{
			OaMsg oaMsg=dockFeeService.getDockFeeMsg(dFeeDto);
			return oaMsg;
		}
	//获取码头规费列表
		@RequestMapping(value="/getarrivallist")
		@ResponseBody
		public OaMsg getArrivalList(HttpServletRequest request,HttpServletResponse response,DockFeeDto dFeeDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
				@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			OaMsg oaMsg=dockFeeService.getArrivalList(dFeeDto, new PageView(pagesize, page));
			return oaMsg;
		}
	//更新码头规费
	@RequestMapping(value="/andorupdate")
	@ResponseBody
	public OaMsg update(HttpServletRequest request,HttpServletResponse response,String dockFeeDto) throws OAException{
		DockFeeDto dFeeDto=new Gson().fromJson(dockFeeDto, DockFeeDto.class);
		OaMsg oaMsg=dockFeeService.addOrUpdateDockFee(dFeeDto);
		return oaMsg;
	}
	//更新码头规费
		@RequestMapping(value="/copy")
		@ResponseBody
		public OaMsg copy(HttpServletRequest request,HttpServletResponse response,DockFeeCharge dockFeeCharge) throws OAException{
			OaMsg oaMsg=dockFeeService.copy(dockFeeCharge);
			return oaMsg;
		}
	
	//删除码头规费
	@RequestMapping(value="/delete")
	@ResponseBody
	public OaMsg delete(HttpServletRequest request,HttpServletResponse response,@ModelAttribute DockFeeDto dFeeDto) throws OAException{
		OaMsg oaMsg=dockFeeService.deleteDockFee(dFeeDto);
		return oaMsg;
	}
	//获取码头规费编号
	@RequestMapping(value="/getcodenum")
	@ResponseBody
	public OaMsg getCodeNum(HttpServletRequest request,HttpServletResponse response,@ModelAttribute DockFeeDto dFeeDto) throws OAException{
		OaMsg oaMsg=dockFeeService.getCodeNum(dFeeDto);
		return oaMsg;
	}
		/**
		 * @Title exportExcel
		 * @Descrption:TODO
		 * @param:@param request
		 * @param:@param response
		 * @param:@param dFeeDto
		 * @param:@return
		 * @param:@throws OAException
		 * @return:OaMsg
		 * @auhor jiahy
		 * @date 2016年2月19日上午9:27:46
		 * @throws
		 */
		@RequestMapping(value="/exportExcel")
		public void exportExcel(HttpServletRequest request,HttpServletResponse response,@ModelAttribute final DockFeeDto dFeeDto) throws OAException{
			ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

				@Override
				public HSSFWorkbook getWorkBook(HttpServletRequest request) {
					HSSFWorkbook workbook = null;
					try {
						workbook=dockFeeService.exportExcel(request,dFeeDto);
						
					} catch (OAException e) {
						e.printStackTrace();
					}
					return workbook;
				}
			});
		}
		@RequestMapping(value="/exportListExcel")
		public void exportListExcel(HttpServletRequest request,HttpServletResponse response,@ModelAttribute final DockFeeDto dFeeDto) throws OAException{
			ExcelOutputUtil.handleExcelOutput(request, response, new CallBack(){

				@Override
				public HSSFWorkbook getWorkBook(HttpServletRequest request) {
					HSSFWorkbook workbook = null;
						try {
							workbook=dockFeeService.exportListExcel(request,dFeeDto);
						} catch (OAException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					return workbook;
				}
			});
		}
}
