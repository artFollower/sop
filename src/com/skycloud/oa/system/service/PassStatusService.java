package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.OaMsg;

/**
 * 放行状态表
 * 
 * @author jiahy
 *
 */
public interface PassStatusService {

	/**
	 * 获取放行状态表信息
	 * 
	 * @return
	 * @throws OAException
	 */
	public OaMsg getPassStatusList() throws OAException;
}
