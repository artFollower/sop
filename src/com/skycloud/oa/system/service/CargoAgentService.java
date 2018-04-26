package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.model.CargoAgent;
import com.skycloud.oa.utils.OaMsg;

/**
 * 货物代理
 * 
 * @author jiahy
 *
 */
public interface CargoAgentService {

	/**
	 * 获取货物代理资料
	 * 
	 * @param caDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getCargoAgentList(CargoAgentDto caDto, PageView pageView) throws OAException;

	/**
	 * 增加货物代理资料
	 * 
	 * @param cargoAgent
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addCargoAgent(CargoAgent cargoAgent)
			throws OAException;

	/**
	 * 更新货物代理资料
	 * 
	 * @param cargoAgent
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateCargoAgent(CargoAgent cargoAgent)
			throws OAException;

	/**
	 * 删除货物代理资料
	 * 
	 * @param caDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteCargoAgent(String ids) throws OAException;
}
