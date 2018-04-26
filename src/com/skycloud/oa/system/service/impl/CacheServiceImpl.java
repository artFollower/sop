package com.skycloud.oa.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.CacheDao;
import com.skycloud.oa.system.service.CacheService;
import com.skycloud.oa.utils.OaMsg;
@Service
public class CacheServiceImpl implements CacheService {

	private static Logger LOG = Logger.getLogger(CacheServiceImpl.class);
	
	@Autowired
	private CacheDao cacheDao;
	
	/**
	 * 添加/更新关键字
	 * @param key
	 * @param userId
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	public OaMsg updateCache(String key, String userId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始更新关键字");
			cacheDao.updateCache(key, userId);
			LOG.debug("更新成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException re) {
			LOG.debug("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",re);
		}
		return oaMsg;
	}

	/**
	 * 得到关键字list
	 * @param text
	 * @param userId
	 * @return
	 * @throws OAException
	 * @author yanyufeng
	 */
	@Override
	public OaMsg getCacheResult(String text, String userId) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			LOG.debug("开始查询关键字");
			List<Map<String,Object>> results=cacheDao.getCacheResult(text, userId);
			LOG.debug("更新成功");
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(results);
			oaMsg.setMsg("更新成功");
		} catch (RuntimeException re) {
			LOG.debug("更新失败");
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			
			throw new OAException(Constant.SYS_CODE_DB_ERR, "更新失败",re);
		}
		return oaMsg;
	}

}
