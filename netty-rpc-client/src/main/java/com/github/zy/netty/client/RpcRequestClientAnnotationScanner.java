package com.github.zy.netty.client;

import com.github.zy.netty.client.annotation.RpcRequestClient;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

/**
 * @version 1.0 created by zy on 2020/6/16 18:30
 */
public class RpcRequestClientAnnotationScanner extends ClassPathBeanDefinitionScanner {


    public RpcRequestClientAnnotationScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    public void registerDefaultFilters() {
        this.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        return super.doScan(basePackages);
    }

    @Override
    public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return (beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent())
                && (beanDefinition.getMetadata().hasAnnotation(RpcRequestClient.class.getName()));
    }

}
