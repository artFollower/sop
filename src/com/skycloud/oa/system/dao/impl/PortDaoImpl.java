package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.PortDao;
import com.skycloud.oa.system.dto.ParkDto;
import com.skycloud.oa.system.dto.PortDto;
import com.skycloud.oa.system.model.Port;
import com.skycloud.oa.utils.Common;

@Repository
public class PortDaoImpl extends BaseDaoImpl implements PortDao {
	private static Logger LOG = Logger.getLogger(PortDaoImpl.class);
	@Override
	public List<Map<String, Object>> getList(PortDto portDto,int start,int limit)
			throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(portDto.getLetter())&&!("all").equals(portDto.getLetter())){
				 sql = "select a.*,s.name editUserName from t_pcs_port a LEFT JOIN t_auth_user s on s.id=a.editUserId,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=portDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+portDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select a.*,s.name editUserName from t_pcs_port a LEFT JOIN t_auth_user s on s.id=a.editUserId where 1=1 and isnull(a.status) ";
			}
			
//			String sql="select * from t_pcs_port where 1=1 ";
			if(!Common.empty(portDto.getCode())){
				sql+=" and a.code like '%"+portDto.getCode()+"%' or a.name like '%"+portDto.getName()+"%'";
			}
			if(!Common.empty(portDto.getId())){
				sql+=" and a.id="+portDto.getId();
			}
			sql += " order by CONVERT( a.name USING gbk ) COLLATE gbk_chinese_ci ASC";
			if(limit!=0){
				sql+=" limit "+start+","+limit;
			}
			return executeQuery(sql);
		}catch (RuntimeException e) {
			LOG.error("dao查询park失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询park失败", e);
		}
	}
	@Override
	public int getCount(PortDto portDto) throws OAException {
		try{
			
			String sql="";
			if(!Common.empty(portDto.getLetter())&&!("all").equals(portDto.getLetter())){
				 sql = "select count(*) from t_pcs_port a,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=portDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+portDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select count(*) from t_pcs_port a where 1=1 and isnull(a.status) ";
			}
			
//			String sql="select count(*) from t_pcs_port where 1=1 ";
			if(!Common.empty(portDto.getCode())){
				sql+=" and a.code like '%"+portDto.getCode()+"%' or a.name like '%"+portDto.getName()+"%'";
			}
			if(!Common.empty(portDto.getId())){
				sql+=" and a.id="+portDto.getId();
			}
			return (int) getCount(sql);
		}catch (RuntimeException e){
			LOG.error("dao查询港口表失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询港口表失败",e);
		}
	}

	@Override
	public void add(Port port) throws OAException {
		try{
			save(port);
		}catch(RuntimeException e){
			LOG.error("dao添加港口失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加港口失败",e);
		}
	}

	@Override
	public void updatePort(Port port) throws OAException {
		try{
			update(port);
		}catch(RuntimeException e){
			LOG.error("dao更新港口失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao更新港口失败",e);
		}
	}

	@Override
	public void delete(String ids) throws OAException {
		try{
			String sql="update t_pcs_port set status=1 where id in ("+ids+")";
			execute(sql);
		}catch(RuntimeException e){
			LOG.error("dao删除港口失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao删除港口失败",e);
		}
	}
	

}
