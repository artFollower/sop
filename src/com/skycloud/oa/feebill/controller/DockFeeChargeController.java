/**
 * 
 */
package com.skycloud.oa.feebill.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.DockFeeBillDto;
import com.skycloud.oa.feebill.dto.DockFeeChargeDto;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.service.DockFeeChargeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *费用
 * @author jiahy
 * @version 2015年6月14日 下午6:38:25
 */
@Controller
@RequestMapping("/dockfeecharge")
public class DockFeeChargeController {
@Autowired
private DockFeeChargeService dockFeeChargeService;
		@RequestMapping(value="/list")
		@ResponseBody
		public OaMsg list(HttpServletRequest request,HttpServletResponse response,DockFeeChargeDto dDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
				@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			OaMsg oaMsg=dockFeeChargeService.getDockFeeChargeList(dDto, new PageView(pagesize, page));
          return oaMsg;
		}
		
		@RequestMapping(value="/getFeeHeadList")
		@ResponseBody
		public OaMsg getFeeHeadList(HttpServletRequest request,HttpServletResponse response,DockFeeChargeDto dDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
				@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			OaMsg oaMsg=dockFeeChargeService.getFeeHeadList(dDto, new PageView(pagesize, page));
          return oaMsg;
		}
		
		@RequestMapping(value="/delete")
		@ResponseBody
		public OaMsg delete(HttpServletRequest request,HttpServletResponse response,DockFeeChargeDto dDto) throws OAException{
			OaMsg oaMsg=dockFeeChargeService.deleteDockFeeCharge(dDto);
			return oaMsg;
		}
		@RequestMapping(value="/cleanfrombill")
		@ResponseBody
		public OaMsg clean(HttpServletRequest request,HttpServletResponse response,DockFeeBillDto dDto) throws OAException{
			OaMsg oaMsg=dockFeeChargeService.cleanFeeChargeFromBill(dDto);
			return oaMsg;
		}
		
}
