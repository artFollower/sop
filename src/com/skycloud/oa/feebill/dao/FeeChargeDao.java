package com.skycloud.oa.feebill.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.model.FeeCharge;


/**
 *
 * @author jiahy
 * @version 2015年6月13日 下午1:52:53
 */
public interface FeeChargeDao {
	
	/**
	 * 获取费用列表
	 *@author jiahy
	 * @param feeChargeDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	public List<Map<String,Object>> getFeeChargeList(FeeChargeDto feeChargeDto,int start,int limit) throws OAException;
	
	/**
	 * 获取费用数量
	 *@author jiahy
	 * @param feeChargeDto
	 * @return
	 * @throws OAException
	 */
	public int getFeeChargeCount(FeeChargeDto feeChargeDto) throws OAException;
    /**
     * 添加费用
     *@author jiahy
     * @param feeCharge
     * @return
     * @throws OAException
     */
    public int addFeeCharge(FeeCharge feeCharge) throws OAException;
	/**
	 * 更新费用
	 *@author jiahy
	 * @param feeCharge
	 * @throws OAException
	 */
	public void updateFeeCharge(FeeCharge feeCharge)throws OAException;
	
	/**
	 * 删除费用
	 *@author jiahy
	 * @param feeChargeDto
	 * @throws OAException
	 */
	public int deleteFeeCharge(FeeChargeDto feeChargeDto)throws OAException;

	/**
	 * 获取开票抬头列表
	 *@author jiahy
	 * @param feeChargeDto
	 * @param startRecord
	 * @param maxresult
	 * @return
	 */
	public List<Map<String,Object>> getFeeHeadList(FeeChargeDto feeChargeDto, int startRecord, int maxresult) throws OAException;
	
	/**
	 * 获取开票抬头列表数量
	 *@author jiahy
	 * @param feeChargeDto
	 * @return
	 * @throws OAException
	 */
	public int getFeeHeadCount(FeeChargeDto feeChargeDto) throws OAException;

	/**
	 *@author jiahy
	 */
	public void cleanFeeCharge(FeeChargeDto feeChargeDto)throws OAException;

	/**
	 * 删除账单时，费用项返回之前的抬头
	 *@author jiahy
	 * @param fd
	 */
	public void backFeeChargeFeeHead(FeeChargeDto fd) throws OAException;

	/**
	 * 校验是否货批已经生成首期费，保安费
	 *@author jiahy
	 * @param feeChargeDto
	 */
	public int isMakeInitialFee(FeeChargeDto feeChargeDto) throws OAException;
	
	/**
	 * 添加费用项关系表
	 * @Title insertfeeChargeCargoLading
	 * @Descrption:TODO
	 * @param:@param id
	 * @param:@throws OAException
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年6月8日上午9:29:08
	 * @throws
	 */
	public  void insertfeeChargeCargoLading(int id) throws OAException;
	
	public List<Map<String, Object>> getFeeChargeList(FeeChargeDto feeChargeDto)throws OAException;

	/**
	 * @Title deleteCargoLadingOfFeeCharge
	 * @Descrption:TODO
	 * @param:@param feeChargeDto
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年8月16日下午8:56:28
	 * @throws
	 */
	public void deleteCargoLadingOfFeeCharge(FeeChargeDto feeChargeDto)throws OAException;
	
	
	public void updateInitialFeeAndExceedFeeStatus(Integer feeChargeId)throws OAException;
}
