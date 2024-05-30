package com.microservice.user.exceptions;

public class RegisterInformationConstraintViolateException extends RuntimeException {

    public RegisterInformationConstraintViolateException(String message) {
        super(message);
    }

}
