package com.software.config;

import com.software.SoftwareContext;
import com.software.constant.JwtClaimsConstant;
import com.software.properties.JwtProperties;
import com.software.utils.BaseContext;
import com.software.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.websocket.Decoder;
import jakarta.websocket.Encoder;
import jakarta.websocket.Extension;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {
    private JwtProperties jwtProperties = SoftwareContext.getBean(JwtProperties.class);

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        String[] path = request.getRequestURI().getPath().split("/");
        String token = path[path.length - 1];
        final Map<String, Object> userProperties = sec.getUserProperties();
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            String userRole = String.valueOf(claims.get(JwtClaimsConstant.USER_ROLE).toString());
            log.info("当前用户id：", userId);
            log.info("当前用户身份", userRole);
            userProperties.put("id", userId);
            userProperties.put("valid", true);
        } catch (Exception ex) {
            userProperties.put("valid", false);
            return;
        }
        super.modifyHandshake(sec, request, response);
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
