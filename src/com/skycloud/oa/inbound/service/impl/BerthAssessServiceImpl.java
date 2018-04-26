package com.skycloud.oa.inbound.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.BerthAssessDao;
import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.inbound.service.BerthAssessService;
import com.skycloud.oa.utils.OaMsg;

@Service
public class BerthAssessServiceImpl implements BerthAssessService {

	private static Logger LOG = Logger.getLogger(BerthAssessServiceImpl.class);

	@Autowired
	private BerthAssessDao berthAssessDao;

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTBERTHPROGRAM,type=C.LOG_TYPE.CREATE)
	public void create(BerthAssess berthAssess, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			berthAssess.setId(berthAssessDao.addBerthAssess(berthAssess));
			msg.getData().add(berthAssess);
			msg.setMsg("添加成功");
			LOG.debug("靠泊方案添加成功" + new Gson().toJson(berthAssess));
		} catch (OAException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("靠泊方案创建失败", e);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTBERTHPROGRAM,type=C.LOG_TYPE.DELETE)
	public void delete(String ids, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			berthAssessDao.deleteBerthAssess(ids);
			msg.setMsg("删除成功");
			LOG.debug("成功删除靠泊方案" + ids);
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("删除失败");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_SYS_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	public void getByArrival(String arrivalId, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().add(berthAssessDao.getBerthAssessByArrival(Integer.valueOf(arrivalId)));
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("资源查询失败", o);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.PCS_OUTBERTHPROGRAM,type=C.LOG_TYPE.UPDATE)
	public void update(BerthAssess berthAssess, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			berthAssessDao.updateBerthAssess(berthAssess);
			msg.setMsg("修改成功");
			LOG.debug("修改成功" + new Gson().toJson(berthAssess));
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("修改失败");
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_SYS_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}
}
