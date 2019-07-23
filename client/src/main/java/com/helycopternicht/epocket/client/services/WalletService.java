package com.helycopternicht.epocket.client.services;

import com.helycopternicht.epocket.api.UserBalanceResponse;
import com.helycopternicht.epocket.client.services.dtos.TransactionRequestDto;
import com.helycopternicht.epocket.client.services.dtos.UserBalanceRequestDto;

public interface WalletService {

    void doDeposit(TransactionRequestDto request);

    void doWithdraw(TransactionRequestDto request);

    UserBalanceResponse getUserBalance(UserBalanceRequestDto request);

}
