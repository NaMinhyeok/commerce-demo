package com.nmh.commerce.controller;

import com.nmh.commerce.support.error.CustomException;
import com.nmh.commerce.support.error.ErrorType;
import com.nmh.commerce.support.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiControllerAdvice {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ApiResponse<?>> handleCoreException(CustomException e) {
        log.info("CustomException : {}", e.getMessage(), e);
        return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), HttpStatusCode.valueOf(e.getErrorType().getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Exception : {}", e.getMessage(), e);
        return new ResponseEntity<>(ApiResponse.error(ErrorType.DEFAULT_ERROR), HttpStatusCode.valueOf(ErrorType.DEFAULT_ERROR.getStatus()));
    }
}
