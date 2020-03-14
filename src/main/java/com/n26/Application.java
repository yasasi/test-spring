package com.n26;

import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.model.request.TransactionDto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ConcurrentHashMap<UUID, Transaction> transactionMap(){
        return new ConcurrentHashMap<>();
    }
}
