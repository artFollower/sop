package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.TankDao;
import com.skycloud.oa.system.dto.TankDto;
import com.skycloud.oa.system.model.Tank;
import com.skycloud.oa.utils.Common;

@Repository
public class TankDaoImpl extends BaseDaoImpl implements TankDao{
	private static Logger LOG = Logger.getLogger(TankDaoImpl.class);
	@Override
	public List<Map<String, Object>> getTankList(TankDto tDto,int start,int limit) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(tDto.getLetter())&&!("all").equals(tDto.getLetter())){
				 sql = "select a.*,s.name editUserName ,b.name productName,c.value typeValue,e.name pumpShedName from t_pcs_tank a"
				 		+ " LEFT JOIN t_auth_user s on s.id=a.editUserId "
				 		+ " left join t_pcs_product b on a.productId=b.id "
				 		+ " LEFT JOIN t_pcs_pump_shed e ON e.id=a.pumpShedId "
				 		+ " left join t_pcs_tank_type c on a.type=c.key,t_sys_pinyin d"
				 		+ " where 1=1 and isnull(a.status) ";
				
				String up=tDto.getLetter().toUpperCase();
				
				sql+=" and (a.`code` like '"+tDto.getLetter()+"%' or a.`code` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`code` USING gbk),1)),16,10) between d.charBegin and d.charEnd) and d.letter='"+up+"'";
			}else{
				 sql = "select a.* ,s.name editUserName,b.name productName,c.value typeValue,e.name pumpShedName from t_pcs_tank a"
				 		+ " LEFT JOIN t_auth_user s on s.id=a.editUserId "
				 		+ " left join t_pcs_product b on a.productId=b.id "
				 		+ " LEFT JOIN t_pcs_pump_shed e ON e.id=a.pumpShedId "
				 		+ " left join t_pcs_tank_type c on a.type=c.key  "
				 		+ " where 1=1 and isnull(a.status) ";
			}
			
			
