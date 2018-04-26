/**
 * @Title:PumpShedDao.java
 * @Package com.skycloud.oa.system.dao
 * @Description TODO
 * @autor jiahy
 * @date 2016年9月2日下午1:39:15
 * @version V1.0
 */
package com.skycloud.oa.system.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.PumpShedDto;
import com.skycloud.oa.system.model.PumpShed;

/**
 * @ClassName PumpShedDao
 * @Description TODO
 * @author jiahy
 * @date 2016年9月2日下午1:39:15
 */
public interface PumpShedDao {

	/**
	 * @Title getPumpShedList
	 * @Descrption:TODO
	 * @param:@param psDto
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @return:Collection<? extends Object>
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:43:08
	 * @throws
	 */
	List<Map<String, Object>> getPumpShedList(PumpShedDto psDto, int startRecord, int maxresult) throws OAException;

	/**
	 * @Title getPumpShedListCount
	 * @Descrption:TODO
	 * @param:@param psDto
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:43:28
	 * @throws
	 */
	Integer getPumpShedListCount(PumpShedDto psDto) throws OAException;

	/**
	 * @Title updatePumpShed
	 * @Descrption:TODO
	 * @param:@param pumpShed
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:44:53
	 * @throws
	 */
	void updatePumpShed(PumpShed pumpShed) throws OAException;

	/**
	 * @Title addPumpShed
	 * @Descrption:TODO
	 * @param:@param pumpShed
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:46:19
	 * @throws
	 */
	Integer addPumpShed(PumpShed pumpShed) throws OAException;

	/**
	 * @Title deletePumpShed
	 * @Descrption:TODO
	 * @param:@param psDto
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:47:09
	 * @throws
	 */
	void deletePumpShed(PumpShedDto psDto) throws OAException;

}
