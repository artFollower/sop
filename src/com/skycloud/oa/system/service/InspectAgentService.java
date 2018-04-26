package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.InspectAgentDto;
import com.skycloud.oa.system.model.InspectAgent;
import com.skycloud.oa.utils.OaMsg;

/**
 * 商检单位资料
 * 
 * @author jiahy
 *
 */
public interface InspectAgentService {

	/**
	 * 获取商检单位资料
	 * 
	 * @param iaDto
	 * @return
	 * @
	 */
	public abstract OaMsg getInpectAgentList(InspectAgentDto iaDto, PageView pageView);

	/**
	 * 增加商检单位资料
	 * 
	 * @param inpectAgent
	 * @return
	 * @
	 */
	public abstract OaMsg addInpectAgent(InspectAgent inpectAgent)
			;

	/**
	 * 更新商检单位资料
	 * 
	 * @param inpectAgent
	 * @return
	 * @
	 */
	public abstract OaMsg updateInpectAgent(InspectAgent inpectAgent)
			;

	/**
	 * 删除商检单位资料
	 * 
	 * @param iaDto
	 * @return
	 * @
	 */
	public abstract OaMsg deleteInpectAgent(String ids);

}
