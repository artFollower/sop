package com.skycloud.oa.inbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.StoreDao;
import com.skycloud.oa.inbound.dto.TankLogDto;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.system.dto.TankDto;
import com.skycloud.oa.utils.Common;
@Component
public class StoreDaoImpl extends BaseDaoImpl implements StoreDao {

	private static Logger LOG = Logger.getLogger(StoreDaoImpl.class);

	@Override
	public int addStore(Store Store) throws OAException{
		try {
			  Serializable s=  save(Store);
			  return Integer.valueOf(s.toString());
			} catch (RuntimeException e) {
			LOG.error("dao添加存储罐失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加存储罐失败", e);
			}
	}


	@Override
	public List<Map<String, Object>> getStoreList(String transportIds)throws OAException {
		try {
			String sql = "select a.id id,a.tankId tankId,a.realAmount realAmount,a.measureAmount measureAmount,a.differAmount differAmount,a.messageType,"
					+ "b.name tankName,b.code tankCode,b.description tankDescription,b.productId productId,c.name productName,b.capacityFree capacityFree,a.message message from t_pcs_store a  "
					+ " LEFT JOIN t_pcs_tank b on a.tankId=b.id "
					+ " LEFT JOIN t_pcs_product c on b.productId=c.id"
					+ " where 1=1 and transportId in ("+transportIds+") group by a.tankId";
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public void updateStore(Store store, int type, int logId, int connectType, int logType) throws OAException {
		String sql="update t_pcs_store a  LEFT JOIN t_pcs_transport_program b on a.transportId=b.id LEFT JOIN t_pcs_tank_log c on c.id="+logId+" set a.id=a.id";
		
		if(!Common.isNull(store.getTransportId())){
			sql+=" ,a.transportId="+store.getTransportId();
		}
		if(!Common.isNull(store.getTankId())){
			sql+=" ,a.tankId="+store.getTankId();
		}
		if(store.getStartLevel()!=null){
			sql+=" ,a.startLevel='"+store.getStartLevel()+"'";
		}
		if(store.getEndLevel()!=null){
			sql+=" ,a.endLevel='"+store.getEndLevel()+"'";
		}
		if(store.getStartWeight()!=null){
			sql+=" ,a.startWeight='"+store.getStartWeight()+"'";
			if(!Common.empty(store.getStartWeight())){
				sql+=" ,a.realAmount=round(a.endWeight-"+store.getStartWeight()+",3)";
			}else{
				sql+=" ,a.realAmount=null";
			}
		}
		if(store.getEndWeight()!=null){
			sql+=" ,a.endWeight='"+store.getEndWeight()+"'";
			if(!Common.empty(store.getEndWeight())){
				sql+=" ,a.realAmount=round("+store.getEndWeight()+"-a.startWeight,3)";
			}else{
				sql+=" ,a.realAmount=null";
			}
		}
		if(store.getStartTemperature()!=null){
			sql+=" ,a.startTemperature='"+store.getStartTemperature()+"'";
		}
		if(store.getEndTemperature()!=null){
			sql+=" ,a.endTemperature='"+store.getEndTemperature()+"'";
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
		if(store.getStartHand()!=null){
			sql+=" ,a.startHand='"+store.getStartHand()+"'";
		}
		if(store.getEndHand()!=null){
			sql+=" ,a.endHand='"+store.getEndHand()+"'";
		}
		if(!Common.isNull(store.getStartTime())){
			sql+=" ,a.startTime="+store.getStartTime();
		}
		if(!Common.isNull(store.getEndTime())){
			sql+=" ,a.endTime="+store.getEndTime();
		}
		if(store.getStartHandLevel()!=null){
			sql+=" ,a.startHandLevel='"+store.getStartHandLevel()+"'";
		}
		if(store.getEndHandLevel()!=null){
			sql+=" ,a.endHandLevel='"+store.getEndHandLevel()+"'";
		}
		if(store.getStartHandWeight()!=null){
			sql+=" ,a.startHandWeight='"+store.getStartHandWeight()+"'";
			if(!Common.empty(store.getStartHandWeight())){
				sql+=" ,a.measureAmount=round(a.endHandWeight-"+store.getStartHandWeight()+",3)";
				sql+=" ,a.differAmount=round(a.endHandWeight-"+store.getStartHandWeight()+"-(a.endWeight-"+store.getStartWeight()+"),3)";
			}else{
				sql+=" ,a.measureAmount=null";
				sql+=" ,a.differAmount=null";
			}
		}
		if(store.getEndHandWeight()!=null){
			sql+=" ,a.endHandWeight='"+store.getEndHandWeight()+"'";
			if(!Common.empty(store.getEndHandWeight())){
				sql+=" ,a.measureAmount=round("+store.getEndHandWeight()+"-a.startHandWeight,3)";
				sql+=" ,a.differAmount=round("+store.getEndHandWeight()+"-a.startHandWeight-("+store.getEndWeight()+"-a.startWeight),3)";
			}else{
				sql+=" ,a.measureAmount=null";
				sql+=" ,a.differAmount=null";
			}
		}
		if(store.getStartDiffer()!=null){
			sql+=" ,a.startDiffer='"+store.getStartDiffer()+"'";
		}
		if(store.getEndDiffer()!=null){
			sql+=" ,a.endDiffer='"+store.getEndDiffer()+"'";
		}
		if(store.getMessage()!=null){
			sql+=" ,a.message='"+store.getMessage()+"'";
		}
		if(!Common.isNull(store.getMessageType())){
			sql+=" ,a.messageType="+store.getMessageType();
		}
		if(logType==1){
			sql+=" ,c.startStoreId=a.id ,c.startStoreType="+connectType;
		}
		if(logType==2){
			sql+=" ,c.endStoreId=a.id ,c.endStoreType="+connectType;
		}
//			sql+=" ,a.differAmount=a.measureAmount-a.realAmount";
		sql+=" where a.transportId="+store.getTransportId()+" and a.tankId="+store.getTankId()+" and b.type="+type;
		if(!Common.isNull(store.getId())){
			sql+=" and a.id="+store.getId();
		}
		
		
		String sql1="update t_pcs_store a  LEFT JOIN t_pcs_transport_program b on a.transportId=b.id set a.id=a.id";
		sql1+=" ,a.realAmount=round(a.endWeight-a.startWeight,3)";
		sql1+=" ,a.measureAmount=round(a.endHandWeight-a.startHandWeight,3)";
		sql1+=" ,a.differAmount=round(a.endHandWeight-a.startHandWeight-(a.endWeight-a.startWeight),3)";
		sql1+=" where a.transportId="+store.getTransportId()+" and a.tankId="+store.getTankId()+" and b.type="+type;
		
		if(!Common.isNull(store.getId())){
			sql1+=" and a.id="+store.getId();
		}
		
		try {
			execute(sql);
			execute(sql1);
//			update(contract);
		} catch (OAException e) {
			e.printStackTrace();
		}
		
	}

	
	
	@Override
	public void updateStore(Store store)throws OAException {
		String sql="update t_pcs_store set id=id";
				if(!Common.isNull(store.getTransportId())){
					sql+=" ,transportId="+store.getTransportId();
				}
				if(!Common.isNull(store.getTankId())){
					sql+=" ,tankId="+store.getTankId();
				}
				if(store.getStartLevel()!=null){
					sql+=" ,startLevel='"+store.getStartLevel()+"'";
				}
				if(store.getEndLevel()!=null){
					sql+=" ,endLevel='"+store.getEndLevel()+"'";
				}
				if(store.getStartWeight()!=null){
					sql+=" ,startWeight='"+store.getStartWeight()+"'";
				}
				if(store.getEndWeight()!=null){
					sql+=" ,endWeight='"+store.getEndWeight()+"'";
				}
				if(store.getStartTemperature()!=null){
					sql+=" ,startTemperature='"+store.getStartTemperature()+"'";
				}
				if(store.getEndTemperature()!=null){
					sql+=" ,endTemperature='"+store.getEndTemperature()+"'";
				}
				if(!Common.empty(store.getRealAmount())){
					sql+=" ,realAmount='"+store.getRealAmount()+"'";
				}
				if(!Common.empty(store.getMeasureAmount())){
					sql+=" ,measureAmount='"+store.getMeasureAmount()+"'";
				}
				if(!Common.empty(store.getDifferAmount())){
					sql+=" ,differAmount='"+store.getDifferAmount()+"'";
				}
				if(store.getStartHand()!=null){
					sql+=" ,startHand='"+store.getStartHand()+"'";
				}
				if(store.getEndHand()!=null){
					sql+=" ,endHand='"+store.getEndHand()+"'";
				}
				if(!Common.isNull(store.getStartTime())){
					sql+=" ,startTime="+store.getStartTime();
				}
				if(!Common.isNull(store.getEndTime())){
					sql+=" ,endTime="+store.getEndTime();
				}
				if(store.getStartHandLevel()!=null){
					sql+=" ,startHandLevel='"+store.getStartHandLevel()+"'";
				}
				if(store.getEndHandLevel()!=null){
					sql+=" ,endHandLevel='"+store.getEndHandLevel()+"'";
				}
				if(store.getStartHandWeight()!=null){
					sql+=" ,startHandWeight='"+store.getStartHandWeight()+"'";
				}
				if(store.getEndHandWeight()!=null){
					sql+=" ,endHandWeight='"+store.getEndHandWeight()+"'";
				}
				if(store.getStartDiffer()!=null){
					sql+=" ,startDiffer='"+store.getStartDiffer()+"'";
				}
				if(store.getEndDiffer()!=null){
					sql+=" ,endDiffer='"+store.getEndDiffer()+"'";
				}
				if(store.getMessage()!=null){
					sql+=" ,message='"+store.getMessage()+"'";
				}
				if(!Common.isNull(store.getMessageType())){
					sql+=" ,messageType="+store.getMessageType();
				}
				sql+=" where id="+store.getId();
				
				try {
					execute(sql);
//					update(contract);
				} catch (OAException e) {
					e.printStackTrace();
				}
		
	}

	@Override
	public List<Map<String, Object>> getStoreListByTank(TankLogDto tankLogDto,int start,int limit)
			throws OAException {
		String sql="select a.*,b.code tankCode,from_unixtime(a.startTime) mStartTime,from_unixtime(a.endTime) mEndTime from t_pcs_store a " +
				" LEFT JOIN t_pcs_tank b on b.id=a.tankId where 1=1 and !ISNULL(a.message) ";
		if(!Common.isNull(tankLogDto.getId())){
			sql+=" and a.id="+tankLogDto.getId();
		}
		if(!Common.isNull(tankLogDto.getTankId())){
			sql+=" and a.tankId="+tankLogDto.getTankId();
		}
		sql+=" order by a.startTime asc";
		if(limit!=0){
			sql+=" limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public int getStoreListCount(TankLogDto tankLogDto) throws OAException {
		String sql="select count(*) from t_pcs_store a " +
				" LEFT JOIN t_pcs_tank b on b.id=a.tankId where 1=1 and !ISNULL(a.message)";
		if(!Common.isNull(tankLogDto.getId())){
			sql+=" and a.id="+tankLogDto.getId();
		}
		if(!Common.isNull(tankLogDto.getTankId())){
			sql+=" and a.tankId="+tankLogDto.getTankId();
		}
		sql+=" order by a.startTime desc";
		return (int) getCount(sql);
	}

	@Override
	public void deleteStoreByTransPortId(Integer id) throws OAException {
		try{
		String sql="delete from t_pcs_store where transportId="+id;
			executeUpdate(sql);
		}catch(RuntimeException e){
			LOG.error("dao删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除失败", e);
		}
	}


	@Override
	public void cleanStore(int id,boolean isTrue)throws OAException {
		try {
			String sql=null;
			if(isTrue){
				sql="delete from t_pcs_store where transportId="+id;
			}else{
				sql="delete from t_pcs_store where transportId in (select a.id from t_pcs_transport_program a where a.arrivalId="+id+")";
			}
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao 清空存储罐失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao 清空存储罐失败");
		}
	}




}
