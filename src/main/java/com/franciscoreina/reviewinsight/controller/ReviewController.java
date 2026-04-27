package com.franciscoreina.reviewinsight.controller;

import com.franciscoreina.reviewinsight.model.dto.IngestionRequest;
import com.franciscoreina.reviewinsight.model.dto.IngestionResponse;
import com.franciscoreina.reviewinsight.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/ingest")
    public ResponseEntity<IngestionResponse> ingest(@Valid @RequestBody IngestionRequest request) {
        return ResponseEntity.ok(reviewService.ingestReviews(request));
    }
}
