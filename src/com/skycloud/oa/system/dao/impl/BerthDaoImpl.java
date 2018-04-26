package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.BerthDao;
import com.skycloud.oa.system.dto.BerthDto;
import com.skycloud.oa.system.model.Berth;
import com.skycloud.oa.utils.Common;

@Repository
public class BerthDaoImpl extends BaseDaoImpl implements BerthDao{
	private static Logger LOG = Logger.getLogger(BerthDaoImpl.class);

	@Override
	public List<Map<String, Object>> getList(BerthDto berthDto,int start,int limit)
			throws OAException {
		try {
			

			String sql="";
			if(!Common.empty(berthDto.getLetter())&&!("all").equals(berthDto.getLetter())){
				 sql = "select a.*,s.name editUserName  from t_pcs_berth a LEFT JOIN t_auth_user s on s.id=a.editUserId,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=berthDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+berthDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select a.*,s.name editUserName  from t_pcs_berth a LEFT JOIN t_auth_user s on s.id=a.editUserId where 1=1 and isnull(a.status) ";
			}
			
//			String sql="select a.*  from t_pcs_berth a where 1=1 ";
			if(!Common.empty(berthDto.getId())){
				sql+=" and a.id="+berthDto.getId();
			}
			if(!Common.empty(berthDto.getName())){
				sql+=" and a.name like '%"+berthDto.getName()+"%'";
			}
			if(!Common.empty(berthDto.getIds())){
				 sql+=" and a.id not in ("+berthDto.getIds()+")";
               }
			sql += " order by CONVERT( a.name USING gbk ) COLLATE gbk_chinese_ci ASC";
			if(limit!=0){
				sql+=" limit "+start+","+limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询berth失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询berth失败",e);
		}	
	}

	@Override
	public void add(Berth berth) throws OAException {
		try {
			save(berth);
		} catch (RuntimeException e) {
			LOG.error("dao添加berth失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加berth失败",e);
		}	
	}

	@Override
	public void update(Berth berth) throws OAException {
		try {
			String sql=" update t_pcs_berth  set id=id";
			if(!Common.empty(berth.getName())){
				sql+=" ,name="+"'"+berth.getName()+"'";
			}
			if(!Common.empty(berth.getLimitDisplacement())&&!Common.isNull(berth.getLimitDisplacement())){
				sql+=" ,limitDisplacement="+berth.getLimitDisplacement();
			}
			if(!Common.empty(berth.getLimitLength())&&!Common.isNull(berth.getLimitLength())){
				sql+=" ,limitLength="+berth.getLimitLength();
			}
			if(!Common.empty(berth.getLength())&&!Common.isNull(berth.getLength())){
				sql+=" ,length="+berth.getLength();
			}
			if(!Common.empty(berth.getFrontDepth())&&!Common.isNull(berth.getFrontDepth())){
				sql+=" ,frontDepth="+berth.getFrontDepth();
			}
			if(!Common.empty(berth.getMinLength())&&!Common.isNull(berth.getMinLength())){
				sql+=" ,minLength="+berth.getMinLength();
			}
			if(!Common.empty(berth.getLimitDrought())&&!Common.isNull(berth.getLimitDrought())){
				sql+=" ,limitDrought="+berth.getLimitDrought();
			}
			if(!Common.isNull(berth.getLimitTonnage())){
				sql+=" ,limitTonnage="+berth.getLimitTonnage();
			}
			
			if(!Common.empty(berth.getDescription())&&!Common.isNull(berth.getDescription())){
				sql+=" ,description="+"'"+berth.getDescription()+"'";
			}
			if(!Common.empty(berth.getSafeInfo())&&!Common.isNull(berth.getSafeInfo())){
				sql+=" ,safeInfo="+"'"+berth.getSafeInfo()+"'";
			}
			if(!Common.empty(berth.getEditUserId())&&!Common.isNull(berth.getEditUserId())){
				sql+=" ,editUserId="+berth.getEditUserId();
			}
			if(!Common.empty(berth.getEditTime())){
				sql+=" ,editTime="+"'"+berth.getEditTime()+"'";
			}
			sql+=" where id="+berth.getId();
			executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao更新berth失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao更新berth失败",e);
		}	
	}

	@Override
	public void delete(String ids) throws OAException {
		try {
			String sql="update t_pcs_berth set status=1 where id in ("+ids+")";
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao删除berth失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao删除berth失败",e);
		}		
	}

	@Override
	public int getCount(BerthDto berthDto) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(berthDto.getLetter())&&!("all").equals(berthDto.getLetter())){
				 sql = "select count(*)  from t_pcs_berth a ,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=berthDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+berthDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "select count(*)  from t_pcs_berth a where 1=1 and isnull(a.status) ";
			}
			
//			String sql="select count(*)  from t_pcs_berth a where 1=1 ";
			if(!Common.empty(berthDto.getId())){
				sql+=" and a.id="+berthDto.getId();
			}
			if(!Common.empty(berthDto.getName())){
				sql+=" and a.name= '"+berthDto.getName()+"'";
			}
			return (int) getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询berth失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询berth失败",e);
		}
	}

   
}
