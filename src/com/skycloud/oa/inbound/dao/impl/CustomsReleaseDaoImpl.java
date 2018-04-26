/**
 * 
 */
package com.skycloud.oa.inbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.CustomsReleaseDao;
import com.skycloud.oa.inbound.dto.CustomsReleaseDto;
import com.skycloud.oa.inbound.model.CustomsRelease;
import com.skycloud.oa.utils.Common;
import com.sun.accessibility.internal.resources.accessibility;

/**
 *
 * @author jiahy
 * @version 2015年12月11日 上午8:56:34
 */
@Repository
public class CustomsReleaseDaoImpl extends BaseDaoImpl implements
		CustomsReleaseDao {
	private static Logger LOG = Logger.getLogger(CustomsReleaseDaoImpl.class);

	@Override
	public List<Map<String, Object>> getCustomsReleaseList(CustomsReleaseDto crDto, int startRecord, int maxresult)throws OAException {
		try {
			String sql =" SELECT DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') inboundTime,e.name shipName,h.refName shipRefName,c.name productName, "
						+" d.code tankName,d.type tankType,a.* ,g.name userName,from_unixtime(a.createTime) createTimeStr ,"
						+" (select j.startWeight from t_pcs_tanklogstore j where j.arrivalId=a.arrivalId and a.tankId=j.tankId limit 0,1 ) startWeight, "
						+" (select j.endWeight from t_pcs_tanklogstore j where j.arrivalId=a.arrivalId and a.tankId=j.tankId limit 0,1 ) endWeight"  
						+" from t_pcs_customs_release a  "
						+" INNER JOIN t_pcs_arrival b ON a.arrivalId=b.id  "
						+" INNER JOIN t_pcs_product c on c.id =a.productId "
						+" INNER JOIN t_pcs_tank d ON d.id=a.tankId "
						+" INNER JOIN t_pcs_ship e on e.id=b.shipId "
						+" INNER JOIN t_pcs_ship_ref h on h.id=b.shipRefId "
						+" LEFT JOIN t_pcs_work f on f.arrivalId=a.arrivalId and f.productId=a.productId and f.orderNum=0 "
						+" LEFT JOIN t_auth_user g on a.userId=g.id "
						+" LEFT JOIN t_his_tank_log i on d.key=i.tank and i.DTime=UNIX_TIMESTAMP(CONCAT(DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d'),' 08:00')) "
						+" where 1=1 and f.status in (7,8,9) and i.tankType!=0 and c.oils=0";
						if(crDto.getId()!=null){
							sql+=" and a.id="+crDto.getId();
						}
						if(crDto.getProductId()!=null){
							sql+=" and a.productId="+crDto.getProductId();
						}
						if(crDto.getType()!=null){
							if(crDto.getType()==1){
							 sql+=" and ((a.unOutBoundCount=a.inboundCount and a.inboundCount!=0) or isnull(a.unOutBoundCount)) ";							
							}else if(crDto.getType()==2){
							 sql+=" and !isnull(a.unOutBoundCount) and !isnull(a.inboundCount) and a.unOutBoundCount>0 and a.unOutBoundCount<a.inboundCount ";
							}else if(crDto.getType()==3){
								 sql+=" and !isnull(a.unOutBoundCount) and a.unOutBoundCount=0 ";
								} 
						}
						if(crDto.getTankId()!=null){
							sql+=" and a.tankId="+crDto.getTankId();
						}
						if(!Common.isNull(crDto.getShipName())){
							sql+=" and (e.name like '%"+crDto.getShipName()+"%' or h.refName like '%"+crDto.getShipName()+"%')";
						}
						if(crDto.getStartTime()!=null&&crDto.getStartTime()!=-1){
							sql+=" and  UNIX_TIMESTAMP(DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d %H:%i:%s'))>="+crDto.getStartTime();
						}
						if(crDto.getEndTime()!=null&&crDto.getEndTime()!=-1){
							sql+=" and  UNIX_TIMESTAMP(DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d %H:%i:%s'))<="+crDto.getEndTime();
						}
						sql+=" ORDER BY b.arrivalStartTime desc,b.id desc ";
						if(maxresult!=0){
							sql+="limit "+startRecord+" , "+maxresult;
						}
				return executeQuery(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取卸货海关放行量列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取卸货海关放行量列表失败", e);
		}
	}

	@Override
	public int getCustomsReleaseCount(CustomsReleaseDto crDto)throws OAException {
		try {
			String sql =" SELECT count(1) "  
					+" from t_pcs_customs_release a  "
					+" LEFT JOIN t_pcs_arrival b ON a.arrivalId=b.id  "
					+" LEFT JOIN t_pcs_product c on c.id =a.productId "
					+" LEFT JOIN t_pcs_tank d ON d.id=a.tankId "
					+" LEFT JOIN t_pcs_ship e on e.id=b.shipId "
					+" LEFT JOIN t_pcs_ship_ref h on h.id=b.shipRefId "
					+" LEFT JOIN t_pcs_work f on f.arrivalId=a.arrivalId and f.productId=a.productId and f.orderNum=0 "
					+" LEFT JOIN t_his_tank_log i on d.key=i.tank and i.DTime=UNIX_TIMESTAMP(CONCAT(DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d'),' 08:00')) "
					+" where 1=1 and f.status in (7,8,9) and i.tankType!=0 and c.oils=0 ";
					if(crDto.getId()!=null){
						sql+=" and a.id="+crDto.getId();
					}
					if(crDto.getProductId()!=null){
						sql+=" and a.productId="+crDto.getProductId();
					}
					if(crDto.getTankId()!=null){
						sql+=" and a.tankId="+crDto.getTankId();
					}
					if(crDto.getType()!=null){
						if(crDto.getType()==1){
						 sql+=" and ((a.unOutBoundCount=a.inboundCount and a.inboundCount!=0) or isnull(a.unOutBoundCount)) ";							
						}else if(crDto.getType()==2){
						 sql+=" and !isnull(a.unOutBoundCount) and !isnull(a.inboundCount) and a.unOutBoundCount>0 and a.unOutBoundCount<a.inboundCount ";
						}else if(crDto.getType()==3){
							 sql+=" and !isnull(a.unOutBoundCount) and a.unOutBoundCount=0 ";
							}
					}
					if(!Common.isNull(crDto.getShipName())){
						sql+=" and (e.name like '%"+crDto.getShipName()+"%' or h.refName like '%"+crDto.getShipName()+"%')";
					}
					if(crDto.getStartTime()!=null&&crDto.getStartTime()!=-1){
						sql+=" and  UNIX_TIMESTAMP(DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d %H:%i:%s'))>="+crDto.getStartTime();
					}
					if(crDto.getEndTime()!=null&&crDto.getEndTime()!=-1){
						sql+=" and  UNIX_TIMESTAMP(DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d %H:%i:%s'))<="+crDto.getEndTime();
					}
				return (int) getCount(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取卸货海关放行量列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取卸货海关放行量列表数量失败", e);
		}
	}

	@Override
	public void updateCustomsRelease(CustomsRelease customsRelease)throws OAException {
		try {
			String sql ="update t_pcs_customs_release set id=id ";
			if(customsRelease.getBeforeInboundTank()!=null){
				sql+=", beforeInboundTank='"+customsRelease.getBeforeInboundTank()+"'";
			}
			if(customsRelease.getAfterInboundTank()!=null){
				sql+=", afterInboundTank='"+customsRelease.getAfterInboundTank()+"'";
			}
			if(customsRelease.getHasCustomsPass()!=null){
				sql+=", hasCustomsPass='"+customsRelease.getHasCustomsPass()+"'";
			}
			if(customsRelease.getInboundCount()!=null){
				sql+=", inboundCount='"+customsRelease.getInboundCount()+"'";
			}
			if(customsRelease.getOutBoundCount()!=null){
				sql+=", outBoundCount='"+customsRelease.getOutBoundCount()+"'";
			}
			if(customsRelease.getUnOutBoundCount()!=null){
				sql+=", unOutBoundCount='"+customsRelease.getUnOutBoundCount()+"'";
			}
			if(customsRelease.getDescription()!=null){
				sql+=", description='"+customsRelease.getDescription()+"'";
			}
	        if(customsRelease.getCreateTime()!=null&&customsRelease.getCreateTime()!=-1){
	        	sql+=", createTime="+customsRelease.getCreateTime();
	        }
	        if(customsRelease.getUserId()!=null){
	        	sql+=", userId="+customsRelease.getUserId();
	        }
			sql+=" where id="+customsRelease.getId();
			
			
				 executeUpdate(sql);
		}catch (RuntimeException e) {
			LOG.error("dao更新卸货海关放行量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新卸货海关放行量失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getCargoMsg(CustomsReleaseDto crDto) throws OAException {
		try {  
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT ROUND(sum(b.goodsTotal)-e.goodsSave,3) cargoPass,a.id cargoId,a.code cargoCode,a.goodsPlan goodsPlan,SUM(ROUND(b.goodsTank,3)) goodsTank,")
			.append(" a.clientId  clientId, d.name clientName from t_pcs_cargo a LEFT JOIN (select c.cargoId,sum(c.goodsTotal - c.goodsInPass) goodsSave from t_pcs_goods c where c.isPredict IS NULL	OR c.isPredict != 1 GROUP BY c.cargoId) e ON e.cargoId=a.id,t_pcs_goods b,t_pcs_client d ")
			.append(" where a.id=b.cargoId  and d.id=a.clientId  and (b.isPredict is null or b.isPredict!=1) and")
			.append("  ISNULL(b.sourceGoodsId) ");
			if(crDto.getArrivalId()!=null)
				sql.append(" and a.arrivalId=").append(crDto.getArrivalId());
			if(crDto.getProductId()!=null)
				sql.append(" and a.productId=").append(crDto.getProductId());
			if(crDto.getTaxType()!=null&&crDto.getTaxType()==1)
				sql.append(" and a.taxType!=3");
			else if(crDto.getTaxType()!=null&&crDto.getTaxType()==2)
				sql.append(" and a.taxType=3");
			sql.append(" GROUP BY a.id");
				return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取卸货海关放行量货批列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取卸货海关放行量货批列表失败", e);
		}
	}

	/**
	 * @Title getTotalNum
	 * @Descrption:TODO
	 * @param:@param crDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年7月11日上午8:24:26
	 * @throws
	 */
	@Override
	public Map<String, Object> getTotalNum(CustomsReleaseDto crDto) throws OAException {
		try {  
			String sql =" SELECT  ROUND(SUM((case when IFNULL(a.inboundCount,0)=0 then ((select (j.endWeight-j.startWeight) from t_pcs_tanklogstore j where j.arrivalId=a.arrivalId and a.tankId=j.tankId limit 0,1 )) else a.inboundCount end )),3) totalInboundCount,"
					+ "ROUND(SUM(a.outBoundCount),3) totalOutBoundCount,ROUND(SUM((case when IFNULL(a.inboundCount,0)=0 then ((select (j.endWeight-j.startWeight) from t_pcs_tanklogstore j where j.arrivalId=a.arrivalId and a.tankId=j.tankId limit 0,1 )) else a.unOutBoundCount end )),3) totalUnOutBoundCount "  
					+" from t_pcs_customs_release a  "
					+" INNER JOIN t_pcs_arrival b ON a.arrivalId=b.id  "
					+" INNER JOIN t_pcs_product c on c.id =a.productId "
					+" INNER JOIN t_pcs_tank d ON d.id=a.tankId "
					+" INNER JOIN t_pcs_ship e on e.id=b.shipId "
					+" INNER JOIN t_pcs_ship_ref h on h.id=b.shipRefId "
					+" LEFT JOIN t_pcs_work f on f.arrivalId=a.arrivalId and f.productId=a.productId and f.orderNum=0 "
					+" LEFT JOIN t_auth_user g on a.userId=g.id "
					+" LEFT JOIN t_his_tank_log i on d.key=i.tank and i.DTime=UNIX_TIMESTAMP(CONCAT(DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d'),' 08:00')) "
					+" where 1=1 and f.status in (7,8,9) and i.tankType!=0 and c.oils=0";
					if(crDto.getId()!=null){
						sql+=" and a.id="+crDto.getId();
					}
					if(crDto.getProductId()!=null){
						sql+=" and a.productId="+crDto.getProductId();
					}
					if(crDto.getType()!=null){
						if(crDto.getType()==1){
						 sql+=" and (a.unOutBoundCount!=0 or isnull(a.unOutBoundCount)) ";							
						}else if(crDto.getType()==2){
						 sql+=" and a.unOutBoundCount=0 ";
						}
					}
					if(crDto.getTankId()!=null){
						sql+=" and a.tankId="+crDto.getTankId();
					}
					if(!Common.isNull(crDto.getShipName())){
						sql+=" and (e.name like '%"+crDto.getShipName()+"%' or h.refName like '%"+crDto.getShipName()+"%')";
					}
					if(crDto.getStartTime()!=null&&crDto.getStartTime()!=-1){
						sql+=" and  UNIX_TIMESTAMP(DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d %H:%i:%s'))>="+crDto.getStartTime();
					}
					if(crDto.getEndTime()!=null&&crDto.getEndTime()!=-1){
						sql+=" and  UNIX_TIMESTAMP(DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d %H:%i:%s'))<="+crDto.getEndTime();
					}
					sql+=" ORDER BY b.arrivalStartTime desc,b.id desc ";				
				return executeQueryOne(sql);
		}catch (RuntimeException e) {
			LOG.error("dao获取卸货海关放行统计信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取卸货海关放行量统计信息失败", e);
		}
	}

	/**
	 * @Title getHasPass
	 * @Descrption:TODO
	 * @param:@param valueOf
	 * @param:@param i
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月14日上午10:38:22
	 * @throws
	 */
	@Override
	public Map<String, Object> getHasPass(Integer productId,Integer id, int i)
			throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT SUM(ROUND(a.hasCustomsPass,3)) num from t_pcs_customs_release a ")
			.append(" LEFT JOIN t_pcs_arrival b ON b.id=a.arrivalId ")
			.append(" LEFT JOIN t_pcs_tank c ON a.tankId=c.id ")
			.append(" LEFT JOIN t_his_tank_log d ON c.`key`=d.tank AND  d.DTime=UNIX_TIMESTAMP(CONCAT(DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d'),' 08:00')) ")
			.append(" LEFT JOIN t_pcs_product e ON e.id=a.productId ")
			.append(" WHERE e.oils=0 and  a.arrivalId=").append(id)
			.append(" and e.id=").append(productId);
			if(i==1)
				sql.append(" and d.tankType=1");
			else 
				sql.append(" and (d.tankType=2 or d.tankType=3)");
				
			return executeQueryOne(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取海关已放行量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取海关已放行量失败", e);
		}
	}

}
