package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.ApproveCacheDao;
import com.skycloud.oa.system.dao.ApproveContentDao;
import com.skycloud.oa.system.dao.PumpDao;
import com.skycloud.oa.system.dto.PumpDto;
import com.skycloud.oa.system.model.ApproveCache;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.system.model.Pump;
import com.skycloud.oa.utils.Common;

@Repository
public class ApproveCacheDaoImpl extends BaseDaoImpl implements ApproveCacheDao {
	private static Logger LOG = Logger.getLogger(ApproveCacheDaoImpl.class);

	@Override
	public List<Map<String, Object>> getApproveCache(ApproveCache approveCache)
			throws OAException {
		try{
			String sql="select * from t_pcs_approvecache where 1=1";
			if(approveCache.getUserId()!=null){
				sql+=" and userId="+approveCache.getUserId();
			}
			if(!Common.isNull(approveCache.getType())){
				sql+=" and type="+approveCache.getType();
			}
			if(!Common.isNull(approveCache.getWorkType())){
				sql+=" and workType="+approveCache.getWorkType();
			}
		return executeQuery(sql);
	} catch (RuntimeException e) {
		LOG.error("dao查询park失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询park失败", e);
	}
	}

	@Override
	public void addApproveCache(ApproveCache approveCache) throws OAException {
		try {
			save(approveCache);
		} catch (RuntimeException e) {
			LOG.error("dao添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加失败", e);
		}
		
	}

	@Override
	public void update(ApproveCache approveCache) throws OAException {
		String sql="update t_pcs_approvecache set cacheUser='"+approveCache.getCacheUser()+"' where userId="+approveCache.getUserId()+" and type="+approveCache.getType()+" and workType="+approveCache.getWorkType();
		try {
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新失败", e);
		}
		
	}

}
