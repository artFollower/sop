package com.skycloud.oa.inbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.GraftingHistory;
import com.skycloud.oa.outbound.dao.GoodsLogDao;


public interface GraftingCargoDao{

	
	/**
	 * 查询可嫁接的货主
	 * @author yanyufeng
	 * @throws OAException
	 * 2015-10-8 下午5:41:50
	 */
	public List<Map<String, Object>> getClient() throws OAException;
	
	/**
	 * 查询可嫁接的货体
	 * @author yanyufeng
	 * @param goods
	 * @throws OAException
	 * 2015-10-8 下午5:57:50
	 */
	public List<Map<String, Object>> getGraftingGoods(Goods goods)throws OAException;

	/**
	 * 查询可以被嫁接的货体
	 * @author yanyufeng
	 * @param goods
	 * @return
	 * @throws OAException
	 * 2015-10-9 上午11:16:07
	 */
	public List<Map<String, Object>> toGraftingGoods(Goods goods)throws OAException;

	/**
	 * 更新下一级货体的调号为realId
	 * @author yanyufeng
	 * @param sourceGoodsId
	 * @param realId 需要更新的sourceGoodsId
	 * @throws OAException
	 * 2015-10-9 下午4:59:20
	 */
	public void updateSourceGoodsId(int sourceGoodsId, int realId)throws OAException;

	/**
	 * 把虚拟货体除入库、封放、调入、调入退回之外的其他操作全部归real货体
	 * @author yanyufeng
	 * @param sourceGoodsId
	 * @param realId
	 * @throws OAException
	 * 2015-10-9 下午6:38:44
	 */
	public void changeLog(int sourceGoodsId, int realId)throws OAException;

	/**
	 * 改变所有下级货体的cargoId
	 * @author yanyufeng
	 * @param virCargoId
	 * @param realCargoId
	 * @param type  1：原始货体  2：原号货体
	 * @param i 
	 * @throws OAException
	 * 2015-10-10 上午11:14:24
	 */
	public void changeCargoId(int virGoodsId,int virCargoId, int realCargoId, int type)throws OAException;

	/**
	 * 修复货体日志
	 * @author yanyufeng
	 * @param realId
	 * @throws OAException
	 * 2015-10-12 下午2:39:52
	 */
	public void repairLog(int realId)throws OAException;

	/**
	 * 改变所有下级货体的原号    原始货体-原号货体，原始货体-提单内货体
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @param realId
	 * 2015-10-13 下午5:26:53
	 */
	public void updateRootGoodsId(int virtualGoodsId, int realId)throws OAException;

	/**
	 * 改变所有下级货体的原号   原号货体-原始货体 
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * 2015-10-13 下午6:12:02
	 */
	public void updateRootGoodsId(int virtualGoodsId)throws OAException;

	/**
	 * 改变所有下级货体的原号   原号货体-原号货体，原号货体-提单内货体
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @param realId
	 * 2015-10-13 下午6:40:24
	 */
	public void updateRootGoodsIdByYTY(int virtualGoodsId, int realId)throws OAException;

	/**
	 * 创建真实货体的调出，调出退回操作(根据虚拟货体的调入，调入退回)，并修改时间，修复日志
	 * @author yanyufeng
	 * @param virtualGoodsId 虚拟货体id
	 * @param realId 真实货体id
	 * @throws OAException
	 * 2015-10-14 下午3:07:41
	 */
	public void createOutLog(int virtualGoodsId, int realId)throws OAException;

	/**
	 * 删除虚拟上级货体的相应调出.调出退回日志，并修复日志。
	 * @author yanyufeng
	 * @param virtualGoodsId 虚拟货体id
	 * @param count 
	 * @throws OAException
	 * 2015-10-14 下午4:14:35
	 */
	public void deleteOutLog(int virtualGoodsId, double count)throws OAException;

	/**
	 * 修复货体
	 * @author yanyufeng
	 * @param parseInt
	 * 2015-10-15 下午2:31:12
	 */
	public void repairGoods(int goodsId)throws OAException;

	/**
	 * 修改上级入库/调入记录
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @param parseDouble
	 * @throws OAException
	 * 2015-10-20 下午2:02:13
	 */
	public void updateRKDJLog(int virtualGoodsId, double parseDouble)throws OAException;

	/**
	 * 修改本级入库/调入/封放记录
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @param goodsTotal
	 * @throws OAException
	 * 2015-10-21 下午1:42:50
	 */
	public void updateRKFF(int virtualGoodsId, String goodsTotal)throws OAException;

	/**
	 * 拆分上级货体调出日志
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @param count 
	 * @param newgoodsId 
	 * @throws OAException
	 * 2015-10-21 下午3:07:10
	 */
	public void splitSourceOutLog(int virtualGoodsId, double count, int newgoodsId)throws OAException;

	/**
	 * 查询调出真实量（调出记录-退回）
	 * @author yanyufeng
	 * @param logId
	 * @return
	 * @throws OAException
	 * 2015-10-21 下午4:51:56
	 */
	public List<Map<String, Object>> getTrueOutCountLog(int logId)throws OAException;

