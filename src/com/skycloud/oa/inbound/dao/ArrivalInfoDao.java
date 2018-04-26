package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.Cargo;


/**
 * 到港附加信息（入库计划）
 * @author jiahy
 *
 */
public interface ArrivalInfoDao{
	/**
	 * 在到港附加信息插入相应数据
	 * @author yanyufeng
	 * @param arrival
	 */
	public int addArrivalInfo(ArrivalInfo arrivalInfo) throws OAException;
	
	/**
	 * 删除到港(以及到港信息下的全部货批)
	 * @author yanyufeng
	 * @param id
	 */
	public void deleteArrivalInfo(String id) throws OAException;
	
	/**
	 * 根据时间和船舶id匹配到港附加信息
	 * @author yanyufeng
	 * @param id
	 * @param startTime
	 * @param endTime
	 */
	public List<Map<String,Object>>  getArrivalInfo(int id) throws OAException;

	/**
	 * 更新到港表
	 * @author yanyufeng
	 * @param arrival
	 */
	public void updateArrival(ArrivalInfo arrivalInfo) throws OAException;

	/**
	 *退回时清除不必要数据
	 *@author jiahy
	 * @param id
	 * @throws OAException
	 */
	public void cleanArrivalInfo(int id) throws OAException;
	
}
