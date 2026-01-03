package com.ai.practice.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.ai.practice.model.ChatRequest;
import com.ai.practice.service.IChatService;

@Service
@Primary
public class OllamaServiceImpl implements IChatService {

    private static final Logger logger = LoggerFactory.getLogger(OllamaServiceImpl.class);

    private final ChatClient chatClient;
    
    public OllamaServiceImpl(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @Override
    public String sendMessage(ChatRequest chatRequest) {
         try {
            if (chatRequest == null) {
                throw new IllegalArgumentException("ChatRequest cannot be null");
            }
            
            String model = chatRequest.getModel();
            String input = chatRequest.getInput();
            
            if (input == null || input.trim().isEmpty()) {
                return "Please provide a message.";
            }
            
            ChatOptions options = ChatOptions.builder()
                .model(model != null ? model : "gemini-3-flash-preview")
                .maxTokens(150)
                .temperature(0.8)
                .build();
            
            Prompt prompt = new Prompt(new UserMessage(input), options);
            
            ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
            
            return response.getResult().getOutput().getText();
        } catch (Exception e) {
            logger.error("Error sending message to AI service", e);
            return "Sorry, I encountered an error: " + e.getMessage();
        }
    }
}