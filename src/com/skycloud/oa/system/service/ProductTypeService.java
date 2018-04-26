package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.OaMsg;

/**
 * 货品类型表
 * 
 * @author jiahy
 *
 */
public interface ProductTypeService {
	/**
	 * 获取货品类型
	 * 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getProductTypeList() throws OAException;
}
