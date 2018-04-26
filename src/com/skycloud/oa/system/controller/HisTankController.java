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
import com.skycloud.oa.inbound.dto.InboundOperationDto;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.HisTank;
import com.skycloud.oa.system.dto.HisTankDto;
import com.skycloud.oa.system.service.HisTankService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 储罐基础模块
 * @author jiahy
 *
 */
@Controller
@RequestMapping("/histank")
public class HisTankController {
@Autowired
private HisTankService hisTankService;
    //获取储罐列表
    @RequestMapping("/list")
    @ResponseBody
	public Object list(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute HisTankDto tDto,@ModelAttribute InboundOperationDto ioDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg oaMsg=hisTankService.getHisTankList(tDto, new PageView(pagesize, page));
		return oaMsg;
	}
    //更新储罐
    @RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute HisTank tank) throws OAException{
		return hisTankService.updateHisTank(tank);
	}
}
