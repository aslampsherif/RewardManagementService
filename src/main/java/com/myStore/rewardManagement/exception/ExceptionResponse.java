package com.myStore.rewardManagement.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ExceptionResponse {
    private String message;
    private String exceptionClass;
    private HttpStatus httpStatus;
    private Timestamp timestamp;
}