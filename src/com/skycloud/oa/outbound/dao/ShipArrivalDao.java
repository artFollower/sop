package com.skycloud.oa.outbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.ShipArrivalDto;

/**
 * 
 * <p>出库管理---出港计划</p>
 * @ClassName:ShipArrivalDao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月28日 上午11:19:20
 *
 */
public interface ShipArrivalDao 
{
	/**
	 * 查询出港计划
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param shipArrivalDto
	 * @param pageView
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:19:44
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> list(ShipArrivalDto shipArrivalDto,PageView pageView)throws OAException;
	
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param shipArrivalDto
	 * @return
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:20:24
	 * @return :int 
	 * @throws
	 */
	public int getCount(ShipArrivalDto shipArrivalDto)throws OAException ;
	
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param id
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:20:41
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> get(String id) throws OAException;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param arrival
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:20:48
	 * @return :int 
	 * @throws
	 */
	public int add(Arrival arrival) throws OAException;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param arrivalPlan
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:20:57
	 * @return :int 
	 * @throws
	 */
	public int add(ArrivalPlan arrivalPlan)throws OAException ;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param arrival
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:21:05
	 * @return :void 
	 * @throws
	 */
	public void updateArrival(Arrival arrival)throws OAException ;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param arrivalPlan
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:21:14
	 * @return :void 
	 * @throws
	 */
	public void updatePlan(ArrivalPlan arrivalPlan)throws OAException ;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param id
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:21:23
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> getArrivalPlan(String id) throws OAException ;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param ids
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:21:30
	 * @return :void 
	 * @throws
	 */
	public void delete(String ids) throws OAException ;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param arrivalPlanId
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:21:38
	 * @return :void 
	 * @throws
	 */
	public void deleteShipPlanById(String arrivalPlanId)  throws OAException ;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param arrivalPlanId
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:21:46
	 * @return :void 
	 * @throws
	 */
	public void updateShipPlanStatus(String arrivalPlanId,Integer status)  throws OAException ;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param arrivalPlanId
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:21:54
	 * @return :void 
	 * @throws
	 */
	public void updateArrivalStatus(String arrivalPlanId)  throws OAException ;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param productId
	 * @param clientId
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:22:01
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> getGoodsList(int productId,int clientId)  throws OAException ;
	
	/**
	 * 
	 * @Title:ShipArrivalDao
	 * @Description:
	 * @param shipId
	 * @throws OAException
	 * @Date:2015年5月28日 上午11:22:19
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String, Object>> getArrivalIdByShipId(String shipId)throws OAException ;

	/**
	 *@author jiahy
	 * @param aDto
	 * @return
	 */
	public List<Map<String, Object>> getGoodsMsg( ArrivalPlanDto aDto) throws OAException;

	/**
	 *@author jiahy
	 * @param id
	 * @return
	 */
	public Map<String, Object> getInvoiceByArrivalId(String id) throws OAException;

	/**
	 * 根据goodsId,查询该货体出港计划还未生成开票的计划量的和
	 *@author jiahy
	 * @param object
	 * @return
	 */
	public Map<String, Object> getArrivalPlanAmount(Integer goodsId) throws OAException;
	
	public Map<String,Object> getClearanceClientMsg(Integer goodsId) throws OAException;


	/**
	 * @Title getIsEqual
	 * @Descrption:TODO
	 * @param:@param valueOf
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年10月28日上午11:06:50
	 * @throws
	 */
	public Integer getIsEqual(Integer arrivalId) throws OAException;


	/**
	 * @Title getDifPlanAndInvoiceMsg
	 * @Descrption:TODO
	 * @param:@param arrivalPlanDto
	 * @param:@return
	 * @return:Collection<? extends Object>
	 * @auhor jiahy
	 * @date 2016年10月31日上午9:53:01
	 * @throws
	 */
	public List<Map<String, Object>> getDifPlanAndInvoiceMsg(ArrivalPlanDto arrivalPlanDto) throws OAException;


	/**
	 * @Title updateArrivalPlan
	 * @Descrption:TODO
	 * @param:@param arrivalPlan
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年10月31日下午3:29:29
	 * @throws
	 */
	public void updateArrivalPlan(ArrivalPlan arrivalPlan) throws OAException;
}
