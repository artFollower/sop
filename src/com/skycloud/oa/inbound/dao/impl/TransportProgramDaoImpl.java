package com.skycloud.oa.inbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.TransportProgramDao;
import com.skycloud.oa.inbound.dto.TransportProgramDto;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.planmanager.dto.PlanManagerDto;
import com.skycloud.oa.utils.Common;
@Component
public class TransportProgramDaoImpl extends BaseDaoImpl implements TransportProgramDao {

	private static Logger LOG = Logger.getLogger(TransportProgramDaoImpl.class);

	@Override
	public int addTransportProgram(TransportProgram transportProgram) throws OAException {
		try {
			  String sql="INSERT INTO t_pcs_transport_program (arrivalId, type,orderNum, productId) SELECT DISTINCT "
	+transportProgram.getArrivalId()+","+0+",0,"+"productId FROM t_pcs_cargo WHERE arrivalId = "+transportProgram.getArrivalId()+" AND productId NOT IN ("
			+"SELECT productId FROM t_pcs_transport_program WHERE arrivalId = "+transportProgram.getArrivalId()+" and productId is not null )"
           +" UNION ALL SELECT DISTINCT "
	+transportProgram.getArrivalId()+","+1+",0,"+" productId FROM t_pcs_cargo WHERE arrivalId = "+transportProgram.getArrivalId()+" AND productId NOT IN ("
			+"SELECT productId FROM t_pcs_transport_program WHERE arrivalId = "+transportProgram.getArrivalId()+" and productId is not null )";
			  return insert(sql);
			} catch (RuntimeException e) {
			LOG.error("dao添加接卸方案失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加接卸方案失败", e);
			}
	}
	@Override
	public int addUnloadingTransportProgram(TransportProgram transportProgram)
			throws OAException {
		try{
			String sql=" insert into t_pcs_transport_program (arrivalId,type,orderNum,productId) select distinct "+transportProgram.getArrivalId()+",0,"+transportProgram.getOrderNum()
					+","+transportProgram.getProductId();
//					+" union all select distinct "+transportProgram.getArrivalId()+",1,"+transportProgram.getOrderNum()+","+transportProgram.getProductId();
			return insert(sql);
		}catch (RuntimeException e) {
			LOG.error("dao添加接卸方案失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加接卸方案失败", e);
			}
	};
	public int addBackFlowPlanTransportProgram(TransportProgram transportProgram) throws OAException{
		try{
			return Integer.valueOf(save(transportProgram).toString());
		}catch (RuntimeException e) {
			LOG.error("dao添加倒灌方案失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加倒灌方案失败", e);
			}
	}
	@Override
	public void deleteTransportProgram(String id) throws OAException {
		try{
			String sql="delete from t_pcs_transport_program where id in ("+id+")";
			
			execute(sql);
		}catch (RuntimeException e) {
			LOG.error("dao添加靠泊方案失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加靠泊方案失败", e);
			}
	}

