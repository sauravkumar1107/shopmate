package com.shopmate.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopmate.product.model.Product;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InternalQueryController {
    private final OpenAiService openAiService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @GetMapping("/chat")
    public String comparison(@NonNull final List<Product> productList) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("""
            You are an intelligent product comparator.

            TASK:
            - Given a list of products, create a summary of comparison with features and differences.
            - Create this in markdown format and return a string.
            """);

        prompt.append("\nProducts: ").append("\n");

        for (int i = 0; i < productList.size(); i++) {
            prompt.append(i + 1).append(". ").append(productList.get(i).toString()).append("\n");
        }

        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model("gpt-4o-mini")
                .messages(List.of(new ChatMessage("user", prompt.toString())))
                .temperature(0.0)
                .build();
        System.out.println("request:"+request);
        String response = openAiService.createChatCompletion(request)
                .getChoices()
                .get(0)
                .getMessage()
                .getContent()
                .trim();
        try {
            String cleaned = response
                    .replaceAll("(?s)```json", "")
                    .replaceAll("(?s)```", "")
                    .trim();
            System.out.println("cleaned response:"+cleaned);
            return cleaned;
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON from LLM: " + response, e);
        }
    }
}
