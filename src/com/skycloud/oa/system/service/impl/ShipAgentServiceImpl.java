package com.skycloud.oa.system.service.impl;

import java.util.HashMap;
import java.util.Map;

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
import com.skycloud.oa.system.dao.ShipAgentDao;
import com.skycloud.oa.system.dto.ShipAgentDto;
import com.skycloud.oa.system.model.ShipAgent;
import com.skycloud.oa.system.service.ShipAgentService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ShipAgentServiceImpl implements ShipAgentService {
	private static Logger LOG = Logger.getLogger(ShipAgentServiceImpl.class);

	@Autowired
	private ShipAgentDao shipAgentDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getShipAgentList(ShipAgentDto saDto, PageView pageView)
			{
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(shipAgentDao.getShipAgentList(saDto,pageView.getStartRecord(), pageView.getMaxresult()));
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage() + "");
			map.put("totalpage", pageView.getTotalpage() + "");
			map.put("totalRecord",shipAgentDao.getShipAgentCount(saDto, pageView.getStartRecord(), pageView.getMaxresult())+ "");
			oaMsg.setMap(map);
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_SHIP_AGENT,type=C.LOG_TYPE.CREATE)
	public OaMsg addShipAgent(ShipAgent shipAgent) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			shipAgent.setEditUserId(user.getId()) ;
			shipAgentDao.addShipAgent(shipAgent);
		} catch (OAException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_SHIP_AGENT,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateShipAgent(ShipAgent shipAgent) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			shipAgent.setEditUserId(user.getId()) ;
			shipAgentDao.updateShipAgent(shipAgent);
			baseControllerDao.sendSystemMessage("船代", user.getId(),"BASEINFO",5) ;
		} catch (OAException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_SHIP_AGENT,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteShipAgent(String shipAgent) {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			shipAgentDao.deleteShipAgent(shipAgent);
		} catch (OAException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			
		}
		return oaMsg;
	}

}
