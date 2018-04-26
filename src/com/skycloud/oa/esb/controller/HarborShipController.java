package com.skycloud.oa.esb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.skycloud.oa.esb.dto.HarborShipDto;
import com.skycloud.oa.esb.model.HarborShip;
import com.skycloud.oa.esb.service.HarborShipService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 * 资源管理控制类
 * @ClassName: ResourceController 
 * @Description: TODO
 * @author xie
 * @date 2014年12月29日 下午2:22:16
 */
@Controller
@RequestMapping("/esb/harborShip")
public class HarborShipController {
	
	@Autowired
    private HarborShipService harborShipService;
	
	/**
	 * 查询港务船发货
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月24日 下午3:42:35  
	 * @param harborShip
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public Object get(
			@ModelAttribute HarborShip harborShip,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		PageView pageView = new PageView(pagesize, page);
		harborShipService.get(harborShip, pageView, msg);
		return msg;
	}
	
	/**
	 * 添加港务船发货
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月24日 下午4:42:59  
	 * @param harborShip
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/create")
	public Object create(String harborShip) {
		HarborShipDto harborShipDto = new Gson().fromJson(harborShip, HarborShipDto.class);
		OaMsg msg = new OaMsg();
		harborShipService.create(harborShipDto, msg);
		return msg;
	}
	
	/**
	 * 删除资源
	 * @Description: TODO
	 * @author xie
	 * @param ids
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/remove")
	public Object delete(String ids,String catogary) {
		OaMsg msg = new OaMsg();
		harborShipService.delete(ids,catogary, msg);
		return msg;
	}
	
	/**
	 * 资源信息更新
	 * @Description: TODO
	 * @author xie
	 * @param resource
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update")
	public Object update(
			@ModelAttribute HarborShip harborShip) {
		OaMsg msg = new OaMsg();
		harborShipService.update(harborShip, msg);
		return msg;
	}
	
	/**
	 * 上传报文
	 * @Description: TODO
	 * @author xie
	 * @date 2015年1月29日 上午10:42:21  
	 * @param harborShip
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/upload")
	public Object upload(
			@ModelAttribute HarborShip harborShip) {
		OaMsg msg = new OaMsg();
		harborShipService.upload(harborShip, msg);
		return msg;
	}

}
