package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.ArrivalStatusDao;
@Repository
public class ArrivalStatusDaoImpl extends BaseDaoImpl implements ArrivalStatusDao {
	private static Logger LOG = Logger.getLogger(ArrivalStatusDaoImpl.class);
	@Override
	public List<Map<String, Object>> getArrivalStatusList() throws OAException {
		try {
			String sql="select * from t_pcs_arrival_status";
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}

}
