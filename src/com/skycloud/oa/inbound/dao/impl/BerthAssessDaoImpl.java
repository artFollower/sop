package com.skycloud.oa.inbound.dao.impl;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.BerthAssessDao;
import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.utils.Common;
@Component
public class BerthAssessDaoImpl extends BaseDaoImpl implements BerthAssessDao {

	private static Logger LOG = Logger.getLogger(BerthAssessDaoImpl.class);

	@Override
	public int addBerthAssess(BerthAssess berthAssess) throws OAException {
		try {
			String sql="insert into t_pcs_berth_assess (arrivalId,reviewStatus) select "
					+ berthAssess.getArrivalId()+","+berthAssess.getReviewStatus()
							+ " from DUAL where not exists (select arrivalId from t_pcs_berth_assess where arrivalId="+berthAssess.getArrivalId()+"  and arrivalId is not null)" ;
			  return insert(sql);
			} catch (RuntimeException e) {
			LOG.error("dao添加靠泊评估失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加靠泊评估失败", e);
			}
	}

	@Override
	public void deleteBerthAssess(String id)  throws OAException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> getBerthAssessById(int berthAssessid) throws OAException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateBerthAssess(BerthAssess berthAssess) throws OAException{
		try{
			String sql="update t_pcs_berth_assess set id=id";
			if(null!=berthAssess.getArrivalId()){
				sql+=",arrivalId="+berthAssess.getArrivalId();
			}
			if(null!=berthAssess.getWeather()){
				if(berthAssess.getWeather()==""){
					sql+=" ,weather=null";
				}else{
				sql+=",weather='"+berthAssess.getWeather()+"'";
				}
			}
			if(null!=berthAssess.getWindDirection()){
				if(berthAssess.getWindDirection()==""){
					sql+=" ,windDirection=null";
				}else{
				sql+=",windDirection='"+berthAssess.getWindDirection()+"'";
				}
			}
			if(null!=berthAssess.getWindPower()){
				if(berthAssess.getWindPower()==""){
                     sql+=",windPower=null";					
				}else{
				sql+=",windPower='"+berthAssess.getWindPower()+"'";
				}
			}
			if(null!=berthAssess.getSecurity()){
				sql+=",security='"+berthAssess.getSecurity()+"'";
			}
			if(null!=berthAssess.getReason()){
				sql+=",reason="+"'"+berthAssess.getReason()+"'";
			}
			if(null!=berthAssess.getCreateUserId()){
				sql+=",createUserId="+berthAssess.getCreateUserId();
			}
			if(null!=berthAssess.getCreateTime()){
				sql+=",createTime="+berthAssess.getCreateTime();
			}
			if(null!=berthAssess.getComment()){
				sql+=",comment='"+berthAssess.getComment()+"'";
			}
			if(null!=berthAssess.getReviewUserId()){
				sql+=",reviewUserId="+berthAssess.getReviewUserId();
			}
			if(null!=berthAssess.getReviewTime()){
				sql+=",reviewTime="+berthAssess.getReviewTime();
			}
			if(berthAssess.getReviewStatus()!=null){
				if(berthAssess.getReviewStatus()==-1){
					sql+=",reviewStatus=null";
				}else{
				sql+=",reviewStatus="+berthAssess.getReviewStatus();
				}
			}
			if(null!=berthAssess.getId()){
			sql+=" where id="+berthAssess.getId();
			}else{
				sql+=" where arrivalId="+berthAssess.getArrivalId();
			}
			executeUpdate(sql);
		}catch (RuntimeException e){
			LOG.error("dao更新靠泊评估失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新靠泊评估失败", e);
		}
	}

	@Override
	public Map<String, Object> getBerthAssessByArrival(int arrivalId) throws OAException {
		// TODO Auto-generated method stub
		String sql = "SELECT pba.id,pba.`comment`,pda.security,pba.reason,w.`value` AS weather,wd.`value` AS windDirection,wp.`value` AS windPower,ru.`name`,pda.reviewStatus,"
				+"FROM t_pcs_berth_assess pba LEFT OUTER JOIN t_auth_user ru ON pba.reviewUserId = ru.id LEFT OUTER JOIN t_pcs_weather w ON"
				+"w.`key` = pba.weather LEFT OUTER JOIN t_pcs_wind_direction wd ON wd.`key` = pba.windDirection LEFT OUTER JOIN t_pcs_wind_power wp "
				+"on wp.`key` = pba.windPower WHERE pba.arrivalId = " +arrivalId;
		return execute(sql);
	}

	@Override
	public void cleanBerthAssess(int arrivalId) throws OAException {
		try{
			String sql="UPDATE t_pcs_berth_assess SET weather =null, windDirection =null, windPower =null, windPower =null, SECURITY =null, reason =null, createUserId =null, createTime =null, COMMENT =null, reviewUserId =null, reviewTime =null, reviewStatus =0 WHERE arrivalId="+arrivalId;
			executeUpdate(sql);
		}catch(RuntimeException e){
			LOG.error("dao 清空靠泊评估失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao 清空靠泊评估失败",e);
		}
	}

	/**
	 * @Title getUserId
	 * @Descrption:TODO
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年5月20日上午9:51:31
	 * @throws
	 */
	@Override
	public Integer getUserId() throws OAException {
		StringBuilder sql=new StringBuilder();
		sql.append(" select reviewUserId from  t_pcs_berth_assess where reviewUserId>0 order by id desc limit 0,1 ");
		return (int)executeQueryOne(sql.toString()).get("reviewUserId");
	}


}
