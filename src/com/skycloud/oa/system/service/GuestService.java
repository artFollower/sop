package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.GuestDto;
import com.skycloud.oa.system.model.Guest;
import com.skycloud.oa.utils.OaMsg;

/**
 * 客户账户信息
 * 
 * @author jiahy
 *
 */
public interface GuestService {
	/**
	 * 获取客户账户信息
	 * 
	 * @param gDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getGuestList(GuestDto gDto, int start, int limit)
			throws OAException;

	/**
	 * 增加客户账户信息
	 * 
	 * @param guest
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addGuest(Guest guest) throws OAException;

	/**
	 * 更新客户账户信息
	 * 
	 * @param guest
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateGuest(Guest guest) throws OAException;

	/**
	 * 删除客户账户信息
	 * 
	 * @param gDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteGuest(String ids) throws OAException;

}
