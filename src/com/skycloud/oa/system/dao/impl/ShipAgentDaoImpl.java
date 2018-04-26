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
import com.skycloud.oa.system.dao.ShipAgentDao;
import com.skycloud.oa.system.dto.ShipAgentDto;
import com.skycloud.oa.system.model.ShipAgent;
import com.skycloud.oa.utils.Common;

@Repository
public class ShipAgentDaoImpl extends BaseDaoImpl implements ShipAgentDao{
	private static Logger LOG = Logger.getLogger(ShipAgentDaoImpl.class);
	@Override
	public List<Map<String, Object>> getShipAgentList(ShipAgentDto saDto,int start,int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(saDto.getLetter())&&!("all").equals(saDto.getLetter())){
				 sql = "select a.*,s.name editUserName from t_pcs_ship_agent a LEFT JOIN t_auth_user s on s.id=a.editUserId,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=saDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+saDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select a.*,s.name editUserName from t_pcs_ship_agent a LEFT JOIN t_auth_user s on s.id=a.editUserId where 1=1 and isnull(a.status) ";
			}
			
//			String sql="select * from t_pcs_ship_agent a where 1=1 ";
			if(!Common.empty(saDto.getId())){
				sql+=" and a.id="+saDto.getId();
			}
			if(!Common.empty(saDto.getCode())){
				sql+=" and ( a.code like '%"+saDto.getCode()+"%' or  a.name like '%"+saDto.getName()+"%' or  a.contactName like '%"+saDto.getContactName()+"%' or  a.contactPhone like '%"+saDto.getContactPhone()+"%') ";
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
	public int getShipAgentCount(ShipAgentDto saDto,int start,int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(saDto.getLetter())&&!("all").equals(saDto.getLetter())){
				 sql = "select count(*) from t_pcs_ship_agent a ,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=saDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+saDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select count(*) from t_pcs_ship_agent a where 1=1 and isnull(a.status) ";
			}
			
			
//			String sql="select count(*) from t_pcs_ship_agent where 1=1 ";
			if(!Common.empty(saDto.getId())){
				sql+=" and a.id="+saDto.getId();
			}
			if(!Common.empty(saDto.getCode())){
				sql+=" and ( a.code like '%"+saDto.getCode()+"%' or  a.name like '%"+saDto.getName()+"%' or  a.contactName like '%"+saDto.getContactName()+"%' or  a.contactPhone like '%"+saDto.getContactPhone()+"%') ";
			}
			return (int)getCount(sql) ;
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	
	}

	@Override
	public void addShipAgent(ShipAgent shipAgent) throws OAException {
try {
	save(shipAgent);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}		
	}

	@Override
	public void updateShipAgent(ShipAgent shipAgent) throws OAException {
try {
	update(shipAgent);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}		
	}

	@Override
	public void deleteShipAgent(String ids) throws OAException {
try {
	String sql="update t_pcs_ship_agent set status=1 where id in ("+ids+")";
	execute(sql);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}		
	}

}
