/**
 * 
 */
package com.skycloud.oa.feebill.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.AccountBillLogDto;
import com.skycloud.oa.feebill.model.AccountBillLog;
import com.skycloud.oa.feebill.service.AccountBillLogService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *到账历史记录
 * @author jiahy
 * @version 2015年6月14日 下午6:38:25
 */
@Controller
@RequestMapping("/accountbilllog")
public class AccountBillLogController {
@Autowired
private AccountBillLogService accountBillLogService;
	
	
	 
		@RequestMapping(value="/list")
		@ResponseBody
		public OaMsg list(HttpServletRequest request,HttpServletResponse response,AccountBillLogDto accountBillLogDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
				@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			OaMsg oaMsg=accountBillLogService.getAccountBillLogList(accountBillLogDto, new PageView(pagesize, page));
          return oaMsg;
		}
		
		@RequestMapping(value="/add")
		@ResponseBody
		public OaMsg add(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute AccountBillLog accountBillLog)throws OAException{
			OaMsg oaMsg=accountBillLogService.addAccountBillLog(accountBillLog);
			return oaMsg;
		}
		
		@RequestMapping(value="/update")
		@ResponseBody
		public OaMsg update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute AccountBillLog accountBillLog) throws OAException{
			OaMsg oaMsg=accountBillLogService.updateAccountBillLog(accountBillLog);
			return oaMsg;
		}
		@RequestMapping(value="/delete")
		@ResponseBody
		public OaMsg delete(HttpServletRequest request,HttpServletResponse response,AccountBillLogDto  accountBillLogDto) throws OAException{
			OaMsg oaMsg=accountBillLogService.deleteAccountBillLog(accountBillLogDto);
			return oaMsg;
		}
}
