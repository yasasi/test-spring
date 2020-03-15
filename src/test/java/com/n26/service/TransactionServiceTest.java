package com.n26.service;

import com.n26.exception.TransactionException;
import com.n26.model.Transaction;
import com.n26.model.request.TransactionDto;
import com.n26.util.TestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    public Map<UUID, Transaction> transactionMap;

    @Autowired
    private TestUtils testUtils;


    @After
    public void tearDown() {
        transactionMap.clear();
    }

    @Test
    public void testAddTransaction() throws TransactionException {
        Assert.assertEquals(0, transactionMap.values().size());

        LocalDateTime time = LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(20);
        TransactionDto transactionDto = TransactionDto.builder().amount("200")
                .timestamp(time).build();
        transactionService.addTransaction(transactionDto);
        Assert.assertEquals(1, transactionMap.values().size());
    }

    @Test (expected = TransactionException.class)
    public void testAddTransactionWithFutureDate() throws TransactionException {
        Assert.assertEquals(0, transactionMap.values().size());

        LocalDateTime time = LocalDateTime.now(ZoneId.of("UTC")).plusDays(1);
        TransactionDto transactionDto = TransactionDto.builder().amount("200")
                .timestamp(time).build();
        transactionService.addTransaction(transactionDto);
    }

    @Test (expected = TransactionException.class)
    public void testAddTransactionWithInvalidAmount() throws TransactionException {
        Assert.assertEquals(0, transactionMap.values().size());

        LocalDateTime time = LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(20);
        TransactionDto transactionDto = TransactionDto.builder().amount("hello")
                .timestamp(time).build();
        transactionService.addTransaction(transactionDto);
    }


    @Test (expected = TransactionException.class)
    public void testAddTransactionWithTimeIsNotLessThan60Sec() throws TransactionException {
        Assert.assertEquals(0, transactionMap.values().size());

        LocalDateTime time = LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(65);
        TransactionDto transactionDto = TransactionDto.builder().amount("200")
                .timestamp(time).build();
        transactionService.addTransaction(transactionDto);
    }

    @Test
    public void testDeleteTransaction() {
        testUtils.addTransactions(20);
        Assert.assertEquals(2, transactionMap.values().size());

        transactionService.deleteTransactions();
        Assert.assertEquals(0, transactionMap.values().size());
    }

}
