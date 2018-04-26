package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.GuestDao;
import com.skycloud.oa.system.dto.GuestDto;
import com.skycloud.oa.system.model.Guest;
import com.skycloud.oa.utils.Common;
@Repository
public class GuestDaoImpl extends BaseDaoImpl implements GuestDao {
	private static Logger LOG = Logger.getLogger(GuestDaoImpl.class);
	@Override
	public List<Map<String, Object>> getGuestList(GuestDto gDto,int start,int limit) throws OAException {
		try {String sql="select * from t_pcs_guest where 1=1 ";
		
		if(!Common.empty(gDto.getId())){
			sql+=" and id="+gDto.getId();
		}
		if(!Common.empty(gDto.getUsername())){
			sql+=" and username= '"+gDto.getUsername()+"'";
		}
		if(limit!=0){
			sql+=" limit "+start+" , "+limit;
		}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}

	@Override
	public void addGuest(Guest guest) throws OAException {
try {
	save(guest);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}	
	}

	@Override
	public void updateGuest(Guest guest) throws OAException {
try {
	update(guest);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}		
	}

	@Override
	public void deleteGuest(String ids) {
try {
	String sql="delete from t_pcs_guest where id in ("+ids+")";
	execute(sql);
} catch (OAException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}		
	}

}
