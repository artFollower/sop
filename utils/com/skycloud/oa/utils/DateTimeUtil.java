package com.skycloud.oa.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 对日期进行格式化处理的类，由于java的SimpleDateFormat对null不进行处理，所以对其进行封装 使用静态函数
 * 
 */
public class DateTimeUtil {

	// 缺省的格式
	public static String shortDateFormatText = "yyyy-MM-dd";
	public static String simplateDateFormatText = "yyyyMMddHHmm";
	private static String longDateFormatText = "yyyy年MM月dd日";
	public static String shortDateTimeFormatText = "yyyy-MM-dd HH:mm:ss";
	private static String longDateTimeFormatText = "yyyy年MM月dd日 HH时mm分ss秒";
	private static String shortTimeToMinuteFormatText = "HH:mm";
	private static String shortTimeToSecondFormatText = "HH:mm:ss";
	private static String longTimeToMinuteFormatText = "HH时mm分";
	private static String longTimeToSecondFormatText = "HH时mm分ss秒";
	private static String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	public static String getShortDateFormatText() {
		return shortDateFormatText;
	}
	public static String getLongDateFormatText() {
		return longDateFormatText;
	}

	public static String getShortDateTimeFormatText() {
		return shortDateTimeFormatText;
	}
	public static String getLongDateTimeFormatText() {
		return longDateTimeFormatText;
	}

	public static String getShortTimeToMinuteFormatText() {
		return shortTimeToMinuteFormatText;
	}
	public static String getShortTimeToSecondFormatText() {
		return shortTimeToSecondFormatText;
	}

	public static String getLongTimeToMinuteFormatText() {
		return longTimeToMinuteFormatText;
	}
	public static String getLongTimeToSecondFormatText() {
		return longTimeToSecondFormatText;
	}

	private static SimpleDateFormat getShortDateSDF() {
		return new SimpleDateFormat(shortDateFormatText);
	}
	private static SimpleDateFormat getLongDateSDF() {
		return new SimpleDateFormat(longDateFormatText);
	}

	private static SimpleDateFormat getShortDateTimeSDF() {
		return new SimpleDateFormat(shortDateTimeFormatText);
	}
	private static SimpleDateFormat getLongDateTimeSDF() {
		return new SimpleDateFormat(longDateTimeFormatText);
	}

	private static SimpleDateFormat getShortTimeToMinuteSDF() {
		return new SimpleDateFormat(shortTimeToMinuteFormatText);
	}
	private static SimpleDateFormat getShortTimeToSecondSDF() {
		return new SimpleDateFormat(shortTimeToSecondFormatText);
	}

	private static SimpleDateFormat getLongTimeToMinuteSDF() {
		return new SimpleDateFormat(longTimeToMinuteFormatText);
	}
	private static SimpleDateFormat getLongTimeToSecondSDF() {
		return new SimpleDateFormat(longTimeToSecondFormatText);
	}

