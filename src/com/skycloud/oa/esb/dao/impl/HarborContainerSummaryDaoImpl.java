package com.skycloud.oa.esb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.esb.dao.HarborContainerSummaryDao;
import com.skycloud.oa.esb.model.HarborContainerSummary;
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
public class HarborContainerSummaryDaoImpl extends BaseDaoImpl implements HarborContainerSummaryDao {

	@Override
	public int create(HarborContainerSummary harborContainerSummary) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(harborContainerSummary).toString());
	}

	@Override
	public List<Map<String, Object>> get(HarborContainerSummary harborContainerSummary, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_esb_bill where 1=1";
		if(!Common.isNull(harborContainerSummary.getId())) {
			sql += " and id = "+harborContainerSummary.getId()+"";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(HarborContainerSummary harborContainerSummary) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_esb_bill where 1=1";
		if(!Common.isNull(harborContainerSummary.getId())) {
			sql += " and id = "+harborContainerSummary.getId()+"";
		}
		return getCount(sql);
	}

	@Override
	public boolean modify(HarborContainerSummary harborContainerSummary) throws OAException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(String ids) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_esb_bill";
		sql += " where id in("+ids+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	
	
}
