package com.helycopternicht.epocket.client.services.dtos;

import com.helycopternicht.epocket.api.UserBalanceResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class UserBalanceResponseDto {

    private UserDto user;
    private Map<String, Double> balance;

    public static UserBalanceResponseDto from(UserBalanceResponse grpcResponse) {
        return builder()
                .user(
                        UserDto.builder()
                                .id(grpcResponse.getUser().getUserId())
                                .name(grpcResponse.getUser().getName()).build()
                )
                .balance(grpcResponse.getBalancesMap())
                .build();
    }

}
