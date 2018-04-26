/**
 * 
 */
package com.skycloud.oa.planmanager.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.planmanager.dto.PlanManagerDto;
import com.skycloud.oa.utils.OaMsg;

/**
 *
 * @author jiahy
 * @version 2015年8月14日 下午3:48:05
 */
public interface PlanManagerService {

	/**
	 *@author jiahy
	 * @param planManagerDto
	 * @param pageView
	 * @return
	 */
	public OaMsg getBerthPlanList(PlanManagerDto planManagerDto, PageView pageView) throws OAException;

	/**
	 *@author jiahy
	 * @param planManagerDto
	 * @param pageView
	 * @return
	 */
	public OaMsg getUnloadingPlanList(PlanManagerDto planManagerDto, PageView pageView) throws OAException;

	/**
	 *@author jiahy
	 * @param planManagerDto
	 * @param pageView
	 * @return
	 */
	public OaMsg getBackFlowPlanList(PlanManagerDto planManagerDto, PageView pageView)  throws OAException;

	/**
	 * 添加倒灌方案
	 *@author jiahy
	 * @param transportProgram
	 * @return
	 */
	public OaMsg addBackFlowPlan(TransportProgram transportProgram) throws OAException;

	/**
	 * @param request 
	 * 导出excel
	 * @Title exportExcel
	 * @Descrption:TODO
	 * @param:@param planManagerDto
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2016年6月23日上午10:27:10
	 * @throws
	 */
	public HSSFWorkbook exportExcel(HttpServletRequest request, PlanManagerDto planManagerDto) throws OAException;

	/**
	 * @Title exportTransportProgram
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param transportId
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2017年1月2日下午3:30:25
	 * @throws
	 */
	public HSSFWorkbook exportTransportProgram(HttpServletRequest request,int transportId) throws OAException;

	/**
	 * @Title exportChangeTankProgram
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param transportId
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2017年1月3日上午10:13:16
	 * @throws
	 */
	public HSSFWorkbook exportChangeTankProgram(HttpServletRequest request,int transportId,int type) throws OAException;

	/**
	 * @Title exportBerthProgram
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:HSSFWorkbook
	 * @auhor jiahy
	 * @date 2017年1月3日上午11:28:33
	 * @throws
	 */
	public HSSFWorkbook exportBerthProgram(HttpServletRequest request,int arrivalId) throws OAException;

}
