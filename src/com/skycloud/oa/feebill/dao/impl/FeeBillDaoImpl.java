package com.skycloud.oa.feebill.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.FeeBillDao;
import com.skycloud.oa.feebill.dto.FeeBillDto;
import com.skycloud.oa.feebill.model.FeeBill;
import com.skycloud.oa.utils.Common;
/**
 *账单
 * @author jiahy
 * @version 2015年6月14日 下午1:59:52
 */
@Component
public class FeeBillDaoImpl extends BaseDaoImpl implements FeeBillDao {
	private static Logger LOG = Logger.getLogger(FeeBillDaoImpl.class);

	@Override
	public List<Map<String, Object>> getFeeBillList(FeeBillDto feeBillDto,
			int start, int limit) throws OAException {
         try {
        	 StringBuilder sql=new StringBuilder();
			 sql.append("select a.*,b.name feeHeadName,from_unixtime(a.accountTime) accountTimeStr,c.name createUserName,")
				.append( " d.name billingUserName,from_unixtime(a.billingTime) billingTimeStr,  ")
				.append(" (select e.name from t_pcs_account_bill_log d INNER JOIN t_auth_user e on d.accountUserId=e.id ")
				.append(" WHERE d.feebillId=a.id ORDER BY d.accountTime DESC LIMIT 0,1  )  fundsUserName,")
				.append(" (select FROM_UNIXTIME(f.accountTime) from t_pcs_account_bill_log f ")
				.append(" WHERE f.feebillId=a.id ORDER BY f.accountTime DESC LIMIT 0,1  )  fundsTimeStr,")
				.append(" (select h.name  from t_pcs_approvecontent g INNER JOIN t_auth_user h on g.userId=h.id ")
				.append(" WHERE g.workId=a.id and g.type=5 ORDER BY g.id asc LIMIT 0,1) reviewUserName,")
				.append(" (select FROM_UNIXTIME(g.reviewTime)  from t_pcs_approvecontent g ")
				.append(" WHERE g.workId=a.id and g.type=5 ORDER BY g.id asc LIMIT 0,1) reviewTimeStr,")
				.append(" (select round(sum(d.accountFee),2)  from t_pcs_account_bill_log d where a.id=d.feebillId ) accountTotalFee")
				.append(" from t_pcs_fee_bill a  ")
				.append(" left join t_pcs_client b on b.id=a.feeHead ")
				.append(" left join t_auth_user c on a.createUserId=c.id")
				.append(" left join t_auth_user d on a.billingUserId=d.id");
			 
			 if(!Common.isNull(feeBillDto.getProductId())||!Common.isNull(feeBillDto.getCargoId())||!Common.isNull(feeBillDto.getCargoCode())||!Common.isNull(feeBillDto.getClientId())){
				 sql.append(",(SELECT DISTINCT a.id  from t_pcs_fee_bill a LEFT JOIN t_pcs_fee_charge  b ON  a.id=b.feebillId ")
				 .append(" LEFT JOIN t_pcs_charge_cargo_lading c ON b.id=c.feeChargeId ")
				 .append(" LEFT JOIN t_pcs_cargo d ON d.id=c.cargoId where 1=1 ");
				 if(!Common.isNull(feeBillDto.getProductId())){
					 sql.append(" and b.productId=").append(feeBillDto.getProductId());
				 }
				 if(!Common.isNull(feeBillDto.getCargoId())){
					 sql.append(" and c.cargoId=").append(feeBillDto.getCargoId());
				 }else if(!Common.isNull(feeBillDto.getCargoCode())){
					 sql.append(" and (c.cargoCode like '%").append(feeBillDto.getCargoCode()).append("%' or d.code like '%")
					 .append(feeBillDto.getCargoCode()).append("%')");
				 }
				 if(!Common.isNull(feeBillDto.getClientId())){
					 sql.append(" and b.clientId=").append(feeBillDto.getClientId());
				 }
			     sql.append(" order by a.id desc ) tb");	 
			 }
			 
				sql.append(" where 1=1 ");
			    if(!Common.isNull(feeBillDto.getFeeHead())){
			    	sql.append(" and a.feeHead="+feeBillDto.getFeeHead());
			    }
			    if(!Common.isNull(feeBillDto.getCode())){
			    	sql.append(" and a.code like'%"+feeBillDto.getCode()+"%'");
			    }
			    if(!Common.isNull(feeBillDto.getFeeType())){
			    	sql.append(" and a.feeType="+feeBillDto.getFeeType());	
			    }
			    if(!Common.isNull(feeBillDto.getFeeTypes())){
			    	sql.append(" and  a.feeType in("+feeBillDto.getFeeTypes()+")");
			    }
			    if(feeBillDto.getId()!=null){
			    	sql.append(" and a.id="+feeBillDto.getId());
			    }
			    if(!Common.isNull(feeBillDto.getProductId())||!Common.isNull(feeBillDto.getCargoId())||!Common.isNull(feeBillDto.getCargoCode())||!Common.isNull(feeBillDto.getClientId())){
			    	sql.append(" and a.id=tb.id ");
			    }
			    if(!Common.isNull(feeBillDto.getTotalFee()))
			    	sql.append(" and a.totalFee=").append(feeBillDto.getTotalFee());
			    if(feeBillDto.getBillingCode()!=null&&feeBillDto.getBillingCode()!=""){
			    	sql.append(" and a.billingCode like '%"+feeBillDto.getBillingCode()+"%'");
			    }
			    if(feeBillDto.getStatusStr()!=null){
			    	sql.append(" and a.status in ("+feeBillDto.getStatusStr()+")");
			    }
			    if(feeBillDto.getBillStatus()!=null&&feeBillDto.getBillStatus()!=-1){
			    	if(feeBillDto.getBillStatus()<3){//未提交，审核中，已提交
			    		sql.append(" and (ISNULL(a.fundsStatus) or a.fundsStatus=0) and (ISNULL(a.billingStatus) or a.billingStatus=0)  and a.status="+feeBillDto.getBillStatus());			    		
			    	}else if(feeBillDto.getBillStatus()==3){//未到账
			    		sql.append(" and a.status=2 and (a.fundsStatus!=2 OR ISNULL(a.fundsStatus))");
			    	}else if(feeBillDto.getBillStatus()==4){//未开票
			    		sql.append(" and a.status=2 and (a.billingStatus=0 OR ISNULL(a.billingStatus))");
			    	}else if(feeBillDto.getBillStatus()==5){//已开票
			    		sql.append(" and a.status=2 and a.billingStatus=1 ");
			    	}else if(feeBillDto.getBillStatus()==6){//已全部到账
			    		sql.append(" and a.status=2 and a.fundsStatus=2 ");
			    	}else if(feeBillDto.getBillStatus()==7){//已完成
			    		sql.append(" and a.status=2 and a.billingStatus=1 and a.fundsStatus=2 ");
			    	}else if(feeBillDto.getBillStatus()==8){//未完成
			    		sql.append(" and( a.status!=2 or a.billingStatus!=1 or a.fundsStatus!=2 OR ISNULL(a.fundsStatus) or ISNULL(a.billingStatus)) ");
			    	}
			    }
			    if(feeBillDto.getStartTime()!=null&&feeBillDto.getStartTime()!=-1){
			    	sql.append(" and a.accountTime>="+feeBillDto.getStartTime());
                  }
                  if(feeBillDto.getEndTime()!=null&&feeBillDto.getEndTime()!=-1){
                	  sql.append(" and a.accountTime<="+feeBillDto.getEndTime());
                  }
                  if(feeBillDto.getBillingStartTime()!=null&&feeBillDto.getBillingStartTime()!=-1){
                	  sql.append(" and a.billingTime>="+feeBillDto.getBillingStartTime());
                    }
                  if(feeBillDto.getBillingEndTime()!=null&&feeBillDto.getBillingEndTime()!=-1){
                    	sql.append(" and a.billingTime<="+feeBillDto.getBillingEndTime());
                    }
                  if(feeBillDto.getIndexTh()!=null&&feeBillDto.getIndexTh()==0)
                	  sql.append("  order by a.code desc ");
                  else if(feeBillDto.getIndexTh()!=null&&feeBillDto.getIndexTh()==6)
                	  sql.append("  order by a.billingTime desc ");
                  else if(feeBillDto.getIndexTh()!=null&&feeBillDto.getIndexTh()==13)
                	  sql.append("  order by fundsTimeStr desc ");
                  else if(feeBillDto.getIndexTh()!=null&&feeBillDto.getIndexTh()==15)
                	  sql.append("  order by a.accountTime desc ");
                  else if(feeBillDto.getIndexTh()!=null&&feeBillDto.getIndexTh()==16)
                	  sql.append("  order by a.status,a.fundsStatus,a.billingStatus asc ");
                  else
                    sql.append("  order by a.code desc ");
			    if(limit!=0)
			    	sql.append(" limit "+start+","+limit);
			return executeQuery(sql.toString()) ;
		}catch (RuntimeException e) {
			LOG.error("dao获取账单列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单列表失败", e);
		}
	}

