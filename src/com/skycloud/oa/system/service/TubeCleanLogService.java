package com.skycloud.oa.system.service;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.TubeCleanLogDto;
import com.skycloud.oa.utils.OaMsg;


/**
 *管线清洗记录
 * @author 作者:jiahy
 * @version 时间：2015年4月2日 上午10:59:06
 */
public interface TubeCleanLogService {
	
	/**
	 *获取管线清洗记录
	 *@author jiahy
	 * @param tDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getTubeCleanLogList(TubeCleanLogDto tDto,PageView pageView) throws OAException;
}
