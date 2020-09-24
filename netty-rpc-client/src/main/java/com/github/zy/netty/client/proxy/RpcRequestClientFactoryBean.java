package com.github.zy.netty.client.proxy;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @version 1.0 created by zy on 2020/4/23 23:57
 */
public class RpcRequestClientFactoryBean<T> implements FactoryBean {

    private Class<T> interfaceType;

    public RpcRequestClientFactoryBean(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() {
        //这里主要是创建接口对应的实例，便于注入到spring容器中
        InvocationHandler handler = new RpcRequestClientProxy(interfaceType);
        return (T) Proxy.newProxyInstance(interfaceType.getClassLoader(),
                new Class[]{interfaceType}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceType;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
