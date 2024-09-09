package com.feed.ai.news.exception;

public class MessageFailedException extends RuntimeException{
    public MessageFailedException(String message){
        super(message);
    }

    public MessageFailedException(String message, Throwable clause){
        super(message, clause);
    }
}
