package com.skycloud.oa.inbound.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalDao;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.DutyRecordDao;
import com.skycloud.oa.inbound.dao.DutySysDao;
import com.skycloud.oa.inbound.dao.DutyWeatherDao;
import com.skycloud.oa.inbound.dao.TransDao;
import com.skycloud.oa.inbound.dao.WorkCheckDao;
import com.skycloud.oa.inbound.dao.WorkDao;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.DutyRecord;
import com.skycloud.oa.inbound.model.DutySys;
import com.skycloud.oa.inbound.model.DutyWeather;
import com.skycloud.oa.inbound.service.OperationLogService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class OperationLogServiceImpl implements OperationLogService {
	private static Logger LOG = Logger.getLogger(OperationLogServiceImpl.class);
	@Autowired
	private WorkDao workDao;
	@Autowired
	private WorkCheckDao workCheckDao;
	@Autowired
	private TransDao transDao;
	@Autowired
	private CargoGoodsDao cargoGoodsDao;
	@Autowired
	private DutyRecordDao dutyRecordDao;
	@Autowired
	private ArrivalDao arrivalDao;
	@Autowired
	private DutyWeatherDao dutyWeatherDao;
	@Autowired
	private DutySysDao dutySysDao;
	@Override
	public OaMsg getOperationLogList(long startTime, long endTime, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		List<String> productNameList=new ArrayList<String>();
		List<String> shipNameList=new ArrayList<String>();
		List<String> ids=new ArrayList<String>();
		
		try {
			LOG.debug("开始查询");
			Map<String,Map<String,Object>> productMap=new HashMap<String,Map<String,Object>>();
			List<Map<String,Object>> data=workDao.getOPLogList(0,startTime, endTime,pageView.getStartRecord(), pageView.getMaxresult());
//			List<Map<String,Object>> data=workDao.getWorkList(0,startTime, endTime);
			List<Map<String,Object>> result= new ArrayList<Map<String,Object>>();
			for(int i=0;i<data.size();i++){
				boolean iscontains=ids.contains(data.get(i).get("productId").toString()+data.get(i).get("shipId").toString()+data.get(i).get("arrivalId").toString());
				if(iscontains){
					Map<String,Object> hResult=productMap.get(data.get(i).get("productId").toString()+data.get(i).get("shipId").toString()+data.get(i).get("arrivalId").toString());
					List<String> tankCode=(List<String>) hResult.get("tankCode");
					if(!Common.empty(data.get(i).get("tankCode")))
					tankCode.add(data.get(i).get("tankCode").toString());
					
					List<String> tankId=(List<String>) hResult.get("tankId");
					if(!Common.empty(data.get(i).get("tankId")))
					tankId.add(data.get(i).get("tankId").toString());
					
					List<String> startLevel=(List<String>) hResult.get("startLevel");
					if(!Common.empty(data.get(i).get("startLevel"))){
						startLevel.add(data.get(i).get("startLevel").toString());
					}else{
						startLevel.add("&nbsp;");
					}
					List<String> endLevel=(List<String>) hResult.get("endLevel");
					if(!Common.empty(data.get(i).get("endLevel"))){
						endLevel.add(data.get(i).get("endLevel").toString());
					}else{
						endLevel.add("&nbsp;");
					}
					List<String> startWeight=(List<String>) hResult.get("startWeight");
					if(!Common.empty(data.get(i).get("startWeight"))){
						startWeight.add(data.get(i).get("startWeight").toString());
					}else{
						startWeight.add("&nbsp;");
					}
					List<String> endWeight=(List<String>) hResult.get("endWeight");
					if(!Common.empty(data.get(i).get("endWeight"))){
						endWeight.add(data.get(i).get("endWeight").toString());
					}else{
						endWeight.add("&nbsp;");
					}
					List<String> startTemperature=(List<String>) hResult.get("startTemperature");
					if(!Common.empty(data.get(i).get("startTemperature"))){
						startTemperature.add(data.get(i).get("startTemperature").toString());
					}else{
						startTemperature.add("&nbsp;");
					}
					List<String> endTemperature=(List<String>) hResult.get("endTemperature");
					if(!Common.empty(data.get(i).get("endTemperature"))){
						endTemperature.add(data.get(i).get("endTemperature").toString());
					}else{
						endTemperature.add("&nbsp;");
					}
					List<String> realAmount=(List<String>) hResult.get("realAmount");
					if(!Common.empty(data.get(i).get("realAmount"))){
						realAmount.add(data.get(i).get("realAmount").toString());
					}else{
						realAmount.add("&nbsp;");
					}
					List<String> measureAmount=(List<String>) hResult.get("measureAmount");
					if(!Common.empty(data.get(i).get("measureAmount"))){
						measureAmount.add(data.get(i).get("measureAmount").toString());	
					}else{
						measureAmount.add("&nbsp;");
					}
					List<String> differAmount=(List<String>) hResult.get("differAmount");
					if(!Common.empty(data.get(i).get("differAmount"))){
						differAmount.add(data.get(i).get("differAmount").toString());
					}else{
						differAmount.add("&nbsp;");
					}
				}else{
					Map<String,Object> newResult=new HashMap<String, Object>();
					if(!Common.empty(data.get(i).get("workId")))
						newResult.put("workId", data.get(i).get("workId"));
					if(!Common.empty(data.get(i).get("dispatchId")))
						newResult.put("dispatchId", data.get(i).get("dispatchId"));
					if(!Common.empty(data.get(i).get("shipName"))&&!Common.empty(data.get(i).get("shipCode"))){
						
						newResult.put("shipName", data.get(i).get("shipCode").toString()+"/"+data.get(i).get("shipName").toString());
					}else if (!Common.empty(data.get(i).get("shipName"))){
						newResult.put("shipName", data.get(i).get("shipName").toString());
						
					}
					if(!Common.empty(data.get(i).get("productName")))
					newResult.put("productName", data.get(i).get("productName"));
					if(!Common.empty(data.get(i).get("productId")))
						newResult.put("productId", data.get(i).get("productId"));
					if(!Common.empty(data.get(i).get("tanAmount")))
					newResult.put("tanAmount", data.get(i).get("tanAmount"));
					if(!Common.empty(data.get(i).get("berthName")))
					newResult.put("berthName", data.get(i).get("berthName"));
					if(!Common.empty(data.get(i).get("arrivalTime")))
					newResult.put("arrivalTime", data.get(i).get("arrivalTime"));
					if(!Common.empty(data.get(i).get("mArrivalTime")))
						newResult.put("mArrivalTime", data.get(i).get("mArrivalTime"));
					if(!Common.empty(data.get(i).get("openPump")))
					newResult.put("openPump", data.get(i).get("openPump"));
					if(!Common.empty(data.get(i).get("stopPump")))
					newResult.put("stopPump", data.get(i).get("stopPump"));
					if(!Common.empty(data.get(i).get("mOpenPump")))
						newResult.put("mOpenPump", data.get(i).get("mOpenPump"));
						if(!Common.empty(data.get(i).get("mStopPump")))
						newResult.put("mStopPump", data.get(i).get("mStopPump"));
					
					if(!Common.empty(data.get(i).get("openPumpTime")))
					newResult.put("openPumpTime", data.get(i).get("openPumpTime"));
					if(!Common.empty(data.get(i).get("stopPumpTime")))
					newResult.put("stopPumpTime", data.get(i).get("stopPumpTime"));
					
					if(!Common.empty(data.get(i).get("tearPipeTime")))
					newResult.put("tearPipeTime", data.get(i).get("tearPipeTime"));
					if(!Common.empty(data.get(i).get("leaveTime")))
					newResult.put("leaveTime", data.get(i).get("leaveTime"));
					if(!Common.empty(data.get(i).get("mTearPipeTime")))
						newResult.put("mTearPipeTime", data.get(i).get("mTearPipeTime"));
						if(!Common.empty(data.get(i).get("mLeaveTime")))
						newResult.put("mLeaveTime", data.get(i).get("mLeaveTime"));
					if(!Common.empty(data.get(i).get("evaluate")))
					newResult.put("evaluate", data.get(i).get("evaluate"));
					if(!Common.empty(data.get(i).get("evaluateUserName")))
					newResult.put("evaluateUserName", data.get(i).get("evaluateUserName"));
					if(!Common.empty(data.get(i).get("evaluateUserId")))
						newResult.put("evaluateUserId", data.get(i).get("evaluateUserId"));
					if(!Common.empty(data.get(i).get("description")))
					newResult.put("description", data.get(i).get("description"));
//					if(!Common.empty(data.get(i).get("notification")))
//					newResult.put("notification",data.get(i).get("notification"));
//					if(!Common.empty(data.get(i).get("notificationTime")))
//						newResult.put("notificationTime",data.get(i).get("notificationTime"));
//					if(!Common.empty(data.get(i).get("notificationNum")))
//						newResult.put("notificationNum",data.get(i).get("notificationNum"));
//					if(!Common.empty(data.get(i).get("notificationUserName")))
//						newResult.put("notificationUserName",data.get(i).get("notificationUserName"));
//					if(!Common.empty(data.get(i).get("notificationUserId")))
//						newResult.put("notificationUserId",data.get(i).get("notificationUserId"));
					
					if(!Common.empty(data.get(i).get("arrivalId")))
					newResult.put("arrivalId", data.get(i).get("arrivalId"));
					if(!Common.empty(data.get(i).get("transportId")))
					newResult.put("transportId", data.get(i).get("transportId"));
					
					
					List<String> tankCode=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("tankCode")))
					tankCode.add(data.get(i).get("tankCode").toString());
					newResult.put("tankCode", tankCode);
					
					
					List<String> tankId=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("tankId")))
						tankId.add(data.get(i).get("tankId").toString());
					newResult.put("tankId", tankId);
					
					
					List<String> startLevel=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("startLevel"))){
						startLevel.add(data.get(i).get("startLevel").toString());
					}else{
						startLevel.add("&nbsp;");
					}
					newResult.put("startLevel", startLevel);
					
					List<String> endLevel=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("endLevel"))){
						endLevel.add(data.get(i).get("endLevel").toString());
					}else{
						endLevel.add("&nbsp;");
					}
					newResult.put("endLevel", endLevel);
					
					List<String> startWeight=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("startWeight"))){
						startWeight.add(data.get(i).get("startWeight").toString());
					}else{
						startWeight.add("&nbsp;");
					}
					newResult.put("startWeight", startWeight);
					
					List<String> endWeight=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("endWeight"))){
						endWeight.add(data.get(i).get("endWeight").toString());
					}else{
						endWeight.add("&nbsp;");
					}
					newResult.put("endWeight", endWeight);
					
					List<String> startTemperature=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("startTemperature"))){
						startTemperature.add(data.get(i).get("startTemperature").toString());
					}else{
						startTemperature.add("&nbsp;");
					}
					newResult.put("startTemperature", startTemperature);
					
					List<String> endTemperature=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("endTemperature"))){
						endTemperature.add(data.get(i).get("endTemperature").toString());
					}else{
						endTemperature.add("&nbsp;");
					}
					newResult.put("endTemperature", endTemperature);
					
					List<String> realAmount=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("realAmount"))){
						realAmount.add(data.get(i).get("realAmount").toString());
					}else{
						realAmount.add("&nbsp;");
					}
					newResult.put("realAmount", realAmount);
					
					List<String> measureAmount=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("measureAmount"))){
						measureAmount.add(data.get(i).get("measureAmount").toString());
					}else{
						measureAmount.add("&nbsp;");
					}
					newResult.put("measureAmount", measureAmount);
					
					List<String> differAmount=new ArrayList<String>();
					if(!Common.empty(data.get(i).get("differAmount"))){
						differAmount.add(data.get(i).get("differAmount").toString());
					}else{
						differAmount.add("&nbsp;");
					}
					newResult.put("differAmount", differAmount);
					
					productNameList.add(data.get(i).get("productName").toString());
					shipNameList.add(data.get(i).get("shipName").toString());
					productMap.put(data.get(i).get("productId").toString()+data.get(i).get("shipId").toString()+data.get(i).get("arrivalId").toString(), newResult);
					ids.add(data.get(i).get("productId").toString()+data.get(i).get("shipId").toString()+data.get(i).get("arrivalId").toString());
				}
			}
			
			
			for(int j=0;j<ids.size();j++){
				List<String> tubeNameList=new ArrayList<String>();
				List<String> clientNameList=new ArrayList<String>();
				
				
//				List<String> tankAmountList=(List<String>) productMap.get(ids.get(j)).get("realAmount");
//				for(int i=0;i<tankAmountList.size();i++){
//					tankAmount+=Float.parseFloat(tankAmountList.get(i));
//				}
				//验证记录
				List<Map<String,Object>> notifyList=workDao.getNotify(Integer.parseInt(productMap.get(ids.get(j)).get("workId").toString()));
				List<String> notificationNum=new ArrayList<String>();
				List<String> notification=new ArrayList<String>();
				List<String> notificationUserName=new ArrayList<String>();
				List<String> notificationTime=new ArrayList<String>();
				for(int i=0;i<notifyList.size();i++){
					if(!Common.empty(notifyList.get(i).get("notificationNum"))){
						
						notificationNum.add(notifyList.get(i).get("notificationNum").toString());
					}else{
						notificationNum.add("未填写");
					}
					if(!Common.empty(notifyList.get(i).get("notification"))){
											
						notification.add(notifyList.get(i).get("notification").toString());
						}else{
							notification.add("未填写");
						}
					if(!Common.empty(notifyList.get(i).get("notificationUserName"))){
						
						notificationUserName.add(notifyList.get(i).get("notificationUserName").toString());
					}else{
						notificationUserName.add("未填写");
					}
					if(!Common.empty(notifyList.get(i).get("notificationTime"))){
						
						notificationTime.add(notifyList.get(i).get("notificationTime").toString());
					}else{
						notificationTime.add("未填写");
					}
				}
				productMap.get(ids.get(j)).put("notificationNum", notificationNum);
				productMap.get(ids.get(j)).put("notification", notification);
				productMap.get(ids.get(j)).put("notificationUserName", notificationUserName);
				productMap.get(ids.get(j)).put("notificationTime", notificationTime);
				
				//卸货数量
				double tankAmount=0;
				CargoGoodsDto cargoGoodsDto=new CargoGoodsDto();
				cargoGoodsDto.setArrivalId(Integer.parseInt(productMap.get(ids.get(j)).get("arrivalId").toString()));
				cargoGoodsDto.setProductId(Integer.parseInt(productMap.get(ids.get(j)).get("productId").toString()));
				List<Map<String,Object>> cargoList=cargoGoodsDao.getCargoList(cargoGoodsDto, 0, 0);
				for(int i=0;i<cargoList.size();i++){
					tankAmount=Common.fixDouble(tankAmount+Double.parseDouble(cargoList.get(i).get("goodsPlan").toString()), 3);
				}
				productMap.get(ids.get(j)).put("tankAmount", tankAmount);
				
				int transportId=Integer.parseInt(productMap.get(ids.get(j)).get("transportId").toString());
				int arrivalId=Integer.parseInt(productMap.get(ids.get(j)).get("arrivalId").toString());
				int productId=Integer.parseInt(productMap.get(ids.get(j)).get("productId").toString());
				
				//拼管线名称
				
				
//				List<Map<String,Object>> transList= transDao.getTransList(transportId+"");
				
				List<Map<String,Object>> transList= transDao.getTransListByArrivalId(arrivalId,productId);
				
				
				
				String tubeNames="";
				String tubeIds="";
				if(transList!=null){
				for(int i=0;i<transList.size();i++){
					if(i!=transList.size()-1){
						if(transList.get(i).get("tubeName")!=null){
							if(transList.get(i).get("type").toString().equals("0")){
								tubeNames=transList.get(i).get("tubeName").toString()+","+tubeNames;
							}else{
								tubeNames+=transList.get(i).get("tubeName").toString()+",";
							}
					tubeIds+=transList.get(i).get("tubeId").toString()+",";}
					}else{
						if(transList.get(i).get("tubeName")!=null){
							if(transList.get(i).get("type").toString().equals("0")){
								tubeNames=transList.get(i).get("tubeName").toString()+","+tubeNames;
								if(tubeNames.length()>1&&tubeNames.substring(tubeNames.length()-1, tubeNames.length()).equals(",")){
                                   tubeNames=tubeNames.substring(0, tubeNames.length()-1);									
								}
							}else{
								tubeNames+=transList.get(i).get("tubeName").toString();
							}
						tubeIds+=transList.get(i).get("tubeId").toString();
						}
					}
				}}
				
				productMap.get(ids.get(j)).put("tubeName", tubeNames);
				
				
				
//				List<Map<String,Object>> trans=transDao.getTransList(transportId+"");
//				String tubeName="";
//				Set set =new HashSet<String>();
//				for(int k=0;k<trans.size();k++){
//					if(!tubeNameList.contains(trans.get(k).get("tubeName").toString())){
//						List<String> tankCodeList=(List<String>) productMap.get(ids.get(j)).get("tankCode");
//						
//						if(trans.get(k).get("tankCode")!=null&&tankCodeList.contains(trans.get(k).get("tankCode").toString())){
//						tubeName+=trans.get(k).get("tubeName").toString()+",";
//						tubeNameList.add(trans.get(k).get("tubeName").toString());
////						if(!Common.empty(trans.get(k).get("parentName")))
////						//递归查找该货品下货罐中所有管线
////						tubeName=addTubeName(tubeName,trans.get(k).get("parentName").toString(),trans);
//						}
//					}
//				}
//				if(tubeName.length()>0){
//				tubeName=tubeName.substring(0, tubeName.length()-1);
//				productMap.get(ids.get(j)).put("tubeName", tubeName);
//				}
				
				//拼货主
				CargoGoodsDto cDto=new CargoGoodsDto();
				cDto.setArrivalId(arrivalId);
				List<Map<String,Object>> cargo=cargoGoodsDao.getCargoList(cDto, 0, 0);
				String clientName="";
				for(int l=0;l<cargo.size();l++){
					if(!clientNameList.contains(cargo.get(l).get("clientName").toString())){
						if(cargo.get(l).get("productName").toString().equals(productMap.get(ids.get(j)).get("productName").toString())){
						clientName+=cargo.get(l).get("clientName").toString()+",";
						clientNameList.add(cargo.get(l).get("clientName").toString());
						}
					}
				}
				if(clientName.length()>0){
				clientName=clientName.substring(0, clientName.length()-1);
				productMap.get(ids.get(j)).put("clientName", clientName);
				}
				
				result.add(productMap.get(ids.get(j)));
			}
			
			int count=workDao.getOPLogListCount(0,startTime, endTime);
			if(pageView.getMaxresult()!=0){
				Map<String, String> map = new HashMap<String, String>();
				map.put("totalRecord", count + "");
				oaMsg.setMap(map);
			}
			oaMsg.getData().addAll(result);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}
	private String addTubeName(String tubeName, String parentName, List<Map<String, Object>> trans) {
		for(int i=0;i<trans.size();i++){
			if(trans.get(i).get("tubeName").toString().equals(parentName)){
				tubeName+=trans.get(i).get("tubeName").toString()+",";
				if(!Common.empty(trans.get(i).get("parentName"))){
					tubeName=addTubeName(tubeName,trans.get(i).get("parentName").toString(),trans);
				}
				break;
			}
		}
		return tubeName;
		
	}
	@Override
	public OaMsg getLogInfo(long startTime, long endTime) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			List<Map<String, Object>> data=workDao.getLogInfo(startTime, endTime);
