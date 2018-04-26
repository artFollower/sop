/**
 * @Title:HisTankDao.java
 * @Package com.skycloud.oa.system.dao
 * @Description TODO
 * @autor jiahy
 * @date 2016年4月25日上午10:34:19
 * @version V1.0
 */
package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.HisTank;
import com.skycloud.oa.system.dto.HisTankDto;

/**
 * @ClassName HisTankDao
 * @Description TODO
 * @author jiahy
 * @date 2016年4月25日上午10:34:19
 */
public interface HisTankDao {

	/**
	 * @Title getHisTankList
	 * @Descrption:TODO
	 * @param:@param tDto
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @return:Collection<? extends Object>
	 * @auhor jiahy
	 * @date 2016年4月25日上午10:34:26
	 * @throws
	 */
	List<Map<String, Object>> getHisTankList(HisTankDto tDto, int startRecord, int maxresult) throws OAException;

	/**
	 * @Title getCount
	 * @Descrption:TODO
	 * @param:@param tDto
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2016年4月25日上午10:35:05
	 * @throws
	 */
	int getCount(HisTankDto tDto) throws OAException;

	/**
	 * @Title updateHisTank
	 * @Descrption:TODO
	 * @param:@param hisTank
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年4月25日上午10:36:06
	 * @throws
	 */
	void updateHisTank(HisTank hisTank) throws OAException;

}
