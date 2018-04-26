package com.skycloud.oa.inbound.dao.impl;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.DutyRecordDao;
import com.skycloud.oa.inbound.dao.DutyWeatherDao;
import com.skycloud.oa.inbound.model.DutyRecord;
import com.skycloud.oa.inbound.model.DutyWeather;
@Component
public class DutyWeatherDaoImpl extends BaseDaoImpl implements DutyWeatherDao {

	private static Logger LOG = Logger.getLogger(DutyWeatherDaoImpl.class);


	@Override
	public int addDutyWeather(DutyWeather dutyWeather) throws OAException {
		try {
			save(dutyWeather);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public void deleteDutyWeather(String id) throws OAException {
		String sql = "delete from t_pcs_duty_weather where id in(" + id + ")";
		try {
			execute(sql);
		} catch (OAException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Map<String, Object>> getDutyWeather(String disptachId)
			throws OAException {
		String sql="select a.* from t_pcs_duty_weather a " +
				" where a.dispatchId="+disptachId+" order by a.time ASC";
		try {
			return executeQuery(sql);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw new OAException(Constant.SYS_CODE_DB_ERR, "异常",e);
		}
	}

	@Override
	public void updateDutyWeather(DutyWeather dutyWeather) throws OAException {
		try {
			update(dutyWeather);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
