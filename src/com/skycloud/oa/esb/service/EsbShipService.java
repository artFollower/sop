/**
 * 
 */
package com.skycloud.oa.esb.service;

import com.skycloud.oa.esb.model.EsbShip;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;



/**
 * 港务船舶业务逻辑处理接口类
 * @ClassName: HarborBillService 
 * @Description: TODO
 * @author xie
 * @date 2015年1月26日 下午2:08:40
 */
public interface EsbShipService {
	
	/**
	 * 查询港务船舶
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月26日 下午1:41:00  
	 * @param messageHead
	 * @param pageView
	 * @param msg
	 */
	void get(EsbShip esbShip,PageView pageView,OaMsg msg);
}
