package com.skycloud.oa.esb.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.esb.model.HarborContainer;
import com.skycloud.oa.exception.OAException;

/**
 * 港务集装箱操作持久化接口类
 * @ClassName: HarborDao 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:45:01
 */
public interface HarborContainerDao  {
	
	/**
	 * 添加港务集装箱
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:25:09  
	 * @param harborContainer
	 * @return
	 * @throws OAException
	 */
	int create(HarborContainer harborContainer) throws OAException;
	
	/**
	 * 查询港务集装箱
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:25:14  
	 * @param harborContainer
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(HarborContainer harborContainer,long start,long limit) throws OAException;
	
	
	/**
	 * 统计港务集装箱
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:25:20  
	 * @param harborContainer
	 * @return
	 * @throws OAException
	 */
	long count(HarborContainer harborContainer) throws OAException;
	
	/**
	 * 更新港务集装箱
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:25:25  
	 * @param harborContainer
	 * @return
	 * @throws OAException
	 */
	boolean modify(HarborContainer harborContainer) throws OAException;
	
	/**
	 * 删除港务集装箱
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:25:30  
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids) throws OAException;
	
}
