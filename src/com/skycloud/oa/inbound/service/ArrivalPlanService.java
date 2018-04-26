package com.skycloud.oa.inbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.dto.InsertArrivalPlanDto;
import com.skycloud.oa.inbound.dto.InsertSingleArrivalPlanDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

public interface ArrivalPlanService {
	/**
	 * 添加船舶进港计划确认单
	 * @author yanyufeng
	 * @param arrivalPlan
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addArrivalPlan(InsertArrivalPlanDto arrivalPlanDto) throws OAException;
	
	/**
	 * 添加单个进港计划确认单
	 * @author yanyufeng
	 * @param singleArrivalPlanDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addSingleArrivalPlan(InsertSingleArrivalPlanDto singleArrivalPlanDto)throws OAException;
	/**
	 * 删除船舶进港计划确认单
	 * @author yanyufeng
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteArrivalPlan(String ids)throws OAException;
	
	/**
	 * 删除到港信息（以及该到港下所有计划）
	 * @author yanyufeng
	 * @param id
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteArrival(String id)throws OAException;
	/**
	 * 修改船舶进港计划确认单
	 * @author yanyufeng
	 * @param arrivalPlan
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateArrivalPlan(ArrivalPlan arrivalPlan)throws OAException;
	
	/**
	 * 确认后加到到港信息和货批表内
	 * @author yanyufeng
	 * @param arrivalPlan
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addCargo(String ids)throws OAException;
	
	/**
	 * 多条件查询到港信息列表
	 * @author yanyufeng
	 * @param arrivalDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getArrivalList(ArrivalDto arrivalDto,PageView pageView)throws OAException; 
	
	
	/**
	 * 根据到港id查询未确认的到港计划单
	 * @param aDto
	 * @param oaMsg
	 * @return
	 */
	public abstract OaMsg getArrivalPlanList(ArrivalPlanDto aDto,OaMsg oaMsg)throws OAException;
	
	/**
	 * 根据到港id查询已确认的到港计划单（货批）
	 * @param arrivalId
	 * @param oaMsg
	 * @return
	 */
	public abstract OaMsg getArrivalSureList(int arrivalId,OaMsg oaMsg);
	/**
	 * 更新到港表
	 * @author yanyufeng
	 * @param arrival
	 * @param shipArrivalDraught 
	 * @param isCreateInfo 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateArrival(Arrival arrival, String shipArrivalDraught, int isCreateInfo)throws OAException;
	
	/**
	 * 多条件查询到港计划
	 * @author yanyufeng
	 * @param arrivalPlanDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getArrivalPlanList(ArrivalPlanDto arrivalPlanDto,PageView pageView)throws OAException;

	/**
	 * 添加货批（虚拟库）
	 * @author yanyufeng
	 * @param cargo
	 * @param tankId 
	 * @return
	 * @throws OAException
	 * 2015-9-24 下午5:45:35
	 */
	public abstract OaMsg addCargo(Cargo cargo, Integer tankId)throws OAException;

	/**
	 * 修改货批(虚拟库)
	 * @author yanyufeng
	 * @param cargo
	 * @return
	 * 2015-10-8 上午11:15:48
	 */
	public abstract OaMsg updateCargo(Cargo cargo)throws OAException;

	/**
	 * 更新打印单号
	 * @author yanyufeng
	 * @param planId
	 * @param no
	 * @return
	 * @throws OAException
	 * 2015-11-19 下午5:42:13
	 */
	public abstract OaMsg updateNo(String planId, String no)throws OAException;

	/**
	 * 更新进港联系单单号
	 * @author yanyufeng
	 * @param cargoId
	 * @param no
	 * @return
	 * 2015-11-19 下午9:15:46
	 */
	public abstract OaMsg updateCargoNo(String cargoId, String no);

	/**
	 * 更新入库损耗单单号
	 * @author yanyufeng
	 * @param cargoId
	 * @param no
	 * @return
	 * 2015-11-23 下午4:49:12
	 */
	public abstract OaMsg updateCargoInboundNo(String cargoId, String no);

	/**
	 * 查询到港信息
	 * @author yanyufeng
	 * @param arrivalDto
	 * @return
	 * 2016-1-20 下午2:31:45
	 */
	public abstract OaMsg getArrivalInfo(ArrivalDto arrivalDto)throws OAException;

	/**
	 * 更新到港信息
	 * @author yanyufeng
	 * @param arrivalInfo
	 * @return
	 * @throws OAException
	 * 2016-1-20 下午5:07:30
	 */
	public abstract OaMsg updateArrivalInfo(ArrivalInfo arrivalInfo)throws OAException;

	
}
