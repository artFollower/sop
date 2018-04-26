package com.skycloud.oa.system.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.TankDto;
import com.skycloud.oa.system.model.Tank;

public interface TankDao {

	public List<Map<String, Object>> getTankList(TankDto tDto, int start,
			int limit)throws OAException;
	public Map<String, Object> getTankByCode(String code)throws OAException;
    
	public int getCount(TankDto tDto) throws OAException;
	
	public void addTank(Tank tank)throws OAException;

	public void updateTank(Tank tank)throws OAException;

	public void deleteTank(String ids)throws OAException;
	public void addTankUpdateLog(Integer id)throws OAException;
	public List<Map<String, Object>> getUpdateTankLog(TankDto tDto, int start,
			int limit)throws OAException;
	public int getUpdateTankLogCount(TankDto tankDto)throws OAException;

}
