package com.skycloud.oa.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @ClassName: IndexController 
 * @Description: TODO
 * @author xie
 * @date 2015年1月28日 下午5:23:36
 */
@Controller
@RequestMapping("/")
public class IndexController {
	
	@RequestMapping(value = "/get")
	public String get() {
		return "index";
	}

}
