package com.skycloud.oa.esb.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.esb.model.Harbor;
import com.skycloud.oa.exception.OAException;

/**
 * 港务操作持久化接口类
 * @ClassName: HarborDao 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:45:01
 */
public interface HarborDao  {
	
	/**
	 * 添加港务
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:26:31  
	 * @param harbor
	 * @return
	 * @throws OAException
	 */
	int create(Harbor harbor) throws OAException;
	
	/**
	 * 查询港务
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:26:38  
	 * @param harbor
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(Harbor harbor,long start,long limit) throws OAException;
	
	
	/**
	 * 统计港务信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:26:44  
	 * @param harbor
	 * @return
	 * @throws OAException
	 */
	long count(Harbor harbor) throws OAException;
	
	/**
	 * 更新港务信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:26:51  
	 * @param harbor
	 * @return
	 * @throws OAException
	 */
	boolean modify(Harbor harbor) throws OAException;
	
	/**
	 * 删除港务信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:26:58  
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids,String catogary) throws OAException;
	
}
