/**
 * @Title:ExceedCleanLogDaoImpl.java
 * @Package com.skycloud.oa.feebill.dao.impl
 * @Description TODO
 * @autor jiahy
 * @date 2017年3月25日上午9:14:38
 * @version V1.0
 */
package com.skycloud.oa.feebill.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.ExceedCleanLogDao;
import com.skycloud.oa.feebill.dto.ExceedCleanLogDto;
import com.skycloud.oa.feebill.model.ExceedCleanLog;
import com.skycloud.oa.utils.Common;

/**
 * @ClassName ExceedCleanLogDaoImpl
 * @Description TODO
 * @author jiahy
 * @date 2017年3月25日上午9:14:38
 */
@Component
public class ExceedCleanLogDaoImpl extends BaseDaoImpl implements
		ExceedCleanLogDao {

	/**
	 * @Title getLogList
	 * @Descrption:TODO
	 * @param:@param eclDto
	 * @param:@return
	 * @auhor jiahy
	 * @date 2017年3月25日上午9:14:38
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getLogList(ExceedCleanLogDto eclDto) throws OAException {
              StringBuffer sql=new StringBuffer();
              sql.append(" select a.id,a.ladingId,a.cargoId,a.content,a.createUserId,")
              .append("from_unixtime(a.createTime) createTime,b.name createUserName ")
              .append(" from t_pcs_exceed_clean_log a ")
              .append(" left join t_auth_user b on a.createUserId=b.id ")
              .append(" where 1=1 ");
		     if(!Common.isNull(eclDto.getCargoId()))
		    	 sql.append(" and a.cargoId=").append(eclDto.getCargoId());
		     if(!Common.isNull(eclDto.getLadingId()))
		    	 sql.append(" and a.ladingId=").append(eclDto.getLadingId());
         return executeQuery(sql.toString());		
	}

	/**
	 * @Title addLog
	 * @Descrption:TODO
	 * @param:@param exceedCleanLog
	 * @param:@return
	 * @auhor jiahy
	 * @date 2017年3月25日上午9:14:38
	 * @throws
	 */
	@Override
	public int addLog(ExceedCleanLog exceedCleanLog) throws OAException{
		
		return Integer.parseInt( save(exceedCleanLog).toString());
	}

	/**
	 * @Title deleteLog
	 * @Descrption:TODO
	 * @param:@param id
	 * @auhor jiahy
	 * @date 2017年3月25日上午9:14:38
	 * @throws
	 */
	@Override
	public void deleteLog(int id) throws OAException {
		 StringBuffer sql=new StringBuffer();
		 sql.append(" delete from t_pcs_exceed_clean_log where id=").append(id);
		 execute(sql.toString());
		 }

}
