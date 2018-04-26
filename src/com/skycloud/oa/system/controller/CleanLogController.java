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
import com.skycloud.oa.system.dto.TankCleanLogDto;
import com.skycloud.oa.system.dto.TubeCleanLogDto;
import com.skycloud.oa.system.service.TankCleanLogService;
import com.skycloud.oa.system.service.TubeCleanLogService;
import com.skycloud.oa.utils.OaMsg;


/**
 * 管线，储罐清洗记录模块
 * @author jiahy
 *
 */
@Controller
@RequestMapping("/cleanlog")
public class CleanLogController {

	@Autowired
	private TankCleanLogService tankCleanLogService;
	@Autowired
	private TubeCleanLogService tubeCleanLogService;

    //获取储罐的清洗记录
	@RequestMapping(value = "tanklist")
	@ResponseBody
	public Object tankList(HttpServletRequest request, HttpServletResponse response,@ModelAttribute TankCleanLogDto tDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg msg = tankCleanLogService.getTankCleanLogList(tDto,new PageView(pagesize, page));
		return msg;
	}
	//获取管线的清洗记录
	@RequestMapping(value = "tubelist")
	@ResponseBody
	public Object tubeList(HttpServletRequest request, HttpServletResponse response,@ModelAttribute TubeCleanLogDto tDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg msg = tubeCleanLogService.getTubeCleanLogList(tDto,new PageView(pagesize, page));
		return msg;
	}
	
}
