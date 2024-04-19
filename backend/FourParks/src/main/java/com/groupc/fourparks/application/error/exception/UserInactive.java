package com.groupc.fourparks.application.error.exception;

public class UserInactive extends RuntimeException{
    public UserInactive(String message) {
        super(message);
    }
}
