package com.n26.service.impl;

import com.n26.controller.TransactionController;
import com.n26.dao.StatisticsRepository;
import com.n26.model.Statistics;
import com.n26.model.request.TransactionDto;
import com.n26.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    public static int TRANSACTION_PERIOD = 60000;

    @Autowired
    private StatisticsRepository statisticsRepository;

    @Override
    public void addTransaction(TransactionDto transaction) {
        logger.info("Add the given transaction [{}]" , transaction);
        if (LocalDateTime.now(ZoneId.of("UTC")).getSecond() - transaction.getTimestamp().getSecond() <= TRANSACTION_PERIOD) {
            Statistics statistics = new Statistics();
            BigDecimal amountinBigDec = new BigDecimal(transaction.getAmount());

            statistics.setAvg(amountinBigDec);
            statistics.setSum(amountinBigDec);
            statistics.setMax(amountinBigDec);
            statistics.setMin(amountinBigDec);
            statistics.setCount(Long.valueOf("1"));

            statisticsRepository.addTransaction(statistics);

        }
    }

}
