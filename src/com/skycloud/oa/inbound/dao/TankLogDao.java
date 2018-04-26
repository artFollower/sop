package com.skycloud.oa.inbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.TankLogDto;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TankLog;
import com.skycloud.oa.inbound.model.TankLogStore;


public interface TankLogDao{
	

	 
	/**
	 * 添加储罐台账
	 * @author yanyufeng
	 * @param tankLog
	 * @return
	 * @throws OAException
	 * 2015-2-9 上午11:49:49
	 */
	public int addTankLog(TankLog tankLog) throws OAException;

	/**
	 * 更新储罐台账
	 * @author yanyufeng
	 * @param Store
	 */
	public void updateTankLog(TankLog tankLog)throws OAException;
	
	/**
	 * 根据罐查询台账
	 * @author yanyufeng
	 * @param tankLogDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:20:30
	 */
	public List<Map<String,Object>> getTankLogListByTank(TankLogDto tankLogDto,int start,int limit)throws OAException;
	/**
	 * 统计
	 * @author yanyufeng
	 * @param tankLogDto
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:24:52
	 */
	public int getTankLogListCount(TankLogDto tankLogDto)throws OAException;

	/**根据传输id删除
	 * @param id
	 */
	public void deleteTankLogByTransPortId(Integer id) throws OAException;
	
	/**
	 * 删除储罐台账
	 * @author yanyufeng
	 * @param ids
	 * @throws OAException
	 * 2015-2-11 下午6:07:07
	 */
	public void deleteTankLog(String ids)throws OAException;

	/**
	 * 取消关联
	 * @author yanyufeng
	 * @param tankLogId
	 * @param type
	 * @throws OAException
	 * 2015-3-20 下午6:39:59
	 */
	public void disconnect(int tankLogId, int type)throws OAException;

	/**
	 * 查询储罐台账的储罐
	 * @author yanyufeng
	 * @param tankLogDto
	 * @param startRecord
	 * @param maxresult
	 * @return
	 * @throws OAException
	 * 2015-4-13 下午5:23:36
	 */
	public List<Map<String,Object>> getTankListByTank(
			TankLogDto tankLogDto, int startRecord, int maxresult)throws OAException;

	/**
	 * 查询储罐台账的储罐数量
	 * @author yanyufeng
	 * @param tankLogDto
	 * @return
	 * 2015-4-13 下午5:28:39
	 */
	public int getTankListCount(TankLogDto tankLogDto)throws OAException;

	/**
	 * 添加台账关联
	 * @author yanyufeng
	 * @param tanklogStore
	 * @return
	 * @throws OAException
	 * 2015-7-26 下午2:16:59
	 */
	public int addTankLogStore(TankLogStore tanklogStore)throws OAException;

	/**
	 * 更新台账关联
	 * @author yanyufeng
	 * @param tanklogStore
	 * 2015-7-26 下午3:29:29
	 */
	public void updateTankLogStore(TankLogStore tanklogStore)throws OAException;
	
	
	/**
	 * 查询tanklogstore表
	 * @author yanyufeng
	 * @param tankLogStoreId
	 * @return
	 * @throws OAException
	 * 2015-7-27 上午11:22:11
	 */
	public List<Map<String,Object>> getTankLogStore(int tankLogStoreId)throws OAException;

	/**
	 * 删除台账关联
	 * @author yanyufeng
	 * @param tanklogStoreId
	 * @throws OAException
	 * 2015-7-27 上午11:28:31
	 */
	public void deleteTankLogStore(int tanklogStoreId)throws OAException;

	public List<Map<String,Object>> getTankLogStore(int arrivalId,
			int productId)throws OAException;

	public List<Map<String,Object>> getTankLogStoreSum(int arrivalId,
			int productId)throws OAException;

	/**
	 * 删除该罐的所有台账
	 * @author yanyufeng
	 * @param tank
	 * 2015-7-29 上午11:43:28
	 */
	public void deleteTankLogByTankId(String tank)throws OAException;
	
}
