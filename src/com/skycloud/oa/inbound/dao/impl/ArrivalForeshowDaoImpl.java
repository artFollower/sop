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
import com.skycloud.oa.inbound.dao.ArrivalForeshowDao;
import com.skycloud.oa.inbound.dao.ArrivalPlanDao;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalForeshow;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.utils.Common;
@Component
public class ArrivalForeshowDaoImpl extends BaseDaoImpl implements ArrivalForeshowDao {

	private static Logger LOG = Logger.getLogger(ArrivalForeshowDaoImpl.class);

	@Override
	public int addIntoArrivalForeshow(ArrivalForeshow arrivalForeshow) {
		try {
			Serializable s=save(arrivalForeshow);
			return Integer.parseInt(s.toString());
		} catch (OAException e) {
			e.printStackTrace();
		}
		return 0;
	}



	@Override
	public void deleteArrivalForeshow(String id) {
		String sql="delete from t_pcs_arrival_foreshow where id in("+id+")";
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public void updateArrivalForeshow(ArrivalForeshow arrivalForeshow) {
		String sql="update t_pcs_arrival_foreshow set id=id";
		
		if(!Common.isNull(arrivalForeshow.getCjTime())&&arrivalForeshow.getCjTime()!=-1){
			sql+=" ,cjTime="+arrivalForeshow.getCjTime();
		}else if(!Common.isNull(arrivalForeshow.getCjTime())&&arrivalForeshow.getCjTime()==-1){
			sql+=" ,cjTime=null";
		}
		
		if(!Common.empty(arrivalForeshow.getTcTime())&&!"-1".equals(arrivalForeshow.getTcTime())){
			sql+=" ,tcTime='"+arrivalForeshow.getTcTime()+"'";
		}else if(!Common.empty(arrivalForeshow.getTcTime())&&"-1".equals(arrivalForeshow.getTcTime())){
			sql+=" ,tcTime=null";
		}
		
		if(!Common.isNull(arrivalForeshow.getNorTime())&&arrivalForeshow.getNorTime()!=-1){
			sql+=" ,norTime="+arrivalForeshow.getNorTime();
		}else if(!Common.isNull(arrivalForeshow.getNorTime())&&arrivalForeshow.getNorTime()==-1){
			sql+=" ,norTime=null";
		}
		
//		if(!Common.isNull(arrivalForeshow.getAnchorDate())&&arrivalForeshow.getAnchorDate()!=-1){
//			sql+=" ,anchorDate="+arrivalForeshow.getAnchorDate();
//		}else if(arrivalForeshow.getAnchorDate()==-1){
//			sql+=" ,anchorDate=null";
//		}
		
		if(!Common.isNull(arrivalForeshow.getAnchorTime())&&arrivalForeshow.getAnchorTime()!=-1){
			sql+=" ,anchorTime="+arrivalForeshow.getAnchorTime();
		}else if(!Common.isNull(arrivalForeshow.getAnchorTime())&&arrivalForeshow.getAnchorTime()==-1){
			sql+=" ,anchorTime=null";
		}
		
		if(!Common.isNull(arrivalForeshow.getPumpOpenTime())&&arrivalForeshow.getPumpOpenTime()!=-1){
			sql+=" ,pumpOpenTime="+arrivalForeshow.getPumpOpenTime();
		}else if(!Common.isNull(arrivalForeshow.getPumpOpenTime())&&arrivalForeshow.getPumpOpenTime()==-1){
			sql+=" ,pumpOpenTime=null";
		}
		if(!Common.isNull(arrivalForeshow.getPumpStopTime())&&arrivalForeshow.getPumpStopTime()!=-1){
			sql+=" ,pumpStopTime="+arrivalForeshow.getPumpStopTime();
		}else if(!Common.isNull(arrivalForeshow.getPumpStopTime())&&arrivalForeshow.getPumpStopTime()==-1){
			sql+=" ,pumpStopTime=null";
		}
		
		if(!Common.empty(arrivalForeshow.getWorkTime())&&!"-1".equals(arrivalForeshow.getWorkTime())){
			sql+=" ,workTime='"+arrivalForeshow.getWorkTime()+"'";
		}else if(!Common.empty(arrivalForeshow.getWorkTime())&&"-1".equals(arrivalForeshow.getWorkTime())){
			sql+=" ,workTime=null";
		}
		
		if(!Common.isNull(arrivalForeshow.getLeaveTime())&&arrivalForeshow.getLeaveTime()!=-1){
			sql+=" ,leaveTime="+arrivalForeshow.getLeaveTime();
		}else if(!Common.isNull(arrivalForeshow.getLeaveTime())&&arrivalForeshow.getLeaveTime()==-1){
			sql+=" ,leaveTime=null";
		}
		
		
		if(!Common.isNull(arrivalForeshow.getBerth())&&arrivalForeshow.getBerth()!=-1){
			sql+=" ,berth="+arrivalForeshow.getBerth();
		}else if(!Common.isNull(arrivalForeshow.getBerth())&&arrivalForeshow.getBerth()==-1){
			sql+=" ,berth=null";
		}
		
		if(!Common.isNull(arrivalForeshow.getShipRefId())&&arrivalForeshow.getShipRefId()!=-1){
			sql+=" ,shipRefId="+arrivalForeshow.getShipRefId();
		}else if(!Common.isNull(arrivalForeshow.getShipRefId())&&arrivalForeshow.getShipRefId()==-1){
			sql+=" ,shipRefId=null";
		}
		
		if(!Common.empty(arrivalForeshow.getProductIds())&&!"-1".equals(arrivalForeshow.getProductIds())){
			sql+=" ,productIds='"+arrivalForeshow.getProductIds()+"'";
		}else if(!Common.empty(arrivalForeshow.getProductIds())&&"-1".equals(arrivalForeshow.getProductIds())){
			sql+=" ,productIds=null";
		}
		
		
		if(!Common.empty(arrivalForeshow.getProductNames())&&!"-1".equals(arrivalForeshow.getProductNames())){
			sql+=" ,productNames='"+arrivalForeshow.getProductNames()+"'";
		}else if(!Common.empty(arrivalForeshow.getProductNames())&&"-1".equals(arrivalForeshow.getProductNames())){
			sql+=" ,productNames=null";
		}
		
		if(!Common.empty(arrivalForeshow.getCount())&&!"-1".equals(arrivalForeshow.getCount())){
			sql+=" ,count='"+arrivalForeshow.getCount()+"'";
		}else if(!Common.empty(arrivalForeshow.getCount())&&"-1".equals(arrivalForeshow.getCount())){
			sql+=" ,count=null";
		}
		
		if(!Common.empty(arrivalForeshow.getShipLenth())&&!"-1".equals(arrivalForeshow.getShipLenth())){
			sql+=" ,shipLenth='"+arrivalForeshow.getShipLenth()+"'";
		}else if(!Common.empty(arrivalForeshow.getShipLenth())&&"-1".equals(arrivalForeshow.getShipLenth())){
			sql+=" ,shipLenth=null";
		}
		
		if(!Common.empty(arrivalForeshow.getShipArrivalDraught())&&!"-1".equals(arrivalForeshow.getShipArrivalDraught())){
			sql+=" ,shipArrivalDraught='"+arrivalForeshow.getShipArrivalDraught()+"'";
		}else if(!Common.empty(arrivalForeshow.getShipArrivalDraught())&&"-1".equals(arrivalForeshow.getShipArrivalDraught())){
			sql+=" ,shipArrivalDraught=null";
		}
		
		if(!Common.isNull(arrivalForeshow.getShipAgentId())&&arrivalForeshow.getShipAgentId()!=-1){
			sql+=" ,shipAgentId="+arrivalForeshow.getShipAgentId();
		}else if(!Common.isNull(arrivalForeshow.getShipAgentId())&&arrivalForeshow.getShipAgentId()==-1){
			sql+=" ,shipAgentId=null";
		}
		
		if(!Common.empty(arrivalForeshow.getCargoAgentIds())&&!"-1".equals(arrivalForeshow.getCargoAgentIds())){
			sql+=" ,cargoAgentIds='"+arrivalForeshow.getCargoAgentIds()+"'";
		}else if(!Common.empty(arrivalForeshow.getCargoAgentIds())&&"-1".equals(arrivalForeshow.getCargoAgentIds())){
			sql+=" ,cargoAgentIds=null";
		}
		if(!Common.empty(arrivalForeshow.getCargoAgentNames())&&!"-1".equals(arrivalForeshow.getCargoAgentNames())){
			sql+=" ,cargoAgentNames='"+arrivalForeshow.getCargoAgentNames()+"'";
		}else if(!Common.empty(arrivalForeshow.getCargoAgentNames())&&"-1".equals(arrivalForeshow.getCargoAgentNames())){
			sql+=" ,cargoAgentNames=null";
		}
	

		if(!Common.empty(arrivalForeshow.getPort())&&!"-1".equals(arrivalForeshow.getPort())){
			sql+=" ,port='"+arrivalForeshow.getPort()+"'";
		}else if(!Common.empty(arrivalForeshow.getPort())&&"-1".equals(arrivalForeshow.getPort())){
			sql+=" ,port=null";
		}

		if(!Common.empty(arrivalForeshow.getUnloadNotify())&&!"-1".equals(arrivalForeshow.getUnloadNotify())){
			sql+=" ,unloadNotify='"+arrivalForeshow.getUnloadNotify()+"'";
		}else if(!Common.empty(arrivalForeshow.getUnloadNotify())&&"-1".equals(arrivalForeshow.getUnloadNotify())){
			sql+=" ,unloadNotify=null";
		}

		if(!Common.empty(arrivalForeshow.getIsCustomAgree())&&arrivalForeshow.getIsCustomAgree()!=-1){
			sql+=" ,isCustomAgree="+arrivalForeshow.getIsCustomAgree();
		}else if(!Common.empty(arrivalForeshow.getIsCustomAgree())&&arrivalForeshow.getIsCustomAgree()==-1){
			sql+=" ,isCustomAgree=null";
		}
		
		if(!Common.empty(arrivalForeshow.getNote())&&!"-1".equals(arrivalForeshow.getNote())){
			sql+=" ,note='"+arrivalForeshow.getNote()+"'";
		}else if(!Common.empty(arrivalForeshow.getNote())&&"-1".equals(arrivalForeshow.getNote())){
			sql+=" ,note=null";
		}
		
		if(!Common.empty(arrivalForeshow.getPortNum())&&!"-1".equals(arrivalForeshow.getPortNum())){
			sql+=" ,portNum='"+arrivalForeshow.getPortNum()+"'";
		}else if(!Common.empty(arrivalForeshow.getPortNum())&&"-1".equals(arrivalForeshow.getPortNum())){
			sql+=" ,portNum=null";
		}
		
		if(!Common.empty(arrivalForeshow.getShipInfo())&&!"-1".equals(arrivalForeshow.getShipInfo())){
			sql+=" ,shipInfo='"+arrivalForeshow.getShipInfo()+"'";
		}else if(!Common.empty(arrivalForeshow.getShipInfo())&&"-1".equals(arrivalForeshow.getShipInfo())){
			sql+=" ,shipInfo=null";
		}
		
		if(!Common.empty(arrivalForeshow.getClientIds())&&!"-1".equals(arrivalForeshow.getClientIds())){
			sql+=" ,clientIds='"+arrivalForeshow.getClientIds()+"'";
		}else if(!Common.empty(arrivalForeshow.getClientIds())&&"-1".equals(arrivalForeshow.getClientIds())){
			sql+=" ,clientIds=null";
		}
		
		if(!Common.empty(arrivalForeshow.getClientNames())&&!"-1".equals(arrivalForeshow.getClientNames())){
			sql+=" ,clientNames='"+arrivalForeshow.getClientNames()+"'";
		}
		else if(!Common.empty(arrivalForeshow.getClientNames())&&"-1".equals(arrivalForeshow.getClientNames())){
			sql+=" ,clientNames=null";
		}
		
		if(!Common.empty(arrivalForeshow.getReport())&&!"-1".equals(arrivalForeshow.getReport())){
			sql+=" ,report='"+arrivalForeshow.getReport()+"'";
		}else if(!Common.empty(arrivalForeshow.getReport())&&"-1".equals(arrivalForeshow.getReport())){
			sql+=" ,report=null";
		}
		
		if(!Common.isNull(arrivalForeshow.getLastLeaveTime())&&arrivalForeshow.getLastLeaveTime()!=-1){
			sql+=" ,lastLeaveTime="+arrivalForeshow.getLastLeaveTime();
		}else if(!Common.isNull(arrivalForeshow.getLastLeaveTime())&&arrivalForeshow.getLastLeaveTime()==-1){
			sql+=" ,lastLeaveTime=null";
		}
		
		if(!Common.isNull(arrivalForeshow.getTearPipeTime())&&arrivalForeshow.getTearPipeTime()!=-1){
			sql+=" ,tearPipeTime="+arrivalForeshow.getTearPipeTime();
		}else if(!Common.isNull(arrivalForeshow.getTearPipeTime())&&arrivalForeshow.getTearPipeTime()==-1){
			sql+=" ,tearPipeTime=null";
		}
		if(!Common.isNull(arrivalForeshow.getRepatriateTime())&&arrivalForeshow.getRepatriateTime()!=-1){
			sql+=" ,repatriateTime="+arrivalForeshow.getRepatriateTime();
		}else if(!Common.isNull(arrivalForeshow.getRepatriateTime())&&arrivalForeshow.getRepatriateTime()==-1){
			sql+=" ,repatriateTime=null";
		}
		
		if(!Common.isNull(arrivalForeshow.getOverTime())&&arrivalForeshow.getOverTime()!=-1){
			sql+=" ,overTime="+arrivalForeshow.getOverTime();
		}else if(!Common.isNull(arrivalForeshow.getOverTime())&&arrivalForeshow.getOverTime()==-1){
			sql+=" ,overTime=null";
		}
		
		if(!Common.isNull(arrivalForeshow.getArrivalId())){
			sql+=" ,arrivalId="+arrivalForeshow.getArrivalId();
		}
		
		if(!Common.isNull(arrivalForeshow.getCreateUserId())){
			sql+=" ,createUserId="+arrivalForeshow.getCreateUserId();
		}
		
		if(!Common.isNull(arrivalForeshow.getCreateTime())){
			sql+=" ,createTime="+arrivalForeshow.getCreateTime();
		}
		
		if(!Common.isNull(arrivalForeshow.getStatus())){
			sql+=" ,status="+arrivalForeshow.getStatus();
		}
		if(!Common.isNull(arrivalForeshow.getColor())){
			sql+=" ,color="+arrivalForeshow.getColor();
		}
		sql+=" where id="+arrivalForeshow.getId();
		try {
		execute(sql);
//			update(arrival);
		} catch (OAException e) {
			e.printStackTrace();
		}
		
	}



	@Override
	public int getArrivalForeshowListCount(ArrivalDto arrivalDto) {
		String sql="select count(a.id) from t_pcs_arrival_foreshow a " +
				"LEFT JOIN t_auth_user b on a.createUserId=b.id " +
				"LEFT JOIN t_pcs_ship_ref c on c.id=a.shipRefId " +
				"LEFT JOIN t_pcs_ship d on c.shipId=d.id " +
				"LEFT JOIN t_pcs_ship_agent e on e.id=a.shipAgentId " +
				"LEFT JOIN t_pcs_arrival g on g.id=a.arrivalId " +
				"where 1=1 ";
		
		if(!Common.isNull(arrivalDto.getShipId())){
			sql+=" and d.id="+arrivalDto.getShipId();
		}
		if(!Common.isNull(arrivalDto.getId())){
			sql+=" and a.id="+arrivalDto.getId();
		}
		if(!Common.isNull(arrivalDto.getShipRefId())){
			sql+=" and a.shipRefId="+arrivalDto.getShipRefId()+" and isnull(a.arrivalId) ";
		}
		if(!Common.empty(arrivalDto.getShipRefName())&&!"-1".equals(arrivalDto.getShipRefName())){
			sql+=" and c.refName like '%"+arrivalDto.getShipRefName()+"%'";
		}
		if(!Common.empty(arrivalDto.getStartTime())&&!"-1".equals(arrivalDto.getStartTime())){
			sql+=" and FROM_UNIXTIME(a.anchorTime)>='"+arrivalDto.getStartTime()+"'";
		}
		if(!Common.empty(arrivalDto.getEndTime())&&!"-1".equals(arrivalDto.getEndTime())){
			sql+=" and FROM_UNIXTIME(a.anchorTime)<='"+arrivalDto.getEndTime()+"'";
		}
		if(!Common.empty(arrivalDto.getArrivalStatus())&&0!=arrivalDto.getArrivalStatus()){
			if(arrivalDto.getArrivalStatus()==1){
				sql+=" and isnull(a.status) and ( (case when g.type=3 then (select isnull(w.leaveTime) from t_pcs_work w where w.arrivalId=a.arrivalId limit 0,1 )=1 end) or isnull(a.arrivalId) or (CASE when (g.type=1 or g.type=3) then (select COALESCE(sum(tpw.status) MOD 9,1)  from t_pcs_work tpw where tpw.arrivalId=g.id order by tpw.id limit 0,1)>0 else g.status<>54 end))  ";
				
				}
			if(arrivalDto.getArrivalStatus()==2){
				sql+=" and isnull(a.status) and CASE when (g.type=1 or g.type=3) then (select tpw.status mod 9  from t_pcs_work tpw where tpw.arrivalId=g.id order by tpw.id ASC limit 0,1 )=0 else g.status=54 end ";
				
			}
			
			if(arrivalDto.getArrivalStatus()==4){
				sql+=" and isnull(a.status) and isnull(a.arrivalId) ";
			}
			
			if(arrivalDto.getArrivalStatus()==5){
				sql+=" and a.status=1 ";
			}
			
			if(arrivalDto.getArrivalStatus()==3){
				sql+=" and isnull(a.status) ";
			}
			
		}
			return (int) getCount(sql);
		
	}



	@Override
	public List<Map<String, Object>> getArrivalForeshowList(
			ArrivalDto arrivalDto, int start, int limit) throws OAException {
		String sql="select (select w.leaveTime from t_pcs_work w where w.arrivalId=a.arrivalId limit 0,1 ) wleaveTime,g.type arrivalType,g.status outboundstatus, (select COALESCE(tpw.status MOD 9,1)  from t_pcs_work tpw where tpw.arrivalId=g.id order by tpw.id ASC limit 0,1) inboundstatus,a.*,d.shipLenth sShipLenth,e.name shipAgentName,f.name berthName,d.id shipId,c.refName shipRefName,d.name shipName,FROM_UNIXTIME(a.lastLeaveTime) sLastLeaveTime,FROM_UNIXTIME(a.tearPipeTime) sTearPipeTime,FROM_UNIXTIME(a.cjTime) sCjTime,FROM_UNIXTIME(a.norTime) sNorTime," +
				" FROM_UNIXTIME(a.tcTime) sTcTime,FROM_UNIXTIME(a.anchorDate) sAnchorDate," +
				"FROM_UNIXTIME(a.anchorTime) sAnchorTime,FROM_UNIXTIME(a.pumpOpenTime) sPumpOpenTime," +
				"FROM_UNIXTIME(a.pumpStopTime) sPumpStopTime,FROM_UNIXTIME(a.leaveTime) sLeaveTime," +
				"FROM_UNIXTIME(a.lastLeaveTime) sLastLeaveTime,FROM_UNIXTIME(a.tearPipeTime) sTearPipeTime,FROM_UNIXTIME(a.createTime) sCreateTime,a.taxTypes " +
				" from t_pcs_arrival_foreshow a " +
				"LEFT JOIN t_auth_user b on a.createUserId=b.id " +
				"LEFT JOIN t_pcs_ship_ref c on c.id=a.shipRefId " +
				"LEFT JOIN t_pcs_ship d on c.shipId=d.id " +
				"LEFT JOIN t_pcs_ship_agent e on e.id=a.shipAgentId "+
				"LEFT JOIN t_pcs_berth f on f.id=a.berth " +
				"LEFT JOIN t_pcs_arrival g on g.id=a.arrivalId " +
				
				" where 1=1 ";
		
		if(!Common.isNull(arrivalDto.getShipId())){
			sql+=" and d.id="+arrivalDto.getShipId();
		}
		
		if(!Common.isNull(arrivalDto.getShipRefId())){
			sql+=" and a.shipRefId="+arrivalDto.getShipRefId()+" and isnull(a.arrivalId) ";
		}
		
		if(!Common.isNull(arrivalDto.getArrivalInfoId())){
			sql+=" and a.arrivalId="+arrivalDto.getArrivalInfoId();
		}
		
		if(!Common.isNull(arrivalDto.getId())){
			sql+=" and a.id="+arrivalDto.getId();
		}
		if(!Common.empty(arrivalDto.getStartTime())&&!"-1".equals(arrivalDto.getStartTime())){
			sql+=" and FROM_UNIXTIME(a.anchorTime)>='"+arrivalDto.getStartTime()+"'";
		}
		if(!Common.empty(arrivalDto.getEndTime())&&!"-1".equals(arrivalDto.getEndTime())){
			sql+=" and FROM_UNIXTIME(a.anchorTime)<='"+arrivalDto.getEndTime()+"'";
		}
		if(!Common.empty(arrivalDto.getShipRefName())&&!"-1".equals(arrivalDto.getShipRefName())){
			sql+=" and c.refName like '%"+arrivalDto.getShipRefName()+"%'";
		}
		if(!Common.empty(arrivalDto.getArrivalStatus())&&0!=arrivalDto.getArrivalStatus()){
			if(arrivalDto.getArrivalStatus()==1){
				sql+=" and isnull(a.status) and ( (case when g.type=3 then (select isnull(w.leaveTime) from t_pcs_work w where w.arrivalId=a.arrivalId limit 0,1 )=1 end) or isnull(a.arrivalId) or (CASE when (g.type=1 or g.type=3) then (select COALESCE(sum(tpw.status) MOD 9,1)  from t_pcs_work tpw where tpw.arrivalId=g.id order by tpw.id limit 0,1)>0 else g.status<>54 end))  ";
				
			}
			if(arrivalDto.getArrivalStatus()==2){
				sql+=" and isnull(a.status) and CASE when (g.type=1 or g.type=3) then (select tpw.status mod 9  from t_pcs_work tpw where tpw.arrivalId=g.id order by tpw.id ASC limit 0,1 )=0 else g.status=54 end ";
				
			}
			
			if(arrivalDto.getArrivalStatus()==4){
				sql+=" and isnull(a.status) and isnull(a.arrivalId) ";
			}
			
			if(arrivalDto.getArrivalStatus()==5){
				sql+=" and a.status=1 ";
			}
			
			if(arrivalDto.getArrivalStatus()==3){
				sql+=" and isnull(a.status) ";
			}
			
		}
		if(arrivalDto.getOrder()==1){
			sql+=" order by a.anchorTime DESC";
		}else{
			
			sql+=" order by a.anchorTime ASC";
		}
		
		if(limit!=0){
			sql+=" limit "+start+" , "+limit;
		}
		
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "操作失败",e);
		}
		
	}



	@Override
	public int checkForeshow(int arrivalId) throws OAException {
		String sql="select count(1) from t_pcs_arrival_foreshow where arrivalId="+arrivalId;
		return (int) getCount(sql);
	}



	@Override
	public void updateForeshowBySQL(int arrivalId,int userId) throws OAException {
		
		String sql="update t_pcs_arrival_foreshow a LEFT JOIN t_pcs_arrival b on b.id=a.arrivalId LEFT JOIN t_pcs_arrival_info c on c.arrivalId=b.id LEFT JOIN t_pcs_berth_program e on e.arrivalId=a.arrivalId " +
				" set " +
//				"a.cjTime=c.cjTime,a.norTime=c.norTime,a.anchorDate=c.anchorDate," +
//				" a.anchorTime=UNIX_TIMESTAMP(b.arrivalStartTime),a.pumpOpenTime=c.pumpOpenTime,a.pumpStopTime=c.pumpStopTime,a.workTime=c.workTime,a.leaveTime=c.leaveTime," +
//				" a.berth=e.berthId,a.shipRefId=b.shipRefId," +
				"a.productIds=(select GROUP_CONCAT(DISTINCT tpc.productId) from t_pcs_cargo tpc where tpc.arrivalId=a.arrivalId)," +
				" a.productNames=(select GROUP_CONCAT(DISTINCT tpp.name) from t_pcs_cargo tpc LEFT JOIN t_pcs_product tpp on tpc.productId=tpp.id where tpc.arrivalId=a.arrivalId),"+
				" a.count=(select round(sum(tpc.goodsPlan),3) from t_pcs_cargo tpc where tpc.arrivalId=a.arrivalId)," +
//				" a.shipArrivalDraught=round(c.shipArrivalDraught,3),a.shipAgentId=b.shipAgentId," +
//				" a.cargoAgentIds=(select GROUP_CONCAT(DISTINCT tpc.cargoAgentId) from t_pcs_cargo tpc where tpc.arrivalId=a.arrivalId)," +
//				" a.cargoAgentNames=(select GROUP_CONCAT(DISTINCT tpp.name) from t_pcs_cargo tpc LEFT JOIN t_pcs_cargo_agent tpp on tpc.cargoAgentId=tpp.id where tpc.arrivalId=a.arrivalId),"+
//				" a.port=c.port," +
//				"a.isCustomAgree=b.isCustomAgree," +
//				"a.note=c.note," +
				"a.portNum=c.portNum," +
				" a.clientIds=(select GROUP_CONCAT(DISTINCT tpc.clientId) from t_pcs_cargo tpc where tpc.arrivalId=a.arrivalId)," +
				" a.clientNames=(select GROUP_CONCAT(DISTINCT CONCAT(tpp. NAME,'(',(SELECT	sum(x.goodsPlan) FROM	t_pcs_cargo x WHERE	x.arrivalId = a.arrivalId	AND x.clientId = tpc.clientId),')'  ,  (SELECT GROUP_CONCAT(DISTINCT	case x.taxType when 3 then ';' else '' end)  FROM	t_pcs_cargo x WHERE	x.arrivalId = a.arrivalId	AND x.clientId = tpc.clientId)  )) from t_pcs_cargo tpc LEFT JOIN t_pcs_client tpp on tpc.clientId=tpp.id where tpc.arrivalId=a.arrivalId), "+
//				" a.taxTypes=(select GROUP_CONCAT(DISTINCT tpc.taxType) from t_pcs_cargo tpc where tpc.arrivalId=a.arrivalId)," +
				
//				" ,a.report=c.report" +
//				",a.lastLeaveTime=c.lastLeaveTime,a.tearPipeTime=c.tearPipeTime,a.repatriateTime=c.repatriateTime,a.overTime=c.overTime" +
				" editUserId="+userId+
				" where  a.arrivalId="+arrivalId;
		execute(sql);
		
	}



	@Override
	public void addForeshowBySQL(int arrivalId, int userId) throws OAException {
		String sql="insert into t_pcs_arrival_foreshow(cjTime,tcTime,norTime,anchorDate,anchorTime,pumpOpenTime,pumpStopTime," +
				"workTime,leaveTime,berth,shipRefId,productIds,productNames,taxTypes,count,shipArrivalDraught,shipAgentId,cargoAgentIds," +
				"cargoAgentNames,port,isCustomAgree,note,portNum,clientIds,clientNames,report,lastLeaveTime,tearPipeTime,repatriateTime,overTime,arrivalId,createUserId,editUserId)" +
				" select c.cjTime,FROM_UNIXTIME(c.tcTime) as tcTime,c.norTime,c.anchorDate,UNIX_TIMESTAMP(a.arrivalStartTime) as anchorTime,c.pumpOpenTime,c.pumpStopTime,c.workTime,c.leaveTime,a.berthId,a.shipRefId," +
				"GROUP_CONCAT(DISTINCT b.productId),GROUP_CONCAT(DISTINCT d.name),GROUP_CONCAT(DISTINCT b.taxType),round(sum(b.goodsPlan),3),round(c.shipArrivalDraught,3),a.shipAgentId,GROUP_CONCAT(DISTINCT b.cargoAgentId),GROUP_CONCAT(DISTINCT f.name)," +
				"c.port,a.isCustomAgree,c.note,c.portNum,GROUP_CONCAT(DISTINCT b.clientId),GROUP_CONCAT(DISTINCT CONCAT(e.name,'(',(SELECT	sum(x.goodsPlan) FROM	t_pcs_cargo x WHERE	x.arrivalId = a.id	AND x.clientId = e.id),')')),c.report,c.lastLeaveTime,c.tearPipeTime,c.repatriateTime,c.overTime,a.id,"+userId+","+userId +
				" from t_pcs_arrival a LEFT JOIN t_pcs_cargo b on a.id=b.arrivalId LEFT JOIN t_pcs_product d on d.id=b.productId LEFT JOIN t_pcs_client e on e.id=b.clientId LEFT JOIN t_pcs_cargo_agent f on f.id=b.cargoAgentId,t_pcs_arrival_info c where c.arrivalId=a.id and a.id="+arrivalId;
	
		execute(sql);
	}



	@Override
	public void updateOutForeshowBySQL(int arrivalId, int userId)
			throws OAException {
		
		String sql="update t_pcs_arrival_foreshow a LEFT JOIN t_pcs_arrival b on b.id=a.arrivalId LEFT JOIN t_pcs_work c on c.arrivalId=b.id LEFT JOIN t_pcs_product d on c.productId=d.id LEFT JOIN t_pcs_arrival_info e on e.arrivalId=b.id LEFT JOIN t_pcs_berth_program f on f.arrivalId=a.arrivalId " +
				" set " +
//				"a.cjTime=e.cjTime,a.norTime=e.norTime,a.anchorDate=e.anchorDate," +
//				" a.anchorTime=UNIX_TIMESTAMP(b.arrivalStartTime),a.pumpOpenTime=c.openPump,a.pumpStopTime=c.stopPump,a.workTime=c.workTime,a.leaveTime=c.leaveTime," +
//				" a.berth=f.berthId,a.shipRefId=b.shipRefId," +
				"a.productIds=c.productId," +
				" a.productNames=d.name,"+
				" a.count=(select round(sum(tpc.goodsTotal),3) from t_pcs_arrival_plan tpc where tpc.arrivalId=a.arrivalId)," +
//				" a.shipArrivalDraught=round(e.shipArrivalDraught,3),a.shipAgentId=b.shipAgentId," +
//				" a.cargoAgentIds=(select GROUP_CONCAT(DISTINCT tpc.cargoAgentId) from t_pcs_cargo tpc where tpc.arrivalId=a.arrivalId)," +
//				" a.cargoAgentNames=(select GROUP_CONCAT(DISTINCT tpp.name) from t_pcs_cargo tpc LEFT JOIN t_pcs_cargo_agent tpp on tpc.cargoAgentId=tpp.id where tpc.arrivalId=a.arrivalId),"+
//				" a.port=e.port,a.note=e.note," +
				" a.portNum=e.portNum," +
				" a.clientIds=(select GROUP_CONCAT(DISTINCT tpc.clientId) from t_pcs_arrival_plan tpc where tpc.arrivalId=a.arrivalId)," +
				" a.clientNames=(select GROUP_CONCAT(DISTINCT tpp.name) from t_pcs_arrival_plan tpc LEFT JOIN t_pcs_client tpp on tpc.clientId=tpp.id where tpc.arrivalId=a.arrivalId),"+
//				" a.taxTypes=(select GROUP_CONCAT(DISTINCT tpc.tradeType) from t_pcs_arrival_plan tpc LEFT JOIN t_pcs_client tpp on tpc.clientId=tpp.id where tpc.arrivalId=a.arrivalId),"+
				
//				" a.report=e.report" +
//				",a.lastLeaveTime=e.lastLeaveTime,a.tearPipeTime=c.tearPipeTime,a.repatriateTime=e.repatriateTime,a.overTime=e.overTime" +
				" a.editUserId="+userId+
				" where  a.arrivalId="+arrivalId;
		execute(sql);
		
	}



	@Override
	public void zfArrivalForeshow(String id) throws OAException {
		String sql="update t_pcs_arrival_foreshow set status=1,arrivalId=null where id in("+id+")";
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
