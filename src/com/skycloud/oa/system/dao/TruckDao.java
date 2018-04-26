package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.TruckDto;
import com.skycloud.oa.system.model.Truck;

public interface TruckDao{
	public List<Map<String,Object>> getTruckList(TruckDto tDto,int start,int limit)throws OAException;
	public int getTruckCount(TruckDto tDto,int start,int limit)throws OAException;
	
	public int addTruck(Truck truck)throws OAException;

	public void updateTruck(Truck truck)throws OAException;

	public void deleteTruck(String ids)throws OAException;
	
	public Map<String, Object> getTruckByCode(String code) throws Exception ;
}
