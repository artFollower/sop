package com.skycloud.oa.inbound.dao.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ExceedFeeDao;
import com.skycloud.oa.inbound.dto.ExceedFeeDto;
import com.skycloud.oa.inbound.model.ExceedFee;
import com.skycloud.oa.utils.Common;
/**
 *超期费
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午2:23:48
 */
@Component
public class ExceedFeeDaoImpl extends BaseDaoImpl implements ExceedFeeDao {
	private static Logger LOG = Logger.getLogger(ExceedFeeDaoImpl.class);
    
	@Override
	public List<Map<String, Object>> getExceedFeeCargoList(ExceedFeeDto eFeeDto, int startRecord, int maxresult)
			throws OAException {
		try {
			String sql ="select a.id cargoId,a.code cargoCode,a.goodsTotal goodsTotal,a.goodsInspect goodsInspect,a.clientId clientId,f.name clientName,a.productId productId,g.name productName,"
						+"	b.shipId shipId,h.name shipName,b.shipRefId shipRefId,i.refName shipRefName,DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d' ) arrivalTime,"
						+"	d.id contractId,d.code contractCode,d.type contractType,d.period periodTime,d.overtimePrice overtimePrice,"
						+"  (SELECT FROM_UNIXTIME(max(j.startTime),'%Y-%m-%d') from t_pcs_exceed_fee j where j.cargoId=a.id) startTime,"
						+ " (SELECT FROM_UNIXTIME(max(j.endTime),'%Y-%m-%d') from t_pcs_exceed_fee j where j.cargoId=a.id) endTime,"
						+ " a.isFinish isFinish,"
						+ " (SELECT j.status from t_pcs_exceed_fee j WHERE j.cargoId=a.id ORDER BY j.id DESC LIMIT 0,1 ) `status`,"
						+ " (SELECT count(j.id)  from t_pcs_exceed_fee i, t_pcs_fee_charge j"
						+ " WHERE i.cargoId=a.id and j.exceedId=i.id AND (j.feebillId is null or j.feebillId=0) ) feebillCount ,"
						+ " (SELECT count(p.id) from t_pcs_exceed_fee p where p.cargoId=a.id ) eFCount "// 结算单数量
						+"	from t_pcs_cargo a "//货批
						+"	LEFT JOIN t_pcs_arrival b on a.arrivalId=b.id"
						+"	LEFT JOIN t_pcs_arrival_info c on c.arrivalId=a.arrivalId"
						+"	LEFT JOIN t_pcs_contract d on a.contractId=d.id"
						+"	LEFT JOIN t_pcs_client f on f.id=a.clientId"
						+"	LEFT JOIN t_pcs_product g on g.id=a.productId"
						+"	LEFT JOIN t_pcs_ship h on h.id=b.shipId"
						+"	LEFT JOIN t_pcs_ship_ref i on i.id=b.shipRefId"
						+" where 1=1 and !ISNULL(a.code) and !ISNULL(a.goodsTotal) and a.goodsTotal!=0 and "
						+" ((g.oils=0 and d.type in(2,3,4)) or (g.oils=1 and d.type=1))"
						+"  and b.type=1  ";// 合同不包括包罐合同
//						+ " and ( (SELECT max(j.isFinish) from t_pcs_exceed_fee j where j.cargoId=a.id) is null or "
//						+ " (SELECT max(j.isFinish) from t_pcs_exceed_fee j where j.cargoId=a.id)=0)"
//						+ " and DATE_ADD(FROM_UNIXTIME(c.anchorTime),INTERVAL d.period DAY)<=DATE_FORMAT(NOW(),'%Y-%m-%d') ";
			           if(eFeeDto.getClientId()!=null){
			        	   sql+=" and a.clientId="+eFeeDto.getClientId();
			           }
			           if(eFeeDto.getProductId()!=null){
			        	   sql+=" and a.productId="+eFeeDto.getProductId();
			           }
			           if(eFeeDto.getCargoCode()!=null){
			        	   sql+=" and a.code like '%"+eFeeDto.getCargoCode()+"%'";
			           }
			           if(eFeeDto.getCargoId()!=null){
			        	   sql+=" and a.id="+eFeeDto.getCargoId();
			           }
			           if(eFeeDto.getStartTime()!=null&&eFeeDto.getStartTime()!=-1){
			        	   sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)>="+eFeeDto.getStartTime();
			           }
			           if(eFeeDto.getEndTime()!=null&&eFeeDto.getEndTime()!=-1){
			        	   sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)<="+eFeeDto.getEndTime();
			           }
			           if(eFeeDto.getIsFinish()!=null){
			        	   if(eFeeDto.getIsFinish()==0){
			        		   sql+=" and ( a.isFinish=0 or isNull(a.isFinish)) ";
			        	   }else if(eFeeDto.getIsFinish()==1){
			        		   sql+=" and a.isFinish=1";
			        	   }
			           }
//			           if(eFeeDto.getIndexTh()!=null&&eFeeDto.getIndexTh()==0)
//			        	   sql+=" order by a.code desc ";
//			           else if(eFeeDto.getIndexTh()!=null&&eFeeDto.getIndexTh()==5)
//			        	   sql+=" order by b.arrivalStartTime desc ";
//			           else if(eFeeDto.getIndexTh()!=null&&eFeeDto.getIndexTh()==8)
//			        	   sql+=" order by b.arrivalStartTime desc ";
//			           else
			        	   sql+=" order by b.arrivalStartTime desc,a.id desc ";
			           
