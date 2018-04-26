package com.skycloud.oa.inbound.service.impl;

import java.sql.Timestamp;
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
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.GraftingCargoDao;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.GraftingHistory;
import com.skycloud.oa.inbound.service.GraftingCargoService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.dto.GoodsLogDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.service.GoodsLogService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class GraftingCargoServiceImpl implements GraftingCargoService {
	private static Logger LOG = Logger.getLogger(GraftingCargoServiceImpl.class);
	@Autowired
	private GraftingCargoDao graftingCargoDao;
	@Autowired
	private CargoGoodsDao cargoGoodsDao;
	@Autowired
	private GoodsLogDao goodsLogDao;

	@Autowired
	private GoodsLogService goodsLogService;

	
	@Override
	public OaMsg getClient() throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询");
			oaMsg.getData().addAll(graftingCargoDao.getClient());
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}


	@Override
	public OaMsg getGraftingGoods(Goods goods) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询");
			oaMsg.getData().addAll(graftingCargoDao.getGraftingGoods(goods));
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}


	@Override
	public OaMsg toGraftingGoods(Goods goods) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询");
			oaMsg.getData().addAll(graftingCargoDao.toGraftingGoods(goods));
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.GRAFT)
	public OaMsg graftingAll(int virtualGoodsId, int realId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		
		CargoGoodsDto cDto=new CargoGoodsDto();
		cDto.setGoodsId(virtualGoodsId);
		//虚拟货体
		Map<String, Object> virtualGoods=  cargoGoodsDao.getGoodsList(cDto, 0, 0).get(0);
		
		CargoGoodsDto dDto=new CargoGoodsDto();
		dDto.setGoodsId(realId);
		//真实货体
		Map<String, Object> realGoods=  cargoGoodsDao.getGoodsList(dDto, 0, 0).get(0);
		//虚拟货批	
		CargoGoodsDto virCargoDto=new CargoGoodsDto();
		virCargoDto.setCargoId(Integer.parseInt(virtualGoods.get("cargoId").toString()));
		Map<String, Object> virtualCargo=  cargoGoodsDao.getCargoList(virCargoDto, 0, 0).get(0);
		
		
		//如果是原始货体
		if(Integer.parseInt(virtualGoods.get("rootGoodsId").toString())==0&&Integer.parseInt(virtualGoods.get("sourceGoodsId").toString())==0){
			//把所有下级货体的货批改成real货批
			
			graftingCargoDao.changeLogTime(virtualGoodsId,1,Common.empty(realGoods.get("tankId"))?"-1":realGoods.get("tankId").toString());
			graftingCargoDao.changeCargoId(virtualGoodsId,Integer.parseInt(virtualGoods.get("cargoId").toString()),Integer.parseInt(realGoods.get("cargoId").toString()),1);
			
			
			//如果真实货体原号=null 则真实货体为原始货体，不用改
			if(Integer.parseInt(realGoods.get("rootGoodsId").toString())==0&&Integer.parseInt(realGoods.get("sourceGoodsId").toString())==0){
				
			}else
			
			//如果真实货体是原号货体
			if(Integer.parseInt(realGoods.get("rootGoodsId").toString())==0){
				//修改下级货体的rootGoodsId=真实货体
				graftingCargoDao.updateRootGoodsId(virtualGoodsId,realId);
				
			}
			//否则是提单内货体
			else {
				//修改下级货体的rootGoodsId=真实货体的rootGoodsId
				graftingCargoDao.updateRootGoodsId(virtualGoodsId, Integer.parseInt(realGoods.get("rootGoodsId").toString()));
			}
			
			//修改下一级货体的调号为realId
			graftingCargoDao.updateSourceGoodsId(Integer.parseInt(virtualGoods.get("id").toString()),realId);
			
			//把虚拟货体除入库、封放、调入、调入退回之外的其他操作全部归real货体,并修改时间
			graftingCargoDao.changeLog(Integer.parseInt(virtualGoods.get("id").toString()),realId);
			
			
			
			//如果真实货体原号=null 则真实货体为原始货体，不用改
			if(Integer.parseInt(realGoods.get("rootGoodsId").toString())==0&&Integer.parseInt(realGoods.get("sourceGoodsId").toString())==0){
				
			}else{
				//修改操作日志ladingId
				graftingCargoDao.updateLogLadingId(realId);
				
			}
			
			
			
			//日志转移后修复真实货体日志。
			graftingCargoDao.repairLog(realId);
			graftingCargoDao.repairGoods(realId);
			
			
			//删除虚拟货体
			cargoGoodsDao.deleteGoods(virtualGoodsId+"");
			//如果货批数量=嫁接货体数量，则删除货批。
			if(Double.parseDouble(virtualCargo.get("goodsTotal").toString())==Double.parseDouble(virtualGoods.get("goodsTotal").toString())){
				cargoGoodsDao.deleteCargo(Integer.parseInt(virtualCargo.get("id").toString()));
			}
			//否则更新货批量
			else{
//				Cargo cargo=new Cargo();
//				cargo.setId(Integer.parseInt(virtualCargo.get("id").toString()));
//				
//				double virCargoTotal=Double.parseDouble(virtualCargo.get("goodsTotal").toString());
//				double virGoodsTotal=Double.parseDouble(virtualGoods.get("goodsTotal").toString());
//				double virCargoOutPass=Double.parseDouble(virtualCargo.get("goodsOutPass").toString());
//				cargo.setGoodsTotal(Common.fixDouble(virCargoTotal-virGoodsTotal, 3)+"");
//				cargo.setGoodsInPass(Common.fixDouble(virCargoOutPass-virGoodsTotal,3)+"");
//				cargo.setGoodsOutPass(Common.fixDouble(virCargoOutPass-virGoodsTotal,3)+"");
//				cargo.setId(Integer.parseInt(virtualGoods.get("cargoId").toString()));
//				cargoGoodsDao.updateCargo(cargo);
				graftingCargoDao.repairCargo(Integer.parseInt(virtualCargo.get("id").toString()));
				
			}
			
			
			
			
		}
		//如果是原号货体
		else if(Integer.parseInt(virtualGoods.get("rootGoodsId").toString())==0&&Integer.parseInt(virtualGoods.get("sourceGoodsId").toString())!=0){
			//把所有下级货体的货批改成real货批
			graftingCargoDao.changeLogTime(virtualGoodsId,2,Common.empty(realGoods.get("tankId"))?"-1":realGoods.get("tankId").toString());
			
			graftingCargoDao.changeCargoId(virtualGoodsId,Integer.parseInt(virtualGoods.get("cargoId").toString()),Integer.parseInt(realGoods.get("cargoId").toString()),2);
			
			Goods mGoods =new Goods();
			mGoods.setId(virtualGoodsId);
			mGoods.setSourceGoodsId(realId);
			mGoods.setCargoId(Integer.parseInt(realGoods.get("cargoId").toString()));
			
			//如果真实货体原号=null 则真实货体为原始货体
			if(Integer.parseInt(realGoods.get("rootGoodsId").toString())==0&&Integer.parseInt(realGoods.get("sourceGoodsId").toString())==0){
//				graftingCargoDao.updateRootGoodsId(virtualGoodsId);
				
			}else
			
			//如果真实货体是原号货体
			if(Integer.parseInt(realGoods.get("rootGoodsId").toString())==0){
				//修改下级货体的rootGoodsId=真实货体
				graftingCargoDao.updateRootGoodsIdByYTY(virtualGoodsId,realId);
				//货体本身的root=真实货体
				mGoods.setRootGoodsId(realId);
				
			}
			//否则是提单内货体
			else {
				//修改下级货体的rootGoodsId=真实货体的rootGoodsId
				graftingCargoDao.updateRootGoodsIdByYTY(virtualGoodsId, Integer.parseInt(realGoods.get("rootGoodsId").toString()));
				//货体本身的root=真实货体的root
				mGoods.setRootGoodsId(Integer.parseInt(realGoods.get("rootGoodsId").toString()));
			}
			
			//删除虚拟上级货体的相应调出.调出退回日志，修改入库，调入日志量并修复日志。
			graftingCargoDao.deleteOutLog(virtualGoodsId,Double.parseDouble(virtualGoods.get("goodsTotal").toString()));
			
			graftingCargoDao.updateRKDJLog(Integer.parseInt(virtualGoods.get("sourceGoodsId").toString()),Double.parseDouble(virtualGoods.get("goodsTotal").toString()));
			
			graftingCargoDao.repairLog(Integer.parseInt(virtualGoods.get("sourceGoodsId").toString()));

			
			cargoGoodsDao.updateGoods(mGoods);
			
			
			//创建真实货体的调出，调出退回操作(根据虚拟货体的调入，调入退回)，并修改时间
			graftingCargoDao.createOutLog(virtualGoodsId,realId);
			//更新真实货体
			graftingCargoDao.repairGoods(realId);
			
			
			//如果货批数量=嫁接货体数量，则删除货批。
			if(Double.parseDouble(virtualCargo.get("goodsTotal").toString())==Double.parseDouble(virtualGoods.get("goodsTotal").toString())){
				cargoGoodsDao.deleteCargo(Integer.parseInt(virtualCargo.get("id").toString()));
			}
			//否则修复上级货体，更新货批
			else{
				
				
//				Cargo cargo=new Cargo();
//				cargo.setId(Integer.parseInt(virtualCargo.get("id").toString()));
//				
//				double virCargoTotal=Double.parseDouble(virtualCargo.get("goodsTotal").toString());
//				double virGoodsTotal=Double.parseDouble(virtualGoods.get("goodsTotal").toString());
//				double virCargoOutPass=Double.parseDouble(virtualCargo.get("goodsOutPass").toString());
//				cargo.setGoodsTotal(Common.fixDouble(virCargoTotal-virGoodsTotal, 3)+"");
//				cargo.setGoodsInPass(Common.fixDouble(virCargoOutPass-virGoodsTotal,3)+"");
//				cargo.setGoodsOutPass(Common.fixDouble(virCargoOutPass-virGoodsTotal,3)+"");
//				cargo.setId(Integer.parseInt(virtualGoods.get("cargoId").toString()));
//				cargoGoodsDao.updateCargo(cargo);
				//更新虚拟上级货体
				graftingCargoDao.repairGoods(Integer.parseInt(virtualGoods.get("sourceGoodsId").toString()));
				graftingCargoDao.repairCargo(Integer.parseInt(virtualCargo.get("id").toString()));
				
				
//				CargoGoodsDto sDto=new CargoGoodsDto();
//				sDto.setGoodsId(Integer.parseInt(virtualGoods.get("sourceGoodsId").toString()));
				//虚拟货体上级货体
//				Map<String, Object> virtualSourceGoods=  cargoGoodsDao.getGoodsList(sDto, 0, 0).get(0);
				
				
//				double virtualSourceTotal=Double.parseDouble(virtualSourceGoods.get("goodsTotal").toString());
//				double virtualSourceOut=Double.parseDouble(virtualSourceGoods.get("goodsOut").toString());
				
//				double virtualSourceOutPass=Double.parseDouble(virtualSourceGoods.get("goodsOutPass").toString());
				
				
//				Goods goods=new Goods();
//				goods.setId(Integer.parseInt(virtualGoods.get("sourceGoodsId").toString()));
//				goods.setGoodsOutPass(Common.fixDouble(virtualSourceOutPass-virGoodsTotal,3)+"");
//				goods.setGoodsInPass(Common.fixDouble(virtualSourceOutPass-virGoodsTotal,3)+"");
////				goods.setGoodsTotal(Common.fixDouble(virtualSourceTotal-virGoodsTotal,3)+"");
////				goods.setGoodsOut(Common.fixDouble(virtualSourceOut-virGoodsTotal,3)+"");
//				
//				cargoGoodsDao.updateGoods(goods);
//				
				
			}
			
			
			
			
		}
		//更新真实货体
		graftingCargoDao.repairLading(virtualGoodsId,realId);
		graftingCargoDao.updateLogLadingId(virtualGoodsId,realId);
		
		
		return oaMsg;
	}


	@Override
	public int sqlitGoods(int virtualGoodsId, double count) throws OAException {
		try {
//			count
			CargoGoodsDto cDto=new CargoGoodsDto();
			cDto.setGoodsId(virtualGoodsId);
			//虚拟货体
			Map<String, Object> virtualGoods=  cargoGoodsDao.getGoodsList(cDto, 0, 0).get(0);
			
//			//虚拟货批	
//			CargoGoodsDto virCargoDto=new CargoGoodsDto();
//			virCargoDto.setCargoId(Integer.parseInt(virtualGoods.get("cargoId").toString()));
//			Map<String, Object> virtualCargo=  cargoGoodsDao.getCargoList(virCargoDto, 0, 0).get(0);
			
			List<Map<String,Object>> logData=goodsLogDao.getGoodsLog(virtualGoodsId+"");
			
			//如果是原始货体
			if(Integer.parseInt(virtualGoods.get("rootGoodsId").toString())==0&&Integer.parseInt(virtualGoods.get("sourceGoodsId").toString())==0){
				double oldGoodsPass=Double.parseDouble(virtualGoods.get("goodsOutPass").toString());
				double oldGoodsTotal=Double.parseDouble(virtualGoods.get("goodsTotal").toString());
				
				//如果原货体放行量>拆分量,则创建新货体并全放行，记录日志
				if(oldGoodsPass>=count){
					Goods newGoods=new Goods();
					newGoods.setCargoId(Integer.parseInt(virtualGoods.get("cargoId").toString()));
					newGoods.setClientId(Integer.parseInt(virtualGoods.get("clientId").toString()));
					newGoods.setCode(virtualGoods.get("code").toString()+"  -1");
					newGoods.setContractId(Integer.parseInt(virtualGoods.get("contractId").toString()));
					newGoods.setProductId(Integer.parseInt(virtualGoods.get("productId").toString()));
					newGoods.setGoodsInPass(count+"");
					newGoods.setGoodsOutPass(count+"");
					newGoods.setGoodsTotal(count+"");
					
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("goodsGroupId").toString()))){
						
						newGoods.setGoodsGroupId(Integer.parseInt(virtualGoods.get("goodsGroupId").toString()));
					}
					
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("tankId").toString()))){
						
						newGoods.setTankId(Integer.parseInt(virtualGoods.get("tankId").toString()));
					}
					int newgoodsId=(Integer) cargoGoodsDao.addGoods(newGoods);
					
					GoodsLog goodsLogNew=new GoodsLog();
					goodsLogNew.setGoodsId(newgoodsId);
					goodsLogNew.setType(1);
					goodsLogNew.setCreateTime(Long.parseLong(logData.get(0).get("createTime").toString()));
					goodsLogNew.setGoodsChange(count);
					goodsLogNew.setClientId(Integer.parseInt(virtualGoods.get("clientId").toString()));
					goodsLogNew.setGoodsSave(count);
					goodsLogNew.setSurplus(0);
					goodsLogDao.add(goodsLogNew);
					
					GoodsLog goodsLogNew2=new GoodsLog();
					goodsLogNew2.setGoodsId(newgoodsId);
					goodsLogNew2.setType(4);
					goodsLogNew2.setCreateTime(Long.parseLong(logData.get(0).get("createTime").toString())+10);
					goodsLogNew2.setGoodsChange(0);
					goodsLogNew2.setClientId(Integer.parseInt(virtualGoods.get("clientId").toString()));
					goodsLogNew2.setGoodsSave(0);
					goodsLogNew2.setSurplus(count);
					goodsLogDao.add(goodsLogNew2);
					
					//修复原货体入库封放记录
					
					graftingCargoDao.updateRKDJLog(virtualGoodsId,count);
					
					//按照日志顺序拆分
					splitLogAndGoods(virtualGoodsId,newgoodsId,count);
					
					graftingCargoDao.repairLog(virtualGoodsId);
					graftingCargoDao.repairGoods(virtualGoodsId);
					graftingCargoDao.repairLog(newgoodsId);
					graftingCargoDao.repairGoods(newgoodsId);
					
					return newgoodsId;
				}
				//否则原货体放行量<拆分量，新货体量=总量-拆分量，全部封
				else{
					
					Goods newGoods=new Goods();
					newGoods.setCargoId(Integer.parseInt(virtualGoods.get("cargoId").toString()));
					newGoods.setClientId(Integer.parseInt(virtualGoods.get("clientId").toString()));
					newGoods.setCode(virtualGoods.get("code").toString()+"  -1");
					newGoods.setContractId(Integer.parseInt(virtualGoods.get("contractId").toString()));
					newGoods.setProductId(Integer.parseInt(virtualGoods.get("productId").toString()));
					newGoods.setGoodsInPass("0");
					newGoods.setGoodsOutPass("0");
					newGoods.setGoodsTotal(Common.fixDouble((oldGoodsTotal-count),3)+"");
					newGoods.setGoodsCurrent(newGoods.getGoodsTotal());
					
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("goodsGroupId").toString()))){
						
						newGoods.setGoodsGroupId(Integer.parseInt(virtualGoods.get("goodsGroupId").toString()));
					}
					
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("tankId").toString()))){
						
						newGoods.setTankId(Integer.parseInt(virtualGoods.get("tankId").toString()));
					}
					int newgoodsId=(Integer) cargoGoodsDao.addGoods(newGoods);
					
					
					GoodsLog goodsLogNew=new GoodsLog();
					goodsLogNew.setGoodsId(newgoodsId);
					goodsLogNew.setType(1);
					goodsLogNew.setCreateTime(Long.parseLong(logData.get(0).get("createTime").toString()));
					goodsLogNew.setGoodsChange(Double.parseDouble(newGoods.getGoodsTotal()));
					goodsLogNew.setClientId(Integer.parseInt(virtualGoods.get("clientId").toString()));
					goodsLogNew.setGoodsSave(Double.parseDouble(newGoods.getGoodsTotal()));
					goodsLogNew.setSurplus(0);
					goodsLogDao.add(goodsLogNew);
					
					//修复原货体入库封放记录,修复日志，修复货体
					graftingCargoDao.updateRKFF(virtualGoodsId,newGoods.getGoodsTotal());
					graftingCargoDao.repairLog(virtualGoodsId);
					graftingCargoDao.repairGoods(virtualGoodsId);

					return virtualGoodsId;
					
				}
				
			}//如果是原号货体
			else if(Integer.parseInt(virtualGoods.get("rootGoodsId").toString())==0&&Integer.parseInt(virtualGoods.get("sourceGoodsId").toString())!=0){
				double oldGoodsPass=Double.parseDouble(virtualGoods.get("goodsOutPass").toString());
				double oldGoodsTotal=Double.parseDouble(virtualGoods.get("goodsTotal").toString());
				
				//如果原货体放行量>拆分量,则创建新货体并全放行，记录日志
				if(oldGoodsPass>=count){
					Goods newGoods=new Goods();
					newGoods.setCargoId(Integer.parseInt(virtualGoods.get("cargoId").toString()));
					newGoods.setClientId(Integer.parseInt(virtualGoods.get("clientId").toString()));
					newGoods.setCode(virtualGoods.get("code").toString()+"  -1");
					newGoods.setContractId(Integer.parseInt(virtualGoods.get("contractId").toString()));
					newGoods.setProductId(Integer.parseInt(virtualGoods.get("productId").toString()));
					newGoods.setGoodsInPass(count+"");
					newGoods.setGoodsOutPass(count+"");
					newGoods.setGoodsTotal(count+"");
					newGoods.setSourceGoodsId(Integer.parseInt(virtualGoods.get("sourceGoodsId").toString()));
					newGoods.setRootGoodsId(0);
					
					
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("goodsGroupId").toString()))){
						
						newGoods.setGoodsGroupId(Integer.parseInt(virtualGoods.get("goodsGroupId").toString()));
					}
					
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("tankId").toString()))){
						
						newGoods.setTankId(Integer.parseInt(virtualGoods.get("tankId").toString()));
					}
					int newgoodsId=(Integer) cargoGoodsDao.addGoods(newGoods);
					
					GoodsLog goodsLogNew=new GoodsLog();
					goodsLogNew.setGoodsId(newgoodsId);
					goodsLogNew.setType(2);
					goodsLogNew.setCreateTime(Long.parseLong(logData.get(0).get("createTime").toString()));
					goodsLogNew.setGoodsChange(count);
					goodsLogNew.setClientId(Integer.parseInt(virtualGoods.get("clientId").toString()));
					goodsLogNew.setGoodsSave(count);
					goodsLogNew.setSurplus(0);
					
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("zladingId").toString()))){
						
						goodsLogNew.setLadingId(Integer.parseInt(virtualGoods.get("zladingId").toString()));
					}
					
					goodsLogDao.add(goodsLogNew);
					
					GoodsLog goodsLogNew2=new GoodsLog();
					goodsLogNew2.setGoodsId(newgoodsId);
					goodsLogNew2.setType(4);
					goodsLogNew2.setCreateTime(Long.parseLong(logData.get(0).get("createTime").toString())+10);
					goodsLogNew2.setGoodsChange(0);
					goodsLogNew2.setClientId(Integer.parseInt(virtualGoods.get("clientId").toString()));
					goodsLogNew2.setGoodsSave(0);
					goodsLogNew2.setSurplus(count);
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("zladingId").toString()))){
						
						goodsLogNew2.setLadingId(Integer.parseInt(virtualGoods.get("zladingId").toString()));
					}
					goodsLogDao.add(goodsLogNew2);
					
					//修复原货体入库封放记录
					
					graftingCargoDao.updateRKDJLog(virtualGoodsId,count);
					
					//按照日志顺序拆分
					
					splitLogAndGoods(virtualGoodsId,newgoodsId,count);
					
					//拆分上级调出日志
					graftingCargoDao.splitSourceOutLog(virtualGoodsId,count,newgoodsId);
					graftingCargoDao.repairLog(virtualGoodsId);
					graftingCargoDao.repairGoods(virtualGoodsId);
					graftingCargoDao.repairLog(newgoodsId);
					graftingCargoDao.repairGoods(newgoodsId);

					return newgoodsId;
				}
				//否则原货体放行量<拆分量，新货体量=总量-拆分量，全部封
				else{
					
					Goods newGoods=new Goods();
					newGoods.setCargoId(Integer.parseInt(virtualGoods.get("cargoId").toString()));
					newGoods.setClientId(Integer.parseInt(virtualGoods.get("clientId").toString()));
					newGoods.setCode(virtualGoods.get("code").toString()+"  -1");
					newGoods.setContractId(Integer.parseInt(virtualGoods.get("contractId").toString()));
					newGoods.setProductId(Integer.parseInt(virtualGoods.get("productId").toString()));
					newGoods.setGoodsInPass("0");
					newGoods.setGoodsOutPass("0");
					newGoods.setGoodsCurrent(Common.fixDouble((oldGoodsTotal-count),3)+"");
					newGoods.setGoodsTotal(Common.fixDouble((oldGoodsTotal-count),3)+"");
					
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("goodsGroupId").toString()))){
						
						newGoods.setGoodsGroupId(Integer.parseInt(virtualGoods.get("goodsGroupId").toString()));
					}
					
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("tankId").toString()))){
						
						newGoods.setTankId(Integer.parseInt(virtualGoods.get("tankId").toString()));
					}
					newGoods.setSourceGoodsId(Integer.parseInt(virtualGoods.get("sourceGoodsId").toString()));
					newGoods.setRootGoodsId(0);
					
					int newgoodsId=(Integer) cargoGoodsDao.addGoods(newGoods);
					
					
					
					GoodsLog goodsLogNew=new GoodsLog();
					goodsLogNew.setGoodsId(newgoodsId);
					goodsLogNew.setType(2);
					goodsLogNew.setCreateTime(Long.parseLong(logData.get(0).get("createTime").toString()));
					goodsLogNew.setGoodsChange(Double.parseDouble(newGoods.getGoodsTotal()));
					goodsLogNew.setClientId(Integer.parseInt(virtualGoods.get("clientId").toString()));
					goodsLogNew.setGoodsSave(Double.parseDouble(newGoods.getGoodsTotal()));
					if(!Common.isNull(Integer.parseInt(virtualGoods.get("zladingId").toString()))){
						
						goodsLogNew.setLadingId(Integer.parseInt(virtualGoods.get("zladingId").toString()));
					}
					goodsLogNew.setSurplus(0);
					
					goodsLogDao.add(goodsLogNew);
					
					//修复原货体入库封放记录,修复日志，修复货体
					graftingCargoDao.updateRKFF(virtualGoodsId,newGoods.getGoodsTotal());
					graftingCargoDao.repairLog(virtualGoodsId);
					graftingCargoDao.repairGoods(virtualGoodsId);

					//拆分上级调出日志
					graftingCargoDao.splitSourceOutLog(virtualGoodsId,count,newgoodsId);
					return virtualGoodsId;
					
				}
				
				
				
			}
			
			
			
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return 0;
	}


	private void splitLogAndGoods(int virtualGoodsId, int newgoodsId,
			double count)throws OAException  {
		double mCount=Common.fixDouble(count,3);
		CargoGoodsDto cDto=new CargoGoodsDto();
		cDto.setGoodsId(virtualGoodsId);
		//原货体
		Map<String, Object> oldGoodsData=  cargoGoodsDao.getGoodsList(cDto, 0, 0).get(0);
		
		try {
			List<Map<String,Object>> logData=goodsLogDao.getGoodsGraftingLog(virtualGoodsId+"");
			
			for(int i=0;i<logData.size();i++){
				if(mCount!=0){
				//如果类型是调出
				if(Integer.parseInt(logData.get(i).get("type").toString())==3){
					//查询有没有退回的
					List<Map<String,Object>> backLog=graftingCargoDao.getTrueOutCountLog(Integer.parseInt(logData.get(i).get("id").toString()));
					//如果有
					double trueOutCount=0;
					if(backLog.size()>0){
						//真实调出量=这条调出记录+退回的。
						 trueOutCount=Common.fixDouble(Double.parseDouble(backLog.get(0).get("goodsChange").toString())+Double.parseDouble(backLog.get(0).get("backCount").toString()),3);
				
						
					}
					//否则真实调出量=这条调出量。
					else{
						trueOutCount=Double.parseDouble(logData.get(i).get("goodsChange").toString());
					}
					
					//如果量没扣完
					if(mCount+trueOutCount>=0){
						mCount=Common.fixDouble(mCount+trueOutCount, 3);
						//把这条记录和退回记录的goodsId=newgoodsId
						graftingCargoDao.graftingOutLog(Integer.parseInt(logData.get(i).get("id").toString()),newgoodsId);
						//下级的对应货体的上级货体id=newgoodsId
//						if(virtualGoodsId==103820){
//							System.out.println("newid--"+newgoodsId+"   virid="+virtualGoodsId);
//						}
						graftingCargoDao.updateNextGoodsSourceRoot(virtualGoodsId,newgoodsId,Integer.parseInt(logData.get(i).get("nextLadingId").toString()));
						//如果原货体是原始货体
						if(Integer.parseInt(oldGoodsData.get("rootGoodsId").toString())==0&&Integer.parseInt(oldGoodsData.get("sourceGoodsId").toString())==0){
							graftingCargoDao.updateRootGoodsIdByProduce(Integer.parseInt(logData.get(i).get("nextGoodsId").toString()));
							
						}
						//如果原货体是原号货体
						else if(Integer.parseInt(oldGoodsData.get("rootGoodsId").toString())==0&&Integer.parseInt(oldGoodsData.get("sourceGoodsId").toString())!=0){
							graftingCargoDao.updateRootGoodsIdByProduce(Integer.parseInt(oldGoodsData.get("id").toString()));
							
						}
							
						
					}
					//否则，拆分这条调出,下级同步生成一个货体
					else{
						
						//拆分调出日志,拆出来的日志归到新货体
						int logId=graftingCargoDao.splitOutLog(Integer.parseInt(logData.get(i).get("id").toString()),newgoodsId,mCount);
						
						//下级对应货体
//						Map<String,Object> nextGoods=graftingCargoDao.getNextGoods(virtualGoodsId,Integer.parseInt(logData.get(i).get("nextLadingId").toString())).get(0);
						CargoGoodsDto nDto=new CargoGoodsDto();
						nDto.setGoodsId(Integer.parseInt(logData.get(i).get("nextGoodsId").toString()));
						Map<String,Object> nextGoods=cargoGoodsDao.getGoodsList(nDto, 0, 0).get(0);
						
						//如果是原号货体
						 if(Integer.parseInt(nextGoods.get("rootGoodsId").toString())==0&&Integer.parseInt(nextGoods.get("sourceGoodsId").toString())!=0){
							double oldGoodsPass=Double.parseDouble(nextGoods.get("goodsOutPass").toString());
							double oldGoodsTotal=Double.parseDouble(nextGoods.get("goodsTotal").toString());
							
							//如果原货体放行量>拆分量,则创建新货体并全放行，记录日志
							if(oldGoodsPass>=mCount){
								mCount=Common.fixDouble(mCount, 3);
								Goods newGoods=new Goods();
								newGoods.setCargoId(Integer.parseInt(nextGoods.get("cargoId").toString()));
								newGoods.setClientId(Integer.parseInt(nextGoods.get("clientId").toString()));
								newGoods.setCode(nextGoods.get("code").toString()+"  -1");
								newGoods.setContractId(Integer.parseInt(nextGoods.get("contractId").toString()));
								newGoods.setProductId(Integer.parseInt(nextGoods.get("productId").toString()));
								newGoods.setGoodsInPass(mCount+"");
								newGoods.setGoodsOutPass(mCount+"");
								newGoods.setGoodsTotal(mCount+"");
								newGoods.setSourceGoodsId(newgoodsId);
								newGoods.setRootGoodsId(0);
								
								if(!Common.isNull(Integer.parseInt(nextGoods.get("goodsGroupId").toString()))){
									
									newGoods.setGoodsGroupId(Integer.parseInt(nextGoods.get("goodsGroupId").toString()));
								}
								
								if(!Common.isNull(Integer.parseInt(nextGoods.get("tankId").toString()))){
									
									newGoods.setTankId(Integer.parseInt(nextGoods.get("tankId").toString()));
								}
								
								
								int mNewGoodsId=(Integer) cargoGoodsDao.addGoods(newGoods);
								
								//更新上级货体调出的nextGoods
								graftingCargoDao.updateLogNextGoods(logId,mNewGoodsId);
								
								
								GoodsLog goodsLogNew=new GoodsLog();
								goodsLogNew.setGoodsId(mNewGoodsId);
								goodsLogNew.setType(2);
								goodsLogNew.setCreateTime(Long.parseLong(logData.get(i).get("createTime").toString()));
								goodsLogNew.setGoodsChange(mCount);
								goodsLogNew.setClientId(Integer.parseInt(nextGoods.get("clientId").toString()));
								goodsLogNew.setGoodsSave(mCount);
								goodsLogNew.setSurplus(0);
								
								if(!Common.isNull(Integer.parseInt(nextGoods.get("zladingId").toString()))){
									
									goodsLogNew.setLadingId(Integer.parseInt(nextGoods.get("zladingId").toString()));
								}
								goodsLogDao.add(goodsLogNew);
								
								GoodsLog goodsLogNew2=new GoodsLog();
								goodsLogNew2.setGoodsId(mNewGoodsId);
								goodsLogNew2.setType(4);
								goodsLogNew2.setCreateTime(Long.parseLong(logData.get(0).get("createTime").toString())+10);
								goodsLogNew2.setGoodsChange(0);
								goodsLogNew2.setClientId(Integer.parseInt(nextGoods.get("clientId").toString()));
								goodsLogNew2.setGoodsSave(0);
								goodsLogNew2.setSurplus(mCount);
								if(!Common.isNull(Integer.parseInt(nextGoods.get("zladingId").toString()))){
									
									goodsLogNew2.setLadingId(Integer.parseInt(nextGoods.get("zladingId").toString()));
								}
								goodsLogDao.add(goodsLogNew2);
								
								//修复原货体入库封放记录
								
								graftingCargoDao.updateRKDJLog(Integer.parseInt(nextGoods.get("id").toString()),mCount);
								
								//按照日志顺序拆分
								
								splitLogAndGoods(Integer.parseInt(nextGoods.get("id").toString()),mNewGoodsId,mCount);
								graftingCargoDao.repairLog(Integer.parseInt(nextGoods.get("id").toString()));
								graftingCargoDao.repairGoods(Integer.parseInt(nextGoods.get("id").toString()));
								graftingCargoDao.repairLog(mNewGoodsId);
								graftingCargoDao.repairGoods(mNewGoodsId);


							}
							//否则原货体放行量<拆分量，新货体量=总量-拆分量，全部封
							else{
								
								Goods newGoods=new Goods();
								newGoods.setCargoId(Integer.parseInt(nextGoods.get("cargoId").toString()));
								newGoods.setClientId(Integer.parseInt(nextGoods.get("clientId").toString()));
								newGoods.setCode(nextGoods.get("code").toString()+"  -1");
								newGoods.setContractId(Integer.parseInt(nextGoods.get("contractId").toString()));
								newGoods.setProductId(Integer.parseInt(nextGoods.get("productId").toString()));
								newGoods.setGoodsInPass("0");
								newGoods.setGoodsOutPass("0");
								newGoods.setGoodsCurrent(Common.fixDouble((oldGoodsTotal-mCount),3)+"");
								newGoods.setGoodsTotal(Common.fixDouble((oldGoodsTotal-mCount),3)+"");
								
								if(!Common.isNull(Integer.parseInt(nextGoods.get("goodsGroupId").toString()))){
									
									newGoods.setGoodsGroupId(Integer.parseInt(nextGoods.get("goodsGroupId").toString()));
								}
								if(!Common.isNull(Integer.parseInt(nextGoods.get("tankId").toString()))){
									
									newGoods.setTankId(Integer.parseInt(nextGoods.get("tankId").toString()));
								}
								newGoods.setSourceGoodsId(Integer.parseInt(nextGoods.get("sourceGoodsId").toString()));
								newGoods.setRootGoodsId(0);
								
								int mNewgoodsId=(Integer) cargoGoodsDao.addGoods(newGoods);
								//更新上级货体调出的nextGoods
								graftingCargoDao.updateLogNextGoods(Integer.parseInt(logData.get(i).get("id").toString()),mNewgoodsId);
								Goods oldGoods=new Goods();
								oldGoods.setId(Integer.parseInt(nextGoods.get("id").toString()));
								oldGoods.setSourceGoodsId(newgoodsId);
								cargoGoodsDao.updateGoods(oldGoods);
								
								
								GoodsLog goodsLogNew=new GoodsLog();
								goodsLogNew.setGoodsId(mNewgoodsId);
								goodsLogNew.setType(2);
								goodsLogNew.setCreateTime(Long.parseLong(logData.get(i).get("createTime").toString()));
								goodsLogNew.setGoodsChange(Double.parseDouble(newGoods.getGoodsTotal()));
								goodsLogNew.setClientId(Integer.parseInt(nextGoods.get("clientId").toString()));
								goodsLogNew.setGoodsSave(Double.parseDouble(newGoods.getGoodsTotal()));
								goodsLogNew.setSurplus(0);
								if(!Common.isNull(Integer.parseInt(nextGoods.get("zladingId").toString()))){
									
									goodsLogNew.setLadingId(Integer.parseInt(nextGoods.get("zladingId").toString()));
								}
								goodsLogDao.add(goodsLogNew);
								
								//修复原货体入库封放记录,修复日志，修复货体
								graftingCargoDao.updateRKFF(Integer.parseInt(nextGoods.get("id").toString()),newGoods.getGoodsTotal());
								graftingCargoDao.repairLog(Integer.parseInt(nextGoods.get("id").toString()));
								graftingCargoDao.repairGoods(Integer.parseInt(nextGoods.get("id").toString()));
								graftingCargoDao.repairLog(mNewgoodsId);
								graftingCargoDao.repairGoods(mNewgoodsId);
								
							}
							
							
							
						}
						 //否则如果是提单内货体
						 else if(Integer.parseInt(nextGoods.get("rootGoodsId").toString())!=0&&Integer.parseInt(nextGoods.get("sourceGoodsId").toString())!=0){
								double oldGoodsPass=Double.parseDouble(nextGoods.get("goodsOutPass").toString());
								double oldGoodsTotal=Double.parseDouble(nextGoods.get("goodsTotal").toString());
								
								//如果原货体放行量>拆分量,则创建新货体并全放行，记录日志
								if(oldGoodsPass>=mCount){
									Goods newGoods=new Goods();
									newGoods.setCargoId(Integer.parseInt(nextGoods.get("cargoId").toString()));
									newGoods.setClientId(Integer.parseInt(nextGoods.get("clientId").toString()));
									newGoods.setCode(nextGoods.get("code").toString()+"  -1");
									newGoods.setContractId(Integer.parseInt(nextGoods.get("contractId").toString()));
									newGoods.setProductId(Integer.parseInt(nextGoods.get("productId").toString()));
									newGoods.setGoodsInPass(mCount+"");
									newGoods.setGoodsOutPass(mCount+"");
									newGoods.setGoodsTotal(mCount+"");
									newGoods.setSourceGoodsId(newgoodsId);
									
									CargoGoodsDto dto=new CargoGoodsDto();
									dto.setGoodsId(newgoodsId);
									Map<String,Object> newg=cargoGoodsDao.getGoodsList(dto, 0, 0).get(0);
									
									if(Integer.parseInt(newg.get("rootGoodsId").toString())==0){
										newGoods.setRootGoodsId(newgoodsId);
										
									}else{
										newGoods.setRootGoodsId(Integer.parseInt(newg.get("rootGoodsId").toString()));
										
									}
									
									
									
									if(!Common.isNull(Integer.parseInt(nextGoods.get("goodsGroupId").toString()))){
										
										newGoods.setGoodsGroupId(Integer.parseInt(nextGoods.get("goodsGroupId").toString()));
									}
									
									if(!Common.isNull(Integer.parseInt(nextGoods.get("tankId").toString()))){
										
										newGoods.setTankId(Integer.parseInt(nextGoods.get("tankId").toString()));
									}
									
									
									int mNewGoodsId=(Integer) cargoGoodsDao.addGoods(newGoods);
									//更新上级货体调出的nextGoods
									graftingCargoDao.updateLogNextGoods(logId,mNewGoodsId);
									
									
									
									GoodsLog goodsLogNew=new GoodsLog();
									goodsLogNew.setGoodsId(mNewGoodsId);
									goodsLogNew.setType(2);
									goodsLogNew.setCreateTime(Long.parseLong(logData.get(i).get("createTime").toString()));
									goodsLogNew.setGoodsChange(mCount);
									goodsLogNew.setClientId(Integer.parseInt(nextGoods.get("clientId").toString()));
									goodsLogNew.setGoodsSave(mCount);
									goodsLogNew.setSurplus(0);
									if(!Common.isNull(Integer.parseInt(nextGoods.get("zladingId").toString()))){
										
										goodsLogNew.setLadingId(Integer.parseInt(nextGoods.get("zladingId").toString()));
									}
									goodsLogDao.add(goodsLogNew);
									
									GoodsLog goodsLogNew2=new GoodsLog();
									goodsLogNew2.setGoodsId(mNewGoodsId);
									goodsLogNew2.setType(4);
									goodsLogNew2.setCreateTime(Long.parseLong(logData.get(0).get("createTime").toString())+10);
									goodsLogNew2.setGoodsChange(0);
									goodsLogNew2.setClientId(Integer.parseInt(nextGoods.get("clientId").toString()));
									goodsLogNew2.setGoodsSave(0);
									goodsLogNew2.setSurplus(mCount);
									if(!Common.isNull(Integer.parseInt(nextGoods.get("zladingId").toString()))){
										
										goodsLogNew2.setLadingId(Integer.parseInt(nextGoods.get("zladingId").toString()));
									}
									goodsLogDao.add(goodsLogNew2);
									
									//修复原货体入库封放记录
									
									graftingCargoDao.updateRKDJLog(Integer.parseInt(nextGoods.get("id").toString()),mCount);
									
									//按照日志顺序拆，修日志货体
									
									splitLogAndGoods(Integer.parseInt(nextGoods.get("id").toString()),mNewGoodsId,mCount);
									graftingCargoDao.repairLog(Integer.parseInt(nextGoods.get("id").toString()));
									graftingCargoDao.repairGoods(Integer.parseInt(nextGoods.get("id").toString()));
									graftingCargoDao.repairLog(mNewGoodsId);
									graftingCargoDao.repairGoods(mNewGoodsId);

								
								}
								//否则原货体放行量<拆分量，新货体量=总量-拆分量，全部封，货体关系交叉继承
								else{
									
									Goods newGoods=new Goods();
									newGoods.setCargoId(Integer.parseInt(nextGoods.get("cargoId").toString()));
									newGoods.setClientId(Integer.parseInt(nextGoods.get("clientId").toString()));
									newGoods.setCode(nextGoods.get("code").toString()+"  -1");
									newGoods.setContractId(Integer.parseInt(nextGoods.get("contractId").toString()));
									newGoods.setProductId(Integer.parseInt(nextGoods.get("productId").toString()));
									newGoods.setGoodsInPass("0");
									newGoods.setGoodsOutPass("0");
									newGoods.setGoodsCurrent(Common.fixDouble((oldGoodsTotal-mCount),3)+"");
									newGoods.setGoodsTotal(Common.fixDouble((oldGoodsTotal-mCount),3)+"");
									if(!Common.isNull(Integer.parseInt(nextGoods.get("goodsGroupId").toString()))){
										
										newGoods.setGoodsGroupId(Integer.parseInt(nextGoods.get("goodsGroupId").toString()));
									}
									if(!Common.isNull(Integer.parseInt(nextGoods.get("tankId").toString()))){
										
										newGoods.setTankId(Integer.parseInt(nextGoods.get("tankId").toString()));
									}
									newGoods.setSourceGoodsId(Integer.parseInt(nextGoods.get("sourceGoodsId").toString()));
									
									if(Integer.parseInt(nextGoods.get("rootGoodsId").toString())==0){
										
										newGoods.setRootGoodsId(Integer.parseInt(nextGoods.get("id").toString()));
									}else{
										newGoods.setRootGoodsId(Integer.parseInt(nextGoods.get("rootGoodsId").toString()));
										
									}
									
									int mNewgoodsId=(Integer) cargoGoodsDao.addGoods(newGoods);
									//更新上级货体调出的nextGoods
									graftingCargoDao.updateLogNextGoods(Integer.parseInt(logData.get(i).get("id").toString()),mNewgoodsId);
									
									
									
									Goods upNextGoods=new Goods();
									upNextGoods.setId(Integer.parseInt(nextGoods.get("id").toString()));
									CargoGoodsDto dto=new CargoGoodsDto();
									dto.setGoodsId(newgoodsId);
									Map<String,Object> newg=cargoGoodsDao.getGoodsList(dto, 0, 0).get(0);
									
									if(Integer.parseInt(newg.get("rootGoodsId").toString())==0){
										upNextGoods.setRootGoodsId(newgoodsId);
										
									}else{
										upNextGoods.setRootGoodsId(Integer.parseInt(newg.get("rootGoodsId").toString()));
										
									}
										upNextGoods.setSourceGoodsId(newgoodsId);
									cargoGoodsDao.updateGoods(upNextGoods);
									
									
									Goods oldGoods=new Goods();
									oldGoods.setId(Integer.parseInt(nextGoods.get("id").toString()));
									oldGoods.setSourceGoodsId(newgoodsId);
									cargoGoodsDao.updateGoods(oldGoods);
									
									GoodsLog goodsLogNew=new GoodsLog();
									goodsLogNew.setGoodsId(mNewgoodsId);
									goodsLogNew.setType(2);
									goodsLogNew.setCreateTime(Long.parseLong(logData.get(i).get("createTime").toString()));
									goodsLogNew.setGoodsChange(Double.parseDouble(newGoods.getGoodsTotal()));
									goodsLogNew.setClientId(Integer.parseInt(nextGoods.get("clientId").toString()));
									goodsLogNew.setGoodsSave(Double.parseDouble(newGoods.getGoodsTotal()));
									goodsLogNew.setSurplus(0);
									if(!Common.isNull(Integer.parseInt(nextGoods.get("zladingId").toString()))){
										
										goodsLogNew.setLadingId(Integer.parseInt(nextGoods.get("zladingId").toString()));
									}
									goodsLogDao.add(goodsLogNew);
									
									//修复原货体入库封放记录,修复日志，修复货体
									graftingCargoDao.updateRKFF(Integer.parseInt(nextGoods.get("id").toString()),newGoods.getGoodsTotal());
									graftingCargoDao.repairLog(Integer.parseInt(nextGoods.get("id").toString()));
									graftingCargoDao.repairGoods(Integer.parseInt(nextGoods.get("id").toString()));

									
								}
								
								
								
							}
						return;
					}
					
				}
				//否则如果是发货
				else if(Integer.parseInt(logData.get(i).get("type").toString())==5){
						double outCount=Double.parseDouble(logData.get(i).get("goodsChange").toString());
					
					//如果量没扣完
					if(mCount+outCount>=0){
						mCount+=outCount;
						//把这条记录的goodsId=newgoodsId
						graftingCargoDao.graftingActualLog(Integer.parseInt(logData.get(i).get("id").toString()),newgoodsId);
					}
					//否则拆分发货
					else{
						GoodsLogDto goodslogDto=new GoodsLogDto();
						goodslogDto.setSplitNum(Float.parseFloat(mCount+""));
						goodslogDto.setGoodsLogId(Integer.parseInt(logData.get(i).get("id").toString()));
						goodslogDto.setSplitGoodsId(newgoodsId);
						goodsLogService.splitDeliverGoods(goodslogDto);
						return;
					}
					
				}
				//如果扣损
				else if(Integer.parseInt(logData.get(i).get("type").toString())==9){
					double outCount=Double.parseDouble(logData.get(i).get("goodsChange").toString());
					
				//如果量没扣完
				if(mCount+outCount>=0){
					mCount+=outCount;
					//把这条记录的goodsId=newgoodsId
					graftingCargoDao.graftingActualLog(Integer.parseInt(logData.get(i).get("id").toString()),newgoodsId);
				}
				//否则拆分扣损
				else{
					// 货体日志
					GoodsLog goodsLog = new GoodsLog();
					goodsLog.setGoodsId(newgoodsId);
					goodsLog.setType(9);
					goodsLog.setCreateTime(Long.parseLong(logData.get(i).get("createTime").toString()));
					goodsLog.setGoodsChange(-mCount);
					goodsLogDao.add(goodsLog);
					
					GoodsLog upgoodsLog=new GoodsLog();
					upgoodsLog.setId(Integer.parseInt(logData.get(i).get("id").toString()));
					upgoodsLog.setGoodsChange(-(mCount+outCount));
					goodsLogDao.up(upgoodsLog);
					
					return;
				}
				
			}
				
				
			}
			
				
			}
			
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}


	@Override
	public OaMsg checkWaitOut(int goodsId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询");
			oaMsg.getData().addAll(graftingCargoDao.checkWaitOut(goodsId));
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODSHISTORY,type=C.LOG_TYPE.CREATE)
	public OaMsg addHistory(GraftingHistory graftingHistory) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
		graftingHistory.setCreateTime(Long.parseLong(new Date().getTime()
						/ 1000 + ""));
//		System.out.println(graftingHistory.getCount());
		int id=graftingCargoDao.addHistory(graftingHistory);
		Map<String,String> map=new HashMap<String, String>();
		map.put("id", id+"");
		oaMsg.setMap(map);
		LOG.debug("插入成功");
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		oaMsg.setMsg("插入成功");
		return oaMsg;
	} catch (RuntimeException re) {
		LOG.error("插入失败",re);
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		
		throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
	}
	}


	@Override
	public OaMsg getHistory(GraftingHistory graftingHistory, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		try {
			LOG.debug("开始查询历史记录");
			List<Map<String,Object>> contractList=graftingCargoDao.getHistory(graftingHistory, pageView.getStartRecord(), pageView.getMaxresult());
			
			count=graftingCargoDao.getHistoryCount(graftingHistory);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
			oaMsg.getData().addAll(contractList);
			Map<String, String> map = new HashMap<String, String>();
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
	public OaMsg checkRealWaitOut(int goodsId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询");
			oaMsg.getData().addAll(graftingCargoDao.checkRealWaitOut(goodsId));
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}






}
