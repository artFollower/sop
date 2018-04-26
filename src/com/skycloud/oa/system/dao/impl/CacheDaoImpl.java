package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.CacheDao;

/**
 * @author yanyufeng
 *
 */
@Repository
public class CacheDaoImpl extends BaseDaoImpl implements CacheDao  {

	
	/**
	 * 添加\更新关键字
	 * @param key
	 * @param userId
	 * @author yanyufeng
	 */
	@Override
	public void updateCache(String key, String userId) {
		String sql="SELECT COUNT(*) FROM t_pcs_cache where keyword='"+key+"' and userId="+userId;
		int count=(int) getCount(sql);
		if(count==0){
			String insertSql="insert into t_pcs_cache (keyword,userId,hitRate) values ('"+key+"',"+userId+","+1+")";
			try {
				insert(insertSql);
			} catch (OAException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			String updateSql="update t_pcs_cache set hitRate=hitRate+1 where userId="+userId;
			try {
				execute(updateSql);
			} catch (OAException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 得到关键字list
	 * @param text
	 * @param userId
	 * @return
	 * @author yanyufeng
	 */
	@Override
	public List<Map<String,Object>> getCacheResult(String text, String userId) {
		String sql="SELECT keyword from t_pcs_cache where keyword like '%"+text+"%' and userId="+userId+" order by hitRate DESC limit 0,5";
		List<Map<String, Object>> maplist;
		try {
			maplist = executeQuery(sql);
			return maplist;
		} catch (OAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
