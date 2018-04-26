package com.skycloud.oa.outbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.ShipArrivalDto;
import com.skycloud.oa.utils.OaMsg;

/**
 * 
 * <p>出库管理---出港计划</p>
 * @ClassName:ShipArrivalService
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月28日 上午11:12:39
 *
 */
public interface ShipArrivalService 
{
	/**
	 * 查询出港计划
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param shipArrivalDto
	 * @param pageView
	 * @return
	 * @Date:2015年5月28日 上午11:12:55
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg list(ShipArrivalDto shipArrivalDto,PageView pageView);
	
	
	/**
	 * 通过id获取出港计划
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param id
	 * @return
	 * @Date:2015年5月28日 上午11:13:58
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg get(String id);
	
	/**
	 * 获取货体列表
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param clientId
	 * @param productId
	 * @return
	 * @Date:2015年5月28日 上午11:14:24
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg getGoodsList(int clientId,int productId);
	
	/**
	 * 添加到港计划信息
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param shipArrivalDto
	 * @return
	 * @Date:2015年5月28日 上午11:14:44
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg add(ShipArrivalDto shipArrivalDto);
	
	/**
	 * 更新到港计划基本信息
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param shipArrivalDto
	 * @return
	 * @Date:2015年5月28日 上午11:15:09
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg update(ShipArrivalDto shipArrivalDto);
	
	/**
	 * 删除到港计划信息
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param ids
	 * @return
	 * @Date:2015年5月28日 上午11:15:34
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg delete(String ids);
	
	/**
	 * 通过id删除到港计划信息
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param arrivalPlanId
	 * @return
	 * @Date:2015年5月28日 上午11:16:06
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg deleteShipPlanById(String arrivalPlanId,Integer status);
	
	/**
	 * 更新审批状态
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param arrivalPlanId
	 * @return
	 * @Date:2015年5月28日 上午11:16:45
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg updateShipPlanStatus(String arrivalPlanId);
	
	/**
	 * 更新出港确认状态
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param arrivalId
	 * @return
	 * @Date:2015年5月28日 上午11:17:05
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg updateArrivalStatus(String arrivalId);
	
	/**
	 * 更新到港计划信息
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param shipArrivalDto
	 * @return
	 * @Date:2015年5月28日 上午11:17:28
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg updatePlan(ArrivalPlan shipArrivalDto);
	
	/**
	 * 添加保存到港计划信息
	 * @Title:ShipArrivalService
	 * @Description:
	 * @param arrivalPlan
	 * @return
	 * @Date:2015年5月28日 上午11:17:46
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg saveArrivalPlan(ArrivalPlan arrivalPlan);

	/**
	 *@author jiahy
	 * @param arrivalPlan
	 * @return
	 */
	public OaMsg getGoodsMsg(ArrivalPlanDto aDto) throws OAException;


	/**
	 * @Title reBackPlan
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年8月23日下午3:01:18
	 * @throws
	 */
	public OaMsg reBackPlan(Integer arrivalId) throws OAException;


	/**
	 * @Title getDifPlanAndInvoiceMsg
	 * @Descrption:TODO
	 * @param:@param arrivalPlanDto
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年10月31日上午9:40:34
	 * @throws
	 */
	public OaMsg getDifPlanAndInvoiceMsg(ArrivalPlanDto arrivalPlanDto)throws OAException;


	/**
	 * @Title updateArrivalPlan
	 * @Descrption:TODO
	 * @param:@param arrivalPlan
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年10月31日下午3:28:39
	 * @throws
	 */
	public OaMsg updateArrivalPlan(ArrivalPlan arrivalPlan) throws OAException;
}
