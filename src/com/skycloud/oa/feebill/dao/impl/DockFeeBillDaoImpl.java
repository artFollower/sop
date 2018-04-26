package com.skycloud.oa.feebill.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.DockFeeBillDao;
import com.skycloud.oa.feebill.dto.DockFeeBillDto;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.model.DockFeeBill;
import com.skycloud.oa.utils.Common;
/**
 *账单
 * @author jiahy
 * @version 2015年6月14日 下午1:59:52
 */
@Component
public class DockFeeBillDaoImpl extends BaseDaoImpl implements DockFeeBillDao {
	private static Logger LOG = Logger.getLogger(DockFeeBillDaoImpl.class);

	@Override
	public List<Map<String, Object>> getDockFeeBillList(DockFeeBillDto dDto, int start, int limit)
			throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
				sql.append( "select a.*,(case when a.billType=1 THEN '手撕发票' else '增值税发票' end) tradeTypeStr,(case when a.feeType=1 THEN '靠泊费' else '淡水费' end) feeTypeStr,from_unixtime(a.accountTime) accountTimeStr,c.name createUserName,")
					.append( " d.name billingUserName,from_unixtime(a.billingTime) billingTimeStr,")
					.append( "  e.name reviewUserName, from_unixtime(a.reviewTime) reviewTimeStr,")
					.append( " (select e.name from t_pcs_account_bill_log d LEFT JOIN t_auth_user e on d.accountUserId=e.id ")
					.append( " WHERE d.dockfeebillId=a.id ORDER BY d.accountTime DESC LIMIT 0,1  )  fundsUserName,")
					.append( " (select FROM_UNIXTIME(f.accountTime) from t_pcs_account_bill_log f ")
					.append( " WHERE f.dockfeebillId=a.id ORDER BY f.accountTime DESC LIMIT 0,1  )  fundsTimeStr,")
					.append(" (select id from t_pcs_account_bill_log d WHERE d.dockfeebillId=a.id ORDER BY d.accountTime DESC LIMIT 0,1) accountBillId,")
					.append( " (select round(sum(d.accountFee),2)  from t_pcs_account_bill_log d where a.id=d.dockfeebillId ) accountTotalFee")
					.append( " from t_pcs_dockfee_bill a  ")
					.append( " left join t_auth_user c on a.createUserId=c.id")
					.append( " left join t_auth_user d on a.billingUserId=d.id")
					.append(" LEFT JOIN t_auth_user e on a.reviewUserId=e.id")
					.append( " where 1=1 ");
				if(!Common.isNull(dDto.getId()))
					sql.append(" and a.id=").append(dDto.getId());
			    if(!Common.isNull(dDto.getFeeHead()))
			    	sql.append( " and a.feeHead='").append(dDto.getFeeHead()).append("'");
			    
			    if(!Common.isNull(dDto.getCode()))
			    	sql.append( " and a.code like'%").append(dDto.getCode()).append("%'");
			    
			    if(!Common.isNull(dDto.getBillType()))
			    	sql.append(" and a.billType=").append(dDto.getBillType());
			    if(!Common.isNull(dDto.getFeeType()))
			    	sql.append(" and a.feeType=").append(dDto.getFeeType());
			    if(dDto.getBillStatus()!=null&&dDto.getBillStatus()!=-1){
			    	if(dDto.getBillStatus()<3)//未提交，审核中，已提交
			    		sql.append(" and (ISNULL(a.fundsStatus) or a.fundsStatus=0) and (ISNULL(a.billingStatus) or a.billingStatus=0)  and a.status="+dDto.getBillStatus());			    		
			    	else if(dDto.getBillStatus()==3)//未到账
			    		sql.append(" and a.status=2 and (a.fundsStatus!=2 OR ISNULL(a.fundsStatus))");
			    	else if(dDto.getBillStatus()==4)//未开票
			    		sql.append(" and a.status=2 and (a.billingStatus=0 OR ISNULL(a.billingStatus))");
			    	else if(dDto.getBillStatus()==5)//已开票
			    		sql.append(" and a.status=2 and a.billingStatus=1 ");
			    	else if(dDto.getBillStatus()==6)//已全部到账
			    		sql.append(" and a.status=2 and a.fundsStatus=2 ");
			    	else if(dDto.getBillStatus()==7)//已完成
			    		sql.append(" and a.status=2 and a.billingStatus=1 and a.fundsStatus=2 ");
			    	else if(dDto.getBillStatus()==8)//未完成
			    		sql.append(" and( a.status!=2 or a.billingStatus!=1 or a.fundsStatus!=2 OR ISNULL(a.fundsStatus) or ISNULL(a.billingStatus)) ");
			    }
			    if(dDto.getStartTime()!=null&&dDto.getStartTime()!=-1)
                  	sql.append( " and a.accountTime>=").append(dDto.getStartTime());
                  
