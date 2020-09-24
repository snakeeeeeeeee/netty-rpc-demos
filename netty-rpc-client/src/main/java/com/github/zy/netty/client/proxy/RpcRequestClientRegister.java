package com.github.zy.netty.client.proxy;

import com.github.zy.netty.client.RpcRequestClientAnnotationScanner;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @version 1.0 created by zy on 2020/6/12 11:24
 */
@Slf4j
public class RpcRequestClientRegister implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Optional<Object> mainBean = applicationContext.getBeansWithAnnotation(SpringBootApplication.class).values().stream().findFirst();
        String classPath = mainBean.get().getClass().getPackage().getName();
        List<String> scanPackages = Lists.newArrayList(classPath);
        processBeanDefinitions(getBeanDefinitions(scanPackages, registry), registry);
    }



    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 处理BeanDefinitionHolder的相关信息, 这里是将接口做一个代理,然后放到spring容器中
     * @param beanDefinitions
     * @param registry
     */
    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions, BeanDefinitionRegistry registry) {
        for (BeanDefinitionHolder holder : beanDefinitions) {
            GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
            definition.setBeanClass(RpcRequestClientFactoryBean.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
            registry.registerBeanDefinition(holder.getBeanName(), definition);
        }
    }

    /**
     * 获取被@RpcRequestClient注解标记的接口的BeanDefinitionHolder 集合
     * @param scanPackages 所需要扫描的包路径
     * @param registry BeanDefinitionRegistry
     * @return
     */
    private Set<BeanDefinitionHolder> getBeanDefinitions(List<String> scanPackages, BeanDefinitionRegistry registry){
        RpcRequestClientAnnotationScanner scanner = new RpcRequestClientAnnotationScanner(registry);
        scanner.setResourceLoader(applicationContext);
        return scanner.doScan(StringUtils.toStringArray(scanPackages));
    }


}
