package com.hatoberry.api.message.adapter.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hatoberry.api.common.websocket.WebSocketHandler;
import com.hatoberry.api.message.domain.Message;
import com.hatoberry.api.message.dto.MessageResponse;
import com.hatoberry.api.message.port.MessagePublishPort;
import org.springframework.stereotype.Component;

@Component
public class MessageWebSocketAdapter implements MessagePublishPort {
    private final WebSocketHandler webSocketHandler;
    private final ObjectMapper objectMapper;

    public MessageWebSocketAdapter(WebSocketHandler webSocketHandler,
                                   ObjectMapper objectMapper) {
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = objectMapper;
    }

    @Override
    public void notifyMessagePosted(Message message) {
        try {
            MessageResponse response = new MessageResponse(
                    message.getId(),
                    message.getContent(),
                    message.getPostedAt()
            );
            String json = objectMapper.writeValueAsString(response);
            webSocketHandler.broadcast(json);
        } catch (Exception e) {
            System.err.println("WebSocket通知エラー: " + e.getMessage());
        }
    }
}
