package com.users.yehor_ostroverkhov.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;

@RestController
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<String> handleInvalidBirthDate(DateTimeException birthDateException) {
        String errorMessage = birthDateException.getLocalizedMessage();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleInvalidEmailAddress() {
        String errorMessage = "Email address must be well-formed";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleInvalidRange() {
        String errorMessage = "Invalid range, 'fromDate' must be before 'toDate'";
        return new ResponseEntity<>(errorMessage, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException() {
        String errorMessage = "Entity with provided id not found";
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
