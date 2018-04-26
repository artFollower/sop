/**
 * 
 */
package com.skycloud.oa.feebill.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.OtherFeeCargoDto;
import com.skycloud.oa.feebill.model.OtherFeeCargo;

/**
 *
 * @author jiahy
 * @version 2015年10月21日 下午1:33:50
 */
public interface OtherFeeCargoDao {

	public List<Map<String,Object>> getList(OtherFeeCargoDto oDto,int start,int limit) throws OAException;
	
	public int getListCount(OtherFeeCargoDto oDto) throws OAException;
	
	public int addOtherFeeCargo(OtherFeeCargo otherFeeCargo) throws OAException;
	
	public void updateOtherFeeCargo(OtherFeeCargo otherFeeCargo) throws OAException;
	
	public void deleteOtherFeeCargo(OtherFeeCargoDto oDto) throws OAException;
	
}
