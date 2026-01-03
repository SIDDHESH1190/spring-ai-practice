package com.ai.practice.controller;

import org.springframework.web.bind.annotation.*;

import com.ai.practice.model.ChatRequest;
import com.ai.practice.service.IChatService;

@RestController
@RequestMapping("/chat")
public class ChatController {
    
    private final IChatService chatService;
    
    public ChatController(IChatService chatService) {
        this.chatService = chatService;
    }
    
    @PostMapping("/send")
    public String sendMessage(@RequestBody ChatRequest chatRequest) {
        return chatService.sendMessage(chatRequest);
    }
}