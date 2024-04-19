package com.groupc.fourparks.application.error.exception;

public class UserNotExists extends RuntimeException{

    public UserNotExists(String message){
        super(message);
    }

}
