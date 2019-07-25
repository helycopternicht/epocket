package com.helycopternicht.epocket.client.services;

import com.helycopternicht.epocket.api.TransactionRequest;
import com.helycopternicht.epocket.api.UserBalanceRequest;
import com.helycopternicht.epocket.api.UserBalanceResponse;

public interface WalletService {

    void doDeposit(TransactionRequest request);

    void doWithdraw(TransactionRequest request);

    UserBalanceResponse getUserBalance(UserBalanceRequest request);
}
