package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;

public interface OfficeGradeDao {

    List<Map<String,Object>> getTaxTypeList() throws OAException;

}
