package com.n26.service;

import com.n26.model.request.TransactionDto;

public interface TransactionService {

    void addTransaction(TransactionDto transaction);

}
