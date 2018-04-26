package com.skycloud.oa.inbound.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.DispatchConnectDto;
import com.skycloud.oa.inbound.dto.DispatchDto;
import com.skycloud.oa.inbound.dto.DispatchDto;
import com.skycloud.oa.inbound.model.Dispatch;
import com.skycloud.oa.inbound.service.DispatchService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *调度日志基础资料
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午3:13:30
 */
@Controller
@RequestMapping("/dispatch")
public class DispatchController {
	
	@Autowired
	private DispatchService dispatchService;
	
     //获取系统用户信息
	@RequestMapping(value="/getsystemuser")
	@ResponseBody
	public OaMsg  getSystemUser(HttpServletRequest request,HttpServletResponse response){
		OaMsg oaMsg=new OaMsg();
		//到港信息表数据
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("userId", user.getId());
		map.put("userName", user.getName());
		oaMsg.getData().add(map);
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		return oaMsg;
	}
	@RequestMapping(value="/list")
	@ResponseBody
	public OaMsg list(HttpServletRequest request,HttpServletResponse response,DispatchDto dispatchDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg oaMsg=dispatchService.getDispatchList(dispatchDto, new PageView(pagesize, page));
		return oaMsg;
	}
	@RequestMapping(value="/update")
	@ResponseBody
	public OaMsg update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute DispatchConnectDto dispatchConnectDto) throws OAException{
		OaMsg oaMsg=dispatchService.updateDispatch(dispatchConnectDto);
		return oaMsg;
	}
	@RequestMapping(value="/add")
	@ResponseBody
	public OaMsg add(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute DispatchConnectDto dispatchConnectDto)throws OAException{
		OaMsg oaMsg=dispatchService.addDispatch(dispatchConnectDto);
		return oaMsg;
	}
	@RequestMapping(value="/delete")
	@ResponseBody
	public OaMsg delete(HttpServletRequest request,HttpServletResponse response,DispatchDto dispatchDto) throws OAException{
		OaMsg oaMsg=dispatchService.deleteDispatch(dispatchDto);
		return oaMsg;
	}
}
