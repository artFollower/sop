package com.skycloud.oa.inbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.BerthProgram;
import com.skycloud.oa.planmanager.dto.PlanManagerDto;

/**
 * @author 作者:jiahy
 * @version 时间：2015年2月10日 下午5:54:52
 */
public interface BerthProgramDao{
	
	/**
	 * 添加靠泊方案
	 *@author jiahy
	 * @param berthProgram
	 * @return
	 * @throws OAException
	 */
	public int addBerthProgram(BerthProgram berthProgram) throws OAException;
	
	/**
	 * 根据ids删除靠泊方案
	 *@author jiahy
	 * @param id
	 * @throws OAException
	 */
	public void deleteBerthProgram(String id) throws OAException;
	/**
	 *根据靠泊id获取靠泊方案
	 *@author jiahy
	 * @param berthProgramid
	 * @return
	 * @throws OAException
	 */
	public Map<String,Object> getBerthProgramById(int berthProgramid) throws OAException;
	/**
	 *更新靠泊方案
	 *@author jiahy
	 * @param berthProgram
	 * @throws OAException
	 */
	public void updateBerthProgram(BerthProgram berthProgram) throws OAException;
	/**
	 *根据arrivalId清空数据
	 *@author jiahy
	 * @param arrivalId
	 * @throws OAException
	 */
	public void cleanBerthProgram(int arrivalId) throws OAException;

	/**
	 *@author jiahy
	 * @param planManagerDto
	 * @param startRecord
	 * @param maxresult
	 * @return
	 */
	public List<Map<String,Object>> getBerthProgramList(
			PlanManagerDto planManagerDto, int startRecord, int maxresult) throws OAException;

	/**
	 *@author jiahy
	 * @param planManagerDto
	 * @return
	 */
	public int getBerthProgramCount(PlanManagerDto planManagerDto)  throws OAException;
}
