/**
 * 
 */
package com.skycloud.oa.esb.service;

import com.skycloud.oa.esb.model.MessageHead;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;



/**
 * 消息头业务逻辑处理接口类
 * @ClassName: MessageHeadService 
 * @Description: TODO
 * @author xie
 * @date 2015年1月26日 下午1:38:59
 */
public interface MessageHeadService {
	
	/**
	 * 查询消息头
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月26日 下午1:41:00  
	 * @param messageHead
	 * @param pageView
	 * @param msg
	 */
	void get(MessageHead messageHead,PageView pageView,OaMsg msg);
	
	/**
	 * 更新消息头
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月26日 下午1:41:11  
	 * @param messageHead
	 * @param msg
	 */
	void update(MessageHead messageHead,OaMsg msg);
	
}
