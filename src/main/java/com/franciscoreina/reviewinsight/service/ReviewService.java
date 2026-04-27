package com.franciscoreina.reviewinsight.service;

import com.franciscoreina.reviewinsight.model.dto.IngestionRequest;
import com.franciscoreina.reviewinsight.model.dto.IngestionResponse;

public interface ReviewService {

    IngestionResponse ingestReviews(IngestionRequest request);

}
