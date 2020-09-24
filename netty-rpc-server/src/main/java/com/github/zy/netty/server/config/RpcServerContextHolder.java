package com.github.zy.netty.server.config;

import com.github.zy.netty.common.domain.RpcMappingBean;
import com.github.zy.netty.common.utils.RpcMappingUtil;
import com.github.zy.netty.server.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @version 1.0 created by zy on 2020/4/27 11:21
 */
@Slf4j
public class RpcServerContextHolder {

    /**
     * key urlMapping , value: RpcMappingBean
     */
    private final Map<String, RpcMappingBean> rpcMppingMap = new HashMap<>();

    private final ApplicationContext applicationContext;

    public RpcServerContextHolder(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        initRpcServiceBeans();
    }

    public Optional<RpcMappingBean> getServiceByUrlMapping(String urlMapping) {
        return Optional.ofNullable(rpcMppingMap.get(urlMapping));
    }

    private void initRpcServiceBeans() {
        Map<String, Object> urlMappingMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        RpcMappingUtil.resolveMapping(urlMappingMap, rpcMppingMap);
    }
}
