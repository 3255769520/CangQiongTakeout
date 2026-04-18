package com.hhxy.websocket;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/ws/admin")
@Slf4j
public class WebSocketServer {

    private static final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        log.info("管理端 WebSocket 连接建立: {}", session.getId());
        sessionMap.put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session) {
        log.info("管理端 WebSocket 连接断开: {}", session.getId());
        sessionMap.remove(session.getId());
    }

    public void sendToAllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                log.error("WebSocket 消息推送失败: {}", e.getMessage());
            }
        }
    }
}