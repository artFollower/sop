package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.TankDto;
import com.skycloud.oa.system.model.Tank;
import com.skycloud.oa.utils.OaMsg;

/**
 * 储罐资料
 * 
 * @author feiying
 *
 */
public interface TankService {
	/**
	 * 获取储罐资料
	 * 
	 * @param tDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getTankList(TankDto tDto, PageView pageView)
			throws OAException;

	/**
	 * 增加储罐资料
	 * 
	 * @param tank
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addTank(Tank tank) throws OAException;

	/**
	 * 更新船舶资料
	 * 
	 * @param tank
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateTank(Tank tank) throws OAException;

	/**
	 * 删除储罐资料
	 * 
	 * @param tDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteTank(String ids) throws OAException;

	public abstract OaMsg getUpdateTankLogList(TankDto tankDto,
			PageView pageView) throws OAException;
}
