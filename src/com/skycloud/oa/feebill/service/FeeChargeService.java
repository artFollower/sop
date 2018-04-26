/**
 * 
 */
package com.skycloud.oa.feebill.service;

import java.util.List;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.model.FeeCharge;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *费用接口
 * @author jiahy
 * @version 2015年6月14日 下午4:07:13
 */
public interface FeeChargeService {

	public OaMsg getFeeChargeList(FeeChargeDto feeChargeDto,PageView pageView) throws OAException;
	
	public OaMsg addFeeCharge(FeeCharge feeCharge) throws OAException;
	
	public OaMsg updateFeeCharge(FeeCharge feeCharge) throws OAException;
	
	public OaMsg deleteFeeCharge(FeeChargeDto feeChargeDto) throws OAException;

	public OaMsg addFeeChargeList(List<FeeCharge> feeChargeList)throws OAException;

	/**
	 * 获取费用项列表
	 *@author jiahy
	 * @param feeChargeDto
	 * @param pageView
	 * @return
	 */
	public OaMsg getFeeChargeFeeHeadList(FeeChargeDto feeChargeDto,PageView pageView) throws OAException;

	/**
	 * 从账单移除费用项
	 *@author jiahy
	 * @return
	 */
	public OaMsg cleanFeeChargeFromBill(FeeChargeDto feeChargeDto) throws OAException;

	/**
	 * 校验是否货批已经生成首期费，保安费
	 *@author jiahy
	 * @param feeChargeDto
	 * @return
	 */
	public OaMsg isMakeInitialFee(FeeChargeDto feeChargeDto) throws OAException;
	
}
