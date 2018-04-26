package com.skycloud.oa.utils;

/**
 * 公共方法
 */
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.skycloud.oa.config.Global;

public class Common {
	private static final String randChars = "0123456789abcdefghigklmnopqrstuvtxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
	private static Random random = new Random();
	private static final char[] pregChars = { '.', '\\', '+', '*', '?', '[', '^', ']', '$', '(', ')', '{', '}', '=', '!', '<', '>', '|', ':' };
	private static MessageSource mr = null;

	// 区域ID
	private static Map<String, String[]> timeZoneIDs = new LinkedHashMap<String, String[]>(32);
	static {
		timeZoneIDs.put("-12", new String[] { "GMT-12:00", "(GMT -12:00) Eniwetok, Kwajalein" });
		timeZoneIDs.put("-11", new String[] { "GMT-11:00", "(GMT -11:00) Midway Island, Samoa" });
		timeZoneIDs.put("-10", new String[] { "GMT-10:00", "(GMT -10:00) Hawaii" });
		timeZoneIDs.put("-9", new String[] { "GMT-09:00", "(GMT -09:00) Alaska" });
		timeZoneIDs.put("-8", new String[] { "GMT-08:00", "(GMT -08:00) Pacific Time (US &amp; Canada), Tijuana" });
		timeZoneIDs.put("-7", new String[] { "GMT-07:00", "(GMT -07:00) Mountain Time (US &amp; Canada), Arizona" });
		timeZoneIDs.put("-6", new String[] { "GMT-06:00", "(GMT -06:00) Central Time (US &amp; Canada), Mexico City" });
		timeZoneIDs.put("-5", new String[] { "GMT-05:00", "(GMT -05:00) Eastern Time (US &amp; Canada), Bogota, Lima, Quito" });
		timeZoneIDs.put("-4", new String[] { "GMT-04:00", "(GMT -04:00) Atlantic Time (Canada), Caracas, La Paz" });
		timeZoneIDs.put("-3.5", new String[] { "GMT-03:30", "(GMT -03:30) Newfoundland" });
		timeZoneIDs.put("-3", new String[] { "GMT-03:00", "(GMT -03:00) Brassila, Buenos Aires, Georgetown, Falkland Is" });
		timeZoneIDs.put("-2", new String[] { "GMT-02:00", "(GMT -02:00) Mid-Atlantic, Ascension Is., St. Helena" });
		timeZoneIDs.put("-1", new String[] { "GMT-01:00", "(GMT -01:00) Azores, Cape Verde Islands" });
		timeZoneIDs.put("0", new String[] { "GMT", "(GMT) Casablanca, Dublin, Edinburgh, London, Lisbon, Monrovia" });
		timeZoneIDs.put("1", new String[] { "GMT+01:00", "(GMT +01:00) Amsterdam, Berlin, Brussels, Madrid, Paris, Rome" });
		timeZoneIDs.put("2", new String[] { "GMT+02:00", "(GMT +02:00) Cairo, Helsinki, Kaliningrad, South Africa" });
		timeZoneIDs.put("3", new String[] { "GMT+03:00", "(GMT +03:00) Baghdad, Riyadh, Moscow, Nairobi" });
		timeZoneIDs.put("3.5", new String[] { "GMT+03:30", "(GMT +03:30) Tehran" });
		timeZoneIDs.put("4", new String[] { "GMT+04:00", "(GMT +04:00) Abu Dhabi, Baku, Muscat, Tbilisi" });
		timeZoneIDs.put("4.5", new String[] { "GMT+04:30", "(GMT +04:30) Kabul" });
		timeZoneIDs.put("5", new String[] { "GMT+05:00", "(GMT +05:00) Ekaterinburg, Islamabad, Karachi, Tashkent" });
		timeZoneIDs.put("5.5", new String[] { "GMT+05:30", "(GMT +05:30) Bombay, Calcutta, Madras, New Delhi" });
		timeZoneIDs.put("5.75", new String[] { "GMT+05:45", "(GMT +05:45) Katmandu" });
		timeZoneIDs.put("6", new String[] { "GMT+06:00", "(GMT +06:00) Almaty, Colombo, Dhaka, Novosibirsk" });
		timeZoneIDs.put("6.5", new String[] { "GMT+06:30", "(GMT +06:30) Rangoon" });
		timeZoneIDs.put("7", new String[] { "GMT+07:00", "(GMT +07:00) Bangkok, Hanoi, Jakarta" });
		timeZoneIDs.put("8", new String[] { "GMT+08:00", "(GMT +08:00) Beijing, Hong Kong, Perth, Singapore, Taipei" });
		timeZoneIDs.put("9", new String[] { "GMT+09:00", "(GMT +09:00) Osaka, Sapporo, Seoul, Tokyo, Yakutsk" });
		timeZoneIDs.put("9.5", new String[] { "GMT+09:30", "(GMT +09:30) Adelaide, Darwin" });
		timeZoneIDs.put("10", new String[] { "GMT+10:00", "(GMT +10:00) Canberra, Guam, Melbourne, Sydney, Vladivostok" });
		timeZoneIDs.put("11", new String[] { "GMT+11:00", "(GMT +11:00) Magadan, New Caledonia, Solomon Islands" });
		timeZoneIDs.put("12", new String[] { "GMT+12:00", "(GMT +12:00) Auckland, Wellington, Fiji, Marshall Island" });
	}

	
	
	
	
