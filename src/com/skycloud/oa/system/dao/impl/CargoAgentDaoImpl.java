package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.CargoAgentDao;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.model.CargoAgent;
import com.skycloud.oa.system.service.impl.CargoAgentServiceImpl;
import com.skycloud.oa.utils.Common;

@Repository
public class CargoAgentDaoImpl extends BaseDaoImpl implements CargoAgentDao{
	private static Logger LOG = Logger.getLogger(CargoAgentDaoImpl.class);

	@Override
	public List<Map<String, Object>> getCargoAgentList(CargoAgentDto caDto,int start,int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(caDto.getLetter())&&!("all").equals(caDto.getLetter())){
				 sql = "select a.*,s.name editUserName from t_pcs_cargo_agent a LEFT JOIN t_auth_user s on s.id=a.editUserId,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=caDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+caDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select a.*,s.name editUserName from t_pcs_cargo_agent a LEFT JOIN t_auth_user s on s.id=a.editUserId where 1=1 and isnull(a.status) ";
			}
			
			
			
//		String sql="select * from t_pcs_cargo_agent where 1=1 ";
		if(!Common.empty(caDto.getId())){
			sql+="and a.id="+caDto.getId();
		}
		if(!Common.empty(caDto.getCode())){
			sql+="and ( a.code like '%"+caDto.getCode()+"%' or a.name like '%"+caDto.getName()+"%' or a.contactName like '%"+caDto.getContactName()+"%' or a.contactPhone like '%"+caDto.getContactPhone()+"%')"  ; 
		}
		sql += " order by CONVERT( a.name USING gbk ) COLLATE gbk_chinese_ci ASC";
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
	public int getCargoAgentListCount(CargoAgentDto caDto) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(caDto.getLetter())&&!("all").equals(caDto.getLetter())){
				 sql = "select count(1) from t_pcs_cargo_agent a ,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=caDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+caDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select count(1) from t_pcs_cargo_agent a where 1=1 and isnull(a.status) ";
			}
			
			
//			String sql="select count(*) from t_pcs_cargo_agent where 1=1 ";
			if(!Common.empty(caDto.getId())){
				sql+="and a.id="+caDto.getId();
			}
			if(!Common.empty(caDto.getCode())){
				sql+="and ( a.code like '%"+caDto.getCode()+"%' or a.name like '%"+caDto.getName()+"%' or a.contactName like '%"+caDto.getContactName()+"%' or a.contactPhone like '%"+caDto.getContactPhone()+"%')"  ; 
			}
			
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}
	
	
	@Override
	public void addCargoAgent(CargoAgent cargoAgent) throws OAException {
		
		try {
			save(cargoAgent);
		} catch (RuntimeException e) {
			LOG.error("dao添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加失败",e);
		}
	}

	@Override
	public void updateCargoAgent(CargoAgent cargoAgent) throws OAException {
		try {
			update(cargoAgent);
		} catch (RuntimeException e) {
			LOG.error("dao更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao更新失败",e);
		}
	}

	@Override
	public void deleteCargoAgent(String ids) throws OAException {
		try {
			String sql="update t_pcs_cargo_agent set status=1 where id in ("+ids+")";
			execute(sql);
			
		} catch (RuntimeException e) {
			LOG.error("dao删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao删除失败",e);
		}
	}


	
}
