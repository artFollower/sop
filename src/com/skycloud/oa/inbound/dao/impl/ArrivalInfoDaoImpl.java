package com.skycloud.oa.inbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalInfoDao;
import com.skycloud.oa.inbound.model.ArrivalInfo;
@Component
public class ArrivalInfoDaoImpl extends BaseDaoImpl implements ArrivalInfoDao {

	private static Logger LOG = Logger.getLogger(ArrivalInfoDaoImpl.class);

	@Override
	public int addArrivalInfo(ArrivalInfo arrivalInfo)throws OAException {
		try {
			String sql="INSERT INTO t_pcs_arrival_info (arrivalId,report,shipInfo,portNum,shipArrivalDraught) SELECT "
					+ ""+arrivalInfo.getArrivalId()+",'"+arrivalInfo.getReport()+"','"+arrivalInfo.getShipInfo()+"',"+arrivalInfo.getPortNum()+","+arrivalInfo.getShipArrivalDraught()
		+ " from DUAL WHERE not EXISTS (SELECT arrivalId FROM t_pcs_arrival_info where arrivalId ="+arrivalInfo.getArrivalId()+" and arrivalId is not null)";
		  return insert(sql);
		} catch (RuntimeException e) {
		LOG.error("dao添加到港附加信息失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加到港附加信息失败", e);
		}
	}

	@Override
	public void deleteArrivalInfo(String id) throws OAException  {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map<String, Object>> getArrivalInfo(int id) throws OAException  {
		return null;
	}

	@Override
	public void updateArrival(ArrivalInfo arrivalInfo) throws OAException  {
		try{
		String sql = "update t_pcs_arrival_info set id=id ";
		if (arrivalInfo.getCjTime()!=null) {
			if(arrivalInfo.getCjTime()==-1){
				sql+=" ,cjTime=null";
			}else{
			sql += " ,cjTime=" + arrivalInfo.getCjTime();
			}
		}
		if (arrivalInfo.getTcTime()!=null) {
			if(arrivalInfo.getTcTime()==-1){
				sql+=" ,tcTime=null";
			}else{
			sql += " ,tcTime=" + arrivalInfo.getTcTime();
			}
		}
		if (null!=arrivalInfo.getNorTime()) {
			if(arrivalInfo.getNorTime()==-1){
				sql+=" ,norTime=null";
			}else{
			sql += " ,norTime=" +arrivalInfo.getNorTime();
			}
		}
		if (null!=arrivalInfo.getAnchorDate()) {
			if(arrivalInfo.getAnchorDate()==-1){ 
				sql+=" ,anchorDate=null";
				}else{
				sql += " ,anchorDate=" + arrivalInfo.getAnchorDate();
			}
		}
		if (null!=arrivalInfo.getAnchorTime()) {
			if(arrivalInfo.getAnchorTime()==-1){ sql+=" ,anchorTime=null";}else{
				sql += " ,anchorTime=" + arrivalInfo.getAnchorTime();
			}
		}
		if (null!=arrivalInfo.getPumpOpenTime()) {
			if(arrivalInfo.getPumpOpenTime()==-1){ sql+=" ,pumpOpenTime=null";}else{
				sql += " ,pumpOpenTime=" +  arrivalInfo.getPumpOpenTime();
			}
		}
		if (null!=arrivalInfo.getPumpStopTime()) {
			if(arrivalInfo.getPumpStopTime()==-1){ sql+=" ,pumpStopTime=null";}else{
				sql += " ,pumpStopTime=" + arrivalInfo.getPumpStopTime();
			}
		}
		if (null!=arrivalInfo.getWorkTime()){
			sql+=" ,workTime='"+arrivalInfo.getWorkTime()+"'";
		}
		if(null!=arrivalInfo.getPortType()){
			sql+=" ,portType="+arrivalInfo.getPortType();
		}
		if (null!=arrivalInfo.getLeaveTime()) {
			if(arrivalInfo.getLeaveTime()==-1){ sql+=" ,leaveTime=null";}else{
				sql += " ,leaveTime=" + arrivalInfo.getLeaveTime();
			}
		}
		if (null!=arrivalInfo.getTearPipeTime()) {
			if(arrivalInfo.getTearPipeTime()==-1){ sql+=" ,tearPipeTime=null";}else{
				sql += " ,tearPipeTime=" +  arrivalInfo.getTearPipeTime();
			}
		}
		if (null!=arrivalInfo.getOverTime()) {
			if(arrivalInfo.getOverTime()==-1){ sql+=" ,overTime=null";}else{
				sql += " ,overTime=" +  arrivalInfo.getOverTime();
			}
		}
		if (null!=arrivalInfo.getRepatriateTime()) {
			if(arrivalInfo.getRepatriateTime()==-1){ sql+=" ,repatriateTime=null";}else{
				sql += " ,repatriateTime=" +  arrivalInfo.getRepatriateTime();
			}
		}
		if (null!=arrivalInfo.getLastLeaveTime()) {
			if(arrivalInfo.getLastLeaveTime()==-1){ sql+=" ,lastLeaveTime=null";}else{
				sql += " ,lastLeaveTime=" +  arrivalInfo.getLastLeaveTime();
			}
		}
		if (null!=arrivalInfo.getPort()) {
			sql += " ,port='" + arrivalInfo.getPort() + "'";
		}
		if (null!=arrivalInfo.getShipArrivalDraught()) {
			sql += " ,shipArrivalDraught=" +arrivalInfo.getShipArrivalDraught();
		}
		if (null!=arrivalInfo.getPortNum()) {
			sql += " ,portNum='" + arrivalInfo.getPortNum() + "'";
		}
		if (null!=arrivalInfo.getReport()) {
			sql += " ,report='" + arrivalInfo.getReport() + "'";
		}
		if (null!=arrivalInfo.getShipInfo()) {
			sql += " ,shipInfo='" + arrivalInfo.getShipInfo() + "'";
		}if (null!=arrivalInfo.getNote()) {
			sql += " ,note='" + arrivalInfo.getNote() + "'";
		}
		if (null!=arrivalInfo.getCreateUserId()) {
			sql += " ,createUserId=" + arrivalInfo.getCreateUserId();
		}
		if (null!=arrivalInfo.getCreateTime()) {
			sql += " ,createTime=" + arrivalInfo.getCreateTime();
		}
//		if (null!=arrivalInfo.getReviewUserId()) {
//			sql += " ,reviewUserId=" + arrivalInfo.getReviewUserId();
//		}
//		if (null!=arrivalInfo.getReviewTime()) {
//			sql += " ,reviewTime=" + arrivalInfo.getReviewTime();
//		}
		if(null!=arrivalInfo.getStatus()&&-1!=arrivalInfo.getStatus()){
			sql+=" ,status="+arrivalInfo.getStatus();
		}
		sql+=" where arrivalId="+arrivalInfo.getArrivalId();
		executeUpdate(sql);
		}catch (RuntimeException e){
			LOG.error("dao更新到港附加信息失败",e);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新到港附加信息失败", e);
		}
		
	}

	@Override
	public void cleanArrivalInfo(int id) throws OAException {
		 try{
	    	  String sql=" update t_pcs_arrival_info set overTime=null,repatriateTime=null,status=0 where arrivalId="+id;
	    	  executeUpdate(sql);
	      }catch(RuntimeException e){
	    	  LOG.error("dao 靠泊方案回退失败");
	    	  throw new OAException(Constant.SYS_CODE_DB_ERR," dao 靠泊方案失败",e);
	      }		
	}


}
