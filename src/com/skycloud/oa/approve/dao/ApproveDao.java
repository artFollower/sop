package com.skycloud.oa.approve.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.approve.model.ApproveCenter;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dto.IntentionDto;
import com.skycloud.oa.order.model.Intention;

public interface ApproveDao{


/**
 * 更新审批状态
 * @author yanyufeng
 * @param id
 * @param status
 */
public void updateApproveCenterStatus(int id,int status);

/**
 * 添加审批信息
 * @author yanyufeng
 * @param approveCenter
 * @return
 * @throws OAException
 * 2016-2-17 下午7:44:42
 */
public int saveApproveCenter(ApproveCenter approveCenter)throws OAException;

/**
 * 查询列表
 * @author yanyufeng
 * @param approveCenter
 * @param startRecord
 * @param maxresult
 * @return
 * @throws OAException
 * 2016-2-17 下午7:54:18
 */
public List<Map<String, Object>> getApproveListByCondition(
		ApproveCenter approveCenter, int start, int limit)throws OAException;

/**
 * 列表计数
 * @author yanyufeng
 * @param approveCenter
 * @return
 * @throws OAException
 * 2016-2-17 下午7:54:25
 */
public int getApproveListCountByCondition(ApproveCenter approveCenter)throws OAException;


}
