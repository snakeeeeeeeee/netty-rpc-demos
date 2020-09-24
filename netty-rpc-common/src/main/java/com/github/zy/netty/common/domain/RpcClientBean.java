package com.github.zy.netty.common.domain;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0 created by zy on 2020/4/24 17:57
 */
@Data
@Builder
public class RpcClientBean {
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
     * 所持有方法映射, key为mappingUrl, value为RpcClientMethod
     */
    private Map<String, RpcClientMethod> methodMap;

    public RpcClientMethod getRpcClientMethodInstance() {
        return new RpcClientMethod();
    }

    public RpcClientMethodParam getRpcClientMethodParamInstance() {
        return new RpcClientMethodParam();
    }

    public RpcClientMethodReturnValue getRpcClientMethodReturnValueInstance() {
        return new RpcClientMethodReturnValue();
    }

    @Data
    public class RpcClientMethod {
        private String mappingUrl;

        private Method targetMethod;

        private List<RpcClientMethodParam> methodParams;

        private RpcClientMethodReturnValue returnValue;

        private boolean needReturn;
    }

    /**
     * 持有方法参数
     */
    @Data
    public class RpcClientMethodParam {
        private String paramName;

        private Class<?> paramType;

    }

    /**
     * 方法返回数据
     */
    @Data
    public class RpcClientMethodReturnValue {
        private Class<?> returnType;
    }
}
