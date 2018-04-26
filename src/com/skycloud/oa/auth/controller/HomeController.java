package com.skycloud.oa.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户管理控制台控制类
 * 
 * @ClassName: HomeController
 * @Description: TODO
 * @author xie
 * @date 2014年12月26日 下午5:15:28
 */
@Controller
public class HomeController {

	/**
	 * 进入控制台
	 * @Description: TODO
	 * @author xie
	 * @return
	 */
	@RequestMapping(value = "/")
	public String home() {
		return "home";
	}
}
