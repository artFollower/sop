package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.OperateLogDao;
import com.skycloud.oa.system.model.OperateLog;
import com.skycloud.oa.utils.Common;

@Repository
public class OperateLogDaoImpl extends BaseDaoImpl implements OperateLogDao{

	@Override
	public int create(OperateLog log) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(log).toString());
	}

	@Override
	public List<Map<String, Object>> get(OperateLog log, int start, int limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT u.`name`,l.time,l.ip,o.name AS object,t.`name` AS type,l.content FROM (SELECT ip,";
		sql+="`user`,FROM_UNIXTIME(time) AS time,type,object,content FROM t_sys_log WHERE 1=1";
		if(!Common.empty(log.getType())) {
			sql += " and type = '"+log.getType()+"'";
		}
		if(!Common.empty(log.getObject())) {
			sql += " and object = '"+log.getObject()+"'";
		}
		
		if(!Common.empty(log.getContent())) {
			sql += " and content like '%"+log.getContent()+"%'";
		}
		if(!Common.empty(log.getUser()) && !Common.isNull(log.getUser().getId())) {
			sql += " and user = '"+log.getUser().getId()+"'";
		}
		if(!Common.empty(log.getStartTime())) {
			sql += " and time > UNIX_TIMESTAMP('"+log.getStartTime()+" 00:00:00') ";
		}
		if(!Common.empty(log.getEndTime())) {
			sql += " and time < UNIX_TIMESTAMP('"+log.getEndTime()+" 23:59:59') ";
		}
		sql += " ORDER BY time DESC ";
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		sql += ") l INNER JOIN (SELECT `name`,`key` FROM t_sys_params WHERE type = 'LOGTYPE') t ON ";
		sql+="t.key = l.type INNER JOIN (SELECT `name`,`key` FROM t_sys_params WHERE type = 'LOGOBJECT')";
		sql+=" o ON o.key = l.object INNER JOIN t_auth_user u ON u.id = l.`user`";
		return executeQuery(sql);
	}

	@Override
	public long count(OperateLog log) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_sys_log where 1=1";
		if(!Common.empty(log.getType())) {
			sql += " and type = '"+log.getType()+"'";
		}
		if(!Common.empty(log.getObject())) {
			sql += " and object = '"+log.getObject()+"'";
		}
		if(!Common.empty(log.getUser()) && !Common.isNull(log.getUser().getId())) {
			sql += " and user = '"+log.getUser().getId()+"'";
		}
		if(!Common.empty(log.getContent())) {
			sql += " and content like '%"+log.getContent()+"%'";
		}
		if(!Common.empty(log.getStartTime())) {
			sql += " and time > UNIX_TIMESTAMP('"+log.getStartTime()+" 00:00:00') ";
		}
		if(!Common.empty(log.getEndTime())) {
			sql += " and time < UNIX_TIMESTAMP('"+log.getEndTime()+" 23:59:59') ";
		}
		return getCount(sql);
	}
	
}
