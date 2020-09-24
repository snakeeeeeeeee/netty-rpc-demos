package com.github.zy.netty.common.annotation;

import java.lang.annotation.*;

/**
 * @version 1.0 created by zy on 2020/4/23 16:18
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcMapping {

    /**
     * 标识方法级别的隔离
     * @return
     */
    String url();
}
