package com.skycloud.oa.common.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.model.Truck;
import com.skycloud.oa.utils.OaMsg;

public interface BaseControllerService {
	public OaMsg getVehiclePlateList(String vehiclePlate) ;
	public OaMsg getArrivalShipInfo() ;
	public OaMsg getClientNameList(String clientName) ;
	public OaMsg getClientNameByProductId(String clientName,String productId) ;
	public OaMsg getHistoryClientName() ;
	public OaMsg getLadingCodeList(String id,String code,String productId) ;
	public OaMsg getProductNameList(String productName) ;
	public OaMsg getShipChineseName(String shipId) ;
	public OaMsg getTankName(String tankName) ;
	public OaMsg getParkName(String parkName) ;
	public OaMsg getVehiclePlateByTrainId(String trainId) ;
	public OaMsg getBerthByName(String trainId) ;
	public OaMsg  getParkList(String parkName,String ids) ;
	public OaMsg  getShipName(String shipName) ;
	public OaMsg  getCanMakeInvoiceShipName(String productId,String ladingCode, Integer isNoTransport) ;
	public OaMsg  getCanMakeInvoiceTruck() ;
	public OaMsg getWeather() ;
	public OaMsg getWindDirection() ;
	public OaMsg getWindPower() ;
	public OaMsg getUser() ;
	public OaMsg getTrans(String arrivalId) ;
	public OaMsg getTankCode(Integer productId);
	public OaMsg getCargoAgentList(CargoAgentDto caDto, int start, int limit)throws OAException;
	
	/**
	 * 查询流量计台帐中船名
	 * @Title:BaseControllerService
	 * @Description:
	 * @return
	 * @throws OAException
	 * @Date:2015年8月5日 下午8:23:56
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryShipInfo() throws OAException;
	
	/**
	 * 系统管理-基础信息-客户资料-添加客户编号总数
	 * @Title:BaseControllerService
	 * @Description:
	 * @param queryStr
	 * @return
	 * @throws OAException
	 * @Date:2015年8月5日 下午8:25:39
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryClientCount(String queryStr) throws OAException;
	
	/**
	 * 查询某辆车的核载量
	 * @Title:BaseControllerService
	 * @Description:
	 * @param carNo
	 * @return
	 * @throws OAException
	 * @Date:2015年8月28日 下午1:24:45
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryOneCar(String carNo) throws OAException;
	
	/**
	 * 更新某辆车的核载量
	 * @Title:BaseControllerService
	 * @Description:
	 * @param truck
	 * @return
	 * @throws OAException
	 * @Date:2015年8月28日 下午2:09:00
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg updateOneCar(Truck truck)throws OAException ;
	/**
	 *@author jiahy
	 * @return
	 */
	public OaMsg getCargoList() throws OAException;
}
