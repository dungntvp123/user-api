package com.microservice.user.utils;

import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ValidationUtils {
    public <T> List<String> getViolationMessage(T data) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(data);

        return violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }
}
