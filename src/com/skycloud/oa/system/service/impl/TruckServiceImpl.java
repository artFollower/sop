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
import com.skycloud.oa.system.dao.TruckDao;
import com.skycloud.oa.system.dto.TruckDto;
import com.skycloud.oa.system.model.Truck;
import com.skycloud.oa.system.service.TruckService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class TruckServiceImpl implements TruckService {
	private static Logger LOG = Logger.getLogger(TruckServiceImpl.class);

	@Autowired
	private TruckDao truckDao;
	@Autowired
	private BaseControllerDao baseControllerDao;
	@Override
	public OaMsg getTruckList(TruckDto tDto,PageView pageView)
			 {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(truckDao.getTruckList(tDto, pageView.getStartRecord(), pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD,  truckDao.getTruckCount(tDto,  pageView.getStartRecord(), pageView.getMaxresult())+ "");
		} catch (OAException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_TRUCK,type=C.LOG_TYPE.CREATE)
	public OaMsg addTruck(Truck truck)  {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			truck.setEditUserId(user.getId()) ;
			truckDao.addTruck(truck);
		} catch (OAException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_TRUCK,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateTruck(Truck truck)  {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			truck.setEditUserId(user.getId()) ;
			truckDao.updateTruck(truck);
			baseControllerDao.sendSystemMessage("车辆", user.getId(),"BASEINFO",6) ;
		} catch (OAException re) {
			LOG.error("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("更新失败");
			
		}
		return oaMsg;
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_TRUCK,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteTruck(String truck)  {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			truckDao.deleteTruck(truck);
		} catch (OAException re) {
			LOG.error("删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("删除失败");
			
		}
		return oaMsg;
	}

}
