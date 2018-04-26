package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.ExceedFeeLogDto;
import com.skycloud.oa.inbound.model.ExceedFeeLog;


public interface ExceedFeeLogDao {

	public List<Map<String, Object>> getExceedFeeLogList(ExceedFeeLogDto elDto) throws OAException;
	
	public int getExceedFeeLogCount(ExceedFeeLogDto elDto)throws OAException;
	
	public int addExceedFeeLog(ExceedFeeLog exeedFeelog) throws OAException;
	
	public void updateExceedFeeLog(ExceedFeeLog logList) throws OAException;
	
	public void deleteExceedFeeLog(ExceedFeeLogDto elDto)throws OAException;
}
