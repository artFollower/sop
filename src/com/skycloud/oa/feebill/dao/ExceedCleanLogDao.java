/**
 * @Title:ExceedCleanLogDao.java
 * @Package com.skycloud.oa.feebill.dao
 * @Description TODO
 * @autor jiahy
 * @date 2017年3月25日上午9:10:43
 * @version V1.0
 */
package com.skycloud.oa.feebill.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.ExceedCleanLogDto;
import com.skycloud.oa.feebill.model.ExceedCleanLog;

/**
 * 超期结清日志
 * @ClassName ExceedCleanLogDao
 * @Description TODO
 * @author jiahy
 * @date 2017年3月25日上午9:10:43
 */
public interface ExceedCleanLogDao {

	List<Map<String, Object>> getLogList(ExceedCleanLogDto eclDto) throws OAException;
	
	int addLog(ExceedCleanLog exceedCleanLog) throws OAException;
	
	void deleteLog(int id) throws OAException;
}
