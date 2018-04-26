/**
 * 
 */
package com.skycloud.oa.feebill.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.OtherFeeDao;
import com.skycloud.oa.feebill.dto.OtherFeeDto;
import com.skycloud.oa.feebill.model.OtherFee;

/**
 *
 * @author jiahy
 * @version 2015年10月21日 下午2:51:48
 */
@Component
public class OtherFeeDaoImpl extends BaseDaoImpl implements OtherFeeDao {
	private static Logger LOG = Logger.getLogger(OtherFeeDaoImpl.class);
	@Override
	public List<Map<String, Object>> getList(OtherFeeDto oDto, int start,
			int limit) throws OAException {
		try {
			String sql=" select a.id, a.code,a.description , a.status,e.id feechargeId,e.feeCount feeCount,"
					+ "e.totalFee,e.unitFee,e.type feechargeType,e.feebillId,"
					+ "a.createUserId ,b.name createUserName,a.clientId,c.name clientName,a.productId,"
					+ " d.name productName, FROM_UNIXTIME(a.accountTime,'%Y-%m-%d') accountTime,FROM_UNIXTIME(a.createTime,'%Y-%m-%d') createTime"
					+ "  from t_pcs_other_fee a  "
					+ " left join t_auth_user b on a.createUserId=b.id "
					+ " left join t_pcs_client c on c.id=a.clientId "
					+ " left join t_pcs_product d on d.id=a.productId "
					+ " left join t_pcs_fee_charge e on e.initialId=a.id and e.feeType=6 "
					+ " where 1=1 ";
			     if(oDto.getId()!=null){
			    	 sql+=" and a.id="+oDto.getId();
			     }
			    if(oDto.getClientId()!=null){
			    	sql+=" and a.clientId="+oDto.getClientId();
			    }
			    if(oDto.getProductId()!=null){
			    	sql+=" and a.productId="+oDto.getProductId();
			    }
			    if(oDto.getStatus()!=null&&oDto.getStatus()!=-1){
			    	sql+=" and a.status="+oDto.getStatus();
			    }
			    if(oDto.getStartTime()!=null&&oDto.getStartTime()!=-1){
			    	sql+=" and a.createTime>="+oDto.getStartTime();
			    }
			    
			    if(oDto.getEndTime()!=null&&oDto.getEndTime()!=-1){
			    	sql+=" and a.createTime<="+oDto.getEndTime();
			    }
			    if(oDto.getIndexTh()!=null&&oDto.getIndexTh()==0)
			    	sql+=" order by a.code desc";
			    else if(oDto.getIndexTh()!=null&&oDto.getIndexTh()==1)
				    sql+=" order by a.accountTime desc";
			    else if(oDto.getIndexTh()!=null&&oDto.getIndexTh()==7)
				    sql+=" order by a.status asc";
			    else
				    sql+=" order by a.code desc";
			    if(limit!=0){
			    	sql+=" limit "+start+","+limit;
			    }
		return	executeQuery(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取其他费用列表失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取其他费用列表失败", e);
	}
	}

	@Override
	public int getListCount(OtherFeeDto oDto) throws OAException {
		try {
			String sql=" select count(a.id) from t_pcs_other_fee a  "
					+ " where 1=1 ";
			     if(oDto.getId()!=null){
			    	 sql+=" and a.id="+oDto.getId();
			     }
			    if(oDto.getClientId()!=null){
			    	sql+=" and a.clientId="+oDto.getClientId();
			    }
			    if(oDto.getProductId()!=null){
			    	sql+=" and a.productId="+oDto.getProductId();
			    }
			    if(oDto.getStatus()!=null&&oDto.getStatus()!=-1){
			    	sql+=" and a.status="+oDto.getStatus();
			    }
			    if(oDto.getStartTime()!=null&&oDto.getStartTime()!=-1){
			    	sql+=" and a.createTime>="+oDto.getStartTime();
			    }
			    if(oDto.getEndTime()!=null&&oDto.getEndTime()!=-1){
			    	sql+=" and a.createTime<="+oDto.getEndTime();
			    }
			    sql+=" order by code desc";
			    return (int) getCount(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取其他费用列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取其他费用列表数量失败", e);
	}
	}

	@Override
	public int addOtherFee(OtherFee otherFee) throws OAException {
		try {
			return Integer.valueOf(save(otherFee).toString());
	}catch (RuntimeException e) {
		LOG.error("dao添加其他费用失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加其他费用失败", e);
	}
	}

	@Override
	public void updateOtherFee(OtherFee otherFee) throws OAException {
		try {
			String sql =" update t_pcs_other_fee set id=id";
			if(otherFee.getId()!=null){
				sql+=" ,id="+otherFee.getId();
			}
			if(otherFee.getCode()!=null){
				sql+=" ,code='"+otherFee.getCode()+"'";
			}
			if(otherFee.getCreateUserId()!=null){
				sql+=" ,createUserId="+otherFee.getCreateUserId();
			}
			if(otherFee.getCreateTime()!=null&&otherFee.getCreateTime()!=-1){
				sql+=" ,createTime="+otherFee.getCreateTime();
			}
			if(otherFee.getAccountTime()!=null&&otherFee.getAccountTime()!=-1){
				sql+=" ,accountTime="+otherFee.getAccountTime();
			}
			if(otherFee.getDescription()!=null){
				sql+=" ,description='"+otherFee.getDescription()+"'";
			}
			if(otherFee.getClientId()!=null){
				sql+=" ,clientId="+otherFee.getClientId();
			}
			if(otherFee.getProductId()!=null){
				sql+=" ,productId="+otherFee.getProductId();
			}
			if(otherFee.getStatus()!=null){
				sql+=" ,status="+otherFee.getStatus();
			}
             sql+=" where id="+otherFee.getId();			
			executeUpdate(sql);
	}catch (RuntimeException e) {
		LOG.error("dao更新其他费用失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新其他费用失败", e);
	}
	}

	@Override
	public void deleteOtherFee(OtherFeeDto otherFee) throws OAException {
		try {
			String sql=" delete from t_pcs_other_fee where 1=1 and id="+otherFee.getId();
			executeUpdate(sql);
	}catch (RuntimeException e) {
		LOG.error("dao添加其他费用包含货批失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加费用失败", e);
	}
	}

	@Override
	public Map<String, Object> getCodeNum(OtherFeeDto otherFee)
			throws OAException {
			 try {
				 String sql="select max(code) code from t_pcs_other_fee  where 1=1 and (!ISNULL(code) and code!='')";
					return executeQueryOne(sql);
				}catch (RuntimeException e) {
					LOG.error("dao获取账单号失败");
					throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单号失败", e);
				}
	}

	@Override
	public List<Map<String, Object>> getCargoMsg(OtherFeeDto otherFeeDto)throws OAException  {
	
	 try {
		 if(otherFeeDto.getProductId()!=null&&otherFeeDto.getClientId()!=null){
		 String sql="select DISTINCT b.id ,b.code cargoCode  from t_pcs_goods a "
					+ "INNER JOIN t_pcs_cargo b ON a.cargoId=b.id "
					+ "where a.productId="+otherFeeDto.getProductId()+" and a.clientId="+otherFeeDto.getClientId()+"  ORDER BY b.id asc";
			return executeQuery(sql);
		 }
		}catch (RuntimeException e) {
			LOG.error("dao获取账单号失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单号失败", e);
		}
	return null;
	}

	@Override
	public List<Map<String, Object>> getInboundMsgByOtherId(Integer otherFeeId)
			throws OAException {
		try {
			String sql="SELECT CONCAT(h.`name`,'/',i.refName,'/',DATE_FORMAT(j.arrivalStartTime,'%Y-%m-%d')) inboundMsg,k.id cargoId,k.code cargoCode,k.storageType storageType,k.taxType type,l.type contractType,l.period periodTime from "
					+ " ( SELECT DISTINCT d.cargoId id  from t_pcs_other_fee_cargo d  WHERE d.otherFeeId="+otherFeeId+" ) tb "
						+ " LEFT JOIN t_pcs_cargo k ON k.id = tb.id LEFT JOIN  t_pcs_arrival j ON j.id = k.arrivalId LEFT JOIN  t_pcs_ship h ON h.id = j.shipId LEFT JOIN t_pcs_ship_ref i ON i.id = j.shipRefId LEFT JOIN t_pcs_contract l ON l.id = k.contractId"
						+ " where tb.id!=0";
				return executeQuery(sql);
			}catch (RuntimeException e) {
				LOG.error("dao获取账单号失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单号失败", e);
			}
	}

}
