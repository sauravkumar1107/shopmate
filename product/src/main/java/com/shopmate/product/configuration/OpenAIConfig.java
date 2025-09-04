package com.shopmate.product.configuration;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfig {

    @Bean
    public OpenAiService openAiService() {
        String apiKey = System.getenv("OPENAI_API_KEY");
        return new OpenAiService(apiKey);
    }
}

