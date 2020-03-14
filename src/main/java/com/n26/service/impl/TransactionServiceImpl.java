package com.n26.service.impl;

import com.n26.exception.TransactionException;
import com.n26.model.Transaction;
import com.n26.model.request.TransactionDto;
import com.n26.service.TransactionService;
import com.n26.validation.CreateTransactionValidations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    public Map<UUID, Transaction> transactionMap;

    @Override
    public void addTransaction(TransactionDto transactionDto) throws TransactionException {
        logger.info("Add the given transaction [{}]", transactionDto);

        CreateTransactionValidations.validateTransaction(transactionDto);

        Transaction transaction = new Transaction();
        BigDecimal amountInBigDec = new BigDecimal(transactionDto.getAmount());

        transaction.setAmount(amountInBigDec);
        transaction.setTimestamp(transactionDto.getTimestamp());

        transactionMap.put(UUID.randomUUID(), transaction);
    }

}
