package com.helycopternicht.epocket.services.impl;

import com.helycopternicht.epocket.services.UserBalanceService;
import com.helycopternicht.epocket.services.dtos.UserBalanceResponseDto;
import org.springframework.stereotype.Service;

@Service
public class UserBalanceServiceImpl implements UserBalanceService {

    @Override
    public void calculateMonthlyBalance() {

    }

    @Override
    public UserBalanceResponseDto getUserBalance(Long userId) {
        return null;
    }
}
