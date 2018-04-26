package com.skycloud.oa.inbound.dao.impl;


import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.WorkDao;
import com.skycloud.oa.inbound.model.Notification;
import com.skycloud.oa.inbound.model.Work;
import com.skycloud.oa.utils.Common;
@Component
public class WorkDaoImpl extends BaseDaoImpl implements WorkDao {

	private static Logger LOG = Logger.getLogger(WorkDaoImpl.class);

	@Override
	public int addWork(Work work)  throws OAException{
		try {
			  String sql="insert into t_pcs_work (arrivalId,productId,status,orderNum) select DISTINCT "
			  		+ work.getArrivalId()+","+"productId,2,0 from t_pcs_cargo where arrivalId="+work.getArrivalId()+""
			  				+ " and productId not in (select productId from t_pcs_work where arrivalId="+work.getArrivalId()+" and productId is not null)";
			  return insert(sql);
			} catch (RuntimeException e) {
			LOG.error("dao添加入库作业失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加入库作业失败", e);
			}
	}

	@Override
	public int addUnloadingWork(Work work) throws OAException {
	try{ 
		String sql="insert into t_pcs_work (arrivalId,productId,status,orderNum) select distinct "+work.getArrivalId()+","+work.getProductId()+",5,"+work.getOrderNum();
		
		return insert(sql);
	} catch (RuntimeException e) {
		LOG.error("dao添加入库作业失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加入库作业失败", e);
		}
	}

	
	@Override
	public void deleteWork(String id)  throws OAException{
		try{
			String sql="delete from t_pcs_work where id in ("+id+")";
			execute(sql);
		}catch (RuntimeException e) {
			LOG.error("dao添加入库作业失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加入库作业失败", e);
			}
	}
     
	
	
	
	
