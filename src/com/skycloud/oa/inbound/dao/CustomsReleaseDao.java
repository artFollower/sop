/**
 * 
 */
package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CustomsReleaseDto;
import com.skycloud.oa.inbound.model.CustomsRelease;

/**
 *
 * @author jiahy
 * @version 2015年12月10日 下午3:16:52
 */
public interface CustomsReleaseDao {

	/**
	 *@author jiahy
	 * @param crDto
	 * @param startRecord
	 * @param maxresult
	 * @return
	 */
	List<Map<String,Object>> getCustomsReleaseList(CustomsReleaseDto crDto,
			int startRecord, int maxresult) throws OAException;

	/**
	 *@author jiahy
	 * @param crDto
	 * @return
	 */
	int getCustomsReleaseCount(CustomsReleaseDto crDto) throws OAException;

	/**
	 *@author jiahy
	 * @param customsRelease
	 */
	void updateCustomsRelease(CustomsRelease customsRelease) throws OAException;

	/**
	 *@author jiahy
	 * @param customsReleaseDto
	 * @return
	 */
	List<Map<String,Object>> getCargoMsg(CustomsReleaseDto customsReleaseDto) throws OAException;

	/**
	 * @Title getTotalNum
	 * @Descrption:TODO
	 * @param:@param crDto
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年7月11日上午8:23:09
	 * @throws
	 */
	Map<String, Object> getTotalNum(CustomsReleaseDto crDto) throws OAException;

	/**
	 * @Title getHasPass
	 * @Descrption:TODO
	 * @param:@param valueOf
	 * @param:@param i
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年12月14日上午10:37:57
	 * @throws
	 */
	Map<String, Object> getHasPass(Integer productId,Integer valueOf, int i) throws OAException;

}
