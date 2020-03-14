package com.n26.service.impl;

import com.n26.dao.StatisticsRepository;
import com.n26.model.Statistics;
import com.n26.model.request.TransactionDto;
import com.n26.model.response.StatisticsResponse;
import com.n26.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    public static int TRANSACTION_PERIOD = 60000;

    @Autowired
    StatisticsRepository statisticsRepository;

    @Autowired
    public Statistics statistics;

    public StatisticsResponse getStatistics() {
        Map<LocalDateTime, Statistics> statisticsMap = statisticsRepository.getTransactions();

        StatisticsResponse response = new StatisticsResponse();
        statisticsMap.entrySet().stream()
                .filter(stats ->
                        LocalDateTime.now(ZoneId.of("UTC")).getSecond() - stats.getKey().getSecond() < TRANSACTION_PERIOD)
                .forEach(s -> {
                    BigDecimal sum = response.getSum() !=null ? response.getSum() : BigDecimal.ZERO;
                    Long count = response.getCount() !=null ? response.getCount() : 0L;
                    response.setSum(sum.add(s.getValue().getSum()).setScale(2, RoundingMode.HALF_UP));
                    response.setCount(count+1L);

                    BigDecimal min = response.getMin() !=null ? response.getMin().min(s.getValue().getMin()) : s.getValue().getMin();
                    BigDecimal max = response.getMax() !=null ? response.getMax().max(s.getValue().getMax()) : s.getValue().getMax();
                    response.setMin(min.setScale(2, RoundingMode.HALF_UP));
                    response.setMax(max.setScale(2, RoundingMode.HALF_UP));
                });

        if(response.getCount() > 0L) {
            response.setAvg(response.getSum().divide(BigDecimal.valueOf(response.getCount()), RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
        }
        return response;
    }

}
