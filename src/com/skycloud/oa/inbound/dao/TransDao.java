package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.Trans;


public interface TransDao{
	/**
	 *添加管线关系
	 *@author jiahy
	 * @param Trans
	 * @return
	 * @throws OAException
	 */
	public int addTrans(Trans Trans) throws OAException;
	
	/**
	 *删除管线关系
	 *@author jiahy
	 * @param id
	 * @throws OAException
	 */
	public void deleteTrans(String id)throws OAException;
	
	/**
	 *获取管线关系信息
	 *@author jiahy
	 * @param trasnportprogramId
	 * @return
	 * @throws OAException
	 */
	public List<Map<String,Object>> getTransList(String transportIds) throws OAException;

	/**
	 *更新管线关系
	 *@author jiahy
	 * @param Trans
	 * @throws OAException
	 */
	public void updateTrans(Trans Trans)throws OAException;

	/**根据transportId删除管线
	 * @param id
	 */
	public void deleteTransByTransPortId(Integer id) throws OAException;

	/**
	 *根据arrivalId清空
	 *@author jiahy
	 * @param id
	 * @param b 
	 */
	public void cleanTrans(int id, boolean b)throws OAException;

	public List<Map<String, Object>> getTransListByArrivalId(int arrivalId,
			int productId)throws OAException;
	
}
