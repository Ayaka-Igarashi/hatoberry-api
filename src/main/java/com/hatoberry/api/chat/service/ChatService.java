package com.hatoberry.api.chat.service;

import com.hatoberry.api.chat.domain.message.Message;
import com.hatoberry.api.chat.domain.message.MessageRepository;
import com.hatoberry.api.chat.dto.MessageResponse;
import com.hatoberry.api.chat.websocket.ChatWebSocketHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final MessageRepository messageRepository;
    private final ChatWebSocketHandler webSocketHandler;
    private final ObjectMapper objectMapper;

    public ChatService(MessageRepository messageRepository, 
                       ChatWebSocketHandler webSocketHandler,
                       ObjectMapper objectMapper) {
        this.messageRepository = messageRepository;
        this.webSocketHandler = webSocketHandler;
        this.objectMapper = objectMapper;
    }

    public void postMessage(String content) {
        Message message = new Message(content);
        Message savedMessage = messageRepository.save(message);
        
        // WebSocketで全クライアントに通知
        notifyNewMessage(savedMessage);
    }

    public List<MessageResponse> getMessages() {
        return messageRepository.findAll().stream()
                .map(message -> new MessageResponse(
                        message.getId(),
                        message.getContent(),
                        message.getPostedAt()
                ))
                .toList();
    }

    private void notifyNewMessage(Message message) {
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
