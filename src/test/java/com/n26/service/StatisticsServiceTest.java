package com.n26.service;

import com.n26.model.Transaction;
import com.n26.model.response.StatisticsResponse;
import com.n26.util.TestUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsServiceTest {

    @Autowired
    private StatisticsService statisticsService;

    @Autowired
    public Map<UUID, Transaction> transactionMap;

    @Autowired
    private TestUtils testUtils;

    @After
    public void tearDown() {
        transactionMap.clear();
    }

    @Test
    public void testGetStatistics() {
        testUtils.addTransactions(2);
        StatisticsResponse response = statisticsService.getStatistics();
        Assert.assertEquals(Long.valueOf(2L), response.getCount());
        Assert.assertEquals(BigDecimal.valueOf(200).setScale(2), response.getMax());
        Assert.assertEquals(BigDecimal.valueOf(100).setScale(2), response.getMin());
        Assert.assertEquals(BigDecimal.valueOf(300).setScale(2), response.getSum());
    }

    @Test
    public void testGetStatisticsEmpty() {
        StatisticsResponse response = statisticsService.getStatistics();
        Assert.assertEquals(Long.valueOf(0L), response.getCount());
        Assert.assertEquals(BigDecimal.ZERO.setScale(2), response.getSum());
    }

    //@Test
    public void testGetStatisticsPastValues() {
        testUtils.addTransactions(65);
        StatisticsResponse response = statisticsService.getStatistics();
        Assert.assertEquals(Long.valueOf(0L), response.getCount());
        Assert.assertEquals(BigDecimal.ZERO.setScale(2),response.getSum());
    }
}
