package com.helycopternicht.epocket.client.services.dtos;

import com.helycopternicht.epocket.api.Currency;
import com.helycopternicht.epocket.api.TransactionRequest;
import lombok.Getter;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
public class TransactionRequestDto {

    private Long userId;
    private String currency;

    @Positive
    private BigDecimal amount;

    public TransactionRequest toTransactionRequest() {
        return TransactionRequest.newBuilder()
                .setUserId(getUserId())
                .setCurrency(Currency.valueOf(currency))
                .setAmount(getAmount().doubleValue())
                .build();
    }
}
