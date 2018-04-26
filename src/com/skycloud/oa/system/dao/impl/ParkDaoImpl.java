package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.ParkDao;
import com.skycloud.oa.system.dto.ParkDto;
import com.skycloud.oa.system.model.Park;
import com.skycloud.oa.utils.Common;

@Repository
public class ParkDaoImpl extends BaseDaoImpl implements ParkDao {
	private static Logger LOG = Logger.getLogger(ParkDaoImpl.class);

	@Override
	public List<Map<String, Object>> getParkList(ParkDto parkDto,int start,int limit)
			throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(parkDto.getLetter())&&!("all").equals(parkDto.getLetter())){
				 sql = "SELECT a.*,s.name editUserName,b.`name` productName,d.name pumpName "
				 		+ " FROM t_pcs_park a LEFT JOIN t_auth_user s on s.id=a.editUserId "
				 		+ " LEFT JOIN t_pcs_product b on a.productId=b.id "
				 		+ " LEFT JOIN t_pcs_pump d on a.pumpId=d.id"
				 		+ ",t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=parkDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+parkDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "SELECT a.*,s.name editUserName,b.`name` productName,d.name pumpName  "
				 		+ " FROM t_pcs_park a LEFT JOIN t_auth_user s on s.id=a.editUserId"
				 		+ " LEFT JOIN t_pcs_product b on a.productId=b.id"
				 		+ " LEFT JOIN t_pcs_pump d on a.pumpId=d.id"
				 		+ " WHERE 1=1 and isnull(a.status) ";
			}
			
			if(!Common.empty(parkDto.getName())){
				sql = sql + " and a.name like '%"+parkDto.getName()+"%'" ;
			}
			if(!Common.empty(parkDto.getIds())){
				sql = sql + " and a.id not in ("+parkDto.getIds()+")" ;
			}
			
			if(!Common.empty(parkDto.getId())){
				sql = sql + " and a.id="+parkDto.getId();
			}
			if(parkDto.getProductId()!=null){
				sql+=" and a.productId="+parkDto.getProductId();
			}
			sql += " order by CONVERT( a.name USING gbk ) COLLATE gbk_chinese_ci ASC";
			if(limit!=0){
				sql+=" limit "+start+" , "+limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询park失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询park失败", e);
		}
	}
    
	@Override
	public void addPark(Park park) throws OAException {
		try {
			save(park);
		} catch (RuntimeException e) {
			LOG.error("dao添加park失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加park失败", e);
		}
	}

	@Override
	public void updatePark(Park park) throws OAException {
		try {
          String sql="update t_pcs_park set id=id";
          if(!Common.isNull(park.getName()))
        	  sql+=" ,name="+"'"+park.getName()+"'";
          if(!Common.isNull(park.getPumpId()))
        	  sql+=",pumpId="+park.getPumpId();
        	  sql+=" ,productId="+park.getProductId();
		  if(!Common.isNull(park.getDescription()))
			  sql+=" ,description='"+park.getDescription()+"'";
			
			if(!Common.empty(park.getEditUserId())){
				sql+=" ,editUserId="+park.getEditUserId();
			}
          sql+=" where id="+park.getId();
          executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao更新park失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新park失败", e);
		}
	}

	@Override
	public void deletePark(String ids) throws OAException {
		try {
			String sql = "update t_pcs_park set status=1 where id in (" + ids + ")";
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao删除park失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除park失败", e);
		}
	}

	@Override
	public int getCount(ParkDto parkDto) throws OAException {
		try {
			String sql="";
			if(!Common.empty(parkDto.getLetter())&&!("all").equals(parkDto.getLetter())){
				 sql = "SELECT count(*) FROM t_pcs_park a  LEFT JOIN t_pcs_product b on a.productId=b.id,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=parkDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+parkDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "SELECT count(*) FROM t_pcs_park a  LEFT JOIN t_pcs_product b on a.productId=b.id WHERE 1 = 1 and isnull(a.status) ";
			}
			
//			String sql = "SELECT count(*) FROM t_pcs_park  WHERE 1 = 1 ";
			if(!Common.empty(parkDto.getName())){
				sql = sql + " and a.name like '%"+parkDto.getName()+"%'" ;
			}
			if(!Common.empty(parkDto.getIds())){
				sql = sql + " and a.id not in ("+parkDto.getIds()+")" ;
			}
			if(!Common.empty(parkDto.getId())){
				sql = sql + " and a.id="+parkDto.getId();
			}
			if(parkDto.getProductId()!=null){
				sql+=" and a.productId="+parkDto.getProductId();
			}
			return (int)getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询park失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询park失败", e);
		}
	}

}