	@Override
	public Map<String, Object> getTransportProgramById(TransportProgramDto tDto) throws OAException {
		try {
			StringBuilder sql= new StringBuilder();
			sql.append("select a.id,DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') arrivalTime,from_unixtime(a.createTime) createTime,a.comment,")
			.append("CONCAT(c.name,'/',d.refName) shipName,e.name productName,a.svg,f.name createUserName,g.name reviewUserName,h.name reviewCraftUserName, ")
			.append("(SELECT g.name from t_pcs_berth g,t_pcs_berth_program h where g.id=h.berthId and h.arrivalId=b.id) berthName, ")
			.append("(SELECT ROUND(SUM(m.goodsPlan),3) from t_pcs_cargo m where m.arrivalId=a.arrivalId and m.productId=a.productId) count,")
			.append("(SELECT GROUP_CONCAT(DISTINCT g.NAME) FROM t_pcs_trans f,t_pcs_tube g WHERE f.transportId=a.id and f.tubeId=g.id)  tubeInfo,")
			.append("(SELECT GROUP_CONCAT(DISTINCT i.CODE) FROM t_pcs_store h,t_pcs_tank i WHERE h.transportId = a.id AND i.id = h.tankId) tankInfo  ")
			.append(" from t_pcs_transport_program a ")
			.append("LEFT JOIN t_pcs_arrival b ON a.arrivalId=b.id ")
			.append("LEFT JOIN t_pcs_ship c ON c.id=b.shipId ")
			.append("LEFT JOIN t_pcs_ship_ref d ON d.id=b.shipRefId ")
			.append("LEFT JOIN t_pcs_product e ON e.id=a.productId ")
			.append("LEFT JOIN t_auth_user f on a.createUserId=f.id ")
			.append("LEFT JOIN t_auth_user g on a.reviewUserId=g.id ")
			.append("LEFT JOIN t_auth_user h on a.reviewCraftUserId=h.id ")
			.append(" where 1=1 ");
			if(tDto.getId()!=null)
				sql.append(" and a.id=").append(tDto.getId());
			return executeQueryOne(sql.toString());
		} catch (RuntimeException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询接卸方案失败", e);
		}
	}

	@Override
	public void updateTransportProgram(TransportProgram transportProgram) throws OAException {
		try{
			String sql="update t_pcs_transport_program set id=id";
			if(!Common.isNull(transportProgram.getType())){
				sql+=",type="+"'"+transportProgram.getType()+"'";
			}
			if(!Common.isNull(transportProgram.getFlow())){
				sql+=",flow="+"'"+transportProgram.getFlow()+"'";
			}
			if(!Common.isNull(transportProgram.getSvg())){
				sql+=",svg="+"'"+transportProgram.getSvg()+"'";
			}
			if(!Common.isNull(transportProgram.getNode())){
				sql+=",node="+"'"+transportProgram.getNode()+"'";
			}
			if(!Common.isNull(transportProgram.getComment())){
				sql+=",comment="+"'"+transportProgram.getComment()+"'";
			}
			if(!Common.isNull(transportProgram.getArrivalId())){
				sql+=",arrivalId="+transportProgram.getArrivalId();
			}
			if(transportProgram.getOrderNum()!=null){
				sql+=" ,orderNum="+transportProgram.getOrderNum();
			}
			if(!Common.isNull(transportProgram.getTubeInfo())){
				sql+=",tubeInfo="+"'"+transportProgram.getTubeInfo()+"'";
			}
			if(!Common.isNull(transportProgram.getTankInfo())){
				sql+=",tankInfo="+"'"+transportProgram.getTankInfo()+"'";
			}
			if(!Common.isNull(transportProgram.getDockWork())){
				sql+=",dockWork="+"'"+transportProgram.getDockWork()+"'";
			}
			if(!Common.isNull(transportProgram.getTubeWork())){
				sql+=",tubeWork="+"'"+transportProgram.getTubeWork()+"'";
			}
			if(!Common.isNull(transportProgram.getPumpInfo())){
				sql+=",pumpInfo="+"'"+transportProgram.getPumpInfo()+"'";
			}
			if(!Common.isNull(transportProgram.getPowerWork())){
				sql+=",powerWork="+"'"+transportProgram.getPowerWork()+"'";
			}if(!Common.isNull(transportProgram.getCreateUserId())){
				sql+=",createUserId="+transportProgram.getCreateUserId();
			}
			if(!Common.isNull(transportProgram.getCreateTime())){
				sql+=",createTime="+transportProgram.getCreateTime();
			}
			if(!Common.isNull(transportProgram.getReviewUserId())){
				sql+=",reviewUserId="+transportProgram.getReviewUserId();
			}
			if(!Common.isNull(transportProgram.getReviewTime())){
				sql+=",reviewTime="+transportProgram.getReviewTime();
			}
			if(!Common.isNull(transportProgram.getReviewCraftUserId())){
				sql+=",reviewCraftUserId="+transportProgram.getReviewCraftUserId();
			}
			if(!Common.isNull(transportProgram.getReviewCraftTime())){
				sql+=",reviewCraftTime="+transportProgram.getReviewCraftTime();
			}
			if(transportProgram.getStatus()!=null){
				sql+=",status="+transportProgram.getStatus();
			}
			if(transportProgram.getNoticeCodeA()!=null){
				sql+=",noticeCodeA='"+transportProgram.getNoticeCodeA()+"'";
			}
			if(transportProgram.getNoticeCodeB()!=null){
				sql+=",noticeCodeB='"+transportProgram.getNoticeCodeB()+"'";
			}
			if(transportProgram.getOpenPumpTime()!=null&&transportProgram.getOpenPumpTime()!=-1)
				sql+=",openPumpTime="+transportProgram.getOpenPumpTime();
			if(transportProgram.getStopPumpTime()!=null&&transportProgram.getStopPumpTime()!=-1)
				sql+=",stopPumpTime="+transportProgram.getStopPumpTime();
			sql+=" where 1=1 and id="+transportProgram.getId();
			executeUpdate(sql);
		}catch (RuntimeException e){
			LOG.error("dao更新传输方案失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新传输方案失败", e);
		}
	}

