package com.skycloud.oa.inbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.Notification;
import com.skycloud.oa.inbound.model.Work;


public interface WorkDao{

	/**
	 * 添加到港作业表
	 * @author yanyufeng
	 * @param Work
	 * @return
	 * @throws OAException
	 * 2015-2-12 上午11:06:15
	 */
	public int addWork(Work Work) throws OAException;
	/**
	 * 添加多次接卸到港作业表
	 *@author jiahy
	 * @param work
	 * @return
	 * @throws OAException
	 */
	public int addUnloadingWork(Work work) throws OAException;

	/**
	 * 删除到港作业表
	 * @author yanyufeng
	 * @param id
	 * @throws OAException
	 * 2015-2-12 上午11:06:24
	 */
	public void deleteWork(String id)  throws OAException;
	
	/**
	 * 查询到港作业表
	 * @author yanyufeng
	 * @param transportId
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * 2015-2-12 上午11:06:35
	 */
	public List<Map<String,Object>> getWorkList(Integer transportId, long startTime,long endTime)  throws OAException;

	/**
	 * 根据到港id和货品id确认唯一一个work
	 * @author yanyufeng
	 * @param ArrivalId
	 * @param productId
	 * @return
	 * @throws OAException
	 * 2015-3-19 下午2:24:44
	 */
	public Map<String,Object> getWork(int ArrivalId,int productId) throws OAException;
	
	/**
	 * 获得调度日志基本信息
	 * @author yanyufeng
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:47:17
	 */
	public List<Map<String,Object>> getLogInfo(long startTime,long endTime) throws OAException;
	
	/**
	 * 获取该时间段内是否有调度日志
	 * @author yanyufeng
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:47:31
	 */
	public List<Map<String,Object>> getLogIsHave(long startTime,long endTime) throws OAException;
	
	
	/**
	 * 更新到港表
	 * @author yanyufeng
	 * @param Work
	 */
	public void updateWork(Work Work)  throws OAException;
	/**
	 * 查询调度日志基本信息
	 * @author yanyufeng
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * 2015-2-10 上午10:28:42
	 */
	public List<Map<String,Object>> getWorkList(Integer arrivalId) throws OAException;
	
	/**
	 * 查询关联数据
	 * @author yanyufeng
	 * @param tankId
	 * @param startTime
	 * @return
	 * @throws OAException
	 * 2015-2-10 上午10:38:23
	 */
	public List<Map<String,Object>> getConnectInfo(int tankId,long startTime)throws OAException;
	
	/**
	 * 添加验证信息
	 * @author yanyufeng
	 * @param nList
	 * @throws OAException
	 * 2015-2-26 上午11:44:26
	 */
	public void addNotify(List<Notification> nList)throws OAException;
	
	/**
	 * 删除验证信息
	 * @author yanyufeng
	 * @param id
	 * @throws OAException
	 * 2015-2-26 上午11:46:39
	 */
	public void deleteNotify(String id)throws OAException;
	
	/**
	 * 根据作业id查询验证信息
	 * @author yanyufeng
	 * @param workId
	 * @return
	 * @throws OAException
	 * 2015-2-26 上午11:50:16
	 */
	public List<Map<String,Object>> getNotify(int workId)throws OAException;
	/**
	 *清空work
	 *@author jiahy
	 * @param arrivalId
	 * @param b 
	 * @throws OAException
	 */
	public void cleanWork(int arrivalId, String b)throws OAException;
	/**
	 *更新单个货品的业务流程
	 *@author jiahy
	 * @param arrivalId
	 * @param productId
	 * @param id 
	 * @throws OAException
	 */
	public void updateWorkItemStatus(Work work)throws OAException;


	/**
	 * 入库关联信息
	 * @author yanyufeng
	 * @param tankId
	 * @param mStartTime
	 * @return
	 * 2015-7-24 下午5:06:23
	 */
	public List<Map<String,Object>> getInboundConnectInfo(int tankId,
			long mStartTime) throws OAException;


	/**
	 * 入库后尺关联信息
	 * @author yanyufeng
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * 2015-7-26 下午1:17:37
	 */
	public List<Map<String,Object>> getInboundBConnectInfo(int arrivalId,int tankId)throws OAException;


	/**
	 * 获得取消关联
	 * @author yanyufeng
	 * @param logId
	 * @param type
	 * @return
	 * 2015-7-27 上午10:23:48
	 */
	public List<Map<String,Object>> getinboundDisconnect(int logId, int type)throws OAException;


	public List<Map<String, Object>> getOPLogList(int i, long startTime,
			long endTime, int start, int limit)throws OAException;

	/**
	 * 如果work已经有到港时间则更新到港时间，没有则不更新 
	 *@author jiahy
	 * @param work  arrivalId ,arrivalTime
	 * @throws OAException
	 */
	public void updateWorkArrivalTime(Work work)throws OAException;
	public int getOPLogListCount(int i, long startTime, long endTime)throws OAException;
	
	/**
	 * 入库作业数量确认更新状态和审核人信息
	 * @Title updateWorkStatus
	 * @Descrption:TODO
	 * @param:@param work
	 * @param:@throws OAException
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年6月20日下午9:39:42
	 * @throws
	 */
	public void updateWorkStatus(Work work) throws OAException;
}
