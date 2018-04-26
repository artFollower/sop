package com.skycloud.oa.auth.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.skycloud.oa.auth.dao.UserDao;
import com.skycloud.oa.auth.dto.UserDto;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.Common;

/**
 * 用户持久化操作实现类
 * @ClassName: UserDaoImpl 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 上午11:09:55
 */
@Component
public class UserDaoImpl extends BaseDaoImpl implements UserDao {
	
	private static Logger LOG = Logger.getLogger(UserDaoImpl.class);

	@Override
	public int create(User user) throws OAException {
		// TODO Auto-generated method stub
		
		String salt = Common.getSalt();
		user.setSalt(salt);
		user.setPassword(Common.MD5Encrypt(user.getPassword(), salt, Constant.ENCRYPTTIME));
		LOG.debug("添加用户:"+new Gson().toJson(user));
		return Integer.parseInt(save(user).toString());
	}

	@Override
	public List<Map<String, Object>> get(UserDto user, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select u.*,tr.roles,td.deps,u.jobId,p.sn,p.name as jobName from t_auth_user u";
		
		if(!Common.isNull(user.getOrganizationId())) {
			sql += " RIGHT OUTER JOIN (SELECT * FROM t_auth_parties WHERE parentId = "+user.getOrganizationId()+" AND category = 'EMPLOYEE') p ON p.userId = u.id";
		}
		
		if(!Common.isNull(user.getRoleId())) {
			sql += " RIGHT OUTER JOIN (SELECT r.*, a.userId FROM	t_auth_role r,t_auth_authorization a WHERE	r.id = a.roleId	AND r.id = "+user.getRoleId()+") tra ON tra.userId = u.id";
		}
		
		sql += " LEFT OUTER JOIN (SELECT GROUP_CONCAT(CONCAT(r.id,'|#|',r.`name`)) roles,a.userId FROM t_auth_role r,t_auth_authorization a WHERE r.id = a.roleId GROUP BY a.userId) tr ON tr.userId = u.id";
		sql += " LEFT OUTER JOIN (SELECT GROUP_CONCAT(CONCAT(a.id,'|#|',a.`name`)) deps,p.userId FROM (SELECT * FROM t_auth_parties WHERE category = 'DEPARTMENT') a,t_auth_parties p WHERE a.id = p.parentId GROUP BY p.userId) td ON td.userId = u.id";
		sql += " LEFT OUTER JOIN t_auth_parties p on p.id = u.jobId";
		sql += " where 1=1";
		if(!Common.isNull(user.getId())) {
			sql += " and u.id = "+user.getId();
		}
		if(!Common.empty(user.getName())) {
			sql += " and u.name like '%"+user.getName()+"%'";
		}
		if(!Common.empty(user.getAccount())) {
			sql += " or u.account like '%"+user.getAccount()+"%'";
		}
		if(!Common.empty(user.getEmail())) {
			sql += " or u.email like '%"+user.getEmail()+"%'";
		}
		if(!Common.empty(user.getPhone())) {
			sql += " or u.phone like '%"+user.getPhone()+"%'";
		}
		if(!Common.empty(user.getCategory())) {
			sql += " and p.category = '"+user.getCategory()+"'";
		}
		if(!Common.empty(user.getIds())){
			sql+=" and u.id in("+user.getIds()+")";
		}
		sql += " order by id desc ";
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(UserDto user) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_auth_user where 1=1";
		if(!Common.isNull(user.getId())) {
			sql += " and id = "+user.getId()+"";
		}
		if(!Common.empty(user.getAccount())) {
			sql += " and account like '%"+user.getAccount()+"%'";
		}
		if(!Common.empty(user.getName())) {
			sql += " or name like '%"+user.getName()+"%'";
		}
		if(!Common.empty(user.getEmail())) {
			sql += " or email like '%"+user.getEmail()+"%'";
		}
		if(!Common.empty(user.getPhone())) {
			sql += " or phone like '%"+user.getPhone()+"%'";
		}
		LOG.debug("查询用户sql:"+sql);
		return getCount(sql);
	}

