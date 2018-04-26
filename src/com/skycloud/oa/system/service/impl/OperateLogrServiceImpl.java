package com.skycloud.oa.system.service.impl;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.OperateLogDao;
import com.skycloud.oa.system.model.OperateLog;
import com.skycloud.oa.system.service.OperateLogService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class OperateLogrServiceImpl implements OperateLogService {
	private static Logger LOG = Logger.getLogger(OperateLogrServiceImpl.class);

	@Autowired
	private OperateLogDao operateLogDao;

	@Override
	public void get(OperateLog log, PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(operateLogDao.get(log, pageView.getStartRecord(), pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap().put(Constant.TOTALRECORD, operateLogDao.count(log) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("系统日志查询失败", o);
		}
	}

	@Override
	public void create(OperateLog log, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			log.setUser(user);
			log.setIp(SecurityUtils.getSubject().getSession().getHost());
			log.setTime(System.currentTimeMillis()/1000);
			log.setId(operateLogDao.create(log));
			msg.getData().add(log);
		}catch(Exception e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("系统日志保存失败", e);
		}
	}


}
