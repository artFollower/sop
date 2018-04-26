package com.skycloud.oa.inbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.BerthProgramDao;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.planmanager.dto.PlanManagerDto;
import com.skycloud.oa.utils.Common;
@Component
public class BerthProgramDaoImpl extends BaseDaoImpl implements BerthProgramDao {

	private static Logger LOG = Logger.getLogger(BerthProgramDaoImpl.class);

	@Override
	public int addBerthProgram(BerthProgram berthProgram) throws OAException {
		try {
			  String sql="insert into t_pcs_berth_program (arrivalId) select "
			  		+berthProgram.getArrivalId()+" from dual where not exists (select arrivalId from t_pcs_berth_program where arrivalId="+berthProgram.getArrivalId()+"  and arrivalId is not null)";
			  return insert(sql);
			} catch (RuntimeException e) {
			LOG.error("dao添加靠泊方案失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加靠泊方案失败", e);
			}
	}

	@Override
	public void deleteBerthProgram(String id) throws OAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> getBerthProgramById(int arrivalId) throws OAException{
		try {
			StringBuilder sql=new StringBuilder();
				sql.append("SELECT CONCAT(d.name,'/',e.refName) shipName,c.`name` berthName,GROUP_CONCAT(DISTINCT g.name) productName, ")
				.append("(CASE WHEN a.type=1 or a.type=3 THEN  (SELECT ROUND(SUM(m.goodsPlan),3) from t_pcs_cargo m where m.arrivalId=a.id ) ")
				.append("ELSE (SELECT ROUND(SUM(m.goodsTotal),3) from t_pcs_arrival_plan m where m.arrivalId=a.id ) END ) goodsPlan,")
				.append("DATE_FORMAT(a.arrivalStartTime,'%Y-%m-%d') arrivalTime,b.windPower, ")
				.append("c.length,c.frontDepth,c.limitDisplacement,c.limitLength,c.minLength,c.limitDrought,c.description, ")
				.append("d.shipLenth,d.shipWidth,i.shipArrivalDraught,d.loadCapacity,d.netTons,b.richDraught,b.safeInfo, ")
				.append("b.`comment`,j.name createUserName,k.name reviewUserName ")
				.append("from t_pcs_arrival a  ")
				.append("LEFT JOIN t_pcs_berth_program b ON a.id=b.arrivalId ")
				.append("LEFT JOIN t_pcs_berth c ON c.id=b.berthId ")
				.append("LEFT JOIN t_pcs_ship d ON d.id=a.shipId ")
				.append("LEFT JOIN t_pcs_ship_ref e ON e.id=a.shipRefId ")
				.append("LEFT JOIN t_pcs_work f ON f.arrivalId=a.id ")
				.append("LEFT JOIN t_pcs_product g ON g.id=f.productId ")
				.append("LEFT JOIN t_pcs_cargo h ON h.arrivalId=a.id ")
				.append("LEFT JOIN t_pcs_arrival_info i ON i.arrivalId=a.id ")
				.append("LEFT JOIN t_auth_user j ON j.id=b.createUserId ")
				.append("LEFT JOIN t_auth_user k ON k.id=b.reviewUserId ")
				.append("where a.id=").append(arrivalId);
			return executeQueryOne(sql.toString());
			} catch (RuntimeException e) {
			LOG.error("dao获取靠泊方案失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取靠泊方案失败", e);
			}
	}

	@Override
	public void updateBerthProgram(BerthProgram berthProgram) throws OAException {
		try{
			String sql="update t_pcs_berth_program set id=id ";
			if(null!=berthProgram.getBerthId()){
				if(berthProgram.getBerthId()==-1){
					sql+=" ,berthId=null";
				}else{
					sql+=",berthId="+berthProgram.getBerthId();
				}
			}
			if(null!=berthProgram.getSafeInfo()){
				sql+=",safeInfo="+"'"+berthProgram.getSafeInfo()+"'";
			}
			if(null!=berthProgram.getComment()){
				sql+=",comment="+"'"+berthProgram.getComment()+"'";
			}
			if(null!=berthProgram.getRichDraught()){
				if(berthProgram.getRichDraught()==-1){
				sql+=",richDraught=null";	
				}else{
				sql+=",richDraught="+berthProgram.getRichDraught();
				}
			}
			if(null!=berthProgram.getWindPower()){
				sql+=",windPower='"+berthProgram.getWindPower()+"'";
			}
			if(null!=berthProgram.getCreateUserId()){
				if(berthProgram.getCreateUserId()==-1){
					sql+=",createUserId=null";
				}else{
				sql+=",createUserId="+berthProgram.getCreateUserId();
				}
			}
			if(null!=berthProgram.getCreateTime()){
				if(berthProgram.getCreateTime()==-1){
					sql+=",createTime=null";
				}else{
				sql+=",createTime="+berthProgram.getCreateTime();
				}
			}
			if(null!=berthProgram.getReviewUserId()){
				if(berthProgram.getReviewUserId()==-1){
				sql+=",reviewUserId=null";	
				}else{
				sql+=",reviewUserId="+berthProgram.getReviewUserId();
				}
			}
			if(null!=berthProgram.getReviewTime()){
				if(berthProgram.getReviewTime()==-1){
                   sql+=",reviewTime=null";					
				}else{
					sql+=",reviewTime="+berthProgram.getReviewTime();
				}
			}
			if(null!=berthProgram.getArrivalId()){
				if(berthProgram.getArrivalId()==-1){
					sql+=",arrivalId=null";
				}else{
					sql+=",arrivalId="+berthProgram.getArrivalId();
				}
			}
			if(berthProgram.getStatus()!=null&&berthProgram.getStatus()!=-1){
				sql+=",status="+berthProgram.getStatus();
			}
			if(null!=berthProgram.getId()){
			sql+=" where id="+berthProgram.getId();
			}else{
				sql+=" where arrivalId="+berthProgram.getArrivalId();
			}
			executeUpdate(sql);
			
		} catch(RuntimeException e){
			LOG.error("dao更新靠泊方案失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新靠泊方案失败", e);	
		}
	}

