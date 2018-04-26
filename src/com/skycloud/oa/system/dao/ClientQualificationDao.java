package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.ClientQualificationDto;
import com.skycloud.oa.system.model.ClientQualification;


public interface ClientQualificationDao{

	public List<Map<String,Object>> getClientQualificationList(ClientQualificationDto cqDto,int start,int limit)throws OAException;
	
    public void addClientQualification(ClientQualification clientQualification)throws OAException;
	
	public void updateClientQualification(ClientQualification clientQualification)throws OAException;
	
	public void deleteClientQualification(String ids)throws OAException;

	public int getCount(ClientQualificationDto cqDto)throws OAException;
}
