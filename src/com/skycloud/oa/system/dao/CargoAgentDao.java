package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.model.CargoAgent;


public interface CargoAgentDao {

public List<Map<String,Object>> getCargoAgentList(CargoAgentDto caDto,int start,int limit)throws OAException;

public int getCargoAgentListCount(CargoAgentDto caDto)throws OAException;


public  void addCargoAgent(CargoAgent cargoAgent)throws OAException;

public  void updateCargoAgent(CargoAgent cargoAgent)throws OAException;

public  void deleteCargoAgent(String ids)throws OAException;

}
