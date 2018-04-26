/**
 * 
 */
package com.skycloud.oa.message.service;

import com.skycloud.oa.message.dto.MessageDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;



/**
 * 消息中心业务逻辑处理接口类
 * @ClassName: MessageService 
 * @Description: TODO
 * @author yanyf
 */
public interface MessageService {
	
	

	/**
	 * 创建消息
	 * @author yanyufeng
	 * @param messageDto
	 * @param msg
	 */
	void create(MessageDto messageDto,OaMsg msg);
	


	/**
	 * 查询消息
	 * @author yanyufeng
	 * @param messageDto
	 * @param pageView
	 * @param msg
	 */
	void get(MessageDto messageDto,PageView pageView,OaMsg msg);
	



	/**
	 * 删除消息
	 * @author yanyufeng
	 * @param ids
	 * @param msg
	 */
	void delete(String ids,OaMsg msg);
	
	/**
	 * 更新状态为已读
	 * @author yanyufeng
	 * @param ids
	 * @param msg
	 */
	void updateReadStatus(String ids,OaMsg msg);
	
	
	void getCount(MessageDto messageDto,OaMsg msg);
	
	void sendSystemMessage(String content,OaMsg msg);



	/**
	 * 设置全部为已读
	 * @author yanyufeng
	 * @param id
	 * 2016-3-29 下午3:19:20
	 * @param msg 
	 */
	void setAllRead(int id, OaMsg msg);
	
	
}
