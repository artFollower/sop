package com.skycloud.oa.outbound.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.outbound.dto.WeighBridgeDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.WeighBridge;
import com.skycloud.oa.outbound.service.WeighBridgeService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 
 * <p>出库管理---车发称重</p>
 * @ClassName:WeighBridgeController
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午10:05:20
 *
 */
@Controller
@RequestMapping("/weighBridge")
public class WeighBridgeController 
{
	/**
	 * weighBridgeService
	 */
	@Autowired
	private WeighBridgeService weighBridgeService ;
	
	/**
	 * 查询称重信息
	 * @Title:WeighBridgeController
	 * @Description:
	 * @param serialNo
	 * @return
	 * @Date:2015年5月27日 下午10:06:09
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "queryWeighBridgeInfoStatement")
	@ResponseBody
	public Object queryGoodsLogBySerial(String serialNo)
	{
		return weighBridgeService.queryGoodsLogBySerial(serialNo) ;
	}
	
	/**
	 * 查询开票信息
	 * @Title:WeighBridgeController
	 * @Description:
	 * @param serialNo
	 * @param type
	 * @return
	 * @Date:2015年5月27日 下午10:07:14
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "getDeliverInvoiceInfo")
	@ResponseBody
	public Object getDeliverInvoiceInfo(String serialNo,int type,int morePrint)
	{
		return weighBridgeService.getDeliverInvoiceInfo(serialNo,type,morePrint) ;
	}
	
	/**
	 * 查询地磅信息
	 * @Title:WeighBridgeController
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @Date:2015年7月9日 下午3:28:16
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "findPlat")
	@ResponseBody
	public Object findPlat(@ModelAttribute WeighBridgeDto weighBridge) throws OAException
	{
		return weighBridgeService.findPlat(weighBridge);
	}
	
	/**
	 * 更新地磅的状态
	 * @Title:WeighBridgeController
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @Date:2015年7月9日 下午4:56:58
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "updatePlat")
	@ResponseBody
	public Object updatePlat(@ModelAttribute WeighBridgeDto weighBridge) throws OAException
	{
		return weighBridgeService.updatePlat(weighBridge);
	}
	
	/**
	 * 更新地磅的状态
	 * @Title:WeighBridgeController
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @Date:2015年7月9日 下午6:02:52
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "updatePlatNot")
	@ResponseBody
	public Object updatePlatNot(@ModelAttribute WeighBridgeDto weighBridge) throws OAException
	{
		return weighBridgeService.updatePlatNot(weighBridge);
	}
	
	/**
	 * 更新颜色值
	 * @Title:WeighBridgeController
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @Date:2015年8月6日 上午11:56:09
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "updateColor")
	@ResponseBody
	public Object updateColor(@ModelAttribute WeighBridgeDto weighBridge) throws OAException
	{
		return weighBridgeService.updateColor(weighBridge);
	}
	
	/**
	 * 联单时根据第一个单号查询其他
	 * @Title:WeighBridgeController
	 * @Description:
	 * @param ticketNo
	 * @return
	 * @throws OAException
	 * @Date:2015年9月9日 下午11:07:04
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "findTicket")
	@ResponseBody
	public OaMsg findTicket(@ModelAttribute WeighBridgeDto weighBridge) throws OAException
	{
		return weighBridgeService.findTicket(weighBridge);
	}
	/**
	 * 查询联单的开单总数
	 * @Title:WeighBridgeController
	 * @Description:
	 * @param serialNo
	 * @return
	 * @throws OAException
	 * @Date:2015年9月15日 下午4:57:45
	 * @return:Object 
	 * @throws
	 */
	@RequestMapping(value = "queryTotal")
	@ResponseBody
	public Object queryTotal(String serialNo,String deliverType) throws OAException
	{
		return weighBridgeService.queryTotal(serialNo,deliverType);
	}
	@RequestMapping(value = "confirmship")
	@ResponseBody
	public OaMsg confirmShip(String goodsLogList,String weighBridgeList) throws OAException{
		List<GoodsLog> mGoodsLogList= new Gson().fromJson(goodsLogList,new TypeToken<List<GoodsLog>>() {}.getType());
		List<WeighBridge> mWeighBridgeList=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(weighBridgeList,new TypeToken<List<WeighBridge>>() {}.getType());
		return weighBridgeService.confirmShip(mGoodsLogList,mWeighBridgeList);
	}
	
	@RequestMapping(value = "confirmtruck")
	@ResponseBody
	public OaMsg confirmTruck(String goodsLogList,String weighBridgeList) throws OAException{
		List<GoodsLog> mGoodsLogList= new Gson().fromJson(goodsLogList,new TypeToken<List<GoodsLog>>() {}.getType());
		List<WeighBridge> mWeighBridgeList= new Gson().fromJson(weighBridgeList,new TypeToken<List<WeighBridge>>() {}.getType());
		return weighBridgeService.confirmTruck(mGoodsLogList,mWeighBridgeList);
	}
	
	/**
	 *手动同步地磅数据
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@return
	 * @param:@throws OAException
	 * @return:OaMsg
	 * @auhor jiahy
	 * @date 2017年7月13日上午7:05:28
	 * @throws
	 */
	@RequestMapping(value = "syncSerialToFlowMeter")
	@ResponseBody
	public OaMsg syncSerialToFlowMeter(String serial) throws OAException{
		OaMsg msg = new OaMsg();
		weighBridgeService.syncSerialToFlowMeter(serial,msg);
		return msg;
	}
}
