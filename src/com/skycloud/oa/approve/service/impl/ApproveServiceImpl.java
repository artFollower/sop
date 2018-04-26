package com.skycloud.oa.approve.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.approve.dao.ApproveDao;
import com.skycloud.oa.approve.model.ApproveCenter;
import com.skycloud.oa.approve.service.ApproveService;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dao.IntentionDao;
import com.skycloud.oa.order.dto.IntentionDto;
import com.skycloud.oa.order.model.Intention;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.DateTimeUtil;
import com.skycloud.oa.utils.OaMsg;

/**
 * @author yanyufeng
 *
 */
@Service
public class ApproveServiceImpl implements ApproveService {

	private static Logger LOG = Logger.getLogger(ApproveServiceImpl.class);
	
	@Autowired
	private ApproveDao approveDao;
	
	
	@Override
	public OaMsg save(ApproveCenter approveCenter) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			
			LOG.debug("开始保存审批信息");
			int id=approveDao.saveApproveCenter(approveCenter);
			Map<String,String> map=new HashMap<String,String>();
			map.put("id", id+"");
			oaMsg.setMap(map);
			LOG.debug("插入成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("插入成功");
		} catch (RuntimeException re) {
			LOG.error("插入失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "插入失败",re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg updateStatus(int id, int status) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			
			LOG.debug("开始更新审批信息");
			approveDao.updateApproveCenterStatus(id, status);
			LOG.debug("更新成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException re) {
			LOG.error("更新失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getApproveListByCondition(ApproveCenter approveCenter,
			PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		int count=0;
		try {
			LOG.debug("开始查询订单意向");
			List<Map<String,Object>> approveList=approveDao.getApproveListByCondition(approveCenter, pageView.getStartRecord(), pageView.getMaxresult());
			count=approveDao.getApproveListCountByCondition(approveCenter);
//			pageView.setTotalrecord(count);
			LOG.debug("查询成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("查询成功");
			oaMsg.getData().addAll(approveList);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "查询失败",re);
		}
		return oaMsg;
	}

	


}
