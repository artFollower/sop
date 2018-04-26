/**
 * 
 */
package com.skycloud.oa.feebill.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.DockFeeBillDto;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.model.DockFeeBill;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *账单接口
 * @author jiahy
 * @version 2015年6月14日 下午6:04:16
 */
public interface DockFeeBillService {

	OaMsg getDockFeeBillList(DockFeeBillDto dDto, PageView pageView)throws OAException;

	OaMsg addDockFeeBill(DockFeeBillDto ndfDto)throws OAException;

	OaMsg updateDockFeeBill(DockFeeBill dockFeeBill)throws OAException;

	OaMsg deleteDockFeeBill(DockFeeBillDto dockFeeBillDto)throws OAException;

	OaMsg getCodeNum(DockFeeBillDto dockFeeBillDto)throws OAException;

	OaMsg getTotalFee(DockFeeBillDto dockFeeBillDto)throws OAException;

	OaMsg getDockFeeBillMsg(DockFeeBillDto dDto)throws OAException;

	OaMsg removeFeecharge(DockFeeBillDto dockFeeBillDto) throws OAException;

	OaMsg makeFeeBill(DockFeeChargeDto  dfcDto) throws OAException;

	HSSFWorkbook exportExcel(HttpServletRequest request, DockFeeBillDto dockFeeBillDto) throws OAException;

	HSSFWorkbook exportDetailFee(HttpServletRequest request, int id) throws OAException;

	OaMsg rollBack(DockFeeBillDto dockFeeBillDto) throws OAException;

	OaMsg reviewFeeBill(DockFeeChargeDto dfcDto) throws OAException;

	HSSFWorkbook exportExcel1(HttpServletRequest request, DockFeeBillDto dockFeeBillDto) throws OAException;

}
