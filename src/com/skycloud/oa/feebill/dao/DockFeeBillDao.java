/**
 * 
 */
package com.skycloud.oa.feebill.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.DockFeeBillDto;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.dto.FeeBillDto;
import com.skycloud.oa.feebill.model.DockFeeBill;
import com.skycloud.oa.feebill.model.FeeBill;

/**
 *
 * @author jiahy
 * @version 2015年6月14日 下午3:55:13
 */
public interface DockFeeBillDao {

	public List<Map<String,Object>> getDockFeeBillList(DockFeeBillDto dDto, int startRecord, int maxresult) throws OAException;

	public int getDockFeeBillCount(DockFeeBillDto dDto)throws OAException;

	public int addDockFeeBill(DockFeeBill dfbill)throws OAException;

	public void updateDockFeeBill(DockFeeBill dfbill)throws OAException;

	public Map<String,Object> getCodeNum(DockFeeBillDto dockFeeBillDto)throws OAException;

	public void deleteFeeBill(DockFeeBillDto dockFeeBillDto)throws OAException;

	public Map<String,Object> getDockFeeBillMsg(DockFeeBillDto dDto) throws OAException;

	public List<Map<String, Object>> getDockFeeBillExcelList( DockFeeBillDto dockFeeBillDto) throws OAException;

	public List<Map<String, Object>> getdockFeeMsgByFeeBillId(int id) throws OAException;

	public List<Map<String, Object>> getGroupFeeChargeList(int id) throws OAException;

	public void updateDockFeeStatus(int dfbillId) throws OAException;

	public void cleanFeeStatus(Integer id) throws OAException;

	public List<Map<String,Object>> getTotalFee(DockFeeBillDto dockFeeBillDto) throws OAException;

	public List<Map<String, Object>> getDockFeeBillExcelList1(DockFeeBillDto dockFeeBillDto)throws OAException;


 

}
