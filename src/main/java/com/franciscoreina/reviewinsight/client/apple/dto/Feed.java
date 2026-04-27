package com.franciscoreina.reviewinsight.client.apple.dto;

import java.util.List;

public record Feed(
        List<AppleReviewDTO> entry
) {
}