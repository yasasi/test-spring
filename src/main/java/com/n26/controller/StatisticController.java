package com.n26.controller;

import com.n26.model.response.StatisticsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticController.class);

    @GetMapping
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public StatisticsResponse getStatistics() {

        StatisticsResponse response = new StatisticsResponse();
        response.setAvg(BigDecimal.TEN);
        response.setMax(BigDecimal.valueOf(12.0));

        return  response;
    }

}
