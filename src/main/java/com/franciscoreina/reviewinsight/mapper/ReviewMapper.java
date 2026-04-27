package com.franciscoreina.reviewinsight.mapper;

import com.franciscoreina.reviewinsight.client.apple.dto.AppleReviewDTO;
import com.franciscoreina.reviewinsight.model.domain.Review;
import com.franciscoreina.reviewinsight.model.domain.Sentiment;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public final class ReviewMapper {

    private ReviewMapper() {
    }

    public static Review map(AppleReviewDTO dto) {
        var rating = Integer.parseInt(dto.getRating().getValue());
        var voteCount = Integer.parseInt(dto.getVoteCount().getValue());

        return new Review(
                dto.getAuthor().getName().getLabel(),
                rating,
                dto.getTitle().getLabel(),
                dto.getContent().getLabel(),
                voteCount,
                parseDate(dto.getUpdated().getLabel()),
                Sentiment.fromRating(rating)
        );
    }

    private static LocalDateTime parseDate(String date) {
        return OffsetDateTime.parse(date).toLocalDateTime();
    }
}
