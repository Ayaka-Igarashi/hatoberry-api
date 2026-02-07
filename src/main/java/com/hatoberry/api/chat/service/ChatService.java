package com.hatoberry.api.chat.service;

import com.hatoberry.api.chat.domain.message.Message;
import com.hatoberry.api.chat.domain.message.MessageRepository;
import com.hatoberry.api.chat.dto.MessageResponse;
import com.hatoberry.api.chat.event.MessagePostedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final MessageRepository messageRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ChatService(MessageRepository messageRepository, 
                       ApplicationEventPublisher eventPublisher) {
        this.messageRepository = messageRepository;
        this.eventPublisher = eventPublisher;
    }

    public void postMessage(String content) {
        Message message = new Message(content);
        Message savedMessage = messageRepository.save(message);
        
        MessageResponse response = new MessageResponse(
                savedMessage.getId(),
                savedMessage.getContent(),
                savedMessage.getPostedAt()
        );
        eventPublisher.publishEvent(new MessagePostedEvent(response));
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

}
