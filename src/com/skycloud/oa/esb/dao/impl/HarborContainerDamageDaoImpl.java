package com.skycloud.oa.esb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.esb.dao.HarborContainerDamageDao;
import com.skycloud.oa.esb.model.HarborContainerDamage;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.Common;

/**
 * 残损信息管理持久化操作实现类
 * @ClassName: ResourceDaoImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:47:27
 */
@Component
public class HarborContainerDamageDaoImpl extends BaseDaoImpl implements HarborContainerDamageDao {

	@Override
	public int create(HarborContainerDamage harborContainerDamage) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(harborContainerDamage).toString());
	}

	@Override
	public List<Map<String, Object>> get(HarborContainerDamage harborContainerDamage, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_esb_damage where 1=1";
		if(!Common.isNull(harborContainerDamage.getId())) {
			sql += " and id = "+harborContainerDamage.getId()+"";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(HarborContainerDamage harborContainerDamage) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_esb_damage where 1=1";
		if(!Common.isNull(harborContainerDamage.getId())) {
			sql += " and id = "+harborContainerDamage.getId()+"";
		}
		return getCount(sql);
	}

	@Override
	public boolean modify(HarborContainerDamage harborContainerDamage) throws OAException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String ids) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_esb_damage";
		sql += " where id in("+ids+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	
	
}