//			int dispatchId=0;
//			for(int i=0;i<data.size();i++){
//				if(!Common.empty(data.get(i).get("dispatchId"))&&Integer.parseInt(data.get(i).get("dispatchId").toString())!=0){
//					dispatchId=Integer.parseInt(data.get(i).get("dispatchId").toString());
//					break;
//				}
//			}
			
//			for(int i=0;i<data.size();i++){
//				if(Common.empty(data.get(i).get("dispatchId").toString())||Integer.parseInt(data.get(i).get("dispatchId").toString())==0){
//					Arrival arrival=new Arrival();
//					arrival.setId(Integer.parseInt(data.get(i).get("arrivalId").toString()));
//					arrival.setDispatchId(dispatchId);
//					arrivalDao.updateArrival(arrival);
//				}
//			}
			
			oaMsg.getData().addAll(data);
			oaMsg.setMsg("查询成功");
			return oaMsg;
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}
	@Override
	public OaMsg getLogIsHave(long startTime, long endTime) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			oaMsg.getData().addAll(workDao.getLogIsHave(startTime, endTime));
			oaMsg.setMsg("查询成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}
	@Override
	public OaMsg getLogWorkCheck(String transportId,String types) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			oaMsg.getData().addAll(workCheckDao.getWorkCheckList(transportId,types));
			oaMsg.setMsg("查询成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg addDutyRecord(DutyRecord dutyRecord) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			dutyRecordDao.addDutyRecord(dutyRecord);
			oaMsg.setMsg("添加成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("添加失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "添加失败",re);
		}
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg delectDutyRecord(String id) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			dutyRecordDao.deleteDutyRecord(id);
			oaMsg.setMsg("删除成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("删除失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "删除失败",re);
		}
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateDutyRecord(DutyRecord dutyRecord) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			dutyRecordDao.updateDutyRecord(dutyRecord);
			oaMsg.setMsg("更新成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("更新失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",re);
		}
	}
	@Override
