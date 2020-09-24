package com.github.zy.netty.common.domain;

import com.github.zy.netty.common.async.RpcAsyncService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @version 1.0 created by zy on 2020/4/28 18:01
 */
@Data
@Slf4j
public class MessageResponseModule {

    private Integer code;

    private String info;

    private String payload;

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            RpcAsyncService.INSTANCE.execute(new Runnable() {
                @Override
                public void run() {
                    log.info("11111");
                }
            });
        }
    }
}
