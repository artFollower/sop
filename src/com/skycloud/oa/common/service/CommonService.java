package com.skycloud.oa.common.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.utils.OaMsg;

/**
 * 公共方法业务逻辑处理类
 * @ClassName: CommonService 
 * @Description: TODO
 * @author xie
 * @date 2015年4月2日 下午10:45:28
 */
public interface CommonService {
	
	/**
	 * 公共导出Excel
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月2日 下午10:45:48  
	 * @param type
	 * @return
	 */
	HSSFWorkbook exportExcel(HttpServletRequest request,String type,String params);
	
	/**
	 * 获取基本参数
	 * @Description: TODO
	 * @author xie
	 * @date 2015年7月28日 下午10:13:23  
	 * @param msg
	 */
	void getParams(OaMsg msg);
}
