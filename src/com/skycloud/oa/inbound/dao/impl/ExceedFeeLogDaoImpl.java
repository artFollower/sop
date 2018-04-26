package com.skycloud.oa.inbound.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.ExceedFeeLogDao;
import com.skycloud.oa.inbound.dto.ExceedFeeLogDto;
import com.skycloud.oa.inbound.model.ExceedFeeLog;

/**
 * 保存每个超期提单的数据记录
 * @author littleFly
 *
 */
@Component
public class ExceedFeeLogDaoImpl extends BaseDaoImpl implements ExceedFeeLogDao {
	private static Logger LOG = Logger.getLogger(ExceedFeeDaoImpl.class);
	@Override
	public List<Map<String, Object>> getExceedFeeLogList(ExceedFeeLogDto elDto)
			throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT a.id lId,a.clientName,a.col,a.createTime l_time,a.isCurrent,a.ladingId l_ladingId,a.ladingCode l_ladingCode,a.type,a.operateNum,a.truckCode,a.currentNum,a.contactClientName ")
			.append(" from t_pcs_exceed_fee_log a WHERE a.exceedId=").append(elDto.getExceedId());
			return executeQuery(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取超期提单的数据记录失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期提单的数据记录失败", e);
	}
	}

	@Override
	public int getExceedFeeLogCount(ExceedFeeLogDto elDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("SELECT COUNT(1) from t_pcs_exceed_fee_log a WHERE a.exceedId=").append(elDto.getExceedId());
			return (int) getCount(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao获取超期提单的数据记录数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取超期提单的数据记录数量失败", e);
	}
	}

	@Override
	public int addExceedFeeLog(ExceedFeeLog exceedFeeLog) throws OAException {
		try {
		return	Integer.valueOf(save(exceedFeeLog).toString());
	}catch (RuntimeException e) {
		LOG.error("dao添加超期提单的数据记录失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加超期提单的数据记录失败", e);
	}
	}

	@Override
	public void updateExceedFeeLog(ExceedFeeLog exceedFeeLog) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append("update t_pcs_exceed_fee_log set id=id ");
			if(exceedFeeLog.getClientName()!=null){
				sql.append(", clientName='").append(exceedFeeLog.getClientName()).append("'");				
			}
			if(exceedFeeLog.getContactClientName()!=null){
				sql.append(", contactClientName='").append(exceedFeeLog.getContactClientName()).append("'");
			}
			if(exceedFeeLog.getCol()!=null){
				sql.append(",col='").append(exceedFeeLog.getCol()).append("'");
			}
			if(exceedFeeLog.getCreateTime()!=null){
				sql.append(",createTime='").append(exceedFeeLog.getCreateTime()).append("'");
			}
			if(exceedFeeLog.getCurrentNum()!=null){
				sql.append(",currentNum='").append(exceedFeeLog.getCurrentNum()).append("'");
			}
			if(exceedFeeLog.getExceedId()!=null){
				sql.append(",exceedId='").append(exceedFeeLog.getExceedId()).append("'");
			}
			if(exceedFeeLog.getIsCurrent()!=null){
				sql.append(",isCurrent='").append(exceedFeeLog.getIsCurrent()).append("'");
			}
			if(exceedFeeLog.getLadingCode()!=null){
				sql.append(",ladingCode='").append(exceedFeeLog.getLadingCode()).append("'");
			}
			if(exceedFeeLog.getLadingId()!=null){
				sql.append(",ladingId=").append(exceedFeeLog.getLadingId());
			}
			if(exceedFeeLog.getOperateNum()!=null){
				sql.append(",operateNum='").append(exceedFeeLog.getOperateNum()).append("'");
			}
			if(exceedFeeLog.getTruckCode()!=null){
				sql.append(",truckCode='").append(exceedFeeLog.getTruckCode()).append("'");
			}
			if(exceedFeeLog.getType()!=null){
				sql.append(",type=").append(exceedFeeLog.getType());
			}
			sql.append(" where id=").append(exceedFeeLog.getId());
			executeUpdate(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao修改超期提单的数据记录失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao修改超期提单的数据记录失败", e);
	}
	}

	@Override
	public void deleteExceedFeeLog(ExceedFeeLogDto elDto) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			if(elDto.getExceedId()!=null){
			sql.append("delete from t_pcs_exceed_fee_log where exceedId=").append(elDto.getExceedId());
			if(elDto.getIds()!=null){
				sql.append(" and id not in(").append(elDto.getIds()).append(")");
				}
			}
			execute(sql.toString());
	}catch (RuntimeException e) {
		LOG.error("dao删除超期提单的数据记录失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除超期提单的数据记录失败", e);
	}
	}

}
