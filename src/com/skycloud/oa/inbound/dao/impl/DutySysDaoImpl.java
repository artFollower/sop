package com.skycloud.oa.inbound.dao.impl;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.DutyRecordDao;
import com.skycloud.oa.inbound.dao.DutySysDao;
import com.skycloud.oa.inbound.model.DutyRecord;
import com.skycloud.oa.inbound.model.DutySys;
@Component
public class DutySysDaoImpl extends BaseDaoImpl implements DutySysDao {

	private static Logger LOG = Logger.getLogger(DutySysDaoImpl.class);

	@Override
	public int addDutySys(DutySys dutySys) throws OAException {
		try {
			save(dutySys);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<Map<String, Object>> getDutySys(String disptachId,int type)
			throws OAException {
		String sql="select a.* from t_pcs_duty_sys a " +
				"  where a.dispatchId="+disptachId+" and a.type="+type +" order by a.id ASC";
		try {
			return executeQuery(sql);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "异常",e);
		}
	}

	@Override
	public void updateDutySys(DutySys dutySys) throws OAException {
		try {
			update(dutySys);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
