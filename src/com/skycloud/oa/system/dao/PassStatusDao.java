package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;

public interface PassStatusDao {

	List<Map<String,Object>> getPassStatusList() throws OAException;

}
