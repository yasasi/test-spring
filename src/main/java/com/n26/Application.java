package com.n26;

import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.model.request.TransactionDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public List<String> nameList() {
        return Arrays.asList("John", "Adam", "Harry");
    }

    @Bean
    public ConcurrentHashMap<String, TransactionDto> transactionList() {
        return new ConcurrentHashMap<String, TransactionDto>();
    }

    @Bean
    public Statistics statistics(){
        return new Statistics();
    }

    @Bean
    public ConcurrentHashMap<UUID, Transaction> transactionMap(){
        return new ConcurrentHashMap<>();
    }
}