	private static SimpleDateFormat getSDF(String formatText) {
		return new SimpleDateFormat(formatText);
	}
	public static Date getShortDate(String dateString) {
		if (dateString == null || dateString.length() == 0) {
			return null;
		} else {
			try {
				return getShortDateSDF().parse(dateString);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public static Date getLongDate(String dateString) {

		if (dateString == null || dateString.length() == 0) {
			return null;
		} else {
			try {
				return getLongDateSDF().parse(dateString);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public static Date getShortDateTime(String dateString) {

		if (dateString == null || dateString.length() == 0) {
			return null;
		} else {
			try {
				return getShortDateTimeSDF().parse(dateString);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public static Date getLongDateTime(String dateString) {

		if (dateString == null || dateString.length() == 0) {
			return null;
		} else {
			try {
				return getLongDateTimeSDF().parse(dateString);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public static Date getShortTimeToMinute(String dateString) {

		if (dateString == null || dateString.length() == 0) {
			return null;
		} else {
			try {
				return getShortTimeToMinuteSDF().parse(dateString);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public static Date getShortTimeToSecond(String dateString) {

		if (dateString == null || dateString.length() == 0) {
			return null;
		} else {
			try {
				return getShortTimeToSecondSDF().parse(dateString);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public static Date getLongTimeToMinute(String dateString) {

		if (dateString == null || dateString.length() == 0) {
			return null;
		} else {
			try {
				return getLongTimeToMinuteSDF().parse(dateString);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public static Date getLongTimeToSecond(String dateString) {

		if (dateString == null || dateString.length() == 0) {
			return null;
		} else {
			try {
				return getLongTimeToSecondSDF().parse(dateString);
			} catch (ParseException e) {
				return null;
			}
		}
	}

	public static Date getDate(String dateString, String formatText) {

		if (dateString == null || dateString.length() == 0) {
			return null;
		} else {
			try {
				return getSDF(formatText).parse(dateString);
			} catch (ParseException e) {
				return null;
			}
		}
	}
	
	public static String dateExchange(String dateString, String formatText) {

		if (dateString == null || dateString.length() == 0) {
			return null;
		} else {
			try {
				return getSDF(formatText).format(getSDF(formatText).parse(dateString));
			} catch (ParseException e) {
				return dateString;
			}
		}
	}

	public static String getLongDateString(Date date) {

		if (date == null) {
			return "";
		} else {
			return getLongDateSDF().format(date);
		}
	}

	public static String getShortDateString(Date date) {

		if (date == null) {
			return "";
		} else {
			return getShortDateSDF().format(date);
		}
	}

	public static String getShortDateTimeString(Date date) {

		if (date == null) {
			return "";
		} else {
			return getShortDateTimeSDF().format(date);
		}
	}

	public static String getLongDateTimeString(Date date) {

		if (date == null) {
			return "";
		} else {
			return getLongDateTimeSDF().format(date);
		}
	}

	public static String getShortTimeToMinuteString(Date date) {

		if (date == null) {
			return "";
		} else {
			return getShortTimeToMinuteSDF().format(date);
		}
	}

	public static String getShortTimeToSecondString(Date date) {

		if (date == null) {
			return "";
		} else {
			return getShortTimeToSecondSDF().format(date);
		}
	}

	public static String getLongTimeToMinuteString(Date date) {

		if (date == null) {
			return "";
		} else {
			return getLongTimeToMinuteSDF().format(date);
		}
	}

	public static String getLongTimeToSecondString(Date date) {

		if (date == null) {
			return "";
		} else {
			return getLongTimeToSecondSDF().format(date);
		}
	}

	public static String getString(Date date, String formatText) {

		if (date == null) {
			return "";
		} else {
			return getSDF(formatText).format(date);
		}
	}

	public static String getWeekdayString(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		return dayNames[day - 1];
	}

	public static boolean DatePartEquals(Calendar calendar1, Calendar calendar2) {

		return (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) && calendar1.get(Calendar.DATE) == calendar2.get(Calendar.DATE));
	}
	
	public static boolean dateTimeEquals(String time1,String time2) {
		DateFormat df1 = new SimpleDateFormat(shortDateTimeFormatText);
		DateFormat df2 = new SimpleDateFormat(shortDateTimeFormatText);
		boolean result = true;
		try {
			Date d1 = df1.parse(time1);
			Date d2 = df1.parse(time2);
			
			 long diff = d1.getTime() - d2.getTime();
			 if(diff < 0){
				 result = false;
			 }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static String timeStampToString(Timestamp dateTime){
		String time = "";
		SimpleDateFormat df = new SimpleDateFormat(shortDateTimeFormatText);
		time = df.format(dateTime);
		return time;
	}
	
	public static String dateTimeToShortDate(String dateTime){
		return getShortDateString(DateTimeUtil.getShortDateTime(dateTime));
	}
	
	public static void main(String[] args) {
		System.out.println(DateTimeUtil.getShortDateTime("1987-09-27 17:07:24"));
	}
}
