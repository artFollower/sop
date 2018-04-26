/**
 * 
 * @Project:sop
 * @Title:ReportService.java
 * @Package:com.skycloud.oa.report.service
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月26日 上午10:28:43
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.model.ThroughtMsg;
import com.skycloud.oa.utils.OaMsg;

public interface ReportService 
{
	public OaMsg list(ReportDto report, PageView pageView);
	/**
	 * @Title 更新储罐历史状态（每月最后一天的状态）
	 * @Descrption:TODO
	 * @param:@param reportDto
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年8月19日上午10:05:43
	 * @throws
	 */
	public OaMsg updateTankLogState(ReportDto reportDto) throws OAException;
	/**
	 *@author jiahy
	 * @param report
	 * @return
	 */
	public HSSFWorkbook exportYearPipe(HttpServletRequest request,ReportDto report)throws OAException;

	/**
	 *@author jiahy
	 * @param request
	 * @param report
	 * @return
	 */
	public HSSFWorkbook exportMonthBerth(HttpServletRequest request,ReportDto report)throws OAException;
	/**
	 * @Title addorupdateThroughMsg
	 * @Descrption:TODO
	 * @param:@param throughtMsg
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年12月23日上午10:50:01
	 * @throws
	 */
	public OaMsg addorupdateThroughMsg(ThroughtMsg throughtMsg) throws OAException;
	/**
	 * @Title getBerthDetailList
	 * @Descrption:TODO
	 * @param:@param report
	 * @param:@param pageView
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2017年1月3日下午5:16:04
	 * @throws
	 */
	public OaMsg getBerthDetailList(ReportDto report, PageView pageView) throws OAException;

}
