package com.skycloud.oa.statistics.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.dao.LadingDao;
import com.skycloud.oa.outbound.dto.LadingDto;
import com.skycloud.oa.outbound.model.EveryDayStatics;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.report.utils.FormatUtils;
import com.skycloud.oa.statistics.dao.GoodsStatisticsDao;
import com.skycloud.oa.statistics.dto.StatisticsDto;
import com.skycloud.oa.statistics.service.StatisticsService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.ExcelUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelUtil.CallBack;

@Service
public class StatisticsServiceImpl implements StatisticsService
{
	private static Logger LOG = Logger.getLogger(StatisticsService.class);

	@Autowired
	CargoGoodsDao cargoGoodsDao;
	@Autowired
	GoodsLogDao goodsLogDao;
	@Autowired
	LadingDao ladingDao;

	@Autowired
	GoodsStatisticsDao goodsStatisticsDao;

	@Override
	public OaMsg getCargo(StatisticsDto sDto, PageView pageView)throws OAException
	{
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始-------list--------------");
			List<Map<String, Object>> cargoList = goodsStatisticsDao.get(sDto,
					pageView.getStartRecord(), pageView.getMaxresult());

			for (Map<String, Object> map : cargoList) {
				double goodsSend = 0, goodsOld = 0, goodsTotal = 0,goodsSave = 0;
				if (!Common.empty(map.get("goodsTotal"))) {// 总量
					goodsTotal = Double.parseDouble(map.get("goodsTotal")
							.toString());
					map.put("goodsTotal", Common.fixDouble(goodsTotal, 3));
				} else {
					map.put("goodsTotal", "0");
				}
				if (!Common.empty(map.get("goodsSave"))) {// 今日前发量
					goodsSave = Double.parseDouble(map.get("goodsSave")
							.toString());
					map.put("goodsSave", Common.fixDouble(goodsSave, 3));
				} else {
					map.put("goodsSave", "0");
				}
				if (!Common.empty(map.get("goodsOld"))) {// 今日前发量
					goodsOld = Double.parseDouble(map.get("goodsOld")
							.toString());
					map.put("goodsOld", Common.fixDouble(goodsOld, 3));
				} else {
					map.put("goodsOld", "0");
				}
				if (!Common.empty(map.get("goodsSend"))) {// 总发量
					goodsSend = Double.parseDouble(map.get("goodsSend")
							.toString());
					map.put("goodsSend", Common.fixDouble(goodsSend, 3));
					map.put("goodsToday",
							Common.fixDouble(goodsSend - goodsOld, 3));
					map.put("goodsSurplus",
							Common.fixDouble(goodsTotal - goodsSend, 3));
				} else {
					map.put("goodsSend", "0");
					map.put("goodsToday", "0");
					map.put("goodsSurplus", Common.fixDouble(goodsTotal, 3));
				}
			}
			oaMsg.getData().addAll(cargoList);
			int count = goodsStatisticsDao.count(sDto);
			oaMsg.getMap().put(Constant.TOTALRECORD, count + "");
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getGoods(StatisticsDto sDto, PageView pageView,boolean isAll)throws OAException
	{
		OaMsg oaMsg = new OaMsg();
		try {
			//true显示全部，false显示未清盘
			
			boolean isShowAll=false;
			if(!Common.empty(sDto.getIsFinish())){
				
				isShowAll=sDto.getIsFinish()==1?true:false;
			}
			
			List<Map<String, Object>> goodsIds = goodsLogDao.getGoods(sDto,
					pageView.getStartRecord(), pageView.getMaxresult(),isShowAll);
			if (goodsIds.size() > 0) {
				for (int i = 0; i < goodsIds.size(); i++) {
					
					//获得该货体信息
					CargoGoodsDto cgd = new CargoGoodsDto();
					cgd.setGoodsId(Integer.parseInt(goodsIds.get(i)
							.get("goodsId").toString()));

					List<Map<String, Object>> goodsInfoList = cargoGoodsDao
							.getGoodsList(cgd, 0, 0);
					if (goodsInfoList.size() > 0) {
						
						// 货权调出
						double goodsOut = 0;
						// 提货权调出
						double goodsTOut = 0;
						
						
						// 这个区间内的所有日志，用于计算各种总量
						List<Map<String, Object>> goodsLogList = goodsLogDao
								.getGoodsLog(sDto.getShowVirTime(),goodsIds.get(i).get("goodsId")
										.toString(), 0l,
										sDto.getEndTime() + 86400,3);
						if (goodsLogList.size() > 0) {
							for (int j = 0; j < goodsLogList.size(); j++) {
								int ladingType = Common.empty(goodsLogList.get(j)
										.get("ladingType")) ? 0 : Integer
										.parseInt(goodsLogList.get(j)
												.get("ladingType").toString());
									// 货权调出
									if (ladingType == 1) {
										goodsOut += -Double.valueOf(goodsLogList
												.get(j).get("goodsChange")
												.toString());
									}
									// 提货权调出
									if (ladingType == 2) {
										goodsTOut += -Double.valueOf(goodsLogList
												.get(j).get("goodsChange")
												.toString());
									}
								
							}
						}
						// 货权调出
						goodsIds.get(i).put("goodsOut",
								Common.fixDouble(goodsOut, 3));
						// 提货权调出
						goodsIds.get(i).put("goodsTOut",
								Common.fixDouble(goodsTOut, 3));
						
						
						// 货权调入
						double goodsIn = 0;
						// 提货权调入
						double goodsTIn = 0;
						
						// 这个区间内的所有日志，用于计算各种总量
						List<Map<String, Object>> goodsLogList1 = goodsLogDao
								.getGoodsLog(sDto.getShowVirTime(),goodsIds.get(i).get("goodsId")
										.toString(), 0l,
										sDto.getEndTime() + 86400,2);
						if (goodsLogList1.size() > 0) {
							for (int j = 0; j < goodsLogList1.size(); j++) {
								int ladingType = Common.empty(goodsLogList1.get(j)
										.get("ladingType")) ? 0 : Integer
										.parseInt(goodsLogList1.get(j)
												.get("ladingType").toString());
									// 货权调入
									if (ladingType == 1) {
										goodsIn += Double.valueOf(goodsLogList1
												.get(j).get("goodsChange")
												.toString());
									}
									// 提货权调入
									if (ladingType == 2) {
										goodsTIn += Double.valueOf(goodsLogList1
												.get(j).get("goodsChange")
												.toString());
									}
								
							}
						}
						
						
//						if(Integer.parseInt(goodsInfoList.get(0).get("ladingType").toString())==1){
//							goodsIn=Common.fixDouble(Double.parseDouble(goodsInfoList.get(0).get("goodsIn").toString()), 3);
//						}
//						if(Integer.parseInt(goodsInfoList.get(0).get("ladingType").toString())==2){
//							goodsTIn=Common.fixDouble(Double.parseDouble(goodsInfoList.get(0).get("goodsIn").toString()), 3);
//							
//						}
						// 货权调入
						goodsIds.get(i).put("goodsIn",
								Common.fixDouble(goodsIn, 3));
						
						// 提货权调入
						goodsIds.get(i).put("goodsTIn",
								 Common.fixDouble(goodsTIn, 3));
						
						Map<String, Object> goodsInfo = goodsInfoList
								.get(0);
						
						// 本段实发数
						Map<String, Object> thisActnum = goodsLogDao
								.getActualNum(sDto.getShowVirTime(),
										Integer.parseInt(goodsIds.get(i)
												.get("goodsId").toString()),
										sDto.getStartTime(),
										sDto.getEndTime() + 86400).get(0);
						double thisCount = Double.parseDouble(thisActnum.get(
								"goodsSend").toString());
						goodsIds.get(i).put("goodsToday",
								Common.fixDouble(thisCount, 3));
						// 之前发货
						Map<String, Object> oldActnum = goodsLogDao
								.getActualNum(sDto.getShowVirTime(),
										Integer.parseInt(goodsIds.get(i)
												.get("goodsId").toString()),
										0, sDto.getStartTime()).get(0);
						double oldCount = Double.parseDouble(oldActnum.get(
								"goodsSend").toString());
						goodsIds.get(i).put("goodsOld",
								Common.fixDouble(oldCount, 3));
						
//						Map<String, Object> lastGoodsLog= goodsLogDao.getLastGoodsLog(goodsIds.get(i).get("goodsId")
//								.toString(), sDto.getEndTime()+86400).get(0);
						
						// 货体当时存量=总量-之前发货-本段发货-调出
						
						
						Map<String, Object> goodsSurplus = goodsLogDao
								.getSurplus(sDto.getShowVirTime(),
										Integer.parseInt(goodsIds.get(i)
												.get("goodsId").toString()),
										0l, sDto.getEndTime()+86400).get(0);
						double surplus = Double.parseDouble(goodsSurplus.get(
								"goodsSurplus").toString());
						goodsIds.get(i).put("goodsSurplus",
								Common.fixDouble(surplus, 3));
						
						// 当时封量
						double goodsoPass=Common.empty(goodsSurplus.get("goodsSave"))?0:Double.parseDouble(goodsSurplus.get("goodsSave").toString());
						goodsIds.get(i).put("goodsSave",
								Common.fixDouble(goodsoPass,3));
						
						//扣损量
						
						double loss = Double.parseDouble(goodsSurplus.get(
								"goodsLoss").toString());
						goodsIds.get(i).put("goodsLoss",
								Common.fixDouble(loss, 3));
						
						
//						goodsIds.get(i).put("goodsSurplus",
//								Common.fixDouble(Double.parseDouble(goodsInfo.get("goodsTotal").toString())-oldCount-thisCount-goodsOut-goodsTOut, 3));
						
						
						//现存量
						goodsIds.get(i).put("goodsCurrent", Common.fixDouble(Double.parseDouble(goodsInfo.get("goodsCurrent").toString()),3));
						// 实发总数
						Map<String, Object> actnum = goodsLogDao
								.getActualNum(sDto.getShowVirTime(),
										Integer.parseInt(goodsIds.get(i)
												.get("goodsId").toString()),
//										0, (new Date().getTime()/1000))
												0, sDto.getEndTime()+86400)		
								.get(0);
						double totalCount = Double.parseDouble(actnum.get(
								"goodsSend").toString());
						goodsIds.get(i).put("totalCount",
								Common.fixDouble(totalCount, 3));
						
//						// 当时封量
//						double goodsoPass=Common.empty(goodsInfo.get("goodsOutPass"))?0:Double.parseDouble(goodsInfo.get("goodsOutPass").toString());
//						goodsIds.get(i).put("goodsSave",
//								Common.fixDouble(Double.parseDouble(goodsInfo.get("goodsTotal").toString())-goodsoPass, 3));
//						
//						
						
						double goodsInbound=0;
						// 这个区间内的所有日志，用于计算各种总量
						List<Map<String, Object>> goodsLogList2 = goodsLogDao
								.getGoodsLog(sDto.getShowVirTime(),goodsIds.get(i).get("goodsId")
										.toString(), 0l,
										sDto.getEndTime() + 86400,0);
						if (goodsLogList2.size() > 0) {
							for (int j = 0; j < goodsLogList2.size(); j++) {
								int ladingType = Common.empty(goodsLogList2.get(j)
										.get("type")) ? 0 : Integer
										.parseInt(goodsLogList2.get(j)
												.get("type").toString());
								
								
								String deliverNo= Common.empty(goodsLogList2.get(j)
										.get("deliverNo")) ? "" : goodsLogList2.get(j)
												.get("deliverNo").toString();
									if (ladingType == 1||(ladingType==10&&!"1".equals(deliverNo))||ladingType==7) {
										goodsInbound += Double.valueOf(goodsLogList2
												.get(j).get("goodsChange")
												.toString());
									}
								
							}
						}
						
						
						
						// 入库
						double gIn=Common.empty(goodsInfo.get("goodsIn"))?0:Double.parseDouble(goodsInfo.get("goodsIn").toString());
						goodsIds.get(i).put("goodsInbound",
								Common.fixDouble(goodsInbound,3));
						goodsIds.get(i).put("cargoCode",
								goodsInfo.get("cargoCode"));
						goodsIds.get(i).put("shipName",
								goodsInfo.get("shipName"));
						goodsIds.get(i).put("tankCode",
								goodsInfo.get("tankCode"));
						goodsIds.get(i).put("goodsCode",
								goodsInfo.get("code"));
						goodsIds.get(i).put("clientName",
								goodsInfo.get("clientName"));
						goodsIds.get(i).put("sourceCode",
								goodsInfo.get("sourceCode"));
						goodsIds.get(i).put("rootCode",
								goodsInfo.get("rootCode"));
						goodsIds.get(i).put("arrivalStartTime",
								goodsInfo.get("arrivalStartTime"));
						goodsIds.get(i).put("createTime",
								goodsInfo.get("createTime"));
						goodsIds.get(i).put("productName",
								goodsInfo.get("productName"));
						goodsIds.get(i).put("goodsTotal",
								goodsInfo.get("goodsTotal"));
						goodsIds.get(i).put("rootGoodsId",
								goodsInfo.get("rootGoodsId"));
						goodsIds.get(i).put("sourceGoodsId",
								goodsInfo.get("sourceGoodsId"));
						goodsIds.get(i).put("customsPassCode",
								goodsInfo.get("customsPassCode"));
						goodsIds.get(i).put("rLadingCode",
								goodsInfo.get("rLadingCode"));
						goodsIds.get(i).put("ladingCode",
								goodsInfo.get("ladingCode"));
						goodsIds.get(i).put("productId",
								goodsInfo.get("productId"));
						goodsIds.get(i).put("sLadingCode", goodsInfo.get("sLadingCode"));
						goodsIds.get(i).put("sClientName", goodsInfo.get("sClientName"));
						goodsIds.get(i).put("goodsWait", goodsInfo.get("goodsWait"));
						goodsIds.get(i).put("ladingType", goodsInfo.get("ladingType"));
						goodsIds.get(i).put("ladingClientName", goodsInfo.get("ladingClientName"));
						String ladingTime="";
						if(!Common.empty(goodsInfo.get("ladingStartTime"))){
							ladingTime=goodsInfo.get("ladingStartTime").toString();
						}
						if(!Common.empty(goodsInfo.get("ladingEndTime"))){
							ladingTime=goodsInfo.get("ladingEndTime").toString();
						}
						
						if(!Common.empty(goodsInfo.get("isLong"))&&Integer.parseInt(goodsInfo.get("isLong").toString())==1&&Integer.parseInt(goodsInfo.get("ladingType").toString())==2){
							ladingTime="长期";
						}
						goodsIds.get(i).put("ladingTime", ladingTime);
						
					}
					

				}
			}
			oaMsg.getData().addAll(goodsIds);
			int count = cargoGoodsDao.getGoodsCount(sDto,isShowAll);
			oaMsg.getMap().put(Constant.TOTALRECORD, count + "");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (OAException e) {
			LOG.error("查询失败", e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}

		return oaMsg;
	}

	@Override
	public OaMsg sqlitPassGoods(int oldGoodsId, Goods newGoods)
			throws OAException
	{
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
			if (ladingInfo.size() > 0) {
				ladingId = Integer.parseInt(ladingInfo.get(0).get("id")
						.toString());
				ladingType = Integer.parseInt(ladingInfo.get(0).get("type")
						.toString());
			}

			// 原货体
			Goods oldgoods = new Goods();

			// 货体总量
			oldgoods.setGoodsTotal(Common.fixDouble(
					Double.parseDouble(oldGoods.get("goodsTotal").toString())
							- Double.parseDouble(newGoods.getGoodsTotal()), 3)
					+ "");
			oldgoods.setCargoId(Integer.parseInt(oldGoods.get("cargoId")
					.toString()));
			oldgoods.setGoodsCurrent(oldgoods.getGoodsTotal());
			oldgoods.setGoodsTank(oldgoods.getGoodsTotal());
			oldgoods.setClientId(Integer.valueOf(oldGoods.get("clientId")
					.toString()));
			oldgoods.setContractId(Integer.valueOf(oldGoods.get("contractId")
					.toString()));
			if (!Common.empty(oldGoods.get("lossRate"))) {
				oldgoods.setLossRate(oldGoods.get("lossRate").toString());
			}
			oldgoods.setStatus(Integer.valueOf(oldGoods.get("status")
					.toString()));
			oldgoods.setProductId(Integer.valueOf(oldGoods.get("productId")
					.toString()));
			oldgoods.setGoodsInPass(oldgoods.getGoodsTotal());
			// 如果原货体出库放行量>=新货体量 就-新货体量 否则变0
			if (Double.parseDouble(oldGoods.get("goodsOutPass").toString()) >= Double
					.parseDouble(newGoods.getGoodsTotal())) {
				oldgoods.setGoodsOutPass(Common.fixDouble(
						Double.parseDouble(oldGoods.get("goodsOutPass")
								.toString())
								- Double.parseDouble(newGoods.getGoodsTotal()),
						3)
						+ "");
			} else {
				oldgoods.setGoodsOutPass("0");
			}
			if (ladingId != 0)
				oldgoods.setGoodsIn(oldgoods.getGoodsTotal());
			oldgoods.setGoodsTank(Double.parseDouble(oldGoods.get("goodsTank")
					.toString())
					- Double.parseDouble(newGoods.getGoodsTotal())
					+ "");
			if (!Common.empty(oldGoods.get("tankId"))) {
				oldgoods.setTankId(Integer.parseInt(oldGoods.get("tankId")
						.toString()));
			}
			if (!Common.empty(oldGoods.get("isPredict"))) {
				oldgoods.setIsPredict(Integer.parseInt(oldGoods
						.get("isPredict").toString()));
			}
			if (!Common.empty(oldGoods.get("goodsGroupId"))) {
				oldgoods.setGoodsGroupId(Integer.parseInt(oldGoods.get(
						"goodsGroupId").toString()));
			}
			if (!Common.empty(oldGoods.get("sourceGoodsId"))) {
				oldgoods.setSourceGoodsId(Integer.parseInt(oldGoods.get(
						"sourceGoodsId").toString()));
			}
//			if (!Common.empty(oldGoods.get("rootGoodsId"))) {
//				oldgoods.setRootGoodsId(Integer.parseInt(oldGoods.get(
//						"rootGoodsId").toString()));
//			}
			
			//老货体的母货体为空，来源货体不为空，则母货体为老货体
			if((Common.empty(oldGoods.get("rootGoodsId"))||Integer.parseInt(oldGoods.get("rootGoodsId").toString())==0)&&!Common.empty(oldGoods.get("sourceGoodsId"))&&Integer.parseInt(oldGoods.get("sourceGoodsId").toString())!=0){
				oldgoods.setRootGoodsId(Integer.valueOf(oldGoods.get("id")
						.toString()));
				//如果老货体母货体不为空，则新货体的母货体继承
			}else if(!Common.empty(oldGoods.get("rootGoodsId"))){
				oldgoods.setRootGoodsId(Integer.valueOf(oldGoods.get(
						"rootGoodsId").toString()));
			}
			//如果老货体的母货体和来源货体都是空，新货体的母货体也为空
			

			// 新货体
			newGoods.setCargoId(Integer.parseInt(oldGoods.get("cargoId")
					.toString()));
			newGoods.setGoodsCurrent(newGoods.getGoodsTotal());
			newGoods.setGoodsTank(newGoods.getGoodsTotal());
			if (ladingId != 0)
				newGoods.setGoodsIn(newGoods.getGoodsTotal());
			newGoods.setGoodsInPass(newGoods.getGoodsTotal());

			// 如果原货体出库放行量>=新货体量 新货体放行量=新货体量 否则变新货体放行量=原货体出库放行量
			if (Double.parseDouble(oldGoods.get("goodsOutPass").toString()) >= Double
					.parseDouble(newGoods.getGoodsTotal())) {
				newGoods.setGoodsOutPass(Double.parseDouble(newGoods
						.getGoodsTotal()) + "");
			} else {
				newGoods.setGoodsOutPass(Double.parseDouble(oldGoods.get(
						"goodsOutPass").toString())
						+ "");
			}

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
			if (!Common.empty(oldGoods.get("tankId"))) {
				newGoods.setTankId(Integer.parseInt(oldGoods.get("tankId")
						.toString()));
			}
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
			
			
			//老货体的母货体为空，来源货体不为空，则母货体为老货体
			if((Common.empty(oldGoods.get("rootGoodsId"))||Integer.parseInt(oldGoods.get("rootGoodsId").toString())==0)&&!Common.empty(oldGoods.get("sourceGoodsId"))){
				newGoods.setRootGoodsId(Integer.valueOf(oldGoods.get("id")
						.toString()));
				//如果老货体母货体不为空，则新货体的母货体继承
			}else if(!Common.empty(oldGoods.get("rootGoodsId"))){
				newGoods.setRootGoodsId(Integer.valueOf(oldGoods.get(
						"rootGoodsId").toString()));
			}
			//如果老货体的母货体和来源货体都是空，新货体的母货体也为空
			
			
			// 添加货体
			int newgoodsId = (Integer) cargoGoodsDao.addGoods(newGoods);
			int oldgoodsId = (Integer) cargoGoodsDao.addGoods(oldgoods);

			// 添加日志表

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
			goodsLog.setLadingId(ladingId);
			goodsLog.setLadingType(ladingType);
			goodsLogDao.add(goodsLog);

			// 新货体调入
			GoodsLog goodsLog2 = new GoodsLog();
			goodsLog2.setGoodsId(oldgoodsId);
			goodsLog2.setType(2);
			goodsLog2.setCreateTime(Long.parseLong(new Date().getTime() / 1000
					+ ""));
			goodsLog2.setGoodsChange(Common.fixDouble(
					Double.parseDouble(oldgoods.getGoodsIn()), 3));
			// goodsLog.setSurplus(Double.parseDouble(goodsold.getGoodsCurrent()));

			goodsLog2.setGoodsSave(Common.fixDouble(
					(Double.valueOf(oldgoods.getGoodsTotal()) - Math.min(
							Double.valueOf(oldgoods.getGoodsInPass()),
							Double.valueOf(oldgoods.getGoodsOutPass()))), 3));
			goodsLog2.setSurplus(Common.fixDouble(
					Double.valueOf(oldgoods.getGoodsCurrent())
							- goodsLog2.getGoodsSave(), 3));

			goodsLog2.setLadingId(ladingId);
			goodsLog2.setLadingType(ladingType);
			goodsLogDao.add(goodsLog2);

			// 删除原货体
			cargoGoodsDao.deleteGoods(oldGoodsId + "");
			// 删除原货体所有日志
			goodsLogDao.deleteGoodsLog(oldGoodsId + "");

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
	public HSSFWorkbook exportGoodsExcel(OaMsg msg, Integer type) throws OAException {
		// TODO Auto-generated method stub
		
		List<Object> data=msg.getData();
		
		HSSFWorkbook workbook = new HSSFWorkbook();  
		try {
	
			 // 声明一个工作薄  
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.createSheet("货体汇总");  
	        // 设置表格默认列宽度为15个字节  
	        sheet.setDefaultColumnWidth((short) 20);  
	        // 生成一个样式  
//	        HSSFCellStyle style = workbook.createCellStyle();  
//	        // 设置这些样式  
//	        //style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);  
//	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
//	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
//	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
//	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
//	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);  
//	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
//	        // 生成一个字体  
//	        HSSFFont font = workbook.createFont();  
//	        font.setColor(HSSFColor.VIOLET.index);  
//	        font.setFontHeightInPoints((short) 12);  
//	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
//	        // 把字体应用到当前的样式  
//	        style.setFont(font);  
//	        // 生成并设置另一个样式  
//	        HSSFCellStyle style2 = workbook.createCellStyle();  
//	        //style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);  
//	        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
//	        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
//	        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
//	        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);  
//	        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);  
//	        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
//	        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
//	        // 生成另一个字体  
//	        HSSFFont font2 = workbook.createFont();  
//	        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);  
//	        // 把字体应用到当前的样式  
//	        style2.setFont(font2);  
	        //精简模式
	        if(type==1){
	        	for(int i=1;i<=13;i++){
	            	
	            	sheet.setColumnWidth(i, 4000); 
	            }
	        	sheet.setColumnWidth(3, 6000); 
	        	sheet.setColumnWidth(5, 6000); 
	        	 // 产生表格标题行  
		        HSSFRow row = sheet.createRow(0);  
	            HSSFCell cell = row.createCell(0);  
	            HSSFRichTextString text = new HSSFRichTextString("货批号");  
	            cell.setCellValue(text);  
	            
	            HSSFCell bianhaocell = row.createCell(1);  
	            HSSFRichTextString bianhao = new HSSFRichTextString("货品");  
	            bianhaocell.setCellValue(bianhao);  
	            
	            
	            HSSFCell huoticell = row.createCell(2);  
	            HSSFRichTextString huotitext = new HSSFRichTextString("罐号");  
	            huoticell.setCellValue(huotitext);  
	            
	            HSSFCell shangjicell = row.createCell(3);  
	            HSSFRichTextString shangjitext = new HSSFRichTextString("货主");  
	            shangjicell.setCellValue(shangjitext);  
	            
	            HSSFCell yuanshicell = row.createCell(4);  
	            HSSFRichTextString yuanshitext = new HSSFRichTextString("提单类型");  
	            yuanshicell.setCellValue(yuanshitext);  
	            
	            HSSFCell tihuocell = row.createCell(5);  
	            HSSFRichTextString tihuotext = new HSSFRichTextString("上家货主");  
	            tihuocell.setCellValue(tihuotext);  
	            
	            HSSFCell yuanhaocell = row.createCell(6);  
	            HSSFRichTextString yuanhaotext = new HSSFRichTextString("上家提单");  
	            yuanhaocell.setCellValue(yuanhaotext);  
	            
	            HSSFCell diaohaocell = row.createCell(7);  
	            HSSFRichTextString diaohaotext = new HSSFRichTextString("原号");  
	            diaohaocell.setCellValue(diaohaotext);  
	            
	            HSSFCell shuliangcell = row.createCell(8);  
	            HSSFRichTextString shuliangtext = new HSSFRichTextString("调号");  
	            shuliangcell.setCellValue(shuliangtext);
	            
	            HSSFCell diaoru = row.createCell(9);  
	            HSSFRichTextString diaorutext = new HSSFRichTextString("入库（吨）");  
	            diaoru.setCellValue(diaorutext);
	            
	            HSSFCell kousun1 = row.createCell(10);  
	            HSSFRichTextString kousun = new HSSFRichTextString("扣损（吨）");  
	            kousun1.setCellValue(kousun);
	            
	            
	            HSSFCell tdiaoru = row.createCell(11);  
	            HSSFRichTextString tdiaorutext = new HSSFRichTextString("当前封量（吨）");  
	            tdiaoru.setCellValue(tdiaorutext);
	            
	            HSSFCell diaochu = row.createCell(12);  
	            HSSFRichTextString diaochutext = new HSSFRichTextString("现结存量（吨）");  
	            diaochu.setCellValue(diaochutext);
	            
	            HSSFCell tdiaochu = row.createCell(13);  
	            HSSFRichTextString tdiaochutext = new HSSFRichTextString("待提量（吨）");  
	            tdiaochu.setCellValue(tdiaochutext);
				
	            HSSFCell save = row.createCell(14);  
	            HSSFRichTextString savetext = new HSSFRichTextString("参量（吨）");  
	            save.setCellValue(savetext);
	            
	            
	            
	            
	            double goodsInbound=0;
	            double goodsSave=0;
	            double goodsCurrent=0;
	            double goodsWait=0;
	            double goodsCL=0;
	            double goodsLoss=0;
	            for(int i = 0 ; i<data.size();i++){
	            	Map<String,Object> map=(Map<String, Object>) data.get(i);
	            	HSSFRow datarow = sheet.createRow(i+1);  
	                HSSFCell cell0 = datarow.createCell(0);  
	                HSSFRichTextString t0 = new HSSFRichTextString((String)map.get("cargoCode"));  
	                cell0.setCellValue(t0);
	                
	                HSSFCell c1 = datarow.createCell(1);  
	                HSSFRichTextString t1 = new HSSFRichTextString((String)map.get("productName"));  
	                c1.setCellValue(t1);  
	                
	                
	                HSSFCell c2= datarow.createCell(2);  
	                HSSFRichTextString t2 = new HSSFRichTextString((String)map.get("tankCode"));  
	                c2.setCellValue(t2);  
	                
	                
	                String clientName="";
	                if(!Common.empty(map.get("ladingClientName"))){
	                	clientName=map.get("ladingClientName").toString();
	                }else{
	                	clientName=map.get("clientName").toString();
	                }
	                HSSFCell c3 = datarow.createCell(3);  
	                HSSFRichTextString t3 = new HSSFRichTextString(clientName);  
	                c3.setCellValue(t3);  
	                
	                String ladingType="";
					if(!Common.empty(map.get("ladingType"))){
						if(Integer.parseInt(map.get("ladingType").toString())==1){
							ladingType="转卖";
						}else if(Integer.parseInt(map.get("ladingType").toString())==2){
							ladingType="发货";
						}
					}
					else{
						ladingType="";
					}
	                
	                HSSFCell c4 = datarow.createCell(4);  
	                HSSFRichTextString t4 = new HSSFRichTextString(ladingType);  
	                c4.setCellValue(t4); 
	                
	                HSSFCell c5 = datarow.createCell(5);  
	                HSSFRichTextString t5 = new HSSFRichTextString((String)map.get("sClientName"));  
	                c5.setCellValue(t5); 
	                
	                HSSFCell c6 = datarow.createCell(6);  
	                HSSFRichTextString t6 = new HSSFRichTextString((String)map.get("sLadingCode"));  
	                c6.setCellValue(t6); 
	                
	                
	                String rootCode="";
					if(!Common.empty(map.get("rootCode"))){
						rootCode=((String)map.get("rLadingCode"));
					}else if(Common.empty(map.get("rootCode"))&&!Common.empty(map.get("sourceCode"))){
						rootCode=((String)map.get("ladingCode"));
					}
					else{
						rootCode=((String)map.get("cargoCode"));
					}
	                HSSFCell c7 = datarow.createCell(7);  
	                HSSFRichTextString t7 = new HSSFRichTextString(rootCode);  
	                c7.setCellValue(t7);  
	                
	                String sourceCode="";
	                if(!Common.empty(map.get("sourceCode"))){
	                	sourceCode=((String)map.get("ladingCode"));
					}else{
						sourceCode= "";
					}
	                
	                HSSFCell c8 = datarow.createCell(8);  
	                HSSFRichTextString t8 = new HSSFRichTextString(sourceCode);  
	                c8.setCellValue(t8);  
	                
	                
	                HSSFCell c9 = datarow.createCell(9);
	                Double t9 = Common.fixDouble( Double.valueOf(map.get("goodsInbound")+""),3);  
	                c9.setCellValue(t9);  
	                goodsInbound+=Double.parseDouble(t9.toString());
	                
	                HSSFCell c20 = datarow.createCell(10);  
	                Double t20 = Common.fixDouble(Double.valueOf(map.get("goodsLoss")+""),3);  
	                c20.setCellValue(t20);
	                goodsLoss+=Double.parseDouble(t20.toString());
	                
	                HSSFCell c10 = datarow.createCell(11);  
	                Double t10 = Common.fixDouble( Double.valueOf(map.get("goodsSave")+""),3);  
	                c10.setCellValue(t10);
	                goodsSave+=Double.parseDouble(t10.toString());
	                
	                HSSFCell c11 = datarow.createCell(12);  
	              Double t11 = Common.fixDouble( Double.valueOf(map.get("goodsCurrent")+""),3);  
	              c11.setCellValue(t11);
	              goodsCurrent+=Double.parseDouble(t11.toString());
	              
	              HSSFCell c12 = datarow.createCell(13);  
	              Double t12 = Common.fixDouble( Double.valueOf(map.get("goodsWait")+""),3);  
	              c12.setCellValue(t12);
	              goodsWait+=Double.parseDouble(t12.toString());
	              
	              HSSFCell c13 = datarow.createCell(14);  
	              Double t13 = Common.fixDouble( Double.valueOf(map.get("goodsCurrent")+"")-Double.valueOf(map.get("goodsWait")+""),3);  
	              c13.setCellValue(t13);
	              goodsCL+=Double.parseDouble(t13.toString());
	                
	              
	              
	              
	            }
	            
	            
	            HSSFRow datarow = sheet.createRow(data.size()+2);
	            HSSFCell cell0 = datarow.createCell(0);  
	          HSSFRichTextString t0 = new HSSFRichTextString("总计：");  
	          cell0.setCellValue(t0);
	          
	          HSSFCell c9 = datarow.createCell(9);
	          c9.setCellValue(goodsInbound);  
	          
	          HSSFCell c14 = datarow.createCell(10);  
		        c14.setCellValue(goodsLoss);
		        
	        HSSFCell c10 = datarow.createCell(11);  
	        c10.setCellValue(goodsSave);
	        
	        HSSFCell c11 = datarow.createCell(12);  
	        c11.setCellValue(goodsCurrent);
	        
	        HSSFCell c12 = datarow.createCell(13);  
	        c12.setCellValue(goodsWait);
	        
	        HSSFCell c13 = datarow.createCell(13);  
	        c13.setCellValue(goodsCL);
	        
	        	
	        }else{
	        	
	        	for(int i=1;i<=20;i++){
	            	
	            	sheet.setColumnWidth(i, 3000); 
	            }
	        	sheet.setColumnWidth(2, 6000); 
	        	sheet.setColumnWidth(5, 6000); 
	        	sheet.setColumnWidth(6, 6000);
		        // 产生表格标题行  
		        HSSFRow row = sheet.createRow(0);  
	            HSSFCell cell = row.createCell(0);  
	            HSSFRichTextString text = new HSSFRichTextString("货批号");  
	            cell.setCellValue(text);  
	            
	            HSSFCell bianhaocell = row.createCell(1);  
	            HSSFRichTextString bianhao = new HSSFRichTextString("货品");  
	            bianhaocell.setCellValue(bianhao);  
	            
//	            HSSFCell chechuancell = row.createCell(2);  
//	            HSSFRichTextString chechuantext = new HSSFRichTextString("海关放行编号");  
//	            chechuancell.setCellValue(chechuantext);  
	            
	            HSSFCell kaipiaocell = row.createCell(2);  
	            HSSFRichTextString kaipiaotext = new HSSFRichTextString("进货船");  
	            kaipiaocell.setCellValue(kaipiaotext);  
	            
	            HSSFCell daogangriqi = row.createCell(3);  
	            HSSFRichTextString daogangriqitext = new HSSFRichTextString("到港日期");  
	            daogangriqi.setCellValue(daogangriqitext);  
	            
	            
	            HSSFCell huoticell = row.createCell(4);  
	            HSSFRichTextString huotitext = new HSSFRichTextString("罐号");  
	            huoticell.setCellValue(huotitext);  
	            
	            HSSFCell shangjicell = row.createCell(5);  
	            HSSFRichTextString shangjitext = new HSSFRichTextString("货体");  
	            shangjicell.setCellValue(shangjitext);  
	            
	            HSSFCell yuanshicell = row.createCell(6);  
	            HSSFRichTextString yuanshitext = new HSSFRichTextString("货主");  
	            yuanshicell.setCellValue(yuanshitext);  
	            
	            HSSFCell tihuocell = row.createCell(7);  
	            HSSFRichTextString tihuotext = new HSSFRichTextString("原号");  
	            tihuocell.setCellValue(tihuotext);  
	            
	            HSSFCell yuanhaocell = row.createCell(8);  
	            HSSFRichTextString yuanhaotext = new HSSFRichTextString("调号");  
	            yuanhaocell.setCellValue(yuanhaotext);  
	            
	            HSSFCell qizhi = row.createCell(9);  
	            HSSFRichTextString qizhitext = new HSSFRichTextString("提单起止/有效期");  
	            qizhi.setCellValue(qizhitext);  
	            
	            
	            
	            HSSFCell diaohaocell = row.createCell(10);  
	            HSSFRichTextString diaohaotext = new HSSFRichTextString("建仓日期");  
	            diaohaocell.setCellValue(diaohaotext);  
	            
	            HSSFCell shuliangcell = row.createCell(11);  
	            HSSFRichTextString shuliangtext = new HSSFRichTextString("入库（吨）");  
	            shuliangcell.setCellValue(shuliangtext);
	            
	            
	            HSSFCell kousun1 = row.createCell(12);  
	            HSSFRichTextString kousun = new HSSFRichTextString("扣损（吨）");  
	            kousun1.setCellValue(kousun);
	            
	            HSSFCell diaoru = row.createCell(13);  
	            HSSFRichTextString diaorutext = new HSSFRichTextString("货权调入（吨）");  
	            diaoru.setCellValue(diaorutext);
	            
	            HSSFCell tdiaoru = row.createCell(14);  
	            HSSFRichTextString tdiaorutext = new HSSFRichTextString("提货权调入（吨）");  
	            tdiaoru.setCellValue(tdiaorutext);
	            
	            HSSFCell diaochu = row.createCell(15);  
	            HSSFRichTextString diaochutext = new HSSFRichTextString("货权调出（吨）");  
	            diaochu.setCellValue(diaochutext);
	            
	            HSSFCell tdiaochu = row.createCell(16);  
	            HSSFRichTextString tdiaochutext = new HSSFRichTextString("提货权调出（吨）");  
	            tdiaochu.setCellValue(tdiaochutext);
				
	            HSSFCell save = row.createCell(17);  
	            HSSFRichTextString savetext = new HSSFRichTextString("封量（吨）");  
	            save.setCellValue(savetext);
	            
	            HSSFCell zfahuo = row.createCell(18);  
	            HSSFRichTextString zfahuotext = new HSSFRichTextString("之前发货（吨）");  
	            zfahuo.setCellValue(zfahuotext);
	            
	            HSSFCell bshifa = row.createCell(19);  
	            HSSFRichTextString bshifatext = new HSSFRichTextString("本段实发数（吨）");  
	            bshifa.setCellValue(bshifatext);
	            
	            HSSFCell sfshu = row.createCell(20);  
	            HSSFRichTextString sfshutext = new HSSFRichTextString("实发总数（吨）");  
	            sfshu.setCellValue(sfshutext);
	            
	            HSSFCell jiecun = row.createCell(21);  
	            HSSFRichTextString jiecuntext = new HSSFRichTextString("当时结存量（吨）");  
	            jiecun.setCellValue(jiecuntext);
	            
	            
	            
	            
	            double goodsInbound=0;
	            double goodsIn=0;
	            double goodsTIn=0;
	            double goodsOut=0;
	            double goodsTOut=0;
	            double goodsSave=0;
	            double goodsOld=0;
	            double goodsToday=0;
	            double totalCount=0;
	            double goodsSurplus=0;
	            double goodsCurrent=0;
	            double goodsLoss=0;
	            for(int i = 0 ; i<data.size();i++){
	            	Map<String,Object> map=(Map<String, Object>) data.get(i);
	            	HSSFRow datarow = sheet.createRow(i+1);  
	                HSSFCell cell0 = datarow.createCell(0);  
	                HSSFRichTextString t0 = new HSSFRichTextString((String)map.get("cargoCode"));  
	                cell0.setCellValue(t0);
	                
	                HSSFCell c1 = datarow.createCell(1);  
	                HSSFRichTextString t1 = new HSSFRichTextString((String)map.get("productName"));  
	                c1.setCellValue(t1);  
	                
//	                HSSFCell c2 = datarow.createCell(2);  
//	                HSSFRichTextString t2 = new HSSFRichTextString((String)map.get("customsPassCode"));  
//	                c2.setCellValue(t2);  
	                
	                
	                String jhcx=Common.empty(map.get("shipName"))?"":(String)map.get("shipName");
	                
	                HSSFCell c3 = datarow.createCell(2);  
	                HSSFRichTextString t3 = new HSSFRichTextString(jhcx);  
	                c3.setCellValue(t3);  
	                
	                
	                String sTime="";
	                if(!Common.empty(map.get("arrivalStartTime"))){
	                	
	                	sTime=((String) map.get("arrivalStartTime")).split(" ")[0];
	                }
	                
	                HSSFCell c32 = datarow.createCell(3);  
	                HSSFRichTextString t32 = new HSSFRichTextString(sTime);  
	                c32.setCellValue(t32);  
	                
	                HSSFCell c4 = datarow.createCell(4);  
	                HSSFRichTextString t4 = new HSSFRichTextString((String)map.get("tankCode"));  
	                c4.setCellValue(t4);  
	                
	                HSSFCell c5 = datarow.createCell(5);  
	                HSSFRichTextString t5 = new HSSFRichTextString((String)map.get("goodsCode"));  
	                c5.setCellValue(t5);  
	                
	                
	                
	                String clientName="";
	                if(!Common.empty(map.get("ladingClientName"))){
	                	clientName=map.get("ladingClientName").toString();
	                }else{
	                	clientName=map.get("clientName").toString();
	                }
	                
	                HSSFCell c6 = datarow.createCell(6);  
	                HSSFRichTextString t6 = new HSSFRichTextString(clientName);  
	                c6.setCellValue(t6);  
	                
	                String rootCode="";
					if(!Common.empty(map.get("rootCode"))){
						rootCode=((String)map.get("rLadingCode"));
					}else if(Common.empty(map.get("rootCode"))&&!Common.empty(map.get("sourceCode"))){
						rootCode=((String)map.get("ladingCode"));
					}
					else{
						rootCode=((String)map.get("cargoCode"));
					}
	                HSSFCell c7 = datarow.createCell(7);  
	                HSSFRichTextString t7 = new HSSFRichTextString(rootCode);  
	                c7.setCellValue(t7);  
	                
	                String sourceCode="";
	                if(!Common.empty(map.get("sourceCode"))){
	                	sourceCode=((String)map.get("ladingCode"));
					}else{
						sourceCode= "";
					}
	                
	                HSSFCell c8 = datarow.createCell(8);  
	                HSSFRichTextString t8 = new HSSFRichTextString(sourceCode);  
	                c8.setCellValue(t8);  
	                
	                
	                String ladingTime="";
	                
	                if(!Common.empty(map.get("ladingTime"))){
	                	if(map.get("ladingTime").toString().equals("长期")){
	                		ladingTime="长期";
	                	}else{
	                		ladingTime=((String)map.get("ladingTime")).split(" ")[0];
	                	}
	                	
	                }
	                
	                
	                HSSFCell c91 = datarow.createCell(9);  
	                HSSFRichTextString t91 = new HSSFRichTextString(ladingTime);  
	                c91.setCellValue(t91);  
	                
	                
	                HSSFCell c9 = datarow.createCell(10);  
	                HSSFRichTextString t9 = new HSSFRichTextString(((String)map.get("createTime")).split(" ")[0]);  
	                c9.setCellValue(t9);  
	                
	                HSSFCell c10 = datarow.createCell(11);
	                Double t10 = Common.fixDouble( Double.valueOf(map.get("goodsInbound")+""),3);  
	                c10.setCellValue(t10);  
	                goodsInbound+=Double.parseDouble(t10.toString());
	                
	                
	                HSSFCell c20 = datarow.createCell(12);  
	                Double t20 = Common.fixDouble(Double.valueOf(map.get("goodsLoss")+""),3);  
	                c20.setCellValue(t20);
	                goodsLoss+=Double.parseDouble(t20.toString());
	                
	                
	                HSSFCell c11 = datarow.createCell(13);  
	                Double t11 = Common.fixDouble( Double.valueOf(map.get("goodsIn")+""),3);  
	                c11.setCellValue(t11);
	                goodsIn+=Double.parseDouble(t11.toString());
	                
	                
	                
	                HSSFCell c12 = datarow.createCell(14);  
	                Double t12 = Common.fixDouble( Double.valueOf(map.get("goodsTIn")+""),3);  
	                c12.setCellValue(t12);
	                goodsTIn+=Double.parseDouble(t12.toString());
	                
	                HSSFCell c13 = datarow.createCell(15);  
	                Double t13 =Common.fixDouble(  Double.valueOf(map.get("goodsOut")+""),3);  
	                c13.setCellValue(t13);
	                goodsOut+=Double.parseDouble(t13.toString());
	                
	                HSSFCell c14 = datarow.createCell(16);  
	                Double t14 = Common.fixDouble( Double.valueOf(map.get("goodsTOut")+""),3);  
	                c14.setCellValue(t14);
	                goodsTOut+=Double.parseDouble(t14.toString());

	                HSSFCell c15 = datarow.createCell(17);  
	                Double t15 = Common.fixDouble( Double.valueOf(map.get("goodsSave")+""),3);  
	                c15.setCellValue(t15);
	                goodsSave+=Double.parseDouble(t15.toString());
	                
	                HSSFCell c16 = datarow.createCell(18);  
	                Double t16 = Double.valueOf(map.get("goodsOld")+"");  
	                c16.setCellValue(t16);
	                goodsOld+=Common.fixDouble( Double.parseDouble(t16.toString()),3);
	                
	                HSSFCell c17 = datarow.createCell(19);  
	                Double t17 = Common.fixDouble( Double.valueOf(map.get("goodsToday")+""),3);  
	                c17.setCellValue(t17);
	                goodsToday+=Double.parseDouble(t17.toString());
	                
	                HSSFCell c18 = datarow.createCell(20);  
	                Double t18 = Common.fixDouble( Double.valueOf(map.get("totalCount")+""),3);  
	                c18.setCellValue(t18);
	                totalCount+=Double.parseDouble(t18.toString());
	                
	                
	                HSSFCell c19 = datarow.createCell(21);  
	                Double t19 = Common.fixDouble(Double.valueOf(map.get("goodsSurplus")+""),3);  
	                c19.setCellValue(t19);
	                goodsSurplus+=Double.parseDouble(t19.toString());
	                
	               
//	                goodsSurplus+=Double.parseDouble(t19.toString());
	                
	                
	            }
	            
	            
	            HSSFRow datarow = sheet.createRow(data.size()+2);
	            HSSFCell cell0 = datarow.createCell(0);  
	          HSSFRichTextString t0 = new HSSFRichTextString("总计：");  
	          cell0.setCellValue(t0);
	          
	          HSSFCell c10 = datarow.createCell(11);
	        c10.setCellValue(goodsInbound);  
	        
	        HSSFCell c111 = datarow.createCell(12);  
	        c111.setCellValue(goodsLoss);
	        
	        HSSFCell c11 = datarow.createCell(13);  
	        c11.setCellValue(goodsIn);
	        
	        HSSFCell c12 = datarow.createCell(14);  
	        c12.setCellValue(goodsTIn);
	        
	        HSSFCell c13 = datarow.createCell(15);  
	        c13.setCellValue(goodsOut);
	        
	        HSSFCell c14 = datarow.createCell(16);  
	        c14.setCellValue(goodsTOut);

	        HSSFCell c15 = datarow.createCell(17);  
	        c15.setCellValue(goodsSave);
	        
	        HSSFCell c16 = datarow.createCell(18);  
	        c16.setCellValue(goodsOld);
	        
	        HSSFCell c17 = datarow.createCell(19);  
	        c17.setCellValue(goodsToday);
	        
	        HSSFCell c18 = datarow.createCell(20);  
	        c18.setCellValue(totalCount);
	        
	        HSSFCell c19 = datarow.createCell(21);  
	        c19.setCellValue(goodsSurplus);
	          
	        	
	        }
	        
	        
	        
	        
	    
            
		} catch (Exception re) {
			re.printStackTrace() ;
		}
		return workbook;
		
	}

	@Override
	public HSSFWorkbook exportCargoExcel(OaMsg msg,int type,HttpServletRequest request,final StatisticsDto statisticsDto) throws OAException {
		// TODO Auto-generated method stub
		
		final List<Object> data=msg.getData();
		
		HSSFWorkbook workbook = new HSSFWorkbook();  
		try {
	if(type==1){
		 // 声明一个工作薄  
        // 生成一个表格  
        HSSFSheet sheet = workbook.createSheet("货批汇总");  
        // 设置表格默认列宽度为15个字节  
        sheet.setDefaultColumnWidth((short) 20);  
        // 产生表格标题行  
        HSSFRow row = sheet.createRow(0);  
        HSSFCell cell = row.createCell(0);  
        for(int i=2;i<=15;i++){
        	
        	sheet.setColumnWidth(i, 3000); 
        }
        sheet.setColumnWidth(3, 6000); 
        HSSFRichTextString text = new HSSFRichTextString("货主");  
        cell.setCellValue(text);  
        
        HSSFCell bianhaocell = row.createCell(1);  
        HSSFRichTextString bianhao = new HSSFRichTextString("货批号");  
        bianhaocell.setCellValue(bianhao);  
        
        HSSFCell chechuancell = row.createCell(2);  
        HSSFRichTextString chechuantext = new HSSFRichTextString("货品");  
        chechuancell.setCellValue(chechuantext);  
        
        HSSFCell kaipiaocell = row.createCell(3);  
        HSSFRichTextString kaipiaotext = new HSSFRichTextString("进货船");  
        kaipiaocell.setCellValue(kaipiaotext);  
        
        HSSFCell h1 = row.createCell(4);  
      HSSFRichTextString h11 = new HSSFRichTextString("到港日期");  
      h1.setCellValue(h11);  
        
      HSSFCell h2 = row.createCell(5);  
    HSSFRichTextString h12 = new HSSFRichTextString("入港确认单");  
    h2.setCellValue(h12);  
    
    HSSFCell h3 = row.createCell(6);  
  HSSFRichTextString h13 = new HSSFRichTextString("入港联系单");  
  h3.setCellValue(h13);  
  
  HSSFCell h4 = row.createCell(7);  
HSSFRichTextString h14 = new HSSFRichTextString("入库损耗单");  
h4.setCellValue(h14);  
      
        
        HSSFCell kaipiaocell1 = row.createCell(8);  
      HSSFRichTextString kaipiaotext1 = new HSSFRichTextString("仓储性质");  
      kaipiaocell1.setCellValue(kaipiaotext1);  
        
        HSSFCell huoticell = row.createCell(9);  
        HSSFRichTextString huotitext = new HSSFRichTextString("商检");  
        huoticell.setCellValue(huotitext);  
        
        HSSFCell shangjicell = row.createCell(10);  
        HSSFRichTextString shangjitext = new HSSFRichTextString("入库(吨)");  
        shangjicell.setCellValue(shangjitext);  
        
        HSSFCell yuanshicell = row.createCell(11);  
        HSSFRichTextString yuanshitext = new HSSFRichTextString("封量(吨)");  
        yuanshicell.setCellValue(yuanshitext);  
        
        HSSFCell tihuocell = row.createCell(12);  
        HSSFRichTextString tihuotext = new HSSFRichTextString("之前发货(吨)");  
        tihuocell.setCellValue(tihuotext);  
        
        HSSFCell yuanhaocell = row.createCell(13);  
        HSSFRichTextString yuanhaotext = new HSSFRichTextString("本段实发数(吨)");  
        yuanhaocell.setCellValue(yuanhaotext);  
        
        HSSFCell diaohaocell = row.createCell(14);  
        HSSFRichTextString diaohaotext = new HSSFRichTextString("实发总数(吨)");  
        diaohaocell.setCellValue(diaohaotext);  
        
        HSSFCell shuliangcell = row.createCell(15);  
        HSSFRichTextString shuliangtext = new HSSFRichTextString("结存量(吨)");  
        shuliangcell.setCellValue(shuliangtext);
        
        
        double goodsTank=0;
        double goodsSave=0;
        double goodsOld=0;
        double goodsToday=0;
        double goodsSend=0;
        double goodsSurplus=0;
        
        
        for(int i = 0 ; i<data.size();i++){
        	Map<String,Object> map=(Map<String, Object>) data.get(i);
        	HSSFRow datarow = sheet.createRow(i+1);  
            HSSFCell cell0 = datarow.createCell(0);  
//            cell0.setCellStyle(style);  
            HSSFRichTextString t0 = new HSSFRichTextString((String)map.get("clientName"));  
            cell0.setCellValue(t0);
            
            HSSFCell c1 = datarow.createCell(1);  
//            c1.setCellStyle(style);  
            HSSFRichTextString t1 = new HSSFRichTextString((String)map.get("code"));  
            c1.setCellValue(t1);  
            
            HSSFCell c2 = datarow.createCell(2);  
//            c2.setCellStyle(style);  
            HSSFRichTextString t2 = new HSSFRichTextString((String)map.get("productName"));  
            c2.setCellValue(t2);  
            
            
            
            HSSFCell c3 = datarow.createCell(3);  
//            c3.setCellStyle(style);  
            HSSFRichTextString t3 = new HSSFRichTextString((String)map.get("shipName"));  
            c3.setCellValue(t3);  
            
            String sTime="";
            if(!Common.empty(map.get("arrivalStartTime"))){
            	
            	sTime=((String) map.get("arrivalStartTime")).split(" ")[0];
            }
            
            HSSFCell f1 = datarow.createCell(4);  
//          c3.setCellStyle(style);  
          HSSFRichTextString f11 = new HSSFRichTextString(sTime);  
          f1.setCellValue(f11); 
          
          String cNo="";
          if(!Common.empty(map.get("confirmNo"))){
          	
        	  cNo=((String) map.get("confirmNo")).replace("No.", "");
          }
            
          HSSFCell f2 = datarow.createCell(5);  
//        c3.setCellStyle(style);  
        HSSFRichTextString f12 = new HSSFRichTextString(cNo);  
        f2.setCellValue(f12); 
        
        String pNo="";
        if(!Common.empty(map.get("planNo"))){
        	
        	pNo=((String) map.get("planNo")).replace("No.", "");
        }
        
        HSSFCell f3 = datarow.createCell(6);  
//      c3.setCellStyle(style);  
      HSSFRichTextString f13 = new HSSFRichTextString(pNo);  
      f3.setCellValue(f13); 
      
      
      String iNo="";
      if(!Common.empty(map.get("inboundNo"))){
      	
    	  iNo=((String) map.get("inboundNo")).replace("No.", "");
      }
      
      HSSFCell f4 = datarow.createCell(7);  
//    c3.setCellStyle(style);  
    HSSFRichTextString f14 = new HSSFRichTextString(iNo);  
    f4.setCellValue(f14); 
          
          
            
            HSSFCell c41 = datarow.createCell(8);  
//          c4.setCellStyle(style);  
          //仓储性质  1：一般 2：保税临租 3：包罐 4 临租 5 通过
            String sotrageType="";
            if(!Common.empty(map.get("storageType"))){
            	
            	switch((String)map.get("storageType")){
            	case "1":
            		sotrageType="一般";
            		break;
            	case "2":
            		sotrageType="保税临租";
            		break;
            	case "3":
            		sotrageType="包罐";
            		break;
            	case "4":
            		sotrageType="临租";
            		break;
            	case "5":
            		sotrageType="通过";
            		break;
            	}
            }
          HSSFRichTextString t41 = new HSSFRichTextString(sotrageType);  
          c41.setCellValue(t41);  
          
          
            HSSFCell c4 = datarow.createCell(9);  
//            c4.setCellStyle(style);  
            Double t4=Common.fixDouble(  Double.valueOf(map.get("goodsInspect")+""),3);   
            c4.setCellValue(t4);  
            
            HSSFCell c5 = datarow.createCell(10);  
//            c5.setCellStyle(style);  
//            HSSFRichTextString t5 = new HSSFRichTextString(map.get("goodsTank")+"");  
            Double t5 =Common.fixDouble(  Double.valueOf(map.get("goodsTotal")+""),3);  
            c5.setCellValue(t5); 
            goodsTank+=Double.parseDouble(t5.toString());
            
            HSSFCell c6 = datarow.createCell(11);  
//            c6.setCellStyle(style);  
//            HSSFRichTextString t6 = new HSSFRichTextString(map.get("goodsSave")+"");
            Double t6 = Common.fixDouble( Double.valueOf(map.get("goodsSave")+""),3);  
            c6.setCellValue(t6);  
            goodsSave+=Double.parseDouble(t6.toString());
            
            HSSFCell c7 = datarow.createCell(12);  
//            c7.setCellStyle(style);  
//            HSSFRichTextString t7 = new HSSFRichTextString(map.get("goodsOld")+"");  
            Double t7 = Common.fixDouble( Double.valueOf(map.get("goodsOld")+""),3);  
            c7.setCellValue(t7);  
            goodsOld+=Double.parseDouble(t7.toString());
            
            HSSFCell c8 = datarow.createCell(13);  
//            c8.setCellStyle(style);  
//            HSSFRichTextString t8 = new HSSFRichTextString(map.get("goodsToday")+"");  
            Double t8 = Common.fixDouble( Double.valueOf(map.get("goodsToday")+""),3);  
            c8.setCellValue(t8);  
            goodsToday+=Double.parseDouble(t8.toString());
            
            HSSFCell c9 = datarow.createCell(14);  
//            c9.setCellStyle(style);  
//            HSSFRichTextString t9 = new HSSFRichTextString(map.get("goodsSend")+"");  
            Double t9 = Common.fixDouble( Double.valueOf(map.get("goodsSend")+""),3);  
            c9.setCellValue(t9);  
            goodsSend+=Double.parseDouble(t9.toString());
            
            HSSFCell c10 = datarow.createCell(15);
//            c10.setCellStyle(style);  
//            HSSFRichTextString t10 = new HSSFRichTextString(map.get("goodsSurplus")+"");  
//            double mGSurplus=Common.fixDouble(Double.valueOf(map.get("goodsSurplus")+""),3)<0?0:Common.fixDouble(Double.valueOf(map.get("goodsSurplus")+""),3);  
            
            
            Double t10 = Common.fixDouble(Double.valueOf(map.get("goodsSurplus")+""),3);  
            c10.setCellValue(t10);  
            goodsSurplus+=Double.parseDouble(t10.toString());
            
        }
        
        
        HSSFRow datarow = sheet.createRow(data.size()+2);
        HSSFCell cell0 = datarow.createCell(0);  
//      cell0.setCellStyle(style);  
      HSSFRichTextString t0 = new HSSFRichTextString("总计：");  
      cell0.setCellValue(t0);
      
      HSSFCell c5 = datarow.createCell(10);  
//    c5.setCellStyle(style);  
//    HSSFRichTextString t5 = new HSSFRichTextString(goodsTank+"");  
    c5.setCellValue(goodsTank); 
    
    HSSFCell c6 = datarow.createCell(11);  
//    c6.setCellStyle(style);  
//    HSSFRichTextString t6 = new HSSFRichTextString(goodsSave+"");  
    c6.setCellValue(goodsSave);  
    
    HSSFCell c7 = datarow.createCell(12);  
//    c7.setCellStyle(style);  
//    HSSFRichTextString t7 = new HSSFRichTextString(goodsOld+"");  
    c7.setCellValue(goodsOld);  
    
    HSSFCell c8 = datarow.createCell(13);  
//    c8.setCellStyle(style);  
//    HSSFRichTextString t8 = new HSSFRichTextString(goodsToday+"");  
    c8.setCellValue(goodsToday);  
    
    HSSFCell c9 = datarow.createCell(14);  
//    c9.setCellStyle(style);  
//    HSSFRichTextString t9 = new HSSFRichTextString(goodsSend+"");  
    c9.setCellValue(goodsSend);  
    
    HSSFCell c10 = datarow.createCell(15);
//    c10.setCellStyle(style);  
//    HSSFRichTextString t10 = new HSSFRichTextString(goodsSurplus+"");  
    c10.setCellValue(goodsSurplus);  
	}else if(type==2){
		

    	


		ExcelUtil excelUtil=null;
		excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/cargoHuizong.xls", new CallBack() {
			@Override
			public void setSheetValue(HSSFSheet sheet) 
			{
			int item=3,reginItem,itemChildrenSize;
			HSSFRow itemRow = null;
			Map<String, Object> itemMapData;
			double sumgoodsInspect=0;
			double sumgoodsTotal=0;
			double sumgoodsSave=0;
			double sumgoodsOld=0;
			double sumgoodsSend=0;
			double sumgoodsSurplus=0;
			double sumgoodsToday=0;
			itemRow=sheet.getRow(1);
			 itemMapData=(Map<String, Object>) data.get(0);
			itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("货品:"+itemMapData.get("productName").toString()));//序号
			
			 if(data!=null&&data.size()>0){
				 int size=data.size();
				for(int i=0;i<size;i++){
					
				 if(i!=0){
					itemRow=sheet.createRow(i+item);
					itemRow.setHeight(sheet.getRow(3).getHeight());
					for(int j=0;j<13;j++){
						itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
					}
				 }else{
					 itemRow=sheet.getRow(item);
				 }
				 //初始化数据
				 itemMapData=(Map<String, Object>) data.get(i);
				 
				
				 
				 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(i+1));//序号
				 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(itemMapData.get("clientName")));
				 itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(itemMapData.get("productName")));
				 itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(itemMapData.get("code")));
				 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(itemMapData.get("shipName")));
				 
				 String sTime="";
		            if(!Common.empty(itemMapData.get("arrivalStartTime"))){
		            	
		            	sTime=((String) itemMapData.get("arrivalStartTime")).split(" ")[0];
		            }
				 
				 itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(sTime));
				 itemRow.getCell(6).setCellValue(Common.fixDouble(  Double.valueOf(itemMapData.get("goodsInspect")+""),3)+"");
				 sumgoodsInspect+=Common.fixDouble(  Double.valueOf(itemMapData.get("goodsInspect")+""),3);
				 itemRow.getCell(7).setCellValue(Common.fixDouble(  Double.valueOf(itemMapData.get("goodsTotal")+""),3)+"");
				 sumgoodsTotal+=Common.fixDouble(  Double.valueOf(itemMapData.get("goodsTotal")+""),3);
				 
				 itemRow.getCell(8).setCellValue(Common.fixDouble(  Double.valueOf(itemMapData.get("goodsSave")+""),3)+"");
				 sumgoodsSave+=Common.fixDouble(  Double.valueOf(itemMapData.get("goodsSave")+""),3);
				 
				 itemRow.getCell(9).setCellValue(Common.fixDouble(  Double.valueOf(itemMapData.get("goodsOld")+""),3)+"");  
				 sumgoodsOld+=Common.fixDouble(  Double.valueOf(itemMapData.get("goodsOld")+""),3);
				 
				 itemRow.getCell(10).setCellValue(Common.fixDouble(  Double.valueOf(itemMapData.get("goodsToday")+""),3)+"");  
				 sumgoodsToday+=Common.fixDouble(  Double.valueOf(itemMapData.get("goodsToday")+""),3);
				 
				 itemRow.getCell(11).setCellValue(Common.fixDouble( Double.valueOf(itemMapData.get("goodsSend")+""),3)+"");  
				 sumgoodsSend+=Common.fixDouble(  Double.valueOf(itemMapData.get("goodsSend")+""),3);
				 
				 itemRow.getCell(12).setCellValue(Common.fixDouble(  Double.valueOf(itemMapData.get("goodsSurplus")+""),3)+"");  
				 sumgoodsSurplus+=Common.fixDouble(  Double.valueOf(itemMapData.get("goodsSurplus")+""),3);
				 
				}
				
				itemRow=sheet.createRow(3+size);
				itemRow.setHeight(sheet.getRow(3).getHeight());
				for(int j=0;j<13;j++){
					itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
				}
				
				itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("合计"));//序号
				itemRow.getCell(6).setCellValue(Common.fixDouble(sumgoodsInspect,3)+"");  
				 itemRow.getCell(7).setCellValue(Common.fixDouble(sumgoodsTotal,3)+"");  
				 itemRow.getCell(8).setCellValue(Common.fixDouble(sumgoodsSave,3)+"");  
				 itemRow.getCell(9).setCellValue(Common.fixDouble(sumgoodsOld,3)+"");  
				 itemRow.getCell(10).setCellValue(Common.fixDouble(sumgoodsToday,3)+""); 
				 itemRow.getCell(11).setCellValue(Common.fixDouble(sumgoodsSend,3)+"");  
				 itemRow.getCell(12).setCellValue(Common.fixDouble(sumgoodsSurplus,3)+"");  
				 
				 itemRow=sheet.createRow(4+size);
				 itemRow.setHeight(sheet.getRow(3).getHeight());
					for(int j=0;j<13;j++){
						itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
					}
				 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("统计时间:"));//序号
					itemRow.getCell(1).setCellValue(statisticsDto.getsStartTime()+" 到 "+statisticsDto.getsEndTime());  
			 }
			 
