package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.model.ClientGroup;
import com.skycloud.oa.utils.OaMsg;

/**
 * 船舶中英对照
 * 
 * @author yanyuf
 *
 */
public interface ClientGroupService {
	public abstract OaMsg getClientGroup(String id, String name,String letter, PageView pageView) throws OAException;


	public OaMsg updateClientGroup(ClientGroup clientGroup) throws OAException;

	public OaMsg addClientGroup(ClientGroup clientGroup) throws OAException;

	public OaMsg deleteClientGroup(String ids) throws OAException;
}
