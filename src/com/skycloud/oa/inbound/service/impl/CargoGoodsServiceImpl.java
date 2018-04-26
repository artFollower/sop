package com.skycloud.oa.inbound.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.FeeChargeDao;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.inbound.dao.ArrivalDao;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.GoodsGroupDao;
import com.skycloud.oa.inbound.dao.GraftingCargoDao;
import com.skycloud.oa.inbound.dao.InitialFeeDao;
import com.skycloud.oa.inbound.dto.CargoDto;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.GoodsGroup;
import com.skycloud.oa.inbound.model.InitialFee;
import com.skycloud.oa.inbound.service.CargoGoodsService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.dao.LadingDao;
import com.skycloud.oa.outbound.dao.ShipArrivalDao;
import com.skycloud.oa.outbound.dto.LadingDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.Lading;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class CargoGoodsServiceImpl implements CargoGoodsService {
	private static Logger LOG = Logger.getLogger(CargoGoodsServiceImpl.class);
	@Autowired
	private CargoGoodsDao cargoGoodsDao;
	@Autowired
	private GoodsGroupDao goodsGroupDao;
	@Autowired
	private LadingDao ladingDao;
	@Autowired
	private GoodsLogDao goodsLogDao;
	@Autowired
	private ArrivalDao arrivalDao;
	@Autowired
	private InitialFeeDao  initialFeeDao;
	
	@Autowired
	private FeeChargeDao feeChargeDao;
	
    @Autowired
    private GraftingCargoDao graftingCargoDao;
    @Autowired
    private ShipArrivalDao shipArrivalDao;

	/**
	 * 根据条件获取货批列表
	 * 
	 * @return
	 * @throws OAException
	 */
	@Override
	public OaMsg getCargoList(CargoGoodsDto agDto, PageView pageView,boolean isGetGood)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		int count = 0;
		List<Map<String, Object>> finalData =new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> cargoListData =new ArrayList<Map<String,Object>>();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			LOG.debug("获取货批表信息");
		 cargoListData = cargoGoodsDao.getCargoList(agDto,
							pageView.getStartRecord(), pageView.getMaxresult());
		    
			for(int i=0;i<cargoListData.size();i++){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("cargodata", cargoListData.get(i));
			CargoGoodsDto  ccDto=new CargoGoodsDto();
			ccDto.setCargoId(Integer.valueOf(cargoListData.get(i).get("id").toString()));
			ccDto.setTankGoods(agDto.isTankGoods());
			if(agDto.getIsPredict()!=null){
				ccDto.setIsPredict(agDto.getIsPredict());
			}
			if(!Common.isNull(agDto.getGoodsStatus())){
				ccDto.setGoodsStatus(agDto.getGoodsStatus());
			}
			if(isGetGood){
				map.put("goodsdata", cargoGoodsDao.getGoodsList(ccDto, 0, 0));
			}
			finalData.add(map);
			}
			oaMsg.getData().addAll(finalData);
			count = cargoGoodsDao.getCargoCount(agDto);
			Map<String, String> map = new HashMap<String, String>();
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	/**
	 * 根据条件获取货体列表
	 * 
	 * @param agDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	@Override
	public OaMsg getGoodsList(CargoGoodsDto agDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		List<Map<String, Object>> ltResultData = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> cargoGoodsData = new ArrayList<Map<String, Object>>();
		Map<String, Map<String, Object>> goodsData = new HashMap<String, Map<String, Object>>();// 把groupid作为key,添加货体组
		LOG.debug("不是货体组的单独处理");
		List<Map<String, Object>> nullgoodsDataList = new ArrayList<Map<String, Object>>();// 不是货体组的货体集合
		Map<String, Object> nullgoodsData = new HashMap<String, Object>();// 不是货体组的货体
		nullgoodsData.put("goodsgroupdata", "null");
		List<String> zids = new ArrayList<String>();
		int count = 0;
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			LOG.debug("获取货体表信息");
			cargoGoodsData = cargoGoodsDao.getGoodsList(agDto,
					pageView.getStartRecord(), pageView.getMaxresult());

			if(cargoGoodsData.size()>0){
				for(int i=0;i<cargoGoodsData.size();i++){
					if(Common.fixDouble(Double.parseDouble(cargoGoodsData.get(i).get("goodsCurrent").toString()), 0)!=0)
					cargoGoodsData.get(i).put("shipCount", Common.empty(shipArrivalDao.getArrivalPlanAmount(Integer.parseInt(cargoGoodsData.get(i).get("id").toString())))?0:shipArrivalDao.getArrivalPlanAmount(Integer.parseInt(cargoGoodsData.get(i).get("id").toString())).get("amount"));
				}
			}
			
			for (int i = 0; i < cargoGoodsData.size(); i++) {
				String groupId = cargoGoodsData.get(i).get("zid").toString();
//				LOG.debug("不是货体组的");
				if (Common.isNull(groupId)
						|| Common.isNull(Integer.valueOf(groupId))) {
					nullgoodsDataList.add(cargoGoodsData.get(i));
				} else {
					if (zids.contains(groupId)) {
						List<Map<String, Object>> list = (List<Map<String, Object>>) goodsData
								.get(groupId).get("goodsdata");
						list.add(cargoGoodsData.get(i));
					} else {
						zids.add(groupId);
						Map<String, Object> data = new HashMap<String, Object>();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("id", cargoGoodsData.get(i).get("zid"));
//						map.put("ladingCode", cargoGoodsData.get(i).get("ladingCode"));
//						map.put("rLadingCode", cargoGoodsData.get(i).get("rLadingCode"));
//						map.put("goodsWait", cargoGoodsData.get(i).get("goodsWait"));
//						map.put("goodsOutPass", cargoGoodsData.get(i).get("goodsOutPass"));
//						map.put("sourceClientName", cargoGoodsData.get(i).get("sourceClientName"));
						map.put("code", cargoGoodsData.get(i).get("zcode"));
						map.put("clientId",
								cargoGoodsData.get(i).get("zclientId"));
						map.put("productId",
								cargoGoodsData.get(i).get("zproductId"));
						map.put("ladingId",
								cargoGoodsData.get(i).get("zladingId"));
						map.put("goodsinspect",
								cargoGoodsData.get(i).get("zgoodsInspect"));
						map.put("goodsTank",
								cargoGoodsData.get(i).get("zgoodsTank"));
						map.put("goodsTotal",
								cargoGoodsData.get(i).get("zgoodsTotal"));
						map.put("goodsIn", cargoGoodsData.get(i)
								.get("zgoodsIn"));
						map.put("goodsOut",
								cargoGoodsData.get(i).get("zgoodsOut"));
						map.put("goodsCurrent",
								cargoGoodsData.get(i).get("zgoodsCurrent"));
						map.put("goodsInPass",
								cargoGoodsData.get(i).get("zgoodsInPass"));
						map.put("goodsOutPass",
								cargoGoodsData.get(i).get("zgoodsOutPass"));
						data.put("goodsgroupdata", map);
						List<Map<String, Object>> gdl = new ArrayList<Map<String, Object>>();
						gdl.add(cargoGoodsData.get(i));
						data.put("goodsdata", gdl);
						goodsData.put(groupId, data);
					}
				}
			}
			nullgoodsData.put("goodsdata", nullgoodsDataList);

			ltResultData.add(nullgoodsData);
			for (int i = 0; i < zids.size(); i++) {
				ltResultData.add(goodsData.get(zids.get(i)));
			}
			

			
			
			oaMsg.getData().addAll(ltResultData);

			count = cargoGoodsDao.getGoodsCount(agDto);
//			pageView.setTotalrecord(count);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage() + "");
			map.put("totalpage", pageView.getTotalpage() + "");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	/**
	 * 获取单个货批的详情信息（包括货体）
	 * 
	 * @param agDto
	 * @return
	 * @throws OAException
	 */
	@Override
	public OaMsg getCargoGoodsDetail(CargoGoodsDto agDto) throws OAException {
		// {cargodata:{},goodsgroup:[{goodsgroupdata:{},goodsData:[{},{},{}]},{.....}]}
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> itemArrival = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> ltGoodsdata = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> goodsGroup = new ArrayList<Map<String, Object>>();
			Map<String, Object> cargoGoodsGroupData = new HashMap<String, Object>();
			LOG.debug("获取单个货批信息");
			cargoGoodsGroupData.put("cargodata",
					cargoGoodsDao.getCargoList(agDto, 0, 0).get(0));
			LOG.debug("获取货体表信息");
			// TODO 将是货体组的货体给放在一个组里
			PageView pageView = new PageView(0, 0);
			OaMsg oamsg = getGoodsList(agDto, pageView);
			cargoGoodsGroupData.put("goodsgroup", oamsg.getData());
			LOG.debug("存储到list");
			itemArrival.add(cargoGoodsGroupData);
			LOG.debug("添加到oaMsg");
			oaMsg.getData().addAll(itemArrival);

		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	
	
	
	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.SPLIT)
	public OaMsg sqlitOriginalGoods(String oldGoodsId ,Goods newGoods)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			
			int countType=newGoods.getType();
			
			CargoGoodsDto cargoGoodsDto=new CargoGoodsDto();
			cargoGoodsDto.setGoodsId(Integer.parseInt(oldGoodsId));
			Map<String,Object> oldGoods=cargoGoodsDao.getGoodsList(cargoGoodsDto, 0, 0).get(0);
//			for(int i=0;i<newGoodsList.size();i++){
//				newGoodsList.get(i).setCargoId(Integer.parseInt(oldGoods.get("cargoId").toString()));
//				cargoGoodsDao.addGoods(newGoodsList.get(i));
//			}
			
			
			//货批各种放行商检数减
			CargoGoodsDto cargoDto=new CargoGoodsDto();
			cargoDto.setCargoId(Integer.parseInt(oldGoods.get("cargoId").toString()));
			Map<String,Object> cargoInfo=cargoGoodsDao.getCargoList(cargoDto, 0, 0).get(0);
			
			Cargo cargo=new Cargo();
			cargo.setId(Integer.parseInt(oldGoods.get("cargoId").toString()));
			double inspect=Common.empty(oldGoods.get("goodsInspect"))?0:Double.parseDouble(oldGoods.get("goodsInspect").toString());
			double outPass=Common.empty(oldGoods.get("goodsOutPass"))?0:Double.parseDouble(oldGoods.get("goodsOutPass").toString());
			double inPass=Common.empty(oldGoods.get("goodsInPass"))?0:Double.parseDouble(oldGoods.get("goodsInPass").toString());
			double cInspect=Common.empty(cargoInfo.get("goodsInspect"))?0:Double.parseDouble(cargoInfo.get("goodsInspect").toString());
			double cOutPass=Common.empty(cargoInfo.get("goodsOutPass"))?0:Double.parseDouble(cargoInfo.get("goodsOutPass").toString());
			double cInPass=Common.empty(cargoInfo.get("goodsInPass"))?0:Double.parseDouble(cargoInfo.get("goodsInPass").toString());
			
//			cargo.setGoodsInspect(Common.fixDouble((cInspect-inspect),3)+"");
//			cargo.setGoodsOutPass(Common.fixDouble((cOutPass-outPass),3)+"");
//			cargo.setGoodsInPass(Common.fixDouble((cInPass-inPass),3)+"");
//			cargoGoodsDao.updateCargo(cargo);
			//原货体
			Goods oldgoods=new Goods();
			
//			Goods oldgoods= new Gson().fromJson(oldGoods.toString(),new TypeToken<Goods>() {}.getType());
			oldgoods.setId(Integer.parseInt(oldGoodsId));
			oldgoods.setGoodsTotal(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsTotal").toString())-Double.parseDouble(newGoods.getGoodsTotal()),3)+"");
			oldgoods.setCargoId(Integer.parseInt(oldGoods.get("cargoId").toString()));
			oldgoods.setGoodsCurrent(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsCurrent" +
					"").toString())-Double.parseDouble(newGoods.getGoodsTotal()),3)+"");
			oldgoods.setGoodsTank(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsTank").toString())-Double.parseDouble(newGoods.getGoodsTank()),3)+"");
			oldgoods.setClientId(Integer.valueOf(oldGoods.get("clientId").toString()));
			oldgoods.setContractId(Integer.valueOf(oldGoods.get("contractId").toString()));
			if(!Common.empty(oldGoods.get("lossRate"))){
				oldgoods.setLossRate(oldGoods.get("lossRate").toString());
			}
			oldgoods.setStatus(Integer.valueOf(oldGoods.get("status").toString()));
			oldgoods.setProductId(Integer.valueOf(oldGoods.get("productId").toString()));
//			oldgoods.setGoodsInPass("0");
			
			
			if(newGoods.getType()==1){
				
				oldgoods.setGoodsInspect(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsInspect").toString())-Double.parseDouble(newGoods.getGoodsInspect()),3)+"");
			}
			
//			oldgoods.setGoodsOut("0");
//			oldgoods.setGoodsOutPass("0");
			oldgoods.setTankId(Integer.parseInt(oldGoods.get("tankId").toString()));
			if(!Common.empty(oldGoods.get("isPredict"))){
				oldgoods.setIsPredict(Integer.parseInt(oldGoods.get("isPredict").toString()));
			}
			
			//新货体
			newGoods.setCargoId(Integer.parseInt(oldGoods.get("cargoId").toString()));
			newGoods.setGoodsCurrent(newGoods.getGoodsTotal());
			newGoods.setGoodsTank(newGoods.getGoodsTank());
			newGoods.setClientId(Integer.valueOf(oldGoods.get("clientId").toString()));
			newGoods.setContractId(Integer.valueOf(oldGoods.get("contractId").toString()));
			if(!Common.empty(oldGoods.get("lossRate"))){
			newGoods.setLossRate(oldGoods.get("lossRate").toString());
			}
			newGoods.setStatus(Integer.valueOf(oldGoods.get("status").toString()));
			newGoods.setProductId(Integer.valueOf(oldGoods.get("productId").toString()));
			if(!Common.empty(oldGoods.get("isPredict"))){
				newGoods.setIsPredict(Integer.parseInt(oldGoods.get("isPredict").toString()));
			}
			newGoods.setGoodsInPass("0");
			newGoods.setGoodsOutPass("0");
			if(newGoods.getType()==1){
				newGoods.setGoodsInspect(newGoods.getGoodsInspect());
				
			}else{
				newGoods.setGoodsInspect("0");
			}
			newGoods.setType(0);
			
			//查询车船id
//			int shipId=Integer.valueOf(arrivalDao.getshipId(Integer.valueOf(oldGoods.get("id").toString())).get(0).get("shipId").toString());
			
			int codeCount=cargoGoodsDao.getTheSameGoods(oldGoods.get("code").toString(),Integer.parseInt(oldGoodsId));
			newGoods.setCode(oldGoods.get("code").toString()+"  -"+(codeCount+1));
			
//			newGoods.setGoodsInspect(goodsInspect)
			int newgoodsId=(Integer) cargoGoodsDao.addGoods(newGoods);
			
			GoodsLog goodsLog=new GoodsLog();
			goodsLog.setGoodsId(newgoodsId);
			goodsLog.setType(1);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			goodsLog.setGoodsChange(Common.fixDouble(Double.parseDouble(newGoods.getGoodsTotal()),3));
//			goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));
			goodsLog.setGoodsSave(Common.fixDouble(Double.parseDouble(newGoods.getGoodsTotal()),3));
//			goodsLog.setDeliverType(2);
//			goodsLog.setBatchId(shipId);
			goodsLogDao.add(goodsLog);
			
			if(countType==1){
				
				GoodsLog goodsLog2=new GoodsLog();
				goodsLog2.setGoodsId(newgoodsId);
				goodsLog2.setType(10);
				goodsLog2.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
				goodsLog2.setGoodsChange(0);
//				goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));
				goodsLog2.setGoodsSave(Common.fixDouble(Double.parseDouble(newGoods.getGoodsTotal()),3));
//				goodsLog.setDeliverType(2);
//				goodsLog.setBatchId(shipId);
				goodsLogDao.add(goodsLog2);
				
			}
			
			//删除原货体所有日志
//			goodsLogDao.deleteGoodsLog(oldGoodsId+"");
			
//			int oldgoodsId=(Integer) cargoGoodsDao.addGoods(oldgoods);
			
			cargoGoodsDao.updateGoods(oldgoods);
			
			GoodsLog goodsLog1=new GoodsLog();
			goodsLog1.setGoodsId(Integer.parseInt(oldGoodsId));
			goodsLog1.setType(1);
			goodsLog1.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			goodsLog1.setGoodsChange(-Common.fixDouble(Double.parseDouble(newGoods.getGoodsTotal()),3));
			goodsLog1.setGoodsSave(Common.fixDouble(Double.parseDouble(oldgoods.getGoodsTotal())-Double.parseDouble(oldGoods.get("goodsOutPass").toString()),3));
			goodsLog1.setSurplus(Common.fixDouble(Double.parseDouble(oldgoods.getGoodsCurrent())-goodsLog1.getGoodsSave(),3));
//			goodsLog1.setDeliverType(2);
//			goodsLog1.setBatchId(shipId);
			goodsLogDao.add(goodsLog1);
			
			
//			cargoGoodsDao.deleteGoods(oldGoodsId);
			
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String,String> map=new HashMap<String, String>();
//			map.put("code", oldGoods.get("code").toString().split(" ")[0]);
			map.put("cargoId",oldGoods.get("cargoId").toString()+"");
			oaMsg.setMap(map);
		} catch (OAException re) {
			LOG.error("拆分失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "拆分失败", re);
		}
		return oaMsg;
	}
	
	
	/**
	 * 拆分货体，生成新的货体，更新被拆分的货体 传入old id， new total,description
	 * @param id
	 * @param code
	 * @param goodsnew
	 * @param goodsold
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_LADING,type=C.LOG_TYPE.UPDATE)
	public OaMsg splitupdateGoods(String id, String code, List<Goods> goodsNewList,
			List<Goods> goodsOldList, String goodGroupId) throws OAException {
		//
		LadingDto ladingDto=new LadingDto();
		ladingDto.setId(Integer.parseInt(id));
		Map<String, Object> lading=ladingDao.getLadingList(ladingDto, 0, 0).get(0);
		Lading upLading=new Lading();
		upLading.setId(Integer.parseInt(id));
		double totalCount=0;
//		upLading.setGoodsTotal(lading.get("goodsTotal")+)
		OaMsg oaMsg = new OaMsg();
		String ids="";
		try {
			for(int i=0;i<goodsNewList.size();i++){
			Goods goodsold=goodsOldList.get(i);
			Goods goodsnew=goodsNewList.get(i);
			
			totalCount+=Double.valueOf(goodsnew.getGoodsTotal());
			
			LOG.debug("将被拆分的货体一些信息填写进入新货体");
			CargoGoodsDto agDto = new CargoGoodsDto();
			agDto.setGoodsId(goodsold.getId());
			Map<String, Object> oldGoods = cargoGoodsDao.getGoodsList(agDto, 0,
					0).get(0);
			if (Common.empty(oldGoods.get("goodsTotal"))
					|| (Double.valueOf(oldGoods.get("goodsTotal").toString()) < Double
							.valueOf(goodsnew.getGoodsTotal()))) {
				oaMsg.setCode(Constant.SYS_CODE_PARAM_NULL);
				oaMsg.setMsg("拆分错误");
				return oaMsg;
			}

			
			List<Map<String, Object>>  upGoodsList=ladingDao.getGoodsListByLadingId(Integer.parseInt(id));
			for(int j=0;j<upGoodsList.size();j++){
				Goods upGoods=new Goods();
				upGoods.setId(Integer.valueOf(upGoodsList.get(j).get("id").toString()));
				upGoods.setCode(code+"  "+(j+1)+"/"+(upGoodsList.size()+1));
				cargoGoodsDao.updateGoods(upGoods);
			}
			
			LOG.debug("生成新的数据");
//			int codeCount=cargoGoodsDao.getTheSameGoodsCount(code);
				goodsnew.setCode(code +"  "+ (upGoodsList.size()+1)+"/"+(upGoodsList.size()+1));
			if (!Common.empty(oldGoods.get("cargoId")))// 货批号继承
				goodsnew.setCargoId(Integer.valueOf(oldGoods.get("cargoId")
						.toString()));
			if (!Common.empty(oldGoods.get("contractId")))// 合同号继承
				if(Integer.parseInt(lading.get("type").toString())==2){
					goodsnew.setContractId(Integer.valueOf(oldGoods.get(
							"contractId").toString()));
				}
				
			if (Integer.parseInt(lading.get("type").toString())==1){
				goodsnew.setClientId(Integer.valueOf(lading.get("receiveClientId")
						.toString()));
			}else{
				goodsnew.setClientId(Integer.valueOf(oldGoods.get(
						"clientId").toString()));
				goodsnew.setLadingClientId(Integer.valueOf(lading.get("receiveClientId").toString()));
			}
			if (!Common.empty(oldGoods.get("productId")))// 货品ID继承
				goodsnew.setProductId(Integer.valueOf(oldGoods.get("productId")
						.toString()));
			goodsnew.setSourceGoodsId(Integer.valueOf(oldGoods.get("id")
					.toString()));// 来源货体
			goodsnew.setGoodsGroupId(Integer.valueOf(goodGroupId));//货体组
			
			
			//老货体的母货体为空，来源货体不为空，则母货体为老货体
			if((Common.empty(oldGoods.get("rootGoodsId"))||Integer.parseInt(oldGoods.get("rootGoodsId").toString())==0)&&!Common.empty(oldGoods.get("sourceGoodsId"))&&Integer.parseInt(oldGoods.get("sourceGoodsId").toString())!=0){
				goodsnew.setRootGoodsId(Integer.valueOf(oldGoods.get("id")
						.toString()));
				//如果老货体母货体不为空，则新货体的母货体继承
			}else if(!Common.empty(oldGoods.get("rootGoodsId"))){
				goodsnew.setRootGoodsId(Integer.valueOf(oldGoods.get(
						"rootGoodsId").toString()));
			}
			//如果老货体的母货体和来源货体都是空，新货体的母货体也为空
			
			
			
//			if (Common.empty(oldGoods.get("rootGoodsId"))
//					|| Common.isNull(Integer.valueOf(oldGoods
//							.get("rootGoodsId").toString()))) {
//				goodsnew.setRootGoodsId(Integer.valueOf(oldGoods.get("id")
//						.toString()));// 母货体
//			} else {
//				goodsnew.setRootGoodsId(Integer.valueOf(oldGoods.get(
//						"rootGoodsId").toString()));
//			}

			if (!Common.empty(oldGoods.get("tankId"))
					&& !Common.isNull(oldGoods.get("tankId").toString()))// 继承货罐
				goodsnew.setTankId(Integer.valueOf(oldGoods.get("tankId")
						.toString()));
			goodsnew.setCreateTime(new Timestamp(System.currentTimeMillis()));// 创建时间
			goodsnew.setLossRate("" + 0);// 损耗率0
			goodsnew.setGoodsInspect("" + 0);// 商检0
			goodsnew.setGoodsTank("" + 0);// 罐检0
			goodsnew.setGoodsOut("" + 0);// 调出0
			goodsnew.setGoodsInPass("" + 0);// 入库放行总量0
			goodsnew.setGoodsOutPass("" + 0);
			;// 出库放行总量0
			goodsnew.setGoodsCurrent(goodsnew.getGoodsTotal());// 货体总量=当前存量
			goodsnew.setGoodsIn(goodsnew.getGoodsTotal());// 调入=当前存量
		
			int newid=(Integer) cargoGoodsDao.addGoods(goodsnew);
			ids+=newid+",";
		  
			LOG.debug("更新被拆分的数据");
			if (!Common.empty(oldGoods.get("goodsCurrent")))
				goodsold.setGoodsCurrent(""
						+ Common.fixDouble((Double.valueOf(oldGoods.get("goodsCurrent")
								.toString()) - Double.valueOf(goodsnew
								.getGoodsTotal())),3));
			if (!Common.empty(oldGoods.get("goodsOut").toString())){
				goodsold.setGoodsOut(""+Common.fixDouble((Double.valueOf(goodsnew.getGoodsTotal())+Double.valueOf(oldGoods.get("goodsOut").toString())),3));
			}else{
				goodsold.setGoodsOut(""+Common.fixDouble((Double.valueOf(goodsnew.getGoodsTotal())),3));
				
			}
//			updateGoods(goodsold, false,false);
			cargoGoodsDao.updateGoods(goodsold);

			
			
			
			//更新被拆分货体的提单调出量
			LOG.debug("更新被拆分货体的提单调出量");
			LadingDto oldLadingDto=new LadingDto();
			oldLadingDto.setGoodsId(goodsold.getId());
			List<Map<String,Object>> oldLadingList=ladingDao.getLadingByGoodsId(oldLadingDto);
			if(oldLadingList.size()!=0){
				Map<String,Object> oldLading=oldLadingList.get(0);
			Lading oldUpLading=new Lading();
			oldUpLading.setId(Integer.parseInt(oldLading.get("id").toString()));
			//原提单的调出量+
			double goodsOut=oldLading.get("goodsOut")==null?0:Double.valueOf(oldLading.get("goodsOut").toString());
			oldUpLading.setGoodsOut(Common.fixDouble((goodsOut+Double.valueOf(goodsnew.getGoodsTotal())),3)+"");
			ladingDao.updateLading(oldUpLading);
			}
			
			
			
			//新货体调入
			GoodsLog goodsLog=new GoodsLog();
			goodsLog.setGoodsId(newid);
			goodsLog.setType(2);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			goodsLog.setGoodsChange(Common.fixDouble(Double.parseDouble(goodsnew.getGoodsIn()),3));
//			goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));
			goodsLog.setGoodsSave(Common.fixDouble(Double.parseDouble(goodsnew.getGoodsCurrent()),3));
			goodsLog.setLadingId(Integer.parseInt(id));
			goodsLog.setLadingClientId(Integer.valueOf(lading.get("clientId").toString()));
			
			goodsLog.setLadingType(Integer.parseInt(lading.get("type").toString()));
			goodsLogDao.add(goodsLog);
			
			
			//原货体调出
			GoodsLog goodsLogOld=new GoodsLog();
			goodsLogOld.setGoodsId(goodsold.getId());
			goodsLogOld.setType(3);
			goodsLogOld.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			goodsLogOld.setGoodsChange(Common.fixDouble(Double.parseDouble("-"+goodsnew.getGoodsIn()),3));
			goodsLogOld.setGoodsSave(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsTotal").toString())-Math.min(Double.parseDouble(oldGoods.get("goodsInPass").toString()),Double.parseDouble(oldGoods.get("goodsOutPass").toString())),3));
			goodsLogOld.setClientId(Integer.valueOf(lading.get("receiveClientId").toString()));
			goodsLogOld.setLadingClientId(Integer.valueOf(lading.get("receiveClientId").toString()));
			
			goodsLogOld.setSurplus(Common.fixDouble(Double.parseDouble(goodsold.getGoodsCurrent())-goodsLogOld.getGoodsSave(),3));
			if(oldLadingList.size()!=0){
			goodsLogOld.setLadingId(Integer.parseInt(oldLadingList.get(0).get("id").toString()));
			}
			goodsLogOld.setLadingType(Integer.parseInt(lading.get("type").toString()));
			goodsLogOld.setNextLadingId(Integer.parseInt(id));
			goodsLogOld.setNextGoodsId(newid);
			goodsLogDao.add(goodsLogOld);
			
			
			}
			upLading.setGoodsTotal(Common.fixDouble((Double.valueOf(lading.get("goodsTotal").toString())+totalCount),3)+"");
//			upLading.setGoodsPass(Common.fixDouble((Double.valueOf(lading.get("goodsPass").toString())+totalCount),3)+"");
			ladingDao.updateLading(upLading);
			 Map<String,String> map=new HashMap<String, String>();
			 map.put("id", ids.substring(0,ids.length()-1));
			 oaMsg.setMap(map);	
		} catch (RuntimeException re) {
			LOG.error("拆分失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	
	/**
	 * 拆分货体，生成新的货体，更新被拆分的货体 传入old id， new total,description
	 * 返回新活体的id
	 * @param goodsnew
	 * @param goodsold
	 * @return
	 * @throws OAException
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.SPLIT)
	public OaMsg splitGoods(int ladingId,String code,List<Goods> goodsNewList, List<Goods> goodsOldList,String buyClientId,int ladingType) throws OAException {
		//
		OaMsg oaMsg = new OaMsg();
		String ids="";
		try {
			
			LadingDto ladingDto=new LadingDto();
			ladingDto.setId(ladingId);
			Map<String, Object> lading=ladingDao.getLadingList(ladingDto, 0, 0).get(0);
			
			for(int i=0;i<goodsNewList.size();i++){
			
			Goods goodsold=goodsOldList.get(i);
			Goods goodsnew=goodsNewList.get(i);
			LOG.debug("将被拆分的货体一些信息填写进入新货体");
			CargoGoodsDto agDto = new CargoGoodsDto();
			agDto.setGoodsId(goodsold.getId());
			Map<String, Object> oldGoods = cargoGoodsDao.getGoodsList(agDto, 0,
					0).get(0);
			if (Common.empty(oldGoods.get("goodsTotal"))
					|| (Double.valueOf(oldGoods.get("goodsTotal").toString()) < Double
							.valueOf(goodsnew.getGoodsTotal()))) {
				oaMsg.setCode(Constant.SYS_CODE_PARAM_NULL);
				oaMsg.setMsg("拆分错误");
				return oaMsg;
			}

			LOG.debug("生成新的数据");
			if (!Common.empty(oldGoods.get("code").toString()))// 生成货体号
				goodsnew.setCode(code +"  "+ (i+1)+"/"+goodsNewList.size());
			if (!Common.empty(oldGoods.get("cargoId")))// 货批号继承
				goodsnew.setCargoId(Integer.valueOf(oldGoods.get("cargoId")
						.toString()));
			if (!Common.empty(oldGoods.get("contractId")))// 合同号继承
				goodsnew.setContractId(Integer.valueOf(oldGoods.get(
						"contractId").toString()));
			if (!Common.empty(oldGoods.get("goodsGroupId")))// 货体组继承
				goodsnew.setGoodsGroupId(Integer.valueOf(oldGoods.get(
						"goodsGroupId").toString()));
			if (!Common.empty(oldGoods.get("clientId")))// 货主继承
				goodsnew.setClientId(Integer.valueOf(oldGoods.get("clientId")
						.toString()));
			
//			if (ladingType==1){
//				goodsnew.setClientId(Integer.valueOf(lading.get("receiveClientId")
//						.toString()));
//			}else{
//				goodsnew.setClientId(Integer.valueOf(lading.get("clientId").toString()));
//				goodsnew.setLadingClientId(Integer.valueOf(lading.get("receiveClientId").toString()));
//			}
			
			
			if (!Common.empty(oldGoods.get("productId")))// 货批ID继承
				goodsnew.setProductId(Integer.valueOf(oldGoods.get("productId")
						.toString()));
			goodsnew.setSourceGoodsId(Integer.valueOf(oldGoods.get("id")
					.toString()));// 来源货体
			
			//老货体的母货体为空，来源货体不为空，则母货体为老货体
			if((Common.empty(oldGoods.get("rootGoodsId"))||Integer.parseInt(oldGoods.get("rootGoodsId").toString())==0)&&!Common.empty(oldGoods.get("sourceGoodsId"))&&Integer.parseInt(oldGoods.get("sourceGoodsId").toString())!=0){
				goodsnew.setRootGoodsId(Integer.valueOf(oldGoods.get("id")
						.toString()));
				//如果老货体母货体不为空，则新货体的母货体继承
			}else if(!Common.empty(oldGoods.get("rootGoodsId"))){
				goodsnew.setRootGoodsId(Integer.valueOf(oldGoods.get(
						"rootGoodsId").toString()));
			}else {
				goodsnew.setRootGoodsId(0);
			}
			//如果老货体的母货体和来源货体都是空，新货体的母货体也为空
			
			
			
			
//			if (Common.empty(oldGoods.get("rootGoodsId"))
//					|| Common.isNull(Integer.valueOf(oldGoods
//							.get("rootGoodsId").toString()))) {
//				goodsnew.setRootGoodsId(Integer.valueOf(oldGoods.get("id")
//						.toString()));// 母货体
//			} else {
//				goodsnew.setRootGoodsId(Integer.valueOf(oldGoods.get(
//						"rootGoodsId").toString()));
//			}

			if (!Common.empty(oldGoods.get("tankId"))
					&& !Common.isNull(oldGoods.get("tankId").toString()))// 继承货罐
				goodsnew.setTankId(Integer.valueOf(oldGoods.get("tankId")
						.toString()));
			goodsnew.setCreateTime(new Timestamp(System.currentTimeMillis()));// 创建时间
			goodsnew.setLossRate("" + 0);// 损耗率0
			goodsnew.setGoodsInspect("" + 0);// 商检0
			goodsnew.setGoodsTank("" + 0);// 罐检0
			goodsnew.setGoodsOut("" + 0);// 调出0
//			goodsnew.setGoodsInPass("" + 0);// 入库放行总量0
//			goodsnew.setGoodsOutPass("" + 0);
			;// 出库放行总量0
			goodsnew.setGoodsCurrent(goodsnew.getGoodsTotal());// 货体总量=当前存量
			goodsnew.setGoodsIn(goodsnew.getGoodsTotal());// 调入=当前存量
		
			int newid=(Integer) cargoGoodsDao.addGoods(goodsnew);
			ids+=newid+",";
			
			
			
			
			LOG.debug("更新被拆分的数据");
			if (!Common.empty(oldGoods.get("goodsCurrent")))
				goodsold.setGoodsCurrent(""
						+ Common.fixDouble(Double.valueOf(oldGoods.get("goodsCurrent")
								.toString()) - Double.valueOf(goodsnew
								.getGoodsTotal()),3));
			if (!Common.empty(oldGoods.get("goodsOut"))){
				goodsold.setGoodsOut(""+Common.fixDouble((Double.valueOf(goodsnew.getGoodsTotal())+Double.valueOf(oldGoods.get("goodsOut").toString())),3));
			}else{
				goodsold.setGoodsOut(""+Common.fixDouble((Double.valueOf(goodsnew.getGoodsTotal())),3));
				
			}
//			updateGoods(goodsold, false,false);
			cargoGoodsDao.updateGoods(goodsold);

			//更新被拆分货体的提单调出量
			LOG.debug("更新被拆分货体的提单调出量");
			LadingDto oldLadingDto=new LadingDto();
			oldLadingDto.setGoodsId(goodsold.getId());
			List<Map<String,Object>> oldLadingList=ladingDao.getLadingByGoodsId(oldLadingDto);
			if(oldLadingList.size()!=0){
				Map<String,Object> oldLading=oldLadingList.get(0);
			Lading oldUpLading=new Lading();
			oldUpLading.setId(Integer.parseInt(oldLading.get("id").toString()));
			//原提单的调出量+
			double goodsOut=oldLading.get("goodsOut")==null?0:Double.valueOf(oldLading.get("goodsOut").toString());
			oldUpLading.setGoodsOut(Common.fixDouble((goodsOut+Double.valueOf(goodsnew.getGoodsTotal())),3)+"");
			ladingDao.updateLading(oldUpLading);
			}
			
			//添加日志表
			
			//新货体调入
			GoodsLog goodsLog=new GoodsLog();  
			goodsLog.setGoodsId(newid);
			goodsLog.setType(2);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			goodsLog.setGoodsChange(Common.fixDouble(Double.parseDouble(goodsnew.getGoodsIn()),3));
//			goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));
			goodsLog.setGoodsSave(Common.fixDouble(Double.parseDouble(goodsnew.getGoodsCurrent()),3));
			goodsLog.setLadingId(ladingId);
				
			goodsLog.setLadingClientId(Integer.parseInt(lading.get("clientId").toString()));
			goodsLog.setLadingType(ladingType);
			goodsLogDao.add(goodsLog);
			
			//原货体调出
			GoodsLog goodsLogOld=new GoodsLog();
			goodsLogOld.setGoodsId(goodsold.getId());
			goodsLogOld.setType(3);
			goodsLogOld.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			goodsLogOld.setGoodsChange(Common.fixDouble(Double.parseDouble("-"+goodsnew.getGoodsIn()),3));
//			goodsLogOld.setClientId(Integer.parseInt(oldGoods.get("clientId").toString()));
			goodsLogOld.setGoodsSave(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsTotal").toString())-Math.min(Double.parseDouble(oldGoods.get("goodsInPass").toString()),Double.parseDouble(oldGoods.get("goodsOutPass").toString())),3));
			goodsLogOld.setClientId(Integer.parseInt(buyClientId));
			goodsLogOld.setLadingClientId(Integer.parseInt(buyClientId));
			goodsLogOld.setSurplus(Common.fixDouble(Double.parseDouble(goodsold.getGoodsCurrent())-goodsLogOld.getGoodsSave(),3));
			if(oldLadingList.size()!=0){
				goodsLogOld.setLadingId(Integer.valueOf(oldLadingList.get(0).get("id").toString()));
			}
			goodsLogOld.setLadingType(ladingType);
			goodsLogOld.setNextLadingId(ladingId);
			goodsLogOld.setNextGoodsId(newid);
			goodsLogDao.add(goodsLogOld);
			
			
			}
			 Map<String,String> map=new HashMap<String, String>();
			 map.put("id", ids.substring(0,ids.length()-1));
			 oaMsg.setMap(map);	
		} catch (RuntimeException re) {
			LOG.error("拆分失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	/**
	 * 合并货体，将多个货体合并成货体组 只将itGoods的id，code传入
	 * 
	 * @param goodsfir
	 * @param goodssec
	 * @return
	 * @throws OAException
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.MERGE)
	public OaMsg mergeGoods(List<Goods> ltGoods) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			GoodsGroup goodsGroup = new GoodsGroup();

			String code = "HTZ"
					+ Common.changeNum((goodsGroupDao
							.getTheSameGoodsGroupCount("HTZ") + 1));

			goodsGroup.setCode(code);// goodsGroup命名 重新定义
			try {
				String ids = "";

				for (int i = 0; i < ltGoods.size(); i++) {
					if (i != ltGoods.size() - 1) {
						ids += ltGoods.get(i).getId() + ",";
					} else {
						ids += ltGoods.get(i).getId();
					}
				}
				int goodsGroupId = goodsGroupDao.addGoodsGroupBygoods(ids);
				for (Goods goods : ltGoods) {
					goods.setGoodsGroupId(goodsGroupId);
//					updateGoods(goods, false,false);
					cargoGoodsDao.updateGoods(goods);
				}
				goodsGroup.setId(goodsGroupId);
				goodsGroupDao.updateGoodsGroup(goodsGroup);
			} catch (Exception e) {
				LOG.debug("类型转换失败");
			}
		} catch (RuntimeException re) {
			LOG.error("合并失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	/**
	 * 更新货批表（如果修改了与货体相关修改货体）
	 * 
	 * @param cargo
	 * @param ischangegoods是否修改货体
	 * @return
	 * @throws OAException
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateCargo(CargoDto cargoDto, boolean ischangegoods)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		List<Map<String, Object>> changesdata = null;
		double[] goodsnum = new double[] { 0, 0, 0, 0, 0, 0 };
		Goods goods = new Goods();
		try {
			if (ischangegoods) {
				LOG.debug("获取货批货体关联属性数据");
				changesdata = cargoGoodsDao.getAppointCargoGoodsData(cargoDto.getCargo()
						.getId());
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			cargoGoodsDao.updateCargo(cargoDto.getCargo());
			List<Goods> goodslist=cargoDto.getGoodsList();
			for(int i=0;i<goodslist.size();i++){
				cargoGoodsDao.updateGoods(goodslist.get(i));
			}
			
			
			
			if (ischangegoods) {
				try {
					LOG.debug("同步更新搜索到的第一个货体数据");
					LOG.error("如果第一个值为0，可能出现修改为负数的情况");
					goods.setId((int) changesdata.get(0).get("GDid"));
					LOG.debug("计算除第一个货体以外的其他货体关联属性的总和");
					for (int i = 1; i < changesdata.size(); i++) {
						goodsnum[0] += Double.parseDouble(changesdata.get(i)
								.get("GDgoodsTotal").toString());// 货体总量
						goodsnum[1] += Double.parseDouble(changesdata.get(i)
								.get("GDgoodsInspect").toString());// 商检数量
						goodsnum[2] += Double.parseDouble(changesdata.get(i)
								.get("GDgoodsTank").toString());// 罐检数量
						goodsnum[3] += Double.parseDouble(changesdata.get(i)
								.get("GDgoodsCurrent").toString());// 当前存量
						goodsnum[4] += Double.parseDouble(changesdata.get(i)
								.get("GDgoodsInPass").toString());// 入库放行总量
						goodsnum[5] += Double.parseDouble(changesdata.get(i)
								.get("GDgoodsOutPass").toString()); // 出库放行总量
					}
					LOG.debug("计算完毕");
					// 商检数量
					if (!Common.empty(cargoDto.getCargo().getGoodsInspect())
							&& Double.valueOf(cargoDto.getCargo().getGoodsInspect()) != Double
									.parseDouble(changesdata.get(0)
											.get("CGgoodsInspect").toString())) {
						goods.setGoodsInspect(Common.fixDouble((Double.valueOf(cargoDto.getCargo()
								.getGoodsInspect()) - goodsnum[1]),3) + "");
						cargoDto.getCargo().setGoodsTotal(cargoDto.getCargo().getGoodsInspect());// 货体总量
						cargoDto.getCargo().setGoodsCurrent(cargoDto.getCargo().getGoodsInspect());// 当前存量
					}
					// 货体总量
					if (!Common.empty(cargoDto.getCargo().getGoodsTotal())
							&& Double.valueOf(cargoDto.getCargo().getGoodsTotal()) != Double
									.parseDouble(changesdata.get(0)
											.get("CGgoodsTotal").toString())) {
						goods.setGoodsTotal(Common.fixDouble((Double.valueOf(cargoDto.getCargo()
								.getGoodsTotal()) - goodsnum[0]),3) + "");
					}
					// 罐检数量
					if (!Common.empty(cargoDto.getCargo().getGoodsTank())
							&& Double.valueOf(cargoDto.getCargo().getGoodsTank()) != Double
									.parseDouble(changesdata.get(0)
											.get("CGgoodsTank").toString())) {
						goods.setGoodsTank(Common.fixDouble((Double.valueOf(cargoDto.getCargo()
								.getGoodsTank()) - goodsnum[2]),3) + "");
					}
					// 当前存量
					if (!Common.empty(cargoDto.getCargo().getGoodsCurrent())
							&& Double.valueOf(cargoDto.getCargo().getGoodsCurrent()) != Double
									.parseDouble(changesdata.get(0)
											.get("CGgoodsCurrent").toString())) {
						goods.setGoodsCurrent(Common.fixDouble((Double.valueOf(cargoDto.getCargo()
								.getGoodsCurrent()) - goodsnum[3]),3) + "");
					}
					// 入库放行总量
					if (!Common.empty(cargoDto.getCargo().getGoodsInPass())
							&& Double.valueOf(cargoDto.getCargo().getGoodsInPass()) != Double
									.parseDouble(changesdata.get(0)
											.get("CGgoodsInPass").toString())) {
						goods.setGoodsInPass(Common.fixDouble((Double.valueOf(cargoDto.getCargo()
								.getGoodsInPass()) - goodsnum[4]),3) + "");
					}
					// 出库放行总量
					if (!Common.empty(cargoDto.getCargo().getGoodsOutPass())
							&& Double.valueOf(cargoDto.getCargo().getGoodsOutPass()) != Double
									.parseDouble(changesdata.get(0)
											.get("CGgoodsOutPass").toString())) {
						goods.setGoodsInspect(Common.fixDouble((Double.valueOf(cargoDto.getCargo()
								.getGoodsOutPass()) - goodsnum[5]),3) + "");
					}
					cargoGoodsDao.updateGoods(goods);
				} catch (Exception e) {
					LOG.error("数据转换错误");
					e.printStackTrace();
				}
			}
		} catch (RuntimeException e) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败" + e);
			LOG.error("更新失败", e);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败", e);
		}
		return oaMsg;
	}

	/**
	 * 更新货体表（如果修改了相关货批的修改货批）
	 * 
	 * @param goods
	 * @param ischangecargo是否自动修改货批
	 * @return
	 * @throws OAException
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateGoods(Goods goods, boolean ischangecargo,boolean isconfirm)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		Cargo cargo = new Cargo();
		Map<String, Object> goodschangedata = null;
		Map<String, Object> cargochangedata = null;
		CargoGoodsDto cgDto = new CargoGoodsDto();
		CargoGoodsDto agDto = new CargoGoodsDto();
		cgDto.setGoodsId(goods.getId());
		agDto.setCargoId(goods.getCargoId());
		goodschangedata = cargoGoodsDao.getGoodsList(cgDto, 0, 0).get(0);
		cargochangedata = cargoGoodsDao.getCargoList(agDto, 0, 0).get(0);
		
		double gGoodsInspect=goodschangedata.get("goodsInspect")==null?0:Double.valueOf(goodschangedata.get("goodsInspect").toString());
		
//		if(ischangecargo){
//			if(isconfirm){
//				//如果修改了商检量
//				if(Double.parseDouble(goods.getGoodsInspect())!=gGoodsInspect){
//					FeeChargeDto fDto=new FeeChargeDto();
//					fDto.setCargoId(goods.getCargoId());
//					// 0,没有生成，1，已经生成
//			  if(feeChargeDao.isMakeInitialFee(fDto)>0){
//				  oaMsg.setCode(Constant.SYS_CODE_DISABLED);
//				  return oaMsg;
//			  }
//				}
//			}
//			
//		}
		
		
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			double oldGTotal=goodschangedata.get("goodsTotal")==null?0:Double.valueOf(goodschangedata.get("goodsTotal").toString());
			
			double gTotal=goods.getGoodsTotal()==null?0:Double.valueOf(goods.getGoodsTotal());
			double gOutPass=Common.empty(goods.getGoodsOutPass())?0:Double.valueOf(goods.getGoodsOutPass());
			double gInPass=Common.empty(goods.getGoodsInPass())?0:Double.valueOf(goods.getGoodsInPass());
			double gCurrent=goodschangedata.get("goodsCurrent")==null?0:Double.valueOf(goodschangedata.get("goodsCurrent").toString());
			
			//现存量需要更新差量
			double diffgCurrent=Common.fixDouble(oldGTotal-gTotal, 3);
			
			
			if(Common.fixDouble(gCurrent-diffgCurrent,3)<0){
				oaMsg.setCode(Constant.SYS_CODE_DISABLED);
				oaMsg.setMsg("该修改导致货体总量小于货体已操作量，修改失败，审批失效！请重新提交！");
				return oaMsg;
			}else{
				
				goods.setGoodsCurrent(Common.fixDouble(gCurrent-diffgCurrent,3)+"");
			}
			
			
			
			
			
			cargoGoodsDao.updateGoods(goods);
			if(isconfirm){
				
				if(Double.parseDouble(goods.getGoodsInspect())!=gGoodsInspect||gTotal!=oldGTotal){
					//货体日志（商检）
					GoodsLog insLog=new GoodsLog();
					insLog.setGoodsId(goods.getId());
					insLog.setType(10);
					insLog.setGoodsChange(-diffgCurrent);
					insLog.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
					insLog.setGoodsSave(Common.fixDouble((gTotal-Math.min(gOutPass, gInPass)),3));
					insLog.setSurplus(Common.fixDouble(gCurrent-diffgCurrent-insLog.getGoodsSave(),3));
					goodsLogDao.add(insLog);
				}
				else{
					//货体日志（封放）
					
					GoodsLog goodsLog=new GoodsLog();
					goodsLog.setGoodsId(goods.getId());
					goodsLog.setType(4);
					goodsLog.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
					goodsLog.setGoodsSave(Common.fixDouble((gTotal-Math.min(gOutPass, gInPass)),3));
					goodsLog.setSurplus(Common.fixDouble(gCurrent-diffgCurrent-goodsLog.getGoodsSave(),3));
					goodsLogDao.add(goodsLog);
					
				}
				
				
			}
			
			
			if (ischangecargo) {
				try {
					cargo.setId(goods.getCargoId());
					LOG.debug("执行更新" + goods.getGoodsTotal() + "====="
							+ goodschangedata.get("goodsTotal").toString());
					
					double goodsTotal=goodschangedata.get("goodsTotal")==null?0:Double.valueOf(goodschangedata.get("goodsTotal").toString());
					if (!Common.empty(goods.getGoodsTotal())
							&& Double.valueOf(goods.getGoodsTotal()) != goodsTotal) {
						LOG.debug("执行cargo的更新"
								+ cargochangedata.get("goodsTotal").toString()
								+ "==" + goods.getGoodsTotal() + "===="
								+ goodsTotal);
						cargo.setGoodsTotal(""
								+ Common.fixDouble((Double.valueOf(cargochangedata.get(
										"goodsTotal").toString())
										+ Double.valueOf(goods.getGoodsTotal()) - goodsTotal),3));
					}
					
					double goodsInspect=goodschangedata.get("goodsInspect")==null?0:Double.valueOf(goodschangedata.get("goodsInspect").toString());
					
					if (!Common.empty(goods.getGoodsInspect())
							&& Double.valueOf(goods.getGoodsInspect()) != goodsInspect) {
						cargo.setGoodsInspect(""
								+ Common.fixDouble((Double.valueOf(cargochangedata.get(
										"goodsInspect").toString())
										+ Double.valueOf(goods
												.getGoodsInspect()) - goodsInspect),3));
						if(Double.valueOf(cargo.getGoodsInspect())>0){
							cargo.setIsInspect(1);
						}else{
							cargo.setIsInspect(0);
						}
					}
					
					double goodsTank=goodschangedata.get("goodsTank")==null?0:Double.valueOf(goodschangedata.get("goodsTank").toString());
					if (!Common.empty(goods.getGoodsTank())
							&& Double.valueOf(goods.getGoodsTank()) != goodsTank) {
						cargo.setGoodsTank(""
								+ Common.fixDouble((Double.valueOf(cargochangedata.get(
										"goodsTank").toString())
										+ Double.valueOf(goods.getGoodsTank()) - goodsTank),3));
					}
					double goodsCurrent=goodschangedata.get("goodsCurrent")==null?0:Double.valueOf(goodschangedata.get("goodsCurrent").toString());
					if (!Common.empty(goods.getGoodsCurrent())
							&& Double.valueOf(goods.getGoodsCurrent()) != goodsCurrent) {
						
						double cargoCurrent=cargochangedata.get(
								"goodsCurrent")==null?0:Double.valueOf(cargochangedata.get(
										"goodsCurrent").toString());
						
						cargo.setGoodsCurrent(""
								+ Common.fixDouble((cargoCurrent
										+ Double.valueOf(goods
												.getGoodsCurrent()) - goodsCurrent),3));
					}
					double goodsInPass=goodschangedata.get("goodsInPass")==null?0:Double.valueOf(goodschangedata.get("goodsInPass").toString());
					if (!Common.empty(goods.getGoodsInPass())
							&& Double.valueOf(goods.getGoodsInPass()) != goodsInPass) {
						
						cargo.setGoodsInPass(""
								+ Common.fixDouble((Double.valueOf(cargochangedata.get(
										"goodsInPass").toString())
										+ Double.valueOf(goods
												.getGoodsInPass()) - goodsInPass),3));
					}
					double goodsOutPass=goodschangedata.get("goodsOutPass")==null?0:Double.valueOf(goodschangedata.get("goodsOutPass").toString());
					if (!Common.empty(goods.getGoodsOutPass())
							&& Double.valueOf(goods.getGoodsOutPass()) != goodsOutPass) {
						cargo.setGoodsOutPass(""
								+ Common.fixDouble((Double.valueOf(cargochangedata.get(
										"goodsOutPass").toString())
										+ Double.valueOf(goods
												.getGoodsOutPass()) - goodsOutPass),3));
					}
					cargoGoodsDao.updateCargo(cargo);
				} catch (Exception e) {
					LOG.error("转换错误");
					e.printStackTrace();
				}
			}

		} catch (RuntimeException e) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败" + e);
			LOG.error("更新失败", e);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败", e);
		}
		return oaMsg;
	}

	/**
	 * 合并货体，将多个原始货体合并成新原始货体
	 * @param ids
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.MERGE)
	public OaMsg mergeGoodsForNewGood(String ids) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			
		
		CargoGoodsDto cgDto=new CargoGoodsDto();
		cgDto.setGoodsIds(ids);
		
		double goodsInspect=0;
		double goodsTank=0;
		double goodsTotal=0;
		double goodsIn=0;
		double goodsOut=0;
		double goodsCurrent=0;
		double goodsInPass=0;
		double goodsOutPass=0;
		
		List<Map<String, Object>> goodsList=cargoGoodsDao.getGoodsList(cgDto, 0, 0);
		long createTime=0l;
		//合并数量
		for(int i=0 ; i<goodsList.size();i++){
			goodsInspect+=goodsList.get(i).get("goodsInspect")==null?0:Double.parseDouble(goodsList.get(i).get("goodsInspect").toString());
			goodsTank+=goodsList.get(i).get("goodsTank")==null?0:Double.parseDouble(goodsList.get(i).get("goodsTank").toString());
			goodsTotal+=goodsList.get(i).get("goodsTotal")==null?0:Double.parseDouble(goodsList.get(i).get("goodsTotal").toString());
			goodsIn+=goodsList.get(i).get("goodsIn")==null?0:Double.parseDouble(goodsList.get(i).get("goodsIn").toString());
			goodsOut+=goodsList.get(i).get("goodsOut")==null?0:Double.parseDouble(goodsList.get(i).get("goodsOut").toString());
			goodsCurrent+=goodsList.get(i).get("goodsCurrent")==null?0:Double.parseDouble(goodsList.get(i).get("goodsCurrent").toString());
			goodsInPass+=goodsList.get(i).get("goodsInPass")==null?0:Double.parseDouble(goodsList.get(i).get("goodsInPass").toString());
			goodsOutPass+=goodsList.get(i).get("goodsOutPass")==null?0:Double.parseDouble(goodsList.get(i).get("goodsOutPass").toString());
			Goods goods=new Goods();
			goods.setId(Integer.parseInt(goodsList.get(i).get("id").toString()));
			goods.setStatus(1);
			
//			cargoGoodsDao.updateGoods(goods);
			cargoGoodsDao.deleteGoods(goodsList.get(i).get("id").toString());
			createTime=Long.parseLong(goodsList.get(i).get("mCreateTime").toString());
			//删除原货体所有日志
			goodsLogDao.deleteGoodsLog(goodsList.get(i).get("id").toString());
		}
		
		goodsInspect=Common.fixDouble(goodsInspect, 3);
		goodsTank=Common.fixDouble(goodsTank, 3);
		goodsTotal=Common.fixDouble(goodsTotal, 3);
		goodsIn=Common.fixDouble(goodsIn, 3);
		goodsOut=Common.fixDouble(goodsOut, 3);
		goodsCurrent=Common.fixDouble(goodsCurrent, 3);
		goodsInPass=Common.fixDouble(goodsInPass, 3);
		goodsOutPass=Common.fixDouble(goodsOutPass, 3);
		
		
		Map<String, Object> data=goodsList.get(0);
		Goods goods=new Goods();
		goods.setCargoId(Integer.parseInt(data.get("cargoId").toString()));
		goods.setContractId(Integer.parseInt(data.get("contractId").toString()));
		goods.setClientId(Integer.parseInt(data.get("clientId").toString()));
//		goods.setCreateTime(new Timestamp(System.currentTimeMillis()));
		goods.setCreateTime(new Timestamp(createTime*1000));
		goods.setProductId(Integer.parseInt(data.get("productId").toString()));
		if(!Common.empty(data.get("lossRate"))){
			goods.setLossRate(data.get("lossRate").toString());
			
		}
		goods.setGoodsInspect(goodsInspect+"");
		goods.setGoodsTank(goodsTank+"");
		goods.setGoodsTotal(goodsTotal+"");
		goods.setGoodsIn(goodsIn+"");
		goods.setGoodsOut(goodsOut+"");
		goods.setGoodsCurrent(goodsCurrent+"");
		goods.setGoodsInPass(goodsInPass+"");
		goods.setGoodsOutPass(goodsOutPass+"");
		
		if(!Common.empty(data.get("isPredict"))){
			goods.setIsPredict(Integer.parseInt(data.get("isPredict").toString()));
		}
		
		int gId=(Integer) cargoGoodsDao.addGoods(goods);
		
		
		//查询车船id
//		int shipId=arrivalDao.getshipId(Integer.parseInt(goodsList.get(0).get("id").toString())).size()==0?-1:Integer.valueOf(arrivalDao.getshipId(Integer.parseInt(goodsList.get(0).get("id").toString())).get(0).get("shipId").toString());
		GoodsLog goodsLog=new GoodsLog();
		goodsLog.setGoodsId(gId);
		goodsLog.setType(1);
		goodsLog.setCreateTime(createTime);
		goodsLog.setGoodsChange(Common.fixDouble(Double.valueOf(goods.getGoodsTotal()),3));
//	goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));
		goodsLog.setGoodsSave(Common.fixDouble(Double.valueOf(goods.getGoodsTotal()),3));
//		goodsLog.setDeliverType(2);
//		goodsLog.setBatchId(shipId);
		goodsLogDao.add(goodsLog);
		
		
		String cargoCode=data.get("cargoCode").toString();
		
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		oaMsg.setMsg("合并成功");
		Map<String,String> map=new HashMap<String, String>();
		map.put("code", cargoCode);
		map.put("cargoId",data.get("cargoId").toString());
		oaMsg.setMap(map);
		
		
		} catch (RuntimeException re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败" + re);
			LOG.error("更新失败", re);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateGoodsCode(int cargoId,String code) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			CargoGoodsDto agDto=new CargoGoodsDto();
			agDto.setCargoId(cargoId);
			List<Map<String, Object>> cargo=cargoGoodsDao.getCargoList(agDto, 0, 0);
			code=cargo.get(0).get("code").toString();
			List<Map<String, Object>> newGoodsList=cargoGoodsDao.getGoodsList(agDto, 0, 0);
			for(int i=0;i<newGoodsList.size();i++){
				Goods newgoods=new Goods();
				newgoods.setId(Integer.parseInt(newGoodsList.get(i).get("id").toString()));
				newgoods.setCode(code+"   "+(i+1)+"/"+newGoodsList.size());
				cargoGoodsDao.updateGoods(newgoods);
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败" + re);
			LOG.error("更新失败", re);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PREDICTGOODS,type=C.LOG_TYPE.CREATE)
	public OaMsg predictGoods(String goodsId,String cargoId, String goodsTotal,String tankId)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
		CargoGoodsDto cargoGoodsDto=new CargoGoodsDto();
		cargoGoodsDto.setCargoId(Integer.parseInt(cargoId));
		Map<String,Object> cargo=cargoGoodsDao.getCargoList(cargoGoodsDto, 0, 0).get(0);
		
		Cargo mCargo=new Cargo();
		mCargo.setIsPredict(1);
//		double goodsTank=Common.empty(cargo.get("goodsTank"))?0:Double.parseDouble(cargo.get("goodsTank").toString());
//		//如果是老货体。。罐检计算差值
//		if(!Common.empty(goodsId)){
//			CargoGoodsDto gdto=new CargoGoodsDto();
//			gdto.setGoodsId(Integer.parseInt(goodsId));
//			
//			Map<String,Object> goods=cargoGoodsDao.getGoodsList(gdto, 0, 0).get(0);
//			
//			double diff=Double.parseDouble(goods.get("goodsTotal").toString())-Double.parseDouble(goodsTotal);
//			
//			mCargo.setGoodsTank(""+Common.fixDouble((goodsTank+diff),3));
//			
//		}else{
//			mCargo.setGoodsTank(""+Common.fixDouble((goodsTank+Double.parseDouble(goodsTotal)),3));
//		}
		mCargo.setId(Integer.parseInt(cargo.get("id").toString()));
		cargoGoodsDao.updateCargo(mCargo);
		
		Goods goods=new Goods();
		int id;
		if(!Common.empty(goodsId)){
			id=Integer.parseInt(goodsId);
			goods.setId(Integer.valueOf(goodsId));
			if(!Common.empty(tankId)){
				goods.setTankId(Integer.parseInt(tankId));
			}
			goods.setGoodsTotal(goodsTotal);
			goods.setGoodsCurrent(goodsTotal);
			cargoGoodsDao.updateGoods(goods);
			
			
			//查询车船id
//			int shipId=Integer.valueOf(arrivalDao.getshipId(id).get(0).get("shipId").toString());
			goodsLogDao.deleteGoodsLog(id+"");
			GoodsLog goodsLog=new GoodsLog();
			goodsLog.setGoodsId(id);
			goodsLog.setType(7);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			goodsLog.setGoodsChange(Common.fixDouble(Double.parseDouble(goods.getGoodsTotal()),3));
//			goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));
			goodsLog.setGoodsSave(Common.fixDouble(Double.parseDouble(goods.getGoodsTotal()),3));
			goodsLog.setSurplus(0);
//			goodsLog.setDeliverType(2);
//			goodsLog.setBatchId(shipId);
			goodsLogDao.add(goodsLog);
			
			
			
		}else{
			goods.setCargoId(Integer.valueOf(cargoId));
			if(!Common.empty(tankId)){
				goods.setTankId(Integer.parseInt(tankId));
			}
			goods.setGoodsTotal(goodsTotal);
			goods.setClientId(Integer.valueOf(cargo.get("clientId").toString()));
			goods.setContractId(Integer.valueOf(cargo.get("contractId").toString()));
			goods.setGoodsCurrent(goodsTotal);
			goods.setGoodsTank(goodsTotal);
			goods.setProductId(Integer.valueOf(cargo.get("productId").toString()));
			goods.setIsPredict(1);
			goods.setGoodsOutPass(0+"");
			goods.setGoodsIn(0+"");
			goods.setGoodsOut(0+"");
			goods.setGoodsInPass(0+"");
			goods.setGoodsInspect(0+"");
			id=(Integer) cargoGoodsDao.addGoods(goods);
			//查询车船id
//			int shipId=Integer.valueOf(arrivalDao.getshipId(id).get(0).get("shipId").toString());
			
			GoodsLog goodsLog=new GoodsLog();
			goodsLog.setGoodsId(id);
			goodsLog.setType(7);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			goodsLog.setGoodsChange(Common.fixDouble(Double.parseDouble(goods.getGoodsTotal()),3));
			goodsLog.setSurplus(0);
			goodsLog.setGoodsSave(Common.fixDouble(Double.parseDouble(goods.getGoodsTotal()),3));
//			goodsLog.setDeliverType(2);
//			goodsLog.setBatchId(shipId);
			goodsLogDao.add(goodsLog);
			
		}
		
		
		

		
		
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (RuntimeException re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败" + re);
			LOG.error("添加失败", re);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "添加失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PREDICTGOODS,type=C.LOG_TYPE.MERGE)
	public OaMsg mergePredict(String predictGoodsId, String goodsId)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			
			CargoGoodsDto cgd=new CargoGoodsDto();
			cgd.setGoodsId(Integer.valueOf(predictGoodsId));
			CargoGoodsDto cgd1=new CargoGoodsDto();
			cgd1.setGoodsId(Integer.valueOf(goodsId));
			//预入库的货体
			Map<String,Object> predictGoods=cargoGoodsDao.getGoodsList(cgd, 0, 0).get(0);
			//需要关联的货体
			Map<String,Object> goods=cargoGoodsDao.getGoodsList(cgd1, 0, 0).get(0);
			
			//差值
			double diff=Common.fixDouble(Double.valueOf(goods.get("goodsTotal").toString())-Double.valueOf(predictGoods.get("goodsTotal").toString()),3);
			
//			double inspectDiff=Double.valueOf(goods.get("goodsInspect").toString())-Double.valueOf(predictGoods.get("goodsInspect").toString());
			double goodsInspect=Common.empty(goods.get("goodsInspect"))?0:Double.valueOf(goods.get("goodsInspect").toString());
			double goodsTank=Common.empty(goods.get("goodsTank"))?0:Double.valueOf(goods.get("goodsTank").toString());
			
			double pGoodsInspect=Common.empty(predictGoods.get("goodsInspect"))?0:Double.valueOf(predictGoods.get("goodsInspect").toString());
			double pGoodsInPass=Common.empty(predictGoods.get("goodsInPass"))?0:Double.valueOf(predictGoods.get("goodsInPass").toString());
			double pGoodsOutPass=Common.empty(predictGoods.get("goodsOutPass"))?0:Double.valueOf(predictGoods.get("goodsOutPass").toString());
			
			CargoGoodsDto cargoDto=new CargoGoodsDto();
			cargoDto.setCargoId(Integer.valueOf(goods.get("cargoId").toString()));
			Map<String,Object> cargoInfo=cargoGoodsDao.getCargoList(cargoDto, 0, 0).get(0);
//			
//			//更新货批罐检数
			Cargo cargo=new Cargo();
			cargo.setId(Integer.valueOf(goods.get("cargoId").toString()));
//			cargo.setGoodsInspect(Common.fixDouble((Double.parseDouble(cargoInfo.get("goodsTank").toString())-goodsInspect),3)+"");
			cargo.setGoodsInspect(Common.fixDouble(Double.parseDouble(cargoInfo.get("goodsInspect").toString())+pGoodsInspect,3)+"");
			cargo.setGoodsInPass(Common.fixDouble(Double.parseDouble(cargoInfo.get("goodsInPass").toString())+pGoodsInPass,3)+"");
			cargo.setGoodsOutPass(Common.fixDouble(Double.parseDouble(cargoInfo.get("goodsOutPass").toString())+pGoodsOutPass,3)+"");
			
			cargoGoodsDao.updateCargo(cargo);
			
			
			Goods upPredictGoods=new Goods();
			
			upPredictGoods.setId(Integer.parseInt(predictGoods.get("id").toString()));
			upPredictGoods.setGoodsTotal(goods.get("goodsTotal").toString());
			upPredictGoods.setGoodsTank(goods.get("goodsTank").toString());
			upPredictGoods.setGoodsCurrent(""+Common.fixDouble(Double.parseDouble(predictGoods.get("goodsCurrent").toString())+Double.parseDouble(upPredictGoods.getGoodsTotal().toString())-Double.parseDouble(predictGoods.get("goodsTotal").toString()), 3));
			upPredictGoods.setIsPredict(2);
			cargoGoodsDao.updateGoods(upPredictGoods);
			
			cargoGoodsDao.deleteGoods(goods.get("id").toString());
			//删除原货体所有日志
			goodsLogDao.deleteGoodsLog(goods.get("id").toString());
			
			
			//查询车船id
//			int shipId=Integer.valueOf(arrivalDao.getshipId(upPredictGoods.getId()).get(0).get("shipId").toString());
			GoodsLog goodsLog=new GoodsLog();
			goodsLog.setGoodsId(Integer.parseInt(predictGoods.get("id").toString()));
			goodsLog.setType(1);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			goodsLog.setGoodsChange(diff);
//		goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));
			//预入库封量=预入库总量-min（放行）+变化量
			double in=predictGoods.get("goodsInPass")==null?0:Double.parseDouble(predictGoods.get("goodsInPass").toString());
			double out=predictGoods.get("goodsOutPass")==null?0:Double.parseDouble(predictGoods.get("goodsOutPass").toString());
			goodsLog.setGoodsSave(Common.fixDouble(Double.parseDouble(predictGoods.get("goodsTotal").toString())-Math.min(in, out)+diff,3));
			goodsLog.setSurplus(Common.fixDouble(Double.parseDouble(upPredictGoods.getGoodsCurrent())-goodsLog.getGoodsSave(),3));
//			goodsLog.setDeliverType(2);
//			goodsLog.setBatchId(shipId);
			goodsLogDao.add(goodsLog);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (OAException e) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("关联失败" + e);
			LOG.error("关联失败", e);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "关联失败", e);
		}
		return oaMsg;
	}

	@Override
	public Object getPredictGoods(CargoGoodsDto cargoDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
		 oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		 oaMsg.getData().addAll(cargoGoodsDao.getGoodsList(cargoDto,
					pageView.getStartRecord(), pageView.getMaxresult()));
			} catch (OAException e) {
				oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
				oaMsg.setMsg("关联失败" + e);
				LOG.error("关联失败", e);
				throw new OAException(Constant.SYS_CODE_DB_ERR, "关联失败", e);
			}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.DELETE)
	public Object deleteGoods(String ids) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
		 oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		 cargoGoodsDao.deleteGoods(ids);
		//删除原货体所有日志
			goodsLogDao.deleteGoodsLog(ids);
			} catch (OAException e) {
				oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
				oaMsg.setMsg("删除失败" + e);
				LOG.error("删除失败", e);
				throw new OAException(Constant.SYS_CODE_DB_ERR, "删除失败", e);
			}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO,type=C.LOG_TYPE.SPLIT)
	public Object sqlitCargo(String clientId,String cargoId, String goodsIdList,
			String goodsCount, String cargoCount, int isCreateGoods)throws OAException {
		
		OaMsg oaMsg=new OaMsg();
		try{
		 oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		 
		 CargoGoodsDto cargoDto=new CargoGoodsDto();
		 cargoDto.setCargoId(Integer.parseInt(cargoId));
		 //原货批
		 Map<String,Object> oldCargoInfo=cargoGoodsDao.getCargoList(cargoDto, 0, 0).get(0);
		 //更新原货批
		 Cargo oldCargo=new Cargo();
		 oldCargo.setId(Integer.parseInt(oldCargoInfo.get("id").toString()));
		 oldCargo.setGoodsTotal(new BigDecimal(Double.parseDouble(oldCargoInfo.get("goodsTotal").toString())-Double.parseDouble(cargoCount)).setScale(3,RoundingMode.HALF_UP).doubleValue()+"");
		 oldCargo.setGoodsPlan(new BigDecimal(Double.parseDouble(oldCargoInfo.get("goodsPlan").toString())-Double.parseDouble(cargoCount)).setScale(3,RoundingMode.HALF_UP).doubleValue()+"");
		 
		 
		 
		 //添加新货批
		 Cargo newCargo=new Cargo();
		 newCargo.setArrivalId(Integer.parseInt(oldCargoInfo.get("arrivalId").toString()));
		 newCargo.setClientId(Integer.parseInt(clientId));
		 newCargo.setProductId(Integer.parseInt(oldCargoInfo.get("productId").toString()));
		 newCargo.setGoodsTotal(cargoCount);
		 newCargo.setGoodsInPass("0");
		 newCargo.setGoodsOutPass("0");
		 newCargo.setGoodsInspect("0");
		 newCargo.setType(Integer.parseInt(oldCargoInfo.get("type").toString()));
		 
		 //如果船检过，就加上
		 if(Integer.parseInt(oldCargoInfo.get("cargoStatus").toString())>=8){
		 if(oldCargoInfo.get("goodsShip").toString().equals("0")){
			 newCargo.setGoodsShip("0");
		 }else{
			 newCargo.setGoodsShip(cargoCount);
		 }
		 if(oldCargoInfo.get("goodsTank").toString().equals("0")){
			 newCargo.setGoodsTank("0");
		 }else{
			 
			 newCargo.setGoodsTank(Common.fixDouble(Double.parseDouble(cargoCount)*(Common.fixDouble(Double.parseDouble(oldCargoInfo.get("goodsTank").toString()),3)/Common.fixDouble(Double.parseDouble(oldCargoInfo.get("goodsTotal").toString()), 3)),3)+"");
		 }
		 
		 
		 if(Integer.parseInt(oldCargoInfo.get("cargoStatus").toString())>=8){
			 //如果船检过   就减量
			 if(!oldCargoInfo.get("goodsShip").toString().equals("0")){
				 oldCargo.setGoodsShip(new BigDecimal(Double.parseDouble(oldCargoInfo.get("goodsShip").toString())-Double.parseDouble(cargoCount)).setScale(3,RoundingMode.HALF_UP).doubleValue()+"");
			 }
			 if(!oldCargoInfo.get("goodsTank").toString().equals("0")){
				 oldCargo.setGoodsTank(new BigDecimal(Double.parseDouble(oldCargoInfo.get("goodsTank").toString())-Double.parseDouble(newCargo.getGoodsTank())).setScale(3,RoundingMode.HALF_UP).doubleValue()+"");
			 }
		 }
		 
		 }
		 newCargo.setGoodsPlan(cargoCount);
		 newCargo.setGoodsTotal(cargoCount);
		 newCargo.setRequirement("");
		 
//		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			String date = format.format(new Date());
//			String iNo=date.replace("-", "");
//			LOG.debug(iNo);
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
			newCargo.setNo(No);
			newCargo.setConnectNo(No);
		 
		 int newCargoId=cargoGoodsDao.addIntoCargo(newCargo);
		//添加首期费
			InitialFee initialFee=new InitialFee();
			initialFee.setCargoId(newCargoId);
			initialFeeDao.addInitialFee(initialFee);

		 
		 int shipgoodsId=0;
		 long cTime=0;
		 Timestamp ccTime = null;
		 //如果有拆过货体
		 if(!Common.empty(goodsIdList)){
			 String[] idList=goodsIdList.split(",");
			 shipgoodsId=Integer.parseInt(idList[0]);
			 String[] countList=goodsCount.split(",");
			 
			 double goodsOutPass=0;
			 double goodsInPass=0;
			 
			 double goodsTank=0;
			 for(int i=0;i<idList.length;i++){
				 CargoGoodsDto gDt=new CargoGoodsDto();
				 //得到拆过的货体
				 gDt.setGoodsId(Integer.valueOf(idList[i]));
				 Map<String,Object> oldGoods=cargoGoodsDao.getGoodsList(gDt, 0, 0).get(0);
				 //计算需要从原货批扣除的放行量
				 cTime=Long.parseLong(oldGoods.get("mCreateTime").toString());
				 ccTime=new Timestamp(Long.parseLong(oldGoods.get("mCreateTime").toString())*1000);
				 if(!Common.empty(oldGoods.get("goodsOutPass")))
				 goodsOutPass+=Double.parseDouble(oldGoods.get("goodsOutPass").toString());
				 if(!Common.empty(oldGoods.get("goodsInPass")))
				 goodsInPass+=Double.parseDouble(oldGoods.get("goodsInPass").toString());
				 //更新原货体     放行量清零
				 Goods oldGood=new Goods();
				 oldGood.setId(Integer.parseInt(oldGoods.get("id").toString()));
				 oldGood.setGoodsTotal(new BigDecimal(Double.parseDouble(oldGoods.get("goodsTotal").toString())-Double.parseDouble(countList[i])).setScale(3,RoundingMode.HALF_UP).doubleValue()  +"");
				 oldGood.setGoodsCurrent(oldGood.getGoodsTotal());
				 oldGood.setGoodsTank(new BigDecimal(Double.parseDouble(oldGoods.get("goodsTank").toString())-(Double.parseDouble(countList[i])*(Common.fixDouble(Double.parseDouble(oldCargoInfo.get("goodsTank").toString()),3)/Common.fixDouble(Double.parseDouble(oldCargoInfo.get("goodsTotal").toString()), 3)))).setScale(3,RoundingMode.HALF_UP).doubleValue()+"");
				 oldGood.setGoodsInPass("0");
				 oldGood.setGoodsOutPass("0");
				 cargoGoodsDao.updateGoods(oldGood);
				 
				 
				 if(!Common.isNull(oldGoods.get("isPredict").toString())&&Integer.parseInt(oldGoods.get("isPredict").toString())==1){
					 
				 }else{
					 goodsTank=Common.fixDouble(goodsTank+Common.fixDouble(goodsTank+Double.parseDouble(countList[i]), 3),3);
				 }
				 
				//查询车船id
//					int shipId=Integer.valueOf(arrivalDao.getshipId(oldGood.getId()).get(0).get("shipId").toString());
					//更新老货体入库日志
				 goodsLogDao.deleteGoodsLog(oldGood.getId()+"");
					GoodsLog goodsLog=new GoodsLog();
					goodsLog.setGoodsId(oldGood.getId());
					goodsLog.setType(1);
					goodsLog.setCreateTime(Long.parseLong(oldGoods.get("mCreateTime").toString()));
					goodsLog.setGoodsChange(Common.fixDouble(Double.parseDouble(oldGood.getGoodsTotal()),3));
//					goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));
					goodsLog.setGoodsSave(Common.fixDouble(Double.parseDouble(oldGood.getGoodsTotal()),3));
//					goodsLog.setDeliverType(2);
//					goodsLog.setBatchId(shipId);
					goodsLogDao.add(goodsLog);
				 
					
				 
			 }
			 //原货批扣掉放行量
			 oldCargo.setGoodsInPass(new BigDecimal(Double.parseDouble(oldCargoInfo.get("goodsInPass").toString())-goodsInPass).setScale(3,RoundingMode.HALF_UP).doubleValue()+"");
			 oldCargo.setGoodsOutPass(new BigDecimal(Double.parseDouble(oldCargoInfo.get("goodsOutPass").toString())-goodsOutPass).setScale(3,RoundingMode.HALF_UP).doubleValue()+"");
//			 oldCargo.setGoodsTank(Common.fixDouble(Double.parseDouble(oldCargoInfo.get("goodsTank").toString())-goodsTank, 3)+"");
		 }
		 cargoGoodsDao.updateCargo(oldCargo);
		 
		 //如果需要同步生成货体
		 if(isCreateGoods==1){
			 Goods newgood=new Goods();
			 newgood.setCargoId(newCargoId);
			 newgood.setClientId(Integer.valueOf(clientId));
			 newgood.setCode(oldCargoInfo.get("code").toString());
			 newgood.setGoodsCurrent(cargoCount);
			 newgood.setGoodsTotal(cargoCount);
			 //如果罐检过   
			 if(!oldCargoInfo.get("goodsTank").toString().equals("0")){
				 newgood.setGoodsTank((Common.fixDouble(Double.parseDouble(cargoCount)*(Common.fixDouble(Double.parseDouble(oldCargoInfo.get("goodsTank").toString()),3)/Common.fixDouble(Double.parseDouble(oldCargoInfo.get("goodsTotal").toString()), 3)),3))+"");
			 }
			 newgood.setCreateTime(ccTime);
			 newgood.setProductId(Integer.parseInt(oldCargoInfo.get("productId").toString()));
			 int goodsId= (Integer) cargoGoodsDao.addGoods(newgood);
			 
			 	//查询车船id
//				int shipId=Integer.valueOf(arrivalDao.getshipId(Integer.valueOf(shipgoodsId)).get(0).get("shipId").toString());
				
				GoodsLog goodsLog1=new GoodsLog();
				goodsLog1.setGoodsId(goodsId);
				goodsLog1.setType(1);
				goodsLog1.setCreateTime(cTime);
				goodsLog1.setGoodsChange(Common.fixDouble(Double.parseDouble(newgood.getGoodsTotal()),3));
//				goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));
				goodsLog1.setGoodsSave(Common.fixDouble(Double.parseDouble(newgood.getGoodsTotal()),3));
//				goodsLog1.setDeliverType(2);
//				goodsLog1.setBatchId(shipId);
				goodsLogDao.add(goodsLog1);
			 
			 
		 }
		 
		 
		 
			} catch (OAException e) {
				oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
				oaMsg.setMsg("拆分失败" + e);
				LOG.error("拆分失败", e);
				throw new OAException(Constant.SYS_CODE_DB_ERR, "拆分失败", e);
			}
		return oaMsg;
	}

	@Override
	public Object getOriginalGoodsList(CargoGoodsDto cargoDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		try {
			cargoDto.setGoodsStatus("0");
			cargoDto.setTankGoods(true);
			oaMsg.getData().addAll(cargoGoodsDao.getGoodsList(cargoDto,pageView.getStartRecord(), pageView.getMaxresult()));
			count = cargoGoodsDao.getGoodsCount(cargoDto);
			Map<String, String> map = new HashMap<String, String>();
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (OAException e) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败" + e);
			LOG.error("查询失败", e);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", e);
		}
		
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.UPDATE)
	public Object updateGoodsCode(int ladingId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LadingDto agDto=new LadingDto();
			agDto.setId(ladingId);
			List<Map<String, Object>> lading=ladingDao.getLadingList(agDto, 0, 0);
			String code=lading.get(0).get("code").toString();
			List<Map<String, Object>> newGoodsList=ladingDao.getGoodsListByLadingId(ladingId);
			for(int i=0;i<newGoodsList.size();i++){
				Goods newgoods=new Goods();
				newgoods.setId(Integer.parseInt(newGoodsList.get(i).get("id").toString()));
				newgoods.setCode(code+"   "+(i+1)+"/"+newGoodsList.size());
				cargoGoodsDao.updateGoods(newgoods);
			}
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException re) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败" + re);
			LOG.error("更新失败", re);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败", re);
		}
		return oaMsg;
	}

	@Override
	public Object getGoodsById(CargoGoodsDto cargoDto) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.getData().addAll(cargoGoodsDao.getGoodsList(cargoDto,0,0));
		} catch (OAException e) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败" + e);
			LOG.error("查询失败", e);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", e);
		}
		
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.SPLIT)
	public OaMsg sqlitPassGoods(int oldGoodsId, Goods newGoods) throws OAException {
		OaMsg oaMsg = new OaMsg();

		try {
			CargoGoodsDto cargoGoodsDto = new CargoGoodsDto();
			cargoGoodsDto.setGoodsId(oldGoodsId);
			Map<String, Object> oldGoods = cargoGoodsDao.getGoodsList(
					cargoGoodsDto, 0, 0).get(0);

			// 搜索货体提单
			LadingDto ladingDto = new LadingDto();
			ladingDto.setGoodsId(oldGoodsId);
			List<Map<String, Object>> ladingInfo = ladingDao
					.getLadingByGoodsId(ladingDto);
			int ladingId = 0;
			int ladingType = 0;
			int ladingClientId=0;
			int ladingBuyClientId=0;
			if (ladingInfo.size() > 0) {
				ladingId = Integer.parseInt(ladingInfo.get(0).get("id")
						.toString());
				ladingType = Integer.parseInt(ladingInfo.get(0).get("type")
						.toString());
				ladingClientId=Integer.parseInt(ladingInfo.get(0).get("clientId")
						.toString());
				ladingBuyClientId=Integer.parseInt(ladingInfo.get(0).get("receiveClientId")
						.toString());
			}

			// 更新原货体  现存量,货体总量,调入量,放心量
			Goods oldgoods = new Goods();
			oldgoods.setId(oldGoodsId);
			oldgoods.setGoodsTotal(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsTotal").toString())-Double.parseDouble(newGoods.getGoodsTotal()), 3)+"");
			oldgoods.setGoodsIn(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsIn").toString())-Double.parseDouble(newGoods.getGoodsTotal()), 3)+"");
			oldgoods.setGoodsInPass(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsInPass").toString())-Double.parseDouble(newGoods.getGoodsTotal()), 3)+"");
			oldgoods.setGoodsOutPass(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsOutPass").toString())-Double.parseDouble(newGoods.getGoodsTotal()), 3)+"");
			oldgoods.setGoodsCurrent(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsCurrent").toString())-Double.parseDouble(newGoods.getGoodsTotal()), 3)+"");
			cargoGoodsDao.updateGoods(oldgoods);
			

			// 新货体
			newGoods.setCargoId(Integer.parseInt(oldGoods.get("cargoId")
					.toString()));
			newGoods.setGoodsCurrent(newGoods.getGoodsTotal());
			newGoods.setGoodsTank("0");
			newGoods.setGoodsOut("0");
			newGoods.setGoodsInspect("0");
			newGoods.setStatus(0);
			if (ladingId != 0)
				newGoods.setGoodsIn(newGoods.getGoodsTotal());
			newGoods.setGoodsInPass(newGoods.getGoodsTotal());
			newGoods.setGoodsOutPass(newGoods.getGoodsTotal());
//			// 如果原货体出库放行量>=新货体量 新货体放行量=新货体量 否则变新货体放行量=原货体出库放行量
//			if (Double.parseDouble(oldGoods.get("goodsOutPass").toString()) >= Double
//					.parseDouble(newGoods.getGoodsTotal())) {
//				newGoods.setGoodsOutPass(Double.parseDouble(newGoods
//						.getGoodsTotal()) + "");
//			} else {
//				newGoods.setGoodsOutPass(Double.parseDouble(oldGoods.get(
//						"goodsOutPass").toString())
//						+ "");
//			}

			newGoods.setClientId(Integer.valueOf(oldGoods.get("clientId")
					.toString()));
			
			if (!Common.empty(oldGoods.get("ladingClientId"))&&!"0".equals(oldGoods.get("ladingClientId").toString())) {
				newGoods.setLadingClientId(Integer.valueOf(oldGoods.get("ladingClientId")
						.toString()));
				}
			
			if (!Common.empty(oldGoods.get("contractId"))) {
			newGoods.setContractId(Integer.valueOf(oldGoods.get("contractId")
					.toString()));
			}
			if (!Common.empty(oldGoods.get("lossRate"))) {
				newGoods.setLossRate(oldGoods.get("lossRate").toString());
			}
			newGoods.setStatus(Integer.valueOf(oldGoods.get("status")
					.toString()));
			newGoods.setProductId(Integer.valueOf(oldGoods.get("productId")
					.toString()));
			if (!Common.empty(oldGoods.get("isPredict"))) {
				newGoods.setIsPredict(Integer.parseInt(oldGoods
						.get("isPredict").toString()));
			}
			if (!Common.empty(oldGoods.get("goodsGroupId"))) {
				newGoods.setGoodsGroupId(Integer.parseInt(oldGoods.get(
						"goodsGroupId").toString()));
			}
//			if (!Common.empty(oldGoods.get("rootGoodsId"))) {
//				newGoods.setRootGoodsId(Integer.parseInt(oldGoods.get(
//						"rootGoodsId").toString()));
//			}
			if (!Common.empty(oldGoods.get("sourceGoodsId"))) {
				newGoods.setSourceGoodsId(Integer.parseInt(oldGoods.get(
						"sourceGoodsId").toString()));
			}
			
			if (!Common.empty(oldGoods.get("rootGoodsId"))) {
				newGoods.setRootGoodsId(Integer.parseInt(oldGoods.get(
						"rootGoodsId").toString()));
			}
			
			
//			//老货体的母货体为空，来源货体不为空，则母货体为老货体
//			if((Common.empty(oldGoods.get("rootGoodsId"))||Integer.parseInt(oldGoods.get("rootGoodsId").toString())==0)&&!Common.empty(oldGoods.get("sourceGoodsId"))){
//				newGoods.setRootGoodsId(Integer.valueOf(oldGoods.get("id")
//						.toString()));
//				//如果老货体母货体不为空，则新货体的母货体继承
//			}else if(!Common.empty(oldGoods.get("rootGoodsId"))){
//				newGoods.setRootGoodsId(Integer.valueOf(oldGoods.get(
//						"rootGoodsId").toString()));
//			}
			//如果老货体的母货体和来源货体都是空，新货体的母货体也为空
			
//			int codeCount=cargoGoodsDao.getTheSameGoods(oldGoods.get("code").toString(),oldGoodsId);
			newGoods.setCode(oldGoods.get("code").toString()+"  -1");
			
			// 添加货体
			int newgoodsId = (Integer) cargoGoodsDao.addGoods(newGoods);
//			int oldgoodsId = (Integer) cargoGoodsDao.addGoods(oldgoods);

			// 添加日志表

			
			//原货体调入量减少
			
			graftingCargoDao.updateRKDJLog(oldGoodsId, Double.parseDouble(newGoods.getGoodsTotal()));
			
			
			//获得原货体调入操作时间，用于生成新货体调入日志
			Map<String, Object> timeInfo = graftingCargoDao.getRKDJTime(oldGoodsId).get(0);
			//修复原货体日志
			graftingCargoDao.repairLog(oldGoodsId);
//			GoodsLog goodsLogOld=new GoodsLog();
//			goodsLogOld.setGoodsId(oldGoodsId);
//			goodsLogOld.setType(3);
//			goodsLogOld.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
//			goodsLogOld.setGoodsChange(Common.fixDouble(Double.parseDouble("-"+newGoods.getGoodsIn()),3));
//			goodsLogOld.setClientId(Integer.parseInt(oldGoods.get("clientId").toString()));
//			goodsLogOld.setGoodsSave(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsTotal").toString())-Math.min(Double.parseDouble(oldGoods.get("goodsInPass").toString()),Double.parseDouble(oldGoods.get("goodsOutPass").toString())),3));
//			goodsLogOld.setSurplus(Common.fixDouble(Double.parseDouble(oldgoods.getGoodsCurrent())-goodsLogOld.getGoodsSave(),3));
//		    goodsLogOld.setLadingId(ladingId);
//			goodsLogOld.setLadingType(ladingType); 
//			goodsLogOld.setNextGoodsId(newgoodsId); 
//			goodsLogOld.setLadingClientId(ladingBuyClientId);
//			goodsLogDao.add(goodsLogOld);
			
			long crTime=Long.parseLong(timeInfo.get("createTime").toString());
			
			// 新货体调入
			GoodsLog goodsLog = new GoodsLog();
			goodsLog.setGoodsId(newgoodsId);
			goodsLog.setType(2);
			goodsLog.setCreateTime(crTime);
			goodsLog.setGoodsChange(Common.fixDouble(
					Double.parseDouble(newGoods.getGoodsIn()), 3));
			// goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));

			goodsLog.setGoodsSave(Common.fixDouble(
					(Double.valueOf(newGoods.getGoodsTotal()) - Math.min(
							Double.valueOf(newGoods.getGoodsInPass()),
							Double.valueOf(newGoods.getGoodsOutPass()))), 3));
			goodsLog.setSurplus(Common.fixDouble(
					Double.valueOf(newGoods.getGoodsCurrent())
							- goodsLog.getGoodsSave(), 3));
			goodsLog.setLadingId(ladingId);
			goodsLog.setLadingClientId(ladingClientId);
			goodsLog.setLadingType(ladingType);
			goodsLogDao.add(goodsLog);


			// 新货体封放
						GoodsLog goodsLog1 = new GoodsLog();
						goodsLog1.setGoodsId(newgoodsId);
						goodsLog1.setType(4);
						goodsLog1.setCreateTime(crTime+1000);
						goodsLog1.setGoodsChange(0);
						// goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));

						goodsLog1.setGoodsSave(0);
						goodsLog1.setSurplus(Common.fixDouble(
								Double.valueOf(newGoods.getGoodsTotal()), 3));
						goodsLog1.setLadingId(ladingId);
						goodsLog1.setLadingClientId(ladingClientId);
						goodsLog1.setLadingType(ladingType);
						goodsLogDao.add(goodsLog1);
			
			//拆分上级货体的调出操作
						graftingCargoDao.splitSourceOutLog(oldGoodsId, Common.fixDouble(Double.parseDouble(newGoods.getGoodsTotal()),3), newgoodsId);
						graftingCargoDao.repairLog(Integer.parseInt(oldGoods.get(
								"sourceGoodsId").toString()));

			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, String> map = new HashMap<String, String>();
			// map.put("code", oldGoods.get("code").toString().split(" ")[0]);
			map.put("cargoId", oldGoods.get("cargoId").toString() + "");
			map.put("ladingId", ladingId + "");
			oaMsg.setMap(map);
		} catch (OAException re) {
			LOG.error("拆分失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "拆分失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.SPLIT)
	public OaMsg sqlitOriginalGoods2(String oldGoodsId, Goods newGoods)
			throws OAException {
		OaMsg oaMsg = new OaMsg();

		try {
			CargoGoodsDto cargoGoodsDto = new CargoGoodsDto();
			cargoGoodsDto.setGoodsId(Integer.parseInt(oldGoodsId));
			Map<String, Object> oldGoods = cargoGoodsDao.getGoodsList(
					cargoGoodsDto, 0, 0).get(0);

			

			// 更新原货体  调出量，现存量
			Goods oldgoods = new Goods();
			oldgoods.setId(Integer.parseInt(oldGoodsId));
			oldgoods.setGoodsOut(Common.fixDouble(Double.parseDouble(newGoods.getGoodsTotal())+Double.parseDouble(oldGoods.get("goodsOut").toString()), 3)+"");
			oldgoods.setGoodsCurrent(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsCurrent").toString())-Double.parseDouble(newGoods.getGoodsTotal()), 3)+"");
			cargoGoodsDao.updateGoods(oldgoods);
			

			// 新货体
			newGoods.setCargoId(Integer.parseInt(oldGoods.get("cargoId")
					.toString()));
			newGoods.setGoodsCurrent(newGoods.getGoodsTotal());
			newGoods.setGoodsTank(newGoods.getGoodsTotal());
				newGoods.setGoodsIn(newGoods.getGoodsTotal());
			newGoods.setGoodsInPass(newGoods.getGoodsTotal());
			
//			// 如果原货体出库放行量>=新货体量 新货体放行量=新货体量 否则变新货体放行量=原货体出库放行量
			if (Double.parseDouble(oldGoods.get("goodsOutPass").toString()) >= Double
					.parseDouble(newGoods.getGoodsTotal())) {
				newGoods.setGoodsOutPass(Double.parseDouble(newGoods
						.getGoodsTotal()) + "");
			} else {
				newGoods.setGoodsOutPass(Double.parseDouble(oldGoods.get(
						"goodsOutPass").toString())
						+ "");
			}
				newGoods.setGoodsInspect("0");
				newGoods.setGoodsOut("0");
			newGoods.setClientId(Integer.valueOf(oldGoods.get("clientId")
					.toString()));
			newGoods.setContractId(Integer.valueOf(oldGoods.get("contractId")
					.toString()));
			if (!Common.empty(oldGoods.get("lossRate"))) {
				newGoods.setLossRate(oldGoods.get("lossRate").toString());
			}
			newGoods.setStatus(Integer.valueOf(oldGoods.get("status")
					.toString()));
			newGoods.setProductId(Integer.valueOf(oldGoods.get("productId")
					.toString()));
			if (!Common.empty(oldGoods.get("isPredict"))) {
				newGoods.setIsPredict(Integer.parseInt(oldGoods
						.get("isPredict").toString()));
			}
			if (!Common.empty(oldGoods.get("goodsGroupId"))) {
				newGoods.setGoodsGroupId(Integer.parseInt(oldGoods.get(
						"goodsGroupId").toString()));
			}
			newGoods.setRootGoodsId(0);
			newGoods.setSourceGoodsId(Integer.parseInt(oldGoods.get("id").toString()));
			
//			if (!Common.empty(oldGoods.get("rootGoodsId"))) {
//				newGoods.setRootGoodsId(Integer.parseInt(oldGoods.get(
//						"rootGoodsId").toString()));
//			}

//			if (!Common.empty(oldGoods.get("sourceGoodsId"))) {
//				newGoods.setSourceGoodsId(Integer.parseInt(oldGoods.get(
//						"sourceGoodsId").toString()));
//			}
			
			
//			//老货体的母货体为空，来源货体不为空，则母货体为老货体
//			if((Common.empty(oldGoods.get("rootGoodsId"))||Integer.parseInt(oldGoods.get("rootGoodsId").toString())==0)&&!Common.empty(oldGoods.get("sourceGoodsId"))){
//				newGoods.setRootGoodsId(Integer.valueOf(oldGoods.get("id")
//						.toString()));
//				//如果老货体母货体不为空，则新货体的母货体继承
//			}else if(!Common.empty(oldGoods.get("rootGoodsId"))){
//				newGoods.setRootGoodsId(Integer.valueOf(oldGoods.get(
//						"rootGoodsId").toString()));
//			}
//			//如果老货体的母货体和来源货体都是空，新货体的母货体也为空
			
			int codeCount=cargoGoodsDao.getTheSameGoods(oldGoods.get("code").toString(),Integer.parseInt(oldGoodsId));
			newGoods.setCode(oldGoods.get("code").toString()+"  -"+(codeCount+1));
			
			// 添加货体
			int newgoodsId = (Integer) cargoGoodsDao.addGoods(newGoods);
//			int oldgoodsId = (Integer) cargoGoodsDao.addGoods(oldgoods);

			// 添加日志表

			
			
			//原货体调出
			GoodsLog goodsLogOld=new GoodsLog();
			goodsLogOld.setGoodsId(Integer.parseInt(oldGoodsId));
			goodsLogOld.setType(3);
			goodsLogOld.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
			goodsLogOld.setGoodsChange(Common.fixDouble(Double.parseDouble("-"+newGoods.getGoodsIn()),3));
			goodsLogOld.setClientId(Integer.parseInt(oldGoods.get("clientId").toString()));
			goodsLogOld.setGoodsSave(Common.fixDouble(Double.parseDouble(oldGoods.get("goodsTotal").toString())-Math.min(Double.parseDouble(oldGoods.get("goodsInPass").toString()),Double.parseDouble(oldGoods.get("goodsOutPass").toString())),3));
			goodsLogOld.setSurplus(Common.fixDouble(Double.parseDouble(oldgoods.getGoodsCurrent())-goodsLogOld.getGoodsSave(),3));
			goodsLogOld.setNextGoodsId(newgoodsId);
			goodsLogDao.add(goodsLogOld);
			
			
			
			// 新货体调入
			GoodsLog goodsLog = new GoodsLog();
			goodsLog.setGoodsId(newgoodsId);
			goodsLog.setType(2);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime() / 1000
					+ ""));
			goodsLog.setGoodsChange(Common.fixDouble(
					Double.parseDouble(newGoods.getGoodsIn()), 3));
			// goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));

			goodsLog.setGoodsSave(Common.fixDouble(
					(Double.valueOf(newGoods.getGoodsTotal()) - Math.min(
							Double.valueOf(newGoods.getGoodsInPass()),
							Double.valueOf(newGoods.getGoodsOutPass()))), 3));
			goodsLog.setSurplus(Common.fixDouble(
					Double.valueOf(newGoods.getGoodsCurrent())
							- goodsLog.getGoodsSave(), 3));
			goodsLogDao.add(goodsLog);

//			// 新货体调入
//			GoodsLog goodsLog2 = new GoodsLog();
//			goodsLog2.setGoodsId(oldgoodsId);
//			goodsLog2.setType(2);
//			goodsLog2.setCreateTime(Long.parseLong(new Date().getTime() / 1000
//					+ ""));
//			goodsLog2.setGoodsChange(Common.fixDouble(
//					Double.parseDouble(oldgoods.getGoodsIn()), 3));
//			// goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));
//
//			goodsLog2.setGoodsSave(Common.fixDouble(
//					(Double.valueOf(oldgoods.getGoodsTotal()) - Math.min(
//							Double.valueOf(oldgoods.getGoodsInPass()),
//							Double.valueOf(oldgoods.getGoodsOutPass()))), 3));
//			goodsLog2.setSurplus(Common.fixDouble(
//					Double.valueOf(oldgoods.getGoodsCurrent())
//							- goodsLog2.getGoodsSave(), 3));
//
//			goodsLog2.setLadingId(ladingId);
//			goodsLog2.setLadingType(ladingType);
//			goodsLogDao.add(goodsLog2);

//			// 删除原货体
//			cargoGoodsDao.deleteGoods(oldGoodsId + "");
//			// 删除原货体所有日志
//			goodsLogDao.deleteGoodsLog(oldGoodsId + "");

			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			Map<String, String> map = new HashMap<String, String>();
			// map.put("code", oldGoods.get("code").toString().split(" ")[0]);
			oaMsg.setMap(map);
		} catch (OAException re) {
			LOG.error("拆分失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "拆分失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO,type=C.LOG_TYPE.UPDATE)
	public void updateCargo(Cargo cargo) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			
			cargoGoodsDao.updateCargo(cargo);
		} catch (RuntimeException e) {
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败" + e);
			LOG.error("更新失败", e);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败", e);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.UPDATE)
	public void updateGoods(Goods goods) throws OAException {
		OaMsg oaMsg = new OaMsg();
	try {
		
		cargoGoodsDao.updateGoods(goods);
	} catch (OAException e) {
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		oaMsg.setMsg("更新失败" + e);
		LOG.error("更新失败", e);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败", e);
	}}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.UPDATE)
	public OaMsg editGoodsTotal(Integer goodsId, String goodsTotal,Integer isPredict)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
	try {
		CargoGoodsDto dto=new CargoGoodsDto();
		dto.setGoodsId(goodsId);
		dto.setIsPredict(isPredict);
		Map<String, Object> goodsInfo=cargoGoodsDao.getGoodsList(dto, 0, 0).get(0);
		//总量变化量
		double goodsChange=Common.fixDouble(Double.valueOf(goodsInfo.get("goodsTotal").toString())-Double.valueOf(goodsTotal),3);
		//可提量
		double check=Common.fixDouble(Double.valueOf(goodsInfo.get("goodsCurrent").toString())-(Double.valueOf(goodsInfo.get("goodsTotal").toString())-Math.min(Double.valueOf(goodsInfo.get("goodsInPass").toString()), Double.valueOf(goodsInfo.get("goodsOutPass").toString()))),3);
		
		if(goodsChange>0&&check-goodsChange<0){
			oaMsg.setCode(Constant.SYS_CODE_DISABLED);
			return oaMsg;
		}
		Goods goods=new Goods();
		goods.setId(goodsId);
		goods.setGoodsTotal(goodsTotal);
		goods.setGoodsCurrent(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsCurrent").toString())-goodsChange,3)+"");
		
		if(goodsChange>=0){
			goods.setGoodsInPass(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsInPass").toString())-goodsChange,3)+"");
			goods.setGoodsOutPass(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsOutPass").toString())-goodsChange,3)+"");
			}else{
				goods.setGoodsInPass(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsInPass").toString()),3)+"");
				goods.setGoodsOutPass(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsOutPass").toString()),3)+"");
					
			}
		//货体日志--入库
		int type=1;
		if(isPredict==1){
			type=7;
		}
		GoodsLog goodsLogOld=new GoodsLog();
		goodsLogOld.setGoodsId(goodsId);
		goodsLogOld.setType(type);
		goodsLogOld.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
		goodsLogOld.setGoodsChange(-goodsChange);
		goodsLogOld.setClientId(Integer.parseInt(goodsInfo.get("clientId").toString()));
		goodsLogOld.setGoodsSave(Common.fixDouble(Double.parseDouble(goods.getGoodsTotal())-Math.min(Double.parseDouble(goods.getGoodsInPass()),Double.parseDouble(goods.getGoodsOutPass())),3));
		goodsLogOld.setSurplus(Common.fixDouble(Double.parseDouble(goods.getGoodsCurrent())-goodsLogOld.getGoodsSave(),3));
		goodsLogDao.add(goodsLogOld);
		
		cargoGoodsDao.updateGoods(goods);
		
		Integer cargoId=Integer.valueOf(goodsInfo.get("cargoId").toString());
		//更新货批
		cargoGoodsDao.updateCargoAll(cargoId);
		
		
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		return oaMsg;
		
	} catch (RuntimeException e) {
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		oaMsg.setMsg("更新失败" + e);
		LOG.error("更新失败", e);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败", e);
	}}

	@Override
	public OaMsg clientGoods(CargoGoodsDto agDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		List<Map<String, Object>> cargoGoodsData = new ArrayList<Map<String, Object>>();
		int count = 0;
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			LOG.debug("获取货体表信息");
			cargoGoodsData = cargoGoodsDao.getGoodsList(agDto,
					pageView.getStartRecord(), pageView.getMaxresult());

			
			if(cargoGoodsData.size()>0){
				for(int i=0;i<cargoGoodsData.size();i++){
					cargoGoodsData.get(i).put("shipCount", shipArrivalDao.getArrivalPlanAmount(Integer.parseInt(cargoGoodsData.get(i).get("id").toString())).get("amount"));
				}
			}
			
			
			oaMsg.getData().addAll(cargoGoodsData);

			count = cargoGoodsDao.getGoodsCount(agDto);
//			pageView.setTotalrecord(count);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage() + "");
			map.put("totalpage", pageView.getTotalpage() + "");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.LOSS)
	public OaMsg goodsLoss(Integer goodsId, String lossCount)throws OAException {
		OaMsg oaMsg = new OaMsg();
	try {
		CargoGoodsDto dto=new CargoGoodsDto();
		dto.setGoodsId(goodsId);
		Map<String, Object> goodsInfo=cargoGoodsDao.getGoodsList(dto, 0, 0).get(0);
		//总量变化量
		double goodsChange=-Common.fixDouble(Double.valueOf(lossCount),3);
		//可提量
		double check=Common.fixDouble(Double.valueOf(goodsInfo.get("goodsCurrent").toString())-(Double.valueOf(goodsInfo.get("goodsTotal").toString())-Math.min(Double.valueOf(goodsInfo.get("goodsInPass").toString()), Double.valueOf(goodsInfo.get("goodsOutPass").toString()))),3);
		
		//如果填入一个负数，表示要撤销扣损
		if(goodsChange>0){
			//已经扣损过的量
			Map<String, Object> lossedInfo=cargoGoodsDao.getGoodsLossedCount(goodsId);
			
			double lossed=Double.parseDouble(lossedInfo.get("lossed").toString());
			//如果撤销扣损的量>已经扣损过的量，则不通过
			if(goodsChange>lossed){
				oaMsg.setCode(Constant.SYS_CODE_DISABLED);
				oaMsg.setMsg("超过可撤销的扣损数，请重新填写!");
				return oaMsg;
			}
			
		}else 
		if(goodsChange<0&&check+goodsChange<0){
			oaMsg.setCode(Constant.SYS_CODE_DISABLED);
			oaMsg.setMsg("扣损数量不能大于可提量(如有封量，请先放行！)");
			return oaMsg;
		}
		Goods goods=new Goods();
		goods.setId(goodsId);
		goods.setGoodsCurrent(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsCurrent").toString())+goodsChange,3)+"");
		
//		if(goodsChange>=0){
//			goods.setGoodsInPass(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsInPass").toString())+goodsChange,3)+"");
//			goods.setGoodsOutPass(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsOutPass").toString())+goodsChange,3)+"");
//			}else{
//				goods.setGoodsInPass(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsInPass").toString()),3)+"");
//				goods.setGoodsOutPass(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsOutPass").toString()),3)+"");
//					
//			}
		

	
		cargoGoodsDao.updateGoods(goods);
		
		//货体日志--商检封放（扣损）
		GoodsLog goodsLogOld=new GoodsLog();
		goodsLogOld.setGoodsId(goodsId);
		goodsLogOld.setType(10);
		goodsLogOld.setCreateTime(Long.parseLong(new Date().getTime()/1000+""));
		goodsLogOld.setGoodsChange(goodsChange);
		goodsLogOld.setGoodsSave(Common.fixDouble(Double.valueOf(goodsInfo.get("goodsTotal").toString())-Math.min(Double.valueOf(goodsInfo.get("goodsInPass").toString()),Double.valueOf(goodsInfo.get("goodsOutPass").toString())),3));
		goodsLogOld.setSurplus(Common.fixDouble(Double.parseDouble(goods.getGoodsCurrent())-goodsLogOld.getGoodsSave(),3));
		goodsLogOld.setDeliverNo("1");
		goodsLogDao.add(goodsLogOld);
		
		
		Integer cargoId=Integer.valueOf(goodsInfo.get("cargoId").toString());
		//更新货批
		cargoGoodsDao.updateCargoAll(cargoId);
		
		ladingDao.repairLading(goodsInfo.get("zladingId").toString());
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		return oaMsg;
		
	} catch (RuntimeException e) {
		oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		oaMsg.setMsg("更新失败" + e);
		LOG.error("更新失败", e);
		throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败", e);
	}}

	@Override
	public OaMsg getCargoByClientId(int clientId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		List<Map<String, Object>> cargoListData =new ArrayList<Map<String,Object>>();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			LOG.debug("获取货批表信息");
		 cargoListData = cargoGoodsDao.getCargoByClientId(clientId);
						
			oaMsg.getData().addAll(cargoListData);
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getClientByCargoId(int cargoId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		List<Map<String, Object>> clientListData =new ArrayList<Map<String,Object>>();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			LOG.debug("获取货主信息");
			clientListData = cargoGoodsDao.getClientByCargoId(cargoId);
						
			oaMsg.getData().addAll(clientListData);
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg clientGoodstotal(CargoGoodsDto cargoDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		List<Map<String, Object>> cargoGoodsData = new ArrayList<Map<String, Object>>();
		int count = 0;
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			LOG.debug("获取货体表信息");
			cargoGoodsData = cargoGoodsDao.getGoodsList(cargoDto,
					0, 0);
			double goodsTotal=0;
			
			if(cargoGoodsData.size()>0){
				for(int i=0;i<cargoGoodsData.size();i++){
					cargoGoodsData.get(i).put("shipCount", Common.empty(shipArrivalDao.getArrivalPlanAmount(Integer.parseInt(cargoGoodsData.get(i).get("id").toString())))?0:shipArrivalDao.getArrivalPlanAmount(Integer.parseInt(cargoGoodsData.get(i).get("id").toString())).get("amount"));
				}
			}
			
			for(int i=0;i<cargoGoodsData.size();i++){
				goodsTotal+=Common.fixDouble(Double.parseDouble(!Common.empty(cargoGoodsData.get(i).get("goodsCurrent"))?cargoGoodsData.get(i).get("goodsCurrent").toString():"0")-Double.parseDouble(!Common.empty(cargoGoodsData.get(i).get("shipCount"))?cargoGoodsData.get(i).get("shipCount").toString():"0")-
						Double.parseDouble(!Common.empty(cargoGoodsData.get(i).get("goodsWait"))?cargoGoodsData.get(i).get("goodsWait").toString():"0")-
						(Double.parseDouble(!Common.empty(cargoGoodsData.get(i).get("goodsTotal"))?cargoGoodsData.get(i).get("goodsTotal").toString():"0")-Double.parseDouble(!Common.empty(cargoGoodsData.get(i).get("goodsOutPass"))?cargoGoodsData.get(i).get("goodsOutPass").toString():"0")),3);
			}

			Map<String, String> map = new HashMap<String, String>();
			map.put("goodsTotal", goodsTotal + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	public Object getInspectTotal(CargoGoodsDto cargoDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		int count = 0;
		List<Map<String, Object>> cargoListData =new ArrayList<Map<String,Object>>();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			LOG.debug("获取货批表信息");
		 cargoListData = cargoGoodsDao.getCargoInspectTotal(cargoDto);
		    oaMsg.getData().addAll(cargoListData);
			Map<String, String> map = new HashMap<String, String>();
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	public Object deleteCargo(int cargoId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始删除货批");
			CargoGoodsDto dto=new CargoGoodsDto();
			dto.setCargoId(cargoId);
			Map<String, Object> cargoListData =new HashMap<String,Object>();
			 cargoListData = cargoGoodsDao.getCargoList(dto,0,0).get(0);
			if(Double.parseDouble(cargoListData.get("goodsOutPass").toString())>0){

				oaMsg.setCode(Constant.SYS_CODE_DISABLED);
				oaMsg.setMsg("状态已变不可删除");
				return oaMsg;
			}
			
			cargoGoodsDao.deleteCargo(cargoId);
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
