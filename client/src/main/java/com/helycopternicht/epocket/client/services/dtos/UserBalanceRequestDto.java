package com.helycopternicht.epocket.client.services.dtos;

import com.helycopternicht.epocket.api.UserBalanceRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserBalanceRequestDto {

    private Long userId;

    public UserBalanceRequest toUserBalanceRequest() {
        return UserBalanceRequest.newBuilder()
                .setUserId(getUserId())
                .build();
    }

}
