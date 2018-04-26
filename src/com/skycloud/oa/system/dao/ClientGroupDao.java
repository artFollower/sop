package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.model.ClientGroup;


public interface ClientGroupDao{
	
	public List<Map<String,Object>> getClientGroup(String id, String name,String letter, int start,int limit)throws OAException;
	
	public int getClientGroupCount(String name, String letter) throws OAException;
	
	public void addClientGroup(ClientGroup clientGroup)throws OAException;

	public void updateClientGroup(ClientGroup clientGroup)throws OAException;

	public void deleteClientGroup(String ids)throws OAException;

}
