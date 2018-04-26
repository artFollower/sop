package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.PumpDto;
import com.skycloud.oa.system.model.Pump;
import com.skycloud.oa.utils.OaMsg;

/**
 * 泊位资料
 * 
 * @author jiahy
 *
 */
public interface PumpService {

	public OaMsg getPumpList(PumpDto pumpDto,PageView pageView)throws OAException;

	public OaMsg updatePump(Pump pump) throws OAException;

	public OaMsg addPump(Pump pump) throws OAException;

	public OaMsg deletePump(PumpDto pumpDto) throws OAException;
}
