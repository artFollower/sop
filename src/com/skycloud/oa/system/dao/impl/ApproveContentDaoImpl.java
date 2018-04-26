package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.ApproveContentDao;
import com.skycloud.oa.system.dao.PumpDao;
import com.skycloud.oa.system.dto.ApproveContentDto;
import com.skycloud.oa.system.dto.PumpDto;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.system.model.Pump;
import com.skycloud.oa.utils.Common;

@Repository
public class ApproveContentDaoImpl extends BaseDaoImpl implements ApproveContentDao {
	private static Logger LOG = Logger.getLogger(ApproveContentDaoImpl.class);

	

	@Override
	public List<Map<String, Object>> getApproveContent(
			ApproveContent approveContent, int start, int limit)
			throws OAException {
		try{
			String sql="select a.*,b.name userName ,from_unixtime(a.reviewTime) mReviewTime from t_pcs_approvecontent a LEFT JOIN t_auth_user b on a.userId=b.id where 1=1";
			if(approveContent.getType()!=null){
				sql+=" and a.type="+approveContent.getType();
			}
			if(approveContent.getTypeStatus()!=null){
				sql+=" and a.typeStatus="+approveContent.getTypeStatus();
			}
			if(approveContent.getWorkId()!=null){
				sql+=" and a.workId="+approveContent.getWorkId();
			}
			if(approveContent.getUserId()!=null){
				sql+=" and a.userId="+approveContent.getUserId();
			}
//			sql+=" limit 0,1";
		return executeQuery(sql);
	} catch (RuntimeException e) {
		LOG.error("dao查询park失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询park失败", e);
	}
}

	@Override
	public void addApproveContent(ApproveContent approveContent)throws OAException{
	try {
		save(approveContent);
	} catch (RuntimeException e) {
		LOG.error("dao添加失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加失败", e);
	}
	}

	@Override
	public void update(ApproveContent approveContent) throws OAException {
		
		String sql="update t_pcs_approvecontent set id=id";
		if(!Common.empty(approveContent.getContent())){
			sql+=" , content='"+approveContent.getContent()+"'";
		}
		if(!Common.isNull(approveContent.getReviewTime())){
			sql+=" , reviewTime='"+approveContent.getReviewTime()+"'";
		}
		if(approveContent.getTypeStatus()!=null){
			sql+=" , typeStatus="+approveContent.getTypeStatus();
		}
		sql+=" where workId="+approveContent.getWorkId()+" and type="+approveContent.getType()+" and userId="+approveContent.getUserId();
		try {
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新失败", e);
		}
		
	}

	@Override
	public void check(ApproveContent approveContent) throws OAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanToStatus(Integer arrivalId) throws OAException {
		try {
			String sql="delete from t_pcs_approvecontent where workId in (select a.id from t_pcs_berth_assess a where a.arrivalId="+arrivalId+")";
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao工作流退回失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao工作流退回失败", e);
		}
	}

	@Override
	public void cleanApproveContent(ApproveContentDto approveContentDto)
			throws OAException {
		try {
			String sql="delete from t_pcs_approvecontent where workId="+approveContentDto.getTypeId()+" and type="+approveContentDto.getType();
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao工作流退回失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao工作流退回失败", e);
		}
	}

}
