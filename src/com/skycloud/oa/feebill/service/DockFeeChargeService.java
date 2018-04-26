/**
 * 
 */
package com.skycloud.oa.feebill.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.DockFeeBillDto;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *费用接口
 * @author jiahy
 * @version 2015年6月14日 下午4:07:13
 */
public interface DockFeeChargeService {

	OaMsg getDockFeeChargeList(DockFeeChargeDto dDto, PageView pageView)throws OAException;

	OaMsg deleteDockFeeCharge(DockFeeChargeDto dDto)throws OAException;

	OaMsg cleanFeeChargeFromBill(DockFeeBillDto dDto)throws OAException;

	OaMsg getFeeHeadList(DockFeeChargeDto dDto, PageView pageView) throws OAException;
	
}
