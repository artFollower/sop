package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ParkDto;
import com.skycloud.oa.system.model.Park;
import com.skycloud.oa.utils.OaMsg;

/**
 * 泊位资料
 * 
 * @author jiahy
 *
 */
public interface ParkService {

	public OaMsg getParkList(ParkDto ParkDto,PageView pageView)throws OAException;

	public OaMsg updatePark(Park Park) throws OAException;

	public OaMsg addPark(Park Park) throws OAException;

	public OaMsg deletePark(ParkDto parkDto) throws OAException;
}
