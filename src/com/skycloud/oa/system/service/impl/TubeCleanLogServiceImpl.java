package com.skycloud.oa.system.service.impl;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.common.dao.BaseControllerDao;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.TankDao;
import com.skycloud.oa.system.dao.TubeCleanLogDao;
import com.skycloud.oa.system.dto.TankDto;
import com.skycloud.oa.system.dto.TubeCleanLogDto;
import com.skycloud.oa.system.model.Tank;
import com.skycloud.oa.system.service.TankService;
import com.skycloud.oa.system.service.TubeCleanLogService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class TubeCleanLogServiceImpl implements TubeCleanLogService {
	private static Logger LOG = Logger.getLogger(TubeCleanLogServiceImpl.class);
 @Autowired
 private TubeCleanLogDao tubeCleanLogDao;
	
	@Override
	public OaMsg getTubeCleanLogList(TubeCleanLogDto tDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(tubeCleanLogDao.getTubeCleanLogList(tDto, pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, tubeCleanLogDao.getCount(tDto)+"");
		} catch (RuntimeException e) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", e);
		}
		return oaMsg;
	}

	
}
