package com.skycloud.oa.inbound.service.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.skycloud.oa.inbound.dao.ArrivalWorkDao;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.JobCheckDao;
import com.skycloud.oa.inbound.dao.TransportProgramDao;
import com.skycloud.oa.inbound.dao.WorkCheckDao;
import com.skycloud.oa.inbound.dao.WorkDao;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.dto.TransportProgramDto;
import com.skycloud.oa.inbound.model.ArrivalWork;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.service.ArrivalWorkService;
import com.skycloud.oa.order.dao.ContractDao;
import com.skycloud.oa.order.dto.ContractDto;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ArrivalWorkServiceImpl implements ArrivalWorkService {
	private static Logger LOG = Logger.getLogger(ArrivalWorkServiceImpl.class);
	@Autowired
	private ArrivalWorkDao arrivalWorkDao;
	@Autowired
	private ArrivalDao arrivalDao;
	@Autowired
	private CargoGoodsDao cargoGoodsDao;
	@Autowired
	private ContractDao contractDao;
	@Autowired
	private TransportProgramDao transportProgramDao;
	@Autowired
	private WorkDao workDao;
	@Autowired
	private JobCheckDao jobCheckDao;
	@Autowired
	private WorkCheckDao workCheckDao;
	@Autowired
	private ArrivalInfoDao arrivalInfoDao;
	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO,type=C.LOG_TYPE.UPDATE)
	public OaMsg addArrivalWork(String description,ArrivalWork arrivalWork,String cargoAgentId, String code,String inspectAgentIds,String inspectAgentNames, String passShipInspect) throws OAException {
		OaMsg oaMsg=new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			LOG.debug("查询arrival");
			Map<String,Object> arrival=arrivalDao.getArrivalById(arrivalWork.getArrivalId());
			CargoGoodsDto cgDto=new CargoGoodsDto();
			cgDto.setCargoId(arrivalWork.getCargoId());
			LOG.debug("查询cargo");
			Map<String,Object> cg=cargoGoodsDao.getCargoList(cgDto, 0, 0).get(0);
			LOG.debug("查询成功");
			Cargo cargo=new Cargo();
			if(!Common.empty(arrivalWork.getGoodsTotal())){
				if(!arrivalWork.getGoodsTotal().equals(cg.get("goodsPlan")+"")){
					cargo.setGoodsPlan(arrivalWork.getGoodsTotal());
//					cargo.setGoodsTotal(arrivalWork.getGoodsTotal());
					
//					Map<String,Object> workInfo=workDao.getWork(arrivalWork.getArrivalId(), Integer.parseInt(cg.get("productId").toString()));
					//如果在接卸方案之后，则退回到接卸方案未提交
//					if(Integer.parseInt(workInfo.get("status").toString())>5){
//						TransportProgramDto inDto=new TransportProgramDto();
//						inDto.setType(0);
//						inDto.setArrivalId(arrivalWork.getArrivalId());
//						inDto.setProductId(Integer.parseInt(cg.get("productId").toString()));
//						
//						int transportId=Integer.parseInt(transportProgramDao.getTransportProgramById(inDto).get("id").toString());
//						
//						TransportProgram transportProgram=new TransportProgram();
//						transportProgram.setId(transportId);
//						transportProgram.setStatus(0);
//						transportProgramDao.updateTransportProgram(transportProgram);
//						workDao.cleanWork(Integer.parseInt(workInfo.get("id").toString()),5+"");
//						jobCheckDao.cleanJobCheck(transportId,true);
//						//删除清空
//						workCheckDao.cleanWorkCheck(transportId,true);
//						cargoGoodsDao.cleanGoods(Integer.parseInt(cg.get("productId").toString()),true);
//						
//						
//						
//					}
					
					
					
					
					
					
					
				}
			}
			
			if(!Common.empty(arrivalWork.getOriginalArea())){
				if(!arrivalWork.getOriginalArea().equals(cg.get("originalArea"))){
					cargo.setOriginalArea(arrivalWork.getOriginalArea());
				}
			}
			if(!Common.empty(arrivalWork.getStorageType())){
				if(!arrivalWork.getStorageType().equals(cg.get("storageType"))){
					cargo.setStorageType(arrivalWork.getStorageType());
				}
			}
			
//			cargo.setClientId((int)cg.get("clientId"));
//			cargo.setProductId(arrivalWork.getProductId());
			if(!Common.empty(arrivalWork.getRequirement())){
				if(!arrivalWork.getRequirement().equals(cg.get("requirement"))){
					cargo.setRequirement(arrivalWork.getRequirement());
				}
			}
			cargo.setId(arrivalWork.getCargoId());
			if(!Common.empty(cargoAgentId)){
				cargo.setCargoAgentId(Integer.parseInt(cargoAgentId));
			}
			if(!Common.empty(inspectAgentIds)){
				cargo.setInspectAgentIds(inspectAgentIds);
			}
			if(!Common.empty(inspectAgentNames)){
				cargo.setInspectAgentNames(inspectAgentNames);
			}
			if(!Common.empty(passShipInspect)){
				cargo.setGoodsInspect(passShipInspect);
			}
			
			if(arrivalWork.getContractId()!=Integer.parseInt(cg.get("contractId")+"")){
				
				List<Map<String,Object>> cgoods=cargoGoodsDao.getGoodsList(cgDto, 0, 0);
				if(cgoods.size()>0){
					oaMsg.setCode("1004");
					Map<String,String> map=new HashMap<String,String>();
					map.put("cargoId", cgDto.getCargoId()+"");
					oaMsg.setMap(map);
				}
				cargo.setContractId(arrivalWork.getContractId());
			}
			
			if(arrivalWork.getTradeType()!=Integer.parseInt(cg.get("taxType")+"")){
				cargo.setTaxType(arrivalWork.getTradeType());
			}
			
			if(!Common.empty(description)){
				cargo.setDescription(description);
			}
			
			if(arrivalWork.getIsCustomAgree()!=null){
				cargo.setIsCustomAgree(arrivalWork.getIsCustomAgree());
			}
			if(arrivalWork.getIsDeclareCustom()!=null){
				cargo.setIsDeclareCustom(arrivalWork.getIsDeclareCustom());
			}
			if(!Common.empty(arrivalWork.getCustomLading())){
				cargo.setCustomLading(arrivalWork.getCustomLading());
			}
			if(!Common.empty(arrivalWork.getCustomLadingCount())){
				cargo.setCustomLadingCount(arrivalWork.getCustomLadingCount());
			}
			
			if(!code.equals(cg.get("code")+"")){
				int codeCount=cargoGoodsDao.checkCargoCode(code);
				if(codeCount>0){
					oaMsg.setCode(Constant.SYS_CODE_DISABLED);
					return oaMsg;
				}
				cargo.setCode(code);
				
				//改了货批号要修改原始货体货体号
				CargoGoodsDto cgd=new CargoGoodsDto();
				cgd.setCargoId(arrivalWork.getCargoId());
				
				List<Map<String,Object>> goodsList= cargoGoodsDao.getGoodsList(cgd, 0, 0);
				for(int i=0;i<goodsList.size();i++){
					Goods goods=new Goods();
					goods.setCode(cargo.getCode()+" "+(i+1)+"/"+goodsList.size());
					goods.setId(Integer.parseInt(goodsList.get(i).get("id").toString()));
					cargoGoodsDao.updateGoods(goods);
				}
				
			}
			
			//如果改变了客户,货批下所有货体都要变
			if(!Common.empty(arrivalWork.getClientId())){
				if(arrivalWork.getClientId()!=Integer.valueOf(cg.get("clientId").toString())){
					cargo.setClientId(arrivalWork.getClientId());
					
					CargoGoodsDto cgd=new CargoGoodsDto();
					cgd.setCargoId(arrivalWork.getCargoId());
					
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
							goods.setClientId(arrivalWork.getClientId());
							cargoGoodsDao.updateGoods(goods);
						}
						
					}else{
						//有已经交易过的货体，退回。
						oaMsg.setCode(Constant.SYS_CODE_CONFLICT);
						return oaMsg;
					}
					
					
					
				}
			}
			
