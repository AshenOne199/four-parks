package com.groupc.fourparks.application.error.exception;

public class LoginAttempts extends RuntimeException{
    public LoginAttempts(String message) {
        super(message);
    }
}
