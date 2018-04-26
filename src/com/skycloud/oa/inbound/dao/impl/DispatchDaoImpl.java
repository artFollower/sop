package com.skycloud.oa.inbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.DispatchDao;
import com.skycloud.oa.inbound.dto.DispatchConnectDto;
import com.skycloud.oa.inbound.dto.DispatchDto;
import com.skycloud.oa.inbound.model.Dispatch;
import com.skycloud.oa.utils.Common;
@Component
public class DispatchDaoImpl extends BaseDaoImpl implements DispatchDao {

	private static Logger LOG = Logger.getLogger(DispatchDaoImpl.class);

	@Override
	public int addIntoDispatch(Dispatch dispatch) {
		try {
			String sql="INSERT INTO t_pcs_dispatch ( weather, windDirection, windPower, dispatchUserId, deliveryUserId, dayWordUserId, "
					+ "dockUserId, powerUserId, dayWordUser, deliveryUser, description, dispatchUser, dockUser, powerUser, sWeather, "
					+ "sWindPower,sWindDirection, time ) SELECT ";
			   if(!Common.empty(dispatch.getWeather())){sql+="'"+dispatch.getWeather()+"',";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getWindDirection())){sql+=dispatch.getWindDirection()+",";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getWindPower())){sql+=dispatch.getWindPower()+",";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getDispatchUserId())){sql+=dispatch.getDispatchUserId()+",";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getDeliveryUserId())){sql+=dispatch.getDeliveryUserId()+",";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getDayWordUserId())){sql+=dispatch.getDayWordUserId()+",";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getDockUserId())){sql+=dispatch.getDockUserId()+",";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getPowerUserId())){sql+=dispatch.getPowerUserId()+",";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getDayWordUser())){sql+="'"+dispatch.getDayWordUser()+"',";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getDeliveryUser())){sql+="'"+dispatch.getDeliveryUser()+"',";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getDescription())){sql+="'"+dispatch.getDescription()+"',";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getDispatchUser())){sql+="'"+dispatch.getDispatchUser()+"',";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getDockUser())){sql+="'"+dispatch.getDockUser()+"',";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getPowerUser())){sql+="'"+dispatch.getPowerUser()+"',";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getsWeather())){sql+="'"+dispatch.getsWeather()+"',";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getsWindPower())){sql+="'"+dispatch.getsWindPower()+"',";}else {sql+="null,";}
			   if(!Common.empty(dispatch.getsWindDirection())){sql+="'"+dispatch.getsWindDirection()+"',";}else {sql+="null,";}
				  
			   if(!Common.empty(dispatch.getTime())){sql+=dispatch.getTime();}else {sql+="null";}
			  
			   sql+=" FROM DUAL WHERE NOT EXISTS ( SELECT time FROM t_pcs_dispatch WHERE time ="+dispatch.getTime()+" AND time IS NOT NULL )";
			   int id=  insert(sql);
			return id;
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}



	@Override
	public void updateDispatch(Dispatch dispatch) {
		// TODO Auto-generated method stub
		try {
			update(dispatch);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Override
	public List<Map<String, Object>> getDispatchList(DispatchDto dispatchDto,
			int startRecord, int currentpage)throws OAException {
		try {
			String sql="select * from t_pcs_dispatch where 1=1 ";
			if(dispatchDto.getStartTime()!=null){
				sql+=" and time >="+dispatchDto.getStartTime();
			}
			if(dispatchDto.getEndTime()!=null&&dispatchDto.getEndTime()!=-1){
				sql+=" and time <="+dispatchDto.getEndTime();
			}
			sql+=" order by time desc";
			if(currentpage!=0)
			sql+=" limit "+startRecord+","+currentpage;
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}



	@Override
	public int getDispatchCount(DispatchDto dispatchDto)throws OAException {
		try {
			String sql="select count(*) from t_pcs_dispatch where 1=1 ";
			if(dispatchDto.getStartTime()!=null){
				sql+=" and time >="+dispatchDto.getStartTime();
			}
			if(dispatchDto.getEndTime()!=null&&dispatchDto.getEndTime()!=-1){
				sql+=" and time <="+dispatchDto.getEndTime();
			}
			sql+=" order by time desc";
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}



	@Override
	public void addDispatchConnect(int id, DispatchConnectDto dispatchConnectDto)throws OAException {
		String  sql="insert t_pcs_dispatch_connect (dispatchId,type,userId) values ";
		
		if(!Common.empty(dispatchConnectDto.getDD1())){
			String[] DD1=dispatchConnectDto.getDD1().split(",");
			for(int i=0;i<DD1.length;i++){
				sql+=" ("+id+",11,"+DD1[i]+"), ";
			}
		}
		
		if(!Common.empty(dispatchConnectDto.getDD2())){
			String[] DD2=dispatchConnectDto.getDD2().split(",");
			for(int i=0;i<DD2.length;i++){
				sql+=" ("+id+",12,"+DD2[i]+"), ";
			}
		}
		
		if(!Common.empty(dispatchConnectDto.getFH1())){
			String[] FH1=dispatchConnectDto.getFH1().split(",");
			for(int i=0;i<FH1.length;i++){
				sql+=" ("+id+",21,"+FH1[i]+"), ";
			}
		}
		
		if(!Common.empty(dispatchConnectDto.getFH2())){
			String[] FH2=dispatchConnectDto.getFH2().split(",");
			for(int i=0;i<FH2.length;i++){
				sql+=" ("+id+",22,"+FH2[i]+"), ";
			}
		}
		
		if(!Common.empty(dispatchConnectDto.getRB1())){
			String[] RB1=dispatchConnectDto.getRB1().split(",");
			for(int i=0;i<RB1.length;i++){
				sql+=" ("+id+",31,"+RB1[i]+"), ";
			}
		}
		
		if(!Common.empty(dispatchConnectDto.getRB2())){
			String[] RB2=dispatchConnectDto.getRB2().split(",");
			for(int i=0;i<RB2.length;i++){
				sql+=" ("+id+",32,"+RB2[i]+"), ";
			}
		}
		
		if(!Common.empty(dispatchConnectDto.getMT1())){
			String[] MT1=dispatchConnectDto.getMT1().split(",");
			for(int i=0;i<MT1.length;i++){
				sql+=" ("+id+",41,"+MT1[i]+"), ";
			}
		}
		
		if(!Common.empty(dispatchConnectDto.getMT2())){
			String[] MT2=dispatchConnectDto.getMT2().split(",");
			for(int i=0;i<MT2.length;i++){
				sql+=" ("+id+",42,"+MT2[i]+"), ";
			}
		}
		
		if(!Common.empty(dispatchConnectDto.getDL1())){
			String[] DL1=dispatchConnectDto.getDL1().split(",");
			for(int i=0;i<DL1.length;i++){
				sql+=" ("+id+",51,"+DL1[i]+"), ";
			}
		}
		
		if(!Common.empty(dispatchConnectDto.getDL2())){
			String[] DL2=dispatchConnectDto.getDL2().split(",");
			for(int i=0;i<DL2.length;i++){
				sql+=" ("+id+",52,"+DL2[i]+"), ";
			}
		}
		
		sql=sql.substring(0, sql.length()-2);
		if(sql.substring(sql.length()-1,sql.length()).equals("e")){
			return ;
		}
		
		try {
			execute(sql);
		} catch (OAException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}



	@Override
	public void deleteDispatchConnect(int dispatchId)
			throws OAException {
		String sql="delete from t_pcs_dispatch_connect where dispatchId="+dispatchId;
		try {
			execute(sql);
		} catch (OAException e) {
			LOG.error("dao删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
		
	}



	@Override
	public List<Map<String, Object>> getDispatchConnectList(DispatchDto dispatchDto)
			throws OAException {
		String sql="select a.*,b.name userName from t_pcs_dispatch_connect a LEFT JOIN t_auth_user b on a.userId=b.id LEFT JOIN t_pcs_dispatch c on c.id=a.dispatchId where 1=1  and not ISNULL(b.jobId) and b.jobId<>''  ";
		 
		
		if(!Common.isNull(dispatchDto.getDispatchId())){
			sql+=" and a.dispatchId="+dispatchDto.getDispatchId();
		}
		
		if(dispatchDto.getStartTime()!=null){
			sql+=" and c.time >="+dispatchDto.getStartTime();
		}
		if(dispatchDto.getEndTime()!=null&&dispatchDto.getEndTime()!=-1){
			sql+=" and c.time <="+dispatchDto.getEndTime();
		}
		
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			LOG.error("dao删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}


}
