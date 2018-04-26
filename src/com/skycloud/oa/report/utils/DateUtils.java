/**
 * 
 * @Project:sop
 * @Title:DateUtils.java
 * @Package:com.skycloud.oa.report.utils
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月27日 下午8:18:43
 * @Version:SkyCloud版权所有
 */
package com.skycloud.oa.report.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * <p>日期组件</p>
 * @ClassName:DateUtils
 * @Description:
 * @Author:YouHaiDong
 * @Date:2015年10月27日 下午8:18:43
 * 
 */
public class DateUtils 
{
	/**
	 * 获取某月的第一天日期时间
	 * @Title:DateUtils
	 * @Description:
	 * @param date
	 * @return
	 * @Date:2015年10月27日 下午8:19:58
	 * @return:String 
	 * @throws
	 */
	public static String getFirstDayOfMonth(String date)
	{
		String month = "";
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		month = date+"-01";
		
		return month;
	}
	
	/**
	 * 获取某月最后一天日期时间
	 * @Title:DateUtils
	 * @Description:
	 * @param date
	 * @return
	 * @Date:2015年10月27日 下午8:23:58
	 * @return:String 
	 * @throws
	 */
	public static String getLastDayOfMonth(String date)
	{
		Calendar cal = Calendar.getInstance();
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		 cal.set(Calendar.DATE, 1);
		//获取某月最后一天
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
        cal.set(Calendar.DATE, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
	}
	
	/**
	 * 获取某月的上个月的最后一天
	 * @Title:DateUtils
	 * @Description:
	 * @param date
	 * @return
	 * @Date:2015年12月1日 下午6:33:01
	 * @return:String 
	 * @throws
	 */
	public static String getLastDayOfLastMonth(String date)
	{
		Calendar cal = Calendar.getInstance();
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-2);
		cal.set(Calendar.DATE, 1);
		//获取某月最后一天
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
        cal.set(Calendar.DATE, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
	}
	
	/**
	 * 获取某年第一天日期时间
	 * @Title:DateUtils
	 * @Description:
	 * @param date
	 * @return
	 * @Date:2015年10月30日 上午11:06:11
	 * @return:String 
	 * @throws
	 */
	public static String getFirstDayOfYear(String date)
	{
		String year = "";
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		year = date+"-01-01";
		
		return year;
	}
	/**
	 * 获取某年第一天日期时间
	 * @Title:DateUtils
	 * @Description:
	 * @param date
	 * @return
	 * @Date:2015年10月30日 上午11:06:11
	 * @return:String 
	 * @throws
	 */
	public static Long getFirstDayOfYear(int year)
	{ Calendar cal = Calendar.getInstance();
		cal.set(year, 0, 1, 0, 0,0);
		
		return cal.getTimeInMillis()/1000;
	}
	/**
	 * 获取某年最后一天的日期时间
	 * @Title:DateUtils
	 * @Description:
	 * @param date
	 * @return
	 * @Date:2015年10月30日 上午11:07:54
	 * @return:String 
	 * @throws
	 */
	public static String getLastDayOfYear(String date)
	{
		String year = "";
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		year = date+"-12-31 23:59:59";
		
		return year;
	}
	
	/**
	 * 
	 * @Title:DateUtils
	 * @Description:
	 * @param date
	 * @return
	 * @Date:2015年11月18日 上午9:26:28
	 * @return:String 
	 * @throws
	 */
	public static String getFirstDay(String date)
	{
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		String year = date.substring(0, 4);
		String thisDate = year+"-01-01";
		
		return thisDate;
	}
	
	/**
	 * 获取去年的第一天
	 * @Title:DateUtils
	 * @Description:
	 * @param date
	 * @return
	 * @Date:2015年11月26日 上午10:59:31
	 * @return:String 
	 * @throws
	 */
	public static String getFirstDayOfLastYear(String date)
	{
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		String year = date.substring(0, 4);
		String lastDate = (Integer.parseInt(year)-1)+"-01-01 00:00:00";
		
		return lastDate;
	}
	
	/**
	 * 获取去年同期月末日期
	 * @Title:DateUtils
	 * @Description:
	 * @param date
	 * @return
	 * @Date:2015年11月26日 上午11:01:29
	 * @return:String 
	 * @throws
	 */
	public static String getThisDayOfLastYear(String date)
	{
		Calendar cal = Calendar.getInstance();
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		
		//设置年份
		cal.set(Calendar.YEAR,year-1);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DATE, 1);
		//获取某月最后一天
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最大天数
        cal.set(Calendar.DATE, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        lastDayOfMonth = lastDayOfMonth+" 23:59:59";
		
		return lastDayOfMonth;
	}
	
	/**
	 * 获取去年同期月初日期
	 * @Title:DateUtils
	 * @Description:
	 * @param date
	 * @return
	 * @Date:2015年11月26日 上午11:05:39
	 * @return:String 
	 * @throws
	 */
	public static String getThisFirstDayOfLastYear(String date)
	{
		Calendar cal = Calendar.getInstance();
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		
		//设置年份
		cal.set(Calendar.YEAR,year-1);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//设置日历中月份的最大天数
        cal.set(Calendar.DATE, 1);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        firstDayOfMonth = firstDayOfMonth+" 00:00:00";
		
		return firstDayOfMonth;
	}
	
	public static String getLastYearTime(String time){
		if(time == null || "".equals(time))
		{
			return "";
		}
		
		int year = Integer.parseInt(time.substring(0, 4));
		time=(year-1)+time.substring(4, time.length());
		
		return time;
	}
	
	/**
	 * 获取月份存在多少小时数
	 * @param time
	 * @return
	 */
	public static int getHoursOfMonth(String time){
		Calendar cal = Calendar.getInstance();
		if(time == null || "".equals(time))
		{
			return 1;
		}
		
		int year = Integer.parseInt(time.substring(0, 4));
		int month = Integer.parseInt(time.substring(5, 7));
		//设置年份
				cal.set(Calendar.YEAR,year);
				//设置月份
				cal.set(Calendar.MONTH, month-1);
				cal.set(Calendar.DATE, 1);
		int dates=cal.getActualMaximum(Calendar.DATE);
		return (dates*24);
	}
	/**
	 * 获取月份存在多少小时数
	 * @param time
	 * @return
	 */
	public static int getHoursOfMonth(String startTime,String endTime){
		Calendar startCal = Calendar.getInstance();
		Calendar endCal = Calendar.getInstance();
		if(startTime == null || "".equals(startTime))
		{
			return 1;
		}
		
		int startYear = Integer.parseInt(startTime.substring(0, 4));
		int startMonth = Integer.parseInt(startTime.substring(5, 7))-1;
		int endYear = Integer.parseInt(endTime.substring(0, 4));
		int endMonth = Integer.parseInt(endTime.substring(5, 7))-1;
		//设置年份
		startCal.set(Calendar.YEAR,startYear);
				//设置月份
		startCal.set(Calendar.MONTH, startMonth);
		startCal.set(Calendar.DATE, 1);
		//设置年份
		endCal.set(Calendar.YEAR,endYear);
				//设置月份
		endCal.set(Calendar.MONTH, endMonth);
		endCal.set(Calendar.DATE, 1);
		
		endCal.set(Calendar.DATE, endCal.getActualMaximum(Calendar.DAY_OF_MONTH));
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Long dates= (endCal.getTimeInMillis()-startCal.getTimeInMillis())/3600000;
		return (int) (dates+24);
	}
	
	
	public static int getHoursOfYear(String time) {
		Calendar cal = Calendar.getInstance();
		if(time == null || "".equals(time))
		{
			return 1;
		}
		
		int year = Integer.parseInt(time.substring(0, 4));
		int month =2;
		//设置年份
				cal.set(Calendar.YEAR,year);
				//设置月份
				cal.set(Calendar.MONTH, month-1);
		return cal.getActualMaximum(Calendar.DATE)==29?(366*24):(365*24);
	}
	public static String getFirstDayOfLastMonth(String date) {
		Calendar cal = Calendar.getInstance();
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-2);
		cal.set(Calendar.DATE,1);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
		return firstDayOfMonth;
	}
	public static Long getFirstDayOfLastMonth(Long date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date*1000);
		//设置月份
		if(cal.get(Calendar.MONTH)==0){
			return 2L;
		}else{
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
		cal.set(Calendar.DATE,1);
        //格式化日期
       return cal.getTimeInMillis()/1000;
		}
	}
	public static Long getLastDayOfLastMonth(Long date) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date*1000);
		//设置月份
		if(cal.get(Calendar.MONTH)==0){
			return 1L;
		}else{
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
        //格式化日期
       return cal.getTimeInMillis()/1000;
		}
	}
	public static String getThisTimeOfLastYear(String date) {
		Calendar cal = Calendar.getInstance();
		if(date == null || "".equals(date))
		{
			return "";
		}
		
		int year = Integer.parseInt(date.substring(0, 4));
		int month = Integer.parseInt(date.substring(5, 7));
		int day=Integer.parseInt(date.substring(8, 10));
		//设置年份
		cal.set(Calendar.YEAR,year-1);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		if(month==2&&day==29){
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		}else{
			 cal.set(Calendar.DAY_OF_MONTH, day);
		}
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
	}
	public static Long getLongTime(String time){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			return sdf.parse(time).getTime()/1000;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0L;
	}
	public static String getStringTime(Long timeL){
		return new Timestamp(timeL*1000).toString().substring(0, 19);
	}
	/**
	 * @Title:DateUtils
	 * @Description:
	 * @param args
	 * @Date:2015年10月27日 下午8:18:43
	 * @return :void 
	 * @throws
	 */
	public static void main(String[] args) 
	{
		System.out.println(getThisDayOfLastYear("2017-02"));
	}


	


}
