package com.nadila.blogapi.exception;

public class AlreadyExistsException extends RuntimeException{
    public AlreadyExistsException(String userAlreadyExists) {
        super(userAlreadyExists);
    }
}
