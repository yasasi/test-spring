package com.n26.controller;

import com.n26.Application;
import com.n26.ApplicationTests;
import com.n26.service.StatisticsService;
import com.n26.exception.TransactionException;
import com.n26.model.response.StatisticsResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StatisticController.class)
@ContextConfiguration(classes = {Application.class, ApplicationTests.class})
public class StatisticsControllerTest {

    @MockBean
    private StatisticsService statisticsService;

    @InjectMocks
    private StatisticController statisticController;

    private MockMvc mvc;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(statisticController)
                .build();
    }

    @Test
    public void testGetStatistics() throws Exception {
        StatisticsResponse response = StatisticsResponse.builder().sum(BigDecimal.valueOf(300)).
                min(BigDecimal.valueOf(100)).build();

        when(statisticsService.getStatistics()).thenReturn(response);

        mvc.perform(get("/statistics")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string("{\"sum\":300,\"avg\":null,\"max\":null,\"min\":100,\"count\":null}"))
                .andExpect(status().isOk());
    }

}
