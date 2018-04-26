/**
 * @Title:PumpShedDaoImpl.java
 * @Package com.skycloud.oa.system.dao.impl
 * @Description TODO
 * @autor jiahy
 * @date 2016年9月2日下午1:48:00
 * @version V1.0
 */
package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.PumpShedDao;
import com.skycloud.oa.system.dto.PumpShedDto;
import com.skycloud.oa.system.model.PumpShed;
import com.skycloud.oa.utils.Common;

/**
 * @ClassName PumpShedDaoImpl
 * @Description TODO
 * @author jiahy
 * @date 2016年9月2日下午1:48:00
 */
@Repository
public class PumpShedDaoImpl extends BaseDaoImpl implements PumpShedDao {
	
	private static Logger LOG = Logger.getLogger(PumpShedDaoImpl.class);

	/**
	 * @Title getPumpShedList
	 * @Descrption:TODO
	 * @param:@param psDto
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:50:11
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getPumpShedList(PumpShedDto psDto, int start, int limit)
			throws OAException {
		try {
			 StringBuilder sql=new StringBuilder();
			 sql.append(" select a.*,b.name editUserName,from_unixtime(a.editTime) editTimeStr ")
			 .append("from t_pcs_pump_shed a left join t_auth_user b on a.editUserId=b.id where a.status=0 ");
			 
			 if(!Common.isNull(psDto.getId()))
			  sql.append(" and a.id=").append(psDto.getId());
				 
			 if(limit!=0)
				 sql.append(" limit ").append(start).append(",").append(limit);
			 return executeQuery(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("dao泵棚列表查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao泵棚列表查询失败",e);
		}
	}

	/**
	 * @Title getPumpShedListCount
	 * @Descrption:TODO
	 * @param:@param psDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:50:11
	 * @throws
	 */
	@Override
	public Integer getPumpShedListCount(PumpShedDto psDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("select count(1) from t_pcs_pump_shed where status=0 ");
			 
			if(!Common.isNull(psDto.getId()))
				  sql.append(" and  id=").append(psDto.getId());
					 
			
			return (int) getCount(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("dao泵棚列表数量查询失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao泵棚列表数量查询失败",e);
		}
	}

	/**
	 * @Title updatePumpShed
	 * @Descrption:TODO
	 * @param:@param pumpShed
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:50:11
	 * @throws
	 */
	@Override
	public void updatePumpShed(PumpShed pumpShed) throws OAException {
		try {
			update(pumpShed);
		} catch (RuntimeException e) {
			LOG.error("dao泵棚更新失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao泵棚更新失败",e);
		}
	}

	/**
	 * @Title addPumpShed
	 * @Descrption:TODO
	 * @param:@param pumpShed
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:50:11
	 * @throws
	 */
	@Override
	public Integer addPumpShed(PumpShed pumpShed) throws OAException {
		try {
			
		return	Integer.valueOf(save(pumpShed).toString());
			
		} catch (RuntimeException e) {
			LOG.error("dao泵棚添加失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao泵棚添加失败",e);
		}
	}

	/**
	 * @Title deletePumpShed
	 * @Descrption:TODO
	 * @param:@param psDto
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年9月2日下午1:50:11
	 * @throws
	 */
	@Override
	public void deletePumpShed(PumpShedDto psDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" update t_pcs_pump_shed  set status=1");
			executeUpdate(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("dao泵棚删除失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao泵棚删除失败",e);
		}
	}
	

}
