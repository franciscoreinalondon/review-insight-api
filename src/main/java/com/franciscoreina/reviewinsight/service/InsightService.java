package com.franciscoreina.reviewinsight.service;

import com.franciscoreina.reviewinsight.model.domain.Insight;
import com.franciscoreina.reviewinsight.model.domain.Review;

import java.util.List;

public interface InsightService {

    Insight extractInsights(List<Review> reviews, int appId);

}
