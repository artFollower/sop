package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ClientDto;
import com.skycloud.oa.system.model.Client;
import com.skycloud.oa.system.model.ClientFile;
import com.skycloud.oa.utils.OaMsg;

/**
 * 客户资料
 * 
 * @author jiahy
 *
 */
public interface ClientService {

	/**
	 * 获取客户资料
	 * 
	 * @param cDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getClientList(ClientDto cDto, PageView pageView)
			throws OAException;

	/**
	 * 增加客户资料
	 * 
	 * @param client
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addClient(Client client) throws OAException;

	/**
	 * 更新客户资料
	 * 
	 * @param client
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateClient(Client client) throws OAException;

	/**
	 * 删除客户资料
	 * 
	 * @param client
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteClient(String ids) throws OAException;



	OaMsg getTreeGoods(ArrivalDto cDto) throws OAException;

	/**
	 * 从客户组里移除客户
	 * @author yanyufeng
	 * @param groupId
	 * @return
	 * @throws OAException
	 * 2015-3-9 下午2:05:54
	 */
	public abstract OaMsg removeGroup(String clientId)throws OAException;

	public abstract OaMsg addFile(ClientFile clientFile)throws OAException;

	public abstract OaMsg deleteClientFile(String id, String url)throws OAException;

	public abstract OaMsg getClientFile(String clientId)throws OAException;
}
