package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.ParkDto;
import com.skycloud.oa.system.model.Park;

public interface ParkDao {

	public List<Map<String, Object>> getParkList(ParkDto tDto, int start,int limit)throws OAException;
    
	public int getCount(ParkDto tDto) throws OAException;
	
	public void addPark(Park park)throws OAException;

	public void updatePark(Park park)throws OAException;

	public void deletePark(String ids)throws OAException;

}
