package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.ClientGroupDao;
import com.skycloud.oa.system.model.ClientGroup;
import com.skycloud.oa.utils.Common;

@Repository
public class ClientGroupDaoImpl extends BaseDaoImpl implements ClientGroupDao{
	private static Logger LOG = Logger.getLogger(ClientGroupDaoImpl.class);


	@Override
	public List<Map<String, Object>> getClientGroup(String id,String name ,String letter,int start,int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(letter)&&!("all").equals(letter)){
				 sql = "select a.*,b.name editUserName from t_pcs_client_group a LEFT JOIN t_auth_user b on a.editUserId=b.id,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=letter.toUpperCase();
				
				sql+=" and (a.`name` like '"+letter+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select a.*,b.name editUserName from t_pcs_client_group a LEFT JOIN t_auth_user b on a.editUserId=b.id where 1=1 and isnull(a.status) ";
			}
			
			
//		String sql="select * from t_pcs_client_group where 1=1 ";
			
				if(!Common.empty(id)){
					sql+=" and a.id="+id ;
				}
			
				if(!Common.empty(name)){
					sql+=" and a.name like '%"+name+"%'";
				}
				sql += " order by CONVERT( a.name USING gbk ) COLLATE gbk_chinese_ci ASC";
				if(limit!=0){
					sql+=" limit "+start+","+limit;
				}
		
		return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}
	}


	@Override
	public int getClientGroupCount(String name,String letter) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(letter)&&!("all").equals(letter)){
				 sql = "select count(*) from t_pcs_client_group a,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=letter.toUpperCase();
				
				sql+=" and (a.`name` like '"+letter+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select count(*) from t_pcs_client_group a where 1=1 and isnull(a.status) ";
			}
			
//			String sql="select count(*) from t_pcs_client_group where 1=1 ";
			if(!Common.empty(name)){
				sql+=" and name like '%"+name+"%'";
			}
			return (int) getCount(sql);
			} catch (RuntimeException e) {
				LOG.error("dao查询失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
			}
	}


	@Override
	public void addClientGroup(ClientGroup clientGroup) throws OAException {
		try {
			save(clientGroup);
			} catch (RuntimeException e) {
				LOG.error("dao查询失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
			}
	}


	@Override
	public void updateClientGroup(ClientGroup clientGroup) throws OAException {
		try {
			update(clientGroup);
			} catch (RuntimeException e) {
				LOG.error("dao失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
			}
	}


	@Override
	public void deleteClientGroup(String ids) throws OAException {
		try {
			String sql = "update t_pcs_client_group set status=1 where id in (" + ids + ")";
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除tube失败", e);
		}
	}
	

}
