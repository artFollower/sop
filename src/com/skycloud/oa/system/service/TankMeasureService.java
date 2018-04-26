/**
 * 
 */
package com.skycloud.oa.system.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.TankMeasureDto;
import com.skycloud.oa.system.model.TankMeasure;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年11月30日 下午3:41:47
 */
public interface TankMeasureService {

	/**
	 *@author jiahy
	 * @param tankMeasureDto
	 * @param pageView
	 * @return
	 */
	OaMsg getList(TankMeasureDto tankMeasureDto, PageView pageView) throws OAException;

	/**
	 *@author jiahy
	 * @param tankMeasure
	 * @return
	 */
	OaMsg addTankMeasure(TankMeasure tankMeasure) throws OAException;

	/**
	 *@author jiahy
	 * @param tankMeasure
	 * @return
	 */
	OaMsg updateTankMeasure(TankMeasure tankMeasure) throws OAException;

	/**
	 *@author jiahy
	 * @param tankMeasureDto
	 * @return
	 */
	OaMsg deleteTankMeasure(TankMeasureDto tankMeasureDto) throws OAException;

	/**
	 *@author jiahy
	 * @return
	 */
	OaMsg getCodeNum() throws OAException;

	/**
	 * @Title exportExcel
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年9月13日上午9:05:37
	 * @throws
	 */
	HSSFWorkbook exportExcel(HttpServletRequest request) throws OAException;

}
