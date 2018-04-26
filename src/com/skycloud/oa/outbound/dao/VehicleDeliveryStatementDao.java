package com.skycloud.oa.outbound.dao;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.VehicleDeliveryStatementDto;

/**
 * 
 * <p>台账管理---流量报表</p>
 * @ClassName:VehicleDeliveryStatementDao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午10:59:33
 *
 */
public interface VehicleDeliveryStatementDao 
{
	/**
	 * 查询车位发货日报表信息
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:59:54
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryParkDailyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView) throws Exception ;
	
	/**
	 * 查询车位流量日报表
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:00:19
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryWeighDailyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView) throws Exception ;
	
	/**
	 * 查询车位发货月报表
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:00:39
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryVehicleMonthlyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView) throws Exception ;
	
	/**
	 * 查询车位发货历史累计量报表
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:01:01
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryVehicleHistoryCumulantStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView) throws Exception ;
	
	/**
	 * 查询车位流量计月报总表
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:01:23
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryVehicleMonthlyTotalStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView)  throws Exception;
	
	/**
	 * 查询货品月发货量报表
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:01:44
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryProductMonthlyStatement(VehicleDeliveryStatementDto vehicleDeliveryStatementDto, PageView pageView) throws Exception ;
	
	/**
	 * 查询车位发货日报表信息数据总条数
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:05
	 * @return :int 
	 * @throws
	 */
	public int getParkDailyStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws Exception ;
	
	/**
	 * 查询车位流量日报表数据总条数
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:18
	 * @return :int 
	 * @throws
	 */
	public int getWeighDailyStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws Exception ;
	
	/**
	 * 查询车位发货月报表数据总条数
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:27
	 * @return :int 
	 * @throws
	 */
	public int getVehicleMonthlyStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws Exception ;
	
	/**
	 * 查询车位发货历史累计量报表数据总条数
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:38
	 * @return :int 
	 * @throws
	 */
	public int getVehicleHistoryCumulantStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws Exception ;
	
	/**
	 * 查询车位流量计月报总表数据总条数
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:47
	 * @return :int 
	 * @throws
	 */
	public int getVehicleMonthlyTotalStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto)  throws Exception;
	
	/**
	 * 查询货品月发货量报表数据总条数
	 * @Title:VehicleDeliveryStatementDao
	 * @Description:
	 * @param vehicleDeliveryStatementDto
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午11:02:54
	 * @return :int 
	 * @throws
	 */
	public int getProductMonthlyStatementCount(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws Exception ;

	/**
	 *@author jiahy
	 * @param vsDto
	 * @return
	 */
	public Map<String,Object> getTotalNum( VehicleDeliveryStatementDto vsDto) throws Exception ;

	public List<Map<String, Object>> getProductNameMsg(String stringValue) throws OAException;

	public List<Map<String, Object>> getProductData(VehicleDeliveryStatementDto vsDto) throws OAException;

	public List<Map<String, Object>> getInportMsg(String dateTime, Integer productId) throws OAException;

	public List<Map<String, Object>> getInportData(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws OAException;
	
	public List<Map<String, Object>> getTankMsg(String dateTime, Integer productId,Integer clientId) throws OAException;

	public List<Map<String, Object>> getTankData(VehicleDeliveryStatementDto vehicleDeliveryStatementDto) throws OAException;

	public List<Map<String, Object>> getProductNameTypeMsg(VehicleDeliveryStatementDto vsDto) throws OAException;

	public List<Map<String, Object>> getOutBoundDataList(VehicleDeliveryStatementDto vsDto)throws OAException;
    //获取拼装车数据
	public int getAssemBlyTruck(VehicleDeliveryStatementDto vsDto)throws OAException;

	public Map<String, Object> getProductNameById(int productId) throws OAException;

	public Map<String, Object> getClientNameById(Integer clientId) throws OAException;

}
