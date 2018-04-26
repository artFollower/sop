/**
 * 
 */
package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.TankMeasureDto;
import com.skycloud.oa.system.model.TankMeasure;

/**
 *
 * @author jiahy
 * @version 2015年11月30日 下午3:56:11
 */
public interface TankMeasureDao {

	/**
	 *@author jiahy
	 * @param tankMeasureDto
	 * @param firstResult
	 * @param maxresult
	 * @return
	 */
	List<Map<String,Object>> getList(TankMeasureDto tankMeasureDto,
			int firstResult, int maxresult) throws OAException;

	/**
	 *@author jiahy
	 * @param tankMeasureDto
	 * @return
	 */
	int getListCount(TankMeasureDto tankMeasureDto) throws OAException;

	/**
	 *@author jiahy
	 * @param tankMeasure
	 */
	int addTankMeasure(TankMeasure tankMeasure) throws OAException;

	/**
	 *@author jiahy
	 * @param tankMeasure
	 */
	void updateTankMeasure(TankMeasure tankMeasure) throws OAException;

	/**
	 *@author jiahy
	 * @param tankMeasureDto
	 */
	void deleteTankMeasure(TankMeasureDto tankMeasureDto) throws OAException;

	/**
	 *@author jiahy
	 * @param format
	 * @return
	 */
	Map<String,Object> getCodeNum(String format) throws OAException;

	/**
	 *@author jiahy
	 * @param tankMeasureDto
	 * @return
	 */
	Map<String, Object> getTankMeasureMsg(TankMeasureDto tankMeasureDto) throws OAException;
    
	
	public int checkInvoiceTankName(String tankName) throws OAException;
}
