package com.lanyine.manifold.rpc;

import java.util.Map;
import java.util.Set;

import com.lanyine.manifold.rpc.annotation.RSType;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.remoting.caucho.HessianServiceExporter;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * 服务端服务接口自动扫描类
 * 
 * @author shadow
 * 
 */
public final class RemoteServicePathScanner extends RServiceClassPathScanner {
	// (spring-boot)AnnotationConfigEmbeddedWebApplicationContext
	public RemoteServicePathScanner(BeanDefinitionRegistry registry) {
		super(registry);
	}

	@Override
	public Set<BeanDefinitionHolder> scanner(Set<BeanDefinitionHolder> beanDefinitions) {
		for (BeanDefinitionHolder holder : beanDefinitions) {
			GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
			AnnotationMetadata metadata = ((ScannedGenericBeanDefinition) definition).getMetadata();
			Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(ANNOTATION_CLASS.getName());
			RSType type = (RSType) annotationAttributes.get("type");
			int port = (int) annotationAttributes.get("port");
			String beanNameRef = holder.getBeanName();

			if (!beanNameRef.startsWith("/"))
				throw new RuntimeException("API beanNameRef[" + beanNameRef + "] is illegal. It should be like '[/"
						+ beanNameRef + "]' . ");

			Object beanObj = getBeanRef(beanNameRef.substring(1));
			if (beanObj == null) {
				log.error("No bean named '{}' is defined", beanNameRef);
				continue;
			}
			Class<?> clazz = null;
			if (RSType.HESSIAN == type) {
				clazz = HessianServiceExporter.class;
			} else if (RSType.HTTP == type) {
				clazz = HttpInvokerServiceExporter.class;
			} else if (RSType.RMI == type) {
				clazz = RmiServiceExporter.class;
				definition.getPropertyValues().add("registryPort", port);
				definition.getPropertyValues().add("serviceName", beanNameRef.substring(1));
			}
			definition.getPropertyValues().add("serviceInterface", definition.getBeanClassName());
			definition.getPropertyValues().add("service", beanObj);
			// set bean class
			definition.setBeanClass(clazz);
			log.info("Service[{}]-[{}] register successfully.", type.name(), beanNameRef);
		}
		return beanDefinitions;
	}
}