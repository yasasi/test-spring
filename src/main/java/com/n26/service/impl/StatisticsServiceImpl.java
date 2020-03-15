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
    public static final long TRANSACTION_PERIOD = 60L;
    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);

    @Autowired
    public Map<UUID, Transaction> transactionMap;

    public StatisticsResponse getStatistics() {
        StatisticsResponse response = new StatisticsResponse();
        transactionMap.entrySet().stream()
                .filter(trans ->
                        trans.getValue().getTimestamp().
                                isAfter(LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(TRANSACTION_PERIOD)))
                .forEach(trans -> {
                    BigDecimal sum = response.getSum() !=null ? new BigDecimal(response.getSum()) : BigDecimal.ZERO;
                    Long count = response.getCount() !=null ? Long.parseLong(response.getCount()) : 0L;
                    response.setSum((sum.add(trans.getValue().getAmount()).setScale(2, RoundingMode.HALF_UP)).toString());
                    response.setCount(Long.toString(count+1L));

                    BigDecimal min = response.getMin() !=null ? new BigDecimal(response.getMin()).min(trans.getValue().getAmount()) : trans.getValue().getAmount();
                    BigDecimal max = response.getMax() !=null ? new BigDecimal(response.getMax()).max(trans.getValue().getAmount()) : trans.getValue().getAmount();
                    response.setMin((min.setScale(2, RoundingMode.HALF_UP).toString()));
                    response.setMax((max.setScale(2, RoundingMode.HALF_UP)).toString());
                });

        if(response.getCount() != null && response.getSum() !=null && Long.parseLong(response.getCount()) > 0L) {
            response.setAvg(new BigDecimal(response.getSum()).divide(new BigDecimal(response.getCount()), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP).toString());
        }
        return response;
    }

    @Scheduled(fixedDelay=5000)
    public void refreshTransactions() {
        System.out.println("Refreshing transaction list...");
        transactionMap.entrySet().stream().forEach(t-> {
            System.out.println("Refreshing transaction list...");
            if( LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(TRANSACTION_PERIOD).isAfter(t.getValue().getTimestamp())){
                transactionMap.remove(t.getKey());
                System.out.println("remove expired transaction" + t.getKey());
            }
        });
    }


}
