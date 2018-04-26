package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.TankLogDto;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TankLog;


public interface StoreDao{
	
	/**
	 * 增加储罐台账Store
	 * @author yanyufeng
	 * @param Store
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:19:40
	 */
	public int addStore(Store Store)throws OAException;
	
	/**
	 * 查询储罐台账
	 * @author yanyufeng
	 * @param transportprogramId
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:20:05
	 */
	public List<Map<String,Object>> getStoreList(String transportIds)throws OAException;

	/**
	 * 更新储罐台账
	 * @author yanyufeng
	 * @param Store
	 */
	public void updateStore(Store Store)throws OAException;
	
	public void updateStore(Store Store,int type, int logId, int connectType, int logType)throws OAException;
	
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
	public List<Map<String,Object>> getStoreListByTank(TankLogDto tankLogDto,int start,int limit)throws OAException;
	/**
	 * 统计
	 * @author yanyufeng
	 * @param tankLogDto
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:24:52
	 */
	public int getStoreListCount(TankLogDto tankLogDto)throws OAException;

	/**根据传输id删除
	 * @param id
	 */
	public void deleteStoreByTransPortId(Integer id) throws OAException;

	/**
	 *根据arrivalId清空
	 *@author jiahy
	 * @param id
	 * @param b 
	 */
	public void cleanStore(int id, boolean b)throws OAException;
}
