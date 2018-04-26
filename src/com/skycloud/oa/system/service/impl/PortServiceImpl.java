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
import com.skycloud.oa.system.dao.PortDao;
import com.skycloud.oa.system.dto.PortDto;
import com.skycloud.oa.system.model.Port;
import com.skycloud.oa.system.service.PortService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class PortServiceImpl implements PortService {
	private static Logger LOG = Logger.getLogger(PortServiceImpl.class);

@Autowired
private PortDao portDao;
@Autowired
private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getPortList(PortDto PortDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(portDao.getList(PortDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, portDao.getCount(PortDto)+"");
			return oaMsg;
		}catch(RuntimeException e){
			LOG.error("查询港口信息失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询港口信息失败");
			LOG.error("获取港口信息失败",e);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", e);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PORT,type=C.LOG_TYPE.UPDATE)
	public OaMsg updatePort(Port port) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			port.setEditUserId(user.getId()) ;
			portDao.updatePort(port);
			baseControllerDao.sendSystemMessage("港口", user.getId(),"BASEINFO",13) ;
		} catch (RuntimeException re) {
			LOG.error("更新港口信息失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新港口信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PORT,type=C.LOG_TYPE.CREATE)
	public OaMsg addPort(Port port) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			port.setEditUserId(user.getId()) ;
			portDao.add(port);
		} catch (RuntimeException re) {
			LOG.error("添加港口信息失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加港口信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PORT,type=C.LOG_TYPE.DELETE)
	public OaMsg deletePort(PortDto portDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			portDao.delete(portDto.getIds());;
		} catch (RuntimeException re) {
			LOG.error("删除港口信息失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除港口信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}


}
