package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.BerthDto;
import com.skycloud.oa.system.dto.ShipDto;
import com.skycloud.oa.system.model.Berth;
import com.skycloud.oa.system.model.Ship;
import com.skycloud.oa.utils.OaMsg;

/**
 * 泊位资料
 * 
 * @author jiahy
 *
 */
public interface BerthService {

	public OaMsg getBerthList(BerthDto berthDto, PageView pageView)throws OAException;
	
	public OaMsg addBerth(Berth berth) throws OAException;
	
	public OaMsg updateBerth(Berth berth) throws OAException;
	
	public OaMsg deleteBerth(BerthDto berthDto) throws OAException;
}
