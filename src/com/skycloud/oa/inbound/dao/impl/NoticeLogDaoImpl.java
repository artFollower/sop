package com.skycloud.oa.inbound.dao.impl;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.skycloud.oa.base.dao.impl.BaseDaoImpl;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.NoticeLogDao;
import com.skycloud.oa.inbound.dto.NoticeLogDto;
import com.skycloud.oa.inbound.model.NoticeLog;
import com.skycloud.oa.utils.Common;
@Component
public class NoticeLogDaoImpl extends BaseDaoImpl implements NoticeLogDao {

	private static Logger LOG = Logger.getLogger(NoticeLogDaoImpl.class);

	@Override
	public List<Map<String, Object>> getNoticeLogList(NoticeLogDto noticeLogDto, int start, int end) throws OAException {
		try{
			String sql="select id,content,from_unixtime(createTime) createTime,type from t_pcs_notice_log where 1=1";
			if(!Common.empty(noticeLogDto.getType())&&!Common.isNull(noticeLogDto.getType())){
				sql+=" and type="+noticeLogDto.getType();
			}
			if(!Common.empty(noticeLogDto.getStartTime())&&!Common.isNull(noticeLogDto.getStartTime())){
				sql+=" and CONVERT(varchar(100), createTime, 25)>='"+noticeLogDto.getStartTime()+"'";
			}
           if(!Common.empty(noticeLogDto.getEndTime())&&!Common.isNull(noticeLogDto.getEndTime())){
        	   sql+=" and CONVERT(varchar(100), createTime, 25)<='"+noticeLogDto.getEndTime()+"'";
			}
           sql+=" order by createTime desc";
           sql+=" limit "+start+" , "+end;
			return executeQuery(sql);
				} catch (RuntimeException e) {
				LOG.error("dao添加入库作业失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加入库作业失败", e);
				}
	}

	@Override
	public int addNoticeLog(NoticeLog noticeLog) throws OAException {
		try {
			  Serializable s=	save(noticeLog);
			  return Integer.valueOf(s.toString());
			} catch (RuntimeException e) {
			LOG.error("dao添加通知记录失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加通知记录失败", e);
			}
	}

	@Override
	public void updateNoticeLog(NoticeLog noticeLog) throws OAException {
		try{
			String sql="update t_pcs_notice_log set id=id";
			if(!Common.empty(noticeLog.getContent())){
				sql+=",content="+"'"+noticeLog.getContent()+"'";
			}
			sql+=" where id="+noticeLog.getId();
			executeUpdate(sql);
		}catch (RuntimeException e){
			LOG.error("更新通知记录失败");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao更新通知记录失败", e);
		}
	}

	@Override
	public void deleteNoticeLog(String ids) throws OAException {
		try{
			String sql="delete from t_pcs_notice_log where id in("+ids+")";
			executeQuery(sql);
		}catch(RuntimeException e){
			LOG.error("删除通知记录");
			throw new OAException(Constant.SYS_CODE_DB_ERR, "dao删除通知记录失败",e);
		}
		
		
	}

	@Override
	public int getNoticeLogCount(NoticeLogDto noticeLogDto) throws OAException {
		try{
			String sql="select count(*) from t_pcs_notice_log where 1=1";
			if(!Common.empty(noticeLogDto.getType())&&!Common.isNull(noticeLogDto.getType())){
				sql+=" and type="+noticeLogDto.getType();
			}
			if(!Common.empty(noticeLogDto.getStartTime())&&!Common.isNull(noticeLogDto.getStartTime())){
				sql+=" and CONVERT(varchar(100), createTime, 25)>='"+noticeLogDto.getStartTime()+"'";
			}
           if(!Common.empty(noticeLogDto.getEndTime())&&!Common.isNull(noticeLogDto.getEndTime())){
        	   sql+=" and CONVERT(varchar(100), createTime, 25)<='"+noticeLogDto.getEndTime()+"'";
			}
           sql+=" order by createTime desc";
			return (int) getCount(sql);
				} catch (RuntimeException e) {
				LOG.error("dao添加入库作业失败");
				throw new OAException(Constant.SYS_CODE_DB_ERR, "dao添加入库作业失败", e);
				}
	}
}
