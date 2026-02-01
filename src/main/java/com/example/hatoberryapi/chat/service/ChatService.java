package com.example.hatoberryapi.chat.service;

import com.example.hatoberryapi.chat.domain.message.Message;
import com.example.hatoberryapi.chat.domain.message.MessageRepository;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    private final MessageRepository messageRepository;

    public ChatService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void postMessage(String content) {
        Message message = new Message(content);
        messageRepository.save(message);
    }
}
