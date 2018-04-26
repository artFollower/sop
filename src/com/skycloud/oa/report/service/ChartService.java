/**
 * 
 * @Project:sop
 * @Title:ChartService.java
 * @Package:com.skycloud.oa.report.service
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月28日 下午1:37:57
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.service;

import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.utils.OaMsg;

/**
 * <p>图形封装接口</p>
 * @ClassName:ChartService
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月28日 下午1:37:57
 * 
 */
public interface ChartService 
{
	/**
	 * 查询库容状态统计图形数据
	 * @Title:ChartService
	 * @Description:
	 * @param report
	 * @return
	 * @Date:2015年10月28日 下午2:14:04
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg findChart(ReportDto report);
	
	/**
	 * 查询库容状态图
	 * @Title:ChartService
	 * @Description:
	 * @param report
	 * @return
	 * @Date:2015年12月4日 下午8:11:24
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg findChartDetail(ReportDto report);
}
