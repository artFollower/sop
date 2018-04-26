package com.skycloud.oa.inbound.service;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.NoticeLogDto;
import com.skycloud.oa.inbound.model.NoticeLog;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.model.Product;
import com.skycloud.oa.utils.OaMsg;

public interface NoticeLogService {
	/**
	 * 获取通知单记录
	 * 
	 * @param pDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg getNoticeLogList(NoticeLogDto nlDto,PageView pageView)
			throws OAException;

	/**
	 * 增加通知单记录
	 * 
	 * @param product
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg addNoticeLog(NoticeLog noticeLog) throws OAException;

	/**
	 * 更新通知单记录
	 * 
	 * @param product
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg updateNoticeLog(NoticeLog noticeLog) throws OAException;

	/**
	 * 删除通知单记录
	 * 
	 * @param pDto
	 * @return
	 * @throws OAException
	 */
	public abstract OaMsg deleteNoticeLog(String ids) throws OAException;
}
