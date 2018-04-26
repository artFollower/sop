package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.CertifyAgentDto;
import com.skycloud.oa.system.model.CertifyAgent;
import com.skycloud.oa.utils.OaMsg;

/**
 * 开证单位资料
 * 
 * @author jiahy
 *
 */
public interface CertifyAgentService {

	/**
	 * 获取开证单位资料
	 * 
	 * @param caDto
	 * @return
	 * @
	 */
	public abstract OaMsg getCertifyAgentList(CertifyAgentDto caDto, PageView pageView);

	/**
	 * 增加开证单位资料
	 * 
	 * @param certifyAgent
	 * @return
	 * @
	 */
	public abstract OaMsg addCertifyAgent(CertifyAgent certifyAgent)
			;

	/**
	 * 更新开证单位资料
	 * 
	 * @param certifyAgent
	 * @return
	 * @
	 */
	public abstract OaMsg updateCertifyAgent(CertifyAgent certifyAgent)
			;

	/**
	 * 删除开证单位资料
	 * 
	 * @param caDto
	 * @return
	 * @
	 */
	public abstract OaMsg deleteCertifyAgent(String ids);
}
