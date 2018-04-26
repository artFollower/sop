package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.InspectAgentDao;
import com.skycloud.oa.system.dto.InspectAgentDto;
import com.skycloud.oa.system.model.InspectAgent;
import com.skycloud.oa.utils.Common;

@Repository
public class InspectAgentDaoImpl extends BaseDaoImpl implements InspectAgentDao {
	private static Logger LOG = Logger.getLogger(InspectAgentDaoImpl.class);

	@Override
	public List<Map<String, Object>> getInpectAgentList(InspectAgentDto iaDto,
			int start, int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(iaDto.getLetter())&&!("all").equals(iaDto.getLetter())){
				 sql = "select a.*,s.name editUserName from t_pcs_inspect_agent a LEFT JOIN t_auth_user s on s.id=a.editUserId,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=iaDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+iaDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select a.*,s.name editUserName from t_pcs_inspect_agent a LEFT JOIN t_auth_user s on s.id=a.editUserId where 1=1 and isnull(a.status) ";
			}
			
//			String sql = "select * from t_pcs_inspect_agent where 1=1 ";
			if (!Common.empty(iaDto.getId())) {
				sql += "and a.id=" + iaDto.getId();
			}
			if(!Common.empty(iaDto.getCode())){
				sql+="and ( a.code like '%"+iaDto.getCode()+"%' or  a.name like '%"+iaDto.getName()+"%' or  a.contactName like '%"+iaDto.getContactName()+"%' or  a.contactPhone like '%"+iaDto.getContactPhone()+"%') ";
			}
			sql += " order by CONVERT( a.name USING gbk ) COLLATE gbk_chinese_ci ASC";
			if (limit != 0) {
				sql += " limit " + start + " , " + limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}
	@Override
	public int getInpectAgentCount(InspectAgentDto iaDto,
			int start, int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(iaDto.getLetter())&&!("all").equals(iaDto.getLetter())){
				 sql = "select count(1) from t_pcs_inspect_agent a ,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=iaDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+iaDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select count(1) from t_pcs_inspect_agent a where 1=1 and isnull(a.status) ";
			}
			
//			String sql = "select count(1) from t_pcs_inspect_agent where 1=1 ";
			if (!Common.empty(iaDto.getId())) {
				sql += "and a.id=" + iaDto.getId();
			}
			if(!Common.empty(iaDto.getCode())){
				sql+="and ( a.code like '%"+iaDto.getCode()+"%' or  a.name like '%"+iaDto.getName()+"%' or  a.contactName like '%"+iaDto.getContactName()+"%' or  a.contactPhone like '%"+iaDto.getContactPhone()+"%') ";
			}
			return (int)getCount(sql) ;
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public void addInpectAgent(InspectAgent inpectAgent) throws OAException {
		try {
			save(inpectAgent);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public void updateInpectAgent(InspectAgent inpectAgent) throws OAException {
		try {
			update(inpectAgent);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

	@Override
	public void deleteInpectAgent(String ids) throws OAException {
		try {
			String sql = "update t_pcs_inspect_agent set status=1 where id in (" + ids
					+ ")";
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询失败", e);
		}
	}

}
