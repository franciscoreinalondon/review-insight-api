package com.franciscoreina.reviewinsight.client;

import com.franciscoreina.reviewinsight.model.domain.Review;

import java.util.List;

public interface ReviewClient {

    List<Review> fetchReviews(int appId, String country, int pages);

}