//	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg getDutyRecord(String dispatchId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			oaMsg.getData().addAll(dutyRecordDao.getDutyRecord(dispatchId));
			oaMsg.setMsg("查询成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}

	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOGNOTIY,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteNotify(String id) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
				workDao.deleteNotify(id);
		}catch(RuntimeException e){
			LOG.debug("删除失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"删除失败",e);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getNotify(int workId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(workDao.getNotify(workId));
		}catch(RuntimeException e){
			LOG.debug("查询失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"查询失败",e);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOGWEATHER,type=C.LOG_TYPE.CREATE)
	public OaMsg addDutyWeather(DutyWeather dutyWeather) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			dutyWeatherDao.addDutyWeather(dutyWeather);
			oaMsg.setMsg("添加成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("添加失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "添加失败",re);
		}
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OPERATIONLOGWEATHER,type=C.LOG_TYPE.DELETE)
	public Object delectDutyWeather(String id) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			dutyWeatherDao.deleteDutyWeather(id);
			oaMsg.setMsg("删除成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("删除失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "删除失败",re);
		}
	}
	@Override
	public Object getDutyWeather(String dispatchId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			oaMsg.getData().addAll(dutyWeatherDao.getDutyWeather(dispatchId));
			oaMsg.setMsg("查询成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}
	@Override
	public Object getdutySys(String dispatchId,int type) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			oaMsg.getData().addAll(dutySysDao.getDutySys(dispatchId,type));
			oaMsg.setMsg("查询成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
	}
	@Override
	public Object updatedutySys(DutySys dutySys) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			dutySysDao.updateDutySys(dutySys);
			oaMsg.setMsg("更新成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("更新失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",re);
		}
	}

	
}
