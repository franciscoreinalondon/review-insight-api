package com.franciscoreina.reviewinsight.client.openai;

import com.franciscoreina.reviewinsight.config.WebClientConfig;
import com.franciscoreina.reviewinsight.exception.MockLoadingException;
import com.franciscoreina.reviewinsight.model.domain.Insight;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class OpenAiMockLoader {

    private final ObjectMapper objectMapper;
    private final WebClientConfig.MockProperties mockProperties;

    public Insight loadInsights(int appId) {
        String path = getPath(appId);

        try (InputStream is = getResource(path)) {
            return objectMapper.readValue(is, Insight.class);
        } catch (Exception e) {
            throw new RuntimeException("Error loading OpenAI mock", e);
        }
    }

    private String getPath(int appId) {
        String path = mockProperties.getOpenai().getInsights().get(appId);

        if (path == null) {
            throw new IllegalArgumentException("No OpenAI mock configured for appId: " + appId);
        }

        return path;
    }

    private InputStream getResource(String path) {
        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(path);

        if (is == null) {
            throw new MockLoadingException("Mock file not found: " + path);
        }

        return is;
    }
}