	@Override
	public boolean modify(User user) throws OAException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("update t_auth_user set ");
		if(!Common.empty(user.getAccount())) {
			sql.append("account='"+user.getAccount()+"',");
		}
		if(!Common.empty(user.getName())) {
			sql.append("name='"+user.getName()+"',");
		}
		if(!Common.empty(user.getEmail())) {
			sql.append("email='"+user.getEmail()+"',");
		}
		if(!Common.empty(user.getPhone())) {
			sql.append("phone='"+user.getPhone()+"',");
		}
		if(!Common.empty(user.getDescription())) {
			sql.append("description='"+user.getDescription()+"',");
		}
		if(!Common.empty(user.getStatus())) {
			sql.append("status='"+user.getStatus()+"',");
		}
		if(!Common.empty(user.getPhoto())) {
			sql.append("photo='"+user.getPhoto()+"',");
		}
		if(!Common.empty(user.getCategory())) {
			sql.append("category='"+user.getCategory()+"',");
		}
		sql.append("clientId='"+(Common.empty(user.getClientId())?"":user.getClientId())+"',");
		sql.append("clientGroupId='"+(Common.empty(user.getClientGroupId())?"":user.getClientGroupId())+"',");
		sql.append("jobId='"+(Common.empty(user.getJobId())?"":user.getJobId())+"'");
		sql.append("where id="+user.getId());
		
		return executeUpdate(sql.toString()) > 0 ? true :false;
	}

	@Override
	public boolean delete(String ids) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_auth_user where id in("+ids+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public boolean resetPassword(UserDto user) throws OAException {
		// TODO Auto-generated method stub
		return executeUpdate("update t_auth_user set password = '"+user.getPassword()+"' where id = "+user.getId()) > 0 ? true :false;
	}

	@Override
	public List<Map<String, Object>> login(User user) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_auth_user where 1=1";
		if(!Common.empty(user.getAccount())) {
			sql += " and account = '"+user.getAccount()+"'";
		}
		if(!Common.empty(user.getEmail())) {
			sql += " or email = '"+user.getEmail()+"'";
		}
		if(!Common.empty(user.getPhone())) {
			sql += " or phone = '"+user.getPhone()+"'";
		}
		LOG.debug("用户登录查询sql:"+sql);
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getByAccount(String account) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_auth_user where ";
			sql += " account = '"+account+"'";
			sql += " or email = '"+account+"'";
			sql += " or phone = '"+account+"'";
		LOG.debug("shiro查询sql:"+sql);
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getGrantRole(User user) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT a.id,r.name,r.description,r.status,r.type FROM t_auth_role r,t_auth_authorization a WHERE r.id = a.roleId AND r.status = 0 AND a.userId = "+user.getId();
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getUnGrantRole(User user) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM t_auth_role r WHERE r.id NOT in (SELECT a.roleId from t_auth_authorization a WHERE a.userId = "+user.getId()+") AND r.status = 0";
		return executeQuery(sql);
	}

	@Override
	public boolean grantAuthority(String userId,String roleIds) throws OAException {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO t_auth_authorization(userId,roleId) SELECT '"+userId+"',r.id FROM t_auth_role r WHERE r.id in("+roleIds+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public boolean recoverAuthority(String ids) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_auth_authorization where id in ("+ids+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public List<Map<String, Object>> getUserRole(User user) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT r.type FROM t_auth_role r,t_auth_authorization a WHERE r.id = a.roleId AND r.status = 0 AND a.userId = "+user.getId();
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getUserPermission(User user) throws OAException {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT r.indentifier FROM t_auth_authorization a,t_auth_security_resources r,t_auth_resource_assignments ra ");
		sql.append("WHERE ra.roleId = a.roleId AND ra.sourceId = r.id AND r.status = 0 AND a.userId = ");
		sql.append(user.getId());
		return executeQuery(sql.toString());
	}

	/**
	 * @Title getUserByPermission
	 * @Descrption:TODO
	 * @param:@param user
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月22日下午2:00:44
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getUserByPermission(UserDto user) throws OAException {
		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT DISTINCT a.* from t_auth_user a,t_auth_authorization b,t_auth_resource_assignments c,t_auth_security_resources d ")
		.append(" where a.id=b.userId AND b.roleId=c.roleId AND c.sourceId=d.id AND c.roleId>22 ")
		 .append(" AND d.indentifier='").append(user.getPermission()).append("' ORDER BY a.`name` ASC");
		return executeQuery(sql.toString());
	}
	
}
