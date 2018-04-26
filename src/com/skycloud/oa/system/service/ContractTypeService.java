package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.dto.ContractTypeDto;
import com.skycloud.oa.system.model.CargoAgent;
import com.skycloud.oa.utils.OaMsg;

/**
 * 合同类型
 * 
 * @author jiahy
 *
 */
public interface ContractTypeService {

	/**
	 * 获取合同类型
	 * 
	 * @param caDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getContractTypeList() throws OAException;

	// /**
	// * 增加合同类型
	// *
	// * @param cargoAgent
	// * @return
	// * @throws OAException
	// */
	// public abstract OaMsg addCargoAgent(CargoAgent cargoAgent)
	// throws OAException;
	//
	// /**
	// * 更新合同类型
	// *
	// * @param cargoAgent
	// * @return
	// * @throws OAException
	// */
	// public abstract OaMsg updateCargoAgent(CargoAgent cargoAgent)
	// throws OAException;
	//
	// /**
	// * 删除合同类型
	// *
	// * @param caDto
	// * @return
	// * @throws OAException
	// */
	// public abstract OaMsg deleteCargoAgent(String ids)
	// throws OAException;
}
