package com.helycopternicht.epocket.services;

import com.helycopternicht.epocket.models.User;
import com.helycopternicht.epocket.services.dtos.UserBalanceResponseDto;


public interface UserBalanceService {

    UserBalanceResponseDto getUserBalance(User user);
}
