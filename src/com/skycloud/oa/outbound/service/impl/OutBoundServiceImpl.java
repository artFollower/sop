package com.skycloud.oa.outbound.service.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import com.skycloud.oa.inbound.dao.ArrivalInfoDao;
import com.skycloud.oa.inbound.dao.BerthAssessDao;
import com.skycloud.oa.inbound.dao.BerthProgramDao;
import com.skycloud.oa.inbound.dao.TransportProgramDao;
import com.skycloud.oa.inbound.dao.WorkDao;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.Work;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.dao.OutBoundDao;
import com.skycloud.oa.outbound.dao.ShipDeliveryMeasureDao;
import com.skycloud.oa.outbound.dao.VehicleDeliverWorkDao;
import com.skycloud.oa.outbound.dao.WeighBridgeDao;
import com.skycloud.oa.outbound.dto.GoodsLogDto;
import com.skycloud.oa.outbound.dto.ShipArrivalDto;
import com.skycloud.oa.outbound.dto.ShipDeliverMeasureDto;
import com.skycloud.oa.outbound.dto.ShipDeliverWorkQueryDto;
import com.skycloud.oa.outbound.model.Approve;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.ShipDeliveryMeasure;
import com.skycloud.oa.outbound.model.UploadFileInfo;
import com.skycloud.oa.outbound.service.OutBoundService;
import com.skycloud.oa.report.utils.DateUtils;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.system.dao.ShipRefDao;
import com.skycloud.oa.system.model.ShipRef;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.ExcelUtil.CallBack;
import com.skycloud.oa.utils.OaMsg;
@Service
public class OutBoundServiceImpl implements OutBoundService{
	private static Logger LOG = Logger.getLogger(OutBoundServiceImpl.class);
	@Autowired
	private OutBoundDao outBoundDao ; 
	@Autowired
	private VehicleDeliverWorkDao vehicleDeliverWorkDao ;
	@Autowired
	private GoodsLogDao goodsLogDao ;
	@Autowired
	private BerthProgramDao berthProgramDao ;
	@Autowired
	private TransportProgramDao transportProgramDao ;
	@Autowired
	private BerthAssessDao berthAssessDao ;
	@Autowired
	private ArrivalInfoDao arrivalInfoDao;//入库计划
	@Autowired
	private WeighBridgeDao weighBridgeDao;
	@Autowired
	private WorkDao workDao;//通知单
	@Autowired
	private ShipRefDao shipRefDao;
	@Autowired
	private ArrivalDao arrivalDao;
	@Autowired
	private ShipDeliveryMeasureDao sDMDao;
	/**获取船舶出库列表
	 * @Title getOutBoundList
	 * @Descrption:TODO
	 * @param:@param arrivalDto
	 * @param:@param pageView
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:17:47
	 * @throws
	 */
	@Override
	public OaMsg getOutBoundList(ShipArrivalDto arrivalDto, PageView pageView) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(outBoundDao.getOutBoundList(arrivalDto,pageView.getStartRecord(),pageView.getMaxresult())) ; 
			oaMsg.getMap().put(Constant.TOTALRECORD, outBoundDao.getOutBoundListCount(arrivalDto)+"");
		} catch (Exception re) {
			LOG.error("service获取船舶出库列表失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	/**
	 * 更新船发列表基本信息
	 * @Title updateBaseInfo
	 * @Descrption:TODO
	 * @param:@param shipArrivalDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:18:34
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTBOUND_BASEINFO,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateBaseInfo(ShipArrivalDto shipArrivalDto) {
		
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Arrival arrival=new Arrival();
			arrival.setId(Integer.valueOf(shipArrivalDto.getArrivalId()));
			//更新船名
			if(null!=shipArrivalDto.getShipRefName()&&0!=shipArrivalDto.getShipId()){
				Object id=shipRefDao.getShipRef(shipArrivalDto.getShipId(),shipArrivalDto.getShipRefName()).get("id");
				if(null==id||Integer.valueOf(id.toString())==0){
				ShipRef shipRef=new ShipRef();
				shipRef.setRefName(shipArrivalDto.getShipRefName());
				shipRef.setShipId(shipArrivalDto.getShipId());
				arrival.setShipRefId(shipRefDao.addShipRef(shipRef));
				}else{
					arrival.setShipRefId(Integer.valueOf(id.toString()));
				}
			}
			arrival.setArrivalStartTime(Timestamp.valueOf(shipArrivalDto.getArrivalTime()));
			arrival.setShipAgentId(Integer.valueOf(shipArrivalDto.getShipAgentId()));
			arrival.setBerthId(shipArrivalDto.getBerthId());
			arrivalDao.updateArrival(arrival);
			Work work =new Work();
			work.setArrivalId(Integer.valueOf(shipArrivalDto.getArrivalId()));
			work.setArrivalTime(DateUtils.getLongTime(shipArrivalDto.getArrivalTime()));
			workDao.updateWork(work);
			Map<String,Object> bpMap=outBoundDao.getBerthProgramByArrivalId(shipArrivalDto.getArrivalId());
			
			BerthProgram bProgram=new BerthProgram();
			bProgram.setBerthId(shipArrivalDto.getBerthId());
			bProgram.setArrivalId(Integer.valueOf(shipArrivalDto.getArrivalId()));
			if(bpMap!=null&&bpMap.get("id")!=null){
				berthProgramDao.updateBerthProgram(bProgram);
			}else{
				 outBoundDao.saveBerthProgram(bProgram);
			}
			ArrivalInfo arrivalInfo = new ArrivalInfo() ;
			arrivalInfo.setShipArrivalDraught(Float.parseFloat(shipArrivalDto.getShipArrivalDraught())) ;
			arrivalInfo.setArrivalId(Integer.parseInt(shipArrivalDto.getArrivalId())) ;
			arrivalInfo.setReport(shipArrivalDto.getReport()) ;
			if(Common.empty(shipArrivalDto.getArrivalInfoId())||"0".equals(shipArrivalDto.getArrivalInfoId())){
				arrivalInfoDao.addArrivalInfo(arrivalInfo);
			}else{
				arrivalInfoDao.updateArrival(arrivalInfo);
			}
			if("已申报".equals(shipArrivalDto.getReport())){
			//生成分流台账
			List<Map<String,Object>> l = outBoundDao.getWorkByArrivalId(shipArrivalDto.getArrivalId()) ;
			if(l.size()<=0){
				outBoundDao.insertWorkInfo(Integer.valueOf(shipArrivalDto.getArrivalId()));
			}
			Map<String, Object> tpMap=outBoundDao.getTransportProgramMsg(shipArrivalDto.getArrivalId());
			if(tpMap==null){
				TransportProgram transportProgram=new TransportProgram();
				transportProgram.setArrivalId(Integer.valueOf(shipArrivalDto.getArrivalId()));
				transportProgram.setType(2);
				outBoundDao.saveTransportProgram(transportProgram);
			}
			//生成流量计台账
			if(shipArrivalDto.getBerthId()==4||shipArrivalDto.getBerthId()==5||shipArrivalDto.getProductId()==31||shipArrivalDto.getProductId()==33){
				ShipDeliveryMeasure sMeasure=new ShipDeliveryMeasure();
				sMeasure.setArrivalId(Integer.valueOf(shipArrivalDto.getArrivalId()));
				sDMDao.add(sMeasure);
			}else{
				ShipDeliverMeasureDto sDto=new ShipDeliverMeasureDto();
				sDto.setArrivalId(Integer.valueOf(shipArrivalDto.getArrivalId()));
				sDMDao.delete(sDto);
			}
			}
		} catch (Exception re) {
			LOG.error(" service更新船发列表基本信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	/**通过id获取出库作业计划
	 * @Title getArrivalInfoById
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:19:00
	 * @throws
	 */
	@Override
	public OaMsg getArrivalInfoById(String arrivalId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, Object> map=new HashMap<String,Object>();
			map.putAll(outBoundDao.getArrivalInfoById(arrivalId));
			map.putAll(outBoundDao.getTubesAndTanksByArrivalId(arrivalId));
			//获取tankInfo,tubInfo
			oaMsg.getData().add(map);
		} catch (Exception re) {
			LOG.error("通过id获取作业计划失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	
	
	/**
	 * 保存或提交作业计划
	 * @Title addOrUpdateArrivalInfo
	 * @Descrption:TODO
	 * @param:@param arrivalInfo
	 * @param:@param isTransport
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:19:21
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTBOUND_ARRIVALINFO,type=C.LOG_TYPE.UPDATE)
	public OaMsg addOrUpdateArrivalInfo(ArrivalInfo arrivalInfo,Integer isTransport)throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			
			if(!Common.isNull(arrivalInfo.getId())){
				arrivalInfoDao.updateArrival(arrivalInfo) ;
			}else{
				oaMsg.getMap().put("id", outBoundDao.saveArrivalInfo(arrivalInfo)+"");
			}
			if(arrivalInfo.getStatus()==1){
				if(isTransport!=null&&isTransport==2){
					outBoundDao.updateStatus("52", arrivalInfo.getArrivalId()+"") ;
				}else{
					outBoundDao.updateStatus("51", arrivalInfo.getArrivalId()+"") ;
				}
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("service保存或提交作业计划失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	
	/**
	 * @Title getBerthProgramByArrivalId
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年3月15日上午9:30:17
	 * @throws
	 */
	@Override
	public OaMsg getBerthProgramByArrivalId(String arrivalId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().add(outBoundDao.getBerthProgramByArrivalId(arrivalId)) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("获取靠泊方案信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	/**
	 * @Title addOrUpdateBerthProgram
	 * @Descrption:TODO
	 * @param:@param berthProgram
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月16日上午11:54:26
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTBOUND_BERTHPROGRAM,type=C.LOG_TYPE.UPDATE)
	public OaMsg addOrUpdateBerthProgram(BerthProgram berthProgram, SecurityCodeDto scDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			Map<String,Object> bpMap= outBoundDao.getBerthProgramByArrivalId(berthProgram.getArrivalId()+"");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(berthProgram.getStatus()==2){
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
				if(bpMap!=null&&bpMap.get("type")!=null&&Integer.valueOf(bpMap.get("type").toString())==5){
					outBoundDao.updateStatus("54", berthProgram.getArrivalId()+"") ;
				}else{
				outBoundDao.updateStatus("52", berthProgram.getArrivalId()+"") ;
				}
			}
		   if(bpMap!=null&&bpMap.get("id")!=null)
			   berthProgram.setId(Integer.valueOf(bpMap.get("id").toString()));  
		   	
		 //生成流量计台账
		   if(berthProgram.getBerthId()!=null&&bpMap!=null&&bpMap.get("productId")!=null){
			if(berthProgram.getBerthId()==4||berthProgram.getBerthId()==5||Integer.valueOf(bpMap.get("productId").toString())==31||Integer.valueOf(bpMap.get("productId").toString())==33){
				ShipDeliveryMeasure sMeasure=new ShipDeliveryMeasure();
				sMeasure.setArrivalId(berthProgram.getArrivalId());
				sDMDao.add(sMeasure);
			}else{
				ShipDeliverMeasureDto sDto=new ShipDeliverMeasureDto();
				sDto.setArrivalId(berthProgram.getArrivalId());
				sDMDao.delete(sDto);
			}}
		   
			if(!Common.isNull(berthProgram.getId())){
				berthProgramDao.updateBerthProgram(berthProgram);
				//同步更新arrival表
				Arrival arrival=new Arrival();
				arrival.setId(berthProgram.getArrivalId());
				arrival.setBerthId(berthProgram.getBerthId());
				arrivalDao.updateArrival(arrival);
			}else{
				oaMsg.getMap().put("id", outBoundDao.saveBerthProgram(berthProgram)+"");
			}
			
		} catch (Exception re) {
			LOG.error("获取靠泊方案信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	/**获取靠泊评估信息
	 * @Title getBerthAssess
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年3月16日下午1:23:23
	 * @throws
	 */
	@Override
	public OaMsg getBerthAssess(String arrivalId) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().add(outBoundDao.getBerthAssess(arrivalId)) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("获取靠泊评估信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	/**
	 * 保存或更新靠泊评估
	 * @Title addOrUpdateBerthAssess
	 * @Descrption:TODO
	 * @param:@param berthAssess
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月16日下午2:43:35
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTBOUND_BERTHASSESS,type=C.LOG_TYPE.CREATE)
	public OaMsg addOrUpdateBerthAssess(BerthAssess berthAssess, SecurityCodeDto scDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(berthAssess.getReviewStatus()==2){
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
			}
			Map<String, Object> baMap=outBoundDao.getBerthAssess(berthAssess.getArrivalId()+"");
			if(baMap!=null&&baMap.get("id")!=null){
				berthAssess.setId(Integer.valueOf(baMap.get("id").toString()));
			}
			if(!Common.isNull(berthAssess.getId())){
				berthAssessDao.updateBerthAssess(berthAssess);
			}else{
				oaMsg.getMap().put("id", outBoundDao.saveBerthAssess(berthAssess)+"");
			}
		} catch (Exception re) {
			LOG.error("获取靠泊方案信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	/**
	 * 获取发货准备信息
	 * @Title getDeliverReadyMsg
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月17日上午9:56:38
	 * @throws
	 */
	@Override
	public OaMsg getDeliverReadyMsg(String arrivalId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, Object> map=new HashMap<String,Object>();
			map.putAll(outBoundDao.getTransportProgramMsg(arrivalId));
			map.put("isInvoice", (outBoundDao.checkIsInvoice(arrivalId)>0?1:0)+"");
			oaMsg.getData().add(map);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("获取发货准备信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**保存更新船舶出库工艺流程
	 * @Title addOrUpdateTransportProgram
	 * @Descrption:TODO
	 * @param:@param transportProgram
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月24日下午2:05:46
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTBOUND_TRANSPORTPROGRAM,type=C.LOG_TYPE.UPDATE)
	public OaMsg addOrUpdateTransportProgram(TransportProgram transportProgram)throws OAException
	{
		OaMsg oaMsg = new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try 
		{
			Map<String, Object> tpMap=outBoundDao.getTransportProgramMsg(transportProgram.getArrivalId()+"");
			if(tpMap!=null&&tpMap.get("id")!=null){
				transportProgram.setId(Integer.valueOf(tpMap.get("id").toString()));
			}
			    String flowXml = transportProgram.getFlow() ;
				Map<String, String> idInfo=analysisXML(flowXml) ;
				 transportProgram.setPumpInfo(idInfo.get("pumpNames"));
				 transportProgram.setTankInfo(idInfo.get("tankNames"));
				 transportProgram.setTubeInfo(idInfo.get("tubeNames"));
				Integer transportId=transportProgram.getId();
				if(!Common.isNull(transportProgram.getId())){
					transportProgramDao.updateTransportProgram(transportProgram) ;
					if(transportProgram.getStatus()==2){
						outBoundDao.updateStatus("53", transportProgram.getArrivalId()+"");
						//校验是否生成分流台账
						List<Map<String,Object>> l = outBoundDao.getWorkByArrivalId(transportProgram.getArrivalId()+"") ;
						if(l.size()<=0){
							outBoundDao.insertWorkInfo(transportProgram.getArrivalId());
						}
					}
				}else{
					transportId=outBoundDao.saveTransportProgram(transportProgram);
					oaMsg.getMap().put("id", transportId+"");
				}
				//添加罐
				outBoundDao.deleteStore(transportId,idInfo.get("tankIds")) ;
				String[] t = idInfo.get("tankIds").split(",") ;
				for(int i=0 ;i<t.length;i++){
					outBoundDao.saveStore(transportId,t[i]) ;
				}
				//添加管线
				outBoundDao.deleteTrans(transportId,idInfo.get("tubeIds")) ;
				String[] t1 = idInfo.get("tubeIds").split(",");
				for(int i =0;i<t1.length;i++){
					outBoundDao.saveTrans(transportId, t1[i]) ;
				}
			
		} catch (Exception re) {
			LOG.error("保存更新船舶出库工艺流程失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	/**
	 * 获取船舶出库发货信息
	 * @Title getAmountAffirmMsg
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月24日下午2:06:18
	 * @throws
	 */
	@Override
	public OaMsg getAmountAffirmMsg(String arrivalId)throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			List<Object> list = new ArrayList<Object>() ;
			list.add(outBoundDao.getAmountAffirmMsg(arrivalId)) ;
			list.add(vehicleDeliverWorkDao.getApproveInfo(4,arrivalId)) ;
			oaMsg.getData().addAll(list);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("获取船舶出库发货信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	/**
	 * @Title confirmData
	 * @Descrption:TODO
	 * @param:@param data
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年3月24日下午4:18:43
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTBOUND_AMOUNTAFFIRM,type=C.LOG_TYPE.CONFIRM)
	public OaMsg confirmData(ShipDeliverWorkQueryDto sqDto, SecurityCodeDto scDto)throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			if(sqDto.getApprove().getStatus()==2){
				if(scDto!=null&&scDto.getIsSecurity()==1){
					User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
					if(!SecurityCodeHandler.getInstance().volidateCode((user.getId()+scDto.getObject()), scDto.getSecurityCode(), oaMsg)){
						return oaMsg;
					}
				if(!Common.empty(oaMsg.getMap().get("userId"))){
					sqDto.getApprove().setReviewUserId(Integer.valueOf(oaMsg.getMap().get("userId").toString()));
					}else{
						oaMsg.setCode(Constant.SYS_CODE_TIME_OUT);
						oaMsg.setMsg("验证码过期");
						return oaMsg;
					}
				}
			}
			approve(sqDto);
		    if(sqDto.isChange()){//如果实发量没有改变---》船发称重完毕--》提交审核
		    	if(sqDto.getGoodsLoglist().size()>0){
					for(int i = 0 ,len=sqDto.getGoodsLoglist().size();i<len;i++){
						GoodsLog g = sqDto.getGoodsLoglist().get(i);
						//如果实发量修改，或者出库时间修改
						if(Common.sub(g.getActualNum(), g.getTempDeliverNum(),3)!=0||!Common.isNull(g.getCreateTime())){
							goodsLogDao.up(g);
							weighBridgeDao.updateGoodsCurrentAndWeighBridge(g);	
							g.setActualNum(Common.add(g.getTempDeliverNum(), 0,3));
							g.setId(-1);
							goodsLogDao.synGoodsLog(g);
							//修改出库时间遍历修改每次结存量
							if(!Common.isNull(g.getCreateTime())){
							goodsLogDao.syncGoodsLogGoodsSave(new GoodsLogDto(g.getSerial(),sqDto.getChoiceTime()));
							}
						}
					}
				}
		    }
		} catch (Exception re) {
			LOG.error("船舶出库数量确认失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	/**
	 * 获取分类台账列表
	 * @Title getShipFlowList
	 * @Descrption:TODO
	 * @param:@param startTime
	 * @param:@param endTime
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年6月1日下午8:50:21
	 * @throws
	 */
	@Override
	public OaMsg getShipFlowList(long startTime,long endTime){
		//TODO 1.查询船舶，2.根据arrivalId查询记录
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		List<Map<String,Object>> shipData=outBoundDao.getOutArrivalList(startTime,endTime);
		     if(shipData!=null&&shipData.size()>0){
		     for(int i=0;i<shipData.size();i++){
		    	 shipData.get(i).put("outFlowData", outBoundDao.getOutFlowDataByArrivalId(Integer.valueOf(shipData.get(i).get("arrivalId").toString())));
		    	 shipData.get(i).put("tubeNames", outBoundDao.getTubeNamesByArrivalId(Integer.valueOf(shipData.get(i).get("arrivalId").toString())));
		    	 shipData.get(i).put("clientName", outBoundDao.getClientNameByArrivalId(Integer.valueOf(shipData.get(i).get("arrivalId").toString())));
		     }	
		     }
			oaMsg.getData().addAll(shipData);
		} catch (Exception re) {
			LOG.error("查询储罐台账记录", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	
	/**
	 * 获取分流台账信息
	 * @Title getShipFlowInfo
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月1日下午8:51:18
	 * @throws
	 */
	@Override
	public OaMsg getShipFlowInfo(Integer arrivalId) throws OAException{
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, Object> map=outBoundDao.getShipFlowInfo(arrivalId);
			if(map!=null){
				map.put("checkMsg",outBoundDao.getShipFlowCheckMsg(arrivalId));
				map.put("tubeName",outBoundDao.getTubeNamesByArrivalId(arrivalId));
			}
			oaMsg.getData().add(map);
		} catch (RuntimeException re) {
			LOG.error("service获取分流台账信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	
	/**
	 * 保存分流台账记录信息
	 * @Title saveShipFlow
	 * @Descrption:TODO
	 * @param:@param deliverWorkList
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年6月1日下午10:20:08
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTWORK,type=C.LOG_TYPE.UPDATE)
	public OaMsg saveShipFlow(ShipDeliverWorkQueryDto wDto) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Work work = wDto.getWork();
			workDao.updateWork(work) ;
			outBoundDao.updateArrivalTime(work.getArrivalTime(), work.getArrivalId()+"") ;
			setOverTime(work,wDto.getArrivalId()) ;
			List<WorkCheck> workCheckList = wDto.getWorkCheckList() ;
			outBoundDao.deleteWorkCheckByTransportId(wDto.getDeliveryShip().getTransportId()) ;
			for(int i = 0;i<workCheckList.size();i++){
				outBoundDao.saveCheckWork(workCheckList.get(i)) ;
			}
			outBoundDao.deleteDeliveryShipByTransportId(wDto.getDeliveryShip().getTransportId()) ;
			outBoundDao.saveDeliveryShip(wDto.getDeliveryShip()) ;
		} catch (Exception re) {
			LOG.error("serice保存分流台账记录信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	//解析xml获取罐id
	/**解析工艺流程*/
	@SuppressWarnings("rawtypes")
	public Map<String, String>  analysisXML(String xml)throws Exception{
		Map<String, String> idINfo=new HashMap<String, String>();
			Document dom=DocumentHelper.parseText(xml);
			Element root=dom.getRootElement();
         List orggannodes=root.selectNodes("/mxGraphModel/root");
         String tankIds = "",tankNames="",tubeIds = "",tubeNames="",pumpNames="" ;
		for(Iterator i=orggannodes.iterator();i.hasNext();){
			Element Datas=(Element)i.next();
			List cells=Datas.selectNodes("mxCell");
			for(Iterator c=cells.iterator();c.hasNext();){
				Element cell=(Element)c.next();
				if(cell.attributeValue("type")!=null&&cell.attributeValue("type").equals("tank")){
						tankIds+=cell.attributeValue("key")+"," ;
						tankNames+=cell.attributeValue("value")+",";
					}else if(cell.attributeValue("type")!=null&&(cell.attributeValue("type").equals("sTube")||cell.attributeValue("type").equals("bTube"))){
						tubeIds+=cell.attributeValue("key")+"," ;
						tubeNames+=cell.attributeValue("value")+",";
					}else if(cell.attributeValue("type")!=null&&cell.attributeValue("type").equals("pupm")){
						pumpNames+=cell.attributeValue("value")+",";
					}
			}
		}
		if(tankIds.length()>0)
			idINfo.put("tankIds", tankIds.substring(0,tankIds.length()-1));
		else  
			idINfo.put("tankIds","");
		
		if(tankNames.length()>0)
			idINfo.put("tankNames", tankNames.substring(0,tankNames.length()-1));
		else  
			idINfo.put("tankNames","");
		
		if(tubeIds.length()>0)
			idINfo.put("tubeIds", tubeIds.substring(0,tubeIds.length()-1));
		else  
			idINfo.put("tubeIds","");
		
		if(tubeNames.length()>0)
			idINfo.put("tubeNames", tubeNames.substring(0,tubeNames.length()-1));
		else  
			idINfo.put("tubeNames","");
		
		if(pumpNames.length()>0)
			idINfo.put("pumpNames", pumpNames.substring(0,pumpNames.length()-1));
		else  
			idINfo.put("pumpNames","");
		 
		return idINfo ;
	}
	
	/**
	 * 审核状态记录更新
	 * @param o
	 * @param arrivalId
	 * @throws Exception
	 */
	public void  approve(ShipDeliverWorkQueryDto sqDto)throws Exception{
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		Approve approve = sqDto.getApprove() ;
		approve.setModelId(4) ;
		if(approve.getStatus()==0||approve.getStatus()==1){			//添加审批信息
			approve.setCheckUserId(user.getId()) ;
			approve.setComment("") ;
			approve.setCreateTime(new Date()) ;
			if(approve.getId()>0){
				vehicleDeliverWorkDao.updateApprove(approve,1) ;
			}else{
				vehicleDeliverWorkDao.saveApprove(approve) ;
			}
		}
		if(approve.getStatus()==2){
			if(approve.getReviewUserId()==0){
			approve.setReviewUserId(user.getId()) ;
			}
			approve.setReviewTime(new Date().getTime()/1000) ;
			vehicleDeliverWorkDao.updateApprove(approve,0) ;
			outBoundDao.updateStatus("54",sqDto.getArrivalId()+"");
		}
		if(approve.getStatus()==3){
			if(approve.getReviewUserId()!=0){
				approve.setReviewUserId(user.getId()) ;
				}
			approve.setReviewTime(new Date().getTime()/1000) ;
			approve.setStatus(3) ;
			vehicleDeliverWorkDao.updateApprove(approve,0) ;
			arrivalDao.updateArrival(new Arrival(sqDto.getArrivalId(), 53));
		}
	}


	/**
	 * 查询码头作业通知单信息
	 */
	@Override
	public OaMsg queryDockNotifyInfo(String arrivalId) {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始-------saveArrivalInfo--------------");
			List<Object> list = new ArrayList<Object>() ;
			list.add(outBoundDao.queryDockNotifyInfo(arrivalId)) ;
			list.add(outBoundDao.getArrivalById(arrivalId)) ;
			//添加用户信息
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			list.add(user) ;
			Map<String,Object> isInvoice=new HashMap<String, Object>();
			isInvoice.put("isInvoice", (outBoundDao.checkIsInvoice(arrivalId)>0?1:0)+"");
		    list.add(isInvoice);
			oaMsg.setData(list) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (Exception re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	//设置超期时间或速谴时间
	@Log(object=C.LOG_OBJECT.PCS_OUTWORK,type=C.LOG_TYPE.UPDATE)
	public void setOverTime(Work work,int arrivalId)throws Exception{
		Map<String,Object> arrivalInfo=outBoundDao.getArrivalInfoById(arrivalId+"");
	 
	    if(arrivalInfo!=null){
		ArrivalInfo upArrivalInfo=new ArrivalInfo();
		upArrivalInfo.setArrivalId(arrivalId);
		
		
		if(arrivalInfo.get("mlastLeaveTime")!=null&&!Common.isNull(arrivalInfo.get("mlastLeaveTime").toString())&&!Common.isNull(work.getLeaveTime())){
			long time=Long.parseLong(arrivalInfo.get("mlastLeaveTime").toString());
			long time1=work.getLeaveTime();
			if(time!=-1&&time1!=-1){
				long scTime=(time1-time)*1000;
				BigDecimal b = new BigDecimal(Float.valueOf(scTime)/3600000);
				float repatriateOverTime = b.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				if(repatriateOverTime<0){
					upArrivalInfo.setRepatriateTime(-repatriateOverTime);
				}else{
					upArrivalInfo.setOverTime(repatriateOverTime);
				}
				arrivalInfoDao.updateArrival(upArrivalInfo);
			}
		}}
	}
	

	@Override
	public OaMsg getLogIsHave(long startTime, long endTime) {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			oaMsg.getData().addAll(outBoundDao.getLogIsHave(startTime, endTime));
			oaMsg.setMsg("查询成功");
		} catch (OAException  re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg ;
	}
	
	/**通过id获取到港计划信息
	 * @Title getArrivalById
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年6月12日下午4:33:00
	 * @throws
	 */
	@Override
	public OaMsg getArrivalById(String arrivalId) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().addAll(outBoundDao.getArrivalById(arrivalId)) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("service通过id获取到港计划信息失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	
	
	@Override
	public OaMsg getBaseGoodsInfo(String arrivalId)throws OAException {
		
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().addAll(outBoundDao.getBaseGoodsInfo(arrivalId)) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (RuntimeException re) {
			LOG.error("getBaseGoodsInfo失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	


	/**
	 * 作业文件上传
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTFILEUPLOADINFO,type=C.LOG_TYPE.CREATE)
	public OaMsg saveUploadInfo(UploadFileInfo fileInfo) {
		
		OaMsg oaMsg = new OaMsg();
		try {
			outBoundDao.saveUploadInfo(fileInfo) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("作业文件上传失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getFileList(String arrivalId,Integer type) {
		
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.getData().addAll(outBoundDao.getFileList(arrivalId,type)) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("鎻掑叆澶辫触", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}
	/**
	 * 删除作业文件
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTFILEUPLOADINFO,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteUploadFile(String id) {
		
		OaMsg oaMsg = new OaMsg();
		try {
			outBoundDao.deleteUploadFile(id) ;
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (Exception re) {
			LOG.error("删除作业文件失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	/**
	 * @Title exportOutbound
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param startTime
	 * @param:@param endTime
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月3日上午10:38:52
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportOutbound(HttpServletRequest request, final String startTime, final String endTime)
			throws OAException {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/outboundMsg.xls",new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet){
				try {
					sheet.getRow(1).getCell(0).setCellValue(startTime+"/"+endTime);
					List<Map<String, Object>> data=outBoundDao.getExportOutbound(startTime,endTime);
					if(data!=null&&data.size()>0){
						int itemRowNum=3,itemNum=0,startRow,endRow;
						HSSFRow  itemRow;
						Map<String, Object> map;
						String[] productNames,clientNames,goodsNum,originalArea;
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
							originalArea=map.get("originalArea").toString().split(",");
							itemNum=productNames.length;
							itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("shipRefName")));
							itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("shipName")));
							itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
							itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("leaveTime")));
							itemRow.getCell(4).setCellValue(FormatUtils.getDoubleValue(map.get("loadCapacity")));
							itemRow.getCell(8).setCellValue(FormatUtils.getStringValue(map.get("shipAgentName")));
							itemRow.getCell(9).setCellValue(FormatUtils.getStringValue(map.get("berthName")));
						
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
	                        itemRow.getCell(10).setCellValue(originalArea[m]);
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
	                         }
						}
					}
				} catch (OAException e) {
					e.printStackTrace();
				}
			}
			}).getWorkbook();
	}


	
	
	
}
