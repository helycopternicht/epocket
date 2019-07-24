package com.helycopternicht.epocket.client.services.dtos;

import com.helycopternicht.epocket.api.UserBalanceResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class UserBalanceResponseDto {

    private UserDto user;
    private List<CurrencyBalanceDto> balance;

    public static UserBalanceResponseDto from(UserBalanceResponse grpcResponse) {

        UserDto userDto = UserDto.builder()
                .id(grpcResponse.getUser().getUserId())
                .name(grpcResponse.getUser().getName())
                .build();

        List<CurrencyBalanceDto> balances = grpcResponse.getBalancesList().stream().map(el ->
                CurrencyBalanceDto.builder()
                        .currency(el.getCurrency().toString())
                        .balance(el.getBalance())
                        .build()
        ).collect(Collectors.toList());

        return builder()
                .user(userDto)
                .balance(balances)
                .build();
    }

}
