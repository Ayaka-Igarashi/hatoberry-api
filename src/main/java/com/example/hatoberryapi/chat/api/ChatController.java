package com.example.hatoberryapi.chat.api;

import com.example.hatoberryapi.chat.dto.MessageRequest;
import com.example.hatoberryapi.chat.dto.MessageResponse;
import com.example.hatoberryapi.chat.service.ChatService;
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
        return this.chatService.getMessages();
    }
}