/**
 * 
 */
package com.skycloud.oa.feebill.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.FeeBillDto;
import com.skycloud.oa.feebill.model.FeeBill;

/**
 *
 * @author jiahy
 * @version 2015年6月14日 下午3:55:13
 */
public interface FeeBillDao {
 
	public List<Map<String,Object>> getFeeBillList(FeeBillDto feeBillDto,int start,int limit) throws OAException;
	
	public int getFeeBillCount(FeeBillDto feeBillDto) throws OAException;
	
	public int addFeeBill(FeeBill feeBill)throws OAException;
	
	public void updateFeeBill(FeeBill feeBill) throws OAException;
	
	public void deleteFeeBill(FeeBillDto feeBillDto) throws OAException;

	public List<Map<String,Object>> getCodeNum(FeeBillDto feeBillDto) throws OAException;
	
	public List<Map<String,Object>> getFeeBillExelList(FeeBillDto feeBillDto) throws OAException;
	
	public List<Map<String,Object>> getCargoFeeBillList(FeeBillDto feeBillDto) throws OAException;

	public List<Map<String,Object>> getTotalFee(FeeBillDto feeBillDto) throws OAException;

	/**
	 * 获取合同首期费合同性质
	 *@author jiahy
	 * @param valueOf
	 * @return
	 */
	public Map<String, Object> getCargoCodesByInitialId(Integer valueOf) throws OAException;

	/**
	 * @Title getCargoCodesByfeebillId
	 * @Descrption:TODO
	 * @param:@param valueOf
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年4月7日上午9:27:07
	 * @throws
	 */
	public Map<String, Object> getCargoCodesByfeebillId(Integer feebillId) throws OAException;

	/**
	 * @param i 
	 * @Title getUpLoadFilesByfeebillId
	 * @Descrption:TODO
	 * @param:@param valueOf
	 * @param:@return
	 * @return:Map<? extends String,? extends Object>
	 * @auhor jiahy
	 * @date 2016年7月18日上午10:24:32
	 * @throws
	 */
	public Map<String, Object> getUpLoadFilesByfeebillId(Integer valueOf, Integer type) throws OAException ;

	/**
	 * @Title getFeeChargeList
	 * @Descrption:TODO
	 * @param:@param feeBillDto
	 * @param:@return
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年7月27日上午7:47:32
	 * @throws
	 */
	public List<Map<String, Object>> getBillFeeList(FeeBillDto feeBillDto) throws OAException;

	/**
	 * @Title getFeeMsg
	 * @Descrption:TODO
	 * @param:@param fDto
	 * @param:@return
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2016年7月27日下午2:25:24
	 * @throws
	 */
	public Map<String, Object> getFeeMsg(FeeBillDto fDto) throws OAException;

	/**
	 * @Title getFeeChargeList
	 * @Descrption:TODO
	 * @param:@param fDto
	 * @param:@return
	 * @return:Object
	 * @auhor jiahy
	 * @date 2016年7月27日下午2:25:28
	 * @throws
	 */
	public List<Map<String, Object>> getFeeChargeList(FeeBillDto fDto) throws OAException;

	/**
	 * @Title getGroupFeeChargeList
	 * @Descrption:TODO
	 * @param:@param feeBillDto
	 * @param:@return
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年8月1日上午9:11:30
	 * @throws
	 */
	public List<Map<String, Object>> getGroupFeeChargeList(FeeBillDto feeBillDto) throws OAException;

	/**
	 * @Title backFeeStatus
	 * @Descrption:TODO
	 * @param:@param feeBillDto
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年11月28日下午1:55:07
	 * @throws
	 */
	public void backFeeStatus(FeeBillDto feeBillDto) throws OAException;
}
