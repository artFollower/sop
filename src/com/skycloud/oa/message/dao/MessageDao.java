package com.skycloud.oa.message.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.auth.model.Role;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.message.dto.MessageDto;
import com.skycloud.oa.message.model.MessageContent;
import com.skycloud.oa.message.model.MessageGet;

/**
 * 消息中心持久化类
 * @ClassName: MessageDao 
 * @Description: TODO
 * @author yanyf
 */
public interface MessageDao  {
	


	/**
	 * 创建消息
	 * @author yanyufeng
	 * @param messageDto
	 * @return
	 * @throws OAException
	 */
	void createMessage(MessageDto messageDto,List<String> getUserIdList) throws OAException;
	
	void createSysMessage(int id,String content)throws OAException;
	
	/**
	 * 插入消息内容表
	 * @author yanyufeng
	 */
	int addMessageContent(MessageContent message)throws OAException;
	
	/**
	 * 查询消息
	 * @author yanyufeng
	 * @param messageDto
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(MessageDto messageDto,long start,long limit) throws OAException;
	
	
	/**
	 * 计数
	 * @author yanyufeng
	 * @param messageDto
	 * @return
	 * @throws OAException
	 */
	long count(MessageDto messageDto) throws OAException;
	

	
	/**
	 * 删除消息接收表
	 * @author yanyufeng
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids) throws OAException;
	
	boolean updateReadStatus(String ids)throws OAException;
	
	List<Map<String,Object>> getCount(MessageDto messageDto)throws OAException;
	
	void  addMessageBysSQL(String sql)throws OAException;

	boolean setAllRead(int id)throws OAException;
	
}
