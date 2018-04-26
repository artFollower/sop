/**
 * 
 */
package com.skycloud.oa.system.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.system.dao.TankMeasureDao;
import com.skycloud.oa.system.dto.TankMeasureDto;
import com.skycloud.oa.system.model.TankMeasure;

/**
 *
 * @author jiahy
 * @version 2015年11月30日 下午4:30:08
 */
@Component
public class TankMeasureDaoImpl extends BaseDaoImpl implements TankMeasureDao {
	private static Logger LOG = Logger.getLogger(ClientGradeDaoImpl.class);
	@Override
	public List<Map<String, Object>> getList(TankMeasureDto tankMeasureDto,
			int start, int limit) throws OAException {
		try {
			String sql=" select a.*,from_unixtime(a.createTime) createTimeStr,b.code tankName,c.name productName, d.name userName  from t_pcs_tank_measure a left join t_pcs_tank b on b.id=a.tankId "
					+ " left join t_pcs_product c on c.id=a.productId "
					+ " left join t_auth_user d on d.id=a.userId  where 1=1 ";
					if(tankMeasureDto.getId()!=null){
						sql+=" and a.id="+tankMeasureDto.getId();
					}
					if(tankMeasureDto.getProductId()!=null){
						sql+=" and a.productId="+tankMeasureDto.getProductId();
					}	
			       if(tankMeasureDto.getTankId()!=null&&tankMeasureDto.getTankId()!=0){
			    	   sql+=" and a.tankId="+tankMeasureDto.getTankId();
			       }
			       sql+=" order by a.createTime desc";
			        if(limit!=0){
			        	sql+=" limit "+start+" , "+limit;
			        }
			return executeQuery(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询tankMeasuerlist失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"daotankMeasuerlist失败",e);
		}	
	}

	@Override
	public int getListCount(TankMeasureDto tankMeasureDto) throws OAException {
		try {
			String sql=" select count(1)  from t_pcs_tank_measure a left join t_pcs_tank b on b.id=a.tankId "
					+ " left join t_pcs_product c on c.id=a.productId "
					+ " left join t_auth_user d on d.id=a.userId  where 1=1 ";
					if(tankMeasureDto.getId()!=null){
						sql+=" and a.id="+tankMeasureDto.getId();
					}
					if(tankMeasureDto.getProductId()!=null){
						sql+=" and a.productId="+tankMeasureDto.getProductId();
					}	
			       if(tankMeasureDto.getTankId()!=null){
			    	   sql+=" and a.tankId="+tankMeasureDto.getTankId();
			       }
			       return (int)getCount(sql);
		} catch (RuntimeException e) {
			LOG.error("dao查询tankMeasuerlist数量失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao查询tankMeasuerlist数量失败",e);
		}	
	}

