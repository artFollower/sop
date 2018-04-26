package com.skycloud.oa.inbound.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;
import org.apache.shiro.SecurityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.common.dto.SecurityCodeDto;
import com.skycloud.oa.common.handler.SecurityCodeHandler;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalDao;
import com.skycloud.oa.inbound.dao.ArrivalForeshowDao;
import com.skycloud.oa.inbound.dao.ArrivalInfoDao;
import com.skycloud.oa.inbound.dao.BerthAssessDao;
import com.skycloud.oa.inbound.dao.BerthProgramDao;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.InboundArrivalDao;
import com.skycloud.oa.inbound.dao.JobCheckDao;
import com.skycloud.oa.inbound.dao.NotifyDao;
import com.skycloud.oa.inbound.dao.StoreDao;
import com.skycloud.oa.inbound.dao.TransDao;
import com.skycloud.oa.inbound.dao.TransportInfoDao;
import com.skycloud.oa.inbound.dao.TransportProgramDao;
import com.skycloud.oa.inbound.dao.WorkCheckDao;
import com.skycloud.oa.inbound.dao.WorkDao;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.inbound.dto.NotifyDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalForeshow;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.JobCheck;
import com.skycloud.oa.inbound.model.Notification;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.Trans;
import com.skycloud.oa.inbound.model.TransportInfo;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.Work;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.inbound.service.InboundOperationService;
import com.skycloud.oa.message.dao.MessageDao;
import com.skycloud.oa.message.dto.MessageDto;
import com.skycloud.oa.message.model.MessageContent;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.system.dao.ApproveCacheDao;
import com.skycloud.oa.system.dao.ApproveContentDao;
import com.skycloud.oa.system.dao.ShipRefDao;
import com.skycloud.oa.system.model.ApproveCache;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.system.model.OperateLog;
import com.skycloud.oa.system.model.ShipRef;
import com.skycloud.oa.system.service.OperateLogService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;
@Service
public class InboundOperationServiceImpl implements InboundOperationService {
	private static Logger LOG = Logger.getLogger(InboundOperationServiceImpl.class);
	@Autowired
	private InboundArrivalDao inboundArrivalDao;//到港信息
	@Autowired
	private ArrivalInfoDao arrivalInfoDao;//入库计划
	@Autowired
	private BerthAssessDao berthAssessDao;//靠泊评估
	@Autowired
	private BerthProgramDao berthProgramDao;//靠泊方案
	@Autowired
	private ArrivalDao arrivalDao;//到港信息
	@Autowired
	private TransportProgramDao transportProgramDao;//接卸方案
	@Autowired
	private TransDao transDao;//传输方案
	@Autowired
	private StoreDao storeDao;//存储罐
	@Autowired
	private WorkCheckDao workCheckDao;//作业检查
	@Autowired
	private JobCheckDao jobCheckDao;//岗位检查表
	@Autowired
	private WorkDao workDao;//入库作业表
	@Autowired
	private CargoGoodsDao cargoGoodsDao;
	@Autowired
	private GoodsLogDao goodsLogDao;
	@Autowired
	private TransportInfoDao transportInfoDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private OperateLogService operateLogService;
	@Autowired
	private ApproveContentDao approveContentDao;
	@Autowired
	private ApproveCacheDao approveCacheDao;
	@Autowired
	private NotifyDao notifyDao;
	@Autowired ShipRefDao shipRefDao;
	
	
	@Autowired
	private ArrivalForeshowDao arrivalForeshowDao;
	
	
	@Override
	public OaMsg getInboundOperationList(InboundOperationDto ioDto,PageView pageView)throws OAException {
		OaMsg oaMsg=new OaMsg();
		List<Map<String,Object>> hdata=new ArrayList<Map<String,Object>>();
		List<String> lsids = new ArrayList<String>();
		Map<String, Map<String, Object>> arrivalData = new HashMap<String, Map<String, Object>>();
		
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(ioDto.getResult()<7){
				List<Map<String,Object>> data=null;
				if(ioDto.getResult()!=0){
			 data=inboundArrivalDao.getInboundArrivalList(ioDto,pageView.getStartRecord(),pageView.getMaxresult());
				}else{
				data=inboundArrivalDao.getInboundArrivalList(ioDto,pageView.getStartRecord(),pageView.getMaxresult());	
				}
			//到港信息表数据
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			switch (ioDto.getResult()) {
			case 0:
				//处理入库列表
				for(int i=0;i<data.size();i++){
					Map<String,Object> map=data.get(i);
					String arrivalId=  map.get("id").toString();
					String productId= map.get("productId").toString();
					if(Integer.valueOf(map.get("workId").toString())!=0){
					  if(lsids.contains(arrivalId)){
						 List<Map<String,Object>> goodsmsg= (List<Map<String, Object>>) arrivalData.get(arrivalId).get("goodsMsg");
						 Map<String,Object> itemgoodsmsg=new HashMap<String, Object>();
						  itemgoodsmsg.put("productId", productId);
						  itemgoodsmsg.put("productName",Common.empty(map.get("productName"))?"":map.get("productName").toString());
						  itemgoodsmsg.put("goodsPlan",Common.empty(map.get("goodsPlan"))?"":map.get("goodsPlan").toString());
						  itemgoodsmsg.put("clientCode",Common.empty(map.get("clientCode"))?"":map.get("clientCode").toString());
						  itemgoodsmsg.put("clientId", Common.empty(map.get("clientId"))?"":map.get("clientId").toString());
						  itemgoodsmsg.put("clientName",Common.empty(map.get("clientName"))?"":map.get("clientName").toString());
						  itemgoodsmsg.put("cargoId",Common.empty(map.get("cargoId"))?"":map.get("cargoId").toString());
						  itemgoodsmsg.put("cargoAgentId",Common.empty(map.get("cargoAgentId"))?"":map.get("cargoAgentId").toString());
						  itemgoodsmsg.put("cargoAgentCode",Common.empty(map.get("cargoAgentCode"))?"":map.get("cargoAgentCode").toString());
						  itemgoodsmsg.put("cargoCode",Common.empty(map.get("cargoCode"))?"":map.get("cargoCode").toString());
						  itemgoodsmsg.put("description",Common.empty(map.get("description"))?"":map.get("description").toString());
						  itemgoodsmsg.put("contractId",Common.empty(map.get("contractId"))?"":map.get("contractId").toString());
						  itemgoodsmsg.put("transportprogramId",Common.empty(map.get("transportprogramId"))?"":map.get("transportprogramId").toString());
						  itemgoodsmsg.put("ttpmreviewStatus",Common.empty(map.get("ttpmreviewStatus"))?"":map.get("ttpmreviewStatus").toString());
						  itemgoodsmsg.put("orderNum",Common.empty(map.get("orderNum"))?"":map.get("orderNum").toString());
//						  itemgoodsmsg.put("backflowplanId",Common.empty(map.get("backflowplanId"))?"":map.get("backflowplanId").toString());
//						  itemgoodsmsg.put("bfpnreviewStatus",Common.empty(map.get("bfpnreviewStatus"))?"":map.get("bfpnreviewStatus").toString());
						  itemgoodsmsg.put("workId",Common.empty(map.get("workId"))?"":map.get("workId").toString());
						  itemgoodsmsg.put("leaveTime",Common.empty(map.get("workId"))?"":map.get("leaveTime").toString());
						  itemgoodsmsg.put("atamreviewStatus",Common.empty(map.get("atamreviewStatus"))?"":map.get("atamreviewStatus").toString());
						  itemgoodsmsg.put("itemStatusKey",Common.empty(map.get("itemStatusKey"))?"":map.get("itemStatusKey").toString());
						  goodsmsg.add(itemgoodsmsg);
					  }else{
						  lsids.add(arrivalId);
						  Map<String,Object> itemArrival=new HashMap<String, Object>();
						  
						  itemArrival.put("id", Common.empty(map.get("id"))?"":map.get("id").toString());
						  itemArrival.put("shipId",Common.empty(map.get("shipId"))?"":map.get("shipId").toString());
						  itemArrival.put("shipAgentId",Common.empty(map.get("shipAgentId"))?"":map.get("shipAgentId").toString());
						  itemArrival.put("shipAgentCode",Common.empty(map.get("shipAgentCode"))?"":map.get("shipAgentCode").toString());
						  itemArrival.put("shipAgentName",Common.empty(map.get("shipAgentName"))?"":map.get("shipAgentName").toString());
						  itemArrival.put("report",Common.empty(map.get("report"))?"":map.get("report").toString());
						  itemArrival.put("shipCode",Common.empty(map.get("shipCode"))?"":map.get("shipCode").toString());
						  itemArrival.put("shipName",Common.empty(map.get("shipName"))?"":map.get("shipName").toString());
						  itemArrival.put("shipRefName",Common.empty(map.get("shipRefName"))?"":map.get("shipRefName").toString());
						  itemArrival.put("shipWidth",Common.empty(map.get("shipWidth"))?"":map.get("shipWidth").toString());
						  itemArrival.put("shipLenth",Common.empty(map.get("shipLenth"))?"":map.get("shipLenth").toString());
						  itemArrival.put("shipDraught",Common.empty(map.get("shipDraught"))?"":map.get("shipDraught").toString());
						  itemArrival.put("netTons",Common.empty(map.get("netTons"))?"":map.get("netTons").toString());
						  itemArrival.put("loadCapacity",Common.empty(map.get("loadCapacity"))?"":map.get("loadCapacity").toString());
						  itemArrival.put("arrivalStartTime",Common.empty(map.get("arrivalStartTime"))?"":map.get("arrivalStartTime").toString());
						  itemArrival.put("statusKey",Common.empty(map.get("statusKey"))?"":map.get("statusKey").toString());
						  itemArrival.put("statusValue",Common.empty(map.get("statusValue"))?"":map.get("statusValue").toString());
						  itemArrival.put("arrivalType",Common.empty(map.get("arrivalType"))?"":map.get("arrivalType").toString());
						  itemArrival.put("arrivalinfoId",Common.empty(map.get("arrivalinfoId"))?"":map.get("arrivalinfoId").toString());
						  itemArrival.put("anchorTime",Common.empty(map.get("anchorTime"))?"":map.get("anchorTime").toString());
						  itemArrival.put("port",Common.empty(map.get("port"))?"":map.get("port").toString());
						  itemArrival.put("alifreviewStatus",Common.empty(map.get("alifreviewStatus"))?"":map.get("alifreviewStatus").toString());
						  itemArrival.put("shipArrivalDraught",Common.empty(map.get("shipArrivalDraught"))?"":map.get("shipArrivalDraught").toString());
						  
						  itemArrival.put("berthassessId",Common.empty(map.get("berthassessId"))?"":map.get("berthassessId").toString());
						  itemArrival.put("bhasreviewStatus",Common.empty(map.get("bhasreviewStatus"))?"":map.get("bhasreviewStatus").toString());
						  
						  itemArrival.put("berthprogramId",Common.empty(map.get("berthprogramId"))?"":map.get("berthprogramId").toString());
						  itemArrival.put("berthId", Common.empty(map.get("berthId"))?"":map.get("berthId").toString());
						  itemArrival.put("berthName",Common.empty(map.get("berthName"))?"":map.get("berthName").toString());
						  itemArrival.put("bhpnreviewStatus",Common.empty(map.get("bhpnreviewStatus"))?"":map.get("bhpnreviewStatus").toString());
						  //登陆人
						  itemArrival.put("createUserId",user.getId());
						  itemArrival.put("createUserName", user.getName());
						  Map<String,Object> itemgoodsmsg=new HashMap<String, Object>();
						  itemgoodsmsg.put("productId", productId);
						  itemgoodsmsg.put("productName",Common.empty(map.get("productName"))?"":map.get("productName").toString());
						  itemgoodsmsg.put("goodsPlan",Common.empty(map.get("goodsPlan"))?"":map.get("goodsPlan").toString());
						  itemgoodsmsg.put("clientCode",Common.empty(map.get("clientCode"))?"":map.get("clientCode").toString());
						  itemgoodsmsg.put("clientId", Common.empty(map.get("clientId"))?"":map.get("clientId").toString());
						  itemgoodsmsg.put("clientName",Common.empty(map.get("clientName"))?"":map.get("clientName").toString());
						  itemgoodsmsg.put("cargoId",Common.empty(map.get("cargoId"))?"":map.get("cargoId").toString());
						  itemgoodsmsg.put("cargoAgentId",Common.empty(map.get("cargoAgentId"))?"":map.get("cargoAgentId").toString());
						  itemgoodsmsg.put("cargoAgentCode",Common.empty(map.get("cargoAgentCode"))?"":map.get("cargoAgentCode").toString());
						  itemgoodsmsg.put("cargoCode",Common.empty(map.get("cargoCode"))?"":map.get("cargoCode").toString());
						  itemgoodsmsg.put("description",Common.empty(map.get("description"))?"":map.get("description").toString());
						  itemgoodsmsg.put("contractId",Common.empty(map.get("contractId"))?"":map.get("contractId").toString());
						  itemgoodsmsg.put("transportprogramId",Common.empty(map.get("transportprogramId"))?"":map.get("transportprogramId").toString());
						  itemgoodsmsg.put("ttpmreviewStatus",Common.empty(map.get("ttpmreviewStatus"))?"":map.get("ttpmreviewStatus").toString());
						  itemgoodsmsg.put("orderNum",Common.empty(map.get("orderNum"))?"":map.get("orderNum").toString());
//						  itemgoodsmsg.put("backflowplanId",Common.empty(map.get("backflowplanId"))?"":map.get("backflowplanId").toString());
//						  itemgoodsmsg.put("bfpnreviewStatus",Common.empty(map.get("bfpnreviewStatus"))?"":map.get("bfpnreviewStatus").toString());
						  itemgoodsmsg.put("workId",Common.empty(map.get("workId"))?"":map.get("workId").toString());
						  itemgoodsmsg.put("leaveTime",Common.empty(map.get("workId"))?"":map.get("leaveTime").toString());
						  itemgoodsmsg.put("atamreviewStatus",Common.empty(map.get("atamreviewStatus"))?"":map.get("atamreviewStatus").toString());
						  itemgoodsmsg.put("itemStatusKey",Common.empty(map.get("itemStatusKey"))?"":map.get("itemStatusKey").toString());
						  List<Map<String,Object>> goodsMsg=new ArrayList<Map<String,Object>>();
						  goodsMsg.add(itemgoodsmsg);
						  itemArrival.put("goodsMsg", goodsMsg);
						  arrivalData.put(arrivalId, itemArrival);
					  }
					}
				  }
				for(int i=0;i<lsids.size();i++){
					hdata.add(arrivalData.get(lsids.get(i)));
				}
				oaMsg.getMap().put(Constant.TOTALRECORD, inboundArrivalDao.getInboundArrivalList(ioDto) + "");
				break;
			case 1:
				//处理入库计划
				hdata.addAll(data);
				break;
			case 2:
				//处理靠泊评估
				hdata.addAll(data);
				break;
			case 3:
				//处理靠泊方案
				hdata.addAll(data);
			case 4:
				//处理接卸方案
				List<Map<String,Object>> transList= transDao.getTransList(ioDto.getTransportIds());
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
				List<Map<String,Object>> storeList=storeDao.getStoreList(ioDto.getTransportIds());
				String tankNames="";
				String tankIds="";
				if(storeList!=null){
				for(int i=0;i<storeList.size();i++){
					if(i!=storeList.size()-1){
						tankNames+=storeList.get(i).get("tankCode").toString()+",";
						tankIds+=storeList.get(i).get("tankId").toString()+",";
					}else{
						tankNames+=storeList.get(i).get("tankCode").toString();
						tankIds+=storeList.get(i).get("tankId").toString();
					}
				}}
			Map<String,Object> mapa=data.get(0);
			mapa.put("tubeNames", tubeNames);
			mapa.put("tubeIds", tubeIds);
			mapa.put("tankNames", tankNames);
			mapa.put("tankIds",tankIds);
				hdata.addAll(data);
			case 6:
				//处理数量确认储罐列表
				hdata.addAll(data);
				break;
			
			default:
				break;
			}
			}else{
				List<Map<String,Object>> list = null;
				switch (ioDto.getResult()) {
				case 7:
					Map<String,Object> unloadingMap=new HashMap<String, Object>();
					if(ioDto.getUnloadingType()==1){
					unloadingMap.put("unloadingplan", inboundArrivalDao.getInboundArrivalList(ioDto, 0, 0));//多次接卸次数 arrivalId,productId,result=7,unloadingType=1
					}else if(ioDto.getUnloadingType()==2){
					unloadingMap.put("backflowplan", inboundArrivalDao.getInboundArrivalList(ioDto, 0, 0));//打循环次数arrivalId,productId,result=7,unloadingType=2
					}
					list=new ArrayList<Map<String,Object>>();
	                list.add(unloadingMap);				
					break;
				case 11:
					//获取存储罐信息表
				list=storeDao.getStoreList(ioDto.getId()+"");
					break;
				case 12:
					//获取管线信息
					list=transDao.getTransList(ioDto.getId()+"");
						break;
				case 13:
					//获取岗位检查信息
					list=workCheckDao.getWorkCheckList(ioDto.getId().toString(),"1,2");
						break;
				case 14:
					//获取作业检查信息
					list=jobCheckDao.getJobCheckList(ioDto.getId(),"1,2,3,4");
					for(int i=0;i<list.size();i++){
						Map<String,Object> map=list.get(i);
						if(map.get("job").toString().equals("1")){
							map.put("post", "码头");
							map.put("checkContent", "船上卸货品种和岸上所使用的管线中的物料的确认");
						}else if(map.get("job").toString().equals("2")){
							map.put("post", "动力班");
							map.put("checkContent", "呼吸阀 / 人孔及储罐附件 / 管线及附件 / 管线连接情况的核对");
						}else if(map.get("job").toString().equals("3")){
							map.put("post", "计量班");
							map.put("checkContent", "高位报警及储罐联锁");
						}else if(map.get("job").toString().equals("4")){
							map.put("post", "调度及中控室");
							map.put("checkContent", "SCADA系统 / 接卸数量与罐容量核对");
						}
					}
					break;
				case 15:
					//获取接卸过程信息
					if(ioDto.getId()!=null)
					list=workDao.getWorkList(ioDto.getId());
					break;
				case 17:
					//码头接卸通知单
					list=workCheckDao.getWorkCheckList(ioDto.getId().toString(),"4,5,6,7");
					break;
				case 18:
					//配管通知单
					list=workCheckDao.getWorkCheckList(ioDto.getId().toString(),"8,9,10");
					break;
				case 19:
					//动力班通知单
					list=workCheckDao.getWorkCheckList(ioDto.getId().toString(),"11,12,13,14");
					break;
				case 20:
					//打循环码头通知单
					list=workCheckDao.getWorkCheckList(ioDto.getId().toString(),"15,16,17,18");
					break;
				case 21:
					//查询接卸准备的通知列表
					list=workDao.getNotify(ioDto.getWorkId());
					break;
				case 22:
					//打回流通知单
					list=workCheckDao.getWorkCheckList(ioDto.getId().toString(),"19,20,21,22");
					break;
				default:
					break;
				}
				
				hdata.addAll(list);
				
			}
			
			oaMsg.getData().addAll(hdata);
		}catch(RuntimeException e){
			LOG.debug("查询入库计划失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"查询失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ALLITEMS,type=C.LOG_TYPE.CREATE)
	public OaMsg addInboundOperationItem(InboundOperationDto ioDto)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			//入库计划
			ArrivalInfo arrivalInfo=new ArrivalInfo();
			arrivalInfo.setArrivalId(ioDto.getId());
			arrivalInfo.setReport("未申报");
			arrivalInfo.setShipInfo("未收到");
			InboundOperationDto iDto=new InboundOperationDto();
			Calendar a =Calendar.getInstance();
			iDto.setShipId(ioDto.getShipId());
			iDto.setResult(0);
			iDto.setStartTime(a.get(Calendar.YEAR)+"-01-01");
			arrivalInfo.setPortNum((inboundArrivalDao.getInboundArrivalList(iDto)+1)+"");
			arrivalInfoDao.addArrivalInfo(arrivalInfo);
			//靠泊评估
			BerthAssess berthAssess=new BerthAssess();
			berthAssess.setArrivalId(ioDto.getId());
			berthAssess.setReviewStatus(0);
			berthAssessDao.addBerthAssess(berthAssess);
			//靠泊方案
			BerthProgram berthProgram=new BerthProgram();
			berthProgram.setArrivalId(ioDto.getId());
			berthProgramDao.addBerthProgram(berthProgram);
			//接卸方案 打回流方案一起插入
			TransportProgram transportProgram=new TransportProgram();
			transportProgram.setArrivalId(ioDto.getId());
//			transportProgram.setType(0);
			transportProgramDao.addTransportProgram(transportProgram);
			//入库作业
			Work work=new Work();
			work.setArrivalId(ioDto.getId());
			workDao.addWork(work);
		}catch(RuntimeException e){
			LOG.debug("service添加入库管理相关表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service添加入库管理相关表失败",e);
		}
		OperateLog log=new OperateLog();
		log.setType(C.LOG_TYPE.CREATE);
		log.setObject(C.LOG_OBJECT.PCS_INBOUND_ALLITEMS);
		log.setContent("添加入库相关表");
		operateLogService.create(log, oaMsg);
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVAL_INFO,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateArrivalInfo(ArrivalInfo arrivalInfo) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String,Object>> workList=workDao.getWorkList(arrivalInfo.getArrivalId());
			if(workList.size()>0){
				if(!Common.empty(workList.get(0).get("mTearPipeTime"))&&Long.parseLong(workList.get(0).get("mTearPipeTime").toString())!=0&&!Common.empty(arrivalInfo.getNorTime())&&!Common.empty(arrivalInfo.getLastLeaveTime())){
							long time=Long.parseLong(arrivalInfo.getLastLeaveTime().toString());
							long time1=Long.parseLong(workList.get(0).get("mTearPipeTime").toString());
							long scTime=(time1-time)*1000;
							BigDecimal b = new BigDecimal(Float.valueOf(scTime)/3600000);
							float repatriateOverTime = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
							if(repatriateOverTime<0){
								arrivalInfo.setRepatriateTime(-repatriateOverTime);
							}else{
								arrivalInfo.setOverTime(repatriateOverTime);
							}
						}
			}
			if(!Common.isNull(arrivalInfo.getArrivalId())&&arrivalInfo.getAnchorTime()!=null&&arrivalInfo.getAnchorTime()!=-1){
				Work work=new Work();
				work.setArrivalId(arrivalInfo.getArrivalId());
				work.setArrivalTime(arrivalInfo.getAnchorTime());
				workDao.updateWorkArrivalTime(work);
			}
			arrivalInfoDao.updateArrival(arrivalInfo);
		}catch(RuntimeException e){
			LOG.debug("service更新入库计划失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新入库计划失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_BERTH_ASSESS,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateBerthAssess(BerthAssess berthAssess,SecurityCodeDto scDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		OperateLog log=new OperateLog();
		try{
			
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(berthAssess.getReviewStatus()==2||berthAssess.getReviewStatus()==3){
				log.setType(C.LOG_TYPE.VERIFY);
				log.setContent("审核靠泊评估");	
				if(scDto!=null&&scDto.getIsSecurity()==1){
					User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
					if(!SecurityCodeHandler.getInstance().volidateCode((user.getId()+scDto.getObject()), scDto.getSecurityCode(), oaMsg)){
						return oaMsg;
					}
					if(!Common.empty(oaMsg.getMap().get("userId"))){
					berthAssess.setReviewUserId(Integer.valueOf(oaMsg.getMap().get("userId").toString()));
					}else{
						oaMsg.setCode(Constant.SYS_CODE_TIME_OUT);
						oaMsg.setMsg("验证码过期");
						return oaMsg;
					}
				}
			}else{
				log.setType(C.LOG_TYPE.UPDATE);
				log.setContent("更新靠泊评估");	
			}
			berthAssessDao.updateBerthAssess(berthAssess);
			if(berthAssess.getReviewStatus()==2){
			Arrival arrival=new Arrival();
			arrival.setId(berthAssess.getArrivalId());
			arrival.setStatus(4);
			arrivalDao.updateArrival(arrival);
			}
			
		}catch(RuntimeException e){
			LOG.debug("service更新靠泊评估失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新靠泊评估失败",e);
		}
		log.setObject(C.LOG_OBJECT.PCS_INBOUND_BERTH_ASSESS);
		operateLogService.create(log, oaMsg);
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_BERTH_PROGRAM,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateBerthProgram(BerthProgram berthProgram,SecurityCodeDto scDto)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		OperateLog log=new OperateLog();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(berthProgram.getStatus()==2||berthProgram.getStatus()==3){
				log.setType(C.LOG_TYPE.VERIFY);
				log.setContent("审核靠泊方案");
				if(scDto!=null&&scDto.getIsSecurity()==1){
					User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
					if(!SecurityCodeHandler.getInstance().volidateCode((user.getId()+scDto.getObject()), scDto.getSecurityCode(), oaMsg)){
						return oaMsg;
					}
					if(!Common.empty(oaMsg.getMap().get("userId"))){
						berthProgram.setReviewUserId(Integer.valueOf(oaMsg.getMap().get("userId").toString()));
						}else{
							oaMsg.setCode(Constant.SYS_CODE_TIME_OUT);
							oaMsg.setMsg("验证码过期");
							return oaMsg;
						}
				}
			}else{
				log.setType(C.LOG_TYPE.UPDATE);
				log.setContent("更新靠泊方案");
			}
			berthProgramDao.updateBerthProgram(berthProgram);
			if(berthProgram.getStatus()==2){
			Arrival arrival=new Arrival();
			arrival.setId(berthProgram.getArrivalId());
			arrival.setStatus(5);
			arrivalDao.updateArrival(arrival);
			}
			
		}catch(RuntimeException e){
			LOG.debug("service更新靠泊方案失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新靠泊方案失败",e);
		}
		log.setObject(C.LOG_OBJECT.PCS_INBOUND_BERTH_PROGRAM);
		operateLogService.create(log, oaMsg);
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVAL,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateArrival(Arrival arrival,String shipRefName) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			if(null!=shipRefName&&0!=arrival.getShipId()){
				Object id=shipRefDao.getShipRef(arrival.getShipId(), shipRefName).get("id");
				if(null==id||Integer.valueOf(id.toString())==0){
				ShipRef shipRef=new ShipRef();
				shipRef.setRefName(shipRefName);
				shipRef.setShipId(arrival.getShipId());
				arrival.setShipRefId(shipRefDao.addShipRef(shipRef));
				}else{
					arrival.setShipRefId(Integer.valueOf(id.toString()));
				}
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			arrivalDao.updateArrival(arrival);
			if(!Common.isNull(arrival.getId())&&arrival.getArrivalStartTime()!=null&&!Common.isNull(arrival.getArrivalStartTime().toString())){
				Work work =new Work();
				work.setArrivalId(arrival.getId());
				work.setArrivalTime(arrival.getArrivalStartTime().getTime()/1000);
				workDao.updateWorkArrivalTime(work);
			}
		}catch(RuntimeException e){
			LOG.debug("service更新入库列表失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新入库列表失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_TRANSPORT_PROGRAM,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateTransportProgram(TransportProgram transportProgram,String tankIds,SecurityCodeDto scDto,Integer workId)throws OAException {
		OaMsg oaMsg=new OaMsg();
		OperateLog log=new OperateLog();
		
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(transportProgram.getStatus()!=null&&(transportProgram.getStatus()==2||transportProgram.getStatus()==3||transportProgram.getStatus()==4||transportProgram.getStatus()==5)){
				if(scDto!=null&&scDto.getIsSecurity()==1){
					User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
					if(!SecurityCodeHandler.getInstance().volidateCode((user.getId()+scDto.getObject()), scDto.getSecurityCode(), oaMsg)){
						return oaMsg;
					}
					if(!Common.empty(oaMsg.getMap().get("userId"))){
						if(transportProgram.getStatus()==4){
						transportProgram.setReviewUserId(Integer.valueOf(oaMsg.getMap().get("userId")));
						}else if(transportProgram.getStatus()==5){
						transportProgram.setReviewCraftUserId(Integer.valueOf(oaMsg.getMap().get("userId")));
						}else{
						transportProgram.setReviewUserId(Integer.valueOf(oaMsg.getMap().get("userId")));
						transportProgram.setReviewCraftUserId(Integer.valueOf(oaMsg.getMap().get("userId")));
						}
						}else{
							oaMsg.setCode(Constant.SYS_CODE_TIME_OUT);
							oaMsg.setMsg("验证码过期");
							return oaMsg;
						}
				}
				log.setType(C.LOG_TYPE.VERIFY);
				if(transportProgram.getType()!=null&&transportProgram.getType()==0){
				log.setContent("审核接卸方案");
				
				}else if(transportProgram.getType()!=null&&transportProgram.getType()==1){
					log.setContent("审核打循环方案");
				}else{
					log.setContent("审核倒罐方案");
				}
			}else{
				
				log.setType(C.LOG_TYPE.UPDATE);
				if(transportProgram.getType()!=null&&transportProgram.getType()==0){
					log.setContent("更新接卸方案");
				}else if(transportProgram.getType()!=null&&transportProgram.getType()==1){
					log.setContent("更新打循环方案");
				}else{
					log.setContent("更新倒罐方案");
				}
			}
			Map<String, Object> tpMap = transportProgramDao.getMsgById(transportProgram.getId());
			if(!Common.empty(transportProgram.getArrivalId())){
				
				if(tpMap.get("status")!=null &&Integer.valueOf(tpMap.get("status").toString())==2 &&transportProgram.getStatus()!=null && transportProgram.getStatus()<2&& transportProgram.getStatus()!=-1){
					oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
					oaMsg.setMsg("已经审批无法提交。");
					return oaMsg;
				}
			}
			transportProgramDao.updateTransportProgram(transportProgram);
			if(transportProgram.getType()!=null&&transportProgram.getType()==0&&transportProgram.getStatus()!=null){
				handleInboundTransportPlan( transportProgram, tankIds,workId);	
			}else if(transportProgram.getType()!=null&&transportProgram.getType()!=0&&transportProgram.getStatus()!=null&&transportProgram.getStatus()==-1){
				transportProgramDao.rebackTransportProgram(transportProgram.getId());
			}
			
		}catch(Exception e){
			LOG.debug("service更新接卸方案失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新接卸方案失败",e);
		}
		log.setObject(C.LOG_OBJECT.PCS_INBOUND_TRANSPORT_PROGRAM);
		operateLogService.create(log, oaMsg);
		return oaMsg;
	}
	//处理入库作业接卸方案
    public void  handleInboundTransportPlan(TransportProgram tsProgram,String tankIds, Integer workId) throws Exception{
    	//保存和提交的时候添加存储罐和管线状态status==0||1
		if(tsProgram.getStatus()==0||tsProgram.getStatus()==1){
		List<Trans> transList=new ArrayList<Trans>();
		List<Store> storeList=new ArrayList<Store>();
		Integer tpId=tsProgram.getId();
		String flowStr=tsProgram.getFlow();
		String[] tanks=tankIds.split(",");
		for(int i=0;i<tanks.length;i++){
			if(!tanks[i].equals("-1")&&tanks[i]!=""){
			Store store=new Store();
			store.setTankId(Integer.valueOf(tanks[i]));
			store.setTransportId(tpId);
			storeList.add(store);
			}
		}
		if(flowStr!=null&&!flowStr.equals("")){
		List<Trans> translist=analysisXML(flowStr);
		for(int i=0;i<translist.size();i++){
			Trans trans=translist.get(i);
			trans.setTransportId(tpId);
			transList.add(trans);
		}}
		//删除之前的罐管
		storeDao.deleteStoreByTransPortId(tsProgram.getId());
	    transDao.deleteTransByTransPortId(tsProgram.getId());
		
		addStoreList(storeList);//添加存储罐
		addTransList(transList);//添加管线
		
		}else if(tsProgram.getStatus()==2){//审核通过 更新流程状态，创建对应store,trans,管线检查
			
			//岗位检查表添加	
			List<JobCheck> jobCheckList=new ArrayList<JobCheck>();
			int[] jobArray=new int[]{1,2,3,4};//1码头，2动力班3计量班，4，调度及中控室
			for(int i=0;i<4;i++){
				JobCheck jobCheck=new JobCheck();
				jobCheck.setTransportId(tsProgram.getId());
				jobCheck.setJob(jobArray[i]);
				jobCheckList.add(jobCheck);
			}
		   addJobCheckList(jobCheckList);
		   
		   //更新主流程状态
		  Work work =new Work();
		  work.setId(workId);
		  work.setStatus(6);
		   workDao.updateWork(work);
		   //生成岗位检查信息
		   for(int i=0;i<2;i++){
				WorkCheck workCheck=new WorkCheck();
				workCheck.setCheckType(i+1);
				workCheck.setTransportId(tsProgram.getId());
				workCheckDao.addOnlyWorkCheck(workCheck);
			}
		}
    }
	
	
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_TRANS,type=C.LOG_TYPE.CREATE)
	public OaMsg addTransList(List<Trans> transList) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			for(int i=0;i<transList.size();i++){
              				
				transDao.addTrans(transList.get(i));
			}
		}catch(RuntimeException e){
			LOG.debug("service添加传输管线失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service添加传输管线失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_STORE,type=C.LOG_TYPE.CREATE)
	public OaMsg addStoreList(List<Store> storeList) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			for(int i=0;i<storeList.size();i++){
				storeDao.addStore(storeList.get(i));
			}
		}catch(RuntimeException e){
			LOG.debug("service添加存储罐失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service添加存储罐失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_WORK_CHECK,type=C.LOG_TYPE.CREATE)
	public OaMsg addWorkCheckList(Integer tpId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
				for(int i=0;i<2;i++){
					WorkCheck workCheck=new WorkCheck();
					workCheck.setCheckType(i+1);
					workCheck.setTransportId(tpId);
					workCheckDao.addOnlyWorkCheck(workCheck);
				}
			
		}catch(RuntimeException e){
			LOG.debug("service添加存储罐失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新接卸方案失败",e);
		}
		return oaMsg;
	} 
   
	List<String> checkParent(List<String> name,String parentId,Map<String,Map<String,Object>> list){
		if(list!=null&&list.get(parentId)!=null&&list.get(parentId).get("parentId")!=null&&!Common.isNull(Integer.valueOf(list.get(parentId).get("parentId").toString()))){
			if(list.get(parentId).get("parentName")!=null)
			name.add(list.get(parentId).get("parentName").toString());
			checkParent(name, list.get(parentId).get("parentId").toString(), list);
		}else{
			return name;
		}
		return null;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_JOB_CHECK,type=C.LOG_TYPE.CREATE)
	public OaMsg addJobCheckList(List<JobCheck> list) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			for(int i=0;i<list.size();i++){
				jobCheckDao.addOnlyJobCheck(list.get(i));
			}
		}catch(RuntimeException e){
			LOG.debug("service添加岗位检查失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service添加岗位检查失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_WORK_CHECK,type=C.LOG_TYPE.CREATE)
	public OaMsg addWorkCheck(WorkCheck workCheck) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			workCheckDao.addWorkCheck(workCheck);
		}catch(RuntimeException e){
			LOG.debug("service添加作业检查失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service添加作业检查失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_WORK_CHECK,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateWorkCheck(WorkCheck workCheck) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
				workCheckDao.updateWorkCheck(workCheck);
		}catch(RuntimeException e){
			LOG.debug("service更新作业检查失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新作业检查失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_JOB_CHECK,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateJobCheck(JobCheck jobCheck) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			jobCheckDao.updateJobCheck(jobCheck);
		}catch(RuntimeException e){
			LOG.debug("service更新作业检查失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新作业检查失败",e);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_WORK,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateWork(String arrivalId,Work work,List<Notification> notificationList) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			if(!Common.isNull(work.getId())){
				Map<String, Object> workinfo=workDao.getWorkList(work.getId()).get(0);
				if(!Common.empty(workinfo.get("mArrivalTime"))&&!Common.isNull(work.getArrivalTime())){
					
					InboundOperationDto iod=new InboundOperationDto();
					iod.setArrivalId(Integer.valueOf(arrivalId));
					iod.setResult(1);
					Map<String,Object> arrivalInfo=inboundArrivalDao.getInboundArrivalList(iod,0,0).get(0);
					
					ArrivalInfo upArrivalInfo=new ArrivalInfo();
					upArrivalInfo.setArrivalId(Integer.parseInt(arrivalId));
					upArrivalInfo.setId(Integer.parseInt(arrivalInfo.get("arrivalinfoId").toString()));
					upArrivalInfo.setAnchorTime(work.getArrivalTime());
					arrivalInfoDao.updateArrival(upArrivalInfo);
					
					
					if((int)(Long.parseLong(workinfo.get("mArrivalTime").toString())/86400)!=(int)(work.getArrivalTime()/86400)){
						Arrival arrival=new Arrival();
						arrival.setId(Integer.parseInt(workinfo.get("arrivalId").toString()));
						arrival.setDispatchId(0);
						arrival.setArrivalStartTime(new Timestamp(work.getArrivalTime()*1000));
						arrivalDao.updateArrival(arrival);
					}
				}
			}
			if(!Common.empty(arrivalId)&&!Common.isNull(work.getTearPipeTime())){
				//允许最大在港时间=NOR发出时间+6+卸货数量/150，单位小时，其中卸货数量为各货品数量的总和
				//速遣时间（-）/超期时间（+）：（单位小时，小数点后精确到分钟）=拆管时间-允许最大在港时间。
				InboundOperationDto iod=new InboundOperationDto();
				iod.setArrivalId(Integer.valueOf(arrivalId));
				iod.setResult(1);
				Map<String,Object> arrivalInfo=inboundArrivalDao.getInboundArrivalList(iod,0,0).get(0);
				
				if(!Common.empty(arrivalInfo.get("norTime"))){
					
					
					
//			//计算卸货数量
//			CargoGoodsDto cgd=new CargoGoodsDto();
//			cgd.setArrivalId(Integer.parseInt(arrivalId));
//			List<Map<String,Object>> cargoList=cargoGoodsDao.getCargoList(cgd, 0, 0);
//			double allCount=0;
//			for(int i=0;i<cargoList.size();i++){
//				if(!Common.empty(cargoList.get(i).get("goodsPlan"))){
//					allCount+=Double.parseDouble(cargoList.get(i).get("goodsPlan").toString());
//				}
//			}
					ArrivalInfo upArrivalInfo=new ArrivalInfo();
					upArrivalInfo.setArrivalId(Integer.parseInt(arrivalId));
					upArrivalInfo.setId(Integer.parseInt(arrivalInfo.get("arrivalinfoId").toString()));
//			upArrivalInfo.setLastLeaveTime(Long.parseLong(new Date(arrivalInfo.get("norTime").toString()).getTime()+21600000+(allCount/150)+""));
					if(!Common.empty(arrivalInfo.get("mLastLeaveTime"))){
						long time=Long.parseLong(arrivalInfo.get("mLastLeaveTime").toString());
						long time1=work.getTearPipeTime();
						long scTime=(time1-time)*1000;
						BigDecimal b = new BigDecimal(Float.valueOf(scTime)/3600000);
						float repatriateOverTime = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
						if(repatriateOverTime<0){
							upArrivalInfo.setRepatriateTime(-repatriateOverTime);
						}else{
							upArrivalInfo.setOverTime(repatriateOverTime);
						}
					}
					arrivalInfoDao.updateArrival(upArrivalInfo);
				}
			}
			
			
			
			if(!Common.empty(arrivalId)){
				
				
				
				ArrivalDto dod=new ArrivalDto();
				dod.setArrivalInfoId(Integer.valueOf(arrivalId));
				List<Map<String,Object>> arrivalforeList=arrivalForeshowDao.getArrivalForeshowList(dod, 0, 0);
				
				
				
				if(!Common.empty(arrivalforeList)){
					Map<String,Object> arrivalfore=arrivalForeshowDao.getArrivalForeshowList(dod, 0, 0).get(0);
					
						
			
						ArrivalForeshow arrivalForeshow=new ArrivalForeshow();
						arrivalForeshow.setArrivalId(Integer.parseInt(arrivalId));
						arrivalForeshow.setId(Integer.parseInt(arrivalfore.get("id").toString()));
//			upArrivalInfo.setLastLeaveTime(Long.parseLong(new Date(arrivalInfo.get("norTime").toString()).getTime()+21600000+(allCount/150)+""));
						arrivalForeshow.setAnchorTime(work.getArrivalTime());
						arrivalForeshow.setPumpOpenTime(work.getOpenPump());
						arrivalForeshow.setPumpStopTime(work.getStopPump());
						arrivalForeshow.setTearPipeTime(work.getTearPipeTime());
						arrivalForeshow.setLeaveTime(work.getLeaveTime());
						if(Double.parseDouble(work.getWorkTime().toString())!=-1){
							arrivalForeshow.setWorkTime(work.getWorkTime().toString());
						}
						arrivalForeshowDao.updateArrivalForeshow(arrivalForeshow);
					
					
				}
			}
			
			workDao.updateWork(work);
			if(notificationList!=null)
			workDao.addNotify(notificationList);
		}catch(RuntimeException e){
			LOG.debug("service更新入库作业失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新入库作业失败",e);
		}
		return oaMsg;
	}
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_WORK,type=C.LOG_TYPE.UPDATE)
    public OaMsg updateWork(Work work) throws OAException {
    	OaMsg oaMsg=new OaMsg();
    	OperateLog log=new OperateLog();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			workDao.updateWork(work);
			if(work.getReviewStatus()!=null&&(work.getReviewStatus()==2||work.getReviewStatus()==3)){
				log.setType(C.LOG_TYPE.VERIFY);
				log.setContent("审核数量确认");
			}else{
				log.setType(C.LOG_TYPE.UPDATE);
				log.setContent("更新入库作业");
			}
			
		}catch(RuntimeException e){
			LOG.debug("service更新入库作业失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新入库作业失败",e);
		}
		log.setObject(C.LOG_OBJECT.PCS_INBOUND_WORK);
		operateLogService.create(log, oaMsg);
		return oaMsg;
    };
	
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_CARGO_GOODS,type=C.LOG_TYPE.CREATE)
	public OaMsg updateCargoGoodsList(List<Cargo> cargoList,List<Goods> goodsList,Work work,String shipId,SecurityCodeDto scDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(work.getReviewStatus()==2){//如果是提交，更新状态
				if(scDto!=null&&scDto.getIsSecurity()==1){
					User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
					if(!SecurityCodeHandler.getInstance().volidateCode((user.getId()+scDto.getObject()), scDto.getSecurityCode(), oaMsg)){
						return oaMsg;
					}
					if(!Common.empty(oaMsg.getMap().get("userId"))){
						work.setReviewUserId(Integer.valueOf(oaMsg.getMap().get("userId").toString()));
						}else{
							oaMsg.setCode(Constant.SYS_CODE_TIME_OUT);
							oaMsg.setMsg("验证码过期");
							return oaMsg;
						}
				}
				work.setStatus(9);
				for(int i=0;i<goodsList.size();i++){
				GoodsLog goodsLog=new GoodsLog();
				goodsLog.setType(1);
				goodsLog.setCreateTime(System.currentTimeMillis()/1000);
				goodsLog.setGoodsId(goodsList.get(i).getId());
				goodsLog.setGoodsChange(Double.parseDouble(goodsList.get(i).getGoodsTotal()));
				goodsLog.setGoodsSave(Double.parseDouble(goodsList.get(i).getGoodsTotal()));
				goodsLog.setBatchId(Integer.valueOf(shipId));
				goodsLog.setDeliverType(2);
				goodsLogDao.add(goodsLog);
				
				Goods goods =new Goods();
				goods.setId(goodsList.get(i).getId());
				goods.setCreateTime(new Timestamp(new Date().getTime()));
				cargoGoodsDao.updateGoods(goods);
				}
				}
			//更新货批
			for(int i=0;i<cargoList.size();i++){
				cargoGoodsDao.updateCargo(cargoList.get(i));
				}
			//添加更新货体
			String ids="";
			
			for(int i=0;i<goodsList.size();i++){
				if(!ids.contains("a"+goodsList.get(i).getCargoId()+"a")){
//			cargoGoodsDao.deleteGoodsByCargoId(goodsList.get(i).getCargoId());//删除上次保存的货体
			ids+="a"+goodsList.get(i).getCargoId()+"a";
			}
				if(!Common.isNull(goodsList.get(i).getId())){
					cargoGoodsDao.updateGoods(goodsList.get(i));
				}else{
					int id=(Integer) cargoGoodsDao.addGoods(goodsList.get(i));
					goodsList.get(i).setId(id);
				}
			}
		
			workDao.updateWorkStatus(work);
		}catch(RuntimeException e){
			LOG.debug("service添加货体失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service添加货体失败",e);
		}
		OperateLog log=new OperateLog();
		log.setType(C.LOG_TYPE.CREATE);
		log.setObject(C.LOG_OBJECT.PCS_INBOUND_CARGO_GOODS);
		log.setContent("生成货体");
		operateLogService.create(log, oaMsg);
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_STORE,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateStoreList(List<Store> storeList) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(storeList!=null){
			for(int i=0;i<storeList.size();i++){
			storeDao.updateStore(storeList.get(i));;
			}
			}
		}catch(RuntimeException e){
			LOG.debug("service更新存储罐失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"service更新存储罐失败",e);
		}
		return oaMsg;
	}
	/**解析工艺流程*/
	public List<Trans>  analysisXML(String xml){
		try {
			List<Map<String,String>> lineData=new ArrayList<Map<String,String>>();
			Map<String,Map<String,String>> valueData=new HashMap<String,Map<String,String>>();
			List<String> valueIds=new ArrayList<String>();
			List<Trans> transl=new ArrayList<Trans>();
			Map<String,Trans> mapTrans=new HashMap<String, Trans>();//key 保存子管线id
			List<String>  transIds=new ArrayList<String>();
			
			Document dom=DocumentHelper.parseText(xml);
			Element root=dom.getRootElement();
         List orggannodes=root.selectNodes("/mxGraphModel/root");			
		for(Iterator i=orggannodes.iterator();i.hasNext();){
			Element Datas=(Element)i.next();
			List cells=Datas.selectNodes("mxCell");
			for(Iterator c=cells.iterator();c.hasNext();){
				Element cell=(Element)c.next();
				//线
				if(cell.attributeValue("edge")!=null&&cell.attributeValue("edge").equals("1")){
					Map<String,String> map=new HashMap<String,String>();
					map.put("source",cell.attributeValue("source"));
					map.put("target", cell.attributeValue("target"));;
			         lineData.add(map);
				}
				//罐
				if(cell.attributeValue("type")!=null&&cell.attributeValue("type").equals("tank"))
					{
					Map<String,String> map1=new HashMap<String, String>();
					map1.put("id", cell.attributeValue("id"));
					map1.put("key", cell.attributeValue("key"));
					map1.put("type", "1");//罐区
					valueIds.add(cell.attributeValue("id"));
					valueData.put(cell.attributeValue("id"), map1);
					//管线
					}else if(cell.attributeValue("type")!=null&&(cell.attributeValue("type").equals("sTube")||cell.attributeValue("type").equals("bTube"))){
						Map<String,String> map1=new HashMap<String, String>();
						map1.put("id", cell.attributeValue("id"));
						map1.put("key", cell.attributeValue("key"));//罐和管线id
						map1.put("type", "2");//管
						valueIds.add(cell.attributeValue("id"));
						valueData.put(cell.attributeValue("id"), map1);
					}
				    //泊位
					else if(cell.attributeValue("type")!=null&&cell.attributeValue("type").equals("berth")){
						Map<String,String> map1=new HashMap<String, String>();
						map1.put("id", cell.attributeValue("id"));
						map1.put("type", "0");//泊位
						valueIds.add(cell.attributeValue("id"));
						valueData.put(cell.attributeValue("id"), map1);
					};
			}
			
			for(int j=0;j<lineData.size();j++){
				Map<String,String> maps=valueData.get(lineData.get(j).get("source"));
				Map<String,String>mapt=valueData.get(lineData.get(j).get("target"));
				if(mapt.get("type").equals("1")&&!maps.get("type").equals("1")){//如果是目标罐，并且来源不是管线  
					if(transIds.contains(maps.get("id"))){
						Trans trans1=mapTrans.get(maps.get("id"));
						trans1.setTankId(Common.checkInteger(mapt.get("key")));
					}else{
						if(Common.checkInteger(maps.get("key"))!=0){
							Trans trans=new Trans();
							trans.setTubeId(Common.checkInteger(maps.get("key")));//source
							trans.setTankId(Common.checkInteger(mapt.get("key")));//target
							transIds.add(maps.get("id"));
							mapTrans.put(maps.get("id"), trans);
						}
					}
				}else if(mapt.get("type").equals("2")&&!maps.get("type").equals("1")){//如果是管
					if(transIds.contains(mapt.get("id"))){
						Trans trans1=mapTrans.get(mapt.get("id"));
						if(maps.get("type").equals("2")){
						trans1.setParentId(Common.checkInteger(maps.get("key")));
						}
					}else{
						if(Common.checkInteger(mapt.get("key"))!=0){
							Trans trans=new Trans();
							if(maps.get("type").equals("2")){
								trans.setParentId(Common.checkInteger(maps.get("key")));
							}
							
							trans.setTubeId(Common.checkInteger(mapt.get("key")));
							transIds.add(mapt.get("id"));
							mapTrans.put(mapt.get("id"), trans);
						}
					}
				}
			}
			for(int n=0;n<transIds.size();n++){
				transl.add(mapTrans.get(transIds.get(n)));
			}
			return transl;
		}	
			
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_STORE,type=C.LOG_TYPE.UPDATE)
	public OaMsg cleanToStatus(InboundOperationDto ioDto)throws OAException {
		OaMsg oaMsg=new OaMsg();
		OperateLog log=new OperateLog();
		log.setType(C.LOG_TYPE.UPDATE);
		log.setObject(C.LOG_OBJECT.PCS_INBOUND_ALLITEMS);
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Arrival arrival =new Arrival();
			arrival.setId(ioDto.getId());
			arrival.setStatus(Integer.valueOf(ioDto.getStatuskey()));
	    arrivalDao.updateArrival(arrival);
	    MessageContent message=new MessageContent();
	    message.setCreateTime(System.currentTimeMillis()/1000);
	    User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		String statusName="";
		String  state="";
	    switch (arrival.getStatus()) {
		case 1:
			break;
		case 2://回退到到港计划，清空靠泊评估，靠泊方案，接卸方案，打循环方案，接卸作业work,trans,store,workCheck,jobCheck,goods
			//更新清空
			statusName="入库计划";
			state="1";
			arrivalInfoDao.cleanArrivalInfo(arrival.getId());
			berthAssessDao.cleanBerthAssess(arrival.getId());
			berthProgramDao.cleanBerthProgram(arrival.getId());
			notifyDao.cleanNotify(arrival.getId(),true);
			transportProgramDao.cleanTransportProgram(arrival.getId());
			workDao.cleanWork(arrival.getId(),null);
			jobCheckDao.cleanJobCheck(arrival.getId(),false);
			//删除清空
			transDao.cleanTrans(arrival.getId(),false);
			storeDao.cleanStore(arrival.getId(),false);
			workCheckDao.cleanWorkCheck(arrival.getId(),false);
			cargoGoodsDao.cleanCargo(arrival.getId());
			cargoGoodsDao.cleanGoods(arrival.getId(),false );
			approveContentDao.cleanToStatus(arrival.getId());
			goodsLogDao.cleanToStatus(arrival.getId());
			log.setContent("退回到入库计划");
			break;
		case 3://退回到靠泊评估
			statusName="靠泊评估";
			state="2";
			BerthAssess berthAssess=new BerthAssess();
			berthAssess.setArrivalId(arrival.getId());
			berthAssess.setReviewStatus(0);
			berthAssessDao.updateBerthAssess(berthAssess);
			berthProgramDao.cleanBerthProgram(arrival.getId());
			notifyDao.cleanNotify(arrival.getId(),true);
			transportProgramDao.cleanTransportProgram(arrival.getId());
			workDao.cleanWork(arrival.getId(),null);
			jobCheckDao.cleanJobCheck(arrival.getId(),false);
			
			//删除清空
			transDao.cleanTrans(arrival.getId(),false);
			storeDao.cleanStore(arrival.getId(),false);
			workCheckDao.cleanWorkCheck(arrival.getId(),false);
			cargoGoodsDao.cleanCargo(arrival.getId());
			cargoGoodsDao.cleanGoods(arrival.getId(),false);
			approveContentDao.cleanToStatus(arrival.getId());
			goodsLogDao.cleanToStatus(arrival.getId());
			log.setContent("退回到靠泊评估");
			break;	
		case 4://退回到靠泊方案
			statusName="靠泊方案";
			state="2";
			BerthProgram berthProgram=new BerthProgram();
			berthProgram.setArrivalId(arrival.getId());
			berthProgram.setStatus(0);
			berthProgramDao.updateBerthProgram(berthProgram);//更新状态
			notifyDao.cleanNotify(arrival.getId(),true);
			transportProgramDao.cleanTransportProgram(arrival.getId());
			workDao.cleanWork(arrival.getId(),null);
			jobCheckDao.cleanJobCheck(arrival.getId(),false);
			goodsLogDao.cleanToStatus(arrival.getId());
			//删除清空
			transDao.cleanTrans(arrival.getId(),false);
			storeDao.cleanStore(arrival.getId(),false);
			cargoGoodsDao.cleanCargo(arrival.getId());
			workCheckDao.cleanWorkCheck(arrival.getId(),false);
			cargoGoodsDao.cleanGoods(arrival.getId(),false);
			log.setContent("退回到靠泊方案");
			break;
		case 5://退回到接卸方案
			statusName="接卸方案";
			state="3";
			notifyDao.cleanNotify(arrival.getId(),ioDto.getProductId(),ioDto.getOrderNum(),true);
			TransportProgram transportProgram=new TransportProgram();
			transportProgram.setId(ioDto.getTransportId());
			transportProgram.setStatus(0);
			transportProgram.setNoticeCodeA("");
			transportProgram.setNoticeCodeB("");
			transportProgramDao.updateTransportProgram(transportProgram);
			TransportProgram transportProgram1=new TransportProgram();
			transportProgram1.setId(ioDto.getBackflowId());
			transportProgram1.setStatus(0);
			transportProgram1.setNoticeCodeA("");
			transportProgram1.setNoticeCodeB("");
			transportProgramDao.updateTransportProgram(transportProgram1);
			workDao.cleanWork(ioDto.getWorkId(),5+"");
			jobCheckDao.cleanJobCheck(ioDto.getTransportId(),true);
			//删除清空
			workCheckDao.cleanWorkCheck(ioDto.getTransportId(),true);
			cargoGoodsDao.cleanCargo(arrival.getId(),ioDto.getProductId());
			goodsLogDao.cleanToStatus(arrival.getId(),ioDto.getProductId());
			cargoGoodsDao.cleanGoods(arrival.getId(),ioDto.getProductId(),true);
			log.setContent("退回到接卸方案");
			break;
		case 6://退回到接卸准备
			statusName="接卸准备";
			state="4";
			notifyDao.cleanNotify(arrival.getId(),ioDto.getProductId(),ioDto.getOrderNum(),false);
			Work work =new Work();
			work.setId(ioDto.getWorkId());
			work.setStatus(6);
			work.setReviewStatus(0);
			work.setCreateUserId(0);
			work.setCreateTime(0L);
			work.setReviewUserId(0);
			work.setReviewTime(0L);
			workDao.updateWork(work);
			cargoGoodsDao.cleanCargo(arrival.getId(),ioDto.getProductId());
			goodsLogDao.cleanToStatus(arrival.getId(),ioDto.getProductId());
			cargoGoodsDao.cleanGoods(arrival.getId(),ioDto.getProductId(),true);
			log.setContent("退回到接卸准备");
			break;
		case 8:
			//校验是否已经入库确认
			statusName="数量确认";
			state="6";
			Work work1 =new Work();
			work1.setId(ioDto.getWorkId());
			work1.setStatus(8);
			work1.setReviewStatus(0);
			work1.setCreateUserId(0);
			work1.setCreateTime(0L);
			work1.setReviewUserId(0);
			work1.setReviewTime(0L);
			workDao.updateWork(work1);
			goodsLogDao.cleanToStatus(arrival.getId(),ioDto.getProductId());
			cargoGoodsDao.cleanGoods(arrival.getId(),ioDto.getProductId(),true);
			cargoGoodsDao.cleanCargo(arrival.getId(),ioDto.getProductId());
			log.setContent("退回到数量确认");
			break;
		default:
			break;
		}
	    messageDao.addMessageBysSQL("INSERT INTO t_pcs_message_content(content,createTime,sendUserId,TYPE,url) "
	    		+ "SELECT CONCAT(a.name ,'回退了一艘预计 [',b.arrivalStartTime,']到达的名为：[',c.name,']-[',d.refName,']的"
	    		+ "船的[',e.name,']的流程到"+statusName+"，请您获悉。'),UNIX_TIMESTAMP(DATE_FORMAT(NOW(),'%Y-%m-%d %H:%i:%s')),"+user.getId()+",2,"
	    		+ "CONCAT('#/inboundoperation/get?arrivalId=',"+arrival.getId()+",'&productId=',"+ioDto.getProductId()+",'&state=',"+state+") FROM t_auth_user a LEFT JOIN t_pcs_arrival b on  b.id="+arrival.getId()
	    		+ " LEFT JOIN t_pcs_ship c  on c.id=b.shipId  LEFT JOIN  t_pcs_ship_ref d  on d.id=b.shipRefId "
	    		+ " LEFT JOIN t_pcs_product e on "+ioDto.getProductId()+"=e.id  WHERE  a.id="+user.getId()+";");
	    messageDao.addMessageBysSQL(" INSERT INTO t_pcs_message_get(getUserId,messageId,STATUS)"
		+ " SELECT   DISTINCT u.userId,(SELECT MAX(id) FROM t_pcs_message_content),2"
		+ " FROM  `t_auth_authorization` u ,  `t_auth_security_resources` t,  `t_auth_resource_assignments` ta "
		+ " WHERE t.indentifier='AINBOUNDBACKMSG'  AND ta.sourceId = t.id   AND ta.roleId = u.roleId;");
		}catch (RuntimeException e){
			LOG.error("service回退失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
          throw new OAException(Constant.SYS_CODE_DB_ERR,"回退失败");			
		}
		operateLogService.create(log, oaMsg);
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_TRANSPORT_INFO,type=C.LOG_TYPE.CREATE)
	public OaMsg addTransportInfo(TransportInfo transportInfo) throws OAException{
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			transportInfoDao.addTransportInfo(transportInfo);
		}catch(RuntimeException e){
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			LOG.error("service添加传输附加信息");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service添加传输附加信息");
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_TRANSPORT_INFO,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateTransportInfo(TransportInfo transportInfo)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			transportInfoDao.updateTransportInfo(transportInfo);
		}catch(RuntimeException e){
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			LOG.error("service更改传输附加信息");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service更改传输附加信息");
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_BERTH_ASSESS,type=C.LOG_TYPE.VERIFY)
	public OaMsg sendCheck(String ids, Integer berthassessId, String content,String url,String msgContent)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			String[] id=ids.split(",");
			List<String> strIds=new ArrayList<String>();
			for(int i=0;i<id.length;i++){
				strIds.add(id[i]);
				ApproveContent approveContent =new ApproveContent();
				approveContent.setUserId(Integer.parseInt(id[i]));
				approveContent.setType(2);
				approveContent.setWorkId(berthassessId);
               if(approveContentDao.getApproveContent(approveContent, 0, 0).size()==0){
					approveContent.setTypeStatus(0);
					approveContentDao.addApproveContent(approveContent);
				}else{
					approveContent.setTypeStatus(0);
					approveContentDao.update(approveContent);
				}
				
				
			}
			User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			
			ApproveCache approveCache=new ApproveCache();
			approveCache.setUserId(mUser.getId());
			approveCache.setCacheUser(ids);
			approveCache.setWorkType(2);
			approveCache.setType(1);
			//缓存选择
			if(approveCacheDao.getApproveCache(approveCache).size()>0){
				approveCacheDao.update(approveCache);
			}else{
				
				approveCacheDao.addApproveCache(approveCache);
			}
			MessageDto mDto=new MessageDto();
			mDto.setSendUserId(mUser.getId());
			mDto.setTaskId(0);
			mDto.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			mDto.setUrl(url);
			mDto.setContent(msgContent);
			mDto.setType("1");
			messageDao.createMessage(mDto, strIds);
			
			//如果有审批信息并且用户不在提交列表内，就算该用户审批通过
			boolean isInIds=false;
			for (int i=0;i<id.length;i++){   
				if((mUser.getId()+"").equals(id[i])){
					isInIds=true;
					break;
				}
			}
			//如果有审批信息，就算该用户审批通过
			if(!Common.empty(content)&&!isInIds){
				ApproveContent approveContent=new ApproveContent();
				
				approveContent.setUserId(mUser.getId());
				approveContent.setWorkId(berthassessId);
				approveContent.setTypeStatus(1);
				approveContent.setType(2);
				approveContent.setContent(content);
				approveContent.setReviewTime(Long.parseLong(new Date().getTime()/1000+""));
				approveContentDao.update(approveContent);
			}
			
		}catch(RuntimeException e){
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			LOG.error("service更改传输附加信息");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service更改传输附加信息");
		}
		return oaMsg;
	}

	@Override
	public OaMsg getApproveContent(ApproveContent approveContent)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询审批");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(approveContentDao.getApproveContent(approveContent, 0, 0));
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_BERTH_ASSESS,type=C.LOG_TYPE.VERIFY)
	public OaMsg sendCopy(String ids, Integer berthassessId, String content)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始抄送");
			String[] id=ids.split(",");
			List<String> strIds=new ArrayList<String>();
			for(int i=0;i<id.length;i++){
				strIds.add(id[i]);
			}
			User mUser = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			
			ApproveCache approveCache=new ApproveCache();
			approveCache.setUserId(mUser.getId());
			approveCache.setCacheUser(ids);
			approveCache.setType(2);
			approveCache.setWorkType(2);
			//缓存选择
			if(approveCacheDao.getApproveCache(approveCache).size()>0){
				approveCacheDao.update(approveCache);
			}else{
				
				approveCacheDao.addApproveCache(approveCache);
			}
			
			MessageDto mDto=new MessageDto();
			mDto.setSendUserId(mUser.getId());
			mDto.setTaskId(0);
			mDto.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			mDto.setUrl("#/inboundoperation");
			mDto.setContent(content);
			mDto.setType("1");
			messageDao.createMessage(mDto, strIds);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (RuntimeException re) {
			LOG.error("抄送失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "抄送失败",re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_MORE_INBOUND_PROGRAM,type=C.LOG_TYPE.CREATE)
	public OaMsg addUnloading(InboundOperationDto ioDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			TransportProgram transportProgram=new TransportProgram();
			transportProgram.setArrivalId(ioDto.getArrivalId());
			transportProgram.setProductId(ioDto.getProductId());
			transportProgram.setOrderNum(ioDto.getOrderNum());
			transportProgramDao.addUnloadingTransportProgram(transportProgram);
            Work work=new Work();			
			work.setArrivalId(ioDto.getArrivalId());
			work.setProductId(ioDto.getProductId());
			work.setOrderNum(ioDto.getOrderNum());
			workDao.addUnloadingWork(work);
		} catch (RuntimeException re) {
			LOG.error("添加一个多次接卸失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "添加一个多次接卸失败",re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_MORE_INBOUND_PROGRAM,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteUnloading(InboundOperationDto ioDto) throws OAException {
		//TODO  与其相关的表的删除还未处理哦
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			//删除接卸方案
			transportProgramDao.deleteTransportProgram(ioDto.getTransportId()+"");
			//删除通知单
			NotifyDto notifyDto=new NotifyDto();
			notifyDto.setTransportId(ioDto.getTransportId());
			notifyDao.deleteNotify(notifyDto);
			//删除工作检查
			jobCheckDao.cleanJobCheck(ioDto.getTransportId(),true);
			//删除清空删除岗位检查
			workCheckDao.cleanWorkCheck(ioDto.getTransportId(),true);
			//删除work
			workDao.deleteWork(ioDto.getWorkId()+"");
			//删除store
			storeDao.deleteStoreByTransPortId(ioDto.getTransportId());
			
		} catch (RuntimeException re) {
			LOG.error("删除一个多次接卸失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "删除一个多次接卸",re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_MORE_BACK_FLOW_PROGRAM,type=C.LOG_TYPE.CREATE)
	public OaMsg addBackFlow(InboundOperationDto ioDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			TransportProgram transportProgram=new TransportProgram();
			transportProgram.setArrivalId(ioDto.getArrivalId());
			transportProgram.setProductId(ioDto.getProductId());
			transportProgram.setOrderNum(ioDto.getOrderNum());
			transportProgramDao.addBackFlowTransportProgram(transportProgram);
		} catch (RuntimeException re) {
			LOG.error("添加一个多次打循环失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "添加一个多次打循环失败",re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_MORE_BACK_FLOW_PROGRAM,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteBackFlow(InboundOperationDto ioDto) throws OAException {
		//TODO  与其相关的表的删除还未处理哦
				OaMsg oaMsg=new OaMsg();
				try {
					oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
					if(ioDto.getTransportId()!=null){
						NotifyDto notifyDto=new NotifyDto();
						notifyDto.setTransportId(ioDto.getTransportId());
						notifyDao.deleteNotify(notifyDto);
					transportProgramDao.deleteTransportProgram(ioDto.getTransportId()+"");
					transportInfoDao.deleteTransportInfoByTransPortId(ioDto.getTransportId());
					}
				} catch (RuntimeException re) {
					LOG.error("删除一个多次接卸失败",re);
					oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
					throw new OAException(Constant.SYS_CODE_DB_ERR, "删除一个多次接卸",re);
				}
				return oaMsg;
	}

	@Override
	public OaMsg getTotalGoodsTank(InboundOperationDto ioDto)
			throws OAException {
		//TODO  与其相关的表的删除还未处理哦
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().add(cargoGoodsDao.getTotalGoodsTank(ioDto));
			
		} catch (RuntimeException re) {
			LOG.error("根据arrivalId productId 查询总的储罐收货量失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "根据arrivalId productId 查询总的储罐收货量失败",re);
		}
		return oaMsg;
	}

	/**
	 * @Title exportInbound
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param ioDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月28日下午3:08:14
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportInbound(HttpServletRequest request, final InboundOperationDto ioDto) throws OAException {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/inboundMsg.xls",new CallBack() {
		@Override
		public void setSheetValue(HSSFSheet sheet){
			try {
				sheet.getRow(1).getCell(0).setCellValue(ioDto.getStartTime()+"/"+ioDto.getEndTime());
				List<Map<String, Object>> data=inboundArrivalDao.getExportInbound(ioDto);
				if(data!=null&&data.size()>0){
					int itemRowNum=3,itemNum=0,startRow,endRow;
					HSSFRow  itemRow;
					Map<String, Object> map;
					String[] productNames,clientNames,goodsNum;
					for(int i=0,len=data.size();i<len;i++){
						startRow=itemRowNum;
						itemRow=sheet.createRow(itemRowNum++);
						itemRow.setHeight(sheet.getRow(2).getHeight());
						for(int j=0;j<11;j++){
							itemRow.createCell(j).setCellStyle(sheet.getRow(2).getCell(j).getCellStyle());
						}
						map=data.get(i);
						productNames=map.get("productName").toString().split(",");
						clientNames=map.get("clientName").toString().split(",");
						goodsNum=map.get("goodsPlan").toString().split(",");
						itemNum=productNames.length;
						itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("shipRefName")));
						itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
						itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
						itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("leaveTime")));
						itemRow.getCell(4).setCellValue(FormatUtils.getDoubleValue(map.get("loadCapacity")));
						itemRow.getCell(8).setCellValue(FormatUtils.getStringValue(map.get("shipAgentName")));
						itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(map.get("berthName")));
						itemRow.getCell(10).setCellValue(FormatUtils.getStringValue(map.get("originalArea")));
					
                         for(int m=0;m<itemNum;m++){	
                        if(m!=0){
                        	itemRow=sheet.createRow(itemRowNum++);
                        	itemRow.setHeight(sheet.getRow(2).getHeight());
                        	for(int j=0;j<11;j++){
    							itemRow.createCell(j).setCellStyle(sheet.getRow(2).getCell(j).getCellStyle());
    						}
                        }
                        itemRow.getCell(5).setCellValue(productNames[m]);
                        itemRow.getCell(6).setCellValue(goodsNum[m]);
                        itemRow.getCell(7).setCellValue(clientNames[m]);
                         }
                         endRow=itemRowNum-1;
                         if(itemNum>1){
                        	 sheet.addMergedRegion(new Region(startRow,(short)0, endRow, (short)0));
                        	 sheet.addMergedRegion(new Region(startRow,(short)1, endRow, (short)1));
                        	 sheet.addMergedRegion(new Region(startRow,(short)2, endRow, (short)2));
                        	 sheet.addMergedRegion(new Region(startRow,(short)3, endRow, (short)3));
                        	 sheet.addMergedRegion(new Region(startRow,(short)4, endRow, (short)4));
                        	 sheet.addMergedRegion(new Region(startRow,(short)8, endRow, (short)8));
                        	 sheet.addMergedRegion(new Region(startRow,(short)9, endRow, (short)9));
                        	 sheet.addMergedRegion(new Region(startRow,(short)10, endRow, (short)10));
                         }
					}
				}
			} catch (OAException e) {
				e.printStackTrace();
			}
		}
		}).getWorkbook();
	}

	/**
	 * @Title updateInboundInfo
	 * @Descrption:TODO
	 * @param:@param ioDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月28日下午2:14:32
	 * @throws
	 */
	@Override
	public OaMsg updateInboundInfo(InboundOperationDto ioDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			//更新靠泊方案
			BerthProgram bp=new BerthProgram();
			bp.setId(ioDto.getBerthprogramId());
			bp.setBerthId(ioDto.getBerthId());
			bp.setStatus(-1);
			berthProgramDao.updateBerthProgram(bp);
			//是否申报
			if(ioDto.getIsMakeDispatchLog()!=null&&ioDto.getIsMakeDispatchLog()==1){
				Work work=new Work();
				work.setArrivalId(ioDto.getArrivalId());
				work.setArrivalTime(ioDto.getArrivalTime());
				work.setType(1);
				workDao.updateWork(work);
			}
			//作业计划信息
			ArrivalInfo aInfo=new ArrivalInfo();
			aInfo.setArrivalId(ioDto.getArrivalId());
			aInfo.setShipArrivalDraught(ioDto.getShipArrivalDraught());
			aInfo.setReport(ioDto.getReport());
			aInfo.setStatus(-1);
			arrivalInfoDao.updateArrival(aInfo);
			//到港信息
			Arrival arrival=new Arrival();
			arrival.setId(ioDto.getArrivalId());
			arrival.setShipId(ioDto.getShipId());
			arrival.setShipAgentId(ioDto.getShipAgentId());
			arrival.setArrivalStartTime(ioDto.getArrivalStartTime());
			if(null!=ioDto.getShipRefName()&&0!=arrival.getShipId()){
				Object id=shipRefDao.getShipRef(arrival.getShipId(), ioDto.getShipRefName()).get("id");
				if(null==id||Integer.valueOf(id.toString())==0){
				ShipRef shipRef=new ShipRef();
				shipRef.setRefName(ioDto.getShipRefName());
				shipRef.setShipId(arrival.getShipId());
				arrival.setShipRefId(shipRefDao.addShipRef(shipRef));
				}else{
				arrival.setShipRefId(Integer.valueOf(id.toString()));
				}
			}
			arrivalDao.updateArrival(arrival);
		} catch (RuntimeException re) {
			LOG.error("service更新列表基本信息失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service更新列表基本信息失败",re);
		}
		return oaMsg;
	}

	/**
	 * @Title getPrintList
	 * @Descrption:TODO
	 * @param:@param ioDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月8日上午10:27:31
	 * @throws
	 */
	@Override
	public OaMsg getPrintList(InboundOperationDto ioDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
			try{
				oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
				oaMsg.getData().addAll(inboundArrivalDao.getPrintList(ioDto));
			}catch (RuntimeException re) {
				LOG.error("service获取打印信息失败失败",re);
				oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
				throw new OAException(Constant.SYS_CODE_DB_ERR, "service获取打印信息失败失败",re);
			}
			return oaMsg;
	}
	 
}
