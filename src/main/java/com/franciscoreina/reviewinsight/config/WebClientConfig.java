package com.franciscoreina.reviewinsight.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(@Value("${openai.api-key}") String apiKey) {
        return WebClient.builder()
                .baseUrl("https://api.openai.com")
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    @ConfigurationProperties(prefix = "mocks")
    @Data
    @Component
    public static class MockProperties {

        private Apple apple;
        private OpenAi openai;

        @Data
        public static class Apple {
            private boolean enabled;
            private Map<Integer, String> reviews;
        }

        @Data
        public static class OpenAi {
            private boolean enabled;
            private Map<Integer, String> insights;
        }
    }
}
