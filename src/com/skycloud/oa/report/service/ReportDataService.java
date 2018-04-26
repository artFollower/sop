/**
 * 
 */
package com.skycloud.oa.report.service;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年11月10日 上午8:33:14
 */
public interface ReportDataService {

	/**
	 *@author jiahy
	 * @param report
	 * @return
	 * @throws OAException
	 */
	public OaMsg getYearPipeInboundData(ReportDto report) throws OAException;
	
	/**
	 *@author jiahy
	 * @param report
	 * @return
	 * @throws OAException
	 */
	public OaMsg getYearPipeOutboundData(ReportDto report) throws OAException;
	
	/**
	 *@author jiahy
	 * @param report
	 * @return
	 * @throws OAException
	 */
	public OaMsg getMonthBerth(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 * @return
	 */
	public OaMsg getYearPipe(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 * @return
	 */
	public OaMsg getProductionTarget(ReportDto report) throws OAException;
	
	/**
	 * 分流台账
	 * @param report
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public OaMsg getOutBoundBook(ReportDto report,PageView pageView) throws OAException;

}
