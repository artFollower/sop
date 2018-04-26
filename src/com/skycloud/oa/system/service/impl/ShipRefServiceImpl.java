package com.skycloud.oa.system.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.ShipRefDao;
import com.skycloud.oa.system.model.ShipRef;
import com.skycloud.oa.system.service.ShipRefService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class ShipRefServiceImpl implements ShipRefService {
	private static Logger LOG = Logger.getLogger(ShipRefServiceImpl.class);

	@Autowired
	private ShipRefDao shipRefDao;



	@Override
	public OaMsg getShipRefList(int shipId, PageView pageView)
			throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(
					shipRefDao.getShipRefList(shipId,0, 0));
		} catch (RuntimeException re) {
			LOG.error("查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "数据库连接超时", re);
		}
		return oaMsg;
	}



	@Override
	public OaMsg addShip(ShipRef shipRef) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			shipRefDao.addShipRef(shipRef);
		} catch (OAException re) {
			LOG.error("添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			oaMsg.setMsg("添加失败");
			
		}
		return oaMsg;
	}


}
