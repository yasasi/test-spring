package com.n26.controller;

import com.n26.exception.TransactionException;
import com.n26.model.Statistics;
import com.n26.model.request.TransactionDto;
import com.n26.service.TransactionService;
import com.n26.validation.CreateTransactionValidations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    public List<String> nameList;

    @Autowired
    public ConcurrentHashMap<String, TransactionDto> transactionList;

    @Autowired
    public Statistics statistics;


    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Void> createTranscation(@Valid @RequestBody TransactionDto transaction) throws TransactionException {
        logger.info("Request received to create a Transaction [{}]", transaction);
        transactionService.addTransaction(transaction);
/*
        // validate request: return 400 422

        System.out.println(nameList);
        // check timestamp, if > 60s return 204

        // if < 60: save and return 201
        if (transaction.getAmount().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }*/
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
