package com.skycloud.oa.common.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 公共方法业务逻辑处理类
 * @ClassName: CommonService 
 * @Description: TODO
 * @author xie
 * @date 2015年4月2日 下午10:45:28
 */
public interface ExcelService {
	
	HSSFWorkbook exportExcel(HttpServletRequest request,String type,String params);
	
}
