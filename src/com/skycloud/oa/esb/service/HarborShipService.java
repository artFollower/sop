/**
 * 
 */
package com.skycloud.oa.esb.service;

import com.skycloud.oa.esb.dto.HarborShipDto;
import com.skycloud.oa.esb.model.HarborShip;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;



/**
 * 港务船业务逻辑处理接口类
 * @ClassName: HarborShipService 
 * @Description: TODO
 * @author xie
 * @date 2015年1月23日 下午5:32:53
 */
public interface HarborShipService {
	
	/**
	 * 添加港务船
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月23日 下午5:33:30  
	 * @param resource
	 * @param msg
	 */
	void create(HarborShipDto harborShipDto,
			OaMsg msg);
	
	/**
	 * 查询港务船
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @param pageView
	 * @param msg
	 */
	void get(HarborShip harborShip,PageView pageView,OaMsg msg);
	
	/**
	 * 更新港务船
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @param msg
	 */
	void update(HarborShip harborShip,OaMsg msg);
	
	/**
	 * 删除港务船
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @param msg
	 */
	void delete(String ids,String catogary,OaMsg msg);
	
	/**
	 * 上传报文
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月26日 下午3:35:08  
	 * @param harborShip
	 * @param msg
	 */
	void upload(HarborShip harborShip,OaMsg msg);
	
}
