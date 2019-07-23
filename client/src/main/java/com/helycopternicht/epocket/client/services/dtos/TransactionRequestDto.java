package com.helycopternicht.epocket.client.services.dtos;

import com.helycopternicht.epocket.api.TransactionRequest;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class TransactionRequestDto {

    private Long userId;
    private String currency;
    private BigDecimal amount;

    public TransactionRequest toTransactionRequest() {
        return TransactionRequest.newBuilder()
                .setUserId(getUserId())
                .setCurrency(getCurrency())
                .setAmount(getAmount().doubleValue())
                .build();
    }
}