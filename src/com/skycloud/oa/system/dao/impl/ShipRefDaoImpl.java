package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.ShipRefDao;
import com.skycloud.oa.system.model.ShipRef;
import com.skycloud.oa.utils.Common;

@Repository
public class ShipRefDaoImpl extends BaseDaoImpl implements ShipRefDao{
	private static Logger LOG = Logger.getLogger(ShipRefDaoImpl.class);

	@Override
	public List<Map<String, Object>> getShipRefList(int shipId, int start,
			int limit) throws OAException {
		String sql="";
		if(shipId!=0){
			sql="select a.*,b.name shipName from t_pcs_ship_ref a LEFT JOIN t_pcs_ship b on a.shipId=b.id where a.status<>1 and a.shipId="+shipId;
		}else{
			sql="select a.*,b.name shipName from t_pcs_ship_ref a LEFT JOIN t_pcs_ship b on a.shipId=b.id where a.status<>1 ";
		}
		if(limit!=0){
			sql+=" limit "+start+" , "+limit;
		}
		return executeQuery(sql);
	}

	@Override
	public int addShipRef(ShipRef shipRef) throws OAException {
		try {
			return (Integer) save(shipRef);
		} catch (RuntimeException e) {
			LOG.error("dao查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询失败",e);
		}		
			
		
	}

	@Override
	public Map<String,Object> getShipRef(int shipId, String shipRefName) throws OAException {
		String sql=" select id from t_pcs_ship_ref where 1=1";
		if(0!=shipId){
			sql+=" and shipId="+shipId;
		}
		if(null!=shipRefName){
			sql+=" and refName='"+shipRefName+"'";
		}
		sql+=" limit 0,1";
	 return  executeQueryOne(sql);
		
	}
	

}
