package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.ApproveContentDao;
import com.skycloud.oa.system.dao.ApproveWorkDao;
import com.skycloud.oa.system.dao.PumpDao;
import com.skycloud.oa.system.dto.PumpDto;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.system.model.ApproveWork;
import com.skycloud.oa.system.model.Pump;
import com.skycloud.oa.utils.Common;

@Repository
public class ApproveWorkDaoImpl extends BaseDaoImpl implements ApproveWorkDao {
	private static Logger LOG = Logger.getLogger(ApproveWorkDaoImpl.class);

	

	@Override
	public List<Map<String, Object>> getApproveWork(
			ApproveWork approveWork)
			throws OAException {
		try{
			String sql="select * from t_pcs_approvework where 1=1";
			if(approveWork.getType()!=null){
				sql+=" and type="+approveWork.getType();
			}
			if(approveWork.getTypeStatus()!=null){
				sql+=" and typeStatus="+approveWork.getTypeStatus();
			}
		return executeQuery(sql);
	} catch (RuntimeException e) {
		LOG.error("dao查询park失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询park失败", e);
	}
}

}
