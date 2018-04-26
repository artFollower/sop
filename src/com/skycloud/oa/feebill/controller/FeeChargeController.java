/**
 * 
 */
package com.skycloud.oa.feebill.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.feebill.dto.FeeChargeDto;
import com.skycloud.oa.feebill.model.FeeCharge;
import com.skycloud.oa.feebill.service.FeeChargeService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.OaMsg;

/**
 *费用
 * @author jiahy
 * @version 2015年6月14日 下午6:38:25
 */
@Controller
@RequestMapping("/feecharge")
public class FeeChargeController {
@Autowired
private FeeChargeService feeChargeService;
	
	
	 
		@RequestMapping(value="/list")
		@ResponseBody
		public OaMsg list(HttpServletRequest request,HttpServletResponse response,FeeChargeDto feeChargeDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
				@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			OaMsg oaMsg=feeChargeService.getFeeChargeList(feeChargeDto, new PageView(pagesize, page));
          return oaMsg;
		}
		
		/**
		 * 获取费用项列表
		 *@author jiahy
		 * @return
		 * @throws OAException
		 */
		@RequestMapping(value="/feechargelist")
		@ResponseBody
		public OaMsg feechargelist(HttpServletRequest request,HttpServletResponse response,FeeChargeDto feeChargeDto,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
				@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException{
			OaMsg oaMsg=feeChargeService.getFeeChargeFeeHeadList(feeChargeDto, new PageView(pagesize, page));
          return oaMsg;
		}
		
		@RequestMapping(value="/add")
		@ResponseBody
		public OaMsg add(HttpServletRequest request ,HttpServletResponse response,@ModelAttribute FeeCharge feeCharge)throws OAException{
			OaMsg oaMsg=feeChargeService.addFeeCharge(feeCharge);
			return oaMsg;
		}
		
		@RequestMapping(value="/addall")
		@ResponseBody
		public OaMsg addAll(HttpServletRequest request ,HttpServletResponse response,@RequestParam String feechargelist) throws OAException{
			List<FeeCharge> feeChargeList=new Gson().fromJson(feechargelist,new TypeToken<List<FeeCharge>>() {}.getType());
			OaMsg oaMsg=feeChargeService.addFeeChargeList(feeChargeList);
			return oaMsg;
		} 
		@RequestMapping(value="/update")
		@ResponseBody
		public OaMsg update(HttpServletRequest request,HttpServletResponse response,@ModelAttribute FeeCharge feeCharge) throws OAException{
			OaMsg oaMsg=feeChargeService.updateFeeCharge(feeCharge);
			return oaMsg;
		}
		@RequestMapping(value="/delete")
		@ResponseBody
		public OaMsg delete(HttpServletRequest request,HttpServletResponse response,FeeChargeDto  feeChargeDto) throws OAException{
			OaMsg oaMsg=feeChargeService.deleteFeeCharge(feeChargeDto);
			return oaMsg;
		}
		@RequestMapping(value="/cleanfrombill")
		@ResponseBody
		public OaMsg clean(HttpServletRequest request,HttpServletResponse response,FeeChargeDto  feeChargeDto) throws OAException{
			OaMsg oaMsg=feeChargeService.cleanFeeChargeFromBill(feeChargeDto);
			return oaMsg;
		}
		
		/**
		 * 校验是否货批已经生成首期费，保安费  cargoId
		 *@author jiahy
		 * @param request
		 * @param response
		 * @param feeChargeDto
		 * @return 
		 * @throws OAException
		 */
		@RequestMapping(value="ismakeinitialfee")
		@ResponseBody
		public OaMsg isMakeInitialfee(HttpServletRequest request,HttpServletResponse response,FeeChargeDto  feeChargeDto) throws OAException{
			OaMsg oaMsg=feeChargeService.isMakeInitialFee(feeChargeDto);
			return oaMsg;
		}
		
		
}
