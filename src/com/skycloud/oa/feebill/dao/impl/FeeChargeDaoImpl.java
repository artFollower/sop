package com.skycloud.oa.feebill.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.FeeChargeDao;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.model.FeeCharge;
import com.skycloud.oa.utils.Common;
/**
 *费用
 * @author jiahy
 * @version 2015年6月14日 下午1:59:52
 */
@Component
public class FeeChargeDaoImpl extends BaseDaoImpl implements FeeChargeDao {
	private static Logger LOG = Logger.getLogger(FeeChargeDaoImpl.class);

	@Override
	public List<Map<String, Object>> getFeeChargeList(FeeChargeDto feeChargeDto, int start, int limit) throws OAException {
		try {
			String sql="select a.*,b.name feeHeadName,c.name clientName,d.name productName, from_unixtime(a.createTime) createTimeStr,e.code feebillCode,f.code cargoCode, "
					+ " (SELECT CONCAT(h.`name`,'/',i.refName,'/',DATE_FORMAT(j.arrivalStartTime,'%Y-%m-%d')) from t_pcs_cargo k,t_pcs_arrival j,t_pcs_ship h,t_pcs_ship_ref i "
					+ " WHERE a.cargoId is not null and k.id=a.cargoId and j.id=k.arrivalId and h.id=j.shipId and i.id=j.shipRefId )  inboundMsg,"
					+ " g.code initialCode,h.code exceedCode,i.code otherCode,j.code ladingCode "
					+ " from t_pcs_fee_charge a "
					+ " left join t_pcs_client b on b.id=a.feeHead "
					+ " left join t_pcs_client c on c.id=a.clientId "
					+ " left join t_pcs_product d on d.id=a.productId "
					+ " left join t_pcs_initial_fee g on a.initialId=g.id "
					+ " left join t_pcs_exceed_fee h on a.exceedId=h.id "
					+ " left join t_pcs_other_fee i on  a.initialId=i.id "
					+ " left join t_pcs_lading j on j.id=h.ladingId "
					+ " left join t_pcs_fee_bill e on e.id=a.feebillId "
					+ " left join t_pcs_cargo f on a.cargoId=f.id "
					+ " where 1=1 ";
			if(!Common.isNull(feeChargeDto.getType())){
				sql+=" and a.type="+feeChargeDto.getType();
			}
			if(feeChargeDto.getInitialId()!=null){
				sql+=" and a.initialId="+feeChargeDto.getInitialId();
			} 
			if(feeChargeDto.getExceedId()!=null){
				sql+=" and a.exceedId="+feeChargeDto.getExceedId();
			}
			if(feeChargeDto.getFeeHead()!=null){
				sql+=" and a.feeHead="+feeChargeDto.getFeeHead();
			}
			if(feeChargeDto.getClientId()!=null){
				sql+=" and a.clientId="+feeChargeDto.getClientId();
			}
			 if(feeChargeDto.getFeebillId()!=null){
			    	sql+=" and a.feebillId="+feeChargeDto.getFeebillId();
			    }
			 if(feeChargeDto.getBillType()!=null){
             	if(feeChargeDto.getBillType()==1){
                 	sql+=" and a.feebillId is null";
                 }
                 if(feeChargeDto.getBillType()==2){
                 	sql+=" and a.feebillId is not null";
                 }
             }
             if(feeChargeDto.getStartTime()!=null&&feeChargeDto.getStartTime()!=-1){
               	sql+=" and a.createTime>="+feeChargeDto.getStartTime();
               }
               if(feeChargeDto.getEndTime()!=null&&feeChargeDto.getEndTime()!=-1){
               	sql+=" and a.createTime<="+feeChargeDto.getEndTime();
               }
			sql+=" order by a.createTime desc ";
			   if(limit!=0)
			sql+= "limit "+start+","+limit;
			return executeQuery(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取费用列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取费用列表失败", e);
		}
	}
	@Override
	public int getFeeChargeCount(FeeChargeDto feeChargeDto) throws OAException {
		String sql="select count(*) from t_pcs_fee_charge a where 1=1";
		if(!Common.isNull(feeChargeDto.getType())){
			sql+=" and a.type="+feeChargeDto.getType();
		}
		if(feeChargeDto.getInitialId()!=null){
			sql+=" and a.initialId="+feeChargeDto.getInitialId();
		} 
		if(feeChargeDto.getExceedId()!=null){
			sql+=" and a.exceedId="+feeChargeDto.getExceedId();
		}
		if(feeChargeDto.getFeeHead()!=null){
			sql+=" and a.feeHead="+feeChargeDto.getFeeHead();
		}
		if(feeChargeDto.getClientId()!=null){
			sql+=" and a.clientId="+feeChargeDto.getClientId();
		}
		if(feeChargeDto.getFeebillId()!=null){
	    	sql+=" and a.feebillId="+feeChargeDto.getFeebillId();
	    }
		 if(feeChargeDto.getBillType()!=null){
         	if(feeChargeDto.getBillType()==1){
             	sql+=" and a.feebillId is null";
             }
             if(feeChargeDto.getBillType()==2){
             	sql+=" and a.feebillId is not null";
             }
         }
         if(feeChargeDto.getStartTime()!=null&&feeChargeDto.getStartTime()!=-1){
           	sql+=" and a.createTime>="+feeChargeDto.getStartTime();
           }
           if(feeChargeDto.getEndTime()!=null&&feeChargeDto.getEndTime()!=-1){
           	sql+=" and a.createTime<="+feeChargeDto.getEndTime();
           }
		return (int) getCount(sql);
	}
	@Override
	public int addFeeCharge(FeeCharge feeCharge) throws OAException {
		try {
			return (Integer) save(feeCharge);
	}catch (RuntimeException e) {
		LOG.error("dao添加费用失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加费用失败", e);
	}
	}

	@Override
	public void updateFeeCharge(FeeCharge feeCharge) throws OAException {
			try {
				String sql="update t_pcs_fee_charge set id=id ";
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
				if(feeCharge.getFeeHead()!=null){
					sql+=",feeHead="+feeCharge.getFeeHead();
				}
				if(feeCharge.getExceedId()!=null){
					sql+=",exceedId="+feeCharge.getExceedId();
				}
				if(feeCharge.getInitialId()!=null){
					sql+=",initialId="+feeCharge.getInitialId();
				}
				if(feeCharge.getProductId()!=null){
					sql+=",productId="+feeCharge.getProductId();
				}
				if(feeCharge.getFeebillId()!=null){
					sql+=", feebillId="+feeCharge.getFeebillId();
				}
				if(feeCharge.getClientId()!=null){
					sql+=",clientId="+feeCharge.getClientId();
				}
				if(feeCharge.getDiscountFee()!=null){
					sql+=",discountFee="+feeCharge.getDiscountFee();
				}
				if(feeCharge.getDescription()!=null&&!Common.empty(feeCharge.getDescription())){
					sql+=",description='"+feeCharge.getDescription()+"'";
				}
				if(feeCharge.getIsCopy()!=null){
				    sql+=",isCopy="+feeCharge.getIsCopy();	
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
				
				if(!Common.isNull(feeCharge.getFeebillId())&&!Common.isNull(feeCharge.getId())){
					executeProcedureOne("call proc_storageFeeUpdateStatusByfeeChargeId("+feeCharge.getId()+")");
				}
				executeUpdate(sql);
		}catch (RuntimeException e) {
			LOG.error("dao更新费用失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新费用失败", e);
		}
	}

	@Override
	public int deleteFeeCharge(FeeChargeDto feeChargeDto) throws OAException {
		try {
			String sql =" delete from t_pcs_fee_charge where 1=1 ";
					if(feeChargeDto.getId()!=null){
						sql+= " and id="+feeChargeDto.getId();
					}
			        if(feeChargeDto.getInitialId()!=null){
			        	sql+=" and feeType!=6 and initialId="+feeChargeDto.getInitialId();
			        }
			        if(feeChargeDto.getOtherFeeId()!=null){
			        	sql+=" and feeType=6 and initialId="+feeChargeDto.getOtherFeeId();
			        }
			        if(feeChargeDto.getExceedId()!=null){
			        	sql+="and exceedId="+feeChargeDto.getExceedId();
			        }
             return  executeUpdate(sql);			
		}catch (RuntimeException e) {
			LOG.error("dao删除费用失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除费用失败", e);
		}
	}
	@Override
	public List<Map<String, Object>> getFeeHeadList(FeeChargeDto feeChargeDto,int startRecord, int maxresult) throws OAException {
       try {
    	   StringBuilder sql=new StringBuilder();
    	    sql.append("SELECT a.feeHead feeHead,b.name feeHeadName, a.unFinal unFinal, a.totalFee allTotalFee " )
    	    		.append(" from (SELECT DISTINCT(a.feeHead) feeHead ,COUNT(a.id) unFinal ,sum(round(a.totalFee,2)) totalFee  ")
    	    				.append(" from t_pcs_fee_charge a left join t_pcs_client b on a.feeHead=b.id where 1=1 and a.type=1 and a.feeHead is not null");
                      if(feeChargeDto.getClientId()!=null){
                    	  sql.append(" and a.clientId=").append(feeChargeDto.getClientId());
                      }
                      if(feeChargeDto.getFeeHead()!=null){
                    	  sql.append(" and a.feeHead=").append(feeChargeDto.getFeeHead());
                      }
                      if(feeChargeDto.getBillType()!=null){
                      	if(feeChargeDto.getBillType()==1){
                      		sql.append(" and a.feebillId is null");
                          }
                          if(feeChargeDto.getBillType()==2){
                        	  sql.append(" and a.feebillId is not null");
                          }
                      }
                      if(feeChargeDto.getStartTime()!=null&&feeChargeDto.getStartTime()!=-1){
                    	  sql.append(" and a.createTime>=").append(feeChargeDto.getStartTime());
                        }
                        if(feeChargeDto.getEndTime()!=null&&feeChargeDto.getEndTime()!=-1){
                        	sql.append(" and a.createTime<=").append(feeChargeDto.getEndTime());
                        }
                        sql.append(" GROUP BY a.feeHead  order by  CONVERT(b.name USING gbk) asc limit ").append(startRecord).append(",").append(maxresult)
                      .append(") as a ,t_pcs_client b  where  a.feeHead=b.id ");
                      if(feeChargeDto.getBillType()!=null&&feeChargeDto.getBillType()==1){
                    	  sql.append(" AND a.unFinal!=0");
                          }
			return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao 获取抬头列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取抬头列表失败", e);
		}
	}
	@Override
	public int getFeeHeadCount(FeeChargeDto feeChargeDto) throws OAException {
		try {
			 String sql="SELECT count(a.feeHead) " 
                     +" from (SELECT DISTINCT(feeHead) feeHead ,COUNT(id) unFinal ,sum(round(totalFee,2)) totalFee  "
                     +" from t_pcs_fee_charge where 1=1 and type=1 and feeHead is not null";
                     if(feeChargeDto.getClientId()!=null){
                     	sql+=" and clientId="+feeChargeDto.getClientId();
                     }
                     if(feeChargeDto.getFeeHead()!=null){
                     	sql+=" and feeHead="+feeChargeDto.getFeeHead();
                     }
                     if(feeChargeDto.getBillType()!=null){
                     	if(feeChargeDto.getBillType()==1){
                         	sql+=" and feebillId is null";
                         }
                         if(feeChargeDto.getBillType()==2){
                         	sql+=" and feebillId is not null";
                         }
                     }
                     if(feeChargeDto.getStartTime()!=null&&feeChargeDto.getStartTime()!=-1){
                       	sql+=" and createTime>="+feeChargeDto.getStartTime();
                       }
                       if(feeChargeDto.getEndTime()!=null&&feeChargeDto.getEndTime()!=-1){
                       	sql+=" and createTime<="+feeChargeDto.getEndTime();
                       }
                     sql+=" GROUP BY feeHead ";
                     sql+= ") as a ,t_pcs_client b  where  a.feeHead=b.id ";
                     if(feeChargeDto.getBillType()!=null&&feeChargeDto.getBillType()==1){
                         sql+=" AND a.unFinal!=0";
                         }
			
			return (int) getCount(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取抬头列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取抬头列表数量失败", e);
		}
	}
	@Override
	public void cleanFeeCharge(FeeChargeDto feeChargeDto) throws OAException {
		try {
			String sql="update t_pcs_fee_charge set id=id,feebillId=null where  1=1";
					if(feeChargeDto.getId()!=null){
						sql+=" and id="+feeChargeDto.getId();
					}
					if(feeChargeDto.getFeebillId()!=null){
						sql+=" and feebillId="+feeChargeDto.getFeebillId();
					}
              executeUpdate(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取抬头列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取抬头列表数量失败", e);
		}
	}
	@Override
	public void backFeeChargeFeeHead(FeeChargeDto feeChargeDto) throws OAException {
		try {
			if(feeChargeDto.getFeebillId()!=null){
			String sql="update t_pcs_fee_charge set id=id,feeHead=clientId where  1=1 and feebillId="+feeChargeDto.getFeebillId();
                executeUpdate(sql);
			}
		}catch (RuntimeException e) {
			LOG.error("dao获取抬头列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取抬头列表数量失败", e);
		}
	}
	
	/**
	 * 校验是否货批已经生成首期费，保安费
	 *@author jiahy
	 * @param feeChargeDto
	 */
	@Override
	public int isMakeInitialFee(FeeChargeDto feeChargeDto) throws OAException {
		try {
			if(feeChargeDto.getCargoId()!=null){
				String sql="select count(1) from t_pcs_fee_charge where initialId<>0 and feeType=1 and cargoId="+feeChargeDto.getCargoId();
			return (int) getCount(sql);	
			}
		}catch (RuntimeException e) {
			LOG.error("dao校验是否货批已经生成首期费，保安费失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao校验是否货批已经生成首期费，保安费失败", e);
		}
		return 0;
	}
	/**
	 * 
	 * @Title insertfeeChargeCargoLading
	 * @Descrption:TODO
	 * @param:@param id
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月8日上午9:29:29
	 * @throws
	 */
	@Override
	public void insertfeeChargeCargoLading(int id) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("call proc_chargeCargoLadingInsert(").append(id).append(")");
			executeProcedure(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao添加费用项关系表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加费用项关系表失败", e);
		}
	}
	/**
	 * @Title getFeeChargeList
	 * @Descrption:TODO
	 * @param:@param feeChargeDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年8月4日上午9:00:24
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getFeeChargeList(FeeChargeDto feeChargeDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" select a.id ,a.initialId,a.exceedId,a.feebillId,a.feeType ,a.unitFee,a.feeCount,a.totalFee,a.feeHead,b.name feeHeadName  ")
			.append("from t_pcs_fee_charge a left join t_pcs_client b on a.feeHead=b.id  where 1=1 ");
			if(!Common.isNull(feeChargeDto.getInitialId())){
				sql.append(" AND a.initialId=").append(feeChargeDto.getInitialId());
			}
			if(!Common.isNull(feeChargeDto.getExceedId())){
				sql.append(" AND a.exceedId=").append(feeChargeDto.getExceedId());
			}
			if(!Common.isNull(feeChargeDto.getFeebillId())){
				sql.append(" AND a.feebillId=").append(feeChargeDto.getFeebillId());
			}
			return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取费用项列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取费用项列表失败", e);
		}
	}
	/**
	 * @Title deleteCargoLadingOfFeeCharge
	 * @Descrption:TODO
	 * @param:@param feeChargeDto
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年8月16日下午8:56:49
	 * @throws
	 */
	@Override
	public void deleteCargoLadingOfFeeCharge(FeeChargeDto feeChargeDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("DELETE FROM t_pcs_charge_cargo_lading  where feeChargeId in ( SELECT a.id from t_pcs_fee_charge a where 1=1 ");
			if(!Common.isNull(feeChargeDto.getInitialId())){
				sql.append(" AND a.feeType!=6 AND a.initialId=").append(feeChargeDto.getInitialId());
			}else if(!Common.isNull(feeChargeDto.getOtherFeeId())){
				sql.append(" AND a.feeType=6 AND a.initialId=").append(feeChargeDto.getOtherFeeId());
			}else if(!Common.isNull(feeChargeDto.getExceedId())){
				sql.append(" AND a.exceedId=").append(feeChargeDto.getExceedId());
			}else{
				sql.append(" AND a.id=-1");
			}
			sql.append(" );");
			 execute(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取费用项列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取费用项列表失败", e);
		}
	}
	/**
	 * @Title updateInitialFeeAndExceedFeeStatus
	 * @Descrption:TODO
	 * @param:@param feeChargeId
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年8月22日上午10:34:00
	 * @throws
	 */
	@Override
	public void updateInitialFeeAndExceedFeeStatus(Integer feeChargeId) throws OAException {
		try{
			if(feeChargeId!=null)
			executeProcedureOne("call proc_storageFeeUpdateStatusByfeeChargeId("+feeChargeId+")");
	}catch (RuntimeException e) {
		LOG.error("dao获取费用项列表失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取费用项列表失败", e);
	}
	}

}