                  if(dDto.getEndTime()!=null&&dDto.getEndTime()!=-1)
                  	sql.append( " and a.accountTime<=").append(dDto.getEndTime());
                  
                  if(dDto.getBillingStartTime()!=null&&dDto.getBillingStartTime()!=-1)
                    	sql.append( " and a.billingTime>=").append(dDto.getBillingStartTime());
                    
                    if(dDto.getBillingEndTime()!=null&&dDto.getBillingEndTime()!=-1)
                    	sql.append( " and a.billingTime<=").append(dDto.getBillingEndTime());
                    
                  sql.append( " order by a.code desc");
			    if(limit!=0)
			    sql.append( " limit ").append(start).append(",").append(limit);
			return executeQuery(sql.toString()) ;
		}catch (RuntimeException e) {
			LOG.error("dao获取账单列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单列表失败", e);
		}
	}

	@Override
	public int getDockFeeBillCount(DockFeeBillDto dDto) throws OAException {
		 try {
		StringBuilder sql=new StringBuilder();
		sql.append( "select count(1) from t_pcs_dockfee_bill a  where 1=1 ");
		  if(!Common.isNull(dDto.getFeeHead()))
		    	sql.append( " and a.feeHead='").append(dDto.getFeeHead()).append("'");
		    
		    if(!Common.isNull(dDto.getCode()))
		    	sql.append( " and a.code like'%").append(dDto.getCode()).append("%'");
		    
		    if(!Common.isNull(dDto.getBillType()))
		    	sql.append(" and a.billType=").append(dDto.getBillType());
		    if(!Common.isNull(dDto.getFeeType()))
		    	sql.append(" and a.feeType=").append(dDto.getFeeType());
		    
		    if(dDto.getBillStatus()!=null&&dDto.getBillStatus()!=-1){
		    	if(dDto.getBillStatus()<3)//未提交，审核中，已提交
		    		sql.append(" and (ISNULL(a.fundsStatus) or a.fundsStatus=0) and (ISNULL(a.billingStatus) or a.billingStatus=0)  and a.status="+dDto.getBillStatus());			    		
		    	else if(dDto.getBillStatus()==3)//未到账
		    		sql.append(" and a.status=2 and (a.fundsStatus!=2 OR ISNULL(a.fundsStatus))");
		    	else if(dDto.getBillStatus()==4)//未开票
		    		sql.append(" and a.status=2 and (a.billingStatus=0 OR ISNULL(a.billingStatus))");
		    	else if(dDto.getBillStatus()==5)//已开票
		    		sql.append(" and a.status=2 and a.billingStatus=1 ");
		    	else if(dDto.getBillStatus()==6)//已全部到账
		    		sql.append(" and a.status=2 and a.fundsStatus=2 ");
		    	else if(dDto.getBillStatus()==7)//已完成
		    		sql.append(" and a.status=2 and a.billingStatus=1 and a.fundsStatus=2 ");
		    	else if(dDto.getBillStatus()==8)//未完成
		    		sql.append(" and( a.status!=2 or a.billingStatus!=1 or a.fundsStatus!=2 OR ISNULL(a.fundsStatus) or ISNULL(a.billingStatus)) ");
		    }
		    if(dDto.getStartTime()!=null&&dDto.getStartTime()!=-1)
            	sql.append( " and a.accountTime>=").append(dDto.getStartTime());
            
            if(dDto.getEndTime()!=null&&dDto.getEndTime()!=-1)
            	sql.append( " and a.accountTime<=").append(dDto.getEndTime());
            
            if(dDto.getBillingStartTime()!=null&&dDto.getBillingStartTime()!=-1)
              	sql.append( " and a.billingTime>=").append(dDto.getBillingStartTime());
              
              if(dDto.getBillingEndTime()!=null&&dDto.getBillingEndTime()!=-1)
              	sql.append( " and a.billingTime<=").append(dDto.getBillingEndTime());
              
			return (int) getCount(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取账单列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单列表数量失败", e);
	}
		 }

	@Override
	public int addDockFeeBill(DockFeeBill dfbill) throws OAException {
		  try {
				return (Integer) save(dfbill);
			}catch (RuntimeException e) {
				LOG.error("dao添加账单失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加账单失败", e);
			}
	}

	@Override
	public void updateDockFeeBill(DockFeeBill feeBill) throws OAException {
		   try {
			   StringBuilder sql=new StringBuilder();
				 sql.append("update t_pcs_dockfee_bill set id=id");
				if(feeBill.getCode()!=null){
					sql.append(",code='"+feeBill.getCode()+"'");
				}
				if(feeBill.getFeeType()!=null){
					sql.append(",feeType="+feeBill.getFeeType());
				}
				if(feeBill.getBillType()!=null)
					sql.append(",billType=").append(feeBill.getBillType());
				if(feeBill.getFeeHead()!=null){
					sql.append(",feeHead='"+feeBill.getFeeHead()+"'");
				}
				if(feeBill.getCreateUserId()!=null){
					sql.append(",createUserId="+feeBill.getCreateUserId());
				}
				if(feeBill.getCreateTime()!=null){
					sql.append(",createTime="+feeBill.getCreateTime());
				}
				if(feeBill.getTotalFee()!=null){
					sql.append(",totalFee='"+feeBill.getTotalFee()+"'");
				}
				if(feeBill.getHasTotalFee()!=null){
					sql.append(",hasTotalFee='"+feeBill.getHasTotalFee()+"'");
				}
				if(feeBill.getReviewUserId()!=null){
					sql.append(",reviewUserId="+feeBill.getReviewUserId());
				}
				if(feeBill.getReviewTime()!=null){
					sql.append(",reviewTime="+feeBill.getReviewTime());
				}
				if(!Common.isNull(feeBill.getComment())){
					sql.append(",comment='").append(feeBill.getComment()).append("'");
				}
				if(feeBill.getAccountTime()!=null){
					sql.append(",accountTime="+feeBill.getAccountTime());
				}
				if(feeBill.getDescription()!=null){
					sql.append(",description='"+feeBill.getDescription()+"'");
				}
				if(feeBill.getStatus()!=null){
					sql.append(",status="+feeBill.getStatus());
				}
				if(feeBill.getFundsTotalFee()!=null){
					sql.append(",fundsTotalFee='"+feeBill.getFundsTotalFee()+"'");
				}
				if(feeBill.getFundsStatus()!=null&&feeBill.getFundsStatus()!=-1){
					sql.append(",fundsStatus="+feeBill.getFundsStatus());
				}else if(feeBill.getFundsStatus()!=null&&feeBill.getFundsStatus()==-1){
					sql.append(",fundsStatus=null");
				}
				if(feeBill.getBillingCode()!=null){
					sql.append(",billingCode='"+feeBill.getBillingCode()+"'");
				}
				if(feeBill.getBillingStatus()!=null&&feeBill.getBillingStatus()!=-1){
					sql.append(",billingStatus="+feeBill.getBillingStatus());
				}else if(feeBill.getBillingStatus()!=null&&feeBill.getBillingStatus()==-1){
					sql.append(",billingStatus=null");
				}
				if(feeBill.getBillingUserId()!=null){
					sql.append(",billingUserId="+feeBill.getBillingUserId());
				}
				if(feeBill.getBillingTime()!=null){
					sql.append(",billingTime="+feeBill.getBillingTime());
				}
				if(feeBill.getUnTaxFee()!=null){
					sql.append(",unTaxFee='"+feeBill.getUnTaxFee()+"'");
				}
				if(feeBill.getTaxRate()!=null){
					sql.append(",taxRate='"+feeBill.getTaxRate()+"'");
				}
				if(feeBill.getTaxFee()!=null){
					sql.append(",taxFee='"+feeBill.getTaxFee()+"'");
				}
				
				
				sql.append(" where id="+feeBill.getId());
				executeUpdate(sql.toString());
			}catch (RuntimeException e) {
				LOG.error("dao更新账单失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新账单失败", e);
			}
	}

	@Override
	public Map<String, Object> getCodeNum(DockFeeBillDto dockFeeBillDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT CONCAT('',right(concat('0000000',IFNULL(MAX(code),0)+1),7))  codeNum   from t_pcs_dockfee_bill ");
			return executeQueryOne(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表失败", e);
		}
	}

	@Override
	public void deleteFeeBill(DockFeeBillDto dockFeeBillDto) throws OAException {
		 try {
				String sql="delete from t_pcs_dockfee_bill where id="+dockFeeBillDto.getId();
				execute(sql);
			}catch (RuntimeException e) {
				LOG.error("dao删除账单失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除账单失败", e);
			}
	}

	@Override
	public Map<String, Object> getDockFeeBillMsg(DockFeeBillDto dDto)
			throws OAException {
		 try {
			StringBuilder sql=new StringBuilder();
				sql.append(" select a.*,(case when a.feeType=1 THEN '外贸' else '内贸' end) tradeTypeStr, ")
				.append(" from_unixtime(a.accountTime) accountTimeStr,c.name createUserName,e.name reviewUserName, ")
				.append(" FROM_UNIXTIME(a.reviewTime) reviewTimeStr,d.name billingUserName,from_unixtime(a.billingTime) billingTimeStr, ")
				.append(" (select e.name from t_pcs_account_bill_log d INNER JOIN t_auth_user e on d.accountUserId=e.id  ")
				.append(" WHERE d.dockfeebillId=a.id ORDER BY d.accountTime DESC LIMIT 0,1  )  fundsUserName, ")
				.append(" (select FROM_UNIXTIME(f.accountTime) from t_pcs_account_bill_log f  ")
				.append(" WHERE f.dockfeebillId=a.id ORDER BY f.accountTime DESC LIMIT 0,1  )  fundsTimeStr, ")
				.append(" (select id from t_pcs_account_bill_log d WHERE d.dockfeebillId=a.id ORDER BY d.accountTime DESC LIMIT 0,1) accountBillId, ")
				.append(" (select round(sum(d.accountFee),2)  from t_pcs_account_bill_log d where a.id=d.dockfeebillId ) accountTotalFee ")
				.append(" from t_pcs_dockfee_bill a ")  
				.append(" left join t_auth_user c on a.createUserId=c.id ")
				.append(" left join t_auth_user d on a.billingUserId=d.id ")
				.append(" LEFT JOIN t_auth_user e ON a.reviewUserId=e.id ")
				.append(" where a.id=").append(dDto.getId());
				return executeQueryOne(sql.toString());
			}catch (RuntimeException e) {
				LOG.error("dao获取账单明细失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单明细失败", e);
			}
	}

	/**
	 * @Title getDockFeeBillExcelList
	 * @Descrption:TODO
	 * @param:@param dockFeeBillDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月1日上午9:24:31
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getDockFeeBillExcelList(DockFeeBillDto dDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
				sql.append("SELECT b.id,b.`code`,FROM_UNIXTIME(b.accountTime,'%Y-%m-%d') accountTime,b.feeHead, ")
				.append("(CASE WHEN b.feeType=1 THEN '手撕发票' ELSE '增值税发票' END) tradeType,")
				.append("(CASE WHEN a.feeType=1 THEN '靠泊费' WHEN a.feeType=2 THEN '淡水费' ELSE '其他费' END) feeType, ")
				.append("a.unitFee,a.feeCount,a.totalFee,a.discountFee,CONCAT(e.`name`,'/',f.refName) shipName, ")
				.append(" DATE_FORMAT(d.arrivalStartTime,'%Y-%m-%d') arrivalTime, ")  
				.append("(SELECT FROM_UNIXTIME(accountTime,'%Y-%m-%d') from t_pcs_account_bill_log ")
				.append("WHERE dockfeebillId=b.id ORDER BY accountTime DESC LIMIT 0,1) fundsTime, ")
				.append("b.billingCode,FROM_UNIXTIME(b.billingTime,'%Y-%m-%d') billingTime,b.totalFee allTotalFee, ")
				.append("(SELECT SUM(ROUND(accountFee,2)) from t_pcs_account_bill_log where dockfeebillId=b.id) accountFee,  ")
				.append("b.taxFee,b.taxRate,b.unTaxFee,b.description ")
				.append(" from t_pcs_dockfee_charge a  ")
				.append("LEFT JOIN t_pcs_dockfee_bill b ON a.feebillId=b.id ")
				.append("LEFT JOIN t_pcs_dock_fee c ON c.id=a.dockFeeId ")
				.append("LEFT JOIN t_pcs_arrival d ON d.id=c.arrivalId ")
				.append("LEFT JOIN t_pcs_ship e ON e.id=d.shipId ")
				.append("LEFT JOIN t_pcs_ship_ref f ON f.id=d.shipRefId ")
				.append("WHERE !ISNULL(a.feebillId) ");
				
				if(!Common.isNull(dDto.getFeeHead()))
				    	sql.append( " and a.feeHead='").append(dDto.getFeeHead()).append("'");
				
				if(!Common.isNull(dDto.getCode()))
					sql.append( " and a.code like'%").append(dDto.getCode()).append("%'");
				
				if(!Common.isNull(dDto.getTradeType()))
					sql.append(" and a.feeType=").append(dDto.getTradeType());
				
				if(dDto.getBillStatus()!=null&&dDto.getBillStatus()!=-1){
					if(dDto.getBillStatus()<4){
				    sql.append( " and ISNULL(a.fundsStatus) and ISNULL(a.billingStatus)  and a.status=").append(dDto.getBillStatus());			    		
				}else if(dDto.getBillStatus()==4){
					sql.append( " and a.status=2 and a.billingStatus=1");
				}else if(dDto.getBillStatus()==5){
					sql.append( " and a.status=2 and a.fundsStatus=2");
				}else if(dDto.getBillStatus()==6){
					sql.append( " and a.status=2  and a.billingStatus=1 and (a.fundsStatus<>2 or ISNULL(a.fundsStatus)) ");
					}
				}
				if(dDto.getStartTime()!=null&&dDto.getStartTime()!=-1)
				  	sql.append( " and b.accountTime>=").append(dDto.getStartTime());
				  
				if(dDto.getEndTime()!=null&&dDto.getEndTime()!=-1)
				  	sql.append( " and b.accountTime<=").append(dDto.getEndTime());
				  
				if(dDto.getBillingStartTime()!=null&&dDto.getBillingStartTime()!=-1)
				    	sql.append( " and b.billingTime>=").append(dDto.getBillingStartTime());
				
				if(dDto.getBillingEndTime()!=null&&dDto.getBillingEndTime()!=-1)
					sql.append( " and b.billingTime<=").append(dDto.getBillingEndTime());
	                    
			sql.append(" order by b.code asc");
			System.out.println(sql.toString());
			return executeQuery(sql.toString());
			}catch (RuntimeException e) {
		LOG.error("dao获取账单明细失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单明细失败", e);
	}
	}

	/**
	 * @Title getdockFeeMsgByFeeBillId
	 * @Descrption:TODO
	 * @param:@param id
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月2日上午9:47:44
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getdockFeeMsgByFeeBillId(int id)throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT DISTINCT a.dockFeeId,IFNULL(c.name,'') reviewUserName  from t_pcs_dockfee_charge a left join t_pcs_dockfee_bill b on a.feebillId=b.id ")
			.append(" left join t_auth_user c on b.reviewUserId=c.id")
			.append(" where a.feebillId=")
			.append(id).append(" ORDER BY a.dockFeeId ASC");
			return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取账单明细失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单明细失败", e);
		}
	}

	/**
	 * @Title getGroupFeeChargeList
	 * @Descrption:TODO
	 * @param:@param id
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月5日上午9:00:51
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getGroupFeeChargeList(int id)
			throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT  (CASE WHEN a.feeType=1 THEN '靠泊费' WHEN a.feeType=2 THEN '淡水费' ELSE '其他费' END) feeType,SUM(ROUND(a.totalFee,2)) totalFee,SUM(ROUND(a.discountFee,2)) discountFee  from t_pcs_dockfee_charge a where a.feebillId=")
			.append(id).append(" GROUP BY a.feeType");
			return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取费用项组失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取费用项组失败", e);
		}
	}

	/**
	 * @Title updateDockFeeStatus
	 * @Descrption:TODO
	 * @param:@param dfbillId
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月7日下午2:57:22
	 * @throws
	 */
	@Override
	public void updateDockFeeStatus(int dfbillId) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" call proc_dockFeeUpdateStatusByfeebillId(").append(dfbillId).append(")");
			  executeProcedure(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao更新码头规费状态失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新码头规费状态失败", e);
		}
	}

	@Override
	public void cleanFeeStatus(Integer id) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" update t_pcs_dock_fee a left join t_pcs_dockfee_charge b on a.id=b.dockFeeId set a.status=1 where b.feebillId=")
			.append(id);
			  executeProcedure(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao更新码头规费状态失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新码头规费状态失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getTotalFee(DockFeeBillDto dDto) throws OAException {
		 try {
			StringBuilder sql=new StringBuilder();
				
				sql.append( " select round(sum(a.totalFee),2) totalFee,round(sum(a.unTaxFee),2) unTaxFee,round(sum(a.taxFee),2) taxFee,round(sum((select round(sum(d.accountFee),2)  from t_pcs_account_bill_log d where a.id=d.dockfeebillId )),2) accountTotalFee ")
				.append(" from t_pcs_dockfee_bill a where 1=1 ");
				
				if(!Common.isNull(dDto.getId()))
					sql.append(" and a.id=").append(dDto.getId());
			    if(!Common.isNull(dDto.getFeeHead()))
			    	sql.append( " and a.feeHead='").append(dDto.getFeeHead()).append("'");
			    
			    if(!Common.isNull(dDto.getCode()))
			    	sql.append( " and a.code like'%").append(dDto.getCode()).append("%'");
			    
			    if(!Common.isNull(dDto.getBillType()))
			    	sql.append(" and a.billType=").append(dDto.getBillType());
			    if(!Common.isNull(dDto.getFeeType()))
			    	sql.append(" and a.feeType=").append(dDto.getFeeType());
			    if(dDto.getBillStatus()!=null&&dDto.getBillStatus()!=-1){
			    	if(dDto.getBillStatus()<3)//未提交，审核中，已提交
			    		sql.append(" and (ISNULL(a.fundsStatus) or a.fundsStatus=0) and (ISNULL(a.billingStatus) or a.billingStatus=0)  and a.status="+dDto.getBillStatus());			    		
			    	else if(dDto.getBillStatus()==3)//未到账
			    		sql.append(" and a.status=2 and (a.fundsStatus!=2 OR ISNULL(a.fundsStatus))");
			    	else if(dDto.getBillStatus()==4)//未开票
			    		sql.append(" and a.status=2 and (a.billingStatus=0 OR ISNULL(a.billingStatus))");
			    	else if(dDto.getBillStatus()==5)//已开票
			    		sql.append(" and a.status=2 and a.billingStatus=1 ");
			    	else if(dDto.getBillStatus()==6)//已全部到账
			    		sql.append(" and a.status=2 and a.fundsStatus=2 ");
			    	else if(dDto.getBillStatus()==7)//已完成
			    		sql.append(" and a.status=2 and a.billingStatus=1 and a.fundsStatus=2 ");
			    	else if(dDto.getBillStatus()==8)//未完成
			    		sql.append(" and( a.status!=2 or a.billingStatus!=1 or a.fundsStatus!=2 OR ISNULL(a.fundsStatus) or ISNULL(a.billingStatus)) ");
			    }
			    if(dDto.getStartTime()!=null&&dDto.getStartTime()!=-1)
                  	sql.append( " and a.accountTime>=").append(dDto.getStartTime());
                  
                  if(dDto.getEndTime()!=null&&dDto.getEndTime()!=-1)
                  	sql.append( " and a.accountTime<=").append(dDto.getEndTime());
                  
                  if(dDto.getBillingStartTime()!=null&&dDto.getBillingStartTime()!=-1)
                    	sql.append( " and a.billingTime>=").append(dDto.getBillingStartTime());
                    
                    if(dDto.getBillingEndTime()!=null&&dDto.getBillingEndTime()!=-1)
                    	sql.append( " and a.billingTime<=").append(dDto.getBillingEndTime());
                    
                  sql.append( " order by a.code desc");
			return executeQuery(sql.toString()) ;
		}catch (RuntimeException e) {
				LOG.error("dao获取账单统计费用失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单统计费用失败", e);
			}
	}

	@Override
	public List<Map<String, Object>> getDockFeeBillExcelList1(DockFeeBillDto dDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
				sql.append("SELECT b.id,b.`code`,FROM_UNIXTIME(b.accountTime,'%Y-%m-%d') accountTime,b.feeHead, ")
				.append("(CASE WHEN b.feeType=1 THEN '手撕发票' ELSE '增值税发票' END) tradeType,")
				.append("(SELECT FROM_UNIXTIME(accountTime,'%Y-%m-%d') from t_pcs_account_bill_log ")
				.append("WHERE dockfeebillId=b.id ORDER BY accountTime DESC LIMIT 0,1) fundsTime, ")
				.append("b.billingCode,FROM_UNIXTIME(b.billingTime,'%Y-%m-%d') billingTime,b.totalFee allTotalFee, ")
				.append("(SELECT SUM(ROUND(accountFee,2)) from t_pcs_account_bill_log where dockfeebillId=b.id) accountFee,  ")
				.append("b.taxFee,b.taxRate,b.unTaxFee,b.description ")
				.append(" from t_pcs_dockfee_charge a  ")
				.append("LEFT JOIN t_pcs_dockfee_bill b ON a.feebillId=b.id ")
				.append("WHERE !ISNULL(a.feebillId) ");
				
				if(!Common.isNull(dDto.getFeeHead()))
				    	sql.append( " and a.feeHead='").append(dDto.getFeeHead()).append("'");
				
				if(!Common.isNull(dDto.getCode()))
					sql.append( " and a.code like'%").append(dDto.getCode()).append("%'");
				
				if(!Common.isNull(dDto.getTradeType()))
					sql.append(" and a.feeType=").append(dDto.getTradeType());
				
				if(dDto.getBillStatus()!=null&&dDto.getBillStatus()!=-1){
					if(dDto.getBillStatus()<4){
				    sql.append( " and ISNULL(a.fundsStatus) and ISNULL(a.billingStatus)  and a.status=").append(dDto.getBillStatus());			    		
				}else if(dDto.getBillStatus()==4){
					sql.append( " and a.status=2 and a.billingStatus=1");
				}else if(dDto.getBillStatus()==5){
					sql.append( " and a.status=2 and a.fundsStatus=2");
				}else if(dDto.getBillStatus()==6){
					sql.append( " and a.status=2  and a.billingStatus=1 and (a.fundsStatus<>2 or ISNULL(a.fundsStatus)) ");
					}
				}
				if(dDto.getStartTime()!=null&&dDto.getStartTime()!=-1)
				  	sql.append( " and b.accountTime>=").append(dDto.getStartTime());
				  
				if(dDto.getEndTime()!=null&&dDto.getEndTime()!=-1)
				  	sql.append( " and b.accountTime<=").append(dDto.getEndTime());
				  
				if(dDto.getBillingStartTime()!=null&&dDto.getBillingStartTime()!=-1)
				    	sql.append( " and b.billingTime>=").append(dDto.getBillingStartTime());
				
				if(dDto.getBillingEndTime()!=null&&dDto.getBillingEndTime()!=-1)
					sql.append( " and b.billingTime<=").append(dDto.getBillingEndTime());
	                    
			sql.append(" group by b.id order by b.code asc ");
			System.out.println(sql.toString());
			return executeQuery(sql.toString());
			}catch (RuntimeException e) {
		LOG.error("dao获取账单明细失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单明细失败", e);
	}
	}

}
