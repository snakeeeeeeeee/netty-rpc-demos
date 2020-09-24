package com.github.zy.netty.common.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/**
 * @version 1.0 created by zy on 2020/5/21 11:01
 */
@Getter
@Setter
public class CleanNotActiveSessionEvent extends ApplicationEvent {

    /**
     * 需要清除的session信息
     */
    private List<CleanSessionEventMessage> messages;

    public CleanNotActiveSessionEvent(Object source, List<CleanSessionEventMessage> messages) {
        super(source);
        this.messages = messages;
    }
}
