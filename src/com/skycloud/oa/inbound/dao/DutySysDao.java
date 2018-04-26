package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.DutySys;
import com.skycloud.oa.inbound.model.DutyWeather;


public interface DutySysDao{
	/**
	 * 增加调度值班系统检查记录
	 * @author yanyufeng
	 * @param dutyRecord
	 * @return
	 * 2015-2-5 下午2:56:55
	 */
	public int addDutySys(DutySys dutySys)throws OAException;
	
	
	/**
	 * 查询调度值班系统检查记录
	 * @author yanyufeng
	 * @param disptachId
	 * @return
	 * 2015-2-5 下午2:57:12
	 */
	public List<Map<String,Object>> getDutySys(String disptachId,int type)throws OAException;

	
	
	/**
	 * 更新调度值班系统检查记录
	 * @author yanyufeng
	 * @param dutyRecord
	 * 2015-2-5 下午2:57:20
	 */
	public void updateDutySys(DutySys dutySys)throws OAException;
	
}
