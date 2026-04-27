package com.franciscoreina.reviewinsight.model.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record IngestionRequest(
        @NotNull(message = "appId is required")
        Integer appId,

        @NotNull(message = "country is required")
        String country,

        @NotNull(message = "pages is required")
        @Min(value = 1, message = "pages must be at least 1")
        @Max(value = 10, message = "pages cannot be greater than 10")
        Integer pages
) {
}
