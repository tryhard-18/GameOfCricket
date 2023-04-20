package com.tekion.gameofcricket.error;

public class ValidationException extends RuntimeException{
    public ValidationException(String message){
        super(message);
    }
}
