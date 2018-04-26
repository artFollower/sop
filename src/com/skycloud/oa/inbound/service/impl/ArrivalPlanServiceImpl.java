package com.skycloud.oa.inbound.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalDao;
import com.skycloud.oa.inbound.dao.ArrivalInfoDao;
import com.skycloud.oa.inbound.dao.ArrivalPlanDao;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.InboundArrivalDao;
import com.skycloud.oa.inbound.dao.InitialFeeDao;
import com.skycloud.oa.inbound.dao.WorkDao;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.inbound.dto.InsertArrivalPlanDto;
import com.skycloud.oa.inbound.dto.InsertSingleArrivalPlanDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.ArrivalWork;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.InitialFee;
import com.skycloud.oa.inbound.model.Work;
import com.skycloud.oa.inbound.service.ArrivalPlanService;
import com.skycloud.oa.inbound.service.InboundOperationService;
import com.skycloud.oa.inbound.service.InitialFeeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ArrivalPlanServiceImpl implements ArrivalPlanService {
	private static Logger LOG = Logger.getLogger(ArrivalPlanServiceImpl.class);
	@Autowired
	private ArrivalPlanDao arrivalPlanDao;
	@Autowired
	private ArrivalDao arrivalDao;
	@Autowired
	private CargoGoodsDao cargoGoodsDao;
	@Autowired
	private InitialFeeDao initialFeeDao;
	@Autowired
	private WorkDao workDao;//入库作业表
	@Autowired
	private GoodsLogDao goodsLogDao;
	@Autowired
	private ArrivalInfoDao arrivalInfoDao;
	@Autowired
	private InboundArrivalDao inboundArrivalDao;
	
	
	@Autowired
	private InboundOperationService inboundOperationService;
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVAL,type=C.LOG_TYPE.CREATE)
	public OaMsg addArrivalPlan(InsertArrivalPlanDto arrivalPlanDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始插入到港计划表");
//			arrivalPlanDto.getArrival().setType(1);
			int id=arrivalDao.addIntoArrival(arrivalPlanDto.getArrival());
			
			ArrivalInfo arrivalInfo= new ArrivalInfo();
			arrivalInfo.setArrivalId(arrivalPlanDto.getArrival().getId());
			arrivalInfo.setReport("未申报");
			arrivalInfo.setShipInfo("未收到");
			InboundOperationDto iDto=new InboundOperationDto();
			Calendar a =Calendar.getInstance();
			iDto.setShipId(arrivalPlanDto.getArrival().getShipId());
			iDto.setResult(0);
			iDto.setStartTime(a.get(Calendar.YEAR)+"-01-01");
			arrivalInfo.setPortNum((inboundArrivalDao.getInboundArrivalList(iDto)+1)+"");
			arrivalInfoDao.addArrivalInfo(arrivalInfo);
			
			
			LOG.debug("插入成功");
			LOG.debug("开始插入船舶进港计划确认单");
			for(int i=0;i<arrivalPlanDto.getArrivalPlanList().size();i++){
				arrivalPlanDto.getArrivalPlanList().get(i).setArrivalId(id);
				arrivalPlanDto.getArrivalPlanList().get(i).setShipId(arrivalPlanDto.getArrival().getShipId());
				arrivalPlanDto.getArrivalPlanList().get(i).setArrivalStartTime(arrivalPlanDto.getArrival().getArrivalStartTime());
				arrivalPlanDto.getArrivalPlanList().get(i).setArrivalEndTime(arrivalPlanDto.getArrival().getArrivalEndTime());
				arrivalPlanDao.addArrivalPlan(arrivalPlanDto.getArrivalPlanList().get(i));
			}
			LOG.debug("插入成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", id+"");
			oaMsg.setMap(map);
			
			oaMsg.setMsg("插入成功");
		} catch (RuntimeException re) {
			LOG.error("插入失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
		}
		return oaMsg;
	}
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALPLAN,type=C.LOG_TYPE.CREATE)
	public OaMsg addSingleArrivalPlan(
			InsertSingleArrivalPlanDto singleArrivalPlanDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			LOG.debug("查询到港信息");
		Map<String,Object> arrival=arrivalDao.getArrivalById(singleArrivalPlanDto.getArrivalId());
		LOG.debug("查询成功");
		ArrivalPlan arrivalPlan=new ArrivalPlan();
		arrivalPlan.setArrivalId(singleArrivalPlanDto.getArrivalId());
		arrivalPlan.setClientId(singleArrivalPlanDto.getClientId());
		arrivalPlan.setCreateUserId(singleArrivalPlanDto.getCreateUserId());
		arrivalPlan.setGoodsTotal(singleArrivalPlanDto.getGoodsTotal());
		arrivalPlan.setProductId(singleArrivalPlanDto.getProductId());
		arrivalPlan.setRequirement(singleArrivalPlanDto.getRequirement());
		arrivalPlan.setTradeType(singleArrivalPlanDto.getTradeType());
		arrivalPlan.setIsCustomAgree(singleArrivalPlanDto.getIsCustomAgree());
		arrivalPlan.setIsDeclareCustom(singleArrivalPlanDto.getIsDeclareCustom());
		arrivalPlan.setCustomLading(singleArrivalPlanDto.getCustomLading());
		arrivalPlan.setCustomLadingCount(singleArrivalPlanDto.getCustomLadingCount());
		if(!Common.empty(arrival.get("arrivalStartTime"))){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				long time = df.parse(arrival.get("arrivalStartTime")+"").getTime();
				arrivalPlan.setArrivalStartTime(new Timestamp(time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!Common.empty(arrival.get("arrivalEndTime"))){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				long time = df.parse(arrival.get("arrivalEndTime")+"").getTime();
				arrivalPlan.setArrivalEndTime(new Timestamp(time));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(!Common.empty(arrival.get("shipAgentId"))){
			arrivalPlan.setShipAgentId((int)arrival.get("shipAgentId"));
		}
		if(!Common.empty(arrival.get("shipId"))){
			arrivalPlan.setShipId((int)arrival.get("shipId"));
		}
		if(!Common.empty(arrival.get("type"))){
			arrivalPlan.setType((int)arrival.get("type"));
		}
		arrivalPlan.setStatus(0);
		LOG.debug("添加单条货批信息");
		arrivalPlanDao.addArrivalPlan(arrivalPlan);
		LOG.debug("添加成功");
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		oaMsg.setMsg("插入成功");
	} catch (RuntimeException re) {
		LOG.error("插入失败",re);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
	}
		return oaMsg;
	}
	

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALPLAN,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteArrivalPlan(String ids) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始删除船舶进港计划确认单");
			arrivalPlanDao.deleteArrivalPlan(ids);
			LOG.debug("删除成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("删除成功");
		} catch (RuntimeException re) {
			LOG.error("删除失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "删除失败",re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALPLAN,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateArrivalPlan(ArrivalPlan arrivalPlan) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始修改船舶进港计划确认单");
			arrivalPlanDao.updateArrivalPlan(arrivalPlan);
			LOG.debug("修改成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("修改成功");
		} catch (RuntimeException re) {
			LOG.error("修改失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "修改失败",re);
		}
		return oaMsg;
	}

	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO,type=C.LOG_TYPE.CREATE)
	public OaMsg addCargo(String ids) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			String[] aIds=ids.split(",");
			for(int i=0;i<aIds.length;i++){
				Map<String,Object> checkArrivalPlan=arrivalPlanDao.getArrivalPlanById(Integer.parseInt(aIds[i]));
				
				if("2".equals(checkArrivalPlan.get("status").toString())){
					oaMsg.setCode(Constant.SYS_CODE_DISABLED);
					return oaMsg;
				}
			}
			
			
			
			for(int i=0;i<aIds.length;i++){
				
				LOG.debug("根据id获得计划表");
				Map<String,Object> arrivalPlan=arrivalPlanDao.getArrivalPlanById(Integer.parseInt(aIds[i]));
				LOG.debug("更新状态");
				arrivalPlanDao.updateArrivalPlanStatus(Integer.parseInt(aIds[i]),2);
				Cargo cargo=new Cargo();
				cargo.setClientId((int)arrivalPlan.get("clientId"));
				cargo.setGoodsPlan(""+arrivalPlan.get("goodsTotal"));
				cargo.setGoodsTotal(""+arrivalPlan.get("goodsTotal"));
				cargo.setOriginalArea(""+arrivalPlan.get("originalArea"));
				cargo.setProductId((int)arrivalPlan.get("productId"));
				cargo.setTaxType((int)arrivalPlan.get("tradeType"));
				cargo.setRequirement(arrivalPlan.get("requirement")+"");
				cargo.setArrivalId((int)arrivalPlan.get("arrivalId"));
				cargo.setGoodsCurrent("0");
				cargo.setGoodsInPass("0");
				cargo.setGoodsInspect("0");
				cargo.setGoodsOutPass("0");
				cargo.setGoodsShip("0");
				cargo.setGoodsTank("0");
				cargo.setIsDeclareCustom((int)arrivalPlan.get("isDeclareCustom"));
				cargo.setIsCustomAgree((int)arrivalPlan.get("isCustomAgree"));
				if(!Common.empty(arrivalPlan.get("customLading"))){
					cargo.setCustomLading(arrivalPlan.get("customLading")+"");
				}
				if(!Common.empty(arrivalPlan.get("customLadingCount"))){
					cargo.setCustomLadingCount(arrivalPlan.get("customLadingCount")+"");
				}
				LOG.debug("开始插入货批表");
				

				
//				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//					String date = format.format(new Date());
//					String iNo=date.replace("-", "");
//					LOG.debug(iNo);
					String No="No.";
					int NoCount=cargoGoodsDao.checkNo(No);
					String strNo="";
					if(NoCount<9){
						strNo="00000"+(NoCount+1);
					}else if(NoCount>=9&&NoCount<99){
						strNo="0000"+(NoCount+1);
					}else if(NoCount>=99&&NoCount<999){
						strNo="000"+(NoCount+1);
					}else if(NoCount>=999&&NoCount<9999){
						strNo="00"+(NoCount+1);
					}else if(NoCount>=9999&&NoCount<99999){
						strNo="0"+(NoCount+1);
					}else if(NoCount>=99999&&NoCount<999999){
						strNo=""+(NoCount+1);
					}
					
					No=No+strNo;
					cargo.setNo(No);
					cargo.setConnectNo(No);
				
				
				int cargoId=cargoGoodsDao.addIntoCargo(cargo);
				
				arrivalPlanDao.updateArrivalPlanCargoId(aIds[i],cargoId);
				
				//添加首期费
				InitialFee initialFee=new InitialFee();
				initialFee.setCargoId(cargoId);
				initialFeeDao.addInitialFee(initialFee);
				
				LOG.debug("插入成功");
				oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
				oaMsg.setMsg("插入成功");
			}
			
		} catch (RuntimeException re) {
			LOG.error("插入失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
		}
		
		return oaMsg;
	}

	@Override
	public OaMsg getArrivalList(ArrivalDto arrivalDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		try {
			LOG.debug("开始查询到港信息");
			List<Map<String,Object>> arrivalList=arrivalDao.getArrivalList(arrivalDto,pageView.getStartRecord(), pageView.getMaxresult());
//			for(int i=0;i<arrivalList.size();i++){
//				CargoGoodsDto cgDto=new CargoGoodsDto();
//				cgDto.setArrivalId((Integer) arrivalList.get(i).get("id"));
//				List<Map<String,Object>> cargoList=cargoGoodsDao.getCargoList(cgDto, 0, 0);
//				List<Map<String,Object>> arrivalPlanList=arrivalPlanDao.getArrivalPlanList((int) arrivalList.get(i).get("id"), 0, 0);
//				arrivalList.get(i).put("sureList", cargoList);
//				arrivalList.get(i).put("planList", arrivalPlanList);
//			}
			count=arrivalDao.getArrivalListCount(arrivalDto);
//			pageView.setTotalrecord(count);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
			oaMsg.getData().addAll(arrivalList);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVAL,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateArrival(Arrival arrival,String shipArrivalDraught, int isCreateInfo) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始修改到港信息");
			
			if(isCreateInfo==0){
				ArrivalInfo arrivalInfo= new ArrivalInfo();
				arrivalInfo.setArrivalId(arrival.getId());
				arrivalInfo.setReport("未申报");
				arrivalInfo.setShipInfo("未收到");
				InboundOperationDto iDto=new InboundOperationDto();
				Calendar a =Calendar.getInstance();
				iDto.setShipId(arrival.getShipId());
				iDto.setResult(0);
				iDto.setStartTime(a.get(Calendar.YEAR)+"-01-01");
				arrivalInfo.setPortNum((inboundArrivalDao.getInboundArrivalList(iDto)+1)+"");
				arrivalInfoDao.addArrivalInfo(arrivalInfo);
			}
			if(!Common.empty(shipArrivalDraught)){
				ArrivalInfo arrivalInfo=new ArrivalInfo();
				arrivalInfo.setArrivalId(arrival.getId());
				arrivalInfo.setShipArrivalDraught(Float.parseFloat(shipArrivalDraught));
				arrivalInfoDao.updateArrival(arrivalInfo);
			}
			
			
			Map<String, Object> arrivalData=arrivalDao.getArrivalById(arrival.getId());
			
			//判断类型是否改变
			if(arrival.getType()!=Integer.parseInt(arrivalData.get("type").toString())){
				//如果要改为通过
				if(arrival.getType()==3){
					//如果流程>靠泊方案，<入库完成，把流程=入库完成
					if(Integer.parseInt(arrivalData.get("workStatus").toString())>=5&&Integer.parseInt(arrivalData.get("workStatus").toString())<9){
						Work work=new Work();
						work.setArrivalId(arrival.getId());
						work.setStatus(9);
						workDao.updateWork(work);
					}
					//如果流程==入库完成
					else if(Integer.parseInt(arrivalData.get("workStatus").toString())==9){
						oaMsg.setCode(Constant.SYS_CODE_DISABLED);
						return oaMsg;
					}
					//如果通过要改为非通过
				}else if(arrival.getType()==1){
					//如果通过船的接卸已经完成，需要退回
					if(Integer.parseInt(arrivalData.get("workStatus").toString())==9){
						
						InboundOperationDto ioDto=new InboundOperationDto();
						ioDto.setArrivalId(arrival.getId());
						ioDto.setStatuskey(2+"");
						inboundOperationService.cleanToStatus(ioDto);
					}
				}
			}
			
			arrivalDao.updateArrival(arrival);
			//更新arrivalInfo的时间
			if(!Common.isNull(arrival.getId())&&arrival.getArrivalStartTime()!=null&&!Common.isNull(arrival.getArrivalStartTime().toString())){
				ArrivalInfo arrivalInfo=new ArrivalInfo();
				arrivalInfo.setArrivalId(arrival.getId());
				arrivalInfo.setAnchorTime(arrival.getArrivalStartTime().getTime()/1000);
				arrivalInfoDao.updateArrival(arrivalInfo);
			}
			LOG.debug("修改成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("修改成功");
		} catch (RuntimeException re) {
			LOG.error("修改失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "修改失败",re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVAL,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteArrival(String id) throws OAException {
		
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始删除到港信息");
			arrivalDao.deleteArrival(id);
			LOG.debug("删除成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("删除成功");
		} catch (RuntimeException re) {
			LOG.error("删除失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "删除失败",re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getArrivalPlanList(ArrivalPlanDto arrivalPlanDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询");
			List<Map<String,Object>> arrivalPlanList=arrivalPlanDao.getArrivalPlanList(arrivalPlanDto,pageView.getStartRecord(), pageView.getMaxresult());
			int count= arrivalPlanDao.getArrivalPlanListCount(arrivalPlanDto);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
			oaMsg.getData().addAll(arrivalPlanList);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getArrivalPlanList(ArrivalPlanDto aDto,OaMsg oaMsg)throws OAException{
		try{
		List<Map<String,Object>> arrivalPlanList=arrivalPlanDao.getArrivalPlanList1(aDto, 0, 0);
		oaMsg.getData().addAll(arrivalPlanList);
		LOG.debug("查询成功");
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		oaMsg.setMsg("查询成功");
	} catch (RuntimeException o) {
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		oaMsg.setMsg("系统繁忙,请稍后再试");
		LOG.error("查询失败", o);
	}
		return oaMsg;
	}

	@Override
	public OaMsg getArrivalSureList(int arrivalId,OaMsg oaMsg) {
		try{
			CargoGoodsDto cgDto=new CargoGoodsDto();
			cgDto.setArrivalId(arrivalId);
			List<Map<String,Object>> cargoList=cargoGoodsDao.getCargoList(cgDto, 0, 0);
			oaMsg.getData().addAll(cargoList);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (OAException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("查询失败", o);
		}
			return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO,type=C.LOG_TYPE.CREATE)
	public OaMsg addCargo(Cargo cargo,Integer tankId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
				cargo.setType(1);
				cargo.setGoodsPlan(cargo.getGoodsTotal());
				cargo.setStatus(cargo.getStatus());
				cargo.setGoodsCurrent(cargo.getGoodsTotal());
				cargo.setGoodsInPass("0");
				cargo.setGoodsInspect("0");
				cargo.setGoodsOutPass("0");
				cargo.setGoodsShip("0");
				cargo.setGoodsTank("0");
				LOG.debug("开始插入货批表");
				int cargoId=cargoGoodsDao.addIntoCargo(cargo);
				
				
				Goods goods =new Goods();
				goods.setCargoId(cargoId);
				goods.setCode(cargo.getCode()+" 1/1");
				if(!Common.isNull(cargo.getContractId())){
					goods.setContractId(cargo.getContractId());
				}
				goods.setClientId(cargo.getClientId());
				goods.setProductId(cargo.getProductId());
				goods.setCreateTime(new Timestamp(new Date().getTime()));
				goods.setGoodsTotal(cargo.getGoodsTotal());
				goods.setGoodsCurrent(cargo.getGoodsTotal());
				goods.setGoodsInPass("0");
				goods.setGoodsInspect("0");
				goods.setGoodsOutPass("0");
				goods.setGoodsTank("0");
				goods.setGoodsOut("0");
				goods.setGoodsIn("0");
				if(!Common.isNull(tankId)){
					goods.setTankId(tankId);
				}
				goods.setStatus(0);
				int goodsId=(Integer) cargoGoodsDao.addGoods(goods);
				
				
				//货体日志--入库
				GoodsLog goodsLogOld=new GoodsLog();
				goodsLogOld.setGoodsId(goodsId);
				goodsLogOld.setType(1);
				goodsLogOld.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
				goodsLogOld.setGoodsChange(Common.fixDouble(Double.parseDouble(goods.getGoodsTotal()),3));
				goodsLogOld.setClientId(goods.getClientId());
				goodsLogOld.setGoodsSave(Common.fixDouble(Double.parseDouble(goods.getGoodsTotal())-Math.min(Double.parseDouble(goods.getGoodsInPass()),Double.parseDouble(goods.getGoodsOutPass())),3));
				goodsLogOld.setSurplus(0);
				goodsLogDao.add(goodsLogOld);
				
				
				
				//添加首期费
				InitialFee initialFee=new InitialFee();
				initialFee.setCargoId(cargoId);
				initialFeeDao.addInitialFee(initialFee);
				
				LOG.debug("插入成功");
				oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
				oaMsg.setMsg("插入成功");
			
		} catch (RuntimeException re) {
			LOG.error("插入失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
		}
		
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateCargo(Cargo cargo) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("查询arrival");
			CargoGoodsDto cgDto=new CargoGoodsDto();
			cgDto.setCargoId(cargo.getId());
			LOG.debug("查询cargo");
			Map<String,Object> cg=cargoGoodsDao.getCargoList(cgDto, 0, 0).get(0);
			LOG.debug("查询成功");
			Cargo cargo1=new Cargo();
			cargo1.setId(cargo.getId());
			
			if(!Common.empty(cargo.getGoodsTotal())){
				if(!cargo.getGoodsTotal().equals(cg.get("goodsPlan")+"")){
					cargo1.setGoodsPlan(cargo.getGoodsTotal());
					
				}
			}
			
			if(!Common.isNull(cargo.getContractId())){
				if(cargo.getContractId()!=Integer.parseInt(cg.get("contractId")+"")){
					cargo1.setContractId(cargo.getContractId());
				}
			}

			if(!Common.isNull(cargo.getTaxType())){
				
				if(cargo.getTaxType()!=Integer.parseInt(cg.get("taxType")+"")){
					cargo1.setTaxType(cargo.getTaxType());
				}
			}
			
			
			
			//如果改变了客户,货批下所有货体都要变
			if(!Common.empty(cargo.getClientId())){
				if(cargo.getClientId()!=Integer.valueOf(cg.get("clientId").toString())){
					cargo1.setClientId(cargo.getClientId());
					
					CargoGoodsDto cgd=new CargoGoodsDto();
					cgd.setCargoId(cargo.getId());
					
					List<Map<String,Object>> goodsList= cargoGoodsDao.getGoodsList(cgd, 0, 0);
					boolean canChange=true;
					for(int i=0;i<goodsList.size();i++){
						//判断有没有交易过的货体
						if(!Common.empty(goodsList.get(i).get("goodsCurrent"))&&!Common.empty(goodsList.get(i).get("goodsTotal")))
						if(Double.valueOf(goodsList.get(i).get("goodsCurrent").toString())<Double.valueOf(goodsList.get(i).get("goodsTotal").toString())){
							canChange=false;
						}
						
					}
					
					if(canChange){
						
						for(int i=0;i<goodsList.size();i++){
							Goods goods=new Goods();
							goods.setId(Integer.parseInt(goodsList.get(i).get("id").toString()));
							goods.setClientId(cargo.getClientId());
							cargoGoodsDao.updateGoods(goods);
						}
						
					}else{
						//有已经交易过的货体，退回。
						oaMsg.setCode(Constant.SYS_CODE_CONFLICT);
						return oaMsg;
					}
					
					
					
				}
			}
			
			
			//如果改变了货品,货批下所有货体都要变
			if(!Common.empty(cargo.getProductId())){
				if(cargo.getProductId()!=Integer.valueOf(cg.get("productId").toString())){
					cargo1.setProductId(cargo.getProductId());
					
					CargoGoodsDto cgd=new CargoGoodsDto();
					cgd.setCargoId(cargo.getId());
					
					List<Map<String,Object>> goodsList= cargoGoodsDao.getGoodsList(cgd, 0, 0);
					boolean canChange=true;
					for(int i=0;i<goodsList.size();i++){
						//判断有没有交易过的货体
						if(!Common.empty(goodsList.get(i).get("goodsCurrent"))&&!Common.empty(goodsList.get(i).get("goodsTotal")))
						if(Double.valueOf(goodsList.get(i).get("goodsCurrent").toString())<Double.valueOf(goodsList.get(i).get("goodsTotal").toString())){
							canChange=false;
						}
						
					}
					
					if(canChange){
						
						for(int i=0;i<goodsList.size();i++){
							Goods goods=new Goods();
							goods.setId(Integer.parseInt(goodsList.get(i).get("id").toString()));
							goods.setProductId(cargo.getProductId());
							cargoGoodsDao.updateGoods(goods);
						}
						
					}else{
						//有已经交易过的货体，退回。
						oaMsg.setCode(Constant.SYS_CODE_CONFLICT);
						return oaMsg;
					}
					
					
					
				}
			}
			
			
			
			if(!Common.empty(cargo.getCode())){
				cargo1.setCode(cargo.getCode());
			}
			
			if(!Common.isNull(cargo.getClearanceClientId())){
				if(cargo.getClearanceClientId()!=Integer.parseInt(cg.get("clearanceClientId")+"")){
					cargo1.setClearanceClientId(cargo.getClearanceClientId());
				}
				
			}
			
			if(!Common.empty(cargo.getCustomLading())){
				cargo1.setCustomLading(cargo.getCustomLading());
			}
			if(!Common.empty(cargo.getCustomPassCount())){
				cargo1.setCustomPassCount(cargo.getCustomPassCount());
			}
			
			if(!Common.empty(cargo.getCustomLadingTime())){
				cargo1.setCustomLadingTime(cargo.getCustomLadingTime());
			}
			
			
			LOG.debug("开始更新货批表");
			cargoGoodsDao.updateCargo(cargo1);
			LOG.debug("更新成功");
		} catch (RuntimeException re) {
			LOG.error("操作失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg updateNo(String planId, String no) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			arrivalPlanDao.updateNo(planId,no);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("查询失败", o);
		}
			return oaMsg;
	}

	@Override
	public OaMsg updateCargoNo(String cargoId, String no) {
		OaMsg oaMsg=new OaMsg();
		try{
			arrivalPlanDao.updateCargoNo(cargoId,no);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("查询失败", o);
		}
			return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateCargoInboundNo(String cargoId, String no) {
		OaMsg oaMsg=new OaMsg();
		try{
			arrivalPlanDao.updateCargoinboundNo(cargoId,no);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("查询失败", o);
		}
			return oaMsg;
	}

	@Override
	public OaMsg getArrivalInfo(ArrivalDto arrivalDto) throws OAException
{
		OaMsg oaMsg=new OaMsg();
		int count=0;
		try {
			LOG.debug("开始查询到港信息");
			List<Map<String,Object>> arrivalList=arrivalDao.getArrivalInfo(arrivalDto);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
			oaMsg.getData().addAll(arrivalList);
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg updateArrivalInfo(ArrivalInfo arrivalInfo) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			arrivalInfoDao.updateArrival(arrivalInfo);
			LOG.debug("更新成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException o) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("系统繁忙,请稍后再试");
			LOG.error("更新失败", o);
		}
			return oaMsg;
	}



}
