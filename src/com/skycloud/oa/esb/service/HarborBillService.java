/**
 * 
 */
package com.skycloud.oa.esb.service;

import com.skycloud.oa.esb.dto.BulkDto;
import com.skycloud.oa.esb.model.HarborBill;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;



/**
 * 港务提单业务逻辑处理接口类
 * @ClassName: HarborBillService 
 * @Description: TODO
 * @author xie
 * @date 2015年1月26日 下午2:08:40
 */
public interface HarborBillService {
	
	/**
	 * 查询港务提单
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月26日 下午1:41:00  
	 * @param messageHead
	 * @param pageView
	 * @param msg
	 */
	void get(HarborBill harborBill,PageView pageView,OaMsg msg);
	
	/**
	 * 更新港务提单
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月26日 下午1:41:11  
	 * @param messageHead
	 * @param msg
	 */
	void update(BulkDto discharge,OaMsg msg);
	
	/**
	 * 创建港务提单
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月27日 上午4:07:33  
	 * @param discharge
	 * @param msg
	 */
	void save(BulkDto discharge,OaMsg msg);
	
	/**
	 * 删除获取信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月27日 上午2:55:07  
	 * @param ids
	 * @param msg
	 */
	void delete(String ids,String catogary,OaMsg msg);
	
}
