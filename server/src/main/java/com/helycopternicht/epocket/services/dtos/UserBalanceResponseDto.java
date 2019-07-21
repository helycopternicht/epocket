package com.helycopternicht.epocket.services.dtos;

import com.helycopternicht.epocket.models.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@Builder
public class UserBalanceResponseDto {
    private Map<Currency, BigDecimal> balance;
}
