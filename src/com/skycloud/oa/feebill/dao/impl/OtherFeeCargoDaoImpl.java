/**
 * 
 */
package com.skycloud.oa.feebill.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dao.OtherFeeCargoDao;
import com.skycloud.oa.feebill.dto.OtherFeeCargoDto;
import com.skycloud.oa.feebill.model.OtherFeeCargo;

/**
 *
 * @author jiahy
 * @version 2015年10月21日 下午2:50:33
 */
@Component
public class OtherFeeCargoDaoImpl extends BaseDaoImpl implements
		OtherFeeCargoDao {
	private static Logger LOG = Logger.getLogger(OtherFeeCargoDaoImpl.class);
	@Override
	public List<Map<String, Object>> getList(OtherFeeCargoDto oDto, int start,
			int limit) throws OAException {
		try {
	    String sql="select a.id id ,a.cargoId cargoId, a.count count,a.otherFeeId otherFeeId,b.code cargoCode,a.itemFee"
	    		+ " from t_pcs_other_fee_cargo a "
	    		+ " left join t_pcs_cargo b on a.cargoId=b.id"
	    		+ " where 1=1 ";
	               if(oDto.getOtherFeeId()!=null){
	            	   sql+=" and otherFeeId="+oDto.getOtherFeeId();
	               }
	              if(limit!=0){
	            	  sql+=" limit "+start+" , "+limit;
	              }
	    return executeQuery(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取其他费用包含货批失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取其他费用包含货批失败", e);
	}
	}

	@Override
	public int getListCount(OtherFeeCargoDto oDto) throws OAException {
		try {
			 String sql="select count(a.id) "
			    		+ " from t_pcs_other_fee_cargo a "
			    		+ "where 1=1 ";
			               if(oDto.getOtherFeeId()!=null){
			            	   sql+=" and otherFeeId="+oDto.getOtherFeeId();
			               }
			    return (int) getCount(sql);
	}catch (RuntimeException e) {
		LOG.error("dao获取其他费用包含货批列表数量失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao获取其他费用包含货批数量失败", e);
	}
	}

	@Override
	public int addOtherFeeCargo(OtherFeeCargo otherFeeCargo)
			throws OAException {
		try {
			return Integer.valueOf(save(otherFeeCargo).toString());
	}catch (RuntimeException e) {
		LOG.error("dao添加其他费用包含货批失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加费用失败", e);
	}
	}

	@Override
	public void updateOtherFeeCargo(OtherFeeCargo otherFeeCargo)
			throws OAException {
		try {
		    String sql=" update t_pcs_other_fee_cargo set id=id"; 
			if(otherFeeCargo.getId()!=null){
				sql+=",id="+otherFeeCargo.getId();
			}
			if(otherFeeCargo.getOtherFeeId()!=null){
				sql+=",otherFeeId="+otherFeeCargo.getOtherFeeId();
			}
			if(otherFeeCargo.getCargoId()!=null){
				sql+=",cargoId="+otherFeeCargo.getCargoId();
			}
			if(otherFeeCargo.getItemFee()!=null){
				sql+=",itemFee='"+otherFeeCargo.getItemFee()+"'";
			}
			if(otherFeeCargo.getCount()!=null){
				sql+=",count='"+otherFeeCargo.getCount()+"'";
			}
             sql+=" where id="+otherFeeCargo.getId();   
			executeUpdate(sql);
	}catch (RuntimeException e) {
		LOG.error("dao更新其他费用包含货批失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新其他费用包含货批失败", e);
	}
	}

	@Override
	public void deleteOtherFeeCargo(OtherFeeCargoDto oDto)
			throws OAException {
		try {
			String sql=" delete from t_pcs_other_fee_cargo where 1=1 ";
			if(oDto.getId()!=null){
				sql+=" and id="+oDto.getId();
			}else if(oDto.getOtherFeeId()!=null){
				sql+=" and otherFeeId="+oDto.getOtherFeeId();
			}
			executeUpdate(sql);
	}catch (RuntimeException e) {
		LOG.error("dao添加其他费用包含货批失败");
		throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加费用失败", e);
	}
	}

}
