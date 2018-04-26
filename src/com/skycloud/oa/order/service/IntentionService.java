/**
 * 
 */
package com.skycloud.oa.order.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dto.IntentionDto;
import com.skycloud.oa.order.model.Intention;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;


/**
 * @author yanyufeng
 *
 */
public interface IntentionService {
	
	
	/**
	 * 增加订单意向
	 * @author yanyufeng
	 * @param intention
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addIntention(Intention intention) throws OAException;
	

	/**
	 * 删除订单意向
	 * @author yanyufeng
	 * @param intentionId
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteIntention(String intentionId) throws OAException;
	
	/**
	 * 修改订单意向
	 * @author yanyufeng
	 * @param mEditTime 
	 * @param intentionId
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateIntention(Intention intention, String mEditTime) throws OAException;
	
	/**
	 * 多条件查询所有订单意向
	 * @author yanyufeng
	 * @param IntentionDto
	 * @return
	 */
	public abstract OaMsg getIntentionListByCondition(IntentionDto intentionDto,PageView pageView) throws OAException;


	
}
