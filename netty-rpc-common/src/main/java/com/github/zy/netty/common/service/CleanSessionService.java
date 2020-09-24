package com.github.zy.netty.common.service;

import com.github.zy.netty.common.event.CleanNotActiveSessionEvent;
import com.github.zy.netty.common.event.CleanSessionEventMessage;
import com.github.zy.netty.common.session.ServerSession;
import com.github.zy.netty.common.session.SessionManager;
import io.netty.channel.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0 created by zy on 2020/5/8 11:30
 */
@Slf4j
@RequiredArgsConstructor
public class CleanSessionService {

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private final SessionManager sessionManager;
    private final ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        clearSession();
    }

    public void clearSession() {
        executor.scheduleAtFixedRate(() -> {
            log.debug("检查非活动状态session....");
            List<String> removeIds = new ArrayList<>();
            List<CleanSessionEventMessage> messages = new ArrayList<>();
            sessionManager.findAll().forEach(session -> {
                ServerSession serverSession = (ServerSession)session;
                Channel channel = serverSession.getChannel();
                boolean active = channel.isActive();
                if (!active) {
                    removeIds.add(serverSession.getId());
                    messages.add(CleanSessionEventMessage.builder().sessionId(serverSession.getId()).systemId(serverSession.getSystemId()).ip(serverSession.getIp()).build());
                }
            });

            //移除不是活动中的session
            if (!CollectionUtils.isEmpty(removeIds)) {
                log.debug("移除非活动状态session {}", removeIds);
                sessionManager.delete(removeIds);


            }

            if (!CollectionUtils.isEmpty(messages)) {
                log.debug("发布移除session事件...");
                //发布清除session事件
                applicationContext.publishEvent(new CleanNotActiveSessionEvent(this, messages));
            }

        }, 5, 5, TimeUnit.SECONDS);
    }


}
