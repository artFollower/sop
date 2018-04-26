package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.OaMsg;

/**
 * 货批状态表
 * 
 * @author jiahy
 *
 */
public interface CargoStatusService {

	/**
	 * 获取货批状态表信息
	 * 
	 * @return
	 * @throws OAException
	 */
	public OaMsg getCargoStatusList() throws OAException;
}
