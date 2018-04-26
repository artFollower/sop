package com.skycloud.oa.inbound.dao;

import java.util.List;
import java.util.Map;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.NoticeLogDto;
import com.skycloud.oa.inbound.model.NoticeLog;
import com.skycloud.oa.inbound.model.Work;


public interface NoticeLogDao{
	/**
	 * 查询通知记录
	 * @param noticeLogDao
	 * @param start
	 * @param end
	 * @return
	 * @throws OAException
	 */
	public List<Map<String,Object>> getNoticeLogList(NoticeLogDto noticeLogDto,int start,int end) throws OAException; 
	
	/**
	 * 添加通知记录
	 * @param noticeLog
	 * @return
	 * @throws OAException
	 */
	public int addNoticeLog(NoticeLog noticeLog)throws OAException;
	/**
	 * 更新通知记录
	 * @param noticeLog
	 * @throws OAException
	 */
	public void updateNoticeLog(NoticeLog noticeLog) throws OAException;
	
	/**
	 * 删除通知记录
	 * @param ids
	 * @throws OAException
	 */
	public void deleteNoticeLog(String ids) throws OAException;

	/**
	 *查询数量
	 *@author jiahy
	 * @param noticeLogDto
	 * @return
	 */
	public int getNoticeLogCount(NoticeLogDto noticeLogDto)  throws OAException ;
}
