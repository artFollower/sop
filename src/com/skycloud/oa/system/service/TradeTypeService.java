package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.OaMsg;

/**
 * 贸易类型表
 * 
 * @author jiahy
 *
 */
public interface TradeTypeService {
	/**
	 * 获取贸易类型
	 * 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getTradeTypeList() throws OAException;
}
