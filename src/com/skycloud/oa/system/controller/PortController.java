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
import com.skycloud.oa.system.dto.PortDto;
import com.skycloud.oa.system.model.Port;
import com.skycloud.oa.system.service.PortService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 港口基础模块
 * @author jiahy
 *
 */
@Controller
@RequestMapping("/port")
public class PortController {

	@Autowired
	private PortService portService;
     //获取港口列表
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,@ModelAttribute PortDto portDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg msg =portService.getPortList(portDto,new PageView(pagesize, page));
		return msg;
	}
	//更新港口
	@RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Port port) throws OAException{
		OaMsg oaMsg=null;
//    	port.setEditTime(System.currentTimeMillis()/1000);
		oaMsg= portService.updatePort(port);
		return oaMsg;
	}
	//添加港口
	@RequestMapping("/add")
    @ResponseBody
	public Object add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Port port) throws OAException{
//    	port.setEditTime(System.currentTimeMillis()/1000);
		OaMsg oaMsg=null;
    	oaMsg=portService.addPort(port);
    	return oaMsg;
	}
	//删除港口
	@RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,@ModelAttribute PortDto portDto) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=portService.deletePort(portDto);
		return oaMsg;
	}
}
