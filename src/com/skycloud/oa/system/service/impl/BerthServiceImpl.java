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
import com.skycloud.oa.system.dao.BerthDao;
import com.skycloud.oa.system.dto.BerthDto;
import com.skycloud.oa.system.model.Berth;
import com.skycloud.oa.system.service.BerthService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class BerthServiceImpl implements BerthService {
	private static Logger LOG = Logger.getLogger(BerthServiceImpl.class);

	@Autowired
	private BerthDao berthDao;
	@Autowired
	private BaseControllerDao baseControllerDao;

	@Override
	public OaMsg getBerthList(BerthDto berthDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			oaMsg.getData().addAll(berthDao.getList(berthDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD,berthDao.getCount(berthDto)+"");
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
	@Log(object=C.LOG_OBJECT.PCS_BERTH,type=C.LOG_TYPE.CREATE)
	public OaMsg addBerth(Berth berth) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			berth.setEditUserId(user.getId()) ;
			berthDao.add(berth);
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_BERTH,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateBerth(Berth berth) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			berth.setEditUserId(user.getId()) ;
			berthDao.update(berth);
			baseControllerDao.sendSystemMessage("泊位", user.getId(),"BASEINFO",10) ;
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_BERTH,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteBerth(BerthDto berthDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			berthDao.delete(berthDto.getIds());
		} catch (RuntimeException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}


}
