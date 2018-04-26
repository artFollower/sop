package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.TankCleanLogDto;
import com.skycloud.oa.system.model.TankCleanLog;

public interface TankCleanLogDao {

	public List<Map<String, Object>> getTankCleanLogList(TankCleanLogDto tDto, int start,
			int limit)throws OAException;
    
	public int getCount(TankCleanLogDto tDto) throws OAException;
	
	public void addTankCleanLog(TankCleanLog tankCleanLog)throws OAException;

	public void updateTankCleanLog(TankCleanLog tankCleanLog)throws OAException;

	public void deleteTankCleanLog(String ids)throws OAException;

}