//				if (region.size()>region1.size()){
//						region2=region;
//					}else{
//						region2=region1;
//					}
//				 
			 sheet.setForceFormulaRecalculation(true);
			}
		});
		
		return excelUtil.getWorkbook();
		
	}
			 
	
        
            
		} catch (Exception re) {
			re.printStackTrace() ;
		}
		return workbook;
		
	}

	@Override
	public OaMsg getCargoTotal(StatisticsDto sDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始-------list--------------");
			
			List<Map<String, Object>> cargoList = goodsStatisticsDao.getcargoTotal(sDto);

			for (Map<String, Object> map : cargoList) {
				double goodsSend = 0, goodsTotal = 0,goodsOld = 0;
				if (!Common.empty(map.get("goodsTotal"))) {// 总量
					goodsTotal = Double.parseDouble(map.get("goodsTotal")
							.toString());
				} 
				if (!Common.empty(map.get("goodsOld"))) {// 今日前发量
					goodsOld = Double.parseDouble(map.get("goodsOld")
							.toString());
					map.put("goodsOld", Common.fixDouble(goodsOld, 3));
				}
				
				if (!Common.empty(map.get("goodsSend"))) {// 总发量
					goodsSend = Double.parseDouble(map.get("goodsSend")
							.toString());
				}
				map.put("cargoTotal",Common.fixDouble(goodsTotal - goodsSend, 3));
				map.put("goodsben",Common.fixDouble(goodsSend - goodsOld, 3));
			}
			oaMsg.getData().addAll(cargoList);
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getLog(StatisticsDto statisticsDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始-------list--------------");
			String ids="";
			if(!Common.isNull(statisticsDto.getType())){
				if(statisticsDto.getType()==21){
					List<Map<String, Object>> goodsIds=goodsStatisticsDao.getLogGoodsIds(statisticsDto);
					
					if(!Common.empty(goodsIds)){
						
						for(int i=0;i<goodsIds.size();i++){
							if(i==goodsIds.size()-1){
								ids+=goodsIds.get(i).get("goodsId").toString();
							}else{
								
								ids+=goodsIds.get(i).get("goodsId").toString()+",";
							}
						}
						
						statisticsDto.setGoodsIds(ids);
					}
					else{
						statisticsDto.setGoodsIds("0");
					}
					
				}
				//发货清单
				else if(statisticsDto.getType()==22){
				

					List<Map<String, Object>> goodsIds=goodsStatisticsDao.getLogGoodsIds(statisticsDto);
					
					if(!Common.empty(goodsIds)){
						if(!Common.empty(goodsIds.get(0).get("goodsId"))){
						String goodsIds1=getDownGoodsIds(goodsIds.get(0).get("goodsId").toString());
						statisticsDto.setGoodsIds(goodsIds1);
					}
					else{
						statisticsDto.setGoodsIds("0");
					}
						}
					else{
						statisticsDto.setGoodsIds("0");
					}
					
//			System.out.println(statisticsDto.getGoodsIds());
					
					
				}
			}
			
			List<Map<String, Object>> cargoList = goodsStatisticsDao.getLog(statisticsDto,
					pageView.getStartRecord(), pageView.getMaxresult());

			oaMsg.getData().addAll(cargoList);
			int count = goodsStatisticsDao.logCount(statisticsDto);
			oaMsg.getMap().put(Constant.TOTALRECORD, count + "");
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	private String getDownGoodsIds(String ids) throws OAException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> goodsIds=goodsStatisticsDao.getDownGoodsId(ids);
		
		if(goodsIds.get(0).get("goodsIds").toString().equals(ids)){
			return ids;
		}else{
			return getDownGoodsIds(goodsIds.get(0).get("goodsIds").toString());
		}
		
	}

	@Override
	public OaMsg getLogTotal(StatisticsDto statisticsDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始-------list--------------");
			String ids="";
			if(!Common.isNull(statisticsDto.getType())){
				if(statisticsDto.getType()==21){
					List<Map<String, Object>> goodsIds=goodsStatisticsDao.getLogGoodsIds(statisticsDto);
					if(!Common.empty(goodsIds)){
						for(int i=0;i<goodsIds.size();i++){
						if(i==goodsIds.size()-1){
							ids+=goodsIds.get(i).get("goodsId").toString();
						}else{
							
							ids+=goodsIds.get(i).get("goodsId").toString()+",";
						}
					}
					
					statisticsDto.setGoodsIds(ids);
					
//					List<Map<String, Object>> goodsSaveTotal=goodsStatisticsDao.getLogGoodsSaveTotal(statisticsDto);
//					oaMsg.getMap().put("goodsSave", goodsSaveTotal.get(0).get("goodsSave").toString());
					
					}
					else{
						statisticsDto.setGoodsIds("0");
					}
				}//发货清单
				else if(statisticsDto.getType()==22){
				

					List<Map<String, Object>> goodsIds=goodsStatisticsDao.getLogGoodsIds(statisticsDto);
					
					if(!Common.empty(goodsIds)){
						if(!Common.empty(goodsIds.get(0).get("goodsId"))){
						String goodsIds1=getDownGoodsIds(goodsIds.get(0).get("goodsId").toString());
						statisticsDto.setGoodsIds(goodsIds1);
					}
					else{
						statisticsDto.setGoodsIds("0");
					}
						}
					else{
						statisticsDto.setGoodsIds("0");
					}
					
//			System.out.println(statisticsDto.getGoodsIds());
					
					
				}
			}
			
			
			List<Map<String, Object>> logList = goodsStatisticsDao.getLogTotal(statisticsDto);

			
			oaMsg.getData().addAll(logList);
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public HSSFWorkbook exportLogExcel(HttpServletRequest request,OaMsg msg,Integer type,final StatisticsDto statisticsDto) throws OAException {
		// TODO Auto-generated method stub
		
		final List<Object> data=msg.getData();
		
		HSSFWorkbook workbook = new HSSFWorkbook();  
		try {
	
			 // 声明一个工作薄  
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.createSheet("日志汇总");  
	        // 设置表格默认列宽度为15个字节  
	        sheet.setDefaultColumnWidth((short) 20);  
	        //普通日志
	        if(type==1){
	        	sheet.setColumnWidth(4, 2000);
	        	sheet.setColumnWidth(1, 2000);
	        	sheet.setColumnWidth(2, 5000);
	        	sheet.setColumnWidth(5, 5000);
	        	sheet.setColumnWidth(6, 5000);
	        	sheet.setColumnWidth(7, 4000);
	        	sheet.setColumnWidth(8, 4000);
	        	
	        	 // 产生表格标题行  
		        HSSFRow row = sheet.createRow(0);  
	            HSSFCell cell = row.createCell(0);  
	            HSSFRichTextString text = new HSSFRichTextString("货权");  
	            cell.setCellValue(text);  
	            
	            HSSFCell tidanhuozhu = row.createCell(1);  
	            HSSFRichTextString tidanhuozhu1 = new HSSFRichTextString("提单货主");  
	            tidanhuozhu.setCellValue(tidanhuozhu1);  
	            
	            
	            HSSFCell bianhaocell = row.createCell(2);  
	            HSSFRichTextString bianhao = new HSSFRichTextString("货品");  
	            bianhaocell.setCellValue(bianhao);  
	            
	            HSSFCell huoticell3 = row.createCell(3);  
	            HSSFRichTextString huotitext3 = new HSSFRichTextString("货批号");  
	            huoticell3.setCellValue(huotitext3);  
	            
	            HSSFCell huoticell = row.createCell(4);  
	            HSSFRichTextString huotitext = new HSSFRichTextString("上下家");  
	            huoticell.setCellValue(huotitext);  
	            
	            HSSFCell shangjicell = row.createCell(5);  
	            HSSFRichTextString shangjitext = new HSSFRichTextString("方式");  
	            shangjicell.setCellValue(shangjitext);  
	            
	            HSSFCell fahuoguan = row.createCell(6);  
	            HSSFRichTextString fahuoguantext = new HSSFRichTextString("发货罐号");  
	            fahuoguan.setCellValue(fahuoguantext);  
	            
	            
	            
	            HSSFCell yuanshicell = row.createCell(7);  
	            HSSFRichTextString yuanshitext = new HSSFRichTextString("日期");  
	            yuanshicell.setCellValue(yuanshitext);  
	            
	            HSSFCell tihuocell = row.createCell(8);  
	            HSSFRichTextString tihuotext = new HSSFRichTextString("单号");  
	            tihuocell.setCellValue(tihuotext);  
	            
	            HSSFCell yuanhaocell = row.createCell(9);  
	            HSSFRichTextString yuanhaotext = new HSSFRichTextString("货量变化（吨）");  
	            yuanhaocell.setCellValue(yuanhaotext);  
	            
	            HSSFCell diaohaocell = row.createCell(10);  
	            HSSFRichTextString diaohaotext = new HSSFRichTextString("存量（吨）");  
	            diaohaocell.setCellValue(diaohaotext);  
	            
	            HSSFCell shuliangcell = row.createCell(11);  
	            HSSFRichTextString shuliangtext = new HSSFRichTextString("封量（吨）");  
	            shuliangcell.setCellValue(shuliangtext);
	            
	            HSSFCell diaoru = row.createCell(12);  
	            HSSFRichTextString diaorutext = new HSSFRichTextString("发货单");  
	            diaoru.setCellValue(diaorutext);
	            
	            HSSFCell tdiaoru = row.createCell(13);  
	            HSSFRichTextString tdiaorutext = new HSSFRichTextString("车船号");  
	            tdiaoru.setCellValue(tdiaorutext);
	            
	            for(int i = 0 ; i<data.size();i++){
	            	Map<String,Object> map=(Map<String, Object>) data.get(i);
	            	HSSFRow datarow = sheet.createRow(i+1);  
	                HSSFCell cell0 = datarow.createCell(0);  
	                HSSFRichTextString t0 = new HSSFRichTextString((String)map.get("clientName"));  
	                cell0.setCellValue(t0);
	                
	                String goodsLadingClientName="";
	                if(!Common.empty(map.get("goodsLadingClientName"))){
	                	goodsLadingClientName=map.get("goodsLadingClientName").toString();
	                }
	                HSSFCell cell1 = datarow.createCell(1);  
	                HSSFRichTextString t01 = new HSSFRichTextString(goodsLadingClientName);  
	                cell1.setCellValue(t01);
	                
	                
	                HSSFCell c1 = datarow.createCell(2);  
	                HSSFRichTextString t1 = new HSSFRichTextString((String)map.get("productName"));  
	                c1.setCellValue(t1);  
	                
	                HSSFCell cc1 = datarow.createCell(3);  
	                HSSFRichTextString tt1 = new HSSFRichTextString((String)map.get("cargoCode"));  
	                cc1.setCellValue(tt1);  
	                
	                
	                String lName="";
	                if(1==Integer.parseInt(map.get("type").toString())){
	                	lName=(String)map.get("shipName");
	                }else if (2==Integer.parseInt(map.get("type").toString())){
	                	lName=(String)map.get("ladingInClientName");
	                }else{
	                	lName=(String)map.get("ladingClientName");
	                }
	                HSSFCell c2= datarow.createCell(4);  
	                HSSFRichTextString t2 = new HSSFRichTextString(lName);  
	                c2.setCellValue(t2);  
	                
	                String sType="";
	                switch (Integer.parseInt(map.get("type").toString())) {
					case 1:
						sType="入库";
						break;

					case 2:
						sType="调入";
						break;
						
					case 3:
						sType="调出";
						break;
						
					case 4:
						sType="封放";
						break;
						
					case 5:
						if(Integer.parseInt(map.get("actualType").toString())>0){
							sType= "发货";
							
						}else{
							sType= "发货待提";
						}
						break;
						
					case 6:
						sType="退回";
						break;
						
					case 7:
						sType="预入库";
						break;
						
					case 9:
						sType="扣损";
						break;
						
					case 10:
						sType="商检&封放";
						break;
						
					case 11:
						sType="清盘";
						break;
						
					case 12:
						sType="复盘";
						break;
					}
	                HSSFCell c3 = datarow.createCell(5);  
	                HSSFRichTextString t3 = new HSSFRichTextString(sType);  
	                c3.setCellValue(t3);  
	                
	                HSSFCell guanhao = datarow.createCell(6);  
	                String tanknam="";
	                if(!Common.empty(map.get("tankName1"))){
	                	tanknam=map.get("tankName1").toString();
	                }else if(!Common.empty(map.get("tankName"))){
	                	tanknam=map.get("tankName").toString();
	                }
	                
	                HSSFRichTextString guanhaot = new HSSFRichTextString(tanknam);  
	                guanhao.setCellValue(guanhaot); 
	                
	                HSSFCell c4 = datarow.createCell(7);  
	                HSSFRichTextString t4 = new HSSFRichTextString(new Date(Long.parseLong(map.get("createTime").toString())*1000).toLocaleString());  
	                c4.setCellValue(t4); 
	                
	                
	                String ladingEvidence="";
	                if(!Common.empty(map.get("ladingEvidence"))){
	                	ladingEvidence=map.get("ladingEvidence").toString();
	                }else if(!Common.empty(map.get("nextLadingCode"))){
	                	ladingEvidence=map.get("nextLadingCode").toString();
	                }else{
	                	  if(!Common.empty(map.get("ladingCode1"))){
	                	ladingEvidence=map.get("ladingCode1").toString();
	                	  }
	                }
	                
	                
	                HSSFCell c5 = datarow.createCell(8);  
	                HSSFRichTextString t5 = new HSSFRichTextString(ladingEvidence);  
	                c5.setCellValue(t5); 
	                
	                HSSFCell c6 = datarow.createCell(9);  
	                Double t6 = Double.parseDouble(map.get("goodsChange").toString());  
	                c6.setCellValue(t6); 
	                
	                
	                String surplus="";
	                if(Integer.parseInt(map.get("type").toString())==5 && Double.parseDouble(map.get("actualNum").toString())==0){
	                	
	                }else{
	                	surplus=map.get("surplus").toString();
	                }
	                HSSFCell c7 = datarow.createCell(10);
	                if("".equals(surplus)){
	                	 String t7 = surplus;  
	 	                c7.setCellValue(t7); 
	                }else{
	                	
	                	Double t7 = Double.parseDouble(surplus);  
	                	c7.setCellValue(t7);  
	                }
	                
	                String goodsSave="";
	                if(Integer.parseInt(map.get("type").toString())==5 && Double.parseDouble(map.get("actualNum").toString())==0){
	                	
	                }else{
	                	goodsSave=map.get("goodsSave").toString();
	                }
	                
	                HSSFCell c8 = datarow.createCell(11);  
	                
	                if("".equals(goodsSave)){
	                	 String t8 = goodsSave;  
	 	                c8.setCellValue(t8); 
	                }else{
	                	
	                	Double t8 = Double.parseDouble(goodsSave);  
	                	c8.setCellValue(t8);  
	                	
	                }
	                
	                
	                String serial="";
	                if(!Common.empty(map.get("serial"))){
	                	serial=map.get("serial").toString();
	                }
	                
	                
	                
	                
	                
	                HSSFCell c9 = datarow.createCell(12);
	                HSSFRichTextString t9= new HSSFRichTextString(serial);  
	                c9.setCellValue(t9);  
	                
	                String carshipCode="";
	                if(!Common.empty(map.get("carshipCode"))){
	                	carshipCode=map.get("carshipCode").toString();
	                }
	                
	                HSSFCell c10 = datarow.createCell(13);  
	                HSSFRichTextString t10= new HSSFRichTextString(carshipCode);  
	                c10.setCellValue(t10);
	                
	                
	            }
	            
	        
	        	
	        }
	        //进出存
	        else if (type==2){
	        	for(int i=2;i<=12;i++){
	            	
	            	sheet.setColumnWidth(i, 4000); 
	            }
	        	sheet.setColumnWidth(4, 6000); 
	        	 // 产生表格标题行  
		        HSSFRow row = sheet.createRow(0);  
	            HSSFCell cell = row.createCell(0);  
	            HSSFRichTextString text = new HSSFRichTextString("货权");  
	            cell.setCellValue(text);  
	            
	            HSSFCell cell1 = row.createCell(1);  
	            HSSFRichTextString text1 = new HSSFRichTextString("提单货主");  
	            cell1.setCellValue(text1);  
	            
	            
	            HSSFCell bianhaocell = row.createCell(2);  
	            HSSFRichTextString bianhao = new HSSFRichTextString("货品");  
	            bianhaocell.setCellValue(bianhao);  
	            
	            
	            HSSFCell huoticell = row.createCell(3);  
	            HSSFRichTextString huotitext = new HSSFRichTextString("货批");  
	            huoticell.setCellValue(huotitext);  
	            
	            HSSFCell shangjicell = row.createCell(4);  
	            HSSFRichTextString shangjitext = new HSSFRichTextString("上下家/来源");  
	            shangjicell.setCellValue(shangjitext);  
	            
	            HSSFCell yuanshicell = row.createCell(5);  
	            HSSFRichTextString yuanshitext = new HSSFRichTextString("方式");  
	            yuanshicell.setCellValue(yuanshitext);  
	            
	            HSSFCell tihuocell = row.createCell(6);  
	            HSSFRichTextString tihuotext = new HSSFRichTextString("日期");  
	            tihuocell.setCellValue(tihuotext);  
	            
	            HSSFCell yuanhaocell = row.createCell(7);  
	            HSSFRichTextString yuanhaotext = new HSSFRichTextString("单号");  
	            yuanhaocell.setCellValue(yuanhaotext);  
	            
	            HSSFCell diaohaocell = row.createCell(8);  
	            HSSFRichTextString diaohaotext = new HSSFRichTextString("货量变化（吨）");  
	            diaohaocell.setCellValue(diaohaotext);  
	            
	            HSSFCell shuliangcell = row.createCell(9);  
	            HSSFRichTextString shuliangtext = new HSSFRichTextString("存量（吨）");  
	            shuliangcell.setCellValue(shuliangtext);
	            
	            HSSFCell diaoru = row.createCell(10);  
	            HSSFRichTextString diaorutext = new HSSFRichTextString("封量（吨）");  
	            diaoru.setCellValue(diaorutext);
	            
	            HSSFCell tdiaoru = row.createCell(11);  
	            HSSFRichTextString tdiaorutext = new HSSFRichTextString("发货单");  
	            tdiaoru.setCellValue(tdiaorutext);
	            
	            HSSFCell tdiaoru1 = row.createCell(12);  
	            HSSFRichTextString tdiaorutext1 = new HSSFRichTextString("车船号");  
	            tdiaoru1.setCellValue(tdiaorutext1);
	            
	            
	            
	            int goodsId=0;
	            
	            for(int i = 0 ; i<data.size();i++){
	            	Map<String,Object> map=(Map<String, Object>) data.get(i);
	            	HSSFRow datarow = sheet.createRow(i+1);  
	            	
	            	String clientName="";
	            	if(goodsId==Integer.parseInt(map.get("goodsId").toString())){
	            		
	            	}else{
	            		clientName=map.get("clientName").toString();
	            	}
	            	
	                HSSFCell cell0 = datarow.createCell(0);  
	                HSSFRichTextString t0 = new HSSFRichTextString(clientName);  
	                cell0.setCellValue(t0);
	                
	                
	                String goodsLadingClientName="";
	            	if(goodsId==Integer.parseInt(map.get("goodsId").toString())){
	            		
	            	}else{
	            		if(!Common.empty(map.get("goodsLadingClientName")))
	            		goodsLadingClientName=map.get("goodsLadingClientName").toString();
	            	}
	                
	                HSSFCell cell11 = datarow.createCell(1);  
	                HSSFRichTextString t111 = new HSSFRichTextString(goodsLadingClientName);  
	                cell11.setCellValue(t111);
	                
	                
	                String productName="";
	            	if(goodsId==Integer.parseInt(map.get("goodsId").toString())){
	            		
	            	}else{
	            		productName=map.get("productName").toString();
	            	}
	                HSSFCell c1 = datarow.createCell(2);  
	                HSSFRichTextString t1 = new HSSFRichTextString(productName);  
	                c1.setCellValue(t1);  
	                
	                String cargoCode="";
	            	if(goodsId==Integer.parseInt(map.get("goodsId").toString())){
	            		
	            	}else{
	            		goodsId=Integer.parseInt(map.get("goodsId").toString());
	            		cargoCode=map.get("cargoCode").toString();
	            	}
	                HSSFCell c2= datarow.createCell(3);  
	                HSSFRichTextString t2 = new HSSFRichTextString(cargoCode);  
	                c2.setCellValue(t2);  
	                
	                
	                String lName="";
	                if(1==Integer.parseInt(map.get("type").toString())){
	                	lName=(String)map.get("shipName");
	                }else if (2==Integer.parseInt(map.get("type").toString())){
	                	lName=(String)map.get("ladingInClientName");
	                }else{
	                	lName=(String)map.get("ladingClientName");
	                }
	                
	                HSSFCell c3 = datarow.createCell(4);  
	                
	                HSSFRichTextString t3 = new HSSFRichTextString(lName);  
	                c3.setCellValue(t3);  
	                
	                String sType="";
	                int ladingType=Integer.parseInt(map.get("ladingType").toString());
	                switch (Integer.parseInt(map.get("type").toString())) {
					case 1:
						sType="入库";
						break;

					case 2:
						
						
						if(ladingType==1){
							sType="货权调入";
						}else{
							sType="提货权调入";
						}
						
//						sType="调入";
						break;
						
					case 3:
						
						if(ladingType==1){
							sType="货权转移";
						}else{
							sType="提货权转卖";
						}
						
//						sType="调出";
						break;
						
					case 4:
						sType="封放";
						break;
						
					case 5:
						if(Integer.parseInt(map.get("actualType").toString())>0){
							sType= "发货";
							
						}else{
							sType= "发货待提";
						}
						break;
						
					case 6:
						sType="退回";
						break;
						
					case 7:
						sType="预入库";
						break;
						
					case 9:
						sType="扣损";
						break;
						
					case 10:
						sType="商检&封放";
						break;
						
					case 11:
						sType="清盘";
						break;
						
					case 12:
						sType="复盘";
						break;
					}
	                
	                
	               
	                
	                HSSFCell c4 = datarow.createCell(5);  
	                HSSFRichTextString t4= new HSSFRichTextString(sType);  
	                c4.setCellValue(t4); 
	                
	                HSSFCell c5 = datarow.createCell(6);  
	                HSSFRichTextString t5 = new HSSFRichTextString(new Date(Long.parseLong(map.get("createTime").toString())*1000).toLocaleString());  
		               c5.setCellValue(t5); 
	                
	                String ladingEvidence="";
	                if(!Common.empty(map.get("ladingEvidence"))){
	                	ladingEvidence=map.get("ladingEvidence").toString();
	                }else if(!Common.empty(map.get("nextLadingCode"))){
	                	ladingEvidence=map.get("nextLadingCode").toString();
	                }else{
	                	if(!Common.empty(map.get("ladingCode1")))
	                	ladingEvidence=map.get("ladingCode1").toString();
	                	
	                }
	                
	                
	                
	                HSSFCell c6 = datarow.createCell(7);  
	                HSSFRichTextString t6 = new HSSFRichTextString(ladingEvidence);  
	                 c6.setCellValue(t6); 
	                
	                HSSFCell c7 = datarow.createCell(8);  
	                Double t7 = Double.parseDouble(map.get("goodsChange").toString());  
	                c7.setCellValue(t7);  
	                
	                String surplus="";
//	                if(Integer.parseInt(map.get("type").toString())==5 && Double.parseDouble(map.get("actualNum").toString())==0){
//	                	
//	                }else{
	                	surplus=map.get("surplus").toString();
//	                }
	                
	                HSSFCell c8 = datarow.createCell(9);  
	                Double t8 = Double.parseDouble(surplus);  
	                c8.setCellValue(t8);  
	                
	                
	                String goodsSave="";
	                if(Integer.parseInt(map.get("type").toString())==5 && Double.parseDouble(map.get("actualNum").toString())==0){
	                	
	                }else{
	                	goodsSave=map.get("goodsSave").toString();
	                }
	                
	                
	                
	                HSSFCell c9 = datarow.createCell(10);
	                Double t9 = Double.parseDouble(goodsSave);  
	                c9.setCellValue(t9);  
	                
	                String serial="";
	                if(!Common.empty(map.get("serial"))){
	                	serial=map.get("serial").toString();
	                }
	                
	                HSSFCell c10 = datarow.createCell(11);
	                HSSFRichTextString t10= new HSSFRichTextString(serial);  
	                c10.setCellValue(t10);  
	                
	                
	                String carshipCode="";
	                if(!Common.empty(map.get("carshipCode"))){
	                	carshipCode=map.get("carshipCode").toString();
	                }
	                HSSFCell c11 = datarow.createCell(12);  
	                HSSFRichTextString t11= new HSSFRichTextString(carshipCode);  
	                c11.setCellValue(t11);
	                
	            }
	            
	        
	        	
	        }
	        //进出存精简模式
	        else if(type==3){
	        	for(int i=2;i<=12;i++){
	            	
	            	sheet.setColumnWidth(i, 4000); 
	            }
	        	sheet.setColumnWidth(4, 6000); 
	        	 // 产生表格标题行  
		        HSSFRow row = sheet.createRow(0);  
	            HSSFCell cell = row.createCell(0);  
	            HSSFRichTextString text = new HSSFRichTextString("货主");  
	            cell.setCellValue(text);  
	            
	            
	            HSSFCell cell11 = row.createCell(1);  
	            HSSFRichTextString cell112 = new HSSFRichTextString("提单货主");  
	            cell11.setCellValue(cell112);  
	            
	            HSSFCell bianhaocell = row.createCell(2);  
	            HSSFRichTextString bianhao = new HSSFRichTextString("货品");  
	            bianhaocell.setCellValue(bianhao);  
	            
	            
	            HSSFCell huoticell = row.createCell(3);  
	            HSSFRichTextString huotitext = new HSSFRichTextString("货批");  
	            huoticell.setCellValue(huotitext);  
	            
	            HSSFCell shangjicell = row.createCell(4);  
	            HSSFRichTextString shangjitext = new HSSFRichTextString("上下家/来源");  
	            shangjicell.setCellValue(shangjitext);  
	            
	            HSSFCell yuanshicell = row.createCell(5);  
	            HSSFRichTextString yuanshitext = new HSSFRichTextString("方式");  
	            yuanshicell.setCellValue(yuanshitext);  
	            
	            HSSFCell tihuocell = row.createCell(6);  
	            HSSFRichTextString tihuotext = new HSSFRichTextString("日期");  
	            tihuocell.setCellValue(tihuotext);  
	            
	            HSSFCell yuanhaocell = row.createCell(7);  
	            HSSFRichTextString yuanhaotext = new HSSFRichTextString("单号");  
	            yuanhaocell.setCellValue(yuanhaotext);  
	            
	            HSSFCell diaohaocell = row.createCell(8);  
	            HSSFRichTextString diaohaotext = new HSSFRichTextString("货量变化（吨）");  
	            diaohaocell.setCellValue(diaohaotext);  
	            
	            HSSFCell shuliangcell = row.createCell(9);  
	            HSSFRichTextString shuliangtext = new HSSFRichTextString("存量（吨）");  
	            shuliangcell.setCellValue(shuliangtext);
	            
	            HSSFCell diaoru = row.createCell(10);  
	            HSSFRichTextString diaorutext = new HSSFRichTextString("封量（吨）");  
	            diaoru.setCellValue(diaorutext);
	            
	           
	            
	            
	            
	            int goodsId=0;
	            
	            for(int i = 0 ; i<data.size();i++){
	            	Map<String,Object> map=(Map<String, Object>) data.get(i);
	            	HSSFRow datarow = sheet.createRow(i+1);  
	            	
	            	String clientName="";
	            	if(goodsId==Integer.parseInt(map.get("goodsId").toString())){
	            		
	            	}else{
	            		clientName=map.get("clientName").toString();
	            	}
	            	
	                HSSFCell cell0 = datarow.createCell(0);  
	                HSSFRichTextString t0 = new HSSFRichTextString(clientName);  
	                cell0.setCellValue(t0);
	                
	                String goodsLadingClientName="";
	            	if(goodsId==Integer.parseInt(map.get("goodsId").toString())){
	            		
	            	}else{
	            		if(!Common.empty(map.get("goodsLadingClientName")))
	            		goodsLadingClientName=map.get("goodsLadingClientName").toString();
	            	}
	                
	                HSSFCell cell111 = datarow.createCell(1);  
	                HSSFRichTextString t111 = new HSSFRichTextString(goodsLadingClientName);  
	                cell111.setCellValue(t111);
	                
	                
	                
	                
	                String productName="";
	            	if(goodsId==Integer.parseInt(map.get("goodsId").toString())){
	            		
	            	}else{
	            		productName=map.get("productName").toString();
	            	}
	                HSSFCell c1 = datarow.createCell(2);  
	                HSSFRichTextString t1 = new HSSFRichTextString(productName);  
	                c1.setCellValue(t1);  
	                
	                String cargoCode="";
	            	if(goodsId==Integer.parseInt(map.get("goodsId").toString())){
	            		
	            	}else{
	            		goodsId=Integer.parseInt(map.get("goodsId").toString());
	            		cargoCode=map.get("cargoCode").toString();
	            	}
	                HSSFCell c2= datarow.createCell(3);  
	                HSSFRichTextString t2 = new HSSFRichTextString(cargoCode);  
	                c2.setCellValue(t2);  
	                
	                
	                
	                
	                String lName="";
	                if(1==Integer.parseInt(map.get("type").toString())){
	                	lName=(String)map.get("shipName");
	                }else if (2==Integer.parseInt(map.get("type").toString())){
	                	lName=(String)map.get("ladingInClientName");
	                }else{
	                	lName=(String)map.get("ladingClientName");
	                }
	                
	                HSSFCell c3 = datarow.createCell(4);  
	                
	                HSSFRichTextString t3 = new HSSFRichTextString(lName);  
	                c3.setCellValue(t3);   
	                
	                String sType="";
	                int ladingType=Integer.parseInt(map.get("ladingType").toString());
	                switch (Integer.parseInt(map.get("type").toString())) {
					case 1:
						sType="入库";
						break;

					case 2:
						
						
						if(ladingType==1){
							sType="货权调入";
						}else{
							sType="提货权调入";
						}
						
//						sType="调入";
						break;
						
					case 3:
						
						if(ladingType==1){
							sType="货权转移";
						}else{
							sType="提货权转卖";
						}
						
//						sType="调出";
						break;
					case 4:
						sType="封放";
						break;
						
					case 5:
						if(Integer.parseInt(map.get("actualType").toString())>0){
							sType= "发货";
							
						}else{
							sType= "发货待提";
						}
						break;
						
					case 6:
						sType="退回";
						break;
						
					case 7:
						sType="预入库";
						break;
						
					case 9:
						sType="扣损";
						break;
						
					case 10:
						sType="商检&封放";
						break;
						
					case 11:
						sType="清盘";
						break;
						
					case 12:
						sType="复盘";
						break;
					}
	                
	                
	               
	                
	                HSSFCell c4 = datarow.createCell(5);  
	                HSSFRichTextString t4= new HSSFRichTextString(sType);  
	                c4.setCellValue(t4); 
	                
	                HSSFCell c5 = datarow.createCell(6);  
	                HSSFRichTextString t5 = new HSSFRichTextString(new Date(Long.parseLong(map.get("createTime").toString())*1000).toLocaleString());  
		               c5.setCellValue(t5); 
	                
	                String ladingEvidence="";
	                if(!Common.empty(map.get("ladingEvidence"))){
	                	ladingEvidence=map.get("ladingEvidence").toString();
	                }else if(!Common.empty(map.get("nextLadingCode"))){
	                	ladingEvidence=map.get("nextLadingCode").toString();
	                }else{
	                	if(!Common.empty(map.get("ladingCode1")))
	                	ladingEvidence=map.get("ladingCode1").toString();
	                	
	                }
	                
	                
	                
	                HSSFCell c6 = datarow.createCell(7);  
	                HSSFRichTextString t6 = new HSSFRichTextString(ladingEvidence);  
	                 c6.setCellValue(t6); 
	                
	                HSSFCell c7 = datarow.createCell(8);  
	                Double t7 = Double.parseDouble(map.get("goodsChange").toString());  
	                c7.setCellValue(t7);  
	                
	                String surplus="";
//	                if(Integer.parseInt(map.get("type").toString())==5 && Double.parseDouble(map.get("actualNum").toString())==0){
//	                	
//	                }else{
	                	surplus=map.get("surplus").toString();
//	                }
	                
	                HSSFCell c8 = datarow.createCell(9);  
	                Double t8 =  Double.parseDouble(surplus);  
	                c8.setCellValue(t8);  
	                
	                
	                String goodsSave="";
	                if(Integer.parseInt(map.get("type").toString())==5 && Double.parseDouble(map.get("actualNum").toString())==0){
	                	
	                }else{
	                	goodsSave=map.get("goodsSave").toString();
	                }
	                
	                
	                
	                HSSFCell c9 = datarow.createCell(10);
	                Double t9 = Double.parseDouble(goodsSave);  
	                c9.setCellValue(t9);  
	                
	                
	                
	            }
	            
	        
	        	
	        }else if (type==4){
	        	


	    		ExcelUtil excelUtil=null;
	    		excelUtil=new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/fahuoList.xls", new CallBack() {
	    			@Override
	    			public void setSheetValue(HSSFSheet sheet) 
	    			{
	    			int item=3,reginItem,itemChildrenSize;
					HSSFRow itemRow = null;
					Map<String, Object> itemMapData;
					double sumD=0;
					double sumC=0;
					itemRow=sheet.getRow(1);
					 itemMapData=(Map<String, Object>) data.get(0);
					itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("货品:"+itemMapData.get("productName").toString()+"     批号:"+itemMapData.get("cargoCode").toString()));//序号
					
					 if(data!=null&&data.size()>0){
						 int size=data.size();
						for(int i=0;i<size;i++){
							
						 if(i!=0){
							itemRow=sheet.createRow(i+item);
							itemRow.setHeight(sheet.getRow(3).getHeight());
							for(int j=0;j<10;j++){
								itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
							}
						 }else{
							 itemRow=sheet.getRow(item);
						 }
						 //初始化数据
						 itemMapData=(Map<String, Object>) data.get(i);
						 
						
						 
						 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(i+1));//序号
						 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(itemMapData.get("clientName")));
						 itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(itemMapData.get("ladingClientName")));
						 itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(itemMapData.get("serial")));
						 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(itemMapData.get("ladingCode1")));
						 itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(itemMapData.get("ladingEvidence")));
						 itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(itemMapData.get("carshipCode")));
						 itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(new Date(Long.parseLong(itemMapData.get("createTime").toString())*1000).toLocaleString()));
						 itemRow.getCell(8).setCellValue(FormatUtils.getDoubleValue(Common.fixDouble(Double.parseDouble(itemMapData.get("deliverNum").toString()),3)));  
						 sumD+=Common.fixDouble(Double.parseDouble(itemMapData.get("deliverNum").toString()),3);
						 itemRow.getCell(9).setCellValue(FormatUtils.getDoubleValue(Common.fixDouble(-Double.parseDouble(itemMapData.get("goodsChange").toString()),3)));  
						 sumC+=	Common.fixDouble(-Double.parseDouble(itemMapData.get("goodsChange").toString()),3);
						}
						
						itemRow=sheet.createRow(3+size);
						itemRow.setHeight(sheet.getRow(3).getHeight());
						for(int j=0;j<10;j++){
							itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
						}
						
						itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("合计"));//序号
						itemRow.getCell(8).setCellValue(FormatUtils.getDoubleValue(Common.fixDouble(sumD,3)));  
						 itemRow.getCell(9).setCellValue(FormatUtils.getDoubleValue(Common.fixDouble(sumC,3)));  
						 itemRow=sheet.createRow(4+size);
						 itemRow.setHeight(sheet.getRow(3).getHeight());
							for(int j=0;j<10;j++){
								itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
							}
						 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("统计时间:"));//序号
							itemRow.getCell(1).setCellValue(statisticsDto.getsStartTime()+" 到 "+statisticsDto.getsEndTime());  
					 }
					 
