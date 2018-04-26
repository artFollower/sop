package com.skycloud.oa.system.controller;

import java.sql.Timestamp;

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
import com.skycloud.oa.system.dto.TubeDto;
import com.skycloud.oa.system.model.Tube;
import com.skycloud.oa.system.service.TubeService;
import com.skycloud.oa.utils.OaMsg;


/**
 * 管线基础模块
 * @author jiahy
 *
 */
@Controller
@RequestMapping("/tube")
public class TubeController {

	@Autowired
	private TubeService tubeService;
	

   //获取管线列表
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,@ModelAttribute TubeDto tubeDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg msg = tubeService.getTubeList(tubeDto,new PageView(pagesize, page));
		return msg;
	}
	//更新管线
	@RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Tube tube) throws OAException{
		OaMsg oaMsg=null;
    	tube.setEditTime(new Timestamp(System.currentTimeMillis()));
		oaMsg= tubeService.updateTube(tube);
		return oaMsg;
	}
	//添加管线
	@RequestMapping("/add")
    @ResponseBody
	public Object add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Tube tube) throws OAException{
    	tube.setEditTime(new Timestamp(System.currentTimeMillis()));
		OaMsg oaMsg=null;
    	oaMsg=tubeService.addTube(tube);
    	return oaMsg;
	}
	//删除管线
	@RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,@ModelAttribute TubeDto tubeDto) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=tubeService.deleteTube(tubeDto);
		return oaMsg;
	}
}
