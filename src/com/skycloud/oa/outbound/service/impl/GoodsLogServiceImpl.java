package com.skycloud.oa.outbound.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.config.Global;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.dao.ShipArrivalDao;
import com.skycloud.oa.outbound.dao.VehicleDeliverWorkDao;
import com.skycloud.oa.outbound.dto.GoodsLogDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.Train;
import com.skycloud.oa.outbound.service.GoodsLogService;
import com.skycloud.oa.report.utils.DateUtils;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.system.dao.TankDao;
import com.skycloud.oa.system.dao.TankMeasureDao;
import com.skycloud.oa.system.dao.TruckDao;
import com.skycloud.oa.system.model.Truck;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.HttpRequestUtils;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
@Service
public class GoodsLogServiceImpl implements GoodsLogService 
{
	private static Logger LOG = Logger.getLogger(GoodsLogServiceImpl.class);
	@Autowired
	private GoodsLogDao invoiceDao ;
	@Autowired
	private VehicleDeliverWorkDao vehicleDeliverWorkDao ;
	@Autowired
	private TruckDao truckDao;
	@Autowired
	private TankMeasureDao tankMeasureDao;
	@Autowired
	private TankDao tankDao;
	@Autowired
	private ShipArrivalDao shipArrivalDao;
	/**查询所有开票信息
	 * @Title list
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@param pageView
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:13:00
	 * @throws
	 */
	@Override
	public OaMsg list(GoodsLogDto invoiceDto, PageView pageView) throws OAException{
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(invoiceDao.getInvoiceList(invoiceDto, pageView)) ;
			oaMsg.getMap().put(Constant.TOTALRECORD,  invoiceDao.getInvoiceListCount(invoiceDto)+ "");
		} catch (RuntimeException re) {
			LOG.error("service查询所有开票信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * 通过id查询开票信息
	 */
	@Override
	public OaMsg get(String id) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(invoiceDao.get(id));
		} catch (RuntimeException re) {
			LOG.error("service通过id查询开票信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * 更新发货开票
	 * @Title changeInvoice
	 * @Descrption:TODO
	 * @param:@param goodsLog
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:27:19
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODSLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg changeInvoice(GoodsLog goodsLog) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			//修改开票量
			if(goodsLog.getDeliverType() == 1 && Integer.valueOf(invoiceDao.checkIsChangeDeliveryNum(goodsLog).get("isChange").toString()) == 1) {				
				try {
					invoiceDao.up(goodsLog);
					invoiceDao.changeInvoice(goodsLog);
					List<Map<String, Object>> goodsLogList=invoiceDao.getFlowMeterList(goodsLog.getSerial());
					JSONArray array = new JSONArray();
					JSONObject obj;
					if(goodsLogList!=null &&goodsLogList.size()>0&&goodsLogList.get(0).get("serial")!=null){
						for(int i=0,len=goodsLogList.size();i<len;i++){
							obj = new JSONObject();
							obj.put("notify",goodsLogList.get(i).get("serial"));//通知单号
							obj.put("ydb",goodsLogList.get(i).get("deliverNum"));//调拨量(开票数量)
							obj.put("m_num",goodsLogList.get(i).get("deliverNum"));//计划发送量
							array.add(obj);
						}
						JSONObject	objData = new JSONObject();
						objData.put("weights", array);
						JSONObject json = HttpRequestUtils.httpPost(Global.cloudConfig.get("flowmeter.url").toString()+"update","weights="+objData.toJSONString());
						if(!Common.empty(json) && json.get("code")!=null) {
							if("0000".equals(json.getString("code"))) {
							}else {
								oaMsg.setCode("0001");
								oaMsg.setMsg(json.getString("msg"));
							}
						}
					}
				} catch (Exception re) {
					oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
					invoiceDao.up(goodsLog);
					invoiceDao.changeInvoice(goodsLog);	
				}
				
			}else {
				invoiceDao.up(goodsLog);
				invoiceDao.changeInvoice(goodsLog);				
			}
		} catch (RuntimeException re) {
			LOG.error("service更新发货开票失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}

		return oaMsg;
	}


	/**
	 * 删除开票信息
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODSLOG,type=C.LOG_TYPE.DELETE)
	public OaMsg delete(String ids) throws OAException{
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, Object> map =  invoiceDao.getGoodsLogSerial(Integer.valueOf(ids));
			if(!Common.empty(map)&&Integer.valueOf(map.get("deliverType").toString()) == 1) {
				LOG.debug("weightsUrl:"+Global.cloudConfig.get("flowmeter.url").toString()+"delete?notifies="+invoiceDao.getFlowMeterSerial(Integer.valueOf(ids)).get("serial"));
				JSONObject json = HttpRequestUtils.httpPost(Global.cloudConfig.get("flowmeter.url").toString()+"delete","notifies="+invoiceDao.getFlowMeterSerial(Integer.valueOf(ids)).get("serial"));
				if(!Common.empty(json) && "0000".equals(json.getString("code"))) {
					invoiceDao.delete(ids);	
				}else{
					oaMsg.setCode("0001");
					oaMsg.setMsg(json.getString("msg"));
				}
			}else {
				invoiceDao.delete(ids);				
			}
		} catch (RuntimeException re) {
			LOG.error("service删除发货开票失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	
	/**
	 * 获取最新通知单号
	 * @Title getSerial
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:52:31
	 * @throws
	 */
	public OaMsg getSerial(String serial) throws OAException{
		OaMsg oaMsg = new OaMsg();
		try {
		Map<String,Object> map =vehicleDeliverWorkDao.getSerial(serial);
		oaMsg.getData().add(map);
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		}catch(RuntimeException e){
			LOG.error("service获取最新通知单号失败", e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg ;
	}
	/**
	 * 添加发货开票
	 * @Title addGoodsLog
	 * @Descrption:TODO
	 * @param:@param s
	 * @param:@param id
	 * @param:@param msg
	 * @param:@return
	 * @param:@throws Exception
	 * @return:String
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:53:09
	 * @throws
	 */
	@Log(object=C.LOG_OBJECT.PCS_GOODSLOG,type=C.LOG_TYPE.CREATE)
	public synchronized String addGoodsLog(GoodsLogDto s,int id,OaMsg msg)throws Exception{
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		String mSerial="";//第一个通知单号
		GoodsLog goodsLog;
		String serial;
		Object goodsCurrent,waitAmount,planAmount;
		Double leftAmount;
		for(int i = 0,len=s.getGoodsLogList().size();i<len;i++){
			goodsLog = s.getGoodsLogList().get(i);
			goodsLog.setBatchId(id);
			goodsLog.setVehicleShipId(s.getVehicleShipId());
			goodsLog.setCreateUserId(user.getId()) ;
			goodsLog.setActualType(0);
			goodsCurrent=invoiceDao.getGoodsCurrent(goodsLog.getGoodsId()).get("goodsCurrent");
			waitAmount=invoiceDao.queryWaitAmount(goodsLog.getGoodsId()).get("waitAmount");
			goodsCurrent=(goodsCurrent==null?0D:goodsCurrent);
			waitAmount=(waitAmount==null?0D:waitAmount);
			if(goodsLog.getDeliverType()==1){
				planAmount=shipArrivalDao.getArrivalPlanAmount(goodsLog.getGoodsId()).get("amount");
				planAmount=(planAmount==null?0D:planAmount);
				leftAmount=Common.sub(Common.sub(Common.sub(goodsCurrent, waitAmount, 3),planAmount,3),goodsLog.getDeliverNum(),3);
			}else{ 
				leftAmount=Common.sub(Common.sub(goodsCurrent, waitAmount, 3),goodsLog.getDeliverNum(),3);
			}
			if(leftAmount>=0){
				serial=(String)invoiceDao.insertDeliverGoods(goodsLog).get("serial");
				LOG.debug("serial:---------"+serial);
				goodsLog.setSerial(serial);
				if(i==0)
					mSerial=serial;
			}else{
				mSerial=null;
				break;
			}
		}
		if(mSerial==null)
			return null;
		//油品流量计拼单处理 eg:一个仓位由2个以上的发货开票组成 流量计传送一个拼单和的数量。
		if(s.getGoodsLogList()!=null&&s.getIsOils()==1&&s.getGoodsLogList().get(0).getDeliverType()==1) 
			addFlowMeter(s.getProductCode(), s.getGoodsLogList(),msg);
		return mSerial;
	}
	
	/**添加发货开票信息
	 * @Title add
	 * @Descrption:TODO
	 * @param:@param s
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:53:57
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODSLOG,type=C.LOG_TYPE.CREATE)
	public OaMsg add(GoodsLogDto s) throws OAException {
		OaMsg oaMsg = new OaMsg(); 
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			int id = 0;
			String mserial =null;
			Boolean isCanMakeInvoice=true;
			if (s.getDeliverType() == 1){
				
				int vehicleShipId = s.getVehicleShipId();
				
				//校验是否存在该车
				if (vehicleShipId == 0){
					 Map<String, Object>  m = truckDao.getTruckByCode(s.getVehiclePlate());
					 
					if (m!=null&&m.get("id")!=null&&Integer.valueOf(m.get("id").toString())>0) 
						vehicleShipId = (int) m.get("id");
					 else
						vehicleShipId = truckDao.addTruck(new Truck(s.getVehiclePlate()));
					s.setVehicleShipId(vehicleShipId);
				}
				
				// 根据车牌号id,找到还没有出库的车发出库记录train表如果不存在就添加新的
				Map<String, Object> trainMap = vehicleDeliverWorkDao.getTrainIdByPlateId(vehicleShipId);
				if (trainMap != null && trainMap.get("id") != null && Integer.valueOf(trainMap.get("id").toString())!=0) {
					id = Integer.valueOf(trainMap.get("id").toString());
				} else {
					User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
					Train t = new Train();
					t.setClientId(s.getClientId());
					t.setProductId(s.getProductId());
					t.setPlateId(vehicleShipId);
					t.setOperator(user.getId());
					t.setTip(s.getTip());
					t.setStatus("40");
					id = vehicleDeliverWorkDao.saveTrain(t);
				}
				
			} else if (s.getDeliverType() == 2){ // 船发
				if (s.getGoodsLogList().get(0).getBatchId() != 0) {
					id = s.getGoodsLogList().get(0).getBatchId();
				} else {
					isCanMakeInvoice=false;
					oaMsg.setMsg("发货开票没有关联出港计划");
				}
			} else if (s.getDeliverType() == 3) {
				if (s.getGoodsLogList().get(0).getBatchId() != 0) {
					id = s.getGoodsLogList().get(0).getBatchId();
				} else {
					isCanMakeInvoice=false;
					oaMsg.setMsg("发货开票没有关联转输计划");
				}
			} else {
				isCanMakeInvoice=false;
				oaMsg.setMsg("发货类型不能为空");
			}
			if(isCanMakeInvoice){
				mserial = addGoodsLog(s, id, oaMsg);
				if(mserial==null)
					oaMsg.setMsg("开票量不符合实际情况，请刷新后重新开票。");
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("serial", mserial);
			oaMsg.setMap(map);
		} catch (Exception re) {
			LOG.error("添加发货开票失败", re);
		}
		return oaMsg;
	}
	/**校验是否存在联单可能
	 * @Title checkIsKupono
	 * @Descrption:TODO
	 * @param:@param glDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:57:07
	 * @throws
	 */
	@Override
	public OaMsg checkIsKupono(GoodsLogDto glDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, Object> map =invoiceDao.checkIsKupono(glDto);
			map.putAll(vehicleDeliverWorkDao.getSerial(glDto.getSerial()));
			map.put("isHasOilMeasure",1);
			if(glDto.getDeliverType()==1&&glDto.getIsOils()==1){//车发油品
				String[] tankNames=glDto.getTankNames().split(",");
				for(int i=0;i<tankNames.length;i++){
					if(!"A".equals(tankNames[i])){
						if(tankMeasureDao.checkInvoiceTankName(tankNames[i])==0){
							map.put("isHasOilMeasure",0);
							break;
						};
					}
				}
			}
			oaMsg.getData().add(map);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * 拆分发货记录
	 * @Title splitDeliverGoods
	 * @Descrption:TODO
	 * @param:@param glDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:57:34
	 * @throws
	 */
	@Override
	public OaMsg splitDeliverGoods(GoodsLogDto glDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
          int result= 	invoiceDao.splitDeliverGoods(glDto);		
			if(result==1)
			{
				oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			}else{
				oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
				oaMsg.setMsg("拆分失败");
			}
		} catch (Exception re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * 获取打印信息
	 * @Title getPrintList
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月12日下午3:57:50
	 * @throws
	 */
	@Override
	public OaMsg getPrintList(GoodsLogDto invoiceDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		    oaMsg.getData().addAll(invoiceDao.getPrintList(invoiceDto));
		} catch (Exception re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg invoiceQuery(GoodsLogDto invoiceDto,PageView pageView) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		    oaMsg.getData().addAll(invoiceDao.invoiceQuery(invoiceDto,pageView.getStartRecord(),pageView.getMaxresult()));
		    oaMsg.getMap().put(Constant.TOTALRECORD, invoiceDao.invoiceQueryCount(invoiceDto)+"");
		} catch (Exception re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getTotalNum(GoodsLogDto invoiceDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		    oaMsg.getData().add(invoiceDao.getTotalNum(invoiceDto));
		} catch (Exception re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	//添加油品添加到流量计
	public void addFlowMeter(String productCode,List<GoodsLog> goodsLogList,OaMsg msg)throws OAException{
		try {
			JSONArray array = new JSONArray();
			GoodsLog goodsLog=new GoodsLog();
			JSONObject obj;
			int len=goodsLogList.size();
			if(len==1){
				goodsLog=goodsLogList.get(0);
				obj = new JSONObject();
				obj.put("notify",goodsLog.getSerial());//通知单号
				obj.put("hpz", productCode);//货品
				obj.put("ydb", goodsLog.getDeliverNum());//调拨量(开票数量)
				obj.put("m_num",goodsLog.getDeliverNum());//计划发送量
				obj.put("tankNo",getTankKey(tankDao.getTankByCode(goodsLog.getTankName())));////罐号
				obj.put("fInTime",DateUtils.getStringTime(goodsLog.getCreateTime()));//进库时间
				array.add(obj);
			}else{//多个单子时，同仓位只开一单，把同仓位的开票数累加发送流量计
				Map<String, GoodsLog> gLogMap=new HashMap<String, GoodsLog>();//整合后的goodsLog
				List<String> storageList=new ArrayList<String>();//仓位
				for(GoodsLog gLog:goodsLogList){
					if(storageList.contains(gLog.getStorageInfo())){
						gLogMap.get(gLog.getStorageInfo()).setDeliverNum(Common.add(gLogMap.get(gLog.getStorageInfo()).getDeliverNum(),gLog.getDeliverNum(), 3));
					}else{
						storageList.add(gLog.getStorageInfo());
						gLogMap.put(gLog.getStorageInfo(), gLog);
					}
				}
				for(int i=0,lenA=storageList.size();i<lenA;i++){
					obj = new JSONObject();
					obj.put("notify",gLogMap.get(storageList.get(i)).getSerial() );//通知单号
					obj.put("hpz", productCode);//货品
					obj.put("ydb", gLogMap.get(storageList.get(i)).getDeliverNum());//调拨量(开票数量)
					obj.put("m_num",gLogMap.get(storageList.get(i)).getDeliverNum());//计划发送量
					obj.put("tankNo",getTankKey(tankDao.getTankByCode(gLogMap.get(storageList.get(i)).getTankName())));////罐号
					obj.put("fInTime",DateUtils.getStringTime(gLogMap.get(storageList.get(i)).getCreateTime()));//进库时间
					array.add(obj);
				}
			}
			LOG.debug("weights:"+array.toJSONString());
			JSONObject	objData = new JSONObject();
			objData.put("weights", array);
			LOG.debug("weightsUrl:"+Global.cloudConfig.get("flowmeter.url").toString()+"create");
			JSONObject json = HttpRequestUtils.httpPost(Global.cloudConfig.get("flowmeter.url").toString()+"create","weights="+objData.toJSONString());
			handleJson(json,msg);
		} catch (Exception re) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
		}
	}

	public  String  getTankKey(Map<String, Object> map){
		if(map!=null&&map.get("key")!=null){
			return map.get("key").toString();
		}else{
			return "";
		}
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
		LOG.debug(json);
         if(json!=null&&json.get("code")!=null){
        	 if("0000".equals(json.get("code"))){
        		 oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
        	 }else{
        		 oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
        		 oaMsg.setMsg("同步流量计失败，请手动同步。");
        	 }
         }else{
        	 oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
        	 oaMsg.setMsg("同步流量计失败，请联系管理员。");
         }

	}

	/**
	 * @Title getFlowMeterBySerial
	 * @Descrption:TODO
	 * @param:@param serial
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月25日上午9:44:26
	 * @throws
	 */
	@Override
	public OaMsg getFlowMeterBySerial(String serial) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		    oaMsg.getData().add(invoiceDao.getFlowMeterBySerial(serial));
		} catch (Exception re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * @Title searchLading
	 * @Descrption:TODO
	 * @param:@param invoiceDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月13日上午9:37:31
	 * @throws
	 */
	@Override
	public OaMsg searchLading(GoodsLogDto invoiceDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
		  List<Map<String,Object>> data=	invoiceDao.searchLading(invoiceDto);
		    if(data!=null&&data.size()>0){
                  for(int i=0;i<data.size();i++){
                	  if(data.get(i).get("id")!=null && Integer.valueOf(data.get(i).get("id").toString())!=0){
                		  data.get(i).put("waitAmount",invoiceDao.queryWaitAmount(Integer.valueOf(data.get(i).get("id").toString())).get("waitAmount") );
                		  data.get(i).put("planAmount", shipArrivalDao.getArrivalPlanAmount(Integer.valueOf(data.get(i).get("id").toString())).get("amount"));
                	  }
                  }		    	
		    }
			oaMsg.getData().addAll(data) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("插入失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * @Title exportInbound
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年8月29日上午9:39:02
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportInbound(final HttpServletRequest request) throws OAException {
		 return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/deleteInvoice.xls", new CallBack() {
			
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				try {
				GoodsLogDto gLogDto=new GoodsLogDto();
				gLogDto.setType(Integer.valueOf(request.getParameter("type")));
				if(request.getParameter("vsName")!=null)
				  gLogDto.setVsName(request.getParameter("vsName").toString());
				if(request.getParameter("productId")!=null)
					gLogDto.setProductId(Integer.valueOf(request.getParameter("productId")));
				if(request.getParameter("clientId")!=null)
					gLogDto.setClientId(Integer.valueOf(request.getParameter("clientId")));
				if(request.getParameter("deliverType")!=null)
					gLogDto.setDeliverType(Integer.valueOf(request.getParameter("deliverType")));
				if(request.getParameter("cancelType")!=null)
					gLogDto.setCancelType(Integer.valueOf(request.getParameter("cancelType")));
				if(request.getParameter("actualType")!=null)
					gLogDto.setActualType(Integer.valueOf(request.getParameter("actualType")));
				if(request.getParameter("ladingEvidence")!=null)
					gLogDto.setLadingEvidence(request.getParameter("ladingEvidence"));
				if(request.getParameter("serial")!=null)
					gLogDto.setSerial(request.getParameter("serial"));
				Integer isDeleteInvoice=1;
				if(gLogDto.getType()==5){
					sheet.getRow(0).getCell(0).setCellValue("发货开票列表");
					sheet.getRow(1).getCell(15).setCellValue("备注");
					isDeleteInvoice=0;
				}
				
				
				
				 List<Map<String, Object>> data = invoiceDao.getInvoiceList(gLogDto, new PageView(0, 0));
					Integer itemRowNo=2;
					HSSFRow itemRow;
					Map<String, Object> map;
					for(int i=0,len=data.size();i<len;i++){
						itemRow=sheet.createRow(itemRowNo++);
						itemRow.setHeight(sheet.getRow(1).getHeight());
					for(int j=0;j<16;j++){
						itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
					}	
					map=data.get(i);
						itemRow.getCell(0).setCellValue(i+1);
						itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("serial")));
						itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("clientCode")));
						if(Integer.valueOf(map.get("deliverType").toString())==1)
							itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("vsName")));
						else
							itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("vsName"))+"/"+FormatUtils.getStringValue(map.get("shipArrivalTime")));
						itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("invoiceTime")));
						itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(map.get("code")));
						itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(map.get("productName")));
						itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(map.get("clientName")));
						itemRow.getCell(8).setCellValue(FormatUtils.getStringValue(map.get("ladingClientName")));
						itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(map.get("yuanhao")));
						itemRow.getCell(10).setCellValue(FormatUtils.getStringValue(map.get("diaohao")));
						itemRow.getCell(11).setCellValue(FormatUtils.getStringValue(map.get("ladingEvidence")));
						itemRow.getCell(12).setCellValue(FormatUtils.getDoubleValue(map.get("deliverNum")));
						itemRow.getCell(13).setCellValue(FormatUtils.getStringValue(map.get("createUserName")));
						itemRow.getCell(14).setCellValue(FormatUtils.getDoubleValue(map.get("actualNum")));
						if(Integer.valueOf(map.get("actualType").toString())==1&&isDeleteInvoice==1){
						itemRow.getCell(15).setCellValue(FormatUtils.getStringValue(map.get("deliverNo")));
						}else if(isDeleteInvoice==0){
							itemRow.getCell(15).setCellValue(FormatUtils.getStringValue(map.get("remark")));
						}
					}
					
				} catch (OAException e) {
					e.printStackTrace();
				}
				
			}
		}).getWorkbook();
	}
}
