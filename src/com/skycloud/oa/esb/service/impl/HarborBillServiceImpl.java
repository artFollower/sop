package com.skycloud.oa.esb.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.esb.dao.HarborBillDao;
import com.skycloud.oa.esb.dao.HarborDao;
import com.skycloud.oa.esb.dto.BulkDto;
import com.skycloud.oa.esb.model.Harbor;
import com.skycloud.oa.esb.model.HarborBill;
import com.skycloud.oa.esb.service.HarborBillService;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 货物业务逻辑处理接口实现类
 * @ClassName: HarborShipServiceImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午5:37:49
 */
@Service
public class HarborBillServiceImpl implements HarborBillService {

	private static Logger LOG = Logger.getLogger(HarborBillServiceImpl.class);
	
	@Autowired
	private HarborBillDao harborBillDao;
	
	@Autowired
	private HarborDao harborDao;

	@Override
	public void get(HarborBill harborBill, PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(harborBillDao.get(harborBill, pageView.getStartRecord(), pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap().put(Constant.TOTALRECORD, harborBillDao.count(harborBill) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("货物信息查询失败", o);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.ESB_BILL,type=C.LOG_TYPE.UPDATE)
	public void update(BulkDto discharge, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			HarborBill harborBill = discharge.getHarborBill();
			if (harborBillDao.modify(harborBill) && harborDao.modify(discharge.getHarbor())) {
				msg.setMsg("货物信息修改成功");
				LOG.debug("货物信息成功修改"+ new Gson().toJson(discharge));
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("货物信息不存在");
				LOG.debug("货物信息不存在:" +  new Gson().toJson(discharge));
			}
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.ESB_BILL,type=C.LOG_TYPE.DELETE)
	public void delete(String ids,String catogary, OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			if(harborBillDao.delete(ids) && harborDao.delete(ids, catogary)) {
				msg.setMsg("删除成功");
				LOG.debug("成功删除港务信息"+ids);
			}else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("删除的对象不存在");
				LOG.debug("删除的港务信息不存在:"+ids);
			}
		}catch(OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.ESB_BILL,type=C.LOG_TYPE.CREATE)
	public void save(BulkDto discharge, OaMsg msg) {
		// TODO Auto-generated method stub
		try{
			HarborBill harborBill = discharge.getHarborBill();
			harborBill.setId(harborBillDao.create(harborBill));//保存港务提单
			
			Harbor harbor = discharge.getHarbor();
			harbor.setObjectId(harborBill.getId());
			harbor.setId(harborDao.create(harbor));
			
			msg.setMsg("添加成功");
			LOG.debug("添加成功");
		}catch(OAException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("添加港务信息失败", e);
		}
	}
	
}
