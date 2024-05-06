package com.groupc.fourparks.infraestructure.exception;

public class InternalServerErrorException extends RuntimeException{
    public InternalServerErrorException(String message){
        super(message);
    }
}
