package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.HisTankDao;
import com.skycloud.oa.system.dto.HisTank;
import com.skycloud.oa.system.dto.HisTankDto;
import com.skycloud.oa.utils.Common;

@Repository
public class HisTankDaoImpl extends BaseDaoImpl implements HisTankDao{
	private static Logger LOG = Logger.getLogger(HisTankDaoImpl.class);

	/**
	 * @Title getHisTankList
	 * @Descrption:TODO
	 * @param:@param tDto
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月25日上午10:37:28
	 * @throws
	 */
	@Override
	public List<Map<String, Object>> getHisTankList(HisTankDto tDto, int start, int limit) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT a.*,b.`code` tankCode,FROM_UNIXTIME(a.DTime,'%Y-%m-%d') DTimeStr,d.`name` productName  ")
			.append("  from t_his_tank_log a LEFT JOIN t_pcs_tank b ON a.tank=b.`key`  ")
			.append(" LEFT JOIN t_pcs_product d ON a.goods=d.`code` where 1=1 ");
			if(!Common.isNull(tDto.getProductId())){
				sql.append(" and d.id=").append(tDto.getProductId());
			}
			if(!Common.isNull(tDto.getTankId())){
				sql.append(" and  b.id=").append(tDto.getTankId());
			}
			if(!Common.isNull(tDto.getTank())){
                sql.append(" and a.tank='").append(tDto.getTank()).append("'"); 				
			}
			if(!Common.isNull(tDto.getDtime())){
				sql.append(" and a.Dtime=").append(tDto.getDtime());
			}
			if(!Common.isNull(tDto.getStartTime())){
				sql.append(" and a.DTime>=").append(tDto.getStartTime());
			}
			if(!Common.isNull(tDto.getEndTime())){
				sql.append(" and a.DTime<=").append(tDto.getEndTime());
			}
			if(limit!=0){
				sql.append(" limit ").append(start).append(" , ").append(limit);
			}
			return executeQuery(sql.toString());
	} catch (RuntimeException e) {
		LOG.error("dao查询历史储罐失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询历史储罐失败",e);
	}
	}

	/**
	 * @Title getCount
	 * @Descrption:TODO
	 * @param:@param tDto
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月25日上午10:37:28
	 * @throws
	 */
	@Override
	public int getCount(HisTankDto tDto) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append(" SELECT COUNT(1) ")
			.append("  from t_his_tank_log a LEFT JOIN t_pcs_tank b ON a.tank=b.`key`  ")
			.append(" LEFT JOIN t_pcs_product d ON a.goods=d.`code` where 1=1 ");
			if(!Common.isNull(tDto.getProductId())){
				sql.append(" and d.id=").append(tDto.getProductId());
			}
			if(!Common.isNull(tDto.getTankId())){
				sql.append(" and  b.id=").append(tDto.getTankId());
			}
			if(!Common.isNull(tDto.getTank())){
                sql.append(" and a.tank='").append(tDto.getTank()).append("'"); 				
			}
			if(!Common.isNull(tDto.getDtime())){
				sql.append(" and a.Dtime=").append(tDto.getDtime());
			}
			if(!Common.isNull(tDto.getStartTime())){
				sql.append(" and a.DTime>=").append(tDto.getStartTime());
			}
			if(!Common.isNull(tDto.getEndTime())){
				sql.append(" and a.DTime<=").append(tDto.getEndTime());
			}
			return (int) getCount(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("dao查询历史储罐失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询历史储罐失败",e);
		}
	}

	/**
	 * @Title updateHisTank
	 * @Descrption:TODO
	 * @param:@param hisTank
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月25日上午10:37:28
	 * @throws
	 */
	@Override
	public void updateHisTank(HisTank hisTank) throws OAException {
		try{
			StringBuilder sql=new StringBuilder();
			sql.append("update t_his_tank_log  set tank=tank ");
			
			if(!Common.isNull(hisTank.getGoods())){
				sql.append(", goods='").append(hisTank.getGoods()).append("'");
			}
			if(!Common.isNull(hisTank.getTankType())){
			 sql.append(", tankType=").append(hisTank.getTankType());		
			}
			if( hisTank.getIsUse()!=null){
				sql.append(", isUse=").append(hisTank.getIsUse());
			}
			if(!Common.empty(hisTank.getCapacityTotal())){
				sql.append(",capacityTotal=").append(hisTank.getCapacityTotal());
			}
			sql.append(" where 1=1").append(" and DTime=").append(hisTank.getDTime()).append(" and tank='").append(hisTank.getTank()).append("'");
			executeUpdate(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("dao更新历史储罐失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao更新历史储罐失败",e);
		}
	}
	
}
