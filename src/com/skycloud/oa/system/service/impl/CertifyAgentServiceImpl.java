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
import com.skycloud.oa.system.dao.CertifyAgentDao;
import com.skycloud.oa.system.dto.CertifyAgentDto;
import com.skycloud.oa.system.model.CertifyAgent;
import com.skycloud.oa.system.service.CertifyAgentService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class CertifyAgentServiceImpl implements CertifyAgentService {
	private static Logger LOG = Logger.getLogger(CertifyAgentServiceImpl.class);
	@Autowired
	private CertifyAgentDao certifyAgentDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getCertifyAgentList(CertifyAgentDto caDto, PageView pageViewt) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(certifyAgentDao.getCertifyAgentList(caDto, pageViewt.getStartRecord(), pageViewt.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD,certifyAgentDao.getCertifyAgentCount(caDto, pageViewt.getStartRecord(), pageViewt.getMaxresult())+"");
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CERTIFY_AGENT,type=C.LOG_TYPE.CREATE)
	public OaMsg addCertifyAgent(CertifyAgent certifyAgent) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			certifyAgent.setEditUserId(user.getId()) ;
			certifyAgentDao.addCertifyAgent(certifyAgent);
		} catch (OAException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CERTIFY_AGENT,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateCertifyAgent(CertifyAgent certifyAgent){
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			certifyAgent.setEditUserId(user.getId()) ;
			certifyAgentDao.updateCertifyAgent(certifyAgent);
			baseControllerDao.sendSystemMessage("开证单位", user.getId(),"BASEINFO",8) ;
		} catch (OAException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CERTIFY_AGENT,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteCertifyAgent(String certifyAgent) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			certifyAgentDao.deleteCertifyAgent(certifyAgent);
		} catch (OAException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			
		}
		return oaMsg;
	}

}
