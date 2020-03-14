package com.n26.controller;

import com.n26.model.Statistics;
import com.n26.model.request.TransactionDto;
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


    @PostMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void createTranscation(@Valid @RequestBody TransactionDto transaction) {
        logger.info("Request received to create a Transaction [{}]", transaction);

        // validate request: return 400 422

        System.out.println(nameList);
        // check timestamp, if > 60s return 204

        // if < 60: save and return 201
    }

}
