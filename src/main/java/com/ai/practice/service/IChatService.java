package com.ai.practice.service;

import com.ai.practice.model.ChatRequest;

public interface IChatService {
    String sendMessage(ChatRequest chatRequest);
}
