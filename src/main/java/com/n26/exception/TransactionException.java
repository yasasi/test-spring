package com.n26.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class TransactionException extends Exception {

    private HttpStatus status;

    public TransactionException(HttpStatus status) {
        this.status = status;
    }

}