package com.skycloud.oa.esb.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.esb.dao.HarborBillDao;
import com.skycloud.oa.esb.model.HarborBill;
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
public class HarborBillDaoImpl extends BaseDaoImpl implements HarborBillDao {

	@Override
	public int create(HarborBill harborBill) throws OAException {
		// TODO Auto-generated method stub
		return Integer.parseInt(save(harborBill).toString());
	}

	@Override
	public List<Map<String, Object>> get(HarborBill harborBill, long start, long limit) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM `t_esb_bill` b,t_esb_harbor h WHERE b.id = h.objectId AND h.catogary = '"+harborBill.getShip().getCatogary()+"'";
		if(!Common.isNull(harborBill.getId())) {
			sql += " and id = "+harborBill.getId()+"";
		}
		if(!Common.isNull(harborBill.getShip().getId())) {
			sql += " and b.portId = "+harborBill.getShip().getId()+"";
		}
		if(limit != 0) {
			sql += " limit "+start+","+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public long count(HarborBill harborBill) throws OAException {
		// TODO Auto-generated method stub
		String sql = "select count(1) FROM `t_esb_bill` b,t_esb_harbor h WHERE b.id = h.objectId AND h.catogary = '"+harborBill.getShip().getCatogary()+"'";
		if(!Common.isNull(harborBill.getId())) {
			sql += " and id = "+harborBill.getId()+"";
		}
		if(!Common.isNull(harborBill.getShip().getId())) {
			sql += " and b.portId = "+harborBill.getShip().getId()+"";
		}
		return getCount(sql);
	}

	@Override
	public boolean modify(HarborBill harborBill) throws OAException {
		// TODO Auto-generated method stub
		String sql = "update t_esb_bill set bl='"+harborBill.getBl()+"',consingee='"+
				harborBill.getConsingee()+"',shipper='"+harborBill.getShipper()+"',consignor='"+harborBill.getConsignor()
				+"',cargoName='"+harborBill.getCargoName()+"',cargoType='"+harborBill.getCargoType()+"',danger='"+harborBill.getDanger()+
				"',dangerNO='"+harborBill.getDangerNO()+"',grossWeight='"+harborBill.getGrossWeight()+"',transnateFlag='"+harborBill.getTransnateFlag()+
				"',ifcsumType='"+harborBill.getIfcsumType()+"',pay='"+harborBill.getPay()+"',startPort='"+harborBill.getStartPort()+
				"',disPort='"+harborBill.getDisPort()+"' where id = "+harborBill.getId();
		return executeUpdate(sql) > 0 ? true :false;
	}

	@Override
	public boolean delete(String ids) throws OAException {
		// TODO Auto-generated method stub
		String sql = "delete from t_esb_bill where id in("+ids+")";
		return executeUpdate(sql) > 0 ? true :false;
	}

	
	
}
