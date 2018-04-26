package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.ApproveContentDto;
import com.skycloud.oa.system.model.ApproveContent;

public interface ApproveContentDao {

	/**
	 * 查询审批
	 * @author yanyufeng
	 * @param approveContent
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 * 2015-4-1 下午5:59:43
	 */
	public List<Map<String, Object>> getApproveContent(ApproveContent approveContent, int start,int limit)throws OAException;
    
	
	/**
	 * 添加审批
	 * @author yanyufeng
	 * @param ApproveContent
	 * @throws OAException
	 * 2015-4-1 下午5:59:51
	 */
	public void addApproveContent(ApproveContent ApproveContent)throws OAException;


	/**
	 * 更新审批
	 * @author yanyufeng
	 * @param approveContent
	 * @throws OAException
	 * 2015-4-1 下午5:59:58
	 */
	public void update(ApproveContent approveContent)throws OAException;

	/**
	 * 检测是否都审批通过
	 * @author yanyufeng
	 * @param approveContent
	 * @throws OAException
	 * 2015-4-1 下午6:00:05
	 */
	public void check(ApproveContent approveContent)throws OAException;
	/**
	 *清除审核工作流
	 *@author jiahy
	 * @param arrivalId
	 * @throws OAException
	 */
	public void cleanToStatus(Integer arrivalId)throws OAException;


	/**
	 *清除审核工作流信息
	 *@author jiahy
	 * @param approveContentDto
	 */
	public void cleanApproveContent(ApproveContentDto approveContentDto)throws OAException;

}
