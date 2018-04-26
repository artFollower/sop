package com.skycloud.oa.system.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.ClientQualificationDto;
import com.skycloud.oa.system.model.ClientQualification;
import com.skycloud.oa.utils.OaMsg;

/**
 * 客户资质信息
 * 
 * @author jiahy
 *
 */
public interface ClientQualificationService {

	/**
	 * 获取客户资质信息
	 * 
	 * @param cqDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getClientQualificationList(
			ClientQualificationDto cqDto, PageView pageView)
			throws OAException;

	/**
	 * 添加客户资质信息
	 * 
	 * @param clientQualification
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addClientQualification(
			ClientQualification clientQualification) throws OAException;

	/**
	 * 更新客户资质信息
	 * 
	 * @param clientQualification
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateClientQualification(
			ClientQualification clientQualification) throws OAException;

	/**
	 * 删除客户资质信息
	 * 
	 * @param cqDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteClientQualification(String ids)
			throws OAException;
}
