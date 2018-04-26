package com.skycloud.oa.order.controller;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.config.C;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.exception.OAException;
import com.skycloud.oa.order.dto.IntentionDto;
import com.skycloud.oa.order.model.Intention;
import com.skycloud.oa.order.service.IntentionService;
import com.skycloud.oa.orm.PageView;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

/**
 * 订单意向控制类
 * 
 * @ClassName: OrderIntentController
 * @Description: TODO
 * @author xie
 * @date 2014年11月29日 下午6:50:16
 */
@Controller
@RequestMapping("/orderInstent")
public class OrderIntentController {

	@Autowired
	private IntentionService intentionService;


	/**
	 * 查询所有订单意向
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param intentionDto
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response,@ModelAttribute IntentionDto intentionDto,
			@RequestParam(defaultValue = "20", required = false, value = "pagesize") int pagesize,
			@RequestParam(defaultValue = "0", required = false, value = "page") int page) throws OAException {
		OaMsg msg = intentionService.getIntentionListByCondition(intentionDto, new PageView(pagesize, page));
		return msg;
	}

	/**
	 * 更新
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param intention
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Intention intention,String tReviewTime,String mEditTime) throws OAException {
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		intention.setEditUserId(user.getId());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!Common.empty(tReviewTime)){
			long mReviewTime;
			try {
				mReviewTime = format.parse(tReviewTime).getTime() / 1000;
				intention.setReviewTime(mReviewTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(intention.getStatus()==2){
			intention.setReviewUserId(user.getId());
		}
		return intentionService.updateIntention(intention,mEditTime);
	}
	
	/**
	 * 更新状态
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param intention
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "updateStatus")
	@ResponseBody
	public Object updateStatus(HttpServletRequest request, HttpServletResponse response,int id, int status,String reviewTime) throws OAException {
		Intention intention=new Intention();
		intention.setId(id);
		intention.setStatus(status);
		if(status==2){
			User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			intention.setReviewUserId(user.getId());
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(!Common.empty(reviewTime)){
			long mReviewTime;
			intention.setReviewTime(new Date().getTime()/1000);
		}
		return intentionService.updateIntention(intention,"");
	}
	
	

	/**
	 * 保存
	 * 
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param intention
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response,Intention intention) throws OAException {
		User user = (User) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
		intention.setCreateUserId(user.getId());
		intention.setCreateTime(new Timestamp(new Date().getTime()));
		return intentionService.addIntention(intention);
	}
	
	/**
	 * 删除意向
	 * @Description: TODO
	 * @param request
	 * @param response
	 * @param intentionIds
	 * @return
	 * @throws OAException
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public Object delete(HttpServletRequest request, HttpServletResponse response, String intentionIds) throws OAException {
		return intentionService.deleteIntention(intentionIds);
	}
	
	
	
}