	/**
	 * 嫁接调出日志到新货体
	 * @author yanyufeng
	 * @param parseInt
	 * @param newgoodsId
	 * 2015-10-21 下午5:14:02
	 */
	public void graftingOutLog(int logId, int newgoodsId)throws OAException;

	/**
	 * 更新virgoods对应lading的下级货体的上级货体为newgoods
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @param newgoodsId
	 * @param ladingId
	 * @throws OAException
	 * 2015-10-21 下午5:34:13
	 */
	public void updateNextGoodsSourceRoot(int virtualGoodsId, int newgoodsId,
			int ladingId)throws OAException;

	/**
	 * 拆调出日志，拆出来的日志归到新货体
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @param newgoodsId
	 * @param count
	 * @return 
	 * @throws OAException
	 * 2015-10-21 下午6:10:28
	 */
	public int splitOutLog(int virtualGoodsId, int newgoodsId,
			double count)throws OAException;

	/**
	 * 查询提单对应的下级货体
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @param parseInt
	 * @throws OAException
	 * 2015-10-21 下午6:28:02
	 */
	public List<Map<String, Object>> getNextGoods(int virtualGoodsId, int nextLadingId)throws OAException;

	/**
	 * 嫁接提货日志
	 * @author yanyufeng
	 * @param parseInt
	 * @param newgoodsId
	 * @throws OAException
	 * 2015-10-21 下午7:33:23
	 */
	public void graftingActualLog(int logId, int newgoodsId)throws OAException;

	/**
	 * 更新log的nextGoodsId
	 * @author yanyufeng
	 * @param logId
	 * @param mNewgoodsId
	 * @throws OAException
	 * 2015-10-23 下午1:22:07
	 */
	public void updateLogNextGoods(int logId, int mNewgoodsId)throws OAException;

	public  List<Map<String, Object>> checkWaitOut(int goodsId)throws OAException;

	/**
	 * 修复提单
	 * @author yanyufeng
	 * @param realId 
	 * @param virtualGoodsId 
	 * @throws OAException
	 * 2015-10-23 下午4:34:58
	 */
	public void repairLading(int virtualGoodsId, int realId)throws OAException;

	/**
	 * 更新日志ladingId
	 * @author yanyufeng
	 * @param realId 
	 * @param virtualGoodsId 
	 * @throws OAException
	 * 2015-10-24 下午2:48:53
	 */
	public void updateLogLadingId(int virtualGoodsId, int realId)throws OAException;

	/**
	 * 修改下级所有货体的rootGoodsId
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @throws OAException
	 * 2015-10-24 下午3:20:18
	 */
	public void updateRootGoodsIdByProduce(int virtualGoodsId)throws OAException;

	/**
	 * 添加历史记录
	 * @author yanyufeng
	 * @param graftingHistory
	 * @return
	 * @throws OAException
	 * 2015-10-28 上午9:51:56
	 */
	public int addHistory(GraftingHistory graftingHistory)throws OAException;

	/**
	 * 查询嫁接历史
	 * @author yanyufeng
	 * @param graftingHistory
	 * @param startRecord
	 * @param maxresult
	 * @return
	 * 2015-10-28 上午9:58:12
	 */
	public List<Map<String, Object>> getHistory(
			GraftingHistory graftingHistory, int startRecord, int maxresult)throws OAException;

	/**
	 * 嫁接历史数量
	 * @author yanyufeng
	 * @param graftingHistory
	 * @return
	 * @throws OAException
	 * 2015-10-28 上午9:58:42
	 */
	public int getHistoryCount(GraftingHistory graftingHistory)throws OAException;

	/**
	 * 修复货批
	 * @author yanyufeng
	 * @param parseInt
	 * @throws OAException
	 * 2015-10-28 下午5:53:35
	 */
	public void repairCargo(int parseInt)throws OAException;

	/**
	 * 判断真实货体待提量
	 * @author yanyufeng
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2016-3-18 上午10:04:27
	 */
	public List<Map<String, Object>> checkRealWaitOut(int goodsId)throws OAException;

	/**
	 * 修改真实货体的日志ladingId
	 * @author yanyufeng
	 * @param realId
	 * @throws OAException
	 * 2016-3-18 上午10:32:02
	 */
	public void updateLogLadingId(int realId)throws OAException;

	/**
	 * 通过货体号修复相应提单
	 * @author yanyufeng
	 * @param parseInt
	 * @throws OAException
	 * 2016-5-26 下午6:10:09
	 */
	public void repairLadingOutByGoodsId(int parseInt)throws OAException;

	/**
	 * 获得入库调入操作时间
	 * @author yanyufeng
	 * @param oldGoodsId
	 * @return
	 * @throws OAException
	 * 2016-5-26 下午8:30:33
	 */
	public List<Map<String, Object>> getRKDJTime(int oldGoodsId)throws OAException;

	public void changeLogTime(int virtualGoodsId, int i, String tankId)throws OAException;


	

}
