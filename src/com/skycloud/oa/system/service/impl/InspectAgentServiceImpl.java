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
import com.skycloud.oa.system.dao.InspectAgentDao;
import com.skycloud.oa.system.dto.InspectAgentDto;
import com.skycloud.oa.system.model.InspectAgent;
import com.skycloud.oa.system.service.InspectAgentService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class InspectAgentServiceImpl implements InspectAgentService {
	private static Logger LOG = Logger.getLogger(InspectAgentServiceImpl.class);

	@Autowired
	private InspectAgentDao inpectAgentDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getInpectAgentList(InspectAgentDto iaDto, PageView pageView)
			 {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(inpectAgentDao.getInpectAgentList(iaDto, pageView.getStartRecord(), pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD,inpectAgentDao.getInpectAgentCount(iaDto,  pageView.getStartRecord(), pageView.getMaxresult())+"");
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("查询失败");
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INSPECT_AGENT,type=C.LOG_TYPE.CREATE)
	public OaMsg addInpectAgent(InspectAgent inpectAgent)  {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			inpectAgent.setEditUserId(user.getId()) ;
			inpectAgentDao.addInpectAgent(inpectAgent);
		} catch (OAException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INSPECT_AGENT,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateInpectAgent(InspectAgent inpectAgent)  {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			inpectAgent.setEditUserId(user.getId()) ;
			inpectAgentDao.updateInpectAgent(inpectAgent);
			baseControllerDao.sendSystemMessage("商检单位", user.getId(),"BASEINFO",7) ;
		} catch (OAException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_INSPECT_AGENT,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteInpectAgent(String inpectAgent)  {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			inpectAgentDao.deleteInpectAgent(inpectAgent);
		} catch (OAException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			
		}
		return oaMsg;
	}

}
