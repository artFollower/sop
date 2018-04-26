package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;

public interface CargoStatusDao {

	List<Map<String,Object>> getCargoStatusList() throws OAException;

}
