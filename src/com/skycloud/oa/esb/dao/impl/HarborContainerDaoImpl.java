package com.skycloud.oa.esb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.esb.dao.HarborContainerDao;
import com.skycloud.oa.esb.model.HarborContainer;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.Common;

/**
 * 港务管理持久化操作实现类
 * @ClassName: ResourceDaoImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:47:27
 */
@Component
public class HarborContainerDaoImpl extends BaseDaoImpl implements HarborContainerDao {

	@Override
	public int create(HarborContainer harborContainer) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(harborContainer).toString());
	}

	@Override
	public List<Map<String, Object>> get(HarborContainer harborContainer, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_esb_ctn where 1=1";
		if(!Common.isNull(harborContainer.getId())) {
			sql += " and id = "+harborContainer.getId()+"";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(HarborContainer harborContainer) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_esb_ctn where 1=1";
		if(!Common.isNull(harborContainer.getId())) {
			sql += " and id = "+harborContainer.getId()+"";
		}
		return getCount(sql);
	}

	@Override
	public boolean modify(HarborContainer harborContainer) throws OAException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String ids) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_esb_ctn";
		sql += " where id in("+ids+")";
		return executeUpdate(sql) > 0 ? true :false;
	}
	
}
