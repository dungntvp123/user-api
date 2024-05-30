package com.microservice.user.exceptions;

public class ResetPasswordInformationConstraintViolateException extends RuntimeException {
    public ResetPasswordInformationConstraintViolateException(String message) {
        super(message);
    }
}
