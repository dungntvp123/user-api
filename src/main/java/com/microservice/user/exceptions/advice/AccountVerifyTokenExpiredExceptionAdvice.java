package com.microservice.user.exceptions.advice;

import com.microservice.user.common.ResponseBody;
import com.microservice.user.common.ResponseStatus;
import com.microservice.user.exceptions.AccountVerifyTokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountVerifyTokenExpiredExceptionAdvice {
    @org.springframework.web.bind.annotation.ResponseBody
    @ExceptionHandler(AccountVerifyTokenExpiredException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseBody handle() {
        return new ResponseBody(ResponseStatus.ACCOUNT_VERIFICATION_EXPIRED);
    }
}
