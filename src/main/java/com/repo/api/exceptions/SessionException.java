package com.repo.api.exceptions;

public class SessionException extends  RuntimeException{

    public SessionException(String message){
        super(message);
    }

    public SessionException(String message,Throwable cause){
        super(message,cause);
    }
}
