package com.github.zy.netty.common.session;

import com.github.zy.netty.common.constants.Profile;
import io.netty.channel.Channel;

/**
 * @version 1.0 created by zy on 2020/4/26 16:17
 */
public class SessionHelper {

    private static final String DELIMITER = ":";

    private SessionHelper() {
    }

    public static ClientSession buildClientSession(String serverIp, int serverPort, Profile profile, Channel channel) {
        ClientSession session = buildBasicClientSession(serverIp, serverPort, profile, channel);
        session.setSessionType(SessionType.CLIENT);
        return session;
    }


    public static ServerSession buildServerSession(String clientIp, int clientPort, Profile profile, String systemId, Channel channel) {
        ServerSession session = buildBasicSeverSession(clientIp, clientPort, profile, channel);
        session.setSessionType(SessionType.SERVER);
        session.setSystemId(systemId);
        return session;
    }

    public static ServerSession buildServerSession(String clientIp, String systemId, Profile profile, Channel channel) {
        ServerSession session = buildBasicSeverSession(clientIp, systemId, profile, channel);
        session.setSessionType(SessionType.SERVER);
        session.setSystemId(systemId);
        return session;
    }

    private static Session buildBasicSession(String ip, String systemId, Profile profile, Channel channel) {
        Session session = new Session();
        session.setIp(ip);
        session.setId(getSessionId(systemId, ip));
        session.setChannel(channel);
        session.setProfile(profile);
        return session;
    }

    private static Session buildBasicSession(String ip, int port, Profile profile, Channel channel) {
        Session session = new Session();
        session.setIp(ip);
        session.setPort(port);
        session.setId(getSessionId(ip, port));
        session.setChannel(channel);
        session.setProfile(profile);
        return session;
    }

    private static ClientSession buildBasicClientSession(String ip, int port, Profile profile, Channel channel) {
        ClientSession session = new ClientSession();
        session.setIp(ip);
        session.setPort(port);
        session.setId(getSessionId(ip, port));
        session.setChannel(channel);
        session.setProfile(profile);
        return session;
    }

    private static ServerSession buildBasicSeverSession(String ip, String systemId, Profile profile, Channel channel) {
        ServerSession session = new ServerSession();
        session.setIp(ip);
        session.setId(getSessionId(systemId, ip));
        session.setChannel(channel);
        session.setProfile(profile);
        return session;
    }

    private static ServerSession buildBasicSeverSession(String ip, int port, Profile profile, Channel channel) {
        ServerSession session = new ServerSession();
        session.setIp(ip);
        session.setPort(port);
        session.setId(getSessionId(ip, port));
        session.setChannel(channel);
        session.setProfile(profile);
        return session;
    }

    public static String getSessionId(String ip, int port) {
        return ip + DELIMITER + port;
    }

    public static String getSessionId(String systemId, String ip){
        return systemId + DELIMITER + ip;
    }
}
