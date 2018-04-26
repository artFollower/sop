package com.skycloud.oa.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.BerthDto;
import com.skycloud.oa.system.model.Berth;
import com.skycloud.oa.system.service.BerthService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 泊位基础模块
 * @author jiahy
 *
 */
@Controller
@RequestMapping("/berth")
public class BerthController {

	@Autowired
	private BerthService berthService;
    //获取泊位列表
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,@ModelAttribute BerthDto berthDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg msg = berthService.getBerthList(berthDto,new PageView(pagesize, page));
		return msg;
	}
	//添加泊位
	 @RequestMapping("/add")
	    @ResponseBody
	    public Object add(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute Berth berth)throws OAException{
	    	OaMsg oaMsg=null;
	    	oaMsg=berthService.addBerth(berth);
	    	return oaMsg;
	    }
	    //更新泊位
	    @RequestMapping("/update")
	    @ResponseBody
		public Object update(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute Berth berth) throws OAException{
	    	OaMsg oaMsg=null;
	    	oaMsg=berthService.updateBerth(berth);
	    	return oaMsg;
	    }
	    //删除泊位
	    @RequestMapping("/delete")
	    @ResponseBody
	    public Object delete(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute BerthDto berthDto)throws OAException{
	    	
	    	OaMsg oaMsg=null;
	    	oaMsg=berthService.deleteBerth(berthDto);
	    	return oaMsg;
	    }

	
}
