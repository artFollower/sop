package com.skycloud.oa.inbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.GraftingHistory;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

public interface GraftingCargoService {

	/**
	 * 查询可嫁接的货主
	 * @author yanyufeng
	 * @return
	 * @throws OAException
	 * 2015-10-8 下午6:10:00
	 */
	public abstract OaMsg getClient() throws OAException;

	/**
	 * 查询可嫁接的货体
	 * @author yanyufeng
	 * @param goods
	 * @return
	 * @throws OAException
	 * 2015-10-8 下午6:10:12
	 */
	public abstract OaMsg getGraftingGoods(Goods goods)throws OAException;

	/**
	 * 查询可以被嫁接的货体
	 * @author yanyufeng
	 * @param goods
	 * @return
	 * 2015-10-9 上午11:08:58
	 */
	public abstract OaMsg toGraftingGoods(Goods goods)throws OAException;

	/**
	 * 嫁接整批
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @param realId
	 * @return
	 * @throws OAException
	 * 2015-10-9 下午4:22:05
	 */
	public abstract OaMsg graftingAll(int virtualGoodsId, int realId)throws OAException;

	/**
	 * 拆分货体返回拆分完的货体号
	 * @author yanyufeng
	 * @param virtualGoodsId
	 * @param count
	 * @return
	 * 2015-10-20 下午5:43:30
	 */
	public abstract int sqlitGoods(int virtualGoodsId, double count)throws OAException;

	/**
	 * 查询虚拟库货体是否有待提
	 * @author yanyufeng
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2015-10-23 下午3:11:37
	 */
	public abstract OaMsg checkWaitOut(int goodsId)throws OAException;

	/**
	 * 添加嫁接历史
	 * @author yanyufeng
	 * @param graftingHistory
	 * @return
	 * @throws OAException
	 * 2015-10-28 上午9:45:40
	 */
	public abstract OaMsg addHistory(GraftingHistory graftingHistory)throws OAException;

	/**
	 * 查询历史记录
	 * @author yanyufeng
	 * @param graftingHistory
	 * @param pageView
	 * @return
	 * @throws OAException
	 * 2015-10-28 上午9:55:48
	 */
	public abstract OaMsg getHistory(GraftingHistory graftingHistory,
			PageView pageView)throws OAException;

	/**
	 * 判断真实库货体有没有待提
	 * @author yanyufeng
	 * @param goodsId
	 * @return
	 * @throws OAException
	 * 2016-3-18 上午10:02:50
	 */
	public abstract OaMsg checkRealWaitOut(int goodsId)throws OAException;
	

}
