/**
 * @Title:ThroughtMsgDao.java
 * @Package com.skycloud.oa.report.dao
 * @Description TODO
 * @autor jiahy
 * @date 2016年12月20日下午1:48:20
 * @version V1.0
 */
package com.skycloud.oa.report.dao;

import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.report.model.ThroughtMsg;

/**
 * @ClassName ThroughtMsgDao
 * @Description TODO
 * @author jiahy
 * @date 2016年12月20日下午1:48:20
 */
public interface ThroughtMsgDao {
	public Map<String, Object> getThroughtMsg(Long time) throws OAException;
    public int addThroughtMsg(ThroughtMsg throughtMsg) throws OAException;
    public void updateThroughtMsg(ThroughtMsg throughtMsg) throws OAException;
	
	
}
