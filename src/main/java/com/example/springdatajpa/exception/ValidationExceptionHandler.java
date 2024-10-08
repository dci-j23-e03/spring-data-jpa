package com.example.springdatajpa.exception;

import com.example.springdatajpa.exception.response.ValidationErrorResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Iterator;
import java.util.Set;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ValidationErrorResponse errorResponse = new ValidationErrorResponse();
        violations.forEach(violation -> {
            String message = violation.getMessage();
            String field = "";
            Iterator<Path.Node> iterator = violation.getPropertyPath().iterator();
            while (iterator.hasNext()) {
                field = iterator.next().toString();
            }
            errorResponse.getErrors().add(new ValidationErrorResponse.SingleValidationError(field, message));
        });
        return new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // we can have multiple handlers for multiple exception types, and also be returning different responses
//    @ExceptionHandler(DepartmentNotFound.class)
//    public ResponseEntity<ErrorDto> handleDepartmentNotFound...
}
