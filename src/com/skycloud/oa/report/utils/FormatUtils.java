/**
 * 
 * @Project:sop
 * @Title:FormatUtils.java
 * @Package:com.skycloud.oa.report.utils
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月11日 下午2:42:29
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.utils;

import java.math.BigDecimal;

/**
 * <p>判断字段</p>
 * @ClassName:FormatUtils
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年11月11日 下午2:42:29
 * 
 */
public class FormatUtils 
{
	/**
	 * 判断对象是否为空
	 * @Title:FormatUtils
	 * @Description:
	 * @param object
	 * @return
	 * @Date:2015年11月11日 下午2:43:26
	 * @return:String 
	 * @throws
	 */
	public static String getStringValue(Object object)
	{
		if(object==null || "".equals(object.toString()) ||object.toString().equals("null"))
		{
			return "";
		}
		else
		{
			return object.toString();
		}
	}
	
	/**
	 * 将对象转换成Double
	 * @Title:FormatUtils
	 * @Description:
	 * @param object
	 * @return
	 * @Date:2015年11月11日 下午2:49:28
	 * @return:Double 
	 * @throws
	 */
	public static Double getDoubleValue(Object object)
	{
		String str=getStringValue(object);
		try
		{
			return Double.parseDouble(str);
		}
		catch(NumberFormatException ex)
		{
		  return 0d;
		}
	}
	
	/**
	 * 保留三位小数
	 * @Title:FormatUtils
	 * @Description:
	 * @param num
	 * @return
	 * @Date:2015年11月26日 上午10:07:30
	 * @return: double 
	 * @throws
	 */
	public static double formatNum(double num)
	{
		BigDecimal bg = new BigDecimal(num);  
        double format = bg.setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue(); 
        
        return format;
	}
	
	/**
	 * 数值相加
	 * @Title:FormatUtils
	 * @Description:
	 * @param v1
	 * @param v2
	 * @return
	 * @Date:2015年11月17日 下午7:28:22
	 * @return:Double 
	 * @throws
	 */
	public static Double add(Double v1, Double v2) 
	{
		 BigDecimal b1 = new BigDecimal(v1.toString());
		 BigDecimal b2 = new BigDecimal(v2.toString());
		 return new Double(b1.add(b2).doubleValue());
	}
}
