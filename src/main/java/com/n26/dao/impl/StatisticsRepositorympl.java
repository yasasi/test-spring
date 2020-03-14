package com.n26.dao.impl;

import com.n26.dao.StatisticsRepository;
import com.n26.model.Statistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class StatisticsRepositorympl implements StatisticsRepository {

    private Map<LocalDateTime, Statistics> statisticsMap = new ConcurrentHashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(StatisticsRepositorympl.class);


    @Override
    public void addTransaction(Statistics statistics) {
        statisticsMap.put(statistics.getTimestamp(), statistics);
        logger.info("Statistic map {}", statisticsMap.get(statistics.getTimestamp()));
    }

    @Override
    public Map<LocalDateTime, Statistics> getTransactions() {
        return statisticsMap;
    }
}
