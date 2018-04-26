package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.TubeCleanLogDto;
import com.skycloud.oa.system.model.TubeCleanLog;

public interface TubeCleanLogDao {

	public List<Map<String, Object>> getTubeCleanLogList(TubeCleanLogDto tDto, int start,
			int limit)throws OAException;
    
	public int getCount(TubeCleanLogDto tDto) throws OAException;
	
	public void addTubeCleanLog(TubeCleanLog tubeCleanLog)throws OAException;

	public void updateTubeCleanLog(TubeCleanLog tubeCleanLog)throws OAException;

	public void deleteTubeCleanLog(String ids)throws OAException;

}
