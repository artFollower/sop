//package com.skycloud.oa.system.controller;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.skycloud.oa.system.model.User;
//import com.skycloud.oa.utils.OaMsg;
//
///**
// * 用户操作控制类
// * @ClassName: UserController 
// * @Description: TODO
// * @author xie
// * @date 2014年11月29日 下午6:16:02
// */
//@Controller
//public class UserController {
//
//	/**
//	 * 模拟登陆
//	 * @Description: TODO
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "login")
//	public String login(HttpServletRequest request, HttpServletResponse response,User user) {
//		return "home";
//	}
//	
//	@RequestMapping(value = "console")
//	public String console(HttpServletRequest request, HttpServletResponse response) {
//		return "console";
//	}
//
//	/**
//	 * 模拟退出
//	 * @Description: TODO
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "loginOut")
//	public String loginOut(HttpServletRequest request, HttpServletResponse response) {
//		return "index";
//	}
//	
//	@RequestMapping(value = "index")
//	public String index(HttpServletRequest request, HttpServletResponse response) {
//		return "index";
//	}
//	
//	@RequestMapping(value = "table")
//	@ResponseBody
//	public Object table(HttpServletRequest request, HttpServletResponse response,int start,int length) {
//		OaMsg msg = new OaMsg();
//		for(int i=0;i<20;i++) {
//			User user = new User();
//			user.setId("id"+i+start);
//			user.setName("name"+i);
//			user.setAccount("account"+i);
//			user.setPassword("password"+i);
//			msg.getData().add(user);
//		}
////		msg.setRecordsTotal(200);
////		msg.setRecordsFiltered(200);
//		return msg;
//	}
//	
//	@RequestMapping(value = "menu")
//	@ResponseBody
//	public Object menu(HttpServletRequest request, HttpServletResponse response) {
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("errorMessage", null);
//		map.put("hasErrors", false);
//		map.put("success", true);
//		
//			List<Object> list = new ArrayList<Object>();
//			Map<String,Object> menu = new HashMap<String,Object>();
//			menu.put("id", 0);
//			menu.put("name", "用户角色管理 ");
//			menu.put("description", "description");
//			menu.put("checked", false);
//			menu.put("level", 0);
//			menu.put("menuIcon", "glyphicon  glyphicon-list-alt");
//			
//			List<Object> children = new ArrayList<Object>();
//			List<Object> _children = new ArrayList<Object>();
//			Map<String,Object> child = new HashMap<String,Object>();
//			child.put("id", 1);
//			child.put("name", "用户 ");
//			child.put("description", "description");
//			child.put("checked", false);
//			child.put("level", 1);
//			child.put("children", _children);
//			child.put("menuIcon", "glyphicon  glyphicon-list-alt");
//			children.add(child);
//			
//			
//			Map<String,Object> _child = new HashMap<String,Object>();
//			_child.put("id", 2);
//			_child.put("name", "用户角色assad ");
//			_child.put("description", "description");
//			_child.put("checked", false);
//			_child.put("level", 2);
//			_child.put("children", new ArrayList<Object>());
//			_child.put("menuIcon", "glyphicon  glyphicon-list-alt");
//			_children.add(_child);
//			
//			menu.put("children", children);
//			list.add(menu);
//			map.put("data", list);
//			
//		return map;	
//	}
//}
