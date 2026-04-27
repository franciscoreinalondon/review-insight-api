package com.franciscoreina.reviewinsight.client.apple;

import com.franciscoreina.reviewinsight.config.WebClientConfig;
import com.franciscoreina.reviewinsight.exception.MockLoadingException;
import com.franciscoreina.reviewinsight.mapper.ReviewMapper;
import com.franciscoreina.reviewinsight.model.domain.Review;
import com.franciscoreina.reviewinsight.client.apple.dto.AppleReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AppleMockLoader {

    private final ObjectMapper objectMapper;
    private final WebClientConfig.MockProperties mockProperties;

    public List<Review> loadReviews(Integer appId) {
        String path = mockProperties.getApple().getReviews().get(appId);

        if (path == null) {
            throw new IllegalArgumentException("No Apple mock defined for appId: " + appId);
        }

        try (InputStream is = getResource(path)) {
            AppleReviewResponse response =
                    objectMapper.readValue(is, AppleReviewResponse.class);
            return response.feed().entry().stream()
                    .skip(1) // Apple adds metadata in the first element
                    .map(ReviewMapper::map)
                    .toList();
        } catch (IOException e) {
            throw new MockLoadingException("Error loading Apple mock from: " + path, e);
        }
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
