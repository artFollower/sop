package com.skycloud.oa.order.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.base.dao.BaseDao;
import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.order.dto.GetIntentionDto;
import com.skycloud.oa.order.dto.IntentionDto;
import com.skycloud.oa.order.model.Intention;

public interface IntentionDao{
/**
 * 添加订单意向
 * @author yanyufeng
 * @param intention
 * @return 
 */
public int addIntention(Intention intention);

/**
 * 获得当年相同类型的意向数量
 * @author yanyufeng
 * @param code
 * @return
 */
public int getTheSameIntention(String code);

/**
 * 删除订单意向
 * @author yanyufeng
 * @param intentionId
 */
public void deleteIntention(String intentionId);

/**
 * 修改订单意向
 * @author yanyufeng
 * @param intention
 */
public void updateIntention(Intention intention);

/**
 * 多条件查询所有订单意向
 * @author yanyufeng
 * @param intentionDto
 * @param start
 * @param limit
 * @return
 */
public List<Map<String, Object>> getIntentionListByCondition(IntentionDto intentionDto,int start,int limit);

/**
 * 多条件查询所有订单意向的条数
 * @author yanyufeng
 * @param intentionDto
 * @return
 */
public int getIntentionListCountByCondition(IntentionDto intentionDto);

/**
 * 更新意向状态
 * @author yanyufeng
 * @param id
 * @param status
 */
public void updateIntentionStatus(int id,int status);

///**
// * 智能搜索订单意向
// * @author yanyufeng
// * @param text
// * @return
// */
//public List<GetIntentionDto> getIntentionListBySmart(String text,int start,int limit);
//
///**
// * 智能搜索订单意向数量
// * @author yanyufeng
// * @param text
// * @return
// */
//public int getIntentionListBySmart(String text);
}
