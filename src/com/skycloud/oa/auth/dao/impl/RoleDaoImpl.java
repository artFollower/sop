package com.skycloud.oa.auth.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

import com.skycloud.oa.auth.dao.RoleDao;
import com.skycloud.oa.auth.model.Role;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.Common;

/**
 * 角色持久化操作实现类
 * @ClassName: RoleDaoImpl 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 上午11:09:49
 */
@Component
public class RoleDaoImpl extends BaseDaoImpl implements RoleDao {
	
	@Override
	public int create(Role role) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(role).toString());
	}

	@Override
	public List<Map<String, Object>> get(Role role, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_auth_role where 1=1";
		if(!Common.isNull(role.getId())) {
			sql += " and id = "+role.getId()+"";
		}
		if(!Common.empty(role.getName())) {
			sql += " and name like '%"+role.getName()+"%'";
		}
		if(!Common.empty(role.getType())) {
			sql += " and type = '"+role.getType()+"'";
		}
		if(!Common.empty(role.getStatus()) && !Common.isNull(role.getStatus())) {
			sql += " and status = "+role.getStatus()+"";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(Role role) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_auth_role where 1=1";
		if(!Common.isNull(role.getId())) {
			sql += " and id = "+role.getId()+"";
		}
		if(!Common.empty(role.getName())) {
			sql += " and name like '%"+role.getName()+"%'";
		}
		if(!Common.empty(role.getType())) {
			sql += " and type = '"+role.getType()+"'";
		}
		if(!Common.empty(role.getStatus())) {
			sql += " and status = "+role.getStatus()+"";
		}
		return getCount(sql);
	}

	@Override
	public boolean modify  (Role role) throws OAException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		if(!Common.empty(role.getName())) {
			sql.append("name='"+role.getName()+"',");
		}
		if(!Common.empty(role.getDescription())) {
			sql.append("description='"+role.getDescription()+"',");
		}
		if(!Common.empty(role.getStatus())) {
			sql.append("status='"+role.getStatus()+"',");
		}
		if(!Common.empty(role.getType())) {
			sql.append("type='"+role.getType()+"',");
		}
		String _sql = sql.toString();
		if(!Common.empty(_sql)) {
			_sql = _sql.substring(0, _sql.lastIndexOf(","));
			_sql = "update t_auth_role set "+_sql+"where id="+role.getId();
			return executeUpdate(_sql) > 0 ? true :false;
		}else {
			throw new OAException(Constant.SYS_CODE_NULL_ERR, "要更新的参数为空");
		}
		
	}

	@Override
	public boolean delete(String ids) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_auth_role";
		if(ids.contains(",")) {
			sql += " where id in("+ids+")";
		}else {
			sql += " where id = "+ids+"";
		}
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public List<Map<String, Object>> getGrantPermission(Role role) throws OAException {
		// TODO Auto-generated method stub
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT r.*,t.roleId FROM t_auth_security_resources r LEFT OUTER JOIN (SELECT * FROM t_auth_resource_assignments WHERE roleId =");
		sql.append(role.getId());
		sql.append(") t ON t.sourceId = r.id,t_auth_resource_assignments ra,t_auth_authorization a WHERE r.id = ra.sourceId AND ra.roleId = a.roleId and r.status = 0 AND a.userId = ");
		sql.append(user.getId());
		return executeQuery(sql.toString());
	}

	@Override
	public List<Map<String, Object>> getUnGrantPermission(Role role) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM	 t_auth_security_resources r WHERE r.id NOT IN (SELECT a.sourceId	FROM t_auth_resource_assignments a WHERE a.roleId ="+role.getId()+") AND r.status = 0";
		return executeQuery(sql);
	}

	@Override
	public boolean grantPermission(String roleId, String permissionIds) throws OAException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO t_auth_resource_assignments(roleId,sourceId) SELECT '"+roleId+"',r.id FROM t_auth_security_resources r WHERE r.id in("+permissionIds+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public boolean recoverPermission(String ids) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_auth_resource_assignments where id in ("+ids+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public boolean modifyPermission(Role role) throws OAException
	{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("delete from t_auth_resource_assignments where roleId = ");
		sql.append(role.getId());
		execute(sql.toString());
		
		if(!Common.empty(role.getPermissions())) {
			String permission = role.getPermissions();
			if(permission.endsWith(",")) {
				permission = permission.substring(0,permission.lastIndexOf(","));
			}
			return grantPermission(role.getId()+"",permission);
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> getUser(Role role, long start, long limit)
			throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT au.userId,u.account,u.`name`,u.`status`,u.category,r.`name` as roleName FROM"
				+" t_auth_user u,t_auth_authorization au,t_auth_role r WHERE u.id = "
				+"au.userId AND r.id = au.roleId ";
		if(!Common.isNull(role.getId())){
			sql+=" and r.id="+role.getId()+"";
		}
		
		if(limit != 0) {
			sql += " order by u.id desc limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long countUser(Role role) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_auth_authorization where 1=1 ";
		if(!Common.isNull(role.getId())){
			sql+=" and roleId="+role.getId()+"";
		}
		return getCount(sql);
	}

	
	
}
