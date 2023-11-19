package com.sis.sisassignment;

import com.sis.sisassignment.web.rest.exception.RequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This exception is thrown when a request's query parameter fails validation.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleExceptions(ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<String> handleExceptions(RequestException exception) {
        return ResponseEntity
                .status(exception.getCode())
                .body(exception.getMessage());
    }

}
