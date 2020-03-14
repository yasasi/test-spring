package com.n26.service.impl;

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
    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    public static final long TRANSACTION_PERIOD = 6000L;

    @Autowired
    public Map<UUID, Transaction> transactionMap;

    public StatisticsResponse getStatistics() {
        StatisticsResponse response = new StatisticsResponse();
        transactionMap.entrySet().stream()
                .filter(trans ->
                        LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(TRANSACTION_PERIOD).
                                isBefore(trans.getValue().getTimestamp()))
                .forEach(trans -> {
                    BigDecimal sum = response.getSum() !=null ? response.getSum() : BigDecimal.ZERO;
                    Long count = response.getCount() !=null ? response.getCount() : 0L;
                    response.setSum(sum.add(trans.getValue().getAmount()).setScale(2, RoundingMode.HALF_UP));
                    response.setCount(count+1L);

                    BigDecimal min = response.getMin() !=null ? response.getMin().min(trans.getValue().getAmount()) : trans.getValue().getAmount();
                    BigDecimal max = response.getMax() !=null ? response.getMax().max(trans.getValue().getAmount()) : trans.getValue().getAmount();
                    response.setMin(min.setScale(2, RoundingMode.HALF_UP));
                    response.setMax(max.setScale(2, RoundingMode.HALF_UP));
                });

        if(response.getCount() != null && response.getSum() !=null && response.getCount() > 0L) {
            response.setAvg(response.getSum().divide(BigDecimal.valueOf(response.getCount()), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
        }
        return response;
    }

    @Scheduled(fixedDelay=5000)
    public void refreshTransactions() {
        System.out.println("Refreshing transaction list...");
        transactionMap.entrySet().stream().forEach(tranKey-> {
            System.out.println("Refreshing transaction list...");
            if(LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(TRANSACTION_PERIOD).isBefore(transactionMap.get(tranKey).getTimestamp())){
                transactionMap.remove(tranKey);
                System.out.println("remove expired transaction" + tranKey);
            }
        });
    }


}
