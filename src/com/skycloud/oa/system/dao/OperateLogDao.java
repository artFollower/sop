package com.skycloud.oa.system.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.model.OperateLog;

/**
 * 系统操作日志持久化接口类
 * @ClassName: LoggerDao 
 * @Description: TODO
 * @author xie
 * @date 2015年2月8日 上午2:43:11
 */
public interface OperateLogDao {
	
	/**
	 * 添加系统日志
	 * @Description: TODO
	 * @author xie
	 * @date 2015年2月8日 上午2:44:25  
	 * @param logger
	 * @return
	 * @throws OAException
	 */
	public int create(OperateLog log) throws OAException;

	/**
	 * 查询系统操作日志
	 * @Description: TODO
	 * @author xie
	 * @date 2015年2月8日 上午2:45:46  
	 * @param logger
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public List<Map<String, Object>> get(OperateLog log,int start,int limit)throws OAException;

	/**
	 * 统计操作日志数量
	 * @Description: TODO
	 * @author xie
	 * @date 2015年2月8日 上午2:46:18  
	 * @param logger
	 * @return
	 * @throws OAException
	 */
	public long count(OperateLog log)throws OAException;
}
