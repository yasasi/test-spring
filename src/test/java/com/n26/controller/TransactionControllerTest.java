package com.n26.controller;

import com.n26.Application;
import com.n26.ApplicationTests;
import com.n26.TestAdvice;
import com.n26.service.TransactionService;
import com.n26.exception.TransactionException;
import com.n26.model.request.TransactionDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import java.lang.reflect.Method;

import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionController.class)
@ContextConfiguration(classes = {Application.class, ApplicationTests.class})
public class TransactionControllerTest {

    @MockBean
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController statisticController;

    private MockMvc mvc;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(statisticController)
                .setHandlerExceptionResolvers(withExceptionControllerAdvice())
                .build();
    }

    @Test
    public void testCreateTransaction() throws Exception {
        willDoNothing()
                .given(transactionService)
                .addTransaction(Mockito.any(TransactionDto.class));

        mvc.perform(post("/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"amount\" : \"200\",\n" +
                        "  \"timestamp\" : \"2020-03-14T14:06:20.3Z\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddTransactionWithUnprocessableEntity() throws Exception {
        willThrow(new TransactionException(HttpStatus.UNPROCESSABLE_ENTITY)).
                given(transactionService).addTransaction(Mockito.any(TransactionDto.class));

        mvc.perform(post("/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"amount\" : \"200\",\n" +
                        "  \"timestamp\" : \"2020-03-14T14:06:20.3Z\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testAddTransactionWithNoContent() throws Exception {
        willThrow(new TransactionException(HttpStatus.NO_CONTENT)).
                given(transactionService).addTransaction(Mockito.any(TransactionDto.class));

        mvc.perform(post("/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "  \"amount\" : \"200\",\n" +
                        "  \"timestamp\" : \"2020-03-14T14:06:20.3Z\"}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTransactions() throws Exception {
        willDoNothing()
                .given(transactionService)
                .deleteTransactions();

        mvc.perform(delete("/transactions")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private ExceptionHandlerExceptionResolver withExceptionControllerAdvice() {
        final ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
            @Override
            protected ServletInvocableHandlerMethod getExceptionHandlerMethod(final HandlerMethod handlerMethod, final Exception exception) {
                Method method = new ExceptionHandlerMethodResolver(TestAdvice.class).resolveMethod(exception);
                if (method != null) {
                    return new ServletInvocableHandlerMethod(new TestAdvice(), method);
                }
                return super.getExceptionHandlerMethod(handlerMethod, exception);
            }
        };
        exceptionResolver.afterPropertiesSet();
        return exceptionResolver;
    }

}
