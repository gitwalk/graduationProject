package com.chinesechess.Config;

import com.chinesechess.controller.*;
import com.chinesechess.Handler.MyHandler;
import com.chinesechess.interceptor.WebSocketInterceptor;
import org.springframework.context.annotation.*;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new MyHandler(), "/myHandler")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");

    }


}
