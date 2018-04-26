package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.ShipAgentDto;
import com.skycloud.oa.system.model.ShipAgent;


public interface ShipAgentDao{
	
	public List<Map<String,Object>> getShipAgentList(ShipAgentDto saDto,int start,int limit)throws OAException;
	public int getShipAgentCount(ShipAgentDto saDto,int start,int limit)throws OAException;
	
public void addShipAgent(ShipAgent shipAgent)throws OAException;
	
	public void updateShipAgent(ShipAgent shipAgent)throws OAException;
	
	public void deleteShipAgent(String ids)throws OAException;

}
