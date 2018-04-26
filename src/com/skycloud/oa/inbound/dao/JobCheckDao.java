package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.JobCheck;


public interface JobCheckDao{
	/**
	 * 在岗位检查表插入相应数据
	 * @author yanyufeng
	 * @param JobCheck
	 */
	public int addJobCheck(JobCheck jobCheck) throws OAException;
	
	/**
	 * 添加如果存在不添加
	 * @param jobcheck
	 * @return
	 * @throws OAException
	 */
	public int addOnlyJobCheck(JobCheck jobcheck) throws OAException;
	
	
	/**
	 * 删除到港(以及到港信息下的全部货批)
	 * @author yanyufeng
	 * @param id
	 */
	public void deleteJobCheck(String id) throws OAException;
	
	/**
	 * 根据时间和船舶id匹配到港信息表
	 * @author yanyufeng
	 * @param id
	 * @param startTime
	 * @param endTime
	 */
	public List<Map<String,Object>> getJobCheckList(int transId,String job) throws OAException;

	/**
	 * 更新到港表
	 * @author yanyufeng
	 * @param JobCheck
	 */
	public void updateJobCheck(JobCheck jobCheck) throws OAException;

	/**
	 *根据arrivalId清空
	 *@author jiahy
	 * @param id
	 * @param b 
	 */
	public void cleanJobCheck(int id, boolean b) throws OAException;
	
}
