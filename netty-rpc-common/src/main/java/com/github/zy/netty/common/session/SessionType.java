package com.github.zy.netty.common.session;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0 created by zy on 2020/4/26 14:54
 */
@Getter
@AllArgsConstructor
public enum SessionType {

    CLIENT(1, "客户端"),
    SERVER(2, "服务端");


    private int type;

    private String desc;
}
