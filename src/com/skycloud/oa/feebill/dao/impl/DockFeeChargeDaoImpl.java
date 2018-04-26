package com.skycloud.oa.feebill.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.DockFeeChargeDao;
import com.skycloud.oa.feebill.dto.DockFeeBillDto;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.model.DockFeeCharge;
import com.skycloud.oa.feebill.model.FeeCharge;
import com.skycloud.oa.utils.Common;
/**
 *费用
 * @author jiahy
 * @version 2015年6月14日 下午1:59:52
 */
@Component
public class DockFeeChargeDaoImpl extends BaseDaoImpl implements DockFeeChargeDao {
	private static Logger LOG = Logger.getLogger(DockFeeChargeDaoImpl.class);

	@Override
	public void addDockFeeCharge(DockFeeCharge feeCharge) throws OAException {
		try {
			  save(feeCharge);
	}catch (RuntimeException e) {
		LOG.error("dao添加费用失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加费用失败", e);
	}
	}

	@Override
	public void updateDockFeeCharge(DockFeeCharge feeCharge) throws OAException {
		try {
			String sql="update t_pcs_dockfee_charge set id=id ";
			if(feeCharge.getType()!=null){
				sql+=",type="+feeCharge.getType();
			}
			if(feeCharge.getFeeType()!=null){
				sql+=",feeType="+feeCharge.getFeeType();
			}
			if(feeCharge.getUnitFee()!=null){
				sql+=",unitFee='"+feeCharge.getUnitFee()+"'";
			}
			if(feeCharge.getFeeCount()!=null){
				sql+=",feeCount='"+feeCharge.getFeeCount()+"'";
			}
			if(feeCharge.getTotalFee()!=null){
				sql+=",totalFee='"+feeCharge.getTotalFee()+"'";
			}
			if(feeCharge.getDiscountFee()!=null){
				sql+=",discountFee="+feeCharge.getDiscountFee();
			}
			if(feeCharge.getDescription()!=null&&!Common.empty(feeCharge.getDescription())){
				sql+=",description='"+feeCharge.getDescription()+"'";
			}
			if(feeCharge.getFeebillId()!=null){
				sql+=",feebillId="+feeCharge.getFeebillId();
			}
			if(feeCharge.getClientName()!=null){
				sql+=",clientName='"+feeCharge.getClientName()+"'";
			}
			
			
			if(feeCharge.getCreateTime()!=null){
				if(feeCharge.getCreateTime()==0){
					sql+=",createTime=null";
				}else{
					sql+=",createTime="+feeCharge.getCreateTime();
				}
				
			}
			if(feeCharge.getId()!=null){
			sql+=" where id="+feeCharge.getId();
			}else if(feeCharge.getFeebillId()!=null){
				sql+=" where feebillId="+feeCharge.getFeebillId();
			}
			executeUpdate(sql);
	}catch (RuntimeException e) {
		LOG.error("dao更新费用失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新费用失败", e);
	}
	}

