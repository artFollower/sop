package com.skycloud.oa.esb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.esb.dao.MessageHeadDao;
import com.skycloud.oa.esb.model.MessageHead;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.utils.Common;

/**
 * 港务提单管理持久化操作实现类
 * @ClassName: ResourceDaoImpl 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:47:27
 */
@Component
public class MessageHeadDaoImpl extends BaseDaoImpl implements MessageHeadDao {

	@Override
	public int create(MessageHead messageHead) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(messageHead).toString());
	}

	@Override
	public List<Map<String, Object>> get(MessageHead messageHead, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select * from t_esb_head where 1=1";
		if(!Common.isNull(messageHead.getId())) {
			sql += " and id = "+messageHead.getId()+"";
		}
		if(!Common.isNull(messageHead.getShip().getId())) {
			sql += " and portId = "+messageHead.getShip().getId()+"";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(MessageHead messageHead) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) from t_esb_head where 1=1";
		if(!Common.isNull(messageHead.getId())) {
			sql += " and id = "+messageHead.getId()+"";
		}
		if(!Common.isNull(messageHead.getShip().getId())) {
			sql += " and portId = "+messageHead.getShip().getId()+"";
		}
		return getCount(sql);
	}

	@Override
	public boolean modify(MessageHead messageHead) throws OAException {
		// TODO Auto-generated method stub
		String sql = "update t_esb_head set fileDescription='"+messageHead.getFileDescription()+"',fileType='"+
				messageHead.getFileType()+"',sendCode='"+messageHead.getSendCode()+"',receiverCode='"+messageHead.getReceiverCode()
				+"',sendPortCode='"+messageHead.getSendCode()+"',receiverPortCode='"+messageHead.getReceiverCode()+"',fileCreateTime='"+
				messageHead.getFileCreateTime()+"' where id = "+messageHead.getId();
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public boolean delete(String ids) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_esb_head";
		sql += " where id in("+ids+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	
	
}
