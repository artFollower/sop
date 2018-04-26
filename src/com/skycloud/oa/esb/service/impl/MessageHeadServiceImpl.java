package com.skycloud.oa.esb.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.esb.dao.MessageHeadDao;
import com.skycloud.oa.esb.model.MessageHead;
import com.skycloud.oa.esb.service.MessageHeadService;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 消息头业务逻辑处理接口实现类
 * @ClassName: HarborShipServiceImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午5:37:49
 */
@Service
public class MessageHeadServiceImpl implements MessageHeadService {

	private static Logger LOG = Logger.getLogger(MessageHeadServiceImpl.class);
	
	@Autowired
	private MessageHeadDao messageHeadDao;

	@Override
	public void get(MessageHead messageHead, PageView pageView, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			msg.getData().addAll(messageHeadDao.get(messageHead, pageView.getStartRecord(), pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap().put(Constant.TOTALRECORD, messageHeadDao.count(messageHead) + "");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("报文头信息查询失败", o);
		}
	}

	@Override
	@Log(object=C.LOG_OBJECT.ESB_HEAD,type=C.LOG_TYPE.UPDATE)
	public void update(MessageHead messageHead, OaMsg msg) {
		// TODO Auto-generated method stub
		try {
			if (messageHeadDao.modify(messageHead)) {
				msg.setMsg("报文头信息修改成功");
				LOG.debug("报文头成功修改"+ new Gson().toJson(messageHead));
			} else {
				msg.setCode(Constant.SYS_CODE_NOT_EXIT);
				msg.setMsg("报文头不存在");
				LOG.debug("报文头不存在:" +  new Gson().toJson(messageHead));
			}
		} catch (OAException e) {
			LOG.error(e.getMessage(), e);
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
		}
	}
	
}
