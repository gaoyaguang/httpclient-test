package com.light.httpclient.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * @Description: 动态读取配置文件来加载属性
 *
 * @author GaoYaguang
 * @version 1.0.0
 * <pre>
 * Modification History: 
 * Date         Author      Version     Description 
 * ------------------------------------------------------------------ 
 * 2017年1月11日     GaoYaguang    1.0.0     1.0.0 Version 
 * </pre>
 */
public class PropertiesUtil {

	private static final Logger LOG = Logger.getLogger(PropertiesUtil.class);

	private static Hashtable<String, Properties> pptContainer = new Hashtable<String, Properties>();

	public final static String getValue(String propertyFilePath, String key) {
		Properties ppts = getProperties(propertyFilePath);
		return ppts == null ? null : ppts.getProperty(key);
	}

	public final static Properties getProperties(String propertyFilePath) {
		if (propertyFilePath == null) {
			LOG.error("propertyFilePath is null!");
			return null;
		}
		Properties ppts = pptContainer.get(propertyFilePath);
		if (ppts == null) {
			ppts = loadPropertyFile(propertyFilePath);
			if (ppts != null) {
				pptContainer.put(propertyFilePath, ppts);
			}
		}
		return ppts;
	}

	private static Properties loadPropertyFile(String propertyFilePath) {
		InputStream is = PropertiesUtil.class.getResourceAsStream(propertyFilePath);
		if (is == null) {
			return loadPropertyFileByFileSystem(propertyFilePath);
		}
		Properties ppts = new Properties();
		try {
			ppts.load(is);
			return ppts;
		} catch (Exception e) {
			LOG.debug("加载属性文件出错:" + propertyFilePath, e);
			return null;
		}
	}

	private static Properties loadPropertyFileByFileSystem(final String propertyFilePath) {
		try {
			Properties ppts = new Properties();
			ppts.load(new FileInputStream(propertyFilePath));
			return ppts;
		} catch (FileNotFoundException e) {
			LOG.error("FileInputStream(\"" + propertyFilePath + "\")! FileNotFoundException: " + e);
			return null;
		} catch (IOException e) {
			LOG.error("Properties.load(InputStream)! IOException: " + e);
			return null;
		}
	}
}
