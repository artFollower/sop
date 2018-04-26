package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.TruckDto;
import com.skycloud.oa.system.model.Truck;
import com.skycloud.oa.utils.OaMsg;

/**
 * 车辆资料
 * 
 * @author jiahy
 *
 */
public interface TruckService {
	/**
	 * 获取车辆资料
	 * 
	 * @param tDto
	 * @return
	 * @
	 */
	public abstract OaMsg getTruckList(TruckDto tDto,PageView pageView)
			;

	/**
	 * 增加车辆资料
	 * 
	 * @param truck
	 * @return
	 * @
	 */
	public abstract OaMsg addTruck(Truck truck) ;

	/**
	 * 更新车辆资料
	 * 
	 * @param truck
	 * @return
	 * @
	 */
	public abstract OaMsg updateTruck(Truck truck) ;

	/**
	 * 删除车辆资料
	 * 
	 * @param tDto
	 * @return
	 * @
	 */
	public abstract OaMsg deleteTruck(String ids) ;
}
