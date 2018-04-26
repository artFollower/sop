package com.skycloud.oa.system.service;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.TankCleanLogDao;
import com.skycloud.oa.system.dto.TankCleanLogDto;
import com.skycloud.oa.system.dto.TankDto;
import com.skycloud.oa.system.model.Tank;
import com.skycloud.oa.utils.OaMsg;


/**
 *储罐清洗记录
 * @author 作者:jiahy
 * @version 时间：2015年4月2日 上午10:59:06
 */
public interface TankCleanLogService {
	
	/**
	 *获取储罐清洗记录
	 *@author jiahy
	 * @param tDto
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getTankCleanLogList(TankCleanLogDto tDto,PageView pageView) throws OAException;
}
