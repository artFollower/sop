package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.ClientQualificationDao;
import com.skycloud.oa.system.dto.ClientQualificationDto;
import com.skycloud.oa.system.model.ClientQualification;
import com.skycloud.oa.utils.Common;
@Repository
public class ClientQualificationDaoImpl extends BaseDaoImpl implements
		ClientQualificationDao {
	private static Logger LOG = Logger.getLogger(ClientQualificationDaoImpl.class);
	@Override
	public List<Map<String, Object>> getClientQualificationList(
			ClientQualificationDto cqDto,int start,int limit) throws OAException {
		try {
			String sql="select a.*,s.name editUserName b.name clientName,b.code clientCode,c.name typeValue from t_pcs_client_qualification a LEFT JOIN t_pcs_client b on a.clientId=b.id LEFT JOIN t_pcs_qualification c on a.type=c.id LEFT JOIN t_auth_user s on s.id=a.editUserId where 1=1 and isnull(a.status) ";
			if(!Common.empty(cqDto.getId())){
				sql+=" and id="+cqDto.getId();
			}
			if(!Common.empty(cqDto.getClientId())){
				sql+=" and clientId= "+cqDto.getClientId();
			}
			if(!Common.empty(cqDto.getClientCode())){
				sql+=" and ( b.code like '%"+cqDto.getClientCode()+"%' or  b.name like '%"+cqDto.getClientName()+"%' or  c.name like '%"+cqDto.getTypeValue()+"%') ";
			}
			if(limit!=0){
				sql+=" limit "+start+" , "+limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}

	@Override
	public void addClientQualification(ClientQualification clientQualification) throws OAException {
    try {
		save(clientQualification);
    } catch (RuntimeException e) {
		LOG.error("dao查询失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
	}	
	}

	@Override
	public void updateClientQualification(
			ClientQualification clientQualification) throws OAException {
try {
	update(clientQualification);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}	
	}

	@Override
	public void deleteClientQualification(String ids) throws OAException {
try {
	String sql="update t_pcs_client_qualification set status=1 where id in ("+ids+")";
	execute(sql);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}	
	}

	@Override
	public int getCount(ClientQualificationDto cqDto) throws OAException {
		try {
			String sql="select count(1) from t_pcs_client_qualification a  LEFT JOIN t_pcs_client b on a.clientId=b.id LEFT JOIN t_pcs_qualification c on a.type=c.id where 1=1 and isnull(a.status) ";
			if(!Common.empty(cqDto.getId())){
				sql+="and id="+cqDto.getId();
			}
			if(!Common.empty(cqDto.getClientId())){
				sql+="and clientId= "+cqDto.getClientId();
			}
			if(!Common.empty(cqDto.getClientCode())){
				sql+="and ( b.code like '%"+cqDto.getClientCode()+"%' or  b.name like '%"+cqDto.getClientName()+"%' or  c.name like '%"+cqDto.getTypeValue()+"%') ";
			}
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}

}
