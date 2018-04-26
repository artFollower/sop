package com.skycloud.oa.inbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.TankLogDao;
import com.skycloud.oa.inbound.dto.TankLogDto;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TankLog;
import com.skycloud.oa.inbound.model.TankLogStore;
import com.skycloud.oa.utils.Common;
@Component
public class TankLogDaoImpl extends BaseDaoImpl implements TankLogDao {

	private static Logger LOG = Logger.getLogger(TankLogDaoImpl.class);

	@Override
	public int addTankLog(TankLog tankLog) throws OAException{
		try {
			  Serializable s=  save(tankLog);
			  return Integer.valueOf(s.toString());
			} catch (RuntimeException e) {
			LOG.error("dao添加储罐台账失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加储罐台账失败", e);
			}
	}



	@Override
	public void updateTankLog(TankLog tankLog)throws OAException {
		
		String bStartLevel="";
		String bEndLevel="";
		String bStartWeight="";
		String bEndWeight="";
		String bStartTemperature="";
		String bEndTemperature="";
		String bStartHand="";
		String bEndHand="";
		String bStartHandLevel="";
		String bEndHandLevel="";
		String bStartHandWeight="";
		String bEndHandWeight="";
		String bStartDiffer="";
		String bEndDiffer="";
		
		String sql="update t_pcs_tank_log a LEFT JOIN t_pcs_store b on b.id=a.startStoreId LEFT JOIN t_pcs_store c on c.id=a.endStoreId set a.id=a.id";
				if(!Common.isNull(tankLog.getTankId())){
					sql+=" ,a.tankId="+tankLog.getTankId();
				}
				if(tankLog.getStartLevel()!=null){ 
					sql+=" ,a.startLevel='"+tankLog.getStartLevel()+"'";
					bStartLevel=" case a.startStoreType when '1' then '"+tankLog.getStartLevel()+"' else b.startLevel end";
					bEndLevel=" case a.startStoreType when '2' then '"+tankLog.getStartLevel()+"' else b.endLevel end";
//					
					sql+=" ,b.startLevel="+bStartLevel;
					sql+=" ,b.endLevel="+bEndLevel;
//					sql+=" ,case a.startStoreType when '1' then b.startLevel='"+tankLog.getStartLevel()+"' when '2' then b.endLevel='"+tankLog.getStartLevel()+"'";
				}
				if(tankLog.getEndLevel()!=null){
					sql+=" ,a.endLevel='"+tankLog.getEndLevel()+"'";
					sql+=" ,c.startLevel=case a.endStoreType when '1' then '"+tankLog.getEndLevel()+"' else case b.id when c.id then "+bStartLevel+" else c.startLevel end end";
					sql+=" ,c.endLevel=case a.endStoreType when '2' then '"+tankLog.getEndLevel()+"' else case b.id when c.id then "+bEndLevel+" else c.endLevel end end";
//					sql+=" ,case a.endStoreType when '1' then c.startLevel='"+tankLog.getEndLevel()+"' when '2' then c.endLevel='"+tankLog.getEndLevel()+"'";
				}
				if(tankLog.getStartWeight()!=null){
					sql+=" ,a.startWeight='"+tankLog.getStartWeight()+"'";
					bStartWeight="case a.startStoreType when '1' then '"+tankLog.getStartWeight()+"' else b.startWeight end";
					bEndWeight="case a.startStoreType when '2' then '"+tankLog.getStartWeight()+"' else b.endWeight end";
					sql+=" ,b.startWeight="+bStartWeight;
					sql+=" ,b.endWeight="+bEndWeight;
//					sql+=" ,case a.startStoreType when '1' then b.startWeight='"+tankLog.getStartWeight()+"' when '2' then b.endWeight='"+tankLog.getStartWeight()+"'";
				}
				if(tankLog.getEndWeight()!=null){
					sql+=" ,a.endWeight='"+tankLog.getEndWeight()+"'";
					sql+=" ,c.startWeight=case a.endStoreType when '1' then '"+tankLog.getEndWeight()+"' else case b.id when c.id then "+bStartWeight+" else c.startWeight end end";
					sql+=" ,c.endWeight=case a.endStoreType when '2' then '"+tankLog.getEndWeight()+"' else case b.id when c.id then "+bEndWeight+" else c.endWeight end end";
//					sql+=" ,case a.endStoreType when '1' then c.startWeight='"+tankLog.getEndWeight()+"' when '2' then c.endWeight='"+tankLog.getEndWeight()+"'";
				}
				if(tankLog.getStartTemperature()!=null){
					sql+=" ,a.startTemperature='"+tankLog.getStartTemperature()+"'";
					bStartTemperature="case a.startStoreType when '1' then '"+tankLog.getStartTemperature()+"' else b.startTemperature end";
					bEndTemperature="case a.startStoreType when '2' then '"+tankLog.getStartTemperature()+"' else b.endTemperature end";
					sql+=" ,b.startTemperature="+bStartTemperature;
					sql+=" ,b.endTemperature="+bEndTemperature;
//					sql+=" ,case a.startStoreType when '1' then b.startTemperature='"+tankLog.getStartTemperature()+"' when '2' then b.endTemperature='"+tankLog.getStartTemperature()+"'";
				}
				if(tankLog.getEndTemperature()!=null){
					sql+=" ,a.endTemperature='"+tankLog.getEndTemperature()+"'";
					sql+=" ,c.startTemperature=case a.endStoreType when '1' then '"+tankLog.getEndTemperature()+"' else case b.id when c.id then "+bStartTemperature+" else c.startTemperature end end";
					sql+=" ,c.endTemperature=case a.endStoreType when '2' then '"+tankLog.getEndTemperature()+"' else case b.id when c.id then "+bEndTemperature+" else c.endTemperature end end";
//					sql+=" ,case a.endStoreType when '1' then c.startTemperature='"+tankLog.getStartTemperature()+"' when '2' then c.endTemperature='"+tankLog.getEndTemperature()+"'";
				}
				if(!Common.empty(tankLog.getRealAmount())){
					sql+=" ,a.realAmount='"+tankLog.getRealAmount()+"'";
				}
				if(!Common.empty(tankLog.getMeasureAmount())){
					sql+=" ,a.measureAmount='"+tankLog.getMeasureAmount()+"'";
				}
				if(!Common.empty(tankLog.getDifferAmount())){
					sql+=" ,a.differAmount='"+tankLog.getDifferAmount()+"'";
				}
				if(tankLog.getStartHand()!=null){
					sql+=" ,a.startHand='"+tankLog.getStartHand()+"'";
					bStartHand="case a.startStoreType when '1' then '"+tankLog.getStartHand()+"' else b.startHand end";
					bEndHand="case a.startStoreType when '2' then '"+tankLog.getStartHand()+"' else b.endHand end";
					sql+=" ,b.startHand="+bStartHand;
					sql+=" ,b.endHand="+bEndHand;
//					sql+=" ,case a.startStoreType when '1' then b.startHand='"+tankLog.getStartHand()+"' when '2' then b.endHand='"+tankLog.getStartHand()+"'";
				}
				if(tankLog.getEndHand()!=null){
					sql+=" ,a.endHand='"+tankLog.getEndHand()+"'";
					sql+=" ,c.startHand=case a.endStoreType when '1' then '"+tankLog.getEndHand()+"' else case b.id when c.id then "+bStartHand+" else c.startHand end end";
					sql+=" ,c.endHand=case a.endStoreType when '2' then '"+tankLog.getEndHand()+"' else case b.id when c.id then "+bEndHand+" else c.endHand end  end";
//					sql+=" ,case a.endStoreType when '1' then c.startHand='"+tankLog.getEndHand()+"' when '2' then c.endHand='"+tankLog.getEndHand()+"'";
				}
				if(!Common.isNull(tankLog.getStartTime())){
					sql+=" ,a.startTime="+tankLog.getStartTime();
				}
				if(!Common.isNull(tankLog.getEndTime())){
					sql+=" ,a.endTime="+tankLog.getEndTime();
				}
				if(tankLog.getStartHandLevel()!=null){
					sql+=" ,a.startHandLevel='"+tankLog.getStartHandLevel()+"'";
					bStartHandLevel="case a.startStoreType when '1' then '"+tankLog.getStartHandLevel()+"' else b.startHandLevel end";
					bEndHandLevel="case a.startStoreType when '2' then '"+tankLog.getStartHandLevel()+"' else b.endHandLevel end";
					sql+=" ,b.startHandLevel="+bStartHandLevel;
					sql+=" ,b.endHandLevel="+bEndHandLevel;
//					sql+=" ,case a.startStoreType when '1' then b.startHandLevel='"+tankLog.getStartHandLevel()+"' when '2' then b.endHandLevel='"+tankLog.getStartHandLevel()+"'";
				}
				if(tankLog.getEndHandLevel()!=null){
					sql+=" ,a.endHandLevel='"+tankLog.getEndHandLevel()+"'";
					sql+=" ,c.startHandLevel=case a.endStoreType when '1' then '"+tankLog.getEndHandLevel()+"' else case b.id when c.id then "+bStartHandLevel+" else c.startHandLevel end end";
					sql+=" ,c.endHandLevel=case a.endStoreType when '2' then '"+tankLog.getEndHandLevel()+"' else case b.id when c.id then "+bEndHandLevel+" else c.endHandLevel end end";
//					sql+=" ,case a.endStoreType when '1' then c.startHandLevel='"+tankLog.getEndHandLevel()+"' when '2' then c.endHandLevel='"+tankLog.getEndHandLevel()+"'";
				}
				if(tankLog.getStartHandWeight()!=null){
					sql+=" ,a.startHandWeight='"+tankLog.getStartHandWeight()+"'";
					bStartHandWeight="case a.startStoreType when '1' then '"+tankLog.getStartHandWeight()+"' else b.startHandWeight end";
					bEndHandWeight="case a.startStoreType when '2' then '"+tankLog.getStartHandWeight()+"' else b.endHandWeight end";
					sql+=" ,b.startHandWeight="+bStartHandWeight;
					sql+=" ,b.endHandWeight="+bEndHandWeight;
//					sql+=" ,case a.startStoreType when '1' then b.startHandWeight='"+tankLog.getStartHandWeight()+"' when '2' then b.endHandWeight='"+tankLog.getStartHandWeight()+"'";
				}
				if(tankLog.getEndHandWeight()!=null){
					sql+=" ,a.endHandWeight='"+tankLog.getEndHandWeight()+"'";
					sql+=" ,c.startHandWeight=case a.endStoreType when '1' then '"+tankLog.getEndHandWeight()+"' else case b.id when c.id then "+bStartHandWeight+" else  c.startHandWeight end end";
					sql+=" ,c.endHandWeight=case a.endStoreType when '2' then '"+tankLog.getEndHandWeight()+"' else case b.id when c.id then "+bEndHandWeight+" else  c.endHandWeight end end";
//					sql+=" ,case a.endStoreType when '1' then c.startHandWeight='"+tankLog.getEndHandWeight()+"' when '2' then c.endHandWeight='"+tankLog.getEndHandWeight()+"'";
				}
				if(tankLog.getStartDiffer()!=null){
					sql+=" ,a.startDiffer='"+tankLog.getStartDiffer()+"'";
					bStartDiffer="case a.startStoreType when '1' then '"+tankLog.getStartDiffer()+"' else b.startDiffer end";
					bEndDiffer="case a.startStoreType when '2' then '"+tankLog.getStartDiffer()+"' else b.endDiffer end";
					sql+=" ,b.startDiffer="+bStartDiffer;
					sql+=" ,b.endDiffer="+bEndDiffer;
//					sql+=" ,case a.startStoreType when '1' then b.startDiffer='"+tankLog.getStartDiffer()+"' when '2' then b.endDiffer='"+tankLog.getStartDiffer()+"'";
				}
				if(tankLog.getEndDiffer()!=null){
					sql+=" ,a.endDiffer='"+tankLog.getEndDiffer()+"'";
					sql+=" ,c.startDiffer=case a.endStoreType when '1' then '"+tankLog.getStartDiffer()+"' else case b.id when c.id then "+bStartDiffer+" else c.startDiffer end end";
					sql+=" ,c.endDiffer=case a.endStoreType when '2' then '"+tankLog.getStartDiffer()+"' else case b.id when c.id then "+bEndDiffer+" else c.endDiffer end end";
//					sql+=" ,case a.endStoreType when '1' then c.startDiffer='"+tankLog.getStartDiffer()+"' when '2' then c.endDiffer='"+tankLog.getEndDiffer()+"'";
				}
				if(tankLog.getMessage()!=null){
					sql+=" ,a.message='"+tankLog.getMessage()+"'";
				}
				if(!Common.isNull(tankLog.getMessageType())){
					sql+=" ,a.messageType="+tankLog.getMessageType();
				}
				if(!Common.isNull(tankLog.getStartStoreId())){
					sql+=" ,a.startStoreId="+tankLog.getStartStoreId();
				}
				if(!Common.isNull(tankLog.getEndStoreId())){
					sql+=" ,a.endStoreId="+tankLog.getEndStoreId();
				}
				
				if(!Common.isNull(tankLog.getProductId())){
					sql+=" ,a.productId="+tankLog.getProductId();
				}
				
//				sql+=" ,b.realAmount=b.endWeight-b.startWeight";
//				sql+=" ,c.realAmount=c.endWeight-c.startWeight";
//				sql+=" ,b.measureAmount=b.endHandWeight-b.startHandWeight";
//				sql+=" ,c.measureAmount=c.endHandWeight-c.startHandWeight";
//				sql+=" ,b.differAmount=b.endHandWeight-b.startHandWeight-(b.endWeight-b.startWeight)";
//				sql+=" ,c.differAmount=c.endHandWeight-c.startHandWeight-(c.endWeight-c.startWeight)";
				
				sql+=" where a.id="+tankLog.getId();
				
				
				
				String sql1="update t_pcs_tank_log a LEFT JOIN t_pcs_store b on b.id=a.startStoreId LEFT JOIN t_pcs_store c on c.id=a.endStoreId set a.id=a.id";
				sql1+=" ,b.realAmount=round(b.endWeight-b.startWeight,3)";
				sql1+=" ,c.realAmount=round(c.endWeight-c.startWeight,3)";
				sql1+=" ,b.measureAmount=round(b.endHandWeight-b.startHandWeight,3)";
				sql1+=" ,c.measureAmount=round(c.endHandWeight-c.startHandWeight,3)";
				sql1+=" ,b.differAmount=round(b.endHandWeight-b.startHandWeight-(b.endWeight-b.startWeight),3)";
				sql1+=" ,c.differAmount=round(c.endHandWeight-c.startHandWeight-(c.endWeight-c.startWeight),3)";
				sql1+=" where a.id="+tankLog.getId();
				
				
				
				String StartLevel=tankLog.getStartLevel()==null?"0":tankLog.getStartLevel();
				String EndLevel=tankLog.getEndLevel()==null?"0":tankLog.getEndLevel();
				String StartWeight=tankLog.getStartWeight()==null?"0":tankLog.getStartWeight();
				String EndWeight=tankLog.getEndWeight()==null?"0":tankLog.getEndWeight();
				String StartTemperature=tankLog.getStartTemperature()==null?"0":tankLog.getStartTemperature();
				String EndTemperature=tankLog.getEndTemperature()==null?"0":tankLog.getEndTemperature();
				String StartHandLevel=tankLog.getStartHandLevel()==null?"0":tankLog.getStartHandLevel();
				String EndHandLevel=tankLog.getEndHandLevel()==null?"0":tankLog.getEndHandLevel();
				String StartHandWeight=tankLog.getStartHandWeight()==null?"0":tankLog.getStartHandWeight();
				String EndHandWeight=tankLog.getEndHandWeight()==null?"0":tankLog.getEndHandWeight();
				
				
				String sql2="update t_pcs_tanklogstore a LEFT JOIN t_pcs_tank_log b on b.id=a.startLogId set a.id=a.id ";
				
				sql2+=" , a.startLevel=case a.startType when '1' then '"+StartLevel+"' else '"+EndLevel+"' end";
				sql2+=" , a.startWeight=case a.startType when '1' then '"+StartWeight+"' else '"+EndWeight+"' end";
				sql2+=" , a.startTemperature=case a.startType when '1' then '"+StartTemperature+"' else '"+EndTemperature+"' end";
				sql2+=" , a.startHandLevel=case a.startType when '1' then '"+StartHandLevel+"' else '"+EndHandLevel+"' end";
				sql2+=" , a.startHandWeight=case a.startType when '1' then '"+StartHandWeight+"' else '"+EndHandWeight+"' end";
				sql2+=" where b.id="+tankLog.getId();
				
				String sql3="update t_pcs_tanklogstore a LEFT JOIN t_pcs_tank_log b on b.id=a.endLogId set a.id=a.id ";
				
				sql3+=" , a.endLevel=case a.endType when '1' then '"+StartLevel+"' else '"+EndLevel+"' end";
				sql3+=" , a.endWeight=case a.endType when '1' then '"+StartWeight+"' else '"+EndWeight+"' end";
				sql3+=" , a.endTemperature=case a.endType when '1' then '"+StartTemperature+"' else '"+EndTemperature+"' end";
				sql3+=" , a.endHandLevel=case a.endType when '1' then '"+StartHandLevel+"' else '"+EndHandLevel+"' end";
				sql3+=" , a.endHandWeight=case a.endType when '1' then '"+StartHandWeight+"' else '"+EndHandWeight+"' end";
				sql3+=" where b.id="+tankLog.getId();
				
				
				String sql4="update t_pcs_tanklogstore a   set a.id=a.id";
				sql4+=" ,a.realAmount=round(a.endWeight-a.startWeight,3)";
				sql4+=" ,a.measureAmount=round(a.endHandWeight-a.startHandWeight,3)";
				sql4+=" ,a.differAmount=round(a.endHandWeight-a.startHandWeight-(a.endWeight-a.startWeight),3)";
				sql4+=" where a.startLogId="+tankLog.getId()+" or a.endLogId="+tankLog.getId();
				
				
				try {
					execute(sql);
					execute(sql1);
					execute(sql2);
					execute(sql3);
					execute(sql4);
//					update(contract);
				} catch (OAException e) {
					e.printStackTrace();
				}
		
	}

