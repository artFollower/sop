package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.model.OperateLog;
import com.skycloud.oa.utils.OaMsg;

/**
 * 系统操作日志业务逻辑处理类
 * @ClassName: LoggerService 
 * @Description: TODO
 * @author xie
 * @date 2015年2月8日 上午3:30:33
 */
public interface OperateLogService {

	/**
	 * 查询
	 * @Description: TODO
	 * @author xie
	 * @date 2015年2月8日 上午3:31:10  
	 * @param logger
	 * @param pageView
	 * @return
	 * @throws OAException
	 */
	public void get(OperateLog log, PageView pageView,OaMsg msg);
	
	/**
	 * 记录日志
	 * @Description: TODO
	 * @author xie
	 * @date 2015年3月12日 上午10:21:09  
	 * @param log
	 * @param msg
	 */
	public void create(OperateLog log,OaMsg msg);
}
