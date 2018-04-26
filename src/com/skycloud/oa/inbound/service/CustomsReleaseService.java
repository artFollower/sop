/**
 * 
 */
package com.skycloud.oa.inbound.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CustomsReleaseDto;
import com.skycloud.oa.inbound.model.CustomsRelease;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年12月10日 下午2:57:16
 */
public interface CustomsReleaseService {

	/**
	 *@author jiahy
	 * @param crDto
	 * @param pageView
	 * @return
	 */
	OaMsg getCustomsReleaseList(CustomsReleaseDto crDto, PageView pageView) throws OAException;

	/**
	 *@author jiahy
	 * @param customsRelease
	 * @return
	 */
	OaMsg updateCustomsRelease(CustomsRelease customsRelease)  throws OAException;


	/**
	 * @Title getTotalNum
	 * @Descrption:TODO
	 * @param:@param crDto
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年7月11日上午8:21:39
	 * @throws
	 */
	OaMsg getTotalNum(CustomsReleaseDto crDto)throws OAException;

	HSSFWorkbook exportExcel(HttpServletRequest request, CustomsReleaseDto crDto) throws OAException;
}
