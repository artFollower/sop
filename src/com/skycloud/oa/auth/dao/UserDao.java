package com.skycloud.oa.auth.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.auth.dto.UserDto;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.exception.OAException;

/**
 * 用户操作持久化类
 * @ClassName: UserDao 
 * @Description: TODO
 * @author xie
 * @date 2014年12月25日 下午5:42:39
 */
public interface UserDao  {
	
	/**
	 * 添加用户
	 * @Description: TODO
	 * @author xie
	 * @param user 用户实体
	 * @return
	 * @throws OAException
	 */
	int create(User user) throws OAException;
	
	/**
	 * 查询用户
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(UserDto user,long start,long limit) throws OAException;
	
	/**
	 * 登录查询
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> login(User user) throws OAException;
	
	/**
	 * 计数
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	long count(UserDto user) throws OAException;
	
	/**
	 * 
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	boolean modify(User user) throws OAException;
	
	/**
	 * 
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids) throws OAException;
	
	/**
	 * 重置密码
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	boolean resetPassword(UserDto user) throws OAException;
	
	/**
	 * 结合shiro验证的时候根据账号查询用户信息
	 * @Description: TODO
	 * @author xie
	 * @param account
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> getByAccount(String account) throws OAException;
	
	/**
	 * 获取用户已分配的角色
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> getGrantRole(User user) throws OAException;
	
	/**
	 * 获取未分配给用户的角色
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> getUnGrantRole(User user) throws OAException;
	
	/**
	 * 授权
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	boolean grantAuthority(String userId,String roleIds) throws OAException;
	
	/**
	 * 回收权限
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	boolean recoverAuthority(String ids) throws OAException;
	
	/**
	 * 获取用户的角色
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 */
	List<Map<String,Object>> getUserRole(User user) throws OAException;
	
	/**
	 * 获取用户的权限
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 */
	List<Map<String,Object>> getUserPermission(User user) throws OAException;

	/**
	 * @Title getUserByPermission
	 * @Descrption:TODO
	 * @param:@param user
	 * @param:@return
	 * @return:Collection<? extends Object>
	 * @auhor jiahy
	 * @date 2016年5月22日下午1:59:34
	 * @throws
	 */
	List<Map<String,Object>> getUserByPermission(UserDto user)throws OAException;
}
