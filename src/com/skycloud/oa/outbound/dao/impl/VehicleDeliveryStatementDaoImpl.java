package com.skycloud.oa.outbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.VehicleDeliveryStatementDao;
import com.skycloud.oa.outbound.dto.VehicleDeliveryStatementDto;
import com.skycloud.oa.utils.Common;

/**
 * 
 * <p>台账管理---流量报表</p>
 * @ClassName:VehicleDeliveryStatementDaoImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午11:06:04
 *
 */
@Component
public class VehicleDeliveryStatementDaoImpl extends BaseDaoImpl implements VehicleDeliveryStatementDao 
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(VehicleDeliveryStatementDaoImpl.class);
	
	/**
	 * 查询车位发货日报表信息
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:59:54
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> queryParkDailyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("select r.*,p.name parkName from t_pcs_measure_record r") ;
		sb.append(" left join t_pcs_park p on r.parkId=p.id where 1=1  ") ;
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append(" and DATE_FORMAT(r.meterStartTime, '%Y-%m-%d')='"+vehicleDeliveryStatementDto.getDate()+"'") ;
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getParkId()))
		{
			sb.append(" and r.parkId="+vehicleDeliveryStatementDto.getParkId()) ;
		}
		if(!Common.empty(pageView))
		{
			sb.append(" limit "+pageView.getStartRecord()+","+pageView.getMaxresult()) ;
		}
		
		logger.info("查询车位发货日报表信息SQL语句："+sb.toString());
		
		return executeQuery(sb.toString());
	}
	
	/**
	 * 查询车位流量日报表
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:00:19
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> queryWeighDailyStatement(VehicleDeliveryStatementDto vsDto, PageView pageView) throws Exception 
	{
		     StringBuilder sql=new StringBuilder();
			sql.append(" select a.serial, FROM_UNIXTIME(a.createTime) createTime, FROM_UNIXTIME(a.invoiceTime) invoiceTime,")
			.append(" ROUND(a.actualNum,3) actualNum,ROUND(a.deliverNum,3) deliverNum,a.storageInfo,a.ladingEvidence, ")
			.append(" c.`name` productName,e.inPort,IFNULL(f.`code`,a.tankName) tankName,j.name clientName,")
			.append(" (CASE WHEN a.deliverType=1 THEN d.name ELSE CONCAT(g.refName,'/',FROM_UNIXTIME(a.createTime,'%y-%m-%d')) END) truckName")
			.append(" from t_pcs_goodslog a ")
			.append(" LEFT JOIN t_pcs_goods b on a.goodsId=b.id ")
			.append(" LEFT JOIN t_pcs_product c on c.id=b.productId ")
			.append(" LEFT JOIN t_pcs_truck d on d.id=a.vehicleShipId ")
			.append(" LEFT JOIN t_pcs_ship_ref g on g.id=a.vehicleShipId ")
			.append(" LEFT JOIN t_pcs_weighbridge e on e.serial=a.serial ")
			.append(" LEFT JOIN t_pcs_tank f on f.id=e.tankId ")
			.append(" LEFT JOIN t_pcs_client j ON j.id=b.clientId")
			.append(" WHERE   a.type=5 and a.actualType=1 and a.deliverType in(");
		        if(!Common.isNull(vsDto.getIsShowOutbound())&&vsDto.getIsShowOutbound()==2)
		        	sql.append("2,3,");
				
		        if(!Common.isNull(vsDto.getIsShowTruck())&&vsDto.getIsShowTruck()==2)
					sql.append("1,-1)");
				else
					sql.append("-1)");
				
		        if(vsDto.getInPort()!=null)
					sql.append(" and e.inPort='").append(vsDto.getInPort()).append("'");
				
		        if(vsDto.getProductId()!=0)
					sql.append(" and b.productId=").append(vsDto.getProductId());
				
		        if(!Common.isNull(vsDto.getClientId()))
					sql.append(" and b.clientId=").append(vsDto.getClientId());
				
		        if(vsDto.getTankId()!=0)
					sql.append(" and e.tankId=").append(vsDto.getTankId());
				
		        if(vsDto.getTimeType()!=null&&vsDto.getTimeType()==1){
					if(vsDto.getStartTime()!=null&&vsDto.getStartTime()!=-1)
						sql.append(" and a.invoiceTime>=").append(vsDto.getStartTime());
					
					if(vsDto.getEndTime()!=null&&vsDto.getEndTime()!=-1)
						sql.append(" and a.invoiceTime<=").append(vsDto.getEndTime());
					
					sql.append(" ORDER BY a.invoiceTime DESC ");
				}else{
				
					if(vsDto.getStartTime()!=null&&vsDto.getStartTime()!=-1)
					sql.append(" and a.createTime>=").append(vsDto.getStartTime());
				
					if(vsDto.getEndTime()!=null&&vsDto.getEndTime()!=-1)
						sql.append(" and a.createTime<=").append(vsDto.getEndTime());
	
					sql.append(" ORDER BY a.createTime DESC ");
				}
				if(pageView.getMaxresult()!=0)
					sql.append(" limit ").append(pageView.getStartRecord()).append(" , ").append(pageView.getMaxresult());
				
				return executeQuery(sql.toString());
	}
	
	/**
	 * 查询车位发货日报表信息数据总条数
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:05
	 * @return :int 
	 * @throws
	 */
	@Override
	public int getParkDailyStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("select count(1) cou from t_pcs_measure_record r") ;
		sb.append(" left join t_pcs_park p on r.parkId=p.id where 1=1  ") ;
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append(" and DATE_FORMAT(r.meterStartTime, '%Y-%m-%d')='"+vehicleDeliveryStatementDto.getDate()+"'") ;
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getParkId()))
		{
			sb.append(" and r.parkId="+vehicleDeliveryStatementDto.getParkId()) ;
		}
		
		logger.info("查询车位发货日报表信息数据总条数SQL语句："+sb.toString());
		
		return (int)getCount(sb.toString());
	}
	
	/**
	 * 查询车位流量日报表数据总条数
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:18
	 * @return :int 
	 * @throws
	 */
	@Override
	public int getWeighDailyStatementCount(VehicleDeliveryStatementDto vsDto) throws Exception 
	{
		StringBuilder sql=new StringBuilder();
		 sql.append(" select count(1) from t_pcs_goodslog a ");
		if (vsDto.getProductId() != 0 || !Common.isNull(vsDto.getClientId())) {
			sql.append(" LEFT JOIN t_pcs_goods b on a.goodsId=b.id ")
					.append(" LEFT JOIN t_pcs_product c on c.id=b.productId ")
					.append(" LEFT JOIN t_pcs_client g ON g.id=b.clientId")
					.append(" LEFT JOIN t_pcs_truck d on d.id=a.vehicleShipId ");
		}
		if (vsDto.getInPort() != null || vsDto.getTankId() != 0) {
			sql.append(" LEFT JOIN t_pcs_weighbridge e on e.serial=a.serial ")
					.append(" LEFT JOIN t_pcs_tank f on f.id=e.tankId ");
		}
		sql.append(" WHERE  a.type=5 and a.actualType=1 and a.deliverType in (");
		if (!Common.isNull(vsDto.getIsShowOutbound())&& vsDto.getIsShowOutbound() == 2) 
			sql.append("2,3,");
		
		if (!Common.isNull(vsDto.getIsShowTruck())&& vsDto.getIsShowTruck() == 2) 
			sql.append("1,-1)");
		 else 
			sql.append("-1)");
		
		if (vsDto.getInPort() != null) 
			sql.append(" and e.inPort='").append(vsDto.getInPort()).append("'");
		
		if (vsDto.getProductId() != 0) 
			sql.append(" and b.productId=" ).append(vsDto.getProductId());
		
		if (!Common.isNull(vsDto.getClientId()))
			sql.append(" and b.clientId=" ).append( vsDto.getClientId());
		
		if (vsDto.getTankId() != 0) 
			sql.append(" and e.tankId=").append(vsDto.getTankId());
		
		if (vsDto.getTimeType() != null && vsDto.getTimeType() == 1) {
			if (vsDto.getStartTime() != null && vsDto.getStartTime() != -1) 
				sql.append(" and a.invoiceTime>=").append(vsDto.getStartTime());
			
			if (vsDto.getEndTime() != null && vsDto.getEndTime() != -1) 
				sql.append(" and a.invoiceTime<=").append(vsDto.getEndTime());
			
		} else {
			if (vsDto.getStartTime() != null && vsDto.getStartTime() != -1) 
				sql.append(" and a.createTime>=").append(vsDto.getStartTime());
			
			if (vsDto.getEndTime() != null && vsDto.getEndTime() != -1) 
				sql.append(" and a.createTime<=").append(vsDto.getEndTime());
			
		}
				return (int) getCount(sql.toString());
	}
	
	/**
	 * 查询车位发货月报表
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:00:39
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> queryVehicleMonthlyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT 	");
		sb.append("	  DATE_FORMAT(meterStartTime, '%Y-%m-%d') meterStartTime,	");
		sb.append("	  MIN(meterStart) meterStart,	");
		sb.append("	  MAX(meterEnd) meterEnd,	");
		sb.append("	  SUM(meterActualNum) meterActualNum,	");
		sb.append("	  SUM(reserveNum) reserveNum,	");
		sb.append("	  SUM(diffNum) diffNum	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure_record 	");
		sb.append("	WHERE 1=1 	");
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append("	and DATE_FORMAT(meterStartTime, '%Y-%m') ='"+vehicleDeliveryStatementDto.getDate()+"'");
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getParkId()))
		{
			sb.append("	and parkId ="+vehicleDeliveryStatementDto.getParkId());
		}
		sb.append("	GROUP BY DATE_FORMAT(meterStartTime, '%Y-%m-%d') 	");
		if(!Common.empty(pageView))
		{
			sb.append(" limit "+pageView.getStartRecord()+","+pageView.getMaxresult()) ;
		}
		sb.append("	union SELECT '' meterStartTime,'' meterStart,'合计' meterEnd,temp.meterActualNum,temp.reserveNum,temp.diffNum from(");
		sb.append("	SELECT 	");
		sb.append("	  DATE_FORMAT(meterStartTime, '%Y-%m-%d') meterStartTime,	");
		sb.append("	  MIN(meterStart) meterStart,	");
		sb.append("	  MAX(meterEnd) meterEnd,	");
		sb.append("	  SUM(meterActualNum) meterActualNum,	");
		sb.append("	  SUM(reserveNum) reserveNum,	");
		sb.append("	  SUM(diffNum) diffNum	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure_record 	");
		sb.append("	WHERE 1=1 	");
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append("	and DATE_FORMAT(meterStartTime, '%Y-%m') ='"+vehicleDeliveryStatementDto.getDate()+"'");
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getParkId()))
		{
			sb.append("	and parkId ="+vehicleDeliveryStatementDto.getParkId());
		}
		sb.append("	GROUP BY DATE_FORMAT(meterStartTime, '%Y-%m-%d') 	");
		if(!Common.empty(pageView))
		{
			sb.append(" limit "+pageView.getStartRecord()+","+pageView.getMaxresult()) ;
		}
		sb.append("	)temp  	");
		sb.append("	ORDER BY DATE_FORMAT(meterStartTime, '%Y-%m-%d') DESC 	");
		
		logger.info("查询车位发货月报表SQL语句："+sb.toString());
		
		return executeQuery(sb.toString());
	}
	
	/**
	 * 查询车位发货月报表数据总条数
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:27
	 * @return :int 
	 * @throws
	 */
	@Override
	public int getVehicleMonthlyStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT count(1) from (");
		sb.append("	SELECT 	");
		sb.append("	  DATE_FORMAT(meterStartTime, '%Y-%m-%d') meterStartTime,	");
		sb.append("	  MIN(meterStart) meterStart,	");
		sb.append("	  MAX(meterEnd) meterEnd,	");
		sb.append("	  SUM(meterActualNum) meterActualNum,	");
		sb.append("	  SUM(reserveNum) reserveNum,	");
		sb.append("	  SUM(diffNum) diffNum	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure_record 	");
		sb.append("	WHERE 1=1 	");
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append("	and DATE_FORMAT(meterStartTime, '%Y-%m') ='"+vehicleDeliveryStatementDto.getDate()+"'");
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getParkId()))
		{
			sb.append("	and parkId ="+vehicleDeliveryStatementDto.getParkId());
		}
		sb.append("	GROUP BY DATE_FORMAT(meterStartTime, '%Y-%m-%d') 	");
		sb.append("	ORDER BY DATE_FORMAT(meterStartTime, '%Y-%m-%d') DESC 	");
		sb.append("	)temp 	");
		
		logger.info("查询车位发货月报表数据总条数SQL语句："+sb.toString());

		return (int)getCount(sb.toString());
	}
	
	/**
	 * 月度汇总表
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:01:44
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryProductMonthlyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT 	");
		sb.append("	  p.name productName,	");
		sb.append("	  SUM(meterActualNum) meterActualNum 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure_record r 	");
		sb.append("	  LEFT JOIN t_pcs_product p 	");
		sb.append("	    ON p.id = r.productId 	");
		sb.append("	WHERE 1=1 	");
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append("	and DATE_FORMAT(meterStartTime, '%Y-%m') = '"+vehicleDeliveryStatementDto.getDate()+"' 	");
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getProductId()))
		{
			sb.append("	and p.id = "+vehicleDeliveryStatementDto.getProductId());
		}
		sb.append("	GROUP BY p.name 	");
		if(!Common.empty(pageView))
		{
			sb.append(" limit "+pageView.getStartRecord()+","+pageView.getMaxresult()) ;
		}
		sb.append("	UNION	");
		sb.append("	SELECT 	");
		sb.append("	  '合计' productName, SUM(temp.meterActualNum) FROM(	");
		sb.append("	SELECT 	");
		sb.append("	  p.name productName,	");
		sb.append("	  SUM(meterActualNum) meterActualNum 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure_record r 	");
		sb.append("	  LEFT JOIN t_pcs_product p 	");
		sb.append("	    ON p.id = r.productId 	");
		sb.append("	WHERE 1=1 	");
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append("	and DATE_FORMAT(meterStartTime, '%Y-%m') = '"+vehicleDeliveryStatementDto.getDate()+"' 	");
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getProductId()))
		{
			sb.append("	and p.id = "+vehicleDeliveryStatementDto.getProductId());
		}
		sb.append("	GROUP BY p.name 	");
		if(!Common.empty(pageView))
		{
			sb.append(" limit "+pageView.getStartRecord()+","+pageView.getMaxresult()) ;
		}
		sb.append("	  )temp	");
		
		logger.info("月度汇总表SQL语句："+sb.toString());

		return executeQuery(sb.toString()) ;
	}
	
	/**
	 * 月度汇总表总条数
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:54
	 * @return :int 
	 * @throws
	 */
	public int getProductMonthlyStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT 	count(1) from (");
		sb.append("	SELECT 	");
		sb.append("	  p.name productName,	");
		sb.append("	  SUM(meterActualNum) meterActualNum 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure_record r 	");
		sb.append("	  LEFT JOIN t_pcs_product p 	");
		sb.append("	    ON p.id = r.productId 	");
		sb.append("	WHERE 1=1 	");
		if(!Common.empty(vehicleDeliveryStatementDto.getDate())){
			sb.append("	and DATE_FORMAT(meterStartTime, '%Y-%m') = '"+vehicleDeliveryStatementDto.getDate()+"' 	");
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getProductId())){
			sb.append("	and p.id = "+vehicleDeliveryStatementDto.getProductId());
		}
		sb.append("	GROUP BY p.name 	");
		sb.append("	)temp  	");
		
		logger.info("月度汇总表总条数SQL语句："+sb.toString());
		
		return (int)getCount(sb.toString());
	}
	
	/**
	 * 查询车位发货历史累计量报表
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:01:01
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> queryVehicleHistoryCumulantStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT 	");
		sb.append("	  pk.name parkName,	");
		sb.append("	  DATE_FORMAT(r.meterStartTime, '%Y-%m-%d') meterStartTime,	");
		sb.append("	  SUM(meterActualNum) meterActualNum 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure_record r 	");
		sb.append("	  LEFT JOIN t_pcs_park pk 	");
		sb.append("	    ON pk.id = r.parkId 	");
		sb.append("	WHERE 1=1	");
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append("	and DATE_FORMAT(r.meterStartTime, '%Y-%m-%d') = '"+vehicleDeliveryStatementDto.getDate()+"' 	");
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getParkId()))
		{
			sb.append("	and r.parkId ="+vehicleDeliveryStatementDto.getParkId());
		}
		sb.append("	GROUP BY r.parkId,DATE_FORMAT(r.meterStartTime, '%Y-%m-%d') 	");
		if(!Common.empty(pageView))
		{
			sb.append(" limit "+pageView.getStartRecord()+","+pageView.getMaxresult()) ;
		}
		
		logger.info("查询车位发货历史累计量报表SQL语句："+sb.toString());
		
		return executeQuery(sb.toString());
	}
	
	/**
	 * 查询车位发货历史累计量报表数据总条数
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:38
	 * @return :int 
	 * @throws
	 */
	@Override
	public int getVehicleHistoryCumulantStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT count(1) from ( 	");
		sb.append("	SELECT 	");
		sb.append("	  pk.name parkName,	");
		sb.append("	  DATE_FORMAT(r.meterStartTime, '%Y-%m-%d') meterStartTime,	");
		sb.append("	  SUM(meterActualNum) meterActualNum 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure_record r 	");
		sb.append("	  LEFT JOIN t_pcs_park pk 	");
		sb.append("	    ON pk.id = r.parkId 	");
		sb.append("	WHERE 1=1	");
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append("	and DATE_FORMAT(r.meterStartTime, '%Y-%m-%d') = '"+vehicleDeliveryStatementDto.getDate()+"' 	");
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getParkId()))
		{
			sb.append("	and r.parkId ="+vehicleDeliveryStatementDto.getParkId());
		}
		sb.append("	GROUP BY r.parkId,DATE_FORMAT(r.meterStartTime, '%Y-%m-%d') 	");
		sb.append("	)temp 	");
		
		logger.info("查询车位发货历史累计量报表数据总条数SQL语句："+sb.toString());
		
		return (int)getCount(sb.toString());
	}

	/**
	 * 查询车位流量计月报总表
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:01:23
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> queryVehicleMonthlyTotalStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT 	");
		sb.append("	  p.name parkName,	");
		sb.append("	  pd.name productName,	");
		sb.append("	  SUM(r.meterActualNum) meterActualNum 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure_record r 	");
		sb.append("	  LEFT JOIN t_pcs_park p 	");
		sb.append("	    ON r.parkId = p.id 	");
		sb.append("	  LEFT JOIN t_pcs_product pd 	");
		sb.append("	    ON pd.id = r.productId 	");
		sb.append("	WHERE 1=1	");
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append("	and DATE_FORMAT(r.meterStartTime, '%Y-%m') = '"+vehicleDeliveryStatementDto.getDate()+"' 	");
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getParkId()))
		{
			sb.append("	and r.parkId ="+vehicleDeliveryStatementDto.getParkId());
		}
		sb.append(" GROUP BY p.name,pd.name	ORDER BY p.name,pd.name DESC 	");
		if(!Common.empty(pageView))
		{
			sb.append(" limit "+pageView.getStartRecord()+","+pageView.getMaxresult()) ;
		}
		
		logger.info("查询车位流量计月报总表SQL语句："+sb.toString());
		
		return executeQuery(sb.toString());
	}
	
	/**
	 * 查询车位流量计月报总表数据总条数
	 * @Title:VehicleDeliveryStatementDaoImpl
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:47
	 * @return :int 
	 * @throws
	 */
	@Override
	public int getVehicleMonthlyTotalStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT count(1) from (	");
		sb.append("	SELECT 	");
		sb.append("	  p.name parkName,	");
		sb.append("	  pd.name productName,	");
		sb.append("	  SUM(r.meterActualNum) meterActualNum 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure_record r 	");
		sb.append("	  LEFT JOIN t_pcs_park p 	");
		sb.append("	    ON r.parkId = p.id 	");
		sb.append("	  LEFT JOIN t_pcs_product pd 	");
		sb.append("	    ON pd.id = r.productId 	");
		sb.append("	WHERE 1=1	");
		if(!Common.empty(vehicleDeliveryStatementDto.getDate()))
		{
			sb.append("	and DATE_FORMAT(r.meterStartTime, '%Y-%m') = '"+vehicleDeliveryStatementDto.getDate()+"' 	");
		}
		if(!Common.isNull(vehicleDeliveryStatementDto.getParkId()))
		{
			sb.append("	and r.parkId ="+vehicleDeliveryStatementDto.getParkId());
		}
		sb.append(" GROUP BY p.name,pd.name	ORDER BY p.name DESC 	");
		sb.append("	)temp 	");
		
		logger.info("查询车位流量计月报总表数据总条数SQL语句："+sb.toString());
		
		return (int)getCount(sb.toString());
	}

	@Override
	public Map<String, Object> getTotalNum(VehicleDeliveryStatementDto vsDto)
			throws Exception {
		String sql=" select SUM(ROUND(a.actualNum,3)) totalNum,COUNT(DISTINCT a.batchId,a.deliverType) truckNum"
				+" from t_pcs_goodslog a ";
				if(vsDto.getProductId()!=0||vsDto.getClientId()!=null){
					sql+=" LEFT JOIN t_pcs_goods b on a.goodsId=b.id "
				+" LEFT JOIN t_pcs_product c on c.id=b.productId " ;
				}
				if(vsDto.getInPort()!=null||vsDto.getTankId()!=0){
					sql+=" LEFT JOIN t_pcs_weighbridge e on e.serial=a.serial "
				+" LEFT JOIN t_pcs_tank f on f.id=e.tankId ";
				}
				sql+=" WHERE  a.type=5 and a.actualType=1 and a.deliverType in(";
				if(!Common.isNull(vsDto.getIsShowOutbound())&&vsDto.getIsShowOutbound()==2){
		        	sql+="2,3,";
		        }
				if(!Common.isNull(vsDto.getIsShowTruck())&&vsDto.getIsShowTruck()==2){
					sql+="1,-1)";
				}else{
					sql+="-1)";
				}
				if(vsDto.getInPort()!=null){
					sql+=" and e.inPort='"+vsDto.getInPort()+"'";
				}
				if(vsDto.getProductId()!=0){
					sql+=" and b.productId="+vsDto.getProductId();
				}
				if(vsDto.getClientId()!=null)
					sql+=" and b.clientId="+vsDto.getClientId();
				if(vsDto.getTankId()!=0){
					sql+=" and e.tankId="+vsDto.getTankId();
				}
				if(vsDto.getTimeType()!=null&&vsDto.getTimeType()==1){
					if(vsDto.getStartTime()!=null&&vsDto.getStartTime()!=-1){
						sql+=" and a.invoiceTime>="+vsDto.getStartTime();
					}
					if(vsDto.getEndTime()!=null&&vsDto.getEndTime()!=-1){
						sql+=" and a.invoiceTime<="+vsDto.getEndTime();
					}
					sql+=" ORDER BY a.invoiceTime DESC ";
				}else{
				if(vsDto.getStartTime()!=null&&vsDto.getStartTime()!=-1){
					sql+=" and a.createTime>="+vsDto.getStartTime();
				}
				if(vsDto.getEndTime()!=null&&vsDto.getEndTime()!=-1){
					sql+=" and a.createTime<="+vsDto.getEndTime();
				}
				sql+=" ORDER BY a.createTime DESC ";
				}
				return executeQueryOne(sql);
	}

	@Override
	public List<Map<String, Object>> getProductNameMsg(String stringValue) throws OAException {
		try{
			String sql="SELECT  b.productId productId, c.name productName "
					+" from t_pcs_goodslog a  left join t_pcs_goods b on a.goodsId=b.id left join t_pcs_product c on  b.productId=c.id "
					+" where a.type=5 and a.actualType=1 and a.deliverType=1  "
					+" and FROM_UNIXTIME(a.createTime,'%Y-%m')='"+stringValue+"'"
					+" GROUP BY b.productId";
			return executeQuery(sql);
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "daogetProductNameMsg失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getProductData(VehicleDeliveryStatementDto vsDto) throws OAException {
		try{
			String sql="SELECT count(DISTINCT a.batchId) count,SUM(ROUND(a.actualNum,3)) actualNum,FROM_UNIXTIME(a.createTime,'%d') date "
			+" from t_pcs_goodslog a left join t_pcs_goods b on a.goodsId=b.id left join t_pcs_product c on  b.productId=c.id "
			+" where a.type=5 and a.actualType=1 and a.deliverType=1  "
			+" and FROM_UNIXTIME(a.createTime,'%Y-%m')='"+vsDto.getDateTime()+"'"
			+" and b.productId="+vsDto.getProductId()
			+" GROUP BY FROM_UNIXTIME(a.createTime,'%Y-%m-%d') ASC";
			
			
			
			return executeQuery(sql);
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "daogetProductData失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getInportMsg(String dateTime,Integer productId) throws OAException {
		try{
			String sql="SELECT  IFNULL(b.inPort,'') inport  from t_pcs_goodslog a "
					 + " left join t_pcs_weighbridge b on a.serial=b.serial "
					 + " left join t_pcs_goods c on a.goodsId=c.id "
					 +" where a.type=5 and a.actualType=1 and a.deliverType=1   "
					 +"  and c.productId="+productId
					 +" and FROM_UNIXTIME(a.createTime,'%Y-%m')='"+dateTime+"'"
					 +" GROUP BY b.inPort ";
			return executeQuery(sql);
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "daogetInportMsg失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getInportData(VehicleDeliveryStatementDto vsDto)
			throws OAException {
		try{
			String sql="SELECT count(DISTINCT a.batchId) count,SUM(ROUND(a.actualNum,3)) actualNum,FROM_UNIXTIME(a.createTime,'%d') date "
					+" from t_pcs_goodslog a "
					+ " left join t_pcs_weighbridge b on a.serial=b.serial "
					 + " left join t_pcs_goods c on a.goodsId=c.id "
					+"  where a.type=5 and a.actualType=1 and a.deliverType=1   "
					+"   and c.productId="+vsDto.getProductId()
					+"  and FROM_UNIXTIME(a.createTime,'%Y-%m')='"+vsDto.getDateTime()+"'"
					+"  and b.inPort='"+vsDto.getInPort()+"'"
					+"  GROUP BY FROM_UNIXTIME(a.createTime,'%Y-%m-%d') ASC";
			return executeQuery(sql);
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "daogetInportData失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getTankMsg(String dateTime, Integer productId,Integer clientId) throws OAException {
		try{
			String sql="SELECT  b.tankId tankId , d.code tankName from t_pcs_goodslog a "
					+ "left join t_pcs_weighbridge b on a.serial=b.serial "
					 + " left join t_pcs_tank d on d.id=b.tankId"
					 + " left join t_pcs_goods c on a.goodsId=c.id "
					 +" where a.type=5 and a.actualType=1 and a.deliverType=1  "
					 +"  and c.productId="+productId
					 +" and FROM_UNIXTIME(a.createTime,'%Y-%m')='"+dateTime+"'";
					if(clientId!=null)
						sql+=" and c.clientId="+clientId;
					 sql+=" GROUP BY b.tankId ";
			return executeQuery(sql);
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "daogetInportMsg失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getTankData(VehicleDeliveryStatementDto vsDto)
			throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT count(DISTINCT a.batchId) count,SUM(ROUND(a.actualNum,3)) actualNum,FROM_UNIXTIME(a.createTime,'%d') date ")
					.append("  from t_pcs_goodslog a  ")
					.append(" LEFT JOIN t_pcs_weighbridge b ON a.serial=b.serial ")
					.append(" LEFT JOIN t_pcs_goods c ON a.goodsId=c.id  ")
					.append("   where a.type=5 and a.actualType=1 and a.deliverType=1    ")
					.append("    and c.productId="+vsDto.getProductId());
			if(vsDto.getClientId()!=null)
				sql.append(" and c.clientId=").append(vsDto.getClientId());
					sql.append("  and FROM_UNIXTIME(a.createTime,'%Y-%m')='"+vsDto.getDateTime()+"'")
					.append("   and b.tankId="+vsDto.getTankId() )
					.append("   GROUP BY FROM_UNIXTIME(a.createTime,'%Y-%m-%d') ASC");
			return executeQuery(sql.toString());
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "daogetInportData失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getProductNameTypeMsg(VehicleDeliveryStatementDto vsDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT (CASE WHEN c.type=1 then '汽油' WHEN c.type=3 THEN '柴油' ELSE c.name END  ) productName ,c.type productId ")
			.append(" from t_pcs_goodslog a ")
			.append(" LEFT JOIN t_pcs_goods b ON a.goodsId=b.id  ")
			.append(" LEFT JOIN t_pcs_product c ON b.productId=c.id  ")
			.append(" where a.type=5 and a.actualType=1 and a.deliverType=1");
			if(vsDto.getStartTime()!=null){
				sql.append(" and a.createTime >=").append(vsDto.getStartTime());
			}
			if(vsDto.getEndTime()!=null){
				sql.append(" and a.createTime <=").append(vsDto.getEndTime());
			}					
			sql.append(" GROUP BY c.type  order by c.type desc");
			return executeQuery(sql.toString());
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "getProductNameTypeMsg失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getOutBoundDataList(VehicleDeliveryStatementDto vsDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT count(DISTINCT a.batchId) count,SUM(ROUND(a.actualNum,3)) num,b.inPort port") 
			.append("  from t_pcs_goodslog a ")
			.append(" LEFT JOIN t_pcs_weighbridge b ON a.serial=b.serial ")
			.append(" LEFT JOIN t_pcs_goods c ON a.goodsId=c.id  ")
			.append(" LEFT JOIN t_pcs_product d ON c.productId=d.id  ")
			.append(" where a.type=5 and a.actualType=1 and a.deliverType=1"); 
			
			if(vsDto.getStartTime()!=null){
				sql.append(" and a.createTime >=").append(vsDto.getStartTime());
			}
			if(vsDto.getEndTime()!=null){
				sql.append(" and a.createTime <=").append(vsDto.getEndTime());
			}	
			if(vsDto.getProductId()!=0){
				sql.append(" and d.type=").append(vsDto.getProductId());
			}
			sql.append(" GROUP BY b.inPort  ORDER BY b.inPort ASC");
			return executeQuery(sql.toString());
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "getProductNameTypeMsg失败", e);
		}
	}

	@Override
	public int getAssemBlyTruck(VehicleDeliveryStatementDto vsDto)
			throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
				sql.append(" SELECT count(1) FROM ( SELECT DISTINCT a.batchId ")
				.append(" FROM t_pcs_goodslog a ")
				.append(" LEFT JOIN t_pcs_goods b ON a.goodsId = b.id ")
				.append(" LEFT JOIN t_pcs_product c ON c.id = b.productId ")
				.append(" LEFT JOIN t_pcs_weighbridge e ON e.serial=a.serial ");
				if(vsDto.getProductId()!=0){
					sql.append(",( SELECT DISTINCT a.batchId id ")
					.append(" FROM t_pcs_goodslog a ")
					.append(" LEFT JOIN t_pcs_goods b ON a.goodsId = b.id ")
					.append(" LEFT JOIN t_pcs_product c ON c.id = b.productId ")
					.append(" WHERE a.type=5 AND a.deliverType=1 AND a.actualType=1 ")
					.append(" and c.type=").append(vsDto.getProductId());
					if(vsDto.getStartTime()!=null)
						sql.append(" AND a.createTime>=").append(vsDto.getStartTime());
						if(vsDto.getEndTime()!=null)
						sql.append(" AND a.createTime<=").append(vsDto.getEndTime());
					sql.append(" ) tp");
				}
				sql.append(" WHERE a.type=5 AND a.deliverType=1 AND a.actualType=1 ");
				
				if(vsDto.getProductId()!=0){
					sql.append(" AND a.batchId=tp.id ");
				}
				if(vsDto.getStartTime()!=null)
				sql.append(" AND a.createTime>=").append(vsDto.getStartTime());
				if(vsDto.getEndTime()!=null)
				sql.append(" AND a.createTime<=").append(vsDto.getEndTime());
				sql.append(" GROUP BY a.batchId HAVING COUNT(DISTINCT e.inPort)>1 ) tb ");
			
			return (int) getCount(sql.toString());
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "获取拼装车数量失败", e);
		}
	}

	/**
	 * @Title getProductNameById
	 * @Descrption:TODO
	 * @param:@param productId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2017年1月6日下午4:53:29
	 * @throws
	 */
	@Override
	public Map<String, Object> getProductNameById(int productId)
			throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("select name from t_pcs_product where id=").append(productId);
			return executeQueryOne(sql.toString());
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "获取货品名失败", e);
		}
	}

	/**
	 * @Title getClientNameById
	 * @Descrption:TODO
	 * @param:@param clientId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2017年1月6日下午5:14:15
	 * @throws
	 */
	@Override
	public Map<String, Object> getClientNameById(Integer clientId)
			throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("select name from t_pcs_client where id=").append(clientId);
			return executeQueryOne(sql.toString());
		}catch(RuntimeException e){
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "获取货主名失败", e);
		}
	}

}
