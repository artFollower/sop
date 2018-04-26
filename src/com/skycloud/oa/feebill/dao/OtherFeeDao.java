/**
 * 
 */
package com.skycloud.oa.feebill.dao;

import java.util.List;
import java.util.Map;




import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.FeeBillDto;
import com.skycloud.oa.feebill.dto.OtherFeeDto;
import com.skycloud.oa.feebill.model.OtherFee;

/**
 *
 * @author jiahy
 * @version 2015年10月21日 下午1:33:34
 */
public interface OtherFeeDao {

	public List<Map<String,Object>> getList(OtherFeeDto oDto ,int start,int limit) throws OAException;
	
	public int getListCount(OtherFeeDto oDto) throws OAException;
	
	public int addOtherFee(OtherFee otherFee) throws OAException;
	
	public void updateOtherFee(OtherFee otherFee) throws OAException;
	
	public void deleteOtherFee(OtherFeeDto otherFee) throws OAException;
	
	public Map<String,Object> getCodeNum(OtherFeeDto otherFee) throws OAException;

	/**
	 *@author jiahy
	 * @param otherFeeDto
	 */
	public List<Map<String,Object>> getCargoMsg(OtherFeeDto otherFeeDto)throws OAException ;

	/**
	 *@author jiahy
	 * @param valueOf
	 * @return
	 */
	public List<Map<String,Object>> getInboundMsgByOtherId(Integer valueOf) throws OAException ;
}
