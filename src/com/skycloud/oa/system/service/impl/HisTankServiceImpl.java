package com.skycloud.oa.system.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.HisTankDao;
import com.skycloud.oa.system.dto.HisTank;
import com.skycloud.oa.system.dto.HisTankDto;
import com.skycloud.oa.system.service.HisTankService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class HisTankServiceImpl implements HisTankService {
	private static Logger LOG = Logger.getLogger(HisTankServiceImpl.class);
  @Autowired
  private HisTankDao hisTankDao;
	@Override
	public OaMsg getHisTankList(HisTankDto tDto, PageView pageView)throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(hisTankDao.getHisTankList(tDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, hisTankDao.getCount(tDto)+"");
		} catch (RuntimeException re) {
			LOG.error("历史储罐查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_TANK,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateHisTank(HisTank hisTank) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			hisTankDao.updateHisTank(hisTank);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		} catch (RuntimeException re) {
			LOG.error("历史储罐更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}
}
