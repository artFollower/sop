package com.skycloud.oa.inbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.DockFeeDao;
import com.skycloud.oa.inbound.dao.InitialFeeDao;
import com.skycloud.oa.inbound.dto.DockFeeDto;
import com.skycloud.oa.inbound.dto.InitialFeeDto;
import com.skycloud.oa.inbound.model.DockFee;
import com.skycloud.oa.inbound.model.InitialFee;
import com.skycloud.oa.utils.Common;
/**
 *码头规费dao
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午2:23:48
 */
@Component
public class DockFeeDaoImpl extends BaseDaoImpl implements DockFeeDao {
	private static Logger LOG = Logger.getLogger(DockFeeDaoImpl.class);

	@Override
	public List<Map<String, Object>> getDockFeeList(DockFeeDto dFeeDto, int startRecord, int maxresult)
			throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT a.*,FROM_UNIXTIME(a.createTime) createTimeStr,FROM_UNIXTIME(a.accountTime) accountTimeStr,f.name createUserName, ")
			.append(" CONCAT(c.name,'/',d.refName) boundMsg,DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') arrivalTime, ")
			.append(" (case when b.type=1 then '入港' when b.type=2 then '出港' when b.type=3 then '入港通过' when b.type=5 then '出港通过' END) arrivalType, ")
//			.append(" (select round(sum(w.totalFee),2) from t_pcs_dockfee_charge w where w.dockfeeId=a.id ) totalFee,") 
//			.append(" (select round(sum(w.discountFee),2) from t_pcs_dockfee_charge w where w.dockfeeId=a.id ) discountFee,")
			.append(" b.id,e.name shipRefName ")
			.append(" from t_pcs_dock_fee a  ")
			.append(" LEFT JOIN t_pcs_arrival b ON a.arrivalId=b.id ")
			.append(" LEFT JOIN t_pcs_ship c on c.id=b.shipId ")
			.append(" LEFT JOIN t_pcs_ship_ref d on d.id=b.shipRefId ")
			.append(" LEFT JOIN t_pcs_ship_agent e on e.id=b.shipAgentId ")
			.append(" LEFT JOIN t_auth_user f on f.id=a.createUserId ")
			.append(" where 1=1");
			if(!Common.isNull(dFeeDto.getId()))
				sql.append(" and a.id=").append(dFeeDto.getId());
			
			if(!Common.isNull(dFeeDto.getArrivalType())&&dFeeDto.getArrivalType()==1)
				sql.append(" and (b.type=1 or b.type=3) ");
			else if(!Common.isNull(dFeeDto.getArrivalType())&&dFeeDto.getArrivalType()==2)
				sql.append(" and (b.type=2 or b.type=5) ");
			
			if(!Common.isNull(dFeeDto.getCode()))
				sql.append(" and a.code like '%").append(dFeeDto.getCode()).append("%'");
			
			if(!Common.isNull(dFeeDto.getClientName()))
				sql.append(" and a.clientName like '%").append(dFeeDto.getClientName()).append("%'");
			
			if(!Common.empty(dFeeDto.getStatus())&&dFeeDto.getStatus()!=-1)
				sql.append(" and a.status=").append(dFeeDto.getStatus());
			
			if(!Common.isNull(dFeeDto.getBillType())&&dFeeDto.getBillType()!=0)
				sql.append(" and a.billType=").append(dFeeDto.getBillType());
			
			if(!Common.isNull(dFeeDto.getStartTime())&&dFeeDto.getStartTime()!=-1)
				sql.append(" and UNIX_TIMESTAMP(b.arrivalStartTime) >=").append(dFeeDto.getStartTime());
			
			if(!Common.isNull(dFeeDto.getEndTime())&&dFeeDto.getEndTime()!=-1)
				sql.append(" and UNIX_TIMESTAMP(b.arrivalStartTime)<=").append(dFeeDto.getEndTime());
			
			if(!Common.isNull(dFeeDto.getShipName()))
				sql.append(" and ( c.name like '%").append(dFeeDto.getShipName()).append("%' or d.refName like '%").append(dFeeDto.getShipName()).append("%') ");
			
