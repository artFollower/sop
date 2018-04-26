package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.DutyWeather;


public interface DutyWeatherDao{
	/**
	 * 增加调度值班天气预报记录
	 * @author yanyufeng
	 * @param dutyRecord
	 * @return
	 * 2015-2-5 下午2:56:55
	 */
	public int addDutyWeather(DutyWeather dutyWeather)throws OAException;
	
	/**
	 * 删除调度值班天气预报记录
	 * @author yanyufeng
	 * @param id
	 * 2015-2-5 下午2:57:03
	 */
	public void deleteDutyWeather(String id)throws OAException;
	
	/**
	 * 查询调度值班天气预报记录
	 * @author yanyufeng
	 * @param disptachId
	 * @return
	 * 2015-2-5 下午2:57:12
	 */
	public List<Map<String,Object>> getDutyWeather(String disptachId)throws OAException;

	
	
	/**
	 * 更新调度值班天气预报记录
	 * @author yanyufeng
	 * @param dutyRecord
	 * 2015-2-5 下午2:57:20
	 */
	public void updateDutyWeather(DutyWeather dutyWeather)throws OAException;
	
}
