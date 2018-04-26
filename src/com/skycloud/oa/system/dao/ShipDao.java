package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.ShipDto;
import com.skycloud.oa.system.model.Ship;
import com.skycloud.oa.system.model.ShipRef;


public interface ShipDao{
	public List<Map<String,Object>> getShipList(ShipDto sDto,int start,int limit)throws OAException;
	public List<Map<String,Object>> getShipREfList(String shipId)throws OAException;
	
	public int getShipListCount(ShipDto sDto)throws OAException;
    
	public String addShip(Ship ship)throws OAException;
	public void saveShipRef(ShipRef ship)throws OAException;
	public void updateShipRef(ShipRef ship)throws OAException;

	public void updateShip(Ship ship)throws OAException;
	public void deleteShipRefInfo(String shipRefId) throws OAException ;
	public void deleteShip(String ids)throws OAException;
}