	private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274,  
        2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,  
        4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };  
private final static String[] lc_FirstLetter = { "A", "B", "C", "D", "E",  
        "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",  
        "T", "W", "X", "Y", "Z" };  


private final static String[] lc_nanLetter={"鋆","琦","重",
	"鑫" , "沐", "斐" , "莞",   "怡"  ,  "圳", "酯", "泓", "鲲","昊","昕","浏","泾","璜","晟","瀚","垠","瀛","奕","兖","聿","珑","麒","麟","胥","璐" , "蠡","斛","浜","瑾","睿","喆","孚",
	"葳","禧","埝", "灏","玺","濠","靓","晖","亘","浒","煜","宸", "馨","祺","莘","甬","榭","铨","琛","暨","殡","桦",  "弇","昉"
};
private final static String[] lc_nanFirstLetter={"Y","Q","C",
	"X" , "M", "F" , "G",   "Y"  ,  "Z", "Z", "H", "K","H","X","L","J","H","C",
	"H","Y","Y","Y","Y","Y","L","Q","L","X","L" , "L","H","B","J","R","Z","F",
	"W","X","N", "H","X","H","L","H","G","H","Y","C", "X","Q","X","Y","X","Q","C",
	"J","B","H",  "Y","F"
};

/** 
 * 取得给定汉字串的首字母串,即声母串 
 * @param str 给定汉字串 
 * @return 声母串 
 */  
public static String getAllFirstLetter(String str) {  
    if (str == null || str.trim().length() == 0) {  
        return "";  
    }  

    String _str = "";  
    for (int i = 0; i < str.length(); i++) {  
        _str = _str + getFirstLetter(str.substring(i, i + 1));  
    }  

    return _str;  
}  

/** 
 * 取得给定汉字的首字母,即声母 
 * @param chinese 给定的汉字 
 * @return 给定汉字的声母 
 */  
public static String  getFirstLetter(String chinese) {  
    if (chinese == null || chinese.trim().length() == 0) {  
        return "";  
    }  
    
    for(int i=0;i<lc_nanLetter.length;i++){
		if(chinese.equals(lc_nanLetter[i])){
			chinese=lc_nanFirstLetter[i];
			return chinese;
		}
	}
    
    chinese = conversionStr(chinese, "GB2312", "ISO8859-1");  

    if (chinese.length() > 1) // 判断是不是汉字  
    {  
        int li_SectorCode = (int) chinese.charAt(0); // 汉字区码  
        int li_PositionCode = (int) chinese.charAt(1); // 汉字位码  
        li_SectorCode = li_SectorCode - 160;  
        li_PositionCode = li_PositionCode - 160;  
        int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码  
        if (li_SecPosCode > 1600 && li_SecPosCode < 5590) {  
            for (int i = 0; i < 23; i++) {  
                if (li_SecPosCode >= li_SecPosValue[i]  
                        && li_SecPosCode < li_SecPosValue[i + 1]) {  
                    chinese = lc_FirstLetter[i];  
                    break;  
                }  
            }  
        } else // 非汉字字符,如图形符号或ASCII码  
        {  
        	chinese = conversionStr(chinese, "ISO8859-1", "GB2312");  
        	chinese = chinese.substring(0, 1);  
        	
        	
        	
        }  
    }  

    return chinese;  
}  

/** 
 * 字符串编码转换 
 * @param str 要转换编码的字符串 
 * @param charsetName 原来的编码 
 * @param toCharsetName 转换后的编码 
 * @return 经过编码转换后的字符串 
 */  
