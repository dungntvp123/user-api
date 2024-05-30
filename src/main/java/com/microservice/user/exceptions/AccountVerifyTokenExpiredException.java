package com.microservice.user.exceptions;

public class AccountVerifyTokenExpiredException extends RuntimeException {
    public AccountVerifyTokenExpiredException(String message) {
        super(message);
    }

    public AccountVerifyTokenExpiredException() {
        super();
    }
}
