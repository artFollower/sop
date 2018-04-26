package com.skycloud.oa.outbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.outbound.dao.VehicleDeliverWorkDao;
import com.skycloud.oa.outbound.dto.VehicleDeliverWorkQueryDto;
import com.skycloud.oa.outbound.model.Approve;
import com.skycloud.oa.outbound.model.BatchVehicleInfo;
import com.skycloud.oa.outbound.model.DeliveryGoodsRecord;
import com.skycloud.oa.outbound.model.Measure;
import com.skycloud.oa.outbound.model.Train;
import com.skycloud.oa.outbound.model.VehiclePlan;
import com.skycloud.oa.outbound.model.Weigh;
import com.skycloud.oa.utils.Common;

/**
 * 
 * <p>请用一句话概括功能</p>
 * @ClassName:VehicleDeliverWorkDaoImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年7月31日 下午5:29:19
 *
 */
@Component
public class VehicleDeliverWorkDaoImpl extends BaseDaoImpl implements VehicleDeliverWorkDao 
{
	/**
	 * 记录日志
	 */
	private static Logger logger = Logger.getLogger(VehicleDeliverWorkDaoImpl.class);
	
	/**
	 * 出库管理-车发出库-车发作业-保存
	 * @Title:saveWrokPlan
	 * @Description:
	 * @param weigh
	 * @throws Exception
	 * @see
	 */
	@Override
	public void saveWrokPlan(Weigh weigh) throws Exception 
	{
		save(weigh) ;
	}
	
	/**
	 * 
	 * @Title:getStatusByPlateId
	 * @Description:
	 * @param plateId
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getStatusByPlateId(int plateId) throws Exception 
	{
		String sql = "select id,status from t_pcs_train where status!=43 and plateId="+plateId ;
		
		return executeQuery(sql);
	}

	/**
	 * 
	 * @Title:getTrainIdByPlateId
	 * @Description:
	 * @param plateId
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public  Map<String, Object> getTrainIdByPlateId(int plateId) throws Exception 
	{
		String sql = "select max(id) id from t_pcs_train where status in (40,41,42) and plateId="+plateId +"  AND deliverTime>=CURDATE()  limit 0,1";
		
		return executeQueryOne(sql);
	}
	
	/**
	 * 
	 * @Title:getSerial
	 * @Description:
	 * @param dateStr
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public Map<String, Object> getSerial(String dateStr) throws OAException 
	{
		String sql = "SELECT CONCAT('',(IFNULL(max(serial),"+dateStr+"000)+1)) serial FROM t_pcs_goodslog WHERE SUBSTR(serial,1,8)='"+dateStr+"'" ;
		
		return executeQueryOne(sql);
	}

	/**
	 * 
	 * @Title:saveWrokPlan
	 * @Description:
	 * @param measure
	 * @throws Exception
	 * @see
	 */
	@Override
	public void saveWrokPlan(Measure measure) throws Exception 
	{
		save(measure) ;
	}

	/**
	 * 
	 * @Title:updateWrokPlan
	 * @Description:
	 * @param weigh
	 * @throws Exception
	 * @see
	 */
	@Override
	public void updateWrokPlan(Weigh weigh) throws Exception 
	{
		update(weigh) ;
	}

	/**
	 * 
	 * @Title:trainNotifyCount
	 * @Description:
	 * @param trainId
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public int trainNotifyCount(String trainId) throws Exception 
	{
		String sql  = "select count(1) from t_pcs_notify where type=17 and batchId="+trainId ;
		
		return (int)getCount(sql);
	}

	/**
	 * 
	 * @Title:upMeasure
	 * @Description:
	 * @param measure
	 * @throws Exception
	 * @see
	 */
	@Override
	public void upMeasure(Measure measure) throws Exception 
	{
		String sql = "update t_pcs_measure set actualMeterStart="+measure.getActualMeterStart()+",actualMeterEnd="+measure.getActualMeterEnd()+" where id="+measure.getId() ;
		
		executeUpdate(sql) ;
	}

	/**
	 * 
	 * @Title:deleteWeighInfo
	 * @Description:
	 * @param trainId
	 * @throws Exception
	 * @see
	 */
	@Override
	public void deleteWeighInfo(String trainId) throws Exception 
	{
		String sql = "delete from t_pcs_weigh where trainId="+trainId ;
		executeUpdate(sql) ;
	}

