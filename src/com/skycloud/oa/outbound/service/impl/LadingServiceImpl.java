package com.skycloud.oa.outbound.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.CargoGoodsDao;
import com.skycloud.oa.inbound.dao.GoodsGroupDao;
import com.skycloud.oa.inbound.dao.GraftingCargoDao;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.GoodsGroup;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dao.GoodsLogDao;
import com.skycloud.oa.outbound.dao.LadingDao;
import com.skycloud.oa.outbound.dto.GoodsDto;
import com.skycloud.oa.outbound.dto.LadingDto;
import com.skycloud.oa.outbound.model.GoodsLog;
import com.skycloud.oa.outbound.model.Lading;
import com.skycloud.oa.outbound.service.LadingService;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class LadingServiceImpl implements LadingService {
	private static Logger LOG = Logger.getLogger(LadingServiceImpl.class);
	@Autowired
	private LadingDao ladingDao;
	@Autowired
	private GoodsGroupDao goodsGroupDao;
	@Autowired
	private CargoGoodsDao cargoGoodsDao;
	@Autowired
	private GoodsLogDao goodsLogDao;
	
	@Autowired
	private GraftingCargoDao graftingCargoDao;

	@Override
	@Log(object=C.LOG_OBJECT.PCS_LADING,type=C.LOG_TYPE.CREATE)
	public OaMsg addLading(Lading lading) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始插入提单");
			lading.setIsFinish(0);
			if(lading.getType()==1){
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String date = format.format(new Date());
					String iNo=date.replace("-", "");
					LOG.debug(iNo);
					String No="No."+iNo;
					Map<String, Object>  NoCount=ladingDao.checkNo(No);
					if(Common.empty(NoCount.get("No"))){
						No=No+"001";
					}else{
						long n=Long.parseLong(NoCount.get("No").toString())+1;
						No="No."+n;
					}
//					String strNo="";
//					if(NoCount<9){
//						strNo="00"+(NoCount+1);
//					}else if(NoCount>=9&&NoCount<99){
//						strNo="0"+(NoCount+1);
//					}else{
//						strNo=""+(NoCount+1);
//					}
//					
//					No=No+strNo;
					lading.setNo(No);
			}
			int id = ladingDao.addLading(lading);
			LOG.debug("插入成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("插入成功");
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("插入失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败", re);
		}
		return oaMsg;

	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_LADING,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteLading(String ladingId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始撤销提单");
			ladingDao.deleteLading(ladingId);
			LOG.debug("开始移除货体组与提单的关联");
			goodsGroupDao.removeLadingFromGoodsGroup(ladingId);
			LOG.debug("成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("撤销成功");
		} catch (RuntimeException re) {
			LOG.error("撤销失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "撤销失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_LADING,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateLading(Lading lading) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("开始更新提单");
			
			
			List<Map<String, Object>> ladingIds = ladingDao.getCheckEndTimeLadingIds(lading.getId());
			if(!Common.empty(ladingIds.get(0).get("ladingIds"))){
				
				List<Map<String, Object>> endTime = ladingDao.getCheckEndTime(ladingIds.get(0).get("ladingIds").toString());
				if (lading.getType()==2){
					if(!Common.empty(endTime.get(0).get("endTime"))){
						long etimeo=Long.parseLong(endTime.get(0).get("endTime").toString());
						if(!Common.empty(lading.getEndTime())){
							System.out.println(lading.getEndTime().getTime());
							if(etimeo*1000<lading.getEndTime().getTime()){
								oaMsg.setCode("1004");
								return oaMsg;
							}
							
						}
						
					}
					
					if(!Common.empty(endTime.get(0).get("isLong"))&&!Common.empty(endTime.get(0).get("count"))){
						
						if(Double.parseDouble(endTime.get(0).get("isLong").toString())!=Double.parseDouble(endTime.get(0).get("count").toString())){
							if(lading.getIsLong()==1){
								oaMsg.setCode("1004");
								return oaMsg;
							}
							
						}
						
					}
					
				}
			}
			
			
			LadingDto ladingDto = new LadingDto();
			ladingDto.setId(lading.getId());
			List<Map<String, Object>> ladingList = ladingDao.getLadingList(
					ladingDto, 0, 0);
			// 提单放行量减了
			if (Double.parseDouble(ladingList.get(0).get("goodsPass").toString()) > Double
					.parseDouble(lading.getGoodsPass())) {
				double goodsPassRemove = Common.fixDouble(Double.parseDouble(ladingList.get(0)
						.get("goodsPass").toString())
						- Double.parseDouble(lading.getGoodsPass()),3);
				List<Map<String, Object>> goodsList = ladingDao
						.getGoodsListByLadingId(lading.getId());
				// 递归减
				doRemove(goodsPassRemove, goodsList, 0, lading.getId());
			}
			// 提单放行量加了
			if (Double.parseDouble(ladingList.get(0).get("goodsPass").toString()) < Double
					.parseDouble(lading.getGoodsPass())) {
				double goodsPassAdd = Common.fixDouble(Double.parseDouble(lading.getGoodsPass())
						- Double.parseDouble(ladingList.get(0).get("goodsPass")
								.toString()),3);
				List<Map<String, Object>> goodsList = ladingDao
						.getGoodsListByLadingId(lading.getId());
				// 递归加
				doAdd(goodsPassAdd, goodsList, 0, lading.getId());
			}
			
			
			
			
			ladingDao.updateLading(lading);
			LOG.debug("更新成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException re) {
			LOG.error("更新失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败", re);
		}
		return oaMsg;
	}

	private void doAdd(double goodsPassAdd, List<Map<String, Object>> goodsList,
			int i, int ladingId) throws OAException {
		Goods goods = new Goods();
		goods.setId(Integer.parseInt(goodsList.get(i).get("id").toString()));
		// 当前可以加的放行量=货体总量-出库放行量
		double canAddPass = Common.fixDouble(Double.parseDouble(goodsList.get(i).get("goodsTotal")
				.toString())
				- Double.parseDouble(goodsList.get(i).get("goodsOutPass")
						.toString()),3);
		if (goodsPassAdd > canAddPass) {
			double finalAdd = Common.fixDouble(Double.parseDouble(goodsList.get(i)
					.get("goodsOutPass").toString())
					+ canAddPass,3);
			goods.setGoodsOutPass(finalAdd + "");
			double left = goodsPassAdd - canAddPass;

			// 货体日志
			GoodsLog goodsLog = new GoodsLog();
			goodsLog.setGoodsId(goods.getId());
			goodsLog.setType(4);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime() / 1000
					+ ""));
			goodsLog.setGoodsSave(Common.fixDouble((Double.valueOf(goodsList.get(i)
					.get("goodsTotal").toString()) - Math.min(
							Double.valueOf(goodsList.get(i).get("goodsOutPass")
							.toString()),
							Double.valueOf(goodsList.get(i).get("goodsInPass")
							.toString())))
					- canAddPass,3));
			goodsLog.setSurplus(Common.fixDouble(Double.valueOf(goodsList.get(i)
					.get("goodsCurrent").toString())
					- goodsLog.getGoodsSave(),3));
			goodsLog.setLadingId(ladingId);
			// goodsLog.setClientId(Integer.parseInt(goodsList.get(i).get("clientId").toString()));
			// goodsLog.setGoodsChange(oldCurrent);
			goodsLogDao.add(goodsLog);

			doAdd(left, goodsList, i + 1, ladingId);
		} else {
			double finalAdd = Common.fixDouble(Double.parseDouble(goodsList.get(i)
					.get("goodsOutPass").toString())
					+ goodsPassAdd,3);
			goods.setGoodsOutPass(finalAdd + "");
			// 货体日志
			GoodsLog goodsLog = new GoodsLog();
			goodsLog.setGoodsId(goods.getId());
			goodsLog.setType(4);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime() / 1000
					+ ""));
			goodsLog.setGoodsSave(Common.fixDouble((Double.valueOf(goodsList.get(i)
					.get("goodsTotal").toString()) - Math.min(
							Double.valueOf(goodsList.get(i).get("goodsOutPass")
							.toString()),
							Double.valueOf(goodsList.get(i).get("goodsInPass")
							.toString())))
					- goodsPassAdd,3));
			goodsLog.setSurplus(Common.fixDouble(Double.valueOf(goodsList.get(i)
					.get("goodsCurrent").toString())
					- goodsLog.getGoodsSave(),3));
			goodsLog.setLadingId(ladingId);
			// goodsLog.setClientId(Integer.parseInt(goodsList.get(i).get("clientId").toString()));
			// goodsLog.setGoodsChange(oldCurrent);
			goodsLogDao.add(goodsLog);
		}
		try {
			cargoGoodsDao.updateGoods(goods);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void doRemove(double goodsPassRemove,
			List<Map<String, Object>> goodsList, int i, int ladingId)
			throws OAException {

		Goods goods = new Goods();
		goods.setId(Integer.parseInt(goodsList.get(i).get("id").toString()));
		// 当前可以减的放行量=出库放行量-（总量-当前存量）
		double canRemovePass =Common.fixDouble(Double.valueOf(goodsList.get(i)
				.get("goodsOutPass").toString())
				- (Double.valueOf(goodsList.get(i).get("goodsTotal")
						.toString()) - Double.valueOf(goodsList.get(i)
						.get("goodsCurrent").toString())),3);

		// 如果减的放行量>可减的放行量
		if (goodsPassRemove > canRemovePass) {
			double finalRemove = Common.fixDouble(Double.valueOf(goodsList.get(i)
					.get("goodsOutPass").toString())
					- canRemovePass,3);
			goods.setGoodsOutPass(finalRemove + "");
			double left = Common.fixDouble(goodsPassRemove - canRemovePass,3);

			// 货体日志
			GoodsLog goodsLog = new GoodsLog();
			goodsLog.setGoodsId(goods.getId());
			goodsLog.setType(4);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime() / 1000
					+ ""));
			goodsLog.setGoodsSave(Common.fixDouble((Double.valueOf(goodsList.get(i)
					.get("goodsTotal").toString()) - Math.min(
							Double.valueOf(goodsList.get(i).get("goodsOutPass")
							.toString()),
							Double.valueOf(goodsList.get(i).get("goodsInPass")
							.toString())))
					+ canRemovePass,3));
			goodsLog.setSurplus(Common.fixDouble(Double.valueOf(goodsList.get(i)
					.get("goodsCurrent").toString())
					- goodsLog.getGoodsSave(),3));
			goodsLog.setLadingId(ladingId);
			// goodsLog.setClientId(Integer.parseInt(goodsList.get(i).get("clientId").toString()));
			// goodsLog.setGoodsChange(oldCurrent);
			goodsLogDao.add(goodsLog);

			doRemove(left, goodsList, i + 1, ladingId);
		} else {
			double finalRemove =Common.fixDouble( Double.valueOf(goodsList.get(i)
					.get("goodsOutPass").toString())
					- goodsPassRemove,3);
			goods.setGoodsOutPass(finalRemove + "");

			// 货体日志
			GoodsLog goodsLog = new GoodsLog();
			goodsLog.setGoodsId(goods.getId());
			goodsLog.setType(4);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime() / 1000
					+ ""));
			goodsLog.setGoodsSave(Common.fixDouble((Double.valueOf(goodsList.get(i)
					.get("goodsTotal").toString()) - Math.min(
							Double.valueOf(goodsList.get(i).get("goodsOutPass")
							.toString()),
							Double.valueOf(goodsList.get(i).get("goodsInPass")
							.toString())))
					+ goodsPassRemove,3));
			goodsLog.setSurplus(Common.fixDouble(Double.valueOf(goodsList.get(i)
					.get("goodsCurrent").toString())
					- goodsLog.getGoodsSave(),3));
			goodsLog.setLadingId(ladingId);
			// goodsLog.setClientId(Integer.parseInt(goodsList.get(i).get("clientId").toString()));
			// goodsLog.setGoodsChange(oldCurrent);
			goodsLogDao.add(goodsLog);

		}
		try {
			cargoGoodsDao.updateGoods(goods);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public OaMsg getLadingGoods(int ladingId, OaMsg oaMsg) {
		try {
			List<Map<String, Object>> goodsList = ladingDao
					.getGoodsListByLadingId(ladingId);
			oaMsg.getData().addAll(goodsList);
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
	public OaMsg getLadingList(LadingDto ladingDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			LOG.debug("查询提单列表");
			List<Map<String, Object>> ladingList = ladingDao.getLadingList(ladingDto, pageView.getStartRecord(),pageView.getMaxresult());
			if (!Common.isNull(ladingDto.getId())){
				LOG.debug("查询单个提单的所有货体");
				ladingList.get(0).put(
						"goodsList",
						ladingDao.getGoodsListByLadingId((int) ladingList
								.get(0).get("id")));
			}
//			for(int i=0;i<ladingList.size();i++){
//			ladingList.get(i).putAll(ladingDao.getGoodsWaitByLadingId(ladingList.get(i).get("id").toString()));
//			ladingList.get(i).putAll(ladingDao.getGoodsLossByLadingId(ladingList.get(i).get("id").toString()));
//			}
			
			
			int count = ladingDao.getLadingListCount(ladingDto);
			LOG.debug("查询成功");
			oaMsg.getData().addAll(ladingList);
			oaMsg.setMsg("查询成功");
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage() + "");
			map.put("totalpage", pageView.getTotalpage() + "");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg addLadingGoodsGroup(LadingDto ladingDto) throws OAException {
		// goodsIds(要添加进入的货体)，ladingId(提单id)
		OaMsg oaMsg = new OaMsg();
		int goodsGroupId = 0;
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			LOG.debug("要添加的货体的计算");
			Map<String, Object> res = goodsGroupDao
					.computeLadingGoods(ladingDto);
			LOG.debug("拼装提单货体组");
			GoodsGroup goodsGroup = new GoodsGroup();
			goodsGroup.setLadingid(ladingDto.getId());
			String code = "HTZ"
					+ Common.changeNum((goodsGroupDao
							.getTheSameGoodsGroupCount("HTZ") + 1));
			goodsGroup.setCode(code);
			goodsGroup.setClientid(Integer.valueOf(res.get("clientId")
					.toString()));
			goodsGroup.setProductid(Integer.valueOf(res.get("productId")
					.toString()));
			goodsGroup.setGoodsCurrent(Common.fixDouble(Double.valueOf(res.get("goodsCurrent").toString()), 3)+"");
			goodsGroup.setGoodsIn(Common.fixDouble(Double.valueOf(res.get("goodsIn").toString()), 3)+"");
			goodsGroup.setGoodsInPass(Common.fixDouble(Double.valueOf(res.get("goodsInPass").toString()), 3)+"");
			goodsGroup.setGoodsInspect(Common.fixDouble(Double.valueOf(res.get("goodsInspect").toString()), 3)+"");
			goodsGroup.setGoodsOut("0");
			goodsGroup.setGoodsOutPass(Common.fixDouble(Double.valueOf(res.get("goodsOutPass").toString()), 3)+"");
			goodsGroup.setGoodsTank(Common.fixDouble(Double.valueOf(res.get("goodsTank").toString()), 3)+"");
			goodsGroup.setGoodsTotal(Common.fixDouble(Double.valueOf(res.get("goodsTotal").toString()), 3)+"");
			goodsGroupId = Integer.valueOf(goodsGroupDao.addGoodsGroup(
					goodsGroup).toString());
			Map<String, String> map = new HashMap<String, String>();
			map.put("goodsGroupId", goodsGroupId + "");
			oaMsg.setMap(map);
			// LOG.debug("提交事务");
			// Session session=HibernateUtils.getCurrentSession();
			// if(session!=null){
			// session.getTransaction().commit();
			// }
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}

		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateGoods(LadingDto ladingDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		try {
			LOG.debug("更新原来的货体的货体组id");
			CargoGoodsDto caDto = new CargoGoodsDto();
			caDto.setGoodsIds(ladingDto.getGoodsIds());
			List<Map<String, Object>> ggid = cargoGoodsDao.getGoodsList(caDto,
					0, 0);
			LOG.debug("将是其他货体组的货体从货体组中移除");
			for (int i = 0; i < ggid.size(); i++) {
				if (!Common.empty(ggid.get(i).get("goodsGroupId").toString())
						&& !Common.isNull(ggid.get(i).get("goodsGroupId")
								.toString()))
					goodsGroupDao.removeGoodsFromGoodsGroup(
							Integer.valueOf(ggid.get(i).get("id").toString()),
							Integer.valueOf(ggid.get(i).get("goodsGroupId")
									.toString()));
			}
			goodsGroupDao.updateGoods(ladingDto.getGoodsIds(),
					ladingDto.getGoodsGroupId(), ladingDto.getBuyClientId(),
					ladingDto.getLadingStatus());
			
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			goodsGroupDao.deleteGoodsGroup("" + ladingDto.getGoodsGroupId());
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg updateLadingGoodsGroup(LadingDto ladingDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		String oldids = "";
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			LOG.debug("清空被移除的数据");
			int goodsGroupId = ladingDto.getGoodsGroupId();
			CargoGoodsDto ccDto = new CargoGoodsDto();
			ccDto.setGoodsGroupId(goodsGroupId);
			List<Map<String, Object>> gcid = cargoGoodsDao.getGoodsList(ccDto,
					0, 0);
			for (int i = 0; i < gcid.size(); i++) {
				oldids += Integer.valueOf(gcid.get(i).get("id").toString())
						+ ",";
				// goodsGroupDao.removeGoodsFromGoodsGroup(Integer.valueOf(gcid.get(i).get("id").toString()),Integer.valueOf(gcid.get(i).get("goodsGroupId").toString()));
			}
			oldids = oldids.substring(0, oldids.length() - 1);
			String removeids = Common.removeSameItem(oldids,
					ladingDto.getGoodsIds());
			LOG.debug("从货体组清除被移除的ids");
			if (!Common.empty(removeids)) {

				goodsGroupDao
						.removeGoodsFromGoodsGroup(removeids, goodsGroupId);
			}

			LOG.debug("获取是货体组的货体");
			CargoGoodsDto caDto = new CargoGoodsDto();
			caDto.setGoodsIds(Common.removeSameItem(ladingDto.getGoodsIds(),
					oldids));
			List<Map<String, Object>> ggid = cargoGoodsDao.getGoodsList(caDto,
					0, 0);
			for (int i = 0; i < ggid.size(); i++) {
				if (!Common.empty(ggid.get(i).get("goodsGroupId").toString())
						&& !Common.isNull(ggid.get(i).get("goodsGroupId")
								.toString()))
					goodsGroupDao.removeGoodsFromGoodsGroup(
							Integer.valueOf(ggid.get(i).get("id").toString()),
							Integer.valueOf(ggid.get(i).get("goodsGroupId")
									.toString()));
			}
			if (!Common.empty(ladingDto.getGoodsIds()))
				goodsGroupDao.updateGoodsToGoodsGroup(ladingDto.getGoodsIds(),
						goodsGroupId, ladingDto.getBuyClientId(),
						ladingDto.getLadingStatus());
		} catch (RuntimeException re) {
			LOG.error("查询失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.DELETE)
	public OaMsg removeLadingGoods(String ids) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			CargoGoodsDto caDto = new CargoGoodsDto();
			caDto.setGoodsIds(ids);
			List<Map<String, Object>> goodsList = cargoGoodsDao.getGoodsList(
					caDto, 0, 0);
			for (int i = 0; i < goodsList.size(); i++) {
				CargoGoodsDto cgDto = new CargoGoodsDto();
				cgDto.setGoodsId(Integer.valueOf(goodsList.get(i)
						.get("sourceGoodsId").toString()));
				Goods goods = new Goods();
				goods.setId(Integer.valueOf(goodsList.get(i)
						.get("sourceGoodsId").toString()));
				Map<String, Object> oldgoods = cargoGoodsDao.getGoodsList(
						cgDto, 0, 0).get(0);
				double oldCurrent = Double.valueOf(oldgoods.get("goodsCurrent")
						.toString());
				goods.setGoodsCurrent(Common.fixDouble((Double.valueOf(goodsList.get(i)
						.get("goodsCurrent").toString()) + oldCurrent),3)
						+ "");
				double out = Double.valueOf(oldgoods.get("goodsOut").toString());
				goods.setGoodsOut(Common.fixDouble((out - Double.valueOf(goodsList.get(i)
						.get("goodsOut").toString())),3)
						+ "");
				cargoGoodsDao.updateGoods(goods);
			}
			cargoGoodsDao.deleteGoods(ids);
		} catch (RuntimeException re) {
			LOG.error("移除失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "移除失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_LADING,type=C.LOG_TYPE.RECOVER)
	public OaMsg backLading(String ladingId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> goodsList = ladingDao
					.getGoodsListByLadingId(Integer.parseInt(ladingId));
			
			//检验货体是否有待提的，如果有则不能退回
			LadingDto ladingDto=new LadingDto();
			ladingDto.setId(Integer.parseInt(ladingId));
			
			Map<String, Object> ladingInfo = ladingDao
					.getLadingList(ladingDto, 0, 0).get(0);
			
			
			Map<String, Object> goodsWait =ladingDao.getGoodsWaitByLadingId(ladingId);
				if(Double.valueOf(goodsWait.get("goodsWait").toString())>0){
					oaMsg.setCode(Constant.SYS_CODE_DISABLED);
					return oaMsg;
				}
				
				
			for (int i = 0; i < goodsList.size(); i++) {
				
				if(Double.valueOf(goodsList.get(i)
						.get("goodsCurrent").toString())!=0){
					
					
					CargoGoodsDto cgDto = new CargoGoodsDto();
					// 来源货体
					cgDto.setGoodsId(Integer.valueOf(goodsList.get(i)
							.get("sourceGoodsId").toString()));
					Goods goods = new Goods();
					goods.setId(Integer.valueOf(goodsList.get(i)
							.get("sourceGoodsId").toString()));
					// 获得来源货体
					Map<String, Object> oldgoods = cargoGoodsDao.getGoodsList(
							cgDto, 0, 0).get(0);
					// 来源货体现存量
					double oldCurrent = Double.valueOf(oldgoods.get("goodsCurrent")
							.toString());
					// 来源货体现存量改变
					goods.setGoodsCurrent(Common.fixDouble((Double.valueOf(goodsList.get(i)
							.get("goodsCurrent").toString()) + oldCurrent),3)
							+ "");
					double out = Double.valueOf(oldgoods.get("goodsOut").toString());
					goods.setGoodsOut(Common.fixDouble((out - Double.valueOf(goodsList.get(i)
							.get("goodsCurrent").toString())),3)
							+ "");
					
//				double outPass = Double.valueOf(oldgoods.get("goodsOutPass").toString());
//				
//				goods.setGoodsOutPass(Common.fixDouble((outPass - Double.valueOf(goodsList.get(i)
//						.get("goodsCurrent").toString())),3)
//						+ "");
					
					cargoGoodsDao.updateGoods(goods);
					
					// 更新被拆分货体的提单调出量
					
					LadingDto oldLadingDto = new LadingDto();
					oldLadingDto.setGoodsId(goods.getId());
					List<Map<String, Object>> oldLadingList = ladingDao
							.getLadingByGoodsId(oldLadingDto);
					if (oldLadingList.size() != 0) {
						Map<String, Object> oldLading = oldLadingList.get(0);
						Lading oldUpLading = new Lading();
						oldUpLading.setId(Integer.parseInt(oldLading.get("id")
								.toString()));
						// 原提单的调出量-
						double goodsOut = oldLading.get("goodsOut") == null ? 0
								: Double.valueOf(oldLading.get("goodsOut")
										.toString());
						oldUpLading.setGoodsOut(Common.fixDouble((goodsOut - Double.valueOf(goodsList
								.get(i).get("goodsCurrent").toString())),3)
								+ "");
						ladingDao.updateLading(oldUpLading);
					}
					
					Goods nowGoods = new Goods();
					nowGoods.setGoodsCurrent("0");
					nowGoods.setId(Integer.valueOf(goodsList.get(i).get("id")
							.toString()));
					nowGoods.setGoodsIn(Common.fixDouble((Double.valueOf(goodsList.get(i)
							.get("goodsIn").toString()))
							- Double.valueOf(goodsList.get(i).get("goodsCurrent")
									.toString()),3) + "");
					nowGoods.setGoodsTotal(nowGoods.getGoodsIn());
					nowGoods.setGoodsOutPass(nowGoods.getGoodsTotal());
					nowGoods.setGoodsInPass(nowGoods.getGoodsTotal());
					cargoGoodsDao.updateGoods(nowGoods);
					
					// 原货体日志
					GoodsLog goodsLog = new GoodsLog();
					goodsLog.setGoodsId(cgDto.getGoodsId());
					goodsLog.setType(3);
					goodsLog.setCreateTime(Long.parseLong(new Date().getTime()
							/ 1000 + ""));
					goodsLog.setGoodsSave(Common.fixDouble((Double.valueOf(oldgoods
							.get("goodsTotal").toString()) - Math.min(
									Double.valueOf(oldgoods.get("goodsOutPass").toString()),
									Double.valueOf(oldgoods.get("goodsInPass").toString()))),3));
					goodsLog.setSurplus(Common.fixDouble(Double.valueOf(goods.getGoodsCurrent())
							- goodsLog.getGoodsSave(),3));
					goodsLog.setClientId(Integer.parseInt(goodsList.get(i)
							.get("clientId").toString()));
					goodsLog.setGoodsChange(Double.valueOf(goodsList.get(i)
							.get("goodsCurrent").toString()));
					if (oldLadingList.size() != 0) {
						goodsLog.setLadingId(Integer.parseInt(oldLadingList.get(0)
								.get("id").toString()));
						
					}
					LadingDto ladDto=new LadingDto();
					ladDto.setId(Integer.parseInt(ladingId));
					Map<String, Object> ladingData=ladingDao.getLadingList(ladDto, 0, 0).get(0);
					
					goodsLog.setLadingType(Integer.parseInt(ladingData
							.get("type").toString()));
					goodsLog.setNextLadingId(Integer.parseInt(ladingId));
					goodsLog.setNextGoodsId(Integer.valueOf(goodsList.get(i)
							.get("id").toString()));
					goodsLog.setLadingClientId(Integer.parseInt(ladingInfo.get("receiveClientId").toString()));
					goodsLogDao.add(goodsLog);
					
					
					
					// 被货体日志
					GoodsLog goodsLog1 = new GoodsLog();
					goodsLog1.setGoodsId(Integer.valueOf(goodsList.get(i)
							.get("id").toString()));
					goodsLog1.setType(2);
					goodsLog1.setCreateTime(Long.parseLong(new Date().getTime()
							/ 1000 + ""));
					goodsLog1.setClientId(Integer.parseInt(goodsList.get(i)
							.get("clientId").toString()));
					goodsLog1.setGoodsSave(0);
					goodsLog1.setSurplus(0);
					goodsLog1.setGoodsChange(-Double.valueOf(goodsList.get(i)
							.get("goodsCurrent").toString()));
					goodsLog1.setLadingId(Integer.parseInt(ladingId));
					goodsLog1.setLadingClientId(Integer.parseInt(ladingInfo.get("clientId").toString()));
					goodsLog1.setLadingType(Integer.parseInt(ladingInfo.get("type").toString()));
					goodsLogDao.add(goodsLog1);
				}
				
				
				
				

			}
			ladingDao.updateLadingStatus(ladingId, 2);
			
			ladingDao.repairLading(ladingId);
			
		} catch (RuntimeException re) {
			LOG.error("退回失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "退回失败", re);
		}
		return oaMsg;
	}

	/**
	 * 提单扣损
	 * 
	 * @param ladingId
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_LADING,type=C.LOG_TYPE.LOSS)
	public OaMsg lossLading(String ladingId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> goodsList = ladingDao
					.getGoodsListByLadingId(Integer.parseInt(ladingId));
			
			//检验货体是否有待提的，如果有则不能退回
			LadingDto ladingDto=new LadingDto();
			ladingDto.setId(Integer.parseInt(ladingId));
			
			Map<String, Object> goodsWait =ladingDao.getGoodsWaitByLadingId(ladingId);
				if(Double.valueOf(goodsWait.get("goodsWait").toString())>0){
					oaMsg.setCode(Constant.SYS_CODE_DISABLED);
					return oaMsg;
				}
				
				
			
			
			for (int i = 0; i < goodsList.size(); i++) {
				// 货体日志
				GoodsLog goodsLog = new GoodsLog();
				goodsLog.setGoodsId(Integer.valueOf(goodsList.get(i).get("id")
						.toString()));
				goodsLog.setType(9);
				goodsLog.setCreateTime(Long.parseLong(new Date().getTime()
						/ 1000 + ""));
				goodsLog.setGoodsChange(-Double.valueOf(goodsList.get(i)
						.get("goodsCurrent").toString()));
				goodsLogDao.add(goodsLog);
				
				
				
				Goods nowGoods = new Goods();
				nowGoods.setGoodsCurrent("0");
				nowGoods.setId(Integer.valueOf(goodsList.get(i).get("id")
						.toString()));
				cargoGoodsDao.updateGoods(nowGoods);
				
				
				
				
				
			}
			ladingDao.updateLadingStatus(ladingId, 2);
		} catch (RuntimeException re) {
			LOG.error("扣损失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "扣损失败", re);
		}
		return oaMsg;
	}

	/**
	 * 撤销单个货体
	 * 
	 * @param id
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.DELETE)
	public OaMsg removeGood(String id, String ladingId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			LadingDto ladDto=new LadingDto();
			ladDto.setId(Integer.parseInt(ladingId));
			Map<String, Object> ladingData=ladingDao.getLadingList(ladDto, 0, 0).get(0);
			
			CargoGoodsDto cgd = new CargoGoodsDto();
			cgd.setGoodsId(Integer.parseInt(id));
			List<Map<String, Object>> goodsList = cargoGoodsDao.getGoodsList(
					cgd, 0, 0);
			CargoGoodsDto cgDto = new CargoGoodsDto();
			// 来源货体
			cgDto.setGoodsId(Integer.valueOf(goodsList.get(0)
					.get("sourceGoodsId").toString()));
			Goods goods = new Goods();
			goods.setId(Integer.valueOf(goodsList.get(0).get("sourceGoodsId")
					.toString()));
			// 获得来源货体
			Map<String, Object> oldgoods = cargoGoodsDao.getGoodsList(cgDto, 0,
					0).get(0);
			// 来源货体现存量
			double oldCurrent = Double.valueOf(oldgoods.get("goodsCurrent")
					.toString());
			// 来源货体现存量改变
			goods.setGoodsCurrent(Common.fixDouble((Double.valueOf(goodsList.get(0)
					.get("goodsCurrent").toString()) + oldCurrent),3)
					+ "");
			double out = Double.valueOf(oldgoods.get("goodsOut").toString());
			goods.setGoodsOut(Common.fixDouble((out - Double.valueOf(goodsList.get(0)
					.get("goodsCurrent").toString())),3)
					+ "");
			cargoGoodsDao.updateGoods(goods);
			cargoGoodsDao.deleteGoods(id);

			LadingDto ladingDto = new LadingDto();
			ladingDto.setId(Integer.parseInt(ladingId));
			List<Map<String, Object>> ladinglist = ladingDao.getLadingList(
					ladingDto, 0, 0);
			Lading lading = new Lading();
			lading.setId(Integer.parseInt(ladingId));
			lading.setGoodsTotal(Common.fixDouble((Double.valueOf(ladinglist.get(0)
					.get("goodsTotal").toString()) - Double.valueOf(goodsList
					.get(0).get("goodsCurrent").toString())),3)
					+ "");
			lading.setGoodsPass(Common.fixDouble((Double.valueOf(ladinglist.get(0)
					.get("goodsPass").toString()) - Double.valueOf(goodsList
					.get(0).get("goodsOutPass").toString())),3)
					+ "");
			ladingDao.updateLading(lading);

			List<Map<String, Object>> upGoodsList = ladingDao
					.getGoodsListByLadingId(Integer.parseInt(ladingId));
			for (int i = 0; i < upGoodsList.size(); i++) {
				Goods upGoods = new Goods();
				upGoods.setId(Integer.valueOf(upGoodsList.get(i).get("id")
						.toString()));
				upGoods.setCode(ladinglist.get(0).get("code").toString() + "-"
						+ (i + 1) + "/" + upGoodsList.size());
				cargoGoodsDao.updateGoods(upGoods);
			}

			// 更新被拆分货体的提单调出量

			LadingDto oldLadingDto = new LadingDto();
			oldLadingDto.setGoodsId(goods.getId());
			List<Map<String, Object>> oldLadingList = ladingDao
					.getLadingByGoodsId(oldLadingDto);
			if (oldLadingList.size() != 0) {
				Map<String, Object> oldLading = oldLadingList.get(0);
				Lading oldUpLading = new Lading();
				oldUpLading.setId(Integer.parseInt(oldLading.get("id")
						.toString()));
				// 原提单的调出量-
				double goodsOut = oldLading.get("goodsOut") == null ? 0 : Double
						.valueOf(oldLading.get("goodsOut").toString());
				oldUpLading.setGoodsOut(Common.fixDouble((goodsOut - Double.valueOf(goodsList
						.get(0).get("goodsCurrent").toString())),3)
						+ "");
				ladingDao.updateLading(oldUpLading);
			}

			// 原货体调出日志删除
			goodsLogDao.deleteGoodsLogByNextGoodsId(id);
			graftingCargoDao.repairLog(cgDto.getGoodsId());
//			GoodsLog goodsLog = new GoodsLog();
//			goodsLog.setGoodsId(cgDto.getGoodsId());
//			goodsLog.setType(3);
//			goodsLog.setCreateTime(Long.parseLong(new Date().getTime() / 1000
//					+ ""));
//			goodsLog.setGoodsSave(Common.fixDouble((Double.valueOf(oldgoods.get("goodsTotal")
//					.toString()) - Math.min(
//					Double.valueOf(oldgoods.get("goodsOutPass").toString()),
//					Double.valueOf(oldgoods.get("goodsInPass").toString()))),3));
//			goodsLog.setSurplus(Double.valueOf(goods.getGoodsCurrent())
//					- goodsLog.getGoodsSave());
//			goodsLog.setClientId(Integer.parseInt(goodsList.get(0)
//					.get("clientId").toString()));
//			goodsLog.setGoodsChange(Double.valueOf(goodsList.get(0)
//					.get("goodsCurrent").toString()));
//			if (oldLadingList.size() != 0) {
//				goodsLog.setLadingId(Integer.parseInt(oldLadingList.get(0)
//						.get("id").toString()));
//			}
//			goodsLog.setLadingType(Integer.parseInt(ladingData
//					.get("type").toString()));
//			goodsLog.setNextLadingId(Integer.parseInt(ladingId));
//			goodsLog.setNextGoodsId(Integer.parseInt(id));
//			goodsLogDao.add(goodsLog);

		} catch (RuntimeException re) {
			LOG.error("撤销失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "撤销失败", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getGoodsTree(GoodsDto goodsDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
//			List<GoodsDto> goodslist = new ArrayList<GoodsDto>();
//			goodslist.add(ladingDao.getTreeGoodsList(goodsDto));
			
			//查询该货体所在的所有原号
			List<Map<String, Object>> rootIds=ladingDao.getTreeRootIds(goodsDto);
			String rIds=rootIds.get(0).get("rootIds")==null?"":rootIds.get(0).get("rootIds").toString();
			List<Map<String, Object>> cargoLZ=ladingDao.getTreeGoodsList(rIds,goodsDto);
			List<Map<String, Object>> tCargoLZ=new ArrayList<Map<String,Object>>();
			for(int i=0;i<cargoLZ.size();i++){
				if("0".equals(cargoLZ.get(i).get("sourceGoodsId").toString())&&"0".equals(cargoLZ.get(i).get("rootGoodsId").toString())){
					tCargoLZ.add(cargoLZ.get(i));
					getGoodsChildren(tCargoLZ,cargoLZ,i);
				}
			}
			
//			oaMsg.getData().addAll(ladingDao.getTreeGoodsList(goodsDto));
			oaMsg.getData().addAll(tCargoLZ);
			oaMsg.setMsg("查询成功");
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", "0");
			map.put("totalpage", "1");
			map.put("totalRecord", "1");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_GOODS,type=C.LOG_TYPE.UPDATE)
	public OaMsg goodsPass(String ladingId, String goodsId, String count,String contractId,String goodsTotal)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			// 更新货体
			CargoGoodsDto goodsDto = new CargoGoodsDto();
			goodsDto.setGoodsId(Integer.valueOf(goodsId));
			List<Map<String, Object>> goodsList = cargoGoodsDao.getGoodsList(
					goodsDto, 0, 0);
			
			LadingDto ladingDto = new LadingDto();
			ladingDto.setId(Integer.parseInt(ladingId));
			List<Map<String, Object>> ladingList = ladingDao.getLadingList(
					ladingDto, 0, 0);
			
			double oGoodsTotal=Double.valueOf(goodsList.get(0)
					.get("goodsTotal").toString());
			double oGoodsCurrent=Double.valueOf(goodsList.get(0)
					.get("goodsCurrent").toString());
			
			Goods goods = new Goods();
			goods.setId(Integer.valueOf(goodsId));
			if(!Common.empty(count)&&Double.parseDouble(count)!=0){
				//如果放行量大于货体总量，则报1003返回
				if(Double.valueOf(goodsList.get(0)
						.get("goodsTotal").toString())!=Double.parseDouble(goodsTotal)){
					if(Common.fixDouble((Double.valueOf(goodsList.get(0)
							.get("goodsOutPass").toString()) + Double.valueOf(count)),3)>Double.parseDouble(goodsTotal)){
						oaMsg.setCode(Constant.SYS_CODE_DISABLED);
						return oaMsg;
					}else{
						
						goods.setGoodsOutPass(Common.fixDouble((Double.valueOf(goodsList.get(0)
								.get("goodsOutPass").toString()) + Double.valueOf(count)),3)
								+ "");
						goods.setGoodsInPass(Common.fixDouble((Double.valueOf(goodsList.get(0)
								.get("goodsOutPass").toString()) + Double.valueOf(count)),3)
								+ "");
						
					}
				}else{
					if(Common.fixDouble((Double.valueOf(goodsList.get(0)
							.get("goodsOutPass").toString()) + Double.valueOf(count)),3)>Double.valueOf(goodsList.get(0)
									.get("goodsTotal").toString())){
						oaMsg.setCode(Constant.SYS_CODE_DISABLED);
						return oaMsg;
					}else{
						
						goods.setGoodsOutPass(Common.fixDouble((Double.valueOf(goodsList.get(0)
								.get("goodsOutPass").toString()) + Double.valueOf(count)),3)
								+ "");
						goods.setGoodsInPass(Common.fixDouble((Double.valueOf(goodsList.get(0)
								.get("goodsOutPass").toString()) + Double.valueOf(count)),3)
								+ "");
						
					}
				}
				
				
				
			}
			if(!Common.empty(contractId)){
				goods.setContractId(Integer.parseInt(contractId));
			}
			
			double gCount=Common.fixDouble(Double.parseDouble(goodsTotal)-Double.valueOf(goodsList.get(0)
					.get("goodsTotal").toString()),3);
			//如果总量改过
			if(Double.valueOf(goodsList.get(0)
					.get("goodsTotal").toString())!=Double.parseDouble(goodsTotal)){
				
				CargoGoodsDto goodsDto1 = new CargoGoodsDto();
				goodsDto1.setGoodsId(Integer.valueOf(goodsList.get(0).get("sourceGoodsId").toString()));
				List<Map<String, Object>> goodsList1 = cargoGoodsDao.getGoodsList(
						goodsDto1, 0, 0);
				
				double canChange=Common.fixDouble(Double.parseDouble(goodsList1.get(0).get("goodsCurrent").toString())-(Double.parseDouble(goodsList1.get(0).get("goodsTotal").toString())-Double.parseDouble(goodsList1.get(0).get("goodsOutPass").toString())),3);
				
				
				
				if(gCount>canChange){
					oaMsg.setCode("1004");
					return oaMsg;
				}
				
				goods.setGoodsTotal(Common.fixDouble(Double.parseDouble(goodsTotal),3)+"");
				goods.setGoodsCurrent(Common.fixDouble((Double.valueOf(goodsList.get(0)
					.get("goodsCurrent").toString())+gCount),3)+"");
				oGoodsTotal=Common.fixDouble(Double.parseDouble(goodsTotal),3);
				oGoodsCurrent=Double.parseDouble(goods.getGoodsCurrent());
//				goodsLogDao.updateGoodsInOut(goodsTotal,goods.getId());
				
				
				Map<String, Object> oldgoods = cargoGoodsDao.getGoodsList(
						goodsDto1, 0, 0).get(0);
				
				
				// 原货体日志
				GoodsLog goodsLog = new GoodsLog();
				goodsLog.setGoodsId(Integer.parseInt(goodsList.get(0)
						.get("sourceGoodsId").toString()));
				goodsLog.setType(3);
				goodsLog.setCreateTime(Long.parseLong(new Date().getTime()
						/ 1000 + ""));
				goodsLog.setGoodsSave(Common.fixDouble((Double.valueOf(oldgoods
						.get("goodsTotal").toString()) - Math.min(
								Double.valueOf(oldgoods.get("goodsOutPass").toString()),
								Double.valueOf(oldgoods.get("goodsInPass").toString()))),3));
				goodsLog.setSurplus(Common.fixDouble(Double.valueOf(oldgoods
						.get("goodsCurrent").toString())-(Double.valueOf(oldgoods
								.get("goodsTotal").toString())-Double.valueOf(oldgoods
										.get("goodsOutPass").toString()))
						-gCount,3));
				goodsLog.setClientId(Integer.parseInt(goodsList.get(0)
						.get("clientId").toString()));
				goodsLog.setGoodsChange(Common.fixDouble(-gCount,3));
				if (!Common.empty(oldgoods.get("zladingId"))) {
					goodsLog.setLadingId(Integer.parseInt(oldgoods.get("zladingId").toString()));
					//修复上级提单
					
					LadingDto ladingDto1 = new LadingDto();
					ladingDto1.setId(Integer.parseInt(oldgoods.get("zladingId").toString()));
					List<Map<String, Object>> ladingList1 = ladingDao.getLadingList(
							ladingDto1, 0, 0);
					
					Lading lading1 = new Lading();
					lading1.setId(Integer.parseInt(oldgoods.get("zladingId").toString()));
					lading1.setGoodsOut(""+Common.fixDouble(Double.parseDouble(ladingList1.get(0).get("goodsOut").toString())+gCount,3));
					ladingDao.updateLading(lading1);
				}
				
				goodsLog.setLadingType(Integer.parseInt(ladingList.get(0).get("type").toString()));
				goodsLog.setNextLadingId(Integer.parseInt(ladingId));
				goodsLog.setNextGoodsId(Integer.valueOf(goodsList.get(0)
						.get("id").toString()));
				goodsLog.setLadingClientId(Integer.parseInt(ladingList.get(0).get("receiveClientId").toString()));
				goodsLogDao.add(goodsLog);
				
				
				
				// 被货体日志
				GoodsLog goodsLog1 = new GoodsLog();
				goodsLog1.setGoodsId(Integer.valueOf(goodsList.get(0)
						.get("id").toString()));
				goodsLog1.setType(2);
				goodsLog1.setCreateTime(Long.parseLong(new Date().getTime()
						/ 1000 + ""));
				goodsLog1.setClientId(Integer.parseInt(goodsList.get(0)
						.get("clientId").toString()));
				goodsLog1.setGoodsSave(Common.fixDouble((oGoodsTotal - Math.min(
						Double.valueOf(goodsList.get(0).get("goodsOutPass")
						.toString()),
						Double.valueOf(goodsList.get(0).get("goodsInPass")
						.toString())))
				- Double.valueOf(count),3));
				goodsLog1.setSurplus(Common.fixDouble(oGoodsCurrent
						- goodsLog1.getGoodsSave(),3));
				goodsLog1.setGoodsChange(Common.fixDouble(gCount,3));
				goodsLog1.setLadingId(Integer.parseInt(ladingId));
				goodsLog1.setLadingClientId(Integer.parseInt(ladingList.get(0).get("clientId").toString()));
				goodsLog1.setLadingType(Integer.parseInt(ladingList.get(0).get("type").toString()));
				goodsLogDao.add(goodsLog1);
				
				//修复上级货体
				graftingCargoDao.repairGoods(Integer.parseInt(goodsList.get(0)
						.get("sourceGoodsId").toString()));
				
				
				
			}
			
			
			
			cargoGoodsDao.updateGoods(goods);
			
			
			

			
			
			// 货体日志
			if(!Common.empty(count)&&Double.parseDouble(count)!=0){
			GoodsLog goodsLog = new GoodsLog();
			goodsLog.setGoodsId(Integer.valueOf(goodsId));
			goodsLog.setType(4);
			goodsLog.setCreateTime(Long.parseLong(new Date().getTime() / 1000
					+ ""));
			goodsLog.setGoodsSave(Common.fixDouble((oGoodsTotal - Math.min(
							Double.valueOf(goodsList.get(0).get("goodsOutPass")
							.toString()),
							Double.valueOf(goodsList.get(0).get("goodsInPass")
							.toString())))
					- Double.valueOf(count),3));
			goodsLog.setSurplus(Common.fixDouble(oGoodsCurrent
					- goodsLog.getGoodsSave(),3));
			goodsLog.setLadingId(Integer.parseInt(ladingId));
			goodsLogDao.add(goodsLog);
			}
			graftingCargoDao.repairGoods(Integer.parseInt(goodsId));
			
			// 更新提单
			if((!Common.empty(count)&&Double.parseDouble(count)!=0)||Double.valueOf(goodsList.get(0)
					.get("goodsTotal").toString())!=Double.parseDouble(goodsTotal)){
			
			Lading lading = new Lading();
			lading.setId(Integer
					.valueOf(ladingList.get(0).get("id").toString()));
			lading.setGoodsPass(Common.fixDouble((Double.valueOf(ladingList.get(0)
					.get("goodsPass").toString()) + Double.valueOf(count)),3)
					+ "");
			lading.setGoodsTotal(Common.fixDouble((Double.valueOf(ladingList.get(0)
					.get("goodsTotal").toString()) + Double.valueOf(gCount)),3)
					+ "");
			ladingDao.updateLading(lading);
			}

		} catch (RuntimeException re) {
			LOG.error("操作失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "扣损失败", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getGoodsLS(String goodsId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			// List<GoodsLSDto> goodsLSList=new ArrayList<GoodsLSDto>();
			// //发货记录
			// List<Map<String,Object>>
			// invoiceList=ladingDao.getGoodsInvoice(goodsId);
			// for(int i=0;i<invoiceList.size();i++){
			// GoodsLSDto goodsLSDto =new GoodsLSDto();
			// goodsLSDto.setCarshipNo(invoiceList.get(i).get("carshipCode").toString());
			// goodsLSDto.setChangeCount("-"+invoiceList.get(i).get("actualNum").toString());
			// goodsLSDto.setClientName(invoiceList.get(i).get("clientName").toString());
			// goodsLSDto.setCurrentCount(invoiceList.get(i).get("surplus").toString());
			// goodsLSDto.setDeliverNo(invoiceList.get(i).get("deliverNo").toString());
			// goodsLSDto.setLadingId(invoiceList.get(i).get("ladingCode").toString());
			// goodsLSDto.setSaveCount(Float.valueOf(invoiceList.get(i).get("goodsTotal").toString())-Float.valueOf(invoiceList.get(i).get("pass").toString())+"");
			// goodsLSDto.setTime(new
			// Date(invoiceList.get(i).get("createTime").toString()).getTime());
			// goodsLSDto.setType("提货");
			//
			// goodsLSList.add(goodsLSDto);
			// }
			//
			// //调拨记录
			// CargoGoodsDto agDto=new CargoGoodsDto();
			// agDto.setSourceGoodsId(Integer.valueOf(goodsId));
			// List<Map<String,Object>>
			// goodsList=cargoGoodsDao.getGoodsList(agDto, 0, 0);
			// for(int i=0;i<goodsList.size();i++){
			// GoodsLSDto goodsLSDto =new GoodsLSDto();
			// goodsLSDto.setCarshipNo("");
			// goodsLSDto.setChangeCount("-"+goodsList.get(i).get("goodsTotal").toString());
			// goodsLSDto.setClientName(goodsList.get(i).get("clientName").toString());
			// // goodsLSDto.setCurrentCount(currentCount);
			// // goodsLSDto.setDeliverNo("");
			// // goodsLSDto.setLadingId(ladingId);
			// // goodsLSDto.setSaveCount(saveCount);
			// // goodsLSDto.setTime(time);
			// // goodsLSDto.setType(type);
			//
			// goodsLSList.add(goodsLSDto);
			// }
			//
			// //根据时间排序
			// ComparatorLS comparatorLS=new ComparatorLS();
			// Collections.sort(goodsLSList,comparatorLS);

			// //发货记录
			List<Map<String, Object>> goodsLogList = goodsLogDao
					.getGoodsLog(goodsId);

			oaMsg.getData().addAll(goodsLogList);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			return oaMsg;
		} catch (RuntimeException re) {
			LOG.error("操作失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "操作失败", re);
		}

	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_LADING,type=C.LOG_TYPE.UPLOAD)
	public OaMsg updateLading(int id, String url) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			 Lading lading=new Lading();
	            lading.setId(id);
	            lading.setFileUrl(url);
	            ladingDao.updateLading(lading);
					oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
					return oaMsg;
				} catch (RuntimeException re) {
					LOG.error("操作失败", re);
					oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
					throw new OAException(Constant.SYS_CODE_DB_ERR, "操作失败", re);
				}
		
	}

	@Override
	public OaMsg getCargoTree(String cargoId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> cargoLZ=ladingDao.getTreeCargoList(cargoId);
			List<Map<String, Object>> tCargoLZ=new ArrayList<Map<String,Object>>();
			getChildren(cargoLZ);
			for(int i=0;i<cargoLZ.size();i++){
//				if("0".equals(cargoLZ.get(i).get("sourceGoodsId").toString())&&"0".equals(cargoLZ.get(i).get("rootGoodsId").toString())){
//					tCargoLZ.add(cargoLZ.get(i));
//					getGoodsChildren(tCargoLZ,cargoLZ,i);
//				}
				if(Common.empty(cargoLZ.get(i).get("parentId")) || "0".equals(cargoLZ.get(i).get("parentId").toString())) {
					tCargoLZ.add(cargoLZ.get(i));
				}
			}
			
			oaMsg.getData().addAll(tCargoLZ);
//			oaMsg.getData().addAll(ladingDao.getTreeCargoList(cargoId));
			oaMsg.setMsg("查询成功");
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", "0");
			map.put("totalpage", "1");
			map.put("totalRecord", "1");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}
	
	private void getChildren(List<Map<String, Object>> list) {
		for(int i = 0;i<list.size();i++) {
			Map<String, Object> map = list.get(i);
			for(int j = 0;j<list.size();j++) {//找出该节点的子节点
				Map<String, Object> node = list.get(j);
				if(!Common.empty(node.get("parentId")) && node.get("parentId").toString().equals(map.get("id").toString())) {
					List<Map<String, Object>> children = null;
					if(map.containsKey("children")) {
						children = (List<Map<String, Object>>) map.get("children");
					}else {
						children = new ArrayList<Map<String, Object>>();
						map.put("children", children);
					}
					children.add(node);
				}
			}
		}
	}

	private void getGoodsChildren(List<Map<String, Object>> tCargoLZ,
			List<Map<String, Object>> cargoLZ, int i) {
		for(int j=0;j<cargoLZ.size();j++){
			if(cargoLZ.get(j).get("sourceGoodsId").toString().equals(cargoLZ.get(i).get("id").toString())){
				tCargoLZ.add(cargoLZ.get(j));
				getGoodsChildren(tCargoLZ,cargoLZ,j);
			}
		}
		
		
	}

	@Override
	public Object getGoods(CargoGoodsDto cargoGoodsDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(cargoGoodsDao.getGoodsList(cargoGoodsDto, 0, 0));
			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	public Object checkCode(String code, int clientId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> count=ladingDao.checkCode(code,clientId);
			oaMsg.getData().addAll(count);
			
			oaMsg.setMsg(count.get(0).get("count").toString());
			if(Integer.parseInt(count.get(0).get("count").toString())>0){
				oaMsg.setCode(Constant.SYS_CODE_DISABLED);
			}
			
//			oaMsg.setMsg("查询成功");
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_LADING,type=C.LOG_TYPE.UPLOAD)
	public Object updateLadingNo(String id, String no) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			ladingDao.updateladingNo(id,no);
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
	@Log(object=C.LOG_OBJECT.PCS_LADING,type=C.LOG_TYPE.AGAINST)
	public Object againstLading(String ladingId) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			List<Map<String, Object>> goodsList = ladingDao
					.getGoodsListByLadingId(Integer.parseInt(ladingId));
			
			
			
			
			//检验货体是否有待提的，如果有则不能冲销
			LadingDto ladingDto=new LadingDto();
			ladingDto.setId(Integer.parseInt(ladingId));
			
			List<Map<String, Object>> ladingList = ladingDao.getLadingList(ladingDto,0,0);
			
			if(!Common.empty(ladingList)){
				if(Double.valueOf(ladingList.get(0).get("goodsDelivery").toString())+Double.valueOf(ladingList.get(0).get("goodsOut").toString())>0){
					oaMsg.setCode(Constant.SYS_CODE_DISABLED);
					oaMsg.setMsg("该提单已经有后续操作，无法冲销！");
					return oaMsg;
				}
			}
			
			Map<String, Object> goodsWait =ladingDao.getGoodsWaitByLadingId(ladingId);
				if(Double.valueOf(goodsWait.get("goodsWait").toString())>0){
					oaMsg.setCode(Constant.SYS_CODE_DISABLED);
					oaMsg.setMsg("该提单存在发货待提，无法冲销！");
					return oaMsg;
				}
				
				
			
			
			for (int i = 0; i < goodsList.size(); i++) {
				
				
				goodsLogDao.deleteGoodsLog(goodsList.get(i).get("id").toString());
				
				goodsLogDao.deleteGoodsLogByNextGoodsId(goodsList.get(i).get("id").toString());
				
				graftingCargoDao.repairLog(Integer.parseInt(goodsList.get(i).get("sourceGoodsId").toString()));
				
				graftingCargoDao.repairGoods(Integer.parseInt(goodsList.get(i).get("sourceGoodsId").toString()));
				
				graftingCargoDao.repairLadingOutByGoodsId(Integer.parseInt(goodsList.get(i).get("sourceGoodsId").toString()));
				
				cargoGoodsDao.deleteGoods(goodsList.get(i).get("id").toString());
				
			}
			ladingDao.realDeleteLading(ladingId);
			oaMsg.setMsg("冲销成功！");
		} catch (RuntimeException re) {
			LOG.error("冲销失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "冲销失败", re);
		}
		return oaMsg;
	}

}
