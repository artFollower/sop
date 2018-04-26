/**
 * @Title:ThroughtMsgDaoImpl.java
 * @Package com.skycloud.oa.report.dao.impl
 * @Description TODO
 * @autor jiahy
 * @date 2016年12月20日下午1:50:32
 * @version V1.0
 */
package com.skycloud.oa.report.dao.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.report.dao.ThroughtMsgDao;
import com.skycloud.oa.report.model.ThroughtMsg;

/**
 * @ClassName ThroughtMsgDaoImpl
 * @Description TODO
 * @author jiahy
 * @date 2016年12月20日下午1:50:32
 */
@Repository
public class ThroughtMsgDaoImpl extends BaseDaoImpl implements ThroughtMsgDao {

	/**
	 * @Title getThroughtMsg
	 * @Descrption:TODO
	 * @param:@param time
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年12月20日下午1:50:32
	 * @throws
	 */
	@Override
	public Map<String, Object> getThroughtMsg(Long time) throws OAException {
		StringBuilder sql=new StringBuilder();
		sql.append("select id,content from t_pcs_throught_msg where time=").append(time).append(" limit 0,1 ");
		return executeQueryOne(sql.toString());
	}

	/**
	 * @Title addThroughtMsg
	 * @Descrption:TODO
	 * @param:@param throughtMsg
	 * @auhor jiahy
	 * @date 2016年12月20日下午1:50:32
	 * @throws
	 */
	@Override
	public int addThroughtMsg(ThroughtMsg throughtMsg)  throws OAException {
		return Integer.parseInt(save(throughtMsg).toString());
	}

	/**
	 * @Title updateThroughtMsg
	 * @Descrption:TODO
	 * @param:@param throughtMsg
	 * @auhor jiahy
	 * @date 2016年12月20日下午1:50:32
	 * @throws
	 */
	@Override
	public void updateThroughtMsg(ThroughtMsg throughtMsg) throws OAException {
		update(throughtMsg);
	}

}
