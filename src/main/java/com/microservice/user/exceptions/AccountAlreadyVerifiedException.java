package com.microservice.user.exceptions;

public class AccountAlreadyVerifiedException extends RuntimeException{
    public AccountAlreadyVerifiedException(String message) {
        super(message);
    }
    public AccountAlreadyVerifiedException() {
        super();
    }
}
