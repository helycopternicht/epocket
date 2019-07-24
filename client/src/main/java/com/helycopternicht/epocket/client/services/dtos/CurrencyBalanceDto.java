package com.helycopternicht.epocket.client.services.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CurrencyBalanceDto {
    private String currency;
    private Double balance;
}
