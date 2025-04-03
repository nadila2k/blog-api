package com.nadila.blogapi.exception;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String userNotFound) {
        super(userNotFound);
    }
}
