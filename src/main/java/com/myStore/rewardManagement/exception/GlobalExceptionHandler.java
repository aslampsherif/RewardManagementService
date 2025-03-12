package com.myStore.rewardManagement.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ExceptionResponse> handleServiceException(ServiceException ex) {
        log.error(ex.getMessage());
        ExceptionResponse response = ExceptionResponse.builder()
                .message(ex.getMessage())
                .exceptionClass(ex.getClass().getSimpleName())
                .httpStatus(ex.httpStatus)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(ex.httpStatus).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        log.error(ex.getMessage());
        ExceptionResponse response = ExceptionResponse.builder()
                .message(ex.getMessage())
                .exceptionClass(ex.getClass().getSimpleName())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<ExceptionResponse> handleInvalidRequest(Exception ex) {
        log.error(ex.getMessage());
        ExceptionResponse response = ExceptionResponse.builder()
                .message(ex.getMessage())
                .exceptionClass(ex.getClass().getSimpleName())
                .httpStatus(HttpStatus.BAD_REQUEST)
                .timestamp(Timestamp.valueOf(LocalDateTime.now()))
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}