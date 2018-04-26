package com.skycloud.oa.annatation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 日志注解
 * @ClassName: Log 
 * @Description: TODO
 * @author xie
 * @date 2015年2月8日 上午3:08:27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
	String object();//对象
	String type();//操作
}
