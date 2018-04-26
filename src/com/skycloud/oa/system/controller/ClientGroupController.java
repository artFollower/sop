package com.skycloud.oa.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.model.ClientGroup;
import com.skycloud.oa.system.service.ClientGroupService;
import com.skycloud.oa.utils.OaMsg;

@Controller
@RequestMapping("/clientgroup")
public class ClientGroupController {
@Autowired
private ClientGroupService clientGroupService;


    @RequestMapping("/list")
    @ResponseBody
	public OaMsg list(HttpServletRequest request ,HttpServletResponse response,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page,String id,String name,String letter) throws OAException{
    	PageView pageView=new PageView(pagesize, page);
		OaMsg oaMsg=clientGroupService.getClientGroup(id,name,letter,pageView);
		return oaMsg;
	} 
    
    @RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ClientGroup clientGroup) throws OAException{
		OaMsg oaMsg=null;
		oaMsg= clientGroupService.updateClientGroup(clientGroup);
		return oaMsg;
	}
	@RequestMapping("/save")
    @ResponseBody
	public Object add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute ClientGroup clientGroup) throws OAException{
		OaMsg oaMsg=null;
    	oaMsg=clientGroupService.addClientGroup(clientGroup);
    	return oaMsg;
	}
	@RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,String ids) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=clientGroupService.deleteClientGroup(ids);
		return oaMsg;
	}
    
    
}
