package com.skycloud.oa.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.utils.Common;
import com.skycloud.oa.utils.OaMsg;

public class ExceptionHandler implements HandlerExceptionResolver {
	
	private static Logger LOG = Logger.getLogger(ExceptionHandler.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		boolean isAjax = Common.isAjax(request);
		OaMsg msg = new OaMsg();
		LOG.error("异常拦截",ex);
		if(ex instanceof UnauthorizedException) {
			msg.setCode(Constant.SYS_CODE_UNAUTHORIZED);
			msg.setMsg("无权操作");
		}else if(ex instanceof OAException){
			OAException e = (OAException) ex;
			msg.setCode(e.getCode());
			msg.setMsg(e.getDesc());
		}else if(ex instanceof BeanInstantiationException){
			msg.setCode(Constant.SYS_CODE_PARAM_ERROR);
			msg.setMsg("参数解析错误");
		}else {
			msg.setCode(Constant.SYS_CODE_SYS_ERR);
			msg.setMsg("系统错误,请联系管理员");
		}
		if(isAjax) {
			try {
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
				response.getWriter().write(new Gson().toJson(msg));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				
			}
		}else {
			return new ModelAndView("error/error").addObject(msg);
		}
		return null;
	}

}
