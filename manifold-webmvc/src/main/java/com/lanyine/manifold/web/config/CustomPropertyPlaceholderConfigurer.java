package com.lanyine.manifold.web.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * <pre>
 * 配置Bean:
 * 	&#64;Bean
	public CustomPropertyPlaceholderConfigurer getPropertyConfigurer() {
		CustomPropertyPlaceholderConfigurer propertyConfigurer = new CustomPropertyPlaceholderConfigurer();
		Resource[] resources = new Resource[] { new ClassPathResource("your.properties") };//your.properties in classpath/
		propertyConfigurer.setLocations(resources);
		propertyConfigurer.setFileEncoding("UTF-8");
		return propertyConfigurer;
	}
 * 
 * 注入Bean:
 * &#64;Autowired
 * protected CustomPropertyPlaceholderConfigurer propertyConfigurer;
 * 
 * 获取属性值：
 * propertyConfigurer.getContextProperty("name")
 * </pre>
 * 
 * @author shadow
 *
 */
public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private static Map<String, Object> ctxPropertiesMap = new HashMap<>();

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
			throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	public Object getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}
}
