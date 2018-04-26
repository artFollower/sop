package com.skycloud.oa.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.common.service.CommonService;
import com.skycloud.oa.common.service.ExcelService;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;

/**
 * 公共控制类
 * 
 * @ClassName: CommonController
 * @Description: TODO
 * @author xie
 * @date 2015年4月2日 下午10:46:05
 */
@Controller
@RequestMapping("/common")
public class CommonController {

	@Autowired
	CommonService commonService;
	@Autowired
	ExcelService excelService;

	/**
	 * 导出excel
	 * 
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月2日 下午10:50:54
	 * @param request
	 * @param response
	 * @param name
	 * @param type
	 */
	@RequestMapping(value = "exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response, String name, final String type,final String params) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack() {
			
			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				if ("51".equals((String) request.getParameter("type"))
						|| "50".equals((String) request.getParameter("type"))
						|| "53".equals(request.getParameter("type"))
						|| "52".equals(request.getParameter("type"))
						|| "54".equals((String) request.getParameter("type"))||"55".equals((String) request.getParameter("type"))
						||"56".equals((String) request.getParameter("type"))) {
					workbook = excelService.exportExcel(request, type, params);
				} else {
					workbook = commonService.exportExcel(request, type, params);
				}
				return workbook;
			}
		});
	}

	/**
	 * 查询基本参数信息
	 * 
	 * @Description: TODO
	 * @author xie
	 * @date 2015年7月28日 下午10:18:02
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/params")
	public Object params() {
		OaMsg msg = new OaMsg();
		commonService.getParams(msg);
		return msg;
	}

}
