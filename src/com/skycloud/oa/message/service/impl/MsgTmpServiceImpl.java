package com.skycloud.oa.message.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Global;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.message.dao.MsgTmpDao;
import com.skycloud.oa.message.service.MsgTmpService;

@Service
public class MsgTmpServiceImpl implements MsgTmpService
{
	private Logger LOG = Logger.getLogger(MsgTmpServiceImpl.class);

	@Autowired
	private MsgTmpDao msgTmpDao;

	@Override
	public void initTemp()
	{
		// TODO Auto-generated method stub
		List<Map<String, Object>> list;
		try {
			list = msgTmpDao.list();
			for (Map<String, Object> map : list) {
				Global.msgTmp.put(map.get("code").toString(), map);
			}
			LOG.debug("系统配置已初始化");
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
