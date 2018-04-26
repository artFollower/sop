package com.skycloud.oa.system.service.impl;

import java.sql.Timestamp;
import java.util.Date;
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
import com.skycloud.oa.system.dao.ClientDao;
import com.skycloud.oa.system.dao.ClientGroupDao;
import com.skycloud.oa.system.model.ClientGroup;
import com.skycloud.oa.system.service.ClientGroupService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ClientGroupServiceImpl implements ClientGroupService {
	private static Logger LOG = Logger.getLogger(ClientGroupServiceImpl.class);

	@Autowired
	private ClientGroupDao clientGroupDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Autowired
	private ClientDao clientDao;


	@Override
	public OaMsg getClientGroup(String id,String name,String letter, PageView pageView) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(
					clientGroupDao.getClientGroup(id,name,letter,pageView.getStartRecord(),pageView.getMaxresult()));
			int count=clientGroupDao.getClientGroupCount(name,letter);
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
	@Log(object=C.LOG_OBJECT.PCS_CLIENT_GROUP,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateClientGroup(ClientGroup clientGroup) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			clientGroup.setEditUserId(user.getId()) ;
			clientGroupDao.updateClientGroup(clientGroup);
			baseControllerDao.sendSystemMessage("客户组", user.getId(),"BASEINFO",0) ;
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_CLIENT_GROUP,type=C.LOG_TYPE.CREATE)
	public OaMsg addClientGroup(ClientGroup clientGroup) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			clientGroup.setEditUserId(user.getId()) ;
			clientGroupDao.addClientGroup(clientGroup);
			oaMsg.setMsg("添加成功");
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_CLIENT_GROUP,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteClientGroup(String ids) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			clientGroupDao.deleteClientGroup(ids);
			clientDao.removeClientGroup(ids); 
			
			oaMsg.setMsg("删除成功");
		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}


}
