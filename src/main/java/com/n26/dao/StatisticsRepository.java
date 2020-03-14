package com.n26.dao;

import com.n26.model.Statistics;
import com.n26.model.request.TransactionDto;
import com.n26.model.response.StatisticsResponse;

import java.time.LocalDateTime;
import java.util.Map;

public interface StatisticsRepository {

    void addTransaction(Statistics statistics);
    Map<LocalDateTime, Statistics> getTransactions();
}
