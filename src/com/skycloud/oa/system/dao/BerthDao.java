package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.BerthDto;
import com.skycloud.oa.system.model.Berth;

public interface BerthDao {

	public List<Map<String, Object>> getList(BerthDto berthDto, int i, int j)throws OAException;
    
	public int getCount(BerthDto berthDto) throws OAException;
	
	public void add(Berth berth)throws OAException;

	public void update(Berth berth)throws OAException;

	public void delete(String ids)throws OAException;

}
