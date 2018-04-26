package com.skycloud.oa.inbound.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.model.DockFeeCharge;
import com.skycloud.oa.inbound.dto.DockFeeDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *码头规费
 * @author 作者:jiahy
 * @version 时间：2015年5月6日 下午7:28:43
 */
public interface DockFeeService {

	public OaMsg getDockFeeList(DockFeeDto dFeeDto, PageView pageView) throws OAException;

	public OaMsg addOrUpdateDockFee(DockFeeDto dFeeDto) throws OAException;

	public OaMsg deleteDockFee(DockFeeDto dFeeDto) throws OAException;

	public OaMsg getCodeNum(DockFeeDto dFeeDto) throws OAException;

	public OaMsg getArrivalList(DockFeeDto dFeeDto, PageView pageView) throws OAException;

	public OaMsg getDockFeeMsg(DockFeeDto dFeeDto) throws OAException;

	/**
	 * @Title exportExcel
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param dFeeDto
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年2月19日上午9:29:32
	 * @throws
	 */
	public HSSFWorkbook exportExcel(HttpServletRequest request, DockFeeDto dFeeDto) throws OAException;

	/**
	 * @Title exportListExcel
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param dFeeDto
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年12月15日上午8:28:17
	 * @throws
	 */
	public HSSFWorkbook exportListExcel(HttpServletRequest request,DockFeeDto dFeeDto) throws OAException;

	/**
	 * 复制费用项
	 * @author yanyufeng
	 * @param dockFeeCharge
	 * @return
	 * 2017-8-22 上午10:48:22
	 */
	public OaMsg copy(DockFeeCharge dockFeeCharge) throws OAException;
	
}
