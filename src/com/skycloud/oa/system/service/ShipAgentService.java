package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ShipAgentDto;
import com.skycloud.oa.system.model.ShipAgent;
import com.skycloud.oa.utils.OaMsg;

/**
 * 船舶代理资料
 * 
 * @author jiahy
 *
 */
public interface ShipAgentService {
	/**
	 * 获取船舶代理资料
	 * 
	 * @param saDto
	 * @return
	 * @
	 */
	public abstract OaMsg getShipAgentList(ShipAgentDto saDto,  PageView pageView);

	/**
	 * 增加船舶代理资料
	 * 
	 * @param shipAgent
	 * @return
	 * @
	 */
	public abstract OaMsg addShipAgent(ShipAgent shipAgent);

	/**
	 * 更新船舶代理资料
	 * 
	 * @param shipAgent
	 * @return
	 * @
	 */
	public abstract OaMsg updateShipAgent(ShipAgent shipAgent)
			;

	/**
	 * 删除船舶代理资料
	 * 
	 * @param saDto
	 * @return
	 * @
	 */
	public abstract OaMsg deleteShipAgent(String ids);
}
