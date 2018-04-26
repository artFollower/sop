package com.skycloud.oa.system.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.TankCleanLogDao;
import com.skycloud.oa.system.dto.TankCleanLogDto;
import com.skycloud.oa.system.service.TankCleanLogService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class TankCleanLogServiceImpl implements TankCleanLogService {
	private static Logger LOG = Logger.getLogger(TankCleanLogServiceImpl.class);
    @Autowired
	private TankCleanLogDao tankCleanLogDao;
	
	@Override
	public OaMsg getTankCleanLogList(TankCleanLogDto tDto,
			PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(tankCleanLogDao.getTankCleanLogList(tDto, pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, tankCleanLogDao.getCount(tDto)+"");
		} catch (RuntimeException e) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", e);
		}
		return oaMsg;
	}

	

}
