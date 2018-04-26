/**
 * @Title:PumpShedController.java
 * @Package com.skycloud.oa.system.controller
 * @Description TODO
 * @autor jiahy
 * @date 2016年9月2日下午1:19:24
 * @version V1.0
 */
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
import com.skycloud.oa.system.dto.PumpShedDto;
import com.skycloud.oa.system.model.PumpShed;
import com.skycloud.oa.system.service.PumpShedService;

/**
 * 泵棚
 * @ClassName PumpShedController
 * @Description TODO
 * @author jiahy
 * @date 2016年9月2日下午1:19:24
 */
@Controller
@RequestMapping("/pumpshed")
public class PumpShedController {
	@Autowired
	private PumpShedService pumpShedService;
	
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,@ModelAttribute PumpShedDto psDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
		return pumpShedService.getPumpShedList(psDto,new PageView(pagesize, page));
	}
	
	@RequestMapping("/update")
    @ResponseBody
	public Object update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute PumpShed pumpShed) throws OAException{
		return pumpShedService.updatePumpShed(pumpShed);
	}
	@RequestMapping("/add")
    @ResponseBody
	public Object add(HttpServletRequest request,HttpServletResponse response,@ModelAttribute PumpShed pumpShed) throws OAException{
    	return pumpShedService.addPumpShed(pumpShed);
	}
	@RequestMapping("/delete")
    @ResponseBody
	public Object delete(HttpServletRequest request,HttpServletResponse response,@ModelAttribute PumpShedDto psDto) throws OAException{
		 return pumpShedService.deletePumpShed(psDto);
	}
}
