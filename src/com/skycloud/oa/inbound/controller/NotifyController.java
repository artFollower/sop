package com.skycloud.oa.inbound.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.NotifyDto;
import com.skycloud.oa.inbound.model.Notify;
import com.skycloud.oa.inbound.service.NotifyService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.ExcelOutputUtil;
import com.skycloud.oa.utils.OaMsg;
import com.skycloud.oa.utils.ExcelOutputUtil.CallBack;

/**
 * 通知单
 * 
 * @author 作者:jiahy
 * @version 时间：2015年3月16日 下午3:13:30
 */
@Controller
@RequestMapping("/notify")
public class NotifyController {

	@Autowired
	private NotifyService notifyService;

	// 获取系统用户信息
	@RequestMapping(value = "/getsystemuser")
	@ResponseBody
	public OaMsg getSystemUser(HttpServletRequest request,
			HttpServletResponse response) {
		OaMsg oaMsg = new OaMsg();
		// 到港信息表数据
		User user = (User) SecurityUtils.getSubject().getPrincipals()
				.getPrimaryPrincipal();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", user.getId());
		map.put("userName", user.getName());
		oaMsg.getData().add(map);
		oaMsg.setCode(Constant.SYS_CODE_SUCCESS);
		return oaMsg;
	}

	// 获取通知单列表
	@RequestMapping(value = "/list")
	@ResponseBody
	public OaMsg list(
			HttpServletRequest request,
			HttpServletResponse response,
			NotifyDto notifyDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page)
			throws OAException {
		OaMsg oaMsg = notifyService.getNotifyList(notifyDto, new PageView(
				pagesize, page));
		return oaMsg;
	}

	// 更新通知单
	@RequestMapping(value = "/update")
	@ResponseBody
	public OaMsg update(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute Notify notify)
			throws OAException {
		OaMsg oaMsg = notifyService.updateNotify(notify);
		return oaMsg;
	}

	// 添加通知单
	@RequestMapping(value = "/add")
	@ResponseBody
	public OaMsg add(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute Notify notify) throws OAException {
		OaMsg oaMsg = notifyService.addNotify(notify);
		return oaMsg;
	}

	// 删除通知单
	@RequestMapping(value = "/delete")
	@ResponseBody
	public OaMsg delete(HttpServletRequest request,
			HttpServletResponse response, NotifyDto notifyDto)
			throws OAException {
		OaMsg oaMsg = notifyService.deleteNotify(notifyDto);
		return oaMsg;
	}

	// 获取通知单编号
	@RequestMapping(value = "/getcodenum")
	@ResponseBody
	public OaMsg getCodeNum(HttpServletRequest request,
			HttpServletResponse response, NotifyDto notifyDto)
			throws OAException {
		OaMsg oaMsg = notifyService.getCodeNum(notifyDto);
		return oaMsg;
	}

	// 重置通知单
	@RequestMapping(value = "/reset")
	@ResponseBody
	public OaMsg reset(HttpServletRequest request,
			HttpServletResponse response, NotifyDto notifyDto)
			throws OAException {
		OaMsg oaMsg = notifyService.resetNotify(notifyDto);
		return oaMsg;
	}
	
	@RequestMapping(value = "exportItemExcel")
	public void exportItemExcel(HttpServletRequest request,HttpServletResponse response,final NotifyDto notifyDto) {
		ExcelOutputUtil.handleExcelOutput(request, response, new CallBack() {
			
			@Override
			public HSSFWorkbook getWorkBook(HttpServletRequest request) {
				HSSFWorkbook workbook = null;
				try {
					workbook=notifyService.exportItemExcel(request,notifyDto);
				} catch (OAException e) {
					e.printStackTrace();
				}
				return workbook;
			}
		});
	}
	
	
}
