package com.skycloud.oa.outbound.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.config.Global;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalDao;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.dao.WeighBridgeDao;
import com.skycloud.oa.outbound.dto.WeighBridgeDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.WeighBridge;
import com.skycloud.oa.outbound.service.WeighBridgeService;
import com.skycloud.oa.report.utils.DateUtils;
import com.skycloud.oa.system.dao.TankDao;
import com.skycloud.oa.system.dao.TankMeasureDao;
import com.skycloud.oa.system.dto.TankMeasureDto;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.HttpRequestUtils;
import com.skycloud.oa.utils.OaMsg;

/**
 * 
 * <p>出库管理---车发称重</p>
 * @ClassName:WeighBridgeServiceImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年5月27日 下午7:41:28
 *
 */
@Service
public class WeighBridgeServiceImpl implements WeighBridgeService 
{
	private static Logger LOG = Logger.getLogger(VehicleDeliverWorkServiceImpl.class);
	
	@Autowired
	private WeighBridgeDao weighBridgeDao;
	@Autowired
	private GoodsLogDao goodsLogDao;
	@Autowired
	private ArrivalDao arrivalDao;
	@Autowired
	private TankMeasureDao tankMeasureDao;
	@Autowired
	private TankDao tankDao;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public OaMsg queryGoodsLogBySerial(String servalNo) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List list = new ArrayList<Map<String, Object>>();
			// 开票信息
			list.add(weighBridgeDao.queryGoodsLogBySerial(servalNo));
			// 称重信息
			Map<String, Object> weightData = weighBridgeDao.queryWeighBridgeBySerial(servalNo);
			weightData.putAll(weighBridgeDao.getOutboundReviewStatus(servalNo));
			list.add(weightData);
			oaMsg.setData(list);
		} catch (Exception re) {
			LOG.error("service查询称重信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
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
	@Override
	public OaMsg getDeliverInvoiceInfo(String serialNo, int type, int morePrint) {
		OaMsg oaMsg = new OaMsg();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			if (serialNo.length() == 3) {
				serialNo = sdf.format(new Date()) + serialNo;
			}
			List<Map<String, Object>> data = weighBridgeDao.getDeliverInvoiceInfo(serialNo, morePrint);
			Map<String, Object> tankdata = null;
			Double normalVolume = 0D;
			if (data != null && data.size() > 0 && type == 1) {
				for (int i = 0; i < data.size(); i++) {
					if (data.get(i).get("oils") != null && Integer.valueOf(data.get(i).get("oils").toString()) == 1) {
						tankdata = tankMeasureDao.getTankMeasureMsg(
								new TankMeasureDto(Integer.valueOf(data.get(i).get("tankId").toString())));
						if (tankdata != null && tankdata.get("id") != null) {
							normalVolume = Common.div(Common.mul(data.get(i).get("deliverNum"), 1000, 3),
									Common.sub(tankdata.get("normalDensity"), 0.0011, 4), 3);
							data.get(i).put("normalVolume", normalVolume);
							data.get(i).put("viewVolume", Common.div(normalVolume, tankdata.get("volumeRatio"), 3));
							data.get(i).put("normalDensity", tankdata.get("normalDensity"));
							data.get(i).put("viewDensity", tankdata.get("textDensity"));
							data.get(i).put("volumeRatio", tankdata.get("volumeRatio"));
						}
					}
				}
			}
			oaMsg.getData().addAll(data);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("service查询开票信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * 查询地磅信息
	 * @Title:findPlat
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public OaMsg findPlat(WeighBridgeDto weighBridge) throws OAException {
		OaMsg oaMsg = new OaMsg();
		oaMsg.getData().addAll(weighBridgeDao.findPlat(weighBridge));
		return oaMsg;
	}

	/**
	 * 更新地磅的状态
	 * 
	 * @Title:updatePlat
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public OaMsg updatePlat(WeighBridgeDto weighBridge) throws OAException {
		OaMsg oaMsg = new OaMsg();
		weighBridgeDao.updatePlat(weighBridge);
		return oaMsg;
	}

	/**
	 * 更新地磅的状态
	 * 
	 * @Title:updatePlatNot
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public OaMsg updatePlatNot(WeighBridgeDto weighBridge) throws OAException {
		OaMsg oaMsg = new OaMsg();
		weighBridgeDao.updatePlatNot(weighBridge);
		return oaMsg;
	}
	/**
	 * 更新颜色值
	 * @Title:updateColor
	 * @Description:
	 * @param weighBridge
	 * @return
	 * @throws OAException
	 * @see 
	 */
	@Override
	public OaMsg updateColor(WeighBridgeDto weighBridge) throws OAException {
		OaMsg oaMsg = new OaMsg();
		weighBridgeDao.updateColor(weighBridge);
		return oaMsg;
	}

	/**
	 * 联单时根据第一个单号查询其他
	 * 
	 * @Title:findTicket
	 * @Description:
	 * @param ticketNo
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public OaMsg findTicket(WeighBridgeDto wb) throws OAException {
		OaMsg oaMsg = new OaMsg();
		List<Map<String, Object>> data = weighBridgeDao.findTicket(wb.getTicketNo(), wb.getDeliverType());
		Integer status = 0, deliverType = 0, oils = -1;
		if (data != null && data.size() > 0) {
			for (Map<String, Object> wbMap : data) {
				if (wb.getTicketNo().equals(wbMap.get("serial"))) {
					status = (data.get(0).get("status") != null ? Integer.valueOf(data.get(0).get("status").toString()):0);
					deliverType = (data.get(0).get("deliverType") != null? Integer.valueOf(data.get(0).get("deliverType").toString()) : 0);
					oils = (data.get(0).get("oils") != null ? Integer.valueOf(data.get(0).get("oils").toString()): (-1));
				}
			}
		}
		if (deliverType == 1) {
			if (oils == 0) {// 化学品
				if (status == 0) {// 化学品入库称重 -- 同步通知单到流量计
					addFlowMeter(wb.getTicketNo(), oaMsg);
				} else if (status == 2) {// 化学品出库称重 --获取表返值和发货口
					data = getFlowMeter(data, wb.getTicketNo(), oaMsg);
				} else {
				}
			} else if (oils == 1) {// 油品 ---获取表反值和发货口
				data = getFlowMeter(data, wb.getTicketNo(), oaMsg);
			} else {
			}
		}
		oaMsg.getData().addAll(data);
		return oaMsg;
	}

	/**
	 * 查询联单的开单总数
	 * @Title:queryTotal
	 * @Description:
	 * @param serialNo
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public OaMsg queryTotal(String serialNo, String deliverType) throws OAException {
		OaMsg oaMsg = new OaMsg();
		oaMsg.getData().addAll(weighBridgeDao.queryTotal(serialNo, deliverType));
		return oaMsg;
	}
	/**
	 * 船发称重
	 *@author jiahy
	 * @param mGoodsLogList 船发相关 goodsLog 信息
	 * @param mWeighBridgeList 称重相关list
	 * @return
	 * @throws OAException 
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_SHIPWEIGHBRIDGE,type=C.LOG_TYPE.UPDATE)
	public OaMsg confirmShip(List<GoodsLog> mGoodsLogList, List<WeighBridge> mWeighBridgeList)throws OAException {
		//TODO 更新goodsLog ,更新goods,更新arrial状态，添加weighbridge
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(mGoodsLogList!=null&&mGoodsLogList.size()>0){
			for(int i=0;i<mGoodsLogList.size();i++){
				mGoodsLogList.get(i).setCreateTime((System.currentTimeMillis()/1000)+i);
				if(i==0){
					arrivalDao.updateArrival(new Arrival(mGoodsLogList.get(i).getBatchId(),53));
				}
				goodsLogDao.updateGoodsCurrentByActualNum(mGoodsLogList.get(i));
			}
			}
			if(mWeighBridgeList!=null&&mWeighBridgeList.size()>0){
				for(int i=0;i<mWeighBridgeList.size();i++){
					if(mWeighBridgeList.get(i).getId()==0)
				weighBridgeDao.addWeighBridge(mWeighBridgeList.get(i));
				}
			}
			
		} catch (Exception re) {
			LOG.error("船发称重失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "船发称重失败",re);
		}
		return oaMsg;
	}
	
	public  void addFlowMeter(String serial,OaMsg oaMsg)throws OAException{
		try {
			List<Map<String, Object>> goodsLogList=goodsLogDao.getFlowMeterList(serial);
			JSONArray array = new JSONArray();
			JSONObject obj;
			if(goodsLogList!=null &&goodsLogList.size()>0&&goodsLogList.get(0).get("serial")!=null){
				for(int i=0,len=goodsLogList.size();i<len;i++){
					obj = new JSONObject();
					obj.put("notify",goodsLogList.get(i).get("serial"));//通知单号
					obj.put("hpz", goodsLogList.get(i).get("productCode"));//货品
					obj.put("ydb",goodsLogList.get(i).get("deliverNum"));//调拨量(开票数量)
					obj.put("m_num",goodsLogList.get(i).get("deliverNum"));//计划发送量
					obj.put("tankNo",getTankKey(tankDao.getTankByCode((goodsLogList.get(i).get("tankName")!=null?goodsLogList.get(i).get("tankName").toString():"-1"))));////罐号
					obj.put("fInTime",DateUtils.getStringTime(Long.valueOf(goodsLogList.get(i).get("createTime").toString())));//进库时间
					array.add(obj);
				}
				JSONObject	objData = new JSONObject();
				objData.put("weights", array);
				JSONObject json = HttpRequestUtils.httpPost(Global.cloudConfig.get("flowmeter.url").toString()+"create","weights="+objData.toJSONString());
				handleJson(json, oaMsg);
			}
		} catch (Exception re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
	}
	
	public List<Map<String, Object>>  getFlowMeter(List<Map<String, Object>> data,String serial,OaMsg  oaMsg) throws OAException{
		  Map<String, Object> map = null;
		  if(data!=null&&data.size()>0){
				for(Map<String, Object> wbMap:data){
					if(serial.equals(wbMap.get("serial"))){
						map=wbMap;
					}}
			}
		  List<Map<String, Object>> FlowMeterData=new ArrayList<Map<String, Object>>();
		  
        	if(map.get("measureWeigh")==null||map.get("inPort")==null){
        		List<Map<String, Object>> serialList=goodsLogDao.getFlowMeterList(serial);
				String serials="";
				for(Map<String, Object> mapA:serialList){
					serials+=mapA.get("serial")+",";
				}
				JSONObject json = HttpRequestUtils.httpPost(Global.cloudConfig.get("flowmeter.url").toString()+"get","notifies="+serials.substring(0, serials.length()-1));
				if(json!=null&& json.get("data")!=null){
					List<Map<String, Object>> nData=(List<Map<String, Object>>) json.get("data");
					handleJson(json, oaMsg);
					if(nData!=null&&nData.size()>0){
						for(Map<String, Object> mapA:nData){
							handleFlag(mapA,oaMsg);
							if("".equals(mapA.get("flag").toString())){
								FlowMeterData.addAll(goodsLogDao.syncFlowMeter(mapA.get("NOTIFY").toString(),Double.valueOf(mapA.get("Bakn1").toString()), (mapA.get("Bakn2")!=null?Integer.valueOf(mapA.get("Bakn2").toString()):0)));					
							}
						}}
					for(Map<String,Object> mapB:FlowMeterData){
						for(Map<String, Object> mapC:data){
							if(mapB.get("serial").toString().equals(mapC.get("serial"))){
		                            mapC.put("weightBridgeId", mapB.get("id"));
		                            mapC.put("measureWeigh",mapB.get("measureWeigh"));
		                            mapC.put("inPort",mapB.get("inPort"));
							}
						}
					}	  
				}
        	} 
		return data;
	}
	public  String  getTankKey(Map<String, Object> map){
		if(map!=null&&map.get("key")!=null){
			return map.get("key").toString();
		}else{
			return "";
		}
	}

	/**
	 * @throws Exception 
	 * @Title confirmTruck
	 * @Descrption:TODO
	 * @param:@param mGoodsLogList
	 * @param:@param mWeighBridgeList
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月15日上午8:32:15
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_WEIGHTBRIDGE,type=C.LOG_TYPE.UPDATE)
	public OaMsg confirmTruck(List<GoodsLog> mGoodsLogList, List<WeighBridge> mWeighBridgeList) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			  if(mWeighBridgeList!=null&&mWeighBridgeList.size()>0){
				  int status=mWeighBridgeList.get(0).getStatus();
				  for(int i=0;i<mWeighBridgeList.size();i++){
					   if(mWeighBridgeList.get(i).getId()!=0){
						    weighBridgeDao.up(mWeighBridgeList.get(i));   
					   }else{
							mWeighBridgeList.get(i).setCreateTime(new Date());
							weighBridgeDao.addWeighBridge(mWeighBridgeList.get(i));
					   }
					 } 
				  if(status==3){
					  if(mGoodsLogList!=null&&mGoodsLogList.size()>0){
						  DecimalFormat df = new DecimalFormat("0.000");
						  weighBridgeDao.updateTrainStatusByWeighSerial(mGoodsLogList.get(0).getSerial(),new BigDecimal(df.format(mGoodsLogList.get(0).getActualNum())),mGoodsLogList.get(0).getCreateTime());
					  } 
				  }else{
					  addFlowMeter(mGoodsLogList.get(0).getSerial(),oaMsg);
				  }
			  }
		}catch(OAException e){
			LOG.error("车发称重失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "车发称重失败",e);
		}
		return oaMsg;
	}
	
	/**
	 * 处理发送流量计后返回的json数据
	 * @Title handleJson
	 * @Descrption:TODO
	 * @param:@param json
	 * @param:@param oaMsg
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年5月25日上午9:32:57
	 * @throws
	 */
	private void handleJson(JSONObject json, OaMsg oaMsg) {
         if(json!=null&&json.get("code")!=null){
        	 if("0000".equals(json.get("code"))){
        		 oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
        	 }else{
        		 oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
        		 oaMsg.setMsg("同步流量计失败，请手动同步。");
        	 }
         }else{
        	 oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
        	 oaMsg.setMsg("同步流量计失败，请联系管理员。");
         }

	}
	
	/**
	 * flag标签
	 * @Title handleFlag
	 * @Descrption:TODO
	 * @param:@param mapA
	 * @param:@param oaMsg
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年5月25日上午10:07:26
	 * @throws
	 */
	private void handleFlag(Map<String, Object> mapA, OaMsg oaMsg) {
		Map<String,String> map=oaMsg.getMap();
	 String flag=map.get("flag");//标签
	   if(flag!=null){
		   if("*".equals(flag)){
			   return ;
		   }else if("-".equals(flag)){
			   if("*".equals(mapA.get("flag").toString())){
					map.put("flag","0");
					map.put("msg","车辆还未开始装货");
				}
		   }else if("".equals(flag)){
			   if("*".equals(mapA.get("flag").toString())){
					map.put("flag","0");
					map.put("msg","车辆还未开始装货");
				}else if("-".equals(mapA.get("flag").toString())){
					map.put("flag","1");//正在装货
					map.put("msg","车辆正在装货");
				} 
		   }
	   }else{
		if("*".equals(mapA.get("flag").toString())){
			map.put("flag","0");
			map.put("msg","车辆还未开始装货");
		}else if("-".equals(mapA.get("flag").toString())){
			map.put("flag","1");//正在装货
			map.put("msg","车辆正在装货");
		}else if("".equals(mapA.get("flag").toString())){
			map.put("flag", "2");//表示已经出库
		}
	   }
	}
	/**
	 * @throws OAException 
	 * @Title syncSerialToFlowMeter
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@param msg
	 * @auhor jiahy
	 * @date 2017年7月13日上午7:07:09
	 * @throws
	 */
	@Override
	public void syncSerialToFlowMeter(String serial, OaMsg msg) throws OAException {
		addFlowMeter(serial,msg);
		if(Common.empty(msg.getCode())){
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("同步流量计失败，该通知单不符合情况。");
		}
	}

	
}
