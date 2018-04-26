package com.skycloud.oa.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesHelper {
	private String charset = "utf-8";
	private Properties properties = null;
	private String fileName = null;

	public PropertiesHelper(String fileName) throws IOException {
		this.properties = new Properties();
		this.fileName = fileName;
		InputStream fis = new FileInputStream(fileName);
		this.properties.load(fis);
		fis.close();
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getValue(String key) {
		return properties.getProperty(key);
	}

	public void setValue(String key, String value) {
		properties.setProperty(key, value);
	}

	public void saveProperties() throws IOException {
		properties.store(new FileOutputStream(fileName), null);
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}