	@Override
	public void updateWork(Work work)  throws OAException {
		try{
			String sql="update t_pcs_work set id=id";
			if(null!=work.getStayTime()){
				if(work.getStayTime()==-1){
					sql+=",statyTime=null";
				}else{
				sql+=",stayTime="+work.getStayTime();
				}
			}
			if(null!=work.getType()){
				sql+=",type="+work.getType();
			}
			if(null!=work.getCheckTime()){
				if(work.getCheckTime()==-1){
				sql+=",checkTime=null";	
				}else{
				sql+=",checkTime="+work.getCheckTime();
				}
			}
			if(null!=work.getArrivalTime()){
				if(work.getArrivalTime()==-1){
					sql+=",arrivalTime=null";
				}else{
				sql+=",arrivalTime="+work.getArrivalTime();
				}
			}
			if(null!=work.getOpenPump()){
				if(work.getOpenPump()==-1){
					sql+=",openPump=null";
				}else{
				sql+=",openPump="+work.getOpenPump();
				}
			}
			if(null!=work.getStopPump()){
				if(work.getStopPump()==-1){
					sql+=",stopPump=null";
				}else{
				sql+=",stopPump="+work.getStopPump();
				}
			}
			if(null!=work.getLeaveTime()){
				if(work.getLeaveTime()==-1){
					sql+=",leaveTime=null";
				}else{
				sql+=",leaveTime="+work.getLeaveTime();
				}
			}
			if(null!=work.getTearPipeTime()){
				if(work.getTearPipeTime()==-1){
					sql+=",tearPipeTime=null";
				}else{
				sql+=",tearPipeTime="+work.getTearPipeTime();
				}
			}
			if(null!=work.getArrivalId()){
				sql+=",arrivalId="+work.getArrivalId();
			}
			if(null!=work.getWorkTime()){
				if(work.getWorkTime()==-1){
					sql+=",workTime=null";
				}else{
					sql+=",workTime="+work.getWorkTime();
				}
			}
			if(work.getOrderNum()!=null){
				sql+=",orderNum="+work.getOrderNum();
			}
			if(null!=work.getDockCheck()){
				sql+=",dockCheck='"+work.getDockCheck()+"'";
			}
			if(null!=work.getEvaluate()){
				sql+=",evaluate='"+work.getEvaluate()+"'";
			}
			if(null!=work.getEvaluateUserId()){
				sql+=",evaluateUserId="+work.getEvaluateUserId();
			}
			if(null!=work.getEvaluateUser()){
				sql+=",evaluateUser='"+work.getEvaluateUser()+"'";
			}
			if(null!=work.getEvaluateTime()){
				sql+=",evaluateTime="+work.getEvaluateTime();
			}
			if(null!=work.getDynamicUserId()){
				sql+=",dynamicUserId="+work.getDynamicUserId();
			}
			if(null!=work.getDynamicContent()){
				sql+=",dynamicContent='"+work.getDynamicContent()+"'";
			}
			if(null!=work.getDockUserId()){
				sql+=",dockUserId="+work.getDockUserId();
			}
			if(null!=work.getDockContent()){
				sql+=",dockContent='"+work.getDockContent()+"'";
			}
			if(null!=work.getShipClientId()){
				sql+=",shipClientId="+work.getShipClientId();
			}
			if(null!=work.getShipClientContent()){
				sql+=",shipClientContent='"+work.getShipClientContent()+"'";
			}
			if(null!=work.getUnusualLog()){
				sql+=",unusualLog="+"'"+work.getUnusualLog()+"'";
			}
			if(null!=work.getStatus()){
				sql+=",status="+work.getStatus();
			}
			if(work.getReviewStatus()!=null){
				sql+=",reviewStatus="+work.getReviewStatus();
			}
			if(!Common.isNull(work.getCreateUserId())){
				sql+=",createUserId="+work.getCreateUserId();
			}
			if(!Common.isNull(work.getCreateTime())){
				sql+=",createTime="+work.getCreateTime();
			}
			if(!Common.isNull(work.getReviewUserId())){
				sql+=",reviewUserId="+work.getReviewUserId();
			}
			if(!Common.isNull(work.getReviewTime())){
				sql+=",reviewTime="+work.getReviewTime();
			}
			if(!Common.isNull(work.getCreateRUserId())){
				sql+=",createRUserId="+work.getCreateRUserId();
			}
			if(!Common.isNull(work.getCreateRTime())){
				sql+=",createRTime="+work.getCreateRTime();
			}
			if(null!=work.getDescription()){
				sql+=",description='"+work.getDescription()+"'";
			}
			sql+=" where 1=1 ";
			if(!Common.isNull(work.getId())){
				sql+="  and id="+work.getId();
			}else if(!Common.isNull(work.getArrivalId())){
				sql+=" and arrivalId="+work.getArrivalId();
			}
			executeUpdate(sql);
			System.out.println(sql);
			
			if(!Common.isNull(work.getArrivalTime())&&work.getArrivalTime()!=-1&&!Common.isNull(work.getId())){
				//同一艘船的到港时间、离港时间、服务评价、评价人同步。
				String sql1=" update t_pcs_work a,t_pcs_work b set b.arrivalTime=a.arrivalTime,b.leaveTime=a.leaveTime," +
						"b.evaluate=a.evaluate,b.evaluateUserId=a.evaluateUserId,b.evaluateUser=a.evaluateUser where a.id="+work.getId()+" and a.arrivalId= b.arrivalId";
				System.out.println(sql1);
				executeUpdate(sql1);
			}
			
		}catch (RuntimeException e){
			LOG.error("dao更新入库作业失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新入库作业失败", e);
		}
	}

	
	@Override
	public List<Map<String, Object>> getOPLogList(int i, long startTime,
			long endTime, int start, int limit) throws OAException {
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
		String sql="select a.description,a.id workId, c.id dispatchId,b.id arrivalId, b.shipId,j.name shipCode, k.refName shipName,m.name productName,a.productId,a.tankAmount,h.code tankCode,h.id tankId,d.id transportId," +
//				"j.name tubeName," +
				" o.name berthName,FROM_UNIXTIME(a.arrivalTime) mArrivalTime,FROM_UNIXTIME(a.openPump) mOpenPump,FROM_UNIXTIME(a.stopPump) mStopPump,FROM_UNIXTIME(a.tearPipeTime) mTearPipeTime," +
				"FROM_UNIXTIME(a.leaveTime) mLeaveTime," +
				"a.arrivalTime,a.openPump,a.stopPump,a.tearPipeTime,a.leaveTime,d.openPumpTime,d.stopPumpTime," +
				"i.startLevel,i.endLevel,i.startWeight,i.endWeight,i.startTemperature,i.endTemperature,i.realAmount,i.measureAmount,i.differAmount," +
//				"p.name clientName," +
				" a.notificationUserId, a.evaluateUserId, a.evaluate,a.evaluateUser evaluateUserName,r.name notificationUserName,a.notificationNum,a.notification,a.notificationTime from t_pcs_work a " +
				" LEFT JOIN t_pcs_arrival b on a.arrivalId=b.id " +
				" LEFT JOIN t_pcs_dispatch c on b.dispatchId=c.id " +
				" LEFT JOIN t_pcs_transport_program d on d.arrivalId=b.id  " +
//				" LEFT JOIN t_pcs_work_check e on e.transId=d.id " +
//				" LEFT JOIN t_pcs_job_check f on f.transId=d.id " +
				" LEFT JOIN view_store g on g.arrivalId=b.id and g.productId=d.productId" +
				" LEFT JOIN t_pcs_tank h on g.tankId=h.id " +
				" LEFT JOIN t_pcs_tanklogstore i on i.arrivalId=b.id and i.tankId=g.tankId " +
				" LEFT JOIN t_pcs_ship_ref k on k.id=b.shipRefId " +
				" LEFT JOIN t_pcs_ship j on j.id=k.shipId " +
				" LEFT JOIN t_pcs_product m on m.id=a.productId " +
				" LEFT JOIN t_pcs_berth_program n on n.arrivalId=b.id " +
				" LEFT JOIN t_pcs_berth o on o.id=n.berthId " +
//				" LEFT JOIN t_pcs_client p on p.name=l.clientId " +
				" LEFT JOIN t_auth_user q on q.id=a.evaluateUserId " +
				" LEFT JOIN t_auth_user r on r.id=a.notificationUserId where 1=1 and d.type=0 ";
//				sql+=" and a.productId=case  when h.productId is null then a.productId when h.productId is not null then h.productId end  ";
        		sql+=" and a.productId=d.productId ";
		if(startTime!=0&& endTime!=0){
      	  sql+="  and a.arrivalTime>="+startTime+" and a.arrivalTime<"+endTime+" and a.type in (1,3)";
        }
		sql+=" and d.orderNum=0 and a.orderNum=0";
		
		sql+=" order by a.arrivalTime ASC, g.tankId DESC";
		if(limit!=0){
			sql+=" limit " + start + "," + limit;
		}
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
		}
	}
	
	
	@Override
	public List<Map<String, Object>> getWorkList(Integer arrivalId, long startTime, long endTime)  throws OAException{
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
		String sql="select a.description,a.id workId, c.id dispatchId,b.id arrivalId, b.shipId, k.refName shipName,m.name productName,a.productId,a.tankAmount,h.code tankCode,d.id transportId," +
//				"j.name tubeName," +
				" o.name berthName,a.arrivalTime,a.openPump,a.stopPump,a.tearPipeTime,a.leaveTime," +
				"g.startLevel,g.endLevel,g.startWeight,g.endWeight,g.startTemperature,g.endTemperature,g.realAmount,g.measureAmount,g.differAmount," +
//				"p.name clientName," +
				" a.notificationUserId, a.evaluateUserId, a.evaluate,a.evaluateUser evaluateUserName,r.name notificationUserName,a.notificationNum,a.notification,a.notificationTime from t_pcs_work a " +
				" LEFT JOIN t_pcs_arrival b on a.arrivalId=b.id " +
				" LEFT JOIN t_pcs_dispatch c on b.dispatchId=c.id " +
				" LEFT JOIN t_pcs_transport_program d on d.arrivalId=b.id " +
//				" LEFT JOIN t_pcs_work_check e on e.transId=d.id " +
//				" LEFT JOIN t_pcs_job_check f on f.transId=d.id " +
				" LEFT JOIN t_pcs_store g on g.transportId=d.id " +
				" LEFT JOIN t_pcs_tank h on g.tankId=h.id " +
//				" LEFT JOIN t_pcs_trans i on i.storeId=g.id " +
//				" LEFT JOIN t_pcs_tube j on j.id=i.tubeId " +
				" LEFT JOIN t_pcs_ship_ref k on k.id=b.shipRefId " +
				" LEFT JOIN t_pcs_product m on m.id=a.productId " +
				" LEFT JOIN t_pcs_berth_program n on n.arrivalId=b.id " +
				" LEFT JOIN t_pcs_berth o on o.id=n.berthId " +
//				" LEFT JOIN t_pcs_client p on p.name=l.clientId " +
				" LEFT JOIN t_auth_user q on q.id=a.evaluateUserId " +
				" LEFT JOIN t_auth_user r on r.id=a.notificationUserId where 1=1 and d.type=0 ";
				sql+=" and a.productId=case  when h.productId is null then a.productId when h.productId is not null then h.productId end  ";
        		sql+=" and a.productId=d.productId ";
		if(startTime!=0&& endTime!=0){
      	  sql+="  and a.arrivalTime>="+startTime+" and a.arrivalTime<"+endTime+" and a.type=1";
        }
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
		}
	}

	@Override
	public List<Map<String, Object>> getLogInfo(long startTime, long endTime)  throws OAException{
		String sql="select c.*,c.id dispatchId,c.sWeather weatherName,c.sWindDirection windDName,c.sWindPower windPName,d.name dispatchUserName,e.name deliveryUserName,f.name dayWordUserName,g.name dockUserName,h.name powerUserName " +
				" from t_pcs_dispatch c " +
				" LEFT JOIN t_auth_user d on d.id=c.dispatchUserId " +
				" LEFT JOIN t_auth_user e on e.id=c.deliveryUserId " +
				" LEFT JOIN t_auth_user f on f.id=c.dayWordUserId " +
				" LEFT JOIN t_auth_user g on g.id=c.dockUserId " +
				" LEFT JOIN t_auth_user h on h.id=c.powerUserId " +
//				" LEFT JOIN t_pcs_weather i on i.key=c.weather " +
				" LEFT JOIN t_pcs_wind_direction j on j.key=c.windDirection " +
//				" LEFT JOIN t_pcs_wind_power k on k.key=c.windPower " +
				" where c.time="+startTime;
		
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
		}
	}

	@Override
	public List<Map<String, Object>> getLogIsHave(long startTime, long endTime) throws OAException {
		String sql="select a.arrivalId,a.arrivalTime, b.shipId from t_pcs_work a LEFT JOIN t_pcs_arrival b on b.id=a.arrivalId "+
				" where (b.type=1 or b.type=3) and  a.arrivalTime>="+startTime+" and a.arrivalTime<"+endTime +" and a.type=1";
		try {
			return executeQuery(sql);
		} catch (RuntimeException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
		}
	}

	@Override
	public List<Map<String, Object>> getWorkList(Integer id)
			throws OAException {
		try {
			String sql=" select a.id id,a.type workType,from_unixtime(a.checkTime) checkTime,from_unixtime(a.arrivalTime) arrivalTime,from_unixtime(a.openPump) openPump,from_unixtime(a.stopPump) stopPump, a.stayTime stayTime,from_unixtime(a.leaveTime) leaveTime,from_unixtime(a.tearPipeTime) tearPipeTime,a.tearPipeTime mTearPipeTime,"
					+ " a.arrivalId arrivalId, a.workTime workTime, a.evaluate evaluate, a.evaluateUserId evaluateUserId,a.evaluateUser evaluateUserName, from_unixtime(a.evaluateTime) evaluateTime,"
					+ " a.dynamicUserId dynamicUserId,c.name dynamicUserName,a.dynamicContent, a.dockUserId dockUserId,d.name dockUserName,a.dockContent,a.shipClientId shipClientId,e.name shipClientName,a.shipClientContent,"
					+ " a.unusualLog unusualLog,a.dockCheck dockCheck,j.name createRUserName,a.createRUserId,"
					+ " h.dispatchUser dispatchUser,h.dockUser dockUser,h.powerUser powerUser,"
					+ "a.createUserId ,f.name createUserName,from_unixtime(a.createTime) createTime,"
					+ "a.reviewUserId,i.name reviewUserName,from_unixtime(a.reviewTime) reviewTime,a.arrivalTime mArrivalTime"
					+ " from t_pcs_work a "
					+ " LEFT JOIN t_auth_user b on a.evaluateUserId=b.id"
					+ " LEFT JOIN t_auth_user c on a.dynamicUserId=c.id"
					+ " LEFT JOIN t_auth_user d on a.dockUserId=d.id"
					+ " LEFT JOIN t_auth_user f on a.createUserId=f.id"
					+ " LEFT JOIN t_auth_user e on a.shipClientId=e.id"
					+ " LEFT JOIN t_auth_user j on a.createRUserId=j.id"
					+" LEFT JOIN t_pcs_arrival g on g.id=a.arrivalId"
					+ " LEFT JOIN t_pcs_dispatch h on h.id=g.dispatchId"
					+ " LEFT JOIN t_auth_user i on a.reviewUserId=i.id"
					+ " where 1=1 ";
			if(id!=0){
				sql+=" and a.id="+id;
			}
			
			return  executeQuery(sql);
			} catch (RuntimeException e) {
			LOG.error("dao查询入库作业表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询入库作业表失败", e);
			}
	}

	@Override
	public List<Map<String, Object>> getConnectInfo(int tankId, long startTime)
			throws OAException {
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
				String sql="select DISTINCT k.refName shipName,m.name productName,h.productId,a.tankAmount,h.code tankCode,d.id transportId," +
						" o.name berthName,from_unixtime(a.arrivalTime) arrivalTime,d.type transportType,b.arrivalStartTime,g.id storeId from t_pcs_work a" +
						" LEFT JOIN t_pcs_arrival b on a.arrivalId=b.id " +
						" LEFT JOIN t_pcs_dispatch c on b.dispatchId=c.id " +
						" LEFT JOIN t_pcs_transport_program d on d.arrivalId=b.id " +
//						" LEFT JOIN t_pcs_work_check e on e.transId=d.id " +
//						" LEFT JOIN t_pcs_job_check f on f.transId=d.id " +
						" LEFT JOIN t_pcs_store g on g.transportId=d.id " +
						" LEFT JOIN t_pcs_tank h on g.tankId=h.id " +
//						" LEFT JOIN t_pcs_trans i on i.storeId=g.id " +
//						" LEFT JOIN t_pcs_tube j on j.id=i.tubeId " +
						" LEFT JOIN t_pcs_ship_ref k on k.id=b.shipRefId " +
						" LEFT JOIN t_pcs_product m on m.id=h.productId " +
						" LEFT JOIN t_pcs_berth_program n on n.arrivalId=b.id " +
						" LEFT JOIN t_pcs_berth o on o.id=n.berthId " +
//						" LEFT JOIN t_pcs_client p on p.name=l.clientId " +
						" LEFT JOIN t_auth_user q on q.id=a.evaluateUserId " +
						" LEFT JOIN t_auth_user r on r.id=a.notificationUserId where 1=1 and d.type=2 and h.productId=a.productId ";
				
				
//		        if(!Common.isNull(startTime)){
//		      	  sql+="  and a.arrivalTime>="+startTime+" and a.arrivalTime<"+(startTime+86400);
//		        }
		       sql+= "and case d.type when '0' then a.arrivalTime>="+startTime+" else b.arrivalStartTime>='"+new Timestamp(startTime * 1000)+"' end ";
		       
		       sql+= "and case d.type when '0' then a.arrivalTime<"+(startTime+86400)+" else b.arrivalStartTime<'"+new Timestamp((startTime + 86400) * 1000)+"' end ";
		       
		       
//		        if(!Common.isNull(startTime)){
//		        	sql+=" and b.arrivalStartTime>='"+new Timestamp(startTime * 1000)+"' and b.arrivalStartTime<'"+new Timestamp((startTime + 86400) * 1000)+"'";
//		        }
		        
		        if(!Common.isNull(tankId)){
		        	sql+=" and g.tankId="+tankId;
		        }
				try {
					System.out.println(sql);
					return executeQuery(sql);
				} catch (OAException e) {
					throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
				}
	}

	@Override
	public void addNotify(List<Notification> nList) throws OAException {
		try {
			for(Notification notify:nList){
				if(!Common.isNull(notify.getId())&&notify.getId()!=0){
					String sql="update t_pcs_work_notification set workId=workId";
					if(!Common.empty(notify.getNotification())){
						sql+=" , notification='"+notify.getNotification()+"'";
					}
					if(!Common.empty(notify.getNotificationNum())){
						sql+=" , notificationNum='"+notify.getNotificationNum()+"'";
					}
					if(!Common.isNull(notify.getNotificationTime())){
						sql+=" , notificationTime="+notify.getNotificationTime();
					}
					if(!Common.isNull(notify.getNotificationUserId())){
						sql+=" , notificationUserId="+notify.getNotificationUserId();
					}
					if(!Common.empty(notify.getNotificationUserName())){
						sql+=" , notificationUserName='"+notify.getNotificationUserName()+"'";
					}
					if(!Common.isNull(notify.getWorkId())){
						sql+=" , workId="+notify.getWorkId();
					}
					sql+=" where id="+notify.getId();
					execute(sql);
				}
				else{
					save(notify);
				}
			}
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
		}
		
	}

	@Override
	public void deleteNotify(String id) throws OAException {
		String sql="delete from t_pcs_work_notification where id in ("+id+")";
		try {
			execute(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
		}
	}

	@Override
	public List<Map<String, Object>> getNotify(int workId) throws OAException {
		String sql="select a.id,a.notification,a.notificationNum,from_unixtime(a.notificationTime) notificationTime,a.notificationUserId,a.workId,a.notificationUserName from t_pcs_work_notification a  where a.workId="+workId;
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
		}
	}

	@Override
	public void cleanWork(int id,String status) throws OAException {
		try {
			String sql=null;
			if(status!=null){
				sql="update t_pcs_work set id=id,stayTime=null,checkTime=null,openPump=null,stopPump=null"
						+ ",leaveTime=null,tearPipeTime=null,workTime=null,dockCheck=null,evaluate=null,evaluateUserId=null,evaluateUser=null,evaluateTime=null,dynamicUserId=null,dynamicContent=null"
						+ ",dockUserId=null,dockContent=null,shipClientId=null,shipClientContent=null,unusualLog=null,reviewStatus=null,createUserId=null,createTime=null,reviewUserId=null,reviewTime=null,createRUserId=null,createRTime=null ,status="+status+" where id="+id;
			}else{
				sql="update t_pcs_work set id=id,stayTime=null,checkTime=null,openPump=null,stopPump=null"
						+ ",leaveTime=null,tearPipeTime=null,workTime=null,dockCheck=null,evaluate=null,evaluateUserId=null,evaluateUser=null,evaluateTime=null,dynamicUserId=null,dynamicContent=null"
						+ ",dockUserId=null,dockContent=null,shipClientId=null,shipClientContent=null,unusualLog=null,reviewStatus=null,createUserId=null,createTime=null,reviewUserId=null,reviewTime=null,createRUserId=null,createRTime=null,status=2  where orderNum=0 and arrivalId="+id;
				String sql1="delete from t_pcs_work where orderNum!=0 and arrivalId="+id;
				execute(sql1);
			}
			executeUpdate(sql);
			
			
		} catch (RuntimeException e) {
			LOG.error("dao回退失败",e);
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao回退失败",e);
		}
	}

	@Override
	public void updateWorkItemStatus(Work work)
			throws OAException {
		try {
			String sql="update t_pcs_work set id=id";
			if(!Common.isNull(work.getStatus())){
				sql+=" ,status="+work.getStatus();
			}
			sql+=" where 1=1 ";
			if(!Common.isNull(work.getId())){
				sql+=" and id="+work.getId();
			}
			if(!Common.isNull(work.getArrivalId())){
				sql+=" and arrivalId="+work.getArrivalId();
			}
			if(!Common.isNull(work.getProductId())){
				sql+=" and productId="+work.getProductId();
			}
			executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao单个货品流程状态失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao单个货品流程失败");
		}
	}

	@Override
	public Map<String, Object> getWork(int ArrivalId, int productId)
			throws OAException {
		try {
			String sql="select * from t_pcs_work where arrivalId="+ArrivalId+" and productId="+productId;
			return executeQuery(sql).get(0);
			
		} catch (Exception e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"查询失败");
		}
	}

	@Override
	public List<Map<String, Object>> getInboundConnectInfo(int tankId,
			long mStartTime) throws OAException {

       String sql="select DISTINCT c.id arrivalId,a.tankId,d.name shipName,c.arrivalStartTime from view_store a LEFT JOIN t_pcs_tanklogstore b on a.tankId=b.tankId and a.arrivalId=b.arrivalId LEFT JOIN t_pcs_arrival c on c.id=a.arrivalId LEFT JOIN t_pcs_ship d on d.id=c.shipId where 1=1";
       
        sql+=" and c.arrivalStartTime>= FROM_UNIXTIME("+mStartTime+") and c.arrivalStartTime< FROM_UNIXTIME("+(mStartTime+86400)+") ";
        
        
        if(!Common.isNull(tankId)){
        	sql+=" and a.tankId="+tankId;
        }
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
		}
}

	@Override
	public List<Map<String, Object>> getInboundBConnectInfo(int arrivalId,int tankId)
			throws OAException {
	       String sql="select a.* from t_pcs_tanklogstore a where a.arrivalId="+arrivalId +" and a.tankId="+tankId;
	       
			try {
				return executeQuery(sql);
			} catch (OAException e) {
				throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
			}
	}

	@Override
	public List<Map<String, Object>> getinboundDisconnect(int logId, int type)throws OAException {
		String sql="select a.* from t_pcs_tanklogstore a where (a.startLogId="+logId+" and a.startType="+type+") or (a.endLogId="+logId+" and a.endType="+type+")";
		
		try {
			return executeQuery(sql);
		} catch (OAException e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"异常",e);
		}
		
	}

	@Override
	public void updateWorkArrivalTime(Work work) throws OAException {
		try {
			if(work.getArrivalId()!=null&&work.getArrivalTime()!=null){
				String sql="update t_pcs_work set arrivalTime="+work.getArrivalTime()+" where arrivalId="+work.getArrivalId()+" and arrivalTime is not null and arrivalTime!=-1 and arrivalTime!="+work.getArrivalTime();
				executeUpdate(sql);
			}
		} catch (Exception e) {
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao 更新work到港时间失败");
		}
	}

	@Override
	public int getOPLogListCount(int i, long startTime, long endTime)
			throws OAException {
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
		String sql="select 	count(DISTINCT a.id) from t_pcs_work a " +
				" LEFT JOIN t_pcs_arrival b on a.arrivalId=b.id " +
				" LEFT JOIN t_pcs_dispatch c on b.dispatchId=c.id " +
				" LEFT JOIN t_pcs_transport_program d on d.arrivalId=b.id  " +
//				" LEFT JOIN t_pcs_work_check e on e.transId=d.id " +
//				" LEFT JOIN t_pcs_job_check f on f.transId=d.id " +
				" LEFT JOIN view_store g on g.arrivalId=b.id and g.productId=d.productId" +
				" LEFT JOIN t_pcs_tank h on g.tankId=h.id " +
				" LEFT JOIN t_pcs_tanklogstore i on i.arrivalId=b.id and i.tankId=g.tankId " +
//				" LEFT JOIN t_pcs_tube j on j.id=i.tubeId " +
				" LEFT JOIN t_pcs_ship_ref k on k.id=b.shipRefId " +
				" LEFT JOIN t_pcs_product m on m.id=a.productId " +
				" LEFT JOIN t_pcs_berth_program n on n.arrivalId=b.id " +
				" LEFT JOIN t_pcs_berth o on o.id=n.berthId " +
//				" LEFT JOIN t_pcs_client p on p.name=l.clientId " +
				" LEFT JOIN t_auth_user q on q.id=a.evaluateUserId " +
				" LEFT JOIN t_auth_user r on r.id=a.notificationUserId where 1=1 and d.type=0 ";
				sql+=" and a.productId=case  when h.productId is null then a.productId when h.productId is not null then h.productId end  ";
        		sql+=" and a.productId=d.productId ";
		if(startTime!=0&& endTime!=0){
      	  sql+="  and a.arrivalTime>="+startTime+" and a.arrivalTime<"+endTime+" and a.type in (1,3)";
        }
		sql+=" and d.orderNum=0 and a.orderNum=0";
		
		sql+=" order by g.tankId DESC";
		return (int) getCount(sql);
	}

	/**
	 * @Title updateWorkStatus
	 * @Descrption:TODO
	 * @param:@param work
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年6月20日下午9:40:10
	 * @throws
	 */
	@Override
	public void updateWorkStatus(Work work) throws OAException {
		String sql="update t_pcs_work set id=id";
		if(null!=work.getStatus()){
			sql+=",status="+work.getStatus();
		}
		if(work.getReviewStatus()!=null){
			sql+=",reviewStatus="+work.getReviewStatus();
		}
		if(!Common.isNull(work.getCreateUserId())){
			sql+=",createUserId="+work.getCreateUserId();
		}
		if(!Common.isNull(work.getCreateTime())){
			sql+=",createTime="+work.getCreateTime();
		}
		if(!Common.isNull(work.getReviewUserId())){
			sql+=",reviewUserId="+work.getReviewUserId();
		}
		if(!Common.isNull(work.getReviewTime())){
			sql+=",reviewTime="+work.getReviewTime();
		}
		if(!Common.isNull(work.getCreateRUserId())){
			sql+=",createRUserId="+work.getCreateRUserId();
		}
		sql+=" where 1=1 ";
		if(!Common.isNull(work.getArrivalId())&&!Common.isNull(work.getProductId())){
			sql+=" and arrivalId="+work.getArrivalId()+" and productId="+work.getProductId();
		}else{
			sql+=" and id=0;";
		}
		executeUpdate(sql);
	}

}