	@Override
	public int addTankMeasure(TankMeasure tankMeasure) throws OAException {
		try {
		  return (Integer)save(tankMeasure);
		} catch (RuntimeException e) {
			LOG.error("dao添加TankMeasure失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"dao添加TankMeasure失败",e);
		}	
	}

	@Override
	public void updateTankMeasure(TankMeasure tankMeasure) throws OAException {
		try {
              String sql=" update t_pcs_tank_measure set id=id";
              if(tankMeasure.getNotify()!=null){
            	  sql+=" , notify='"+tankMeasure.getNotify()+"'";
              }
              if(tankMeasure.getTankId()!=null){
            	  sql+=" , tankId="+tankMeasure.getTankId();
              }
              if(tankMeasure.getProductId()!=null){
            	  sql+=" , productId="+tankMeasure.getProductId();
              }
              if(tankMeasure.getPort()!=null){
            	  sql+=" , port='"+tankMeasure.getPort()+"'";
              }
              if(tankMeasure.getNormalDensity()!=null){
            	  sql+=" , normalDensity='"+tankMeasure.getNormalDensity()+"'";
              }
              if(tankMeasure.getNormalVolume()!=null){
            	  sql+=" , normalVolume='"+tankMeasure.getNormalVolume()+"'";
              }
              if(tankMeasure.getTankTemperature()!=null){
            	  sql+=" , tankTemperature='"+tankMeasure.getTankTemperature()+"'";
              }
              if(tankMeasure.getTextDensity()!=null){
            	  sql+=" , textDensity='"+tankMeasure.getTextDensity()+"'";
              }
              if(tankMeasure.getTextTemperature()!=null){
            	  sql+=" , textTemperature='"+tankMeasure.getTextTemperature()+"'";
              }
              if(tankMeasure.getViewDensity()!=null){
            	  sql+=" , viewDensity='"+tankMeasure.getViewDensity()+"'";
              }
              if(tankMeasure.getViewVolume()!=null){
            	  sql+=" , viewVolume='"+tankMeasure.getViewVolume()+"'";
              }
              if(tankMeasure.getVolumeRatio()!=null){
            	  sql+=" , volumeRatio='"+tankMeasure.getVolumeRatio()+"'";
              }
              if(tankMeasure.getCreateTime()!=null&&tankMeasure.getCreateTime()!=-1){
            	  sql+=" , createTime="+tankMeasure.getCreateTime();
              }
              if(tankMeasure.getUserId()!=null){
            	  sql+=" , userId="+tankMeasure.getUserId();
              }
              if(tankMeasure.getDescription()!=null){
            	  sql+=" , description='"+tankMeasure.getDescription()+"'";
              }
              sql+=" where id="+tankMeasure.getId();
			executeUpdate(sql);
		} catch (RuntimeException e) {
			LOG.error("daoupdateTankMeasure失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"daoupdateTankMeasure失败",e);
		}	
	}

	@Override
	public void deleteTankMeasure(TankMeasureDto tankMeasureDto)
			throws OAException {
		try  {
                String sql="delete from t_pcs_tank_measure  where id="+tankMeasureDto.getId();
                  execute(sql);
		} catch (RuntimeException e) {
			LOG.error("daodeleteTankMeasure失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"daodeleteTankMeasure失败",e);
		}	
	}

	@Override
	public Map<String, Object> getCodeNum(String format) throws OAException {
		try  {
            String sql="SELECT CONCAT('',(IFNULL(max(notify),"+format+"000)+1))  codenum   from t_pcs_tank_measure WHERE notify LIKE '%"+format+"%'";
            return   executeProcedureOne(sql);
	} catch (RuntimeException e) {
		LOG.error("daodeleteTankMeasure失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR,"daodeleteTankMeasure失败",e);
	}	
	}

	@Override
	public Map<String, Object> getTankMeasureMsg(TankMeasureDto tankMeasureDto)
			throws OAException {
		try  {
			if(tankMeasureDto.getTankId()!=null&&tankMeasureDto.getTankId()!=0){
           String sql =" select * from t_pcs_tank_measure where tankId="+tankMeasureDto.getTankId()+" order by id desc limit 0,1";
            return   executeProcedureOne(sql);
			}
	} catch (RuntimeException e) {
		LOG.error("daodeleteTankMeasure失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR,"daodeleteTankMeasure失败",e);
	}
		return null;	
	}

	/**
	 * @Title checkInvoiceTankName
	 * @Descrption:TODO
	 * @param:@param tankName
	 * @param:@return
	 * @param:@throws OAException
	 * @auhor jiahy
	 * @date 2016年4月6日上午10:32:56
	 * @throws
	 */
	@Override
	public int checkInvoiceTankName(String tankName) throws OAException {
		try {
			StringBuilder sql=new StringBuilder();
			sql.append(" select count(1) from t_pcs_tank_measure a inner join t_pcs_tank b on a.tankId=b.id where b.code='").append(tankName).append("'");
			return (int) getCount(sql.toString());
		} catch (RuntimeException e) {
			LOG.error("checkInvoiceTankName失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR,"checkInvoiceTankName失败",e);
		}
	}

}
