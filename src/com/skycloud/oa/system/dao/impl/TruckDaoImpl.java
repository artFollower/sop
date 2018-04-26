package com.skycloud.oa.system.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.TruckDao;
import com.skycloud.oa.system.dto.TruckDto;
import com.skycloud.oa.system.model.Truck;
import com.skycloud.oa.utils.Common;

@Repository
public class TruckDaoImpl extends BaseDaoImpl implements TruckDao{
	private static Logger LOG = Logger.getLogger(TruckDaoImpl.class);
	@Override
	public List<Map<String, Object>> getTruckList(TruckDto tDto,int start,int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(tDto.getLetter())&&!("all").equals(tDto.getLetter())){
				 sql = "select a.*,s.name editUserName from t_pcs_truck a LEFT JOIN t_auth_user s on s.id=a.editUserId,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=tDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+tDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select a.*,s.name editUserName from t_pcs_truck a LEFT JOIN t_auth_user s on s.id=a.editUserId where 1=1 and isnull(a.status) ";
			}
			
			
//			String sql="select * from t_pcs_truck where 1=1 ";
			if(!Common.empty(tDto.getId())){
				sql+=" and a.id="+tDto.getId();
			}
			if(!Common.empty(tDto.getCode())){
				sql+=" and ( a.code like '%"+tDto.getCode()+"%' or  a.name like '%"+tDto.getName()+"%' or  a.company like '%"+tDto.getCompany()+"%' ) ";
			}
			sql+=" order by a.editTime DESC";
			if(limit!=0){
				sql+=" limit "+start+" , "+limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	
	}
	
	
	
	
	@Override
	public int getTruckCount(TruckDto tDto,int start,int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(tDto.getLetter())&&!("all").equals(tDto.getLetter())){
				 sql = "select count(1) from t_pcs_truck a ,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=tDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+tDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select count(1) from t_pcs_truck a where 1=1 and isnull(a.status) ";
			}
			
//			String sql="select count(1) from t_pcs_truck where 1=1 ";
			if(!Common.empty(tDto.getId())){
				sql+=" and a.id="+tDto.getId();
			}
			if(!Common.empty(tDto.getCode())){
				sql+=" and ( a.code like '%"+tDto.getCode()+"%' or  a.name like '%"+tDto.getName()+"%' or  a.company like '%"+tDto.getCompany()+"%' ) ";
			}
			sql+=" order by a.editTime DESC";
			return (int)getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	
	}

	@Override
	public int addTruck(Truck truck) throws OAException {
		Serializable s = save(truck);
		return Integer.parseInt(s.toString()) ;
	}

	@Override
	public void updateTruck(Truck truck) throws OAException {
try {
	update(truck);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}		
	}

	@Override
	public Map<String, Object> getTruckByCode(String code) throws Exception {
		String sql = "select * from t_pcs_truck where lower(code)='"+code+"' limit 0,1" ;
		return executeQueryOne(sql) ;
	}
	@Override
	public void deleteTruck(String ids) throws OAException {
try {
	String sql="update t_pcs_truck set status=1 where id in ("+ids+")";
	execute(sql);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}		
	}

}
