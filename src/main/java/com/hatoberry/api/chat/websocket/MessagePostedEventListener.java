package com.hatoberry.api.chat.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatoberry.api.chat.event.MessagePostedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MessagePostedEventListener {
    private final ChatWebSocketHandler webSocketHandler;
    private final ObjectMapper objectMapper;

    public MessagePostedEventListener(ChatWebSocketHandler webSocketHandler, ObjectMapper objectMapper) {
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = objectMapper;
    }

    @EventListener
    @Async
    public void onMessagePosted(MessagePostedEvent event) {
        try {
            String json = objectMapper.writeValueAsString(event.response());
            webSocketHandler.broadcast(json);
        } catch (Exception e) {
            System.err.println("WebSocket通知エラー: " + e.getMessage());
        }
    }
}