			sql.append(" order by b.arrivalStartTime desc");
			if(maxresult!=0)
				sql.append(" limit ").append(startRecord).append(" , ").append(maxresult);
			return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取码头规费结算单列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取码头规费结算单列表失败", e);
		}
	}

	@Override
	public int getDockFeeCount(DockFeeDto dFeeDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT count(1) from t_pcs_dock_fee a  ")
			.append(" LEFT JOIN t_pcs_arrival b ON a.arrivalId=b.id ")
			.append(" LEFT JOIN t_pcs_ship c on c.id=b.shipId ")
			.append(" LEFT JOIN t_pcs_ship_ref d on d.id=b.shipRefId ")
			.append(" where 1=1 ");
			if(!Common.isNull(dFeeDto.getId()))
				sql.append(" and a.id=").append(dFeeDto.getId());
			
			if(!Common.isNull(dFeeDto.getArrivalType())&&dFeeDto.getArrivalType()==1)
				sql.append(" and (b.type=1 or b.type=3) ");
			else if(!Common.isNull(dFeeDto.getArrivalType())&&dFeeDto.getArrivalType()==2)
				sql.append(" and (b.type=2 or b.type=5) ");
			
			if(!Common.isNull(dFeeDto.getCode()))
				sql.append(" and a.code like '%").append(dFeeDto.getCode()).append("%'");
			
			if(!Common.isNull(dFeeDto.getClientName()))
				sql.append(" and a.clientName like '%").append(dFeeDto.getClientName()).append("%'");
			
			if(!Common.empty(dFeeDto.getStatus())&&dFeeDto.getStatus()!=-1)
				sql.append(" and a.status=").append(dFeeDto.getStatus());
			
			if(!Common.isNull(dFeeDto.getBillType())&&dFeeDto.getBillType()!=0)
				sql.append(" and a.billType=").append(dFeeDto.getBillType());
			
			if(!Common.isNull(dFeeDto.getStartTime())&&dFeeDto.getStartTime()!=-1)
				sql.append(" and UNIX_TIMESTAMP(b.arrivalStartTime) >=").append(dFeeDto.getStartTime());
			
			if(!Common.isNull(dFeeDto.getEndTime())&&dFeeDto.getEndTime()!=-1)
				sql.append(" and UNIX_TIMESTAMP(b.arrivalStartTime)<=").append(dFeeDto.getEndTime());
			
			if(!Common.isNull(dFeeDto.getShipName()))
				sql.append(" and ( c.name like '%").append(dFeeDto.getShipName()).append("%' or d.refName like '%").append(dFeeDto.getShipName()).append("%') ");
			
		 return (int) getCount(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取码头规费结算单列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取码头规费结算单列表数量失败", e);
		}
	}

	@Override
	public void updateDockFee(DockFee dockFee) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" update t_pcs_dock_fee  set id=id");
			if(dockFee.getAccountType()!=null){
				sql.append(",accountType=").append(dockFee.getAccountType());
			}
			if(dockFee.getArrivalId()!=null){
				sql.append(",arrivalId=").append(dockFee.getArrivalId());
			}
			if(dockFee.getBillCode()!=null){
				sql.append(",billCode='").append(dockFee.getBillCode()).append("'");
			}
			if(dockFee.getBillType()!=null){
				sql.append(",billType=").append(dockFee.getBillType());
			}
			if(dockFee.getClientName()!=null){
				sql.append(",clientName='").append(dockFee.getClientName()).append("'");
			}
			if(dockFee.getCode()!=null){
				sql.append(",code='").append(dockFee.getCode()).append("'");
			}
			if(dockFee.getContractName()!=null){
				sql.append(",contractName='").append(dockFee.getContractName()).append("'");
			}
			if(dockFee.getTradeType()!=null){
				sql.append(",tradeType=").append(dockFee.getTradeType());
			}
			if(dockFee.getCreateTime()!=null){
				sql.append(",createTime=").append(dockFee.getCreateTime());
			}
			if(dockFee.getCreateUserId()!=null){
				sql.append(",createUserId=").append(dockFee.getCreateUserId());
			}
			if(dockFee.getOverTime()!=null){
				sql.append(",overTime='").append(dockFee.getOverTime()).append("'");
			}
			if(dockFee.getProductName()!=null){
				sql.append(",productName='").append(dockFee.getProductName()).append("'");
			}
			if(dockFee.getStatus()!=null){
				sql.append(",status=").append(dockFee.getStatus());
			}
			if(dockFee.getWaterWeigh()!=null){
				sql.append(",waterWeigh='").append(dockFee.getWaterWeigh()).append("'");
			}
			sql.append(" where id=").append(dockFee.getId());
			executeUpdate(sql.toString());		
		}catch (RuntimeException e) {
			LOG.error("dao添加或更新码头规费结算单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加或更新码头规费结算单失败", e);
		}
	}

	@Override
	public int addDockFee(DockFee dockFee) throws OAException {
		try {
			return Integer.valueOf(save(dockFee).toString());
		}catch (RuntimeException e) {
			LOG.error("dao添加或更新码头规费结算单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加或更新码头规费结算单失败", e);
		}
	}

	@Override
	public void deleteDockFee(DockFeeDto dFeeDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" delete from t_pcs_dock_fee where id in (").append(dFeeDto.getId()).append(")");
			execute(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao删除码头规费结算单失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除码头规费结算单失败", e);
		}
	}

	@Override
	public Map<String,Object> getCodeNum(DockFeeDto dFeeDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT CONCAT('',right(concat('0000000',IFNULL(MAX(code),0)+1),7))  codeNum   from t_pcs_dock_fee ");
			return executeQueryOne(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表失败", e);
		}
	}

	/**
	 *获取出入港包含通过单位不包含转输的船信
	 */
	@Override
	public List<Map<String, Object>> getArrivalList(DockFeeDto dFeeDto, int startRecord, int maxresult)
			throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
				sql.append(" SELECT c.name shipName,b.refName ,DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') arrivalTime,")
				.append(" (case when a.type=1 then '入港' when a.type=2 then '出港' when a.type=3 then '入港通过' when a.type=5 then '出港通过' END) arrivalType,")
				.append(" a.id arrivalId,d.name shipAgentName,c.netTons,")
				.append(" ( case when a.type =1 or a.type=3 then (SELECT GROUP_CONCAT(DISTINCT f.name) from t_pcs_work e,t_pcs_product f where e.arrivalId=a.id and f.id =e.productId )")
				.append(" else g.name END) productName, ")
				.append(" (CASE WHEN a.type=1 OR a.type=3 THEN (SELECT h.taxType from t_pcs_cargo h where h.arrivalId=a.id LIMIT 0,1) ELSE  ( ")
				.append(" SELECT i.tradeType from t_pcs_arrival_plan i where i.arrivalId=a.id LIMIT 0,1 ")
				.append(" ) END) shipType ");
				if(maxresult==0)
				{
					sql.append(",(SELECT ROUND((UNIX_TIMESTAMP(FROM_UNIXTIME(w.leaveTime,'%Y-%m-%d'))-UNIX_TIMESTAMP(FROM_UNIXTIME(w.arrivalTime,'%Y-%m-%d')))/86400+1 ,0) ")
							.append(" from t_pcs_work w where w.arrivalId=a.id limit 0,1 ) stayDay");
				}
				sql.append(" from t_pcs_arrival a ")
				.append(" LEFT JOIN t_pcs_ship_ref b on a.shipRefId=b.id")
				.append(" LEFT JOIN t_pcs_ship c on a.shipId=c.id")
				.append(" LEFT JOIN t_pcs_product g on g.id=a.productId")
				.append(" LEFT JOIN t_pcs_ship_agent d on a.shipAgentId=d.id")
				.append(" where a.type<>4 and a.type<6 and b.refName!='转输' and (( a.status>4 and a.status<10) or ( a.status>51 and a.status<55))");
				if(!Common.isNull(dFeeDto.getArrivalId())){
					sql.append(" and a.id=").append(dFeeDto.getArrivalId());
				}
				if(!Common.isNull(dFeeDto.getShipName())){
					sql.append(" and ( c.name like '%").append(dFeeDto.getShipName()).append("%' or b.refName like '%")
					.append(dFeeDto.getShipName()).append("%') ");
				}
				if(!Common.isNull(dFeeDto.getClientName())){
					sql.append(" and d.name like '%").append(dFeeDto.getClientName()).append("%'");
				}
				if(!Common.isNull(dFeeDto.getStartTime())&&dFeeDto.getStartTime()!=-1){
					sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) >=").append(dFeeDto.getStartTime());
				}
				
				if(!Common.isNull(dFeeDto.getEndTime())&&dFeeDto.getEndTime()!=-1){
					sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) <=").append(dFeeDto.getEndTime());
				}
				sql.append(" and ( select count(1) from t_pcs_dock_fee i where i.arrivalId=a.id )=0 ")
				.append(" ORDER BY a.arrivalStartTime DESC");
				if(maxresult!=0){
			    sql.append(" limit ").append(startRecord).append(" , ").append(maxresult);
				}
			return executeQuery(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表失败", e);
		}
	}

	@Override
	public int getArrivalListCount(DockFeeDto dFeeDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
				sql.append(" SELECT COUNT(1) from t_pcs_arrival a ")
				.append(" LEFT JOIN t_pcs_ship_ref b on a.shipRefId=b.id ")
				.append(" LEFT JOIN t_pcs_ship c on a.shipId=c.id ")
				.append(" LEFT JOIN t_pcs_product g on g.id=a.productId ")
				.append(" LEFT JOIN t_pcs_ship_agent d on a.shipAgentId=d.id ")
				.append(" where a.type<>4 and a.type<6 and b.refName!='转输' ")
				.append(" and (( a.status>4 and a.status<10) or ( a.status>51 and a.status<55)) ");

				if(!Common.isNull(dFeeDto.getShipName())){
					sql.append(" and ( c.name like '%").append(dFeeDto.getShipName()).append("%' or b.refName like '%")
					.append(dFeeDto.getShipName()).append("%') ");
				}
				if(!Common.isNull(dFeeDto.getArrivalId())){
					sql.append(" and a.id=").append(dFeeDto.getArrivalId());
				}
				if(!Common.isNull(dFeeDto.getClientName())){
					sql.append(" and d.name like '%").append(dFeeDto.getClientName()).append("%'");
				}
				if(!Common.isNull(dFeeDto.getStartTime())&&dFeeDto.getStartTime()!=-1){
					sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) >=").append(dFeeDto.getStartTime());
				}
				
				if(!Common.isNull(dFeeDto.getEndTime())&&dFeeDto.getEndTime()!=-1){
					sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) <=").append(dFeeDto.getEndTime());
				}
				sql.append(" and ( select count(1) from t_pcs_dock_fee i where i.arrivalId=a.id )=0 ");
			return (int) getCount(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表失败", e);
		}
	}

	@Override
	public Map<String, Object> getDockFeeMsg(DockFeeDto dFeeDto) throws OAException {
		try {
			if(dFeeDto.getId()!=null){
			StringBuilder sql=new StringBuilder();
					sql.append(" SELECT a.*,FROM_UNIXTIME(a.createTime) createTimeStr, ")
					.append(" FROM_UNIXTIME(a.accountTime) accountTimeStr,f.name createUserName,  ")
					.append("  c.name shipName,d.refName ,DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') arrivalTime,  ")
					.append(" (case when b.type=1 then '入港' when b.type=2 then '出港' when b.type=3 then '入港通过' ")
					.append("  when b.type=5 then '出港通过' END) arrivalType,  b.id,e.name shipRefName ,c.netTons, ")
					.append(" (SELECT ROUND((UNIX_TIMESTAMP(FROM_UNIXTIME(w.leaveTime,'%Y-%m-%d'))-UNIX_TIMESTAMP(FROM_UNIXTIME(w.arrivalTime,'%Y-%m-%d')))/86400+1 ,0)  from t_pcs_work w where w.arrivalId=a.arrivalId limit 0,1 ) stayDay ")
					.append("  from t_pcs_dock_fee a   ")
					.append("  LEFT JOIN t_pcs_arrival b ON a.arrivalId=b.id ") 
					.append("  LEFT JOIN t_pcs_ship c on c.id=b.shipId  ")
					.append("  LEFT JOIN t_pcs_ship_ref d on d.id=b.shipRefId  ")
					.append("  LEFT JOIN t_pcs_ship_agent e on e.id=b.shipAgentId  ")
					.append("  LEFT JOIN t_auth_user f on f.id=a.createUserId  ")
					.append("  where a.id=").append(dFeeDto.getId());
					return executeQueryOne(sql.toString());
			}
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表失败", e);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getDockFeeChargeList(DockFeeDto dFeeDto) throws OAException {
		try {
			if(dFeeDto.getId()!=null){
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT * from t_pcs_dockfee_charge where dockFeeId=").append(dFeeDto.getId());
			return executeQuery(sql.toString());
			}
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表失败", e);
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> getDockFeeExcelList(DockFeeDto dFeeDto) throws OAException {
		try{
		StringBuilder sql=new StringBuilder();
		sql.append(" SELECT a.*,FROM_UNIXTIME(a.createTime) createTimeStr, ")
		.append(" FROM_UNIXTIME(a.accountTime) accountTimeStr,f.name createUserName,  ")
		.append("  c.name shipName,d.refName ,DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') arrivalTime,  ")
		.append("(case when a.billType=1 then '手撕发票' else '增值税发票' end) billTypeStr,")
		.append(" (case when b.type=1 or b.type=3 then '入港' when b.type=2 or b.type=5 then '出港' END) arrivalType, ")
		.append("  b.id,e.name shipAgentName ,c.netTons,CONCAT(c.name,'/',d.refName) boundMsg,")
		.append(" (SELECT ROUND((UNIX_TIMESTAMP(FROM_UNIXTIME(w.leaveTime,'%Y-%m-%d'))-UNIX_TIMESTAMP(FROM_UNIXTIME(w.arrivalTime,'%Y-%m-%d')))/86400+1 ,0)  from t_pcs_work w where w.arrivalId=a.arrivalId limit 0,1 ) stayDay, ")
		.append(" (select g.totalFee from t_pcs_dockfee_charge g where g.feeType=1 and a.id=g.dockFeeId limit 0,1 ) berthFee,")
		.append(" (select g.totalFee from t_pcs_dockfee_charge g where g.feeType=2 and a.id=g.dockFeeId limit 0,1 ) waterFee")
		.append("  from t_pcs_dock_fee a   ")
		.append("  LEFT JOIN t_pcs_arrival b ON a.arrivalId=b.id ") 
		.append("  LEFT JOIN t_pcs_ship c on c.id=b.shipId  ")
		.append("  LEFT JOIN t_pcs_ship_ref d on d.id=b.shipRefId  ")
		.append("  LEFT JOIN t_pcs_ship_agent e on e.id=b.shipAgentId  ")
		.append("  LEFT JOIN t_auth_user f on f.id=a.createUserId  where 1=1 ");
		if(!Common.isNull(dFeeDto.getId()))
			sql.append(" and a.id=").append(dFeeDto.getId());
		
		if(!Common.isNull(dFeeDto.getArrivalType())&&dFeeDto.getArrivalType()==1)
			sql.append(" and (b.type=1 or b.type=3) ");
		else if(!Common.isNull(dFeeDto.getArrivalType())&&dFeeDto.getArrivalType()==2)
			sql.append(" and (b.type=2 or b.type=5) ");
		
		if(!Common.isNull(dFeeDto.getCode()))
			sql.append(" and a.code like '%").append(dFeeDto.getCode()).append("%'");
		
		if(!Common.isNull(dFeeDto.getClientName()))
			sql.append(" and a.clientName like '%").append(dFeeDto.getClientName()).append("%'");
		
		if(!Common.isNull(dFeeDto.getStatus())&&dFeeDto.getStatus()!=-1)
			sql.append(" and a.status=").append(dFeeDto.getStatus());
		
		if(!Common.isNull(dFeeDto.getBillType())&&dFeeDto.getBillType()!=0)
			sql.append(" and a.billType=").append(dFeeDto.getBillType());
		
		if(!Common.isNull(dFeeDto.getStartTime())&&dFeeDto.getStartTime()!=-1)
			sql.append(" and UNIX_TIMESTAMP(b.arrivalStartTime) >=").append(dFeeDto.getStartTime());
		
		if(!Common.isNull(dFeeDto.getEndTime())&&dFeeDto.getEndTime()!=-1)
			sql.append(" and UNIX_TIMESTAMP(b.arrivalStartTime)<=").append(dFeeDto.getEndTime());
		
		if(!Common.isNull(dFeeDto.getShipName()))
			sql.append(" and ( c.name like '%").append(dFeeDto.getShipName()).append("%' or d.refName like '%").append(dFeeDto.getShipName()).append("%') ");
		sql.append(" order by b.arrivalStartTime asc ");
		return executeQuery(sql.toString());
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @Title getDockFeePrice
	 * @Descrption:TODO
	 * @param:@param dockFeeId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年12月30日上午9:59:00
	 * @throws
	 */
	@Override
	public Map<String, Object> getDockFeePrice(Integer dockFeeId)
			throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT round(sum(totalFee), 2) totalFee,round(sum(discountFee), 2) discountFee from t_pcs_dockfee_charge where dockFeeId=").append(dockFeeId);
			return executeQueryOne(sql.toString());
		}catch (RuntimeException e) {
			LOG.error("dao获取首期费列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取首期费列表失败", e);
		}
	}


}
