package com.repo.api.exceptions;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String message){
        super(message);
    }

    public CustomerNotFoundException(String message,Throwable cause){
        super(message,cause);
    }
}
