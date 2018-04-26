package com.skycloud.oa.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware
{
	private static ApplicationContext applicationContext; // Spring上下文

	/**
	 * 实现ApplicationContextAware接口的回掉方法，设置上下文环境
	 * 
	 * @param applicationContext
	 * @throws BeansException
	 */
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	/**
	 * 获取对象
	 * 
	 * @param name
	 * @return Object 一个以所给名字注册的bean的实例
	 * @throws BeansException
	 */
	public static Object getBean(String name) throws BeansException
	{
		return applicationContext.getBean(name);
	}

	/**
	 * 获取类型为requiredType的对象
	 * 如果bean的类型不能被转换，抛出BeanNotOfRequiredTypeException异常
	 * 
	 * @param name
	 *            bean实例名
	 * @param requiredType 返回对象类型
	 * @return Object 返回requiredType类型对象
	 * @throws BeansException
	 */
	public static Object getBean(String name, Class<?> requiredType) throws BeansException
	{
		return applicationContext.getBean(name, requiredType);
	}

	/**
	 * 如果BeanFactory包含所给名称的bean，返回true
	 * 
	 * @param name
	 * @return boolean
	 */
	public static boolean containsBean(String name)
	{
		return applicationContext.containsBean(name);
	}

	/**
	 * 判断给定名字的bean是singleton还是prototype
	 *如果为找到，抛出NoSuchBeanDefinitionException异常
	 * 
	 * @param name
	 * @return boolean
	 * @throws NoSuchBeanDefinitionException
	 */
	public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException
	{
		return applicationContext.isSingleton(name);
	}

	/**
	 * @param name
	 * @return Class 注册对象类型
	 * @throws NoSuchBeanDefinitionException
	 */
	public static Class<?> getType(String name) throws NoSuchBeanDefinitionException
	{
		return applicationContext.getType(name);
	}

	/**
	 * 如果给定的bean的名字在bean定义有别名，返回别名
	 * 
	 * @param name
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	public static String[] getAliases(String name) throws NoSuchBeanDefinitionException
	{
		return applicationContext.getAliases(name);
	}
}
