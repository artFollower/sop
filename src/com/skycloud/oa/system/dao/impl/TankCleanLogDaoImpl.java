package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.TankCleanLogDao;
import com.skycloud.oa.system.dao.TankDao;
import com.skycloud.oa.system.dto.TankCleanLogDto;
import com.skycloud.oa.system.dto.TankDto;
import com.skycloud.oa.system.model.Tank;
import com.skycloud.oa.system.model.TankCleanLog;
import com.skycloud.oa.utils.Common;

@Repository
public class TankCleanLogDaoImpl extends BaseDaoImpl implements TankCleanLogDao{
	private static Logger LOG = Logger.getLogger(TankCleanLogDaoImpl.class);

	@Override
	public List<Map<String, Object>> getTankCleanLogList(TankCleanLogDto tDto,
			int start, int limit) throws OAException {
		try {
			String sql="select a.id,a.productId,a.tankId,a.description,a.editUserId,from_unixtime(a.editTime) editTime,b.code tankCode,c.name productName,"
					+ "e.name editUserName  from t_pcs_tank_clean_log a "
					+ " LEFT JOIN t_pcs_tank b on a.tankId=b.id "
					+ " LEFT JOIN t_pcs_product c on a.productId=c.id "
					+ " LEFT JOIN t_auth_user e on a.editUserId=e.id "
					+ " where 1=1";
			     if(tDto.getTankId()!=null){
			    	 sql+=" and a.tankId="+tDto.getTankId();
			     }
			     sql+=" order by a.editTime desc";
			     sql+=" limit "+start+","+limit;
			 return executeQuery(sql);
		} catch (RuntimeException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao储罐获取失败");
		}
	}

	@Override
	public int getCount(TankCleanLogDto tDto) throws OAException {
		try {
			String sql="select count(*)  from t_pcs_tank_clean_log a "
					+ " LEFT JOIN t_pcs_tank b on a.tankId=b.id "
					+ " LEFT JOIN t_pcs_product c on a.productId=c.id "
					+ " LEFT JOIN t_auth_user e on a.editUserId=e.id "
					+ " where 1=1";
			     if(tDto.getTankId()!=null){
			    	 sql+=" and a.tankId="+tDto.getTankId();
			     }
			 return (int) getCount(sql);
		} catch (RuntimeException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao储罐获取失败");
		}
	}

	@Override
	public void addTankCleanLog(TankCleanLog tankCleanLog) throws OAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTankCleanLog(TankCleanLog tankCleanLog)
			throws OAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTankCleanLog(String ids) throws OAException {
		// TODO Auto-generated method stub
		
	}
	
}
