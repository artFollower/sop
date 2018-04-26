/**
 * 
 * @Project:sop
 * @Title:ReportDaoImpl.java
 * @Package:com.skycloud.oa.report.dao.impl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月26日 上午10:27:56
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.report.dao.ReportDao;
import com.skycloud.oa.report.dto.ReportDto;
import com.skycloud.oa.report.utils.DateUtils;
import com.skycloud.oa.utils.Common;

/**
 * <p>报表公共查询模块</p>
 * @ClassName:ReportDaoImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月26日 上午10:27:56
 * 
 */
@Repository
public class ReportDaoImpl extends BaseDaoImpl implements ReportDao 
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(ReportDaoImpl.class);
	
	/**
	 * 报表查询公共方法
	 * @Title:list
	 * @Description:
	 * @param report
	 * @param pageView
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public List<Map<String, Object>> list(ReportDto report, PageView pageView) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		
		//判断报表对应的模块
		switch(report.getModule())
		{
		    //月仓储入库明细表
			case 1:
			sql.append(" SELECT DATE_FORMAT( a.arrivalStartTime, '%Y-%m-%d')  arrivalTime, ")
			.append(" s.`name` shipName,e.refName shipRefName,GROUP_CONCAT(DISTINCT t.`name`) productName,b.`name`  berthName, ")
			.append(" (CASE WHEN ISNULL(d.tankCodes) OR d.tankCodes='' THEN GROUP_CONCAT(DISTINCT k.code) ELSE d.tankCodes END) tankName, ")
			.append(" ROUND(IFNULL(sum(d.goodsTank), 0),3) goodsTotal ")
			.append(" FROM t_pcs_arrival a ")
			.append(" LEFT JOIN t_pcs_cargo c ON a.id = c.arrivalId ")
			.append(" LEFT JOIN t_pcs_goods d ON d.cargoId = c.id and (d.isPredict is null or d.isPredict!=1) and ISNULL(d.sourceGoodsId) ")
			.append(" LEFT JOIN t_pcs_tank k ON k.id = d.tankId ")
			.append(" LEFT JOIN t_pcs_product t ON t.id = c.productId ")
			.append(" LEFT JOIN t_pcs_berth_program m ON m.arrivalId = a.id ")
			.append(" LEFT JOIN t_pcs_berth b ON b.id = m.berthId ")
			.append(" LEFT JOIN t_pcs_ship s ON a.shipId = s.id ")
			.append(" LEFT JOIN t_pcs_ship_ref e ON a.shipRefId = e.id ")
			.append(" WHERE a.type = 1 ");
			if(report.getStartTime()!=null){
				sql.append(" AND   DATE_FORMAT( a.arrivalStartTime, '%Y-%m-%d') >='").append(report.getStartTime()).append("'");
			}
			if(report.getEndTime()!=null){
				sql.append(" AND  DATE_FORMAT( a.arrivalStartTime, '%Y-%m-%d') <='").append(report.getEndTime()).append("'");
			}
			sql.append(" AND (SELECT status from t_pcs_work where arrivalId=a.id LIMIT 0,1)=9 ")
			.append(" GROUP BY a.id,c.productId ORDER BY a.arrivalStartTime ASC ");
			if(pageView!=null&&pageView.getMaxresult()!=0){
				sql.append(" limit ").append(pageView.getStartRecord()).append(" , ").append(pageView.getMaxresult());
			}
			
			break;
			//长江石化码头货物进出港通过量统计
			
			case 2:
				sql.append(" SELECT DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') arrivalTime,CONCAT(c.`name`,'/',d.refName) shipName, ")
				.append(" e.berthId,f.`name` berthName,SUM(ROUND(b.goodsTotal,3)) goodsTotal,")
				.append("(CASE WHEN a.type=3 THEN '入港' ELSE '出港' END) arrivalType,IFNULL(a.description,'') description")
				.append(" from t_pcs_arrival a ")
				.append(" LEFT JOIN t_pcs_arrival_plan b on b.arrivalId=a.id ")
				.append(" LEFT JOIN t_pcs_ship c on c.id=a.shipId ")
				.append(" LEFT JOIN t_pcs_ship_ref d on d.id=a.shipRefId  ")
				.append(" LEFT JOIN t_pcs_berth_program e on e.arrivalId=a.id ")
				.append(" LEFT JOIN t_pcs_berth f on f.id=e.berthId ")
				.append(" where  (a.type=3 or a.type=5) ");
				if(!Common.isNull(report.getStartTime())){
					sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')>='").append(report.getStartTime()).append("'");
				}
				if(!Common.isNull(report.getEndTime())){
					sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(report.getEndTime()).append("'");
				}
				if(!Common.isNull(report.getClientId())){
					sql.append(" and b.clientId=").append(report.getClientId());
				}
                sql.append(" GROUP BY a.id order by a.arrivalStartTime asc");				
			break;
			//管道运输通过明细表
			case 3:
				
			sql.append(" SELECT FROM_UNIXTIME(b.arrivalTime) startTime, FROM_UNIXTIME(b.leaveTime) leaveTime, ")
			.append(" CONCAT(c.`name`,'/',d.refName) shipName,(CASE WHEN a.type!=2 THEN(SELECT SUM(ROUND(e.goodsPlan,3))  ")
			.append(" from t_pcs_cargo e where e.arrivalId=a.id) ELSE (SELECT SUM(ROUND(e.goodsTotal,3))  ")
			.append(" from t_pcs_arrival_plan e where e.arrivalId=a.id) END) goodsPlan, (SELECT f.name ")
			.append(" from t_pcs_arrival_plan e,t_pcs_client f where e.arrivalId=a.id and f.id=e.clientId limit 0,1) clientName ")
			.append(" ,GROUP_CONCAT(f.name) productName,h.`name` berthName ,a.description")
			.append(" from t_pcs_arrival a  ")
			.append(" LEFT JOIN t_pcs_work b on a.id=b.arrivalId ")
			.append(" LEFT JOIN t_pcs_ship c on a.shipId=c.id ")
			.append(" LEFT JOIN t_pcs_ship_ref d on a.shipRefId=d.id ")
			.append(" LEFT JOIN t_pcs_product f on f.id=b.productId ")
			.append(" LEFT JOIN t_pcs_berth_program g on g.arrivalId=a.id ")
			.append(" LEFT JOIN t_pcs_berth h on h.id=g.berthId ")
			.append(" where (a.type=3 OR (a.type in(1,2) and f.type in (17,18,19))) ")
			.append(" AND a.`status`!=44 ")
			.append(" AND d.refName!='转输' ");
			if(!Common.isNull(report.getStartTime())){
				sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')>='").append(report.getStartTime()).append("'");
			}
			if(!Common.isNull(report.getEndTime())){
				sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(report.getEndTime()).append("'");
			}
			sql.append(" GROUP BY a.id ORDER BY a.arrivalStartTime ASC ");
				logger.info("管道运输通过明细表："+sql.toString());
				
			break;
			//年度仓储汇总表
			case 4:
				sql.append(" SELECT FROM_UNIXTIME(c.createTime,'%Y-%m') statisDate,");
				sql.append(" p.`name` as productName,");
				sql.append(" ROUND(SUM(g.goodsTotal),3) goodsTotal,");
				sql.append(" ROUND(SUM(g.goodsIn),3) goodsInPass,");
				sql.append(" ROUND(SUM(g.goodsOut),3) goodsOut,");
				sql.append(" ROUND(SUM(g.goodsTotal+g.goodsIn-g.goodsOut),3) goodsCurrent ");
				sql.append(" FROM t_pcs_goodslog c ");
				sql.append(" LEFT JOIN t_pcs_goods g ON g.id = c.goodsId ");
				sql.append(" LEFT JOIN t_pcs_product p ON g.productId = p.id ");
				sql.append(" WHERE c.createTime IS NOT NULL ");
				//统计年份
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and FROM_UNIXTIME(c.createTime,'%Y-%m-%d')<= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and FROM_UNIXTIME(c.createTime,'%Y-%m-%d') >= '").append(report.getEndTime()).append("' ");
				}
				//客户编码
				if(!Common.isNull(report.getProductId()) && report.getProductId() != 0)
				{
					sql.append(" and p.id = ").append(report.getProductId()).append(" ");
				}
				sql.append(" GROUP BY FROM_UNIXTIME(c.createTime,'%Y-%m') ");
				sql.append(" ORDER BY c.createTime desc ");
				
				logger.info("年度仓储汇总表："+sql.toString());
				
			break;
			//装车站发货统计表
			case 5:
				sql.append("select w.inPort,SUM(w.deliveryNum) weight,COUNT(w.inPort) amount ");
				sql.append("FROM t_pcs_weighbridge w LEFT JOIN t_pcs_goodslog g ON w.serial = g.serial ");
				sql.append(" WHERE w.inPort LIKE '%A%' ");
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and FROM_UNIXTIME(w.outTime,'%Y-%m-%d')<= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and FROM_UNIXTIME(w.outTime,'%Y-%m-%d') >= '").append(report.getEndTime()).append("' ");
				}
				sql.append("GROUP BY w.inPort ORDER BY w.inPort");
				
				logger.info("装车站发货统计表："+sql.toString());
				
			break;
			
			//装车站发货统计表
			case 6:
				sql.append("select  t.`name` AS productName,w.inPort,COUNT(w.inPort) amount,SUM(w.deliveryNum) weight,");
				sql.append("g.createTime FROM t_pcs_weighbridge w ");
				sql.append("LEFT JOIN t_pcs_goodslog g ON w.serial = g.serial ");
				sql.append("LEFT JOIN t_pcs_goods s ON s.id = g.goodsId ");
				sql.append("LEFT JOIN t_pcs_product t ON t.id = s.productId ");
				sql.append(" WHERE w.inPort LIKE '%A%' ");
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and FROM_UNIXTIME(w.outTime,'%Y-%m-%d')<= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and FROM_UNIXTIME(w.outTime,'%Y-%m-%d') >= '").append(report.getEndTime()).append("' ");
				}
				sql.append("GROUP BY w.inPort ORDER BY g.createTime ");
				
				logger.info("装车站发货统计表11111："+sql.toString());
				
			break;
			//码头进出港统计表
			case 7:
				sql.append("select * from(");
				sql.append("SELECT IFNULL(p.`name`,'') productName,b.`name` AS berthName,");
				sql.append("'入港' AS inOutType,ROUND(IFNULL(sum(c.goodsPlan), 0),3) AS deliverNum,");
				sql.append("COUNT(a.shipId) shipNum FROM t_pcs_arrival a LEFT JOIN t_pcs_cargo c ON a.id = c.arrivalId ");
				sql.append("LEFT JOIN t_pcs_goods d ON d.cargoId = c.id AND isnull(d.sourceGoodsId) AND isnull(d.rootGoodsId) ");
				sql.append("LEFT JOIN t_pcs_berth_program m ON m.arrivalId = a.id LEFT JOIN t_pcs_berth b ON b.id = m.berthId ");
				sql.append("LEFT JOIN t_pcs_product p ON p.id = c.productId WHERE a.type = 1 AND b.`name` IS NOT NULL ");
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '").append(report.getEndTime()).append("' ");
				}
				sql.append("GROUP BY a.id union all ");
				sql.append("SELECT IFNULL(p.`name`,'') productName,h.`name` berthName,'出港' AS inOutType,ROUND(IFNULL(sum(a.goodsTotal), 0),3) deliverNum,");
				sql.append("COUNT(a.shipId) shipNum FROM t_pcs_arrival c LEFT JOIN t_pcs_arrival_plan a ON a.arrivalId = c.id ");
				sql.append("LEFT JOIN t_pcs_berth_program m ON m.arrivalId = c.id LEFT JOIN t_pcs_berth h ON h.id = m.berthId ");
				sql.append("LEFT JOIN t_pcs_product p ON p.id = c.productId WHERE c.type = 2 AND a.shipId IS NOT NULL ");
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d')<= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d') >= '").append(report.getEndTime()).append("' ");
				}
				sql.append("GROUP BY c.id)w order by w.productName ");
				
				logger.info("码头进出港统计表："+sql.toString());
				
			break;
			//外贸进出港统计表
			case 8:
				sql.append("SELECT p.`name` AS productName,b.`name` AS berthName,");
				sql.append("(CASE WHEN a.type = 1 THEN '入港' ELSE '出港' END) AS inOutType,");
				sql.append("lg.deliverNum,COUNT(a.shipId) shipNum FROM t_pcs_arrival a ");
				sql.append("LEFT JOIN t_pcs_cargo c ON a.id = c.arrivalId ");
				sql.append("LEFT JOIN t_pcs_goods d ON d.cargoId = c.id ");
				sql.append("LEFT JOIN t_pcs_goodslog lg ON lg.goodsId = d.id ");
				sql.append("LEFT JOIN t_pcs_berth b ON b.id = a.berthId ");
				sql.append("LEFT JOIN t_pcs_product p ON p.id = a.productId ");
				sql.append("WHERE (a.type = 1 OR a.type = 2) AND p.`name` IS NOT NULL ");
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and FROM_UNIXTIME(a.createTime,'%Y-%m-%d')<= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and FROM_UNIXTIME(a.createTime,'%Y-%m-%d')>= '").append(report.getEndTime()).append("' ");
				}
				sql.append("GROUP BY b.id ORDER BY p.`name` ");
				
				logger.info("外贸进出港统计表："+sql.toString());
				
			break;
			//储罐周转率
			case 9:
				sql.append("SELECT IFNULL(p.`name`,'') AS productName,");
				sql.append("(CASE WHEN c.taxType = 1 THEN '内贸' WHEN c.taxType = 2 THEN '外贸' WHEN c.taxType = 3 THEN '保税' END) AS taxType,");
				sql.append("ROUND(IFNULL(k.capacityTotal, 0), 3) AS capacityTotal, ");
				sql.append("ROUND(IFNULL(s.goodsInPass, 0), 3) AS goodsInPass, ");
				sql.append("(CASE WHEN k.capacityTotal = 0 || k.capacityTotal IS NULL THEN 0 ELSE ROUND(s.goodsInPass/k.capacityTotal,2) END) AS rate ");
				sql.append("FROM t_pcs_cargo c ");
				sql.append("LEFT JOIN t_pcs_goods s ON c.id = s.cargoId ");
				sql.append("LEFT JOIN t_pcs_product p ON p.id = s.productId ");
				sql.append("LEFT JOIN t_pcs_tank k ON k.id = s.tankId ");
				sql.append("WHERE c.taxType != 0 AND p.`name` IS NOT NULL ");
				
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and FROM_UNIXTIME(s.createTime,'%Y-%m-%d')<= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and FROM_UNIXTIME(s.createTime,'%Y-%m-%d')>= '").append(report.getEndTime()).append("' ");
				}
				sql.append("ORDER BY s.createTime DESC ");
				
				logger.info("储罐周转率："+sql.toString());
				
			break;
			//通过单位统计表
			case 10:
				sql.append("select * from(");
				sql.append("SELECT e.`name` AS clientName,p.`name` productName,");
				sql.append("ifnull(k.capacityTotal,0) tankTotal,ROUND(IFNULL(sum(c.goodsPlan), 0),3) AS goodsTotal ");
				sql.append("FROM t_pcs_arrival a LEFT JOIN t_pcs_cargo c ON a.id = c.arrivalId ");
				sql.append("LEFT JOIN t_pcs_goods d ON d.cargoId = c.id AND isnull(d.sourceGoodsId) AND isnull(d.rootGoodsId) ");
				sql.append("LEFT JOIN t_pcs_product p ON p.id = c.productId LEFT JOIN t_pcs_client e ON e.id = c.clientId ");
				sql.append("LEFT JOIN t_pcs_tank k ON k.id = d.tankId WHERE a.type = 3 ");
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '").append(report.getEndTime()).append("' ");
				}
				
				sql.append("GROUP BY a.id union all ");
				sql.append("SELECT f.`name` clientName,p.`name` productName,ifnull(k.capacityTotal,0) tankTotal, ");
				sql.append("ROUND(IFNULL(sum(a.goodsTotal), 0),3) goodsTotal  ");
				sql.append("FROM t_pcs_arrival c LEFT JOIN t_pcs_arrival_plan a ON a.arrivalId = c.id ");
				sql.append("LEFT JOIN t_pcs_cargo o ON o.id = a.cargoId LEFT JOIN t_pcs_client f ON f.id = a.clientId ");
				sql.append("LEFT JOIN t_pcs_product p ON p.id = a.productId LEFT JOIN t_pcs_tank k ON k.id = a.tankCodes ");
				sql.append("WHERE c.type = 5 ");
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d') <= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d') >= '").append(report.getEndTime()).append("' ");
				}
				
				sql.append("GROUP BY c.id)a ");
				
				logger.info("通过单位统计表："+sql.toString());
				
			break;
			//吞吐量统计表
			case 11:
				sql.append("select * from(");
				sql.append("SELECT IFNULL(p.`name`,'') AS productName,");
				sql.append("ROUND(IFNULL(sum(c.goodsPlan), 0),3) AS goodsTotal,COUNT(a.shipId) shipCount ");
				sql.append("FROM t_pcs_arrival a LEFT JOIN t_pcs_cargo c ON a.id = c.arrivalId ");
				sql.append("LEFT JOIN t_pcs_goods d ON d.cargoId = c.id AND isnull(d.sourceGoodsId) AND isnull(d.rootGoodsId) ");
				sql.append("LEFT JOIN t_pcs_product p ON p.id = c.productId WHERE a.type = 1 AND p.`name` IS NOT NULL ");
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '").append(report.getEndTime()).append("' ");
				}
				sql.append("GROUP BY p.id ");
				sql.append("union all ");
				sql.append("SELECT IFNULL(p.`name`,'') AS productName,ROUND(IFNULL(sum(a.goodsTotal), 0),3) goodsTotal,");
				sql.append("COUNT(c.shipId) shipCount FROM t_pcs_arrival c ");
				sql.append("LEFT JOIN t_pcs_arrival_plan a ON a.arrivalId = c.id LEFT JOIN t_pcs_product p ON p.id = c.productId ");
				sql.append("WHERE c.type = 2 ");
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >= '").append(report.getEndTime()).append("' ");
				}
				sql.append("GROUP BY p.id ");
				sql.append("union all ");
				sql.append("SELECT p.`name` productName,SUM(ROUND(b.actualNum, 3)) goodsTotal,count(DISTINCT b.batchId) shipCount ");
				sql.append("FROM t_pcs_train a INNER JOIN t_pcs_goodslog b ON a.id = b.batchId LEFT JOIN t_pcs_product p ON p.id = a.productId ");
				sql.append("WHERE b.type = 5 AND b.deliverType = 1 AND b.actualType = 1 ");
				if(!Common.isNull(report.getStartTime()))
				{
					sql.append(" and DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d') <= '").append(report.getStartTime()).append("' ");
				}
				if(!Common.isNull(report.getEndTime()))
				{
				sql.append(" and DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d') >= '").append(report.getEndTime()).append("' ");
				}
				sql.append("GROUP BY p.id ");
				sql.append(")a order by a.productName ");
				
				logger.info("吞吐量统计表："+sql.toString());
			break;
		}
		
		return executeQuery(sql.toString());
	}

	/**
	 * 查询公共报表总记录数
	 * @Title:getCount
	 * @Description:
	 * @param report
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public int getCount(ReportDto report) throws OAException 
	{
		StringBuffer sql = new StringBuffer();
		switch(report.getModule())
		{
			case 1:
				sql.append("SELECT COUNT(1) from( SELECT 1")
				.append(" FROM t_pcs_arrival a ")
				.append(" LEFT JOIN t_pcs_cargo c ON a.id = c.arrivalId ")
				.append(" LEFT JOIN t_pcs_ship s ON a.shipId = s.id ")
				.append(" WHERE a.type = 1 ");
				if(report.getStartTime()!=null){
					sql.append(" AND   DATE_FORMAT( a.arrivalStartTime, '%Y-%m-%d') >='").append(report.getStartTime()).append("'");
				}
				if(report.getEndTime()!=null){
					sql.append(" AND   DATE_FORMAT( a.arrivalStartTime, '%Y-%m-%d') <='").append(report.getEndTime()).append("'");
				}
				sql.append(" AND (SELECT status from t_pcs_work where arrivalId=a.id LIMIT 0,1)=9 ")
				.append(" GROUP BY a.id,c.productId ) AA");
				logger.info("查询月仓储入库明细表总条数语句："+sql.toString());
			break;
			case 2:
				
				
				
				break;
		}
		return (int)getCount(sql.toString());
	}
	
	/**
	 * 
	 * @Title:ReportDaoImpl
	 * @Description:
	 * @param sql
	 * @param report
	 * @Date:2015年10月27日 下午2:08:45
	 * @return: void 
	 * @throws
	 */
	public void querySql(StringBuffer sql,ReportDto report)
	{
		if(!Common.isNull(report.getModule()) && report.getModule() == 1)
		{
			sql.append("FROM t_pcs_arrival a ");
			sql.append("LEFT JOIN t_pcs_cargo c ON a.id = c.arrivalId ");
			sql.append("LEFT JOIN t_pcs_goods d ON d.cargoId = c.id  ");
			sql.append("LEFT JOIN t_pcs_tank k ON k.id = d.tankId ");
			sql.append("LEFT JOIN t_pcs_product t ON t.id = c.productId ");
			sql.append("LEFT JOIN t_pcs_berth_program m ON m.arrivalId = a.id  ");
			sql.append("LEFT JOIN t_pcs_berth b ON b.id = m.berthId ");
			sql.append("LEFT JOIN t_pcs_ship s ON a.shipId = s.id ");
			sql.append("WHERE a.type=1  and a.status > 1 AND a.`status` < 11 AND m.`status` = 2 ");
			if(!Common.isNull(report.getStatisMonth()))
			{
				sql.append(" and a.arrivalStartTime <= '").append(DateUtils.getLastDayOfMonth(report.getStatisMonth())).append("' ");
				sql.append(" and a.arrivalStartTime >= '").append(DateUtils.getFirstDayOfMonth(report.getStatisMonth())).append("' ");
			}
			sql.append(" group by a.id ");
		}
	}

	@Override
	public List<Map<String, Object>> getYearPipeInboundData(ReportDto report) throws OAException {
		String sql="SELECT a.id ,b.code cargoCode,b.goodsPlan,b.clientId,c.name,b.productId,d.name productName,DATE_FORMAT(a.arrivalStartTime,'%m') date "
				+" from t_pcs_arrival a  "
				+" LEFT JOIN t_pcs_cargo b on a.id=b.arrivalId "
				+" LEFT JOIN t_pcs_client c on c.id=b.clientId "
				+" LEFT JOIN t_pcs_product d on d.id=b.productId "
				+" where b.id is not null ";
		  if(report.getProductId()!=17&&report.getProductId()!=18&&report.getProductId()!=19){
			  sql+=" and a.type=3 ";
		  }else{
			  sql+=" and  (a.type = 3 or a.type= 1) ";
		  } 
		
		   if(report.getClientId()!=0){
			   sql+=" and b.clientId="+report.getClientId();
		   }
		   if(report.getProductId()!=0){
			   sql+=" and d.type="+report.getProductId();
		   }
		   
		   if(report.getStatisYear()!=null){
			   sql+=" and a.arrivalStartTime>='"+report.getStatisYear()+"-01-01 00:00:00"+"'";
			   sql+=" and a.arrivalStartTime<='"+ report.getStatisYear()+"-12-31 00:00:00"+"'";
		   }
		   sql+=" order by a.arrivalStartTime asc";
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getYearPipeOutboundData(ReportDto report)
			throws OAException {
		String sql="SELECT a.id ,b.deliverNum goodsPlan ,a.productId ,c.name productName,b.ladingClientId ,c.name clientName ,DATE_FORMAT(a.arrivalStartTime,'%m') date "
					+" from t_pcs_arrival a "
					+" LEFT JOIN t_pcs_goodslog b on b.batchId=a.id and b.type=5 and b.deliverType!=1 "
					+" LEFT JOIN t_pcs_product c on c.id=a.productId "
					+" LEFT JOIN t_pcs_client d on b.ladingClientId=d.id "
					+" where b.id is not null and a.type=2 ";
		   if(report.getClientId()!=0){
			   sql+=" and (b.ladingClientId="+report.getClientId();
			   sql+=" or b.clientId="+report.getClientId()+")";
		   }
		   if(report.getProductId()!=0){
			   sql+=" and c.type="+report.getProductId();
		   }
		   if(report.getStatisYear()!=null){
			   sql+=" and a.arrivalStartTime>='"+report.getStatisYear()+"-01-01 00:00:00"+"'";
			   sql+=" and a.arrivalStartTime<='"+ report.getStatisYear()+"-12-31 00:00:00"+"'";
		   }
		   sql+=" order by a.arrivalStartTime asc";
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getMonthBerth(ReportDto report)
			throws OAException {
		StringBuilder sql=new StringBuilder();
		String timea="",timeb="";
		timea=report.getStartTime();
		timeb=report.getEndTime();
	sql.append(" SELECT SUM(ROUND(( ")
	.append(" (CASE WHEN FROM_UNIXTIME(v.leaveTime,'%Y-%m')>'").append(timeb)
	.append("' THEN UNIX_TIMESTAMP('").append(DateUtils.getLastDayOfMonth(timeb)+" 23:59:59")
	.append("') ELSE v.leaveTime END)-(CASE WHEN FROM_UNIXTIME(v.arrivalTime,'%Y-%m')<'")
	.append(timea)
	.append("' THEN UNIX_TIMESTAMP('").append(timea+"-01 00:00:00")
	.append("') ELSE v.arrivalTime END))/3600,2)) count, v.berthId,v.productName from view_berth_program v where 1=1")
	.append(" and ((FROM_UNIXTIME(v.arrivalTime,'%Y-%m')>='").append(timea).append("' AND FROM_UNIXTIME(v.arrivalTime,'%Y-%m')<='")
	.append(timeb).append("') or  (FROM_UNIXTIME(v.leaveTime,'%Y-%m')>='").append(timea)
   .append("' AND FROM_UNIXTIME(v.leaveTime,'%Y-%m')<='").append(timeb).append("')) ")
   .append(" GROUP BY v.berthId ,v.productName  order by v.berthId asc ");
     	return executeQuery(sql.toString());
	}

	@Override
	public List<Map<String, Object>> getSpecialMonthBerth(ReportDto report)
			throws OAException {
        StringBuilder sql=new StringBuilder();
        String timea="",timeb="";
        timea=report.getStartTime();
		timeb=report.getEndTime();
	sql.append(" SELECT SUM(ROUND(( ")
	.append(" (CASE WHEN FROM_UNIXTIME(v.leaveTime,'%Y-%m')>'").append(timeb)
	.append("' THEN UNIX_TIMESTAMP('").append(DateUtils.getLastDayOfMonth(timeb)+" 23:59:59")
	.append("') ELSE v.leaveTime END)-(CASE WHEN FROM_UNIXTIME(v.arrivalTime,'%Y-%m')<'")
	.append(timea)
	.append("' THEN UNIX_TIMESTAMP('").append(timea+"-01 00:00:00")
	.append("') ELSE v.arrivalTime END))/3600,3)) count, v.berthId,v.clientName from view_berth_program v where 1=1 AND (v.type=3 or v.type=5) AND productName='润滑油基础油' ")
	.append(" and ((FROM_UNIXTIME(v.arrivalTime,'%Y-%m')>='").append(timea).append("' AND FROM_UNIXTIME(v.arrivalTime,'%Y-%m')<='")
	.append(timeb).append("') or  (FROM_UNIXTIME(v.leaveTime,'%Y-%m')>='").append(timea)
   .append("' AND FROM_UNIXTIME(v.leaveTime,'%Y-%m')<='").append(timeb).append("')) ")
    .append(" GROUP BY v.berthId ,v.clientName order by v.berthId asc ");
     	return executeQuery(sql.toString());
	}

	@Override
	public Map<String, Object> getInboundArrival(ReportDto report)
			throws OAException {
		String sql="SELECT COUNT( DISTINCT  a.id) numf,sum(ROUND(e.goodsPlan,3)) nume "
				+ " from t_pcs_arrival a LEFT JOIN t_pcs_cargo e on e.arrivalId=a.id, t_pcs_ship c "
				+ " WHERE 1=1 and a.shipId=c.id ";
		 if(report.getType()!=null){
			 if(report.getType()==1){
				 sql+=" and (a.type=1 or a.type=3)  and c.name!='转输' and c.name!='ZHUANSHU' ";
			 }else if(report.getType()==2){
				 sql+=" and  a.type=3  and c.name!='转输' and c.name!='ZHUANSHU' ";
			 }else if(report.getType()==3){
				 sql+=" and a.type=1  and (c.name='转输' or c.name='ZHUANSHU') ";
			 }
		 }
		  if(report.getStatisMonth()!=null){
		   sql+=" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m')='"+report.getStatisYear()+"-"+report.getStatisMonth()+"'";
		  }else{
			  sql+=" and DATE_FORMAT(a.arrivalStartTime,'%Y')='"+report.getStatisYear()+"'";  
		  }
		return executeQueryOne(sql);
	}

	@Override
	public Map<String, Object> getOutbountArrival(ReportDto report)
			throws OAException {
		String sql="SELECT COUNT( DISTINCT  a.id) count,sum(ROUND(e.goodsTotal,3)) num "
				+ " from t_pcs_arrival a LEFT JOIN t_pcs_arrival_plan e on e.arrivalId=a.id, t_pcs_ship c "
				+ " WHERE 1=1 and a.shipId=c.id ";
				if(report.getType()!=null){
					 if(report.getType()==1){
						 sql+=" and (a.type=2 or a.type=5)  and c.name!='转输' and c.name!='ZHUANSHU' ";
					 }else if(report.getType()==2){
						 sql+=" and  a.type=5  and c.name!='转输' and c.name!='ZHUANSHU' ";
					 }else if(report.getType()==3){
						 sql+=" and a.type=2  and (c.name='转输' or c.name='ZHUANSHU') ";
					 }
				 }
				if(report.getStatisMonth()!=null){
					   sql+=" and DATE_FORMAT(a.arrivalStartTime,'%Y-%m')='"+report.getStatisYear()+"-"+report.getStatisMonth()+"'";
					  }else{
						  sql+=" and DATE_FORMAT(a.arrivalStartTime,'%Y')='"+report.getStatisYear()+"'";  
					  }
		return executeQueryOne(sql);
	}

	@Override
	public Map<String, Object> getTruckData(ReportDto report)
			throws OAException {
		String sql="SELECT count(DISTINCT a.batchId) count, SUM(ROUND(a.actualNum,3)) num  from t_pcs_goodslog a where a.type=5 and a.deliverType=1 and a.actualType=1 ";
		if(report.getStatisMonth()!=null){
			   sql+=" and FROM_UNIXTIME(a.createTime,'%Y-%m')='"+report.getStatisYear()+"-"+report.getStatisMonth()+"'";
			  }else{
			   sql+=" and FROM_UNIXTIME(a.createTime,'%Y')='"+report.getStatisYear()+"'";  
			  }
		return executeQueryOne(sql);
	}

	@Override
	public Map<String, Object> getInboundTankNum(ReportDto report) throws OAException {
		
		String sql="select a.id ,b.id transportId,b.productId,e.name productName,d.code tankName,a.arrivalStartTime,f.realAmount from t_pcs_arrival a "
					+" LEFT JOIN t_pcs_transport_program b on a.id=b.arrivalId  and b.type=0 "
					+" LEFT JOIN t_pcs_product e on e.id=b.productId "
					+" LEFT JOIN t_pcs_store c on c.transportId=b.id "
					+" LEFT JOIN t_pcs_tank d on d.id=c.tankId "
					+" LEFT JOIN t_pcs_tanklogstore f on f.tankId=c.tankId and f.arrivalId=a.id "
					+" where a.type=1 and b.id is not null and DATE_FORMAT(a.arrivalStartTime,'%Y')='2015'";
		           if(report.getProductName()!=null){
		        	   sql+=" and e.name='"+report.getProductName()+"'";
		           }         
		
		return null;
	}

	@Override
	public Map<String, Object> getOutboundTankNum(ReportDto report)
			throws OAException {
		// TODO Auto-generated method stub
		return null;
	}
    //获取储罐台账数据列表
	@Override
	public List<Map<String, Object>> getOutArrivalBook(ReportDto report, int start, int limit) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			try{
					sql.append("SELECT a.id arrivalId, b.id workId,")
					.append(" ( SELECT ROUND(SUM(g.deliverNum), 3) FROM")
					.append(" t_pcs_goodslog g WHERE g.batchId = a.id")
					.append(" AND (g.deliverType=2 OR g.deliverType=3) AND g.type = 5 ) totalNum, ")
					.append(" DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d %H:%i') arrivalTime, ")
					.append("CONCAT( w.name,'/',d.refName) shipName, a.shipRefId, a.shipId, e. NAME productName, ")
					.append(" a.productId, f.NAME berthName, h.id transportId, ")
					.append(" FROM_UNIXTIME(b.openPump,'%Y-%m-%d %H:%i') openPump, FROM_UNIXTIME(b.stopPump,'%Y-%m-%d %H:%i') stopPump,FROM_UNIXTIME(b.leaveTime,'%Y-%m-%d %H:%i') leaveTime, ")
					.append(" (CASE WHEN k.tubeStatus=1 THEN '充满' WHEN k.tubeStatus=1 THEN '局部空' ELSE '全空' END) tubeStatus, ")
					.append(" b.evaluate, b.`evaluateUser` evaluateUserName, ")
					.append(" (CASE WHEN a. STATUS = 54 THEN ( ")
					.append(" SELECT ROUND(SUM(g.actualNum), 3) FROM t_pcs_goodslog g ")
					.append(" WHERE g.batchId = a.id AND (g.deliverType=2 OR g.deliverType=3) AND g.type = 5 ")
					.append(" ) ELSE '' END ) amount, r.NAME user1, s.NAME user2, ")
					.append("(CASE WHEN a. STATUS = 54 THEN '数量已确认' ELSE '数量未确认' END) status, b.description comment ")
					.append(" FROM t_pcs_arrival a ")
					.append(" LEFT JOIN t_pcs_work b ON b.arrivalId = a.id ")
					.append(" LEFT JOIN t_pcs_ship w ON w.id = a.shipId ")
					.append(" LEFT JOIN t_pcs_ship_ref d ON d.id = a.shipRefId ")
					.append(" LEFT JOIN t_pcs_product e ON a.productId = e.id ")
					.append(" LEFT JOIN t_pcs_berth f ON f.id = a.berthId ")
					.append(" LEFT JOIN t_pcs_transport_program h ON h.arrivalId = a.id ")
					.append(" LEFT JOIN t_pcs_delivery_ship k ON k.transportId = h.id ")
					.append(" LEFT JOIN t_pcs_work_check p ON p.transportId = h.id ")
					.append(" AND p.checkType = 21 ")
					.append(" LEFT JOIN t_pcs_work_check q ON q.transportId = h.id ")
					.append(" AND q.checkType = 22 ")
					.append(" LEFT JOIN t_auth_user r ON r.id = p.checkUserId ")
					.append(" LEFT JOIN t_auth_user s ON s.id = q.checkUserId ")
					.append(" WHERE 1 = 1 AND a.type = 2 AND !ISNULL(b.id)");
					if(report.getStartTime()!=null)
					sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >='").append(report.getStartTime()).append("'");
					if(report.getEndTime()!=null)
					sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <='").append(report.getEndTime()).append("'");
					if(report.getShipName()!=null){
					sql.append(" AND( w.name like '%").append(report.getShipName()).append("%' or d.refName like '%")
					.append(report.getShipName()).append("%') ");
					}
					sql.append(" order by a.arrivalStartTime asc ");
					if(limit!=0){
						sql.append(" limit ").append(start).append(" , ").append(limit);
					}
					
			return	executeQuery(sql.toString());
			}catch(RuntimeException e){
				logger.error("dao获取储罐台账数据列表失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取储罐台账数据列表失败",e);
			}
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getOutArrivalBookCount(ReportDto report) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT count(1) ")
			.append(" FROM t_pcs_arrival a ")
			.append(" LEFT JOIN t_pcs_work b ON b.arrivalId = a.id ")
			.append(" LEFT JOIN t_pcs_ship w ON w.id = a.shipId ")
			.append(" LEFT JOIN t_pcs_ship_ref d ON d.id = a.shipRefId ")
			.append(" WHERE 1 = 1 AND a.type = 2 AND !ISNULL(b.id)");
			if(report.getStartTime()!=null)
			sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') >='").append(report.getStartTime()).append("'");
			if(report.getEndTime()!=null)
			sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') <='").append(report.getEndTime()).append("'");
			if(report.getShipName()!=null){
				sql.append(" AND( w.name like '%").append(report.getShipName()).append("%' or d.refName like '%")
				.append(report.getShipName()).append("%') ");
				}
			sql.append(" order by a.arrivalStartTime asc ");
			return (int) getCount(sql.toString());
		} catch (RuntimeException e) {
			 e.printStackTrace();
		}
		return 0;
	}

	@Override
	public Map<String, Object> getOutBoundPlace(Integer arrivalId) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT GROUP_CONCAT(DISTINCT a.flow) place ")
			.append(" FROM t_pcs_arrival_plan a where a.arrivalId=").append(arrivalId).append(" group by a.arrivalId");
			return executeProcedureOne(sql.toString());
		} catch (RuntimeException e) {
			 e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title getPumpShedRotation
	 * @Descrption:TODO
	 * @param:@param report
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月12日上午9:03:05
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getPumpShedRotation(ReportDto report) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("call proc_pumpShedrotationBySelect(UNIX_TIMESTAMP('").append(report.getStartTime()+" 00:00:00'),UNIX_TIMESTAMP('")
			.append(report.getEndTime()+" 23:59:59'));");
             return executeProcedure(sql.toString());			
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title getBerthDetailList
	 * @Descrption:TODO
	 * @param:@param report
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2017年1月3日下午5:35:00
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getBerthDetailList(ReportDto report,int start, int limit) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT v.*,FROM_UNIXTIME(v.arrivalTime) arrivalTimeStr,FROM_UNIXTIME(v.leaveTime) leaveTimeStr,CONCAT(c.name,'/',b.refName) shipName, ")
			.append(" ROUND(((CASE WHEN FROM_UNIXTIME(v.leaveTime, '%Y-%m') > '").append(report.getStartTime()).append("' THEN UNIX_TIMESTAMP('")
			.append(DateUtils.getLastDayOfMonth(report.getStartTime())).append(" 23:59:59') ")
			.append(" ELSE v.leaveTime END )-(CASE WHEN FROM_UNIXTIME(v.arrivalTime, '%Y-%m') < '").append(report.getStartTime()).append("' THEN ")
			.append(" UNIX_TIMESTAMP('").append(report.getStartTime()).append("-01 00:00:00') ELSE v.arrivalTime END ) ) / 3600, 2 ) timeCount ")
			 .append(" from view_berth_program  v  ")
			 .append(" LEFT JOIN t_pcs_arrival a ON a.id=v.arrivalId ")
			 .append(" LEFT JOIN t_pcs_ship_ref b ON b.id=a.shipRefId ")
			 .append(" LEFT JOIN t_pcs_ship c ON c.id=a.shipId");
			if(report.getProductId()!=0)
			 sql.append(" ,(SELECT n.name from t_pcs_product n where n.id=").append(report.getProductId()).append(" limit 0,1 ) m ");
			 sql.append(" where  ( FROM_UNIXTIME(v.leaveTime,'%Y-%m')='").append(report.getStartTime())
			 .append("' OR FROM_UNIXTIME(v.arrivalTime,'%Y-%m')='").append(report.getStartTime()).append("' ) ");
			  if(report.getBerthId()!=null)
				  sql.append(" AND v.berthId=").append(report.getBerthId());
			  if(report.getProductId()!=0)
				  sql.append(" AND v.productName=m.name ");
			 sql.append(" ORDER BY UNIX_TIMESTAMP(v.arrivalTime) ASC");
			if(limit!=0)
				sql.append(" limit ").append(start).append(" , ").append(limit);
             return executeQuery(sql.toString());			
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title getBerthDetailCount
	 * @Descrption:TODO
	 * @param:@param report
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2017年1月3日下午5:35:00
	 * @throws
	 */
	@Override
	public int getBerthDetailCount(ReportDto report) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
				sql.append(" SELECT count(1) from view_berth_program  v  ");
				if(report.getProductId()!=0)
				 sql.append(" ,(SELECT n.name from t_pcs_product n where n.id=").append(report.getProductId()).append(" limit 0,1 ) m ");
				 sql.append(" where  ( FROM_UNIXTIME(v.leaveTime,'%Y-%m')='").append(report.getStartTime())
				 .append("' OR FROM_UNIXTIME(v.arrivalTime,'%Y-%m')='").append(report.getStartTime()).append("' ) ");
				  if(report.getBerthId()!=null)
					  sql.append(" AND v.berthId=").append(report.getBerthId());
				  if(report.getProductId()!=0)
					  sql.append(" AND v.productName=m.name ");
             return (int) getCount(sql.toString());			
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
