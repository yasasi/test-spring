package com.n26.controller;

import com.n26.model.response.StatisticsResponse;
import com.n26.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/statistics")
public class StatisticController {

    private static final Logger logger = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private StatisticsService statisticsService;

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public StatisticsResponse getStatistics() {
        return  statisticsService.getStatistics();
    }

}
