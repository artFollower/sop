package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.OaMsg;

/**
 * 到港状态表
 * 
 * @author yanyf
 *
 */
public interface ArrivalStatusService {

	/**
	 * 获取到港状态表信息
	 * 
	 * @return
	 * @throws OAException
	 */
	public OaMsg getArrivalStatusList() throws OAException;
}
