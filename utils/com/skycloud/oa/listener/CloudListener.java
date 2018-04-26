package com.skycloud.oa.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.skycloud.oa.message.service.MsgTmpService;
import com.skycloud.oa.utils.SpringContextUtil;

@Service
public class CloudListener implements ApplicationListener<ContextRefreshedEvent>
{
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event)
	{
		// TODO Auto-generated method stub
		if(event.getApplicationContext().getParent() == null){
			//加载消息模板
			MsgTmpService msgTmpService = (MsgTmpService) SpringContextUtil.getBean("msgTmpServiceImpl");
			msgTmpService.initTemp();
		}
		
	}

}
