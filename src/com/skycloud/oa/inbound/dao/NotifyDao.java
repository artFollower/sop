package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.NotifyDto;
import com.skycloud.oa.inbound.model.Notify;

/**
 *其他通知单
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午2:24:06
 */
public interface NotifyDao {

	/**
	 *获取通知单列表
	 *@author jiahy
	 * @param notifyDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public List<Map<String,Object>> getNotifyList(NotifyDto notifyDto,int start,int limit) throws OAException;
	
	/**
	 *添加通知单
	 *@author jiahy
	 * @param notify
	 * @throws OAException
	 */
	public List<Map<String, Object>> getNotifyShipInfoList(NotifyDto notifyDto,int start,int limit) throws OAException;
	/**
	 *添加通知单
	 *@author tsz
	 * @param notify
	 * @throws OAException
	 */
	public void addNotify(Notify notify)throws OAException;
	
	
	/**
	 *更新通知单
	 *@author jiahy
	 * @param notify
	 * @throws OAException
	 */
	public void updateNotify(Notify notify)throws OAException;
	/**
	 *删除通知单
	 *@author jiahy
	 * @param notifyDto
	 * @throws OAException
	 */
	public void deleteNotify(NotifyDto notifyDto)throws OAException;
	/**
	 *
	 *@author jiahy
	 * @param notify
	 * @return
	 * @throws OAException
	 */
	public int getNotifyCount(NotifyDto notifyDto)throws OAException;
	/**
	 *重置通知单
	 *@author jiahy
	 * @param notifyDto
	 */
	public void resetNotify(NotifyDto notifyDto) throws OAException;
	/**
	 *清除流程内的通知单
	 *@author jiahy
	 * @param id
	 * @param b
	 */
	public void cleanNotify(int id, boolean b) throws OAException;
	/**
	 *
	 *@author jiahy
	 * @param arrivalId
	 * @param productId
	 * @param orderNum 
	 * @param b
	 */
	public void cleanNotify(Integer arrivalId, Integer productId, Integer orderNum, boolean b) throws OAException;
	public Map<String,Object> getNotifyCode() throws OAException ;
	/**
	 *@author jiahy
	 * @param notifyDto
	 * @return
	 */
	public Map<String, Object> getNotifyNum(NotifyDto notifyDto) throws OAException;
	/**
	 * 更新流程状态
	 *@author jiahy
	 * @param notify1
	 */
	public void updateNotifyState(Notify notify) throws OAException;

	/**
	 * 查看通知单是否生成
	 * @Title checkNotify
	 * @Descrption:TODO
	 * @param:@param notify
	 * @param:@return
	 * @return:int
	 * @auhor jiahy
	 * @date 2018年1月9日上午11:01:56
	 * @throws
	 */
	public  Map<String, Object> checkNotify(Notify notify);
}
