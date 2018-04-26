package com.skycloud.oa.auth.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.auth.model.Parties;
import com.skycloud.oa.exception.OAException;

/**
 * 组织架构持久化类
 * @ClassName: OrganizationDao 
 * @Description: TODO
 * @author xie
 * @date 2015年1月16日 上午10:20:44
 */
public interface OrganizationDao  {
	
	/**
	 * 组织架构
	 * @Description: TODO
	 * @param user
	 * @return
	 * @throws OAException
	 */
	int create(Parties parties) throws OAException;
	
	/**
	 * 保存员工信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月8日 下午8:11:08  
	 * @param parties
	 * @return
	 * @throws OAException
	 */
	int createEmployee(Parties parties) throws OAException;
	
	/**
	 * 查询组织架构
	 * @Description: TODO
	 * @param parties
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(Parties parties,long start,long limit) throws OAException;
	
	/**
	 * 统计
	 * @Description: TODO
	 * @param parties
	 * @return
	 * @throws OAException
	 */
	long count(Parties parties) throws OAException;
	
		
	/**
	 * 修改信息
	 * @Description: TODO
	 * @param parties
	 * @return
	 * @throws OAException
	 */
	boolean modify(Parties parties) throws OAException;
	
	/**
	 * 更新用户信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年4月8日 下午7:19:53  
	 * @param parties
	 * @return
	 * @throws OAException
	 */
	boolean modifyEmployee(Parties parties) throws OAException;
	
	/**
	 * 删除
	 * @Description: TODO
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids) throws OAException;
	
	/**
	 * 获取关联的用户
	 * @Description: TODO
	 * @author xie
	 * @param user
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> getRelationUser(Parties parties,long start,long limit) throws OAException;
	
	/**
	 * 
	 * @Description: TODO
	 * @param parties
	 * @return
	 * @throws OAException
	 */
	long countRelationUser(Parties parties) throws OAException;
	
	/**
	 * 获取未关联的用户
	 * @Description: TODO
	 * @param parties
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> getUnRelationUser(Parties parties,long start,long limit) throws OAException;
	
	/**
	 * 
	 * @Description: TODO
	 * @param parties
	 * @return
	 * @throws OAException
	 */
	long countUnRelationUser(Parties parties) throws OAException;
	
	/**
	 * 
	 * @Description: TODO
	 * @param userIds
	 * @param departmentId
	 * @return
	 * @throws OAException
	 */
	boolean relationUser(String userIds,int departmentId) throws OAException;
}
