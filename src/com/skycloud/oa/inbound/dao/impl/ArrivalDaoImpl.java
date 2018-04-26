package com.skycloud.oa.inbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalDao;
import com.skycloud.oa.inbound.dao.ArrivalPlanDao;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.utils.Common;
@Component
public class ArrivalDaoImpl extends BaseDaoImpl implements ArrivalDao {

	private static Logger LOG = Logger.getLogger(ArrivalDaoImpl.class);

	@Override
	public int addIntoArrival(Arrival arrival) {
		try {
			Serializable s=save(arrival);
			return Integer.parseInt(s.toString());
		} catch (OAException e) {
			e.printStackTrace();
		}
		return 0;
	}



	@Override
	public Map<String,Object> getArrivalById(int arrivalid) {
		String sql="SELECT  a.*,max(b.status) workStatus FROM t_pcs_arrival a LEFT JOIN t_pcs_work b on b.arrivalId=a.id where a.id="+arrivalid;
		try {
			Map<String, Object> result=executeQueryOne(sql);
			return result;
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateArrival(Arrival arrival) {
		String sql="update t_pcs_arrival set id=id";
		if(!Common.empty(arrival.getDescription())){
			sql+=" ,description='"+arrival.getDescription()+"'";
		}
		if(!Common.isNull(arrival.getShipAgentId())){
			if(arrival.getShipAgentId()==-1){
				sql+=" ,shipAgentId=null";	
			}else{
			sql+=" ,shipAgentId="+arrival.getShipAgentId();
			}
		}
		if(!Common.isNull(arrival.getShipId())){
			sql+=" ,shipId="+arrival.getShipId();
		}
		if(!Common.isNull(arrival.getShipRefId())){
			sql+=" ,shipRefId="+arrival.getShipRefId();
		}
		if(!Common.isNull(arrival.getStatus())&&arrival.getStatus()!=-1){
			sql+=" ,status="+arrival.getStatus();
		}else if(arrival.getStatus()==-1){
			sql+=" ,status=0";
		}
		if(!Common.isNull(arrival.getType())){
			sql+=" ,type="+arrival.getType();
		}
		if(!Common.isNull(arrival.getBerthId())){
			sql+=" ,berthId="+arrival.getBerthId();
		}
		if(!Common.empty(arrival.getArrivalStartTime())){
			sql+=" ,arrivalStartTime='"+arrival.getArrivalStartTime()+"'";
		}
		if(!Common.empty(arrival.getArrivalEndTime())){
			sql+=" ,arrivalEndTime='"+arrival.getArrivalEndTime()+"'";
		}
		if(!Common.isNull(arrival.getDispatchId())){
			sql+=" ,dispatchId="+arrival.getDispatchId();
		}
		if(!Common.isNull(arrival.getCreateUserId())){
			sql+=" ,createUserId="+arrival.getCreateUserId();
		}
		if(!Common.isNull(arrival.getCreateTime())){
			sql+=" ,createTime="+arrival.getCreateTime();
		}
		if(!Common.isNull(arrival.getReviewUserId())){
			sql+=", reviewUserId="+arrival.getReviewUserId();
		}
		if(!Common.isNull(arrival.getReviewTime())){
			sql+=", reviewTime="+arrival.getReviewTime();
		}
		if(!Common.isNull(arrival.getReviewArrivalUserId())){
			sql+=" ,reviewArrivalUserId="+arrival.getReviewArrivalUserId();
		}
		if(!Common.isNull(arrival.getReviewArrivalTime())){
			sql+=" ,reviewArrivalTime="+arrival.getReviewArrivalTime();
		}
		if(!Common.isNull(arrival.getReviewStatus())){
			sql+=" ,reviewStatus="+arrival.getReviewStatus();
		}
		if(!Common.isNull(arrival.getIsCanBack())){
			sql+=" ,isCanBack="+arrival.getIsCanBack();
		}
		if(!Common.empty(arrival.getIsCustomAgree())){
			sql+=" ,isCustomAgree="+arrival.getIsCustomAgree();
		}
		if(!Common.empty(arrival.getIsDeclareCustom())){
			sql+=" ,isDeclareCustom="+arrival.getIsDeclareCustom();
		}
		if(!Common.empty(arrival.getCustomLading())){
			sql+=" ,customLading='"+arrival.getCustomLading()+"'";
		}
		if(!Common.empty(arrival.getCustomLadingCount())){
			sql+=" ,customLadingCount='"+arrival.getCustomLadingCount()+"'";
		}
		
		if(!Common.empty(arrival.getIsTrim())){
			sql+=" ,isTrim="+arrival.getIsTrim();
		}
		if(!Common.empty(arrival.getOriginalArea())){
			sql+=" ,originalArea='"+arrival.getOriginalArea()+"'";
		}
		sql+=" where id="+arrival.getId();
		try {
		execute(sql);
//			update(arrival);
		} catch (OAException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Map<String, Object>> getArrivalList(ArrivalDto arrivalDto,int start,int limit) {
		String sql="SELECT  tpa.originalArea ,ai.id arrivalInfoId,ai.shipArrivalDraught,(select count(tpc.id) from t_pcs_cargo tpc where tpc.arrivalId=tpa.id and (isnull(tpc.contractId) or tpc.contractId=0)) unContract,tpa.*,tps.shipLenth,tps.shipWidth,tps.shipDraught,tps.loadCapacity,DATE_FORMAT(tpa.arrivalStartTime,'%Y-%m-%d') mArrivalTime,DATE_FORMAT(tpa.arrivalEndTime,'%Y-%m-%d') mEndTime, tps.name shipName,tpsa.name shipAgentName,tpas.value statusName,tpsr.refName shipRefName,u.name reviewArrivalUserName,from_unixtime(tpa.reviewArrivalTime) mReviewArrivalTime,tpa.isTrim FROM t_pcs_arrival tpa " +
				" INNER JOIN t_pcs_ship tps on tps.id=tpa.shipId LEFT JOIN t_pcs_ship_agent tpsa on " +
				" tpsa.id=tpa.shipAgentId  INNER JOIN t_pcs_arrival_status tpas on tpas.key=tpa.status LEFT JOIN t_pcs_ship_ref tpsr on tpa.shipRefId=tpsr.id " +
				" LEFT JOIN t_auth_user u on u.id=tpa.reviewArrivalUserId LEFT JOIN t_pcs_arrival_info ai on ai.arrivalId=tpa.id where 1=1";
		if(!Common.empty(arrivalDto.getShipAgentId())&&0!=arrivalDto.getShipAgentId()){
			sql+=" and tpa.shipAgentId="+arrivalDto.getShipAgentId();
		}
		if(!Common.empty(arrivalDto.getShipId())&&0!=arrivalDto.getShipId()){
			sql+=" and tpa.shipId="+arrivalDto.getShipId();
		}
		if(!Common.empty(arrivalDto.getStatus())&&0!=arrivalDto.getStatus()){
			sql+=" and tpa.status="+arrivalDto.getStatus();
		}
		if(!Common.empty(arrivalDto.getArrivalStatus())&&0!=arrivalDto.getArrivalStatus()){
			if(arrivalDto.getArrivalStatus()==1){
				sql+=" and (select COALESCE(sum(tpw.status) MOD 9,1)  from t_pcs_work tpw where tpw.arrivalId=tpa.id)>0 ";
				
			}
			if(arrivalDto.getArrivalStatus()==2){
				sql+=" and (select sum(tpw.status) mod 9  from t_pcs_work tpw where tpw.arrivalId=tpa.id)=0 ";
				
			}
			
			if(arrivalDto.getArrivalStatus()==5){
				sql+=" and (select count(1) from t_pcs_arrival_foreshow tpaf where tpaf.arrivalId=tpa.id)=0 and tpa.type<>10 and tpa.type<>9 ";
			}
			
			if(arrivalDto.getArrivalStatus()==4){
				sql+=" and tpa.type =10";
			}
			
		}
		if(!Common.empty(arrivalDto.getId())&&0!=arrivalDto.getId()){
			sql+=" and tpa.id="+arrivalDto.getId();
		}
		if(!Common.empty(arrivalDto.getStartTime())&&!"-1".equals(arrivalDto.getStartTime())){
			sql+=" and tpa.arrivalStartTime>='"+arrivalDto.getStartTime()+"'";
		}
		if(!Common.empty(arrivalDto.getEndTime())&&!"-1".equals(arrivalDto.getEndTime())){
			sql+=" and tpa.arrivalStartTime<='"+arrivalDto.getEndTime()+"'";
		}
		if(!Common.empty(arrivalDto.getShipName())){
			sql+=" and tps.name like '%"+arrivalDto.getShipName()+"%'";
		}
		if(!Common.isNull(arrivalDto.getClientId())&&arrivalDto.getClientId()!=-1){
			sql+=" and (select count(1) from t_pcs_cargo tpc1 where tpc1.arrivalId=tpa.id and tpc1.clientId="+arrivalDto.getClientId()+")>0 ";
		}
		if(!Common.isNull(arrivalDto.getProductId())&&arrivalDto.getProductId()!=-1){
			sql+=" and (select count(1) from t_pcs_cargo tpc1 where tpc1.arrivalId=tpa.id and tpc1.productId="+arrivalDto.getProductId()+")>0 ";
		}
		if(!Common.empty(arrivalDto.getInboundCode())&&!arrivalDto.getInboundCode().equals("-1")){
			sql+=" and (select count(1) from t_pcs_cargo tpc1 where tpc1.arrivalId=tpa.id and tpc1.No like '%"+arrivalDto.getInboundCode()+"%')>0 ";
		}
		if(!Common.isNull(arrivalDto.getType())){
			if(arrivalDto.getType()==13){
				
				if(!Common.empty(arrivalDto.getArrivalStatus())&&0!=arrivalDto.getArrivalStatus()){
					if(arrivalDto.getArrivalStatus()!=4){
						sql+=" and tpa.type in (1,3)";
					}
				}else{
					sql+=" and tpa.type in (1,3)";
				}
					

			}else {
				
				sql+=" and tpa.type="+arrivalDto.getType();
			}
		}
		if(limit!=0){
			sql+=" order by tpa.arrivalStartTime DESC limit "+start+","+limit;
		}else{
			sql+=" order by tpa.arrivalStartTime DESC";
		}
			try {
				return executeQuery(sql);
			} catch (OAException e) {
				e.printStackTrace();
			}
		return null;
	}



	@Override
	public int getArrivalListCount(ArrivalDto arrivalDto) {
		String sql="SELECT COUNT(*) FROM t_pcs_arrival tpa " +
				" INNER JOIN t_pcs_ship tps on tps.id=tpa.shipId LEFT JOIN t_pcs_ship_agent tpsa on " +
				" tpsa.id=tpa.shipAgentId  where 1=1";
		if(!Common.empty(arrivalDto.getShipAgentId())&&0!=arrivalDto.getShipAgentId()){
			sql+=" and tpa.shipAgentId="+arrivalDto.getShipAgentId();
		}
		if(!Common.empty(arrivalDto.getShipId())&&0!=arrivalDto.getShipId()){
			sql+=" and tpa.shipId="+arrivalDto.getShipId();
		}
		if(!Common.empty(arrivalDto.getStatus())&&0!=arrivalDto.getStatus()){
			sql+=" and tpa.status="+arrivalDto.getStatus();
		}
		if(!Common.empty(arrivalDto.getId())&&0!=arrivalDto.getId()){
			sql+=" and tpa.id="+arrivalDto.getId();
		}
		if(!Common.empty(arrivalDto.getStartTime())&&!"-1".equals(arrivalDto.getStartTime())){
			sql+=" and tpa.arrivalStartTime>='"+arrivalDto.getStartTime()+"'";
		}
		if(!Common.empty(arrivalDto.getEndTime())&&!"-1".equals(arrivalDto.getEndTime())){
			sql+=" and tpa.arrivalStartTime<='"+arrivalDto.getEndTime()+"'";
		}
		if(!Common.empty(arrivalDto.getShipName())){
			sql+=" and tps.name like '%"+arrivalDto.getShipName()+"%'";
		}
		if(!Common.empty(arrivalDto.getArrivalStatus())&&0!=arrivalDto.getArrivalStatus()){
			if(arrivalDto.getArrivalStatus()==1){
				sql+=" and (select COALESCE(sum(tpw.status) MOD 9,1)  from t_pcs_work tpw where tpw.arrivalId=tpa.id)>0 ";
				
			}
			if(arrivalDto.getArrivalStatus()==2){
				sql+=" and (select sum(tpw.status) mod 9  from t_pcs_work tpw where tpw.arrivalId=tpa.id)=0 ";
				
			}
			
			if(arrivalDto.getArrivalStatus()==5){
				sql+=" and (select count(1) from t_pcs_arrival_foreshow tpaf where tpaf.arrivalId=tpa.id)=0 and tpa.type<>10 and tpa.type<>9 ";
			}
			if(arrivalDto.getArrivalStatus()==4){
				sql+=" and tpa.type =10";
			}
		}
		if(!Common.isNull(arrivalDto.getClientId())&&arrivalDto.getClientId()!=-1){
			sql+=" and (select count(1) from t_pcs_cargo tpc1 where tpc1.arrivalId=tpa.id and tpc1.clientId="+arrivalDto.getClientId()+")>0 ";
		}
		if(!Common.isNull(arrivalDto.getProductId())&&arrivalDto.getProductId()!=-1){
			sql+=" and (select count(1) from t_pcs_cargo tpc1 where tpc1.arrivalId=tpa.id and tpc1.productId="+arrivalDto.getProductId()+")>0 ";
		}
		if(!Common.empty(arrivalDto.getInboundCode())&&!arrivalDto.getInboundCode().equals("-1")){
			sql+=" and (select count(1) from t_pcs_cargo tpc1 where tpc1.arrivalId=tpa.id and tpc1.No like '%"+arrivalDto.getInboundCode()+"%')>0 ";
		}
		if(!Common.isNull(arrivalDto.getType())){
			if(arrivalDto.getType()==13){
				
				if(!Common.empty(arrivalDto.getArrivalStatus())&&0!=arrivalDto.getArrivalStatus()){
					if(arrivalDto.getArrivalStatus()!=4){
						sql+=" and tpa.type in (1,3)";
					}
				}else{
					sql+=" and tpa.type in (1,3)";
				}
					
			}else {
				
				sql+=" and tpa.type="+arrivalDto.getType();
			}
		}
			return (int) getCount(sql);
	}



	@Override
	public void deleteArrival(String id) {
		String sql="update t_pcs_arrival set type=10 where id="+id;
		String sql1="delete from t_pcs_cargo where arrivalId="+id;
//		String sql1="delete from t_pcs_arrival_plan where arrivalId in("+id+")";
		String sql2="update t_pcs_arrival_plan set status=1 where arrivalId="+id;
		String sql3="delete from t_pcs_arrival_foreshow  where arrivalId="+id;
		
		try {
			execute(sql);
			execute(sql1);
			execute(sql2);
			execute(sql3);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public List<Map<String,Object>> getshipId(Integer goodsId) throws OAException {
		String sql="select a.shipId from t_pcs_arrival a LEFT JOIN t_pcs_cargo b on b.arrivalId=a.id LEFT JOIN t_pcs_goods c on c.cargoId=b.id " +
				"where c.id="+goodsId;
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "操作失败",e);
		}
		
	}



	@Override
	public List<Map<String, Object>> getArrivalInfo(ArrivalDto arrivalDto)throws OAException  {
		String sql="select a.id arrivalInfoId,from_unixtime(a.cjTime) nCjTime,from_unixtime(a.tcTime) nTcTime,from_unixtime(a.norTime) nNorTime,from_unixtime(a.anchorTime) nAnchorTime,from_unixtime(a.pumpOpenTime) nPumpOpenTime,from_unixtime(a.pumpStopTime) nPumpStopTime,from_unixtime(a.leaveTime) nLeaveTime,from_unixtime(a.tearPipeTime) nTearPipeTime,b.* from t_pcs_arrival_info a LEFT JOIN t_pcs_arrival b on b.id=a.arrivalId where 1=1";
		
		if(!Common.empty(arrivalDto.getArrivalInfoId())){
			sql+=" and a.id="+arrivalDto.getArrivalInfoId();
		}
		if(!Common.empty(arrivalDto.getId())){
			sql+=" and a.arrivalId="+arrivalDto.getId();
		}
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "操作失败",e);
		}
		
	}

}
