package com.skycloud.oa.auth.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.skycloud.oa.auth.dao.ResourceDao;
import com.skycloud.oa.auth.model.SecurityResources;
import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.Common;

/**
 * 资源管理持久化操作实现类
 * @ClassName: ResourceDaoImpl 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 下午2:07:32
 */
@Component
public class ResourceDaoImpl extends BaseDaoImpl implements ResourceDao {
	
	private static Logger LOG = Logger.getLogger(ResourceDaoImpl.class);

	@Override
	public int create(SecurityResources resource) throws OAException {
		// TODO Auto-generated method stub
		LOG.debug("添加资源:"+new Gson().toJson(resource));
		return Integer.parseInt(save(resource).toString());
	}

	@Override
	public List<Map<String, Object>> get(SecurityResources resource, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_auth_security_resources where 1=1";
		if(!Common.isNull(resource.getId())) {
			sql += " and id = "+resource.getId()+"";
		}
		if(!Common.empty(resource.getName())) {
			sql += " and name like '%"+resource.getName()+"%'";
		}
		if(!Common.empty(resource.getCategory())) {
			sql += " and category = '"+resource.getCategory()+"'";
		}if(!Common.empty(resource.getIndentifier())) {
			sql += " and indentifier like '%"+resource.getIndentifier()+"%'";
		}
		if(!Common.empty(resource.getStatus())) {
			sql += " and status = "+resource.getStatus()+"";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(SecurityResources resource) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_auth_security_resources where 1=1";
		if(!Common.isNull(resource.getId())) {
			sql += " and id = "+resource.getId()+"";
		}
		if(!Common.empty(resource.getName())) {
			sql += " and name like '%"+resource.getName()+"%'";
		}
		if(!Common.empty(resource.getCategory())) {
			sql += " and category = '"+resource.getCategory()+"'";
		}
		if(!Common.empty(resource.getIndentifier())) {
			sql += " and indentifier like '%"+resource.getIndentifier()+"%'";
		}
		if(!Common.empty(resource.getStatus())) {
			sql += " and status = "+resource.getStatus()+"";
		}
		return getCount(sql);
	}

	@Override
	public boolean modify(SecurityResources resource) throws OAException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		if(!Common.empty(resource.getName())) {
			sql.append("name='"+resource.getName()+"',");
		}
		if(!Common.empty(resource.getDescription())) {
			sql.append("description='"+resource.getDescription()+"',");
		}
		if(!Common.empty(resource.getStatus())) {
			sql.append("status='"+resource.getStatus()+"',");
		}
		if(!Common.empty(resource.getCategory())) {
			sql.append("category='"+resource.getCategory()+"',");
		}
		if(!Common.empty(resource.getIndentifier())) {
			sql.append("indentifier='"+resource.getIndentifier()+"',");
		}
		if(!Common.empty(resource.getParent()) && !Common.isNull(resource.getParent().getId())) {
			sql.append("parentId=");
			sql.append(resource.getParent().getId());
		}else {
			sql.append("parentId=NULL");
		}
		String _sql = sql.toString();
		if(!Common.empty(_sql)) {
			_sql = "update t_auth_security_resources set "+_sql+" where id="+resource.getId();
			return executeUpdate(_sql) > 0 ? true :false;
		}else {
			throw new OAException(Constant.SYS_CODE_NULL_ERR, "要更新的参数为空");
		}
	}

	@Override
	public boolean delete(String ids) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_auth_security_resources";
		if(ids.contains(",")) {
			sql += " where id in("+ids+")";
		}else {
			sql += " where id = "+ids+"";
		}
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public boolean move(SecurityResources resource) throws OAException {
		// TODO Auto-generated method stub
		String sql = "update t_auth_security_resources set parentId="+resource.getParentId()+" where id="+resource.getId();
		return executeUpdate(sql) > 0 ? true :false;
	}
	
}
