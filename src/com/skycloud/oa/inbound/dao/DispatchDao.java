package com.skycloud.oa.inbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.DispatchConnectDto;
import com.skycloud.oa.inbound.dto.DispatchDto;
import com.skycloud.oa.inbound.model.Dispatch;


public interface DispatchDao{
	/**
	 * 添加调度日志基本信息
	 * @author yanyufeng
	 * @param dispatch
	 * @return
	 * 2015-2-5 下午3:06:57
	 */
	public int addIntoDispatch(Dispatch dispatch);
	
	

	/**
	 * 修改调度日志基本信息
	 * @author yanyufeng
	 * @param dispatch
	 * 2015-2-5 下午3:07:57
	 */
	public void updateDispatch(Dispatch dispatch);



	/**
	 *查询调度日志基础信息
	 *@author jiahy
	 * @param dispatchDto
	 * @param startRecord
	 * @param currentpage
	 */
	public List<Map<String, Object>> getDispatchList(DispatchDto dispatchDto, int startRecord,
			int currentpage)throws OAException;



	/**
	 *获取数量
	 *@author jiahy
	 * @param dispatchDto
	 * @return
	 */
	public int getDispatchCount(DispatchDto dispatchDto)throws OAException;



	/**
	 * 添加基本信息关联
	 * @author yanyufeng
	 * @param id
	 * @param dispatchConnectDto
	 * 2015-5-16 下午8:36:22
	 */
	public void addDispatchConnect(int id, DispatchConnectDto dispatchConnectDto)throws OAException;



	/**
	 * 更新基本信息关联
	 * @author yanyufeng
	 * @param dispatchConnectDto
	 * @throws OAException
	 * 2015-5-16 下午9:01:49
	 */
	public void deleteDispatchConnect(int dispatchId)throws OAException;



	public List<Map<String, Object>> getDispatchConnectList(DispatchDto dispatchDto)throws OAException;
	
	
	
	
}
