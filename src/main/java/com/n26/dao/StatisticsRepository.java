package com.n26.dao;

import com.n26.model.request.TransactionDto;
import com.n26.model.response.StatisticsResponse;

public interface StatisticsRepository {

    void addTransaction(TransactionDto transaction);
}
