/**
 * 
 * @Project:sop
 * @Title:ExportDaoImpl.java
 * @Package:com.skycloud.oa.report.dao.impl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月13日 上午11:24:43
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.report.dao.ExportDao;
import com.skycloud.oa.report.dto.ExportDto;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.utils.DateUtils;
import com.skycloud.oa.utils.Common;

/**
 * <p>导出Excel数据源</p>
 * @ClassName:ExportDaoImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月13日 上午11:24:43
 * 
 */
@Repository
public class ExportDaoImpl extends BaseDaoImpl implements ExportDao 
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(ExportDaoImpl.class);

	/**
	 * 查询码头进出港统计表(进港信息)
	 * @Title:findInDock
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findInDock(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(DISTINCT a.id) shipNum,");
		//查询罐检量
		if(!Common.isNull(export.getPlanTank()) && export.getPlanTank() == 1)
		{
			sql.append("IFNULL(ROUND(SUM(d.goodsTank),3),0) totalNum ");
		}
		//查询计划量
		else if(!Common.isNull(export.getType()) && export.getType() == 1)
		{
			sql.append("IFNULL(ROUND(SUM(d.goodsPlan),3),0) totalNum ");
		}else{
			sql.append("IFNULL(ROUND(SUM(d.goodsTank),3),0) totalNum ");
		}
		sql.append("FROM t_pcs_arrival a LEFT JOIN t_pcs_cargo d ON a.id = d.arrivalId ");
		sql.append("LEFT JOIN t_pcs_product e ON d.productId = e.id ");
		sql.append("LEFT JOIN t_pcs_berth_program h ON a.id = h.arrivalId ");
		sql.append("LEFT JOIN t_pcs_berth i ON h.berthId = i.id WHERE 1=1 and (SELECT g.`status` from t_pcs_work g where g.arrivalId=a.id AND g.productId = d.productId LIMIT 0,1)=9 ");
		//进港-入港
		if(!Common.isNull(export.getType()) && export.getType() == 0)
		{
			sql.append("and a.type = 1  ");
		}
		//进港-通过
		else if(!Common.isNull(export.getType()) && export.getType() == 1)
		{
			sql.append("and a.type = 3  ");
		}
		//统计月份
		if(export.getStartTime()!=null)
		{
			sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '").append(export.getStartTime()).append("' ");
			sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '").append(export.getEndTime()).append("' ");
		}
		//货品ID
		if(!Common.isNull(export.getProductId()) && export.getProductId() != 0)
		{
			sql.append("AND e.type=").append(export.getProductId()).append(" ");
		}
		//泊位ID
		if(!Common.isNull(export.getBerthId()))
		{
			sql.append("AND i.id =").append(export.getBerthId()).append(" ");
		}
		//客户编码
		if(!Common.isNull(export.getClientId()) && export.getClientId() != 0)
		{
			sql.append(" and d.clientId =").append(export.getClientId()).append(" ");
		}
		//外贸
		if(!Common.isNull(export.getInOut()) && export.getInOut() == 1)
		{
			sql.append(" and ( d.taxType = 2 or d.taxType=3) ");
		}
		
		logger.info("查询码头进出港统计表(进港信息)："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 查询码头进出港统计表(出港信息)
	 * @Title:findOutnDock
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findOutnDock(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(DISTINCT a.id) shipNum");
		//出港-出港
				if( export.getType() == 0)
				{
		     sql.append(",IFNULL(ROUND(SUM(c.actualNum),3),0) totalNum ");
				}
				//出港-通过
				else if( export.getType() == 1)
				{
					sql.append(",IFNULL(ROUND(SUM(n.goodsTotal),3),0) totalNum ");
				}
		sql.append("FROM t_pcs_arrival a ");
		if( export.getType() == 0)
		{
			sql.append("LEFT JOIN t_pcs_goodslog c ON c.batchId = a.id and c.type=5 and c.deliverType=2 ");
		}
		else if( export.getType() == 1)
		{
			sql.append(" LEFT JOIN t_pcs_arrival_plan n ON a.id = n.arrivalId ");
		}
		sql.append("LEFT JOIN t_pcs_product p ON a.productId = p.id ");
		sql.append("LEFT JOIN t_pcs_berth b ON b.id = a.berthId ");
		if(export.getType()==0&&export.getInOut()==1)
		{
		sql.append(",(select w.clientId,w.tradeType,w.arrivalId from t_pcs_arrival_plan w  GROUP BY w.arrivalId) n ");
		}
		sql.append("WHERE a.`status` =54 ");
		//出港-出港
		if(!Common.isNull(export.getType()) && export.getType() == 0)
		{
			sql.append("and a.type = 2 ");
			if(export.getInOut()==1){
				sql.append("and n.arrivalId =a.id ");
			}
		}
		//出港-通过
		else if(!Common.isNull(export.getType()) && export.getType() == 1)
		{
			sql.append("and a.type = 5  ");
		}
		//统计月份
		if(export.getStartTime()!=null)
		{
			sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >='").append(export.getStartTime()).append("' ");
			sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(export.getEndTime()).append("' ");
		}
		//货品ID
		if(!Common.isNull(export.getProductId()) && export.getProductId() != 0)
		{
			sql.append("AND p.type =").append(export.getProductId()).append(" ");
		}
		//泊位ID
		if(!Common.isNull(export.getBerthId()))
		{
			sql.append("AND b.id =").append(export.getBerthId()).append(" ");
		}
		//客户编码
		if(!Common.isNull(export.getClientId()) && export.getClientId() != 0)
		{
			sql.append(" and n.clientId =").append(export.getClientId()).append(" ");
		}
		//外贸
		if(!Common.isNull(export.getInOut()) && export.getInOut() == 1)
		{
			sql.append(" and (n.tradeType = 2 or n.tradeType=3) ");
		}
		
		logger.info("码头进出港统计表进港信息(出港信息)："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 查询码头进出港统计表(进港-1月到某月的累计)
	 * @Title:findClientInDock
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findAddInDock(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(DISTINCT a.id) shipNum, ");
		//查询罐检量
		if(!Common.isNull(export.getPlanTank()) && export.getPlanTank() == 1)
		{
			sql.append("IFNULL(ROUND(SUM(d.goodsTank),3),0) totalNum ");
		}
		//查询计划量
		else
		{
			sql.append("IFNULL(ROUND(SUM(d.goodsTank),3),0) totalNum ");
		}
		sql.append("FROM t_pcs_arrival a LEFT JOIN t_pcs_cargo d ON a.id = d.arrivalId ");
		sql.append("LEFT JOIN t_pcs_product e ON d.productId = e.id ");
		sql.append("LEFT JOIN t_pcs_berth_program h ON a.id = h.arrivalId ");
		sql.append("LEFT JOIN t_pcs_berth i ON h.berthId = i.id  ");
		sql.append("LEFT JOIN t_pcs_client c ON c.id = d.clientId WHERE ");
		sql.append("a.`status` > 1 AND a.`status` < 11 and (a.type = 1 OR a.type = 3)");
		//统计月份
		if(export.getStartTime()!=null)
		{
			sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '").append(export.getStartTime()).append("' ");
			
			if(!Common.isNull(export.getLastThis()) && export.getLastThis() == 1)
			{
				sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '").append(DateUtils.getLastDayOfLastMonth(export.getEndTime())).append("' ");
			}
			//如果统计日期是2015-11，这个是统计2015-01到2015-11累计量
			else
			{
				sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<= '").append(export.getEndTime()).append("' ");
			}
		}
		//泊位ID
		if(!Common.isNull(export.getBerthId()))
		{
			sql.append("AND i.id =").append(export.getBerthId()).append(" ");
		}
		//外贸
		if(!Common.isNull(export.getInOut()) && export.getInOut() == 1)
		{
			sql.append(" and ( d.taxType=2 or d.taxType=3) ");
		}
		
		logger.info("查询码头进出港统计表(进港-1月到某月的累计)："+sql.toString());
		
		return executeQuery(sql.toString());
	}
	
	/**
	 * 查询码头进出港统计表(出港-1月到某月的累计)
	 * @Title:findAddOutDock
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findAddOutDock(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(DISTINCT a.id) shipNum,IFNULL(ROUND(SUM(n.goodsTotal),3),0) totalNum ");
		sql.append("FROM t_pcs_arrival a LEFT JOIN t_pcs_arrival_plan n ON a.id = n.arrivalId ");
		sql.append("LEFT JOIN t_pcs_product p ON a.productId = p.id ");
		sql.append("LEFT JOIN t_pcs_berth b ON b.id = a.berthId ");
		sql.append("WHERE a.`status` >= 50 and (a.type = 2 OR a.type = 5) ");
		//统计月份
		if(!Common.isNull(export.getStatisMonth()))
		{
			sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '").append(export.getStartTime()).append("' ");
			//如果统计日期是2015-11，这个是统计2015-01到2015-10累计量
			if(!Common.isNull(export.getLastThis()) && export.getLastThis() == 1)
			{
				sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '").append(DateUtils.getLastDayOfLastMonth(export.getEndTime())).append("' ");
			}
			//如果统计日期是2015-11，这个是统计2015-01到2015-11累计量
			else
			{
				sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<= '").append(export.getEndTime()).append("' ");
			}
		}
		//泊位ID
		if(!Common.isNull(export.getBerthId()))
		{
			sql.append("AND b.id =").append(export.getBerthId()).append(" ");
		}
		//外贸
		if(!Common.isNull(export.getInOut()) && export.getInOut() == 1)
		{
			sql.append(" and (n.tradeType = 2 or n.tradeType=3) ");
		}
		
		logger.info("查询码头进出港统计表(出港-1月到某月的累计)："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 查询装车站发货统计表（A01-A48车位）
	 * @Title:findStation
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findStation(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ROUND(IFNULL(SUM(g.actualNum),0),3) totalAmount,");
		sql.append("COUNT(DISTINCT g.batchId) carNum FROM t_pcs_weighbridge w ");
		sql.append("LEFT JOIN t_pcs_goodslog g ON g.serial = w.serial ");
		sql.append("LEFT JOIN t_pcs_goods s ON s.id = g.goodsId ");
		sql.append("LEFT JOIN t_pcs_product p ON p.id = s.productId ");
		sql.append("LEFT JOIN t_pcs_product_type t ON t.`key` = p.type ");
		sql.append("WHERE g.deliverType = 1 ");
		//统计月份
		if(!Common.isNull(export.getStatisMonth()))
		{
			sql.append(" and FROM_UNIXTIME(g.createTime,'%Y-%m-%d') <= '").append(DateUtils.getLastDayOfMonth(export.getStatisMonth())).append("' ");
			//查询1-某月的累计
			if(!Common.isNull(export.getType()) && export.getType() == 1)
			{
				sql.append(" and FROM_UNIXTIME(g.createTime,'%Y-%m-%d') >= '").append(DateUtils.getLastDayOfLastMonth(export.getStatisMonth())).append("' ");
			}
			//查询1-某月的上月累计
			else if(!Common.isNull(export.getType()) && export.getType() == 2)
			{
				sql.append(" and FROM_UNIXTIME(g.createTime,'%Y-%m-%d') >= '").append(DateUtils.getFirstDay(export.getStatisMonth())).append("' ");
			}
			//查询某月的数据
			else
			{
				sql.append(" and FROM_UNIXTIME(g.createTime,'%Y-%m-%d') >= '").append(DateUtils.getFirstDayOfMonth(export.getStatisMonth())).append("' ");
			}
		}
		//发货口
		if(!Common.isNull(export.getInPort()) && !"0".equals(export.getInPort()))
		{
			sql.append(" and w.inPort = '").append(export.getInPort()).append("' ");
		}
		//货品编码
		if(!Common.isNull(export.getProductId()) && export.getProductId() != 0)
		{
			sql.append(" and t.`key` = ").append(export.getProductId()).append(" ");
		}
		
		logger.info("查询装车站发货统计表（A01-A48车位）："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 查询通过单位统计表（进货总量）
	 * @Title:findUnits
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findUnits(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ");
		//查询罐检量
		if(!Common.isNull(export.getPlanTank()) && export.getPlanTank() == 1)
		{
			sql.append("IFNULL(ROUND(SUM(c.goodsPlan),3),0) goodsAmount ");
		}
		//查询计划量
		else
		{
			sql.append("IFNULL(ROUND(SUM(c.goodsPlan),3),0) goodsAmount ");
		}
		sql.append(" FROM t_pcs_arrival a ");
		sql.append("LEFT JOIN t_pcs_cargo c ON a.id = c.arrivalId ");
		sql.append("LEFT JOIN t_pcs_goods s ON s.cargoId = c.id ");
		sql.append("LEFT JOIN t_pcs_product p ON p.id = c.productId LEFT JOIN t_pcs_product_type t ON t.`key` = p.type ");
		sql.append("WHERE (SELECT g.`status` from t_pcs_work g where g.arrivalId=a.id AND g.productId = c.productId LIMIT 0,1)=9 and a.type = 3 ");
		//统计月份
		if(!Common.isNull(export.getStatisMonth()))
		{
			//去年同期
			if(!Common.isNull(export.getLastThis()) && export.getLastThis() == 1)
			{
				sql.append(" and a.arrivalStartTime <= '").append(DateUtils.getThisDayOfLastYear(export.getStatisMonth())).append("' ");
				//去年吞吐量-累计
				if(!Common.isNull(export.getAddUp()) && export.getAddUp() == 1)
				{
					sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getFirstDayOfLastYear(export.getStatisMonth())).append("' ");
				}
				//去年吞吐量
				else
				{
					sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getThisFirstDayOfLastYear(export.getStatisMonth())).append("' ");
				}
			}
			//今年同期
			else
			{
				sql.append(" and a.arrivalStartTime <= '").append(DateUtils.getLastDayOfMonth(export.getStatisMonth())).append("' ");
				//今年吞吐量-累计
				if(!Common.isNull(export.getAddUp()) && export.getAddUp() == 1)
				{
					sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getFirstDay(export.getStatisMonth())).append("' ");
				}
				//今年吞吐量
				else
				{
					sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getFirstDayOfMonth(export.getStatisMonth())).append("' ");
				}
			}
		}
		//货品编码
		if(!Common.isNull(export.getProductId()) && export.getProductId() != 0)
		{
			sql.append(" and t.`key` = ").append(export.getProductId()).append(" ");
		}
		//客户编码
		if(!Common.isNull(export.getClientId()))
		{
			sql.append("AND c.clientId=").append(export.getClientId()).append(" ");
		}
		
		logger.info("查询通过单位统计表(进货总量)："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 查询通过单位统计表(发货总量)
	 * @Title:findOutUnits
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findOutUnits(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT IFNULL(SUM(ROUND(e.goodsTotal, 3)),0) goodsAmount");
		sql.append(" FROM t_pcs_arrival a ");
		sql.append("LEFT JOIN t_pcs_arrival_plan e ON e.arrivalId = a.id ");
		sql.append("LEFT JOIN t_pcs_goods s ON s.id = e.goodsId ");
		sql.append("LEFT JOIN t_pcs_product p ON p.id = a.productId  ");
		sql.append("LEFT JOIN t_pcs_product_type t ON t.`key` = p.type ");
		sql.append("  WHERE a.type = 5 and a.status=54 ");
		//统计月份
		if(!Common.isNull(export.getStatisMonth()))
		{
			//去年同期
			if(!Common.isNull(export.getLastThis()) && export.getLastThis() == 1)
			{
				sql.append(" and a.arrivalStartTime <= '").append(DateUtils.getThisDayOfLastYear(export.getStatisMonth())).append("' ");
				//去年吞吐量-累计
				if(!Common.isNull(export.getAddUp()) && export.getAddUp() == 1)
				{
					sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getFirstDayOfLastYear(export.getStatisMonth())).append("' ");
				}
				//去年吞吐量
				else
				{
					sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getThisFirstDayOfLastYear(export.getStatisMonth())).append("' ");
				}
			}
			//今年同期
			else
			{
				sql.append(" and a.arrivalStartTime <= '").append(DateUtils.getLastDayOfMonth(export.getStatisMonth())).append("' ");
				//今年吞吐量-累计
				if(!Common.isNull(export.getAddUp()) && export.getAddUp() == 1)
				{
					sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getFirstDay(export.getStatisMonth())).append("' ");
				}
				//今年吞吐量
				else
				{
					sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getFirstDayOfMonth(export.getStatisMonth())).append("' ");
				}
			}
		}
		//货品编码
		if(!Common.isNull(export.getProductId()) && export.getProductId() != 0)
		{
			sql.append(" and t.`key` = ").append(export.getProductId()).append(" ");
		}
		//客户编码
		if(!Common.isNull(export.getClientId()))
		{
			sql.append("AND e.clientId=").append(export.getClientId()).append(" ");
		}
		
		logger.info("查询通过单位统计表(发货总量)："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 查询仓储进出存汇总表
	 * @Title:findInOut
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findInOut(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT IFNULL(ROUND(SUM(s.goodsTotal),3),0) totalAmount,");
		sql.append("IFNULL(ROUND(SUM(s.goodsIn),3),0) inAmount,IFNULL(ROUND(SUM(s.goodsOut),3),0) outAmount, ");
		sql.append("IFNULL(ROUND(SUM(s.goodsCurrent),3),0) curAmount ");
		sql.append("FROM t_pcs_goods s LEFT JOIN t_pcs_product p ON s.productId = p.id ");
		sql.append("WHERE 1=1 ");
		//统计月份
		if(!Common.isNull(export.getStatisMonth()))
		{
			sql.append(" and s.createTime <= '").append(DateUtils.getLastDayOfMonth(export.getStatisMonth())).append("' ");
			sql.append(" and s.createTime >= '").append(DateUtils.getFirstDayOfMonth(export.getStatisMonth())).append("' ");
		}
		//货品编码
		if(!Common.isNull(export.getProductId()) && export.getProductId() != 0)
		{
			sql.append(" and p.id = ").append(export.getProductId()).append(" ");
		}
		
		logger.info("查询仓储进出存汇总表："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 查询储罐周转率
	 * @Title:findTurnRate
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public Map<String, Object> findTurnRate(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
			sql.append(" SELECT SUM(ROUND(b.goodsPlan,3)) totalAmount ")
			.append(" FROM t_pcs_arrival a ")
			.append(" LEFT JOIN t_pcs_cargo b ON a.id=b.arrivalId ")
			.append(" LEFT JOIN t_pcs_product c ON c.id=b.productId ")
			.append(" WHERE  a.type=1 and (select d.status from t_pcs_work d where d.arrivalId=a.id limit 0,1 )=9 ");
			 if(!Common.isNull(export.getProductId())){
				 sql.append(" and c.type=").append(export.getProductId());
			 }
			 if(!Common.isNull(export.getTaxTypes())){
				 sql.append(" and b.taxType in (").append(export.getTaxTypes()).append(")");
			 }
			 if(!Common.isNull(export.getStorageType())){
				 if(export.getStorageType()==3){
					 sql.append(" and b.storageType=3");
				 }else{
					 sql.append(" and b.storageType!=3");
				 }
			 }
			 if(!Common.isNull(export.getNoClientId())){
				 sql.append(" and b.clientId not in (").append(export.getNoClientId()).append(" )");
			 }
			 if(!Common.isNull(export.getYesClientId())){
				 sql.append(" and b.clientId in (").append(export.getYesClientId()).append(" )");
			 }
			 if(!Common.isNull(export.getStartTime())){
				 sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')>='").append(export.getStartTime()).append("'");
			 }
			 if(!Common.isNull(export.getEndTime())){
				 sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(export.getEndTime()).append("'");
			 }
		return executeQueryOne(sql.toString());
	}

	/**
	 * 查询储罐周转率-最大可储存量（吨）
	 * @Title:findMaxTurnRate
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> findMaxTurnRate(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT (CASE WHEN ROUND(SUM(IFNULL(k.capacityTotal,0)),3) IS NULL THEN 0 ELSE ROUND(SUM(IFNULL(k.capacityTotal,0)),3) END) tankTotal ");
		sql.append("FROM t_pcs_arrival a LEFT JOIN t_pcs_cargo c ON a.id = c.arrivalId LEFT JOIN t_pcs_goods s ON c.id = s.cargoId LEFT JOIN t_pcs_tank k ON s.tankId = k.id ");
		sql.append("LEFT JOIN t_pcs_product p ON p.id = s.productId LEFT JOIN t_pcs_product_type t ON t.`key` = p.type ");
		sql.append("WHERE 1=1 ");
		//统计月份
		if(!Common.isNull(export.getStatisMonth()))
		{
			sql.append(" and a.arrivalStartTime <= '").append(DateUtils.getLastDayOfMonth(export.getStatisMonth())).append("' ");
			sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getFirstDayOfMonth(export.getStatisMonth())).append("' ");
		}
		//货品编码
		if(!Common.isNull(export.getProductId()) && export.getProductId() != 0)
		{
			sql.append(" and t.`key` = ").append(export.getProductId()).append(" ");
		}
		//贸易类型
		if(!Common.isNull(export.getType()) && export.getType() != 0)
		{
			sql.append(" and c.taxType = ").append(export.getType()).append(" ");
		}
		//包罐
		if(!Common.isNull(export.getTank()) && export.getTank() == 1)
		{
			sql.append(" and c.storageType = 3 ");
		}
		//客户ID
		if(!Common.isNull(export.getClientId()) && export.getClientId() != 0)
		{
			sql.append(" and c.clientId = ").append(export.getClientId()).append(" ");
		}
		
		logger.info("查询储罐周转率-最大可储存量（吨）："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 吞吐量统计表-进货总量
	 * @Title:queryInThrough
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public Map<String, Object> queryInThrough(ExportDto export) throws OAException 
	{   
		Map<String, Object> map=new HashMap<String,Object>();
		
		 StringBuilder sql=new StringBuilder();
		 StringBuilder sql1=new StringBuilder();
		 
		 sql.append(" SELECT COUNT(DISTINCT a.id) shipNum  ")
		 .append(" from t_pcs_arrival a ")
		 .append(" LEFT JOIN t_pcs_cargo b ON a.id=b.arrivalId ")
		 .append(" LEFT JOIN t_pcs_product e ON e.id=b.productId ")
		 .append(" where 1=1  AND a.id not in (0,1,2) and b.goodsPlan <> 0 ")
		 .append(" and (select productId from t_pcs_work where arrivalId=a.id order by productId desc limit 0,1)=b.productId ")
		 .append(" and (SELECT status from t_pcs_work  where arrivalId=a.id AND productId =b.productId LIMIT 0,1)>2 ");
		  //入库或入库通过
			if(!Common.isNull(export.getType()) && export.getType() == 1){
				sql.append(" and a.type = 1 ");
			}else{
				sql.append(" and a.type = 3 ");
			}
			
			if(export.getStartTime()!=null){ 
				if(export.getAddUp()==1){
					sql.append("  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '"+export.getStartTime().substring(0, 4)+"-01-01'  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '"+export.getEndTime()+"'");
				}else{
					sql.append("  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '"+export.getStartTime()+"'  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '"+export.getEndTime()+"'");
				}
			}
			
			if(!Common.isNull(export.getProductId())){
			 sql.append(" AND e.type= "+export.getProductId());
			}
			
			if(!Common.isNull(export.getClientId())){
				sql.append(" and b.clientId = "+export.getClientId());
			}
				
				
				
		sql1.append(" SELECT  IFNULL( ROUND(SUM(b.goodsPlan), 3),0) totalNum ")
			 .append(" from t_pcs_arrival a ")
			 .append(" LEFT JOIN t_pcs_cargo b ON a.id=b.arrivalId ")
			 .append(" LEFT JOIN t_pcs_product e ON e.id=b.productId ")
			 .append(" where 1=1  AND a.id not in (0,1,2) and b.goodsPlan <> 0 ")
			 .append(" and (SELECT status from t_pcs_work  where arrivalId=a.id AND productId =b.productId LIMIT 0,1)>2 ");
		  //入库或入库通过
			if(!Common.isNull(export.getType()) && export.getType() == 1){
				sql1.append(" and a.type = 1 ");
			}else{
				sql1.append(" and a.type = 3 ");
			}
			
			if(export.getStartTime()!=null){ 
				if(export.getAddUp()==1){
					sql1.append("  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '"+export.getStartTime().substring(0, 4)+"-01-01'  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '"+export.getEndTime()+"'");
				}else{
					sql1.append("  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '"+export.getStartTime()+"'  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '"+export.getEndTime()+"'");
				}
			}
			
			if(!Common.isNull(export.getProductId())){
			 sql1.append(" AND e.type= "+export.getProductId());
			}
			
			if(!Common.isNull(export.getClientId())){
				sql1.append(" and b.clientId = "+export.getClientId());
			}
				logger.info("吞吐量统计表-接卸：" + sql.toString());
				map.putAll(executeQueryOne(sql.toString()));
				map.putAll(executeQueryOne(sql1.toString()));
		return map;
	}

	/**
	 * 吞吐量统计表-出货总量
	 * @Title:queryOutThrough
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public Map<String, Object> queryOutThrough(ExportDto export) throws OAException 
	{
		StringBuilder sql=new StringBuilder();
		if(!Common.isNull(export.getType()) && export.getType() == 1){
			sql.append(" SELECT COUNT(DISTINCT a.batchId) shipNum, ROUND(SUM(a.deliverNum), 3) totalNum  ")
			.append(" from t_pcs_goodslog a  ,t_pcs_goods b  left join t_pcs_product f on f.id=b.productId  ")
			.append(" ,t_pcs_arrival c,t_pcs_ship_ref e 	WHERE 1=1  AND a.goodsId=b.id 	and c.id=a.batchId ")
			.append(" and a.type=5  and c.`status`=54 and e.id=a.vehicleShipId AND e.refName != '转输' ")
			.append("  AND a.deliverType = 2 ");//and a.actualType=1
		// 统计月份
		if (export.getStartTime() != null) {
			//累计
			if (export.getAddUp() == 1) {
				sql.append("  AND DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d') >= '")
				.append( export.getStartTime().substring(0, 4) + "-01-01'")
				.append("  AND DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d') <= '")
				.append( export.getEndTime() + "'");
			} else {
				sql.append("  AND DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d') >= '")
				.append( export.getStartTime() + "'");
				sql.append("  AND DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d') <= '")
				.append( export.getEndTime() + "'");
			}
		}
		if (!Common.isNull(export.getProductId()) && export.getProductId() != 0) {
			sql.append("and f.type=").append(export.getProductId());
		}
			
		}else{
			sql.append(" SELECT COUNT(DISTINCT a.id) shipNum,ROUND(SUM(b.goodsTotal), 3) totalNum ")
				.append(" FROM t_pcs_arrival a ")
				.append(" LEFT JOIN t_pcs_arrival_plan b ON a.id=b.arrivalId ")
				.append(" LEFT JOIN t_pcs_product c ON c.id=a.productId ")
				.append(" LEFT JOIN t_pcs_ship_ref d ON d.id=a.shipRefId ")
				.append(" WHERE a.type=5 AND d.refName != '转输' and b.goodsTotal <> 0 ");
		if (export.getStartTime() != null) {
			//累计
			if (export.getAddUp() == 1) {
				sql.append("  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '")
				.append( export.getStartTime().substring(0, 4) + "-01-01'")
				.append("  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '")
				.append( export.getEndTime() + "'");
			} else {
				sql.append("  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '")
				.append( export.getStartTime() + "'");
				sql.append("  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '")
				.append( export.getEndTime() + "'");
			}
		}
		if (!Common.isNull(export.getProductId()) && export.getProductId() != 0) {
			sql.append("and c.type=").append(export.getProductId());
		}
		}
		return executeQueryOne(sql.toString());
	}

	/**
	 * 吞吐量统计表-灌装站发货总量（车发）
	 * @Title:queryOutCar
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public Map<String, Object> queryOutCar(ExportDto export) throws OAException 
	{  
		StringBuilder sql=new StringBuilder();
			sql.append(" SELECT COUNT(DISTINCT a.batchId) shipNum, ROUND(SUM(a.actualNum),3) totalNum ")
			.append(" from t_pcs_goodslog a ")
			.append(" LEFT JOIN t_pcs_goods b ON a.goodsId=b.id ")
			.append(" LEFT JOIN t_pcs_product c on c.id=b.productId ")
			.append(" LEFT JOIN t_pcs_product_type d on d.`key`=c.type ")
			.append(" WHERE a.type=5 and a.deliverType=1 AND a.actualType=1 ");
		if(export.getStartTime()!=null)
		{
			if(export.getAddUp()==1){
			sql.append(" AND FROM_UNIXTIME(a.createTime,'%Y-%m-%d') >= '").append(export.getStartTime().substring(0, 4)).append("-01-01'")
			.append(" AND FROM_UNIXTIME(a.createTime,'%Y-%m-%d') <= '").append(export.getEndTime()).append("'");
			}else{
				sql.append(" AND FROM_UNIXTIME(a.createTime,'%Y-%m-%d') >= '").append(export.getStartTime()).append("'")
				.append(" AND FROM_UNIXTIME(a.createTime,'%Y-%m-%d') <= '").append(export.getEndTime()).append("'");
			}
		}
		//货品编码
				if(!Common.isNull(export.getProductId()))
				{
					sql.append(" AND c.type=").append(export.getProductId());
				}
		logger.info("吞吐量统计表--（车发）："+sql.toString());
		return executeQueryOne(sql.toString());
	}

	/**
	 * 吞吐量统计表-转输（输入）
	 * @Title:queryInput
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public Map<String, Object> queryInput(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(DISTINCT a.id) shipNum, ");
		sql.append("ROUND(sum(e.goodsPlan),3) totalNum ");
		sql.append("from t_pcs_arrival a LEFT JOIN t_pcs_cargo e on e.arrivalId=a.id left join t_pcs_product f on f.id=e.productId , t_pcs_ship_ref c ");
		sql.append("WHERE a.shipRefId=c.id and a.type=1 and c.refName='转输' ");
		//统计月份
		if(export.getStartTime()!=null)
		{
			if(export.getAddUp()==1){
				sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '"+export.getStartTime().substring(0, 4)+"-01-01'");
				sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '"+export.getEndTime()+"'");
			}else{
				sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '"+export.getStartTime()+"'");
				sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '"+export.getEndTime()+"'");
			}
			
		}
		//货品编码
		if(!Common.isNull(export.getProductId()) && export.getProductId() != 0)
		{
			sql.append(" and f.type = ").append(export.getProductId()).append(" ");
		}
		
		logger.info("吞吐量统计表-转输（输入）："+sql.toString());
		
		return executeQueryOne(sql.toString());
	}

	/**
	 * 吞吐量统计表-转输（输出）
	 * @Title:queryOutput
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public Map<String, Object> queryOutput(ExportDto export) throws OAException 
	{
		String sql="SELECT  ROUND(SUM(a.deliverNum), 3 ) totalNum, COUNT(DISTINCT a.batchId) shipNum "
		+" from t_pcs_goodslog a "
		+" LEFT JOIN t_pcs_goods b on a.goodsId=b.id "
		+" LEFT JOIN t_pcs_arrival e on e.id=a.batchId "
		+" LEFT JOIN t_pcs_ship_ref f on f.id=a.vehicleShipId "
		+ " LEFT JOIN t_pcs_product g on g.id=b.productId "
		+" WHERE a.type=5 and a.deliverType=3 and a.actualType=1 and f.refName='转输' ";
		//统计月份
				if(export.getStartTime()!=null)
				{
					if(export.getAddUp()==1){
						sql+="  AND DATE_FORMAT(e.arrivalStartTime,'%Y-%m-%d') >= '"+export.getStartTime().substring(0, 4)+"-01-01'";
						sql+="  AND DATE_FORMAT(e.arrivalStartTime,'%Y-%m-%d') <= '"+export.getEndTime()+"'";
					}else{
					sql+="  AND DATE_FORMAT(e.arrivalStartTime,'%Y-%m-%d') >= '"+export.getStartTime()+"'";
					sql+="  AND DATE_FORMAT(e.arrivalStartTime,'%Y-%m-%d') <= '"+export.getEndTime()+"'";
					}
				}
				//货品编码
				if(!Common.isNull(export.getProductId()) && export.getProductId() != 0)
				{
					sql+="AND g.type="+export.getProductId();
				}
		logger.info("吞吐量统计表-转输（输出）："+sql.toString());
		
		return executeQueryOne(sql);
	}

	/**
	 * 仓储进出存汇总表-本期入库
	 * @Title:queryInStorage
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> queryInStorage(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT IFNULL(ROUND(SUM(d.goodsInPass), 3),0) inAmount ");
		sql.append("FROM t_pcs_arrival a ");
		sql.append("LEFT JOIN t_pcs_cargo d ON a.id = d.arrivalId   ");
		sql.append("LEFT JOIN t_pcs_product e ON d.productId = e.id,t_pcs_ship s WHERE s.id = a.shipId AND a.type = 1 ");
		//统计月份
		if(!Common.isNull(export.getStatisMonth()))
		{
			sql.append(" and a.arrivalStartTime <= '").append(DateUtils.getLastDayOfMonth(export.getStatisMonth())).append("' ");
			sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getFirstDayOfMonth(export.getStatisMonth())).append("' ");
		}
		//货品编码
		if(!Common.isNull(export.getProductId()) && export.getProductId() != 0)
		{
			sql.append(" and e.type = ").append(export.getProductId()).append(" ");
		}
		
		logger.info("仓储进出存汇总表-本期入库："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 仓储进出存汇总表-本期出库
	 * @Title:queryOutStorage
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> queryOutStorage(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT IFNULL(ROUND(SUM(g.actualNum), 3),0) outAmount FROM t_pcs_goodslog g ");
		sql.append("LEFT JOIN t_pcs_goods s ON s.id = g.goodsId LEFT JOIN t_pcs_product t ON t.id = s.productId ");
		sql.append("WHERE g.type = 5  ");
		//统计月份
		if(!Common.isNull(export.getStatisMonth()))
		{
			sql.append(" and FROM_UNIXTIME(g.createTime,'%Y-%m') = '").append(export.getStatisMonth()).append("' ");
		}
		//货品编码
		if(!Common.isNull(export.getProductId()))
		{
			sql.append(" and t.type = ").append(export.getProductId()).append(" ");
		}
		
		logger.info("仓储进出存汇总表-本期出库："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	/**
	 * 仓储进出存汇总表-期末结存
	 * @Title:queryCurrentStorage
	 * @Description:
	 * @param export
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> queryCurrentStorage(ExportDto export) throws OAException 
	{
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT ROUND(SUM(a.goodsCurrent),3) curAmount ");
		sql.append("FROM (SELECT (SELECT(d.surplus + d.goodsSave) FROM t_pcs_goodslog d ");
		sql.append("WHERE 1=1  ");
		//统计月份
		if(!Common.isNull(export.getStatisMonth()))
		{
			sql.append(" and FROM_UNIXTIME(d.createTime, '%Y-%m') = '").append(export.getStatisMonth()).append("' ");
		}
		sql.append(" AND d.goodsId = a.goodsId ORDER BY d.createTime DESC LIMIT 0,1 ) goodsCurrent ");
		sql.append(" FROM t_pcs_goodslog a,t_pcs_goods b,t_pcs_product c WHERE a.goodsId = b.id ");
		sql.append(" AND b.productId = c.id ");
		//货品编码
		if(!Common.isNull(export.getProductId()))
		{
			sql.append(" and c.type = ").append(export.getProductId()).append(" ");
		}
		sql.append(" GROUP BY a.goodsId ) a");
		
		logger.info("仓储进出存汇总表-期末结存："+sql.toString());
		
		return executeQuery(sql.toString());
	}

	
	/**
	 * 
	 */
	@Override
	public List<Map<String, Object>> getForeignTradeInbound(ExportDto export) throws OAException {
		try{
			
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT COUNT(DISTINCT a.id) count,b.berthId ,sum(ROUND(d.goodsPlan,3)) num")
			.append(" from t_pcs_arrival a LEFT JOIN t_pcs_berth_program b ON a.id=b.arrivalId ")
			.append(" LEFT JOIN t_pcs_ship_ref c on c.id=a.shipRefId ")
			.append(" LEFT JOIN t_pcs_cargo d on d.arrivalId=a.id ")
			.append(" LEFT JOIN t_pcs_product e on e.id=d.productId ")
			.append(" LEFT JOIN t_pcs_client f on f.id=d.clientId ")
			.append(" WHERE 1=1 AND a.id not in(0,1,2)  AND c.refName!='转输'  and d.goodsPlan <> 0 AND ")
			.append("(SELECT g.`status` from t_pcs_work g where g.arrivalId=a.id AND g.productId = d.productId LIMIT 0,1) >2");
			
			if(!Common.isNull(export.getTaxType())&&export.getTaxType()==1)//是否外贸
				sql.append("  AND (d.taxType=2 or d.taxType=3 ) ");
			if(export.getIsPass()==0)//正常入库
				sql.append("  AND a.type=1 ");
			else if(export.getIsPass()==1){//通过单位
				sql.append("  AND a.type=3 ");
				if(export.getClientCode()!=null)//通过单位名称
					sql.append("  AND f.name='").append(export.getClientCode()).append("'");
			}
			if(export.getBerthIds()!=null)
				sql.append(" AND b.berthId in (").append(export.getBerthIds()).append(")");
			if(export.getProductId()!=0)
				sql.append(" AND e.type=").append(export.getProductId());
			if(export.getStartTime()!=null)
				sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')>='").append(export.getStartTime()).append("'");
			if(export.getEndTime()!=null)
				sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(export.getEndTime()).append("'");	
			
				sql.append("  GROUP BY b.berthId ORDER BY b.berthId ASC ");
			return executeQuery(sql.toString());
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getForeignTradeOutbound(ExportDto export) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT COUNT( DISTINCT  a.id) count, a.berthId, ");
			
			if(export.getIsPass()==0)//正常出库
				sql.append("sum(ROUND(b.deliverNum,3)) num");
			else if(export.getIsPass()==1)//通过单位
				sql.append(" sum(ROUND(b.goodsTotal,3)) num ");
			sql.append(" from t_pcs_arrival a  ");
			
			if(export.getIsPass()==0)//正常出库
				sql.append(" LEFT JOIN t_pcs_goodslog b on a.id=b.batchId and b.type=5 and b.actualType=1 AND b.deliverType = 2  ");
			else if(export.getIsPass()==1){//通过单位
				sql.append(" LEFT JOIN t_pcs_arrival_plan b on a.id=b.arrivalId ");
				sql.append(" LEFT JOIN t_pcs_client f on f.id=b.clientId ");
			}
			sql.append("  LEFT JOIN t_pcs_ship_ref c on c.id=a.shipRefId ");
			sql.append("  LEFT JOIN t_pcs_product d on d.id=a.productId ");
			sql.append(" where 1=1 AND c.refName !='转输' and a.status>50 ");
			
			if(export.getIsPass()==0){//正常出库
				sql.append("and a.type=2 ");
				if(!Common.isNull(export.getTaxType())&&export.getTaxType()==1){
					sql.append("  AND ( SELECT e.tradeType  from t_pcs_arrival_plan e where e.arrivalId=a.id LIMIT 0,1) in (2,3) ");
				}
			}else if(export.getIsPass()==1){//通过单位
				sql.append("and a.type=5 ");
				if(!Common.isNull(export.getTaxType())&&export.getTaxType()==1){
					sql.append(" and b.tradeType in(2,3) ");
				}
				if(export.getClientCode()!=null){
					sql.append("  AND f.name='").append(export.getClientCode()).append("'");
				}
			}
				if(export.getBerthIds()!=null)
					sql.append(" AND a.berthId in (").append(export.getBerthIds()).append(")");
				if(export.getProductId()!=0)
					sql.append(" AND d.type=").append(export.getProductId());
				if(export.getStartTime()!=null)
					sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')>='").append(export.getStartTime()).append("'");
				if(export.getEndTime()!=null)
					sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(export.getEndTime()).append("'");	
				
			sql.append(" GROUP BY a.berthId ORDER BY a.berthId ASC ");
			return executeQuery(sql.toString());
	}catch(RuntimeException e){
		e.printStackTrace();
	}
	return null;
	}

	@Override
	public Map<String, Object> getInboundPass(ExportDto export) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT IFNULL( ROUND(SUM(b.goodsPlan), 3),0) num ");
			sql.append(" from t_pcs_arrival a LEFT JOIN t_pcs_cargo b ON a.id=b.arrivalId" )
			.append(" LEFT JOIN t_pcs_client c ON c.id=b.clientId LEFT JOIN t_pcs_product d ON d.id=b.productId ")	
			.append(" where 1=1 AND (SELECT g.`status` from t_pcs_work g where g.arrivalId=a.id AND g.productId = b.productId LIMIT 0,1)=9 ");
			//入库  AND f.refName!='转输'
			if(export.getIsPass()==0){
				sql.append(" and a.type = 1 ");
			} else if(export.getIsPass()==1){//入库通过
				sql.append(" and a.type = 3 ");
			}
				if(export.getStartTime()!=null){   
						sql.append("  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '").append(export.getStartTime()).append("'");
					}
				if(export.getEndTime()!=null){
					sql.append("  AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '").append(export.getEndTime()).append("'");
				}
			    if(export.getProductId()!=0){
			    	sql.append(" AND d.type=").append(export.getProductId());
			    }
			    if(export.getClientCode()!=null){
			    	sql.append(" AND c.name='").append(export.getClientCode()).append("'");
			    }
			return executeQueryOne(sql.toString());
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> getOutboundPass(ExportDto export) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			//正常出库
			if(export.getIsPass()==0){
				sql.append("SELECT sum(ROUND(b.deliverNum,3)) num");
			}else if(export.getIsPass()==1){//通过单位
				sql.append("SELECT sum(ROUND(b.goodsTotal,3)) num ");
			}
			
			sql.append(" from t_pcs_arrival a  ");
			//正常出库
			if(export.getIsPass()==0){
				sql.append(" LEFT JOIN t_pcs_goodslog b on a.id=b.batchId and b.type=5 and b.actualType=1 AND b.deliverType = 2  ");
				sql.append(" LEFT JOIN t_pcs_goods g  on g.id=b.goodsId ");
				sql.append(" LEFT JOIN t_pcs_client f on f.id=g.clientId ");
			}else if(export.getIsPass()==1){//通过单位
				sql.append(" LEFT JOIN t_pcs_arrival_plan b on a.id=b.arrivalId ");
				sql.append(" LEFT JOIN t_pcs_client f on f.id=b.clientId ");
			}
			sql.append("  LEFT JOIN t_pcs_ship_ref c on c.id=a.shipRefId ");
			sql.append("  LEFT JOIN t_pcs_product d on d.id=a.productId ");
			sql.append(" where 1=1 AND c.refName !='转输' and a.status=54 ");
			//正常出库
			if(export.getIsPass()==0){
				sql.append("and a.type=2 ");				
			}else if(export.getIsPass()==1){//通过单位
				sql.append("and a.type=5  ");	
			}
			if(export.getClientCode()!=null){
				sql.append("  AND f.name='").append(export.getClientCode()).append("'");
			}
				if(export.getProductName()!=null){
				sql.append(" AND d.type=").append(export.getProductId());
				}
				
				if(export.getStartTime()!=null){
				sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')>='").append(export.getStartTime()).append("'");
				}
				
				if(export.getEndTime()!=null){
					sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(export.getEndTime()).append("'");	
				}
			return executeQueryOne(sql.toString());
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title queryInThroughOf2015
	 * @Descrption:TODO
	 * @param:@param exportLastYear
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月19日上午9:13:09
	 * @throws
	 */
	@Override
	public Map<String, Object> queryThroughtRateOf2015(ExportDto export) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT SUM(a.totalCount) shipNum,SUM(a.totalNum) totalNum from 2015_throught_rate a LEFT JOIN t_pcs_product b on a.productId=b.id where 1=1");
			if(!Common.isNull(export.getIsYearType())){
				sql.append(" and a.type=").append(export.getIsYearType());
			}
			if(!Common.isNull(export.getProductId())){
				sql.append(" and b.type=").append(export.getProductId());
			}
			if(!Common.isNull(export.getAddUp())&&export.getAddUp()==1){
				sql.append(" and a.month>=1");
			}else if(!Common.isNull(export.getStartTime())){
				sql.append(" and a.month>=").append(Integer.valueOf(export.getStartTime().substring(5, 7)));
			}
			if(!Common.isNull(export.getEndTime())){
				sql.append(" and a.month<=").append(Integer.valueOf(export.getEndTime().substring(5, 7)));
			}
			if(!Common.isNull(export.getClientId())){
				sql.append(" and a.clientId=").append(export.getClientId());
			}
		return executeQueryOne(sql.toString());	
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title getUnit2015
	 * @Descrption:TODO
	 * @param:@param nExportDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月19日上午10:47:50
	 * @throws
	 */
	@Override
	public Map<String, Object> getUnit2015(ExportDto nExportDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT SUM(a.totalNum) num  from 2015_export_unit a  LEFT JOIN t_pcs_product b on a.productId=b.id ")
			.append(" LEFT JOIN t_pcs_client c on c.id=a.clientId where 1=1");
			if(!Common.isNull(nExportDto.getProductId())){
				sql.append(" and b.type=").append(nExportDto.getProductId());
			}
			if(!Common.isNull(nExportDto.getIsYearType())){
				sql.append(" and a.type=").append(nExportDto.getIsYearType());
			}
			if(!Common.isNull(nExportDto.getClientCode())){
				sql.append(" and c.name ='").append(nExportDto.getClientCode()).append("'");
			}
			if(!Common.isNull(nExportDto.getStartTime())){
				sql.append(" and a.month>=").append(Integer.valueOf(nExportDto.getStartTime().substring(5, 7)));
			}
			if(!Common.isNull(nExportDto.getEndTime())){
				sql.append(" and a.month<=").append(Integer.valueOf(nExportDto.getEndTime().substring(5, 7)));
			}
			return executeQueryOne(sql.toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title getMaxNumOfTank
	 * @Descrption:TODO
	 * @param:@param export
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月20日下午3:00:12
	 * @throws
	 */
	@Override
	public Map<String, Object> getMaxNumOfTank(ExportDto export) throws OAException {
		try{
			
			StringBuilder sql=new StringBuilder();
			sql.append(" select SUM(a.capacityTotal) tankNum from t_his_tank_log a ")
			.append(" LEFT JOIN t_pcs_product b on a.goods=b.code ")
			.append(" LEFT JOIN t_pcs_tank c on c.key=a.tank")
			.append(" where 1=1 and a.isUse=1 ");
			if(!Common.isNull(export.getProductId())){
				sql.append(" and  b.type=").append(export.getProductId());
			}
			if(export.getTaxType()!=null){
				sql.append(" and a.tankType=").append(export.getTaxType());
			}
			if(!Common.isNull(export.getEndTime())){
				sql.append(" and  a.DTime=").append(DateUtils.getLongTime(export.getEndTime()+" 08:00:00"));
			}
			return executeQueryOne(sql.toString());
	} catch (RuntimeException e) {
		e.printStackTrace();
	}
	return null;
	}

	/**
	 * 获取拼装船数据
	 * @Title getAssemBlyShip
	 * @Descrption:TODO
	 * @param:@param exportDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月26日上午10:07:11
	 * @throws
	 */
	@Override
	public int getAssemBlyShip(ExportDto exportDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
				sql.append("select count(1) from(  SELECT DISTINCT a.id FROM t_pcs_arrival a ")
				.append(" LEFT JOIN t_pcs_transport_program b ON a.id=b.arrivalId ")
				.append(" LEFT JOIN t_pcs_product c ON c.id = b.productId ")
				.append(" LEFT JOIN t_pcs_cargo d ON d.arrivalId=a.id")
				.append(" LEFT JOIN t_pcs_ship_ref e ON e.id=a.shipRefId ");
				if(exportDto.getProductId()!=0){
					sql.append(",( SELECT DISTINCT a.id FROM t_pcs_arrival a ")
					.append("LEFT JOIN t_pcs_transport_program b ON a.id=b.arrivalId ")
					.append("LEFT JOIN t_pcs_product c ON c.id = b.productId ")
					.append(" LEFT JOIN t_pcs_cargo d ON d.arrivalId=a.id")
					.append(" WHERE a.type = 1 and b.type = 0 ")
					.append(" and c.type=").append(exportDto.getProductId());
					if(!Common.isNull(exportDto.getTaxType())&&exportDto.getTaxType()==1)//是否外贸
						sql.append("  AND (d.taxType=2 or d.taxType=3 ) ");
					if(exportDto.getStartTime()!=null)
						sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')>='").append(exportDto.getStartTime()).append("'");
					if(exportDto.getEndTime()!=null)
						sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(exportDto.getEndTime()).append("'");
					sql.append(") tp");
				}
				sql.append(" WHERE a.type=1 AND b.type=0 ");
				if(!Common.isNull(exportDto.getTaxType())&&exportDto.getTaxType()==1)//是否外贸
					sql.append("  AND (d.taxType=2 or d.taxType=3 ) ");
				if(exportDto.getProductId()!=0)
					sql.append(" and a.id=tp.id");
				if(exportDto.getStartTime()!=null)
					sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')>='").append(exportDto.getStartTime()).append("'");
				if(exportDto.getEndTime()!=null)
					sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(exportDto.getEndTime()).append("'");	
				sql.append(" GROUP BY a.id HAVING COUNT(DISTINCT c.type)>1 ) tb");
				return (int) getCount(sql.toString());
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
