package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ShipDto;
import com.skycloud.oa.system.dto.ShipInfoDtoList;
import com.skycloud.oa.system.model.Ship;
import com.skycloud.oa.utils.OaMsg;

/**
 * 船舶资料
 * 
 * @author jiahy
 *
 */
public interface ShipService {

	/**
	 * 获取船舶资料
	 * 
	 * @param sDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getShipRefList(String shipId);
	public abstract OaMsg deleteShipRefInfo(String shipRefId);
	public abstract OaMsg getShipList(ShipDto sDto,PageView pageView)
			throws OAException;

	/**
	 * 增加船舶资料
	 * 
	 * @param ship
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addShip(ShipInfoDtoList  shipInfoDtoList) throws OAException;

	/**
	 * 更新船舶资料
	 * 
	 * @param ship
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateShip(ShipInfoDtoList  shipInfoDtoList) throws OAException;

	/**
	 * 删除船舶资料
	 * 
	 * @param sDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteShip(String ids) throws OAException;
}
