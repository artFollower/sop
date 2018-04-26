package com.skycloud.oa.auth.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.auth.model.Role;
import com.skycloud.oa.exception.OAException;

/**
 * 角色操作持久化类
 * @ClassName: RoleDao 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 上午11:06:01
 */
public interface RoleDao  {
	
	/**
	 * 添加角色
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @return
	 * @throws OAException
	 */
	int create(Role role) throws OAException;
	
	/**
	 * 查询角色
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(Role role,long start,long limit) throws OAException;
	
	
	/**
	 * 计数
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @return
	 * @throws OAException
	 */
	long count(Role role) throws OAException;
	
	/**
	 * 
	 * 查看角色下的用户
	 * @param role
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> getUser(Role role,long start,long limit) throws OAException;
	
	/**
	 * 统计角色下的用户
	 * @param role
	 * @return
	 * @throws OAException
	 */
	long countUser(Role role) throws OAException;
	
	/**
	 * 更新角色信息
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @return
	 * @throws OAException
	 */
	boolean modify(Role role) throws OAException;
	
	/**
	 * 修改角色权限
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月26日 下午10:06:49  
	 * @param role
	 * @return
	 * @throws OAException
	 */
	boolean modifyPermission(Role role) throws OAException;
	
	/**
	 * 删除角色
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids) throws OAException;
	
	/**
	 * 获取角色已分配的权限
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> getGrantPermission(Role role) throws OAException;
	
	/**
	 * 获取未分配给角色的权限
	 * @Description: TODO
	 * @author xie
	 * @param role
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> getUnGrantPermission(Role role) throws OAException;
	
	/**
	 * 授权
	 * @Description: TODO
	 * @author xie
	 * @param roleId
	 * @param permissionIds
	 * @return
	 * @throws OAException
	 */
	boolean grantPermission(String roleId,String permissionIds) throws OAException;
	
	/**
	 * 回收权限
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean recoverPermission(String ids) throws OAException;
	
}
