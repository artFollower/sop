package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.OaMsg;

public interface OfficeGradeService {

	/**
	 * 获取批文等级信息
	 * 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getOfficeGradeList() throws OAException;
}
