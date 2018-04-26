package com.skycloud.oa.auth.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.auth.dao.OrganizationDao;
import com.skycloud.oa.auth.model.Parties;
import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.Common;

/**
 * 
 * @ClassName: OrganizationDaoImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月16日 上午10:34:30
 */
@Component
public class OrganizationDaoImpl extends BaseDaoImpl implements OrganizationDao {
	
	@Override
	public int create(Parties parties) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(parties).toString());
	}

	@Override
	public List<Map<String, Object>> get(Parties parties, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_auth_parties where ";
		if(!Common.empty(parties.getCategory())) {
			sql += " category = '"+parties.getCategory()+"'";
		}else {
			sql += " category = '"+C.AUTH.PERTIES_CATEGORY_COMPANY+"' or category = '"+C.AUTH.PERTIES_CATEGORY_DEPARTMENT+"'";
		}
		if(!Common.isNull(parties.getId())) {
			sql += " and id = "+parties.getId()+"";
		}
		if(!Common.empty(parties.getParties()) && !Common.isNull(parties.getParties().getId())) {
			sql += " and parentId = "+parties.getParties().getId()+"";
		}
		if(!Common.empty(parties.getUser()) && !Common.isNull(parties.getUser().getId())) {
			sql += " and userId = "+parties.getUser().getId()+"";
		}
		if(!Common.empty(parties.getName())) {
			sql += " and name like '%"+parties.getName()+"%'";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public boolean modify(Parties parties) throws OAException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
			sql.append("name='"+parties.getName()+"',");
			sql.append("description='"+parties.getDescription()+"',");
			sql.append("parentId='"+parties.getParties().getId()+"',");
		String _sql = sql.toString();
		if(!Common.empty(_sql)) {
			_sql = _sql.substring(0, _sql.lastIndexOf(","));
			_sql = "update t_auth_parties set "+_sql+" where id="+parties.getId();
			return executeUpdate(_sql) > 0 ? true :false;
		}else {
			throw new OAException(Constant.SYS_CODE_NULL_ERR, "要更新的参数为空");
		}
	}

	@Override
	public boolean delete(String ids) throws OAException {
		// TODO Auto-generated method stub
		return executeUpdate("delete from t_auth_parties where id in("+ids+")") > 0 ? true :false;
	}

	@Override
	public long count(Parties parties) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_auth_parties where ";
		if(!Common.empty(parties.getCategory())) {
			sql += " category = '"+parties.getCategory()+"'";
		}else {
			sql += " category = '"+C.AUTH.PERTIES_CATEGORY_COMPANY+"' or category = '"+C.AUTH.PERTIES_CATEGORY_DEPARTMENT+"'";
		}
		if(!Common.isNull(parties.getId())) {
			sql += " and id = "+parties.getId();
		}
		if(!Common.empty(parties.getUser()) && !Common.isNull(parties.getUser().getId())) {
			sql += " and userId = "+parties.getUser().getId();
		}
		if(!Common.empty(parties.getName())) {
			sql += " and name like '%"+parties.getName()+"%'";
		}
		return getCount(sql);
	}

	@Override
	public List<Map<String, Object>> getRelationUser(Parties parties, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT p.id,u.`name`,u.account,u.email,u.phone,p1.name departmentName,p.userId  FROM t_auth_parties p,t_auth_user u,t_auth_parties p1 WHERE u.id = p.userId and p1.id=p.parentId";
		if(!Common.isNull(parties.getId())){
			sql+=" AND p.parentId = "+parties.getId();
		}
		if(limit!=0){
			sql+=" limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long countRelationUser(Parties parties) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT count(1) FROM t_auth_parties p,t_auth_user u WHERE u.id = p.userId AND p.id = "+parties.getId();
		return getCount(sql);
	}

	@Override
	public List<Map<String, Object>> getUnRelationUser(Parties parties, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
//		String sql = "SELECT * from t_auth_user u WHERE u.id NOT in (SELECT userId from t_auth_parties p WHERE p.parentId = "+parties.getId()+") limit "+start+","+limit;
		String sql = "SELECT * from t_auth_user u WHERE u.id NOT in (SELECT userId from t_auth_parties WHERE userId IS NOT NULL) limit "+start+","+limit;
		return executeQuery(sql);
	}

	@Override
	public long countUnRelationUser(Parties parties) throws OAException {
		// TODO Auto-generated method stub
//		String sql = "SELECT count(1) from t_auth_user u WHERE u.id NOT in (SELECT userId from t_auth_parties p WHERE p.parentId = "+parties.getId()+")";
		String sql = "SELECT count(1) from t_auth_user u WHERE u.id NOT in (SELECT userId from t_auth_parties WHERE userId IS NOT NULL)";
		return getCount(sql);
	}

	@Override
	public boolean relationUser(String userIds, int departmentId) throws OAException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO t_auth_parties(name,userId,parentId,category) SELECT `name`,id,"+departmentId+",'EMPLOYEE' FROM t_auth_user WHERE id in ("+userIds+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public boolean modifyEmployee(Parties parties) throws OAException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
			sql.append("name='"+parties.getName()+"',");
			sql.append("description='"+parties.getDescription()+"',");
			sql.append("parentId='"+parties.getParties().getId()+"',");
			sql.append("sn='"+parties.getSn()+"',");
			if(!Common.empty(parties.getClient()) && !Common.isNull(parties.getClient().getId())) {
				sql.append("clientId='"+parties.getClient().getId()+"',");
			}
			if(!Common.empty(parties.getClientGroup()) && !Common.isNull(parties.getClientGroup().getId())) {
				sql.append("clientGroupId='"+parties.getClientGroup().getId()+"',");
			}
		String _sql = sql.toString();
		if(!Common.empty(_sql)) {
			_sql = _sql.substring(0, _sql.lastIndexOf(","));
			_sql = "update t_auth_parties set "+_sql+" where userId="+parties.getId();
			return executeUpdate(_sql) > 0 ? true :false;
		}else {
			throw new OAException(Constant.SYS_CODE_NULL_ERR, "要更新的参数为空");
		}
	}

	@Override
	public int createEmployee(Parties parties) throws OAException {
		// TODO Auto-generated method stub
		String sql = "insert into t_auth_parties(category,name,description,sn,userId,parentId,clientId,clientGroupId)";
		sql += "values('"+C.AUTH.PERTIES_CATEGORY_EMPLOYEE+"','"+parties.getName()+"','"+parties.getDescription()+"','"+parties.getSn()+"','"+(Common.empty(parties.getUser())?"":parties.getUser().getId())+"','"+(Common.empty(parties.getParties())?"":parties.getParties().getId())+"','"+(Common.empty(parties.getClient().getId())?"0":parties.getClient().getId())+"','"+(Common.empty(parties.getClientGroup().getId())?"0":parties.getClientGroup().getId())+"')";
		return insert(sql);
	}
	
	
}
