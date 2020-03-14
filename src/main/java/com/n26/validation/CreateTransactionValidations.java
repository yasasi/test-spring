package com.n26.validation;

import com.n26.exception.TransactionException;
import com.n26.model.request.TransactionDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class CreateTransactionValidations {
    public static final long TRANSACTION_PERIOD = 60L;

    private CreateTransactionValidations() {
    }

    public static final void validateTransaction(TransactionDto transactionDto) throws TransactionException {
        try {
            new BigDecimal(transactionDto.getAmount());
        } catch (NumberFormatException ex) {
            throw new TransactionException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (transactionDto.getTimestamp().isAfter(LocalDateTime.now(ZoneId.of("UTC")))) {
            throw new TransactionException(HttpStatus.UNPROCESSABLE_ENTITY);
        } else if (LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(TRANSACTION_PERIOD).isAfter(transactionDto.getTimestamp())) {

            throw new TransactionException(HttpStatus.NO_CONTENT);
        }
    }

}
