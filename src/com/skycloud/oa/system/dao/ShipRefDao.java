package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.model.ShipRef;


public interface ShipRefDao{
	
	public List<Map<String,Object>> getShipRefList(int shipId,int start,int limit)throws OAException;
	
	public  int addShipRef(ShipRef shipRef)throws OAException;
	
	public Map<String,Object> getShipRef(int shipId,String shipRefName)throws OAException;
	
}
