package com.skycloud.oa.inbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.InitialFeeDto;
import com.skycloud.oa.inbound.model.InitialFee;

/**
 *首期费
 * @author 作者:jiahy
 * @version 时间：2015年5月6日 下午7:44:59
 */
public interface InitialFeeDao {

	/**
	 *获取首期费列表
	 *@author jiahy
	 * @param iFeeDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public List<Map<String, Object>>  getInitialFeeList(InitialFeeDto iFeeDto ,int start,int limit) throws OAException;
	/**
	 *获取首期费列表数量
	 *@author jiahy
	 * @param iFeeDto
	 * @return
	 * @throws OAException
	 */
	public int   getInitialFeeCount(InitialFeeDto iFeeDto) throws OAException;
	/**
	 *添加首期费
	 *@author jiahy
	 * @param initialFee
	 * @return
	 * @throws OAException
	 */
	public int  addInitialFee(InitialFee initialFee) throws OAException;
	
	/**
	 *更新首期费
	 *@author jiahy
	 * @param initialFee
	 * @throws OAException
	 */
	public void updateInitialFee(InitialFee initialFee) throws OAException;
	
	/**
	 *删除首期费
	 *@author jiahy
	 * @param iFeeDto
	 * @throws OAException
	 */
	public void deleteInitialFee(InitialFeeDto iFeeDto) throws OAException;
	/**
	 *获取编号名
	 *@author jiahy
	 * @param iFeeDto
	 */
	public Map<String, Object> getCodeNum(InitialFeeDto iFeeDto) throws OAException;
	/**
	 *添加包罐包量合同的首期费
	 *@author jiahy
	 * @param initialFee
	 */
	public int addContractInitialFee(InitialFee initialFee) throws OAException;
	/**
	 *获取包罐保量合同列表
	 *@author jiahy
	 * @param iFeeDto
	 * @return
	 * @throws OAException
	 */
	public List<Map<String, Object>>  getContractFeeList(InitialFeeDto iFeeDto,int start,int limit) throws OAException;
	/**
	 *获取合同数量
	 *@author jiahy
	 * @param iFeeDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public int getContractFeeCount(InitialFeeDto iFeeDto) throws OAException;
	/**
	 *@author jiahy
	 * @param iFeeDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public List<Map<String,Object>> getList(InitialFeeDto iFeeDto, int start, int limit) throws OAException;
	/**
	 *@author jiahy
	 * @param iFeeDto
	 * @return
	 * @throws OAException
	 */
	public int getCount(InitialFeeDto iFeeDto) throws OAException;
	/**
	 * 根据合同号，起止时间获取时间内的已经入库的货批
	 *@author jiahy
	 * @param iFeeDto
	 * @return
	 */
	public List<Map<String,Object>> getContractCargoList(InitialFeeDto iFeeDto) throws OAException;
	/**
	 * 根据合同首期费id获取货批信息
	 *@author jiahy
	 * @param valueOf
	 * @return
	 */
	public List<Map<String,Object>> getInboundMsgByInitialId(Integer initialId)throws OAException;
	/**
	 *@author jiahy
	 * @param iFeeDto
	 */
	public Map<String,Object> checkCargoFee(InitialFeeDto iFeeDto) throws OAException;
	/**
	 *@author jiahy
	 * @param iFeeDto
	 */
	public void updateCargoFee(InitialFeeDto iFeeDto) throws OAException;
	/**
	 *@author jiahy
	 * @param id
	 */
	public void backStatus(Integer id) throws OAException;
	
	public List<Map<String,Object>> getOutBoundTotalList(InitialFeeDto iFeeDto) throws OAException;
	/**
	 * @param iFeeDto
	 * @param startRecord
	 * @param maxresult
	 * @return
	 * @throws OAException
	 */
	public List<Map<String,Object>> getOutBoundDetailList(InitialFeeDto iFeeDto, int startRecord, int maxresult) throws OAException;
	/**
	 * @param iFeeDto
	 * @return
	 */
	public int getOutBoundDetailCount(InitialFeeDto iFeeDto) throws OAException;
	/**
	 * @param iFeeDto
	 * @return
	 * @throws OAException
	 */
	public Map<String, Object> getDockFeeMsg(InitialFeeDto iFeeDto)throws OAException;
	/**
	 * @param iFeeDto
	 * @param startRecord
	 * @param maxresult
	 * @return
	 */
	public List<Map<String,Object>> getOilsFeeList(InitialFeeDto iFeeDto, int startRecord, int maxresult)throws OAException;
	/**
	 * @param iFeeDto
	 * @return
	 */
	public int getOilsFeeCount(InitialFeeDto iFeeDto)throws OAException;
	/**
	 * @Title getOutBoundSecurityFeeList
	 * @Descrption:TODO
	 * @param:@param iFeeDto
	 * @param:@param startRecord
	 * @param:@param maxresult
	 * @param:@return
	 * @param:@throws OAException
	 * @return:List<Map<String,Object>>
	 * @auhor jiahy
	 * @date 2016年2月15日下午7:42:18
	 * @throws
	 */
	public List<Map<String, Object>> getOutBoundSecurityFeeList(InitialFeeDto iFeeDto, int startRecord,
			int maxresult) throws OAException;
	/**
	 * @Title getOutBoundSecurityFeeCount
	 * @Descrption:TODO
	 * @param:@param iFeeDto
	 * @param:@return
	 * @return:String
	 * @auhor jiahy
	 * @date 2016年2月15日下午7:41:10
	 * @throws
	 */
	public int getOutBoundSecurityFeeCount(InitialFeeDto iFeeDto) throws OAException;
	/**
	 * @Title getOutFeeMsg
	 * @Descrption:TODO
	 * @param:@param iFeeDto
	 * @param:@return
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2016年2月23日下午2:52:25
	 * @throws
	 */
	public Map<String, Object> getOutFeeMsg(InitialFeeDto iFeeDto) throws OAException;
	/**
	 * @Title getOutBoundClientAndNum
	 * @Descrption:TODO
	 * @param:@param valueOf
	 * @param:@return
	 * @return:Map<? extends String,? extends Object>
	 * @auhor jiahy
	 * @date 2016年7月26日上午9:52:14
	 * @throws
	 */
	public Map<String, Object> getOutBoundClientAndNum(Integer valueOf) throws OAException;
	/**
	 * @Title getInitialFeeMsg
	 * @Descrption:TODO
	 * @param:@param iFeeDto
	 * @param:@return
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2016年8月3日下午2:26:44
	 * @throws
	 */
	public Map<String, Object> getInitialFeeMsg(InitialFeeDto iFeeDto) throws OAException;
}
