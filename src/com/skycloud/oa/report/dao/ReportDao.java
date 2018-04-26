/**
 * 
 * @Project:sop
 * @Title:ReportDao.java
 * @Package:com.skycloud.oa.report.dao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月26日 上午10:26:51
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.dto.ReportDto;

/**
 * <p>报表公共查询模块</p>
 * @ClassName:ReportDao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月26日 上午10:26:51
 * 
 */
public interface ReportDao 
{
	/**
	 * 报表查询公共方法
	 * @Title:ReportDao
	 * @Description:
	 * @param report
	 * @param pageView
	 * @return
	 * @throws OAException
	 * @Date:2015年10月26日 上午10:44:13
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> list(ReportDto report,PageView pageView)throws OAException ;
	
	/**
	 * 查询公共报表总记录数
	 * @Title:ReportDao
	 * @Description:
	 * @param report
	 * @return
	 * @throws OAException
	 * @Date:2015年10月26日 上午11:05:05
	 * @return: int 
	 * @throws
	 */
	public int getCount(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 * @return
	 */
	public List<Map<String,Object>> getYearPipeInboundData(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 * @return
	 */
	public List<Map<String, Object>> getYearPipeOutboundData(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 */
	public List<Map<String, Object>>  getMonthBerth(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 * @return
	 */
	public List<Map<String, Object>> getSpecialMonthBerth(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 */
	public Map<String,Object> getInboundArrival(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 */
	public Map<String,Object>  getOutbountArrival(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 * @return
	 */
	public Map<String,Object> getTruckData(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 * @return
	 */
	public Map<String, Object> getInboundTankNum(ReportDto report) throws OAException;

	/**
	 *@author jiahy
	 * @param report
	 * @return
	 */
	public Map<String, Object> getOutboundTankNum(ReportDto report) throws OAException;

	/**
	 * 获取分类台账数据
	 * @param report
	 * @param startRecord
	 * @param maxresult
	 * @return
	 */
	public List<Map<String, Object>> getOutArrivalBook(ReportDto report, int startRecord, int maxresult) throws OAException;

	public int getOutArrivalBookCount(ReportDto report)throws OAException;

	/**
	 * 获取出港的去向
	 * @param valueOf
	 * @return
	 */
	public Map<String, Object> getOutBoundPlace(Integer arrivalId)throws OAException;

	/**
	 * @Title getPumpShedRotation
	 * @Descrption:TODO
	 * @param:@param report
	 * @param:@return
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年9月12日上午9:02:48
	 * @throws
	 */
	public List<Map<String, Object>> getPumpShedRotation(ReportDto report) throws OAException;

	/**
	 * @Title getBerthDetailList
	 * @Descrption:TODO
	 * @param:@param report
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @return:Collection<? extends Object>
	 * @auhor jiahy
	 * @date 2017年1月3日下午5:32:46
	 * @throws
	 */
	public List<Map<String, Object>> getBerthDetailList(ReportDto report,int startRecord, int maxresult) throws OAException;

	/**
	 * @Title getBerthDetailCount
	 * @Descrption:TODO
	 * @param:@param report
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2017年1月3日下午5:34:33
	 * @throws
	 */
	public int getBerthDetailCount(ReportDto report) throws OAException;
}
