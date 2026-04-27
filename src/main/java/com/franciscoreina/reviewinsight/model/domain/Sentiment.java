package com.franciscoreina.reviewinsight.model.domain;

public enum Sentiment {
    POSITIVE,
    NEGATIVE,
    NEUTRAL;

    public static Sentiment fromRating(int rating) {
        if (rating >= 4) return POSITIVE;
        if (rating <= 2) return NEGATIVE;
        return NEUTRAL;
    }
}
