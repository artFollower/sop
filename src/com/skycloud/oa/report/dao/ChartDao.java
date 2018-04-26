/**
 * 
 * @Project:sop
 * @Title:ChartDao.java
 * @Package:com.skycloud.oa.report.dao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月28日 下午1:36:11
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.report.dto.ReportDto;

/**
 * <p>图形公用方法接口</p>
 * @ClassName:ChartDao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月28日 下午1:36:11
 * 
 */
public interface ChartDao 
{
	/**
	 * 查询库容状态统计图形数据
	 * @Title:ChartDao
	 * @Description:
	 * @param report
	 * @return
	 * @throws OAException
	 * @Date:2015年10月28日 下午1:46:07
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> findChart(ReportDto report)throws OAException;
	
	/**
	 * 查询库容状态图
	 * @Title:ChartDao
	 * @Description:
	 * @param report
	 * @return
	 * @throws OAException
	 * @Date:2015年12月4日 下午8:08:29
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> findChartDetail(ReportDto report)throws OAException;
}
