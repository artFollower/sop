package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.QualificationDao;
import com.skycloud.oa.system.dto.QualificationDto;
import com.skycloud.oa.system.model.Qualification;
import com.skycloud.oa.utils.Common;
@Repository
public class QualificationDaoImpl extends BaseDaoImpl implements
		QualificationDao {
	private static Logger LOG = Logger.getLogger(QualificationDaoImpl.class);
	@Override
	public List<Map<String, Object>> getQualificationList(QualificationDto qDto,int start,int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(qDto.getLetter())&&!("all").equals(qDto.getLetter())){
				 sql = "select * from t_pcs_qualification a ,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=qDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+qDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select * from t_pcs_qualification a where 1=1 and isnull(a.status) ";
			}
			
//			String sql="select * from t_pcs_qualification where 1=1 ";
			if(!Common.empty(qDto.getId())){
				sql+=" and a.id="+qDto.getId();
			}
			if(!Common.empty(qDto.getName())){
				sql+=" and a.name like  '%"+qDto.getName()+"%'";
			}
			sql += " order by CONVERT( a.name USING gbk ) COLLATE gbk_chinese_ci ASC";
			if(limit!=0){
				sql+=" limit "+start+","+limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	}

	@Override
	public void addQualification(Qualification qualification) throws OAException {
try {
	save(qualification);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}	
	}

	@Override
	public void updateQualification(Qualification qualification) throws OAException {
try {
	update(qualification);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}	
	}

	@Override
	public void deleteQualification(String ids) throws OAException {
try {
	String sql="update t_pcs_qualification set status=1 where id in ("+ids+")";
	execute(sql);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}	
	}

	@Override
	public int getCount(QualificationDto qDto) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(qDto.getLetter())&&!("all").equals(qDto.getLetter())){
				 sql = "select count(*) from t_pcs_qualification a ,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=qDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+qDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select count(*) from t_pcs_qualification a where 1=1 and isnull(a.status) ";
			}
			
			
//			String sql="select count(*) from t_pcs_qualification where 1=1 ";
			if(!Common.empty(qDto.getId())){
				sql+=" and a.id="+qDto.getId();
			}
			if(!Common.empty(qDto.getName())){
				sql+=" and a.name like  '%"+qDto.getName()+"%'";
			}
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}

}