	@Override
	public List<Map<String, Object>> getTankLogListByTank(TankLogDto tankLogDto,int start,int limit)
			throws OAException {
		String sql="select a.*,b.code tankCode,c.name productName,from_unixtime(a.startTime) mStartTime,from_unixtime(a.endTime) mEndTime from t_pcs_tank_log a " +
				" INNER JOIN t_pcs_tank b on b.id=a.tankId INNER JOIN t_pcs_product c on c.id=a.productId where 1=1 ";
		if(!Common.isNull(tankLogDto.getId())){
			sql+=" and a.id="+tankLogDto.getId();
		}
		if(!Common.isNull(tankLogDto.getTankId())){
			sql+=" and a.tankId="+tankLogDto.getTankId();
		}
		if(!Common.isNull(tankLogDto.getProductId())){
			sql+=" and a.productId="+tankLogDto.getProductId();
		}
		if(!Common.empty(tankLogDto.getYear())){
			sql+=" and a.startTime>="+tankLogDto.getStartTime()+" and a.startTime<"+tankLogDto.getEndTime();
		}
		if(!Common.empty(tankLogDto.getTankName())){
			sql+=" and b.code like '%"+tankLogDto.getTankName()+"%'";
		}
		sql+=" order by a.startTime desc";
		if(limit!=0){
			sql+=" limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public int getTankLogListCount(TankLogDto tankLogDto) throws OAException {
		String sql="select count(*) from t_pcs_tank_log a " +
				" INNER JOIN t_pcs_tank b on b.id=a.tankId where 1=1 ";
		if(!Common.isNull(tankLogDto.getId())){
			sql+=" and a.id="+tankLogDto.getId();
		}
		if(!Common.isNull(tankLogDto.getTankId())){
			sql+=" and a.tankId="+tankLogDto.getTankId();
		}
		if(!Common.isNull(tankLogDto.getProductId())){
			sql+=" and a.productId="+tankLogDto.getProductId();
		}
		if(!Common.empty(tankLogDto.getYear())){
			sql+=" and a.startTime>="+tankLogDto.getStartTime()+" and a.startTime<"+tankLogDto.getEndTime();
		}
		sql+=" order by a.startTime desc";
		return (int) getCount(sql);
	}

	@Override
	public void deleteTankLogByTransPortId(Integer id) throws OAException {
		try{
		String sql="delete from t_pcs_store where transportId="+id;
			executeUpdate(sql);
		}catch(RuntimeException e){
			LOG.error("dao删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除失败", e);
		}
	}



	@Override
	public void deleteTankLog(String ids) throws OAException {
		try{
			
			String sql="delete from t_pcs_tank_log where id in ("+ids+")";
			String sql1="delete from t_pcs_tanklogstore where startLogId in ("+ids+")";
			String sql2="update t_pcs_tanklogstore set endLevel='' ,endWeight='',endTemperature='',endHandLevel='',endHandWeight='' where endLogId in ("+ids+")";
			String sql3="update t_pcs_tanklogstore a   set a.id=a.id";
			sql3+=" ,a.realAmount=round(a.endWeight-a.startWeight,3)";
			sql3+=" ,a.measureAmount=round(a.endHandWeight-a.startHandWeight,3)";
			sql3+=" ,a.differAmount=round(a.endHandWeight-a.startHandWeight-(a.endWeight-a.startWeight),3)";
			sql3+=" where a.endLogId in ("+ids+")";
			
			executeUpdate(sql);
				executeUpdate(sql1);
				executeUpdate(sql2);
				executeUpdate(sql3);
				
				
				String sql4="update t_pcs_store a set";
				sql4+="  a.startLevel= 0";
				sql4+=",a.startWeight=0";
				sql4+=",a.startTemperature=0 ";
				sql4+=",a.startHand= 0 ";
				sql4+=",a.startHandLevel= 0 ";
				sql4+=",a.startHandWeight= 0 ";
				sql4+=",a.startDiffer= 0 ";
				sql4+=" where a.id in (select startStoreId from t_pcs_tank_log where id in ("+ids+") )";
				
				String sql5="update t_pcs_store a set";
				sql5+="  a.endLevel= 0";
				sql5+=",a.endWeight=0";
				sql5+=",a.endTemperature=0 ";
				sql5+=",a.endHand= 0 ";
				sql5+=",a.endHandLevel= 0 ";
				sql5+=",a.endHandWeight= 0 ";
				sql5+=",a.endDiffer= 0 ";
				sql5+=" where a.id in (select endStoreId from t_pcs_tank_log where id in ("+ids+") )";
				executeUpdate(sql4);
				executeUpdate(sql5);
				
				
				
				String sql6="update t_pcs_store a ";
				
				sql6+=" set a.realAmount=round(a.endWeight-a.startWeight,3)";
				sql6+=" ,a.measureAmount=round(a.endHandWeight-a.startHandWeight,3)";
				sql6+=" ,a.differAmount=round(a.endHandWeight-a.startHandWeight-(a.endWeight-a.startWeight),3)";
				sql6+=" where a.id in (select startStoreId from t_pcs_tank_log where id in ("+ids+") )  or a.id in (select endStoreId from t_pcs_tank_log where id in ("+ids+") )";
				executeUpdate(sql6);
				
			}catch(RuntimeException e){
				LOG.error("dao删除失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除失败", e);
			}
	}



	@Override
	public void disconnect(int tankLogId, int type) throws OAException {
		// TODO Auto-generated method stub
		
		boolean isStart=false;
		
		String sq="update t_pcs_store a LEFT JOIN t_pcs_tank_log b on ";
				
		
		if(type==1){
			sq+=" b.startStoreId=a.id ";
			isStart=true;
		}
		if(type==2){
			sq+=" b.endStoreId=a.id";
			isStart=false;
		}
		sq+=" set a.id=a.id ";
		
		if(isStart){
			sq+=",a.startLevel= case b.startStoreType when 1 then 0 else a.startLevel end ";
			sq+=",a.startWeight= case b.startStoreType when 1 then 0 else a.startWeight end ";
			sq+=",a.startTemperature= case b.startStoreType when 1 then 0 else a.startTemperature end ";
			sq+=",a.startHand= case b.startStoreType when 1 then 0 else a.startHand end ";
			sq+=",a.startHandLevel= case b.startStoreType when 1 then 0 else a.startHandLevel end ";
			sq+=",a.startHandWeight= case b.startStoreType when 1 then 0 else a.startHandWeight end ";
			sq+=",a.startDiffer= case b.startStoreType when 1 then 0 else a.startDiffer end ";
			
			sq+=",a.endLevel= case b.startStoreType when 2 then 0 else a.endLevel end ";
			sq+=",a.endWeight= case b.startStoreType when 2 then 0 else a.endWeight end ";
			sq+=",a.endTemperature= case b.startStoreType when 2 then 0 else a.endTemperature end ";
			sq+=",a.endHand= case b.startStoreType when 2 then 0 else a.endHand end ";
			sq+=",a.endHandLevel= case b.startStoreType when 2 then 0 else a.endHandLevel end ";
			sq+=",a.endHandWeight= case b.startStoreType when 2 then 0 else a.endHandWeight end ";
			sq+=",a.endDiffer= case b.startStoreType when 2 then 0 else a.endDiffer end ";
			
			
		}else{
			sq+=",a.startLevel= case b.endStoreType when 1 then 0 else a.startLevel end ";
			sq+=",a.startWeight= case b.endStoreType when 1 then 0 else a.startWeight end ";
			sq+=",a.startTemperature= case b.endStoreType when 1 then 0 else a.startTemperature end ";
			sq+=",a.startHand= case b.endStoreType when 1 then 0 else a.startHand end ";
			sq+=",a.startHandLevel= case b.endStoreType when 1 then 0 else a.startHandLevel end ";
			sq+=",a.startHandWeight= case b.endStoreType when 1 then 0 else a.startHandWeight end ";
			sq+=",a.startDiffer= case b.endStoreType when 1 then 0 else a.startDiffer end ";
			
			sq+=",a.endLevel= case b.endStoreType when 2 then 0 else a.endLevel end ";
			sq+=",a.endWeight= case b.endStoreType when 2 then 0 else a.endWeight end ";
			sq+=",a.endTemperature= case b.endStoreType when 2 then 0 else a.endTemperature end ";
			sq+=",a.endHand= case b.endStoreType when 2 then 0 else a.endHand end ";
			sq+=",a.endHandLevel= case b.endStoreType when 2 then 0 else a.endHandLevel end ";
			sq+=",a.endHandWeight= case b.endStoreType when 2 then 0 else a.endHandWeight end ";
			sq+=",a.endDiffer= case b.endStoreType when 2 then 0 else a.endDiffer end ";
			
		}
		sq+=" where b.id="+tankLogId;
		
		
		
		String sql1="update t_pcs_store a LEFT JOIN t_pcs_tank_log b on ";
				
		
		if(type==1){
			sql1+=" b.startStoreId=a.id ";
		}
		if(type==2){
			sql1+=" b.endStoreId=a.id";
		}
		sql1+=" set a.id=a.id ";
		
		sql1+=" ,a.realAmount=round(a.endWeight-a.startWeight,3)";
		sql1+=" ,a.measureAmount=round(a.endHandWeight-a.startHandWeight,3)";
		sql1+=" ,a.differAmount=round(a.endHandWeight-a.startHandWeight-(a.endWeight-a.startWeight),3)";
		sql1+=" where b.id="+tankLogId;
		
		
		
		try{
			String sql="update t_pcs_tank_log set ";
			if(type==1){
				sql+=" startStoreId=0,startStoreType=0";
			}
			if(type==2){
				sql+=" endStoreId=0,endStoreType=0";
			}
			sql+=" where id="+tankLogId;
				executeUpdate(sq);
				executeUpdate(sql1);
				executeUpdate(sql);
			}catch(RuntimeException e){
				LOG.error("dao删除失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新失败", e);
			}
	}



	@Override
	public List<Map<String, Object>> getTankListByTank(TankLogDto tankLogDto,
			int start, int limit) throws OAException {
		String sql="select DISTINCT(d.tankId),d.productId,e.code code,f.name productName,ifnull(ROUND(e.capacityTotal-e.capacityCurrent,0),0) wy,ifnull(ROUND(e.capacityCurrent,0),0) yy from t_pcs_tank_log d LEFT JOIN t_pcs_tank e on e.id=d.tankId left join t_pcs_product f on f.id=d.productId where 1=1";
		
		
//		String sql="select a.*,b.code tankCode,c.name productName,from_unixtime(a.startTime) mStartTime,from_unixtime(a.endTime) mEndTime from t_pcs_tank_log a " +
//				" LEFT JOIN t_pcs_tank b on b.id=a.tankId LEFT JOIN t_pcs_product c on c.id=a.productId where 1=1 ";
		if(!Common.isNull(tankLogDto.getId())){
			sql+=" and d.id="+tankLogDto.getId();
		}
		if(!Common.isNull(tankLogDto.getTankId())){
			sql+=" and d.tankId="+tankLogDto.getTankId();
		}
		if(!Common.isNull(tankLogDto.getProductId())&&tankLogDto.getProductId()!=0){
			sql+=" and d.productId="+tankLogDto.getProductId();
		}
		if(!Common.empty(tankLogDto.getYear())){
			sql+=" and d.startTime>="+tankLogDto.getStartTime()+" and d.startTime<"+tankLogDto.getEndTime();
		}
		sql+=" order by d.tankId asc";
		if(limit!=0){
			sql+=" limit "+start+","+limit;
		}
		return executeQuery(sql);
	}



	@Override
	public int getTankListCount(TankLogDto tankLogDto) throws OAException {
String sql="select count(*) from t_pcs_tank a left join t_pcs_product b on a.productId=b.id left join t_pcs_tank_type c on a.type=c.key  where 1=1 and a.id in(select DISTINCT(d.tankId) from t_pcs_tank_log d where 1=1";
		
		
//		String sql="select a.*,b.code tankCode,c.name productName,from_unixtime(a.startTime) mStartTime,from_unixtime(a.endTime) mEndTime from t_pcs_tank_log a " +
//				" LEFT JOIN t_pcs_tank b on b.id=a.tankId LEFT JOIN t_pcs_product c on c.id=a.productId where 1=1 ";
		if(!Common.isNull(tankLogDto.getId())){
			sql+=" and d.id="+tankLogDto.getId();
		}
		if(!Common.isNull(tankLogDto.getTankId())){
			sql+=" and d.tankId="+tankLogDto.getTankId();
		}
		if(!Common.isNull(tankLogDto.getProductId())){
			sql+=" and d.productId="+tankLogDto.getProductId();
		}
		if(!Common.empty(tankLogDto.getYear())){
			sql+=" and d.startTime>="+tankLogDto.getStartTime()+" and d.startTime<"+tankLogDto.getEndTime();
		}
		sql+=" order by d.startTime asc)";
		return (int) getCount(sql);
	}



	@Override
	public int addTankLogStore(TankLogStore tanklogStore) throws OAException {
		try {
			  Serializable s=  save(tanklogStore);
			  return Integer.valueOf(s.toString());
			} catch (RuntimeException e) {
			LOG.error("dao添加台账关联失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加储罐台账失败", e);
			}
	}



	@Override
	public void updateTankLogStore(TankLogStore tanklogStore)
			throws OAException {
		String sql="update t_pcs_tanklogstore a   set a.id=a.id";
		if(!Common.isNull(tanklogStore.getEndType())){
			sql+=" ,a.endType="+tanklogStore.getEndType();
		}
		if(!Common.isNull(tanklogStore.getEndLogId())){
			sql+=" ,a.endLogId="+tanklogStore.getEndLogId();
		}
		if(tanklogStore.getStartLevel()!=null){
			sql+=" ,a.startLevel='"+tanklogStore.getStartLevel()+"'";
		}
		if(tanklogStore.getEndLevel()!=null){
			sql+=" ,a.endLevel='"+tanklogStore.getEndLevel()+"'";
		}
		if(tanklogStore.getStartWeight()!=null){
			sql+=" ,a.startWeight='"+tanklogStore.getStartWeight()+"'";
		}
		if(tanklogStore.getEndWeight()!=null){
			sql+=" ,a.endWeight='"+tanklogStore.getEndWeight()+"'";
		}
		if(tanklogStore.getStartTemperature()!=null){
			sql+=" ,a.startTemperature='"+tanklogStore.getStartTemperature()+"'";
		}
		if(tanklogStore.getEndTemperature()!=null){
			sql+=" ,a.endTemperature='"+tanklogStore.getEndTemperature()+"'";
		}
//		if(!Common.empty(store.getRealAmount())){
//			sql+=" ,a.realAmount='"+store.getRealAmount()+"'";
//		}
//		if(!Common.empty(store.getMeasureAmount())){
//			sql+=" ,a.measureAmount='"+store.getMeasureAmount()+"'";
//		} 
//		if(!Common.empty(store.getDifferAmount())){
//			sql+=" ,a.differAmount='"+store.getDifferAmount()+"'";
//		}
		if(tanklogStore.getStartHandLevel()!=null){
			sql+=" ,a.startHandLevel='"+tanklogStore.getStartHandLevel()+"'";
		}
		if(tanklogStore.getEndHandLevel()!=null){
			sql+=" ,a.endHandLevel='"+tanklogStore.getEndHandLevel()+"'";
		}
		if(tanklogStore.getStartHandWeight()!=null){
			sql+=" ,a.startHandWeight='"+tanklogStore.getStartHandWeight()+"'";
		}
		if(tanklogStore.getEndHandWeight()!=null){
			sql+=" ,a.endHandWeight='"+tanklogStore.getEndHandWeight()+"'";
		}
//			sql+=" ,a.differAmount=a.measureAmount-a.realAmount";
		sql+=" where a.id="+tanklogStore.getId();
		
		
		String sql1="update t_pcs_tanklogstore a   set a.id=a.id";
		sql1+=" ,a.realAmount=round(a.endWeight-a.startWeight,3)";
		sql1+=" ,a.measureAmount=round(a.endHandWeight-a.startHandWeight,3)";
		sql1+=" ,a.differAmount=round(a.endHandWeight-a.startHandWeight-(a.endWeight-a.startWeight),3)";
		sql1+=" where a.id="+tanklogStore.getId();
		
		
		
		try {
			execute(sql);
			execute(sql1);
//			update(contract);
		} catch (OAException e) {
			e.printStackTrace();
		}
		
	}



	@Override
	public List<Map<String, Object>> getTankLogStore(int tankLogStoreId)
			throws OAException {
		
		String sql="select * from t_pcs_tanklogstore where id="+tankLogStoreId;
		return executeQuery(sql);
	}



	@Override
	public void deleteTankLogStore(int tanklogStoreId) throws OAException {
		try{
			String sql="delete from t_pcs_tanklogstore where id="+tanklogStoreId;
				executeUpdate(sql);
			}catch(RuntimeException e){
				LOG.error("dao删除失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除失败", e);
			}
		
	}



	@Override
	public List<Map<String, Object>> getTankLogStore(int arrivalId,
			int productId) throws OAException {
		
		String sql="select a.*,c.code tankCode,d.id productId,d.name productName from t_pcs_tanklogstore a LEFT JOIN view_store b on a.arrivalId=b.arrivalId and a.tankId=b.tankId LEFT JOIN t_pcs_tank c on c.id=a.tankId LEFT JOIN t_pcs_product d on d.id=b.productId where a.arrivalId="+arrivalId+" and b.productId="+productId;
		return executeQuery(sql);
	}



	@Override
	public List<Map<String, Object>> getTankLogStoreSum(int arrivalId,
			int productId) throws OAException {
		
		String sql="select round(sum(a.realAmount),3) realAmount,round(sum(a.measureAmount),3) measureAmount,c.code tankCode,a.tankId,d.id productId,d.name productName from t_pcs_tanklogstore a LEFT JOIN view_store b on a.arrivalId=b.arrivalId and a.tankId=b.tankId LEFT JOIN t_pcs_tank c on c.id=a.tankId LEFT JOIN t_pcs_product d on d.id=b.productId where a.arrivalId="+arrivalId+" and b.productId="+productId+" group by a.tankId";
		return executeQuery(sql);
	}



	@Override
	public void deleteTankLogByTankId(String tankIds) throws OAException {
		String sql="delete from t_pcs_tank_log where tankId in ("+tankIds+")";
		executeUpdate(sql);
	}


}
