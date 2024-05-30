package com.microservice.user.exceptions.advice;

import com.microservice.user.common.ResponseBody;
import com.microservice.user.common.ResponseStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.user.exceptions.RegisterInformationConstraintViolateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class RegisterInformationConstraintViolateExceptionAdvice {

    @org.springframework.web.bind.annotation.ResponseBody
    @ExceptionHandler(RegisterInformationConstraintViolateException.class)
    @org.springframework.web.bind.annotation.ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBody handle(RegisterInformationConstraintViolateException exception) {
        try {
            List<String> violations = (new ObjectMapper()).readValue(exception.getMessage(), List.class);
            return new ResponseBody(ResponseStatus.REGISTRATION_INFORMATION_CONSTRAINT_VIOLATE, violations);
        } catch (JsonProcessingException ignored) {

        }
        return null;
    }
}
