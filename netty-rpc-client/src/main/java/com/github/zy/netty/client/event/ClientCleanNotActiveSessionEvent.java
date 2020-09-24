package com.github.zy.netty.client.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * 清除不存在session发布事件
 * @version 1.0 created by zy on 2020/5/21 11:01
 */
@Getter
@Setter
public class ClientCleanNotActiveSessionEvent extends ApplicationEvent {

    /**
     * 需要清除的session信息
     */
    private List<ClientCleanSessionEventMessage> messages;

    public ClientCleanNotActiveSessionEvent(Object source, List<ClientCleanSessionEventMessage> messages) {
        super(source);
        this.messages = messages;
    }
}
