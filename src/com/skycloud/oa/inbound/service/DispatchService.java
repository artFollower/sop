package com.skycloud.oa.inbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.DispatchConnectDto;
import com.skycloud.oa.inbound.dto.DispatchDto;
import com.skycloud.oa.inbound.model.Dispatch;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

public interface DispatchService {
	/**
	 * 增加调度日志基本信息
	 * @author yanyufeng
	 * @param dispatch
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:04:43
	 */
	public abstract OaMsg addDispatch(DispatchConnectDto dispatchConnectDto) throws OAException;
	/**
	 * 修改调度日志基本信息
	 * @author yanyufeng
	 * @param dispatch
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午3:05:03
	 */
	public abstract OaMsg updateDispatch(DispatchConnectDto dispatchConnectDto)throws OAException;
	
	/**
	 *查询调度日志基础资料
	 *@author jiahy
	 * @param dispatchDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getDispatchList(DispatchDto dispatchDto,PageView pageView) throws OAException; 
	/**
	 *删除调度日志基础资料
	 *@author jiahy
	 * @param dispatchDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteDispatch(DispatchDto dispatchDto) throws OAException;
	
	
	/**
	 * 得到基本信息关联
	 * @author yanyufeng
	 * @param dispatchId
	 * @return
	 * @throws OAException
	 * 2015-5-16 下午11:22:38
	 */
	public abstract OaMsg getDispatchConnect(DispatchDto dispatchDto)throws OAException;
}
