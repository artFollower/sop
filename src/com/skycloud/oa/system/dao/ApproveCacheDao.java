package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.model.ApproveCache;

public interface ApproveCacheDao {

	
	/**
	 * 查询审批缓存
	 * @author yanyufeng
	 * @param approveCache
	 * @return
	 * @throws OAException
	 * 2015-4-8 上午11:06:13
	 */
	public List<Map<String, Object>> getApproveCache(ApproveCache approveCache)throws OAException;
    
	

	/**
	 * 添加审批缓存
	 * @author yanyufeng
	 * @param approveCache
	 * @throws OAException
	 * 2015-4-8 上午11:06:24
	 */
	public void addApproveCache(ApproveCache approveCache)throws OAException;


	
	/**
	 * 更新审批缓存
	 * @author yanyufeng
	 * @param approveCache
	 * @throws OAException
	 * 2015-4-8 上午11:06:34
	 */
	public void update(ApproveCache approveCache)throws OAException;


}
