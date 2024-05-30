package com.microservice.user.exceptions.advice;


import com.microservice.user.common.ResponseBody;
import com.microservice.user.common.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class BadCredentialsExceptionAdvice {
    @org.springframework.web.bind.annotation.ResponseBody
    @ExceptionHandler(BadCredentialsException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBody handle() {
        return new ResponseBody(ResponseStatus.INCORRECT_AUTHENTICATION_INFORMATION);
    }
}
