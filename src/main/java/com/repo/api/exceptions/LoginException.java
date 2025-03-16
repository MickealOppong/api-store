package com.repo.api.exceptions;

public class LoginException extends RuntimeException{

    public LoginException(String message){
        super(message);
    }

    public LoginException(String message,Throwable cause){
        super(message,cause);
    }
}
