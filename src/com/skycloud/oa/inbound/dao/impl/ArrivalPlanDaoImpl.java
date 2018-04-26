package com.skycloud.oa.inbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ArrivalPlanDao;
import com.skycloud.oa.inbound.dto.ArrivalPlanDto;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.utils.Common;

@Component
public class ArrivalPlanDaoImpl extends BaseDaoImpl implements ArrivalPlanDao {

	@Override
	public void addArrivalPlan(ArrivalPlan arrivalPlan) {
		try {
			save(arrivalPlan);
		} catch (OAException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteArrivalPlan(String ids) {
		String sql = "delete from t_pcs_arrival_plan where id in(" + ids + ")";
		try {
			execute(sql);
		} catch (OAException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateArrivalPlan(ArrivalPlan arrivalPlan) {
		try {
			
			update(arrivalPlan);
		} catch (OAException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getArrivalPlanList1(ArrivalPlanDto arrivalPlanDto, int start,
			int limit)throws OAException {
		String sql="";
		
			
			sql+= "SELECT (select max(No) from t_pcs_arrival_plan) NoCount,";
		sql+="tpap.*,DATE_FORMAT(tpap.arrivalStartTime,'%Y-%m-%d %H:%i:%s') mArrivalTime,DATE_FORMAT(tpap.createTime,'%Y-%m-%d %H:%i:%s') mCreateTime,DATE_FORMAT(tpap.reviewTime,'%Y-%m-%d %H:%i:%s') mReviewTime,tpp.name productName,tpc.name clientName,tsu.name createUserName,tps.value statusName,n.value tradeTypeName FROM t_pcs_arrival_plan tpap "
				+ " LEFT JOIN t_pcs_product tpp on tpp.id=tpap.productId "
				+ " LEFT JOIN t_pcs_client tpc on tpc.id=tpap.clientId "
//				+ " LEFT JOIN t_pcs_cargo tpca on tpca.arrivalId= tpap.arrivalId "
				+ " LEFT JOIN t_auth_user tsu on tpap.createUserId=tsu.id LEFT JOIN t_pcs_status tps on tps.key=tpap.status LEFT JOIN t_pcs_trade_type n on tpap.tradeType=n.key"
				+ "  where 1=1 ";
		
				if(!Common.isNull(arrivalPlanDto.getArrivalId())){
					sql+=" and tpap.arrivalId="+arrivalPlanDto.getArrivalId();
				}
				
				if(!Common.isNull(arrivalPlanDto.getPlanId())){
					
					sql+=" and tpap.id="+arrivalPlanDto.getPlanId();
					
				}
				
				
				if(!Common.isNull(arrivalPlanDto.getCargoId())){
					sql+=" and tpap.cargoId="+arrivalPlanDto.getCargoId()+" and isNull(tpap.ladingCode) ";
				}else{
					sql+=" and (tpap.status=0 or tpap.status=1 or tpap.status=3) order by tpap.createTime DESC";
				}
				
				
				
		if (limit != 0) {
			sql += "limit " + start + "," + limit;
		}
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> getArrivalPlanById(int id) {
		String sql="select * from t_pcs_arrival_plan where id="+id;
		try {
			return executeQueryOne(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateArrivalPlanStatus(int arrivalPlanId,int status) {
		String sql="update t_pcs_arrival_plan set status="+status+" where id="+arrivalPlanId;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<Map<String, Object>> getArrivalPlanList(ArrivalPlanDto arrivalPlanDto,
			int start, int limit) {
		String sql = "SELECT tpap.*,DATE_FORMAT(tpap.arrivalStartTime,'%Y-%m-%d %H:%i:%s') mArrivalTime,DATE_FORMAT(tpap.createTime,'%Y-%m-%d %H:%i:%s') mCreateTime,DATE_FORMAT(tpap.reviewTime,'%Y-%m-%d %H:%i:%s') mReviewTime,tpp.name productName,tpc.name clientName,tsu.name createUserName,tps.value statusName,tpship.name shipName FROM t_pcs_arrival_plan tpap "
				+ " LEFT JOIN t_pcs_product tpp on tpp.id=tpap.productId LEFT JOIN t_pcs_ship tpship on tpship.id= tpap.shipId"
				+ " LEFT JOIN t_pcs_client tpc on tpc.id=tpap.clientId "
				+ " LEFT JOIN t_auth_user tsu on tpap.createUserId=tsu.id LEFT JOIN t_pcs_status tps on tps.key=tpap.status"
				+ "  where 1=1";
		if(!Common.empty(arrivalPlanDto.getGoodsTotal())){
			sql+=" and tpap.goodsTotal='"+arrivalPlanDto.getGoodsTotal()+"'";
		}
		if(!Common.isNull(arrivalPlanDto.getArrivalId())){
			sql+=" and tpap.arrivalId="+arrivalPlanDto.getArrivalId();
		}
		if(!Common.isNull(arrivalPlanDto.getClientId())){
			sql+=" and tpap.clientId="+arrivalPlanDto.getClientId();
		}
		if(!Common.isNull(arrivalPlanDto.getProductId())){
			sql+=" and tpap.productId="+arrivalPlanDto.getProductId();
		}
		if(!Common.isNull(arrivalPlanDto.getShipId())){
			sql+=" and tpap.shipId="+arrivalPlanDto.getShipId();
		}
		if(!Common.isNull(arrivalPlanDto.getStatus())){
			sql+=" and tpap.status="+arrivalPlanDto.getStatus();
		}
		
		sql+=" order by tpap.createTime DESC";
		if (limit != 0) {
			sql += " limit " + start + "," + limit;
		}
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getArrivalPlanListCount(ArrivalPlanDto arrivalPlanDto) {
		String sql = "SELECT tpap.*,DATE_FORMAT(tpap.arrivalStartTime,'%Y-%m-%d %H:%i:%s') mArrivalTime,DATE_FORMAT(tpap.createTime,'%Y-%m-%d %H:%i:%s') mCreateTime,DATE_FORMAT(tpap.reviewTime,'%Y-%m-%d %H:%i:%s') mReviewTime,tpp.name productName,tpc.name clientName,tsu.name createUserName,tps.value statusName,tpship.name shipName FROM t_pcs_arrival_plan tpap "
				+ " LEFT JOIN t_pcs_product tpp on tpp.id=tpap.productId "
				+ " LEFT JOIN t_pcs_client tpc on tpc.id=tpap.clientId LEFT JOIN t_pcs_ship tpship on tpship.id= tpap.shipId"
				+ " LEFT JOIN t_auth_user tsu on tpap.createUserId=tsu.id LEFT JOIN t_pcs_status tps on tps.key=tpap.status"
				+ "  where 1=1";
		if(!Common.empty(arrivalPlanDto.getGoodsTotal())){
			sql+=" and tpap.goodsTotal='"+arrivalPlanDto.getGoodsTotal()+"'";
		}
		if(!Common.isNull(arrivalPlanDto.getArrivalId())){
			sql+=" and tpap.arrivalId="+arrivalPlanDto.getArrivalId();
		}
		if(!Common.isNull(arrivalPlanDto.getClientId())){
			sql+=" and tpap.clientId="+arrivalPlanDto.getClientId();
		}
		if(!Common.isNull(arrivalPlanDto.getProductId())){
			sql+=" and tpap.productId="+arrivalPlanDto.getProductId();
		}
		if(!Common.isNull(arrivalPlanDto.getShipId())){
			sql+=" and tpap.shipId="+arrivalPlanDto.getShipId();
		}
		if(!Common.isNull(arrivalPlanDto.getStatus())){
			sql+=" and tpap.status="+arrivalPlanDto.getStatus();
		}
		
		return (int)getCount(sql);
	}

	@Override
	public void updateArrivalPlanCargoId(String planId, int cargoId) {
		String sql="update t_pcs_arrival_plan set cargoId="+cargoId+" where id="+planId;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateNo(String planId, String no) {
		String sql="update t_pcs_arrival_plan set No='"+no+"' where id="+planId;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateCargoNo(String cargoId, String no) {
		String sql="update t_pcs_cargo set No='"+no+"' where id="+cargoId;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void updateCargoinboundNo(String cargoId, String no) {
		String sql="update t_pcs_cargo set inboundNo='"+no+"' where id="+cargoId;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	

}
