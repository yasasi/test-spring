package com.n26.controller;

import com.n26.exception.TransactionException;
import com.n26.model.request.TransactionDto;
import com.n26.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionDto transaction) throws TransactionException {
        logger.info("Request received to create a Transaction [{}]", transaction);
        transactionService.addTransaction(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTransactions() {
        logger.info("Delete all the transactions");
        transactionService.deleteTransactions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
