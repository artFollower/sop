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
import com.skycloud.oa.system.dao.TubeDao;
import com.skycloud.oa.system.dto.TubeDto;
import com.skycloud.oa.system.model.Tube;
import com.skycloud.oa.system.service.TubeService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class TubeServiceImpl implements TubeService {
	private static Logger LOG = Logger.getLogger(TubeServiceImpl.class);

	@Autowired
	private TubeDao tubeDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getTubeList(TubeDto tubeDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			oaMsg.getData().addAll(tubeDao.getList(tubeDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, tubeDao.getCount(tubeDto)+"");
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
	@Log(object=C.LOG_OBJECT.PCS_TUBE,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateTube(Tube tube) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			tube.setEditUserId(user.getId()) ;
			tubeDao.update(tube);
			baseControllerDao.sendSystemMessage("管线", user.getId(),"BASEINFO",11) ;
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_TUBE,type=C.LOG_TYPE.CREATE)
	public OaMsg addTube(Tube tube) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			tube.setEditUserId(user.getId()) ;
			tubeDao.add(tube);
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	public OaMsg deleteTube(TubeDto tubeDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			tubeDao.delete(tubeDto.getIds());;
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}


}
