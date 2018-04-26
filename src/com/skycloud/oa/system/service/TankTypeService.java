package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.OaMsg;

/**
 * 税务类型表
 * 
 * @author jiahy
 *
 */
public interface TankTypeService {
	/**
	 * 获取税务类型
	 * 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getTankTypeList() throws OAException;
}
