package com.microservice.user.exceptions.advice;

import com.microservice.user.common.ResponseBody;
import com.microservice.user.common.ResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class NoSuchElementExceptionAdvice {

    @org.springframework.web.bind.annotation.ResponseBody
    @ExceptionHandler(NoSuchElementException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseBody handle() {
        return new ResponseBody(ResponseStatus.DATA_NOT_FOUND);
    }
}
