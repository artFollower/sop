package com.skycloud.oa.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.common.service.SecurityCodeService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 公共控制类
 * 
 * @ClassName: CommonController
 * @Description: TODO
 * @author xie
 * @date 2015年4月2日 下午10:46:05
 */
@Controller
@RequestMapping("/securityCode")
public class SecurityCodeController {

	@Autowired
	SecurityCodeService securityCodeService;

	/**
	 * 查询基本参数信息
	 * 
	 * @Description: TODO
	 * @author xie
	 * @date 2015年7月28日 下午10:18:02
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/send")
	public Object send(String key,String userId,String content) {
		OaMsg msg = new OaMsg();
		securityCodeService.sendPermissionCode(key,userId,content,msg);
		return msg;
	}
	@ResponseBody
	@RequestMapping(value = "/sendMsg")
	public Object sendMsg(String key,String userId,String content){
		OaMsg msg=new OaMsg();
		securityCodeService.sendMsg(key,userId,content,msg);
		return msg;
	}
	
	
}
