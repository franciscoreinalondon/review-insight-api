![Java](https://img.shields.io/badge/Java-21-blue?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0-brightgreen?logo=springboot)
![Apple](https://img.shields.io/badge/Apple%20RSS-Reviews-lightgrey?logo=apple)
![OpenAI](https://img.shields.io/badge/OpenAI-API-black)
![SaaS](https://img.shields.io/badge/SaaS-ready-success)

# Review Insight API (SaaS-ready)

A Spring Boot API designed as a SaaS-ready service to ingest mobile app reviews from Apple RSS and generate AI-powered
insights using OpenAI.

## Overview

This service:

- Fetches app reviews from Apple RSS
- Normalizes and processes review data
- Classifies sentiment (positive, neutral, negative)
- Uses OpenAI to extract insights:
    - Summary
    - Top problems (with examples)

## Tech Stack

- Java 21
- Spring Boot 4.0
- WebClient (HTTP client)
- OpenAI API

## Running the Application

### 1. Set environment variable (OpenAI API Key)

```
export OPENAI_API_KEY=your_key_here
```

### 2. Run the application

```
./mvnw spring-boot:run
```

The API will be available at http://localhost:8080

## Testing the API

```
POST http://localhost:8080/reviews/ingest
```

#### Request Body

```json
{
  "appId": "1052238659",
  "country": "gb",
  "pages": 2
}
```

A [Postman collection](https://github.com/franciscoreinalondon/review-insight-api/tree/main/postman)
is included in this repository.

## Mocking Strategy

The app supports mocking for both: `Apple RSS` and `OpenAI` responses, which can be enabled via configuration.

This allows:

- Fast local development
- No external API dependency
- Consistent test data for development and testing

## OpenAI Integration

- Builds a prompt using reviews
- Sends request via WebClient
- Parses structured response
- Extracts:
    - Summary
    - Top 3 problems with examples

## Project Status

This project is currently under active development.

#### Future improvements:

- Add unit & integration tests (JUnit + Mockito)
- Improve error handling (custom exceptions, global handler)
- Add retries & timeouts (Resilience4j)
- Logging improvements (structured logs)
- Multi-platform support (Google Play)
- Async processing (non-blocking)
