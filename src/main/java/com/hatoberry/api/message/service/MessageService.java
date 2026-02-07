package com.hatoberry.api.message.service;

import com.hatoberry.api.message.domain.Message;
import com.hatoberry.api.message.port.MessagePublishPort;
import com.hatoberry.api.message.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessagePublishPort messagePublishPort;

    public MessageService(MessageRepository messageRepository,
                          MessagePublishPort messagePublishPort) {
        this.messageRepository = messageRepository;
        this.messagePublishPort = messagePublishPort;
    }

    public void postMessage(String content) {
        Message message = new Message(content);
        Message savedMessage = messageRepository.save(message);
        messagePublishPort.notifyMessagePosted(savedMessage);
    }

    public List<Message> getMessages() {
        return messageRepository.findAll();
    }
}
