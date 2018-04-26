package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.PortDto;
import com.skycloud.oa.system.model.Port;

public interface PortDao {

	public List<Map<String, Object>> getList(PortDto portDto, int start,
			int limit)throws OAException;
    
	public int getCount(PortDto portDto) throws OAException;
	
	public void add(Port port)throws OAException;

	public void updatePort(Port port)throws OAException;

	public void delete(String ids)throws OAException;

}
