package com.skycloud.oa.inbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.InitialFeeDao;
import com.skycloud.oa.inbound.dto.InitialFeeDto;
import com.skycloud.oa.inbound.model.InitialFee;
import com.skycloud.oa.utils.Common;
/**
 *首期费结算单
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午2:23:48
 */
@Component
public class InitialFeeDaoImpl extends BaseDaoImpl implements InitialFeeDao {
	private static Logger LOG = Logger.getLogger(InitialFeeDaoImpl.class);

	@Override
	public List<Map<String, Object>> getInitialFeeList(InitialFeeDto iFeeDto, int start, int limit)
			throws OAException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT a.id id,a.code,a.cargoId,b.code cargoCode, a.totalFee,b.taxType,a.type,")
					.append("i.name productName,h.name clientName,c.type contractType,CONCAT(e.`name`,'/',f.refName) shipName,")
					.append("DATE_FORMAT(d.arrivalStartTime,'%Y-%m-%d') arrivalTime,")
					.append("b.goodsInspect,FROM_UNIXTIME(a.accountTime,'%Y-%m-%d') accountTime,a.status")
					.append(" FROM t_pcs_initial_fee a")
					.append(" LEFT JOIN t_pcs_cargo b ON a.cargoId = b.id")
					.append(" LEFT JOIN t_pcs_contract c ON b.contractId = c.id")
					.append(" LEFT JOIN t_pcs_arrival d ON b.arrivalId = d.id")
					.append(" LEFT JOIN t_pcs_ship e ON d.shipId = e.id")
					.append(" LEFT JOIN t_pcs_ship_ref f ON d.shipRefId = f.id")
					.append(" LEFT JOIN t_pcs_client h ON h.id = b.clientId")
					.append(" LEFT JOIN t_pcs_product i ON i.id = b.productId")
					.append(" where 1=1 and a.type=1 and !ISNULL(b.goodsTotal) and b.goodsTotal!=0 and f.refName!='转输' ");
			if (!Common.isNull(iFeeDto.getId()))
				sql.append(" and a.id=").append(iFeeDto.getId());
			if (!Common.isNull(iFeeDto.getClientId()))
				sql.append(" and b.clientId=").append(iFeeDto.getClientId());

			if (!Common.isNull(iFeeDto.getInitialCode()))
				sql.append(" and a.code like'%").append(iFeeDto.getInitialCode()).append("%'");

			if (!Common.isNull(iFeeDto.getProductId()))
				sql.append(" and b.productId=").append(iFeeDto.getProductId());

			if (!Common.isNull(iFeeDto.getGoodsInspect()))
				sql.append(" and b.goodsInspect=").append(iFeeDto.getGoodsInspect());

			if (!Common.isNull(iFeeDto.getType()) && iFeeDto.getType() == 1)
				sql.append(" and c.type in(1,2,3,4)");
			else if (!Common.isNull(iFeeDto.getType()) && iFeeDto.getType() == 5)
				sql.append(" and c.type =5");

			if (!Common.isNull(iFeeDto.getIsFinish()) && iFeeDto.getIsFinish() != 2)
				sql.append(" and a.isFinish=").append(iFeeDto.getIsFinish());

			if (!Common.isNull(iFeeDto.getCargoCode()))
				sql.append(" and b.code like '%").append(iFeeDto.getCargoCode()).append("%'");
			
			if(iFeeDto.getStatus()!=null&&iFeeDto.getStatus()!=-1)
				sql.append(" and a.status=").append(iFeeDto.getStatus());
			
			if (iFeeDto.getStartTime() != null && iFeeDto.getStartTime() != -1)
				sql.append(" and UNIX_TIMESTAMP(d.arrivalStartTime) >=").append(iFeeDto.getStartTime());

			if (iFeeDto.getEndTime() != null && iFeeDto.getEndTime() != -1)
				sql.append(" and UNIX_TIMESTAMP(d.arrivalStartTime) <=").append(iFeeDto.getEndTime());

//			if (iFeeDto.getIndexTh() != null && iFeeDto.getIndexTh() == 0)
//				sql.append(" order by b.code desc");
//			else if (iFeeDto.getIndexTh() != null && iFeeDto.getIndexTh() == 1)
//				sql.append(" order by a.code desc");
//			else if (iFeeDto.getIndexTh() != null && iFeeDto.getIndexTh() == 2)
//				sql.append(" order by a.accountTime desc");
//			else if (iFeeDto.getIndexTh() != null && iFeeDto.getIndexTh() == 4)
				sql.append(" order by d.arrivalStartTime DESC,a.id desc");
//			else if (iFeeDto.getIndexTh() != null && iFeeDto.getIndexTh() == 11)
//				sql.append(" AND !ISNULL(a.code) order by a.status asc");
//			else
//				sql.append(" order by d.arrivalStartTime desc");

			if (limit != 0)
				sql.append(" limit ").append(start).append(" , ").append(limit);
		       	
