package com.skycloud.oa.aspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.springframework.aop.ThrowsAdvice;

/**
 * 
 * <功能描述>
 * 
 * @author xiewei
 * @date 2013-9-22 email:xiewei@sh-skycloud.com
 */
public class ExceptionAspect implements ThrowsAdvice {
	
	private static Logger log = Logger.getLogger(ExceptionAspect.class);

	public void doLog(JoinPoint jp, Exception ex) {
		String packageName = null;
		String method = null;
		if (null != jp) {
			packageName = jp.getTarget().getClass().getName();
			method = jp.getSignature().getName();
			log.error("call " + packageName + "." + method+ " throw exception:");
			log.info(ex);
			ex.printStackTrace();
		}
	}
}
