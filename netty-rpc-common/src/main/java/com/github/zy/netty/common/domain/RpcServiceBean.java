package com.github.zy.netty.common.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0 created by zy on 2020/4/24 14:49
 */
@Data
@Builder
public class RpcServiceBean {
    /**
     * 模块id
     */
    private String moduleId;

    /**
     * 目标类
     */
    private Class targetClass;

    /**
     * 目标实例
     */
    private Object targetInstance;

    /**
     * 所持有方法映射, key为mappingUrl, value为RpcServiceMethod
     */
    private Map<String, RpcServiceMethod> methodMap;

    public RpcServiceMethod getRpcServiceMethodInstance() {
        return new RpcServiceMethod();
    }

    public RpcServiceMethodParam getRpcServiceMethodParamInstance(){
        return new RpcServiceMethodParam();
    }

    public RpcServiceMethodReturnValue getRpcServiceMethodReturnValueInstance(){
        return new RpcServiceMethodReturnValue();
    }

    /**
     * 持有方法
     */
    @Data
    @RequiredArgsConstructor
    public class RpcServiceMethod {

        private String mappingUrl;

        private Method targetMethod;

        private List<RpcServiceMethodParam> methodParams;

        private RpcServiceMethodReturnValue returnValue;

        private boolean needReturn;

    }

    /**
     * 持有方法参数
     */
    @Data
    @RequiredArgsConstructor
    public class RpcServiceMethodParam {
        private String paramName;

        private Class<?> paramType;

    }

    /**
     * 方法返回数据
     */
    @Data
    @RequiredArgsConstructor
    public class RpcServiceMethodReturnValue {
        private Class<?> returnType;

    }
}
