package com.shopmate.product.configuration;

import com.theokanning.openai.service.OpenAiService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class OpenAIConfig {

    @Bean
    public OpenAiService openAiService() {
        String apiKey = System.getenv("OPENAI_API_KEY");
        return new OpenAiService("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",
                Duration.ofMinutes(2));
    }
}

