package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.model.ArrivalPlan;


public interface ArrivalPlanDao{

	/**
	 * 添加船舶进港计划确认单
	 * @author yanyufeng
	 * @param arrivalPlan
	 */
	public void addArrivalPlan(ArrivalPlan arrivalPlan);
	
	/**
	 * 删除船舶进港计划确认单
	 * @author yanyufeng
	 * @param ids
	 */
	public void deleteArrivalPlan(String ids);
	
	/**
	 * 修改船舶进港计划确认单
	 * @author yanyufeng
	 * @param arrivalPlan
	 */
	public void updateArrivalPlan(ArrivalPlan arrivalPlan);
	

	/**
	 * 根据到港信息id查询未确认的计划单
	 * @author yanyufeng
	 * @param i
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getArrivalPlanList1(ArrivalPlanDto arrivalPlanDto, int start,
			int limit)throws OAException;
	
	/**
	 * 通过id得到到港计划
	 * @author yanyufeng
	 * @param id
	 * @return
	 */
	public Map<String,Object> getArrivalPlanById(int id);

	/**
	 * 更新确认状态
	 * @author yanyufeng
	 * @param arrivalPlanId
	 */
	public void updateArrivalPlanStatus(int arrivalPlanId,int status);
	
	/**
	 * 多条件查询到港计划
	 * @author yanyufeng
	 * @param arrivalDto
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Map<String,Object>> getArrivalPlanList(ArrivalPlanDto arrivalPlanDto,int start,int limit);
	
	/**
	 * 得到多条件查询数量
	 * @author yanyufeng
	 * @param arrivalPlanDto
	 * @return
	 */
	public int getArrivalPlanListCount(ArrivalPlanDto arrivalPlanDto);

	/**
	 * cargo关联plan
	 * @author yanyufeng
	 * @param string
	 * @param cargoId
	 * 2015-11-19 下午2:52:36
	 */
	public void updateArrivalPlanCargoId(String planId, int cargoId);

	public void updateNo(String planId, String no);

	public void updateCargoNo(String cargoId, String no);

	public void updateCargoinboundNo(String cargoId, String no);
}
