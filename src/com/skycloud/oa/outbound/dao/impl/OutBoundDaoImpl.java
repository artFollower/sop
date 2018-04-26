package com.skycloud.oa.outbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.ArrivalInfo;
import com.skycloud.oa.inbound.model.ArrivalPlan;
import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.inbound.model.Store;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.inbound.model.Work;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.outbound.dao.OutBoundDao;
import com.skycloud.oa.outbound.dto.ShipArrivalDto;
import com.skycloud.oa.outbound.model.DeliveryShip;
import com.skycloud.oa.outbound.model.UploadFileInfo;
import com.skycloud.oa.utils.Common;

/**
 * 
 * <p>分流台帐</p>
 * @ClassName:ShipDeliverWorkDaoImpl
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年8月3日 下午1:54:27
 *
 */
@Component
public class OutBoundDaoImpl extends BaseDaoImpl implements OutBoundDao 
{
	/**
	 * 记录日志
	 */
	private static Logger LOG = Logger.getLogger(OutBoundDaoImpl.class);
	
	/**
	 * @Title getOutBoundList
	 * @Descrption:TODO
	 * @param:@param arrivalDto
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月11日上午9:36:17
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getOutBoundList(ShipArrivalDto arrivalDto, int start, int limit)
			throws OAException {
		try{
			StringBuilder sb = new StringBuilder() ;
			sb.append("	SELECT a.id,a.type,a.berthId,DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d %H:%i') arrivalTime,c.name shipName,a.shipId,a.productId, ")
			.append(" d.refName refName,f.name berthName,IFNULL(g.report,'未申报') report,e.name productName,g.shipArrivalDraught, ")
			.append(" a.status status,b.value statusName,j.name agentName,a.shipAgentId,g.id arrivalInfoId, ")
			.append(" (case when a.status=54 then sum(ROUND(h.actualNum,3)) else (sum(ROUND(h.deliverNum,3))) end) totalNum, ")
			.append(" GROUP_CONCAT(DISTINCT h.ladingEvidence) ladingEvidence,GROUP_CONCAT(DISTINCT k.name) clientName,GROUP_CONCAT(DISTINCT l.name) ladingClientName,count(DISTINCT h.ladingEvidence)  isInvoice ")
			.append(" from t_pcs_arrival a ")
			.append(" LEFT JOIN t_pcs_arrival_status b on b.key=a.status ")
			.append(" LEFT JOIN t_pcs_ship c on c.id=a.shipId ")
			.append(" LEFT JOIN t_pcs_ship_ref d on d.id=a.shipRefId ")
			.append(" LEFT JOIN t_pcs_product e on e.id=a.productId ")
			.append(" LEFT JOIN t_pcs_berth f on f.id=a.berthId ")
			.append(" LEFT JOIN t_pcs_arrival_info g on g.arrivalId=a.id ")
			.append(" LEFT JOIN t_pcs_ship_agent j ON a.shipAgentId = j.id ")
			.append(" LEFT JOIN t_pcs_goodslog h on h.batchId=a.id and h.type=5 and h.deliverType<>1 ")
			.append(" LEFT JOIN t_pcs_client k on k.id=h.clientId")
			.append(" LEFT JOIN t_pcs_client l on l.id=h.ladingClientId")
			.append(" where 1=1 and (a.type=2 or a.type=5) and a.status>49 ");
			if(!Common.isNull(arrivalDto.getId())){
				sb.append(" and a.id=").append(arrivalDto.getId());
			}
			if(!Common.isNull(arrivalDto.getShipName())){
				sb.append(" and ( c.name like '%").append(arrivalDto.getShipName()).append("%' or d.refName like '%")
				.append(arrivalDto.getShipName()).append("%')");
			}
			if(!Common.isNull(arrivalDto.getIsTransport())&&arrivalDto.getIsTransport()==2){
				sb.append(" and d.refName='转输'");
			}else if(Common.isNull(arrivalDto.getId())){
				sb.append(" and d.refName!='转输'");
			}
			if(!Common.isNull(arrivalDto.getShipId())){
				sb.append(" and a.shipId=").append(arrivalDto.getShipId());
			}
			if(!Common.isNull(arrivalDto.getProductId())){
				sb.append(" and a.productId=").append(arrivalDto.getProductId());
			}
			if(!Common.isNull(arrivalDto.getStartTime())&&arrivalDto.getStartTime()!=-1){
				sb.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) >=").append(arrivalDto.getStartTime());
			}
			if(!Common.isNull(arrivalDto.getEndTime())&&arrivalDto.getEndTime()!=-1){
				sb.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) <=").append(arrivalDto.getEndTime());
			}
			if(!Common.isNull(arrivalDto.getStatus())){
				sb.append(" and a.status in ( ").append(arrivalDto.getStatus()).append(")" );
			}
			sb.append("	GROUP BY a.id ORDER BY a.arrivalStartTime DESC ");
			if(limit!=0)
			sb.append(" limit ").append(start).append(" , ").append(limit);
		return executeQuery(sb.toString());
		}catch(RuntimeException e){
			LOG.error("dao获取船发出库列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取获取船发出库列表失败", e);
		}
	}

	/**
	 * @Title getOutBoundListCount
	 * @Descrption:TODO
	 * @param:@param arrivalDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月11日上午9:36:17
	 * @throws
	 */
	@Override
	public int getOutBoundListCount(ShipArrivalDto arrivalDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT count(1) FROM t_pcs_arrival a  left join t_pcs_ship c on a.shipId=c.id  LEFT JOIN t_pcs_ship_ref d on d.id=a.shipRefId where (a.type = 2 or a.type=5)  and a.status>49") ;
			if(!Common.isNull(arrivalDto.getId())){
				sql.append(" and a.id=").append(arrivalDto.getId());
			}
			if(!Common.isNull(arrivalDto.getShipName())){
				sql.append(" and c.name like '%").append(arrivalDto.getShipName()).append("%' or d.refName like '%")
				.append(arrivalDto.getShipName()).append("%')");
			}
			if(!Common.isNull(arrivalDto.getIsTransport())&&arrivalDto.getIsTransport()==2){
				sql.append(" and d.refName='转输'");
			}else{
				sql.append(" and d.refName!='转输'");
			}
			if(!Common.isNull(arrivalDto.getShipId())){
				sql.append(" and a.shipId=").append(arrivalDto.getShipId());
			}
			if(!Common.isNull(arrivalDto.getProductId())){
				sql.append(" and a.productId=").append(arrivalDto.getProductId());
			}
			if(!Common.isNull(arrivalDto.getStartTime())&&arrivalDto.getStartTime()!=-1){
				sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) >=").append(arrivalDto.getStartTime());
			}
			if(!Common.isNull(arrivalDto.getEndTime())&&arrivalDto.getEndTime()!=-1){
				sql.append(" and UNIX_TIMESTAMP(a.arrivalStartTime) <=").append(arrivalDto.getEndTime());
			}
			if(!Common.isNull(arrivalDto.getStatus())){
				sql.append(" and a.status in ( ").append(arrivalDto.getStatus()).append(")" );
			}
		return (int)getCount(sql.toString());
		}catch (RuntimeException e){
			LOG.error("dao获取船发出库列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取获取船发出库列表数量失败", e);
		}
	}

	/**
	 * 通过id获取作业计划
	 * @Title getArrivalInfoById
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2016年3月14日下午1:52:17
	 * @throws
	 */
	@Override
	public Map<String, Object> getArrivalInfoById(String arrivalId) throws OAException{
		try{
			StringBuilder sql=new StringBuilder();
			 sql.append(" SELECT a.id,FROM_UNIXTIME(a.cjTime) cjTime,FROM_UNIXTIME(a.lastLeaveTime) lastLeaveTime, ")
			 .append(" a.lastLeaveTime mlastLeaveTime,FROM_UNIXTIME(a.tcTime) tcTime, ")
			 .append(" FROM_UNIXTIME(a.norTime) norTime, FROM_UNIXTIME(a.anchorTime) anchorTime,a.tearPipeTime,a.`port`,a.portNum, ")
			 .append(" a.repatriateTime,a.shipInfo,a.note,a.overTime,a.arrivalId,a.createUserId, ")
			 .append(" 	a.createTime,a.reviewUserId,a.reviewTime,a.report,b.status ")
			 .append(" FROM t_pcs_arrival_info a left join t_pcs_arrival b on a.arrivalId=b.id WHERE a.arrivalId =").append(arrivalId);
		return executeQueryOne(sql.toString());
		}catch(RuntimeException e){
			LOG.error("dao获取船发出库列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取获取船发出库列表数量失败", e);	
		}
	}
	
	/**
	 * @Title getTubesAndTanksByArrivalId
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月14日下午2:45:09
	 * @throws
	 */
	@Override
	public Map<String, Object> getTubesAndTanksByArrivalId(String arrivalId) throws OAException {
		try{
			
			StringBuilder sql=new StringBuilder();
			sql.append(" select tubeInfo,tankInfo  from t_pcs_transport_program where arrivalId=").append(arrivalId);
			return executeQueryOne(sql.toString());
	}catch(RuntimeException e){
		LOG.error("dao获取罐和管线失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取获取罐和管线失败", e);	
	}
	}
	
	/**
	 * 
	 * @Title:saveArrivalInfo
	 * @Description:
	 * @param arrivalInfo
	 * @throws OAException
	 * @see
	 */
	@Override
	public int saveArrivalInfo(ArrivalInfo arrivalInfo) throws OAException 
	{
		try{
		return Integer.valueOf(save(arrivalInfo).toString());
		}catch(RuntimeException e){
			LOG.error("saveArrivalInfo失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "saveArrivalInfo失败", e);	
		}
	}
	
	@Override
	public void updateArrivalInfo(ArrivalInfo arrivalInfo) throws OAException 
	{
		try{
			update(arrivalInfo);
			}catch(RuntimeException e){
				LOG.error("saveArrivalInfo失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "saveArrivalInfo失败", e);	
			}
		
	}
	/**
	 * @Title getBerthProgramByArrivalId
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月15日上午9:46:14
	 * @throws
	 */
	@Override
	public Map<String, Object> getBerthProgramByArrivalId(String arrivalId) throws OAException 
	{
		try{
			StringBuilder sql=new StringBuilder();
				sql.append(" SELECT a.id,d.type,a.berthId berthId,a.safeInfo safeInfo,a.`comment` `comment`, ")
				.append(" a.richDraught,a.windPower,a.createUserId createUserId,c.`name` createUserName,d.productId, ")
				.append(" from_unixtime(a.createTime,'%Y-%m-%d') createTime,a.reviewUserId reviewUserId, ")
				.append(" b.`name` reviewUserName,from_unixtime(a.reviewTime,'%Y-%m-%d') reviewTime, ")
				.append("  a.`status` bpstatus,a.arrivalId,b.`status` ")
				.append(" FROM t_pcs_arrival d")
				.append(" LEFT JOIN  t_pcs_berth_program a ON a.arrivalId=d.id ")
				.append(" LEFT JOIN t_auth_user b ON a.reviewUserId = b.id ")
				.append(" LEFT JOIN t_auth_user c ON a.createUserId = c.id ")
				.append(" where d.id=").append(arrivalId);
			return executeQueryOne(sql.toString());	
		}catch(RuntimeException e){
			LOG.error("dao获取罐和管线失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取获取罐和管线失败", e);	
		}
		
	}
	
	
	/**
	 * @Title saveBerthProgram
	 * @Descrption:TODO
	 * @param:@param berthProgram
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月16日上午11:57:34
	 * @throws
	 */
	@Override
	public int saveBerthProgram(BerthProgram berthProgram) throws OAException 
	{
		try{
	  return Integer.valueOf(save(berthProgram).toString());
	}catch(RuntimeException e){
		LOG.error("dao获取罐和管线失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取获取罐和管线失败", e);	
	}
	}

	/**
	 * 获取靠泊评估信息
	 * @Title getBerthAssess
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月16日下午1:24:52
	 * @throws
	 */
	@Override
	public  Map<String, Object> getBerthAssess(String arrivalId)
			throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
		sql.append("select k.id id,k.arrivalId, k.weather weather,k.windDirection windDirection,k.windPower windPower,k.reason reason,k.createUserId createUserId,")
		.append(" m.name createUserName,from_unixtime(k.createTime) createTime,k.security security,k.comment comment, n.name reviewUserName,")
		.append(" from_unixtime(k.reviewTime) reviewTime,k.reviewStatus status from t_pcs_berth_assess k ")
		.append(" LEFT JOIN t_auth_user n on k.reviewUserId=n.id")
		.append(" LEFT JOIN t_auth_user m on k.createUserId=m.id")
		.append("  where k.arrivalId=").append(arrivalId);
		return executeQueryOne(sql.toString());
		}catch(RuntimeException e){
			LOG.error("dao获取靠泊评估信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取靠泊评估信息失败", e);	
		}
	}
	/**
	 * @Title saveBerthAssess
	 * @Descrption:TODO
	 * @param:@param berthAssess
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月16日下午2:46:36
	 * @throws
	 */
	@Override
	public int saveBerthAssess(BerthAssess berthAssess) throws OAException {
		try{
	   return  Integer.valueOf(save(berthAssess).toString()) ;
	}catch(RuntimeException e){
		LOG.error("dao保存靠泊评估失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao保存靠泊评估失败", e);	
	}
	}
	
	/**获取发货准备信息
	 * @Title getTransportProgramMsg
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月17日上午10:15:04
	 * @throws
	 */
	@Override
	public Map<String, Object> getTransportProgramMsg(String arrivalId) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
				sql.append(" SELECT s.id id,s.type,s.flow flow,s.svg svg,s.node node,s.comment comment,s.tubeInfo tubeInfo,")
				.append(" s.tankInfo tankInfo,s.dockWork dockWork,s.tubeWork,s.powerWork powerWork,s.noticeCodeA noticeCodeA,")
				.append(" s.noticeCodeB noticeCodeB,s.createUserId createUserId,t.name createUserName,")
				.append(" from_unixtime(s.createTime) createTime,s.productId productId, w.name productName,")
				.append(" s.status status , from_unixtime(a.openPump) openPumpTime,from_unixtime(a.stopPump) stopPumpTime from t_pcs_transport_program s ")
				.append(" LEFT JOIN t_pcs_work a On a.arrivalId=s.arrivalId ")
				.append(" LEFT JOIN t_auth_user t on s.createUserId=t.id ")					 
				.append(" LEFT JOIN t_pcs_product w on s.productId=w.id ")
				.append(" where s.type=2  and s.arrivalId=").append(arrivalId);
			return executeQueryOne(sql.toString());
			}catch(RuntimeException e){
				LOG.error("dao获取发货准备信息失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取发货准备信息失败", e);	
			}
	}
	/**
	 * @Title getAmountAffirmMsg
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年3月24日下午2:08:27
	 * @throws
	 */
	@Override
	public List<Map<String, Object>>getAmountAffirmMsg (String arrivalId) throws OAException 
	{
		try{
			StringBuilder sql=new StringBuilder();
			 sql.append(" SELECT a.id,a.batchId,a.goodsId,c.refName vsName,a.ladingEvidence ladingEvidence, ")
			 .append(" a.serial,a.deliverNum,a.actualNum,a.tempDeliverNum, ")
			 .append(" d.code ladingCode,e.code goodsCode,round((e.goodsCurrent-(e.goodsTotal -e.goodsInPass)),3) goodsCurrent, ")
			 .append(" e.productId prductId,f.name productName, ")
			 .append(" DATE_FORMAT(FROM_UNIXTIME(a.createTime),'%Y-%m-%d') createTime,FROM_UNIXTIME(a.createTime) createTimeStr, a.createTime createTimeLong,")
			 .append(" g.name userName , h.name ladingClientName,i.status  weighStatus,j.status status")
			 .append(" FROM t_pcs_goodslog a ")
			 .append(" LEFT JOIN t_pcs_ship_ref c on a.vehicleShipId=c.id ")
			 .append(" LEFT JOIN t_pcs_lading d on d.id=a.ladingId ")
			 .append(" LEFT JOIN t_pcs_goods e on e.id=a.goodsId ")
			 .append(" LEFT JOIN t_pcs_product f on f.id=e.productId ")
			 .append(" LEFT JOIN t_auth_user g on g.id=a.createUserId ")
			 .append(" LEFT JOIN t_pcs_client h on h.id=a.ladingClientId")
			 .append(" LEFT JOIN t_pcs_weighbridge i on i.serial=a.serial ")
			 .append(" LEFT JOIN t_pcs_arrival j on j.id=a.batchId ")
			 .append(" WHERE a.type=5 and a.deliverType<>1 and a.batchId= ").append(arrivalId);
		return executeQuery(sql.toString());
	}catch(RuntimeException e){
		LOG.error("dao获取发货准备信息失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取发货准备信息失败", e);	
	}
	}
	
	
	/**
	 * 获取分流台账信息
	 * @Title getShipFlowInfo
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月1日下午8:55:46
	 * @throws
	 */
	@Override
	public Map<String, Object> getShipFlowInfo(Integer arrivalId) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
		sql.append(" SELECT a.id,a.shipId,a.shipRefId,a.productId,b.name shipName,c.refName shipRefName, ")
		.append(" d.`name` productName,e.id workId,FROM_UNIXTIME(e.arrivalTime) arrivalTime,DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d %H:%i:%s') arrivalStartTime, ")
		.append(" FROM_UNIXTIME(e.leaveTime) leaveTime,FROM_UNIXTIME(e.openPump) openPump, ")
		.append(" FROM_UNIXTIME(e.stopPump) stopPump,e.evaluate,e.evaluateUser,e.description,f.id transportId,g.tubeStatus tubeStatus, ")
		.append(" (select ROUND(SUM(deliverNum),3) from t_pcs_goodslog where deliverType!=1 and batchId=a.id and type=5 ) totalNum  ")
		.append(" from t_pcs_arrival a ")
		.append(" LEFT JOIN t_pcs_ship b ON a.shipId=b.id ")
		.append(" LEFT JOIN t_pcs_ship_ref c ON a.shipRefId=c.id ")
		.append(" LEFT JOIN t_pcs_product d ON d.id=a.productId ")
		.append("  LEFT JOIN t_pcs_work e ON e.arrivalId=a.id ")
		.append("  LEFT JOIN t_pcs_transport_program f ON f.arrivalId=a.id ")
		.append("  LEFT JOIN t_pcs_delivery_ship g ON g.transportId = f.id ")
		.append(" where  a.id=").append(arrivalId).append(" GROUP by a.id ");
			return executeQueryOne(sql.toString());
		}catch(RuntimeException e){
			LOG.error("dao获取分流台账信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取分流台账信息失败", e);	
		}
	}

	/**
	 * 获取分流台账检查人信息
	 * @Title getShipFlowCheckMsg
	 * @Descrption:TODO
	 * @param:@param arrivalId
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月1日下午9:21:18
	 * @throws
	 */
	@Override
	public List<Map<String,Object>> getShipFlowCheckMsg(Integer arrivalId)  throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
				 sql.append(" SELECT a.id, a.checkType,a.checkUserId,c.`name` checkUserName,FROM_UNIXTIME(checkTime) checkTime ")
				 .append("  from t_pcs_work_check a LEFT JOIN t_pcs_transport_program b ON a.transportId=b.id ")
				 .append(" LEFT JOIN t_auth_user c ON c.id=a.checkUserId ")
				 .append(" where b.arrivalId=").append(arrivalId);
			return executeQuery(sql.toString());
		}catch(RuntimeException e){
			LOG.error("dao获取分流台账检查人信息失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取分流台账检查人信息失败", e);	
		}
	}
	
	
	@Override
	public void deleteCheckInfo(String transportId) throws Exception 
	{
		String sql = "delete from t_pcs_work_check where transportId= "+transportId ;
		executeUpdate(sql) ;
	}
	
	
	@Override
	public void updateNotifyTime(String time, int type, int aid) throws Exception 
	{
		String sql = "update t_pcs_notify set createTime =UNIX_TIMESTAMP('"+time+"') where type="+type+" and batchId="+aid ;
		executeUpdate(sql) ;
	}
	
	@Override
	public Map<String, Object> getTranspInfoById(String tpId) throws Exception 
	{
		String sql = "select * from t_pcs_transport_program where id="+tpId ;
		return executeQueryOne(sql);
	}
	@Override
	public void updateBerthProgram(BerthProgram berthProgram) throws OAException 
	{
		update(berthProgram) ;
	}
	
	@Override
	public void addConfirmData(ArrivalPlan data) throws OAException 
	{
		String sql = "update t_pcs_arrival_plan set actualNum ="+data.getActualNum()+" where id="+data.getId() ;
		executeUpdate(sql);
	}

	@SuppressWarnings("unused")
	@Override
	public List<Map<String, Object>> getWorkTask(String arrivalId) throws OAException 
	{
		String sql = "" ;
		return null;
	}
	
	@Override
	public void updateArrivalBerthId(String arrivalId, String berthId) throws OAException 
	{
		String sql = "update t_pcs_arrival set berthId ="+berthId+" where id="+arrivalId ;
		executeUpdate(sql) ;
	}

	@Override
	public void updateBerth(BerthAssess berth) throws OAException 
	{
		update(berth) ;
	}

	@Override
	public int saveTransportProgram(TransportProgram transportProgram) throws OAException 
	{
		Serializable s  = save(transportProgram) ;
		return Integer.parseInt(s.toString()) ;
	}

	@Override
	public int updateTransportProgram(TransportProgram transportProgram) throws OAException 
	{
		update(transportProgram) ;
		return 0;
	}
	
	@Override
	public int addDockWorkInfo(TransportProgram transportProgram) throws OAException 
	{
		String sql = "update t_pcs_transport_program set dockWork='"+transportProgram.getDockWork() +"' where id="+transportProgram.getId() ;
		executeUpdate(sql) ;
		return 0;
	}
	
	@Override
	public void saveTrans(int transportId,String tubeId) throws OAException 
	{
		String sql = "INSERT INTO t_pcs_trans(transportId,tubeId) select "+transportId+","+tubeId ;
		execute(sql) ;
	}

	@Override
	public void saveStore(int transportId,String tankId) throws OAException 
	{
		String sql = "INSERT INTO t_pcs_store(transportId,tankId) select "+transportId+","+tankId ;
		execute(sql) ;
	}
	

	@Override
	public void checkCommitChange(int arrivalId,int flag) throws OAException 
	{
		String sql = "call checkCommitChange("+arrivalId+","+flag+")" ;
		//executeUpdate(sql) ;
		executeProcedure(sql);
	}
	
	@Override
	public void deleteStore(int transportId,String tankIds) throws OAException 
	{
		String sql = "delete from t_pcs_store where transportId = "+transportId;
		executeUpdate(sql) ;
	}
	
	
	@Override
	public void deleteTrans(int transportId,String tubeIds) throws OAException {
		
		String sql = "delete from t_pcs_trans where transportId = "+transportId ;
		executeUpdate(sql) ;
	}
	@Override
	public List<Map<String, Object>> getTransportInfo(String arrivalId)
			throws Exception {
		
		String sql = "select id,status,( SELECT NAME FROM t_auth_user WHERE id=reviewUserId)checkUser,FROM_UNIXTIME(reviewTime) checkTime,comment from t_pcs_transport_program where arrivalId = "+arrivalId ;
		return executeQuery(sql);
	}
	@Override
	public List<Map<String, Object>> getBerthInfoById(String berthId,String arrivalId)
			throws OAException {
		
		String sql = "SELECT t.*,round((t.limitDrought-(SELECT shipDraught FROM t_pcs_ship WHERE id=(SELECT shipId FROM t_pcs_arrival a WHERE a.id="+arrivalId+"))),3) richDeep FROM t_pcs_berth t where t.id="+berthId ;
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> queryDockNotifyInfo(String arrivalId)
			throws OAException {
		
		String sql = "SELECT tp.*," +
				"(SELECT n.code FROM t_pcs_notify n WHERE TYPE=15 AND batchId=tp.arrivalId) c1," +
				"(SELECT DATE_FORMAT(FROM_UNIXTIME(n.createTime),'%Y-%m-%d') FROM t_pcs_notify n WHERE TYPE=15 AND batchId=tp.arrivalId) checkTime1," +
				"(SELECT DATE_FORMAT(FROM_UNIXTIME(n.createTime),'%Y-%m-%d') FROM t_pcs_notify n WHERE TYPE=16 AND batchId=tp.arrivalId) checkTime2," +
				"(SELECT n.code FROM t_pcs_notify n WHERE TYPE=16 AND batchId=tp.arrivalId) c2" +
				",( SELECT NAME FROM t_auth_user WHERE id=tp.reviewUserId) checkUser" +
				", DATE_FORMAT(FROM_UNIXTIME(tp.reviewTime),'%Y-%m-%d') checkTime" +
				" FROM t_pcs_transport_program tp WHERE tp.arrivalId="+arrivalId ;
		return executeQuery(sql);
	}

	@Override
	public void saveStore(Store store) throws OAException {
		
		save(store) ;
	}

	@Override
	public void saveWork(Work work) throws OAException {
		
		save(work) ;
	}
	@Override
	public void updateWork(Work work) throws OAException {
		
		update(work) ;
	}

	
	
	@Override
	public List<Map<String, Object>> getStatisticsByTank(String arrivalId)
			throws OAException {
		
		String sql = "SELECT t.code,s.realAmount FROM t_pcs_store s LEFT JOIN t_pcs_tank t ON s.tankId = t.id,t_pcs_transport_program p WHERE s.transportId=p.id AND p.arrivalId="+arrivalId ;
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getWorkByArrivalId(String arrivalId)
			throws OAException {
		String sql = "SELECT * FROM t_pcs_work w WHERE w.arrivalId = "+arrivalId ;
		return executeQuery(sql);
	}

	@Override
	public List<Map<String, Object>> getStoreByArrivalId(String arrivalId)
			throws OAException {
		String sql = "SELECT w.*,t.code FROM t_pcs_store w LEFT JOIN t_pcs_transport_program tp ON w.transportId=tp.id LEFT JOIN t_pcs_tank t ON w.tankId=t.id   WHERE tp.arrivalId="+arrivalId ;
		return executeQuery(sql);
	}

	@Override
	public void updateStore(Store store) throws OAException {
		
		update(store) ;
	}

	
	@Override
	public void deleteWorkCheckByTransportId(int transportId)
			throws OAException {
		
		String sql = "delete from t_pcs_work_check where transportId="+transportId ;
		executeUpdate(sql) ;
	}
	
	@Override
	public void deleteDeliveryShipByTransportId(int transportId)
			throws OAException {
		
		String sql = "delete from t_pcs_delivery_ship where transportId="+transportId ;
		executeUpdate(sql) ;
	}
	@Override
	public void saveCheckWork(WorkCheck workCheck) throws OAException {
		
		save(workCheck) ;
	}
	
	@Override
	public void updateWorkCheck(WorkCheck workcheck) throws Exception {
		
		String sql = "update t_pcs_work_check set content='"+workcheck.getContent()+"',flag="+workcheck.getFlag()+",checkUserId="+workcheck.getCheckUserId()+" where transportId="+workcheck.getTransportId()+" and checkType="+workcheck.getCheckType() ;
		executeUpdate(sql) ;
	}
	
	public int getWorkCheckCount(WorkCheck workCheck) throws Exception{
		String sql = " select count(1) from t_pcs_work_check where checkType="+workCheck.getCheckType()+" and transportId="+workCheck.getTransportId() ;
		return (int)getCount(sql) ;
	}
	
	@Override
	public void updateCheckWork(WorkCheck workCheck) throws OAException {
		
		update(workCheck) ;
	}




	
	@Override
	public List<Map<String, Object>> getPortNumCount(String arrivalId)
			throws OAException {
		
		String sql = "SELECT COUNT(1) count FROM t_pcs_arrival aa WHERE  DATE_FORMAT(NOW(),'%Y') = DATE_FORMAT(aa.arrivalStartTime,'%Y') AND aa.shipId=(select shipId from t_pcs_arrival where id="+arrivalId+")" ;
		return executeQuery(sql);
	}
	@Override
	public List<Map<String, Object>> getAnchorTime(String arrivalId)
			throws OAException {
		
		String sql = "select FROM_UNIXTIME(anchorTime) anchorTime from t_pcs_arrival_info where arrivalId="+arrivalId ;
		return executeQuery(sql) ;
	}
	@Override
	public List<Map<String, Object>> getArrivalById(String arrivalId)
			throws OAException {
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT 	");
		sb.append("	  a.*,	");
		sb.append("	  p.id productId,	");
		sb.append("	  p.name productName,	");
		sb.append("	  rr.refName shipName,	");
		sb.append("	  s.shipLenth,s.shipWidth,s.netTons,");
		sb.append("	  i.shipArrivalDraught,	");
		sb.append("	  s.loadCapacity,	");
		sb.append("	  DATE_FORMAT(a.arrivalStartTime, '%Y-%m-%d') startTime,	");
		sb.append("	  DATE_FORMAT(a.arrivalEndTime, '%Y-%m-%d') endTime,	");
		sb.append("	  (SELECT 	");
		sb.append("	    round(SUM(deliverNum),3)	");
		sb.append("	  FROM	");
		sb.append("	    t_pcs_goodslog	");
		sb.append("	  WHERE batchId = a.id and type=5 and deliverType!=1) totalNum,	");
		sb.append("	  a.berthId,	");
		sb.append("	  pb.name berthName,	");
		sb.append("	  t.tubeInfo,	");
		sb.append("	  t.tankInfo 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_arrival a 	");
		sb.append("	  LEFT JOIN t_pcs_transport_program t 	");
		sb.append("	    ON a.id = t.arrivalId 	");
		sb.append("	  LEFT JOIN t_pcs_berth pb 	");
		sb.append("	    ON a.berthId = pb.id 	");
		sb.append("	  LEFT JOIN t_pcs_ship s 	");
		sb.append("	    ON a.shipId = s.id 	");
		sb.append("	  LEFT JOIN t_pcs_product p 	");
		sb.append("	    ON p.id = a.productId 	");
		sb.append("	  LEFT JOIN t_pcs_ship_ref rr 	");
		sb.append("	    ON a.shipRefId = rr.id 	");
		sb.append("	  LEFT JOIN t_pcs_arrival_info i	");
		sb.append("	  ON a.id=i.arrivalId	");
		sb.append("	WHERE a.id ="+arrivalId);
		return executeQuery(sb.toString());
	}
	
	/**
	 * 出库管理-船舶出库-基本信息-货体信息
	 * @Title:getDeliverGoodsInfo
	 * @Description:
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getBaseGoodsInfo(String arrivalId) throws OAException {
		try{
		StringBuilder sql = new StringBuilder() ;
			sql.append(" SELECT CONCAT(f.name,'/',g.refName,'/',DATE_FORMAT(e.arrivalStartTime,'%Y-%m-%d')) shipInfo, ")
			.append(" d.productId ,h.name productName,c.`code` cargoCode,d.`code` goodsCode,i.name ladingClientName, ")
			.append(" b.ladingCode,b.goodsTotal,(CASE WHEN b.isVerification=1 THEN '是' ELSE '否' END) isVerification, ")
			.append(" IFNULL(b.flow,'') flow ")
			.append(" from t_pcs_arrival a ")
			.append(" LEFT JOIN t_pcs_arrival_plan b ON a.id=b.arrivalId ")
			.append(" LEFT JOIN t_pcs_cargo c ON c.id=b.cargoId ")
			.append(" LEFT JOIN t_pcs_goods d ON d.id=b.goodsId ")
			.append(" LEFT JOIN t_pcs_arrival e ON e.id=c.arrivalId ")
			.append(" LEFT JOIN t_pcs_ship f ON f.id=e.shipId ")
			.append(" LEFT JOIN t_pcs_ship_ref g ON g.id=e.shipRefId ")
			.append(" LEFT JOIN t_pcs_product h on h.id=d.productId ")
			.append(" LEFT JOIN t_pcs_client i on i.id=b.ladingClientId ")
			.append(" WHERE a.id=").append(arrivalId);
		return executeQuery(sql.toString()) ;
		}catch(RuntimeException e){
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void updateDynamicNotifyInfo(TransportProgram d) throws OAException {
		
		String sql = "update t_pcs_transport_program set powerWork='"+d.getPowerWork()+"' where id="+d.getId() ;
		executeUpdate(sql);
	}
	@Override
	public List<Map<String,Object>> getWork(String arrivalId) throws OAException {
		
		String sql = "select w.id,FROM_UNIXTIME(w.openPump) openPump,FROM_UNIXTIME(w.stopPump) stopPump,w.evaluate,w.evaluateUser,w.description from t_pcs_work w where w.arrivalId = "+arrivalId ;
		return executeQuery(sql) ;
	}

	

	@Override
	public List<Map<String,Object>> getDeliveryShip(String arrivalId) throws OAException {
		
		String sql = "select * from t_pcs_delivery_ship where transportId in (select id from t_pcs_transport_program where arrivalId="+arrivalId+")" ;
		return executeQuery(sql) ;
	}
	
	
	/**
	 * 修改分流台账信息-提单数量
	 * @Title:getShipFlowBaseInfo
	 * @Description:
	 * @param arrivalId
	 * @return
	 * @throws OAException
	 * @see
	 */
	@Override
	public List<Map<String, Object>> getShipFlowBaseInfo(String arrivalId) throws OAException 
	{
		StringBuffer sb = new StringBuffer() ;
		sb.append("	SELECT 	");
		sb.append("	  r.refName shipName,	");
		sb.append("	  p.name productName,	");
		//sb.append("	  d.comment,	");
		sb.append("	  DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d %H:%i:%s') arrivalStartTime,	");
		sb.append("	  FROM_UNIXTIME(w.arrivalTime) arrivalTime,	");
		sb.append("	  FROM_UNIXTIME(w.leaveTime) leaveTime,	");
		sb.append("	 (select Round(sum(deliverNum),3) from t_pcs_goodslog where deliverType!=1 and batchId=a.id ) totalNum 	");
		sb.append("	FROM	");
		sb.append("	  t_pcs_arrival a,	");
		sb.append("	  t_pcs_ship_ref r,	");
		sb.append("	  t_pcs_product p,	");
		//sb.append("	  t_pcs_transport_program d,	");
		sb.append("	  t_pcs_work w 	");
		sb.append("	WHERE w.arrivalId = a.id 	");
		sb.append("	  AND a.shipRefId = r.id 	");
		//sb.append("	  AND a.id = d.arrivalId 	");
		sb.append("	  AND a.productId = p.id 	");
		sb.append("	  AND a.id = "+arrivalId);
		
		LOG.info("（ShipDeliverWorkDaoImpl）修改分流台账信息-提单数量："+sb.toString());
		
		return executeQuery(sb.toString());
	}
	
	@Override
	public void updatePipingNotifyInfo(TransportProgram d) throws OAException {
		
		String sql = "update t_pcs_transport_program set tubeWork='"+d.getTubeWork()+"',pipeTask='"+d.getPipeTask()+"' where id="+d.getId() ;
		executeUpdate(sql) ;
	}

	@Override
	public void saveDeliveryShip(DeliveryShip d) throws OAException {
		
		save(d) ;
	}
	
	@Override
	public void updateTranStatus(TransportProgram transportProgram) throws OAException {
		
		String sql = "update t_pcs_transport_program set comment='"+transportProgram.getComment()+"' where id="+transportProgram.getId() ;
		executeUpdate(sql) ;
	}
	@Override
	public void updateDeliveryShip(DeliveryShip d) throws OAException {
		
		update(d) ;
	}

	@Override
	public void updateStatus(String status,String trainId)throws OAException{
		String sql = "update t_pcs_arrival set status="+status +" where id = "+trainId +" and status<"+status;
		executeUpdate(sql) ;
	}
	@Override
	public void insertWorkInfo(int arrivalId) throws OAException {
		
		StringBuffer sb = new StringBuffer() ;
		sb.append("	INSERT INTO t_pcs_work(arrivalId,productId) 	");
		sb.append("	SELECT 	");
		sb.append(arrivalId+",");
		sb.append("	    productId  	");
		sb.append("	  FROM	");
		sb.append("	    t_pcs_arrival 	");
		sb.append("	  WHERE id = "+arrivalId);
		insert(sb.toString()) ;
	}
	
	@Override
	public List<Map<String, Object>> getWorkList(long startTime,long endTime) throws OAException
	{
		//a 到港作业表
		//b 到港信息表
		//c 调度日志基础表
		//d 传输方案表
		//e 作业检查表
		//f 岗位检查表
		//g 存储罐
		//h 储罐资料表
		//i 传输管线表
		//j 管线资料表
		//k 船舶资料表
		//l 货批表
		//m 货品表
		//n 靠泊方案表
		//o 泊位表
		//p 货主表
		StringBuffer sb = new StringBuffer() ;
sb.append(" SELECT  ");
sb.append(" a.id arrivalId, ");
sb.append(" b.id workId, ");
sb.append(" (SELECT ROUND(SUM(g.deliverNum),3) from t_pcs_goodslog g where g.batchId=a.id AND g.deliverType = 2 AND g.type = 5 ) totalNum, ");
sb.append(" a.arrivalStartTime arrivalTime, ");
sb.append(" d.refName shipName,a.shipRefId,a.shipId, ");
sb.append(" e.name productName,a.productId, ");
sb.append(" f.name berthName, ");
sb.append(" j.code tankName, ");
sb.append(" h.id transportId, ");
sb.append(" FROM_UNIXTIME(b.openPump) openPump, ");
sb.append(" FROM_UNIXTIME(b.stopPump) stopPump, ");
sb.append(" i.startHandLevel, ");
sb.append(" i.endHandLevel, ");
sb.append(" i.startLevel, ");
sb.append(" i.endLevel, ");
sb.append(" i.startHandWeight, ");
sb.append(" i.endHandWeight, ");
sb.append(" i.startWeight, ");
sb.append(" i.endWeight, ");
sb.append(" i.startTemperature, ");
sb.append(" i.endTemperature, ");
sb.append(" i.realAmount, ");
sb.append(" i.measureAmount, ");
sb.append(" i.differAmount, ");
sb.append("  k.tubeStatus tubeStatus, ");
sb.append("  m.name transName, ");
sb.append("  o.name clientName, ");
sb.append("  b.evaluate, ");
sb.append(" b.`evaluateUser` evaluateUserName, ");
sb.append(" ( CASE WHEN a. STATUS = 54 THEN (SELECT ROUND(SUM(g.actualNum),3) from t_pcs_goodslog g where g.batchId=a.id AND g.deliverType = 2 AND g.type = 5) ");
sb.append(" ELSE '' END ) amount, ");
sb.append(" r.name user1, ");
sb.append(" s.name user2, ");
sb.append(" a.status, ");
sb.append(" b.description COMMENT ");
sb.append(" from t_pcs_arrival a ");
sb.append(" LEFT JOIN t_pcs_work b ON b.arrivalId=a.id ");
sb.append(" LEFT JOIN t_pcs_ship_ref d on d.id=a.shipRefId ");
sb.append(" LEFT JOIN t_pcs_product e on a.productId=e.id ");
sb.append(" LEFT JOIN t_pcs_berth f on f.id=a.berthId ");
sb.append(" LEFT JOIN t_pcs_transport_program h on h.arrivalId=a.id ");
sb.append(" LEFT JOIN t_pcs_store i on i.transportId=h.id ");
sb.append(" LEFT JOIN t_pcs_tank j on j.id=i.tankId ");
sb.append(" LEFT JOIN t_pcs_delivery_ship k on k.transportId=h.id ");
sb.append(" LEFT JOIN t_pcs_trans l on l.transportId=h.id ");
sb.append(" LEFT JOIN t_pcs_tube m on m.id=l.tubeId ");
sb.append(" LEFT JOIN t_pcs_arrival_plan n on  n.arrivalId=a.id ");
sb.append(" LEFT JOIN t_pcs_client o on o.id=n.clientId ");
sb.append(" LEFT JOIN t_pcs_work_check p on p.transportId=h.id and p.checkType=21 ");
sb.append(" LEFT JOIN t_pcs_work_check q on q.transportId=h.id and q.checkType=22 ");
sb.append(" LEFT JOIN t_auth_user r on r.id=p.checkUserId ");
sb.append(" LEFT JOIN t_auth_user s on s.id=q.checkUserId ");
sb.append(" WHERE 1=1 ");
sb.append(" AND a.type=2 ");
sb.append(" AND UNIX_TIMESTAMP(a.arrivalStartTime) >="+startTime);
sb.append(" AND UNIX_TIMESTAMP(a.arrivalStartTime) <"+endTime);
sb.append(" GROUP BY i.endWeight ");
		return executeQuery(sb.toString());
	}
	
	@Override
	public List<Map<String, Object>> getLogIsHave(long startTime, long endTime)throws OAException {
		String sql="SELECT   UNIX_TIMESTAMP(a.arrivalStartTime) stopPump FROM  t_pcs_work w,t_pcs_arrival a WHERE w.arrivalId=a.id and a.type=2 " +
				" and  UNIX_TIMESTAMP(a.arrivalStartTime)>="+startTime+" and  UNIX_TIMESTAMP(a.arrivalStartTime)<"+endTime;
			return executeQuery(sql);
	}
	@Override
	public void saveUploadInfo(UploadFileInfo fileInfo) throws OAException {
		
		save(fileInfo) ;
	}
	@Override
	public List<Map<String, Object>> getFileList(String arrivalId,Integer type)
			throws Exception {
		
		String sql = "select * from t_pcs_uploadfile_info where refId="+arrivalId+" and type="+type ;
		return executeQuery(sql) ;
	}
	@Override
	public void deleteUploadFile(String id) throws Exception {
		
		String sql = "delete from t_pcs_uploadfile_info where id="+id ;
		executeUpdate(sql) ;
	}
	public void updateArrivalTime(long arrivalTime,String arrivalId) throws Exception {
		String sql = "update t_pcs_arrival set arrivalStartTime=from_unixtime("+arrivalTime+"),arrivalEndTime=from_unixtime("+arrivalTime+") where id="+arrivalId ;
		executeUpdate(sql) ;
	}

	@Override
	public List<Map<String, Object>> getOutArrivalList(long startTime, long endTime) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT a.id arrivalId, b.id workId,  ( SELECT ROUND(SUM(g.deliverNum), 3) FROM  t_pcs_goodslog g WHERE g.batchId = a.id AND")
			.append(" (g.deliverType=2 OR g.deliverType=3) AND g.type = 5 ) totalNum,d.refName shipName, a.shipRefId, a.shipId,e.name productName, ")
			.append(" a.productId, f. NAME berthName, h.id transportId,FROM_UNIXTIME(b.arrivalTime) arrivalTime,FROM_UNIXTIME(b.openPump) openPump,")
			.append(" FROM_UNIXTIME(b.stopPump) stopPump,FROM_UNIXTIME(b.leaveTime) leaveTime,k.tubeStatus tubeStatus,b.evaluate,")
			.append(" b.`evaluateUser` evaluateUserName,(CASE WHEN a. STATUS = 54 THEN (SELECT ROUND(SUM(g.actualNum), 3) FROM t_pcs_goodslog g ")
			.append(" WHERE g.batchId = a.id AND (g.deliverType=2 OR g.deliverType=3) AND g.type = 5 ")
			.append(" ) ELSE '' END ) amount, r. NAME user1, s. NAME user2,a.status, b.description comment ")
			.append(" FROM t_pcs_arrival a ")
			.append(" LEFT JOIN t_pcs_work b ON b.arrivalId = a.id ")
			.append(" LEFT JOIN t_pcs_ship_ref d ON d.id = a.shipRefId ")
			.append(" LEFT JOIN t_pcs_product e ON a.productId = e.id ")
			.append(" LEFT JOIN t_pcs_berth f ON f.id = a.berthId ")
			.append(" LEFT JOIN t_pcs_transport_program h ON h.arrivalId = a.id ")
			.append(" LEFT JOIN t_pcs_delivery_ship k ON k.transportId = h.id ")
			.append(" LEFT JOIN t_pcs_work_check p ON p.transportId = h.id AND p.checkType = 21 ")
			.append(" LEFT JOIN t_pcs_work_check q ON q.transportId = h.id AND q.checkType = 22 ")
			.append(" LEFT JOIN t_auth_user r ON r.id = p.checkUserId ")
			.append(" LEFT JOIN t_auth_user s ON s.id = q.checkUserId ")
			.append(" WHERE 1 = 1 AND (a.type = 2 or a.type=5) AND !ISNULL(b.id) ")
			.append(" AND UNIX_TIMESTAMP(a.arrivalStartTime) >= ").append(startTime)
			.append(" AND UNIX_TIMESTAMP(a.arrivalStartTime) <").append(endTime);
		return	executeQuery(sql.toString());
		}catch(RuntimeException e){
			LOG.error("dao获取分流台账列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取分流台账失败",e);
		}
	}

	@Override
	public List<Map<String, Object>> getOutFlowDataByArrivalId(Integer arrivalId)
			throws OAException {
try{   
	    
	  String sql="SELECT  a.startHandLevel, a.endHandLevel, a.startLevel, a.endLevel, "
		+" a.startHandWeight,  a.endHandWeight,  a.startWeight, a.endWeight, a.startTemperature, "
		+" a.endTemperature,  a.realAmount,  a.measureAmount,  a.differAmount,c.code tankName,c.id tankId "
		+"  from t_pcs_store a,t_pcs_transport_program b,t_pcs_tank c where  a.transportId=b.id AND c.id=a.tankId and b.arrivalId="+arrivalId;
	   return executeQuery(sql);
			
		}catch(RuntimeException e){
			LOG.error("dao获取出库列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取出库列表失败",e);
		}
	}

	@Override
	public List<Map<String, Object>> getTubeNamesByArrivalId(Integer arrivalId)
			throws OAException {
try{
	String sql="SELECT DISTINCT c.`name` tubeName"
+" from t_pcs_trans a ,t_pcs_transport_program b,t_pcs_tube c "
+" WHERE a.transportId=b.id AND c.id=a.tubeId AND b.arrivalId="+arrivalId;
	   return executeQuery(sql);
		}catch(RuntimeException e){
			LOG.error("dao获取出库列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取出库列表失败",e);
		}
	}

	@Override
	public List<Map<String, Object>> getClientNameByArrivalId(Integer arrivalId)
			throws OAException {
try{
	String sql="SELECT DISTINCT b.`name` clientName from t_pcs_arrival_plan a,t_pcs_client b"
			+ " WHERE a.clientId=b.id AND a.arrivalId="+arrivalId;
	
	   return executeQuery(sql);
		}catch(RuntimeException e){
			LOG.error("dao获取出库列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取出库列表失败",e);
		}
	}

	@Override
	public int checkIsInvoice(String arrivalId) throws OAException {
		try{
		String sql="select count(1) from t_pcs_goodslog where batchId="+arrivalId+" and type=5 and deliverType>1";
		return (int) getCount(sql);
		}catch(RuntimeException e){
			LOG.error("dao获取出库列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao获取出库列表失败",e);
		}
	}

	/**
	 * @Title getExportOutbound
	 * @Descrption:TODO
	 * @param:@param startTime
	 * @param:@param endTime
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月3日上午10:42:49
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getExportOutbound(String startTime, String endTime) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT  b.`name` shipName,c.refName shipRefName,DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') arrivalTime,")
				.append(" (SELECT FROM_UNIXTIME(d.leaveTime,'%Y-%m-%d')  from t_pcs_work d where d.arrivalId=a.id LIMIT 0,1) leaveTime,")
				.append(" b.loadCapacity, GROUP_CONCAT(d.flow) originalArea ,e.`name` shipAgentName,g.`name` berthName,GROUP_CONCAT(h.name) productName,")
				.append(" GROUP_CONCAT(i.`name`) clientName,GROUP_CONCAT(d.goodsTotal) goodsPlan")
				.append(" FROM t_pcs_arrival a LEFT JOIN t_pcs_ship b on a.shipId=b.id")
				.append("  LEFT JOIN t_pcs_ship_ref c ON c.id=a.shipRefId ")
				.append(" LEFT JOIN t_pcs_ship_agent e ON e.id=a.shipAgentId")
				.append(" LEFT JOIN t_pcs_berth_program f on f.arrivalId=a.id")
				.append(" LEFT JOIN t_pcs_berth g on g.id=f.berthId")
				.append("  LEFT JOIN t_pcs_arrival_plan d on d.arrivalId=a.id  ")
				.append(" LEFT JOIN t_pcs_goods j on j.id=d.goodsId")
				.append(" LEFT JOIN t_pcs_product h on h.id=j.productId")
				.append(" LEFT JOIN t_pcs_client i ON i.id=j.clientId")
				.append("  WHERE (a.type=2 or a.type=5) AND  c.refName!='转输' AND")
				.append(" a.status=54 ");
			   if(!Common.isNull(startTime)){
				   sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')>='").append(startTime).append("'");
			   }	
			   if(!Common.isNull(endTime)){
				   sql.append(" AND DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d')<='").append(endTime).append("'");
			   }
				sql.append(" GROUP BY a.id ORDER BY a.arrivalStartTime ASC");
			return executeQuery(sql.toString());
		}catch (RuntimeException e){
			LOG.debug("dao 回退失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao 回退失败",e);
		}
	}


}