			           if(maxresult!=0){
			        	   sql+=" limit "+startRecord+" , "+maxresult;
			           }   
			           
				return executeQuery(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取超期费货批列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期费货批列表失败", e);
		}
	}

	@Override
	public int getExceedFeeCargoCount(ExceedFeeDto eFeeDto) throws OAException {
		try {
			String sql ="select count(a.id)"
					+"	from t_pcs_cargo a "
					+"	LEFT JOIN t_pcs_arrival b on a.arrivalId=b.id"
					+"	LEFT JOIN t_pcs_arrival_info c on c.arrivalId=a.arrivalId"
					+"	LEFT JOIN t_pcs_contract d on a.contractId=d.id"
					+"	LEFT JOIN t_pcs_client f on f.id=a.clientId"
					+"	LEFT JOIN t_pcs_product g on g.id=a.productId"
					+"	LEFT JOIN t_pcs_ship h on h.id=b.shipId"
					+"	LEFT JOIN t_pcs_ship_ref i on i.id=b.shipRefId"
					+ " where 1=1 and !ISNULL(a.code)  and !ISNULL(a.goodsTotal) and a.goodsTotal!=0 and "
					+"((g.oils=0  and d.type in(2,3,4)) or(g.oils=1 and d.type=1)) "
					+ " and b.type=1  ";//合同不包括包罐合同
//					+ " and ( (SELECT max(j.isFinish) from t_pcs_exceed_fee j where j.cargoId=a.id) is null or "
//					+ " (SELECT max(j.isFinish) from t_pcs_exceed_fee j where j.cargoId=a.id)=0)"
//					+ " and DATE_ADD(FROM_UNIXTIME(c.anchorTime),INTERVAL d.period DAY)<=DATE_FORMAT(NOW(),'%Y-%m-%d') ";
		           if(eFeeDto.getClientId()!=null){
		        	   sql+=" and a.clientId="+eFeeDto.getClientId();
		           }
		           if(eFeeDto.getProductId()!=null){
		        	   sql+=" and a.productId="+eFeeDto.getProductId();
		           }
		           if(eFeeDto.getCargoCode()!=null){
		        	   sql+=" and a.code like '%"+eFeeDto.getCargoCode()+"%'";
		           }
		           if(eFeeDto.getCargoId()!=null){
		        	   sql+=" and a.id="+eFeeDto.getCargoId();
		           }
		           if(eFeeDto.getStartTime()!=null&&eFeeDto.getStartTime()!=-1){
		        	   sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)>="+eFeeDto.getStartTime();
		           }
		           if(eFeeDto.getEndTime()!=null&&eFeeDto.getEndTime()!=-1){
		        	   sql+=" and UNIX_TIMESTAMP(b.arrivalStartTime)<="+eFeeDto.getEndTime();
		           }
		           if(eFeeDto.getIsFinish()!=null){
		        	   if(eFeeDto.getIsFinish()==0){
		        		   sql+=" and ( a.isFinish=0 or isNull(a.isFinish)) ";
		        	   }else if(eFeeDto.getIsFinish()==1){
		        		   sql+=" and a.isFinish=1";
		        	   }
		           }
//		           if(!Common.isNull(eFeeDto.getIsShowAll())){
//		        	   if(eFeeDto.getIsShowAll()==1){//只显示符合时间条件的
//		        	   sql+=" and DATE_ADD(FROM_UNIXTIME(c.anchorTime),INTERVAL d.period DAY)<=DATE_FORMAT(NOW(),'%Y-%m-%d') "//未到超期日期
//		        		   +" and ( (select DATE_FORMAT(max(j.endTime),'%Y-%m-%d') from t_pcs_exceed_fee j where j.cargoId=a.id)<DATE_FORMAT(NOW(),'%Y-%m-%d') OR 
//		           +" (select DATE_FORMAT(max(j.endTime),'%Y-%m-%d') from t_pcs_exceed_fee j where j.cargoId=a.id) is null ";//
//		        	   }
//		           }
		           if(eFeeDto.getIndexTh()!=null&&eFeeDto.getIndexTh()==0){
		        	   sql+=" order by a.code asc ";
		           }else{
		           sql+=" order by b.arrivalStartTime desc ";
		           }
				return (int) getCount(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取超期费货批列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期费货批列表数量失败", e);
		}
		}

	@Override
	public List<Map<String, Object>> getExceedFeeLadingList(ExceedFeeDto eFeeDto, int startRecord, int maxresult)
			throws OAException {
		try {
			String sql =" select a.id ladingId, a.code ladingCode,a.goodsTotal goodsTotal,a.clientId clientId,c.name clientName,a.clientId sendClientId,c.name sendClientName,a.productId productId,e.name productName,a.type ladingType,"
						+" a.receiveClientId receiveClientId,d.name receiveClientName,DATE_FORMAT(a.createTime,'%Y-%m-%d') createTime,DATE_FORMAT(a.startTime,'%Y-%m-%d %T') startLadingTime,"
						+" DATE_FORMAT(a.endTime,'%Y-%m-%d %T') endLadingTime,"
						+ "(SELECT FROM_UNIXTIME(max(j.startTime),'%Y-%m-%d') from t_pcs_exceed_fee j where j.ladingId=a.id) startTime,"
						+ "(SELECT FROM_UNIXTIME(max(j.endTime),'%Y-%m-%d') from t_pcs_exceed_fee j where j.ladingId=a.id) endTime,"
						+ "a.isFinish isFinish,"
						+ "(SELECT j.status from t_pcs_exceed_fee j WHERE j.ladingId=a.id ORDER BY j.id DESC LIMIT 0,1 ) `status`,"
						+ "(SELECT count(j.id)  from t_pcs_exceed_fee i, t_pcs_fee_charge j WHERE i.ladingId=a.id and j.exceedId=i.id AND (j.feebillId is null or j.feebillId=0) ) feebillCount ,"
						+ " (SELECT count(1) from t_pcs_exceed_fee p where p.ladingId=a.id ) eFCount "//结算单数量
						+" from t_pcs_lading a"
						+" LEFT JOIN t_pcs_client c on a.clientId=c.id"
						+" LEFT JOIN t_pcs_client d on a.receiveClientId=d.id"
						+" LEFT JOIN t_pcs_product e on a.productId=e.id"
						+ " where 1=1 and a.status=1 and a.type=1"
//						+ " and ( (SELECT max(j.isFinish) from t_pcs_exceed_fee j where j.ladingId=a.id) is null or "
//						+ " (SELECT max(j.isFinish) from t_pcs_exceed_fee j where j.ladingId=a.id)=0) and a.type=1 "
						+ " and DATE_FORMAT(a.createTime,'%Y-%m-%d')<=DATE_FORMAT(NOW(),'%Y-%m-%d')  ";
			               
			if(eFeeDto.getClientId()!=null){
	        	   sql+=" and a.clientId="+eFeeDto.getClientId();
	           }
	           if(eFeeDto.getProductId()!=null){
	        	   sql+=" and a.productId="+eFeeDto.getProductId();
	           }
	           if(eFeeDto.getReceiveClientId()!=null){
					  sql+=" and a.receiveClientId="+eFeeDto.getReceiveClientId();
				  }
	           if(!Common.isNull(eFeeDto.getLadingCode())){
	        	   sql+=" and a.code like '%"+eFeeDto.getLadingCode()+"%'";
	           }
	           if(eFeeDto.getLadingId()!=null){
	        	   sql+=" and a.id="+eFeeDto.getLadingId();
	           }
	           if(eFeeDto.getStartTime()!=null&&eFeeDto.getStartTime()!=-1){
	        	   sql+=" and UNIX_TIMESTAMP(a.createTime)>="+eFeeDto.getStartTime();
	           }
	           if(eFeeDto.getEndTime()!=null&&eFeeDto.getEndTime()!=-1){
	        	   sql+=" and UNIX_TIMESTAMP(a.createTime)<="+eFeeDto.getEndTime();
	           }
	           if(eFeeDto.getIsFinish()!=null){
	        	   if(eFeeDto.getIsFinish()==0){
	        		   sql+=" and ( a.isFinish=0 or isNull(a.isFinish)) ";
	        	   }else if(eFeeDto.getIsFinish()==1){
	        		   sql+=" and a.isFinish=1";
	        	   }
	           }
	           if(eFeeDto.getIndexTh()!=null&&eFeeDto.getIndexTh()==0)
	        	   sql+=" order by a.code desc ";
	           else if(eFeeDto.getIndexTh()!=null&&eFeeDto.getIndexTh()==8)
		           sql+=" order by a.createTime desc ";
	           else
		           sql+=" order by a.createTime desc ";
	           if(maxresult!=0){
	        	   sql+=" limit "+startRecord+" , "+maxresult;
	           } 
				return executeQuery(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取超期费提单列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期费提单列表失败", e);
		}
	}

	@Override
	public int getExceedFeeLadingCount(ExceedFeeDto eFeeDto) throws OAException {
		try {
			String sql =" select count(*) from t_pcs_lading a"
					+" LEFT JOIN t_pcs_client c on a.clientId=c.id"
					+" LEFT JOIN t_pcs_client d on a.receiveClientId=d.id"
					+" LEFT JOIN t_pcs_product e on a.productId=e.id"
					+ " where 1=1 and a.status=1 and a.type=1 "
//					+ " and ( (SELECT max(j.isFinish) from t_pcs_exceed_fee j where j.ladingId=a.id) is null or "
//					+ " (SELECT max(j.isFinish) from t_pcs_exceed_fee j where j.ladingId=a.id)=0)  and a.type=1 "
					+ "  and DATE_FORMAT(a.createTime,'%Y-%m-%d')<=DATE_FORMAT(NOW(),'%Y-%m-%d')  ";
			  if(eFeeDto.getClientId()!=null){
	        	   sql+=" and a.clientId="+eFeeDto.getClientId();
	           }
			  if(eFeeDto.getReceiveClientId()!=null){
				  sql+=" and a.receiveClientId="+eFeeDto.getReceiveClientId();
			  }
	           if(eFeeDto.getProductId()!=null){
	        	   sql+=" and a.productId="+eFeeDto.getProductId();
	           }
	           if(!Common.isNull(eFeeDto.getLadingCode())){
	        	   sql+=" and a.code like '%"+eFeeDto.getLadingCode()+"%'";
	           }
	           if(eFeeDto.getLadingId()!=null){
	        	   sql+=" and a.id="+eFeeDto.getLadingId();
	           }
	           if(eFeeDto.getStartTime()!=null&&eFeeDto.getStartTime()!=-1){
	        	   sql+=" and UNIX_TIMESTAMP(a.createTime)>="+eFeeDto.getStartTime();
	           }
	           if(eFeeDto.getEndTime()!=null&&eFeeDto.getEndTime()!=-1){
	        	   sql+=" and UNIX_TIMESTAMP(a.createTime)<="+eFeeDto.getEndTime();
	           }
	           if(eFeeDto.getIsFinish()!=null){
	        	   if(eFeeDto.getIsFinish()==0){
	        		   sql+=" and ( a.isFinish=0 or isNull(a.isFinish)) ";
	        	   }else if(eFeeDto.getIsFinish()==1){
	        		   sql+=" and a.isFinish=1";
	        	   }
	           }
	           sql+=" order by a.createTime desc ";
				return (int) getCount(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取超期费提单列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期费提单列表数量失败", e);
		}
	}
	
	@Override
	public List<Map<String, Object>> getExceedFeeList(ExceedFeeDto iFeeDto, int start, int limit)
			throws OAException {
		try {
		String sql ="SELECT a.id id, a.code code,a.cargoId cargoId,b.code cargoCode, b.goodsTotal cargoGoodsTotal,"
					+" a.ladingId ladingId,c.code ladingCode, c.goodsTotal ladingGoodsTotal, a.clientId clientId,d.name clientName,"
					+ "c.receiveClientId receiveClientId,i.name receiveClientName,"
					+ "c.clientId sendClientId,j.name sendClientName,"
					+" a.productId productId, e.name productName, FROM_UNIXTIME(a.accountTime,'%Y-%m-%d') accountTime,"
					+" a.exceedFee exceedFee , a.exceedCount exceedCount,a.exceedTotalFee exceedTotalFee,a.type type,"
					+" FROM_UNIXTIME(a.startTime,'%Y-%m-%d') startTime,FROM_UNIXTIME(a.endTime,'%Y-%m-%d') endTime,"
					+" a.createUserId createUserId,f.name createUserName,FROM_UNIXTIME(a.createTime) createTime,"
					+" a.billingUserId billingUserId,g.name billingUserName,FROM_UNIXTIME(a.billingTime) billingTime," 
					+" a.fundsUserId fundsUserId,h.name fundsUserName,FROM_UNIXTIME(a.fundsTime) fundsTime,"
					+" a.description description,a.status status,a.fundsStatus fundsStatus,a.billingStatus billingStatus,"
					+" a.fundsTotalFee fundsTotalFee, a.isFinish isFinish"
					+" from t_pcs_exceed_fee a"
					+" LEFT JOIN t_pcs_cargo b  on b.id=a.cargoId"
					+" LEFT JOIN t_pcs_lading c on c.id=a.ladingId"
					+" LEFT JOIN t_pcs_client d on d.id=a.clientId"
					+" LEFT JOIN t_pcs_product e on e.id=a.productId"
					+" LEFT JOIN t_auth_user f on f.id=a.createUserId"
					+" LEFT JOIN t_auth_user g on g.id=a.billingUserId"
					+" LEFT JOIN t_auth_user h on h.id=a.fundsUserId"
					+" LEFT JOIN t_pcs_client i on i.id=c.receiveClientId"
					+" LEFT JOIN t_pcs_client j on j.id=c.clientId ";
		          if(!Common.isNull(iFeeDto.getCargoCode())){
		        	  sql+=",(select DISTINCT a.exceedId id from t_pcs_fee_charge a left join t_pcs_charge_cargo_lading b on a.id=b.feeChargeId"
		        	  	 + " left join t_pcs_cargo c on c.id=b.cargoId where a.feeType=5 and c.code like '%"+iFeeDto.getCargoCode()
		        	  	 +"%' order by a.exceedId desc) tb";
		          }
					sql+=" where 1=1 ";
		           if(iFeeDto.getClientId()!=null){
		        	   sql+=" and a.clientId="+iFeeDto.getClientId();
		           }
		          if(iFeeDto.getCargoId()!=null){
		        	  sql+=" and a.cargoId="+iFeeDto.getCargoId();
		          }
		          if(!Common.isNull(iFeeDto.getCargoCode())){
		        	  sql+=" and a.id=tb.id ";
		          }
		          if(iFeeDto.getLadingId()!=null){
		        	  sql+=" and a.ladingId="+iFeeDto.getLadingId();
		          }
		           if(iFeeDto.getProductId()!=null){
		        	   sql+=" and a.productId="+iFeeDto.getProductId();
		           }
		           if(iFeeDto.getType()!=null&&iFeeDto.getType()!=-1){
		        	   sql+=" and a.type="+iFeeDto.getType();
		           }
		           if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
		        	   sql+=" and a.accountTime>="+iFeeDto.getStartTime();
		           }
		           if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
		        	   sql+=" and a.accountTime<="+iFeeDto.getEndTime();
		           }
		           if(iFeeDto.getStatus()!=null&&iFeeDto.getStatus()!=-1){
					   sql+="  AND a.status="+iFeeDto.getStatus();
				   }
		           if(iFeeDto.getId()!=null){
		        	   sql+=" and a.id="+iFeeDto.getId();
		           }
		           if(iFeeDto.getIsFinish()!=null&&iFeeDto.getIsFinish()!=2){
		        	   sql+=" and a.isFinish="+iFeeDto.getIsFinish();
		           }
		           if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==0)
		        	   sql+=" order by a.code desc ";
		           else if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==1)
			           sql+=" order by a.accountTime desc ";
		           else if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==12)
			           sql+=" order by a.status asc ";
		           else
		        	   sql+=" order by a.code desc ";
		           if(limit!=0){
		        	   sql+=" limit "+start+" , "+limit;
		           }
			return executeQuery(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取超期费列表失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期费列表失败", e);
	}
	}

	@Override
	public int getExceedFeeCount(ExceedFeeDto iFeeDto) throws OAException {
		try {
			String sql="SELECT count(*) "
					+" from t_pcs_exceed_fee a"
					+" LEFT JOIN t_pcs_cargo b  on b.id=a.cargoId"
					+" LEFT JOIN t_pcs_lading c on c.id=a.ladingId"
					+" LEFT JOIN t_pcs_client d on d.id=a.clientId"
					+" LEFT JOIN t_pcs_product e on e.id=a.productId"
					+" LEFT JOIN t_auth_user f on f.id=a.createUserId"
					+" LEFT JOIN t_auth_user g on g.id=a.billingUserId"
					+" LEFT JOIN t_auth_user h on h.id=a.fundsUserId";
			 if(!Common.isNull(iFeeDto.getCargoCode())){
	        	  sql+=",(select DISTINCT a.exceedId id from t_pcs_fee_charge a left join t_pcs_charge_cargo_lading b on a.id=b.feeChargeId"
	        	  	 + " left join t_pcs_cargo c on c.id=b.cargoId where a.feeType=5 and c.code like '%"+iFeeDto.getCargoCode()
	        	  	 +"%' order by a.exceedId desc) tb";
	          }
				sql+=" where 1=1 ";
		           if(iFeeDto.getClientId()!=null){
		        	   sql+=" and a.clientId="+iFeeDto.getClientId();
		           }
		           if(iFeeDto.getProductId()!=null){
		        	   sql+=" and a.productId="+iFeeDto.getProductId();
		           }
		           if(iFeeDto.getCargoId()!=null){
			        	  sql+=" and a.cargoId="+iFeeDto.getCargoId();
			          }
		           if(!Common.isNull(iFeeDto.getCargoCode())){
			        	  sql+=" and a.id=tb.id ";
			          }
			          if(iFeeDto.getLadingId()!=null){
			        	  sql+=" and a.ladingId="+iFeeDto.getLadingId();
			          }
		           if(iFeeDto.getType()!=null&&iFeeDto.getType()!=-1){
		        	   sql+=" and a.type="+iFeeDto.getType();
		           }
		           if(iFeeDto.getStatus()!=null&&iFeeDto.getStatus()!=-1){
					   sql+="  AND a.status="+iFeeDto.getStatus();
				   }
		           if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
		        	   sql+=" and a.accountTime>="+iFeeDto.getStartTime();
		           }
		           if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
		        	   sql+=" and a.accountTime<="+iFeeDto.getEndTime();
		           }
		           if(iFeeDto.getIsFinish()!=null&&iFeeDto.getIsFinish()!=2){
		        	   sql+=" and a.isFinish="+iFeeDto.getIsFinish();
		           }
		           if(iFeeDto.getId()!=null){
		        	   sql+=" and a.id="+iFeeDto.getId();
		           }
		           sql+=" order by a.code desc ";
			return (int) getCount(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取超期费列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期费列表数量失败", e);
	}
	}

	@Override
	public int addExceedFee(ExceedFee exceedFee) throws OAException {
		try {
			return (Integer) save(exceedFee);
	}catch (RuntimeException e) {
		LOG.error("dao添加超期费失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加超期费失败", e);
	}
	}

	@Override
	public void updateExceedFee(ExceedFee exceedFee) throws OAException {
		try {
		String sql=" update t_pcs_exceed_fee set id=id ";
		if(exceedFee.getCode()!=null){
			sql+=" , code='"+exceedFee.getCode()+"'";
		}
		if(exceedFee.getCargoId()!=null){
			sql+=" , cargoId="+exceedFee.getCargoId();
		}
		if(exceedFee.getLadingId()!=null){
			sql+=" ,ladingId="+exceedFee.getLadingId();
		}
		if(exceedFee.getStartTime()!=null){
			sql+=" ,startTime="+exceedFee.getStartTime();
		}
		if(exceedFee.getEndTime()!=null){
			sql+=" ,endTime="+exceedFee.getEndTime();
		}
		if(exceedFee.getExceedFee()!=null){
			sql+=" ,exceedFee="+exceedFee.getExceedFee();
		}
		if(exceedFee.getExceedCount()!=null){
			sql+=" ,exceedCount="+exceedFee.getExceedCount();
		}
		if(exceedFee.getExceedTotalFee()!=null){
			sql+=" ,exceedTotalFee="+exceedFee.getExceedTotalFee();
		}
		if(exceedFee.getProductId()!=null){
			sql+=" ,productId="+exceedFee.getProductId();
		}
		if(exceedFee.getClientId()!=null){
			sql+=" ,clientId="+exceedFee.getClientId();
		}
		if(exceedFee.getType()!=null){
			sql+=" ,type="+exceedFee.getType();
		}
		if(exceedFee.getCreateUserId()!=null){
			sql+=" ,createUserId="+exceedFee.getCreateUserId();
		}
		if(exceedFee.getCreateTime()!=null){
			sql+=" ,createTime="+exceedFee.getCreateTime();
		}
		if(exceedFee.getAccountTime()!=null){
			sql+=",accountTime="+exceedFee.getAccountTime();
		}
		if(exceedFee.getStatus()!=null){
			sql+=" ,status="+exceedFee.getStatus();
		}
		if(exceedFee.getFundsTotalFee()!=null){
			sql+=" ,fundsTotalFee="+exceedFee.getFundsTotalFee();
		}
		if(exceedFee.getFundsStatus()!=null){
			sql+=" ,fundsStatus="+exceedFee.getFundsStatus();
		}
		if(exceedFee.getBillingStatus()!=null){
			sql+=" ,billingStatus="+exceedFee.getBillingStatus();
		}
		if(exceedFee.getDescription()!=null){
			sql+=",description='"+exceedFee.getDescription()+"'";
		}
		if(exceedFee.getBillingUserId()!=null){
			sql+=",billingUserId="+exceedFee.getBillingUserId();
		}
		if(exceedFee.getBillingTime()!=null){
			sql+=",billingTime="+exceedFee.getBillingTime();
		}
		if(exceedFee.getFundsUserId()!=null){
			sql+=" ,fundsUserId="+exceedFee.getFundsUserId();
		}
		if(exceedFee.getFundsTime()!=null){
			sql+=",fundsTime="+exceedFee.getFundsTime();
		}
		if(exceedFee.getIsFinish()!=null){
			sql+=",isFinish="+exceedFee.getIsFinish();
		}
			sql+=" where id="+exceedFee.getId();
		executeUpdate(sql);
	}catch (RuntimeException e) {
		LOG.error("dao更新超期费失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新超期费失败", e);
	}
	}

	@Override
	public void deleteExceedFee(ExceedFeeDto iFeeDto) throws OAException {
		try {
			String sql="delete from t_pcs_exceed_fee where id in ("+iFeeDto.getId()+")";
			execute(sql);	
	}catch (RuntimeException e) {
		LOG.error("dao删除超期费失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除超期费失败", e);
	}
	}

	@Override
	public Map<String,Object> getCodeNum(ExceedFeeDto iFeeDto) throws OAException {
		try {
			String sql="select LPAD(IFNULL(MAX(code),0)+1,7,0) codeNum from t_pcs_exceed_fee";
			return executeQueryOne(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取编号失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取编号失败", e);
	}
	}

	@Override
	public List<Map<String, Object>> getExceedFeeCargoItemList(ExceedFeeDto eFeeDto)throws OAException {
		try {
			String sql;
			if(eFeeDto.getEndTime()!=null){
				sql="call getEveryDayStorageCostForCargo('"+new Timestamp(eFeeDto.getStartTime()*1000).toString().substring(0, 10)+"','"+new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10)+"',"+eFeeDto.getCargoId()+")";
			}else{
				sql="call getEveryDayStorageCostForCargo('2100-01-01','2100-01-01',"+eFeeDto.getCargoId()+")";
			}
			return executeProcedure(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取每天货批超期失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取每天货批超期失败", e);
	}
	}

	@Override
	public List<Map<String, Object>> getExceedFeeLadingItemList(ExceedFeeDto eFeeDto) throws OAException {
		try {
			String sql;
			if(eFeeDto.getEndTime()!=null){
				sql="call getEveryDayStorageCostForLading('"+new Timestamp(eFeeDto.getStartTime()*1000).toString().substring(0, 10)+"','"+new Timestamp(eFeeDto.getEndTime()*1000).toString().substring(0, 10)+"',"+eFeeDto.getLadingId()+")";
			}else{
				sql="call getEveryDayStorageCostForLading('2100-01-01','2100-01-01',"+eFeeDto.getLadingId()+")";
			}
			return executeProcedure(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取每天提单超期失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取每天提单超期失败", e);
	}
	}

	@Override
	public List<Map<String, Object>> getInboundMsgListByExceedId(int exceedId)
			throws OAException {
		try {
			if(exceedId!=0){
			String sql="SELECT CONCAT(h.`name`,'/',i.refName,'/',DATE_FORMAT(j.arrivalStartTime,'%Y-%m-%d')) inboundMsg,k.id cargoId,k.code cargoCode,k.storageType storageType,k.taxType type,l.type contractType,l.period periodTime from "
					+ " ( SELECT DISTINCT f.cargoId id  from t_pcs_exceed_fee d  LEFT JOIN t_pcs_goods_group e on e.ladingId=d.ladingId LEFT JOIN t_pcs_goods f on f.goodsGroupId=e.id "
					+ " WHERE d.id="+exceedId+" ) tb "
						+ " LEFT JOIN t_pcs_cargo k ON k.id = tb.id LEFT JOIN  t_pcs_arrival j ON j.id = k.arrivalId LEFT JOIN  t_pcs_ship h ON h.id = j.shipId LEFT JOIN t_pcs_ship_ref i ON i.id = j.shipRefId LEFT JOIN t_pcs_contract l ON l.id = k.contractId"
						+ " where tb.id!=0";
			return executeQuery(sql);
			}else{
			return null;
			}
	}catch (RuntimeException e) {
		LOG.error("dao获取每天提单超期失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期提单货批船信失败", e);
	}
	}
	@Override
	public List<Map<String, Object>> getInboundMsgListByCargoId(int cargoId)
			throws OAException {
		try {
			if(cargoId!=0){
			String sql="SELECT CONCAT(h.`name`,'/',i.refName,'/',DATE_FORMAT(j.arrivalStartTime,'%Y-%m-%d')) inboundMsg,k.id cargoId,k.code cargoCode,k.taxType type,l.type contractType,l.period periodTime from t_pcs_cargo k "
					+ " LEFT JOIN  t_pcs_arrival j ON j.id = k.arrivalId LEFT JOIN  t_pcs_ship h ON h.id = j.shipId LEFT JOIN t_pcs_ship_ref i ON i.id = j.shipRefId LEFT JOIN t_pcs_contract l ON l.id = k.contractId "
						+ " WHERE  k.id="+cargoId+" ";
			return executeQuery(sql);
			}else{
			return null;
			}
	}catch (RuntimeException e) {
		LOG.error("dao获取每天提单超期失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期提单货批船信失败", e);
	}
	}
	
	
	
	@Override
	public void cleanCargo(ExceedFeeDto eFeeDto) throws OAException {
		try {
			
			String sql ="update t_pcs_cargo set isFinish="+eFeeDto.getIsFinish()+" where id="+eFeeDto.getCargoId();
			executeUpdate(sql);
			sql=" update t_pcs_goods set isFinish="+eFeeDto.getIsFinish()+" where cargoId="+eFeeDto.getCargoId()+" and (isPredict is null or isPredict!=1) and  ISNULL(rootGoodsId)";
			executeUpdate(sql);
	}catch (RuntimeException e) {
		LOG.error("dao结清超期货批失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao结清超期货批失败", e);
	}
	}

	@Override
	public void cleanLading(ExceedFeeDto eFeeDto) throws OAException {
		try {
			String sql="update t_pcs_lading set isFinish="+eFeeDto.getIsFinish()+" where id="+eFeeDto.getLadingId();
			executeUpdate(sql);
			sql=" update t_pcs_goods set isFinish="+eFeeDto.getIsFinish()+" where goodsGroupId is not null and goodsGroupId in (select id from t_pcs_goods_group where ladingId="+eFeeDto.getLadingId()+" )";
			executeUpdate(sql);
	}catch (RuntimeException e) {
		LOG.error("dao 结清超期提单失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao 结清超期提单失败", e);
	}
	}

	@Override
	public List<Map<String, Object>> getInboundMsgListByLadingId(int ladingId)
			throws OAException {
		try {
			if(ladingId!=0){
				String sql="SELECT CONCAT(h.`name`,'/',i.refName,'/',DATE_FORMAT(j.arrivalStartTime,'%Y-%m-%d')) inboundMsg,k.id cargoId,k.code cargoCode,tb.goodsTotal goodsTotal "
						+ " from (SELECT DISTINCT f.cargoId id,round(sum(f.goodsTotal),3) goodsTotal  from  t_pcs_goods_group e LEFT JOIN t_pcs_goods f on f.goodsGroupId=e.id  WHERE  e.ladingId="+ladingId+" group by f.cargoId ) tb  "
								+ " LEFT JOIN  t_pcs_cargo k ON k.id = tb.id LEFT JOIN  t_pcs_arrival j ON j.id = k.arrivalId LEFT JOIN  t_pcs_ship h ON h.id = j.shipId LEFT JOIN t_pcs_ship_ref i ON i.id = j.shipRefId LEFT JOIN t_pcs_contract l ON l.id = k.contractId ";
			return executeQuery(sql);
			}else{
			return null;
			}
	}catch (RuntimeException e) {
		LOG.error("dao获取每天提单超期提单货批船信失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期提单货批船信失败", e);
	}
	}

	@Override
	public void backStatus(Integer id) throws OAException {
		try {
			if(id!=0){
				String sql="update t_pcs_fee_charge a set a.type=0 where  a.exceedId="+id;
			 executeUpdate(sql);
			}
	}catch (RuntimeException e) {
		LOG.error("dao获取每天提单超期提单货批船信失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期提单货批船信失败", e);
	}
	}

	/**
	 * @Title getExceedFeeMsg
	 * @Descrption:TODO
	 * @param:@param eDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年8月15日下午7:22:12
	 * @throws
	 */
	@Override
	public Map<String, Object> getExceedFeeMsg(ExceedFeeDto eDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("");
			sql.append("SELECT a.id, a.code,a.cargoId,b.code cargoCode, b.goodsTotal cargoGoodsTotal,")
			.append(" a.ladingId,c.code ladingCode, c.goodsTotal ladingGoodsTotal, a.clientId ,d.name clientName,")
			.append( "c.receiveClientId ,i.name receiveClientName,c.clientId sendClientId,j.name sendClientName,")
			.append(" a.productId , e.name productName, FROM_UNIXTIME(a.accountTime,'%Y-%m-%d') accountTime,")
			.append(" a.exceedFee , a.exceedTotalFee,a.type,");
			if(!Common.isNull(eDto.getType())&&eDto.getType()==2){
			sql.append("(SELECT GROUP_CONCAT(DISTINCT c.`code`) from t_pcs_goods_group w ")
			.append(" LEFT JOIN t_pcs_goods b ON w.id=b.goodsGroupId LEFT JOIN t_pcs_cargo c ")
			.append(" ON c.id=b.cargoId where w.ladingId=a.ladingId) cargoCodes,");	
			}
			sql.append(" FROM_UNIXTIME(a.startTime,'%Y-%m-%d') startTime,FROM_UNIXTIME(a.endTime,'%Y-%m-%d') endTime,")
			.append(" a.createUserId ,f.name createUserName,FROM_UNIXTIME(a.createTime) createTime,")
			.append("a.description ,a.status , a.isFinish ")
			.append(" from t_pcs_exceed_fee a")
			.append(" LEFT JOIN t_pcs_cargo b  on b.id=a.cargoId")
			.append(" LEFT JOIN t_pcs_lading c on c.id=a.ladingId")
			.append(" LEFT JOIN t_pcs_client d on d.id=a.clientId")
			.append(" LEFT JOIN t_pcs_product e on e.id=a.productId")
			.append(" LEFT JOIN t_auth_user f on f.id=a.createUserId")
			.append(" LEFT JOIN t_pcs_client i on i.id=c.receiveClientId")
			.append(" LEFT JOIN t_pcs_client j on j.id=c.clientId ")
			.append(" where a.id=").append(eDto.getId());
			return executeQueryOne(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取超期结算单信息失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期结算单信息失败", e);
	}
	}

	
}