private static String conversionStr(String str, String charsetName,String toCharsetName) {  
    try {  
        str = new String(str.getBytes(charsetName), toCharsetName);  
    } catch (UnsupportedEncodingException ex) {  
        System.out.println("字符串编码转换异常：" + ex.getMessage());  
    }  
    return str;  
}  
	
	
	
	/**
	 * 判断对象是否为空，判断字符串为空串，判断集合和map为空，
	 * 
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean empty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && "".equals(obj)) {
			return true;
		} else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Map && ((Map) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 将数组对象，或者容器对象，或者map对象的每个内容通过separator链接起来返回结果字符串
	 * 
	 * @param data
	 * @param separator
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String implode(Object data, String separator) {
		if (data == null) {
			return "";
		}
		StringBuffer out = new StringBuffer();
		if (data instanceof Object[]) {
			boolean flag = false;
			for (Object obj : (Object[]) data) {
				if (flag) {
					out.append(separator);
				} else {
					flag = true;
				}
				out.append(obj);
			}
		} else if (data instanceof Map) {
			Map temp = (Map) data;
			Set<Object> keys = temp.keySet();
			boolean flag = false;
			for (Object key : keys) {
				if (flag) {
					out.append(separator);
				} else {
					flag = true;
				}
				out.append(temp.get(key));
			}
		} else if (data instanceof Collection) {
			boolean flag = false;
			for (Object obj : (Collection) data) {
				if (flag) {
					out.append(separator);
				} else {
					flag = true;
				}
				out.append(obj);
			}
		} else {
			return data.toString();
		}
		return out.toString();
	}

	/**
	 * 界定符转义
	 * 
	 * @param text
	 * @param delimiter
	 * @return
	 */
	public static String pregQuote(String text, char... delimiter) {
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			boolean flag = false;
			for (char c : pregChars) {
				if (character == c) {
					flag = true;
					break;
				}
			}
			if (!flag && delimiter != null) {
				for (char d : delimiter) {
					if (character == d) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				sb.append('\\');
			}
			sb.append(character);
			character = iterator.next();
		}
		return sb.toString();
	}

	/**
	 * 把字符串转换为map对象
	 * 
	 * @param s
	 * @return
	 */
	public static Map<String, Object> stringToMap(String s) {
		JsonParser jp = new JsonParser();
		JsonObject je = (JsonObject) jp.parse(s);
		return getMap(je);
	}

	/**
	 * JsonObject 转换为map
	 * 
	 * @param ja
	 * @return
	 */
	public static Map<String, Object> getMap(JsonObject ja) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Iterator<Map.Entry<String, JsonElement>> ite = ja.entrySet().iterator(); ite.hasNext();) {
			Map.Entry<String, JsonElement> je = ite.next();
			String key = je.getKey();
			if (je.getValue().isJsonObject()) {
				map.put(key, getMap((JsonObject) je.getValue()));
			} else {
				map.put(key, je.getValue().getAsString());
			}
		}
		return map;
	}

	/**
	 * 从0到最大数的随机数
	 * 
	 * @param max
	 * @return
	 */
	public static int rand(int max) {
		return rand(0, max);
	}

	/**
	 * 指定范围的随机数
	 * 
	 * @param min
	 * @param max
	 * @return
	 */
	public static int rand(int min, int max) {
		if (min < max) {
			return random.nextInt(max - min + 1) + min;
		} else {
			return min;
		}
	}

	/**
	 * 得到指定长度的字符串，
	 * 
	 * @param length
	 *            字符串长度
	 * @param isOnlyNum
	 *            是否只是数字
	 * @return
	 */
	public static String getRandStr(int length, boolean isOnlyNum) {
		int size = isOnlyNum ? 10 : 62;
		StringBuffer hash = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			hash.append(randChars.charAt(random.nextInt(size)));
		}
		return hash.toString();
	}

	/**
	 * 把String按照,分开成数组,同时将字符串中的null或空去除掉
	 * 
	 * @param idString
	 * @return
	 */
	public static String[] StringToStringArrayWithoutNull(String idString, String separator) {
		String[] s = {};
		List<String> beans = new ArrayList<String>();
		if (!Common.empty(idString)) {
			String[] ids = idString.split(separator);
			for (int i = 0; i < ids.length; i++) {
				if (null == ids[i] || "null".equals(ids[i]) || "".equals(ids[i])) {
					continue;
				}
				beans.add(ids[i]);
			}
		}
		return beans.toArray(s);
	}

	/**
	 * 判断是否是email
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		return Common.strlen(email) > 6 && email.matches("^[\\w\\-\\.]+@[\\w\\-\\.]+(\\.\\w+)+$");
	}

	/**
	 * 得到系统指定编码的字符串长度
	 * 
	 * @param text
	 * @return
	 */
	public static int strlen(String text) {
		return strlen(text, Global.CHARSET);
	}

	/**
	 * 得到指定编码字符串长度
	 * 
	 * @param text
	 * @param charsetName
	 * @return
	 */
	public static int strlen(String text, String charsetName) {
		if (text == null || text.length() == 0) {
			return 0;
		}
		int length = 0;
		try {
			length = text.getBytes(charsetName).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return length;
	}

	/**
	 * 读取资源文件
	 * 
	 * @param request
	 * @param key
	 * @param args
	 * @return
	 */
	public static String getMessage(HttpServletRequest request, String key, Object... args) {
		if (key == null || key.length() == 0) {
			return key;
		}

		if (mr == null) {
			mr = new ClassPathXmlApplicationContext("spring/beans-message.xml");
		}
		Locale locale = null;
		if (!Common.empty(request)) {
			locale = request.getLocale();
		} else {
			locale = Locale.CHINA;
		}
		String message = null;
		if (args == null || args.length == 0) {
			message = mr.getMessage(key, null, null, locale);
		} else {
			message = mr.getMessage(key, args, locale);
		}
		return message == null ? key : message;
	}

	/**
	 * 把数组拼接成字符串
	 * 
	 * @param ser
	 * @param decollator
	 * @return
	 */
	public static String ArrayToString(String[] ser, String decollator) {
		StringBuffer str = new StringBuffer();
		String s = null;
		if (ser != null && ser.length > 0) {
			for (int i = 0; i < ser.length; i++) {
				str.append(ser[i]).append(decollator);
			}
			s = str.toString();
			if (ser.length >= 1) {
				s = s.substring(0, s.length() - 1);
			}
		}
		return s;
	}

	/**
	 * 通过正则表达式匹配内容
	 * 
	 * @param content
	 * @param regex
	 * @return
	 */
	public static List<String> pregMatch(String content, String regex) {
		List<String> strList = new ArrayList<String>();
		try {
			Perl5Matcher patternMatcher = new Perl5Matcher();
			if (patternMatcher.contains(content, new Perl5Compiler().compile(regex))) {
				MatchResult result = patternMatcher.getMatch();
				for (int i = 0; i < result.groups(); i++) {
					strList.add(result.group(i));
				}
				result = null;
			}
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return strList;
	}

	/**
	 * 通过时间戳得到时间格式的字符串
	 * 
	 * @param format
	 * @param timestamp
	 * @param timeoffset
	 * @return
	 */
	public static String gmdate(String format, int timestamp, String timeoffset) {
		return getSimpleDateFormat(format, timeoffset).format(timestamp * 1000l);
	}

	/**
	 * 得到时间格式
	 * 
	 * @param format
	 * @param timeoffset
	 * @return
	 */
	public static SimpleDateFormat getSimpleDateFormat(String format, String timeoffset) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone(timeZoneIDs.get(timeoffset)[0]));
		return sdf;
	}

	public static int time() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * 得到时间的长度
	 * 
	 * @param sGlobal
	 * @param sConfig
	 * @return
	 */
	public static String getTimeOffset(Map<String, Object> sConfig) {
		String timeoffset = null;
		timeoffset = sConfig.get("timeoffset").toString();
		return timeoffset;
	}

	/**
	 * 没有格式的在线ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getOnlineIP(HttpServletRequest request) {
		return getOnlineIP(request, false);
	}

	/**
	 * 得到在线ip
	 * 
	 * @param request
	 * @param format
	 * @return
	 */
	public static String getOnlineIP(HttpServletRequest request, boolean format) {
		// Map<String, Object> sGlobal = (Map<String, Object>)
		// request.getAttribute("sGlobal");
		String onlineip = null;// (String) sGlobal.get("onlineip");
		if (onlineip == null) {
			onlineip = request.getHeader("x-forwarded-for");
			if (Common.empty(onlineip) || "unknown".equalsIgnoreCase(onlineip)) {
				onlineip = request.getHeader("X-Real-IP");
			}
			if (Common.empty(onlineip) || "unknown".equalsIgnoreCase(onlineip)) {
				onlineip = request.getRemoteAddr();
			}
			onlineip = onlineip != null && onlineip.matches("^[\\d\\.]{7,15}$") ? onlineip : "127.0.0.1";
			// sGlobal.put("onlineip", onlineip);
		}
		if (format) {
			String[] ips = onlineip.split("\\.");
			String stip = "000";
			StringBuffer temp = new StringBuffer();
			for (int i = 0; i < 3; i++) {
				int ip = 0;
				if (i < ips.length) {
					ip = intval(ips[i]);
				}
				temp.append(Common.sprintf(stip, ip));
			}
			return temp.toString();
		} else {
			return onlineip;
		}
	}

	public static int intval(String s) {
		return intval(s, 10);
	}

	public static int intval(String s, int radix) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		if (radix == 0) {
			radix = 10;
		} else if (radix < Character.MIN_RADIX) {
			return 0;
		} else if (radix > Character.MAX_RADIX) {
			return 0;
		}
		int result = 0;
		int i = 0, max = s.length();
		int limit;
		int multmin;
		int digit;
		boolean negative = false;
		if (s.charAt(0) == '-') {
			negative = true;
			limit = Integer.MIN_VALUE;
			i++;
		} else {
			limit = -Integer.MAX_VALUE;
		}
		if (i < max) {
			digit = Character.digit(s.charAt(i++), radix);
			if (digit < 0) {
				return 0;
			} else {
				result = -digit;
			}
		}
		multmin = limit / radix;
		while (i < max) {
			digit = Character.digit(s.charAt(i++), radix);
			if (digit < 0) {
				break;
			}
			if (result < multmin) {
				result = limit;
				break;
			}
			result *= radix;
			if (result < limit + digit) {
				result = limit;
				break;
			}
			result -= digit;
		}
		if (negative) {
			if (i > 1) {
				return result;
			} else {
				return 0;
			}
		} else {
			return -result;
		}
	}

	/**
	 * 格式化之后小数点格式
	 * 
	 * @param format
	 * @param number
	 * @return
	 */
	public static String sprintf(String format, double number) {
		return new DecimalFormat(format).format(number);
	}

	/**
	 * 范围
	 * 
	 * @param value
	 * @param max
	 * @param min
	 * @return
	 */
	public static int range(Object value, int max, int min) {
		if (value instanceof String) {
			return Math.min(max, Math.max(intval((String) value), min));
		} else {
			return Math.min(max, Math.max((Integer) value, min));
		}
	}

	/**
	 * 过滤字符串
	 * 
	 * @param text
	 * @return
	 */
	public static String stripSlashes(String text) {
		if (text == null || text.equals("")) {
			return "";
		}
		StringBuffer sb = new StringBuffer(text.length());
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			switch (character) {
			case '\'':
				sb.append("'");
				break;
			case '"':
				sb.append('"');
				break;
			case '\\':
				sb.append(iterator.next());
				break;
			default:
				sb.append(character);
				break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}

	/**
	 * 过滤字符串
	 * 
	 * @param text
	 * @return
	 */
	public static String addSlashes(String text) {
		if (text == null || text.equals("")) {
			return "";
		}
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			switch (character) {
			case '\'':
			case '"':
			case '\\':
				sb.append("\\");
			default:
				sb.append(character);
				break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}

	/**
	 * 截取字符串
	 */
	public static String spiltTime(String time, String decollator) {
		String str = time;
		if (!Common.empty(time)) {
			str = time.substring(0, time.lastIndexOf(decollator));
		}
		return str;
	}

	/**
	 * 街区日期字符串，获取月和日，格式为MM-dd
	 */
	public static String getTimeToMinute(String time) {
		String str = "00-00";
		if (!Common.empty(time)) {
			String[] temp = time.split(" ");
			if (!Common.empty(temp)) {
				str = temp[0].substring(temp[0].indexOf("-") + 1, temp[0].length());
			}
		}
		return str;
	}

	/**
	 * 检查ip字符串
	 * 
	 * @param ipSettingStr
	 * @return
	 */
	public static boolean checkIpSettingStr(String ipSettingStr) {
		if (!Common.empty(ipSettingStr)) {
			String[] ipArr = null;
			String[] numArr = null;
			if (ipSettingStr.indexOf("\r\n") != -1) {
				ipArr = ipSettingStr.split("\r\n");
				for (int i = 0; i < ipArr.length; i++) {
					if (!empty(ipArr[i])) {
						numArr = ipArr[i].split("\\.");
						if (checkIpNum(numArr)) {
							continue;
						} else {
							return false;
						}
					}
				}
			} else {
				if (ipSettingStr.indexOf(".") != -1) {
					numArr = ipSettingStr.split("\\.");
					if (!checkIpNum(numArr)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 检查是否位数字
	 * 
	 * @param numArr
	 * @return
	 */
	private static boolean checkIpNum(String[] numArr) {
		if (!empty(numArr)) {
			for (int i = 0; i < numArr.length; i++) {
				if (!empty(numArr[i])) {
					if (isNumeric(numArr[i])) {
						Integer num = Integer.parseInt(numArr[i]);
						if (num < 0 || num > 255) {
							return false;
						}
					} else {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNumeric(Object obj) {
		if (obj instanceof String && !obj.equals("")) {
			String temp = ((String) obj).toLowerCase();
			if (temp.endsWith("d") || temp.endsWith("f")) {
				return false;
			} else {
				try {
					Double.parseDouble(temp);
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		} else if (obj instanceof Number) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 能够访问的ip
	 * 
	 * @param ip
	 * @param ipAccess
	 * @return
	 */
	public static boolean ipAccess(String ip, Object ipAccess) {
		return empty(ipAccess) ? true : ip.matches("^(" + pregQuote(String.valueOf(ipAccess), '/').replaceAll("\r\n", "|").replaceAll(" ", "") + ").*");
	}

	/**
	 * 禁止访问的ip
	 * 
	 * @param ip
	 * @param ipBanned
	 * @return
	 */
	public static boolean ipBanned(String ip, Object ipBanned) {
		return empty(ipBanned) ? false : ip.matches("^(" + pregQuote(String.valueOf(ipBanned), '/').replaceAll("\r\n", "|").replaceAll(" ", "") + ").*");
	}

	/**
	 * 字符串匹配
	 * 
	 * @param content
	 * @param regex
	 * @return
	 */
	public static boolean matches(String content, String regex) {
		boolean flag = false;
		try {
			flag = new Perl5Matcher().contains(content, new Perl5Compiler().compile(regex));
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 去掉某一字符串中的重复字符
	 * 
	 * @param args
	 */
	public static String removeSameString(String str, String separator, String decollator) {
		Set<String> mLinkedSet = new LinkedHashSet<String>();
		String[] strArray = str.split(separator);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strArray.length; i++) {
			if (!mLinkedSet.contains(strArray[i])) {
				mLinkedSet.add(strArray[i]);
				sb.append(strArray[i]).append(decollator);
			}
		}
		if (!Common.empty(sb.toString())) {
			return sb.toString().substring(0, sb.toString().length() - 1);
		} else {
			return sb.toString();
		}

	}

	/**
	 * 通过B K M G的到byte大小
	 * 
	 * @param unitSize
	 * @return
	 */
	public static long getByteSizeByBKMG(String unitSize) {
		long maxSize = 0;
		String maxFileSize = unitSize;
		Matcher matcher = Pattern.compile("\\d+([bkmg]?)").matcher(maxFileSize.toLowerCase());
		if (matcher.matches()) {
			String ch = matcher.replaceAll("$1");
			if (ch.equals("k")) {
				maxSize = Common.intval(maxFileSize) * 1024;
			} else if (ch.equals("m")) {
				maxSize = Common.intval(maxFileSize) * 1024 * 1024;
			} else if (ch.equals("g")) {
				maxSize = Common.intval(maxFileSize) * 1024 * 1024;
			} else {
				maxSize = Common.intval(maxFileSize);
			}
		}
		return maxSize;
	}

	/**
	 * 判断ext是否在source里面
	 * 
	 * @param source
	 * @param ext
	 * @return
	 */
	public static boolean in_array(Object source, Object ext) {
		return in_array(source, ext, false);
	}

	/**
	 * 判断ext是否在source里面
	 * 
	 * @param source
	 * @param ext
	 * @param strict
	 *            true 表明所属类型也要一样
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean in_array(Object source, Object ext, boolean strict) {
		if (source == null || ext == null) {
			return false;
		}
		if (source instanceof Collection) {
			for (Object s : (Collection) source) {
				if (s.toString().equals(ext.toString())) {
					if (strict) {
						if ((s.getClass().getName().equals(ext.getClass().getName()))) {
							return true;
						}
					} else {
						return true;
					}
				}
			}
		} else {
			for (Object s : (Object[]) source) {
				if (s.toString().equals(ext.toString())) {
					if (strict) {
						if ((s.getClass().getName().equals(ext.getClass().getName()))) {
							return true;
						}
					} else {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 替换html中的特殊字符
	 * 
	 * @param string
	 * @return
	 */
	public static String htmlSpecialChars(String string) {
		return htmlSpecialChars(string, 1);
	}

	public static String htmlSpecialChars(String text, int quotestyle) {
		if (text == null || text.equals("")) {
			return "";
		}
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			switch (character) {
			case '&':
				sb.append("&amp;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '"':
				if (quotestyle == 1 || quotestyle == 2) {
					sb.append("&quot;");
				} else {
					sb.append(character);
				}
				break;
			case '\'':
				if (quotestyle == 2) {
					sb.append("&#039;");
				} else {
					sb.append(character);
				}
				break;
			default:
				sb.append(character);
				break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}

	/**
	 * 去掉文本中的特殊字符
	 * 
	 * @return
	 */
	public static String replaceBlank(String str) {
		Pattern p = Pattern.compile("\t|\r|\n");
		Matcher m = p.matcher(str);
		return m.replaceAll("");
	}

	/**
	 * 与当前的小时差
	 * 
	 * @param createTime
	 * @return
	 */
	public static long getGapHour(Date createTime) {
		// TODO Auto-generated method stub
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH");
		long hour = 0;

		try {
			/*
			 * System.out.println(myFormatter.parse(myFormatter.format(new Date()))); System.out.println(myFormatter.parse(myFormatter.format (createTime)));
			 */
			hour = (myFormatter.parse(myFormatter.format(new Date())).getTime() - myFormatter.parse(myFormatter.format(createTime)).getTime()) / (60 * 60 * 1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hour;
	}

	public static Map<String, String> FilterParams(Map<String, String> params) {
		Map<String, String> param = new HashMap<String, String>();
		try {
			if (params != null && params.size() > 0) {
				for (String key : params.keySet()) {
					if (params.get(key) != null && !params.get(key).equals("")) {
						param.put(key, params.get(key));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return param;
	}

	/**
	 * 将字符串转换为流
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static InputStream fromString(String str) throws UnsupportedEncodingException {
		byte[] bytes = str.getBytes("UTF-8");
		return new ByteArrayInputStream(bytes);
	}

	/**
	 * 快速转换JSON格式字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String obj2Json(Object obj) {
		return new Gson().toJson(obj);
	}

	/**
	 * 是否是url
	 * 
	 * @param value
	 */
	public static boolean isUrl(String value) {
		Pattern p = null;// 正则表达式
		Matcher m = null;// 操作符表达式
		boolean b = false;
		p = p.compile("^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$");
		m = p.matcher(value);
		b = m.matches();
		return b;
	}

	public static String getSQLstr(List<String> result) {
		String sqlstr = "";
		for (int i = 0; i < result.size(); i++) {
			sqlstr += result.get(i) + " , ";
			if (i == result.size() - 1) {
				sqlstr += result.get(i);
			}
		}

		return sqlstr;
	}
	
	/**
	 * 判断空字符串
	 * @Description: TODO
	 * @param str
	 * @return
	 */
	public static boolean isNull(String str) {
		if(str==null||Common.empty(str) || "null".equals(str)) {
			return true;
		}
		return false;
	}
	public static boolean isNull(Float str) {
		if(Common.empty(str) || str==0 ) {
			return true;
		}
		return false;
	}
//	/**
//	 * 判断int类型
//	 * @Description: TODO
//	 * @param value
//	 * @return
//	 */
//	public static boolean isNull(int value) {
//		if(Common.empty(value) || value == 0) {
//			return true;
//		}
//		return false;
//	}
    /**判断Integer类型
     * @param value
     * @return
     */
    public static boolean isNull(Integer value){
    	if(value==null||Common.empty(value)||value==0){
    		return true;
    	}
    	return false;
    }
    public static boolean isNull(Long value){
    	if(Common.empty(value)||value==0){
    		return true;
    	}
    	return false;
    }
    public static String  changeNum(int num){
    	
    	return num>9?(num+""):("0"+num);
    	
    }
    
    public static String removeSameItem(String a1,String b1 ){
    	String  arr="";
    	String[] a=a1.split(",");
    	String[] b=b1.split(",");
    	if(a!=null&&b!=null&&b.length>=1){
    		for(int i=0;i<a.length;i++){
    			boolean flag=false;
    			for(int j=0;j<b.length;j++){
    				if(a[i].equals(b[j])){
    					flag=true;
    				}
    			}
    			if(!flag){
    				arr+=a[i]+",";
    			}
    		}
    		return arr.length()>0?arr.substring(0, arr.length()-1):"";
    		
    	}else{
    		return a1;
    	}
    }
    
    /**
     * 生成MD5加密盐值
     * @Description: TODO
     * @return
     */
    public static String getSalt() {
    	return new SecureRandomNumberGenerator().nextBytes().toHex();
    }
    
    /**
     * MD5加密
     * @Description: TODO
     * @param password 待加密字段
     * @param salt 加密盐值
     * @param index 加密次数
     * @return
     */
    public static String MD5Encrypt(String password,String salt,int index) {
    	return new Md5Hash(password,salt,index).toHex();
    }
    
    /**
     * 判断是否是异步请求
     * @Description: TODO
     * @author xie
     * @date 2015年3月10日 下午4:31:26  
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		if(!Common.empty(requestType) && requestType.equals("XMLHttpRequest")) {
			return true;
		}
		return false;
	}
    
    
    public static double fixDouble(double i,int fix){
    	return new BigDecimal(i).setScale(fix,RoundingMode.HALF_UP).doubleValue();
    }
    
    public static boolean isNumeric(String str){ 
    	return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    	}
    
    
    public static Integer checkInteger(Object obj){
    	if (obj instanceof Integer) {
			return Integer.valueOf(obj.toString().trim());
		}else if(obj instanceof String){
			try {
				return Integer.valueOf(obj.toString().trim());
			} catch (Exception e) {
				return 0;
			}
			
		}else {
			return 0;
		}
    	
    	
    }
  public static  boolean  isDouble(Object str)
    {
       try
       { 
    	   if(str==null){
    		   return false;
    	   }else{
    		   Double.parseDouble(str.toString());
    	   }
          return true;
       }
       catch(NumberFormatException ex){}
       return false;
    } 
    
	public static Double add(Object v1, Object v2,Integer decimal) {
		  if(!isDouble(v1)||!isDouble(v2)){return 0D; }
		
		   BigDecimal b1 = new BigDecimal(v1.toString());
		   BigDecimal b2 = new BigDecimal(v2.toString());
		   return b1.add(b2).setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	public static Double mul(Object v1, Object v2,Integer decimal) {
		 if(!isDouble(v1)||!isDouble(v2)){return 0D; }
		   BigDecimal b1 = new BigDecimal(v1.toString());
		   BigDecimal b2 = new BigDecimal(v2.toString());
		   return b1.multiply(b2).setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	public static Double sub(Object v1,Object v2,Integer decimal){
		 if(!isDouble(v1)||!isDouble(v2)){return 0D; }
		  BigDecimal b1 = new BigDecimal(v1.toString());
		   BigDecimal b2 = new BigDecimal(v2.toString());
		   return b1.subtract(b2).setScale(decimal, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
    public static Double div(Object v1,Object v2,Integer decimal){
    	 if(!isDouble(v1)||!isDouble(v2)){return 0D; }
		  BigDecimal b1 = new BigDecimal(v1.toString());
		   BigDecimal b2 = new BigDecimal(v2.toString());
		   System.out.println(b1+"---"+b2);
		   if(0==Double.valueOf(b2.toString())){return 0D;};
		 return b1.divide(b2,decimal, BigDecimal.ROUND_HALF_UP).doubleValue();  
    }
    
    
    public static HSSFWorkbook getExcel(String name,String[] column,List<List<Object>> data,int[] type){
    	HSSFWorkbook workbook = new HSSFWorkbook();  
		try {
	
			 // 声明一个工作薄  
	        // 生成一个表格  
	        HSSFSheet sheet = workbook.createSheet(name);  
	        // 设置表格默认列宽度为15个字节  
	        sheet.setDefaultColumnWidth((short) 15);  
    	 // 产生表格标题行  
	        
	        HSSFRow row = sheet.createRow(0);  
	        for(int i=0;i<column.length;i++){
	        	
	        	HSSFCell cell = row.createCell(i);  
	        	HSSFRichTextString text = new HSSFRichTextString(column[i]);  
	        	cell.setCellValue(text);  
	        }
        
	        for(int i=0;i<data.size();i++){
	        	HSSFRow datarow = sheet.createRow(i+1);  
	        	
	        	for(int j=0;j<data.get(i).size();j++){
	        		
	        		HSSFCell cell0 = datarow.createCell(j);  
	        		
	        		if(type[j]==0){
	        			 HSSFRichTextString	t0= new HSSFRichTextString((String)data.get(i).get(j));  
	        			cell0.setCellValue(t0);
	        		}else if(type[j]==2){
	        			if(!Common.empty((String)data.get(i).get(j))){
	        				
	        				
	        				SimpleDateFormat   formatter   = new   SimpleDateFormat( "yyyy-MM-dd");
	        						String   s= (String) data.get(i).get(j); 
	        						Date   date   =   formatter.parse(s);
	        				HSSFCellStyle dateCellStyle = workbook.createCellStyle();
	        	            HSSFDataFormat format = workbook.createDataFormat();
	        	            short fmt = format.getFormat("yyyy/MM/dd");
	        	            dateCellStyle.setDataFormat(fmt);
	        				cell0.setCellStyle(dateCellStyle);
	        				cell0.setCellValue(date);
	        				
	        				
	        			}else{
	        				 HSSFRichTextString	t0= new HSSFRichTextString((String)data.get(i).get(j));  
	 	        			cell0.setCellValue(t0);
	        			}
	        		}else{
	        			if(!Common.empty((String)data.get(i).get(j))){
	        				Double t1=Double.parseDouble((String)data.get(i).get(j)); 
	        				cell0.setCellValue(t1);
	        				
	        				
	        			}else{
	        				 HSSFRichTextString	t0= new HSSFRichTextString((String)data.get(i).get(j));  
	 	        			cell0.setCellValue(t0);
	        			}
	        		}
	        		
	        	}
	        	
	        	
	        }
	        HSSFRow datarow = sheet.createRow(data.size()+3);  
	        HSSFCell cell0 = datarow.createCell(0);  
	        HSSFRichTextString	t0= new HSSFRichTextString("生成时间："+new Date().toLocaleString());  
			cell0.setCellValue(t0);
		}catch (Exception re) {
			re.printStackTrace() ;
		}
    	
		return workbook;
    	
    }
    
}