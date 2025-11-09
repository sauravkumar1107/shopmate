package com.shopmate.product.service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopmate.product.model.InvocationRequest;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EndpointSelectorService {

    private final OpenAiService openAiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public EndpointSelectorService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    public InvocationRequest selectEndpointWithParams(String userQuery, List<String> apiOptions) {
       /* StringBuilder prompt = new StringBuilder();
        prompt.append("You are an API router.\n")
                .append("I will give you a natural language request and a list of API endpoints.\n")
                .append("Your job:\n")
                .append("1. Choose the correct endpoint.\n")
                .append("2. Extract parameter values from the request.\n")
                .append("3. Respond only in JSON with the endpoint and params.\n\n")
                .append("User: ").append(userQuery).append("\n")
                .append("Options:\n");*/
        StringBuilder prompt = new StringBuilder();
        prompt.append("""
            You are an intelligent API router.

            TASK:
            - Given a user's natural language request and a list of API endpoints,
              select the most appropriate endpoint.
            - Extract all relevant parameter values from the user's request.
            - Return ONLY a JSON object in this **exact structure** (nothing else, no explanations):

            ```json
            {
                "endpoint": "GET/http://localhost:8080/api/product/{id}",
                "params": {
                    "id": "P67890"
                }
            }
            ```

            Rules:
            - Do not include any text outside of the JSON.
            - Always include both "endpoint" and "params" keys, even if params is empty.
            - Keep endpoint exactly as provided in the options list.
            """);

        prompt.append("\nUser Request: ").append(userQuery).append("\n")
                .append("Available API Options:\n");

        for (int i = 0; i < apiOptions.size(); i++) {
            prompt.append(i + 1).append(". ").append(apiOptions.get(i)).append("\n");
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
            return objectMapper.readValue(cleaned, InvocationRequest.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid JSON from LLM: " + response, e);
        }
    }
}