	/**
	 * 
	 * @Title:deleteMeasureInfo
	 * @Description:
	 * @param trainId
	 * @throws Exception
	 * @see
	 */
	@Override
	public void deleteMeasureInfo(String trainId) throws Exception 
	{
		String sql = "delete from t_pcs_measure where trainId="+trainId ;
		executeUpdate(sql) ;
	}

	/**
	 * 
	 * @Title:saveApprove
	 * @Description:
	 * @param approve
	 * @throws Exception
	 * @see
	 */
	@Override
	public void saveApprove(Approve approve) throws Exception 
	{
		save(approve) ;
	}

	/**
	 * 
	 * @Title:setBillInfoToWeigh
	 * @Description:
	 * @param trainId
	 * @throws Exception
	 * @see
	 */
	@Override
	public void setBillInfoToWeigh(String trainId) throws Exception 
	{
		String sql = "insert into t_pcs_weigh(trainId) values("+trainId+")" ;
		executeUpdate(sql) ;
	}

	/**
	 * 
	 * @Title:getApproveInfo
	 * @Description:
	 * @param modelId
	 * @param refId
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getApproveInfo(int modelId,String refId) throws Exception 
	{
		String sql  = " SELECT a.*,b.name createUserName,DATE_FORMAT(a.createTime,'%Y-%m-%d') createTimeStr,c.`name` checkUser,FROM_UNIXTIME(a.reviewTime,'%Y-%m-%d') checkTime"+
	" FROM t_pcs_approve a LEFT JOIN t_auth_user b on a.checkUserId=b.id LEFT JOIN t_auth_user c on a.reviewUserId=c.id "+
	"WHERE a.modelId="+modelId+" and a.refId="+refId +" limit 0,1";
		return executeQuery(sql) ;
	}

	/**
	 * 出库管理-车发出库-数量确认
	 * @Title:updateApprove
	 * @Description:
	 * @param approve
	 * @param flag
	 * @throws Exception
	 * @see
	 */
	@Override
	public void updateApprove(Approve approve,int flag) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	UPDATE t_pcs_approve ");
		sb.append("	SET	id=id ");
		sb.append("	,`status`="+approve.getStatus());
		if(!Common.isNull(approve.getCheckUserId()))
		{
			sb.append("	,checkUserId="+approve.getCheckUserId());
		}
		if(!Common.isNull(approve.getModelId()))
		{
			sb.append("	,modelId="+approve.getModelId());
		}
		if(!Common.isNull(approve.getRefId()))
		{
			sb.append("	,refId="+approve.getRefId());
		}
		if(flag==1)
		{
			sb.append("	,comment='' ");
			sb.append("	,reviewUserId=null");
			sb.append("	,reviewTime=null"	);
		}
		else
		{
			if(!Common.empty(approve.getComment()))
			{
				sb.append("	,comment='"+approve.getComment()+"' ");
			}
			if(!Common.isNull(approve.getReviewUserId()))
			{
				sb.append("	,reviewUserId="+approve.getReviewUserId());;
			}
			if(!Common.isNull(approve.getReviewTime()))
			{
				sb.append("	,reviewTime= "+approve.getReviewTime());
			}
		}
			
		if(!Common.isNull(approve.getId()))
		{
			sb.append("	WHERE id="+approve.getId());
		}else{
			sb.append(" where id=0");
		}
		
		logger.info("出库管理-车发出库-数量确认中的审核信息SQL语句："+sb.toString());
		
