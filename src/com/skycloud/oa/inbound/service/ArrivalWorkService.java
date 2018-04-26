package com.skycloud.oa.inbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.ArrivalWork;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.utils.OaMsg;

public interface ArrivalWorkService {
	/**
	 * 添加船舶进港工作联系单
	 * @author yanyufeng
	 * @param arrivalWork
	 * @param cargoAgentId 
	 * @param code 
	 * @param passShipInspect 
	 * @param inspectAgentId 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addArrivalWork(String description,ArrivalWork arrivalWork, String cargoAgentId, String code, String inspectAgentIds,String inspectAgentNames, String passShipInspect) throws OAException;
	
	/**
	 * 计算合同批次号（货批号）
	 * @author yanyufeng
	 * @param contractDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getTheCargoCode(ContractDto contractDto)throws OAException;
	
	/**
	 * 检查代码是否重复
	 * @author yanyufeng
	 * @param code
	 * @return
	 * @throws OAException
	 * 2015-2-5 下午1:56:02
	 */
	public abstract OaMsg checkCargoCode(String code)throws OAException;
	/**
	 * 删除船舶进港工作联系单
	 * @author yanyufeng
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteArrivalWork(String ids)throws OAException;
	/**
	 * 修改船舶进港工作联系单
	 * @author yanyufeng
	 * @param arrivalWork
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateArrivalWork(ArrivalWork arrivalWork)throws OAException;

	/**
	 * 删除货批
	 * @author yanyufeng
	 * @param cargoId
	 * @return
	 * @throws OAException
	 * 2015-8-13 下午4:54:11
	 */
	public abstract OaMsg deleteCargo(Integer cargoId)throws OAException;
}
