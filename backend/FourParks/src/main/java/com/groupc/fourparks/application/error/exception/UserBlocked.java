package com.groupc.fourparks.application.error.exception;

public class UserBlocked extends RuntimeException{
    public UserBlocked(String message) {
        super(message);
    }
}
