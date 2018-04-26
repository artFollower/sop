package com.skycloud.oa.common.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.common.dao.SecurityCodeDao;
import com.skycloud.oa.common.model.SecurityCode;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.Common;

/**
 * 验证码
 * @ClassName: SecurityCodeDaoImpl 
 * @Description: TODO
 * @author xie
 * @date 2016年1月14日 下午6:01:07
 */
@Component
public class SecurityCodeDaoImpl extends BaseDaoImpl implements SecurityCodeDao
{

	@Override
	public boolean create(SecurityCode code)
	{
		// TODO Auto-generated method stub
		try {
			delete(code);
			StringBuffer sql = new StringBuffer();
			sql.append("insert into t_sys_security_code(object,code,type,createTime,failTime,userId) ");
			sql.append("values('"+code.getObject()+"','"+code.getCode()+"','"+code.getType()+"','"+code.getCreateTime()+"','"+code.getFailTime()+"','"+code.getUserId()+"')");
			insert(sql.toString());
		} catch (OAException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> get(SecurityCode code)
	{
		// TODO Auto-generated method stub
		try {
			return executeQuery("select u.* FROM t_sys_security_code u where u.object = '"
					+code.getObject()+"' and u.code = '"+code.getCode()+"' order by createTime desc limit 1");
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean delete(SecurityCode code)
	{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("delete FROM t_sys_security_code where object = '"+code.getObject()+"' and userId = '"+code.getUserId()+"'");
		try {
			return executeUpdate(sql.toString()) > 0 ? true :false;
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Map<String, Object>> getPermissionOpenIds(String key,String userId) throws OAException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT u.id,u.openid,s.`name` FROM t_auth_user u,t_auth_authorization a,t_auth_security_resources s,t_auth_resource_assignments ra");
		sql.append(" WHERE u.id = a.userId AND a.roleId = ra.roleId AND ra.sourceId = s.id AND s.indentifier = '");
		sql.append(key).append("'");
		if(!Common.isNull(userId))
		sql.append(" and u.id = '").append(userId+"'");
		return executeQuery(sql.toString());
	}

	@Override
	public boolean deleteUse(SecurityCode code) {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("delete FROM t_sys_security_code where object = '"+code.getObject()+"'");
		try {
			return executeUpdate(sql.toString()) > 0 ? true :false;
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
