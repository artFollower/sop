package com.skycloud.oa.esb.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.esb.model.EsbBerth;
import com.skycloud.oa.esb.model.EsbShip;
import com.skycloud.oa.exception.OAException;

/**
 * 港务船操作持久化接口类
 * @ClassName: EsbShipDao 
 * @Description: TODO
 * @author xie
 * @date 2015年2月3日 上午1:51:34
 */
public interface EsbShipDao  {
	
	void create(EsbBerth berth);
	
	/**
	 * 添加港务船
	 * @Description: TODO
	 * @author xie
	 * @date 2015年2月3日 上午1:51:44  
	 * @param harbor
	 * @return
	 * @throws OAException
	 */
	int create(EsbShip ship) throws OAException;
	
	/**
	 * 查询船舶信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年2月11日 上午10:57:44  
	 * @param ship
	 * @param start
	 * @param limit
	 * @return
	 * @throws OAException
	 */
	List<Map<String,Object>> get(EsbShip ship,long start,long limit) throws OAException;
	
	/**
	 * 统计船舶数量
	 * @Description: TODO
	 * @author xie
	 * @date 2015年2月11日 上午10:58:14  
	 * @param ship
	 * @return
	 * @throws OAException
	 */
	long count(EsbShip ship) throws OAException;
		
}
