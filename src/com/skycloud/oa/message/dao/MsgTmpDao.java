package com.skycloud.oa.message.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;

/**
 * 消息模板
 * @author Administrator
 *
 */
public interface MsgTmpDao {

	/**
	 * 查询
	 * @return
	 */
	List<Map<String, Object>> list() throws OAException;
}
