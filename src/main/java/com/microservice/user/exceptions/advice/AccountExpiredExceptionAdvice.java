package com.microservice.user.exceptions.advice;

import com.microservice.user.common.ResponseBody;
import com.microservice.user.common.ResponseStatus;
import com.microservice.user.data.entity.ActionLog;
import com.microservice.user.service.ActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AccountExpiredExceptionAdvice {
    @Autowired
    private ActionLogService actionLogService;

    @org.springframework.web.bind.annotation.ResponseBody
    @ExceptionHandler(AccountExpiredException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseBody handle() {
        return new ResponseBody(ResponseStatus.ACCOUNT_HAS_BEEN_EXPIRED);
    }
}
