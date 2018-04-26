package com.skycloud.oa.inbound.service;

import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.utils.OaMsg;

/**
 * 靠泊方案业务逻辑处理类
 * @ClassName: BerthAssessService 
 * @Description: TODO
 * @author xie
 * @date 2015年1月19日 下午7:28:11
 */
public interface BerthAssessService {
	
	/**
	 * 创建
	 * @Description: TODO
	 * @author xie
	 * @param berthAssess
	 * @param msg
	 */
	void create(BerthAssess berthAssess,OaMsg msg);
	
	/**
	 * 删除
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @param msg
	 */
	void delete(String ids,OaMsg msg);
	
	/**
	 * 获取到港计划的靠泊评估
	 * @Description: TODO
	 * @author xie
	 * @param arrivalId
	 * @param msg
	 */
	void getByArrival(String arrivalId,OaMsg msg);
	
	/**
	 * 更新靠泊评估
	 * @Description: TODO
	 * @author xie
	 * @param berthAssess
	 * @param msg
	 */
	void update(BerthAssess berthAssess,OaMsg msg);
}