//			String sql="select a.* ,b.name productName,c.value typeValue from t_pcs_tank a left join t_pcs_product b on a.productId=b.id left join t_pcs_tank_type c on a.type=c.key  where 1=1 ";
			if(!Common.empty(tDto.getId())){
				sql+=" and a.id="+tDto.getId();
			}
			if(!Common.empty(tDto.getCode())){
				sql+=" and ( a.code like '%"+tDto.getCode()+"%' or a.name like '%"+tDto.getName()+"%' or b.name like '%"+tDto.getProductName()+"%')";
			}
			if (!Common.empty(tDto.getProductId())) {
				sql += " and a.productId=" + tDto.getProductId();
			}
			if(!Common.empty(tDto.getIds())){
				sql+=" and a.id not in("+tDto.getIds()+")";
			}
			if(!Common.empty(tDto.getType())&&tDto.getType()==0){
//				sql+=" and !ISNULL(a.key) and a.key!=''";
				sql+=" and a.isVir=0";
			}
			sql+=" order by substring(a.code,2)+0 ASC ";
			if(limit!=0){
				sql+=" limit "+start+" , "+limit;
			}
			
			
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	
	}
	public  Map<String, Object> getTankByCode(String code) throws OAException {
		try {
			String sql="select a.* ,b.name productName,c.value typeValue from t_pcs_tank a left join t_pcs_product b on a.productId=b.id left join t_pcs_tank_type c on a.type=c.key  where 1=1 ";
			if(!Common.empty(code)){
				sql+=" and ( a.code ='"+code+"')";
			}
			sql+=" limit 0,1";
			return executeQueryOne(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	
	}

	@Override
	public void addTank(Tank tank) throws OAException {
try {
	StringBuffer sb = new StringBuffer() ;
	sb.append("	INSERT INTO t_pcs_tank (	");
	sb.append("	  `key`,	");
	sb.append("	  capacityCurrent,	");
	sb.append("	  capacityFree,	");
	sb.append("	  capacityTotal,	");
	sb.append("	  `code`,	");
	sb.append("	  description,	");
	sb.append("	  editTime,	");
	sb.append("	  editUserId,	");
	sb.append("   pumpShedId,");
	sb.append("	  `name`,	");
	sb.append("	  productId,	");
	sb.append("	  `status`,	");
	sb.append("	  tankTemperature,	");
	sb.append("	  testDensity,	");
	sb.append("	  testTemperature,	");
	sb.append("	  `type`,isVir	");
	sb.append("	) 	");
	sb.append("	VALUES	");
	sb.append("	  (	");
	sb.append("	    '"+tank.getKey()+"',	");
	sb.append("	    '"+tank.getCapacityCurrent()+"',	");
	sb.append("	    '"+tank.getCapacityFree()+"',	");
	sb.append("	    '"+tank.getCapacityTotal()+"',	");
	sb.append("	    '"+tank.getCode()+"',	");
	sb.append("	    '"+tank.getDescription()+"',	");
	sb.append("	    now(),	");
	sb.append("	    "+tank.getEditUserId()+",	");
	sb.append(tank.getPumpShedId()+",");
	sb.append("	    '"+tank.getName()+"',	");
	sb.append("	    "+tank.getProductId()+",	");
	sb.append("	    "+tank.getStatus()+",	");
	sb.append("	    '"+tank.getTankTemperature()+"',	");
	sb.append("	    '"+tank.getTestDensity()+"',	");
	sb.append("	    '"+tank.getTankTemperature()+"',	");
	sb.append("	    "+tank.getType()+",	");
	sb.append("	    "+tank.getIsVir()+"	");
	
	sb.append("	  )	");
executeUpdate(sb.toString()) ;
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}		
	}

	@Override
	public void updateTank(Tank tank) throws OAException {
try {
	String sql="update t_pcs_tank set id=id";
      if(null!=tank.getCode()){
    	  sql+=" ,code="+"'"+tank.getCode()+"'";
      }
      if(null!=tank.getName()){
    	  sql+=" ,name="+"'"+tank.getName()+"'";
      }
      if(null!=tank.getType()){
    	 sql+=" ,type="+tank.getType();
      }
      if(null!=tank.getPumpShedId()){
    	  sql+=" ,pumpShedId="+tank.getPumpShedId();
      }
      if(null!=tank.getProductId()){
    	 sql+=" ,productId="+tank.getProductId();
      }
      if(null!=tank.getCapacityTotal()){
    	 sql+=" ,capacityTotal="+"'"+tank.getCapacityTotal()+"'";
      }
      if(null!=tank.getCapacityCurrent()){
    	 sql+=" ,capacityCurrent="+"'"+tank.getCapacityCurrent()+"'";
      }
      if(null!=tank.getCapacityFree()){
    	 sql+=" ,capacityFree="+"'"+tank.getCapacityFree()+"'";
      }
      if(null!=tank.getTestDensity()){
    	 sql+=" ,testDensity="+"'"+tank.getTestDensity()+"'";
      }
      if(null!=tank.getTestTemperature()){
    	 sql+=" ,testTemperature="+"'"+tank.getTestTemperature()+"'";
      }
      if(null!=tank.getTankTemperature()){
    	 sql+=" ,tankTemperature="+"'"+tank.getTankTemperature()+"'";
      }
      if(null!=tank.getDescription()){
    	 sql+=" ,description="+"'"+tank.getDescription()+"'";
      }
      if(null!=tank.getStatus()){
    	 sql+=" ,status="+tank.getStatus();
      }
      if(null!=tank.getEditTime()){
    	 sql+=" ,editTime="+"'"+tank.getEditTime()+"'";
      }
      if(null!=tank.getKey()){
    	  sql+=" ,`KEY`="+"'"+tank.getKey()+"'";
      }
      if(null!=tank.getIsVir()){
    	  sql+=" ,`isVir`="+tank.getIsVir();
      }
      if(null!=tank.getEditUserId()){
    	 sql+=" ,editUserId="+tank.getEditUserId();
      }
		sql+=" where id="+tank.getId();
	   executeUpdate(sql);
} catch (RuntimeException e) {
	LOG.error("dao更新储罐失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao更新储罐失败",e);
}		
	}

	@Override
	public void deleteTank(String ids) throws OAException {
try {
	String sql="update t_pcs_tank set status=1 where id in ("+ids+")";
	execute(sql);
} catch (RuntimeException e) {
	LOG.error("dao查询失败");
	throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
}		
	}

	@Override
	public int getCount(TankDto tDto) throws OAException {
		try {
			
			
			String sql="";
			if(!Common.empty(tDto.getLetter())&&!("all").equals(tDto.getLetter())){
				 sql = "select count(*) from t_pcs_tank a left join t_pcs_product b on a.productId=b.id left join t_pcs_tank_type c on a.type=c.key,t_sys_pinyin d where 1=1 and isnull(a.status) ";
				
				String up=tDto.getLetter().toUpperCase();
				
				sql+=" and (a.`code` like '"+tDto.getLetter()+"%' or a.`code` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`code` USING gbk),1)),16,10) between d.charBegin and d.charEnd) and d.letter='"+up+"'";
			}else{
				 sql = "select count(*)  from t_pcs_tank a left join t_pcs_product b on a.productId=b.id left join t_pcs_tank_type c on a.type=c.key  where 1=1 and isnull(a.status) ";
			}
			
			
//			String sql="select count(*) from t_pcs_tank a left join t_pcs_product b on a.productId=b.id left join t_pcs_tank_type c on a.type=c.key  where 1=1 ";
			if(!Common.empty(tDto.getId())){
				sql+=" and a.id="+tDto.getId();
			}
			if(!Common.empty(tDto.getCode())){
				sql+=" and ( a.code like '%"+tDto.getCode()+"%' or a.name like '%"+tDto.getName()+"%' or b.name like '%"+tDto.getProductName()+"%')";
			}
			if (!Common.empty(tDto.getProductId())) {
				sql +=" and a.productId=" + tDto.getProductId();
			}
			if(!Common.empty(tDto.getIds())){
				sql+=" and a.id not in("+tDto.getIds()+")";
			}
			if(tDto.getType()==0){
//				sql+=" and a.key<>'' ";
				sql+=" and a.isVir=0";
			}
			return (int)getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	
	}
	@Override
	public void addTankUpdateLog(Integer id) throws OAException {
		// TODO Auto-generated method stub
		try {
		String sql="insert into t_pcs_tank_updatelog (code,name,type,productId,capacityTotal,capacityCurrent,capacityFree,testDensity,testTemperature,tankTemperature,description,status,editTime,editUserId,createTime,tankId) select code,name,type,productId,capacityTotal,capacityCurrent,capacityFree,testDensity,testTemperature,tankTemperature,description,status,editTime,editUserId,createTime,id as tankId from t_pcs_tank where id="+id;
		execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	
	}
	@Override
	public List<Map<String, Object>> getUpdateTankLog(TankDto tDto, int start,
			int limit) throws OAException {
		try {
			
			String sql="";
				 sql = "select a.*,s.name editUserName ,b.name productName,c.value typeValue from t_pcs_tank_updatelog a LEFT JOIN t_auth_user s on s.id=a.editUserId left join t_pcs_product b on a.productId=b.id left join t_pcs_tank_type c on a.type=c.key where 1=1 and isnull(a.status) ";
				
//			String sql="select a.* ,b.name productName,c.value typeValue from t_pcs_tank a left join t_pcs_product b on a.productId=b.id left join t_pcs_tank_type c on a.type=c.key  where 1=1 ";
			if(!Common.empty(tDto.getId())){
				sql+=" and a.tankId="+tDto.getId();
			}
			if(!Common.empty(tDto.getCode())){
				sql+=" and ( a.code like '%"+tDto.getCode()+"%' or a.name like '%"+tDto.getName()+"%' or b.name like '%"+tDto.getProductName()+"%')";
			}
			if (!Common.empty(tDto.getProductId())) {
				sql += " and a.productId=" + tDto.getProductId();
			}
			if(!Common.empty(tDto.getIds())){
				sql+=" and a.id not in("+tDto.getIds()+")";
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
	public int getUpdateTankLogCount(TankDto tDto) throws OAException {
		try {
			
			String sql="";
				 sql = "select count(1) from t_pcs_tank_updatelog a LEFT JOIN t_auth_user s on s.id=a.editUserId left join t_pcs_product b on a.productId=b.id left join t_pcs_tank_type c on a.type=c.key where 1=1 and isnull(a.status) ";
				
//			String sql="select a.* ,b.name productName,c.value typeValue from t_pcs_tank a left join t_pcs_product b on a.productId=b.id left join t_pcs_tank_type c on a.type=c.key  where 1=1 ";
			if(!Common.empty(tDto.getId())){
				sql+=" and a.tankId="+tDto.getId();
			}
			if(!Common.empty(tDto.getCode())){
				sql+=" and ( a.code like '%"+tDto.getCode()+"%' or a.name like '%"+tDto.getName()+"%' or b.name like '%"+tDto.getProductName()+"%')";
			}
			if (!Common.empty(tDto.getProductId())) {
				sql += " and a.productId=" + tDto.getProductId();
			}
			if(!Common.empty(tDto.getIds())){
				sql+=" and a.id not in("+tDto.getIds()+")";
			}
			
			
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}	
	}

}
