/**
 * @Title:TradeInvoiceController.java
 * @Package com.skycloud.oa.outbound.controller
 * @Description TODO
 * @autor jiahy
 * @date 2016年4月8日上午8:26:58
 * @version V1.0
 */
package com.skycloud.oa.outbound.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.TradeInvoiceDto;
import com.skycloud.oa.outbound.service.TradeInvoiceService;
import com.skycloud.oa.utils.OaMsg;

/**
 * 开票冲销
 * @ClassName TradeInvoiceController
 * @Description TODO
 * @author jiahy
 * @date 2016年4月8日上午8:26:58
 */
@Controller
@RequestMapping("/tradeInvoice")
public class TradeInvoiceController {
	@Autowired
	private TradeInvoiceService tradeInvoiceService;
	//获取货体的列表
	@RequestMapping(value="/getgoodslist")
	@ResponseBody
	public OaMsg getGoodsList(HttpServletRequest request,HttpServletResponse response,TradeInvoiceDto tIDto) throws OAException{
		return tradeInvoiceService.getGoodsList(tIDto);
	}
	@RequestMapping(value="/getOutboundList")
	@ResponseBody
	public OaMsg getOutboundList(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
	           @RequestParam(defaultValue = "0", required = false, value = "page") int page,TradeInvoiceDto tIDto) throws OAException{
		return tradeInvoiceService.getOutboundList(tIDto,new PageView(pagesize, page));
	}
	
	//冲销发货
	@RequestMapping(value="/tradeInvoice")
	@ResponseBody
	public OaMsg tradeInvoice(HttpServletRequest request,HttpServletResponse response,TradeInvoiceDto tIDto) throws OAException{
		return tradeInvoiceService.tradeInvoice(tIDto);
	}
	//冲销发货历史纪录
		@RequestMapping(value="/getTradeInvoiceLogList")
		@ResponseBody
		public OaMsg getTradeInvoiceLogList(HttpServletRequest request,HttpServletResponse response,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize, 
		           @RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			return tradeInvoiceService.getTradeInvoiceLogList(new PageView(pagesize, page));
		}
	
}
