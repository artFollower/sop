package com.skycloud.oa.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.InspectAgentDto;
import com.skycloud.oa.system.model.InspectAgent;
import com.skycloud.oa.system.service.InspectAgentService;

/**
 * 商检控制类
 * @author yanyufeng
 *
 */
@Controller
@RequestMapping("/inspectAgent")
public class InspectAgentController {

	@Autowired
	private InspectAgentService inspectAgentService;
	


	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(
			@ModelAttribute  InspectAgentDto inspectAgentDto,
			@RequestParam(defaultValue = "10", required = false, value = "pageSize") int pageSize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page){
		PageView pageView=new PageView(pageSize, page);
		return inspectAgentService.getInpectAgentList(inspectAgentDto, pageView);
	}
	
	  @RequestMapping("/get")
	    @ResponseBody
		public Object get(HttpServletRequest request,HttpServletResponse response,@ModelAttribute InspectAgentDto inspectAgentDto,String method){
		  PageView pageView=new PageView(0, 0);
		  return inspectAgentService.getInpectAgentList(inspectAgentDto,pageView);
		}

	@RequestMapping(value = "save")
	@ResponseBody
	public Object save( @ModelAttribute InspectAgent inspectAgent) {
		return inspectAgentService.addInpectAgent(inspectAgent);
	}



	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete( String inspectAgentIds) {
		return inspectAgentService.deleteInpectAgent(inspectAgentIds);
	}
	
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update( HttpServletRequest request,HttpServletResponse response,@ModelAttribute InspectAgent inspectAgent) {
		return inspectAgentService.updateInpectAgent(inspectAgent);
	}
}
