package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.PumpDto;
import com.skycloud.oa.system.model.Pump;

public interface PumpDao {

	public List<Map<String, Object>> getPumpList(PumpDto tDto, int start,int limit)throws OAException;
    
	public int getCount(PumpDto tDto) throws OAException;
	
	public void addPump(Pump pump)throws OAException;

	public void updatePump(Pump pump)throws OAException;

	public void deletePump(String ids)throws OAException;

}
