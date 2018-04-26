package com.skycloud.oa.outbound.service;

import java.util.List;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.outbound.dto.WeighBridgeDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.WeighBridge;
import com.skycloud.oa.utils.OaMsg;

/**
 * 
 * <p>出库管理---车发称重</p>
 * @ClassName:WeighBridgeService
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午10:07:48
 *
 */
public interface WeighBridgeService 
{
	/**
	 * 查询称重信息
	 * @Title:WeighBridgeService
	 * @Description:
	 * @param servalNo
	 * @return
	 * @Date:2015年5月27日 下午10:08:04
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryGoodsLogBySerial(String servalNo) ;
	
	/**
	 * 查询开票信息
	 * @Title:WeighBridgeService
	 * @Description:
	 * @param serialNo
	 * @param type
	 * @return
	 * @Date:2015年5月27日 下午10:08:50
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg getDeliverInvoiceInfo(String serialNo,int type,int morePrint) ;
	
	/**
	 * 查询地磅信息
	 * @Title:WeighBridgeService
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @Date:2015年7月9日 下午3:24:01
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg findPlat(WeighBridgeDto weighBridge) throws OAException;
	
	/**
	 * 更新地磅的状态
	 * @Title:WeighBridgeService
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @Date:2015年7月9日 下午4:52:48
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg updatePlat(WeighBridgeDto weighBridge) throws OAException;
	
	/**
	 * 更新地磅的状态
	 * @Title:WeighBridgeService
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @Date:2015年7月9日 下午6:01:12
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg updatePlatNot(WeighBridgeDto weighBridge) throws OAException;
	
	/**
	 * 更新颜色值
	 * @Title:WeighBridgeService
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @Date:2015年8月6日 上午11:54:18
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg updateColor(WeighBridgeDto weighBridge) throws OAException;
	
	/**
	 * 联单时根据第一个单号查询其他
	 * @Title:WeighBridgeService
	 * @Description:
	 * @param ticketNo
	 * @return
	 * @throws OAException
	 * @Date:2015年9月9日 下午11:03:53
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg findTicket(WeighBridgeDto weighBridge) throws OAException;
	
	/**
	 * 查询联单的开单总数
	 * @Title:WeighBridgeService
	 * @Description:
	 * @param serialNo
	 * @return
	 * @throws OAException
	 * @Date:2015年9月15日 下午4:55:51
	 * @return:OaMsg 
	 * @throws
	 */
	public OaMsg queryTotal(String serialNo,String deliverType) throws OAException;

	/**
	 * 船发称重
	 *@author jiahy
	 * @param mGoodsLogList 船发相关 goodsLog 信息
	 * @param mWeighBridgeList 称重相关list
	 * @return
	 */
	public OaMsg confirmShip(List<GoodsLog> mGoodsLogList,List<WeighBridge> mWeighBridgeList)throws OAException;

	/**
	 * @Title confirmTruck
	 * @Descrption:TODO
	 * @param:@param mGoodsLogList
	 * @param:@param mWeighBridgeList
	 * @param:@return
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2016年5月15日上午8:31:51
	 * @throws
	 */
	public OaMsg confirmTruck(List<GoodsLog> mGoodsLogList, List<WeighBridge> mWeighBridgeList) throws OAException;

	/**
	 * 手动同步数据到流量计
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@param msg
	 * @return:void
	 * @auhor jiahy
	 * @date 2017年7月13日上午7:06:42
	 * @throws
	 */
	public void syncSerialToFlowMeter(String serial, OaMsg msg) throws OAException;
}
