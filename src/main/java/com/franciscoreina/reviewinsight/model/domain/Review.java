package com.franciscoreina.reviewinsight.model.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
public record Review(
        String author,
        int rating,
        String title,
        String content,
        int voteCount,
        LocalDateTime date,
        Sentiment sentiment
) {}
