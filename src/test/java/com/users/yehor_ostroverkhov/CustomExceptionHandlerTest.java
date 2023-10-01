package com.users.yehor_ostroverkhov;

import com.users.yehor_ostroverkhov.controller.CustomExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomExceptionHandlerTest {

    private final CustomExceptionHandler customExceptionHandler = new CustomExceptionHandler();

    @Test
    void testHandleInvalidBirthDate() {
        DateTimeException exception = new DateTimeException("Invalid date");
        ResponseEntity<String> responseEntity = customExceptionHandler.handleInvalidBirthDate(exception);

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
        assertEquals("Invalid date", responseEntity.getBody());
    }

    @Test
    void testHandleInvalidEmailAddress() {
        ResponseEntity<String> responseEntity = customExceptionHandler.handleInvalidEmailAddress();

        assertEquals(HttpStatus.NOT_ACCEPTABLE, responseEntity.getStatusCode());
        assertEquals("Email address must be well-formed", responseEntity.getBody());
    }

    @Test
    void testHandleInvalidRange() {
        ResponseEntity<String> responseEntity = customExceptionHandler.handleInvalidRange();

        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertEquals("Invalid range, 'fromDate' must be before 'toDate'", responseEntity.getBody());
    }

    @Test
    void testHandleEntityNotFoundException() {
        ResponseEntity<String> responseEntity = customExceptionHandler.handleEntityNotFoundException();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Entity with provided id not found", responseEntity.getBody());
    }
}