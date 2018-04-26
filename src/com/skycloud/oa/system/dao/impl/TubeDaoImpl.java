package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.TubeDao;
import com.skycloud.oa.system.dto.TubeDto;
import com.skycloud.oa.system.model.Tube;
import com.skycloud.oa.utils.Common;

@Repository
public class TubeDaoImpl extends BaseDaoImpl implements TubeDao {
	private static Logger LOG = Logger.getLogger(TubeDaoImpl.class);

	@Override
	public List<Map<String, Object>> getList(TubeDto tubeDto,int start,int limit)
			throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(tubeDto.getLetter())&&!("all").equals(tubeDto.getLetter())){
				 sql = "SELECT a.*,s.name editUserName,b.`name` productName FROM t_pcs_tube a LEFT JOIN t_auth_user s on s.id=a.editUserId  LEFT JOIN t_pcs_product b on a.productId=b.id,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=tubeDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+tubeDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "SELECT a.*,s.name editUserName,b.`name` productName FROM t_pcs_tube a  LEFT JOIN t_auth_user s on s.id=a.editUserId LEFT JOIN t_pcs_product b on a.productId=b.id WHERE 1 = 1 and isnull(a.status) ";
			}
			
//			String sql = "SELECT a.*,b.`name` productName FROM t_pcs_tube a   LEFT JOIN t_pcs_product b on a.productId=b.id WHERE 1 = 1 ";
			if (!Common.empty(tubeDto.getId())) {
				sql += " and a.id=" + tubeDto.getId();
			}
			if (!Common.empty(tubeDto.getProductId())) {
				sql += " and ( a.productId=" + tubeDto.getProductId()+")";
			}
			if(!Common.empty(tubeDto.getName())){
				sql+=" and (  a.name like '%"+tubeDto.getName()+"%' or  b.name like '%"+tubeDto.getProductName()+"%' ) ";
			}
			if (!Common.empty(tubeDto.getType())&&tubeDto.getType() != -1) {
				sql += " and a.type=" + tubeDto.getType();
			}
			if(!Common.empty(tubeDto.getIds())){
				sql+=" and a.id not in ("+tubeDto.getIds()+")";
			}
			sql += " order by CONVERT( a.name USING gbk ) COLLATE gbk_chinese_ci ASC";
			if(limit!=0){
				sql+=" limit "+start+" , "+limit;
			}
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询tube失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询tube失败", e);
		}
	}
    
	@Override
	public void add(Tube tube) throws OAException {
		try {
			save(tube);
		} catch (RuntimeException e) {
			LOG.error("dao添加tube失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加tube失败", e);
		}
	}

	@Override
	public void update(Tube tube) throws OAException {
		try {
			String sql="update t_pcs_tube set id=id";
			if(!Common.empty(tube.getType())&&!Common.isNull(tube.getType())){
		    	  sql+=" ,type="+tube.getType();
		      }
			if(!Common.empty(tube.getName())&&!Common.isNull(tube.getName())){
		    	  sql+=" ,name="+"'"+tube.getName()+"'";
		      }
		    sql+=" ,productId="+tube.getProductId();
			if(!Common.empty(tube.getDescription())&&!Common.isNull(tube.getDescription())){
		    	  sql+=" ,description="+"'"+tube.getDescription()+"'";
		      }
			if(!Common.empty(tube.getEditUserId())&&!Common.isNull(tube.getEditUserId())){
		    	  sql+=" ,editUserId="+tube.getEditUserId();
		      }
			if(!Common.empty(tube.getEditTime())){
		    	  sql+=" ,editTime="+"'"+tube.getEditTime()+"'";
		      }
			sql+=" where id="+tube.getId();
			executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("dao更新tube失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新tube失败", e);
		}
	}

	@Override
	public void delete(String ids) throws OAException {
		try {
			String sql = "update t_pcs_tube set status=1 where id in (" + ids + ")";
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao删除tube失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除tube失败", e);
		}
	}

	@Override
	public int getCount(TubeDto tubeDto) throws OAException {
		try {
			
			String sql="";
			if(!Common.empty(tubeDto.getLetter())&&!("all").equals(tubeDto.getLetter())){
				 sql = "SELECT count(*) FROM t_pcs_tube a   LEFT JOIN t_pcs_product b on a.productId=b.id,t_sys_pinyin c where 1=1 and isnull(a.status) ";
				
				String up=tubeDto.getLetter().toUpperCase();
				
				sql+=" and (a.`name` like '"+tubeDto.getLetter()+"%' or a.`name` like '"+up+"%' or CONV(HEX(left(CONVERT(a.`name` USING gbk),1)),16,10) between c.charBegin and c.charEnd) and c.letter='"+up+"'";
			}else{
				 sql = "SELECT count(*) FROM t_pcs_tube a   LEFT JOIN t_pcs_product b on a.productId=b.id WHERE 1 = 1 and isnull(a.status) ";
			}
			
//			String sql = "SELECT count(*) FROM t_pcs_tube a   LEFT JOIN t_pcs_product b on a.productId=b.id WHERE 1 = 1 ";
			if (!Common.empty(tubeDto.getId())) {
				sql += " and a.id=" + tubeDto.getId();
			}
			if (!Common.empty(tubeDto.getProductId())) {
				sql += " and  a.productId=" + tubeDto.getProductId();
			}
			if(!Common.empty(tubeDto.getName())){
				 sql+= " and (  a.name like '%"+tubeDto.getName()+"%' or  b.name like '%"+tubeDto.getProductName()+"%' ) ";
			}
			if (!Common.empty(tubeDto.getType())&&tubeDto.getType() != -1) {
				sql += " and a.type=" + tubeDto.getType();
			}
			if(!Common.empty(tubeDto.getIds())){
				sql+=" and a.id not in ("+tubeDto.getIds()+")";
			}
			return (int)getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询tube失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询tube失败", e);
		}
	}

}
