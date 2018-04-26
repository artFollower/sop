package com.skycloud.oa.esb.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.esb.model.HarborShip;
import com.skycloud.oa.exception.OAException;

/**
 * 港务船操作持久化接口类
 * @ClassName: HarborDao 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午4:45:01
 */
public interface HarborShipDao  {
	
	/**
	 * 添加港务船
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:27:15  
	 * @param harborBill
	 * @return
	 * @throws OAException
	 */
	int create(HarborShip harborShip) throws OAException;
	
	/**
	 * 查询港务船
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:27:20  
	 * @param harborBill
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(HarborShip harborShip,long start,long limit) throws OAException;
	
	
	/**
	 * 统计港务船
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:27:26  
	 * @param harborBill
	 * @return
	 * @throws OAException
	 */
	long count(HarborShip harborShip) throws OAException;
	
	/**
	 * 更新港务船
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:27:32  
	 * @param harborBill
	 * @return
	 * @throws OAException
	 */
	boolean modify(HarborShip harborShip) throws OAException;
	
	/**
	 * 删除港务船
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:27:40  
	 * @param ids
	 * @return
	 * @throws OAException
	 */
	boolean delete(String ids,String catogary) throws OAException;
	
}
