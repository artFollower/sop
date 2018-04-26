package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.TubeDto;
import com.skycloud.oa.system.model.Tube;

public interface TubeDao {

	public List<Map<String, Object>> getList(TubeDto tubeDto,int start,int limit)throws OAException;
	
    public int getCount(TubeDto tubeDto) throws OAException;
    
	public void add(Tube tube)throws OAException;

	public void update(Tube tube)throws OAException;

	public void delete(String ids)throws OAException;

}
