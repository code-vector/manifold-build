package com.lanyine.manifold.rpc;

import static org.springframework.util.Assert.notNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

/**
 * 服务接口,自动扫描配置
 * 
 * @author shadow
 * 
 */
public final class RemoteServiceConfigurer implements ApplicationContextAware, InitializingBean {
	private final Logger LOG = LoggerFactory.getLogger(RemoteServiceConfigurer.class);

	private String basePackage;
	private ApplicationContext applicationContext;

	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		notNull(this.basePackage, "Property 'basePackage' is required ");
		long t1 = System.currentTimeMillis();
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) applicationContext;
		RemoteServicePathScanner scan = new RemoteServicePathScanner(registry);
		scan.setApplicationContext(applicationContext);
		// scan
		scan.scan(StringUtils.tokenizeToStringArray(basePackage,
				ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS));
		LOG.info("Register all RemoteServices ,time is {}ms.", (System.currentTimeMillis() - t1));
	}
}