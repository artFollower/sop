package com.skycloud.oa.outbound.dao.impl;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.ShipDeliveryMeasureDao;
import com.skycloud.oa.outbound.dto.ShipDeliverMeasureDto;
import com.skycloud.oa.outbound.model.ShipDeliveryMeasure;
import com.skycloud.oa.utils.Common;

/**
 * 
 * <p>流量计台帐</p>
 * @ClassName:ShipDeliveryMeasureDaoImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年8月6日 下午2:25:40
 *
 */
@Component
public class ShipDeliveryMeasureDaoImpl extends BaseDaoImpl implements ShipDeliveryMeasureDao
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(ShipDeliveryMeasureDaoImpl.class);
	
	/**
	 * 流量计台帐列表
	 * @Title:list
	 * @Description:
	 * @param shipDeliverMeasureDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public List<Map<String, Object>> list(ShipDeliverMeasureDto shipDeliverMeasureDto, PageView pageView) throws Exception 
	{
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT a.id,a.arrivalId,a.startLevel,a.endLevel,a.shipAmount, ROUND(a.endLevel-a.startLevel,3) flowmeter, ")
		.append(" ABS(e.realAmount) realAmount,(case when IFNULL(a.metering,0)!=0 then a.metering else ABS(e.measureAmount) end)metering,f.checkUserId,g.name userName,FROM_UNIXTIME(c.openPump) openPumpTime, ")
		.append("FROM_UNIXTIME(c.stopPump) stopPumpTime,DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d %h:%i') startTime, ")
		.append("CONCAT(h.name,'(',i.refName,')') shipName,j.name berthName,e.tankId,k.code tankName,d.tubeInfo tubeName ")
		.append("from t_pcs_shipdeliverymeasure a ")
		.append("LEFT JOIN t_pcs_arrival b ON a.arrivalId=b.id ")
		.append("LEFT JOIN t_pcs_work c ON c.arrivalId=a.arrivalId ")
		.append("LEFT JOIN t_pcs_transport_program d ON d.arrivalId=b.id ")
		.append("LEFT JOIN t_pcs_store e ON e.transportId=d.id ")
		.append("LEFT JOIN t_pcs_work_check f ON f.transportId = d.id AND f.checkType = 21  ")
		.append("LEFT JOIN t_auth_user g ON g.id=f.checkUserId  ")
		.append("LEFT JOIN t_pcs_ship h ON h.id=b.shipId ")
		.append("LEFT JOIN t_pcs_ship_ref i ON i.id=b.shipRefId ")
		.append("LEFT JOIN t_pcs_berth j ON j.id=b.berthId ")
		.append("LEFT JOIN t_pcs_tank k ON k.id=e.tankId ")
		.append(" where 1=1 and i.refName!='转输' and b.type=2 ");
		if(!Common.empty(shipDeliverMeasureDto.getId()))
			sql.append(" and a.id=").append(shipDeliverMeasureDto.getId());
		if(!Common.empty(shipDeliverMeasureDto.getShipName()))
			sql.append(" and i.refName like '%"+shipDeliverMeasureDto.getShipName()+"%' or h.name like '%"+shipDeliverMeasureDto.getShipName()+"%'" ) ;
		if(!Common.empty(shipDeliverMeasureDto.getStartTime()))
			sql.append(" and DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') >= '"+shipDeliverMeasureDto.getStartTime()+"'" );
		if(!Common.empty(shipDeliverMeasureDto.getEndTime()))
			sql.append(" and DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') <= '"+shipDeliverMeasureDto.getEndTime()+"'" );
		sql.append(" order by b.arrivalStartTime desc ");
		if(!Common.empty(pageView))
			sql.append(" limit "+pageView.getStartRecord()+","+pageView.getMaxresult()) ;
		
		logger.info("（ShipDeliveryMeasureDaoImpl）流量计台帐SQL："+sql.toString());
		
		return executeQuery(sql.toString());
	}
	
	/**
	 * 流量计台帐总条数
	 * @Title:getCount
	 * @Description:
	 * @param shipDeliverMeasureDto
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public int getCount(ShipDeliverMeasureDto shipDeliverMeasureDto) throws Exception 
	{
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT count(1) ")
		.append("from t_pcs_shipdeliverymeasure a ")
		.append("LEFT JOIN t_pcs_arrival b ON a.arrivalId=b.id ")
		.append("LEFT JOIN t_pcs_work c ON c.arrivalId=a.arrivalId ")
		.append("LEFT JOIN t_pcs_transport_program d ON d.arrivalId=b.id ")
		.append("LEFT JOIN t_pcs_store e ON e.transportId=d.id ")
		.append("LEFT JOIN t_pcs_work_check f ON f.transportId = d.id AND f.checkType = 21  ")
		.append("LEFT JOIN t_auth_user g ON g.id=f.checkUserId  ")
		.append("LEFT JOIN t_pcs_ship h ON h.id=b.shipId ")
		.append("LEFT JOIN t_pcs_ship_ref i ON i.id=b.shipRefId ")
		.append("LEFT JOIN t_pcs_berth j ON j.id=b.berthId ")
		.append("LEFT JOIN t_pcs_tank k ON k.id=e.tankId ")
		.append(" where 1=1 and i.refName!='转输' and b.type=2 ");
		if(!Common.empty(shipDeliverMeasureDto.getId()))
			sql.append(" and a.id=").append(shipDeliverMeasureDto.getId());
		if(!Common.empty(shipDeliverMeasureDto.getShipName()))
			sql.append(" and i.refName like '%"+shipDeliverMeasureDto.getShipName()+"%' or h.name like '%"+shipDeliverMeasureDto.getShipName()+"%'" ) ;
		if(!Common.empty(shipDeliverMeasureDto.getStartTime()))
			sql.append(" and DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') >= '"+shipDeliverMeasureDto.getStartTime()+"'" );
		if(!Common.empty(shipDeliverMeasureDto.getEndTime()))
			sql.append(" and DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') <= '"+shipDeliverMeasureDto.getEndTime()+"'" );
		return (int)getCount(sql.toString()) ;
	}

	/**
	 * 
	 * @Title:get
	 * @Description:
	 * @param id
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public Map<String, Object> get(int id) throws Exception 
	{
		StringBuilder sql=new StringBuilder();
		sql.append("SELECT a.id,a.arrivalId,a.startLevel,a.endLevel,a.shipAmount, ROUND(a.endLevel-a.startLevel,3) flowmeter, ")
		.append("  ABS(e.realAmount) realAmount,(case when !ISNULL(a.metering) or a.metering=0 then a.metering else ABS(e.measureAmount) end) metering,f.checkUserId,g.name userName,FROM_UNIXTIME(c.openPump) openPumpTime, ")
		.append("FROM_UNIXTIME(c.stopPump) stopPumpTime,DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d %h:%i') startTime, ")
		.append("CONCAT(h.name,'(',i.refName,')') shipName,j.name berthName,e.tankId,k.code tankName,d.tubeInfo tubeName ")
		.append("from t_pcs_shipdeliverymeasure a ")
		.append("LEFT JOIN t_pcs_arrival b ON a.arrivalId=b.id ")
		.append("LEFT JOIN t_pcs_work c ON c.arrivalId=a.arrivalId ")
		.append("LEFT JOIN t_pcs_transport_program d ON d.arrivalId=b.id ")
		.append("LEFT JOIN t_pcs_store e ON e.transportId=d.id ")
		.append("LEFT JOIN t_pcs_work_check f ON f.transportId = d.id AND f.checkType = 21  ")
		.append("LEFT JOIN t_auth_user g ON g.id=f.checkUserId  ")
		.append("LEFT JOIN t_pcs_ship h ON h.id=b.shipId ")
		.append("LEFT JOIN t_pcs_ship_ref i ON i.id=b.shipRefId ")
		.append("LEFT JOIN t_pcs_berth j ON j.id=b.berthId ")
		.append("LEFT JOIN t_pcs_tank k ON k.id=e.tankId ")
		.append(" where 1=1 and i.refName!='转输' ");
		if(!Common.isNull(id))
			sql.append(" and a.id=").append(id).append(" ") ;		
		return executeQueryOne(sql.toString()) ;
	}

	/**
	 * 台帐管理-流量计台帐-根据ID删除记录
	 * @Title:delete
	 * @Description:
	 * @param id
	 * @throws Exception
	 * @see
	 */
	@Override
	public void delete(ShipDeliverMeasureDto sDto) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("delete from t_pcs_shipdeliverymeasure where 1=1 ") ;
		if(sDto.getId()!=null)
			sb.append(" and id=").append(sDto.getId());
		if(sDto.getArrivalId()!=null)
			sb.append(" and arrivalId=").append(sDto.getArrivalId());
		execute(sb.toString()) ;
	}

	/**
	 * 台帐管理-流量计台帐-根据ID修改记录
	 * @Title:update
	 * @Description:
	 * @param shipDelvieryMeasure
	 * @throws Exception
	 * @see
	 */
	@Override
	public void update(ShipDeliveryMeasure sm) throws Exception{
		StringBuilder sql=new StringBuilder();
		sql.append(" update t_pcs_shipdeliverymeasure set id=id ");
		if(sm.getArrivalId()!=null)
			sql.append(",arrivalId=").append(sm.getArrivalId());
	    if(sm.getStartLevel()!=null)	
	    	sql.append(",startLevel=").append(sm.getStartLevel());
	    if(sm.getEndLevel()!=null)
	    	sql.append(",endLevel=").append(sm.getEndLevel());
	    if(sm.getShipAmount()!=null)
	    	sql.append(",shipAmount=").append(sm.getShipAmount());
	    if(sm.getMetering()!=null)
	    	sql.append(",metering=").append(sm.getMetering());
	    if(sm.getArrivalId()!=null)
	    	sql.append(" where arrivalId=").append(sm.getArrivalId());
	    else 
	    	sql.append(" where id=").append(sm.getId());
	    executeUpdate(sql.toString());
	}

	@Override
	public int add(ShipDeliveryMeasure sm) throws Exception 
	{
		StringBuilder sql=new StringBuilder();
		sql.append("INSERT INTO t_pcs_shipdeliverymeasure (arrivalId) select ").append(sm.getArrivalId())
		.append(" from DUAL where NOT EXISTS( select arrivalId from t_pcs_shipdeliverymeasure where arrivalId=").append(sm.getArrivalId()).append(")");
		return	insert(sql.toString());
	}

}