//	    				if (region.size()>region1.size()){
//	    						region2=region;
//	    					}else{
//	    						region2=region1;
//	    					}
//	    				 
					 sheet.setForceFormulaRecalculation(true);
	    			}
	    		});
	    		
	    		return excelUtil.getWorkbook();
	    	
	    	
	        	
	        	
	        
	        	
	        }else if(type==5){

	        	 // 产生表格标题行  
		        HSSFRow row = sheet.createRow(0);  
	            HSSFCell cell = row.createCell(0);  
	            HSSFRichTextString text = new HSSFRichTextString("货主");  
	            cell.setCellValue(text);  
	            
	            
	            HSSFCell cell11 = row.createCell(1);  
	            HSSFRichTextString cell112 = new HSSFRichTextString("船名");  
	            cell11.setCellValue(cell112);  
	            
	            
	            HSSFCell shijian = row.createCell(2);  
	            HSSFRichTextString shijiantext = new HSSFRichTextString("到港时间");  
	            shijian.setCellValue(shijiantext); 
	            
	            HSSFCell bianhaocell = row.createCell(3);  
	            HSSFRichTextString bianhao = new HSSFRichTextString("货批号");  
	            bianhaocell.setCellValue(bianhao);  
	            
	            
	            HSSFCell huoticell = row.createCell(4);  
	            HSSFRichTextString huotitext = new HSSFRichTextString("货品");  
	            huoticell.setCellValue(huotitext);  
	            
	            HSSFCell shangjicell = row.createCell(5);  
	            HSSFRichTextString shangjitext = new HSSFRichTextString("贸易类型");  
	            shangjicell.setCellValue(shangjitext);  
	            
	            HSSFCell yuanshicell = row.createCell(6);  
	            HSSFRichTextString yuanshitext = new HSSFRichTextString("计划数量");  
	            yuanshicell.setCellValue(yuanshitext);  
	            
	            HSSFCell tihuocell = row.createCell(7);  
	            HSSFRichTextString tihuotext = new HSSFRichTextString("商检数");  
	            tihuocell.setCellValue(tihuotext);  
	            
	            
	            HSSFCell kaipiao = row.createCell(8);  
	            HSSFRichTextString kaipiao1 = new HSSFRichTextString("开票状态");  
	            kaipiao.setCellValue(kaipiao1);  
	            
	            double sumPlan=0;
	            double sumInspect=0;
	            
	            for(int i = 0 ; i<data.size();i++){
	            	Map<String,Object> map=(Map<String, Object>) data.get(i);
	            	HSSFRow datarow = sheet.createRow(i+1);  
	            	
	            	String clientName="";
	            		clientName=map.get("clientName").toString();
	            	
	                HSSFCell cell0 = datarow.createCell(0);  
	                HSSFRichTextString t0 = new HSSFRichTextString(clientName);  
	                cell0.setCellValue(t0);
	                
	                
	                HSSFCell cell111 = datarow.createCell(1);  
	                HSSFRichTextString t111 = new HSSFRichTextString(map.get("shipName").toString());  
	                cell111.setCellValue(t111);
	                
	                String arrivalTime="";
	                if(!Common.empty(map.get("arrivalStartTime"))){
	                	arrivalTime=map.get("arrivalStartTime").toString().split(" ")[0];
	                }
	                HSSFCell c2 = datarow.createCell(2);  
	                HSSFRichTextString tr2 = new HSSFRichTextString(arrivalTime);  
	                c2.setCellValue(tr2);
	                
	                
	                HSSFCell cell2 = datarow.createCell(3);  
	                HSSFRichTextString t2 = new HSSFRichTextString(!Common.empty(map.get("code"))?map.get("code").toString():"");  
	                cell2.setCellValue(t2);
	            
	                HSSFCell cell3 = datarow.createCell(4);  
	                HSSFRichTextString t3 = new HSSFRichTextString(!Common.empty(map.get("productName"))?map.get("productName").toString():"");  
	                cell3.setCellValue(t3);
	                
	                String taxType="";
	                if(!Common.empty(map.get("taxType").toString())){
	                	if("1".equals(map.get("taxType").toString())){
	                		taxType="内贸";
	                	}
	                	if("2".equals(map.get("taxType").toString())){
	                		taxType="外贸";
	                	}
	                	if("3".equals(map.get("taxType").toString())){
	                		taxType="保税";
	                	}
	                }
	                
	                HSSFCell cell4 = datarow.createCell(5);  
	                HSSFRichTextString t4 = new HSSFRichTextString(taxType);  
	                cell4.setCellValue(t4);
	                
	                HSSFCell cell5 = datarow.createCell(6);  
	                double t5 =!Common.empty(map.get("goodsPlan").toString())?Double.parseDouble(map.get("goodsPlan").toString()):0;
	                cell5.setCellValue(t5);
	                sumPlan+=t5;
	                
	                HSSFCell cell6 = datarow.createCell(7);  
	                double t6 =!Common.empty(map.get("goodsInspect").toString())?Double.parseDouble(map.get("goodsInspect").toString()):0;
	                cell6.setCellValue(t6);
	                sumInspect+=t6;
	                
	                
	                String kaipiaozt="";
	                if(!Common.empty(map.get("billingStatus").toString())){
	                	if("1".equals(map.get("billingStatus").toString())){
	                		kaipiaozt="已开票";
	                	}
	                	else{
	                		kaipiaozt="未开票";
	                	}
	                }
	                HSSFCell cell8 = datarow.createCell(8);  
	                HSSFRichTextString t8 = new HSSFRichTextString(kaipiaozt);  
	                cell8.setCellValue(t8);
	                
	                
	            }
	            HSSFRow datarow = sheet.createRow(data.size()+1);  
	            HSSFCell cell1 = datarow.createCell(0);  
                cell1.setCellValue("合计");
	            HSSFCell cell5 = datarow.createCell(6);  
                cell5.setCellValue(sumPlan);
                
                HSSFCell cell6 = datarow.createCell(7);  
                cell6.setCellValue(sumInspect);
	        
	        }
	        
	        
	        
	        
	    
            
		} catch (Exception re) {
			re.printStackTrace() ;
		}
		return workbook;
		
	}

	@Override
	public OaMsg getGoodsTotal(StatisticsDto statisticsDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始-------list--------------");
			
			List<Map<String, Object>> cargoList = goodsStatisticsDao.getGoodsTotal(statisticsDto);

			for (Map<String, Object> map : cargoList) {
				double goodsSend = 0, goodsTotal = 0,goodsSave = 0;
				if (!Common.empty(map.get("goodsTotal"))) {// 总量
					goodsTotal = Double.parseDouble(map.get("goodsTotal")
							.toString());
				} 
				if (!Common.empty(map.get("goodsSave"))) {// 今日前发量
					goodsSave = Double.parseDouble(map.get("goodsSave")
							.toString());
					map.put("goodsSave", Common.fixDouble(goodsSave, 3));
				}
				
				if (!Common.empty(map.get("goodsSend"))) {// 总发量
					goodsSend = Double.parseDouble(map.get("goodsSend")
							.toString());
				}
				map.put("cargoTotal",Common.fixDouble(goodsTotal - goodsSend, 3));
			}
			oaMsg.getData().addAll(cargoList);
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getEasyLog(StatisticsDto statisticsDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始-------list--------------");
			String ids="";
			if(!Common.isNull(statisticsDto.getType())){
				if(statisticsDto.getType()==21){
					List<Map<String, Object>> goodsIds=goodsStatisticsDao.getLogGoodsIds(statisticsDto);
					

					if(!Common.empty(goodsIds)){

						for(int i=0;i<goodsIds.size();i++){
							if(i==goodsIds.size()-1){
								ids+=goodsIds.get(i).get("goodsId").toString();
							}else{
								
								ids+=goodsIds.get(i).get("goodsId").toString()+",";
							}
						}
						
						statisticsDto.setGoodsIds(ids);
//						statisticsDto.setGoodsIds(goodsIds.get(0).get("goodsId").toString());
					}
					else{
						statisticsDto.setGoodsIds("0");
					}
					
				}
			}
			
			
			
//			List<Map<String, Object>> cargoList1 = goodsStatisticsDao.getLog(statisticsDto,
//					0,0);
////
//			for(int i=0;i<cargoList1.size();i++){
//				
//					if(Integer.parseInt(cargoList1.get(i).get("type").toString())==5){
//						if(i+1<cargoList1.size()){
//							//下一条如果也是发货，这个发货之后就要删除
//							if(Integer.parseInt(cargoList1.get(i).get("goodsId").toString())==Integer.parseInt(cargoList1.get(i+1).get("goodsId").toString())&&Integer.parseInt(cargoList1.get(i+1).get("type").toString())==5){
//								cargoList1.get(i).put("isdelete", 1);
//							}else{
//							}
//							
//						}else{
//						}
//						
//					}
//				
//			}
//			
//			
////			
//			for(int i=0;i<cargoList1.size();i++){
//				
//				if(!Common.empty(cargoList1.get(i).get("isdelete"))){
//					cargoList1.remove(i);
//					i--;	
//				}
//			}
//			int count1=cargoList1.size();
//			
//			System.out.println(startindex);
//			System.out.println(endindex);
			
			List<Map<String, Object>> cargoList = goodsStatisticsDao.getLog(statisticsDto,
					0,0);

//			List<Map<String, Object>> cargoList = goodsStatisticsDao.getLog(statisticsDto,
//					startindex, endindex);


			
			
			
			double goodsTH=0;
			for(int i=0;i<cargoList.size();i++){
				
				if(Integer.parseInt(cargoList.get(i).get("type").toString())==1||Integer.parseInt(cargoList.get(i).get("type").toString())==2){
					List<Map<String, Object>> goodsSave = goodsLogDao.getLastGoodsLog(cargoList.get(i).get("goodsId").toString(),
							statisticsDto.getEndTime());
					cargoList.get(i).put("goodsSave", goodsSave.get(0).get("goodsSave").toString());
				}
				
				
					if(Integer.parseInt(cargoList.get(i).get("type").toString())==5){
						goodsTH+=Double.parseDouble(cargoList.get(i).get("goodsChange").toString());
						if(i+1<cargoList.size()){
							//下一条如果也是发货，这个发货之后就要删除
							if(Integer.parseInt(cargoList.get(i).get("goodsId").toString())==Integer.parseInt(cargoList.get(i+1).get("goodsId").toString())&&Integer.parseInt(cargoList.get(i+1).get("type").toString())==5){
								cargoList.get(i).put("isdelete", 1);
							}else{
								cargoList.get(i).put("goodsChange", goodsTH);
								goodsTH=0;
							}
							
						}else{
							cargoList.get(i).put("goodsChange", goodsTH);
							goodsTH=0;
						}
						
					}
				
			}
			
			for(int i=0;i<cargoList.size();i++){
				if(!Common.empty(cargoList.get(i).get("isdelete"))){
					cargoList.remove(i);
					i--;
				}
			}
			
			
			oaMsg.getData().addAll(cargoList);
//			int count = goodsStatisticsDao.logCount(statisticsDto);
//			oaMsg.getMap().put(Constant.TOTALRECORD, count1 + "");
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getPassCargo(StatisticsDto statisticsDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始-------list--------------");
			
			
			
			List<Map<String, Object>> cargoList = goodsStatisticsDao.getPassCargo(statisticsDto,
					pageView.getStartRecord(), pageView.getMaxresult());

			oaMsg.getData().addAll(cargoList);
			int count = goodsStatisticsDao.getPassCargoCount(statisticsDto);
			oaMsg.getMap().put(Constant.TOTALRECORD, count + "");
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getPassTotal(StatisticsDto statisticsDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始-------list--------------");
			
			
			
			List<Map<String, Object>> logList = goodsStatisticsDao.getPassTotal(statisticsDto);
			
			//获得已开票总量
			List<Map<String, Object>> logList1 = goodsStatisticsDao.getPassKPTotal(statisticsDto);
			logList.get(0).put("KPTotal", logList1.get(0).get("KPTotal").toString());
			
			oaMsg.getData().addAll(logList);
		} catch (Exception re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	public OaMsg addEveryDayStatics(EveryDayStatics everyDayStatics)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
		int id=goodsStatisticsDao.addEveryDayStatics(everyDayStatics);
		Map<String,String> map=new HashMap<String, String>();
		map.put("id", id+"");
		oaMsg.setMap(map);
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
	public OaMsg getEveryDayStatics(EveryDayStatics everyDayStatics)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询");
			List<Map<String,Object>> contractList=goodsStatisticsDao.getEveryDayStatics(everyDayStatics);
			
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
			oaMsg.getData().addAll(contractList);
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}
	
	
	/**
	 * @Title cargoList
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@param pageView
	 * @param:@param msg
	 * @auhor jiahy
	 * @date 2017年7月10日上午9:02:42
	 * @throws
	 */
	@Override
	public void cargoList(StatisticsDto statisticsDto, PageView pageView,
			OaMsg msg) {
		 List<Map<String,Object>> dataList = goodsStatisticsDao.cargoList(statisticsDto,pageView.getStartRecord(),pageView.getMaxresult());
		 
		 for(int i =0,len = dataList.size();i<len;i++){
			 statisticsDto.setCargoId(Integer.valueOf(dataList.get(i).get("id").toString()));
			 dataList.get(i).putAll(goodsStatisticsDao.getGoodsSave(statisticsDto));
			 dataList.get(i).put("goodsDeliverCurrent", Common.sub(dataList.get(i).get("goodsCurrent"), dataList.get(i).get("goodsDeliverBefore"), 3));
			 dataList.get(i).put("goodsLeft", Common.sub(dataList.get(i).get("goodsTotal"), dataList.get(i).get("goodsCurrent"), 3));
			 
		 }
		 msg.getData().addAll(dataList);
		 msg.getMap().put(Constant.TOTALRECORD, goodsStatisticsDao.cargoListCount(statisticsDto)+"");
		 
	}

	/**
	 * @Title getCargoListTotal
	 * @Descrption:TODO
	 * @param:@param statisticsDto
	 * @param:@param msg
	 * @auhor jiahy
	 * @date 2017年7月13日下午5:33:57
	 * @throws
	 */
	@Override
	public void getCargoListTotal(StatisticsDto statisticsDto, OaMsg msg) {
		Map<String, Object> map = goodsStatisticsDao.getCargoListTotal(statisticsDto);
		 map.put("goodsTotal", Common.sub(map.get("goodsTotal"), map.get("goodsCurrent"), 3));
		 map.put("goodsben", map.get("goodsBen"));
		 
		 msg.getData().add(map);
	}

	/**
	 * @Title exportCargoListExcel
	 * @Descrption:TODO
	 * @param:@param request
	 * @param:@param sDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2017年7月13日下午6:00:15
	 * @throws
	 */
	@Override
	public HSSFWorkbook exportCargoListExcel(HttpServletRequest request, final StatisticsDto sDto) {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/cargoCollectList.xls", new CallBack() {
			
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				List<Map<String,Object>> dataList = goodsStatisticsDao.cargoList(sDto,0,0);
				Map<String,Object> map;
				HSSFRow itemRow;
				Short itemHeight  = sheet.getRow(1).getHeight();
				
				double sumgoodsInspect=0;
				double sumgoodsTotal=0;
				double sumgoodsSave=0;
				double sumgoodsDeliverBefore=0;
				double sumgoodsDeliverCurrent=0;
				double sumgoodsCurrent=0;
				double sumgoodsLeft=0;
				 for(int i =0,len = dataList.size();i<len;i++){
					 map =  dataList.get(i);
					 sDto.setCargoId(Integer.valueOf(dataList.get(i).get("id").toString()));
					 map.putAll(goodsStatisticsDao.getGoodsSave(sDto));
					 map.put("goodsDeliverCurrent", Common.sub(dataList.get(i).get("goodsCurrent"), dataList.get(i).get("goodsDeliverBefore"), 3));
					 map.put("goodsLeft", Common.sub(dataList.get(i).get("goodsTotal"), dataList.get(i).get("goodsCurrent"), 3));
					 itemRow = sheet.createRow(i+2);
					 itemRow.setHeight(itemHeight);
					 for(int j = 0;j<16;j++){
						 itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
					 }
					 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue(map.get("clientName")));
					 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("cargoCode")));
					 itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("productName")));
					 itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("shipName"))+FormatUtils.getStringValue(map.get("shipRefName")));
					 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
					 itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(map.get("connectNo")).replace("No.", ""));
					 itemRow.getCell(6).setCellValue(FormatUtils.getStringValue(map.get("planNo")).replace("No.", ""));
					 itemRow.getCell(7).setCellValue(FormatUtils.getStringValue(map.get("inboundNo")).replace("No.", ""));
					 itemRow.getCell(8).setCellValue(getStorageType(map.get("storgeType")));
					 itemRow.getCell(9).setCellValue(FormatUtils.getDoubleValue(map.get("goodsInspect")));
					 itemRow.getCell(10).setCellValue(FormatUtils.getDoubleValue(map.get("goodsTotal")));
					 itemRow.getCell(11).setCellValue(FormatUtils.getDoubleValue(map.get("goodsSave")));
					 itemRow.getCell(12).setCellValue(FormatUtils.getDoubleValue(map.get("goodsDeliverBefore")));
					 itemRow.getCell(13).setCellValue(FormatUtils.getDoubleValue(map.get("goodsDeliverCurrent")));
					 itemRow.getCell(14).setCellValue(FormatUtils.getDoubleValue(map.get("goodsCurrent")));
					 itemRow.getCell(15).setCellValue(FormatUtils.getDoubleValue(map.get("goodsLeft")));
					 sumgoodsInspect+=FormatUtils.getDoubleValue(map.get("goodsInspect"));
					  sumgoodsTotal+=FormatUtils.getDoubleValue(map.get("goodsTotal"));
					  sumgoodsSave+=FormatUtils.getDoubleValue(map.get("goodsSave"));
					  sumgoodsDeliverBefore+=FormatUtils.getDoubleValue(map.get("goodsDeliverBefore"));
					  sumgoodsDeliverCurrent+=FormatUtils.getDoubleValue(map.get("goodsDeliverCurrent"));
					  sumgoodsCurrent+=FormatUtils.getDoubleValue(map.get("goodsCurrent"));
					  sumgoodsLeft+=FormatUtils.getDoubleValue(map.get("goodsLeft"));
					 
				 }
				 
				 itemRow=sheet.createRow(2+dataList.size());
				 itemRow.setHeight(sheet.getRow(3).getHeight());
					for(int j=0;j<16;j++){
						itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
					}
					itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("合计"));//序号
					itemRow.getCell(9).setCellValue(FormatUtils.getDoubleValue(sumgoodsInspect));  
					 itemRow.getCell(10).setCellValue(FormatUtils.getDoubleValue(sumgoodsTotal));  
					 itemRow.getCell(11).setCellValue(FormatUtils.getDoubleValue(sumgoodsSave));  
					 itemRow.getCell(12).setCellValue(FormatUtils.getDoubleValue(sumgoodsDeliverBefore));  
					 itemRow.getCell(13).setCellValue(FormatUtils.getDoubleValue(sumgoodsDeliverCurrent)); 
					 itemRow.getCell(14).setCellValue(FormatUtils.getDoubleValue(sumgoodsCurrent));  
					 itemRow.getCell(15).setCellValue(FormatUtils.getDoubleValue(sumgoodsLeft));  
				 
				
			}
		}).getWorkbook();
	}

	public String getStorageType(Object storgeType){
		 if(!Common.empty(storgeType)){
         	
         	switch(Integer.valueOf(storgeType.toString())){
         	case 1:
         		return "一般";
         	case 2:
         		return "保税临租";
         	case 3:
         		return "包罐";
         	case 4:
         		return "临租";
         	case 5:
         		return "通过";
         	default:
         		return "";
         	}
         }else{
        	 return "";
         }
	}
	
	
	
	@Override
	public HSSFWorkbook exportCargoListExcel2(HttpServletRequest request, final StatisticsDto sDto) {
		return new ExcelUtil(request.getSession().getServletContext().getRealPath("")+"/resource/upload/template/cargoHuizong.xls", new CallBack() {
			
			@Override
			public void setSheetValue(HSSFSheet sheet) {
				List<Map<String,Object>> dataList = goodsStatisticsDao.cargoList(sDto,0,0);
				Map<String,Object> map;
				HSSFRow itemRow;
				Short itemHeight  = sheet.getRow(1).getHeight();
				HSSFRow itemRowtitle=sheet.getRow(1);
				itemRowtitle.getCell(0).setCellValue(FormatUtils.getStringValue("货品:"+dataList.get(0).get("productName").toString()));//序号
				double sumgoodsInspect=0;
				double sumgoodsTotal=0;
				double sumgoodsSave=0;
				double sumgoodsDeliverBefore=0;
				double sumgoodsDeliverCurrent=0;
				double sumgoodsCurrent=0;
				double sumgoodsLeft=0;
				 for(int i =0,len = dataList.size();i<len;i++){
					 map =  dataList.get(i);
					 sDto.setCargoId(Integer.valueOf(dataList.get(i).get("id").toString()));
					 map.putAll(goodsStatisticsDao.getGoodsSave(sDto));
					 map.put("goodsDeliverCurrent", Common.sub(dataList.get(i).get("goodsCurrent"), dataList.get(i).get("goodsDeliverBefore"), 3));
					 map.put("goodsLeft", Common.sub(dataList.get(i).get("goodsTotal"), dataList.get(i).get("goodsCurrent"), 3));
					 itemRow = sheet.createRow(i+3);
					 itemRow.setHeight(itemHeight);
					 for(int j = 0;j<13;j++){
						 itemRow.createCell(j).setCellStyle(sheet.getRow(1).getCell(j).getCellStyle());
					 }
					 itemRow.getCell(0).setCellValue(FormatUtils.getDoubleValue(i+1));
					 itemRow.getCell(1).setCellValue(FormatUtils.getStringValue(map.get("clientName")));
					 itemRow.getCell(2).setCellValue(FormatUtils.getStringValue(map.get("productName")));
					 itemRow.getCell(3).setCellValue(FormatUtils.getStringValue(map.get("cargoCode")));
					 itemRow.getCell(4).setCellValue(FormatUtils.getStringValue(map.get("shipName"))+FormatUtils.getStringValue(map.get("shipRefName")));
					 itemRow.getCell(5).setCellValue(FormatUtils.getStringValue(map.get("arrivalTime")));
					 itemRow.getCell(6).setCellValue(FormatUtils.getDoubleValue(map.get("goodsInspect")));
					 itemRow.getCell(7).setCellValue(FormatUtils.getDoubleValue(map.get("goodsTotal")));
					 itemRow.getCell(8).setCellValue(FormatUtils.getDoubleValue(map.get("goodsSave")));
					 itemRow.getCell(9).setCellValue(FormatUtils.getDoubleValue(map.get("goodsDeliverBefore")));
					 itemRow.getCell(10).setCellValue(FormatUtils.getDoubleValue(map.get("goodsDeliverCurrent")));
					 itemRow.getCell(11).setCellValue(FormatUtils.getDoubleValue(map.get("goodsCurrent")));
					 itemRow.getCell(12).setCellValue(FormatUtils.getDoubleValue(map.get("goodsLeft")));
					 
					  sumgoodsInspect+=FormatUtils.getDoubleValue(map.get("goodsInspect"));
					  sumgoodsTotal+=FormatUtils.getDoubleValue(map.get("goodsTotal"));
					  sumgoodsSave+=FormatUtils.getDoubleValue(map.get("goodsSave"));
					  sumgoodsDeliverBefore+=FormatUtils.getDoubleValue(map.get("goodsDeliverBefore"));
					  sumgoodsDeliverCurrent+=FormatUtils.getDoubleValue(map.get("goodsDeliverCurrent"));
					  sumgoodsCurrent+=FormatUtils.getDoubleValue(map.get("goodsCurrent"));
					  sumgoodsLeft+=FormatUtils.getDoubleValue(map.get("goodsLeft"));
					 
				 }
				 
				 itemRow=sheet.createRow(3+dataList.size());
					itemRow.setHeight(sheet.getRow(3).getHeight());
					for(int j=0;j<13;j++){
						itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
					}
					
					itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("合计"));//序号
					itemRow.getCell(6).setCellValue(Common.fixDouble(sumgoodsInspect,3)+"");  
					 itemRow.getCell(7).setCellValue(Common.fixDouble(sumgoodsTotal,3)+"");  
					 itemRow.getCell(8).setCellValue(Common.fixDouble(sumgoodsSave,3)+"");  
					 itemRow.getCell(9).setCellValue(Common.fixDouble(sumgoodsDeliverBefore,3)+"");  
					 itemRow.getCell(10).setCellValue(Common.fixDouble(sumgoodsDeliverCurrent,3)+""); 
					 itemRow.getCell(11).setCellValue(Common.fixDouble(sumgoodsCurrent,3)+"");  
					 itemRow.getCell(12).setCellValue(Common.fixDouble(sumgoodsLeft,3)+"");  
					 
					 itemRow=sheet.createRow(4+dataList.size());
					 itemRow.setHeight(sheet.getRow(3).getHeight());
						for(int j=0;j<13;j++){
							itemRow.createCell(j).setCellStyle(sheet.getRow(3).getCell(j).getCellStyle());
						}
					 itemRow.getCell(0).setCellValue(FormatUtils.getStringValue("统计时间:"));//序号
						itemRow.getCell(1).setCellValue(sDto.getsStartTime()+" 到 "+sDto.getsEndTime());  
				 
				 
				 
				
			}
		}).getWorkbook();
	}
	

}
