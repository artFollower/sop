package com.skycloud.oa.esb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.esb.dao.EsbShipDao;
import com.skycloud.oa.esb.model.EsbBerth;
import com.skycloud.oa.esb.model.EsbShip;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.Common;

/**
 * 港务船管理持久化操作实现类
 * @ClassName: ResourceDaoImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:47:27
 */
@Component
public class EsbShipDaoImpl extends BaseDaoImpl implements EsbShipDao {

	@Override
	public int create(EsbShip ship) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(ship).toString());
	}

	@Override
	public List<Map<String, Object>> get(EsbShip ship, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM t_esb_esbship WHERE 1 = 1 ";
		if(!Common.isNull(ship.getId())) {
			sql += " and id = "+ship.getId()+"";
		}
		if(!Common.empty(ship.getEnglishName())) {
			sql += " and englishName like '%"+ship.getEnglishName()+"%'";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(EsbShip ship) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT count(1) FROM t_esb_esbship WHERE 1 = 1 ";
		if(!Common.isNull(ship.getId())) {
			sql += " and id = "+ship.getId()+"";
		}
		if(!Common.empty(ship.getEnglishName())) {
			sql += " and englishName like '%"+ship.getEnglishName()+"%'";
		}
		return getCount(sql);
	}

	@Override
	public void create(EsbBerth berth) {
		// TODO Auto-generated method stub
		try {
			save(berth);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
