package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.QualificationDto;
import com.skycloud.oa.system.model.Qualification;


public interface QualificationDao{

	public List<Map<String,Object>> getQualificationList(QualificationDto qDto, int start, int limit)throws OAException;
    
	public  int getCount(QualificationDto qDto) throws OAException; 
	
	public void addQualification(Qualification qualification)throws OAException;

	public void updateQualification(Qualification qualification)throws OAException;

	public void deleteQualification(String ids)throws OAException;

}
