package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalForeshow;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.Cargo;


public interface ArrivalForeshowDao{
	/**
	 * 插入船期预报
	 * @author yanyufeng
	 * @param arrival
	 */
	public int addIntoArrivalForeshow(ArrivalForeshow arrivalForeshow);
	
	/**
	 * 删除船期预报
	 * @author yanyufeng
	 * @param id
	 */
	public void deleteArrivalForeshow(String id);
	


	/**
	 * 更新船期预报
	 * @author yanyufeng
	 * @param arrival
	 */
	public void updateArrivalForeshow(ArrivalForeshow arrivalForeshow);
	
	
	/**
	 * 查询的到港信息数量
	 * @author yanyufeng
	 * @param arrivalDto
	 * @return
	 */
	public int getArrivalForeshowListCount(ArrivalDto arrivalDto);


	/**
	 * 船期预告表
	 * @author yanyufeng
	 * @param arrivalDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 * 2016-3-11 下午1:24:33
	 */
	public List<Map<String, Object>> getArrivalForeshowList(
			ArrivalDto arrivalDto, int start, int limit)throws OAException ;

	/**
	 * 检查预报
	 * @author yanyufeng
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * 2016-3-16 下午1:50:26
	 */
	public int checkForeshow(int arrivalId)throws OAException ;

	/**
	 * sql更新预报
	 * @author yanyufeng
	 * @param arrivalId
	 * @param userId 
	 * @throws OAException
	 * 2016-3-16 下午1:50:28
	 */
	public void updateForeshowBySQL(int arrivalId, int userId)throws OAException ;

	/**
	 * sql添加预报
	 * @author yanyufeng
	 * @param arrivalId
	 * @param userId
	 * @throws OAException
	 * 2016-3-16 下午2:45:24
	 */
	public void addForeshowBySQL(int arrivalId, int userId)throws OAException ;

	/**
	 * sql 更新出港预报
	 * @author yanyufeng
	 * @param arrivalId
	 * @param userId
	 * @throws OAException
	 * 2016-5-6 下午2:25:31
	 */
	public void updateOutForeshowBySQL(int arrivalId, int userId)throws OAException ;

	/**
	 * 作废船期预告
	 * @author yanyufeng
	 * @param id
	 * @throws OAException
	 * 2016-5-16 下午2:21:07
	 */
	public void zfArrivalForeshow(String id)throws OAException ;
}
