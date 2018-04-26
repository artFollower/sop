package com.skycloud.oa.inbound.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.InitialFeeDto;
import com.skycloud.oa.inbound.model.InitialFee;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.utils.OaMsg;

/**
 *首期费
 * @author 作者:jiahy
 * @version 时间：2015年5月6日 下午7:28:43
 */
public interface InitialFeeService {

	/**
	 *获取首期费列表
	 *@author jiahy
	 * @param iFeeDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public OaMsg  getInitialFeeList(InitialFeeDto iFeeDto,PageView pageView) throws OAException;

	public OaMsg getInitialFeeMsg(InitialFeeDto iFeeDto)throws OAException;
	/**
	 *添加首期费
	 *@author jiahy
	 * @param initialFee
	 * @return
	 * @throws OAException
	 */
	public OaMsg addInitialFee(InitialFee initialFee)throws OAException;
	
	/**
	 *更新首期费
	 *@author jiahy
	 * @param initialFee
	 * @return
	 * @throws OAException
	 */
	public OaMsg updateInitialFee(InitialFee initialFee) throws OAException;
	
	/**
	 *删除首期费
	 *@author jiahy
	 * @param iFeeDto
	 * @return
	 * @throws OAException
	 */
	public OaMsg deleteInitialFee(InitialFeeDto iFeeDto) throws OAException;

	/**
	 *获取编号
	 *@author jiahy
	 * @param iFeeDto
	 * @return
	 */
	public OaMsg getCodeNum(InitialFeeDto iFeeDto) throws OAException;

	/**
	 *添加（包罐保量）合同
	 *@author jiahy
	 * @param initialFee
	 * @return
	 */
	public OaMsg addContractInitialFee(InitialFee initialFee) throws OAException;

	/**
	 * 根据合同号，起止时间获取时间内的已经入库的货批
	 *@author jiahy
	 * @param iFeeDto
	 * @return
	 */
	public OaMsg getContractCargoList(InitialFeeDto iFeeDto) throws OAException;

	/**
	 * 校验货批是否生成了结算单
	 *@author jiahy
	 * @param iFeeDto
	 * @return
	 */
	public OaMsg checkCargoFee(InitialFeeDto iFeeDto) throws OAException;

	/**
	 *@author jiahy
	 * @param iFeeDto
	 * @return
	 */
	public OaMsg updateCargoFee(InitialFeeDto iFeeDto) throws OAException;

	/**
	 *@author jiahy
	 * @param initialFee
	 * @return
	 */
	public OaMsg backStatus(InitialFee initialFee) throws OAException;

	public OaMsg getOutBoundTotalList(InitialFeeDto iFeeDto, PageView pageView) throws OAException;

	public OaMsg addOrUpdateInitialFee(InitialFeeDto niFeeDto) throws OAException;

	public OaMsg getOilsFeeMsg(InitialFeeDto iFeeDto) throws OAException;

	/**
	 * @Title getOutFeeMsg
	 * @Descrption:TODO
	 * @param:@param iFeeDto
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年2月23日下午2:51:05
	 * @throws
	 */
	public OaMsg getOutFeeMsg(InitialFeeDto iFeeDto)throws OAException;

	/**
	 * @Title exportExcel
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param response
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年9月21日下午2:30:28
	 * @throws
	 */
	public HSSFWorkbook exportExcel(HttpServletRequest request,InitialFeeDto iFeeDto);

	
}
