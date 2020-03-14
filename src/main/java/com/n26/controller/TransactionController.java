package com.n26.controller;

import com.n26.model.request.TransactionDto;
import com.n26.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void createTranscation(@Valid @RequestBody TransactionDto transaction) {
        logger.info("Request received to create a Transaction [{}]", transaction);
        transactionService.addTransaction(transaction);
    }

}
