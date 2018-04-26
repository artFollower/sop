package com.skycloud.oa.inbound.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.TransDao;
import com.skycloud.oa.inbound.model.Trans;
import com.skycloud.oa.utils.Common;
@Component
public class TransDaoImpl extends BaseDaoImpl implements TransDao {

	private static Logger LOG = Logger.getLogger(TransDaoImpl.class);

	@Override
	public int addTrans(Trans Trans)throws OAException {
		try {
			  Serializable s=	save(Trans);
			  return Integer.valueOf(s.toString());
			} catch (RuntimeException e) {
			LOG.error("dao添加传输管线失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加添加传输管线失败", e);
			}
	}

	@Override
	public void deleteTrans(String id) throws OAException {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void updateTrans(Trans Trans) throws OAException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Map<String, Object>> getTransList(String transportIds)
			throws OAException {
		try {
			String sql = "select a.*,b.name tubeName,b.type type,d.name parentName,e.code tankCode, b.productId productId,c.name productName, b.description tubeDescription from t_pcs_trans a  "
					+ " LEFT JOIN t_pcs_tube b on a.tubeId=b.id "
					+ " LEFT JOIN t_pcs_tube d on a.parentId=d.id "
					+ " LEFT JOIN t_pcs_product c on b.productId=c.id "
					+ " LEFT JOIN t_pcs_tank  e on a.tankId=e.id "
					+ " where 1=1 and a.transportId in ("+transportIds+") group by a.tubeId";
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询管线失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询管线失败", e);
		}
	}

	@Override
	public void deleteTransByTransPortId(Integer id)throws OAException {
		try{
			String sql="delete from t_pcs_trans where transportId="+id;
			executeUpdate(sql);
		}catch(RuntimeException e){
			LOG.error("dao管线删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao管线删除失败", e);	
		}
	}

	@Override
	public void cleanTrans(int id,boolean isTrue) throws OAException {
		try {
			String sql=null;
			if(isTrue){
				sql="delete from t_pcs_trans where transportId="+id;
			}else{
				sql="delete from t_pcs_trans where transportId in ( select a.id from t_pcs_transport_program a where a.arrivalId="+id+")";
			}
			execute(sql);
		} catch (RuntimeException e) {
			LOG.error("dao清空管线失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao清空管线失败",e);
		}
	}

	@Override
	public List<Map<String, Object>> getTransListByArrivalId(int arrivalId,
			int productId) throws OAException {
		try {
			String sql = "select a.*,b.name tubeName,b.type type,d.name parentName,e.code tankCode, b.productId productId,c.name productName, b.description tubeDescription from t_pcs_trans a  "
					+ " LEFT JOIN t_pcs_tube b on a.tubeId=b.id "
					+ " LEFT JOIN t_pcs_tube d on a.parentId=d.id "
					+ " LEFT JOIN t_pcs_product c on b.productId=c.id "
					+ " LEFT JOIN t_pcs_tank  e on a.tankId=e.id "
					+ " where 1=1 and a.transportId in (select x.id from t_pcs_transport_program x where x.arrivalId="+arrivalId+" and x.productId="+productId+") group by a.tubeId";
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询管线失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao查询管线失败", e);
		}
	}


}
