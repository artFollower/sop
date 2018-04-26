package com.skycloud.oa.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.google.gson.Gson;
import com.skycloud.oa.config.Constant;
import com.skycloud.oa.utils.OaMsg;

public class FormAuthFilter extends FormAuthenticationFilter {

	private static Logger LOG = Logger.getLogger(FormAuthFilter.class);

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		if (isLoginRequest(request, response)) {
			return true;
		} else {
			if (LOG.isTraceEnabled()) {
				LOG.trace("Attempting to access a path which requires authentication.  Forwarding to the " + "Authentication url [" + getLoginUrl() + "]");
			}
			if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"))) {// 不是ajax请求
				saveRequestAndRedirectToLogin(request, response);
			} else {
				OaMsg msg = new OaMsg();
				msg.setCode(Constant.SYS_CODE_UNLOGIN);
				response.setCharacterEncoding("UTF-8");
				response.setContentType("application/json; charset=utf-8");
				response.getWriter().write(new Gson().toJson(msg));
			}
			return false;

		}

	}

}
