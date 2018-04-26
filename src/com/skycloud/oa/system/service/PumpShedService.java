/**
 * @Title:PumpShedService.java
 * @Package com.skycloud.oa.system.service
 * @Description TODO
 * @autor jiahy
 * @date 2016年9月2日下午1:27:19
 * @version V1.0
 */
package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.PumpShedDto;
import com.skycloud.oa.system.model.PumpShed;
import com.skycloud.oa.utils.OaMsg;

/**
 * @ClassName PumpShedService
 * @Description TODO
 * @author jiahy
 * @date 2016年9月2日下午1:27:19
 */
public interface PumpShedService {

	/**
	 * @Title getPumpShedList
	 * @Descrption:TODO
	 * @param:@param psDto
	 * @param:@param pageView
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:28:57
	 * @throws
	 */
	OaMsg getPumpShedList(PumpShedDto psDto, PageView pageView) throws OAException;

	/**
	 * @Title updatePumpShed
	 * @Descrption:TODO
	 * @param:@param pumpShed
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:30:18
	 * @throws
	 */
	OaMsg updatePumpShed(PumpShed pumpShed) throws OAException;

	/**
	 * @Title addPumpShed
	 * @Descrption:TODO
	 * @param:@param pumpShed
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:31:17
	 * @throws
	 */
	OaMsg addPumpShed(PumpShed pumpShed) throws OAException;

	/**
	 * @Title deletePumpShed
	 * @Descrption:TODO
	 * @param:@param psDto
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:32:07
	 * @throws
	 */
	OaMsg deletePumpShed(PumpShedDto psDto) throws OAException;

}
