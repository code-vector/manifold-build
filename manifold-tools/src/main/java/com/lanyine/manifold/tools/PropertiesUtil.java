package com.lanyine.manifold.tools;

import java.io.BufferedInputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Properties工具类
 * 
 * @author shadow
 *
 */
public final class PropertiesUtil {
	protected static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

	private PropertiesUtil() {

	}

	/**
	 * 根据资源文件名，和name获取对应的value，如果没有对应的值，将返回null
	 * 
	 * @param resourceFile
	 * @param name
	 * @return
	 */
	public static String getValue(String resourceFile, String name) {
		if (resourceFile == null || name == null) {
			return null;
		}
		return loadPropertiesFromResourceFile(resourceFile).getProperty(name);
	}

	/**
	 * 根据资源文件路径，读取资源文件，如果资源文件不存在，将返回默认的[new Properties()]
	 * 
	 * @param resourceFile
	 *            资源文件名
	 * @return
	 */
	public static Properties loadPropertiesFromResourceFile(String resourceFile) {
		Properties properties = new Properties();
		try (BufferedInputStream bis = new BufferedInputStream(
				PropertiesUtil.class.getClassLoader().getResourceAsStream(resourceFile))) {
			properties.load(bis);
			return properties;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return properties;
	}
}