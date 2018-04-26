package com.skycloud.oa.system.service.impl;

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
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.PumpDao;
import com.skycloud.oa.system.dto.PumpDto;
import com.skycloud.oa.system.model.Pump;
import com.skycloud.oa.system.service.PumpService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class PumpServiceImpl implements PumpService {
	private static Logger LOG = Logger.getLogger(PumpServiceImpl.class);

	@Autowired
	private PumpDao PumpDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getPumpList(PumpDto pumpDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			oaMsg.getData().addAll(PumpDao.getPumpList(pumpDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, PumpDao.getCount(pumpDto)+"");
			return oaMsg;
		}catch(RuntimeException e){
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			LOG.error("获取泊位信息失败",e);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", e);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PUMP,type=C.LOG_TYPE.UPDATE)
	public OaMsg updatePump(Pump pump) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			pump.setEditUserId(user.getId()) ;
			PumpDao.updatePump(pump);
			baseControllerDao.sendSystemMessage("泵", user.getId(),"BASEINFO",15) ;
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PUMP,type=C.LOG_TYPE.CREATE)
	public OaMsg addPump(Pump pump) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			pump.setEditUserId(user.getId()) ;
			PumpDao.addPump(pump);
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PUMP,type=C.LOG_TYPE.DELETE)
	public OaMsg deletePump(PumpDto pumpDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			PumpDao.deletePump(pumpDto.getIds());;
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}


}
