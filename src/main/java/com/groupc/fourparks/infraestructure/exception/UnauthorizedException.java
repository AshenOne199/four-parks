package com.groupc.fourparks.infraestructure.exception;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String message){
        super(message);
    }

}
