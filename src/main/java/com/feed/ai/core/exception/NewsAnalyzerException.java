package com.feed.ai.core.exception;

public class NewsAnalyzerException extends RuntimeException {
    public NewsAnalyzerException(String message) {
        super(message);
    }

    public NewsAnalyzerException(String message, Throwable e) {
        super(message, e);
    }
}
