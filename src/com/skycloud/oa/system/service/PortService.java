package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.PortDto;
import com.skycloud.oa.system.model.Port;
import com.skycloud.oa.utils.OaMsg;

/**
 * 港口
 * 
 * @author jiahy
 *
 */
public interface PortService {

	public OaMsg getPortList(PortDto portDto,PageView pageView)throws OAException;

	public OaMsg updatePort(Port port) throws OAException;

	public OaMsg addPort(Port port) throws OAException;

	public OaMsg deletePort(PortDto portDto) throws OAException;
}