	@Override
	public int getFeeBillCount(FeeBillDto feeBillDto) throws OAException {
           try {
        	   StringBuilder sql=new StringBuilder();
			 sql.append(" select count(*) from t_pcs_fee_bill a");
			 if(!Common.isNull(feeBillDto.getProductId())||!Common.isNull(feeBillDto.getCargoId())||!Common.isNull(feeBillDto.getCargoCode())||!Common.isNull(feeBillDto.getClientId())){
				 sql.append(",(SELECT DISTINCT a.id  from t_pcs_fee_bill a LEFT JOIN t_pcs_fee_charge  b ON  a.id=b.feebillId ")
				 .append(" LEFT JOIN t_pcs_charge_cargo_lading c ON b.id=c.feeChargeId ")
				 .append(" LEFT JOIN t_pcs_cargo d ON d.id=c.cargoId where 1=1 ");
				 if(!Common.isNull(feeBillDto.getProductId())){
					 sql.append(" and b.productId=").append(feeBillDto.getProductId());
				 }
				 if(!Common.isNull(feeBillDto.getCargoId())){
					 sql.append(" and c.cargoId=").append(feeBillDto.getCargoId());
				 }else if(!Common.isNull(feeBillDto.getCargoCode())){
					 sql.append(" and (c.cargoCode like '%").append(feeBillDto.getCargoCode()).append("%' or d.code like '%")
					 .append(feeBillDto.getCargoCode()).append("%')");
				 }
				 if(!Common.isNull(feeBillDto.getClientId())){
					 sql.append(" and b.clientId=").append(feeBillDto.getClientId());
				 }
			     sql.append(" order by a.id desc ) tb");	 
			 }
			 sql.append(" where 1=1");
			if(!Common.isNull(feeBillDto.getFeeHead())){
				 sql.append(" and a.feeHead="+feeBillDto.getFeeHead());
		    }
		    if(!Common.isNull(feeBillDto.getCode())){
		    	 sql.append(" and a.code like'%"+feeBillDto.getCode()+"%'");
		    }
		    if(!Common.isNull(feeBillDto.getFeeType())){
		    	 sql.append(" and a.feeType="+feeBillDto.getFeeType());	
		    }
		    if(!Common.isNull(feeBillDto.getFeeTypes())){
		    	 sql.append(" and  a.feeType in("+feeBillDto.getFeeTypes()+")");
		    }
		    if(feeBillDto.getId()!=null){
		    	 sql.append(" and a.id="+feeBillDto.getId());
		    }
		    if(!Common.isNull(feeBillDto.getProductId())||!Common.isNull(feeBillDto.getCargoId())||!Common.isNull(feeBillDto.getCargoCode())||!Common.isNull(feeBillDto.getClientId())){
		    	sql.append(" and a.id=tb.id ");
		    }
		    if(!Common.isNull(feeBillDto.getTotalFee()))
		    	sql.append(" and a.totalFee=").append(feeBillDto.getTotalFee());
		    if(feeBillDto.getBillingCode()!=null&&feeBillDto.getBillingCode()!=""){
		    	 sql.append(" and a.billingCode like '%"+feeBillDto.getBillingCode()+"%'");
		    }
		    if(feeBillDto.getStatusStr()!=null){
		    	 sql.append(" and a.status in ("+feeBillDto.getStatusStr()+")");
		    }
		    if(feeBillDto.getBillStatus()!=null&&feeBillDto.getBillStatus()!=-1){
		    	if(feeBillDto.getBillStatus()<3){//未提交，审核中，已提交
		    		sql.append(" and (ISNULL(a.fundsStatus) or a.fundsStatus=0) and (ISNULL(a.billingStatus) or a.billingStatus=0)  and a.status="+feeBillDto.getBillStatus());			    		
		    	}else if(feeBillDto.getBillStatus()==3){//未到账
		    		sql.append(" and a.status=2 and (a.fundsStatus!=2 OR ISNULL(a.fundsStatus))");
		    	}else if(feeBillDto.getBillStatus()==4){//未开票
		    		sql.append(" and a.status=2 and (a.billingStatus=0 OR ISNULL(a.billingStatus))");
		    	}else if(feeBillDto.getBillStatus()==5){//已开票
		    		sql.append(" and a.status=2 and a.billingStatus=1 ");
		    	}else if(feeBillDto.getBillStatus()==6){//已全部到账
		    		sql.append(" and a.status=2 and a.fundsStatus=2 ");
		    	}else if(feeBillDto.getBillStatus()==7){//已完成
		    		sql.append(" and a.status=2 and a.billingStatus=1 and a.fundsStatus=2 ");
		    	}else if(feeBillDto.getBillStatus()==8){//未完成
		    		sql.append(" and( a.status!=2 or a.billingStatus!=1 or a.fundsStatus!=2 OR ISNULL(a.fundsStatus) or ISNULL(a.billingStatus)) ");
		    	}
		    }
		    if(feeBillDto.getStartTime()!=null&&feeBillDto.getStartTime()!=-1){
		    	 sql.append(" and a.accountTime>="+feeBillDto.getStartTime());
              }
              if(feeBillDto.getEndTime()!=null&&feeBillDto.getEndTime()!=-1){
            	  sql.append(" and a.accountTime<="+feeBillDto.getEndTime());
              }
              if(feeBillDto.getBillingStartTime()!=null&&feeBillDto.getBillingStartTime()!=-1){
            	  sql.append(" and a.billingTime>="+feeBillDto.getBillingStartTime());
              }
              if(feeBillDto.getBillingEndTime()!=null&&feeBillDto.getBillingEndTime()!=-1){
            	  sql.append(" and a.billingTime<="+feeBillDto.getBillingEndTime());
              }
			return (int) getCount(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取账单列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单列表数量失败", e);
		}
	}

	@Override
	public int addFeeBill(FeeBill feeBill) throws OAException {
           try {
			return (Integer) save(feeBill);
		}catch (RuntimeException e) {
			LOG.error("dao添加账单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加账单失败", e);
		}
	}

	@Override
	public void updateFeeBill(FeeBill feeBill) throws OAException {
             try {
			String sql="update t_pcs_fee_bill set id=id";
			if(feeBill.getCode()!=null){
				sql+=",code='"+feeBill.getCode()+"'";
			}
			if(feeBill.getClientId()!=null){
				sql+=",clientId="+feeBill.getClientId();
			}
			if(feeBill.getFeeHead()!=null){
				sql+=",feeHead="+feeBill.getFeeHead();
			}
			if(feeBill.getFeeType()!=null){
				sql+=",feeType="+feeBill.getFeeType();
			}
			if(feeBill.getCreateUserId()!=null){
				sql+=",createUserId="+feeBill.getCreateUserId();
			}
			if(feeBill.getCreateTime()!=null){
				sql+=",createTime="+feeBill.getCreateTime();
			}
			if(feeBill.getTotalFee()!=null){
				sql+=",totalFee='"+feeBill.getTotalFee()+"'";
			}
			if(feeBill.getHasTotalFee()!=null){
				sql+=",hasTotalFee='"+feeBill.getHasTotalFee()+"'";
			}
			if(feeBill.getReviewUserId()!=null&&feeBill.getReviewUserId()!=-1){
				sql+=",reviewUserId="+feeBill.getReviewUserId();
			}else if(feeBill.getReviewUserId()!=null&&feeBill.getReviewUserId()==-1){
				sql+=",reviewUserId=null";
			}
			if(feeBill.getReviewTime()!=null&&feeBill.getReviewTime()!=-1){
				sql+=",reviewTime="+feeBill.getReviewTime();
			}else if(feeBill.getReviewTime()!=null&&feeBill.getReviewTime()==-1){
				sql+=",reviewTime=null";
			}
			if(feeBill.getAccountTime()!=null){
				sql+=",accountTime="+feeBill.getAccountTime();
			}
			if(feeBill.getDescription()!=null){
				sql+=",description='"+feeBill.getDescription()+"'";
			}
			if(feeBill.getStatus()!=null){
				sql+=",status="+feeBill.getStatus();
			}
			if(feeBill.getFundsTotalFee()!=null){
				sql+=",fundsTotalFee='"+feeBill.getFundsTotalFee()+"'";
			}
			if(feeBill.getFundsStatus()!=null&&feeBill.getFundsStatus()!=-1){
				sql+=",fundsStatus="+feeBill.getFundsStatus();
			}else if(feeBill.getFundsStatus()!=null&&feeBill.getFundsStatus()==-1){
				sql+=",fundsStatus=null";
			}
			if(feeBill.getBillingCode()!=null){
				sql+=",billingCode='"+feeBill.getBillingCode()+"'";
			}
			if(feeBill.getBillingStatus()!=null&&feeBill.getBillingStatus()!=-1){
				sql+=",billingStatus="+feeBill.getBillingStatus();
			}else if(feeBill.getBillingStatus()!=null&&feeBill.getBillingStatus()==-1){
				sql+=",billingStatus=null";
			}
			if(feeBill.getBillingUserId()!=null){
				sql+=",billingUserId="+feeBill.getBillingUserId();
			}
			if(feeBill.getBillingTime()!=null){
				sql+=",billingTime="+feeBill.getBillingTime();
			}
			if(feeBill.getUnTaxFee()!=null){
				sql+=",unTaxFee='"+feeBill.getUnTaxFee()+"'";
			}
			if(feeBill.getTaxRate()!=null){
				sql+=",taxRate='"+feeBill.getTaxRate()+"'";
			}
			if(feeBill.getTaxFee()!=null){
				sql+=",taxFee='"+feeBill.getTaxFee()+"'";
			}
			
			
			sql+=" where id="+feeBill.getId();
			executeUpdate(sql);
		}catch (RuntimeException e) {
			LOG.error("dao更新账单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新账单失败", e);
		}
	}

	@Override
	public void deleteFeeBill(FeeBillDto feeBillDto) throws OAException {
            try {
			String sql="delete from t_pcs_fee_bill where id="+feeBillDto.getId();
			execute(sql);
		}catch (RuntimeException e) {
			LOG.error("dao删除账单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除账单失败", e);
		}
	}

	@Override
	public List<Map<String,Object>> getCodeNum(FeeBillDto feeBillDto) throws OAException {
		 try {
			 String sql="select max(code) code from t_pcs_fee_bill where 1=1 and (!ISNULL(code) and code!='')";
				return executeQuery(sql);
			}catch (RuntimeException e) {
				LOG.error("dao获取账单号失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单号失败", e);
			}
	}

	@Override
	public List<Map<String, Object>> getFeeBillExelList(FeeBillDto feeBillDto) throws OAException {
		 try {
			 String sql=" select a.id feebillId,a.feeType, a.code code,FROM_UNIXTIME(a.accountTime) accountTime,b.feeType feeType,b.initialId initialId,"
						+" a.feeHead feeHead,c.name feeHeadName,b.unitFee unitFee,b.totalFee itemTotalFee,a.totalFee totalFee, a.unTaxFee,a.taxRate,a.taxFee,"
						+" (SELECT FROM_UNIXTIME(i.accountTime) from t_pcs_account_bill_log i WHERE i.feebillId=a.id ORDER BY i.id desc  LIMIT 0,1 ) fundsTime,"
						+" e.taxType type,a.billingCode billingCode,b.productId productId,d.name productName,e.code cargoName,e.storageType storageType,f.type contractType,b.feeCount feeCount,"
                        +" (SELECT SUM(ROUND(j.accountFee,2)) FROM t_pcs_account_bill_log j WHERE j.feebillId=a.id ) leftTotalFee,"
                        +" FROM_UNIXTIME(a.billingTime) billingTime,  b.exceedId exceedId,b.discountFee,"
                        + "(CASE WHEN b.feeType=6 THEN h.description WHEN b.initialId!=0 OR !ISNULL(b.initialId) THEN g.description ELSE i.description END) description,"
                        + "(SELECT count(*) FROM t_pcs_fee_charge k WHERE k.feebillId=b.feebillId) feeNum  "
						+" from  t_pcs_fee_charge b"
						+" LEFT JOIN t_pcs_fee_bill a on b.feebillId=a.id"
						+" LEFT JOIN t_pcs_client c on a.feeHead=c.id"
						+" LEFT JOIN t_pcs_product d on b.productId=d.id"
						+" LEFT JOIN t_pcs_cargo e on e.id=b.cargoId"
						+" LEFT JOIN t_pcs_contract f on f.id=e.contractId "
						+" LEFT JOIN t_pcs_initial_fee g on b.initialId=g.id"
						+" LEFT JOIN t_pcs_other_fee h on b.initialId=h.id"
						+" LEFT JOIN t_pcs_exceed_fee i on b.exceedId=i.id" ;
						 if(!Common.isNull(feeBillDto.getProductId())||!Common.isNull(feeBillDto.getCargoId())||!Common.isNull(feeBillDto.getCargoCode())){
							 sql+=",(SELECT DISTINCT b.id  from  t_pcs_fee_charge  b LEFT JOIN t_pcs_charge_cargo_lading c ON b.id=c.feeChargeId"
							 		+ " LEFT JOIN t_pcs_cargo d on d.id=c.cargoId where 1=1 ";
							 if(!Common.isNull(feeBillDto.getProductId())){
								 sql+=" and b.productId="+feeBillDto.getProductId();
							 }
							 if(!Common.isNull(feeBillDto.getCargoId())){
								 sql+=" and c.cargoId="+feeBillDto.getCargoId();
							 }else if(!Common.isNull(feeBillDto.getCargoCode())){
								 sql+=" and (c.cargoCode like '%"+feeBillDto.getCargoCode()+"%' or d.code like '%"+feeBillDto.getCargoCode()+"%')";
							 }
							 sql+=" order by b.id desc ) tb" ;	 
						 }
						sql+=" where a.code is not null ";
			        if(!Common.isNull(feeBillDto.getProductId())||!Common.isNull(feeBillDto.getCargoId())||!Common.isNull(feeBillDto.getCargoCode())){
			        	sql+=" and b.id=tb.id";
			        }
			        if(!Common.isNull(feeBillDto.getCode())){
			        	sql+=" and a.code like '%"+feeBillDto.getCode()+"%'";
			        }
			        if(feeBillDto.getBillStatus()!=null&&feeBillDto.getBillStatus()!=-1){
				    	if(feeBillDto.getBillStatus()<3){//未提交，审核中，已提交
				    		sql+=" and (ISNULL(a.fundsStatus) or a.fundsStatus=0) and (ISNULL(a.billingStatus) or a.billingStatus=0)  and a.status="+feeBillDto.getBillStatus();			    		
				    	}else if(feeBillDto.getBillStatus()==3){//未到账
				    		sql+=" and a.status=2 and (a.fundsStatus!=2 OR ISNULL(a.fundsStatus))";
				    	}else if(feeBillDto.getBillStatus()==4){//未开票
				    		sql+=" and a.status=2 and (a.billingStatus=0 OR ISNULL(a.billingStatus))";
				    	}else if(feeBillDto.getBillStatus()==5){//已开票
				    		sql+=" and a.status=2 and a.billingStatus=1 ";
				    	}else if(feeBillDto.getBillStatus()==6){//已全部到账
				    		sql+=" and a.status=2 and a.fundsStatus=2 ";
				    	}else if(feeBillDto.getBillStatus()==7){//已完成
				    		sql+=" and a.status=2 and a.billingStatus=1 and a.fundsStatus=2 ";
				    	}else if(feeBillDto.getBillStatus()==8){//未完成
				    		sql+=" and( a.status!=2 or a.billingStatus!=1 or a.fundsStatus!=2 OR ISNULL(a.fundsStatus) or ISNULL(a.billingStatus)) ";
				    	}
				    }
			        if(!Common.isNull(feeBillDto.getClientId())){
			        	sql+=" and b.clientId="+feeBillDto.getClientId();
			        }
			        if(!Common.isNull(feeBillDto.getFeeHead())){
			        	sql+=" and a.feeHead="+feeBillDto.getFeeHead();
			        }
			        if(!Common.isNull(feeBillDto.getFeeTypes())){
			        	sql+=" and b.feeType in("+feeBillDto.getFeeTypes()+")";
			        }
			        if(!Common.isNull(feeBillDto.getTotalFee()))
				    	sql+=" and a.totalFee="+feeBillDto.getTotalFee();
			        if(!Common.empty(feeBillDto.getStartTime())&&feeBillDto.getStartTime()!=-1){
			        	sql+=" and a.accountTime>="+feeBillDto.getStartTime();
			        }
			        if(!Common.empty(feeBillDto.getEndTime())&&feeBillDto.getEndTime()!=-1){
			        	sql+=" and a.accountTime<="+feeBillDto.getEndTime();
			        }
			        if(feeBillDto.getBillingStartTime()!=null&&feeBillDto.getBillingStartTime()!=-1){
                    	sql+=" and a.billingTime>="+feeBillDto.getBillingStartTime();
                    }
                    if(feeBillDto.getBillingEndTime()!=null&&feeBillDto.getBillingEndTime()!=-1){
                    	sql+=" and a.billingTime<="+feeBillDto.getBillingEndTime();
                    }
      			sql+= " ORDER BY a.code ASC";
				return executeQuery(sql);
			}catch (RuntimeException e) {
				LOG.error("dao获取账单号失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单号失败", e);
			}
	}

	@Override
	public List<Map<String, Object>> getCargoFeeBillList(FeeBillDto feeBillDto)
			throws OAException {
		try{
		 String sql="SELECT DISTINCT a.id, (case when ISNULL(m.cargoId) then m.cargoCode else a.code end) cargoCode,a.storageType storageType,d.`name` shipName,e.refName shipRefname,FROM_UNIXTIME(c.anchorTime) arrivalTime," 
				 +" f.name productName ,a.goodsTank goodsTank,b.customLading customLading,b.customLadingCount customLadingCount,j.name clientGroupName,"
				+" a.goodsInspect,g.name clientName,a.originalArea,i.code  feeBillCode , FROM_UNIXTIME(i.accountTime) accountTime"
				+" from t_pcs_charge_cargo_lading m "
				+"LEFT JOIN t_pcs_cargo a on a.id=m.cargoId "
				+" LEFT JOIN t_pcs_arrival b on a.arrivalId=b.id"
				+" LEFT JOIN t_pcs_arrival_info c on a.arrivalId=c.arrivalId"
				+" LEFT JOIN t_pcs_ship d on b.shipId=d.id"
				+" LEFT JOIN t_pcs_ship_ref e on b.shipRefId=e.id"
				+" LEFT JOIN t_pcs_product f on a.productId=f.id"
				+" LEFT JOIN t_pcs_client g on a.clientId=g.id"
				+" LEFT JOIN t_pcs_fee_charge h on h.id=m.feeChargeId and h.type=1"
				+" LEFT JOIN t_pcs_fee_bill i on i.id=h.feebillId"
				+" LEFT JOIN t_pcs_client_group j on j.id=g.clientGroupId "
		 		+" where 1=1 ";
			if (!Common.isNull(feeBillDto.getProductId())) 
				sql += " and m.productId=" + feeBillDto.getProductId();
			
			if (!Common.isNull(feeBillDto.getCargoId())) {
				sql += " and m.cargoId=" + feeBillDto.getCargoId();
			} else if (!Common.isNull(feeBillDto.getCargoCode())) {
				sql += " and (m.cargoCode like '%" + feeBillDto.getCargoCode()
						+ "%' or a.code like '%" + feeBillDto.getCargoCode()
						+ "%')";
			}
			if (!Common.isNull(feeBillDto.getCode())) 
				sql += " and i.code like '%" + feeBillDto.getCode() + "%'";
			
	        if(feeBillDto.getBillStatus()!=null&&feeBillDto.getBillStatus()!=-1){
		    	if(feeBillDto.getBillStatus()<3){//未提交，审核中，已提交
		    		sql+=" and (ISNULL(i.fundsStatus) or i.fundsStatus=0) and (ISNULL(i.billingStatus) or i.billingStatus=0)  and i.status="+feeBillDto.getBillStatus();			    		
		    	}else if(feeBillDto.getBillStatus()==3){//未到账
		    		sql+=" and i.status=2 and (i.fundsStatus!=2 OR ISNULL(i.fundsStatus))";
		    	}else if(feeBillDto.getBillStatus()==4){//未开票
		    		sql+=" and i.status=2 and (i.billingStatus=0 OR ISNULL(i.billingStatus))";
		    	}else if(feeBillDto.getBillStatus()==5){//已开票
		    		sql+=" and i.status=2 and i.billingStatus=1 ";
		    	}else if(feeBillDto.getBillStatus()==6){//已全部到账
		    		sql+=" and i.status=2 and i.fundsStatus=2 ";
		    	}else if(feeBillDto.getBillStatus()==7){//已完成
		    		sql+=" and i.status=2 and i.billingStatus=1 and i.fundsStatus=2 ";
		    	}else if(feeBillDto.getBillStatus()==8){//未完成
		    		sql+=" and( i.status!=2 or i.billingStatus!=1 or i.fundsStatus!=2 OR ISNULL(i.fundsStatus) or ISNULL(i.billingStatus)) ";
		    	}
		    }
	        if(!Common.isNull(feeBillDto.getClientId()))
	        	sql+=" and a.clientId="+feeBillDto.getClientId();
	        
	        if(!Common.isNull(feeBillDto.getFeeHead()))
	        	sql+=" and i.feeHead="+feeBillDto.getFeeHead();
	        
	        if(!Common.isNull(feeBillDto.getFeeTypes()))
	        	sql+=" and h.feeType in("+feeBillDto.getFeeTypes()+")";
	        if(!Common.isNull(feeBillDto.getTotalFee()))
		    	sql+=" and i.totalFee="+feeBillDto.getTotalFee();
	        if(!Common.empty(feeBillDto.getStartTime())&&feeBillDto.getStartTime()!=-1)
	        	sql+=" and i.accountTime>="+feeBillDto.getStartTime();
	        
	        if(!Common.empty(feeBillDto.getEndTime())&&feeBillDto.getEndTime()!=-1)
	        	sql+=" and i.accountTime<="+feeBillDto.getEndTime();
	        
	        if(feeBillDto.getBillingStartTime()!=null&&feeBillDto.getBillingStartTime()!=-1)
            	sql+=" and i.billingTime>="+feeBillDto.getBillingStartTime();
            
            if(feeBillDto.getBillingEndTime()!=null&&feeBillDto.getBillingEndTime()!=-1)
            	sql+=" and i.billingTime<="+feeBillDto.getBillingEndTime();
				sql+=" group by m.cargoId ORDER BY b.arrivalTime DESC";
			return executeQuery(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取账单号失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单号失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getTotalFee(FeeBillDto feeBillDto)
			throws OAException {
		 try {
			 StringBuilder sql=new StringBuilder();
				sql.append( " select round(sum(a.totalFee),2) totalFee,round(sum(a.unTaxFee),2) unTaxFee,round(sum(a.taxFee),2) taxFee,round(sum((select round(sum(d.accountFee),2)  from t_pcs_account_bill_log d where a.id=d.feebillId )),2) accountTotalFee ")
				.append(" from t_pcs_fee_bill a ");

				 if(!Common.isNull(feeBillDto.getProductId())||!Common.isNull(feeBillDto.getCargoId())||!Common.isNull(feeBillDto.getCargoCode())||!Common.isNull(feeBillDto.getClientId())){
					 sql.append(",(SELECT DISTINCT a.id  from t_pcs_fee_bill a LEFT JOIN t_pcs_fee_charge  b ON  a.id=b.feebillId ")
					 .append(" LEFT JOIN t_pcs_charge_cargo_lading c ON b.id=c.feeChargeId ")
					 .append(" LEFT JOIN t_pcs_cargo d ON d.id=c.cargoId where 1=1 ");
					 if(!Common.isNull(feeBillDto.getProductId())){
						 sql.append(" and b.productId=").append(feeBillDto.getProductId());
					 }
					 if(!Common.isNull(feeBillDto.getCargoId())){
						 sql.append(" and c.cargoId=").append(feeBillDto.getCargoId());
					 }else if(!Common.isNull(feeBillDto.getCargoCode())){
						 sql.append(" and (c.cargoCode like '%").append(feeBillDto.getCargoCode()).append("%' or d.code like '%")
						 .append(feeBillDto.getCargoCode()).append("%')");
					 }
					 if(!Common.isNull(feeBillDto.getClientId())){
						 sql.append(" and b.clientId=").append(feeBillDto.getClientId());
					 }
				     sql.append(" order by a.id desc ) tb");	 
				 }
					sql.append(" where 1=1 ");
				    if(!Common.isNull(feeBillDto.getFeeHead())){
				    	sql.append(" and a.feeHead="+feeBillDto.getFeeHead());
				    }
				    if(!Common.isNull(feeBillDto.getCode())){
				    	sql.append(" and a.code like'%"+feeBillDto.getCode()+"%'");
				    }
				    if(!Common.isNull(feeBillDto.getFeeType())){
				    	sql.append(" and a.feeType="+feeBillDto.getFeeType());	
				    }
				    if(!Common.isNull(feeBillDto.getFeeTypes())){
				    	sql.append(" and  a.feeType in("+feeBillDto.getFeeTypes()+")");
				    }
				    if(feeBillDto.getId()!=null){
				    	sql.append(" and a.id="+feeBillDto.getId());
				    }
				    if(!Common.isNull(feeBillDto.getProductId())||!Common.isNull(feeBillDto.getCargoId())||!Common.isNull(feeBillDto.getCargoCode())||!Common.isNull(feeBillDto.getClientId())){
				    	sql.append(" and a.id=tb.id ");
				    }
				    if(!Common.isNull(feeBillDto.getTotalFee()))
				    	sql.append(" and a.totalFee=").append(feeBillDto.getTotalFee());
				    if(feeBillDto.getBillingCode()!=null&&feeBillDto.getBillingCode()!=""){
				    	sql.append(" and a.billingCode like '%"+feeBillDto.getBillingCode()+"%'");
				    }
				    if(feeBillDto.getStatusStr()!=null){
				    	sql.append(" and a.status in ("+feeBillDto.getStatusStr()+")");
				    }
				    if(feeBillDto.getBillStatus()!=null&&feeBillDto.getBillStatus()!=-1){
				    	if(feeBillDto.getBillStatus()<3){//未提交，审核中，已提交
				    		sql.append(" and (ISNULL(a.fundsStatus) or a.fundsStatus=0) and (ISNULL(a.billingStatus) or a.billingStatus=0)  and a.status="+feeBillDto.getBillStatus());			    		
				    	}else if(feeBillDto.getBillStatus()==3){//未到账
				    		sql.append(" and a.status=2 and (a.fundsStatus!=2 OR ISNULL(a.fundsStatus))");
				    	}else if(feeBillDto.getBillStatus()==4){//未开票
				    		sql.append(" and a.status=2 and (a.billingStatus=0 OR ISNULL(a.billingStatus))");
				    	}else if(feeBillDto.getBillStatus()==5){//已开票
				    		sql.append(" and a.status=2 and a.billingStatus=1 ");
				    	}else if(feeBillDto.getBillStatus()==6){//已全部到账
				    		sql.append(" and a.status=2 and a.fundsStatus=2 ");
				    	}else if(feeBillDto.getBillStatus()==7){//已完成
				    		sql.append(" and a.status=2 and a.billingStatus=1 and a.fundsStatus=2 ");
				    	}else if(feeBillDto.getBillStatus()==8){//未完成
				    		sql.append(" and( a.status!=2 or a.billingStatus!=1 or a.fundsStatus!=2 OR ISNULL(a.fundsStatus) or ISNULL(a.billingStatus)) ");
				    	}
				    }
				    if(feeBillDto.getStartTime()!=null&&feeBillDto.getStartTime()!=-1){
				    	sql.append(" and a.accountTime>="+feeBillDto.getStartTime());
	                  }
	                  if(feeBillDto.getEndTime()!=null&&feeBillDto.getEndTime()!=-1){
	                	  sql.append(" and a.accountTime<="+feeBillDto.getEndTime());
	                  }
	                  if(feeBillDto.getBillingStartTime()!=null&&feeBillDto.getBillingStartTime()!=-1){
	                	  sql.append(" and a.billingTime>="+feeBillDto.getBillingStartTime());
	                    }
	                  if(feeBillDto.getBillingEndTime()!=null&&feeBillDto.getBillingEndTime()!=-1){
	                    	sql.append(" and a.billingTime<="+feeBillDto.getBillingEndTime());
	                    }
				return executeQuery(sql.toString());
			}catch (RuntimeException e) {
				LOG.error("dao获取账单统计费用失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单统计费用失败", e);
			}
	}

	@Override
	public Map<String, Object> getCargoCodesByInitialId(Integer initialId)
			throws OAException {
		try{
			 String sql="SELECT a.cargoCodes cargoCodes from t_pcs_initial_fee a  WHERE a.type=2 and  a.id="+initialId+" limit 0,1";
				return executeQueryOne(sql);
			}catch (RuntimeException e) {
				LOG.error("dao获取合同性质失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取合同性质失败", e);
			}
	}

	/**
	 * @Title getCargoCodesByfeebillId
	 * @Descrption:TODO
	 * @param:@param feebillId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月7日上午9:30:44
	 * @throws
	 */
	@Override
	public Map<String, Object> getCargoCodesByfeebillId(Integer feebillId) throws OAException {
		try{
			 String sql="SELECT (CASE WHEN ISNULL(GROUP_CONCAT(DISTINCT b.CODE)) THEN GROUP_CONCAT(DISTINCT c.cargoCode) "
			 		+ "ELSE GROUP_CONCAT(DISTINCT b.CODE) END) cargoCodes,GROUP_CONCAT(DISTINCT d.name) productnames "
			 		+ "from t_pcs_fee_charge a left join t_pcs_charge_cargo_lading c on c.feeChargeId=a.id "
			 		+ "left join t_pcs_cargo b on c.cargoId=b.id LEFT JOIN t_pcs_product d on d.id=c.productId WHERE a.feebillId="+feebillId;
				return executeQueryOne(sql);
			}catch (RuntimeException e) {
				LOG.error("dao获取货批号失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取货批号失败", e);
			}
	}

	/**
	 * @throws OAException 
	 * @Title getUpLoadFilesByfeebillId
	 * @Descrption:TODO
	 * @param:@param valueOf
	 * @param:@return
	 * @auhor jiahy
	 * @date 2016年7月18日上午10:24:49
	 * @throws
	 */
	@Override
	public Map<String, Object> getUpLoadFilesByfeebillId(Integer feebillId,Integer type) throws OAException {
		try{
		String sql= "select count(1) fileNum from t_pcs_uploadfile_info where refId="+feebillId+" and type="+type ;
		return executeQueryOne(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取货批号失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取货批号失败", e);
	}
	}

	/**
	 * @Title getFeeChargeList
	 * @Descrption:TODO
	 * @param:@param feeBillDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年7月27日上午7:49:20
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getBillFeeList(FeeBillDto feeBillDto) throws OAException {
		try{
		StringBuilder sql=new StringBuilder();
		   sql.append(" SELECT DISTINCT IFNULL(b.initialId,0) initialId,IFNULL(b.exceedId,0) exceedId ,  ")
		   .append(" IFNULL(b.feeType,0) feeType,c.type initialType,e.name feeHeadName, ")
		   .append(" (select h.name  from t_pcs_approvecontent g INNER JOIN t_auth_user h on g.userId=h.id ")
			.append(" WHERE g.workId=a.id and g.type=5 ORDER BY g.id asc LIMIT 0,1) reviewUserName  ")
		   .append(" from t_pcs_fee_bill a LEFT JOIN t_pcs_fee_charge b ON a.id=b.feebillId ")
		   .append(" LEFT JOIN t_pcs_initial_fee c ON c.id=b.initialId ")
		   .append(" LEFT JOIN t_auth_user d ON d.id=a.reviewUserId ")
		   .append(" LEFT JOIN t_pcs_client e ON e.id=a.feeHead ")
		   .append(" where a.id=");
		  if(!Common.isNull(feeBillDto.getId())){
			  sql.append(feeBillDto.getId());
		  }else{
			  sql.append(0);
		  }
		
		return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取货批号失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取货批号失败", e);
		}
	}

	/**
	 * @Title getFeeMsg
	 * @Descrption:TODO
	 * @param:@param fDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年7月27日下午2:25:58
	 * @throws
	 */
	@Override
	public Map<String, Object> getFeeMsg(FeeBillDto fDto) throws OAException {
	try{
		StringBuilder sql=new StringBuilder();
		sql.append(" call  proc_StorageFeeByIdSelect(").append(fDto.getId()).append(",").append(fDto.getFeeType()).append(",")
		.append(fDto.getInitialType()).append(")");
		return executeProcedureOne(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取结算单信息失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取结算单信息失败", e);
	}
	}

	/**
	 * @Title getFeeChargeList
	 * @Descrption:TODO
	 * @param:@param fDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年7月27日下午2:25:58
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getFeeChargeList(FeeBillDto fDto) throws OAException {
		try{
			StringBuilder  sql=new StringBuilder();
			sql.append(" SELECT feeType,unitFee,(CASE WHEN feeType=5 then 0 else feeCount END) feeCount,totalFee  from t_pcs_fee_charge where 1=1 ");
			if(fDto.getFeeType()==5){
				sql.append(" and exceedId=").append(fDto.getId());
			}else{
				sql.append(" and initialId=").append(fDto.getId());
			}
			sql.append(" and feebillId=").append(fDto.getFeebillId());
			
			return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取费用项失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取费用项失败", e);
		}
	}

	/**
	 * @Title getGroupFeeChargeList
	 * @Descrption:TODO
	 * @param:@param feeBillDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年8月1日上午9:11:45
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getGroupFeeChargeList(FeeBillDto feeBillDto) throws OAException {
		try{
			StringBuilder sql =new StringBuilder();
			sql.append("SELECT  a.feeType,0 unitFee,0 feeCount,SUM(ROUND(a.totalFee,2)) totalFee,SUM(ROUND(a.discountFee,2)) discountFee  from t_pcs_fee_charge a where a.feebillId=")
			.append(feeBillDto.getId()).append(" GROUP BY a.feeType");
			
			return executeQuery(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao根据费用项统计费用总和失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao根据费用项统计费用总和失败", e);
	}
	}

	/**
	 * @Title backFeeStatus
	 * @Descrption:TODO
	 * @param:@param feeBillDto
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年11月28日下午1:55:28
	 * @throws
	 */
	@Override
	public void backFeeStatus(FeeBillDto feeBillDto) throws OAException {
		try{
			StringBuilder sql =new StringBuilder();
			StringBuilder sql1 =new StringBuilder();
			sql.append(" update t_pcs_initial_fee a left join t_pcs_fee_charge b on a.id=b.initialId set a.status=1 where b.feebillId=")
			.append(feeBillDto.getId());
			sql1.append(" update t_pcs_exceed_fee a left join t_pcs_fee_charge b on a.id=b.exceedId set a.status=1 where b.feebillId=")
			.append(feeBillDto.getId());
			executeUpdate(sql.toString());
			executeUpdate(sql1.toString());
	}catch (RuntimeException e) {
		LOG.error("dao根据费用项统计费用总和失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao根据费用项统计费用总和失败", e);
	}
	}
	

}
