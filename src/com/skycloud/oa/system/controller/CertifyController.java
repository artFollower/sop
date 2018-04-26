package com.skycloud.oa.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.dto.CertifyAgentDto;
import com.skycloud.oa.system.model.CertifyAgent;
import com.skycloud.oa.system.service.CertifyAgentService;
/**
 * 车辆操作控制类
 * @author yanyufeng
 *
 */
@Controller
@RequestMapping("/certify")
public class CertifyController {

	@Autowired
	private CertifyAgentService certifyAgentService;


	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(
			@ModelAttribute CertifyAgentDto shipDto,
			@RequestParam(defaultValue = "10", required = false, value = "pageSize") int pageSize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		PageView pageView=new PageView(pageSize, page);
		return certifyAgentService.getCertifyAgentList(shipDto, pageView);
	}

	@RequestMapping(value = "get")
	@ResponseBody
	public Object get(@ModelAttribute CertifyAgentDto shipDto,String method)  {
		PageView pageView=new PageView(0, 0);
			return certifyAgentService.getCertifyAgentList(shipDto,pageView) ;
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public Object save( @ModelAttribute CertifyAgent certifyAgent)  {
		return certifyAgentService.addCertifyAgent(certifyAgent) ;
	}



	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete( String ids)  {
		return certifyAgentService.deleteCertifyAgent(ids) ;
	}
	
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update( @ModelAttribute CertifyAgent certifyAgent)  {
		return certifyAgentService.updateCertifyAgent(certifyAgent) ;
	}
}
