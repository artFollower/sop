package com.skycloud.oa.utils;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SystemProperties {
	private static Logger LOG = Logger.getLogger(SystemProperties.class);

	private static Properties props;

	/**
	 * 加载指定的多个资源文件。
	 * 
	 * @param filename
	 */
	public static void loadPropertyFiles(List<String> filenames) {
		props = new Properties();
		InputStream in = null;
		try {
			if (filenames != null) {
				for (String filename1 : filenames) {
					in = SystemProperties.class.getClassLoader()
							.getResourceAsStream(filename1);
					props.load(in);
				}
			}
		} catch (Exception ex) {
			LOG.error("Fail to load system initialize rules file.", ex);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (java.io.IOException e) {
					LOG.error("I/O Exception at close InputStream.");
				}
			}
		}
	}

	/**
	 * Get the value in system property file by the key
	 * 
	 * @param key
	 *            key String
	 * @return String
	 */
	public static String getProperty(String key) {
		Object obj = props.get(key);
		if (obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}

	/**
	 * Get the value in system property file by the key, with a default
	 * value,once the value is not set.
	 * 
	 * @param key
	 *            key String
	 * @param defaultValue
	 *            defaultValue String
	 * @return String
	 */
	public static String getProperty(String key, String defaultValue) {
		if (props == null)
			return defaultValue;
		Object obj = props.get(key);
		if (obj != null) {
			return obj.toString();
		} else {
			return defaultValue;
		}
	}
}
