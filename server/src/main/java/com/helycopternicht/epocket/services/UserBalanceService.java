package com.helycopternicht.epocket.services;

import com.helycopternicht.epocket.services.dtos.UserBalanceResponseDto;

public interface UserBalanceService {

    void calculateMonthlyBalance();

    UserBalanceResponseDto getUserBalance(Long userId);

}
