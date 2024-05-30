package com.microservice.user.exceptions.advice;

import ch.qos.logback.core.LogbackException;
import com.microservice.user.common.ResponseBody;
import com.microservice.user.common.ResponseStatus;
import com.microservice.user.exceptions.AccountVerifyTokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LockedExceptionAdvice {
    @org.springframework.web.bind.annotation.ResponseBody
    @ExceptionHandler(LockedException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseBody handle() {
        return new ResponseBody(ResponseStatus.ACCOUNT_HAS_BEEN_LOCKED);
    }
}
