package com.github.zy.netty.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @version 1.0 created by zy on 2020/6/12 17:13
 */
public class SpringContextUtil implements ApplicationContextAware {

    public static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName){
        return (T) applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType){
        return (T) applicationContext.getBean(requiredType);
    }

}
