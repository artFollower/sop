package com.skycloud.oa.system.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.GuestDao;
import com.skycloud.oa.system.dto.GuestDto;
import com.skycloud.oa.system.model.Guest;
import com.skycloud.oa.system.service.GuestService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class GuestServiceImpl implements GuestService {
	private static Logger LOG = Logger.getLogger(GuestServiceImpl.class);

	@Autowired
	private GuestDao guestDao;

	@Override
	public OaMsg getGuestList(GuestDto gDto, int start, int limit)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(guestDao.getGuestList(gDto, start, limit));
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg addGuest(Guest guest) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			guestDao.addGuest(guest);
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg updateGuest(Guest guest) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			guestDao.updateGuest(guest);
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg deleteGuest(String guest) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			guestDao.deleteGuest(guest);
		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

}
