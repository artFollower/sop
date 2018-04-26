package com.skycloud.oa.inbound.dao;

import com.skycloud.oa.inbound.model.ArrivalWork;


public interface ArrivalWorkDao{

	/**
	 * 添加船舶进港工作联系单
	 * @author yanyufeng
	 * @param arrivalWork
	 */
	public void addArrivalWork(ArrivalWork arrivalWork);
	
	/**
	 * 删除船舶进港工作联系单
	 * @author yanyufeng
	 * @param ids
	 */
	public void deleteArrivalWork(String ids);
	
	/**
	 * 修改船舶进港工作联系单
	 * @author yanyufeng
	 * @param arrivalWork
	 */
	public void updateArrivalWork(ArrivalWork arrivalWork);

	/**
	 * 删除货批
	 * @author yanyufeng
	 * @param cargoId
	 * 2015-8-13 下午4:55:18
	 */
	public void deleteCargo(Integer cargoId);

	public int getTheSameProductCargo(Integer cargoId);

	public void deleteWorkAndProgram(Integer cargoId);

	public void updateArrivalInfoToUnSend(Integer cargoId);
	

}
