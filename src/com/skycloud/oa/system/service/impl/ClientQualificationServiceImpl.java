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
import com.skycloud.oa.system.dao.ClientQualificationDao;
import com.skycloud.oa.system.dto.ClientQualificationDto;
import com.skycloud.oa.system.model.ClientQualification;
import com.skycloud.oa.system.service.ClientQualificationService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ClientQualificationServiceImpl implements
		ClientQualificationService {
	private static Logger LOG = Logger
			.getLogger(ClientQualificationServiceImpl.class);
	@Autowired
	private ClientQualificationDao clientQualificationDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getClientQualificationList(ClientQualificationDto cqDto,
			PageView pageView) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(
					clientQualificationDao.getClientQualificationList(cqDto,
							pageView.getStartRecord(), pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD,clientQualificationDao.getCount(cqDto)+"");
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CLIENT_QUALIFICATION,type=C.LOG_TYPE.CREATE)
	public OaMsg addClientQualification(ClientQualification clientQualification)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			clientQualification.setEditUserId(user.getId()) ;
			clientQualificationDao.addClientQualification(clientQualification);
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CLIENT_QUALIFICATION,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateClientQualification(
			ClientQualification clientQualification) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			clientQualification.setEditUserId(user.getId()) ;
			clientQualificationDao
					.updateClientQualification(clientQualification);
			baseControllerDao.sendSystemMessage("客户资质", user.getId(),"BASEINFO",9) ;
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CLIENT_QUALIFICATION,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteClientQualification(String clientQualification)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			clientQualificationDao
					.deleteClientQualification(clientQualification);
		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

}
