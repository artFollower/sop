package com.skycloud.oa.esb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.esb.dao.HarborShipDao;
import com.skycloud.oa.esb.model.HarborShip;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.Common;

/**
 * 港务提单管理持久化操作实现类
 * @ClassName: ResourceDaoImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:47:27
 */
@Component
public class HarborShipDaoImpl extends BaseDaoImpl implements HarborShipDao {

	@Override
	public int create(HarborShip harborShip) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(harborShip).toString());
	}

	@Override
	public List<Map<String, Object>> get(HarborShip harborShip, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_esb_ship where 1=1";
		if(!Common.isNull(harborShip.getId())) {
			sql += " and id = "+harborShip.getId()+"";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(HarborShip harborShip) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_esb_ship where 1=1";
		if(!Common.isNull(harborShip.getId())) {
			sql += " and id = "+harborShip.getId()+"";
		}
		return getCount(sql);
	}

	@Override
	public boolean modify(HarborShip harborShip) throws OAException {
		// TODO Auto-generated method stub
		this.update(harborShip);
		return true;
	}

	@Override
	public boolean delete(String ids,String catogary) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_esb_harbor where objectId in(select id from t_esb_bill where portId in("+ids+")) and catogary = '"+catogary+"'";
		executeUpdate(sql);
		sql = "delete from t_esb_bill where portId in("+ids+")";
		executeUpdate(sql);
		sql = "delete from t_esb_head where portId in("+ids+")";
		executeUpdate(sql);
		sql = "delete from t_esb_ship where id in("+ids+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	
	
}
