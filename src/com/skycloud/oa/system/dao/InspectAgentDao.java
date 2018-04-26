package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.InspectAgentDto;
import com.skycloud.oa.system.model.InspectAgent;


public interface InspectAgentDao{

	public List<Map<String,Object>> getInpectAgentList(InspectAgentDto iaDto,int start,int limit)throws OAException;
	public int getInpectAgentCount(InspectAgentDto iaDto,int start,int limit)throws OAException;
	
	public void addInpectAgent(InspectAgent inpectAgent)throws OAException;

	public void updateInpectAgent(InspectAgent inpectAgent)throws OAException;

	public void deleteInpectAgent(String ids)throws OAException;


}
