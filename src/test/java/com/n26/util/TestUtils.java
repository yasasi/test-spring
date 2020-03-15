package com.n26.util;

import com.n26.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class TestUtils {

    @Autowired
    public Map<UUID, Transaction> transactionMap;

    public void addTransactions (int seconds) {
        Transaction transaction1 = Transaction.builder().amount(BigDecimal.valueOf(200)).
                timestamp(LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(seconds)).build();
        Transaction transaction2 = Transaction.builder().amount(BigDecimal.valueOf(100)).
                timestamp(LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(seconds)).build();
        transactionMap.put(UUID.randomUUID(),transaction1);
        transactionMap.put(UUID.randomUUID(),transaction2);
    }
}
