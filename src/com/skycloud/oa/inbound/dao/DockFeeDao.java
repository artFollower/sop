package com.skycloud.oa.inbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.DockFeeDto;
import com.skycloud.oa.inbound.model.DockFee;

/**
 *首期费
 * @author 作者:jiahy
 * @version 时间：2015年5月6日 下午7:44:59
 */
public interface DockFeeDao {

	/**
	 * @param dFeeDto
	 * @param startRecord
	 * @param maxresult
	 * @return
	 * @throws OAException
	 */
  public List<Map<String,Object>> getDockFeeList(DockFeeDto dFeeDto, int startRecord, int maxresult) throws OAException;

	/**
	 * @param dFeeDto
	 * @return
	 * @throws OAException
	 */
  public int getDockFeeCount(DockFeeDto dFeeDto) throws OAException;

	/**
	 * @param dockFee
	 */
	public void updateDockFee(DockFee dockFee) throws OAException;

	/**
	 * @param dockFee
	 * @return
	 */
	public int addDockFee(DockFee dockFee) throws OAException;

	/**
	 * @param dFeeDto
	 */
	public void deleteDockFee(DockFeeDto dFeeDto) throws OAException;

	/**
	 * @param dFeeDto
	 * @return
	 */
	public Map<String,Object> getCodeNum(DockFeeDto dFeeDto) throws OAException;

	public List<Map<String,Object>> getArrivalList(DockFeeDto dFeeDto, int startRecord, int maxresult) throws OAException;

	public int getArrivalListCount(DockFeeDto dFeeDto) throws OAException;

	public Map<String, Object> getDockFeeMsg(DockFeeDto dFeeDto) throws OAException;

	public  List<Map<String,Object>> getDockFeeChargeList(DockFeeDto dFeeDto) throws OAException;

	public List<Map<String, Object>> getDockFeeExcelList(DockFeeDto dFeeDto)throws OAException;

	public Map<String,Object> getDockFeePrice(Integer dockFeeId) throws OAException;

	
}
