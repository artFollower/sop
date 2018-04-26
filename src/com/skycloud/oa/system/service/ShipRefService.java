package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.model.ShipRef;
import com.skycloud.oa.utils.OaMsg;

/**
 * 船舶中英对照
 * 
 * @author yanyuf
 *
 */
public interface ShipRefService {
	/**
	 * 根据船舶id获得该船舶的中文名称list
	 * @author yanyufeng
	 * @param shipId
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getShipRefList(int shipId, PageView pageView) throws OAException;

	/**
	 * 增加船舶中文名
	 * 
	 * @param shipRef
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addShip(ShipRef shipRef) throws OAException;
}
