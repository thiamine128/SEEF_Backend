package com.software.websocket;

import com.software.config.WebSocketConfig;
import com.software.constant.JwtClaimsConstant;
import com.software.properties.JwtProperties;
import com.software.utils.BaseContext;
import com.software.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.OnError;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Tag(name = "WebSocket")
@Component
@ServerEndpoint(value = "/api/webSocket/{token}", configurator = WebSocketConfig.class)
@Slf4j
public class WebSocketServer {
    private static int onlineCount = 0;

    private static ConcurrentHashMap<Long, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    private Session session;

    private Long userId = 0l;

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        this.session = session;
        Map<String, Object> userProperties = session.getUserProperties();
        if (Boolean.parseBoolean(userProperties.get("valid").toString())) {
            this.userId = Long.parseLong(userProperties.get("id").toString());
        } else {
            try {
                session.close();
            } catch (IOException e) {
                log.error("关闭session失败, {}", e.getMessage());
            }
        }
        session.setMaxIdleTimeout(3600000);

        //this.userId = BaseContext.getCurrentUser().get();

        webSocketMap.put(this.userId, this);
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        addOnlineCount();
    }

    /**
     * 关闭连接
     */

    @OnClose
    public void onClose() {
        if (webSocketMap.get(this.userId) != null) {
            webSocketMap.remove(this.userId);
            subOnlineCount();
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) {

    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误：" + this.userId + "，原因：" + error);
    }

    public void sendMessage(String message) throws IOException {
        //需要使用同步机制，否则多并发时会因阻塞而报错
        synchronized(this.session) {
            try {
                this.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("发送给用户 [{}] 的消息出现错误 {}", this.userId, e.getMessage());
                throw e;
            }
        }
    }


    public static void sendInfo(String message, Long userId) throws Exception {

        Iterator entrys = webSocketMap.entrySet().iterator();
        while (entrys.hasNext()) {
            Map.Entry entry = (Map.Entry) entrys.next();
            if (entry.getKey().equals(userId)) {
                webSocketMap.get(entry.getKey()).sendMessage(message);
                log.info("发送消息到用户id为 [{}] ，消息：{}", userId,  message);
                return;
            }
        }
    }


    private static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    private static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}