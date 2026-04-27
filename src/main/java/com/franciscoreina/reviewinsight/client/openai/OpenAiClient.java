package com.franciscoreina.reviewinsight.client.openai;

import com.franciscoreina.reviewinsight.model.domain.Insight;
import com.franciscoreina.reviewinsight.model.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OpenAiClient {

    private final WebClient openAiWebClient;
    private final ObjectMapper objectMapper;

    public Insight call(List<Review> reviews) {

        String prompt = buildPrompt(reviews);

        Map<String, Object> request = Map.of(
                "model", "gpt-4.1-mini",
                "input", List.of(Map.of(
                        "role", "user",
                        "content", prompt
                ))
        );

        String response = openAiWebClient.post()
                .uri("/v1/responses")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return parseResponse(response);
    }

    private String buildPrompt(List<Review> reviews) {

        String reviewsText = reviews.stream()
                .map(r -> "- " + r.content())
                .reduce("", (a, b) -> a + "\n" + b);
        return """
        You are analyzing app reviews.

        TASK:
        Identify the top 3 most common problems mentioned by users.

        RULES:
        - Group similar issues together (e.g. login issues, crashes, support problems)
        - Count approximate occurrences
        - Be concise
        - Ignore positive feedback
        - Focus only on problems

        OUTPUT FORMAT (STRICT):
        Return ONLY a valid JSON. No explanations, no extra text.

        JSON schema:
        {
          "summary": "string",
          "topProblems": [
            {
              "name": "string",
              "count": number,
              "examples": ["string", "string"]
            }
          ]
        }

        REQUIREMENTS:
        - summary must be a short readable paragraph
        - topProblems must contain EXACTLY 3 items
        - count must be an integer
        - examples must contain 2-3 short phrases from reviews
        - DO NOT include markdown
        - DO NOT include backticks
        - DO NOT include any text outside JSON

        REVIEWS:
        %s
        """.formatted(reviewsText);
    }

    private Insight parseResponse(String response) {

        try {
            JsonNode root = objectMapper.readTree(response);

            String text = root
                    .path("output")
                    .get(0)
                    .path("content")
                    .get(0)
                    .path("text")
                    .asText();

            return objectMapper.readValue(text, Insight.class);

        } catch (Exception e) {
            throw new RuntimeException("Error parsing OpenAI response", e);
        }
    }
}
