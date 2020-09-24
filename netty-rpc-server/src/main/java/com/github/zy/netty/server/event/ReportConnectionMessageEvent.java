package com.github.zy.netty.server.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * 上报客户端连接服务端事件
 * @version 1.0 created by zy on 2020/5/28 12:31
 */
@Getter
@Setter
public class ReportConnectionMessageEvent extends ApplicationEvent {

    private ReportConnectionMessage message;

    public ReportConnectionMessageEvent(Object source, ReportConnectionMessage message) {
        super(source);
        this.message = message;
    }
}
