package com.skycloud.oa.inbound.dao.impl;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalWorkDao;
import com.skycloud.oa.inbound.model.ArrivalWork;
@Component
public class ArrivalWorkDaoImpl extends BaseDaoImpl implements ArrivalWorkDao {

	@Override
	public void addArrivalWork(ArrivalWork arrivalWork) {
		try {
			save(arrivalWork);
		} catch (OAException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteArrivalWork(String ids) {
		String sql = "delete from t_pcs_arrival_work where id in(" + ids+")";
		try {
			execute(sql);
		} catch (OAException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateArrivalWork(ArrivalWork arrivalWork) {
		try {
			update(arrivalWork);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void deleteCargo(Integer cargoId) {
		// TODO Auto-generated method stub
		String sql="delete from t_pcs_cargo where id="+cargoId;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getTheSameProductCargo(Integer cargoId) {
		// TODO Auto-generated method stub
		String sql="select count(1) from t_pcs_cargo a LEFT JOIN (select b.arrivalId ,b.productId from t_pcs_cargo b where b.id="+cargoId+") c on c.arrivalId=a.arrivalId where a.productId=c.productId";
		return (int) getCount(sql);
	}

	@Override
	public void deleteWorkAndProgram(Integer cargoId) {
		// TODO Auto-generated method stub
		String sql="delete from t_pcs_work where arrivalId=(select arrivalId from t_pcs_cargo where id="+cargoId+") and productId=(select productId from t_pcs_cargo where id="+cargoId+")";
		String sql1="delete from t_pcs_transport_program where arrivalId=(select arrivalId from t_pcs_cargo where id="+cargoId+") and productId=(select productId from t_pcs_cargo where id="+cargoId+")";
		
		try {
			execute(sql);
			execute(sql1);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateArrivalInfoToUnSend(Integer cargoId) {
		// TODO Auto-generated method stub
		String sql="update t_pcs_arrival_info set report='未申报' where arrivalId=(select arrivalId from t_pcs_cargo where id="+cargoId+")";
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
