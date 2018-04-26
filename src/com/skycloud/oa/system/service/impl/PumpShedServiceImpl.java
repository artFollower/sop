/**
 * @Title:pumpShedServiceImpl.java
 * @Package com.skycloud.oa.system.service.impl
 * @Description TODO
 * @autor jiahy
 * @date 2016年9月2日下午1:27:48
 * @version V1.0
 */
package com.skycloud.oa.system.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dao.PumpShedDao;
import com.skycloud.oa.system.dto.PumpShedDto;
import com.skycloud.oa.system.model.PumpShed;
import com.skycloud.oa.system.service.PumpShedService;
import com.skycloud.oa.utils.OaMsg;

/**
 * @ClassName pumpShedServiceImpl
 * @Description TODO
 * @author jiahy
 * @date 2016年9月2日下午1:27:48
 */
@Service
public class PumpShedServiceImpl implements PumpShedService {
	private static Logger LOG = Logger.getLogger(PumpShedServiceImpl.class);

	@Autowired
	private PumpShedDao pumpShedDao;
	
	/**
	 * @Title getPumpShedList
	 * @Descrption:TODO
	 * @param:@param psDto
	 * @param:@param pageView
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:32:32
	 * @throws
	 */
	@Override
	public OaMsg getPumpShedList(PumpShedDto psDto, PageView pageView) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(pumpShedDao.getPumpShedList(psDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, pumpShedDao.getPumpShedListCount(psDto)+"");
		} catch (RuntimeException re) {
			LOG.error("泵棚列表查询失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "泵棚列表查询失败", re);
		}
		return oaMsg;
	}

	/**
	 * @Title updatePumpShed
	 * @Descrption:TODO
	 * @param:@param pumpShed
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:32:32
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_PUMP_SHED,type=C.LOG_TYPE.UPDATE)
	public OaMsg updatePumpShed(PumpShed pumpShed) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			pumpShedDao.updatePumpShed(pumpShed);
		} catch (RuntimeException re) {
			LOG.error("泵棚添加失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "泵棚添加失败", re);
		}
		return oaMsg;
	}

	/**
	 * @Title addPumpShed
	 * @Descrption:TODO
	 * @param:@param pumpShed
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:32:32
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_PUMP_SHED,type=C.LOG_TYPE.CREATE)
	public OaMsg addPumpShed(PumpShed pumpShed) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getMap().put("id", pumpShedDao.addPumpShed(pumpShed)+"");
		} catch (RuntimeException re) {
			LOG.error("泵棚修改失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "泵棚修改失败", re);
		}
		return oaMsg;
	}

	/**
	 * @Title deletePumpShed
	 * @Descrption:TODO
	 * @param:@param psDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:32:32
	 * @throws
	 */
	@Override
	@Log(object=C.LOG_OBJECT.PCS_PUMP_SHED,type=C.LOG_TYPE.DELETE)
	public OaMsg deletePumpShed(PumpShedDto psDto) throws OAException {
		OaMsg oaMsg = new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			pumpShedDao.deletePumpShed(psDto);
		} catch (RuntimeException re) {
			LOG.error("泵棚删除失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "泵棚删除失败", re);
		}
		return oaMsg;
	}
	
	
}
