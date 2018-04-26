package com.skycloud.oa.esb.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.esb.dao.EsbShipDao;
import com.skycloud.oa.esb.model.EsbShip;
import com.skycloud.oa.esb.service.EsbShipService;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 港务船舶业务逻辑处理接口实现类
 * @ClassName: HarborShipServiceImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午5:37:49
 */
@Service
public class EsbShipServiceImpl implements EsbShipService {

	private static Logger LOG = Logger.getLogger(EsbShipServiceImpl.class);
	
	@Autowired
	private EsbShipDao esbShipDao;

	@Override
	public void get(EsbShip esbShip, PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(esbShipDao.get(esbShip, pageView.getStartRecord(), pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap().put(Constant.TOTALRECORD, esbShipDao.count(esbShip) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("货物信息查询失败", o);
		}
	}
	
}
