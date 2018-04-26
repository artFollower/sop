package com.skycloud.oa.system.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.common.dao.BaseControllerDao;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.TankLogDao;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.TankDao;
import com.skycloud.oa.system.dto.TankDto;
import com.skycloud.oa.system.model.Tank;
import com.skycloud.oa.system.service.TankService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class TankServiceImpl implements TankService {
	private static Logger LOG = Logger.getLogger(TankServiceImpl.class);

	@Autowired
	private TankDao tankDao;
	@Autowired
	private TankLogDao tankLogDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getTankList(TankDto tDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(tankDao.getTankList(tDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, tankDao.getCount(tDto)+"");
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_TANK,type=C.LOG_TYPE.CREATE)
	public OaMsg addTank(Tank tank) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			tank.setEditUserId(user.getId()) ;
			tankDao.addTank(tank);
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_TANK,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateTank(Tank tank) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			tankDao.addTankUpdateLog(tank.getId());
			
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			tank.setEditUserId(user.getId()) ;
			tank.setEditTime(new Timestamp(new Date().getTime()));
			tankDao.updateTank(tank);
			baseControllerDao.sendSystemMessage("罐", user.getId(),"BASEINFO",14) ;
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_TANK,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteTank(String tankIds) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			//删除储罐台账
			tankLogDao.deleteTankLogByTankId(tankIds);
			
			//删除储罐
			tankDao.deleteTank(tankIds);
		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg getUpdateTankLogList(TankDto tankDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(tankDao.getUpdateTankLog(tankDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, tankDao.getUpdateTankLogCount(tankDto)+"");
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

}
