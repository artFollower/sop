package com.skycloud.oa.esb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.skycloud.oa.esb.dto.BulkDto;
import com.skycloud.oa.esb.model.HarborBill;
import com.skycloud.oa.esb.service.HarborBillService;
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
@RequestMapping("/esb/harborBill")
public class HarborBillController {
	
	@Autowired
    private HarborBillService harborBillService;
	
	/**
	 * 查询货物
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月26日 下午1:47:56  
	 * @param head
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(
			String bill,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		HarborBill harborBill = new Gson().fromJson(bill, HarborBill.class);
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		harborBillService.get(harborBill, pageView, msg);
		return msg;
	}
	
	/**
	 * 添加
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月27日 上午4:07:05  
	 * @param bill
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create")
	public Object create(
			String bill) {
		OaMsg msg = new OaMsg();
		BulkDto dd = new Gson().fromJson(bill, BulkDto.class);
		harborBillService.save(dd, msg);
		return msg;
	}
	
	/**
	 * 报文头货物
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月26日 下午1:48:08  
	 * @param resource
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public Object update(
			String bill) {
		OaMsg msg = new OaMsg();
		BulkDto dd = new Gson().fromJson(bill, BulkDto.class);
		harborBillService.update(dd, msg);
		return msg;
	}
	
	@ResponseBody
	@RequestMapping(value = "/remove")
	public Object remove(
			String ids,String catogary) {
		OaMsg msg = new OaMsg();
		harborBillService.delete(ids,catogary, msg);
		return msg;
	}

}
