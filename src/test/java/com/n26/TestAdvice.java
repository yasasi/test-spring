package com.n26;

import com.n26.exception.TransactionException;
import com.n26.model.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@ControllerAdvice
public class TestAdvice {

	@ExceptionHandler(TransactionException.class)
	public ResponseEntity exceptionHandler(TransactionException e) {
		return new ResponseEntity<>(e.getStatus());
	}
}
