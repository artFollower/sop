/**
 * 
 */
package com.skycloud.oa.feebill.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.FeeBillDto;
import com.skycloud.oa.feebill.dto.OtherFeeCargoDto;
import com.skycloud.oa.feebill.dto.OtherFeeDto;
import com.skycloud.oa.feebill.model.OtherFee;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年10月21日 下午3:45:12
 */
public interface OtherFeeService {

	/**
	 *@author jiahy
	 * @param otherFeeDto
	 * @param pageView
	 * @return
	 */
	OaMsg getOtherFeeList(OtherFeeDto otherFeeDto, PageView pageView) throws OAException;

	/**
	 *@author jiahy
	 * @param otherFee
	 * @return
	 */
	OaMsg addOtherFee(OtherFeeDto otherFeeDto)throws OAException;

	/**
	 *@author jiahy
	 * @param otherFee
	 * @return
	 */
	OaMsg updateOtherFee(OtherFeeDto otherFeeDto)throws OAException;

	/**
	 *@author jiahy
	 * @param feeBillDto
	 * @return
	 */
	OaMsg getCodeNum(OtherFeeDto otherFeeDto)throws OAException;

	/**
	 *@author jiahy
	 * @param otherFeeDto
	 * @return
	 */
	OaMsg deleteOtherFee(OtherFeeDto otherFeeDto) throws OAException;

	/**
	 *@author jiahy
	 * @param otherFeeDto
	 * @return
	 */
	OaMsg getCargoMsg(OtherFeeDto otherFeeDto) throws OAException;

	/**
	 *@author jiahy
	 * @param otherFeeDto
	 * @return
	 */
	OaMsg getOtherFeeCargo(OtherFeeDto otherFeeDto) throws OAException;

	/**
	 *@author jiahy
	 * @param otherFeeDto
	 * @return
	 */
	OaMsg deleteFeeCargo(OtherFeeCargoDto otherFeeDto) throws OAException;

	/**
	 * @Title exportExcelList
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param eFeeDto
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年9月23日下午2:11:23
	 * @throws
	 */
	HSSFWorkbook exportExcelList(HttpServletRequest request, OtherFeeDto eFeeDto);

}
