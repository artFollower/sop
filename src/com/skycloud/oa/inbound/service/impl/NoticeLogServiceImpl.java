package com.skycloud.oa.inbound.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dao.NoticeLogDao;
import com.skycloud.oa.inbound.dto.NoticeLogDto;
import com.skycloud.oa.inbound.model.NoticeLog;
import com.skycloud.oa.inbound.service.NoticeLogService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

@Service
public class NoticeLogServiceImpl implements NoticeLogService {
	private static Logger LOG = Logger.getLogger(NoticeLogServiceImpl.class);
@Autowired
private NoticeLogDao noticeLogDao;	
	
	@Override
	public OaMsg getNoticeLogList(NoticeLogDto noticeLogDto,PageView pageView) throws OAException {
		OaMsg oaMsg=new OaMsg();
		try {
			oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
			oaMsg.getData().addAll(noticeLogDao.getNoticeLogList(noticeLogDto,pageView.getStartRecord(),pageView.getMaxresult()));
			oaMsg.getMap().put(Constant.TOTALRECORD, noticeLogDao.getNoticeLogCount(noticeLogDto)+"");
			oaMsg.setMsg("查询成功");
			return oaMsg;
		} catch (RuntimeException  re) {
			LOG.error("service通知记录查询失败",re);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service通知记录查询失败",re);
		}
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_NOTICELOG,type=C.LOG_TYPE.CREATE)
	public OaMsg addNoticeLog(NoticeLog noticeLog) throws OAException {
		OaMsg oaMsg=new OaMsg();
         try{
        	 oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
        	 noticeLogDao.addNoticeLog(noticeLog);
        	 
        	 return oaMsg;
         }catch (RuntimeException e){
        	 LOG.error("service通知记录添加失败",e);
 			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
 			throw new OAException(Constant.SYS_CODE_DB_ERR, "service通知记录添加失败",e);
        	 
         }		
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_NOTICELOG,type=C.LOG_TYPE.UPDATE)
	public OaMsg updateNoticeLog(NoticeLog noticeLog) throws OAException {
		OaMsg oaMsg=new OaMsg();
        try{
       	 oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
       	 noticeLogDao.updateNoticeLog(noticeLog);
       	 
       	 return oaMsg;
        }catch (RuntimeException e){
       	 LOG.error("service通知记录更新失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service通知记录更新失败",e);
       	 
        }		
	}


	@Override
	@Log(object=C.LOG_OBJECT.PCS_NOTICELOG,type=C.LOG_TYPE.DELETE)
	public OaMsg deleteNoticeLog(String ids) throws OAException {
		OaMsg oaMsg=new OaMsg();
        try{
       	 oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
       	 noticeLogDao.deleteNoticeLog(ids);
       	 return oaMsg;
        }catch (RuntimeException e){
       	 LOG.error("service通知记录删除失败",e);
			oaMsg.setCode(Constant.SYS_CODE_DB_ERR);
			throw new OAException(Constant.SYS_CODE_DB_ERR, "service通知记录删除失败",e);
       	 
        }		
	}

	
	
}