			return executeQuery(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取首期费列表失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表失败", e);
	}
	}
	
	@Override
	public int getInitialFeeCount(InitialFeeDto iFeeDto) throws OAException {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT count(1) FROM t_pcs_initial_fee a")
					.append(" LEFT JOIN t_pcs_cargo b ON a.cargoId = b.id")
					.append(" LEFT JOIN t_pcs_contract c ON b.contractId = c.id")
					.append(" LEFT JOIN t_pcs_arrival d ON b.arrivalId = d.id")
					.append(" LEFT JOIN t_pcs_ship e ON d.shipId = e.id")
					.append(" LEFT JOIN t_pcs_ship_ref f ON d.shipRefId = f.id")
					.append(" LEFT JOIN t_pcs_client h ON h.id = b.clientId")
					.append(" LEFT JOIN t_pcs_product i ON i.id = b.productId")
					.append(" where 1=1 and a.type=1 and !ISNULL(b.goodsTotal) and b.goodsTotal!=0 and f.refName!='转输' ");
			if (!Common.isNull(iFeeDto.getId()))
				sql.append(" and a.id=").append(iFeeDto.getId());
			
			if (!Common.isNull(iFeeDto.getClientId()))
				sql.append(" and b.clientId=").append(iFeeDto.getClientId());

			if (!Common.isNull(iFeeDto.getInitialCode()))
				sql.append(" and a.code like'%").append(iFeeDto.getInitialCode()).append("%'");

			if (!Common.isNull(iFeeDto.getProductId()))
				sql.append(" and b.productId=").append(iFeeDto.getProductId());

			if (!Common.isNull(iFeeDto.getGoodsInspect()))
				sql.append(" and b.goodsInspect=").append(iFeeDto.getGoodsInspect());

			if (!Common.isNull(iFeeDto.getType()) && iFeeDto.getType() == 1)
				sql.append(" and c.type in(1,2,3,4)");
			else if (!Common.isNull(iFeeDto.getType()) && iFeeDto.getType() == 5)
				sql.append(" and c.type =5");

			if (!Common.isNull(iFeeDto.getIsFinish()) && iFeeDto.getIsFinish() != 2)
				sql.append(" and a.isFinish=").append(iFeeDto.getIsFinish());

			if (!Common.isNull(iFeeDto.getCargoCode()))
				sql.append(" and b.code like '%").append(iFeeDto.getCargoCode()).append("%'");
			
			if(iFeeDto.getStatus()!=null&&iFeeDto.getStatus()!=-1)
				sql.append(" and a.status=").append(iFeeDto.getStatus());
			
			if (iFeeDto.getStartTime() != null && iFeeDto.getStartTime() != -1)
				sql.append(" and UNIX_TIMESTAMP(d.arrivalStartTime) >=").append(iFeeDto.getStartTime());

			if (iFeeDto.getEndTime() != null && iFeeDto.getEndTime() != -1)
				sql.append(" and UNIX_TIMESTAMP(d.arrivalStartTime) <=").append(iFeeDto.getEndTime());
			return (int) getCount(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取首期费列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表数量失败", e);
	}
	}
	public List<Map<String,Object>> getList(InitialFeeDto iFeeDto, int start, int limit) throws OAException{
		try {
			StringBuilder sql=new StringBuilder();
			if(!Common.isNull(iFeeDto.getId())){
			 sql.append("SELECT a.*,c.type contractType ")
					 .append(" FROM t_pcs_initial_fee a")
					.append(" LEFT JOIN t_pcs_cargo b ON a.cargoId = b.id")
					.append(" LEFT JOIN t_pcs_contract c ON b.contractId = c.id")
					.append(" LEFT JOIN t_pcs_arrival d on d.id=b.arrivalId ")
					.append(" where a.id=").append(iFeeDto.getId())
			 		.append(" order by d.arrivalStartTime DESC");
				return executeQuery(sql.toString());
			}
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表失败", e);
		}
		return null;
	}
	
	public int getCount(InitialFeeDto iFeeDto) throws OAException{
		try{
			String sql=" select count(*) "
					+ " from t_pcs_initial_fee a"
					+ " where 1=1 ";
			         if(iFeeDto.getId()!=null){
			        	 sql+=" and a.id="+iFeeDto.getId();
			         }		
			return (int) getCount(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表数量失败", e);
		}
	}


	@Override
	public int addInitialFee(InitialFee initialFee) throws OAException {
		try {
			String sql="INSERT into t_pcs_initial_fee (cargoId,status,type) SELECT "+initialFee.getCargoId()+",0,1 "
					+ "FROM DUAL WHERE not EXISTS (SELECT cargoId FROM t_pcs_initial_fee where cargoId ="+initialFee.getCargoId()+" and cargoId is not null)";
	  return (int) insert(sql);
	}catch (RuntimeException e) {
		LOG.error("dao添加首期费失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加首期费失败", e);
	}
	}

	@Override
	public void updateInitialFee(InitialFee initialFee) throws OAException {
		try {
		String sql=" update t_pcs_initial_fee set id=id ";
		if(initialFee.getCargoId()!=null){
			sql+=" , cargoId="+initialFee.getCargoId();
		}
		if(initialFee.getCode()!=null){
			sql+=" , code='"+initialFee.getCode()+"'";
		}
		if(initialFee.getTotalFee()!=null){
			sql+=" ,totalFee="+initialFee.getTotalFee();
		}
		if(initialFee.getType()!=null){
			sql+=" ,type="+initialFee.getType();
		}
		if(initialFee.getCreateUserId()!=null){
			sql+=" ,createUserId="+initialFee.getCreateUserId();
		}
		if(initialFee.getCreateTime()!=null){
			sql+=" ,createTime="+initialFee.getCreateTime();
		}
		if(initialFee.getAccountTime()!=null){
			sql+=",accountTime="+initialFee.getAccountTime();
		}
		if(initialFee.getStatus()!=null){
			sql+=" ,status="+initialFee.getStatus();
		}
		if(initialFee.getContractId()!=null){
			sql+=",contractId="+initialFee.getContractId();
		}
		if(initialFee.getContractNum()!=null){
			sql+=",contractNum="+initialFee.getContractNum();
		}
		if(initialFee.getIsFinish()!=null){
			sql+=",isFinish="+initialFee.getIsFinish();
		}
		if(initialFee.getStartTime()!=null&&initialFee.getStartTime()!=-1){
			sql+=", startTime="+initialFee.getStartTime();
		}
		if(initialFee.getEndTime()!=null&&initialFee.getEndTime()!=-1){
			sql+=",endTime="+initialFee.getEndTime();
		}
		if(initialFee.getArrivalId()!=null){
			sql+=", arrivalId="+initialFee.getArrivalId();
		}
		if(initialFee.getCargoCodes()!=null){
			sql+=",cargoCodes='"+initialFee.getCargoCodes()+"'";
		}
		if(initialFee.getDescription()!=null){
			sql+=",description='"+initialFee.getDescription()+"'";
		}
		if(initialFee.getId()!=null){
			sql+=" where id="+initialFee.getId();
		}else{
			sql+=" where cargoId="+initialFee.getCargoId();
		}
		executeUpdate(sql);
	}catch (RuntimeException e) {
		LOG.error("dao更新首期费失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新首期费失败", e);
	}
	}

	@Override
	public void deleteInitialFee(InitialFeeDto iFeeDto) throws OAException {
		try {
			String sql;
			if(!Common.isNull(iFeeDto.getId())&&(iFeeDto.getType()==1||iFeeDto.getType()==5)){
				sql="update t_pcs_initial_fee set status=0,accountTime=null,code=null,"
						+ "createTime=null,createUserId=null,isFinish=0,totalFee=null"
						+ " where id="+iFeeDto.getId();
				executeUpdate(sql);
			}else if(iFeeDto.getType()==2||iFeeDto.getType()==3||iFeeDto.getType()==4){
				sql="delete from t_pcs_initial_fee where 1=1";
				if(iFeeDto.getId()!=null){
					sql+=" and id="+iFeeDto.getId();
				}
				if(iFeeDto.getIds()!=null&&iFeeDto.getIds()!=""){
					sql+=" and id in ("+iFeeDto.getIds()+")";
				}
				if(iFeeDto.getCargoId()!=null){
					sql+=" and cargoId="+iFeeDto.getCargoId();
				}
			execute(sql);	
			}
			
			
	}catch (RuntimeException e) {
		LOG.error("dao删除首期费失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除首期费失败", e);
	}
	}

	@Override
	public Map<String,Object> getCodeNum(InitialFeeDto iFeeDto) throws OAException {
		try {
			String sql="select LPAD(IFNULL(MAX(code),0)+1,7,0) codeNum from t_pcs_initial_fee";
			return executeQueryOne(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取编号失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取编号失败", e);
	}
	}

	@Override
	public int addContractInitialFee(InitialFee initialFee) throws OAException {
		try {
		return	(Integer) save(initialFee);
	}catch (RuntimeException e) {
		LOG.error("dao获取编号失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取编号失败", e);
	}
	}

	@Override
	public List<Map<String, Object>> getContractFeeList(InitialFeeDto iFeeDto,int start,int limit)
			throws OAException {
		try {
			String sql ="SELECT a.id,a.code,a.type,a.cargoCodes, a.totalFee,c.productId,i.name productName,"
					+" c.clientId,h.name clientName,FROM_UNIXTIME(a.accountTime,'%Y-%m-%d') accountTime,a.status "
					+" FROM t_pcs_initial_fee a"
					+" LEFT JOIN t_pcs_contract c ON a.contractId = c.id"
					+" LEFT JOIN t_pcs_client h ON h.id = c.clientId"
					+" LEFT JOIN t_pcs_product i ON i.id = c.productId"
					+ " where a.type=2 ";
			         if(iFeeDto.getId()!=null){
	                   sql+=" and a.id="+iFeeDto.getId();		        	 
			         } 
			        if(iFeeDto.getClientId()!=null){
			        	sql+=" and c.clientId="+iFeeDto.getClientId();
			        }	
			        if(!Common.isNull(iFeeDto.getCargoCode())){
			        	sql+=" and a.cargoCodes like '%"+iFeeDto.getCargoCode()+"%'";
			        }
			         if(iFeeDto.getProductId()!=null){
			        	 sql+=" and c.productId="+iFeeDto.getProductId();
			         }
			         if(iFeeDto.getStatus()!=null&&iFeeDto.getStatus()!=-1){
			        	 sql+=" and a.status="+iFeeDto.getStatus();
			         }
			         if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
			        	 sql+=" and a.createTime >="+iFeeDto.getStartTime();
			         }
			         if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
			        	 sql+=" and a.createTime <="+iFeeDto.getEndTime();
			         }
				     if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==0)
				    	 sql+=" order by a.cargoCodes desc";
				     else if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==1)
				    	 sql+=" order by a.code desc";
				      else if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==2)
				    	  sql+=" order by a.accountTime desc";
				      else if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==6)
				    	  sql+=" order by a.status asc";
				      else
				    	  sql+=" order by a.createTime desc";
			       if(limit!=0){
			    	   sql+=" limit "+ start+" , "+limit;
			       }	
				return executeQuery(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表失败", e);
		}
	}

	@Override
	public int getContractFeeCount(InitialFeeDto iFeeDto)
			throws OAException {
		try {
			String sql=" select count(1) "
					+" FROM t_pcs_initial_fee a"
					+" LEFT JOIN t_pcs_contract c ON a.contractId = c.id"
					+" LEFT JOIN t_pcs_client h ON h.id = c.clientId"
					+" LEFT JOIN t_pcs_product i ON i.id = c.productId"
					+" where  a.type=2 ";
			      if(iFeeDto.getId()!=null){
                  sql+=" and a.id="+iFeeDto.getId();		        	 
		         } 
		        if(iFeeDto.getClientId()!=null){
		        	sql+=" and c.clientId="+iFeeDto.getClientId();
		        }	 
		        if(!Common.isNull(iFeeDto.getCargoCode())){
		        	sql+=" and a.cargoCodes like '%"+iFeeDto.getCargoCode()+"%'";
		        }
		         if(iFeeDto.getProductId()!=null){
		        	 sql+=" and c.productId="+iFeeDto.getProductId();
		         }
		         if(iFeeDto.getStatus()!=null&&iFeeDto.getStatus()!=-1){
		        	 sql+=" and a.status="+iFeeDto.getStatus();
		         }
		         if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
		        	 sql+=" and a.createTime >="+iFeeDto.getStartTime();
		         }
		         if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
		        	 sql+=" and a.createTime <="+iFeeDto.getEndTime();
		         }
		       sql+=" order by a.createTime asc";
			
			return (int) getCount(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取首期费列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表数量失败", e);
	}
	}
	/**
	 *根据合同号，起止时间获取时间内的已经入库的货批
	 */
	@Override
	public List<Map<String, Object>> getContractCargoList(InitialFeeDto iFeeDto)
			throws OAException {
		try {
			String sql="SELECT a.id,a.code cargoCode,round(a.goodsTotal,3) goodsTotal,FROM_UNIXTIME(b.anchorTime) arrivalTime  from t_pcs_cargo a "
					+ "INNER JOIN t_pcs_arrival_info b on a.arrivalId=b.arrivalId  where 1=1 ";
					if(iFeeDto.getContractId()!=null&&iFeeDto.getContractId()!=0){
					sql+= "and   a.contractId="+iFeeDto.getContractId();
					}
			        if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1&&iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=0){
			        	sql+=" and  b.anchorTime>="+iFeeDto.getStartTime()+" and b.anchorTime<="+iFeeDto.getEndTime();
			        }
			        sql+=" order by b.anchorTime asc";
			return executeQuery(sql);
		}catch (RuntimeException e) {
			LOG.error("dao根据合同号，起止时间获取时间内的已经入库的货批失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao根据合同号，起止时间获取时间内的已经入库的货批失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getInboundMsgByInitialId(Integer initialId)
			throws OAException {
		try {
			String sql="select cargoCodes cargoCode from t_pcs_initial_fee where type=2 and id="+initialId;
			return executeQuery(sql);
		}catch (RuntimeException e) {
			LOG.error("dao根据合同首期费id获取货批信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao根据合同合同首期费id获取货批信息失败", e);
		}
	}

	@Override
	public Map<String, Object> checkCargoFee(InitialFeeDto iFeeDto)
			throws OAException {
		try {
			if(iFeeDto.getCargoId()!=null){
			String sql="SELECT GROUP_CONCAT(feeType) feeType ,COUNT(1) count,cargoId,GROUP_CONCAT(feebillId) feebillId  from t_pcs_fee_charge where feeType in (1,5) and cargoId="+iFeeDto.getCargoId()+"  GROUP BY cargoId";
			return executeQueryOne(sql);
			}
		}catch (RuntimeException e) {
			LOG.error("dao校验货批是否生成了结算单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao校验货批是否生成了结算单失败", e);
		}
		return null;
	}

	@Override
	public void updateCargoFee(InitialFeeDto iFeeDto) throws OAException {
		try {
			if(iFeeDto.getCargoId()!=null&&iFeeDto.getClientId()!=null){
			String sql="update t_pcs_fee_charge set clientId="+iFeeDto.getClientId()+" ,feehead="+iFeeDto.getClientId()+" where feebill is null and feeType in(1,2,5) and cargoId="+iFeeDto.getCargoId();
			executeUpdate(sql);
			}
		}catch (RuntimeException e) {
			LOG.error("dao更新货批生成结算单未生成账单的货主和开票抬头失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新货批生成结算单未生成账单的货主和开票抬头失败", e);
		}
	}

	@Override
	public void backStatus(Integer id) throws OAException {
		try {
			
			String sql="update t_pcs_fee_charge set type=0  where feeType<>6 and initialId="+id;
			executeUpdate(sql);
		}catch (RuntimeException e) {
			LOG.error("dao回退首期费失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao回退首期费失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getOutBoundTotalList(InitialFeeDto iFeeDto) throws OAException {
 try {
	         StringBuilder sql=new StringBuilder();		
	
		 sql.append(" SELECT SUM(ROUND(a.actualNum,3)) actualNum,a.deliverType,c.name productName, ")
		 .append(" (case WHEN a.deliverType=1 THEN '车发' ELSE '船发' END) deliverTypeStr ")
		 .append("  from t_pcs_goodslog a   ")
		 .append(" LEFT JOIN t_pcs_goods b ON a.goodsId=b.id ")
		 .append(" LEFT JOIN t_pcs_product c ON c.id=b.productId ")
		 .append(" where a.type=5 and a.deliverType BETWEEN 1 AND 2 AND a.actualType=1 ");
        if(iFeeDto.getClientId()!=null){
        	sql.append(" AND b.clientId=").append(iFeeDto.getClientId());
        }
		 if(iFeeDto.getProductId()!=null){
			 sql.append(" AND b.productId=").append(iFeeDto.getProductId());
		 }
		 if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
			 sql.append(" AND a.createTime>=").append(iFeeDto.getStartTime());
		 }
		 if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
			 sql.append(" AND a.createTime<=").append(iFeeDto.getEndTime());
		 }
	     sql.append("  GROUP BY a.deliverType ORDER BY a.deliverType DESC ");
		return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao回退首期费失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao回退首期费失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getOutBoundDetailList(InitialFeeDto iFeeDto, int startRecord, int maxresult)
			throws OAException {
		try {
	         StringBuilder sql=new StringBuilder();		
				 sql.append("   SELECT ROUND(a.actualNum,3) actualNum,FROM_UNIXTIME(a.createTime) createTime, ");
				 if(iFeeDto.getDeliverType()==1){
					 sql.append(" c.name vsName");
				 }else{
					 sql.append("  CONCAT(d.`name`,'/',e.refName,'/',DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d')) vsName ");
				 }
				sql.append("  from t_pcs_goodslog a   ")
				 .append("  LEFT JOIN t_pcs_goods b ON a.goodsId=b.id ");
				 if(iFeeDto.getDeliverType()==1){
					 sql.append(" LEFT JOIN t_pcs_truck c ON c.id=a.vehicleShipId");
				 }else{
					 sql.append("  LEFT JOIN t_pcs_arrival c on c.id=a.batchId ")
					 .append("  LEFT JOIN t_pcs_ship d ON d.id=c.shipId ")
					 .append("  LEFT JOIN t_pcs_ship_ref e ON e.id=c.shipRefId ");
				 }
				 sql.append("  where a.type=5 and a.deliverType =").append(iFeeDto.getDeliverType()).append(" AND a.actualType=1 ");
	       if(iFeeDto.getClientId()!=null){
	       	sql.append(" AND b.clientId=").append(iFeeDto.getClientId());
	       }
		 if(iFeeDto.getProductId()!=null){
			 sql.append(" AND b.productId=").append(iFeeDto.getProductId());
		 }
		 if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
			 sql.append(" AND a.createTime>=").append(iFeeDto.getStartTime());
		 }
		 if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
			 sql.append(" AND a.createTime<=").append(iFeeDto.getEndTime());
		 }
	     sql.append(" ORDER BY a.createTime DESC ");
		return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao回退首期费失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao回退首期费失败", e);
		}
	}

	@Override
	public int getOutBoundDetailCount(InitialFeeDto iFeeDto) throws OAException {
		try {
	         StringBuilder sql=new StringBuilder();		
			 sql.append("   SELECT count(1)  from t_pcs_goodslog a  ")
			 .append("  LEFT JOIN t_pcs_goods b ON a.goodsId=b.id ")
			 .append("  where a.type=5 and a.deliverType =").append(iFeeDto.getDeliverType()).append(" AND a.actualType=1 ");
       if(iFeeDto.getClientId()!=null){
       	sql.append(" AND b.clientId=").append(iFeeDto.getClientId());
       }
	 if(iFeeDto.getProductId()!=null){
		 sql.append(" AND b.productId=").append(iFeeDto.getProductId());
	 }
	 if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
		 sql.append(" AND a.createTime>=").append(iFeeDto.getStartTime());
	 }
	 if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
		 sql.append(" AND a.createTime<=").append(iFeeDto.getEndTime());
	 }
		return (int) getCount(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao回退首期费失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao回退首期费失败", e);
		}
	}

	@Override
	public Map<String, Object> getDockFeeMsg(InitialFeeDto iFeeDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT a.id id, a.CODE code, a.type type, ")
			.append(" FROM_UNIXTIME(a.startTime) startTime,FROM_UNIXTIME(a.endTime) endTime,")
			.append(" a.totalFee totalFee, a.productId productId, c.NAME productName, a.clientId clientId,b.`name` clientName,a.contractNum contractNum, ")
			.append("  a.createUserId createUserId, d.name createUserName, FROM_UNIXTIME(a.createTime) createTime,FROM_UNIXTIME(a.accountTime) accountTime, ")
			.append("  a.status status,a.description description ")
			.append("  FROM t_pcs_initial_fee a ")
			.append(" LEFT JOIN t_pcs_client b ON b.id = a.clientId ")
			.append(" LEFT JOIN t_pcs_product c ON c.id = a.productId ")
			.append(" LEFT JOIN t_auth_user d on a.createUserId=d.id  ")
			.append("  where 1=1 and a.type=3 ").append(" and a.id=").append(iFeeDto.getId());

			return executeQueryOne(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao回退首期费失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao回退首期费失败", e);
	}
	}

	@Override
	public List<Map<String, Object>> getOilsFeeList(InitialFeeDto iFeeDto, int start, int limit)
			throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT a.id, a.code, a.type,a.totalFee, a.productId, c.name productName, a.clientId,")
			.append("b.`name` clientName,a.contractNum, FROM_UNIXTIME(a.accountTime,'%Y-%m-%d') accountTime,a.status ")
			.append("  FROM t_pcs_initial_fee a ")
			.append(" LEFT JOIN t_pcs_client b ON b.id = a.clientId ")
			.append(" LEFT JOIN t_pcs_product c ON c.id = a.productId ")
			.append("  where a.type=3 ");
				if(!Common.isNull(iFeeDto.getId()))
					sql.append(" and a.id=").append(iFeeDto.getId());
				 if(iFeeDto.getClientId()!=null){
		        	sql.append(" and a.clientId=").append(iFeeDto.getClientId());
		        }	    
		        if(iFeeDto.getInitialCode()!=null&&!"".equals(iFeeDto.getInitialCode())){
		        	sql.append(" and a.code like'%").append(iFeeDto.getInitialCode()).append("%'");
		        }
		         if(iFeeDto.getProductId()!=null){
		        	 sql.append(" and a.productId=").append(iFeeDto.getProductId());
		         }
		         if(iFeeDto.getStatus()!=null&&iFeeDto.getStatus()!=-1){
		        	 sql.append(" and a.status=").append(iFeeDto.getStatus());
		         }
		         if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
		        	 sql.append(" and a.accountTime >=").append(iFeeDto.getStartTime());
		         }
		         if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
		        	 sql.append(" and a.accountTime <=").append(iFeeDto.getEndTime());
		         }
		         if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==0)
		        	 sql.append(" order by a.code desc");
		         else if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==1)
		        	 sql.append(" order by a.accountTime desc");
		         else if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==5)
		        	 sql.append(" AND !ISNULL(a.code)  order by a.status asc");
		         else
		        	 sql.append(" order by a.code desc");
		         if(limit!=0){
		        	 sql.append(" limit ").append(start).append(" , ").append(limit);
			       }
			return executeQuery(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao回退首期费失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao回退首期费失败", e);
	}
	}

	@Override
	public int getOilsFeeCount(InitialFeeDto iFeeDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT count(1) ")
			.append("  FROM t_pcs_initial_fee a ")
			.append(" LEFT JOIN t_pcs_client b ON b.id = a.clientId ")
			.append(" LEFT JOIN t_pcs_product c ON c.id = a.productId ")
			.append("  where a.type=3 ");
			if(!Common.isNull(iFeeDto.getId()))
				sql.append(" and a.id=").append(iFeeDto.getId());
			 if(iFeeDto.getClientId()!=null){
		        	sql.append(" and a.clientId=").append(iFeeDto.getClientId());
		        }	    
		        if(iFeeDto.getInitialCode()!=null&&!"".equals(iFeeDto.getInitialCode())){
		        	sql.append(" and a.code like'%").append(iFeeDto.getInitialCode()).append("%'");
		        }
		         if(iFeeDto.getProductId()!=null){
		        	 sql.append(" and a.productId=").append(iFeeDto.getProductId());
		         }
		         if(iFeeDto.getStatus()!=null&&iFeeDto.getStatus()!=-1){
		        	 sql.append(" and a.status=").append(iFeeDto.getStatus());
		         }
		         if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
		        	 sql.append(" and a.accountTime >=").append(iFeeDto.getStartTime());
		         }
		         if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
		        	 sql.append(" and a.accountTime <=").append(iFeeDto.getEndTime());
		         }
			return (int) getCount(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao回退首期费失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao回退首期费失败", e);
	}
	}

	/**
	 * @Title getOutBoundSecurityFeeList
	 * @Descrption:TODO出库保安费列表
	 * @param:@param iFeeDto
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @param:@throws OAException
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年2月15日下午7:43:37
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getOutBoundSecurityFeeList(InitialFeeDto iFeeDto, int start, int limit)
			throws OAException {
		try{
		 StringBuilder sql=new StringBuilder();
			
		   sql.append(" SELECT   a.id arrivalId,a.shipId ,a.type arrivalType,CONCAT(c.`name`,'/',d.refName) shipNames,d.refName shipRefName,c.name shipName,a.shipRefId,  ")
		   .append(" DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') arrivalTime,a.productId,e.`name` productName, ")
		   .append(" GROUP_CONCAT(b.actualNum) actualNums,GROUP_CONCAT(DISTINCT b.ladingClientId) ladingClientIds, ")
		   .append(" GROUP_CONCAT( f.`name`) ladingClientName,GROUP_CONCAT( b.ladingEvidence) ladingEvidence,GROUP_CONCAT( k.tradeType) tradeType, ")
		   .append(" SUM(ROUND(b.actualNum,3)) actualNum,h.id,h.code,from_unixtime(h.accountTime,'%Y-%m-%d') accountTime,h.status,h.totalFee,h.type,")
		   .append(" GROUP_CONCAT(DISTINCT f.`name`) ladingClientNames,GROUP_CONCAT( j.`name`) clientNames")
		   .append(" from t_pcs_arrival a ")
		   .append(" LEFT JOIN t_pcs_goodslog b ON a.id=b.batchId and b.deliverType=2 AND b.type=5 AND b.actualType=1 ")
		   .append(" LEFT JOIN t_pcs_goods i ON i.id=b.goodsId ")
		   .append(" LEFT JOIN t_pcs_client j ON j.id=i.clientId ")
		   .append(" LEFT JOIN t_pcs_ship c ON c.id=a.shipId ")
		   .append(" LEFT JOIN t_pcs_ship_ref d ON d.id=a.shipRefId ")
		   .append(" LEFT JOIN t_pcs_product e ON e.id=a.productId ")
		   .append(" LEFT JOIN t_pcs_client f ON f.id=b.ladingClientId ")
		   .append(" LEFT JOIN t_pcs_arrival_plan k on k.arrivalId=a.id and k.goodsId=b.goodsId AND k.ladingCode=b.ladingEvidence  ")
		   .append(" LEFT JOIN t_pcs_initial_fee h ON h.arrivalId=a.id and h.type=4 ")
		   .append("  WHERE  c.`name`!='转输' ");
		   
		   if(!Common.isNull(iFeeDto.getTradeType())&&iFeeDto.getTradeType()!=-1){
			   if(iFeeDto.getTradeType()==1){
				sql.append(" AND a.type=5  ");   
			   }else if(iFeeDto.getTradeType()==2){
				   sql.append("AND a.type=2  AND a.`status`=54 and k.tradeType!=1 ");    
			   }
		   }else{
			   sql.append(" AND ((a.type=2  AND a.`status`=54 and k.tradeType!=1) or a.type=5) ");   
		   }
		   if(iFeeDto.getId()!=null){
			   sql.append(" AND a.id=").append(iFeeDto.getId());
		   }
		   if(!Common.isNull(iFeeDto.getShipName())){
			   sql.append(" AND (c.name like '%").append(iFeeDto.getShipName()).append("%' or d.refName like '%")
			   .append(iFeeDto.getShipName()).append("%' )");
		   }
		   if(iFeeDto.getProductId()!=null){
			   sql.append(" AND a.productId=").append(iFeeDto.getProductId());
		   }
		  
		   if(iFeeDto.getClientId()!=null){
			   sql.append(" AND i.clientId=").append(iFeeDto.getClientId());
		   }
		   if(!Common.isNull(iFeeDto.getInitialCode())){
			   sql.append(" AND h.code like '%").append(iFeeDto.getInitialCode()).append("%'");
		   }
		   if(iFeeDto.getStatus()!=null&&iFeeDto.getStatus()!=-1){
			   sql.append(" AND h.status=").append(iFeeDto.getStatus());
		   }
		   if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
	        	 sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) >=").append(iFeeDto.getStartTime());
	         }
	         if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
	        	 sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) <=").append(iFeeDto.getEndTime());
	         }
	         if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==12)
	        	 sql.append("  AND !ISNULL(h.code) "); 
		   sql.append(" GROUP BY a.id ");
		   
		   
		   if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==0)
			   sql.append(" ORDER BY h.code DESC ");
		   else if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==1)
			   sql.append(" ORDER BY h.accountTime DESC ");
		   else if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==3)
			   sql.append(" ORDER BY a.arrivalStartTime DESC ");
		   else if(iFeeDto.getIndexTh()!=null&&iFeeDto.getIndexTh()==12)
			   sql.append("  ORDER BY h.status asc ");
		   else 
			   sql.append(" ORDER BY a.arrivalStartTime DESC ");
			if(limit!=0){
				sql.append(" limit ").append(start).append(" , ").append(limit);
			}
		   
		return 	executeQuery(sql.toString());
			}catch (RuntimeException e) {
				LOG.error("dao出库保安费列表失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao出库保安费列表失败", e);
			}
	}

	/**
	 * @Title getOutBoundSecurityFeeCount
	 * @Descrption:TODO出库保安费列表数量
	 * @param:@param iFeeDto
	 * @param:@return
	 * @param:@throws OAException
	 * @return:int
	 * @auhor jiahy
	 * @date 2016年2月15日下午7:43:43
	 * @throws
	 */
	@Override
	public int getOutBoundSecurityFeeCount(InitialFeeDto iFeeDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			 sql.append(" SELECT count(DISTINCT a.id) ")
			   .append(" from t_pcs_arrival a ")
			   .append(" LEFT JOIN t_pcs_goodslog b ON a.id=b.batchId and b.deliverType=2 AND b.type=5 AND b.actualType=1 ")
			   .append(" LEFT JOIN t_pcs_goods i ON i.id=b.goodsId ")
			   .append(" LEFT JOIN t_pcs_client j ON j.id=i.clientId ")
			   .append(" LEFT JOIN t_pcs_arrival_plan k on k.arrivalId=a.id and k.goodsId=b.goodsId ")
			   .append(" LEFT JOIN t_pcs_ship c ON c.id=a.shipId ")
			   .append(" LEFT JOIN t_pcs_ship_ref d ON d.id=a.shipRefId ")
			   .append(" LEFT JOIN t_pcs_product e ON e.id=a.productId ")
			   .append(" LEFT JOIN t_pcs_client f ON f.id=b.ladingClientId ")
			   .append(" LEFT JOIN t_pcs_initial_fee h ON h.arrivalId=a.id and h.type=4 ")
			   .append("  WHERE c.`name`!='转输' ");
			 if(!Common.isNull(iFeeDto.getTradeType())&&iFeeDto.getTradeType()!=-1){
				   if(iFeeDto.getTradeType()==1){
					sql.append(" AND a.type=5 AND a.tradeType=1 ");   
				   }else if(iFeeDto.getTradeType()==2){
					   sql.append("AND a.type=2  AND a.`status`=54 and k.tradeType!=1 ");    
				   }
			   }else{
				   sql.append(" AND ((a.type=2  AND a.`status`=54 and k.tradeType!=1) or a.type=5) ");   
			   }
			 if(iFeeDto.getArrivalId()!=null){
				   sql.append(" AND a.id=").append(iFeeDto.getArrivalId());
			   }
			 if(!Common.isNull(iFeeDto.getShipName())){
				   sql.append(" AND (c.name like '%").append(iFeeDto.getShipName()).append("%' or d.refName like '%")
				   .append(iFeeDto.getShipName()).append("%' )");
			   }
			 if(iFeeDto.getProductId()!=null){
				   sql.append(" AND a.productId=").append(iFeeDto.getProductId());
			   }
			   if(iFeeDto.getClientId()!=null){
				   sql.append(" AND i.clientId=").append(iFeeDto.getClientId());
			   }
			   if(!Common.isNull(iFeeDto.getInitialCode())){
				   sql.append(" AND h.code like '%").append(iFeeDto.getInitialCode()).append("%'");
			   }
			   if(iFeeDto.getStatus()!=null&&iFeeDto.getStatus()!=-1){
				   sql.append(" AND h.status=").append(iFeeDto.getStatus());
			   }
			   if(iFeeDto.getStartTime()!=null&&iFeeDto.getStartTime()!=-1){
		        	 sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) >=").append(iFeeDto.getStartTime());
		         }
		         if(iFeeDto.getEndTime()!=null&&iFeeDto.getEndTime()!=-1){
		        	 sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) <=").append(iFeeDto.getEndTime());
		         }
			 return (int) getCount(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao出库保安费列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao出库保安费列表数量失败", e);
		}
	}

	@Override
	public Map<String, Object> getOutFeeMsg(InitialFeeDto iFeeDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
				sql.append(" SELECT a.id arrivalId,a.shipId,c.`name` shipName,a.shipRefId,d.refName shipRefName, ")
				.append(" DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') arrivalTime,a.productId, ")
				.append(" e.`name` productName,GROUP_CONCAT(b.actualNum) actualNums, ")
				.append(" GROUP_CONCAT(DISTINCT b.ladingClientId) ladingClientIds, ")
				.append(" GROUP_CONCAT(f.`name`) ladingClientName,GROUP_CONCAT(b.ladingEvidence) ladingEvidence, ")
				.append(" SUM(ROUND(b.actualNum, 3)) actualNum,h.id,h.code,from_unixtime(h.accountTime) accountTimeStr, ")
				.append(" h.`status`,h.totalFee,GROUP_CONCAT(DISTINCT f.`name`) ladingClientNames, ")
				.append(" h.description,h.createUserId,FROM_UNIXTIME(h.createTime) createTime, ")
				.append(" g.`name` createUserName ")
				.append(" FROM t_pcs_initial_fee h ")
				.append(" LEFT JOIN t_pcs_arrival a ON h.arrivalId=a.id ")
				.append(" LEFT JOIN t_pcs_goodslog b ON a.id = b.batchId AND b.deliverType = 2 ")
				.append(" AND b.type = 5 AND b.actualType = 1 ")
				.append(" LEFT JOIN t_pcs_ship c ON c.id = a.shipId ")
				.append(" LEFT JOIN t_pcs_ship_ref d ON d.id = a.shipRefId ")
				.append(" LEFT JOIN t_pcs_product e ON e.id = a.productId ")
				.append(" LEFT JOIN t_pcs_client f ON f.id = b.ladingClientId ")
				.append(" LEFT JOIN t_auth_user g ON g.id= h.createUserId ")
				.append(" WHERE h.type = 4 AND h.id=").append(iFeeDto.getId());
			return executeQueryOne(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取出库首期费结算单失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取出库首期费结算单失败", e);
	}
	}

	/**
	 * @Title getOutBoundClientAndNum
	 * @Descrption:TODO
	 * @param:@param valueOf
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年7月26日上午9:52:40
	 * @throws
	 */
	@Override
	public Map<String, Object> getOutBoundClientAndNum(Integer arrivalId) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			 sql.append(" SELECT  GROUP_CONCAT(b.goodsTotal) actualNums,GROUP_CONCAT(DISTINCT b.ladingClientId) ladingClientIds, ")
			   .append(" GROUP_CONCAT( f.`name`) ladingClientName,GROUP_CONCAT( b.ladingCode) ladingEvidence,GROUP_CONCAT( b.tradeType) tradeType,  ")
			   .append(" SUM(ROUND(b.goodsTotal,3)) actualNum, GROUP_CONCAT(DISTINCT f.`name`) ladingClientNames,GROUP_CONCAT( j.`name`) clientNames")
			   .append(" from t_pcs_arrival a ")
			   .append(" LEFT JOIN t_pcs_arrival_plan b on b.arrivalId=a.id ")
			   .append(" LEFT JOIN t_pcs_client j ON j.id=b.clientId ")
			   .append(" LEFT JOIN t_pcs_client f ON f.id=b.ladingClientId ")
			   .append("  WHERE  a.type=5  ").append(" and a.id=").append(arrivalId);
			 return executeQueryOne(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取出库首期费结算单失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取出库首期费结算单失败", e);
	}
	}

	/**
	 * @Title getInitialFeeMsg
	 * @Descrption:TODO
	 * @param:@param iFeeDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年8月3日下午2:26:58
	 * @throws
	 */
	@Override
	public Map<String, Object> getInitialFeeMsg(InitialFeeDto iFeeDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			switch (iFeeDto.getType()) {
				case 1:
	             sql.append(" SELECT a.id, a.code,FROM_UNIXTIME(a.accountTime,'%Y-%m-%d') accountTime, a.cargoId, b.code cargoCode, a.type,")
					 .append(" a.totalFee, (CASE WHEN b.taxType=1 THEN '内轮' ELSE '外轮' END) shipType,b.taxType, b.productId, i.name productName, b.clientId,")
					 .append(" a.contractId,a.contractNum,c.code contractCode,c.period periodTime,c.type contractType,")
					 .append(" c.storagePrice,c.protectStoragePrice,c.portSecurityPrice,c.passPrice,c.fileUrl,h.code clientCode, h.name clientName,")
					 .append("  e.`name` shipName, f.refName shipRefName, DATE_FORMAT(d.arrivalStartTime,'%Y-%m-%d') arrivalTime, b.goodsInspect,a.createUserId,")
					 .append(" j.name createUserName, FROM_UNIXTIME(a.createTime,'%Y-%m-%d') createTime,a.status,a.description")
					 .append(" FROM t_pcs_initial_fee a")
					 .append(" LEFT JOIN t_pcs_cargo b ON a.cargoId = b.id")
					 .append(" LEFT JOIN t_pcs_contract c ON b.contractId = c.id")
					 .append(" LEFT JOIN t_pcs_arrival d ON b.arrivalId = d.id")
					 .append(" LEFT JOIN t_pcs_ship e ON d.shipId = e.id")
					 .append(" LEFT JOIN t_pcs_ship_ref f ON d.shipRefId = f.id")
					 .append(" LEFT JOIN t_pcs_client h ON h.id = b.clientId")
					 .append(" LEFT JOIN t_pcs_product i ON i.id = b.productId")
					 .append(" LEFT JOIN t_auth_user j on a.createUserId=j.id ")
					 .append(" where a.id=").append(iFeeDto.getId());
					break;
				case 2:
				sql.append(" SELECT a.id, a.code,FROM_UNIXTIME(a.accountTime,'%Y-%m-%d') accountTime,a.type,a.cargoCodes,FROM_UNIXTIME(a.startTime,'%Y-%m-%d') startTime,")
					.append("a.totalFee,FROM_UNIXTIME(a.endTime,'%Y-%m-%d') endTime,c.productId, i.name productName,c.clientId clientId,h.name clientName,")
					.append(" a.contractId,a.contractNum,c.code contractCode,c.period periodTime,c.fileUrl,c.type contractType,c.storagePrice,")
					.append(" h.code clientCode,a.createUserId, j.name createUserName, FROM_UNIXTIME(a.createTime) createTime,a.status,a.description")
					.append(" FROM t_pcs_initial_fee a")
					.append(" LEFT JOIN t_pcs_contract c ON a.contractId = c.id")
					.append(" LEFT JOIN t_pcs_client h ON h.id = c.clientId")
					.append(" LEFT JOIN t_pcs_product i ON i.id = c.productId")
					.append(" LEFT JOIN t_auth_user j on a.createUserId=j.id ")
					.append(" where a.id=").append(iFeeDto.getId());
					break;
				case 3:
					sql.append(" SELECT a.id, a.code, a.type,FROM_UNIXTIME(a.accountTime,'%Y-%m-%d') accountTime,a.contractNum, ")
					.append(" FROM_UNIXTIME(a.startTime,'%Y-%m-%d') startTime,FROM_UNIXTIME(a.endTime,'%Y-%m-%d') endTime,")
					.append(" a.totalFee, a.productId, c.name productName, a.clientId clientId,b.`name` clientName,")
					.append("  a.createUserId, d.name createUserName, FROM_UNIXTIME(a.createTime,'%Y-%m-%d') createTime, ")
					.append("  a.status,a.description ")
					.append("  FROM t_pcs_initial_fee a ")
					.append(" LEFT JOIN t_pcs_client b ON b.id = a.clientId ")
					.append(" LEFT JOIN t_pcs_product c ON c.id = a.productId ")
					.append(" LEFT JOIN t_auth_user d on a.createUserId=d.id  ")
					.append("  where  a.id=").append(iFeeDto.getId());
					break;
				case 4:
					sql.append(" SELECT a.id arrivalId,a.shipId,a.type arrivalType,c.`name` shipName,a.shipRefId,d.refName shipRefName, ")
					.append(" DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') arrivalTime,a.productId, ")
					.append(" e.`name` productName,GROUP_CONCAT(b.actualNum) actualNums, ")
					.append(" GROUP_CONCAT(DISTINCT b.ladingClientId) ladingClientIds, ")
					.append(" GROUP_CONCAT(f.`name`) ladingClientName,GROUP_CONCAT(f.`name`) clientName,GROUP_CONCAT(b.ladingEvidence) ladingEvidence, ")
					.append(" SUM(ROUND(b.actualNum, 3)) actualNum,SUM(ROUND(b.actualNum, 3)) goodsInspect,h.id,h.code,from_unixtime(h.accountTime,'%Y-%m-%d') accountTime, ")
					.append(" h.`status`,h.totalFee,GROUP_CONCAT(DISTINCT f.`name`) ladingClientNames, ")
					.append(" h.description,h.createUserId,FROM_UNIXTIME(h.createTime,'%Y-%m-%d') createTime, ")
					.append(" g.`name` createUserName ")
					.append(" FROM t_pcs_initial_fee h ")
					.append(" LEFT JOIN t_pcs_arrival a ON h.arrivalId=a.id ")
					.append(" LEFT JOIN t_pcs_goodslog b ON a.id = b.batchId AND b.deliverType = 2 ")
					.append(" AND b.type = 5 AND b.actualType = 1 ")
					.append(" LEFT JOIN t_pcs_ship c ON c.id = a.shipId ")
					.append(" LEFT JOIN t_pcs_ship_ref d ON d.id = a.shipRefId ")
					.append(" LEFT JOIN t_pcs_product e ON e.id = a.productId ")
					.append(" LEFT JOIN t_pcs_client f ON f.id = b.ladingClientId ")
					.append(" LEFT JOIN t_auth_user g ON g.id= h.createUserId ")
					.append(" WHERE h.id=").append(iFeeDto.getId());
					break;
				case 5:
					sql.append(" SELECT a.id, a.code,FROM_UNIXTIME(a.accountTime,'%Y-%m-%d') accountTime, a.cargoId, b.code cargoCode, a.type,")
					 .append(" a.totalFee, (CASE WHEN b.taxType=1 THEN '内轮' ELSE '外轮' END) shipType,b.taxType, b.productId, i.name productName, b.clientId,")
					 .append(" a.contractId,a.contractNum,c.code contractCode,c.period periodTime,c.type contractType,")
					 .append(" c.storagePrice,c.protectStoragePrice,c.portSecurityPrice,c.passPrice,c.fileUrl,h.code clientCode, h.name clientName,")
					 .append("  e.`name` shipName, f.refName shipRefName, DATE_FORMAT(d.arrivalStartTime,'%Y-%m-%d') arrivalTime, b.goodsInspect,a.createUserId,")
					 .append(" j.name createUserName, FROM_UNIXTIME(a.createTime,'%Y-%m-%d') createTime,a.status,a.description")
					 .append(" FROM t_pcs_initial_fee a")
					 .append(" LEFT JOIN t_pcs_cargo b ON a.cargoId = b.id")
					 .append(" LEFT JOIN t_pcs_contract c ON b.contractId = c.id")
					 .append(" LEFT JOIN t_pcs_arrival d ON b.arrivalId = d.id")
					 .append(" LEFT JOIN t_pcs_ship e ON d.shipId = e.id")
					 .append(" LEFT JOIN t_pcs_ship_ref f ON d.shipRefId = f.id")
					 .append(" LEFT JOIN t_pcs_client h ON h.id = b.clientId")
					 .append(" LEFT JOIN t_pcs_product i ON i.id = b.productId")
					 .append(" LEFT JOIN t_auth_user j on a.createUserId=j.id ")
					 .append(" where a.id=").append(iFeeDto.getId());
					break;
	
				default:
					break;
			}
			return executeQueryOne(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费信息失败", e);
		}
	}
}
