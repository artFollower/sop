package com.skycloud.oa.system.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ArrivalDto;
import com.skycloud.oa.system.dto.ClientDto;
import com.skycloud.oa.system.model.Client;
import com.skycloud.oa.system.model.ClientFile;


public interface ClientDao{


	public List<Map<String,Object>> getClientList(ClientDto cDto,int start,int limit)throws OAException;
	
	public int getClientListCount(ClientDto cDto)throws OAException;
	
	public int addClient(Client client)throws OAException;

	public void updateClient(Client client)throws OAException;

	public void deleteClient(String ids)throws OAException;

	List<Map<String, Object>> getTreeGoodsList(ArrivalDto cDto)
			throws OAException;

	/**
	 * 移除客户对客户组的关联
	 * @author yanyufeng
	 * @param ids
	 * 2015-3-9 上午10:41:16
	 */
	public void removeClientGroup(String ids)throws OAException;

	/**
	 * 移除客户组里的客户
	 * @author yanyufeng
	 * @param clientId
	 * 2015-3-9 下午2:07:20
	 */
	public void removeGroup(String clientId)throws OAException;

	/**
	 * 检查客户名或者编号是否重名
	 * @author yanyufeng
	 * @param client
	 * @throws OAException
	 * 2015-12-1 下午6:58:35
	 */
	public int checkSame(Client client)throws OAException;

	public int addClientFile(ClientFile clientFile)throws OAException;

	public void deleteClientFile(String id)throws OAException;

	public List<Map<String, Object>> getClientFile(String clientId)throws OAException;
}
