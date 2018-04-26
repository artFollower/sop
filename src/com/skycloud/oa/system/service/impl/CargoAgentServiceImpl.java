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
import com.skycloud.oa.system.dao.CargoAgentDao;
import com.skycloud.oa.system.dto.CargoAgentDto;
import com.skycloud.oa.system.model.CargoAgent;
import com.skycloud.oa.system.service.CargoAgentService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class CargoAgentServiceImpl implements CargoAgentService {
	private static Logger LOG = Logger.getLogger(CargoAgentServiceImpl.class);

	@Autowired
	private CargoAgentDao cargoagentDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getCargoAgentList(CargoAgentDto caDto, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(
					cargoagentDao.getCargoAgentList(caDto, pageView.getStartRecord(),pageView.getMaxresult()));
			int count=cargoagentDao.getCargoAgentListCount(caDto);
			Map<String, String> map = new HashMap<String, String>();
			map.put("currentPage", pageView.getCurrentpage()+"");
			map.put("totalpage", pageView.getTotalpage()+"");
			map.put("totalRecord", count + "");
			oaMsg.setMap(map);
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO_AGENT,type=C.LOG_TYPE.CREATE)
	public OaMsg addCargoAgent(CargoAgent cargoAgent) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			cargoAgent.setEditUserId(user.getId()) ;
			cargoagentDao.addCargoAgent(cargoAgent);

		} catch (RuntimeException re) {
			LOG.error("添加失败", re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO_AGENT,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateCargoAgent(CargoAgent cargoAgent) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			cargoAgent.setEditUserId(user.getId()) ;
			cargoagentDao.updateCargoAgent(cargoAgent);
			baseControllerDao.sendSystemMessage("货代", user.getId(),"BASEINFO",3) ;
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_CARGO_AGENT,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteCargoAgent(String cargoAgent) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			cargoagentDao.deleteCargoAgent(cargoAgent);

		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}
}
