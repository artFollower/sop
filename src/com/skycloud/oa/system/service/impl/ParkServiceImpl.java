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
import com.skycloud.oa.system.dao.ParkDao;
import com.skycloud.oa.system.dto.ParkDto;
import com.skycloud.oa.system.model.Park;
import com.skycloud.oa.system.service.ParkService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ParkServiceImpl implements ParkService {
	private static Logger LOG = Logger.getLogger(ParkServiceImpl.class);

	@Autowired
	private ParkDao parkDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getParkList(ParkDto parkDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try{
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			
			oaMsg.getData().addAll(parkDao.getParkList(parkDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, parkDao.getCount(parkDto)+"");
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
	@Log(object=C.LOG_OBJECT.PCS_PARK,type=C.LOG_TYPE.UPDATE)
	public OaMsg updatePark(Park park) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			park.setEditUserId(user.getId()) ;
			parkDao.updatePark(park);
			baseControllerDao.sendSystemMessage("车位", user.getId(),"BASEINFO",12) ;
		} catch (RuntimeException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PARK,type=C.LOG_TYPE.CREATE)
	public OaMsg addPark(Park park) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			park.setEditUserId(user.getId()) ;
			parkDao.addPark(park);
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_PARK,type=C.LOG_TYPE.DELETE)
	public OaMsg deletePark(ParkDto parkDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			parkDao.deletePark(parkDto.getIds());;
		} catch (RuntimeException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}


}
