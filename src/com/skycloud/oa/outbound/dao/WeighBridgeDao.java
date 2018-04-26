package com.skycloud.oa.outbound.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.outbound.dto.WeighBridgeDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.WeighBridge;

/**
 * 
 * <p>出库管理---车发称重</p>
 * @ClassName:WeighBridgeDao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午10:15:14
 *
 */
public interface WeighBridgeDao 
{
	/**
	 * 查询称重信息
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param servalNo
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:15:52
	 * @return:Map<String,Object> 
	 * @throws
	 */
	public Map<String,Object> queryGoodsLogBySerial(String servalNo) throws Exception;
	
	/**
	 * 查询称重信息
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param servalNo
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:16:28
	 * @return:Map<String,Object> 
	 * @throws
	 */
	public Map<String,Object> queryWeighBridgeBySerial(String servalNo) throws Exception;
	
	/**
	 * 查询开票信息
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param serialNo
	 * @param morePrint
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:16:46
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> getDeliverInvoiceInfo(String serialNo,int morePrint) throws Exception;
	
	/**
	 * 添加称重信息
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:16:58
	 * @return :int 
	 * @throws
	 */
	public int addWeighBridge(WeighBridge weighBridge) throws OAException;
	
	/**
	 * 更新称重信息
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param weighBridge
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:17:11
	 * @return :void 
	 * @throws
	 */
	public void up(WeighBridge weighBridge)throws OAException;
	
	/**
	 * 
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param weighBridge
	 * @param trainId
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:17:24
	 * @return :void 
	 * @throws
	 */
	public void updateActualNum(WeighBridge weighBridge)throws Exception;
	
	/**
	 * 
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param serial
	 * @param dNum
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:17:34
	 * @return :void 
	 * @throws
	 */
	public void updateTrainStatusByWeighSerial(String serial,BigDecimal dNum,Long outTime)throws OAException;
	
	/**
	 * 
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param id
	 * @param serial
	 * @throws Exception
	 * @Date:2015年5月27日 下午10:17:43
	 * @return :void 
	 * @throws
	 */
	public void addCreateUserId(int id,String serial) throws Exception;
	
	/**
	 * 查询地磅信息
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @Date:2015年7月9日 下午3:13:46
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> findPlat(WeighBridgeDto weighBridge) throws OAException;
	
	/**
	 * 更新地磅的状态
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param weighBridge
	 * @throws OAException
	 * @Date:2015年7月9日 下午4:44:00
	 * @return :void 
	 * @throws
	 */
	public void updatePlat(WeighBridgeDto weighBridge) throws OAException;
	
	/**
	 * 更新地磅的状态
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param weighBridge
	 * @throws OAException
	 * @Date:2015年7月9日 下午5:59:19
	 * @return :void 
	 * @throws
	 */
	public void updatePlatNot(WeighBridgeDto weighBridge) throws OAException;
	
	/**
	 * 出库管理-车发称重-修改罐号和发运口
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param weighBridge
	 * @throws OAException
	 * @Date:2015年7月31日 下午2:51:56
	 * @return :void 
	 * @throws
	 */
	public void updateTankId(WeighBridge weighBridge) throws OAException;
	
	/**
	 * 更新货品颜色值
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param weighBridge
	 * @throws OAException
	 * @Date:2015年8月6日 上午11:47:33
	 * @return :void 
	 * @throws
	 */
	public void updateColor(WeighBridgeDto weighBridge) throws OAException;
	
	/**
	 * 联单时根据第一个单号查询其他
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param serial
	 * @return
	 * @throws OAException
	 * @Date:2015年9月9日 下午2:05:22
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> findTicket(String ticketNo,String deliverType) throws OAException;
	
	/**
	 * 更新实发数、出库重和入库重
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param weighBridge
	 * @throws OAException
	 * @Date:2015年9月13日 下午10:15:46
	 * @return: void 
	 * @throws
	 */
	public void updateData(WeighBridge weighBridge) throws OAException;
	
	/**
	 * @param deliverType 
	 * 查询联单的开单总数
	 * @Title:WeighBridgeDao
	 * @Description:
	 * @param serialNo
	 * @return
	 * @throws OAException
	 * @Date:2015年9月15日 下午4:48:43
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> queryTotal(String serialNo, String deliverType) throws OAException;

	/**
	 * 更新货体当前存量和weighbridge数量
	 *@author jiahy
	 * @param actualNum
	 * @param goodsId
	 */
	public void updateGoodsCurrentAndWeighBridge(GoodsLog goodslog)throws OAException;
	/**
	 *@author jiahy
	 * @param w2
	 */
	public void updateWeight(WeighBridge w2) throws OAException;

	/**
	 * 获取船舶出库数量确认审核状态
	 * @Title getOutboundReviewStatus
	 * @Descrption:TODO
	 * @param:@param servalNo
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年6月21日上午8:28:56
	 * @throws
	 */
	public Map<String, Object> getOutboundReviewStatus(String servalNo) throws OAException;
}
