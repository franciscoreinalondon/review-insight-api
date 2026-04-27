package com.franciscoreina.reviewinsight.model.dto;

import com.franciscoreina.reviewinsight.model.domain.Insight;
import com.franciscoreina.reviewinsight.model.domain.Review;
import lombok.Builder;

import java.util.List;

@Builder
public record IngestionResponse(
        List<Review> reviews,
        int totalReviews,
        int positive,
        int negative,
        int neutral,
        Insight insights
) {
}
