package com.skycloud.oa.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.skycloud.oa.utils.SystemProperties;

public class InitSystemListener implements ServletContextListener {
	private static Logger LOG = Logger.getLogger(InitSystemListener.class);

	private static final String CONFIG_LOCATION_PARAM = "initConfigLocation";

	@Override
	public void contextDestroyed(ServletContextEvent event) {

	}

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();

		LOG.info("initialize system properties");

		String configFile = servletContext
				.getInitParameter(CONFIG_LOCATION_PARAM);

		if (!StringUtils.isEmpty(configFile)) {
			String[] cfs = configFile.split(",");
			List<String> configFileList = new ArrayList<String>();
			for (String cf : cfs) {
				configFileList.add(cf);
			}
			SystemProperties.loadPropertyFiles(configFileList);
		}

	}
}
