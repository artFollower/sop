/**
 * 
 * @Project:sop
 * @Title:ChartServiceImpl.java
 * @Package:com.skycloud.oa.report.service.impl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月28日 下午1:38:32
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.report.dao.ChartDao;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.service.ChartService;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.utils.OaMsg;

/**
 * <p>图形封装实现类</p>
 * @ClassName:ChartServiceImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月28日 下午1:38:32
 * 
 */
@Service
public class ChartServiceImpl implements ChartService 
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(ChartServiceImpl.class);
	
	@Autowired
	private ChartDao chartDao;
	
	/**
	 * 查询库容状态统计图形数据
	 * @Title:findChart
	 * @Description:
	 * @param report
	 * @return
	 * @see 
	 */
	@Override
	public OaMsg findChart(ReportDto report) 
	{
		OaMsg oaMsg = new OaMsg();
		try 
		{
			oaMsg.getData().addAll(chartDao.findChart(report)) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} 
		catch (Exception re) 
		{
			logger.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		
		return oaMsg;
	}

	/**
	 * 查询库容状态图
	 * @Title:findChartDetail
	 * @Description:
	 * @param report
	 * @return
	 * @see 
	 */
	@SuppressWarnings({ "unchecked", "null", "rawtypes" })
	@Override
	public OaMsg findChartDetail(ReportDto report) 
	{
		OaMsg oaMsg = new OaMsg();
		try 
		{
			List list = null;
			List<Map<String, Object>> data;
			for(int a=0;a<1;a++)
			{
				report.setProductId(3);
				data = chartDao.findChartDetail(report);
				if(data != null && data.size() > 0)
				{
					for(int i=0;i<data.size();i++)
					{
						Map<String,Object> map= (Map<String, Object>) data.get(0);
						list.add(map.get("shipNum"));
					}
				}
			}
			
			oaMsg.getData().addAll(list);
			
		} 
		catch (OAException e) 
		{
			e.printStackTrace();
		}
		
		return oaMsg;
	}

}
