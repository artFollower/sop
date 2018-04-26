package com.skycloud.oa.order.dao.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dao.IntentionDao;
import com.skycloud.oa.order.dto.GetIntentionDto;
import com.skycloud.oa.order.dto.IntentionDto;
import com.skycloud.oa.order.model.Intention;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.DateTimeUtil;

/**
 * @author yanyufeng
 * 
 */
@Component
public class IntentionDaoImpl extends BaseDaoImpl implements IntentionDao {

	/**
	 * 添加订单意向
	 * 
	 * @param intention
	 * @author yanyufeng
	 */
	@Override
	public int addIntention(final Intention intention) {
		try {
			return (Integer) save(intention);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
	}


	/**
	 * 获得当年相同类型的意向数量
	 * @param code
	 * @return
	 * @author yanyufeng
	 */
	@Override
	public int getTheSameIntention(String code) {
		String codeSql = "SELECT COUNT(*) FROM t_pcs_intention WHERE code like '%"
				+ code + "%'";
		int count=(int) getCount(codeSql);
		return count;
	}
	
	/**
	 * 删除订单意向
	 * 
	 * @param intentionId
	 * @author yanyufeng
	 */
	@Override
	public void deleteIntention(String intentionId) {
		String sql = "update t_pcs_intention set status=4 where id in(" + intentionId+")";
		// jdbcTemplate.update(sql,new Object[]{intentionId});
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 更新订单意向
	 * 
	 * @param intention
	 * @author yanyufeng
	 */
	@Override
	public void updateIntention(Intention intention) {
		String sql="update t_pcs_intention set id=id";
		if(!Common.empty(intention.getCode())){
			sql+=" ,code='"+intention.getCode()+"'";
		}
		if(!Common.empty(intention.getTitle())){
			sql+=" ,title='"+intention.getTitle()+"'";
		}
		if(!Common.isNull(intention.getType())){
			sql+=" ,type="+intention.getType();
		}
		if(!Common.isNull(intention.getClientId())){
			sql+=" ,clientId="+intention.getClientId();
		}
		if(!Common.isNull(intention.getProductId())){
			sql+=" ,productId="+intention.getProductId();
		}
		if(!Common.empty(intention.getQuantity())){
			sql+=" ,quantity='"+intention.getQuantity()+"'";
		}
		if(!Common.empty(intention.getLossRate())){
			sql+=" ,lossRate='"+intention.getLossRate()+"'";
		}
		if(!Common.empty(intention.getTotalPrice())){
			sql+=" ,totalPrice='"+intention.getTotalPrice()+"'";
		}
		if(!Common.empty(intention.getDescription())){
			sql+=" ,description='"+intention.getDescription()+"'";
		}
		if(!Common.isNull(intention.getStatus())){
			sql+=" ,status="+intention.getStatus();
		}
		if(!Common.isNull(intention.getSalesUserId())){
			sql+=" ,salesUserId="+intention.getSalesUserId();
		}
		if(!Common.isNull(intention.getCreateUserId())){
			sql+=" ,createUserId="+intention.getCreateUserId();
		}
		if(!Common.empty(intention.getCreateTime())){
			sql+=" ,createTime='"+intention.getCreateTime()+"'";
		}
		if(!Common.isNull(intention.getEditUserId())){
			sql+=" ,editUserId="+intention.getEditUserId();
		}
		if(!Common.empty(intention.getEditTime())){
			sql+=" ,editTime='"+intention.getEditTime()+"'";
		}
		if(!Common.empty(intention.getOtherPrice())){
			sql+=" ,otherPrice='"+intention.getOtherPrice()+"'";
		}
		if(!Common.empty(intention.getOvertimePrice())){
			sql+=" ,overtimePrice='"+intention.getOvertimePrice()+"'";
		}
		if(!Common.empty(intention.getPassPrice())){
			sql+=" ,passPrice='"+intention.getPassPrice()+"'";
		}
		if(!Common.empty(intention.getPeriod())){
			sql+=" ,period='"+intention.getPeriod()+"'";
		}
		if(!Common.empty(intention.getPortSecurityPrice())){
			sql+=" ,portSecurityPrice='"+intention.getPortSecurityPrice()+"'";
		}
		if(!Common.empty(intention.getPortServicePrice())){
			sql+=" ,portServicePrice='"+intention.getPortServicePrice()+"'";
		}
		if(!Common.empty(intention.getStoragePrice())){
			sql+=" ,storagePrice='"+intention.getStoragePrice()+"'";
		}
		if(!Common.isNull(intention.getReviewUserId())){
			sql+=" ,reviewUserId="+intention.getReviewUserId();
		}
		if(!Common.isNull(intention.getReviewTime())){
			sql+=" ,reviewTime="+intention.getReviewTime();
		}
		if(!Common.empty(intention.getProductNameList())){
			sql+=" ,productNameList='"+intention.getProductNameList()+"'";
		}
		sql+=" where id="+intention.getId();
		try {
			execute(sql);
//			update(contract);
		} catch (OAException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 多条件查询订单意向
	 * 
	 * @param status
	 * @return
	 * @author yanyufeng
	 */
	@Override
	public List<Map<String, Object>> getIntentionListByCondition(
			IntentionDto intentionDto, int start, int limit) {
		String sql = "SELECT tpi.*,DATE_FORMAT(tpi.createTime,'%Y-%m-%d %H:%i:%s') mCreateTime,DATE_FORMAT(tpi.editTime,'%Y-%m-%d %H:%i:%s') mEditTime,tpcg.id clientGroupId,tpcg.name clientGroupName,tpc.name clientName,tpp.name productName ,tsu.name createUserName,"
				+ "tsu2.name editUserName,tpt.value typeName,tps.value statusName,tsu3.name reviewUserName,from_unixtime(tpi.reviewTime) mReviewTime"
				+ " from t_pcs_intention tpi INNER  JOIN t_pcs_client tpc on tpi.clientId=tpc.id "
				+ " LEFT JOIN t_pcs_product tpp on tpi.productId=tpp.id INNER JOIN t_auth_user tsu on "
				+ " tpi.createUserId=tsu.id LEFT JOIN t_auth_user tsu2 on tpi.editUserId=tsu2.id "
				+ " LEFT JOIN t_pcs_contract_type tpt on tpi.type=tpt.key LEFT JOIN t_pcs_status tps on tpi.status=tps.key LEFT JOIN t_pcs_client_group tpcg on tpc.clientGroupId=tpcg.id LEFT JOIN t_auth_user tsu3 on tpi.reviewUserId=tsu3.id  where 1=1 ";
		if (!Common.empty(intentionDto)) {
			if (!Common.empty(intentionDto.getId())
					&& intentionDto.getId() != 0) {
				sql += " and tpi.id=" + intentionDto.getId();
			}
			if (!Common.empty(intentionDto.getCode())) {
				sql += " and tpi.code like'%" + intentionDto.getCode() + "%'";
			}
			if (!Common.empty(intentionDto.getTitle())) {
				sql += " and tpi.title like '%" + intentionDto.getTitle()
						+ "%'";
			}
			if (!Common.empty(intentionDto.getClientId())
					&& intentionDto.getClientId() != 0) {
				sql += " and tpi.clientId=" + intentionDto.getClientId();
			}
			if (!Common.empty(intentionDto.getProductId())
					&& intentionDto.getProductId() != 0) {
				sql += " and tpi.productId=" + intentionDto.getProductId();
			}
			if (!Common.empty(intentionDto.getType())
					&& intentionDto.getType() != 0) {
				sql += " and tpi.type=" + intentionDto.getType();
			}
			if (!Common.empty(intentionDto.getStatus())) {
				sql += " and tpi.status=" + intentionDto.getStatus();
			}else if(Common.isNull(intentionDto.getId())){
				sql+=" and tpi.status<>4";
			}
			if (!Common.empty(intentionDto.getTimeType())) {
				switch (intentionDto.getTimeType()) {
				// 创建时间
				case 1:
					sql += " and tpi.createTime between '"
							+ intentionDto.getStartTime() + "' and '"
							+ intentionDto.getEndTime() + "'";
					break;
				// 修改时间
				case 2:
					sql += " and tpi.editTime between '"
							+ intentionDto.getStartTime() + "' and '"
							+ intentionDto.getEndTime() + "'";
					break;
				// 提交时间
				case 3:
					break;
				// 确认时间
				case 4:
					break;
				}
			}
			if (!Common.empty(intentionDto.getText())) {
				sql += " and (tpi.title like'%" + intentionDto.getText()
						+ "%' or tpc.name like '%" + intentionDto.getText()
						+ "%' " + " or tpp.name like '%"
						+ intentionDto.getText() + "%' or tpi.code like '%"
						+ intentionDto.getText()
						+ "%' or tpi.description like '%"
						+ intentionDto.getText() + "%')";
			}
		}
		if (limit == 0) {
			sql += " order by tpi.editTime DESC ";
		} else {
			sql += " order by tpi.editTime DESC limit " + start + "," + limit;
		}

		try {
			List<Map<String, Object>> mapList = executeQuery(sql);
			return mapList;
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 多条件查询订单意向
	 * 
	 * @param intentionDto
	 * @return
	 * @author yanyufeng
	 */
	@Override
	public int getIntentionListCountByCondition(IntentionDto intentionDto) {
		String sql = "SELECT COUNT(*) from t_pcs_intention tpi INNER JOIN t_pcs_client tpc on tpi.clientId=tpc.id "
				+ " LEFT JOIN t_pcs_product tpp on tpi.productId=tpp.id INNER JOIN t_auth_user tsu on "
				+ " tpi.createUserId=tsu.id LEFT JOIN t_auth_user tsu2 on tpi.editUserId=tsu2.id "
				+ " LEFT JOIN t_pcs_contract_type tpt on tpi.type=tpt.key INNER JOIN t_pcs_status tps on tpi.status=tps.key where tpi.status <> 4 ";
		if (!Common.empty(intentionDto)) {
			if (!Common.empty(intentionDto.getId())
					&& intentionDto.getId() != 0) {
				sql += " and tpi.id=" + intentionDto.getId();
			}
			if (!Common.empty(intentionDto.getCode())) {
				sql += " and tpi.code like'%" + intentionDto.getCode() + "%'";
			}
			if (!Common.empty(intentionDto.getTitle())) {
				sql += " and tpi.title like '%" + intentionDto.getTitle()
						+ "%'";
			}
			if (!Common.empty(intentionDto.getClientId())
					&& intentionDto.getClientId() != 0) {
				sql += " and tpi.clientId=" + intentionDto.getClientId();
			}
			if (!Common.empty(intentionDto.getProductId())
					&& intentionDto.getProductId() != 0) {
				sql += " and tpi.productId=" + intentionDto.getProductId();
			}
			if (!Common.empty(intentionDto.getType())
					&& intentionDto.getType() != 0) {
				sql += " and tpi.type=" + intentionDto.getType();
			}
			if (!Common.empty(intentionDto.getStatus())
					&& intentionDto.getStatus() != 0) {
				sql += " and tpi.status=" + intentionDto.getStatus();
			}
			if (!Common.empty(intentionDto.getTimeType())) {
				switch (intentionDto.getTimeType()) {
				// 创建时间
				case 1:
					sql += " and tpi.createTime between '"
							+ intentionDto.getStartTime() + "' and '"
							+ intentionDto.getEndTime() + "'";
					break;
				// 修改时间
				case 2:
					sql += " and tpi.editTime between '"
							+ intentionDto.getStartTime() + "' and '"
							+ intentionDto.getEndTime() + "'";
					break;
				// 提交时间
				case 3:
					break;
				// 确认时间
				case 4:
					break;
				}
			}
			if (!Common.empty(intentionDto.getText())) {
				sql += " and (tpi.title like'%" + intentionDto.getText()
						+ "%' or tpc.name like '%" + intentionDto.getText()
						+ "%' " + " or tpp.name like '%"
						+ intentionDto.getText() + "%' or tpi.code like '%"
						+ intentionDto.getText()
						+ "%' or tpi.description like '%"
						+ intentionDto.getText() + "%')";
			}
		}
		int count = (int) getCount(sql);
		return count;
	}


	@Override
	public void updateIntentionStatus(int id, int status) {
		String sql="update t_pcs_intention set status="+status+" where id="+id;
		try {
			execute(sql);
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


}
