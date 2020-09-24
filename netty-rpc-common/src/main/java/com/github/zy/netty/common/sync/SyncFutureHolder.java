package com.github.zy.netty.common.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @version 1.0 created by zy on 2020/4/26 23:26
 */
@Slf4j
public class SyncFutureHolder {

    public static ConcurrentHashMap<String, SyncFuture> SYNC_FUTURE_MAP = new ConcurrentHashMap<>();

}
