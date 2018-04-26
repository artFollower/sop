package com.skycloud.oa.esb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.esb.dao.HarborDao;
import com.skycloud.oa.esb.model.Harbor;
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
public class HarborDaoImpl extends BaseDaoImpl implements HarborDao {

	@Override
	public int create(Harbor harbor) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(harbor).toString());
	}

	@Override
	public List<Map<String, Object>> get(Harbor harbor, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_esb_harbor where 1=1";
		if(!Common.isNull(harbor.getId())) {
			sql += " and id = "+harbor.getId()+"";
		}
		if(!Common.isNull(harbor.getObjectId())) {
			sql += " and objectId = '"+harbor.getObjectId()+"'";
		}
		if(!Common.empty(harbor.getCatogary())) {
			sql += " and catogary = "+harbor.getCatogary()+"";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(Harbor harbor) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_esb_harbor where 1=1";
		if(!Common.isNull(harbor.getId())) {
			sql += " and id = "+harbor.getId()+"";
		}
		if(!Common.isNull(harbor.getObjectId())) {
			sql += " and objectId = '"+harbor.getObjectId()+"'";
		}
		if(!Common.empty(harbor.getCatogary())) {
			sql += " and catogary = "+harbor.getCatogary()+"";
		}
		return getCount(sql);
	}

	@Override
	public boolean modify(Harbor harbor) throws OAException {
		// TODO Auto-generated method stub
		String sql = "update t_esb_harbor set dischargePortCode='"+harbor.getDischargePortCode()+"',dischargePort='"+
				harbor.getDischargePort()+"',loadPortCode='"+harbor.getLoadPortCode()+"',loadPort='"+harbor.getLoadPort()
				+"',deliveryCode='"+harbor.getDeliveryCode()+"',delivery='"+harbor.getDelivery()+"',grossWeight='"+harbor.getGrossWeight()+
				"' where objectId = "+harbor.getObjectId()+" and catogary ='"+harbor.getCatogary()+"'";
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public boolean delete(String ids,String catogary) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_esb_harbor where objectId in("+ids+") and catogary = '"+catogary+"'";
		return executeUpdate(sql) > 0 ? true :false;
	}

	
	
}
