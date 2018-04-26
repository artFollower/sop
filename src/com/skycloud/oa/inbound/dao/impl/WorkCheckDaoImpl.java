package com.skycloud.oa.inbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.WorkCheckDao;
import com.skycloud.oa.inbound.model.WorkCheck;
import com.skycloud.oa.utils.Common;
@Component
public class WorkCheckDaoImpl extends BaseDaoImpl implements WorkCheckDao {

	private static Logger LOG = Logger.getLogger(WorkCheckDaoImpl.class);

	@Override
	public int addWorkCheck(WorkCheck workCheck) throws OAException {
		try {
			  Serializable s=	save(workCheck);
			  return Integer.valueOf(s.toString());
			} catch (RuntimeException e) {
			LOG.error("dao添加作业检查表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加作业检查表失败", e);
			}
	}
    

	@Override
	public int addOnlyWorkCheck(WorkCheck workCheck) throws OAException {
		try {
           String sql="Insert into t_pcs_work_check (checkType,checkUserId,content,checkTime,transportId,description,flag,type) select ";
        		   if(workCheck.getCheckType()!=null){ sql+=workCheck.getCheckType()+" ,";}else{ sql+="null,";};
           		   if(workCheck.getCheckUserId()!=null){sql+=workCheck.getCheckUserId()+",";}else{sql+="null,";};
           		 if(workCheck.getContent()!=null){sql+="'"+workCheck.getContent()+"',";}else{sql+="null,";};
           		 if(workCheck.getCheckTime()!=null){sql+=workCheck.getCheckTime()+",";}else{sql+="null,";};
           		 if(workCheck.getTransportId()!=null){sql+=workCheck.getTransportId()+",";}else{sql+="null,";};
           		 if(workCheck.getDescription()!=null){sql+="'"+workCheck.getDescription()+"',";}else{sql+="null,";};
           		 if(workCheck.getFlag()!=null){sql+=workCheck.getFlag()+",";}else{sql+="null,";};
           		 if(workCheck.getType()!=null){sql+=workCheck.getType();}else{sql+="null";};
                 sql+=" from dual where not exists( select * from t_pcs_work_check where transportId="+workCheck.getTransportId()+" and transportId is not null "
                 		+ " and checkType="+workCheck.getCheckType()+" and checkType is not null)";          		 
			
			  return insert(sql);
			} catch (RuntimeException e) {
			LOG.error("dao添加作业检查表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加作业检查表失败", e);
			}
	}

	
	
	@Override
	public void deleteWorkCheck(String id) throws OAException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map<String, Object>> getWorkCheckList(String transportId,String types)
			throws OAException {
		try {
			String sql = "select a.id id,a.checkType checkType,a.checkUserId checkUserId,b.name checkUserName,a.content content,"
					+ "from_unixtime(a.checkTime) checkTime,a.transportId transportId ,a.description description,a.type type,a.flag flag, c.dockWork,c.tubeWork,c.powerWork from t_pcs_work_check a "
					+ " LEFT JOIN t_auth_user b on a.checkUserId=b.id " +
					" LEFT JOIN t_pcs_transport_program c on c.id=a.transportId "
					+ "where a.checkType in ("+types+") and  a.transportId in ("+transportId+")";
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询作业检查表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询作业检查表失败", e);
		}
	}

	@Override
	public void updateWorkCheck(WorkCheck workCheck) throws OAException {
		try {
			String sql ="update t_pcs_work_check set id=id ";
			if(!Common.empty(workCheck.getCheckType())&&!Common.isNull(workCheck.getCheckType())){
				sql+=",checkType="+"'"+workCheck.getCheckType()+"'";
			}
			if(!Common.empty(workCheck.getCheckUserId())&&!Common.isNull(workCheck.getCheckUserId())){
				sql+=",checkUserId="+workCheck.getCheckUserId();
			}
			if(!Common.empty(workCheck.getContent())&&!Common.isNull(workCheck.getContent())){
				sql+=",content="+"'"+workCheck.getContent()+"'";
			}
			if(!Common.empty(workCheck.getCheckTime())&&!Common.isNull(workCheck.getCheckTime())){
				sql+=",checkTime="+workCheck.getCheckTime();
			}
			if(!Common.empty(workCheck.getTransportId())&&!Common.isNull(workCheck.getTransportId())){
				sql+=",transportId="+workCheck.getTransportId();
			}
			if(!Common.empty(workCheck.getDescription())&&!Common.isNull(workCheck.getDescription())){
				sql+=",description="+"'"+workCheck.getDescription()+"'";
			}
			if(!Common.empty(workCheck.getType())&&!Common.isNull(workCheck.getType())){
				sql+=",type="+workCheck.getType();
			}
			if(!Common.empty(workCheck.getFlag())&&!Common.isNull(workCheck.getFlag())){
				sql+=",flag="+workCheck.getFlag();
			}
			sql+=" where id="+workCheck.getId();
			executeUpdate(sql);
			
		} catch (RuntimeException e) {
			LOG.error("dao作业检查表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao作业检查表失败", e);
		}
	}

	@Override
	public void cleanWorkCheck(int id,boolean isTrue) throws OAException {
		try {
			String sql=null;
			if(isTrue){
				 sql="delete from t_pcs_work_check where transportId="+id;
			}else{
				 sql="delete from t_pcs_work_check where transportId in (select a.id from t_pcs_transport_program a where a.arrivalId="+id+")";
			}
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao 清空作业检查失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao 清空作业检查失败",e);
		}
	}

}
