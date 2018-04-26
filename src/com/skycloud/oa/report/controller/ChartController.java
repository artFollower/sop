/**
 * 
 * @Project:sop
 * @Title:ChartController.java
 * @Package:com.skycloud.oa.report.controller
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月28日 下午1:39:13
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.service.ChartService;
import com.skycloud.oa.utils.Common;

/**
 * <p>图形封装类</p>
 * @ClassName:ChartController
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月28日 下午1:39:13
 * 
 */
@Controller
@RequestMapping("/chart")
public class ChartController 
{
	/**
	 * chartService
	 */
	@Autowired
	private ChartService chartService;
	
	/**
	 * 查询库容状态统计图形数据
	 * @Title:ChartController
	 * @Description:
	 * @param report
	 * @return
	 * @Date:2015年10月28日 下午2:19:14
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="findChart")
	@ResponseBody
	public Object findChart(@ModelAttribute ReportDto report)
	{
		return chartService.findChart(report);
	}
	
	/**
	 * 
	 * @Title:ChartController
	 * @Description:
	 * @param report
	 * @return
	 * @Date:2015年12月5日 下午12:12:48
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value="findChartDetail")
	@ResponseBody
	public Object findChartDetail(@ModelAttribute ReportDto report)
	{
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		String date = year + "-" + (month+1<10?("0"+(month+1)):(month+1));
		if(Common.isNull(report.getStatisMonth()))
		{
			report.setStatisMonth(date);
		}
		
		return chartService.findChartDetail(report);
	}
}