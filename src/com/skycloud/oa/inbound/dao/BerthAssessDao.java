package com.skycloud.oa.inbound.dao;

import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.model.BerthAssess;


/**
 * 靠泊评估表
 * @author jiahy
 *
 */
public interface BerthAssessDao{
	/**
	 * 在靠泊评估表插入相应数据
	 * @author yanyufeng
	 * @param BerthAssess
	 */
	public int addBerthAssess(BerthAssess berthAssess) throws OAException;
	
	/**
	 * 删除到港(以及到港信息下的全部货批)
	 * @author yanyufeng
	 * @param id
	 */
	public void deleteBerthAssess(String id) throws OAException;
	
	/**
	 * 根据时间和船舶id匹配靠泊评估表
	 * @author yanyufeng
	 * @param id
	 * @param startTime
	 * @param endTime
	 */
	public Map<String,Object> getBerthAssessById(int berthAssessid) throws OAException;
	
	/**
	 * 获取到港评估
	 * @Description: TODO
	 * @author xie
	 * @param arrivalId
	 * @return map
	 * @throws OAException
	 */
	public Map<String,Object> getBerthAssessByArrival(int arrivalId) throws OAException;

	/**
	 * 更新到港表
	 * @author yanyufeng
	 * @param BerthAssess
	 */
	public void updateBerthAssess(BerthAssess berthAssess) throws OAException;
	
	/**
	 * 根据arrivalId 清空靠泊评估信息
	 * @param arrivalId
	 * @throws OAException
	 */
	public void cleanBerthAssess(int arrivalId) throws OAException;
	
	/**
	 * 获取最近的审核人id
	 * @Title getUserId
	 * @Descrption:TODO
	 * @param:@return
	 * @param:@throws OAException
	 * @return:Integer
	 * @auhor jiahy
	 * @date 2016年5月20日上午9:51:14
	 * @throws
	 */
	public Integer getUserId() throws OAException;
	
	
}
