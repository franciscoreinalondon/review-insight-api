package com.franciscoreina.reviewinsight.client.apple.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Getter
public class AppleReviewDTO {

    private Author author;
    private Updated updated;
    @JsonProperty("im:rating")
    private Rating rating;
    private Title title;
    private Content content;
    @JsonProperty("im:voteCount")
    private VoteCount voteCount;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Author {
        private Label name;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Updated {
        private String label;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Rating {
        @JsonProperty("label")
        private String value;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Title {
        private String label;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {
        private String label;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VoteCount {
        @JsonProperty("label")
        private String value;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Label {
        private String label;
    }
}
