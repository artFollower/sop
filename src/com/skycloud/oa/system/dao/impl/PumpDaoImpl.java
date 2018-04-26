package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.PumpDao;
import com.skycloud.oa.system.dto.PumpDto;
import com.skycloud.oa.system.model.Pump;
import com.skycloud.oa.utils.Common;

@Repository
public class PumpDaoImpl extends BaseDaoImpl implements PumpDao {
	private static Logger LOG = Logger.getLogger(PumpDaoImpl.class);

	@Override
	public List<Map<String, Object>> getPumpList(PumpDto pumpDto,int start,int limit)
			throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(pumpDto.getLetter())&&!("all").equals(pumpDto.getLetter())){
				 sql = "SELECT a.*,s.name editUserName,b.name productName,d.name pumpShedName "
				 		+ "FROM t_pcs_pump a LEFT JOIN t_auth_user s on s.id=a.editUserId "
				 		+ "left join t_pcs_product b on a.productId=b.id "
				 		+ "LEFT JOIN t_pcs_pump_shed d ON d.id=a.pumpShedId "
				 		+ ",t_sys_pinyin c where 1=1 and isnull(a.sysStatus) ";
				
				String up=pumpDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+pumpDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "SELECT a.*,s.name editUserName,b.name productName,d.name pumpShedName "
				 		+ "FROM t_pcs_pump a LEFT JOIN t_auth_user s on s.id=a.editUserId "
				 		+ "left join t_pcs_product b on a.productId=b.id "
				 		+ "LEFT JOIN t_pcs_pump_shed d ON d.id=a.pumpShedId "
				 		+ "WHERE 1 = 1 and isnull(a.sysStatus) ";
			}
			
			if(!Common.empty(pumpDto.getName())){
				sql = sql + " and a.name like '%"+pumpDto.getName()+"%'" ;
			}
			if(!Common.empty(pumpDto.getIds())){
				sql = sql + " and a.id not in ("+pumpDto.getIds()+")" ;
			}
			if(pumpDto.getProductId()!=null){
				sql+=" and a.productId="+pumpDto.getProductId();
			}
			if(pumpDto.getId()!=null){
				sql+=" and a.id="+pumpDto.getId();
			}
			if(pumpDto.getType()!=null&&pumpDto.getType()!=-1){
				sql+=" and a.type="+pumpDto.getType();
			}
			sql += " order by CONVERT( a.name USING gbk ) COLLATE gbk_chinese_ci ASC";
			if(limit!=0){
				sql+=" limit "+start+" , "+limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询pump失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询pump失败", e);
		}
	}
    
	@Override
	public void addPump(Pump pump) throws OAException {
		try {
			save(pump);
		} catch (RuntimeException e) {
			LOG.error("dao添加pump失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加pump失败", e);
		}
	}

	@Override
	public void updatePump(Pump pump) throws OAException {
		try {
          String sql="update t_pcs_pump set id=id";
          if(!Common.empty(pump.getName())&&!Common.isNull(pump.getName())){
        	  sql+=" ,name="+"'"+pump.getName()+"'";
          }
         sql+=" ,productId="+pump.getProductId();
          if(pump.getStatus()!=null){
        	  sql+=" ,status='"+pump.getStatus()+"'";
          }
          if(pump.getPumpShedId()!=null)
        	  sql+=",pumpShedId="+pump.getPumpShedId();
          if(pump.getNote()!=null){
        	  sql+=" ,note='"+pump.getNote()+"'";
          }
          if(pump.getDescription()!=null){
        	  sql+=" ,description='"+pump.getDescription()+"'";
          }
          if(pump.getType()!=null&&pump.getType()!=-1){
        	  sql+=",type="+pump.getType();
          }
          if(!Common.empty(pump.getEditUserId())){
        	  sql+=",editUserId="+pump.getEditUserId();
          }
          sql+=" where id="+pump.getId();
          executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao更新pump失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新pump失败", e);
		}
	}

	@Override
	public void deletePump(String ids) throws OAException {
		try {
			String sql = "update t_pcs_pump set sysStatus=1 where id in (" + ids + ")";
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao删除pump失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除pump失败", e);
		}
	}

	@Override
	public int getCount(PumpDto pumpDto) throws OAException {
		try {
			
			
			String sql="";
			if(!Common.empty(pumpDto.getLetter())&&!("all").equals(pumpDto.getLetter())){
				 sql = "SELECT count(*) FROM t_pcs_pump a ,t_sys_pinyin c where 1=1 and isnull(a.sysStatus) ";
				
				String up=pumpDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+pumpDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "SELECT count(*) FROM t_pcs_pump a WHERE 1 = 1 and isnull(a.sysStatus) ";
			}
			
			
			if(!Common.empty(pumpDto.getName())){
				sql = sql + " and name like '%"+pumpDto.getName()+"%'" ;
			}
			if(!Common.empty(pumpDto.getIds())){
				sql = sql + " and a.id not in ("+pumpDto.getIds()+")" ;
			}
			if(pumpDto.getProductId()!=null){
				sql+=" and a.productId="+pumpDto.getProductId();
			}
			if(pumpDto.getId()!=null){
				sql+=" and a.id="+pumpDto.getId();
			}
			if(pumpDto.getType()!=null&&pumpDto.getType()!=-1){
				sql+=" and a.type="+pumpDto.getType();
			}
			return (int)getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询pump失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询pump失败", e);
		}
	}

}
