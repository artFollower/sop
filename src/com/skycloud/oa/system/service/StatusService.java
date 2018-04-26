package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.OaMsg;

/**
 * 状态定义表
 * 
 * @author jiahy
 *
 */
public interface StatusService {

	/**
	 * 获取状态定义列表
	 * 
	 * @return
	 */
	public abstract OaMsg getStatusList() throws OAException;
}
