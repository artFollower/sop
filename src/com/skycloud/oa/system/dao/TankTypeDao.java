package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;

public interface TankTypeDao {


	List<Map<String,Object>> getTankTypeList() throws OAException;

}
