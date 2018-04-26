/**
 * 
 */
package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.OaMsg;

/**
 * @author yanyufeng
 *
 */
public interface CacheService {

	/**
	 * 添加合同
	 * 
	 * @author yanyufeng
	 * @param contract
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateCache(String key, String userId)
			throws OAException;

	/**
	 * 得到关键字list
	 * 
	 * @author yanyufeng
	 * @param text
	 * @param userId
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getCacheResult(String text, String userId)
			throws OAException;

}