	@Override
	public void cleanBerthProgram(int arrivalId) throws OAException {
      try{
    	  String sql=" update t_pcs_berth_program set safeInfo=null,comment=null,richDraught=null,createUserId=null,createTime=null,"
    	  		+ "reviewUserId=null,reviewTime=null,status=0 where arrivalId="+arrivalId;
    	  executeUpdate(sql);
      }catch(RuntimeException e){
    	  LOG.error("dao 靠泊方案回退失败");
    	  throw new OAException(Constant.SYS_CODE_DB_ERR," dao 靠泊方案失败",e);
      }		
	}

	@Override
	public List<Map<String, Object>> getBerthProgramList(
			PlanManagerDto planManagerDto, int startRecord, int maxresult)
			throws OAException {
		try {
			String sql="SELECT a.id id ,a.berthId berthId,c.`name` berthName,a.arrivalId arrivalId ,b.type arrivalType,"
			 		+"(CASE WHEN b.type=1 or b.type=3 THEN  (SELECT ROUND(SUM(m.goodsPlan),3) from t_pcs_cargo m where m.arrivalId=a.arrivalId )"
			 		+" ELSE (SELECT ROUND(SUM(m.goodsTotal),3) from t_pcs_arrival_plan m where m.arrivalId=a.arrivalId ) END ) count,"
			 		+ "(CASE WHEN b.type = 1 OR b.type = 3 THEN('')ELSE(SELECT ROUND(sum(x.deliverNum),3) FROM t_pcs_goodslog x WHERE x.batchId = a.arrivalId AND x.type = 5 and x.createTime>a.createTime)END) count_plan,"
			 		+ "(select GROUP_CONCAT(DISTINCT p.name) from t_pcs_work w,t_pcs_product p where w.productId=p.id and w.arrivalId=a.arrivalId) productName,"
			 		+" DATE_FORMAT(b.arrivalStartTime,'%Y-%m-%d') arrivalTime,CONCAT(d.`name`,'/',e.refName) shipName,"
			 		+" FROM_UNIXTIME(a.createTime,'%Y-%m-%d %H:%i') createTime,a.`status` "
			 		+" from t_pcs_berth_program a,t_pcs_arrival b,t_pcs_berth c,t_pcs_ship d,t_pcs_ship_ref e "
			 		+" WHERE a.arrivalId=b.id and a.berthId=c.id and d.id=b.shipId  and e.id=b.shipRefId "
			 		+" and a.createTime is not null and e.refName!='转输' ";
			 if(planManagerDto.getBerthId()!=null){
				 sql+=" and a.berthId="+planManagerDto.getBerthId();
			 }
			 if(!Common.isNull(planManagerDto.getShipName())){
				 sql+=" and ( d.name like '%"+planManagerDto.getShipName()+"%' or e.refName like '%"+planManagerDto.getShipName()+"%')";
			 }
			 if(planManagerDto.getStartTime()!=null&&planManagerDto.getStartTime()!=-1){
				 sql+=" and a.createTime>="+planManagerDto.getStartTime();
			 }
			 if(planManagerDto.getEndTime()!=null&&planManagerDto.getEndTime()!=-1){
				 sql+=" and a.createTIme<="+planManagerDto.getEndTime();
			 }
			 if(planManagerDto.getStatus()!=null&&planManagerDto.getStatus()!=-1){
				 sql+=" and a.status="+planManagerDto.getStatus();
			 }
			 sql+=" ORDER BY a.createTime desc";
			 
			 if(maxresult!=0){
				 sql+=" limit "+startRecord+","+maxresult;
			 }
			 return executeQuery(sql);
			} catch (RuntimeException e) {
			LOG.error("dao获取靠泊方案列表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取靠泊方案列表失败", e);
			}
	}

	@Override
	public int getBerthProgramCount(PlanManagerDto planManagerDto)
			throws OAException {
		try {
			    String sql="select count(1) "
						+" from t_pcs_berth_program a,t_pcs_arrival b,t_pcs_berth c,t_pcs_ship d,t_pcs_ship_ref e"
						+" WHERE a.arrivalId=b.id and a.berthId=c.id and d.id=b.shipId  and e.id=b.shipRefId and a.createTime is not null and e.refName!='转输' ";
			    if(planManagerDto.getBerthId()!=null){
					 sql+=" and a.berthId="+planManagerDto.getBerthId();
				 }
			    if(!Common.isNull(planManagerDto.getShipName())){
					 sql+=" and ( d.name like '%"+planManagerDto.getShipName()+"%' or e.refName like '%"+planManagerDto.getShipName()+"%')";
				 }
				 if(planManagerDto.getStartTime()!=null&&planManagerDto.getStartTime()!=-1){
					 sql+=" and a.createTime>="+planManagerDto.getStartTime();
				 }
				 if(planManagerDto.getEndTime()!=null&&planManagerDto.getEndTime()!=-1){
					 sql+=" and a.createTIme<="+planManagerDto.getEndTime();
				 }
				 if(planManagerDto.getStatus()!=null&&planManagerDto.getStatus()!=-1){
					 sql+=" and a.status="+planManagerDto.getStatus();
				 }
				 sql+=" ORDER BY a.createTime desc";
			  return (int)getCount(sql);
			} catch (RuntimeException e) {
			LOG.error("dao获取方案列表数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取靠泊方案列表数量失败", e);
			}
	}


}
