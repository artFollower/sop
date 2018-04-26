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
import com.skycloud.oa.system.dto.PumpDto;
import com.skycloud.oa.system.model.Pump;
import com.skycloud.oa.system.service.PumpService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 车辆操作控制类
 * @author cwj
 *
 */
@Controller
@RequestMapping("/pump")
public class PumpController {

	@Autowired
	private PumpService pumpService;
	


	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,@ModelAttribute PumpDto PumpDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		OaMsg msg = pumpService.getPumpList(PumpDto,new PageView(pagesize, page));
		return msg;
	}
	
	@RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Pump Pump) throws OAException{
		OaMsg oaMsg=null;
		oaMsg= pumpService.updatePump(Pump);
		return oaMsg;
	}
	@RequestMapping("/add")
    @ResponseBody
	public Object add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Pump Pump) throws OAException{
		OaMsg oaMsg=null;
    	oaMsg=pumpService.addPump(Pump);
    	return oaMsg;
	}
	@RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,@ModelAttribute PumpDto PumpDto) throws OAException{
		OaMsg oaMsg=null;
		oaMsg=pumpService.deletePump(PumpDto);
		return oaMsg;
	}
}
