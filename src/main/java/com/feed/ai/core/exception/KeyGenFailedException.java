package com.feed.ai.core.exception;

public class KeyGenFailedException extends RuntimeException{
    public KeyGenFailedException(String message){
        super(message);
    }
    public KeyGenFailedException(String message, Throwable e){
        super(message, e);
    }
}
