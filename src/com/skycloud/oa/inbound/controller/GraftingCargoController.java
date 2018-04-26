package com.skycloud.oa.inbound.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.inbound.dto.CargoGoodsDto;
import com.skycloud.oa.inbound.model.Goods;
import com.skycloud.oa.inbound.model.GraftingHistory;
import com.skycloud.oa.inbound.service.GraftingCargoService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.outbound.dto.GoodsDto;
import com.skycloud.oa.outbound.service.LadingService;
import com.skycloud.oa.utils.OaMsg;


@Controller
@RequestMapping("/grafting")
public class GraftingCargoController {
	
	@Autowired
	private GraftingCargoService graftingCargoService;
	
	@Autowired
	private LadingService ladingService;
	

	/**
	 * 查询可以嫁接的货主
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @return
	 * @throws OAException
	 * 2015-10-8 下午6:19:28
	 */
	@RequestMapping(value = "getClient")
	@ResponseBody
	public Object getClient(HttpServletRequest request, HttpServletResponse response) throws OAException {
		OaMsg msg = graftingCargoService.getClient();
		return msg;
	}

	/**
	 * 查询可以嫁接的货体
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 * @throws OAException
	 * 2015-10-8 下午6:19:44
	 */
	@RequestMapping(value = "getGraftingGoods")
	@ResponseBody
	public Object getGraftingGoods(HttpServletRequest request, HttpServletResponse response, Goods goods) throws OAException {
		OaMsg msg = graftingCargoService.getGraftingGoods(goods);
		return msg;
	}
	
	
	/**
	 * 查询可以被嫁接的货体
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 * @throws OAException
	 * 2015-10-8 下午6:19:44
	 */
	@RequestMapping(value = "toGraftingGoods")
	@ResponseBody
	public Object toGraftingGoods(HttpServletRequest request, HttpServletResponse response, Goods goods) throws OAException {
		OaMsg msg = graftingCargoService.toGraftingGoods(goods);
		return msg;
	}
	
	/**
	 * 整批嫁接
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param virtualGoodsId
	 * @param realId
	 * @return
	 * @throws OAException
	 * 2015-10-20 下午5:39:52
	 */
	@RequestMapping(value = "graftingAll")
	@ResponseBody
	public Object graftingAll(HttpServletRequest request, HttpServletResponse response,int virtualGoodsId,int realId) throws OAException {
		OaMsg msg = graftingCargoService.graftingAll(virtualGoodsId,realId);
		return msg;
	}
	
	
	@RequestMapping(value = "graftingPart")
	@ResponseBody
	public Object graftingPart(HttpServletRequest request, HttpServletResponse response,int virtualGoodsId,int realId,double count) throws OAException {
		OaMsg msg=new OaMsg();
		msg.setCode(Constant.SYS_CODE_SUCCESS);
		int sqlitedGoodsId=graftingCargoService.sqlitGoods(virtualGoodsId,count);
		msg.setMsg(sqlitedGoodsId+"");
		if(sqlitedGoodsId==0){
			msg.setCode(Constant.SYS_CODE_DB_ERR);
			return msg;
		}else{
			
			msg = graftingCargoService.graftingAll(sqlitedGoodsId,realId);
		}
		
		return msg;
	}
	
	
	/**
	 * 查询是否有待提
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 * @throws OAException
	 * 2015-10-23 下午3:10:53
	 */
	@RequestMapping(value = "checkWaitOut")
	@ResponseBody
	public Object checkWaitOut(HttpServletRequest request, HttpServletResponse response,int goodsId) throws OAException {
		OaMsg msg = graftingCargoService.checkWaitOut(goodsId);
		return msg;
	}
	
	
	/**
	 * 查询是否有待提
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param goods
	 * @return
	 * @throws OAException
	 * 2015-10-23 下午3:10:53
	 */
	@RequestMapping(value = "checkRealWaitOut")
	@ResponseBody
	public Object checkRealWaitOut(HttpServletRequest request, HttpServletResponse response,int goodsId) throws OAException {
		OaMsg msg = graftingCargoService.checkRealWaitOut(goodsId);
		return msg;
	}
	
	/**
	 * 添加嫁接历史
	 * @author yanyufeng
	 * @param request
	 * @param response
	 * @param graftingHistory
	 * @return
	 * @throws OAException
	 * 2015-10-28 上午9:45:07
	 */
	@RequestMapping(value = "addHistory")
	@ResponseBody
	public Object addHistory(HttpServletRequest request, HttpServletResponse response,GraftingHistory graftingHistory,int inCargoId,int outCargoId) throws OAException {
		
//		GoodsDto gDto=new GoodsDto();
//		gDto.setId(graftingHistory.getInGoodsId());
		
	    OaMsg inMsg=ladingService.getCargoTree(inCargoId+"");
	    graftingHistory.setInLZ(JSON.toJSONString(inMsg.getData()));
	    
//	    GoodsDto oDto=new GoodsDto();
//	    oDto.setId(graftingHistory.getOutGoodsId());
		
	    OaMsg outMsg=ladingService.getCargoTree(outCargoId+"");
		graftingHistory.setOutLZ(JSON.toJSONString(outMsg.getData()));
		
	    
		OaMsg msg = graftingCargoService.addHistory(graftingHistory);
		
		
		
		
		return msg;
	}
	
	
	@RequestMapping(value = "getHistory")
	@ResponseBody
	public Object getHistory(HttpServletRequest request, HttpServletResponse response,GraftingHistory graftingHistory,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException {
		OaMsg msg = graftingCargoService.getHistory(graftingHistory,new PageView(pagesize, page));
		return msg;
	}

//	@RequestMapping(value = "getHistoryLZ")
//	@ResponseBody
//	public Object getHistoryLZ(HttpServletRequest request, HttpServletResponse response,GraftingHistory graftingHistory,@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
//			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException {
//		OaMsg msg = graftingCargoService.getHistory(graftingHistory,new PageView(pagesize, page));
//		return msg;
//	}

}
