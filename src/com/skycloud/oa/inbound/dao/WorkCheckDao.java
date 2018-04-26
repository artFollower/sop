package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.WorkCheck;


public interface WorkCheckDao{
	/**
	 * 增加作业检查
	 * @author yanyufeng
	 * @param workCheck
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:54:37
	 */
	public int addWorkCheck(WorkCheck workCheck) throws OAException;
	
	/**
	 * 添加作业检查如果存在不添加
	 * @param workCheck
	 * @return
	 * @throws OAException
	 */
	public int addOnlyWorkCheck(WorkCheck workCheck)throws OAException;
	
	/**
	 * 删除作业检查
	 * @author yanyufeng
	 * @param id
	 * @throws OAException
	 * 2015-2-5 下午2:54:46
	 */
	public void deleteWorkCheck(String id) throws OAException;
	
	/**
	 * 查询作业检查
	 * @author yanyufeng
	 * @param transportId
	 * @param types
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:54:58
	 */
	public List<Map<String,Object>> getWorkCheckList(String transportId,String types) throws OAException ;
	/**
	 * 更新作业检查
	 * @author yanyufeng
	 * @param workCheck
	 * @throws OAException
	 * 2015-2-5 下午2:55:10
	 */
	public void updateWorkCheck(WorkCheck workCheck) throws OAException;

	/**
	 *根据arrivalId清空
	 *@author jiahy
	 * @param id
	 * @param b 
	 */
	public void cleanWorkCheck(int id, boolean b)throws OAException;
	
}
