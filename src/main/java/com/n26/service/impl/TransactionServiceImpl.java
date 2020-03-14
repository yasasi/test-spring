package com.n26.service.impl;

import com.n26.dao.StatisticsRepository;
import com.n26.model.request.TransactionDto;
import com.n26.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public void addTransaction(TransactionDto transaction) {

        statisticsRepository.addTransaction(transaction);
    }

}
