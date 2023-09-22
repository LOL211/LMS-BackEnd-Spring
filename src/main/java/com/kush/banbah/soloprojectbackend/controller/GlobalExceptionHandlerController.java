package com.kush.banbah.soloprojectbackend.controller;

import com.kush.banbah.soloprojectbackend.exceptions.EntityDoesNotBelongException;
import com.kush.banbah.soloprojectbackend.exceptions.EntityNotFoundException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidTestNameException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        Map<String, Object> body = new HashMap<>();
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(val -> errors.add(val.getDefaultMessage()));
        body.put("errors", errors);

        return ResponseEntity
                .status(400)
                .body(body);

    }

    @ExceptionHandler(InvalidTestNameException.class)
    public ResponseEntity<Object> handleInvalidTestNameException(InvalidTestNameException ex) {

        return ResponseEntity
                .status(400)
                .body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {

        String errorMessage = ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(400)
                .body(errorMessage);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {

        return ResponseEntity
                .status(403)
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFoundExceptions(Exception ex) {
        return ResponseEntity
                .status(404)
                .body(ex.getMessage());
    }

    @ExceptionHandler(EntityDoesNotBelongException.class)
    public ResponseEntity<String> handleForbiddenExceptions(Exception ex) {
        return ResponseEntity
                .status(403)
                .body(ex.getMessage());
    }


}
