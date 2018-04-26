package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.Cargo;


public interface ArrivalDao{
	/**
	 * 在到港信息表插入相应数据
	 * @author yanyufeng
	 * @param arrival
	 */
	public int addIntoArrival(Arrival arrival);
	
	/**
	 * 删除到港(以及到港信息下的全部货批)
	 * @author yanyufeng
	 * @param id
	 */
	public void deleteArrival(String id);
	
	/**
	 * 根据时间和船舶id匹配到港信息表
	 * @author yanyufeng
	 * @param id
	 * @param startTime
	 * @param endTime
	 */
	public Map<String,Object> getArrivalById(int arrivalid);

	/**
	 * 更新到港表
	 * @author yanyufeng
	 * @param arrival
	 */
	public void updateArrival(Arrival arrival);
	
	/**
	 * 多条件查询到港信息
	 * @author yanyufeng
	 * @param arrivalDto
	 * @return
	 */
	public List<Map<String,Object>> getArrivalList(ArrivalDto arrivalDto,int start,int limit);
	
	/**
	 * 查询的到港信息数量
	 * @author yanyufeng
	 * @param arrivalDto
	 * @return
	 */
	public int getArrivalListCount(ArrivalDto arrivalDto);

	/**
	 * 根据货体id获得车船号
	 * @author yanyufeng
	 * @param id
	 * 2015-3-3 下午1:32:51
	 */
	public List<Map<String,Object>> getshipId(Integer goodsId)throws OAException ;

	/**
	 * 查询到港信息
	 * @author yanyufeng
	 * @param arrivalDto
	 * @return
	 * 2016-1-20 下午2:33:53
	 */
	public List<Map<String, Object>> getArrivalInfo(ArrivalDto arrivalDto)throws OAException ;
}
