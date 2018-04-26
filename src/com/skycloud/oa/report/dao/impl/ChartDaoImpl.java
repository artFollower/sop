/**
 * 
 * @Project:sop
 * @Title:ChartDaoImpl.java
 * @Package:com.skycloud.oa.report.dao.impl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月28日 下午1:36:57
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.report.dao.ChartDao;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.utils.DateUtils;
import com.skycloud.oa.utils.Common;

/**
 * <p>图形公用方法实现类</p>
 * @ClassName:ChartDaoImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月28日 下午1:36:57
 * 
 */
@Repository
public class ChartDaoImpl extends BaseDaoImpl implements ChartDao 
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(ChartDaoImpl.class);
	
	/**
	 * 查询库容状态统计图形数据
	 * @Title:findChart
	 * @Description:
	 * @param report
	 * @return List
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findChart(ReportDto report) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
			sql.append(" SELECT  SUM(a.materialWeight) hasTotalNum,SUM(a.capacityTotal) totalNum,FROM_UNIXTIME(a.DTime,'%Y-%m-%d') DTime,b.code tankCode,d.value productName ")
			.append(" from t_his_tank_log  a LEFT JOIN t_pcs_tank b ON a.tank=b.key ")
			.append(" LEFT JOIN t_pcs_product c ON a.goods=c.code ")
			.append(" LEFT JOIN t_pcs_product_type d on d.key=c.type ")
			.append(" where 1=1 ");
			if(!Common.isNull(report.getStartTime())&&!Common.isNull(report.getEndTime())){
				sql.append(" AND a.DTime in (").append(getEachTimeLong(report.getStartTime(), report.getEndTime())).append(")");
			}
			if(!Common.isNull(report.getTankKeys())){
				sql.append(" AND a.tank not in (").append(report.getTankKeys()).append(")");
			}
			sql.append(" GROUP BY a.DTime,c.type ");
		logger.info("查询库容状态统计图形数据："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	public String getEachTimeLong(String startTime,String endTime){
		Long timeLs=DateUtils.getLongTime(startTime+" 08:00:00");
		Long timeLe=DateUtils.getLongTime(endTime+" 08:00:00");
		StringBuilder time=new StringBuilder("-1");
		while(timeLs<=timeLe) {
			time.append(",").append(timeLs);
			timeLs=timeLs+86400;
		}
		return time.toString();
	}

	/**
	 * 查询库容状态图
	 * @Title:findChartDetail
	 * @Description:
	 * @param report
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findChartDetail(ReportDto report) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT FROM_UNIXTIME(g.createTime, '%Y-%m-%d') statisDate,");
		sql.append("p.`name` productName,ROUND(IFNULL(s.goodsTank, 0),3) productWeight ");
		sql.append("FROM t_pcs_goodslog g LEFT JOIN t_pcs_goods s ON s.id = g.goodsId ");
		sql.append("LEFT JOIN t_pcs_product p ON p.id = s.productId ");
		sql.append("where s.goodsTank <> 0 ");
		//统计月份
		if(!Common.isNull(report.getStatisMonth()))
		{
			sql.append(" and FROM_UNIXTIME(g.createTime,'%Y-%m-%d %H:%i:%s') >= '").append(DateUtils.getFirstDayOfMonth(report.getStatisMonth())).append("' ");
			sql.append(" and FROM_UNIXTIME(g.createTime,'%Y-%m-%d %H:%i:%s') <= '").append(DateUtils.getLastDayOfMonth(report.getStatisMonth())).append("' ");
		}
		//货品编码
		if(!Common.isNull(report.getProductId()) && report.getProductId() != 0)
		{
			sql.append(" and p.type = ").append(report.getProductId()).append(" ");
		}
		sql.append(" GROUP BY FROM_UNIXTIME(g.createTime,'%Y-%m-%d') ORDER BY g.createTime ");
		
		logger.info("查询库容状态统计图形数据："+sql.toString());
		
		return executeQuery(sql.toString());
	}
	
}