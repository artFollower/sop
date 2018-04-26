package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.QualificationDto;
import com.skycloud.oa.system.model.Qualification;
import com.skycloud.oa.utils.OaMsg;

/**
 * 资质类型
 * 
 * @author jiahy
 *
 */
public interface QualificationService {
	/**
	 * 获取资质类型信息
	 * 
	 * @param qDto
	 * @param pageView 
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getQualificationList(QualificationDto qDto, PageView pageView) throws OAException;

	/**
	 * 增加资质类型信息
	 * 
	 * @param qualification
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addQualification(Qualification qualification)
			throws OAException;

	/**
	 * 更新资质类型信息
	 * 
	 * @param qualification
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateQualification(Qualification qualification)
			throws OAException;

	/**
	 * 删除资质类型信息
	 * 
	 * @param qDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteQualification(String ids) throws OAException;
}
