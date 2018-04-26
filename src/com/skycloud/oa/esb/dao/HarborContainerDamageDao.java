package com.skycloud.oa.esb.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.esb.model.HarborContainerDamage;
import com.skycloud.oa.exception.OAException;

/**
 * 残损信息操作持久化接口类
 * @ClassName: HarborDao 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:45:01
 */
public interface HarborContainerDamageDao  {
	
	/**
	 * 添加残损信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:34  
	 * @param harborContainerDamage
	 * @return
	 * @throws OAException
	 */
	int create(HarborContainerDamage harborContainerDamage) throws OAException;
	
	/**
	 * 查询残损信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:40  
	 * @param harborContainerDamage
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(HarborContainerDamage harborContainerDamage,long start,long limit) throws OAException;
	
	
	/**
	 * 统计残损信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:46  
	 * @param harborContainerDamage
	 * @return
	 * @throws OAException
	 */
	long count(HarborContainerDamage harborContainerDamage) throws OAException;
	
	/**
	 * 更新残损信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:54  
	 * @param harborContainerDamage
	 * @return
	 * @throws OAException
	 */
	boolean modify(HarborContainerDamage harborContainerDamage) throws OAException;
	
	/**
	 * 删除残损信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:59  
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids) throws OAException;
	
}
