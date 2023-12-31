package com.kush.banbah.soloprojectbackend.controller;

import com.kush.banbah.soloprojectbackend.exceptions.EntityDoesNotBelongException;
import com.kush.banbah.soloprojectbackend.exceptions.EntityNotFoundException;
import com.kush.banbah.soloprojectbackend.exceptions.InvalidRequestException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.IOException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        return ResponseEntity
                .status(400)
                .body(ex.getBindingResult().getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" ")));

    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(Exception e) {

        return ResponseEntity
                .status(400)
                .body(e.getMessage());
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
    public ResponseEntity<Object> handleAuthenticationException(Exception e) {

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

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(Exception ex) {
        return ResponseEntity
                .status(413)
                .body("File is too large!");
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(Exception ex) {
        return ResponseEntity
                .status(500)
                .body("Error when accessing file system!");
    }


}
