package com.github.zy.netty.common.async;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0 created by zy on 2020/6/9 15:18
 */
public enum RpcAsyncService {

    INSTANCE;

    private final ThreadPoolExecutor execute = new ThreadPoolExecutor(5, 10,
            30, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(100), new RpcAsyncThreadFactory("rpc-async"));

    public void execute(Runnable runnable) {
        execute.execute(runnable);
    }

}
