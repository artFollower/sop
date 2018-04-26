package com.skycloud.oa.message.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.message.dao.MsgTmpDao;

@Repository
public class MsgTmpDaoImpl extends BaseDaoImpl implements MsgTmpDao{

	@Override
	public List<Map<String, Object>> list() throws OAException
	{
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer("select * from t_tmp_msg");
		
		return executeQuery(sql.toString());
	}

}
