package com.skycloud.oa.inbound.dao.impl;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.DutyRecordDao;
import com.skycloud.oa.inbound.model.DutyRecord;
@Component
public class DutyRecordDaoImpl extends BaseDaoImpl implements DutyRecordDao {

	private static Logger LOG = Logger.getLogger(DutyRecordDaoImpl.class);

	@Override
	public int addDutyRecord(DutyRecord dutyRecord)throws OAException {
		try {
			save(dutyRecord);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void deleteDutyRecord(String id)throws OAException {
		String sql = "delete from t_pcs_duty_record where id in(" + id + ")";
		try {
			execute(sql);
		} catch (OAException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getDutyRecord(String disptachId)throws OAException {
		String sql="select a.*,b.name createUserName,c.value weatherName from t_pcs_duty_record a " +
				"LEFT JOIN t_auth_user b on b.id=a.createUserId" +
				" LEFT JOIN t_pcs_weather c on a.weather=c.key where a.dispatchId="+disptachId+" order by a.time ASC";
		try {
			return executeQuery(sql);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "异常",e);
		}
	}

	@Override
	public void updateDutyRecord(DutyRecord dutyRecord)throws OAException {
		try {
			update(dutyRecord);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
