package com.lanyine.manifold.rpc;

import static org.springframework.util.Assert.notNull;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import com.lanyine.manifold.rpc.annotation.RService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 自动扫描带服务注解的接口
 * 
 * @author shadow
 * @see #RService
 */
public abstract class RServiceClassPathScanner extends ClassPathBeanDefinitionScanner {
	protected final Class<? extends Annotation> ANNOTATION_CLASS = RService.class;
	protected Logger log = LoggerFactory.getLogger(ClassPathBeanDefinitionScanner.class);
	protected ApplicationContext applicationContext;

	private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator() {
		@Override
		protected String buildDefaultBeanName(BeanDefinition definition) {
			AnnotationMetadata metadata = ((ScannedGenericBeanDefinition) definition).getMetadata();
			Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ANNOTATION_CLASS.getName());
			return annotationAttributes.get("value").toString();
		}
	};

	// (spring-boot)AnnotationConfigEmbeddedWebApplicationContext
	public RServiceClassPathScanner(BeanDefinitionRegistry registry) {
		super(registry, false);
		this.setBeanNameGenerator(beanNameGenerator);
		this.setIncludeAnnotationConfig(true);
		this.registerDefaultFilters();
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.setResourceLoader(applicationContext);
	}

	@Override
	public Set<BeanDefinitionHolder> doScan(String... basePackages) {
		Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
		if (beanDefinitions.isEmpty()) {
			log.error("No RService was found in '{}' package. Please check your configuration.",
					Arrays.toString(basePackages));
		} else {
			// scanner class path
			scanner(beanDefinitions);
		}
		return beanDefinitions;
	}

	/**
	 * 需要子类去实现的扫描注册的细节
	 */
	public abstract Set<BeanDefinitionHolder> scanner(Set<BeanDefinitionHolder> beanDefinitions);

	/**
	 * 根据BeanName获取Sping-Bean
	 * 
	 * @param beanNameRef
	 * @return
	 */
	protected Object getBeanRef(String beanNameRef) {
		notNull(beanNameRef, "Bean Ref is required .");
		try {
			return applicationContext.getBean(beanNameRef);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
		return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent());
	}

	@Override
	protected boolean checkCandidate(String beanName, BeanDefinition beanDefinition) throws IllegalStateException {
		if (super.checkCandidate(beanName, beanDefinition)) {
			return true;
		} else {
			log.warn("Skipping ServiceExporter with name '" + beanName + "'and '" + beanDefinition.getBeanClassName()
					+ "' serviceInterface " + ". Bean already defined with the same name!");
			return false;
		}
	}

	@Override
	protected void registerDefaultFilters() {
		addIncludeFilter(new AnnotationTypeFilter(ANNOTATION_CLASS));
	}
}