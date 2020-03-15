package com.n26.service.impl;

import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.model.response.StatisticsResponse;
import com.n26.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    public static final long TRANSACTION_PERIOD = 60L;

    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Autowired
    public Map<UUID, Transaction> transactionMap;

    public StatisticsResponse getStatistics() {
        Statistics statistics = new Statistics();
        transactionMap.entrySet().stream()
                .filter(trans ->
                        trans.getValue().getTimestamp().
                                isAfter(LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(TRANSACTION_PERIOD)))
                .forEach(trans -> {
                    BigDecimal sum = statistics.getSum() !=null ? statistics.getSum() : BigDecimal.ZERO;
                    Long count = statistics.getCount() !=null ? statistics.getCount() : 0L;
                    statistics.setSum(sum.add(trans.getValue().getAmount()).setScale(2, RoundingMode.HALF_UP));
                    statistics.setCount(count+1L);

                    BigDecimal min = statistics.getMin() !=null && statistics.getMin().compareTo(BigDecimal.ZERO) >=1 ?
                            statistics.getMin().min(trans.getValue().getAmount()) : trans.getValue().getAmount();
                    BigDecimal max = statistics.getMax() !=null ? statistics.getMax().max(trans.getValue().getAmount()) : trans.getValue().getAmount();
                    statistics.setMin(min.setScale(2, RoundingMode.HALF_UP));
                    statistics.setMax(max.setScale(2, RoundingMode.HALF_UP));
                });

        if(statistics.getCount() != null && statistics.getSum() !=null && statistics.getCount() > 0L) {
            statistics.setAvg(statistics.getSum().divide(BigDecimal.valueOf(statistics.getCount()), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
        }
        StatisticsResponse response = new StatisticsResponse();
        response.setMax(statistics.getMax());
        response.setMin(statistics.getMin());
        response.setSum(statistics.getSum());
        response.setAvg(statistics.getAvg());
        response.setCount(statistics.getCount());
        return response;
    }

    @Scheduled(fixedDelay=5000)
    public void refreshTransactions() {
        logger.info("Refreshing the transactions...");
        transactionMap.entrySet().stream().forEach(t-> {
            logger.info("Refreshing transaction list...");
            if( LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(TRANSACTION_PERIOD).isAfter(t.getValue().getTimestamp())){
                transactionMap.remove(t.getKey());
                logger.info("remove expired transaction" + t.getKey());
            }
        });
    }


}
