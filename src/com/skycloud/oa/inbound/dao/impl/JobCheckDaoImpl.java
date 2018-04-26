package com.skycloud.oa.inbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.JobCheckDao;
import com.skycloud.oa.inbound.model.JobCheck;
import com.skycloud.oa.utils.Common;
@Component
public class JobCheckDaoImpl extends BaseDaoImpl implements JobCheckDao {

	private static Logger LOG = Logger.getLogger(JobCheckDaoImpl.class);

	@Override
	public int addJobCheck(JobCheck jobCheck) throws OAException {
		try {
			  Serializable s=	save(jobCheck);
			  return Integer.valueOf(s.toString());
			} catch (RuntimeException e) {
			LOG.error("dao添加传输管线失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加添加传输管线失败", e);
			}
	}
    
	@Override
	public int addOnlyJobCheck(JobCheck jobcheck) throws OAException {
		try {
			String sql="insert into  t_pcs_job_check (job,result,solve,createTime,createUserId,transportId) select ";
			if(jobcheck.getJob()!=null){sql+=jobcheck.getJob()+",";}else{sql+="null,";};
			if(jobcheck.getResult()!=null){sql+="'"+jobcheck.getResult()+"',";}else{sql+="null,";};
			if(jobcheck.getSolve()!=null){sql+="'"+jobcheck.getSolve()+"',";}else{sql+="null,";};
			if(jobcheck.getCreateTime()!=null){sql+=jobcheck.getCreateTime()+",";}else{sql+="null,";};
			if(jobcheck.getCreateUserId()!=null){sql+=jobcheck.getCreateUserId()+",";}else{sql+="null,";};
			if(jobcheck.getTransportId()!=null){sql+=jobcheck.getTransportId();}else{sql+="null";};
			sql+=" from dual where not exists (select * from t_pcs_job_check a where a.transportId="+jobcheck.getTransportId()+" and a.transportId is not null"
					+ " and a.job="+jobcheck.getJob()+" and a.job is not  null)";
			
			return insert(sql);
			
			} catch (RuntimeException e) {
			LOG.error("dao添加传输管线失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加添加传输管线失败", e);
			}
	}


	
	@Override
	public void deleteJobCheck(String id) throws OAException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateJobCheck(JobCheck jobCheck) throws OAException {
		try {
			String sql="update t_pcs_job_check set id=id ";
			if(!Common.empty(jobCheck.getJob())&&!Common.isNull(jobCheck.getJob())){
				sql+=",job="+jobCheck.getJob();
			}
			if(!Common.empty(jobCheck.getResult())&&!Common.isNull(jobCheck.getResult())){
				sql+=",result="+"'"+jobCheck.getResult()+"'";
			}
			if(!Common.empty(jobCheck.getSolve())&&!Common.isNull(jobCheck.getSolve())){
				sql+=",solve="+"'"+jobCheck.getSolve()+"'";
			}
			if(!Common.empty(jobCheck.getCreateTime())&&!Common.isNull(jobCheck.getCreateTime())){
				sql+=",createTime="+jobCheck.getCreateTime();
			}
			if(!Common.empty(jobCheck.getCreateUserId())&&!Common.isNull(jobCheck.getCreateUserId())){
				sql+=",createUserId="+jobCheck.getCreateUserId();
			}
			if(!Common.empty(jobCheck.getTransportId())&&!Common.isNull(jobCheck.getTransportId())){
				sql+=",transportId="+jobCheck.getTransportId();
			}
			sql+=" where id="+jobCheck.getId();
			
			executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询管线失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询管线失败", e);
		}
	}

	@Override
	public List<Map<String, Object>> getJobCheckList(int transId, String job) throws OAException{
		try {
			String sql = "select a.id id,a.job job ,a.result result,a.solve solve,from_unixtime(a.createTime) createTime,a.createUserId createUserId ,b.name createUserName,a.transportId transportId from t_pcs_job_check a  "
					+ " LEFT JOIN t_auth_user b on a.createUserId=b.id "
					+ " where 1=1 and transportId="+transId+" and job in("+job+")";
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询管线失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询管线失败", e);
		}
	}

	@Override
	public void cleanJobCheck(int id,boolean isTrue) throws OAException {
		try{
			String sql=null;
			if(isTrue){
				sql=" delete from t_pcs_job_check where transportId="+id;
			}else{
				
			 sql=" delete from t_pcs_job_check where transportId in (select b.id from t_pcs_transport_program b where b.type=0 and b.arrivalId="+id+")";
			}
			executeUpdate(sql);
		}catch(RuntimeException e){
			LOG.error("dao 清空岗位检查表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao 清空岗位检查表失败",e);
		}
	}

}
