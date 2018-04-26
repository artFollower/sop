package com.skycloud.oa.inbound.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CustomsReleaseDto;
import com.skycloud.oa.inbound.dto.ExceedFeeDto;
import com.skycloud.oa.inbound.model.CustomsRelease;
import com.skycloud.oa.inbound.model.ExceedFee;
import com.skycloud.oa.inbound.service.CustomsReleaseService;
import com.skycloud.oa.inbound.service.ExceedFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *海关放行量统计表
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午3:13:30
 */
@Controller
@RequestMapping("/customsrelease")
public class CustomsReleaseController {
	
	@Autowired
	private CustomsReleaseService customsReleaseService;
	@RequestMapping(value="/list")
	@ResponseBody
	public OaMsg list(HttpServletRequest request,HttpServletResponse response,CustomsReleaseDto crDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg oaMsg=customsReleaseService.getCustomsReleaseList(crDto, new PageView(pagesize, page));
		return oaMsg;
	}
	@RequestMapping(value="/update")
	@ResponseBody
	public OaMsg update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CustomsRelease customsRelease) throws OAException{
		OaMsg oaMsg=customsReleaseService.updateCustomsRelease(customsRelease);
		return oaMsg;
	}
	@RequestMapping(value="/getTotalNum")
	@ResponseBody
	public OaMsg getTotalNum(HttpServletRequest request,HttpServletResponse response,CustomsReleaseDto crDto) throws OAException{
		OaMsg oaMsg=customsReleaseService.getTotalNum(crDto);
		return oaMsg;
	}
	@RequestMapping(value="/exportExcel")
	@ResponseBody
	public void exportEXCEL(HttpServletRequest request,HttpServletResponse response,@ModelAttribute CustomsReleaseDto crDto) throws OAException{
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try 
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			//进行转码
			response.setHeader("content-disposition", "attachment;filename=" + sdf.format(new Date()) + ".xls");
			
			HSSFWorkbook workbook = customsReleaseService.exportExcel(request,crDto);
			
			fOut = response.getOutputStream();
			
			workbook.write(fOut);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				fOut.flush();
				fOut.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
}
