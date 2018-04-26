/**
 * 
 */
package com.skycloud.oa.approve.service;

import com.skycloud.oa.approve.model.ApproveCenter;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dto.IntentionDto;
import com.skycloud.oa.order.model.Intention;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;


/**
 * @author yanyufeng
 *
 */
public interface ApproveService {
	
	

	/**
	 * 保存审批信息
	 * @author yanyufeng
	 * @param approveCenter
	 * @return
	 * @throws OAException
	 * 2016-2-17 下午7:51:55
	 */
	public abstract OaMsg save(ApproveCenter approveCenter) throws OAException;


	/**
	 * 更新审批状态
	 * @author yanyufeng
	 * @param id
	 * @param status
	 * @return
	 * @throws OAException
	 * 2016-2-17 下午7:52:03
	 */
	public abstract OaMsg updateStatus(int id, int status)throws OAException;


	/**
	 * 查询审批
	 * @author yanyufeng
	 * @param approveCenter
	 * @param pageView
	 * @return
	 * @throws OAException
	 * 2016-2-17 下午7:52:12
	 */
	public abstract OaMsg getApproveListByCondition(
			ApproveCenter approveCenter, PageView pageView)throws OAException;


	
}
