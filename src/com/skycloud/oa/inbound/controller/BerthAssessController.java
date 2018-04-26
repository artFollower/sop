package com.skycloud.oa.inbound.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.inbound.model.BerthAssess;
import com.skycloud.oa.inbound.service.BerthAssessService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 靠泊评估控制类
 * @ClassName: BerthAssessController 
 * @Description: TODO
 * @author xie
 * @date 2015年1月19日 下午8:38:03
 */
@Controller
@RequestMapping("/inbound/berthAssess")
public class BerthAssessController {
	
	@Autowired
	private BerthAssessService berthAssessService;
	
	/**
	 * 根据到港获取靠泊方案
	 * @Description: TODO
	 * @author xie
	 * @param arrivalId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(String arrivalId) {
		OaMsg msg = new OaMsg();
		berthAssessService.getByArrival(arrivalId,msg);
		return msg;
	}
	
	/**
	 * 添加靠泊评估
	 * @Description: TODO
	 * @param berthAssess
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create")
	public Object create(@ModelAttribute BerthAssess berthAssess) {
		OaMsg msg = new OaMsg();
		berthAssessService.create(berthAssess, msg);
		return msg;
	}
	
	/**
	 * 更新靠泊评估
	 * @Description: TODO
	 * @author xie
	 * @param berthAssess
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public Object update(@ModelAttribute BerthAssess berthAssess) {
		OaMsg msg = new OaMsg();
		berthAssessService.update(berthAssess, msg);
		return msg;
	}
	
	
}
