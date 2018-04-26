package com.skycloud.oa.common.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.model.Truck;

public interface BaseControllerDao 
{
	public List<Map<String, Object>> getArrivalShipInfo()throws OAException ;
	public List<Map<String, Object>> getVehiclePlateList(String vehiclePlate)throws OAException ;
	public List<Map<String,Object>> getClientNameList(String clientName)throws OAException ;
	public List<Map<String,Object>> getHistoryClientName()throws OAException ;
	public List<Map<String,Object>> getClientNameByProductId(String clientName,String productId)throws OAException ;
	public List<Map<String,Object>> getLadingCodeList(String id,String code,String productId)throws OAException ;
	public List<Map<String,Object>> getProductNameList(String productName)throws OAException ;
	public List<Map<String,Object>> getShipChineseName(String shipId)throws OAException ;
	public List<Map<String,Object>> getTankName(String tankName)throws OAException ;
	public List<Map<String,Object>> getTankCode(Integer productId)throws OAException ;
	public List<Map<String,Object>> getParkName(String parkName)throws OAException ;
	public List<Map<String,Object>> getVehiclePlateByTrainId(String trainId)throws OAException ;
	public List<Map<String,Object>> getBerthByName(String trainId)throws OAException ;
	public List<Map<String,Object>>  getParkList(String parkName,String ids) throws OAException ;
	public List<Map<String,Object>>  getShipName(String shipName) throws OAException ;
	public List<Map<String,Object>>  getCanMakeInvoiceShipName(String shipName,String ladingCode, Integer isNoTransport) throws OAException ;
	public List<Map<String,Object>>  getCanMakeInvoiceTruck() throws OAException ;
	public List<Map<String,Object>>  getWeather()throws OAException ;
	public List<Map<String,Object>>  getWindDirection()throws OAException ;
	public List<Map<String,Object>>  getWindPower()throws OAException ;
	public List<Map<String,Object>>  getUser()throws OAException ;
	public List<Map<String,Object>>  getTrans(String arrivalId) throws OAException ;
	public List<Map<String, Object>> getCargoAgentList(CargoAgentDto caDto,int start,int limit)throws OAException;
	public void sendSystemMessage(String upInfo,int userId,String right,int item) throws OAException ;
	
	/**
	 * 查询船信
	 * @Title:BaseControllerDao
	 * @Description:
	 * @return
	 * @throws OAException
	 * @Date:2015年8月3日 下午5:14:33
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> queryShipInfo()throws OAException ;
	
	/**
	 * 获取基本的配置参数
	 * @Description: 
	 * @author xie
	 * @date 2015年7月28日 下午10:14:30  
	 * @return
	 * @throws OAException
	 */
	public List<Map<String,Object>>  getParams()throws OAException ;
	
	/**
	 * 系统管理-基础信息-客户资料-添加客户编号总数
	 * @Title:BaseControllerDao
	 * @Description:
	 * @param queryStr
	 * @return
	 * @throws OAException
	 * @Date:2015年8月5日 下午8:21:15
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> queryClientCount(String queryStr)throws OAException ;
	
	/**
	 * 查询某辆车的核载量
	 * @Title:BaseControllerDao
	 * @Description:
	 * @param carNo
	 * @return
	 * @throws OAException
	 * @Date:2015年8月28日 下午1:20:30
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> queryOneCar(String carNo)throws OAException;
	
	/**
	 * 更新某辆车的核载量
	 * @Title:BaseControllerDao
	 * @Description:
	 * @param truck
	 * @return
	 * @throws OAException
	 * @Date:2015年8月28日 下午2:01:32
	 * @return: void
	 * @throws
	 */
	public void updateOneCar(Truck truck)throws OAException ;
	/**
	 *@author jiahy
	 * @return
	 */
	public List<Map<String, Object>> getCargoList() throws OAException;
}
