package com.skycloud.oa.inbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.TankLogDto;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TankLog;
import com.skycloud.oa.inbound.model.TankLogStore;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

public interface TankLogService {
	/**
	 * 获得储罐台账Store
	 * @author yanyufeng
	 * @param tankLogDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:17:18
	 */
	public abstract OaMsg getStoreList(TankLogDto tankLogDto,PageView pageView) throws OAException;
	
	/**
	 * 获得储罐台账
	 * @author yanyufeng
	 * @param tankLogDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 * 2015-2-9 下午1:10:17
	 */
	public abstract OaMsg getTankLogList(TankLogDto tankLogDto,PageView pageView)throws OAException;
	/**
	 * 更新Store
	 * @author yanyufeng
	 * @param store
	 * @param type 
	 * @param logId 
	 * @param connectType 
	 * @param logType 
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:17:58
	 */
	public abstract OaMsg updateStore(Store store, int type, int logId, int connectType, int logType)throws OAException;
	
	
	/**
	 * 更新储罐台账
	 * @author yanyufeng
	 * @param store
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:17:58
	 */
	public abstract OaMsg updateTankLog(TankLog tankLog)throws OAException;
	
	
	
	/**
	 * 增加
	 * @author yanyufeng
	 * @param store
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:18:06
	 */
	public abstract OaMsg addTankLog(TankLog tankLog)throws OAException;
	
	/**
	 * 查询需选择的关联信息
	 * @author yanyufeng
	 * @param tankId
	 * @param startTime
	 * @return
	 * @throws OAException
	 * 2015-2-10 上午11:52:01
	 */
	public abstract OaMsg getConnectInfo(int tankId,long startTime)throws OAException;
	
	/**
	 * 删除储罐台账
	 * @author yanyufeng
	 * @param ids
	 * @return
	 * @throws OAException
	 * 2015-2-11 下午6:07:48
	 */
	public abstract OaMsg deleteTankLog(String ids)throws OAException;

	/**取消关联
	 * @author yanyufeng
	 * @param tankLogId
	 * @param type
	 * @return
	 * @throws OAException
	 * 2015-3-20 下午6:40:07
	 */
	public abstract Object disconnect(int tankLogId, int type)throws OAException;

	/**
	 * 查询储罐台账的储罐
	 * @author yanyufeng
	 * @param tankLogDto
	 * @param pageView
	 * @return
	 * 2015-4-13 下午5:18:44
	 */
	public abstract Object getTankList(TankLogDto tankLogDto, PageView pageView)throws OAException;

	/**
	 * 查询入库关联
	 * @author yanyufeng
	 * @param tankId
	 * @param mStartTime
	 * @return
	 * 2015-7-24 下午4:49:15
	 */
	public abstract Object getInboundConnectInfo(int tankId, long mStartTime)throws OAException;

	/**
	 * 查询入库后尺关联
	 * @author yanyufeng
	 * @param arrivalId
	 * @param tankId
	 * @return
	 * @throws OAException
	 * 2015-7-26 下午2:13:47
	 */
	public abstract Object getInboundBConnectInfo(int arrivalId,int tankId)throws OAException;

	/**
	 * 添加台账关联表
	 * @author yanyufeng
	 * @param tanklogStore
	 * @return
	 * @throws OAException
	 * 2015-7-26 下午2:13:56
	 */
	public abstract Object addTankLogStore(TankLogStore tanklogStore)throws OAException;

	/**
	 * 更新台账关联表
	 * @author yanyufeng
	 * @param tanklogStore
	 * @return
	 * @throws OAException
	 * 2015-7-26 下午3:26:04
	 */
	public abstract Object updateTankLogStore(TankLogStore tanklogStore)throws OAException;

	/**
	 * 入库取消关联信息
	 * @author yanyufeng
	 * @param logId
	 * @param type 
	 * @return
	 * @throws OAException
	 * 2015-7-27 上午9:57:16
	 */
	public abstract Object getinboundDisconnect(int logId, int type)throws OAException;

	/**
	 * 取消入库关联
	 * @author yanyufeng
	 * @param tanklogStoreId
	 * @param type
	 * @param logId
	 * @return
	 * @throws OAException
	 * 2015-7-27 上午11:14:24
	 */
	public abstract Object disconnectInbound(int tanklogStoreId, int type,
			int logId)throws OAException;

	/**
	 * 查询台账关联
	 * @author yanyufeng
	 * @param arrivalId
	 * @param productId
	 * @return
	 * @throws OAException
	 * 2015-7-27 下午3:04:03
	 */
	public abstract Object getTankLogStore(int arrivalId, int productId)throws OAException;

	public abstract Object getTankLogStoreSum(int arrivalId, int productId)throws OAException;
}
