package com.skycloud.oa.system.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.TankTypeDao;
import com.skycloud.oa.system.service.TankTypeService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class TankTypeServiceImpl implements TankTypeService {
	private static Logger LOG = Logger.getLogger(TankTypeServiceImpl.class);

	@Autowired
	private TankTypeDao tankTypeDao;

	@Override
	public OaMsg getTankTypeList() throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(tankTypeDao.getTankTypeList());
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

}
