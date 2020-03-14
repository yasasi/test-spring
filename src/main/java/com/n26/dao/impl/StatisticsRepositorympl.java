package com.n26.dao.impl;

import com.n26.dao.StatisticsRepository;
import com.n26.model.Statistics;
import com.n26.model.request.TransactionDto;
import com.n26.service.StatisticsService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StatisticsRepositorympl implements StatisticsRepository {

    private static final int SUMMARY_WINDOW = 60000;
    private Map<Integer, Statistics> statisticsMap = new ConcurrentHashMap<>();

    @Override
    public void addTransaction(TransactionDto transaction) {
    }
}
