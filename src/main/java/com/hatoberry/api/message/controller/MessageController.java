package com.hatoberry.api.message.controller;

import com.hatoberry.api.message.domain.Message;
import com.hatoberry.api.message.dto.MessageRequest;
import com.hatoberry.api.message.dto.MessageResponse;
import com.hatoberry.api.message.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public void postMessage(@RequestBody MessageRequest request) {
        this.messageService.postMessage(request.content());
    }

    @GetMapping
    public List<MessageResponse> getMessages() {
        return this.messageService.getMessages().stream()
                .map(this::toResponse)
                .toList();
    }

    private MessageResponse toResponse(Message message) {
        return new MessageResponse(
                message.getId(),
                message.getContent(),
                message.getPostedAt()
        );
    }
}
