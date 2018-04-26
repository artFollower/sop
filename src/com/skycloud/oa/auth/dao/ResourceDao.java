package com.skycloud.oa.auth.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.auth.model.SecurityResources;
import com.skycloud.oa.exception.OAException;

/**
 * 资源操作持久化接口类
 * @ClassName: ResourceDao 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 下午2:04:53
 */
public interface ResourceDao  {
	
	/**
	 * 添加资源
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @return
	 * @throws OAException
	 */
	int create(SecurityResources resource) throws OAException;
	
	/**
	 * 资源
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(SecurityResources resource,long start,long limit) throws OAException;
	
	
	/**
	 * 统计资源
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @return
	 * @throws OAException
	 */
	long count(SecurityResources resource) throws OAException;
	
	/**
	 * 更新资源信息
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @return
	 * @throws OAException
	 */
	boolean modify(SecurityResources resource) throws OAException;
	
	/**
	 * 移动节点
	 * @param resource
	 * @return
	 * @throws OAException
	 */
	boolean move(SecurityResources resource) throws OAException;
	
	/**
	 * 删除资源
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids) throws OAException;
	
}
