package com.franciscoreina.reviewinsight.service;

import com.franciscoreina.reviewinsight.client.openai.OpenAiClient;
import com.franciscoreina.reviewinsight.config.WebClientConfig;
import com.franciscoreina.reviewinsight.client.openai.OpenAiMockLoader;
import com.franciscoreina.reviewinsight.model.domain.Insight;
import com.franciscoreina.reviewinsight.model.domain.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class InsightServiceImpl implements InsightService {

    private final OpenAiClient openAiClient;
    private final OpenAiMockLoader mockLoader;
    private final WebClientConfig.MockProperties mockProperties;

    @Override
    public Insight extractInsights(List<Review> reviews, int appId) {
        var useMock = mockProperties.getOpenai().isEnabled();

        log.info("Extracting insights for appId={} (mock={})", appId, useMock);
        if (useMock) {
            return mockLoader.loadInsights(appId);
        }

        return openAiClient.call(reviews);
    }
}
