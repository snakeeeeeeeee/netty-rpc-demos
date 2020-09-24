package com.github.zy.netty.common.sync;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SyncFuture<T> implements Future<T> {

    /**
     * 因为请求和响应是一一对应的，因此初始化CountDownLatch值为1。
     */
    private CountDownLatch latch = new CountDownLatch(1);

    /**
     * 需要响应线程设置的响应结果
     */
    private T response;

    /**
     * Futrue的请求时间，用于计算Future是否超时
     */
    private final long beginTime = System.currentTimeMillis();


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return response != null;
    }

    // 获取响应结果，直到有结果才返回。
    @Override
    public T get() throws InterruptedException {
        latch.await();
        return response;
    }

    // 获取响应结果，直到有结果或者超过指定时间就返回。
    @Override
    public T get(long timeout, TimeUnit unit) {
        try {
            if (response != null) {
                return response;
            } else {
                if (latch.await(timeout, unit)) {
                    return response;
                }
            }
        } catch (InterruptedException e) {
            log.error("终端线程等待异常", e);
        } finally {
            latch.countDown();
        }
        return null;
    }

    // 用于设置响应结果，并且做countDown操作，通知请求线程
    public void setResponse(T response) {
        this.response = response;
        latch.countDown();
    }

    public long getBeginTime() {
        return beginTime;
    }
}
