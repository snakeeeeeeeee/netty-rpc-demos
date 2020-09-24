package com.github.zy.netty.common.utils;

import com.github.zy.netty.common.annotation.RpcMapping;
import com.github.zy.netty.common.domain.RpcMappingBean;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0 created by zy on 2020/4/27 17:04
 */
public class RpcMappingUtil {

    private RpcMappingUtil(){}

    public static void resolveMapping(Map<String, Object> annotationBeanMap, Map<String, RpcMappingBean> mappingBeanMap){
        if (!CollectionUtils.isEmpty(annotationBeanMap)) {
            Collection<Object> beans = annotationBeanMap.values();
            beans.forEach(bean -> {
                Class<?> clazz = bean.getClass();
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    RpcMapping rpcMapping = method.getAnnotation(RpcMapping.class);
                    if (rpcMapping != null) {
                        String urlMapping = rpcMapping.url();
                        if (!annotationBeanMap.containsKey(urlMapping)) {
                            RpcMappingBean rpcMappingBean = RpcMappingBean.builder().targetClass(clazz).targetInstance(bean).urlMapping(urlMapping).targetMethod(method).build();

                            //方法参数
                            Parameter[] parameters = method.getParameters();
                            //有参数的情况
                            if (parameters != null && parameters.length > 0) {
                                List<RpcMappingBean.RpcMethodParam> methodParams = new ArrayList<>();
                                for (Parameter parameter : parameters) {
                                    RpcMappingBean.RpcMethodParam methodParamInstance = rpcMappingBean.getRpcMethodParamInstance();
                                    methodParamInstance.setParamName(parameter.getName());
                                    methodParamInstance.setParamType(parameter.getType());
                                    methodParams.add(methodParamInstance);
                                }
                                rpcMappingBean.setMethodParams(methodParams);
                            }

                            //返回值 todo 返回值需要支持多种类型? List , Map 对象
                            if (!"void".equals(method.getReturnType().getName())) {
                                Class<?> returnType = method.getReturnType();
                                RpcMappingBean.RpcMethodReturnValue returnValueInstance = rpcMappingBean.getRpcMethodReturnValueInstance();
                                returnValueInstance.setReturnType(returnType);
                                rpcMappingBean.setReturnValue(returnValueInstance);
                                rpcMappingBean.setNeedReturn(true);
                            }

                            mappingBeanMap.put(urlMapping, rpcMappingBean);
                        } else {
                            //todo 这里需要包装一下异常
                            throw new RuntimeException(String.format("mappingUrl定义重复: %s 在 : %s 中", urlMapping, clazz.getName()));
                        }
                    }
                }
            });
        }
    }
}
