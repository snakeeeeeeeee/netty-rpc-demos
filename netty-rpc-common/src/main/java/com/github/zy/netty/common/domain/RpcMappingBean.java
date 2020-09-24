package com.github.zy.netty.common.domain;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 方法映射对象
 * @version 1.0 created by zy on 2020/4/27 14:26
 */
@Data
@Builder
public class RpcMappingBean {

    /**
     * 方法的映射
     */
    private String urlMapping;

    /**
     * 目标类
     */
    private Class targetClass;

    /**
     * 目标实例
     */
    private Object targetInstance;

    /**
     * 目标方法
     */
    private Method targetMethod;

    /**
     * 方法的参数
     */
    private List<RpcMethodParam> methodParams;

    /**
     * 返回值
     */
    private RpcMethodReturnValue returnValue;

    /**
     * 是否需要返回值
     */
    private boolean needReturn;


    public RpcMethodParam getRpcMethodParamInstance(){
        return new RpcMethodParam();
    }

    public RpcMethodReturnValue getRpcMethodReturnValueInstance(){
        return new RpcMethodReturnValue();
    }

    /**
     * 持有方法参数
     */
    @Data
    @RequiredArgsConstructor
    public class RpcMethodParam {

        /**
         * 参数名称
         */
        private String paramName;

        /**
         * 方法参数类型
         */
        private Class<?> paramType;

    }

    /**
     * 方法返回数据
     */
    @Data
    @RequiredArgsConstructor
    public class RpcMethodReturnValue {

        /**
         * 返回值类型
         */
        private Class<?> returnType;
    }
}
