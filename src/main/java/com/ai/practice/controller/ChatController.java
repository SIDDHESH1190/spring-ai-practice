package com.ai.practice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import com.ai.practice.model.ChatRequest;
import com.ai.practice.model.ChatResponse;
import com.ai.practice.service.HuggingFaceService;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private HuggingFaceService huggingFaceService;

    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatResponse> send(@RequestBody ChatRequest chatRequest) {
        System.out.println("Received chat request: " + chatRequest);
        if (chatRequest == null || chatRequest.getModel() == null) {
            return ResponseEntity.badRequest().body(new ChatResponse("model is required"));
        }
        String response = huggingFaceService.queryModel(chatRequest);
        return ResponseEntity.ok(new ChatResponse(response));
    }
    
}
