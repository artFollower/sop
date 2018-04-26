package com.skycloud.oa.inbound.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.TransportProgramDto;
import com.skycloud.oa.inbound.model.TransportProgram;
import com.skycloud.oa.planmanager.dto.PlanManagerDto;


public interface TransportProgramDao{
	
	/**
	 *插入接卸方案和打循环方案
	 *@author jiahy
	 * @param transportProgram
	 * @return
	 * @throws OAException
	 */
	public int addTransportProgram(TransportProgram transportProgram ) throws OAException;
	/**
	 * 添加多次接卸的接卸方案和打循环方案
	 *@author jiahy
	 * @param transportProgram
	 * @return
	 * @throws OAException
	 */
	public int addUnloadingTransportProgram(TransportProgram transportProgram) throws OAException;
	/**
	 * 删除到港(以及到港信息下的全部货批)
	 * @author yanyufeng
	 * @param id
	 */
	public void deleteTransportProgram(String id) throws OAException;
	/**
	 * 添加倒灌方案
	 *@author jiahy
	 * @param transportProgram
	 * @return
	 * @throws OAException
	 */
	public int addBackFlowPlanTransportProgram(TransportProgram transportProgram) throws OAException;
	/**
	 * 根据时间和船舶id匹配到港信息表
	 * @author yanyufeng
	 * @param id
	 * @param startTime
	 * @param endTime
	 */
	public Map<String,Object> getTransportProgramById(TransportProgramDto transportProgramDto) throws OAException;

	/**
	 * 更新到港表
	 * @author yanyufeng
	 * @param TransportProgram
	 */
	public void updateTransportProgram(TransportProgram transportProgram) throws OAException;
	/**
	 *根据arrivalId清空传输方案信息
	 *@author jiahy
	 * @param arrivalId
	 * @param type
	 * @throws OAException
	 */
	public void cleanTransportProgram(int arrivalId) throws OAException;
	/**添加多次打循环
	 *@author jiahy
	 * @param transportProgram
	 */
	public int addBackFlowTransportProgram(TransportProgram transportProgram) throws OAException;
	/**
	 *@author jiahy
	 * @param planManagerDto
	 * @param startRecord
	 * @param maxresult
	 * @return
	 */
	public List<Map<String,Object>> getUnloadingPlanList(
			PlanManagerDto planManagerDto, int startRecord, int maxresult) throws OAException;
	/**
	 *@author jiahy
	 * @param planManagerDto
	 * @return
	 */
	public int getUnloadingPlanCount(PlanManagerDto planManagerDto) throws OAException;
	/**
	 *@author jiahy
	 * @param planManagerDto
	 * @param startRecord
	 * @param maxresult
	 * @return
	 */
	public List<Map<String,Object>> getBackFlowList(
			PlanManagerDto planManagerDto, int startRecord, int maxresult) throws OAException;
	/**
	 *@author jiahy
	 * @param planManagerDto
	 * @return
	 */
	public int getBackFlowCount(PlanManagerDto planManagerDto) throws OAException;
	/**
	 * @Title rebackTransportProgram
	 * @Descrption:TODO
	 * @param:@param id
	 * @return:void
	 * @auhor jiahy
	 * @date 2016年7月26日下午8:31:04
	 * @throws
	 */
	public void rebackTransportProgram(Integer id) throws OAException;
	/**
	 * @Title getChangeTankProgramById
	 * @Descrption:TODO
	 * @param:@param transportId
	 * @param:@return
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2017年1月3日上午10:33:04
	 * @throws
	 */
	public Map<String, Object> getChangeTankProgramById(int transportId) throws OAException;
	/**
	 * 获取接卸方案基本信息
	 * @Descrption:TODO
	 * @param:@param id
	 * @param:@return
	 * @return:Map<String,Object>
	 * @auhor jiahy
	 * @date 2017年7月14日下午2:50:33
	 * @throws
	 */
	public Map<String, Object> getMsgById(Integer id);
}
