package com.github.zy.netty.server.demo.demo;

import lombok.Builder;
import lombok.Data;

/**
 * @version 1.0 created by zy on 2020/6/11 10:25
 */
@Builder
@Data
public class HelloResult {

    private String desc;

    private int id;

}
