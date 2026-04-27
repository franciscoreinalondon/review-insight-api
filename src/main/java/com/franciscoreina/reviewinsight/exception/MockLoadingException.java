package com.franciscoreina.reviewinsight.exception;

public class MockLoadingException extends RuntimeException {

    public MockLoadingException(String message) {
        super(message);
    }

    public MockLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

}
