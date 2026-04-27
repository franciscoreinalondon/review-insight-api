package com.franciscoreina.reviewinsight.client.apple;

import com.franciscoreina.reviewinsight.client.ReviewClient;
import com.franciscoreina.reviewinsight.config.WebClientConfig;
import com.franciscoreina.reviewinsight.model.domain.Review;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppleReviewClient implements ReviewClient {

    private final AppleMockLoader appleMockLoader;
    private final AppleRssClient appleRssClient;
    private final WebClientConfig.MockProperties mockProperties;

    @Override
    public List<Review> fetchReviews(int appId, String country, int pages) {
        boolean useMock = mockProperties.getApple().isEnabled();

        log.info("Fetching Apple reviews → appId={}, country={}, pages={}, mock={}",
                appId, country, pages, useMock);
        if (useMock) {
            log.info("Using MOCK Apple reviews for appId={}", appId);
            return appleMockLoader.loadReviews(appId);
        }

        log.info("Calling Apple RSS for appId={}", appId);
        return appleRssClient.fetchReviews(appId, country, pages);
    }
}