		executeUpdate(sb.toString()) ;
 	}

	/**
	 * 
	 * @Title:saveTrain
	 * @Description:
	 * @param train
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public int saveTrain(Train train)throws Exception 
	{
//		Serializable s = save(train) ;
		String sql=" insert into t_pcs_train (clientId,productId,plateId,operator,status,transportId,isChange,tip) values("+train.getClientId()+","+train.getProductId()
				+","+train.getPlateId()+","+train.getOperator()+","+train.getStatus()+",0,0,'"+train.getTip()+"')";
		return  insert(sql);
	}

	/**
	 * 
	 * @Title:updateTrain
	 * @Description:
	 * @param train
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public int updateTrain(Train train) throws Exception 
	{
		 update(train) ;
		 
		 return 0 ;
	}

	/**
	 * 
	 * @Title:getTrainInfoById
	 * @Description:
	 * @param id
	 * @param serial
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getTrainInfoById(String id,String serial) throws Exception 
	{
		String sql="SELECT  a.batchId id,b.status, FROM_UNIXTIME(a.invoiceTime) deliverTime, b.operator, "+
		" a.vehicleShipId plateId, d.name plateName,b.flag, e.name ladingName, b.clientId clientId, "+
				" f.name clientName, GROUP_CONCAT(DISTINCT g.name) productName, "+
				" (CASE WHEN b.status=43 THEN SUM(ROUND(a.actualNum,3)) ELSE SUM(ROUND(a.deliverNum,3)) END) amount "+
				" from t_pcs_goodslog a "+
				" LEFT JOIN t_pcs_train b on b.id=a.batchId "+
				" LEFT JOIN t_auth_user c on c.id=b.operator "+
				" LEFT JOIN t_pcs_truck d on a.vehicleShipId=d.id "+
				" LEFT JOIN t_pcs_client e on a.ladingClientId=e.id "+
				" LEFT JOIN t_pcs_client f on b.clientId=f.id "+
				" LEFT JOIN t_pcs_product g on b.productId=g.id "+
				" WHERE a.batchId="+id+" and a.type =5 and a.deliverType=1 "+
				" ORDER BY a.batchId";
		
		return executeQuery(sql);
	}

	/**
	 * 
	 * @Title:getVehicleInfo
	 * @Description:
	 * @param trainId
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getVehicleInfo(String trainId) throws Exception 
	{
		String sql = "SELECT v.*,t.code plateName FROM t_pcs_batch_vehicle_info v LEFT JOIN t_pcs_truck t ON v.plateId=t.id WHERE trainId="+trainId ;
		return executeQuery(sql);
	}

	/**
	 *根据车发trainId获取所有联单车发称重信息
	 */
	@Override
	public List<Map<String, Object>> getWeighInfo(String trainId) throws Exception 
	{

		String sql="SELECT a.id goodsLogId,a.serial serial,c.id id,f.oils oils,a.actualNum,a.afUpNum, "
				+"d.code tankName,c.*,round(c.outWeigh-c.inWeigh,3) netWeigh,from_unixtime(c.intoTime) intoTimeStr,from_unixtime(c.outTime) outTimeStr "
				+" FROM t_pcs_goodslog a "
				+" LEFT JOIN t_pcs_train b ON a.batchId=b.id "
				+" LEFT JOIN t_pcs_weighbridge c ON a.serial=c.serial "
				+" LEFT JOIN t_pcs_tank d ON d.id=c.tankId "
				+" LEFT JOIN t_pcs_goods e ON e.id=a.goodsId "
				+ " LEFT JOIN t_pcs_product f ON e.productId=f.id"
				+" WHERE  a.type=5 and a.deliverType=1 and a.batchId="+trainId;
		return executeQuery(sql);
	}

	/**
	 * 
	 * @Title:getMeasureInfo
	 * @Description:
	 * @param trainId
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getMeasureInfo(String trainId) throws Exception 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT 	");
		sb.append("	  m.id,	");
		sb.append("	  IFNULL(m.meterStart,'')meterStart,	");
		sb.append("	  IFNULL(m.meterEnd,'')meterEnd,	");
		sb.append("	  IFNULL(m.deliverNum,'')deliverNum,	");
		sb.append("	  IFNULL(m.actualMeterStart,'')actualMeterStart,	");
		sb.append("	  IFNULL(m.actualMeterEnd,'')actualMeterEnd	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_measure m,	");
		sb.append("	    t_pcs_train tr	");
		sb.append("	WHERE  tr.id=m.trainId AND m.trainId = "+trainId );
		
		return executeQuery(sb.toString());
	}

	/**
	 * 
	 * @Title:getCheckMountPageConetent
	 * @Description:
	 * @param trainId
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getCheckMountPageConetent(String trainId) throws Exception 
	{
		return null;
	}

	/**
	 * 
	 * @Title:delete
	 * @Description:
	 * @param ids
	 * @throws Exception
	 * @see
	 */
	@Override
	public void delete(String id) throws Exception 
	{
         // 车发出库撤销   goodslog type 改变，  train
		 // trainId 
		String sql1=" update t_pcs_train set status=44 where id="+id;
		String sql2=" update t_pcs_goodslog set type=8 where deliverType=1 and batchId="+id;
		executeUpdate(sql1);
		executeUpdate(sql2);
	}


	@Override
	public List<Map<String, Object>> searchLading(VehicleDeliverWorkQueryDto vehicleDeliverWorkQueryDto) throws Exception 
	{
		String sql=" SELECT  a.id ,a.code goodsCode,b.code cargoCode,b.goodsTotal cargoTotal,d.code tankCode, "
				+" (CASE WHEN !ISNULL(a.rootGoodsId) and a.rootGoodsId!=0 THEN g.code WHEN ISNULL(i.code) THEN b.code ELSE i.code END ) yuanhao, "
				+" i.code diaohao,round((a.goodsTotal-a.goodsOutPass),3) fengliang,round((a.goodsCurrent-(a.goodsTotal-a.goodsOutPass)),3) goodsCurrent, "
				+" a.goodsTotal goodsTotal,a.goodsInPass goodsInPass,i.id ladingId,i.type ladingType,DATE_FORMAT(i.endTime,'%Y-%m-%d') endTime,a.goodsCurrent currentNum from t_pcs_goods a "
				+" LEFT JOIN t_pcs_cargo b on a.cargoId=b.id "
				+" LEFT JOIN t_pcs_client c on c.id=b.clientId "
				+" LEFT JOIN t_pcs_tank d on d.id=a.tankId "
				+" LEFT JOIN t_pcs_goods e on e.id=a.rootGoodsId "
				+" LEFT JOIN t_pcs_goods_group f on f.id=e.goodsGroupId "
				+" LEFT JOIN t_pcs_lading g on g.id=f.ladingId "
				+" LEFT JOIN t_pcs_goods_group h on h.id=a.goodsGroupId "
				+" LEFT JOIN t_pcs_lading i on i.id=h.ladingId  "
				+" where 1=1 ";
		if(!Common.empty(vehicleDeliverWorkQueryDto.getClientId()))
		{
			sql+="  and ((a.clientId="+ vehicleDeliverWorkQueryDto.getClientId()+" and (ISNULL(a.ladingClientId) or a.ladingClientId=0)) or a.ladingClientId="+vehicleDeliverWorkQueryDto.getClientId()+")"; 
		}
		if(!Common.empty(vehicleDeliverWorkQueryDto.getProductId()))
		{
			sql+="  and a.productId="+vehicleDeliverWorkQueryDto.getProductId(); 
		}
		if(!Common.empty(vehicleDeliverWorkQueryDto.getLadingType()))
		{
			sql+="  and i.type="+vehicleDeliverWorkQueryDto.getLadingType() ; 
		}
		if(!Common.empty(vehicleDeliverWorkQueryDto.getLadingCode()))
		{
			sql+="  and i.code like '%"+vehicleDeliverWorkQueryDto.getLadingCode()+"%'"; 
		}
		if(vehicleDeliverWorkQueryDto.getZeroFlag()!=1)
		{
			sql+=" and round((a.goodsCurrent-(a.goodsTotal-a.goodsOutPass)),3)>0";
		}
            sql+=" order by b.code,a.code asc ";
		return executeQuery(sql);
	}
 
	/**
	 * 
	 * @Title:getStatisticsInfo
	 * @Description:
	 * @param id
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getCheckMountMsg(String id) throws Exception 
	{
		String sql = " SELECT  a.id,a.batchId,a.goodsId,ROUND(a.deliverNum,3) deliverNum,ROUND(a.actualNum,3) actualNum, a.serial serial,a.ladingEvidence ladingEvidence,"
					+" a.createTime,from_unixTime(a.createTime) createTimeStr,a.afUpNum,a.afDiffNum ,b.code goodsCode, "
					+" c.name ladingClientName,d.name productName,e.isChange,IFNULL(f.code,'') ladingCode, "
					+" round((b.goodsCurrent - (b.goodsTotal -b.goodsInPass)),3) goodsCurrent,g.status weighStatus "
					+" from t_pcs_goodslog a LEFT JOIN t_pcs_goods b ON a.goodsId=b.id "
					+" LEFT JOIN t_pcs_client c ON a.ladingClientId=c.id "
					+" LEFT JOIN t_pcs_product d ON d.id=b.productId "
					+" LEFT JOIN t_pcs_train e ON e.id=a.batchId "
					+" LEFT JOIN t_pcs_lading f on f.id=a.ladingId "
					+" LEFT JOIN t_pcs_weighbridge g on g.serial=a.serial"
					+" where a.deliverType=1 and a.type=5 and a.batchId="+id ;
		    return executeQuery(sql);
	}

	/**
	 * 
	 * @Title:savePipeNotifyInfo
	 * @Description:
	 * @param tp
	 * @param trainId
	 * @return
	 * @throws Exception
	 * @see
	 */
	@Override
	public int savePipeNotifyInfo(TransportProgram tp, String trainId) throws Exception 
	{
		Serializable s = save(tp) ;
		String sql = "update t_pcs_train set pipeTransportId="+s+" where id="+trainId ;
		executeUpdate(sql) ;
		return Integer.parseInt(s.toString()) ;
	}
	
	/**
	 * 
	 * @Title:updateVehiclePlanGoodNum
	 * @Description:
	 * @param good
	 * @throws Exception
	 * @see
	 */
	@Override
	public void updateVehiclePlanGoodNum(DeliveryGoodsRecord good) throws Exception 
	{
		
		update(good) ;
	}

	@Override
	public void saveVehiclePlanGoodNum(DeliveryGoodsRecord good)
			throws Exception {
		
		save(good) ;
	}

	
	@Override
	public void updateGoodNum(DeliveryGoodsRecord good) throws Exception {
		
		String sql = "update t_pcs_goods set goodsCurrent=ROUND(goodsCurrent-"+good.getDeliveryNum()+",3) where id="+good.getGoodsId() ;
		executeUpdate(sql) ;
	}

	@Override
	public List<Map<String, Object>> getPipeNotifyInfo(String trainId)
			throws Exception {
		String sql = "select * from t_pcs_transport_program where id = (select pipeTransportId from t_pcs_train where id="+trainId+")" ;
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getWorkCheckPipe(String trainId)
			throws Exception {
		
		String sql = "select w.*,u.name,FROM_UNIXTIME(w.checkTime) ct from t_pcs_work_check w left join t_auth_user u on  w.checkUserId=u.id where transportId =(select id from t_pcs_transport_program where id = (select pipeTransportId from t_pcs_train where id="+trainId+"))" ;
		return executeQuery(sql);
	}
	@Override
	public List<Map<String, Object>> getWorkCheckChange(String trainId)
			throws Exception {
		
		String sql = "select w.*,u.name,FROM_UNIXTIME(w.checkTime) ct from t_pcs_work_check w left join t_auth_user u on  w.checkUserId=u.id where transportId =(select id from t_pcs_transport_program where id = "+trainId+")" ;
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getChangeTankNotifyInfo(String trainId)
			throws Exception {
		String sql = "select tp.*," +
				"(SELECT n.code FROM t_pcs_notify n WHERE TYPE=17 AND batchId="+trainId+") c1" +
				",(SELECT DATE_FORMAT(FROM_UNIXTIME(n.createTime),'%Y-%m-%d') FROM t_pcs_notify n WHERE TYPE=17 AND batchId="+trainId+") checkTime" +
				" from t_pcs_transport_program tp where tp.id ="+trainId ;
		return executeQuery(sql);
	}

	@Override
	public int saveChangeTankNotifyInfo(TransportProgram tp, String trainId)
			throws Exception {
		Serializable s = save(tp) ;
		String sql = "update t_pcs_train set changeTankTransportId="+s+" where id="+trainId ;
		executeUpdate(sql) ;
		return Integer.parseInt(s.toString()) ;
	}

	@Override
	public List<Map<String, Object>> getTotalWeighInfo(String id)
			throws Exception {
		String sql = "SELECT SUM(actualNum) actualNum FROM t_pcs_measure WHERE trainId ="+id ;
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getTotalMeasureInfo(String id)
			throws Exception {
		String sql = "SELECT SUM(netWeight) netWeight,SUM(tare) tare,SUM(roughWeight) roughWeight FROM t_pcs_weigh WHERE trainId="+id ;
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getTankGroupNum(String id)
			throws Exception {
		String sql = "SELECT SUM(num1) num1,t.code  FROM (SELECT netWeight num1,tankId FROM t_pcs_weigh where trainId="+id+"  UNION ALL SELECT actualNum num1,tankId FROM t_pcs_measure where trainId="+id+") temp LEFT JOIN t_pcs_tank t ON temp.tankId=t.id  GROUP BY temp.tankId" ;
		return executeQuery(sql) ;
	}

	@Override
	public List<Map<String, Object>> getParkGroupNum(String id)
			throws Exception {
		String sql = "SELECT SUM(num1) num1,p.name FROM (SELECT netWeight num1,parkId FROM t_pcs_weigh where trainId="+id+"  UNION ALL SELECT actualNum num1,parkId FROM t_pcs_measure where trainId="+id+") temp LEFT JOIN t_pcs_park p ON temp.parkId=p.id GROUP BY temp.parkId ;" ;
		return executeQuery(sql) ;
	}

	@Override
	public void deleteVehicleInfo(String id) throws Exception {
		
		String sql = "delete from t_pcs_batch_vehicle_info where id="+id  ;
		executeUpdate(sql) ;
	}

	@Override
	public void addVehicleInfo(VehicleDeliverWorkQueryDto ladingInfo)
			throws Exception {
		
		String sql = "insert into t_pcs_batch_vehicle_info(trainId,plateId,deliverNum) values("+ladingInfo.getTrainId()+","+ladingInfo.getPlateId()+","+ladingInfo.getDeliverNum()+")" ;
		executeUpdate(sql) ;
	}
	@Override
	public void updateVehicleInfo(VehicleDeliverWorkQueryDto ladingInfo)
			throws Exception {
		
		String sql = "update t_pcs_batch_vehicle_info set trainId="+ladingInfo.getTrainId()+",plateId="+ladingInfo.getPlateId()+",deliverNum="+ladingInfo.getDeliverNum()+"where id="+ladingInfo.getVehicleInfoId() ;
		executeUpdate(sql) ;
	}

	@Override
	public void updateTrainStatus(String trainId,String status,boolean isChange) throws Exception {
		
		String sql = "update t_pcs_train set status ="+status ;
		if(isChange){
			sql += ",isChange=1 " ;
		}else{
			sql += ",isChange=0 " ;
		}
			sql += " where id="+trainId ;
		executeUpdate(sql) ;
	}

	@Override
	public List<Map<String, Object>> getVehicleDeliver(String plateId,
			String trainId) throws Exception {
		String sql = "SELECT deliverNum FROM t_pcs_batch_vehicle_info WHERE trainId="+trainId+" AND plateId="+plateId ;
		return executeQuery(sql);
	}

	@Override
	public void addLadingInfo(VehicleDeliverWorkQueryDto ladingInfo)
			throws Exception {
		
		String sql1 = "insert into t_pcs_vehicle_plan(trainId,deliverNum,ladingId) values("+ladingInfo.getTrainId()+","+ladingInfo.getDeliverNum()+","+ladingInfo.getLadingId()+") " ;
		String sql2 = "update t_pcs_lading set goodsDelivery = round((goodsDelivery+"+ladingInfo.getDeliverNum()+"),3) where id="+ladingInfo.getLadingId();
		String sql3 = "UPDATE t_pcs_train SET approveAmount=(approveAmount+"+ladingInfo.getDeliverNum()+") WHERE id ="+ladingInfo.getTrainId() ;
		execute(sql1) ;
		executeUpdate(sql2) ;
		executeUpdate(sql3) ;
	}

	@Override
	public void deleteBillInfo(String planId) throws Exception {
		
		//String sql = "update t_pcs_lading set goodsDelivery=(goodsDelivery-(select deliverNum from t_pcs_vehicle_plan where id="+planId+")) where id=(select ladingId from t_pcs_vehicle_plan where id="+planId+")" ;
		//String sql2 = "update t_pcs_train set approveAmount=(approveAmount-(select deliverNum from t_pcs_vehicle_plan where id="+planId+")) where id=(select trainId from t_pcs_vehicle_plan where id="+planId+")" ;
		String sql1 = "delete from t_pcs_vehicle_plan where id="+planId ;
		//executeUpdate(sql) ;
		//executeUpdate(sql2) ;
		executeUpdate(sql1) ;
	}

	@Override
	public List<Map<String, Object>> getTruckInvoiceMsg(String trainId) throws Exception 
	{
		String sql ="SELECT a.id,a.tankName,b.code,c.code clientCode,d.name clientName,f.name shangjihuoti,(case when ISNULL(h.name) THEN c.name ELSE h.name END ) yuanshihuoti,"
			+" CONCAT(n.NAME,'/',o.refName,'  ',DATE_FORMAT(m.arrivalStartTime,'%Y-%m-%d')) shipInfo, "
			+" (CASE WHEN !ISNULL(b.rootGoodsId) and b.rootGoodsId!=0 THEN k.code WHEN ISNULL(k.code) THEN l.code ELSE k.code END ) yuanhao, "
			+" i.code diaohao,round(ifnull(b.goodsCurrent, '0'),3) goodsCurrent,a.deliverNum,a.storageInfo,a.serial from t_pcs_goodslog a "
			+" LEFT JOIN t_pcs_goods b on b.id=a.goodsId "
			+" LEFT JOIN t_pcs_cargo l on l.id=b.cargoId "
			+" LEFT JOIN t_pcs_client c on c.id=b.clientId "
			+" LEFT JOIN t_pcs_client d on d.id=a.ladingClientId "
			+" LEFT JOIN t_pcs_goods e on e.id=b.sourceGoodId "
			+" LEFT JOIN t_pcs_client f on f.id=e.clientId "
			+" LEFT JOIN t_pcs_goods g on g.id=b.rootGoodsId "
			+" LEFT JOIN t_pcs_client h on h.id=g.clientId "
			+" LEFT JOIN t_pcs_lading i on i.id=a.ladingId "
			+" LEFT JOIN t_pcs_goods_group j on j.id=g.goodsGroupId "
			+" LEFT JOIN t_pcs_lading k on k.id=j.ladingId "
			+" LEFT JOIN t_pcs_arrival m on m.id=l.arrivalId "
			+" LEFT JOIN t_pcs_ship n on n.id=m.shipId "
			+" LEFT JOIN t_pcs_ship_ref o on o.id=m.shipRefId"
			+" WHERE 1=1 and a.type=5 and a.deliverType = 1 ";	
		if(!Common.empty(trainId))
		{
			sql+=" and a.batchId="+trainId;
			return executeQuery(sql);
		}
		return null;		
	}

	@Override
	public void saveVehiclePlan(VehiclePlan vehiclePlan)throws Exception  {
		save(vehiclePlan) ;
	}


	@Override
	public void saveBatchVehicleInfo(BatchVehicleInfo batchVehicleInfo)throws Exception {
		save(batchVehicleInfo) ;
	}

	@Override
	public void updateVehiclePlanActualNum(VehiclePlan vehiclePlan)
			throws Exception {
		update(vehiclePlan) ;
	}

	@Override
	public void updateLadingNum(VehiclePlan vehiclePlan) throws Exception {
		
		String sql = "update t_pcs_lading set goodsDelivery=round(goodsDelivery+"+vehiclePlan.getActualNum()+"-"+vehiclePlan.getDeliverNum()+"),3) where id="+vehiclePlan.getLadingId() ;
		executeUpdate(sql) ;
	}

	@Override
	public List<Map<String, Object>> getTrainList(VehicleDeliverWorkQueryDto vDto, int start, int limit)
			throws OAException {
		try{
			String sql=" SELECT  a.id,DATE_FORMAT(a.deliverTime,'%Y-%m-%d %H:%i:%s') deliverTime, "
						+" c.code plateName,d.name clientName,j.productId productId, "
						+" f.name productName,a.operator,e.name userName,b.id goodsLogId, "
						+" a.status,g.value statusValue,b.actualNum actualNum,b.deliverNum deliverNum,"
						+ "(case when a.status=43 then (select m.code from t_pcs_weighbridge l inner join t_pcs_tank m on l.tankId=m.id where l.serial=b.serial limit 0,1) else b.tankName end) tankName, "
						+" FROM_UNIXTIME(b.createTime) createTime,FROM_UNIXTIME(b.invoiceTime) invoiceTime,b.serial serial "
						+" from t_pcs_train a "
						+" INNER JOIN t_pcs_goodslog b on a.id=b.batchId AND b.type=5 AND b.deliverType=1 "
						+" INNER JOIN t_pcs_truck c on a.plateId=c.id "
						+" INNER JOIN t_pcs_client d on a.clientId=d.id "
						+" INNER JOIN t_auth_user e on e.id=a.operator "
						+" INNER JOIN t_pcs_goods j on j.id=b.goodsId"
						+" INNER JOIN t_pcs_product f on f.id=j.productId "
						+" INNER JOIN t_pcs_arrival_status g on g.key=a.status, "
			            +" (SELECT DISTINCT h.id from t_pcs_train h ";
			 if(!Common.empty(vDto.getStartTime())&&vDto.getStartTime().indexOf("-1")==-1||!Common.empty(vDto.getEndTime())&&vDto.getEndTime().indexOf("-1")==-1||!Common.empty(vDto.getSerialNum())
					 ||!Common.empty(vDto.getLadingCode())||!Common.empty(vDto.getProductId())&&vDto.getProductId()!=null){
				  
			           sql+= " INNER join t_pcs_goodslog i on h.id=i.batchId and i.deliverType=1 "
			           		+ " INNER JOIN t_pcs_goods k on i.goodsId=k.id";
			          if(!Common.empty(vDto.getStartTime())&&vDto.getStartTime().indexOf("-1")==-1){
			        	  sql+=" and i.invoiceTime >="+vDto.getStartTime();
			          }
			           if(!Common.empty(vDto.getEndTime())&&vDto.getEndTime().indexOf("-1")==-1){
			        	   sql+=" and i.invoiceTime <="+vDto.getEndTime();
			           }
			           if(!Common.empty(vDto.getSerialNum())){
			        	   sql+=" and i.serial like '%"+vDto.getSerialNum()+"%'";
			           }
			           if(!Common.empty(vDto.getLadingCode())){
			        	   sql+=" and i.ladingEvidence like '%"+vDto.getLadingCode()+"%'";
			           }}
			            sql+= " where h.status!=44 ";
			            if(!Common.empty(vDto.getTrainId())&&vDto.getTrainId()!=null){
			            	sql+=" and h.plateId="+vDto.getTrainId();
			            }
			            if(!Common.empty(vDto.getClientId())&&vDto.getClientId()!=null){
			            	sql+=" and h.clientId="+vDto.getClientId();
			            }
			            if(!Common.empty(vDto.getProductId())&&vDto.getProductId()!=null){
			            	sql+=" and k.productId="+vDto.getProductId();
			            }
			            if(!Common.empty(vDto.getStatus())&&vDto.getStatus()!=null){
			            	sql+=" and h.status="+vDto.getStatus();
			            }
			            
			            sql+=" ORDER BY h.id desc ";
			        if(limit!=0){
						sql+=" LIMIT "+start+" , "+limit;
			        }
			        sql+= " ) tb  where 1=1  AND tb.id=a.id ";
			return executeQuery(sql);
		}catch (RuntimeException e) {
			logger.error("dao获取车发出库列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取车发出库列表失败", e);
		}
	}

	@Override
	public int getTrainListCount(
			VehicleDeliverWorkQueryDto vDto)
			throws OAException {
		try{
	String sql=" SELECT  count(DISTINCT h.id) from t_pcs_train h" ;
	          if(!Common.empty(vDto.getStartTime())&&vDto.getStartTime().indexOf("-1")==-1||!Common.empty(vDto.getEndTime())&&vDto.getEndTime().indexOf("-1")==-1
	        		  ||!Common.empty(vDto.getSerialNum())||!Common.empty(vDto.getLadingCode())||!Common.empty(vDto.getProductId())&&vDto.getProductId()!=null){
			     sql+= " inner join t_pcs_goodslog i on h.id=i.batchId and i.deliverType=1 "
			    		 + " INNER JOIN t_pcs_goods k on i.goodsId=k.id ";;
		          if(!Common.empty(vDto.getStartTime())&&vDto.getStartTime().indexOf("-1")==-1){
		        	  sql+=" and i.createTime >="+vDto.getStartTime();
		          }
		           if(!Common.empty(vDto.getEndTime())&&vDto.getEndTime().indexOf("-1")==-1){
		        	   sql+=" and i.createTime <="+vDto.getEndTime();
		           }
		           if(!Common.empty(vDto.getSerialNum())){
		        	   sql+=" and i.serial like '%"+vDto.getSerialNum()+"%'";
		           }
		           if(!Common.empty(vDto.getLadingCode())){
		        	   sql+=" and i.ladingEvidence like '%"+vDto.getLadingCode()+"%'";
		           }
		           }
		            sql+= " where h.status!=44 ";
		            if(!Common.empty(vDto.getTrainId())&&vDto.getTrainId()!=null){
		            	sql+=" and h.plateId="+vDto.getTrainId();
		            }
		            if(!Common.empty(vDto.getClientId())&&vDto.getClientId()!=null){
		            	sql+=" and h.clientId="+vDto.getClientId();
		            }
		            if(!Common.empty(vDto.getProductId())&&vDto.getProductId()!=null){
		            	sql+=" and k.productId="+vDto.getProductId();
		            }
		            return (int) getCount(sql);
		}catch (RuntimeException e) {
			logger.error("dao获取车发出库列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取车发出库列表数量失败", e);
		}        
	}
	
	
}
