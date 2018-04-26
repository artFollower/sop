package com.skycloud.oa.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.skycloud.oa.utils.HibernateUtils;

public class TranscationFilter implements Filter {

	protected Log log = LogFactory.getLog(TranscationFilter.class);

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		try {
			chain.doFilter(request, response);
			Session session = HibernateUtils.getCurrentSession();
			if (session != null) {
				session.getTransaction().commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Session session = HibernateUtils.getCurrentSession();
			if (session != null) {
				try {
					session.getTransaction().rollback();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				}
				log.error(e);
			}
			
//			OaMsg msg = new OaMsg();
//			if(e instanceof UnauthorizedException) {
//				msg.setCode(Constant.SYS_CODE_UNAUTHORIZED);
//				msg.setMsg("无权操作");
//			}else if(e instanceof OAException){
//				OAException _e = (OAException) e;
//				msg.setCode(_e.getCode());
//				msg.setMsg(_e.getDesc());
//			}else {
//				msg.setCode(Constant.SYS_CODE_SYS_ERR);
//				msg.setMsg("系统错误,请联系管理员");
//			}
//			response.getWriter().write(new Gson().toJson(msg));
			

			/**
			 * 反馈信息到前端个去
			 */
//			OaMsg msg = new OaMsg();
//			msg.setCode(Constant.SYS_CODE_DB_ERR);
//			msg.setMsg(e.getMessage());
//			Gson json = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm:ss").disableHtmlEscaping().create();
//			String callback = request.getParameter("callback");
//			if (callback != null && !"".equals(callback)) {
//				response.getWriter().write(callback + "(" + json.toJson(msg) + ")");
//			} else {
//				response.getWriter().write(json.toJson(msg));
//			}
			/*
			 * if(e instanceof NestedServletException){ NestedServletException
			 * ex = (NestedServletException)e; UnChkException ex1 =
			 * (UnChkException) ex.getCause(); request.setAttribute("message",
			 * ex1.getMessage()); request.getRequestDispatcher(
			 * "/WEB-INF/views/result/UnChkException.jsp").forward(request,
			 * response); }else if(e instanceof IOException || e instanceof
			 * ServletException){ request.setAttribute("message",
			 * e.getMessage()); request.getRequestDispatcher(
			 * "/WEB-INF/views/result/UnChkException.jsp").forward(request,
			 * response); }
			 */
			// 异常处理
		} finally {
			Session session = HibernateUtils.getCurrentSession();
			if (session != null) {
				HibernateUtils.closeCurrentSession();
			}
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}
	
}