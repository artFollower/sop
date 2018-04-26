package com.skycloud.oa.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.system.model.OperateLog;
import com.skycloud.oa.system.service.OperateLogService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 操作日志控制类
 * 
 * @ClassName: LoggerController
 * @Description: TODO
 * @author xie
 * @date 2015年2月8日 上午3:35:14
 */
@Controller
@RequestMapping("/sys/log")
public class OperateLogController {

	@Autowired
	private OperateLogService operateLogService;

	/**
	 * 查询操作日志
	 * @Description: TODO
	 * @author xie
	 * @date 2015年2月8日 上午3:37:38  
	 * @param logger
	 * @param pagesize
	 * @param page
	 * @return
	 */
	@RequestMapping(value = "get")
	@ResponseBody
	public Object get(@ModelAttribute OperateLog log, @RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) {
		OaMsg msg = new OaMsg();
		operateLogService.get(log, new PageView(pagesize, page), msg);
		return msg;
	}
}
