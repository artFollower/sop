package com.skycloud.oa.approve.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.approve.dao.ApproveDao;
import com.skycloud.oa.approve.model.ApproveCenter;
import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dto.IntentionDto;
import com.skycloud.oa.order.model.Intention;
import com.skycloud.oa.utils.Common;

/**
 * @author yanyufeng
 * 
 */
@Component
public class ApproveDaoImpl extends BaseDaoImpl implements ApproveDao{


	@Override
	public int saveApproveCenter(ApproveCenter approveCenter) throws OAException{
		try {
			return (Integer) save(approveCenter);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}


	@Override
	public void updateApproveCenterStatus(int id, int status) {
		
		String sql1="update t_pcs_approve_center a set a.status="+status+",a.reviewTime="+Long.parseLong(new Date().getTime()/1000+"")+" where a.id="+id;
		
		
		String sql="update t_pcs_approve_center a set a.status=4 where a.id<>"+id+" and a.url=(select b.url from (select id,url from t_pcs_approve_center) b where b.id="+id+")";
		
		try {
			execute(sql1);
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public List<Map<String, Object>> getApproveListByCondition(
			ApproveCenter approveCenter, int start, int limit)
			throws OAException {
		String sql="select a.*,b.name createUserName,c.name reviewUserName,FROM_UNIXTIME(a.createTime) mCreateTime,FROM_UNIXTIME(a.reviewTime) mReviewTime from t_pcs_approve_center a LEFT JOIN t_auth_user b on b.id=a.createUserId LEFT JOIN t_auth_user c on c.id=a.reviewUserId where 1=1";
		if (!Common.empty(approveCenter)) {
			if (!Common.empty(approveCenter.getId())
					&& approveCenter.getId() != 0) {
				sql += " and a.id=" + approveCenter.getId();
			}
			if (!Common.empty(approveCenter.getPart())&& approveCenter.getPart() != 0) {
				sql += " and a.part=" + approveCenter.getPart();
			}
			if (!Common.empty(approveCenter.getStatus())&& approveCenter.getStatus() != 0) {
				sql += " and a.status=" +approveCenter.getStatus();
			}
			if (!Common.empty(approveCenter.getCreateUserId())&& approveCenter.getCreateUserId() != 0) {
				sql += " and (a.createUserId=" +approveCenter.getCreateUserId()+" or a.reviewUserId="+approveCenter.getCreateUserId()+") ";
			}
		}
		if (limit == 0) {
			sql += " order by a.createTime DESC ";
		} else {
			sql += " order by a.createTime DESC limit " + start + "," + limit;
		}

		try {
			List<Map<String, Object>> mapList = executeQuery(sql);
			return mapList;
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public int getApproveListCountByCondition(ApproveCenter approveCenter)
			throws OAException {
		String sql="select count(a.id) from t_pcs_approve_center a where 1=1";
		if (!Common.empty(approveCenter)) {
			if (!Common.empty(approveCenter.getId())
					&& approveCenter.getId() != 0) {
				sql += " and a.id=" + approveCenter.getId();
			}
			if (!Common.empty(approveCenter.getPart())&& approveCenter.getPart() != 0) {
				sql += " and a.part=" + approveCenter.getPart();
			}
			if (!Common.empty(approveCenter.getStatus())&& approveCenter.getStatus() != 0) {
				sql += " and a.status=" +approveCenter.getStatus();
			}
			if (!Common.empty(approveCenter.getCreateUserId())&& approveCenter.getCreateUserId() != 0) {
				sql += " and (a.createUserId=" +approveCenter.getCreateUserId()+" or a.reviewUserId="+approveCenter.getCreateUserId()+") ";
			}
		}

		int count = (int) getCount(sql);
		return count;
	}




}
