package com.github.zy.netty.server.strategy;
import com.github.zy.netty.common.constants.Profile;
import com.github.zy.netty.common.protocol.DefaultMessagePacket;
import com.github.zy.netty.common.protocol.MessageType;
import com.github.zy.netty.common.session.ServerSession;
import com.github.zy.netty.common.session.SessionHelper;
import com.github.zy.netty.common.session.SessionManager;
import com.github.zy.netty.server.event.ReportConnectionMessage;
import com.github.zy.netty.server.event.ReportConnectionMessageEvent;
import io.netty.channel.ChannelHandlerContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

/**
 * @version 1.0 created by zy on 2020/4/28 0:43
 */
@Slf4j
@RequiredArgsConstructor
public class ServerMessageReportModuleIdResolver implements ServerMessageResolverStrategy {

    @Value("${spring.profiles.active:dev}")
    private String profile;
    private final SessionManager sessionManager;
    private final ApplicationContext applicationContext;

    @Override
    public boolean support(MessageType messageType) {
        return MessageType.REPORT_MODULE_ID.equals(messageType);
    }

    @Override
    public void resolver(ChannelHandlerContext ctx, DefaultMessagePacket msg) {
        //处理客户端上报moduleId的策略
        log.info("接收到客户端上报的系统Id请求 systemId: {}, ip : {}", msg.getSystemId(), msg.getTargetIp());
        ServerSession session = SessionHelper.buildServerSession(msg.getTargetIp(), msg.getSystemId(), Profile.get(profile), ctx.channel());
        if (StringUtils.isEmpty(session.getSystemId())) {
            log.error("客户端上报的系统id为空, 客户端ip : {} ", msg.getTargetIp());
            throw new RuntimeException();
        } else {
            sessionManager.save(session);
            //发布上报系统id事件,也就是初始化连接事件
            applicationContext.publishEvent(new ReportConnectionMessageEvent(this
                    , ReportConnectionMessage.builder().ip(msg.getTargetIp()).profile(profile).systemId(msg.getSystemId()).build()));
        }
    }
}
