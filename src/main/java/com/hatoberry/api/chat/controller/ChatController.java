package com.hatoberry.api.chat.controller;

import com.hatoberry.api.chat.domain.message.Message;
import com.hatoberry.api.chat.dto.MessageRequest;
import com.hatoberry.api.chat.dto.MessageResponse;
import com.hatoberry.api.chat.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/messages")
    public void postMessage(@RequestBody MessageRequest request) {
        this.chatService.postMessage(request.content());
    }

    @GetMapping("/messages")
    public List<MessageResponse> getMessages() {
        return this.chatService.getMessages().stream()
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
