package com.skycloud.oa.esb.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.esb.model.HarborContainerSummary;
import com.skycloud.oa.exception.OAException;

/**
 * 集装箱操作持久化接口类
 * @ClassName: HarborDao 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:45:01
 */
public interface HarborContainerSummaryDao  {
	
	/**
	 * 添加集装箱
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:25:38  
	 * @param harborContainerSummary
	 * @return
	 * @throws OAException
	 */
	int create(HarborContainerSummary harborContainerSummary) throws OAException;
	
	/**
	 * 查询集装箱
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:25:45  
	 * @param harborContainerSummary
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(HarborContainerSummary harborContainerSummary,long start,long limit) throws OAException;
	
	
	/**
	 * 统计集装箱
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:25:51  
	 * @param harborContainerSummary
	 * @return
	 * @throws OAException
	 */
	long count(HarborContainerSummary harborContainerSummary) throws OAException;
	
	/**
	 * 更新集装箱
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:25:56  
	 * @param harborContainerSummary
	 * @return
	 * @throws OAException
	 */
	boolean modify(HarborContainerSummary harborContainerSummary) throws OAException;
	
	/**
	 * 删除集装箱
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:26:02  
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids) throws OAException;
	
}
