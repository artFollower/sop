package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.OaMsg;


/**
 * 客户等级
 * @author yanyufeng
 *
 */
public interface ClientGradeService {

	/**
	 * 获取状态客户等级列表
	 * 
	 * @return
	 */
	public abstract OaMsg getClientGrade() throws OAException;
}
