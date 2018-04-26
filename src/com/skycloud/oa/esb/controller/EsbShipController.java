package com.skycloud.oa.esb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.esb.model.EsbShip;
import com.skycloud.oa.esb.service.EsbShipService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 货物管理控制类
 * @ClassName: MessageHeadController 
 * @Description: TODO
 * @author xie
 * @date 2015年1月26日 下午1:46:04
 */
@Controller
@RequestMapping("/esb/esbShip")
public class EsbShipController {
	
	@Autowired
    private EsbShipService esbShipService;
	
	/**
	 * 查询港务船舶信息
	 * @Description: TODO
	 * @author xie
	 * @date 2015年2月11日 上午11:08:09  
	 * @param esbShip
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(
			EsbShip esbShip,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		esbShipService.get(esbShip, pageView, msg);
		return msg;
	}
	
}
