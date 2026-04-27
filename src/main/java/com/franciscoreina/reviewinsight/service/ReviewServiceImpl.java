package com.franciscoreina.reviewinsight.service;

import com.franciscoreina.reviewinsight.client.ReviewClient;
import com.franciscoreina.reviewinsight.model.domain.Insight;
import com.franciscoreina.reviewinsight.model.domain.Review;
import com.franciscoreina.reviewinsight.model.domain.Sentiment;
import com.franciscoreina.reviewinsight.model.dto.IngestionRequest;
import com.franciscoreina.reviewinsight.model.dto.IngestionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewClient reviewClient;
    private final InsightService insightService;

    @Override
    public IngestionResponse ingestReviews(IngestionRequest request) {

        var appId = request.appId();
        var country = request.country();
        var pages = request.pages();

        List<Review> reviews = reviewClient.fetchReviews(appId, country, pages)
                .stream()
                .sorted(Comparator.comparing(Review::date).reversed())
                .toList();

        var total = reviews.size();
        var positive = (int) reviews.stream().filter(r -> r.sentiment() == Sentiment.POSITIVE).count();
        var negative = (int) reviews.stream().filter(r -> r.sentiment() == Sentiment.NEGATIVE).count();
        var neutral = (int) reviews.stream().filter(r -> r.sentiment() == Sentiment.NEUTRAL).count();

        Insight insight = insightService.extractInsights(reviews, appId);

        return IngestionResponse.builder()
                .reviews(reviews)
                .totalReviews(total)
                .positive(positive)
                .negative(negative)
                .neutral(neutral)
                .insights(insight)
                .build();
    }
}
