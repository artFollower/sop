package com.skycloud.oa.feebill.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.DockFeeBillDto;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.model.DockFeeCharge;

/**
 *
 * @author jiahy
 * @version 2015年6月13日 下午1:52:53
 */
public interface DockFeeChargeDao {

	public void updateDockFeeCharge(DockFeeCharge feeCharge) throws OAException;

	public void addDockFeeCharge(DockFeeCharge feeCharge) throws OAException;
	
	public void deleteDockFeeCharge(DockFeeChargeDto dfDto) throws OAException;

	public List<Map<String,Object>> getDockFeeChargeList(DockFeeChargeDto dDto, int startRecord, int maxresult) throws OAException;

	public int getDockFeeChargeCount(DockFeeChargeDto dDto) throws OAException;

	public void cleanDockFeeCharge(DockFeeBillDto dDto) throws OAException;

	public List<Map<String, Object>> getFeeHeadList(DockFeeChargeDto dDto,int startRecord, int maxresult) throws OAException;

	public int getFeeHeadListCount(DockFeeChargeDto dDto) throws OAException;

	public List<Map<String, Object>> getFeeBillIdByTime(DockFeeChargeDto dfcDto) throws OAException;

	public void checkAndChangeFeeStatus(DockFeeBillDto dfBillDto)throws OAException;
}
