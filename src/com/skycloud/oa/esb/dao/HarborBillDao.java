package com.skycloud.oa.esb.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.esb.model.HarborBill;
import com.skycloud.oa.exception.OAException;

/**
 * 港务提单操作持久化接口类
 * @ClassName: HarborDao 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:45:01
 */
public interface HarborBillDao  {
	
	/**
	 * 添加港务提单
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午4:55:31  
	 * @param harbor
	 * @return
	 * @throws OAException
	 */
	int create(HarborBill harborBill) throws OAException;
	
	/**
	 * 查询港务提单
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:06  
	 * @param harborBill
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(HarborBill harborBill,long start,long limit) throws OAException;
	
	
	/**
	 * 统计港务提单
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:12  
	 * @param harborBill
	 * @return
	 * @throws OAException
	 */
	long count(HarborBill harborBill) throws OAException;
	
	/**
	 * 更新港务提单
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:19  
	 * @param harborBill
	 * @return
	 * @throws OAException
	 */
	boolean modify(HarborBill harborBill) throws OAException;
	
	/**
	 * 删除港务提单
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:24:25  
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids) throws OAException;
	
}
