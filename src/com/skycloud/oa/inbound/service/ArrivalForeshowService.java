package com.skycloud.oa.inbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.dto.InsertArrivalPlanDto;
import com.skycloud.oa.inbound.dto.InsertSingleArrivalPlanDto;
import com.skycloud.oa.inbound.model.Arrival;
import com.skycloud.oa.inbound.model.ArrivalForeshow;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.Cargo;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

public interface ArrivalForeshowService {

	/**
	 * 添加船期预报
	 * @author yanyufeng
	 * @param arrivalForeshow
	 * @return
	 * @throws OAException
	 * 2016-3-11 下午2:25:41
	 */
	public abstract OaMsg addArrivalForeshow(ArrivalForeshow arrivalForeshow)throws OAException;
	/**
	 * 删除船期预报
	 * @author yanyufeng
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteArrivalForeshow(String ids)throws OAException;
	
	
	/**
	 * 更新船期预报
	 * @author yanyufeng
	 * @param arrivalForeshow
	 * @return
	 * @throws OAException
	 * 2016-3-11 下午2:29:59
	 */
	public abstract OaMsg updateArrivalForeshow(ArrivalForeshow arrivalForeshow)throws OAException;

	/**
	 * 船期预告列表
	 * @author yanyufeng
	 * @param arrivalDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 * 2016-3-11 下午1:17:06
	 */
	public abstract OaMsg getArrivalForeshowList(ArrivalDto arrivalDto,
			PageView pageView)throws OAException;
	/**
	 * 检查是否有该船的船期预报
	 * @author yanyufeng
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * 2016-3-16 下午1:45:01
	 */
	public abstract OaMsg checkForeshow(int arrivalId)throws OAException;
	
	
	/**
	 * sql更新船期预报
	 * @author yanyufeng
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * 2016-3-16 下午1:44:51
	 */
	public abstract OaMsg updateForeshowBySQL(int arrivalId)throws OAException;
	/**
	 * sql添加船期预报
	 * @author yanyufeng
	 * @param arrivalId
	 * @return
	 * 2016-3-16 下午2:42:06
	 */
	public abstract OaMsg addForeshowBySQL(int arrivalId)throws OAException;
	/**
	 * 更新船期预告
	 * @author yanyufeng
	 * @param arrivalForeshow
	 * @return
	 * @throws OAException
	 * 2016-3-17 下午3:32:55
	 */
	public abstract OaMsg updateForeshow(ArrivalForeshow arrivalForeshow)throws OAException;
	/**
	 * 添加船期预告
	 * @author yanyufeng
	 * @param arrivalForeshow
	 * @return
	 * @throws OAException
	 * 2016-3-17 下午5:07:28
	 */
	public abstract OaMsg addForeshow(ArrivalForeshow arrivalForeshow)throws OAException;
	public abstract OaMsg zfArrivalForeshow(String id)throws OAException;
	public abstract OaMsg getOutArrivalCargo(int arrivalId)throws OAException;

	
}
