package com.microservice.user.exceptions.advice;


import com.microservice.user.common.ResponseBody;
import com.microservice.user.common.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DisabledExceptionAdvice {

    @org.springframework.web.bind.annotation.ResponseBody
    @ExceptionHandler(DisabledException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseBody handle() {
        return new ResponseBody(ResponseStatus.DISABLED_ACCOUNT);
    }
}
