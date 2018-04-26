package com.skycloud.oa.inbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.DutyRecord;
import com.skycloud.oa.inbound.model.DutySys;
import com.skycloud.oa.inbound.model.DutyWeather;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

public interface OperationLogService {
	/**
	 * 根据时间查询调度日志
	 * @author yanyufeng
	 * @param startTime
	 * @param endTime
	 * @param pageView 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getOperationLogList(long startTime,long endTime, PageView pageView)throws OAException;
	
	/**
	 * 得到调度日志基本信息
	 * @author yanyufeng
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getLogInfo(long startTime,long endTime)throws OAException;
	
	/**
	 * 查询该时间段内是否有调度日志
	 * @author yanyufeng
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:48:32
	 */
	public abstract OaMsg getLogIsHave(long startTime,long endTime)throws OAException;
	
	/**
	 * 查询作业检查
	 * @author yanyufeng
	 * @param transportId
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:48:50
	 */
	public abstract OaMsg getLogWorkCheck(String transportId,String types)throws OAException;
	
	/**
	 * 增加调度值班记录
	 * @author yanyufeng
	 * @param dutyRecord
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:55:31
	 */
	public abstract OaMsg addDutyRecord(DutyRecord dutyRecord)throws OAException;
	
	/**
	 * 删除调度值班记录
	 * @author yanyufeng
	 * @param id
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:56:23
	 */
	public abstract OaMsg delectDutyRecord(String id)throws OAException;
	
	/**
	 * 更新调度值班记录
	 * @author yanyufeng
	 * @param dutyRecord
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:56:31
	 */
	public abstract OaMsg updateDutyRecord(DutyRecord dutyRecord)throws OAException;

	/**
	 * 查询调度值班记录
	 * @author yanyufeng
	 * @param dispatchId
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午2:56:39
	 */
	public abstract OaMsg getDutyRecord(String dispatchId)throws OAException;
	
	/**
	 * 删除验证
	 * @author yanyufeng
	 * @param id
	 * @return
	 * @throws OAException
	 * 2015-2-26 下午3:34:30
	 */
	public OaMsg deleteNotify(String id)throws OAException;

	/**
	 * 查询验证
	 * @author yanyufeng
	 * @param workId
	 * @return
	 * @throws OAException
	 * 2015-2-26 下午3:34:46
	 */
	public OaMsg getNotify(int workId)throws OAException;

	/**
	 * 添加调度日志天气预报记录
	 * @author yanyufeng
	 * @param dutyWeather
	 * @return
	 * @throws OAException
	 * 2015-3-16 下午1:30:52
	 */
	public abstract OaMsg addDutyWeather(DutyWeather dutyWeather)throws OAException;

	/**
	 * 删除调度日志天气预报记录
	 * @author yanyufeng
	 * @param id
	 * @return
	 * @throws OAException
	 * 2015-3-16 下午1:46:53
	 */
	public abstract Object delectDutyWeather(String id)throws OAException;

	/**
	 * 查询调度日志天气预报记录
	 * @author yanyufeng
	 * @param dispatchId
	 * @return
	 * 2015-3-16 下午1:51:40
	 */
	public abstract Object getDutyWeather(String dispatchId)throws OAException;

	/**
	 * 查询系统检查
	 * @author yanyufeng
	 * @param dispatchId
	 * @param type 
	 * @return
	 * @throws OAException
	 * 2016-7-12 上午10:51:00
	 */
	public abstract Object getdutySys(String dispatchId, int type)throws OAException;

	public abstract Object updatedutySys(DutySys DutySys)throws OAException;
}
