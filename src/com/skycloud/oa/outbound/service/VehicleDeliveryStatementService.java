package com.skycloud.oa.outbound.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.VehicleDeliveryStatementDto;
import com.skycloud.oa.utils.OaMsg;

/**
 * 
 * <p>台账管理---流量报表</p>
 * @ClassName:VehicleDeliveryStatementService
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午10:47:53
 *
 */
public interface VehicleDeliveryStatementService 
{
	/**
	 * 查询车位发货日报表信息
	 * @Title:VehicleDeliveryStatementService
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:48:17
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryParkDailyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView);
	
	/**
	 * 查询车位流量日报表
	 * @Title:VehicleDeliveryStatementService
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:48:26
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryWeighDailyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView);
	
	/**
	 * 查询车位发货月报表
	 * @Title:VehicleDeliveryStatementService
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:49:09
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryVehicleMonthlyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView);
	
	/**
	 * 查询车位发货历史累计量报表
	 * @Title:VehicleDeliveryStatementService
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:50:16
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryVehicleHistoryCumulantStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView);
	
	/**
	 * 查询车位流量计月报总表
	 * @Title:VehicleDeliveryStatementService
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:50:46
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryVehicleMonthlyTotalStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView);
	
	/**
	 * 查询货品月发货量报表
	 * @Title:VehicleDeliveryStatementService
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月27日 下午10:51:12
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryProductMonthlyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto,PageView pageView) ;

	/**
	 *@author jiahy
	 * @param vsDto
	 * @return
	 */
	public OaMsg getTotalNum(VehicleDeliveryStatementDto vsDto) throws OAException;

	/**
	 *@author jiahy
	 * @param request
	 * @param type
	 * @param vsDto
	 * @return
	 */
	public HSSFWorkbook exportExcel(HttpServletRequest request, String type,
			VehicleDeliveryStatementDto vsDto);
}
