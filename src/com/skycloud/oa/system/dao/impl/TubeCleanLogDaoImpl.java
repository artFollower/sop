package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.TubeCleanLogDao;
import com.skycloud.oa.system.dto.TankCleanLogDto;
import com.skycloud.oa.system.dto.TubeCleanLogDto;
import com.skycloud.oa.system.model.TankCleanLog;
import com.skycloud.oa.system.model.TubeCleanLog;

@Repository
public class TubeCleanLogDaoImpl extends BaseDaoImpl implements TubeCleanLogDao{
	private static Logger LOG = Logger.getLogger(TubeCleanLogDaoImpl.class);

	@Override
	public List<Map<String, Object>> getTubeCleanLogList(TubeCleanLogDto tDto,
			int start, int limit) throws OAException {
		try {
			String sql="select a.id,a.productId,a.tubeId,a.description,a.editUserId,from_unixtime(a.editTime) editTime,b.name tubeName,c.name productName,"
					+ "e.name editUserName  from t_pcs_tube_clean_log a "
					+ " LEFT JOIN t_pcs_tube b on a.tubeId=b.id "
					+ " LEFT JOIN t_pcs_product c on a.productId=c.id "
					+ " LEFT JOIN t_auth_user e on a.editUserId=e.id "
					+ " where 1=1";
			     if(tDto.getTubeId()!=null){
			    	 sql+=" and a.tubeId="+tDto.getTubeId();
			     }
			     sql+=" order by a.editTime desc";
			     sql+=" limit "+start+","+limit;
			 return executeQuery(sql);
		} catch (RuntimeException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao储罐获取失败");
		}
	}

	@Override
	public int getCount(TubeCleanLogDto tDto) throws OAException {
		try {
			String sql="select count(*)  from t_pcs_tube_clean_log a  where 1=1";
			     if(tDto.getTubeId()!=null){
			    	 sql+=" and a.tubeId="+tDto.getTubeId();
			     }
			 return (int) getCount(sql);
		} catch (RuntimeException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao储罐获取失败");
		}
	}

	@Override
	public void addTubeCleanLog(TubeCleanLog tubeCleanLog) throws OAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTubeCleanLog(TubeCleanLog tubeCleanLog)
			throws OAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTubeCleanLog(String ids) throws OAException {
		// TODO Auto-generated method stub
		
	}

	
}
