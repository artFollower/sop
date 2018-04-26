package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dto.ApproveContentDto;
import com.skycloud.oa.system.model.ApproveContent;
import com.skycloud.oa.utils.OaMsg;

public interface ApproveContentService {

 /**
 *查询审批信息
 *@author jiahy
 * @param approveContent
 * @return
 * @throws OAException
 */
public	OaMsg getApproveContent(ApproveContent approveContent) throws OAException;

/**
 *提交
 *@author jiahy
 * @param approveContentDto
 * @return
 * @throws OAException
 */
public OaMsg sendCheck(ApproveContentDto approveContentDto) throws OAException;

/**
 *抄送
 *@author jiahy
 * @param approveContentDto
 * @return
 * @throws OAException
 */
public OaMsg sendCopy(ApproveContentDto approveContentDto) throws OAException;

/**
 *获取缓存
 *@author jiahy
 * @param id
 * @param workType
 * @return
 * @throws OAException
 */
public OaMsg checkcache(int id, int workType) throws OAException;

/**
 *清除工作流信息
 *@author jiahy
 * @param approveContentDto
 * @return
 * @throws OAException
 */
public OaMsg cleanApproveContentMsg(ApproveContentDto approveContentDto) throws OAException;

}
