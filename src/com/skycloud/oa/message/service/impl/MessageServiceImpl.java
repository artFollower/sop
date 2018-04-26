package com.skycloud.oa.message.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skycloud.oa.auth.dao.UserDao;
import com.skycloud.oa.auth.dto.UserDto;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.message.dao.MessageDao;
import com.skycloud.oa.message.dto.MessageDto;
import com.skycloud.oa.message.model.MessageContent;
import com.skycloud.oa.message.service.MessageService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

@Service
public class MessageServiceImpl implements MessageService {
	private static Logger LOG = Logger.getLogger(MessageServiceImpl.class);
	@Autowired
	MessageDao messageDao;
	@Autowired
	UserDao userDao;
	@Override
	public void create(MessageDto messageDto, OaMsg msg) {
		try{
			List<String> getIdList=new ArrayList<String>();
			if(!Common.isNull(messageDto.getRoleId())){
				
			}
			messageDao.createMessage(messageDto, getIdList);
			msg.setMsg("添加成功");
		}catch(OAException e) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("添加失败", e);
		}

	}

	@Override
	public void get(MessageDto messageDto, PageView pageView, OaMsg msg) {
		try {
			msg.getData().addAll(messageDao.get(messageDto, pageView.getStartRecord(), pageView.getMaxresult()));
			if (pageView.getMaxresult() != 0) {
				msg.getMap().put(Constant.TOTALRECORD, messageDao.count(messageDto) + "");
				msg.getMap().put("currentPage", pageView.getCurrentpage()+"");
				msg.getMap().put("totalpage", pageView.getTotalpage()+"");
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("查询失败", o);
		}

	}

	@Override
	public void delete(String ids, OaMsg msg) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateReadStatus(String ids, OaMsg msg) {
		try {
			if(messageDao.updateReadStatus(ids)){
				msg.setCode(Constant.SYS_CODE_SUCCESS);
			}else{
				msg.setMsg("id不存在");
				msg.setCode(Constant.SYS_CODE_NULL_ERR);
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("更新失败", o);
		}
		
	}

	@Override
	public void getCount(MessageDto messageDto,OaMsg msg) {
		try {
			if(!Common.isNull(messageDto.getGetUserId())){
				msg.getData().addAll(messageDao.getCount(messageDto));
				msg.setCode(Constant.SYS_CODE_SUCCESS);
			}else{
				msg.setMsg("id不存在");
				msg.setCode(Constant.SYS_CODE_NULL_ERR);
			}
			
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("查询失败", o);
		}

	}

	@Override
	public void sendSystemMessage(String content,OaMsg msg) {
		try {
			MessageContent messageContent=new MessageContent();
			messageContent.setContent(content);
			messageContent.setType(2);
			String time=(new Date().getTime()/1000)+"";
			messageContent.setCreateTime(Long.parseLong(time));
			int id=messageDao.addMessageContent(messageContent);
			
//			List<Map<String,Object>> userList=userDao.get(new UserDto(), 0, 0);
			messageDao.createSysMessage(id, content);
			LOG.debug("插入成功");
			msg.setCode(Constant.SYS_CODE_SUCCESS);
			
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("插入失败", o);
		}
	}

	@Override
	public void setAllRead(int id,OaMsg msg) {
		try {
			if(messageDao.setAllRead(id)){
				msg.setCode(Constant.SYS_CODE_SUCCESS);
			}else{
				msg.setMsg("操作错误");
				msg.setCode(Constant.SYS_CODE_NULL_ERR);
			}
		} catch (OAException o) {
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			msg.setMsg("系统繁忙,请稍后再试");
			LOG.error("更新失败", o);
		}
		
	}

}
