package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.model.ApproveWork;

public interface ApproveWorkDao {

	public List<Map<String, Object>> getApproveWork(ApproveWork approveWork)throws OAException;
    
	



}
