package com.skycloud.oa.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.google.gson.Gson;
import com.skycloud.oa.annatation.Log;
import com.skycloud.oa.annatation.Translation;
import com.skycloud.oa.auth.model.User;
import com.skycloud.oa.system.dao.OperateLogDao;
import com.skycloud.oa.system.model.OperateLog;

/**
 * 系统操作日志
 * 
 * @ClassName: CloudLogAspect
 * @Description: TODO
 * @author xie
 * @date 2015年2月8日 上午3:09:08
 */
@Component
@Aspect
public class CloudLogAspect {

	private static Logger LOG = Logger.getLogger(CloudLogAspect.class);

	@Resource
	private OperateLogDao operateLogDao;

	@AfterReturning("execution(* com.skycloud.oa.*.service.*.*(..))")
	// @Before("execution(* com.skycloud.oa.*.service.*.*(..))")
	public void doLog(JoinPoint jp) {
		try {
			Method method = null;
			for (Method m : jp.getTarget().getClass().getMethods()) {
				if (m.getName().equals(jp.getSignature().getName())) {
					method = m;
				}
			}
			if (method != null && method.isAnnotationPresent(Log.class)) {// 添加了注解的记录日志
				OperateLog olog = new OperateLog();

				Subject currentUser = SecurityUtils.getSubject();

				Log log = method.getAnnotation(Log.class);
				olog.setIp(currentUser.getSession().getHost());
				olog.setTime(System.currentTimeMillis() / 1000);
				try {
					User user = (User) currentUser.getPrincipals().getPrimaryPrincipal();
					olog.setUser(user);
				} catch (Exception e) {

				}
				olog.setType(log.type());
				olog.setObject(log.object());
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				
				Class<?>[] cList=method.getParameterTypes();
//				Annotation[][] an= method.getParameterAnnotations();
//				System.out.println("1111"+an[0][0]);
				
				Map<String, String[]> params = request.getParameterMap();
				StringBuffer content = new StringBuffer();
				for (String key : params.keySet()) {
					String mKey=key;
					for(Class clas:cList){
						try {
							if(null!=clas.getDeclaredField(key)){
								//判断是否有注解，有的话就解析
								if(clas.getDeclaredField(key).isAnnotationPresent(Translation.class)){
									Translation trans=clas.getDeclaredField(key).getAnnotation(Translation.class);
									mKey=trans.name();
								}
							}
						} catch (NoSuchFieldException e) {
							// TODO: handle exception
						}
						
					}
					content.append(mKey + ":" + new Gson().toJson(params.get(key)));
				}
				olog.setContent(content.toString());
				olog.setSystem("BMS");
				operateLogDao.create(olog);
			}
		} catch (Exception e) {
			LOG.error("日志记录失败", e);
		}
	}

}
