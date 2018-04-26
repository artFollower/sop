package com.skycloud.oa.outbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.ShipDeliverMeasureDto;
import com.skycloud.oa.outbound.model.ShipDeliveryMeasure;

/**
 * 
 * <p>台帐管理-流量计台帐</p>
 * @ClassName:ShipDeliveryMeasureDao
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年8月31日 上午9:46:23
 *
 */
public interface ShipDeliveryMeasureDao 
{
	/**
	 * 流量计台帐列表
	 * @Title:ShipDeliveryMeasureDao
	 * @Description:
	 * @param shipDeliverMeasureDto
	 * @param pageView
	 * @return
	 * @throws Exception
	 * @Date:2015年8月31日 上午9:46:39
	 * @return:List<Map<String,Object>> 
	 * @throws
	 */
	public List<Map<String,Object>> list(ShipDeliverMeasureDto shipDeliverMeasureDto,PageView pageView) throws Exception;
	
	/**
	 * 流量计台帐列表记录总数
	 * @Title:ShipDeliveryMeasureDao
	 * @Description:
	 * @param shipDeliverMeasureDto
	 * @return
	 * @throws Exception
	 * @Date:2015年8月31日 上午9:48:19
	 * @return :int 
	 * @throws
	 */
	public int getCount(ShipDeliverMeasureDto shipDeliverMeasureDto) throws Exception ;
	
	/**
	 * 根据ID查询某条记录
	 * @Title:ShipDeliveryMeasureDao
	 * @Description:
	 * @param id
	 * @return
	 * @throws Exception
	 * @Date:2015年8月31日 上午9:49:29
	 * @return:Map<String,Object> 
	 * @throws
	 */
	public Map<String,Object> get(int id) throws Exception;
	
	/**
	 * 根据ID删除某条记录
	 * @Title:ShipDeliveryMeasureDao
	 * @Description:
	 * @param id
	 * @throws Exception
	 * @Date:2015年8月31日 上午9:50:09
	 * @return :void 
	 * @throws
	 */
	public void delete(ShipDeliverMeasureDto sDto) throws Exception;
	
	/**
	 * 根据ID更新某条记录
	 * @Title:ShipDeliveryMeasureDao
	 * @Description:
	 * @param shipDelvieryMeasure
	 * @throws Exception
	 * @Date:2015年8月31日 上午9:50:53
	 * @return :void 
	 * @throws
	 */
	public void update(ShipDeliveryMeasure shipDelvieryMeasure) throws Exception;
	
	/**
	 * 根据ID添加某条记录
	 * @Title:ShipDeliveryMeasureDao
	 * @Description:
	 * @param shipDelvieryMeasure
	 * @return
	 * @throws Exception
	 * @Date:2015年8月31日 上午9:51:12
	 * @return :int 
	 * @throws
	 */
	public int add(ShipDeliveryMeasure shipDelvieryMeasure) throws Exception;
}
