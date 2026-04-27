package com.franciscoreina.reviewinsight.client.apple;

import com.franciscoreina.reviewinsight.mapper.ReviewMapper;
import com.franciscoreina.reviewinsight.model.domain.Review;
import com.franciscoreina.reviewinsight.client.apple.dto.AppleReviewDTO;
import com.franciscoreina.reviewinsight.client.apple.dto.AppleReviewResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class AppleRssClient {

    private final ObjectMapper objectMapper;

    private static final String RSS_URL =
            "https://itunes.apple.com/%s/rss/customerreviews/page=%d/id=%d/sortby=mostrecent/json";

    public List<Review> fetchReviews(int appId, String country, int pages) {
        log.info("Calling Apple RSS → appId={}, country={}, pages={}", appId, country, pages);

        List<Review> reviews = new ArrayList<>();
        for (int page = 1; page <= pages; page++) {
            String url = RSS_URL.formatted(country, page, appId);

            try (InputStream is = new URL(url).openStream()) {
                AppleReviewResponse response =
                        objectMapper.readValue(is, AppleReviewResponse.class);

                List<AppleReviewDTO> entries = response.feed().entry();
                // Apple add metadata in the first element → skip
                for (int i = 1; i < entries.size(); i++) {
                    reviews.add(ReviewMapper.map(entries.get(i)));
                }
            } catch (Exception e) {
                log.error("Error fetching Apple reviews (page={})", page, e);
                throw new RuntimeException("Error fetching Apple reviews", e);
            }
        }

        return reviews;
    }
}
