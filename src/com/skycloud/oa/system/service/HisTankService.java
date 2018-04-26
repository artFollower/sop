/**
 * @Title:HisTankService.java
 * @Package com.skycloud.oa.system.service
 * @Description TODO
 * @autor jiahy
 * @date 2016年4月25日上午10:22:10
 * @version V1.0
 */
package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.HisTank;
import com.skycloud.oa.system.dto.HisTankDto;
import com.skycloud.oa.utils.OaMsg;

/**
 * @ClassName HisTankService
 * @Description TODO
 * @author jiahy
 * @date 2016年4月25日上午10:22:10
 */
public interface HisTankService {

	/**
	 * @Title getHisTankList
	 * @Descrption:TODO
	 * @param:@param tankDto
	 * @param:@param pageView
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年4月25日上午10:22:16
	 * @throws
	 */
	OaMsg getHisTankList(HisTankDto tankDto, PageView pageView) throws OAException;

	/**
	 * @Title updateHisTank
	 * @Descrption:TODO
	 * @param:@param tank
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年4月25日上午10:22:21
	 * @throws
	 */
	OaMsg updateHisTank(HisTank tank) throws OAException;

}
