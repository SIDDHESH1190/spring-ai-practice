package com.ai.practice.service;

import com.ai.practice.model.ChatRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class HuggingFaceService {

    @Value("${huggingface.api.key}")
    private String apiKey;

    private final WebClient webClient;

    public HuggingFaceService(WebClient webClient) {
        this.webClient = webClient;
    }

    public String queryModel(ChatRequest chatRequest) {

        String model = chatRequest.getModel();
        String url = "https://api.inference.huggingface.co/models/" + model;

        String input = chatRequest.getInput() == null ? "" : chatRequest.getInput();

        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .headers(headers -> {
                    if (apiKey != null && !apiKey.isEmpty()) {
                        headers.setBearerAuth(apiKey);
                    }
                })
                .bodyValue(Map.of("inputs", input))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