//				LOG.debug("生成code");
//				ContractDto contractDto=new ContractDto();
//				contractDto.setId(cargo.getContractId());
//				String _code=(String) contractDao.getContractListByCondition(contractDto, 0, 0).get(0).get("code")+"HP";
//				int cargoCount=cargoGoodsDao.getTheSameCargo(_code);
//				String codeNumber = "";
//				if (cargoCount < 9) {
//					codeNumber = "0" + (cargoCount + 1);
//				} else if (cargoCount >= 9 && cargoCount < 99) {
//					codeNumber = "" + (cargoCount + 1);
//				}
//				_code+=codeNumber;
//				LOG.debug("code="+_code);
//				cargo.setCode(_code);
//			}
			
			LOG.debug("开始更新货批表");
			cargoGoodsDao.updateCargo(cargo);
			LOG.debug("更新成功");
			LOG.debug("开始插入船舶进港工作联系单");
			ArrivalWork marrivalWork=new ArrivalWork();
			if(!Common.empty(arrival.get("arrivalTime"))){
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					long time = df.parse(arrival.get("arrivalTime")+"").getTime();
					marrivalWork.setArricalTime(new Timestamp(time));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
//			marrivalWork.setArricalTime((Timestamp)arrival.get("arrivalTime"));
			marrivalWork.setArrivalId(arrivalWork.getArrivalId());
			marrivalWork.setCargoId(arrivalWork.getCargoId());
			if(!Common.empty(cg.get("clientId"))){
				marrivalWork.setClientId((int)cg.get("clientId"));
			}
			marrivalWork.setContractId(arrivalWork.getContractId());
			marrivalWork.setCreateTime(arrivalWork.getCreateTime());
			marrivalWork.setCreateUserId(arrivalWork.getCreateUserId());
			marrivalWork.setGoodsTotal(arrivalWork.getGoodsTotal());
			marrivalWork.setOriginalArea(arrivalWork.getOriginalArea());
			marrivalWork.setStorageType(arrivalWork.getStorageType());
			if(!Common.empty(cg.get("productId"))){
				marrivalWork.setProductId((int)cg.get("productId"));
			}
			marrivalWork.setRequirement(arrivalWork.getRequirement());
			if(!Common.empty(arrival.get("shipAgentId"))){
				marrivalWork.setShipAgentId((int)arrival.get("shipAgentId"));
			}
			if(!Common.empty(arrival.get("shipId"))){
				marrivalWork.setShipId((int)arrival.get("shipId"));
			}
			marrivalWork.setStatus(arrivalWork.getStatus());
			arrivalWorkDao.addArrivalWork(marrivalWork);
			LOG.debug("插入成功");
			
			oaMsg.setMsg("插入成功");
		} catch (RuntimeException re) {
			LOG.error("操作失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
		}
		return oaMsg;
	}

	
	@Override
	public OaMsg getTheCargoCode(ContractDto contractDto)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			String code=contractDto.getCode()+"HP";
			int cargoCount=cargoGoodsDao.getTheSameCargo(code,true);
			String codeNumber = "";
			if (cargoCount < 9) {
				codeNumber = "0" + (cargoCount + 1);
			} else if (cargoCount >= 9) {
				codeNumber = "" + (cargoCount + 1);
			}
			code+=codeNumber;
			LOG.debug("code="+code);
			Map<String,String> map=new HashMap<String, String>();
			map.put("code", code);
			oaMsg.setMap(map);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("操作失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
		}
		
		return oaMsg;
	}
	
	@Override
	public OaMsg checkCargoCode(String code) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			int count=cargoGoodsDao.getTheSameCargo(code,false);
			Map<String,String> map=new HashMap<String, String>();
			map.put("count", count+"");
			oaMsg.setMap(map);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("操作失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
		}
		return oaMsg;
	}
	@Override
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALWORK,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteArrivalWork(String ids) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始删除船舶进港工作联系单");
			arrivalWorkDao.deleteArrivalWork(ids);
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
	@Log(object=C.LOG_OBJECT.PCS_INBOUND_ARRIVALWORK,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateArrivalWork(ArrivalWork arrivalWork) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始更新船舶进港工作联系单");
			arrivalWorkDao.updateArrivalWork(arrivalWork);
			LOG.debug("更新成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException re) {
			LOG.error("更新失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",re);
		}
		return oaMsg;
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteCargo(Integer cargoId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始删除货批");
			
			int count=arrivalWorkDao.getTheSameProductCargo(cargoId);
			if(count==1){
				arrivalWorkDao.deleteWorkAndProgram(cargoId);
			}
			arrivalWorkDao.updateArrivalInfoToUnSend(cargoId);
			arrivalWorkDao.deleteCargo(cargoId);
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






}