	@Override
	public void deleteDockFeeCharge(DockFeeChargeDto dfDto) throws OAException {
		try {
            StringBuilder sql=new StringBuilder();
			sql.append(" delete from t_pcs_dockfee_charge where 1=1");
			if(dfDto.getId()!=null){
				sql.append(" and id=").append(dfDto.getId());
			}else if(dfDto.getDockFeeId()!=null){
				sql.append(" and dockfeeId=").append(dfDto.getDockFeeId());
			}else{
				sql.append(" and id=0");
			}
           execute(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao添加费用失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加费用失败", e);
	}
	}

	@Override
	public List<Map<String, Object>> getDockFeeChargeList(DockFeeChargeDto dDto, int startRecord, int maxresult) throws OAException {
		try {
            StringBuilder sql=new StringBuilder();
				sql.append(" select a.*,(case when a.feeType=1 THEN '靠泊费' when a.feeType=2 THEN '淡水费' else '其他费用' end ) feeTypeStr")
				.append(" ,b.code,CONCAT(f.name,'/',e.refName) boundMsg,(case when c.type=1 then '入港' when c.type=2 then '出港'")
				.append("  when c.type=3 then '入港通过' when c.type=5 then '出港通过' END) arrivalType,b.tradeType,b.billType,")
				.append(" DATE_FORMAT(c.arrivalStartTime,'%Y-%m-%d') arrivalTime,b.arrivalId,d.code feebillcode")
				.append(" from t_pcs_dockfee_charge a ")
				.append(" LEFT JOIN t_pcs_dock_fee b on a.dockFeeId=b.id ")
				.append(" LEFT JOIN t_pcs_arrival c on c.id=b.arrivalId ")
				.append(" LEFT JOIN t_pcs_ship_ref e on c.shipRefId=e.id")
				.append(" LEFT JOIN t_pcs_ship f on c.shipId=f.id")
				.append(" LEFT JOIN t_pcs_dockfee_bill d on d.id=a.feebillId ")
				.append(" where 1=1 and a.type=1 ");
				if(!Common.isNull(dDto.getDockFeeId()))
					sql.append(" and a.dockFeeId=").append(dDto.getDockFeeId());
				if(dDto.getFeeType()!=null&&dDto.getFeeType()!=0)
					sql.append(" and a.feeType=").append(dDto.getFeeType());
				if(dDto.getBillType()!=null&&dDto.getBillType()!=0)
					sql.append(" and b.billType=").append(dDto.getBillType());
				if(!Common.isNull(dDto.getClientName()))
					sql.append(" and a.clientName='").append(dDto.getClientName()).append("'");
				if(!Common.isNull(dDto.getTradeType()))
					sql.append(" and b.tradeType=").append(dDto.getTradeType());
				if(dDto.getType()!=null){
					if(dDto.getType()==1){
						sql.append(" and (a.feebillId=0 or ISNULL(a.feebillId))");
					}else if(dDto.getType()==2){
						sql.append(" and !ISNULL(a.feebillId) and a.feebillId!=0 ");
					}
				}
				if(dDto.getFeebillId()!=null)
					sql.append(" and feebillId=").append(dDto.getFeebillId());
				if(dDto.getStartTime()!=null&&dDto.getStartTime()!=-1)
					sql.append(" and UNIX_TIMESTAMP(c.arrivalStartTime) >=").append(dDto.getStartTime());
				if(dDto.getEndTime()!=null&&dDto.getEndTime()!=-1)
					sql.append(" and UNIX_TIMESTAMP(c.arrivalStartTime) <=").append(dDto.getEndTime());
				sql.append(" order by b.code desc ");
				if(maxresult!=0){
					sql.append(" limit ").append(startRecord).append(" , ").append(maxresult);
				}
				
				
         return executeQuery(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取码头规费列表失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取码头规费列表失败", e);
	}
	}

	@Override
	public int getDockFeeChargeCount(DockFeeChargeDto dDto) throws OAException {
		try {
            StringBuilder sql=new StringBuilder();
            sql.append(" select count(1) from t_pcs_dockfee_charge a")
            .append(" LEFT JOIN t_pcs_dock_fee b on a.dockFeeId=b.id ")
			.append(" LEFT JOIN t_pcs_arrival c on c.id=b.arrivalId ")
			.append(" where 1=1 and a.type=1 ");;
			if(dDto.getFeeType()!=null&&dDto.getFeeType()!=0){
				sql.append(" and a.feeType=").append(dDto.getFeeType());
			}
			if(dDto.getBillType()!=null&&dDto.getBillType()!=0){
				sql.append(" and b.billType=").append(dDto.getBillType());
			}
			if(!Common.isNull(dDto.getClientName()))
				sql.append(" and a.clientName='").append(dDto.getClientName()).append("'");
			if(!Common.isNull(dDto.getTradeType()))
				sql.append(" and b.tradeType=").append(dDto.getTradeType());
			if(dDto.getType()!=null){
				if(dDto.getType()==1){
					sql.append(" and (a.feebillId=0 or ISNULL(a.feebillId))");
				}else if(dDto.getType()==2){
					sql.append(" and !ISNULL(a.feebillId) and a.feebillId!=0 ");
				}
			}
			if(dDto.getFeebillId()!=null){
				sql.append(" and feebillId=").append(dDto.getFeebillId());
			}
			if(dDto.getStartTime()!=null&&dDto.getStartTime()!=-1){
				sql.append(" and UNIX_TIMESTAMP(c.arrivalStartTime) >=").append(dDto.getStartTime());
			}
			if(dDto.getEndTime()!=null&&dDto.getEndTime()!=-1){
				sql.append(" and UNIX_TIMESTAMP(c.arrivalStartTime) <=").append(dDto.getEndTime());
			}
          return (int) getCount(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取码头规费列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取码头规费列表数量失败", e);
	}
	}

	@Override
	public void cleanDockFeeCharge(DockFeeBillDto dDto) throws OAException {
		try {
			String sql="update t_pcs_dockfee_charge set id=id,feebillId=null where  1=1";
					if(dDto.getId()!=null){
						sql+=" and id="+dDto.getId();
					}
					if(dDto.getFeebillId()!=null){
						sql+=" and feebillId="+dDto.getFeebillId();
					}
              executeUpdate(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取抬头列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取抬头列表数量失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getFeeHeadList(DockFeeChargeDto dDto,
			int start, int limit) throws OAException {
		try {
			 StringBuilder sql=new StringBuilder();
			sql.append(" SELECT trim(a.clientName) clientName,SUM(ROUND(a.discountFee,3)) totalFee,b.billType ")
			 .append(" from t_pcs_dockfee_charge a ")
			 .append(" LEFT JOIN t_pcs_dock_fee b ON a.dockFeeId=b.id ")
			 .append(" LEFT JOIN t_pcs_arrival c ON c.id=b.arrivalId ")
			 .append(" WHERE 1=1 and a.type=1 ");
			if(!Common.isNull(dDto.getBillType()))
					sql.append(" and b.billType=").append(dDto.getBillType());
			if(!Common.isNull(dDto.getFeeType()))
					sql.append(" and a.feeType=").append(dDto.getFeeType());
			if(!Common.isNull(dDto.getTradeType()))
					sql.append(" and b.tradeType=").append(dDto.getTradeType());
			if(!Common.isNull(dDto.getFeeHead()))
					sql.append(" and a.clientName='").append(dDto.getFeeHead()).append("'");
			if(dDto.getType()!=null){
				if(dDto.getType()==1)
					sql.append(" and IFNULL(a.feebillId,0)=0 ");
				else if(dDto.getType()==2)
					sql.append(" and IFNULL(a.feebillId,0)!=0 ");
			}
			if(!Common.isNull(dDto.getStartTime())&&dDto.getStartTime()!=-1)
				sql.append(" and UNIX_TIMESTAMP(c.arrivalStartTime)>=").append(dDto.getStartTime());
			if(!Common.isNull(dDto.getEndTime())&&dDto.getEndTime()!=-1)
				sql.append(" and UNIX_TIMESTAMP(c.arrivalStartTime)<=").append(dDto.getEndTime());
			 sql.append(" GROUP BY trim(a.clientName),b.billType ")
			 .append("  ORDER BY CONVERT(a.clientName USING gbk) asc,b.billType asc ");
			if(limit!=0)
		    sql.append(" limit ").append(start).append(" , ").append(limit);
		 return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取抬头列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取抬头列表失败", e);
		}
	}

	@Override
	public int getFeeHeadListCount(DockFeeChargeDto dDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
				sql.append(" select count(1) from (SELECT count(1) ")
				.append(" from t_pcs_dockfee_charge a ")
				.append(" LEFT JOIN t_pcs_dock_fee b ON a.dockFeeId=b.id ")
				.append(" LEFT JOIN t_pcs_arrival c ON c.id=b.arrivalId ")
				 .append(" WHERE 1=1 and a.type=1 ");
				if(!Common.isNull(dDto.getBillType()))
						sql.append(" and b.billType=").append(dDto.getBillType());
				if(!Common.isNull(dDto.getTradeType()))
						sql.append(" and b.tradeType=").append(dDto.getTradeType());
				if(!Common.isNull(dDto.getFeeHead()))
						sql.append(" and a.clientName='").append(dDto.getFeeHead()).append("'");
				if(dDto.getType()!=null){
					if(dDto.getType()==1)
						sql.append(" and IFNULL(a.feebillId,0)=0 ");
					else if(dDto.getType()==2)
						sql.append(" and IFNULL(a.feebillId,0)!=0 ");
				}
				if(!Common.isNull(dDto.getStartTime())&&dDto.getStartTime()!=-1)
					sql.append(" and UNIX_TIMESTAMP(c.arrivalStartTime)>=").append(dDto.getStartTime());
				if(!Common.isNull(dDto.getEndTime())&&dDto.getEndTime()!=-1)
					sql.append(" and UNIX_TIMESTAMP(c.arrivalStartTime)<=").append(dDto.getEndTime());
				sql.append(" GROUP BY a.clientName,b.billType ) a");
			return (int) getCount(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取抬头列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取抬头列表数量失败", e);
		}
	}

	/**
	 * @Title getFeeBillIdByTime
	 * @Descrption:TODO
	 * @param:@param dfcDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月7日上午9:32:43
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getFeeBillIdByTime(DockFeeChargeDto dfcDto)throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT DISTINCT a.feebillId from t_pcs_dockfee_charge a ")
			.append(" LEFT JOIN t_pcs_dock_fee b ON a.dockFeeId=b.id ")
			.append(" LEFT JOIN t_pcs_arrival c ON c.id=b.arrivalId ")
			.append(" LEFT JOIN t_pcs_dockfee_bill d ON d.id=a.feebillId ")
			.append(" WHERE 1=1 and !ISNULL(a.feebillId) and d.status=1 ");
			if(!Common.isNull(dfcDto.getTradeType()))
				sql.append(" and b.tradeType=").append(dfcDto.getTradeType());
			if(!Common.isNull(dfcDto.getStartTime())&&dfcDto.getStartTime()!=-1)
				sql.append(" and UNIX_TIMESTAMP(c.arrivalStartTime)>=").append(dfcDto.getStartTime());
			if(!Common.isNull(dfcDto.getEndTime())&&dfcDto.getEndTime()!=-1)
				sql.append(" and UNIX_TIMESTAMP(c.arrivalStartTime)<=").append(dfcDto.getEndTime());
			return executeQuery(sql.toString());
			}catch (RuntimeException e) {
				LOG.error("dao获取账单id失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取账单id失败", e);
			}
	}

	@Override
	public void checkAndChangeFeeStatus(DockFeeBillDto dDto)
			throws OAException {
		try {
			String sql="update t_pcs_dock_fee a set a.id=a.id,a.status=1 where  1=1 and ifnull((select sum(b.feebillId) from t_pcs_dockfee_charge b where b.dockFeeId=a.id),0)=0 ";
					if(dDto.getId()!=null){
						sql+=" and a.id=(select c.dockFeeId from t_pcs_dockfee_charge c where c.id="+dDto.getId()+")";
					}
              executeUpdate(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取抬头列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取抬头列表数量失败", e);
		}
	}



}
