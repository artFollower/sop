package com.skycloud.oa.esb.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.esb.model.MessageHead;
import com.skycloud.oa.exception.OAException;

/**
 * 报文头操作持久化接口类
 * @ClassName: HarborDao 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:45:01
 */
public interface MessageHeadDao  {
	
	/**
	 * 添加报文头
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午4:55:31  
	 * @param harbor
	 * @return
	 * @throws OAException
	 */
	int create(MessageHead messageHead) throws OAException;
	
	/**
	 * 查询报文头
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:06  
	 * @param harborBill
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(MessageHead messageHead,long start,long limit) throws OAException;
	
	
	/**
	 * 统计报文头
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:12  
	 * @param harborBill
	 * @return
	 * @throws OAException
	 */
	long count(MessageHead messageHead) throws OAException;
	
	/**
	 * 更新报文头
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:19  
	 * @param harborBill
	 * @return
	 * @throws OAException
	 */
	boolean modify(MessageHead messageHead) throws OAException;
	
	/**
	 * 删除报文头
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:25  
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids) throws OAException;
	
}
