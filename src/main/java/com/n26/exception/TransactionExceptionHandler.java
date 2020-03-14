package com.n26.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class TransactionExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionExceptionHandler.class);

    @ExceptionHandler(TransactionException.class)
    @ResponseBody
    public ResponseEntity handleException(TransactionException ex) {
        LOGGER.error("Error while handling request, ERROR: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getStatus());
    }

}