  public void cleanTransportProgram(int arrivalId) throws OAException{
	  try{
	  String sql="update t_pcs_transport_program set id=id ,comment=null,flow=null,svg=null,node=null,tubeInfo=null,tankInfo=null"
	  		+ ",dockWork=null,tubeWork=null,powerWork=null,createUserId=null,createTime=null,reviewUserId=null,reviewTime=null"
	  		+ ",reviewCraftUserId=null,reviewCraftTime=null,status=0,noticeCodeA=null,noticeCodeB=null where orderNum=0 and arrivalId="+arrivalId;
	  executeUpdate(sql);
	  String sql1="delete from t_pcs_transport_program where orderNum!=0 and arrivalId="+arrivalId;
	  execute(sql1);
	  }catch(RuntimeException e){
		  LOG.error("");
	  }
	  
	  
  }
@Override
public int addBackFlowTransportProgram(TransportProgram transportProgram)
		throws OAException {
	try{
		String sql=" insert into t_pcs_transport_program (arrivalId,type,orderNum,productId) select distinct "+transportProgram.getArrivalId()+",1,t.orderNum,"+transportProgram.getProductId()+" from " 
		+"(SELECT (max(IFNULL(orderNum,0))+1) orderNum from t_pcs_transport_program where arrivalId="+transportProgram.getArrivalId()+" and type=1 and productId="+transportProgram.getProductId()+" ) t";
		return insert(sql);
	}catch (RuntimeException e) {
		LOG.error("dao添加打循环失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加打循环失败", e);
		}
}
@Override
public List<Map<String, Object>> getUnloadingPlanList(
		PlanManagerDto planManagerDto, int startRecord, int maxresult)
		throws OAException {
	try {
		 String sql="SELECT a.id id ,a.arrivalId arrivalId ,FROM_UNIXTIME(a.createTime,'%Y-%m-%d %H:%i') createTime,a.productId productId,"
					+" c.`name` productName,b.type arrivalType,a.orderNum orderNum,a.`status` ,"
					+" (SELECT g.name from t_pcs_berth g,t_pcs_berth_program h where g.id=h.berthId and h.arrivalId=b.id) berthName,"
					+" DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') arrivalTime,CONCAT(e.`name`,'/',d.refName) shipName,d.refName refName,"
					+" (SELECT ROUND(SUM(m.goodsPlan),3) from t_pcs_cargo m where m.arrivalId=a.arrivalId and m.productId=a.productId) count,"
					+" (SELECT GROUP_CONCAT(DISTINCT g.NAME) FROM t_pcs_trans f,t_pcs_tube g WHERE f.transportId=a.id and f.tubeId=g.id)  tube,"
					+" (SELECT GROUP_CONCAT(DISTINCT i.CODE) FROM t_pcs_store h,t_pcs_tank i WHERE h.transportId = a.id AND i.id = h.tankId) tank "
					+" from t_pcs_transport_program a ,t_pcs_arrival b,t_pcs_product c,t_pcs_ship_ref d,t_pcs_ship e "
					+" WHERE a.arrivalId=b.id and b.type!=3 and e.id=b.shipId "
					+" and d.id=b.shipRefId  and a.productId=c.id  and a.type=0 and a.createTime is not NULL ";
					 if(!Common.isNull(planManagerDto.getShipName())){
						 sql+=" and (e.name like '%"+planManagerDto.getShipName()+"%' or d.refName like '%"+planManagerDto.getShipName()+"%')";
					 }
					 if(planManagerDto.getProductId()!=null){
						 sql+=" and a.productId="+planManagerDto.getProductId();
					 }
					 if(planManagerDto.getStartTime()!=null&&planManagerDto.getStartTime()!=-1){
						 sql+=" and a.createTime>="+planManagerDto.getStartTime();
					 }
					 if(planManagerDto.getEndTime()!=null&&planManagerDto.getEndTime()!=-1){
						 sql+=" and a.createTIme<="+planManagerDto.getEndTime();
					 }
					 if(planManagerDto.getStatus()!=null&&planManagerDto.getStatus()!=-1){
						 if(planManagerDto.getStatus()==1){
							 sql+=" and a.status in (1,4,5) ";
						 }else{
						 sql+=" and a.status="+planManagerDto.getStatus();
						 }
					 }
					 sql+=" ORDER BY a.createTime desc";
		    if(maxresult!=0){
		    	sql+=" limit "+startRecord+","+maxresult;
		    }
		 return executeQuery(sql);
		} catch (RuntimeException e) {
		LOG.error("dao获取接卸方案列表失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取接卸方案列表失败", e);
		}
}
@Override
public int getUnloadingPlanCount(PlanManagerDto planManagerDto)
		throws OAException {
	try {
		 String sql="SELECT count(1) "
					+" from t_pcs_transport_program a,t_pcs_arrival b,t_pcs_product c,t_pcs_ship_ref d,t_pcs_ship e "
					+ " WHERE a.arrivalId=b.id  and a.productId=c.id  and a.type=0 and b.type!=3 and a.createTime is not NULL "
					+" and d.id=b.shipRefId and e.id=b.shipId";
					 if(!Common.isNull(planManagerDto.getShipName())){
						 sql+=" and (e.name like '%"+planManagerDto.getShipName()+"%' or d.refName like '%"+planManagerDto.getShipName()+"%')";
					 }
					 if(planManagerDto.getProductId()!=null){
						 sql+=" and a.productId="+planManagerDto.getProductId();
					 }
					 if(planManagerDto.getStartTime()!=null&&planManagerDto.getStartTime()!=-1){
						 sql+=" and a.createTime>="+planManagerDto.getStartTime();
					 }
					 if(planManagerDto.getEndTime()!=null&&planManagerDto.getEndTime()!=-1){
						 sql+=" and a.createTIme<="+planManagerDto.getEndTime();
					 }
					 if(planManagerDto.getStatus()!=null&&planManagerDto.getStatus()!=-1){
						 if(planManagerDto.getStatus()==1){
							 sql+=" and a.status in (1,4,5) ";
						 }else{
						 sql+=" and a.status="+planManagerDto.getStatus();
						 }
					 }
					 sql+=" ORDER BY a.createTime desc";
		 return (int)getCount(sql);
		} catch (RuntimeException e) {
		LOG.error("dao获取接卸方案列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取接卸方案列表数量失败", e);
		}
}
@Override
public List<Map<String,Object>> getBackFlowList(
		PlanManagerDto planManagerDto, int startRecord, int maxresult)
		throws OAException {
	try {
		String sql="SELECT a.id id ,a.arrivalId arrivalId ,a.type type,FROM_UNIXTIME(a.createTime,'%Y-%m-%d %H:%i') createTime,a.productId productId,"
				+" g.`name` productName,b.type arrivalType,a.orderNum orderNum,a.`status` ,f.transferPurpose,"
				+" (SELECT g.name from t_pcs_berth g,t_pcs_berth_program h where g.id=h.berthId and h.arrivalId=b.id) berthName,"
				+" CONCAT(c.name,'/',e.refName) shipName,DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') arrivalTime,"
				+" f.tankCount count,f.outTankNames outTank,f.inTankNames inTank,f.pupmNames pump,f.tubeNames tube"
				+" from t_pcs_transport_program a LEFT JOIN t_pcs_arrival b on a.arrivalId = b.id "
				+" LEFT JOIN t_pcs_ship c ON c.id=b.shipId "
				+" LEFT JOIN t_pcs_ship_ref e ON e.id=b.shipRefId "
				+" LEFT JOIN t_pcs_transport_info f on f.transportId=a.id "
				+" LEFT JOIN t_pcs_product g on a.productId = g.id WHERE  a.createTime is not NULL ";
		         if(!Common.isNull(planManagerDto.getType())){
                    sql+=" and a.type="+planManagerDto.getType();		        	 
		         }
				if(!Common.isNull(planManagerDto.getShipName())){
					sql+=" and a.arrivalId is not null and (c.name like '%"+planManagerDto.getShipName()+"%' or e.refName like '%"+planManagerDto.getShipName()
					+"%') ";
				}
				 if(planManagerDto.getProductId()!=null){
					 sql+=" and a.productId="+planManagerDto.getProductId();
				 }
				 if(planManagerDto.getStartTime()!=null&&planManagerDto.getStartTime()!=-1){
					 sql+=" and a.createTime>="+planManagerDto.getStartTime();
				 }
				 if(planManagerDto.getEndTime()!=null&&planManagerDto.getEndTime()!=-1){
					 sql+=" and a.createTIme<="+planManagerDto.getEndTime();
				 }
				 if(planManagerDto.getStatus()!=null&&planManagerDto.getStatus()!=-1){
					 if(planManagerDto.getStatus()==1){
						 sql+=" and a.status in (1,4,5) ";
					 }else{
					 sql+=" and a.status="+planManagerDto.getStatus();
					 }
				 }
				 sql+=" ORDER BY a.createTime desc";
	    if(maxresult!=0){
	    	sql+=" limit "+startRecord+","+maxresult;
	    }
		 return executeQuery(sql);
		} catch (RuntimeException e) {
		LOG.error("dao获取倒罐方案列表失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取倒罐方案列表失败", e);
		}
}
@Override
public int getBackFlowCount(PlanManagerDto planManagerDto)
		throws OAException {
	try {
		String sql="SELECT count(a.id) "
				+" from t_pcs_transport_program a LEFT JOIN t_pcs_arrival b on a.arrivalId = b.id "
				+" LEFT JOIN t_pcs_ship c ON c.id=b.shipId "
				+" LEFT JOIN t_pcs_ship_ref e ON e.id=b.shipRefId "
				+ " LEFT JOIN t_pcs_product g on a.productId = g.id WHERE  a.type in (1,3) and a.createTime is not NULL ";
				  if(!Common.isNull(planManagerDto.getType())){
		              sql+=" and a.type="+planManagerDto.getType();		        	 
			         }
				if(!Common.isNull(planManagerDto.getShipName())){
					sql+=" and a.arrivalId is not null and (c.name like '%"+planManagerDto.getShipName()+"%' or e.refName like '%"+planManagerDto.getShipName()
					+"%') ";
				}
				 if(planManagerDto.getProductId()!=null){
					 sql+=" and a.productId="+planManagerDto.getProductId();
				 }
				 if(planManagerDto.getStartTime()!=null&&planManagerDto.getStartTime()!=-1){
					 sql+=" and a.createTime>="+planManagerDto.getStartTime();
				 }
				 if(planManagerDto.getEndTime()!=null&&planManagerDto.getEndTime()!=-1){
					 sql+=" and a.createTIme<="+planManagerDto.getEndTime();
				 }
				 if(planManagerDto.getStatus()!=null&&planManagerDto.getStatus()!=-1){
					 if(planManagerDto.getStatus()==1){
						 sql+=" and a.status in (1,4,5) ";
					 }else{
					 sql+=" and a.status="+planManagerDto.getStatus();
					 }
				 }
	   return (int)getCount(sql);
		} catch (RuntimeException e) {
		LOG.error("dao获取倒罐方案列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取倒罐方案列表列表失败", e);
		}
}
/**
 * @throws OAException 
 * @Title rebackTransportProgram
 * @Descrption:TODO
 * @param:@param id
 * @param:@throws OAE
 * @auhor jiahy
 * @date 2016年7月26日下午8:31:28
 * @throws
 */
	@Override
	public void rebackTransportProgram(Integer id) throws OAException{
		try{
		if(id!=null){
			StringBuilder sql=new StringBuilder();
             sql.append(" update t_pcs_transport_program set status=0 ,comment=null,createUserId=null,createTime=null,reviewUserId=null,")
	  		.append("reviewTime=null,reviewCraftUserId=null,reviewCraftTime=null where id=").append(id);
			 executeUpdate(sql.toString());
		}
	} catch (RuntimeException e) {
		LOG.error("dao获取倒罐方案列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取倒罐方案列表列表失败", e);
		}
	}
/**
 * @Title getChangeTankProgramById
 * @Descrption:TODO
 * @param:@param transportId
 * @param:@return
 * @param:@throws OAException
 * @auhor jiahy
 * @date 2017年1月3日上午10:33:31
 * @throws
 */
@Override
public Map<String, Object> getChangeTankProgramById(int transportId)
		throws OAException {
	try{
		StringBuilder sql=new StringBuilder();
		sql.append("select a.id,e.name productName, from_unixtime(a.createTime) createTime,a.svg,f.name createUserName,g.name reviewUserName, ")
		.append("h.name reviewCraftUserName,i.tankCount count,i.outTankNames outTank,i.inTankNames inTank,")
		.append("i.pupmNames pump,i.tubeNames tube,i.transferPurpose,i.message,k.refName shipName")
		.append(" from t_pcs_transport_program a ")
		.append("LEFT JOIN t_pcs_product e ON e.id=a.productId ")
		.append("LEFT JOIN t_auth_user f on a.createUserId=f.id ")
		.append("LEFT JOIN t_auth_user g on a.reviewUserId=g.id ")
		.append("LEFT JOIN t_auth_user h on a.reviewCraftUserId=h.id ")
		.append("LEFT JOIN t_pcs_transport_info i on i.transportId=a.id ")
		.append("LEFT JOIN t_pcs_arrival j ON j.id=a.arrivalId ")
		.append("LEFT JOIN t_pcs_ship_ref k ON k.id=j.shipRefId ")
		.append(" where 1=1 ");
			sql.append(" and a.id=").append(transportId);
		return executeProcedureOne(sql.toString());
	} catch (RuntimeException e) {
		LOG.error("dao获取倒罐方案列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取倒罐方案列表列表失败", e);
		}
}
/**
 * @throws OAException 
 * @Title getMsgById
 * @Descrption:TODO
 * @param:@param id
 * @param:@return
 * @auhor jiahy
 * @date 2017年7月14日下午2:50:53
 * @throws
 */
@Override
public Map<String, Object> getMsgById(Integer id) {
	try {
		 StringBuffer sql = new StringBuffer();
		 sql.append("select status from t_pcs_transport_program where 1=1 ");
		 if(!Common.isNull(id)){
			 sql.append(" and id=").append(id);
		 }else{
			 sql.append(" and id = 0");
		 }
	
		return executeQueryOne(sql.toString());
	} catch (OAException e) {
		e.printStackTrace();
	}
	return null;
}

}
