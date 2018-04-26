/**
 * 
 * @Project:sop
 * @Title:ExportService.java
 * @Package:com.skycloud.oa.report.service
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月4日 下午4:10:20
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.report.dto.ExportDto;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.utils.OaMsg;

/**
 * <p>导出Excel</p>
 * @ClassName:ExportService
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月4日 下午4:10:20
 * 
 */
public interface ExportService 
{
	
	
	/**
	 * 月仓储入库明细列表导出Excel
	 * @Title:ExportService
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @Date:2015年11月11日 上午10:32:12
	 * @return:HSSFWorkbook 
	 * @throws
	 */
	public HSSFWorkbook exportMonthStorage(final HttpServletRequest request, String type,String params,ReportDto report) throws OAException;
	
	/**
	 * 货物进出港通过量统计导出到Excel
	 * @Title:ExportService
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @Date:2015年11月11日 下午5:15:05
	 * @return:HSSFWorkbook 
	 * @throws
	 */
	public HSSFWorkbook exportInOut(final HttpServletRequest request,ReportDto report) throws OAException;
	
	/**
	 * 储罐周转率导出到Excel
	 * @Title:ExportService
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @Date:2015年11月11日 下午11:06:52
	 * @return:HSSFWorkbook 
	 * @throws
	 */
	public HSSFWorkbook exportTankTurnRate(final HttpServletRequest request, final ExportDto export) throws OAException;
	
	/**
	 * 码头进出港统计表导出到Excel
	 * @Title:ExportService
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @Date:2015年11月12日 上午8:56:02
	 * @return:HSSFWorkbook 
	 * @throws
	 */
	public HSSFWorkbook exportDock(final HttpServletRequest request, String type,String params,final ExportDto export) throws OAException;
	
	/**
	 * 外贸进出港统计表导出到Excel
	 * @Title:ExportService
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @Date:2015年11月12日 上午9:38:35
	 * @return:HSSFWorkbook 
	 * @throws
	 */
	public HSSFWorkbook exportTrade(final HttpServletRequest request, String type,String params,final ExportDto export) throws OAException;
	
	/**
	 * 装车站发货统计表导出到Excel
	 * @Title:ExportService
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @Date:2015年11月12日 上午9:48:01
	 * @return:HSSFWorkbook 
	 * @throws
	 */
	public HSSFWorkbook exportStation(final HttpServletRequest request, String type,String params,final ExportDto export) throws OAException;
	
	/**
	 * 通过单位统计表导出到Excel
	 * @Title:ExportService
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @Date:2015年11月12日 上午10:34:15
	 * @return:HSSFWorkbook 
	 * @throws
	 */
	public HSSFWorkbook exportUnit(final HttpServletRequest request, String type,String params,final ExportDto export) throws OAException;
	
	/**
	 * 年度仓储汇总表导出到Excel
	 * @Title:ExportService
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @return
	 * @throws OAException
	 * @Date:2015年11月12日 上午10:57:30
	 * @return:HSSFWorkbook 
	 * @throws
	 */
	public HSSFWorkbook exportStorage(final HttpServletRequest request, String type,String params,final ExportDto export) throws OAException;
	
	/**
	 * 吞吐量统计表导出到Excel
	 * @Title:ExportService
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @param export
	 * @return
	 * @throws OAException
	 * @Date:2015年11月19日 上午10:35:50
	 * @return:HSSFWorkbook 
	 * @throws
	 */
	public HSSFWorkbook exportThroughtRate(final HttpServletRequest request, String type,String params,final ExportDto export) throws OAException;
	
	/**
	 * 管道运输通过明细表导出到Excel
	 * @Title:ExportService
	 * @Description:
	 * @param request
	 * @param type
	 * @param params
	 * @param report
	 * @return
	 * @throws OAException
	 * @Date:2015年12月7日 上午9:23:29
	 * @return:HSSFWorkbook 
	 * @throws
	 */
	public HSSFWorkbook exportPipe(final HttpServletRequest request, String type,String params,final ReportDto report) throws OAException;

	/**
	 * 分流台账明细表导出到Excel
	 * @param request
	 * @param type
	 * @param report
	 * @return
	 */
	public HSSFWorkbook outBoundBook(HttpServletRequest request, String type, ReportDto report) throws OAException;

	public HSSFWorkbook inBoundBook(HttpServletRequest request, String type,
			ReportDto report) throws OAException;

	/**
	 * @Title exportPumpShedRotation
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年9月9日上午9:51:27
	 * @throws
	 */
	public HSSFWorkbook exportPumpShedRotation(HttpServletRequest request,ReportDto report) throws OAException;
}
